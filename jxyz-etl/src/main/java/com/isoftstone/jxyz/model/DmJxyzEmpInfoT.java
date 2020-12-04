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
 * The persistent class for the dm_jxyz_emp_info_t database table.
 * 
 */
@Entity
@Table(name="dm_jxyz_emp_info_t")
public class DmJxyzEmpInfoT extends ActiveRecord<DmJxyzEmpInfoT>{

	@Id
	private String id;

	@Column(name="ado_id")
	private String adoId;

	@Column(name="bundary_coordinate")
	private String bundaryCoordinate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_user")
	private String createUser;

	@Column(name="customer_complaints")
	private String customerComplaints;

	@Column(name="daily_delivery")
	private int dailyDelivery;

	@Column(name="daily_salary")
	private BigDecimal dailySalary;

	@Column(name="daily_volume")
	private int dailyVolume;

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

	@Column(name="emp_tel")
	private String empTel;

	@Column(name="emp_tel2")
	private String empTel2;

	@Column(name="emp_training_times")
	private int empTrainingTimes;

	@Column(name="emp_working_seniority")
	private double empWorkingSeniority;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="grid_code")
	private String gridCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="hire_time")
	private Date hireTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date leavedate;

	@Column(name="location_latitude")
	private BigDecimal locationLatitude;

	@Column(name="location_longitude")
	private BigDecimal locationLongitude;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	@Column(name="monthly_delivery")
	private int monthlyDelivery;

	@Column(name="monthly_salary")
	private BigDecimal monthlySalary;

	@Column(name="monthly_volume")
	private int monthlyVolume;

	private String remark;

	@Column(name="star_rating")
	private String starRating;

	public DmJxyzEmpInfoT() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getCustomerComplaints() {
		return this.customerComplaints;
	}

	public void setCustomerComplaints(String customerComplaints) {
		this.customerComplaints = customerComplaints;
	}

	public int getDailyDelivery() {
		return this.dailyDelivery;
	}

	public void setDailyDelivery(int dailyDelivery) {
		this.dailyDelivery = dailyDelivery;
	}

	public BigDecimal getDailySalary() {
		return this.dailySalary;
	}

	public void setDailySalary(BigDecimal dailySalary) {
		this.dailySalary = dailySalary;
	}

	public int getDailyVolume() {
		return this.dailyVolume;
	}

	public void setDailyVolume(int dailyVolume) {
		this.dailyVolume = dailyVolume;
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

	public int getEmpTrainingTimes() {
		return this.empTrainingTimes;
	}

	public void setEmpTrainingTimes(int empTrainingTimes) {
		this.empTrainingTimes = empTrainingTimes;
	}

	public double getEmpWorkingSeniority() {
		return this.empWorkingSeniority;
	}

	public void setEmpWorkingSeniority(double empWorkingSeniority) {
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

	public Date getHireTime() {
		return this.hireTime;
	}

	public void setHireTime(Date hireTime) {
		this.hireTime = hireTime;
	}

	public Date getLeavedate() {
		return this.leavedate;
	}

	public void setLeavedate(Date leavedate) {
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

	public int getMonthlyDelivery() {
		return this.monthlyDelivery;
	}

	public void setMonthlyDelivery(int monthlyDelivery) {
		this.monthlyDelivery = monthlyDelivery;
	}

	public BigDecimal getMonthlySalary() {
		return this.monthlySalary;
	}

	public void setMonthlySalary(BigDecimal monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	public int getMonthlyVolume() {
		return this.monthlyVolume;
	}

	public void setMonthlyVolume(int monthlyVolume) {
		this.monthlyVolume = monthlyVolume;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStarRating() {
		return this.starRating;
	}

	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}

}