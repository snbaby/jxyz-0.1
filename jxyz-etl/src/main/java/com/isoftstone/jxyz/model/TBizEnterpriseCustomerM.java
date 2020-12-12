package com.isoftstone.jxyz.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import com.github.drinkjava2.jdialects.annotation.jpa.*;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

@Entity
@Table(name = "t_biz_enterprise_customer_m")
public class TBizEnterpriseCustomerM extends ActiveRecord<DwrSalesDepartmentCollectionT> {


    @Column(name = "city_name")
    private String cityName;


    @Column(name = "city_code")
    private String cityCode;


    @Column(name = "district_code")
    private String districtCode;


    @Column(name = "district_name")
    private String districtName;


    @Column(name = "grid_code")
    private String gridCode;


    @Column(name = "grid_name")
    private String gridName;


    @Column(name = "all_parent_code")
    private String allParentCode;


    @Column(name = "level")
    private Integer level;


    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    @Column(name = "address")
    private String address;


    @Column(name = "parent_enterprise_code")
    private String parentEnterpriseCode;


    @Column(name = "longitude")
    private BigDecimal longitude;


    @Column(name = "latitude")
    private BigDecimal latitude;


    @Column(name = "bundary_coordinates")
    private Object bundaryCoordinates;


    @Column(name = "type")
    private String type;


    @Column(name = "industry_field")
    private String industryField;


    @Column(name = "status")
    private Integer status;


    @Column(name = "create_user")
    private String createUser;


    @Column(name = "create_date")
    private Timestamp createDate;


    @Column(name = "modify_user")
    private String modifyUser;


    @Column(name = "modify_date")
    private Timestamp modifyDate;


    @Column(name = "external_code")
    private String externalCode;

    @Column(name = "modify_user_name")
    private String modifyUserName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getAllParentCode() {
        return allParentCode;
    }

    public void setAllParentCode(String allParentCode) {
        this.allParentCode = allParentCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParentEnterpriseCode() {
        return parentEnterpriseCode;
    }

    public void setParentEnterpriseCode(String parentEnterpriseCode) {
        this.parentEnterpriseCode = parentEnterpriseCode;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Object getBundaryCoordinates() {
        return bundaryCoordinates;
    }

    public void setBundaryCoordinates(Object bundaryCoordinates) {
        this.bundaryCoordinates = bundaryCoordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndustryField() {
        return industryField;
    }

    public void setIndustryField(String industryField) {
        this.industryField = industryField;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }
}
