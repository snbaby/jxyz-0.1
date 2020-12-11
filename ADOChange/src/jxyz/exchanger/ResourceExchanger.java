package jxyz.exchanger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
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
		

			PreparedStatement p1 = connection.prepareStatement(
					"select resources_type, key_market_code, users_qty from dwr_jxyz_resources_d  where grid_code = ? ");
			p1.setString(1, rs.getString("code"));

			String grid_code = rs.getString("code");
			
			
			
			ResultSet rs1 = p1.executeQuery();

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
			
			while (rs1.next()) {
				
				String type = rs1.getString("resources_type");
				String market_key = rs1.getString("key_market_code");
				if(market_key.equals("校园市场"))
				{
					System.out.println("");
				}
				Integer userNum = rs1.getInt("users_qty");
				if(userNum == null)
				{
					userNum = 0;
				}
				
				if(type.contains("学校"))
				{
					schoolnum++;
					school_user_num += userNum;
					if(dtype.length() == 0)
					{
						dtype = "学校";
					}
				}
				if(type.contains("工业园区集群"))
				{
					gyynum++;
					gyy_user_num += userNum;
					if(dtype.length() == 0)
					{
						dtype = "工业园区集群";
					}
				}
				if(type.contains("商业楼宇"))
				{
					businum++;
					busi_user_num += userNum;
					if(dtype.length() == 0)
					{
						dtype = "商业楼宇";
					}
				}
				if(type.contains("住宅小区"))
				{
					housenum++;
					house_user_num += userNum;
					if(dtype.length() == 0)
					{
						dtype = "住宅小区";
					}
				}	
				
				PreparedStatement p2 = connection.prepareStatement(
						"select * from dwr_jxyz_customer_d  where key_market_code = ? ");
				p2.setString(1, market_key);
				ResultSet rs2 = p2.executeQuery();
				

				boolean is_school_cover = false;
				boolean is_gyy_cover = false;
				boolean is_house_cover = false;
				boolean is_business_cover = false;
				while (rs2.next()) {
					if(type.contains("学校"))
					{
						school_custom++;
					}
					if(type.contains("工业园区集群"))
					{
						gyy_custom++;
					}
					if(type.contains("商业楼宇"))
					{
						busi_custom++;
					}
					if(type.contains("住宅小区"))
					{
						house_custom++;
					}	
					
					String sender_no = rs2.getString("sender_no");
					Date date = rs2.getDate("contract_expiration_time");
					if(sender_no != null && date != null && System.currentTimeMillis() < date.getTime())
					{
						if(type.contains("学校"))
						{
							is_school_cover = true;
							school_dev_custom++;
						}
						if(type.contains("工业园区集群"))
						{
							is_gyy_cover = true;
							gyy_dev_custom++;
						}
						if(type.contains("商业楼宇"))
						{
							is_business_cover = true;
							busi_dev_custom++;
						}
						if(type.contains("住宅小区"))
						{
							is_house_cover = true;
							house_dev_custom++;
						}	
					}
				}
				rs2.close();
				p2.close();
				if(is_school_cover)
				{
					school_cover++;
				}
				if(is_gyy_cover)
				{
					gyy_cover++;
				}
				if(is_house_cover)
				{
					house_cover++;
				}
				if(is_business_cover)
				{
					busi_cover++;
				}				
				
			}
			rs1.close();
			p1.close();
			
			if(dtype.length() == 0)
			{
				dtype = "住宅小区";
			}
			
			System.out.println(grid_code + " " + schoolnum + " " + school_custom + " " + school_dev_custom + " " + school_cover);
		

			
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
