package com.isoftstone.jxyz.model;

import com.github.drinkjava2.jdialects.annotation.jpa.*;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import java.sql.Timestamp;


@Entity
@Table(name = "t_biz_resource_customer_relation")
public class TBizResourceCustomerRelation extends ActiveRecord<DwrSalesDepartmentCollectionT> {

    @Column(name = "type")
    private String type;

    @Column(name = "resource_code")
    private String resourceCode;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "modify_date")
    private Timestamp modifyDate;

    @Column(name = "modify_user")
    private String modifyUser;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}
