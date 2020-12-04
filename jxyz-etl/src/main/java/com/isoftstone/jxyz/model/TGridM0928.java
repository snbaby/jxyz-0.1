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
 * The persistent class for the t_grid_m database table.
 * 
 */
@Entity
@Table(name = "t_grid_m_0928")
public class TGridM0928 extends ActiveRecord<TGridM0928> {

	@Id
	private String id;

	@Column(name = "all_parent_code")
	private String allParentCode;

	@Column(name = "area")
	private BigDecimal area;

	@Column(name = "bundary")
	private String bundary;

	@Column(name = "bundary_coordinate")
	private Object bundaryCoordinate;

	@Column(name = "business_type")
	private String businessType;

	@Column(name = "center_latitude")
	private BigDecimal centerLatitude;

	@Column(name = "center_longitude")
	private BigDecimal centerLongitude;

	private String code;

	private String color;

	@Column(name = "cover_rate")
	private BigDecimal coverRate;

	@Column(name = "cover_size")
	private BigDecimal coverSize;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "create_user")
	private String createUser;

	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "extend_column")
	private Object extendColumn;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "grid_status")
	private String gridStatus;

	private String headperson;

	@Column(name = "headperson_code")
	private String headpersonCode;

	@Column(name = "headperson_portal")
	private String headpersonPortal;

	@Column(name = "headperson_tel")
	private String headpersonTel;

	@Column(name = "is_modify")
	private byte isModify;

	@Column(name = "is_town")
	private byte isTown;

	private int level;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	private Date modifyDate;

	@Column(name = "modify_user")
	private String modifyUser;

	private String name;

	@Column(name = "nap_takeup_rate")
	private BigDecimal napTakeupRate;

	@Column(name = "old_grid_code")
	private String oldGridCode;

	@Column(name = "parent_code")
	private String parentCode;

	private int priority;

	@Column(name = "relate_org")
	private String relateOrg;

	@Column(name = "relate_org_code")
	private String relateOrgCode;

	private String remark;

	private int seq;

	@Column(name = "short_name")
	private String shortName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date")
	private Date startDate;

	private String type;

	private String vtype;

	public TGridM0928() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAllParentCode() {
		return this.allParentCode;
	}

	public void setAllParentCode(String allParentCode) {
		this.allParentCode = allParentCode;
	}

	public BigDecimal getArea() {
		return this.area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getBundary() {
		return this.bundary;
	}

	public void setBundary(String bundary) {
		this.bundary = bundary;
	}

//	public Object getBundaryCoordinate() {
//		return this.bundaryCoordinate;
//	}
//
//	public void setBundaryCoordinate(Object bundaryCoordinate) {
//		this.bundaryCoordinate = bundaryCoordinate;
//	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public BigDecimal getCenterLatitude() {
		return this.centerLatitude;
	}

	public void setCenterLatitude(BigDecimal centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

	public BigDecimal getCenterLongitude() {
		return this.centerLongitude;
	}

	public void setCenterLongitude(BigDecimal centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getCoverRate() {
		return this.coverRate;
	}

	public void setCoverRate(BigDecimal coverRate) {
		this.coverRate = coverRate;
	}

	public BigDecimal getCoverSize() {
		return this.coverSize;
	}

	public void setCoverSize(BigDecimal coverSize) {
		this.coverSize = coverSize;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Object getExtendColumn() {
		return this.extendColumn;
	}

	public void setExtendColumn(Object extendColumn) {
		this.extendColumn = extendColumn;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGridStatus() {
		return this.gridStatus;
	}

	public void setGridStatus(String gridStatus) {
		this.gridStatus = gridStatus;
	}

	public String getHeadperson() {
		return this.headperson;
	}

	public void setHeadperson(String headperson) {
		this.headperson = headperson;
	}

	public String getHeadpersonCode() {
		return this.headpersonCode;
	}

	public void setHeadpersonCode(String headpersonCode) {
		this.headpersonCode = headpersonCode;
	}

	public String getHeadpersonPortal() {
		return this.headpersonPortal;
	}

	public void setHeadpersonPortal(String headpersonPortal) {
		this.headpersonPortal = headpersonPortal;
	}

	public String getHeadpersonTel() {
		return this.headpersonTel;
	}

	public void setHeadpersonTel(String headpersonTel) {
		this.headpersonTel = headpersonTel;
	}

	public byte getIsModify() {
		return this.isModify;
	}

	public void setIsModify(Object isModify) {
		if (isModify == null) {
			this.isModify = (byte) 0;
		} else if (isModify.getClass().getSimpleName().equals("Boolean")) {
			this.isModify = (boolean) isModify ? (byte) 1 : (byte) 0;
		} else {
			this.isModify = (byte) isModify;
		}
	}

	public byte getIsTown() {
		return this.isTown;
	}

	public void setIsTown(Object isTown) {
		if (isTown == null) {
			this.isTown = (byte) 0;
		} else if (isTown.getClass().getSimpleName().equals("Boolean")) {
			this.isTown = (boolean) isTown ? (byte) 1 : (byte) 0;
		} else {
			this.isTown = (byte) isTown;
		}
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getNapTakeupRate() {
		return this.napTakeupRate;
	}

	public void setNapTakeupRate(BigDecimal napTakeupRate) {
		this.napTakeupRate = napTakeupRate;
	}

	public String getOldGridCode() {
		return this.oldGridCode;
	}

	public void setOldGridCode(String oldGridCode) {
		this.oldGridCode = oldGridCode;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getRelateOrg() {
		return this.relateOrg;
	}

	public void setRelateOrg(String relateOrg) {
		this.relateOrg = relateOrg;
	}

	public String getRelateOrgCode() {
		return this.relateOrgCode;
	}

	public void setRelateOrgCode(String relateOrgCode) {
		this.relateOrgCode = relateOrgCode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVtype() {
		return this.vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

}