package com.isoftstone.model;

import java.math.BigDecimal;
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
 * The persistent class for the dm_regional_month_collection_t database table.
 * 
 */
@Entity
@Table(name="dm_regional_month_collection_t")
public class DmRegionalMonthCollectionT extends ActiveRecord<DmRegionalMonthCollectionT> {

	@Column(name="collected_qty")
	private int collectedQty;

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

	@Column(name="cur_year_postage_total")
	private BigDecimal curYearPostageTotal;

	@Column(name="daily_effective_person")
	private int dailyEffectivePerson;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="fee_weight")
	private BigDecimal feeWeight;

	@Column(name="files_qty")
	private int filesQty;

	@Column(name="goods_qty")
	private int goodsQty;

	private BigInteger id;

	@Column(name="last_month_collection_qty")
	private int lastMonthCollectionQty;

	@Column(name="last_month_delivery_qty")
	private int lastMonthDeliveryQty;

	@Column(name="last_month_files_qty")
	private int lastMonthFilesQty;

	@Column(name="last_month_goods_qty")
	private int lastMonthGoodsQty;

	@Column(name="last_month_postage_total")
	private BigDecimal lastMonthPostageTotal;

	@Column(name="last_year_all_postage_total")
	private BigDecimal lastYearAllPostageTotal;

	@Column(name="last_year_collection_qty")
	private int lastYearCollectionQty;

	@Column(name="last_year_postage_total")
	private BigDecimal lastYearPostageTotal;

	@Column(name="last_year_total_qty")
	private int lastYearTotalQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	@Column(name="month_delivery_qty")
	private int monthDeliveryQty;

	@Column(name="order_weight")
	private BigDecimal orderWeight;

	@Column(name="payment_amount")
	private BigDecimal paymentAmount;

	@Column(name="period_id")
	private int periodId;

	@Column(name="post_org_name")
	private String postOrgName;

	@Column(name="post_org_no")
	private String postOrgNo;

	@Column(name="postage_other")
	private BigDecimal postageOther;

	@Column(name="postage_paid")
	private BigDecimal postagePaid;

	@Column(name="postage_standard")
	private BigDecimal postageStandard;

	@Column(name="postage_total")
	private BigDecimal postageTotal;

	@Column(name="real_weight")
	private BigDecimal realWeight;

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

	@Column(name="sender_city_name")
	private String senderCityName;

	@Column(name="sender_city_no")
	private String senderCityNo;

	@Column(name="sender_country_name")
	private String senderCountryName;

	@Column(name="sender_country_no")
	private String senderCountryNo;

	@Column(name="sender_county_name")
	private String senderCountyName;

	@Column(name="sender_county_no")
	private String senderCountyNo;

	@Column(name="sender_district_no")
	private String senderDistrictNo;

	@Column(name="sender_province_name")
	private String senderProvinceName;

	@Column(name="sender_province_no")
	private String senderProvinceNo;

	@Column(name="total_charge_owed")
	private BigDecimal totalChargeOwed;

	@Column(name="total_current_charges")
	private BigDecimal totalCurrentCharges;

	@Column(name="total_prepaid_charges")
	private BigDecimal totalPrepaidCharges;

	@Column(name="unpaid_amount")
	private BigDecimal unpaidAmount;

	public DmRegionalMonthCollectionT() {
	}

	public int getCollectedQty() {
		return this.collectedQty;
	}

	public void setCollectedQty(int collectedQty) {
		this.collectedQty = collectedQty;
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

	public BigDecimal getCurYearPostageTotal() {
		return this.curYearPostageTotal;
	}

	public void setCurYearPostageTotal(BigDecimal curYearPostageTotal) {
		this.curYearPostageTotal = curYearPostageTotal;
	}

	public int getDailyEffectivePerson() {
		return this.dailyEffectivePerson;
	}

	public void setDailyEffectivePerson(int dailyEffectivePerson) {
		this.dailyEffectivePerson = dailyEffectivePerson;
	}

	public Object getExtendColumn() {
		return this.extendColumn;
	}

	public void setExtendColumn(Object extendColumn) {
		this.extendColumn = extendColumn;
	}

	public BigDecimal getFeeWeight() {
		return this.feeWeight;
	}

	public void setFeeWeight(BigDecimal feeWeight) {
		this.feeWeight = feeWeight;
	}

	public int getFilesQty() {
		return this.filesQty;
	}

	public void setFilesQty(int filesQty) {
		this.filesQty = filesQty;
	}

	public int getGoodsQty() {
		return this.goodsQty;
	}

	public void setGoodsQty(int goodsQty) {
		this.goodsQty = goodsQty;
	}

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public int getLastMonthCollectionQty() {
		return this.lastMonthCollectionQty;
	}

	public void setLastMonthCollectionQty(int lastMonthCollectionQty) {
		this.lastMonthCollectionQty = lastMonthCollectionQty;
	}

	public int getLastMonthDeliveryQty() {
		return this.lastMonthDeliveryQty;
	}

	public void setLastMonthDeliveryQty(int lastMonthDeliveryQty) {
		this.lastMonthDeliveryQty = lastMonthDeliveryQty;
	}

	public int getLastMonthFilesQty() {
		return this.lastMonthFilesQty;
	}

	public void setLastMonthFilesQty(int lastMonthFilesQty) {
		this.lastMonthFilesQty = lastMonthFilesQty;
	}

	public int getLastMonthGoodsQty() {
		return this.lastMonthGoodsQty;
	}

	public void setLastMonthGoodsQty(int lastMonthGoodsQty) {
		this.lastMonthGoodsQty = lastMonthGoodsQty;
	}

	public BigDecimal getLastMonthPostageTotal() {
		return this.lastMonthPostageTotal;
	}

	public void setLastMonthPostageTotal(BigDecimal lastMonthPostageTotal) {
		this.lastMonthPostageTotal = lastMonthPostageTotal;
	}

	public BigDecimal getLastYearAllPostageTotal() {
		return this.lastYearAllPostageTotal;
	}

	public void setLastYearAllPostageTotal(BigDecimal lastYearAllPostageTotal) {
		this.lastYearAllPostageTotal = lastYearAllPostageTotal;
	}

	public int getLastYearCollectionQty() {
		return this.lastYearCollectionQty;
	}

	public void setLastYearCollectionQty(int lastYearCollectionQty) {
		this.lastYearCollectionQty = lastYearCollectionQty;
	}

	public BigDecimal getLastYearPostageTotal() {
		return this.lastYearPostageTotal;
	}

	public void setLastYearPostageTotal(BigDecimal lastYearPostageTotal) {
		this.lastYearPostageTotal = lastYearPostageTotal;
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

	public int getMonthDeliveryQty() {
		return this.monthDeliveryQty;
	}

	public void setMonthDeliveryQty(int monthDeliveryQty) {
		this.monthDeliveryQty = monthDeliveryQty;
	}

	public BigDecimal getOrderWeight() {
		return this.orderWeight;
	}

	public void setOrderWeight(BigDecimal orderWeight) {
		this.orderWeight = orderWeight;
	}

	public BigDecimal getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public int getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}

	public String getPostOrgName() {
		return this.postOrgName;
	}

	public void setPostOrgName(String postOrgName) {
		this.postOrgName = postOrgName;
	}

	public String getPostOrgNo() {
		return this.postOrgNo;
	}

	public void setPostOrgNo(String postOrgNo) {
		this.postOrgNo = postOrgNo;
	}

	public BigDecimal getPostageOther() {
		return this.postageOther;
	}

	public void setPostageOther(BigDecimal postageOther) {
		this.postageOther = postageOther;
	}

	public BigDecimal getPostagePaid() {
		return this.postagePaid;
	}

	public void setPostagePaid(BigDecimal postagePaid) {
		this.postagePaid = postagePaid;
	}

	public BigDecimal getPostageStandard() {
		return this.postageStandard;
	}

	public void setPostageStandard(BigDecimal postageStandard) {
		this.postageStandard = postageStandard;
	}

	public BigDecimal getPostageTotal() {
		return this.postageTotal;
	}

	public void setPostageTotal(BigDecimal postageTotal) {
		this.postageTotal = postageTotal;
	}

	public BigDecimal getRealWeight() {
		return this.realWeight;
	}

	public void setRealWeight(BigDecimal realWeight) {
		this.realWeight = realWeight;
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

	public String getSenderCityName() {
		return this.senderCityName;
	}

	public void setSenderCityName(String senderCityName) {
		this.senderCityName = senderCityName;
	}

	public String getSenderCityNo() {
		return this.senderCityNo;
	}

	public void setSenderCityNo(String senderCityNo) {
		this.senderCityNo = senderCityNo;
	}

	public String getSenderCountryName() {
		return this.senderCountryName;
	}

	public void setSenderCountryName(String senderCountryName) {
		this.senderCountryName = senderCountryName;
	}

	public String getSenderCountryNo() {
		return this.senderCountryNo;
	}

	public void setSenderCountryNo(String senderCountryNo) {
		this.senderCountryNo = senderCountryNo;
	}

	public String getSenderCountyName() {
		return this.senderCountyName;
	}

	public void setSenderCountyName(String senderCountyName) {
		this.senderCountyName = senderCountyName;
	}

	public String getSenderCountyNo() {
		return this.senderCountyNo;
	}

	public void setSenderCountyNo(String senderCountyNo) {
		this.senderCountyNo = senderCountyNo;
	}

	public String getSenderDistrictNo() {
		return this.senderDistrictNo;
	}

	public void setSenderDistrictNo(String senderDistrictNo) {
		this.senderDistrictNo = senderDistrictNo;
	}

	public String getSenderProvinceName() {
		return this.senderProvinceName;
	}

	public void setSenderProvinceName(String senderProvinceName) {
		this.senderProvinceName = senderProvinceName;
	}

	public String getSenderProvinceNo() {
		return this.senderProvinceNo;
	}

	public void setSenderProvinceNo(String senderProvinceNo) {
		this.senderProvinceNo = senderProvinceNo;
	}

	public BigDecimal getTotalChargeOwed() {
		return this.totalChargeOwed;
	}

	public void setTotalChargeOwed(BigDecimal totalChargeOwed) {
		this.totalChargeOwed = totalChargeOwed;
	}

	public BigDecimal getTotalCurrentCharges() {
		return this.totalCurrentCharges;
	}

	public void setTotalCurrentCharges(BigDecimal totalCurrentCharges) {
		this.totalCurrentCharges = totalCurrentCharges;
	}

	public BigDecimal getTotalPrepaidCharges() {
		return this.totalPrepaidCharges;
	}

	public void setTotalPrepaidCharges(BigDecimal totalPrepaidCharges) {
		this.totalPrepaidCharges = totalPrepaidCharges;
	}

	public BigDecimal getUnpaidAmount() {
		return this.unpaidAmount;
	}

	public void setUnpaidAmount(BigDecimal unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

}