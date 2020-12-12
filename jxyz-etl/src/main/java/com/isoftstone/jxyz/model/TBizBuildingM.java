package com.isoftstone.jxyz.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


@Entity
@Table(name = "t_biz_building_m")
public class TBizBuildingM extends ActiveRecord<DwrSalesDepartmentCollectionT> {
    @Column(name = "extend_column")
    private Object extendColumn;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_date")
    private Timestamp createDate;
    @Column(name = "modify_user")
    private String modifyUser;
    @Column(name = "modify_date")
    private Timestamp modifyDate;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "district_code")
    private String districtCode;
    @Column(name = "district_name")
    private String districtName;
    @Column(name = "grid_code")
    private String gridCode;
    @Column(name = "grid_name")
    private String gridName;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "customer_total")
    private Integer customerTotal;
    @Column(name = "property_name")
    private String propertyName;
    @Column(name = "property_tel")
    private String propertyTel;
    @Column(name = "status")
    private Integer status;
    @Column(name = "bundary_coordinates")
    private Object bundaryCoordinates;
    @Column(name = "wkt")
    private String wkt;

    public Object getExtendColumn() {
        return extendColumn;
    }

    public void setExtendColumn(Object extendColumn) {
        this.extendColumn = extendColumn;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public Integer getCustomerTotal() {
        return customerTotal;
    }

    public void setCustomerTotal(Integer customerTotal) {
        this.customerTotal = customerTotal;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyTel() {
        return propertyTel;
    }

    public void setPropertyTel(String propertyTel) {
        this.propertyTel = propertyTel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getBundaryCoordinates() {
        return bundaryCoordinates;
    }

    public void setBundaryCoordinates(Object bundaryCoordinates) {
        this.bundaryCoordinates = bundaryCoordinates;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

}
