package com.isoftstone.jxyz.model;

import com.github.drinkjava2.jdialects.annotation.jpa.*;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "t_uptown_m")
public class TUptownM extends ActiveRecord<DwrSalesDepartmentCollectionT> {

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


   
    @Column(name = "type")
    private Integer type;


   
    @Column(name = "longitude")
    private BigDecimal longitude;


   
    @Column(name = "latitude")
    private BigDecimal latitude;


   
    @Column(name = "bundary_coordinates")
    private Object bundaryCoordinates;


   
    @Column(name = "actual_householdcount")
    private Integer actualHouseholdcount;

   
    @Column(name = "address")
    private String address;

   
    @Column(name = "status")
    private Integer status;

   
    @Column(name = "total_buildingcount")
    private Integer totalBuildingcount;

   
    @Column(name = "bw_user_num")
    private Integer bwUserNum;

   
    @Column(name = "is_major")
    private Integer isMajor;

   
    @Column(name = "port_use")
    private Integer portUse;


   
    @Column(name = "wkt")
    private String wkt;


   
    @Column(name = "center_coordinate")
    private Object centerCoordinate;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getActualHouseholdcount() {
        return actualHouseholdcount;
    }

    public void setActualHouseholdcount(Integer actualHouseholdcount) {
        this.actualHouseholdcount = actualHouseholdcount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalBuildingcount() {
        return totalBuildingcount;
    }

    public void setTotalBuildingcount(Integer totalBuildingcount) {
        this.totalBuildingcount = totalBuildingcount;
    }

    public Integer getBwUserNum() {
        return bwUserNum;
    }

    public void setBwUserNum(Integer bwUserNum) {
        this.bwUserNum = bwUserNum;
    }

    public Integer getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(Integer isMajor) {
        this.isMajor = isMajor;
    }

    public Integer getPortUse() {
        return portUse;
    }

    public void setPortUse(Integer portUse) {
        this.portUse = portUse;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public Object getCenterCoordinate() {
        return centerCoordinate;
    }

    public void setCenterCoordinate(Object centerCoordinate) {
        this.centerCoordinate = centerCoordinate;
    }
}
