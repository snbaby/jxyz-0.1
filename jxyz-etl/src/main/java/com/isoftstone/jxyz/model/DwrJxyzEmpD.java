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
 * The persistent class for the dwr_jxyz_emp_d database table.
 * 
 */
@Entity
@Table(name="dwr_jxyz_emp_d")
public class DwrJxyzEmpD extends ActiveRecord<DwrJxyzEmpD> {

	@Column(name="ado_id")
	private String adoId;

	@Column(name="bundary_coordinate")
	private String bundaryCoordinate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_user")
	private String createUser;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="emp_code")
	private String empCode;

	@Column(name="emp_dept_code")
	private String empDeptCode;

	@Column(name="emp_dept_name")
	private String empDeptName;

	@Column(name="emp_name")
	private String empName;

	@Column(name="emp_post")
	private String empPost;

	@Column(name="emp_section_code")
	private String empSectionCode;

	@Column(name="emp_section_name")
	private String empSectionName;

	@Column(name="emp_status")
	private String empStatus;

	@Column(name="emp_tel")
	private String empTel;

	@Column(name="emp_tel2")
	private String empTel2;

	@Column(name="emp_training_times")
	private String empTrainingTimes;

	@Column(name="emp_working_seniority")
	private String empWorkingSeniority;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="grid_code")
	private String gridCode;

	@Column(name="hire_time")
	private String hireTime;

	private BigInteger id;

	private String leavedate;

	@Column(name="location_latitude")
	private BigDecimal locationLatitude;

	@Column(name="location_longitude")
	private BigDecimal locationLongitude;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	private String remark;

	public DwrJxyzEmpD() {
	}

	public String getAdoId() {
		return this.adoId;
	}

	public void setAdoId(String adoId) {
		this.adoId = adoId;
	}

	public String getBundaryCoordinate() {
		return this.bundaryCoordinate;
	}

	public void setBundaryCoordinate(String bundaryCoordinate) {
		this.bundaryCoordinate = bundaryCoordinate;
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

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpDeptCode() {
		return this.empDeptCode;
	}

	public void setEmpDeptCode(String empDeptCode) {
		this.empDeptCode = empDeptCode;
	}

	public String getEmpDeptName() {
		return this.empDeptName;
	}

	public void setEmpDeptName(String empDeptName) {
		this.empDeptName = empDeptName;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPost() {
		return this.empPost;
	}

	public void setEmpPost(String empPost) {
		this.empPost = empPost;
	}

	public String getEmpSectionCode() {
		return this.empSectionCode;
	}

	public void setEmpSectionCode(String empSectionCode) {
		this.empSectionCode = empSectionCode;
	}

	public String getEmpSectionName() {
		return this.empSectionName;
	}

	public void setEmpSectionName(String empSectionName) {
		this.empSectionName = empSectionName;
	}

	public String getEmpStatus() {
		return this.empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getEmpTel() {
		return this.empTel;
	}

	public void setEmpTel(String empTel) {
		this.empTel = empTel;
	}

	public String getEmpTel2() {
		return this.empTel2;
	}

	public void setEmpTel2(String empTel2) {
		this.empTel2 = empTel2;
	}

	public String getEmpTrainingTimes() {
		return this.empTrainingTimes;
	}

	public void setEmpTrainingTimes(String empTrainingTimes) {
		this.empTrainingTimes = empTrainingTimes;
	}

	public String getEmpWorkingSeniority() {
		return this.empWorkingSeniority;
	}

	public void setEmpWorkingSeniority(String empWorkingSeniority) {
		this.empWorkingSeniority = empWorkingSeniority;
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

	public String getHireTime() {
		return this.hireTime;
	}

	public void setHireTime(String hireTime) {
		this.hireTime = hireTime;
	}

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getLeavedate() {
		return this.leavedate;
	}

	public void setLeavedate(String leavedate) {
		this.leavedate = leavedate;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}