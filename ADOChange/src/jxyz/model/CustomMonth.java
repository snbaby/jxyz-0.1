package jxyz.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import jxyz.utils.Tools;

public class CustomMonth {
	
	private long id;
	private String post_org_no;
	private String post_org_name;
	private String sender_country_no;
	private String sender_country_name;
	private String sender_province_no;
	private String sender_province_name;
	private String sender_city_no;
	private String sender_city_name;
	private String sender_county_no;
	private String sender_county_name;
	private String sender_district_no;
	private long sender_id;
	private String sender_no;
	private String sender;
	private long sender_warehouse_id;
	private String sender_warehouse_name;
	private String period_id;
	private BigDecimal order_weight;
	private BigDecimal real_weight;
	private BigDecimal fee_weight;
	private BigDecimal postage_total;
	private BigDecimal last_month_postage_total;
	private BigDecimal postage_standard;
	private BigDecimal postage_paid;
	private BigDecimal postage_other;
	private BigDecimal total_current_charges;
	private BigDecimal total_charge_owed;
	private BigDecimal total_prepaid_charges;
	private BigDecimal unpaid_amount;
	private BigDecimal payment_amount;
	private BigDecimal collected_qty;
	private BigDecimal last_month_collection_qty;
	private BigDecimal month_delivery_qty;
	private BigDecimal last_month_delivery_qty;
	private BigDecimal daily_effective_person;
	private BigDecimal files_qty;
	private BigDecimal last_month_files_qty;
	private BigDecimal goods_qty;
	private BigDecimal last_month_goods_qty;
	private BigDecimal is_new_customer;
	private String customer_send_time_type;
	private BigDecimal customer_send_qty1;
	private BigDecimal customer_send_qty2;
	private BigDecimal customer_send_qty3;
	private BigDecimal cur_year_collection_total;
	private BigDecimal last_year_collection_qty;
	private BigDecimal last_year_total_qty;
	private BigDecimal cur_year_postage_total;
	private BigDecimal last_year_postage_total;
	private BigDecimal last_year_all_postage_total;
	private Date created_date;
	private String created_by;
	private String extend_column;
	private String create_user;
	private Date create_date;
	private String modify_user;
	private Date modify_date;
	
