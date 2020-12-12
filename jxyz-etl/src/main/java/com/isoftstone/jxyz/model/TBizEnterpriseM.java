package com.isoftstone.jxyz.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import com.github.drinkjava2.jdialects.annotation.jpa.*;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

@Entity
@Table(name = "t_biz_enterprise_m")
public class TBizEnterpriseM extends ActiveRecord<DwrSalesDepartmentCollectionT> {
    
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

    
    @Column(name = "code")
    private String code;

    
    @Column(name = "name")
    private String name;

    
    @Column(name = "type")
    private String type;


    
    @Column(name = "longitude")
    private BigDecimal longitude;

    
    @Column(name = "latitude")
    private BigDecimal latitude;


    
    @Column(name = "bundary_coordinates")
    private Object bundaryCoordinates;


    
    @Column(name = "employee_number")
    private Integer employeeNumber;


    
    @Column(name = "address")
    private String address;


    
    @Column(name = "customer_manager")
    private String customerManager;


    
    @Column(name = "customer_manager_phone")
    private String customerManagerPhone;


    
    @Column(name = "industry_typ_id")
    private String industryTypId;

    
    @Column(name = "industry_typ_name")
    private String industryTypName;

    
    @Column(name = "industry_sub_typ_id")
    private String industrySubTypId;

    
    @Column(name = "industry_sub_typ_name")
    private String industrySubTypName;


    
    @Column(name = "wkt")
    private String wkt;

    
    @Column(name = "grid_code")
    private String gridCode;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(String customerManager) {
        this.customerManager = customerManager;
    }

    public String getCustomerManagerPhone() {
        return customerManagerPhone;
    }

    public void setCustomerManagerPhone(String customerManagerPhone) {
        this.customerManagerPhone = customerManagerPhone;
    }

    public String getIndustryTypId() {
        return industryTypId;
    }

    public void setIndustryTypId(String industryTypId) {
        this.industryTypId = industryTypId;
    }

    public String getIndustryTypName() {
        return industryTypName;
    }

    public void setIndustryTypName(String industryTypName) {
        this.industryTypName = industryTypName;
    }

    public String getIndustrySubTypId() {
        return industrySubTypId;
    }

    public void setIndustrySubTypId(String industrySubTypId) {
        this.industrySubTypId = industrySubTypId;
    }

    public String getIndustrySubTypName() {
        return industrySubTypName;
    }

    public void setIndustrySubTypName(String industrySubTypName) {
        this.industrySubTypName = industrySubTypName;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }
}
