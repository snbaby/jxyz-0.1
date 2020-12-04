package com.isoftstone.model;

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
 * The persistent class for the t_emolument_result database table.
 * 
 */
@Entity
@Table(name="t_emolument_result")
public class TEmolumentResult extends ActiveRecord<TEmolumentResult> {

	@Id
	private int id;

	@Column(name="ado_id")
	private String adoId;

	@Column(name="all_parent_code")
	private String allParentCode;

	@Column(name="basic_salary")
	private BigDecimal basicSalary;

	@Column(name="city_code")
	private String cityCode;

	@Column(name="city_name")
	private String cityName;

	@Column(name="county_code")
	private String countyCode;

	@Column(name="county_name")
	private String countyName;

	@Column(name="deliver_court_num")
	private int deliverCourtNum;

	@Column(name="deliver_court_total")
	private BigDecimal deliverCourtTotal;

	@Column(name="deliver_num")
	private int deliverNum;

	@Column(name="deliver_total")
	private BigDecimal deliverTotal;

	@Column(name="dept_code")
	private String deptCode;

	@Column(name="dept_name")
	private String deptName;

	@Column(name="emp_code")
	private String empCode;

	@Column(name="emp_name")
	private String empName;

	@Column(name="express_package_num")
	private int expressPackageNum;

	@Column(name="express_package_total")
	private BigDecimal expressPackageTotal;

	@Column(name="grid_code")
	private String gridCode;

	@Column(name="grid_name")
	private String gridName;

	@Column(name="international_delivery_num")
	private int internationalDeliveryNum;

	@Column(name="international_delivery_total")
	private BigDecimal internationalDeliveryTotal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_time")
	private Date modifyTime;

	@Column(name="period_id")
	private String periodId;

	@Column(name="province_code")
	private String provinceCode;

	@Column(name="province_name")
	private String provinceName;

	@Column(name="standard_delivery_num")
	private int standardDeliveryNum;

	@Column(name="standard_delivery_total")
	private BigDecimal standardDeliveryTotal;

	public TEmolumentResult() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdoId() {
		return this.adoId;
	}

	public void setAdoId(String adoId) {
		this.adoId = adoId;
	}

	public String getAllParentCode() {
		return this.allParentCode;
	}

	public void setAllParentCode(String allParentCode) {
		this.allParentCode = allParentCode;
	}

	public BigDecimal getBasicSalary() {
		return this.basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
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

	public int getDeliverCourtNum() {
		return this.deliverCourtNum;
	}

	public void setDeliverCourtNum(int deliverCourtNum) {
		this.deliverCourtNum = deliverCourtNum;
	}

	public BigDecimal getDeliverCourtTotal() {
		return this.deliverCourtTotal;
	}

	public void setDeliverCourtTotal(BigDecimal deliverCourtTotal) {
		this.deliverCourtTotal = deliverCourtTotal;
	}

	public int getDeliverNum() {
		return this.deliverNum;
	}

	public void setDeliverNum(int deliverNum) {
		this.deliverNum = deliverNum;
	}

	public BigDecimal getDeliverTotal() {
		return this.deliverTotal;
	}

	public void setDeliverTotal(BigDecimal deliverTotal) {
		this.deliverTotal = deliverTotal;
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

	public String getEmpCode() {
		return this.empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getExpressPackageNum() {
		return this.expressPackageNum;
	}

	public void setExpressPackageNum(int expressPackageNum) {
		this.expressPackageNum = expressPackageNum;
	}

	public BigDecimal getExpressPackageTotal() {
		return this.expressPackageTotal;
	}

	public void setExpressPackageTotal(BigDecimal expressPackageTotal) {
		this.expressPackageTotal = expressPackageTotal;
	}

	public String getGridCode() {
		return this.gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getGridName() {
		return this.gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public int getInternationalDeliveryNum() {
		return this.internationalDeliveryNum;
	}

	public void setInternationalDeliveryNum(int internationalDeliveryNum) {
		this.internationalDeliveryNum = internationalDeliveryNum;
	}

	public BigDecimal getInternationalDeliveryTotal() {
		return this.internationalDeliveryTotal;
	}

	public void setInternationalDeliveryTotal(BigDecimal internationalDeliveryTotal) {
		this.internationalDeliveryTotal = internationalDeliveryTotal;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
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

	public int getStandardDeliveryNum() {
		return this.standardDeliveryNum;
	}

	public void setStandardDeliveryNum(int standardDeliveryNum) {
		this.standardDeliveryNum = standardDeliveryNum;
	}

	public BigDecimal getStandardDeliveryTotal() {
		return this.standardDeliveryTotal;
	}

	public void setStandardDeliveryTotal(BigDecimal standardDeliveryTotal) {
		this.standardDeliveryTotal = standardDeliveryTotal;
	}

}