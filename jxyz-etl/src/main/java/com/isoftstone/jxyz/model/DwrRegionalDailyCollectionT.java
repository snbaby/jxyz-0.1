package com.isoftstone.jxyz.model;

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
 * The persistent class for the dwr_regional_daily_collection_t database table.
 * 
 */
@Entity
@Table(name = "dwr_regional_daily_collection_t")
public class DwrRegionalDailyCollectionT extends ActiveRecord<DwrRegionalDailyCollectionT> {

	@Column(name = "collected_qty")
	private int collectedQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "daily_effective_person")
	private int dailyEffectivePerson;

	@Column(name = "delivery_qty")
	private int deliveryQty;

	@Column(name = "extend_column")
	private Object extendColumn;

	@Column(name = "fee_weight")
	private BigDecimal feeWeight;

	@Column(name = "files_qty")
	private int filesQty;

	@Column(name = "files_revenue")
	private BigDecimal filesRevenue;

	@Column(name = "goods_qty")
	private int goodsQty;

	@Column(name = "goods_revenue")
	private BigDecimal goodsRevenue;

	private BigInteger id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	private Date modifyDate;

	@Column(name = "modify_user")
	private String modifyUser;

	@Column(name = "order_weight")
	private BigDecimal orderWeight;

	@Column(name = "payment_amount")
	private BigDecimal paymentAmount;

	@Temporal(TemporalType.DATE)
	@Column(name = "period_id")
	private Date periodId;

	@Column(name = "post_org_name")
	private String postOrgName;

	@Column(name = "post_org_no")
	private String postOrgNo;

	@Column(name = "postage_other")
	private BigDecimal postageOther;

	@Column(name = "postage_paid")
	private BigDecimal postagePaid;

	@Column(name = "postage_standard")
	private BigDecimal postageStandard;

	@Column(name = "postage_total")
	private BigDecimal postageTotal;

	@Column(name = "real_weight")
	private BigDecimal realWeight;

	@Column(name = "receiver_city_name")
	private String receiverCityName;

	@Column(name = "receiver_city_no")
	private String receiverCityNo;

	@Column(name = "receiver_country_name")
	private String receiverCountryName;

	@Column(name = "receiver_country_no")
	private String receiverCountryNo;

	@Column(name = "receiver_county_name")
	private String receiverCountyName;

	@Column(name = "receiver_county_no")
	private String receiverCountyNo;

	@Column(name = "receiver_district_no")
	private String receiverDistrictNo;

	@Column(name = "receiver_province_name")
	private String receiverProvinceName;

	@Column(name = "receiver_province_no")
	private String receiverProvinceNo;

	@Column(name = "sender_city_name")
	private String senderCityName;

	@Column(name = "sender_city_no")
	private String senderCityNo;

	@Column(name = "sender_country_name")
	private String senderCountryName;

	@Column(name = "sender_country_no")
	private String senderCountryNo;

	@Column(name = "sender_county_name")
	private String senderCountyName;

	@Column(name = "sender_county_no")
	private String senderCountyNo;

	@Column(name = "sender_district_no")
	private String senderDistrictNo;

	@Column(name = "sender_province_name")
	private String senderProvinceName;

	@Column(name = "sender_province_no")
	private String senderProvinceNo;

	@Column(name = "total_charge_owed")
	private BigDecimal totalChargeOwed;

	@Column(name = "total_current_charges")
	private BigDecimal totalCurrentCharges;

	@Column(name = "total_prepaid_charges")
	private BigDecimal totalPrepaidCharges;

	@Column(name = "unpaid_amount")
	private BigDecimal unpaidAmount;

	@Column(name = "yesterday_collection_qty")
	private int yesterdayCollectionQty;

	@Column(name = "yesterday_delivery_qty")
	private int yesterdayDeliveryQty;

	@Column(name = "yesterday_files_qty")
	private int yesterdayFilesQty;

	@Column(name = "yesterday_goods_qty")
	private int yesterdayGoodsQty;

	@Column(name = "yesterday_postage_total")
	private BigDecimal yesterdayPostageTotal;

	public DwrRegionalDailyCollectionT() {
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

	public int getDailyEffectivePerson() {
		return this.dailyEffectivePerson;
	}

	public void setDailyEffectivePerson(int dailyEffectivePerson) {
		this.dailyEffectivePerson = dailyEffectivePerson;
	}

	public int getDeliveryQty() {
		return this.deliveryQty;
	}

	public void setDeliveryQty(int deliveryQty) {
		this.deliveryQty = deliveryQty;
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

	public BigDecimal getFilesRevenue() {
		return this.filesRevenue;
	}

	public void setFilesRevenue(BigDecimal filesRevenue) {
		this.filesRevenue = filesRevenue;
	}

	public int getGoodsQty() {
		return this.goodsQty;
	}

	public void setGoodsQty(int goodsQty) {
		this.goodsQty = goodsQty;
	}

	public BigDecimal getGoodsRevenue() {
		return this.goodsRevenue;
	}

	public void setGoodsRevenue(BigDecimal goodsRevenue) {
		this.goodsRevenue = goodsRevenue;
	}

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public Date getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(Date periodId) {
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

	public int getYesterdayCollectionQty() {
		return this.yesterdayCollectionQty;
	}

	public void setYesterdayCollectionQty(int yesterdayCollectionQty) {
		this.yesterdayCollectionQty = yesterdayCollectionQty;
	}

	public int getYesterdayDeliveryQty() {
		return this.yesterdayDeliveryQty;
	}

	public void setYesterdayDeliveryQty(int yesterdayDeliveryQty) {
		this.yesterdayDeliveryQty = yesterdayDeliveryQty;
	}

	public int getYesterdayFilesQty() {
		return this.yesterdayFilesQty;
	}

	public void setYesterdayFilesQty(int yesterdayFilesQty) {
		this.yesterdayFilesQty = yesterdayFilesQty;
	}

	public int getYesterdayGoodsQty() {
		return this.yesterdayGoodsQty;
	}

	public void setYesterdayGoodsQty(int yesterdayGoodsQty) {
		this.yesterdayGoodsQty = yesterdayGoodsQty;
	}

	public BigDecimal getYesterdayPostageTotal() {
		return this.yesterdayPostageTotal;
	}

	public void setYesterdayPostageTotal(BigDecimal yesterdayPostageTotal) {
		this.yesterdayPostageTotal = yesterdayPostageTotal;
	}

}