	public CustomMonth(ResultSet rs) throws Exception
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		int size = rsmd.getColumnCount();
		for (int i = 0; i < size; i++) {
			String fname = rsmd.getColumnName(i + 1);
//			System.out.println(fname + "+++++ " + rsmd.getColumnTypeName(i + 1));
			if(!Tools.isExistFieldName(fname, this.getClass()))
			{
				continue;
			}
			Field f = this.getClass().getDeclaredField(fname);
			if(f != null)
			{
				f.set(this, rs.getObject(rsmd.getColumnLabel(i + 1)));
			}
			else
			{
				System.out.println("error： 未知字段: " + rs.getObject(rsmd.getColumnLabel(i + 1)));
			}
		}
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPost_org_no() {
		return post_org_no;
	}
	public void setPost_org_no(String post_org_no) {
		this.post_org_no = post_org_no;
	}
	public String getPost_org_name() {
		return post_org_name;
	}
	public void setPost_org_name(String post_org_name) {
		this.post_org_name = post_org_name;
	}
	public String getSender_country_no() {
		return sender_country_no;
	}
	public void setSender_country_no(String sender_country_no) {
		this.sender_country_no = sender_country_no;
	}
	public String getSender_country_name() {
		return sender_country_name;
	}
	public void setSender_country_name(String sender_country_name) {
		this.sender_country_name = sender_country_name;
	}
	public String getSender_province_no() {
		return sender_province_no;
	}
	public void setSender_province_no(String sender_province_no) {
		this.sender_province_no = sender_province_no;
	}
	public String getSender_province_name() {
		return sender_province_name;
	}
	public void setSender_province_name(String sender_province_name) {
		this.sender_province_name = sender_province_name;
	}
	public String getSender_city_no() {
		return sender_city_no;
	}
	public void setSender_city_no(String sender_city_no) {
		this.sender_city_no = sender_city_no;
	}
	public String getSender_city_name() {
		return sender_city_name;
	}
	public void setSender_city_name(String sender_city_name) {
		this.sender_city_name = sender_city_name;
	}
	public String getSender_county_no() {
		return sender_county_no;
	}
	public void setSender_county_no(String sender_county_no) {
		this.sender_county_no = sender_county_no;
	}
	public String getSender_county_name() {
		return sender_county_name;
	}
	public void setSender_county_name(String sender_county_name) {
		this.sender_county_name = sender_county_name;
	}
	public String getSender_district_no() {
		return sender_district_no;
	}
	public void setSender_district_no(String sender_district_no) {
		this.sender_district_no = sender_district_no;
	}
	public long getSender_id() {
		return sender_id;
	}
	public void setSender_id(long sender_id) {
		this.sender_id = sender_id;
	}
	public String getSender_no() {
		return sender_no;
	}
	public void setSender_no(String sender_no) {
		this.sender_no = sender_no;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public long getSender_warehouse_id() {
		return sender_warehouse_id;
	}
	public void setSender_warehouse_id(long sender_warehouse_id) {
		this.sender_warehouse_id = sender_warehouse_id;
	}
	public String getSender_warehouse_name() {
		return sender_warehouse_name;
	}
	public void setSender_warehouse_name(String sender_warehouse_name) {
		this.sender_warehouse_name = sender_warehouse_name;
	}
	public String getPeriod_id() {
		return period_id;
	}
	public void setPeriod_id(String period_id) {
		this.period_id = period_id;
	}
	public BigDecimal getOrder_weight() {
		return order_weight;
	}
	public void setOrder_weight(BigDecimal order_weight) {
		this.order_weight = order_weight;
	}
	public BigDecimal getReal_weight() {
		return real_weight;
	}
	public void setReal_weight(BigDecimal real_weight) {
		this.real_weight = real_weight;
	}
	public BigDecimal getFee_weight() {
		return fee_weight;
	}
	public void setFee_weight(BigDecimal fee_weight) {
		this.fee_weight = fee_weight;
	}
	public BigDecimal getPostage_total() {
		return postage_total;
	}
	public void setPostage_total(BigDecimal postage_total) {
		this.postage_total = postage_total;
	}
	public BigDecimal getLast_month_postage_total() {
		return last_month_postage_total;
	}
	public void setLast_month_postage_total(BigDecimal last_month_postage_total) {
		this.last_month_postage_total = last_month_postage_total;
	}
	public BigDecimal getPostage_standard() {
		return postage_standard;
	}
	public void setPostage_standard(BigDecimal postage_standard) {
		this.postage_standard = postage_standard;
	}
	public BigDecimal getPostage_paid() {
		return postage_paid;
	}
	public void setPostage_paid(BigDecimal postage_paid) {
		this.postage_paid = postage_paid;
	}
	public BigDecimal getPostage_other() {
		return postage_other;
	}
	public void setPostage_other(BigDecimal postage_other) {
		this.postage_other = postage_other;
	}
	public BigDecimal getTotal_current_charges() {
		return total_current_charges;
	}
	public void setTotal_current_charges(BigDecimal total_current_charges) {
		this.total_current_charges = total_current_charges;
	}
	public BigDecimal getTotal_charge_owed() {
		return total_charge_owed;
	}
	public void setTotal_charge_owed(BigDecimal total_charge_owed) {
		this.total_charge_owed = total_charge_owed;
	}
	public BigDecimal getTotal_prepaid_charges() {
		return total_prepaid_charges;
	}
	public void setTotal_prepaid_charges(BigDecimal total_prepaid_charges) {
		this.total_prepaid_charges = total_prepaid_charges;
	}
	public BigDecimal getUnpaid_amount() {
		return unpaid_amount;
	}
	public void setUnpaid_amount(BigDecimal unpaid_amount) {
		this.unpaid_amount = unpaid_amount;
	}
	public BigDecimal getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(BigDecimal payment_amount) {
		this.payment_amount = payment_amount;
	}
	public BigDecimal getCollected_qty() {
		return collected_qty;
	}
	public void setCollected_qty(BigDecimal collected_qty) {
		this.collected_qty = collected_qty;
	}
	public BigDecimal getLast_month_collection_qty() {
		return last_month_collection_qty;
	}
	public void setLast_month_collection_qty(BigDecimal last_month_collection_qty) {
		this.last_month_collection_qty = last_month_collection_qty;
	}
	public BigDecimal getMonth_delivery_qty() {
		return month_delivery_qty;
	}
	public void setMonth_delivery_qty(BigDecimal month_delivery_qty) {
		this.month_delivery_qty = month_delivery_qty;
	}
	public BigDecimal getLast_month_delivery_qty() {
		return last_month_delivery_qty;
	}
	public void setLast_month_delivery_qty(BigDecimal last_month_delivery_qty) {
		this.last_month_delivery_qty = last_month_delivery_qty;
	}
	public BigDecimal getDaily_effective_person() {
		return daily_effective_person;
	}
	public void setDaily_effective_person(BigDecimal daily_effective_person) {
		this.daily_effective_person = daily_effective_person;
	}
	public BigDecimal getFiles_qty() {
		return files_qty;
	}
	public void setFiles_qty(BigDecimal files_qty) {
		this.files_qty = files_qty;
	}
	public BigDecimal getLast_month_files_qty() {
		return last_month_files_qty;
	}
	public void setLast_month_files_qty(BigDecimal last_month_files_qty) {
		this.last_month_files_qty = last_month_files_qty;
	}
	public BigDecimal getGoods_qty() {
		return goods_qty;
	}
	public void setGoods_qty(BigDecimal goods_qty) {
		this.goods_qty = goods_qty;
	}
	public BigDecimal getLast_month_goods_qty() {
		return last_month_goods_qty;
	}
	public void setLast_month_goods_qty(BigDecimal last_month_goods_qty) {
		this.last_month_goods_qty = last_month_goods_qty;
	}
	public BigDecimal getIs_new_customer() {
		return is_new_customer;
	}
	public void setIs_new_customer(BigDecimal is_new_customer) {
		this.is_new_customer = is_new_customer;
	}
	public String getCustomer_send_time_type() {
		return customer_send_time_type;
	}
	public void setCustomer_send_time_type(String customer_send_time_type) {
		this.customer_send_time_type = customer_send_time_type;
	}
	public BigDecimal getCustomer_send_qty1() {
		return customer_send_qty1;
	}
	public void setCustomer_send_qty1(BigDecimal customer_send_qty1) {
		this.customer_send_qty1 = customer_send_qty1;
	}
	public BigDecimal getCustomer_send_qty2() {
		return customer_send_qty2;
	}
	public void setCustomer_send_qty2(BigDecimal customer_send_qty2) {
		this.customer_send_qty2 = customer_send_qty2;
	}
	public BigDecimal getCustomer_send_qty3() {
		return customer_send_qty3;
	}
	public void setCustomer_send_qty3(BigDecimal customer_send_qty3) {
		this.customer_send_qty3 = customer_send_qty3;
	}
	public BigDecimal getCur_year_collection_total() {
		return cur_year_collection_total;
	}
	public void setCur_year_collection_total(BigDecimal cur_year_collection_total) {
		this.cur_year_collection_total = cur_year_collection_total;
	}
	public BigDecimal getLast_year_collection_qty() {
		return last_year_collection_qty;
	}
	public void setLast_year_collection_qty(BigDecimal last_year_collection_qty) {
		this.last_year_collection_qty = last_year_collection_qty;
	}
	public BigDecimal getLast_year_total_qty() {
		return last_year_total_qty;
	}
	public void setLast_year_total_qty(BigDecimal last_year_total_qty) {
		this.last_year_total_qty = last_year_total_qty;
	}
	public BigDecimal getCur_year_postage_total() {
		return cur_year_postage_total;
	}
	public void setCur_year_postage_total(BigDecimal cur_year_postage_total) {
		this.cur_year_postage_total = cur_year_postage_total;
	}
	public BigDecimal getLast_year_postage_total() {
		return last_year_postage_total;
	}
	public void setLast_year_postage_total(BigDecimal last_year_postage_total) {
		this.last_year_postage_total = last_year_postage_total;
	}
	public BigDecimal getLast_year_all_postage_total() {
		return last_year_all_postage_total;
	}
	public void setLast_year_all_postage_total(BigDecimal last_year_all_postage_total) {
		this.last_year_all_postage_total = last_year_all_postage_total;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getExtend_column() {
		return extend_column;
	}
	public void setExtend_column(String extend_column) {
		this.extend_column = extend_column;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getModify_user() {
		return modify_user;
	}
	public void setModify_user(String modify_user) {
		this.modify_user = modify_user;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	
	public String getGroupName() {
		return getPost_org_no() + "-" + getSender_country_no() + "-" + getSender_province_no() + "-"
				+ getSender_city_no() + "-" + getSender_county_no() + "-" + getSender_district_no() + "-"
				+ getPeriod_id() + "-" + getSender_id() + "-" + getSender_warehouse_id() + "-"
				+ getPeriod_id() + "-" + getCustomer_send_time_type();
	}
	
	

}
