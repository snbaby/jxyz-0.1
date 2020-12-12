package com.isoftstone.jxyz.model;

import com.github.drinkjava2.jdialects.annotation.jpa.*;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import java.sql.Timestamp;

@Entity
@Table(name = "t_biz_enterprise_customer_instance_m")
public class TBizEnterpriseCustomerInstanceM extends ActiveRecord<DwrSalesDepartmentCollectionT> {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "enterprise_code")
    private String enterpriseCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "email")
    private String email;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "field1")
    private String field1;

    @Column(name = "field2")
    private String field2;

    @Column(name = "field3")
    private String field3;

    @Column(name = "field4")
    private String field4;

    @Column(name = "field5")
    private String field5;

    @Column(name = "field6")
    private String field6;

    @Column(name = "field7")
    private String field7;

    @Column(name = "field8")
    private String field8;

    @Column(name = "field9")
    private String field9;

    @Column(name = "field10")
    private String field10;

    @Column(name = "field11")
    private String field11;

    @Column(name = "field12")
    private String field12;

    @Column(name = "field13")
    private String field13;

    @Column(name = "field14")
    private String field14;

    @Column(name = "field15")
    private String field15;

    @Column(name = "field16")
    private String field16;

    @Column(name = "field17")
    private String field17;

    @Column(name = "field18")
    private String field18;

    @Column(name = "field19")
    private String field19;

    @Column(name = "field20")
    private String field20;

    @Column(name = "field21")
    private String field21;

    @Column(name = "field22")
    private String field22;

    @Column(name = "field23")
    private String field23;

    @Column(name = "field24")
    private String field24;

    @Column(name = "field25")
    private String field25;

    @Column(name = "field26")
    private String field26;

    @Column(name = "field27")
    private String field27;

    @Column(name = "field28")
    private String field28;

    @Column(name = "field29")
    private String field29;

    @Column(name = "field30")
    private String field30;

    @Column(name = "field31")
    private String field31;

    @Column(name = "field32")
    private String field32;

    @Column(name = "field33")
    private String field33;

    @Column(name = "field34")
    private String field34;

    @Column(name = "field35")
    private String field35;

    @Column(name = "field36")
    private String field36;

    @Column(name = "field37")
    private String field37;

    @Column(name = "field38")
    private String field38;

    @Column(name = "field39")
    private String field39;

    @Column(name = "field40")
    private String field40;

    @Column(name = "field41")
    private String field41;

    @Column(name = "field42")
    private String field42;

    @Column(name = "field43")
    private String field43;

    @Column(name = "field44")
    private String field44;

    @Column(name = "field45")
    private String field45;

    @Column(name = "field46")
    private String field46;

    @Column(name = "field47")
    private String field47;

    @Column(name = "field48")
    private String field48;

    @Column(name = "field49")
    private String field49;

    @Column(name = "field50")
    private String field50;

    @Column(name = "field51")
    private String field51;

    @Column(name = "field52")
    private String field52;

    @Column(name = "field53")
    private String field53;

    @Column(name = "field54")
    private String field54;

    @Column(name = "field55")
    private String field55;

    @Column(name = "field56")
    private String field56;

    @Column(name = "field57")
    private String field57;

    @Column(name = "field58")
    private String field58;

    @Column(name = "field59")
    private String field59;

    @Column(name = "field60")
    private String field60;

    @Column(name = "field61")
    private String field61;

    @Column(name = "field62")
    private String field62;

    @Column(name = "field63")
    private String field63;

    @Column(name = "field64")
    private String field64;

    @Column(name = "field65")
    private String field65;

    @Column(name = "field66")
    private String field66;

    @Column(name = "field67")
    private String field67;

    @Column(name = "field68")
    private String field68;

    @Column(name = "field69")
    private String field69;

    @Column(name = "field70")
    private String field70;

    @Column(name = "field71")
    private String field71;

    @Column(name = "field72")
    private String field72;

    @Column(name = "field73")
    private String field73;

    @Column(name = "field74")
    private String field74;

    @Column(name = "field75")
    private String field75;

    @Column(name = "field76")
    private String field76;

    @Column(name = "field77")
    private String field77;

    @Column(name = "field78")
    private String field78;

    @Column(name = "field79")
    private String field79;

    @Column(name = "field80")
    private String field80;

    @Column(name = "field81")
    private String field81;

    @Column(name = "field82")
    private String field82;

    @Column(name = "field83")
    private String field83;

    @Column(name = "field84")
    private String field84;

    @Column(name = "field85")
    private String field85;

    @Column(name = "field86")
    private String field86;

    @Column(name = "field87")
    private String field87;

    @Column(name = "field88")
    private String field88;

    @Column(name = "field89")
    private String field89;

    @Column(name = "field90")
    private String field90;


    @Column(name = "create_user")
    private String createUser;


    @Column(name = "create_date")
    private Timestamp createDate;


    @Column(name = "modify_user")
    private String modifyUser;


    @Column(name = "modify_date")
    private Timestamp modifyDate;


    @Column(name = "tag_codexs")
    private String tagCodexs;


    @Column(name = "customer_code")
    private String customerCode;

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public String getField6() {
        return field6;
    }

    public void setField6(String field6) {
        this.field6 = field6;
    }

    public String getField7() {
        return field7;
    }

    public void setField7(String field7) {
        this.field7 = field7;
    }

    public String getField8() {
        return field8;
    }

    public void setField8(String field8) {
        this.field8 = field8;
    }

    public String getField9() {
        return field9;
    }

    public void setField9(String field9) {
        this.field9 = field9;
    }

    public String getField10() {
        return field10;
    }

    public void setField10(String field10) {
        this.field10 = field10;
    }

    public String getField11() {
        return field11;
    }

    public void setField11(String field11) {
        this.field11 = field11;
    }

    public String getField12() {
        return field12;
    }

    public void setField12(String field12) {
        this.field12 = field12;
    }

    public String getField13() {
        return field13;
    }

    public void setField13(String field13) {
        this.field13 = field13;
    }

    public String getField14() {
        return field14;
    }

    public void setField14(String field14) {
        this.field14 = field14;
    }

    public String getField15() {
        return field15;
    }

    public void setField15(String field15) {
        this.field15 = field15;
    }

    public String getField16() {
        return field16;
    }

    public void setField16(String field16) {
        this.field16 = field16;
    }

    public String getField17() {
        return field17;
    }

    public void setField17(String field17) {
        this.field17 = field17;
    }

    public String getField18() {
        return field18;
    }

    public void setField18(String field18) {
        this.field18 = field18;
    }

    public String getField19() {
        return field19;
    }

    public void setField19(String field19) {
        this.field19 = field19;
    }

    public String getField20() {
        return field20;
    }

    public void setField20(String field20) {
        this.field20 = field20;
    }

    public String getField21() {
        return field21;
    }

    public void setField21(String field21) {
        this.field21 = field21;
    }

    public String getField22() {
        return field22;
    }

    public void setField22(String field22) {
        this.field22 = field22;
    }

    public String getField23() {
        return field23;
    }

    public void setField23(String field23) {
        this.field23 = field23;
    }

    public String getField24() {
        return field24;
    }

    public void setField24(String field24) {
        this.field24 = field24;
    }

    public String getField25() {
        return field25;
    }

    public void setField25(String field25) {
        this.field25 = field25;
    }

    public String getField26() {
        return field26;
    }

    public void setField26(String field26) {
        this.field26 = field26;
    }

    public String getField27() {
        return field27;
    }

    public void setField27(String field27) {
        this.field27 = field27;
    }

    public String getField28() {
        return field28;
    }

    public void setField28(String field28) {
        this.field28 = field28;
    }

    public String getField29() {
        return field29;
    }

    public void setField29(String field29) {
        this.field29 = field29;
    }

    public String getField30() {
        return field30;
    }

    public void setField30(String field30) {
        this.field30 = field30;
    }

    public String getField31() {
        return field31;
    }

    public void setField31(String field31) {
        this.field31 = field31;
    }

    public String getField32() {
        return field32;
    }

    public void setField32(String field32) {
        this.field32 = field32;
    }

    public String getField33() {
        return field33;
    }

    public void setField33(String field33) {
        this.field33 = field33;
    }

    public String getField34() {
        return field34;
    }

    public void setField34(String field34) {
        this.field34 = field34;
    }

    public String getField35() {
        return field35;
    }

    public void setField35(String field35) {
        this.field35 = field35;
    }

    public String getField36() {
        return field36;
    }

    public void setField36(String field36) {
        this.field36 = field36;
    }

    public String getField37() {
        return field37;
    }

    public void setField37(String field37) {
        this.field37 = field37;
    }

    public String getField38() {
        return field38;
    }

    public void setField38(String field38) {
        this.field38 = field38;
    }

    public String getField39() {
        return field39;
    }

    public void setField39(String field39) {
        this.field39 = field39;
    }

    public String getField40() {
        return field40;
    }

    public void setField40(String field40) {
        this.field40 = field40;
    }

    public String getField41() {
        return field41;
    }

    public void setField41(String field41) {
        this.field41 = field41;
    }

    public String getField42() {
        return field42;
    }

    public void setField42(String field42) {
        this.field42 = field42;
    }

    public String getField43() {
        return field43;
    }

    public void setField43(String field43) {
        this.field43 = field43;
    }

    public String getField44() {
        return field44;
    }

    public void setField44(String field44) {
        this.field44 = field44;
    }

    public String getField45() {
        return field45;
    }

    public void setField45(String field45) {
        this.field45 = field45;
    }

    public String getField46() {
        return field46;
    }

    public void setField46(String field46) {
        this.field46 = field46;
    }

    public String getField47() {
        return field47;
    }

    public void setField47(String field47) {
        this.field47 = field47;
    }

    public String getField48() {
        return field48;
    }

    public void setField48(String field48) {
        this.field48 = field48;
    }

    public String getField49() {
        return field49;
    }

    public void setField49(String field49) {
        this.field49 = field49;
    }

    public String getField50() {
        return field50;
    }

    public void setField50(String field50) {
        this.field50 = field50;
    }

    public String getField51() {
        return field51;
    }

    public void setField51(String field51) {
        this.field51 = field51;
    }

    public String getField52() {
        return field52;
    }

    public void setField52(String field52) {
        this.field52 = field52;
    }

    public String getField53() {
        return field53;
    }

    public void setField53(String field53) {
        this.field53 = field53;
    }

    public String getField54() {
        return field54;
    }

    public void setField54(String field54) {
        this.field54 = field54;
    }

    public String getField55() {
        return field55;
    }

    public void setField55(String field55) {
        this.field55 = field55;
    }

    public String getField56() {
        return field56;
    }

    public void setField56(String field56) {
        this.field56 = field56;
    }

    public String getField57() {
        return field57;
    }

    public void setField57(String field57) {
        this.field57 = field57;
    }

    public String getField58() {
        return field58;
    }

    public void setField58(String field58) {
        this.field58 = field58;
    }

    public String getField59() {
        return field59;
    }

    public void setField59(String field59) {
        this.field59 = field59;
    }

    public String getField60() {
        return field60;
    }

    public void setField60(String field60) {
        this.field60 = field60;
    }

    public String getField61() {
        return field61;
    }

    public void setField61(String field61) {
        this.field61 = field61;
    }

    public String getField62() {
        return field62;
    }

    public void setField62(String field62) {
        this.field62 = field62;
    }

    public String getField63() {
        return field63;
    }

    public void setField63(String field63) {
        this.field63 = field63;
    }

    public String getField64() {
        return field64;
    }

    public void setField64(String field64) {
        this.field64 = field64;
    }

    public String getField65() {
        return field65;
    }

    public void setField65(String field65) {
        this.field65 = field65;
    }

    public String getField66() {
        return field66;
    }

    public void setField66(String field66) {
        this.field66 = field66;
    }

    public String getField67() {
        return field67;
    }

    public void setField67(String field67) {
        this.field67 = field67;
    }

    public String getField68() {
        return field68;
    }

    public void setField68(String field68) {
        this.field68 = field68;
    }

    public String getField69() {
        return field69;
    }

    public void setField69(String field69) {
        this.field69 = field69;
    }

    public String getField70() {
        return field70;
    }

    public void setField70(String field70) {
        this.field70 = field70;
    }

    public String getField71() {
        return field71;
    }

    public void setField71(String field71) {
        this.field71 = field71;
    }

    public String getField72() {
        return field72;
    }

    public void setField72(String field72) {
        this.field72 = field72;
    }

    public String getField73() {
        return field73;
    }

    public void setField73(String field73) {
        this.field73 = field73;
    }

    public String getField74() {
        return field74;
    }

    public void setField74(String field74) {
        this.field74 = field74;
    }

    public String getField75() {
        return field75;
    }

    public void setField75(String field75) {
        this.field75 = field75;
    }

    public String getField76() {
        return field76;
    }

    public void setField76(String field76) {
        this.field76 = field76;
    }

    public String getField77() {
        return field77;
    }

    public void setField77(String field77) {
        this.field77 = field77;
    }

    public String getField78() {
        return field78;
    }

    public void setField78(String field78) {
        this.field78 = field78;
    }

    public String getField79() {
        return field79;
    }

    public void setField79(String field79) {
        this.field79 = field79;
    }

    public String getField80() {
        return field80;
    }

    public void setField80(String field80) {
        this.field80 = field80;
    }

    public String getField81() {
        return field81;
    }

    public void setField81(String field81) {
        this.field81 = field81;
    }

    public String getField82() {
        return field82;
    }

    public void setField82(String field82) {
        this.field82 = field82;
    }

    public String getField83() {
        return field83;
    }

    public void setField83(String field83) {
        this.field83 = field83;
    }

    public String getField84() {
        return field84;
    }

    public void setField84(String field84) {
        this.field84 = field84;
    }

    public String getField85() {
        return field85;
    }

    public void setField85(String field85) {
        this.field85 = field85;
    }

    public String getField86() {
        return field86;
    }

    public void setField86(String field86) {
        this.field86 = field86;
    }

    public String getField87() {
        return field87;
    }

    public void setField87(String field87) {
        this.field87 = field87;
    }

    public String getField88() {
        return field88;
    }

    public void setField88(String field88) {
        this.field88 = field88;
    }

    public String getField89() {
        return field89;
    }

    public void setField89(String field89) {
        this.field89 = field89;
    }

    public String getField90() {
        return field90;
    }

    public void setField90(String field90) {
        this.field90 = field90;
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

    public String getTagCodexs() {
        return tagCodexs;
    }

    public void setTagCodexs(String tagCodexs) {
        this.tagCodexs = tagCodexs;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
