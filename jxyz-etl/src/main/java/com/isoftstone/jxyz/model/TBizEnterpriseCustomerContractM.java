package com.isoftstone.jxyz.model;

import com.github.drinkjava2.jsqlbox.ActiveRecord;

import com.github.drinkjava2.jdialects.annotation.jpa.*;

import java.sql.Timestamp;

@Entity
@Table(name = "t_biz_enterprise_customer_contract_m")
public class TBizEnterpriseCustomerContractM extends ActiveRecord<DwrSalesDepartmentCollectionT> {
    @Column(name = "enterprise_instance_id")
    private long enterpriseInstanceId;


    @Column(name = "expiration_time")
    private Timestamp expirationTime;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "name")
    private String name;

    @Column(name = "reminder_time")
    private Timestamp reminderTime;

    @Column(name = "reminder_days")
    private Integer reminderDays;

    @Column(name = "status")
    private Integer status;

    @Column(name = "is_reminder")
    private Integer isReminder;

    @Column(name = "reminder_status")
    private Integer reminderStatus;

    @Column(name = "is_end")
    private Integer isEnd;

    @Column(name = "images")
    private String images;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "modify_user")
    private String modifyUser;
    @Column(name = "modify_date")
    private Timestamp modifyDate;

}
