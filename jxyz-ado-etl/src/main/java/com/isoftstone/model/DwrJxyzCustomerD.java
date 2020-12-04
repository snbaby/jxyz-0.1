package com.isoftstone.model;

import java.math.BigInteger;
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
 * The persistent class for the dwr_jxyz_customer_d database table.
 * 
 */
@Entity
@Table(name="dwr_jxyz_customer_d")
public class DwrJxyzCustomerD extends ActiveRecord<DwrJxyzCustomerD> {

	@Id
	private int id;

	@Column(name="bundary_coordinate")
	private String bundaryCoordinate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="contract_expiration_time")
	private Date contractExpirationTime;

	@Column(name="contract_name")
	private String contractName;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="customer_email")
	private String customerEmail;

	@Column(name="customer_maintainer_name")
	private String customerMaintainerName;

	@Column(name="customer_maintainer_no")
	private String customerMaintainerNo;

	@Column(name="emp_position")
	private String empPosition;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="key_market_code")
	private String keyMarketCode;

	@Column(name="key_market_name")
	private String keyMarketName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_sender_date")
	private Date lastSenderDate;

	@Column(name="location_latitude")
	private String locationLatitude;

	@Column(name="location_longitude")
	private String locationLongitude;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	@Column(name="post_org_name")
	private String postOrgName;

	@Column(name="post_org_no")
	private String postOrgNo;

	private String sender;

	@Column(name="sender_addr")
	private String senderAddr;

	@Column(name="sender_fixtel")
	private String senderFixtel;

	@Column(name="sender_id")
	private BigInteger senderId;

	@Column(name="sender_im_id")
	private String senderImId;

	@Column(name="sender_im_type")
	private String senderImType;

	@Column(name="sender_linker")
	private String senderLinker;

	@Column(name="sender_mobile")
	private String senderMobile;

	@Column(name="sender_no")
	private String senderNo;

	@Column(name="sender_warehouse_id")
	private BigInteger senderWarehouseId;

	@Column(name="sender_warehouse_name")
	private String senderWarehouseName;

	public DwrJxyzCustomerD() {
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

	public String getCustomerMaintainerName() {
		return this.customerMaintainerName;
	}

	public void setCustomerMaintainerName(String customerMaintainerName) {
		this.customerMaintainerName = customerMaintainerName;
	}

	public String getCustomerMaintainerNo() {
		return this.customerMaintainerNo;
	}

	public void setCustomerMaintainerNo(String customerMaintainerNo) {
		this.customerMaintainerNo = customerMaintainerNo;
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

	public String getKeyMarketCode() {
		return this.keyMarketCode;
	}

	public void setKeyMarketCode(String keyMarketCode) {
		this.keyMarketCode = keyMarketCode;
	}

	public String getKeyMarketName() {
		return this.keyMarketName;
	}

	public void setKeyMarketName(String keyMarketName) {
		this.keyMarketName = keyMarketName;
	}

	public Date getLastSenderDate() {
		return this.lastSenderDate;
	}

	public void setLastSenderDate(Date lastSenderDate) {
		this.lastSenderDate = lastSenderDate;
	}

	public String getLocationLatitude() {
		return this.locationLatitude;
	}

	public void setLocationLatitude(String locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public String getLocationLongitude() {
		return this.locationLongitude;
	}

	public void setLocationLongitude(String locationLongitude) {
		this.locationLongitude = locationLongitude;
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

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderAddr() {
		return this.senderAddr;
	}

	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}

	public String getSenderFixtel() {
		return this.senderFixtel;
	}

	public void setSenderFixtel(String senderFixtel) {
		this.senderFixtel = senderFixtel;
	}

	public BigInteger getSenderId() {
		return this.senderId;
	}

	public void setSenderId(BigInteger senderId) {
		this.senderId = senderId;
	}

	public String getSenderImId() {
		return this.senderImId;
	}

	public void setSenderImId(String senderImId) {
		this.senderImId = senderImId;
	}

	public String getSenderImType() {
		return this.senderImType;
	}

	public void setSenderImType(String senderImType) {
		this.senderImType = senderImType;
	}

	public String getSenderLinker() {
		return this.senderLinker;
	}

	public void setSenderLinker(String senderLinker) {
		this.senderLinker = senderLinker;
	}

	public String getSenderMobile() {
		return this.senderMobile;
	}

	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}

	public String getSenderNo() {
		return this.senderNo;
	}

	public void setSenderNo(String senderNo) {
		this.senderNo = senderNo;
	}

	public BigInteger getSenderWarehouseId() {
		return this.senderWarehouseId;
	}

	public void setSenderWarehouseId(BigInteger senderWarehouseId) {
		this.senderWarehouseId = senderWarehouseId;
	}

	public String getSenderWarehouseName() {
		return this.senderWarehouseName;
	}

	public void setSenderWarehouseName(String senderWarehouseName) {
		this.senderWarehouseName = senderWarehouseName;
	}

}