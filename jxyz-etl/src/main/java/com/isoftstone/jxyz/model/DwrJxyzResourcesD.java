package com.isoftstone.jxyz.model;

import java.math.BigDecimal;
import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jdialects.annotation.jpa.Temporal;
import com.github.drinkjava2.jdialects.annotation.jpa.TemporalType;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the dwr_jxyz_resources_d database table.
 * 
 */
@Entity
@Table(name="dwr_jxyz_resources_d")
public class DwrJxyzResourcesD extends ActiveRecord<DwrJxyzResourcesD> {

	@Id
	private int id;

	@Column(name="bundary_coordinate")
	private String bundaryCoordinate;

	@Column(name="city_code")
	private String cityCode;

	@Column(name="city_name")
	private String cityName;

	@Column(name="county_code")
	private String countyCode;

	@Column(name="county_name")
	private String countyName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_user")
	private String createUser;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="dept_code")
	private String deptCode;

	@Column(name="dept_name")
	private String deptName;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="grid_code")
	private String gridCode;

	@Column(name="key_market_code")
	private String keyMarketCode;

	@Column(name="key_market_name")
	private String keyMarketName;

	@Column(name="location_latitude")
	private BigDecimal locationLatitude;

	@Column(name="location_longitude")
	private BigDecimal locationLongitude;

	@Column(name="market_address")
	private String marketAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	@Column(name="province_code")
	private String provinceCode;

	@Column(name="province_name")
	private String provinceName;

	@Column(name="resources_type")
	private String resourcesType;

	@Column(name="section_code")
	private String sectionCode;

	@Column(name="section_name")
	private String sectionName;

	@Column(name="users_qty")
	private int usersQty;

	public DwrJxyzResourcesD() {
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

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyCode() {
		return this.countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
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

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getMarketAddress() {
		return this.marketAddress;
	}

	public void setMarketAddress(String marketAddress) {
		this.marketAddress = marketAddress;
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

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getResourcesType() {
		return this.resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
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

	public int getUsersQty() {
		return this.usersQty;
	}

	public void setUsersQty(int usersQty) {
		this.usersQty = usersQty;
	}

}