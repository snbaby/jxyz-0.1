package com.isoftstone.jxyz.model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jdialects.annotation.jpa.Temporal;
import com.github.drinkjava2.jdialects.annotation.jpa.TemporalType;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the dm_delivery_month_t database table.
 * 
 */
@Entity
@Table(name="dm_delivery_month_t")
public class DmDeliveryMonthT extends ActiveRecord<DmDeliveryMonthT> {

	@Column(name="court_mail_qty")
	private int courtMailQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_user")
	private String createUser;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="cur_year_collection_total")
	private int curYearCollectionTotal;

	@Column(name="current_year_total_qty")
	private int currentYearTotalQty;

	@Column(name="deliver_org_name")
	private String deliverOrgName;

	@Column(name="deliver_org_no")
	private String deliverOrgNo;

	@Column(name="deliver_qty")
	private int deliverQty;

	@Column(name="deliver_section")
	private String deliverSection;

	@Column(name="deliver_section_code")
	private String deliverSectionCode;

	@Column(name="deliver_section_name")
	private String deliverSectionName;

	@Column(name="extend_column")
	private Object extendColumn;

	private BigInteger id;

	@Column(name="last_month_deliver_qty")
	private int lastMonthDeliverQty;

	@Column(name="last_year_total_qty")
	private int lastYearTotalQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	@Column(name="period_id")
	private int periodId;

	@Column(name="post_person_id")
	private BigInteger postPersonId;

	@Column(name="post_person_mobile")
	private String postPersonMobile;

	@Column(name="post_person_name")
	private String postPersonName;

	@Column(name="post_person_no")
	private String postPersonNo;

	@Column(name="receiver_city_name")
	private String receiverCityName;

	@Column(name="receiver_city_no")
	private String receiverCityNo;

	@Column(name="receiver_country_name")
	private String receiverCountryName;

	@Column(name="receiver_country_no")
	private String receiverCountryNo;

	@Column(name="receiver_county_name")
	private String receiverCountyName;

	@Column(name="receiver_county_no")
	private String receiverCountyNo;

	@Column(name="receiver_district_no")
	private String receiverDistrictNo;

	@Column(name="receiver_province_name")
	private String receiverProvinceName;

	@Column(name="receiver_province_no")
	private String receiverProvinceNo;

	public DmDeliveryMonthT() {
	}

	public int getCourtMailQty() {
		return this.courtMailQty;
	}

	public void setCourtMailQty(int courtMailQty) {
		this.courtMailQty = courtMailQty;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public int getCurYearCollectionTotal() {
		return this.curYearCollectionTotal;
	}

	public void setCurYearCollectionTotal(int curYearCollectionTotal) {
		this.curYearCollectionTotal = curYearCollectionTotal;
	}

	public int getCurrentYearTotalQty() {
		return this.currentYearTotalQty;
	}

	public void setCurrentYearTotalQty(int currentYearTotalQty) {
		this.currentYearTotalQty = currentYearTotalQty;
	}

	public String getDeliverOrgName() {
		return this.deliverOrgName;
	}

	public void setDeliverOrgName(String deliverOrgName) {
		this.deliverOrgName = deliverOrgName;
	}

	public String getDeliverOrgNo() {
		return this.deliverOrgNo;
	}

	public void setDeliverOrgNo(String deliverOrgNo) {
		this.deliverOrgNo = deliverOrgNo;
	}

	public int getDeliverQty() {
		return this.deliverQty;
	}

	public void setDeliverQty(int deliverQty) {
		this.deliverQty = deliverQty;
	}

	public String getDeliverSection() {
		return this.deliverSection;
	}

	public void setDeliverSection(String deliverSection) {
		this.deliverSection = deliverSection;
	}

	public String getDeliverSectionCode() {
		return this.deliverSectionCode;
	}

	public void setDeliverSectionCode(String deliverSectionCode) {
		this.deliverSectionCode = deliverSectionCode;
	}

	public String getDeliverSectionName() {
		return this.deliverSectionName;
	}

	public void setDeliverSectionName(String deliverSectionName) {
		this.deliverSectionName = deliverSectionName;
	}

	public Object getExtendColumn() {
		return this.extendColumn;
	}

	public void setExtendColumn(Object extendColumn) {
		this.extendColumn = extendColumn;
	}

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public int getLastMonthDeliverQty() {
		return this.lastMonthDeliverQty;
	}

	public void setLastMonthDeliverQty(int lastMonthDeliverQty) {
		this.lastMonthDeliverQty = lastMonthDeliverQty;
	}

	public int getLastYearTotalQty() {
		return this.lastYearTotalQty;
	}

	public void setLastYearTotalQty(int lastYearTotalQty) {
		this.lastYearTotalQty = lastYearTotalQty;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public int getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}

	public BigInteger getPostPersonId() {
		return this.postPersonId;
	}

	public void setPostPersonId(BigInteger postPersonId) {
		this.postPersonId = postPersonId;
	}

	public String getPostPersonMobile() {
		return this.postPersonMobile;
	}

	public void setPostPersonMobile(String postPersonMobile) {
		this.postPersonMobile = postPersonMobile;
	}

	public String getPostPersonName() {
		return this.postPersonName;
	}

	public void setPostPersonName(String postPersonName) {
		this.postPersonName = postPersonName;
	}

	public String getPostPersonNo() {
		return this.postPersonNo;
	}

	public void setPostPersonNo(String postPersonNo) {
		this.postPersonNo = postPersonNo;
	}

	public String getReceiverCityName() {
		return this.receiverCityName;
	}

	public void setReceiverCityName(String receiverCityName) {
		this.receiverCityName = receiverCityName;
	}

	public String getReceiverCityNo() {
		return this.receiverCityNo;
	}

	public void setReceiverCityNo(String receiverCityNo) {
		this.receiverCityNo = receiverCityNo;
	}

	public String getReceiverCountryName() {
		return this.receiverCountryName;
	}

	public void setReceiverCountryName(String receiverCountryName) {
		this.receiverCountryName = receiverCountryName;
	}

	public String getReceiverCountryNo() {
		return this.receiverCountryNo;
	}

	public void setReceiverCountryNo(String receiverCountryNo) {
		this.receiverCountryNo = receiverCountryNo;
	}

	public String getReceiverCountyName() {
		return this.receiverCountyName;
	}

	public void setReceiverCountyName(String receiverCountyName) {
		this.receiverCountyName = receiverCountyName;
	}

	public String getReceiverCountyNo() {
		return this.receiverCountyNo;
	}

	public void setReceiverCountyNo(String receiverCountyNo) {
		this.receiverCountyNo = receiverCountyNo;
	}

	public String getReceiverDistrictNo() {
		return this.receiverDistrictNo;
	}

	public void setReceiverDistrictNo(String receiverDistrictNo) {
		this.receiverDistrictNo = receiverDistrictNo;
	}

	public String getReceiverProvinceName() {
		return this.receiverProvinceName;
	}

	public void setReceiverProvinceName(String receiverProvinceName) {
		this.receiverProvinceName = receiverProvinceName;
	}

	public String getReceiverProvinceNo() {
		return this.receiverProvinceNo;
	}

	public void setReceiverProvinceNo(String receiverProvinceNo) {
		this.receiverProvinceNo = receiverProvinceNo;
	}

}