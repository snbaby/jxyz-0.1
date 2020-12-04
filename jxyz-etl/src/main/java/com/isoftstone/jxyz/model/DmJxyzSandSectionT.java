package com.isoftstone.jxyz.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jdialects.annotation.jpa.Temporal;
import com.github.drinkjava2.jdialects.annotation.jpa.TemporalType;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the dm_jxyz_sand_section_t database table.
 * 
 */
@Entity
@Table(name="dm_jxyz_sand_section_t")
public class DmJxyzSandSectionT extends ActiveRecord<DmJxyzSandSectionT> {

	@Id
	private int id;

	@Column(name="commercial_buildings")
	private int commercialBuildings;

	@Column(name="commercial_buildings_dev_rate")
	private BigDecimal commercialBuildingsDevRate;

	@Column(name="commercial_buildings_rate")
	private BigDecimal commercialBuildingsRate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	@Column(name="create_user")
	private String createUser;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="grid_code")
	private String gridCode;

	@Column(name="industrial_park_dev_qty")
	private BigDecimal industrialParkDevQty;

	@Column(name="industrial_park_qty")
	private int industrialParkQty;

	@Column(name="industrial_park_rate")
	private BigDecimal industrialParkRate;

	@Column(name="mail_per_person_rate")
	private BigDecimal mailPerPersonRate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	private BigInteger qty;

	@Column(name="residential_quarters_qty")
	private int residentialQuartersQty;

	@Column(name="residential_quarters_rate")
	private BigDecimal residentialQuartersRate;

	@Column(name="resources_type")
	private String resourcesType;

	@Column(name="school_qty")
	private int schoolQty;

	@Column(name="school_rate")
	private BigDecimal schoolRate;

	@Column(name="section_name")
	private String sectionName;

	@Column(name="service_per_capita")
	private BigDecimal servicePerCapita;

	@Column(name="service_radius")
	private BigDecimal serviceRadius;

	public DmJxyzSandSectionT() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCommercialBuildings() {
		return this.commercialBuildings;
	}

	public void setCommercialBuildings(int commercialBuildings) {
		this.commercialBuildings = commercialBuildings;
	}

	public BigDecimal getCommercialBuildingsDevRate() {
		return this.commercialBuildingsDevRate;
	}

	public void setCommercialBuildingsDevRate(BigDecimal commercialBuildingsDevRate) {
		this.commercialBuildingsDevRate = commercialBuildingsDevRate;
	}

	public BigDecimal getCommercialBuildingsRate() {
		return this.commercialBuildingsRate;
	}

	public void setCommercialBuildingsRate(BigDecimal commercialBuildingsRate) {
		this.commercialBuildingsRate = commercialBuildingsRate;
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

	public BigDecimal getIndustrialParkDevQty() {
		return this.industrialParkDevQty;
	}

	public void setIndustrialParkDevQty(BigDecimal industrialParkDevQty) {
		this.industrialParkDevQty = industrialParkDevQty;
	}

	public int getIndustrialParkQty() {
		return this.industrialParkQty;
	}

	public void setIndustrialParkQty(int industrialParkQty) {
		this.industrialParkQty = industrialParkQty;
	}

	public BigDecimal getIndustrialParkRate() {
		return this.industrialParkRate;
	}

	public void setIndustrialParkRate(BigDecimal industrialParkRate) {
		this.industrialParkRate = industrialParkRate;
	}

	public BigDecimal getMailPerPersonRate() {
		return this.mailPerPersonRate;
	}

	public void setMailPerPersonRate(BigDecimal mailPerPersonRate) {
		this.mailPerPersonRate = mailPerPersonRate;
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

	public BigInteger getQty() {
		return this.qty;
	}

	public void setQty(BigInteger qty) {
		this.qty = qty;
	}

	public int getResidentialQuartersQty() {
		return this.residentialQuartersQty;
	}

	public void setResidentialQuartersQty(int residentialQuartersQty) {
		this.residentialQuartersQty = residentialQuartersQty;
	}

	public BigDecimal getResidentialQuartersRate() {
		return this.residentialQuartersRate;
	}

	public void setResidentialQuartersRate(BigDecimal residentialQuartersRate) {
		this.residentialQuartersRate = residentialQuartersRate;
	}

	public String getResourcesType() {
		return this.resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}

	public int getSchoolQty() {
		return this.schoolQty;
	}

	public void setSchoolQty(int schoolQty) {
		this.schoolQty = schoolQty;
	}

	public BigDecimal getSchoolRate() {
		return this.schoolRate;
	}

	public void setSchoolRate(BigDecimal schoolRate) {
		this.schoolRate = schoolRate;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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

}