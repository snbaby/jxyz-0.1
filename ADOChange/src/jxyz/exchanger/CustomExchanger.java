package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import jxyz.Application;
import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 处理客户资源
 * 输入表：sdi_jxyz_pkp_waybill_base_$，dwr_jxyz_department_d
 * 输出表：dwr_jxyz_customer_d
 * @author xiaoxin
 *
 */
public class CustomExchanger implements Exchanger {

	@Override
	public void process(Connection connection) throws Exception {
		
		String querySQL = "SELECT sender_id, sender_no, sender,MAX(t.`biz_occur_date`) last_sender_date, sender_warehouse_id, sender_warehouse_name, d.dept_code, d.dept_name FROM sdi_jxyz_pkp_waybill_base_${C_YEAR} t, dwr_jxyz_department_d d  WHERE t.biz_occur_date >= '${START_DATE}'  AND t.biz_occur_date <= '${END_DATE}' AND  t.sender_type = '1' AND t.post_org_no = d.dept_code and 1=1 GROUP BY sender_id, sender_no, sender, sender_warehouse_id, sender_warehouse_name, d.dept_code, d.dept_name";
		//替换变量
		Map<String, String> params = new HashMap<String, String>();
		params.put("C_YEAR", (String)Application.GLOBAL_PARAM.get(Application.CURR_YEAR));
		params.put("START_DATE", (String)Application.GLOBAL_PARAM.get(Application.LAST_DAY));
		params.put("END_DATE", (String)Application.GLOBAL_PARAM.get(Application.CURR_DAY));
		querySQL = Tools.parse(querySQL, params);
		
		PreparedStatement p = connection.prepareStatement(querySQL);
		ResultSet rs = p.executeQuery();
		int insertCount = 0;
		int updateCount = 0;
		
		
		PreparedStatement findPs = connection.prepareStatement("select count(1) from dwr_jxyz_customer_d where sender_no=?");
		
		PreparedStatement updatePs = connection.prepareStatement("update dwr_jxyz_customer_d set sender_id = ?, sender_no = ?, sender = ?, last_sender_date = ?, sender_warehouse_id = ?, sender_warehouse_name = ?, post_org_no=?,post_org_name=?  where sender_no = ?");

		PreparedStatement insert = connection.prepareStatement("insert into dwr_jxyz_customer_d (sender_id,sender_no,sender,last_sender_date,sender_warehouse_id,sender_warehouse_name,post_org_no,post_org_name) value(?,?,?,?,?,?,?,?)");
		
		while(rs.next())
		{
			//查找员工ID是否存在目标表，如果有则更新没有则插入
			findPs.setString(1, rs.getString("sender_no"));
			ResultSet frs = findPs.executeQuery();
			int recordCount = 0;
			if(frs.next())
			{
				recordCount = frs.getInt(1);
			}
			frs.close();
			if(recordCount > 0)
			{
				//更新
				updatePs.setString(1, rs.getString("sender_id"));
				updatePs.setString(2, rs.getString("sender_no"));
				updatePs.setString(3, rs.getString("sender"));
				updatePs.setDate(4, rs.getDate("last_sender_date"));
				updatePs.setInt(5, rs.getInt("sender_warehouse_id"));
				updatePs.setString(6, rs.getString("sender_warehouse_name"));
				updatePs.setString(7, rs.getString("dept_code"));
				updatePs.setString(8, rs.getString("dept_name"));
				updatePs.setString(9, rs.getString("sender_no"));
				updatePs.executeUpdate();
				updateCount++;
			}
			else
			{
				//插入
				insert.setInt(1, rs.getInt("sender_id"));
				insert.setString(2, rs.getString("sender_no"));
				insert.setString(3, rs.getString("sender"));
				insert.setDate(4, rs.getDate("last_sender_date"));
				insert.setInt(5, rs.getInt("sender_warehouse_id"));
				insert.setString(6, rs.getString("sender_warehouse_name"));
				insert.setString(7, rs.getString("dept_code"));
				insert.setString(8, rs.getString("dept_name"));
				insert.executeUpdate();
				insertCount++;
			}
		}
		rs.close();
		p.close();
		findPs.close();
		updatePs.close();
		insert.close();
		System.out.println(String.format("输出dwr_jxyz_customer_d，更新记录: %d, 插入进入数：%s", updateCount, insertCount));
		
		Map<String,String> map = new HashMap<>();
		map.put("tableName", "dwr_jxyz_customer_d");
		String selectSql = "select * from dwr_jxyz_customer_d ";
		map.put("selectSql", selectSql);
		map.put("prefix", "TRUNCATE dwr_jxyz_customer_d");
		HttpUtil.upload(map);
	}

}
