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
 * The persistent class for the dm_emp_month_collection_t database table.
 * 
 */
@Entity
@Table(name="dm_emp_month_collection_t")
public class DmEmpMonthCollectionT extends ActiveRecord<DmEmpMonthCollectionT> {

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

	@Column(name="emp_salary")
	private BigDecimal empSalary;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="fee_weight")
	private BigDecimal feeWeight;

	private BigInteger id;

	@Column(name="international_sales__salary")
	private BigDecimal internationalSalesSalary;

	@Column(name="international_sales_volume")
	private int internationalSalesVolume;

	@Column(name="last_month_collection_qty")
	private int lastMonthCollectionQty;

	@Column(name="last_month_delivery_qty")
	private int lastMonthDeliveryQty;

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

	@Column(name="org_drds_code")
	private String orgDrdsCode;

	@Column(name="package_collection")
	private int packageCollection;

	@Column(name="package_collection_salary")
	private BigDecimal packageCollectionSalary;

	@Column(name="payment_amount")
	private BigDecimal paymentAmount;

	@Column(name="period_id")
	private int periodId;

	@Column(name="post_org_id")
	private BigInteger postOrgId;

	@Column(name="post_org_name")
	private String postOrgName;

	@Column(name="post_org_no")
	private String postOrgNo;

	@Column(name="post_person_id")
	private BigInteger postPersonId;

	@Column(name="post_person_mobile")
	private String postPersonMobile;

	@Column(name="post_person_name")
	private String postPersonName;

	@Column(name="post_person_no")
	private String postPersonNo;

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

	@Column(name="section_code")
	private String sectionCode;

	@Column(name="section_name")
	private String sectionName;

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

	@Column(name="standard_express_collection")
	private int standardExpressCollection;

	@Column(name="standard_express_salary")
	private BigDecimal standardExpressSalary;

	@Column(name="total_charge_owed")
	private BigDecimal totalChargeOwed;

	@Column(name="total_current_charges")
	private BigDecimal totalCurrentCharges;

	@Column(name="total_prepaid_charges")
	private BigDecimal totalPrepaidCharges;

	@Column(name="unpaid_amount")
	private BigDecimal unpaidAmount;

	public DmEmpMonthCollectionT() {
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

	public BigDecimal getEmpSalary() {
		return this.empSalary;
	}

	public void setEmpSalary(BigDecimal empSalary) {
		this.empSalary = empSalary;
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

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigDecimal getInternationalSalesSalary() {
		return this.internationalSalesSalary;
	}

	public void setInternationalSalesSalary(BigDecimal internationalSalesSalary) {
		this.internationalSalesSalary = internationalSalesSalary;
	}

	public int getInternationalSalesVolume() {
		return this.internationalSalesVolume;
	}

	public void setInternationalSalesVolume(int internationalSalesVolume) {
		this.internationalSalesVolume = internationalSalesVolume;
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

	public String getOrgDrdsCode() {
		return this.orgDrdsCode;
	}

	public void setOrgDrdsCode(String orgDrdsCode) {
		this.orgDrdsCode = orgDrdsCode;
	}

	public int getPackageCollection() {
		return this.packageCollection;
	}

	public void setPackageCollection(int packageCollection) {
		this.packageCollection = packageCollection;
	}

	public BigDecimal getPackageCollectionSalary() {
		return this.packageCollectionSalary;
	}

	public void setPackageCollectionSalary(BigDecimal packageCollectionSalary) {
		this.packageCollectionSalary = packageCollectionSalary;
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

	public BigInteger getPostOrgId() {
		return this.postOrgId;
	}

	public void setPostOrgId(BigInteger postOrgId) {
		this.postOrgId = postOrgId;
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

	public String getSectionCode() {
		return this.sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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

	public int getStandardExpressCollection() {
		return this.standardExpressCollection;
	}

	public void setStandardExpressCollection(int standardExpressCollection) {
		this.standardExpressCollection = standardExpressCollection;
	}

	public BigDecimal getStandardExpressSalary() {
		return this.standardExpressSalary;
	}

	public void setStandardExpressSalary(BigDecimal standardExpressSalary) {
		this.standardExpressSalary = standardExpressSalary;
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