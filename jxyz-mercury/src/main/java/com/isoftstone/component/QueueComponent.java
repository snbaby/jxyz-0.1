package com.isoftstone.component;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class QueueComponent {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@RabbitListener(queues = "${jxyz.rabbitmq.queue}")
	@RabbitHandler
	public void mercuryQueue(JSONObject messageJsb, Channel channel, Message message) throws SQLException, IOException {
		System.out.println("mercuryQueue收到  : " + messageJsb.toJSONString() + "收到时间" + new Date());
		try {
			if(verifyData(messageJsb)) {
				parseMessage(messageJsb);
			}
			// 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			log.info("receiver success");
		} catch (Exception e) {
			e.printStackTrace();
			// 丢弃这条消息
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			log.info("receiver fail");
		}
	}
	
	/**
	 * 校验数据是否未有效数据
	 * 基础产品代码为11210、11312、21210、22210、21412
	 * 总邮费必须大于0
	 * @param messageJsb
	 * @return
	 */
	private boolean verifyData(JSONObject messageJsb) {
		Boolean status = false;
		String base_product_no = messageJsb.getString("base_product_no"); // 基础产品代码   11210:国内特快专递  11312:国内快递包裹  '21210','22210','21412'：国际快递
		Double postage_total = messageJsb.getDouble("postage_total");
		if (postage_total != null && postage_total > 0 && ("11312".equals(base_product_no) || "11210".equals(base_product_no) || "21210".equals(base_product_no) || "22210".equals(base_product_no) || "21412".equals(base_product_no))) {
			status = true;
		}
		return status;
	}
	
	private void parseMessage(JSONObject messageJsb) throws SQLException {
		DbContext ctx = DbContext.getGlobalDbContext();
		String base_product_no = messageJsb.getString("base_product_no"); // 基础产品代码   11210:国内特快专递  11312:国内快递包裹  '21210','22210','21412'：国际快递
		String settlement_mode = messageJsb.getString("settlement_mode"); // '结算方式 1:现结 2:计欠 3:预付费 '
		String payment_state = messageJsb.getString("payment_state"); // '支付状态 0未支付 1已支付'
		String contents_attribute = messageJsb.getString("contents_attribute"); // '内件性质 1：文件 2：信函 3：物品 4：包裹'
		String sender_type = messageJsb.getString("sender_type"); // '寄件客户类型：0 散户  1协议客户'
		
		String post_person_no = messageJsb.getString("post_person_no");
		Date biz_occur_date = messageJsb.getDate("biz_occur_date");
		Double order_weight = messageJsb.getDouble("order_weight");
		Double real_weight = messageJsb.getDouble("real_weight");
		Double fee_weight = messageJsb.getDouble("fee_weight");
		Double postage_total = messageJsb.getDouble("postage_total");
		Double postage_standard = messageJsb.getDouble("postage_standard");
		Double postage_paid = messageJsb.getDouble("postage_paid");
		Double postage_other = messageJsb.getDouble("postage_other");
		Double total_current_charges = 0d; // 日现结总资费
		Double total_charge_owed = 0d; //日记欠总资费
		Double total_prepaid_charges = 0d; // 日预付费总资费
		Double unpaid_amount = 0d; // 未支付金额
		Double payment_amount = 0d; // 已支付金额
		
		Integer collected_qty = 1; // 揽收总量
		Integer standard_express_collection = 0; // 标准快递揽收量
		Integer international_sales_volume = 0; // 国际揽收量
		Integer package_collection = 0; // 包裹揽收量
		Integer signed_collection = 0; // 签约客户揽收量
		Integer cash_collection = 0; // 现费揽收量
		Integer files_collection = 0; // 文件揽收量
		Integer goods_collection = 0; // 物品揽收量
		
		Double collected_salary = postage_total; // 揽收收入
		Double standard_express_salary = 0d; // 标快揽收收入
		Double package_collection_salary = 0d; // 包裹揽收收入
		Double international_sales_salary = 0d; // 国际揽收收入
		Double signed_salary = 0d; // 签约客户揽收收入
		Double cash_salary = 0d; // 现费揽收收入
		Double files_salary = 0d; // 文件揽收收入
		Double goods_salary = 0d; // 物品揽收收入

		if ("11210".equals(base_product_no)) { // 标快
			standard_express_collection++;
			standard_express_salary = postage_total;
		} else if ("11312".equals(base_product_no)) { // 包裹
			package_collection++;
			package_collection_salary = postage_total;
		} else if ("21210".equals(base_product_no) || "22210".equals(base_product_no) || "21412".equals(base_product_no)) { // 国际快递
			international_sales_volume++;
			international_sales_salary = postage_total;
		}
		
		if ("1".equals(settlement_mode)) { // 现结
			total_current_charges = postage_total;
		} else if ("2".equals(settlement_mode)) { // 记欠
			total_charge_owed = postage_total;
		} else if ("3".equals(settlement_mode)) { // 预付款
			total_prepaid_charges = postage_total;
		}
		
		if ("0".equals(payment_state)) { // 未付款
			unpaid_amount = postage_total;
		} else if ("1".equals(payment_state)) { // 已付款
			payment_amount = postage_total;
		}
		
		if ("1".equals(contents_attribute)) { // 文件类型
			files_collection++;
			files_salary = postage_total;
		} else if ("3".equals(contents_attribute)) { // 物品类型
			goods_collection++;
			goods_salary = postage_total;
		}
		
		if ("1".equals(sender_type)) { // 协议用户
			signed_collection++;
			signed_salary = postage_total;
		} else if ("0".equals(sender_type)) { // 散户
			cash_collection++;
			cash_salary = postage_total;
		}

		int num = ctx.qryIntValue("select count(1) as num from base_emp_daily_collection_t_2020 where period_id = '"+sdf.format(biz_occur_date)+"' and post_person_no = '"+post_person_no+"' ");
		String sqlStr = "";
		if (num > 0) {
			sqlStr = "UPDATE base_emp_daily_collection_t_2020 SET \r\n" + 
					" order_weight = order_weight + ?, real_weight = real_weight + ?,\r\n" + 
					" fee_weight = fee_weight + ?, postage_total = postage_total + ?,\r\n" + 
					" postage_standard = postage_standard + ?, postage_paid = postage_paid + ?,\r\n" + 
					" postage_other = order_weight + ?, total_current_charges = order_weight + ?,\r\n" + 
					" total_charge_owed = total_charge_owed + ?, total_prepaid_charges = total_prepaid_charges + ?,\r\n" + 
					" unpaid_amount = unpaid_amount + ?, payment_amount = payment_amount + ?,\r\n" + 
					" collected_qty = collected_qty + ?, standard_express_collection = standard_express_collection + ?,\r\n" + 
					" international_sales_volume = international_sales_volume + ?, package_collection = package_collection + ?,\r\n" + 
					" signed_collection = signed_collection + ?, cash_collection = cash_collection + ?,\r\n" + 
					" files_collection = files_collection + ?, goods_collection = goods_collection + ?,\r\n" + 
					" collected_salary = collected_salary + ?, standard_express_salary = standard_express_salary + ?,\r\n" + 
					" package_collection_salary = package_collection_salary + ?, international_sales_salary = international_sales_salary + ?,\r\n" + 
					" signed_salary = signed_salary + ?, cash_salary = cash_salary + ?,\r\n" + 
					" files_salary = files_salary + ?, goods_salary = goods_salary + ? where period_id = ? and post_person_no = ? ;\r\n";
			ctx.execute(sqlStr,order_weight, real_weight, fee_weight, postage_total, postage_standard, postage_paid, postage_other, total_current_charges, total_charge_owed, total_prepaid_charges, unpaid_amount, payment_amount, collected_qty, standard_express_collection, international_sales_volume, package_collection, signed_collection, cash_collection, files_collection, goods_collection, collected_salary, standard_express_salary, package_collection_salary, international_sales_salary, signed_salary, cash_salary, files_salary, goods_salary,sdf.format(biz_occur_date), post_person_no);	
		} else {
			sqlStr = "INSERT INTO `base_emp_daily_collection_t_2020`(`period_id`, `post_person_no`, `order_weight`, `real_weight`, `fee_weight`, `postage_total`, `postage_standard`, `postage_paid`, `postage_other`, `total_current_charges`, `total_charge_owed`, `total_prepaid_charges`, `unpaid_amount`, `payment_amount`, `collected_qty`, `standard_express_collection`, `international_sales_volume`, `package_collection`, `signed_collection`, `cash_collection`, `files_collection`, `goods_collection`, `collected_salary`, `standard_express_salary`, `package_collection_salary`, `international_sales_salary`, `signed_salary`, `cash_salary`, `files_salary`, `goods_salary`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ctx.execute(sqlStr,sdf.format(biz_occur_date), post_person_no, order_weight, real_weight, fee_weight, postage_total, postage_standard, postage_paid, postage_other, total_current_charges, total_charge_owed, total_prepaid_charges, unpaid_amount, payment_amount, collected_qty, standard_express_collection, international_sales_volume, package_collection, signed_collection, cash_collection, files_collection, goods_collection, collected_salary, standard_express_salary, package_collection_salary, international_sales_salary, signed_salary, cash_salary, files_salary, goods_salary);	
		}
	}
}
