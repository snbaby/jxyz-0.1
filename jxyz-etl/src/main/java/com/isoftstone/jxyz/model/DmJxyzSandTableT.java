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
 * The persistent class for the dm_jxyz_sand_table_t database table.
 * 
 */
@Entity
@Table(name="dm_jxyz_sand_table_t")
public class DmJxyzSandTableT extends ActiveRecord<DmJxyzSandTableT> {

	@Id
	private int id;

	@Column(name="characteristic_markets")
	private int characteristicMarkets;

	@Column(name="city_name")
	private String cityName;

	@Column(name="city_no")
	private String cityNo;

	@Column(name="commercial_buildings")
	private int commercialBuildings;

	@Column(name="commercial_buildings_act_customer_qty")
	private int commercialBuildingsActCustomerQty;

	@Column(name="commercial_buildings_customer_qty")
	private int commercialBuildingsCustomerQty;

	@Column(name="commercial_buildings_dev_rate")
	private int commercialBuildingsDevRate;

	@Column(name="county_name")
	private String countyName;

	@Column(name="county_no")
	private String countyNo;

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

	@Column(name="created_user")
	private String createdUser;

	@Column(name="extend_column")
	private Object extendColumn;

	@Column(name="grid_code")
	private String gridCode;

	@Column(name="grid_level")
	private String gridLevel;

	@Column(name="industrial_park")
	private int industrialPark;

	@Column(name="industrial_park_act_customer_qty")
	private int industrialParkActCustomerQty;

	@Column(name="industrial_park_customer_qty")
	private int industrialParkCustomerQty;

	@Column(name="industrial_park_dev_rate")
	private BigDecimal industrialParkDevRate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modify_date")
	private Date modifyDate;

	@Column(name="modify_user")
	private String modifyUser;

	@Column(name="month_cash_income")
	private BigDecimal monthCashIncome;

	@Column(name="month_cash_qty")
	private int monthCashQty;

	@Column(name="month_collection_qty")
	private int monthCollectionQty;

	@Column(name="month_delivery_qty")
	private int monthDeliveryQty;

	@Column(name="month_growth_rate")
	private BigDecimal monthGrowthRate;

	@Column(name="month_salary")
	private BigDecimal monthSalary;

	@Column(name="post_org_name")
	private String postOrgName;

	@Column(name="post_org_no")
	private String postOrgNo;

	@Column(name="post_person_qty")
	private int postPersonQty;

	@Column(name="province_name")
	private String provinceName;

	@Column(name="province_no")
	private String provinceNo;

	@Column(name="region_name")
	private String regionName;

	@Column(name="residential_quarters")
	private int residentialQuarters;

	@Column(name="section_name")
	private String sectionName;

	@Column(name="section_no")
	private String sectionNo;

	@Column(name="year_collection_qty")
	private int yearCollectionQty;

	@Column(name="year_delivery_growth_rate")
	private BigDecimal yearDeliveryGrowthRate;

	@Column(name="year_delivery_qty")
	private int yearDeliveryQty;

	@Column(name="year_growth_rate")
	private BigDecimal yearGrowthRate;

	@Column(name="year_salary")
	private BigDecimal yearSalary;

	@Column(name="yesterday_cash_income")
	private BigDecimal yesterdayCashIncome;

	@Column(name="yesterday_cash_qty")
	private int yesterdayCashQty;

	@Column(name="yesterday_collection_qty")
	private int yesterdayCollectionQty;

	@Column(name="yesterday_delivery_qty")
	private int yesterdayDeliveryQty;

	@Column(name="yesterday_growth_rate")
	private BigDecimal yesterdayGrowthRate;

	@Column(name="yesterday_salary")
	private BigDecimal yesterdaySalary;

	public DmJxyzSandTableT() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCharacteristicMarkets() {
		return this.characteristicMarkets;
	}

