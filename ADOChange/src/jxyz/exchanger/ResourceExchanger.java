package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import jxyz.utils.HttpUtil;
import jxyz.utils.Tools;

/**
 * 处理资源数据，涉及到的看板有
 * 作战图：学校，工厂，住宅，商业地产数量
 * 微网格：4个类型的段道数量，覆盖率，开发率
 * 输入表：t_grid_m，dwr_jxyz_resources_d，dwr_jxyz_customer_d
 * 输出表：jpx_volumn_collect_d
 * @author xiaoxin
 *
 */
public class ResourceExchanger implements Exchanger{
	
	@SuppressWarnings("unused")
	public void process(Connection connection) throws Exception
    {
    	PreparedStatement p = connection.prepareStatement("select * from t_grid_m t where t.level = 5");
		ResultSet rs = p.executeQuery();
		int count = 0;
		
		
		PreparedStatement dp = connection.prepareStatement(
				"truncate  jpx_resource_collect_d");
		dp.executeUpdate();
		dp.close();

		while (rs.next()) {
			String grid_code = rs.getString("code");
			//总数量
			int schoolnum = 0;
			int gyynum = 0;
			int housenum = 0;
			int businum = 0;
			
			//覆盖数量
			int school_cover = 0;
			int gyy_cover = 0;
			int house_cover = 0;
			int busi_cover = 0;
			
			//客户总数
			int school_custom = 0;
			int gyy_custom = 0;
			int house_custom = 0;
			int busi_custom = 0;
			
			//已开发客户总数
			int school_dev_custom = 0;
			int gyy_dev_custom = 0;
			int house_dev_custom = 0;
			int busi_dev_custom = 0;
			
			//总人数
			int school_user_num = 0;
			int gyy_user_num = 0;
			int house_user_num = 0;
			int busi_user_num = 0;
			
			String dtype = "";
			
			PreparedStatement p2 = connection.prepareStatement(
					"select code,employee_number from t_biz_campus_m  where grid_code = ? ");
			p2.setString(1, rs.getString("code"));
			ResultSet campus = p2.executeQuery(); // 高校
			while (campus.next()) {
				dtype = "学校";
				Integer userNum = campus.getInt("employee_number");
				String market_key = campus.getString("code");
				if(userNum == null)
				{
					userNum = 0;
				}
				schoolnum++;
				school_user_num += userNum;
				PreparedStatement customer = connection.prepareStatement(
						"select count(1) as num from t_biz_campus_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code where a.code = ? ");
				customer.setString(1, market_key);
				ResultSet customerRS = customer.executeQuery();
				while (customerRS.next()) {
					Integer num = customerRS.getInt("num");
					school_custom+=num;
				}
				customerRS.close();
				customer.close();
				
				PreparedStatement contract = connection.prepareStatement(
						"select count(c.code) as num from t_biz_campus_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code left join t_biz_enterprise_customer_instance_m d on c.code = d.enterprise_code left join t_biz_enterprise_customer_contract_m e on d.id = e.enterprise_instance_id where e.status = '1' and e.is_end = '0' and a.code = ? GROUP BY c.code ");
				contract.setString(1, market_key);
				ResultSet contractRs = contract.executeQuery();
				boolean is_cover = false;
				while (contractRs.next()) {
					Integer num = contractRs.getInt("num");
					if (num!= null && num > 0) {
						is_cover = true;
						school_dev_custom+=num;
					}
				}
				contractRs.close();
				contract.close();
				if(is_cover)
				{
					school_cover++;
				}	
			}
			campus.close();
			p2.close();
			
			
			PreparedStatement p3 = connection.prepareStatement(
					"select code,employee_number from t_biz_enterprise_m  where grid_code = ? ");
			p3.setString(1, rs.getString("code"));
			ResultSet enterprise = p3.executeQuery(); // 集团
			while (enterprise.next()) {
				dtype = "工业园区集群";
				Integer userNum = enterprise.getInt("employee_number");
				String market_key = enterprise.getString("code");
				if(userNum == null){
					userNum = 0;
				}
				gyynum++;
				gyy_user_num += userNum;
				PreparedStatement customer = connection.prepareStatement(
						"select count(1) as num from t_biz_enterprise_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code where a.code = ? ");
				customer.setString(1, market_key);
				ResultSet customerRS = customer.executeQuery();
				while (customerRS.next()) {
					Integer num = customerRS.getInt("num");
					gyy_custom+=num;
				}
				customerRS.close();
				customer.close();
				
				PreparedStatement contract = connection.prepareStatement(
						"select count(c.code) as num from t_biz_enterprise_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code left join t_biz_enterprise_customer_instance_m d on c.code = d.enterprise_code left join t_biz_enterprise_customer_contract_m e on d.id = e.enterprise_instance_id where e.status = '1' and e.is_end = '0' and a.code = ? GROUP BY c.code ");
				contract.setString(1, market_key);
				ResultSet contractRs = contract.executeQuery();
				boolean is_cover = false;
				while (contractRs.next()) {
					Integer num = contractRs.getInt("num");
					if (num!= null && num > 0) {
						is_cover = true;
						gyy_dev_custom+=num;
					}
				}
				contractRs.close();
				contract.close();
				if(is_cover)
				{
					gyy_cover++;
				}	
			}
			enterprise.close();
			p3.close();
			
			PreparedStatement p1 = connection.prepareStatement("select code,customer_total from t_biz_building_m  where grid_code = ? ");
			p1.setString(1, rs.getString("code"));
			ResultSet building = p1.executeQuery(); // 商业楼宇
			while (building.next()) {
				dtype = "商业楼宇";
				Integer userNum = building.getInt("customer_total");
				String market_key = building.getString("code");
				if(userNum == null)
				{
					userNum = 0;
				}
				businum++;
				busi_user_num += userNum;
				PreparedStatement customer = connection.prepareStatement(
						"select count(1) as num from t_biz_building_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code where a.code = ? ");
				customer.setString(1, market_key);
				ResultSet customerRS = customer.executeQuery();
				while (customerRS.next()) {
					Integer num = customerRS.getInt("num");
					busi_custom+=num;
				}
				customerRS.close();
				customer.close();
				
				PreparedStatement contract = connection.prepareStatement(
						"select count(c.code) as num from t_biz_building_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code left join t_biz_enterprise_customer_instance_m d on c.code = d.enterprise_code left join t_biz_enterprise_customer_contract_m e on d.id = e.enterprise_instance_id where e.status = '1' and e.is_end = '0' and a.code = ? GROUP BY c.code ");
				contract.setString(1, market_key);
				ResultSet contractRs = contract.executeQuery();
				boolean is_cover = false;
				while (contractRs.next()) {
					Integer num = contractRs.getInt("num");
					if (num!= null && num > 0) {
						is_cover = true;
						busi_dev_custom+=num;
					}
				}
				contractRs.close();
				contract.close();
				if(is_cover)
				{
					busi_cover++;
				}	
			}
			building.close();
			p1.close();
			
			
			PreparedStatement p4 = connection.prepareStatement(
					"select code,actual_householdcount from t_uptown_m  where grid_code = ? ");
			p4.setString(1, rs.getString("code"));
			ResultSet uptown = p4.executeQuery(); // 小区
			while (uptown.next()) {
				dtype = "住宅小区";
				Integer userNum = uptown.getInt("actual_householdcount");
				String market_key = uptown.getString("code");
				if(userNum == null){
					userNum = 0;
				}
				housenum++;
				house_user_num += userNum;
				PreparedStatement customer = connection.prepareStatement(
						"select count(1) as num from t_uptown_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code where a.code = ? ");
				customer.setString(1, market_key);
				ResultSet customerRS = customer.executeQuery();
				while (customerRS.next()) {
					Integer num = customerRS.getInt("num");
					house_custom+=num;
				}
				customerRS.close();
				customer.close();
				
				PreparedStatement contract = connection.prepareStatement(
						"select count(c.code) as num from t_uptown_m a left join t_biz_resource_customer_relation b on a.code = b.resource_code left join t_biz_enterprise_customer_m c on b.customer_code = c.code left join t_biz_enterprise_customer_instance_m d on c.code = d.enterprise_code left join t_biz_enterprise_customer_contract_m e on d.id = e.enterprise_instance_id where e.status = '1' and e.is_end = '0' and a.code = ?  GROUP BY c.code");
				contract.setString(1, market_key);
				ResultSet contractRs = contract.executeQuery();
				boolean is_house_cover = false;
				while (contractRs.next()) {
					Integer num = contractRs.getInt("num");
					if (num!= null && num > 0) {
						is_house_cover = true;
						house_dev_custom+=num;
					}
				}
				contractRs.close();
				contract.close();
				if(is_house_cover)
				{
					house_cover++;
				}	
			}
			uptown.close();
			p4.close();
			
			if(schoolnum > 0) {
				dtype = "学校";
			} else if(gyynum > 0) {
				dtype = "工业园区集群";
			} else if(businum > 0) {
				dtype = "商业楼宇";
			} else {
				dtype = "住宅小区";
			}
			
			System.out.println(dtype + "   " +grid_code + " " + schoolnum + " " + gyynum + " " + businum + " " + housenum);
		
			//写入看板资源表
			PreparedStatement write = connection.prepareStatement("insert into jpx_resource_collect_d values(?,?,?,?,?,?,?,?,?,?,?,null,null,null,null,null,?,?,?,?,?,?,?,?,?,?,?,?)");
			write.setString(1, Tools.createUUID());
			write.setString(2, grid_code);
			write.setString(3, dtype);
			write.setInt(4, schoolnum);
			write.setInt(5, gyynum);
			write.setInt(6, housenum);
			write.setInt(7, businum);
				
			
			write.setInt(8, school_user_num);
			write.setInt(9, gyy_user_num);
			write.setInt(10, house_user_num);
			write.setInt(11, busi_user_num);
			
			write.setInt(12, school_cover);
			write.setInt(13, gyy_cover);
			write.setInt(14, house_cover);
			write.setInt(15, busi_cover);
			
			write.setInt(16, school_custom);
			write.setInt(17, gyy_custom);
			write.setInt(18, house_custom);
			write.setInt(19, busi_custom);
			
			write.setInt(20, school_dev_custom);
			write.setInt(21, gyy_dev_custom);
			write.setInt(22, house_dev_custom);
			write.setInt(23, busi_dev_custom);
			

			write.executeUpdate();
			
			count++;
			write.close();
		}
		rs.close();
		p.close();
		System.out.println("写入jpx_volumn_collect_d" + " 记录数:" + count);
		
		Map<String,String> transferMap = new HashMap<>();
		transferMap.put("tableName", "jpx_resource_collect_d");
		String selectSql = "select * from jpx_resource_collect_d ";
		transferMap.put("selectSql", selectSql);
		transferMap.put("prefix", "TRUNCATE jpx_resource_collect_d");
		HttpUtil.upload(transferMap);
    }
	

}
