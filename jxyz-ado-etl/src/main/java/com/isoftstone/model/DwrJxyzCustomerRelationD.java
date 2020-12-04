package com.isoftstone.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jdialects.annotation.jpa.Temporal;
import com.github.drinkjava2.jdialects.annotation.jpa.TemporalType;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the dwr_jxyz_customer_relation_d database table.
 * 
 */
@Entity
@Table(name="dwr_jxyz_customer_relation_d")
public class DwrJxyzCustomerRelationD extends ActiveRecord<DwrJxyzCustomerRelationD> {

	@Id
	private int id;

	@Column(name="bundary_coordinate")
	private String bundaryCoordinate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="contract_expiration_time")
	private Date contractExpirationTime;

	@Column(name="contract_name")
	private String contractName;

	@Column(name="contract_no")
	private String contractNo;

	@Column(name="contract_status")
	private String contractStatus;

	private BigDecimal coverage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_user")
	private String createUser;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="customer_email")
	private String customerEmail;

	@Column(name="customer_key_contact")
	private String customerKeyContact;

	@Column(name="customer_key_contact_number")
	private String customerKeyContactNumber;

	@Column(name="customer_maintainer_name")
	private String customerMaintainerName;

	@Column(name="dept_code")
	private String deptCode;

	@Column(name="dept_name")
	private String deptName;

	@Column(name="development_rate")
	private BigDecimal developmentRate;

	@Column(name="emp_position")
	private String empPosition;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="grid_code")
	private String gridCode;

	@Column(name="key_market_name")
	private String keyMarketName;

	@Column(name="location_latitude")
	private BigDecimal locationLatitude;

	@Column(name="location_longitude")
	private BigDecimal locationLongitude;

	@Column(name="mail_consumption_per_capita")
	private BigDecimal mailConsumptionPerCapita;

	@Column(name="market_address")
	private String marketAddress;

	@Column(name="market_type")
	private String marketType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	@Column(name="region_code")
	private String regionCode;

	@Column(name="region_name")
	private String regionName;

	@Column(name="resident_enterprise_name")
	private String residentEnterpriseName;

	@Column(name="section_name")
	private String sectionName;

	@Column(name="section_postman")
	private String sectionPostman;

	@Column(name="service_per_capita")
	private BigDecimal servicePerCapita;

	@Column(name="service_radius")
	private BigDecimal serviceRadius;

	@Column(name="users_qty")
	private int usersQty;

	public DwrJxyzCustomerRelationD() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBundaryCoordinate() {
		return this.bundaryCoordinate;
	}

	public void setBundaryCoordinate(String bundaryCoordinate) {
		this.bundaryCoordinate = bundaryCoordinate;
	}

	public Date getContractExpirationTime() {
		return this.contractExpirationTime;
	}

	public void setContractExpirationTime(Date contractExpirationTime) {
		this.contractExpirationTime = contractExpirationTime;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractStatus() {
		return this.contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public BigDecimal getCoverage() {
		return this.coverage;
	}

	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
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

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerKeyContact() {
		return this.customerKeyContact;
	}

	public void setCustomerKeyContact(String customerKeyContact) {
		this.customerKeyContact = customerKeyContact;
	}

	public String getCustomerKeyContactNumber() {
		return this.customerKeyContactNumber;
	}

	public void setCustomerKeyContactNumber(String customerKeyContactNumber) {
		this.customerKeyContactNumber = customerKeyContactNumber;
	}

	public String getCustomerMaintainerName() {
		return this.customerMaintainerName;
	}

	public void setCustomerMaintainerName(String customerMaintainerName) {
		this.customerMaintainerName = customerMaintainerName;
	}

	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public BigDecimal getDevelopmentRate() {
		return this.developmentRate;
	}

	public void setDevelopmentRate(BigDecimal developmentRate) {
		this.developmentRate = developmentRate;
	}

	public String getEmpPosition() {
		return this.empPosition;
	}

	public void setEmpPosition(String empPosition) {
		this.empPosition = empPosition;
	}

	public Object getExtendColumn() {
		return this.extendColumn;
	}

	public void setExtendColumn(Object extendColumn) {
		this.extendColumn = extendColumn;
	}

	public String getGridCode() {
		return this.gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getKeyMarketName() {
		return this.keyMarketName;
	}

	public void setKeyMarketName(String keyMarketName) {
		this.keyMarketName = keyMarketName;
	}

	public BigDecimal getLocationLatitude() {
		return this.locationLatitude;
	}

	public void setLocationLatitude(BigDecimal locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public BigDecimal getLocationLongitude() {
		return this.locationLongitude;
	}

	public void setLocationLongitude(BigDecimal locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public BigDecimal getMailConsumptionPerCapita() {
		return this.mailConsumptionPerCapita;
	}

	public void setMailConsumptionPerCapita(BigDecimal mailConsumptionPerCapita) {
		this.mailConsumptionPerCapita = mailConsumptionPerCapita;
	}

	public String getMarketAddress() {
		return this.marketAddress;
	}

	public void setMarketAddress(String marketAddress) {
		this.marketAddress = marketAddress;
	}

	public String getMarketType() {
		return this.marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
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

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getResidentEnterpriseName() {
		return this.residentEnterpriseName;
	}

	public void setResidentEnterpriseName(String residentEnterpriseName) {
		this.residentEnterpriseName = residentEnterpriseName;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionPostman() {
		return this.sectionPostman;
	}

	public void setSectionPostman(String sectionPostman) {
		this.sectionPostman = sectionPostman;
	}

	public BigDecimal getServicePerCapita() {
		return this.servicePerCapita;
	}

	public void setServicePerCapita(BigDecimal servicePerCapita) {
		this.servicePerCapita = servicePerCapita;
	}

	public BigDecimal getServiceRadius() {
		return this.serviceRadius;
	}

	public void setServiceRadius(BigDecimal serviceRadius) {
		this.serviceRadius = serviceRadius;
	}

	public int getUsersQty() {
		return this.usersQty;
	}

	public void setUsersQty(int usersQty) {
		this.usersQty = usersQty;
	}

}