	public void setCharacteristicMarkets(int characteristicMarkets) {
		this.characteristicMarkets = characteristicMarkets;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityNo() {
		return this.cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public int getCommercialBuildings() {
		return this.commercialBuildings;
	}

	public void setCommercialBuildings(int commercialBuildings) {
		this.commercialBuildings = commercialBuildings;
	}

	public int getCommercialBuildingsActCustomerQty() {
		return this.commercialBuildingsActCustomerQty;
	}

	public void setCommercialBuildingsActCustomerQty(int commercialBuildingsActCustomerQty) {
		this.commercialBuildingsActCustomerQty = commercialBuildingsActCustomerQty;
	}

	public int getCommercialBuildingsCustomerQty() {
		return this.commercialBuildingsCustomerQty;
	}

	public void setCommercialBuildingsCustomerQty(int commercialBuildingsCustomerQty) {
		this.commercialBuildingsCustomerQty = commercialBuildingsCustomerQty;
	}

	public int getCommercialBuildingsDevRate() {
		return this.commercialBuildingsDevRate;
	}

	public void setCommercialBuildingsDevRate(int commercialBuildingsDevRate) {
		this.commercialBuildingsDevRate = commercialBuildingsDevRate;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyNo() {
		return this.countyNo;
	}

	public void setCountyNo(String countyNo) {
		this.countyNo = countyNo;
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

	public String getCreatedUser() {
		return this.createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
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

	public String getGridLevel() {
		return this.gridLevel;
	}

	public void setGridLevel(String gridLevel) {
		this.gridLevel = gridLevel;
	}

	public int getIndustrialPark() {
		return this.industrialPark;
	}

	public void setIndustrialPark(int industrialPark) {
		this.industrialPark = industrialPark;
	}

	public int getIndustrialParkActCustomerQty() {
		return this.industrialParkActCustomerQty;
	}

	public void setIndustrialParkActCustomerQty(int industrialParkActCustomerQty) {
		this.industrialParkActCustomerQty = industrialParkActCustomerQty;
	}

	public int getIndustrialParkCustomerQty() {
		return this.industrialParkCustomerQty;
	}

	public void setIndustrialParkCustomerQty(int industrialParkCustomerQty) {
		this.industrialParkCustomerQty = industrialParkCustomerQty;
	}

	public BigDecimal getIndustrialParkDevRate() {
		return this.industrialParkDevRate;
	}

	public void setIndustrialParkDevRate(BigDecimal industrialParkDevRate) {
		this.industrialParkDevRate = industrialParkDevRate;
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

	public BigDecimal getMonthCashIncome() {
		return this.monthCashIncome;
	}

	public void setMonthCashIncome(BigDecimal monthCashIncome) {
		this.monthCashIncome = monthCashIncome;
	}

	public int getMonthCashQty() {
		return this.monthCashQty;
	}

	public void setMonthCashQty(int monthCashQty) {
		this.monthCashQty = monthCashQty;
	}

	public int getMonthCollectionQty() {
		return this.monthCollectionQty;
	}

	public void setMonthCollectionQty(int monthCollectionQty) {
		this.monthCollectionQty = monthCollectionQty;
	}

	public int getMonthDeliveryQty() {
		return this.monthDeliveryQty;
	}

	public void setMonthDeliveryQty(int monthDeliveryQty) {
		this.monthDeliveryQty = monthDeliveryQty;
	}

	public BigDecimal getMonthGrowthRate() {
		return this.monthGrowthRate;
	}

	public void setMonthGrowthRate(BigDecimal monthGrowthRate) {
		this.monthGrowthRate = monthGrowthRate;
	}

	public BigDecimal getMonthSalary() {
		return this.monthSalary;
	}

	public void setMonthSalary(BigDecimal monthSalary) {
		this.monthSalary = monthSalary;
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

	public int getPostPersonQty() {
		return this.postPersonQty;
	}

	public void setPostPersonQty(int postPersonQty) {
		this.postPersonQty = postPersonQty;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceNo() {
		return this.provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getResidentialQuarters() {
		return this.residentialQuarters;
	}

	public void setResidentialQuarters(int residentialQuarters) {
		this.residentialQuarters = residentialQuarters;
	}

	public String getSectionName() {
		return this.sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSectionNo() {
		return this.sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public int getYearCollectionQty() {
		return this.yearCollectionQty;
	}

	public void setYearCollectionQty(int yearCollectionQty) {
		this.yearCollectionQty = yearCollectionQty;
	}

	public BigDecimal getYearDeliveryGrowthRate() {
		return this.yearDeliveryGrowthRate;
	}

	public void setYearDeliveryGrowthRate(BigDecimal yearDeliveryGrowthRate) {
		this.yearDeliveryGrowthRate = yearDeliveryGrowthRate;
	}

	public int getYearDeliveryQty() {
		return this.yearDeliveryQty;
	}

	public void setYearDeliveryQty(int yearDeliveryQty) {
		this.yearDeliveryQty = yearDeliveryQty;
	}

	public BigDecimal getYearGrowthRate() {
		return this.yearGrowthRate;
	}

	public void setYearGrowthRate(BigDecimal yearGrowthRate) {
		this.yearGrowthRate = yearGrowthRate;
	}

	public BigDecimal getYearSalary() {
		return this.yearSalary;
	}

	public void setYearSalary(BigDecimal yearSalary) {
		this.yearSalary = yearSalary;
	}

	public BigDecimal getYesterdayCashIncome() {
		return this.yesterdayCashIncome;
	}

	public void setYesterdayCashIncome(BigDecimal yesterdayCashIncome) {
		this.yesterdayCashIncome = yesterdayCashIncome;
	}

	public int getYesterdayCashQty() {
		return this.yesterdayCashQty;
	}

	public void setYesterdayCashQty(int yesterdayCashQty) {
		this.yesterdayCashQty = yesterdayCashQty;
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

	public BigDecimal getYesterdayGrowthRate() {
		return this.yesterdayGrowthRate;
	}

	public void setYesterdayGrowthRate(BigDecimal yesterdayGrowthRate) {
		this.yesterdayGrowthRate = yesterdayGrowthRate;
	}

	public BigDecimal getYesterdaySalary() {
		return this.yesterdaySalary;
	}

	public void setYesterdaySalary(BigDecimal yesterdaySalary) {
		this.yesterdaySalary = yesterdaySalary;
	}

}