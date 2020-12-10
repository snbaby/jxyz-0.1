package com.isoftstone.model;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "jpx_resource_collect_d")
public class JpxResourceCollectD extends ActiveRecord<JpxResourceCollectD> {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "grid_code")
    private String gridCode;
    @Column(name = "resoures_type")
    private String resouresType;
    @Column(name = "school_num")
    private Integer schoolNum;
    @Column(name = "gyy_num")
    private Integer gyyNum;
    @Column(name = "house_num")
    private Integer houseNum;
    @Column(name = "busi_num")
    private Integer busiNum;
    @Column(name = "school_user_num")
    private Integer schoolUserNum;
    @Column(name = "gyy_user_num")
    private Integer gyyUserNum;
    @Column(name = "house_user_num")
    private Integer houseUserNum;
    @Column(name = "busi_user_num")
    private Integer busiUserNum;
    @Column(name = "extend_column")
    private String extendColumn;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_date")
    private Timestamp createDate;
    @Column(name = "modify_user")
    private String modifyUser;
    @Column(name = "modify_date")
    private Timestamp modifyDate;
    @Column(name = "school_cover_num")
    private Integer schoolCoverNum;
    @Column(name = "gyy_cover_num")
    private Integer gyyCoverNum;
    @Column(name = "house_cover_num")
    private Integer houseCoverNum;
    @Column(name = "busi_cover_num")
    private Integer busiCoverNum;
    @Column(name = "school_custom_num")
    private Integer schoolCustomNum;
    @Column(name = "gyy_custom_num")
    private Integer gyyCustomNum;
    @Column(name = "house_custom_num")
    private Integer houseCustomNum;
    @Column(name = "busi_custom_num")
    private Integer busiCustomNum;
    @Column(name = "school_dev_num")
    private Integer schoolDevNum;
    @Column(name = "gyy_dev_num")
    private Integer gyyDevNum;
    @Column(name = "house_dev_num")
    private Integer houseDevNum;
    @Column(name = "busi_dev_num")
    private Integer busiDevNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }


    public String getResouresType() {
        return resouresType;
    }

    public void setResouresType(String resouresType) {
        this.resouresType = resouresType;
    }


    public Integer getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(Integer schoolNum) {
        this.schoolNum = schoolNum;
    }


    public Integer getGyyNum() {
        return gyyNum;
    }

    public void setGyyNum(Integer gyyNum) {
        this.gyyNum = gyyNum;
    }


    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }


    public Integer getBusiNum() {
        return busiNum;
    }

    public void setBusiNum(Integer busiNum) {
        this.busiNum = busiNum;
    }


    public Integer getSchoolUserNum() {
        return schoolUserNum;
    }

    public void setSchoolUserNum(Integer schoolUserNum) {
        this.schoolUserNum = schoolUserNum;
    }


    public Integer getGyyUserNum() {
        return gyyUserNum;
    }

    public void setGyyUserNum(Integer gyyUserNum) {
        this.gyyUserNum = gyyUserNum;
    }


    public Integer getHouseUserNum() {
        return houseUserNum;
    }

    public void setHouseUserNum(Integer houseUserNum) {
        this.houseUserNum = houseUserNum;
    }


    public Integer getBusiUserNum() {
        return busiUserNum;
    }

    public void setBusiUserNum(Integer busiUserNum) {
        this.busiUserNum = busiUserNum;
    }


    public String getExtendColumn() {
        return extendColumn;
    }

    public void setExtendColumn(String extendColumn) {
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


    public Integer getSchoolCoverNum() {
        return schoolCoverNum;
    }

    public void setSchoolCoverNum(Integer schoolCoverNum) {
        this.schoolCoverNum = schoolCoverNum;
    }


    public Integer getGyyCoverNum() {
        return gyyCoverNum;
    }

    public void setGyyCoverNum(Integer gyyCoverNum) {
        this.gyyCoverNum = gyyCoverNum;
    }


    public Integer getHouseCoverNum() {
        return houseCoverNum;
    }

    public void setHouseCoverNum(Integer houseCoverNum) {
        this.houseCoverNum = houseCoverNum;
    }


    public Integer getBusiCoverNum() {
        return busiCoverNum;
    }

    public void setBusiCoverNum(Integer busiCoverNum) {
        this.busiCoverNum = busiCoverNum;
    }


    public Integer getSchoolCustomNum() {
        return schoolCustomNum;
    }

    public void setSchoolCustomNum(Integer schoolCustomNum) {
        this.schoolCustomNum = schoolCustomNum;
    }


    public Integer getGyyCustomNum() {
        return gyyCustomNum;
    }

    public void setGyyCustomNum(Integer gyyCustomNum) {
        this.gyyCustomNum = gyyCustomNum;
    }


    public Integer getHouseCustomNum() {
        return houseCustomNum;
    }

    public void setHouseCustomNum(Integer houseCustomNum) {
        this.houseCustomNum = houseCustomNum;
    }


    public Integer getBusiCustomNum() {
        return busiCustomNum;
    }

    public void setBusiCustomNum(Integer busiCustomNum) {
        this.busiCustomNum = busiCustomNum;
    }


    public Integer getSchoolDevNum() {
        return schoolDevNum;
    }

    public void setSchoolDevNum(Integer schoolDevNum) {
        this.schoolDevNum = schoolDevNum;
    }


    public Integer getGyyDevNum() {
        return gyyDevNum;
    }

    public void setGyyDevNum(Integer gyyDevNum) {
        this.gyyDevNum = gyyDevNum;
    }


    public Integer getHouseDevNum() {
        return houseDevNum;
    }

    public void setHouseDevNum(Integer houseDevNum) {
        this.houseDevNum = houseDevNum;
    }


    public Integer getBusiDevNum() {
        return busiDevNum;
    }

    public void setBusiDevNum(Integer busiDevNum) {
        this.busiDevNum = busiDevNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpxResourceCollectD that = (JpxResourceCollectD) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(gridCode, that.gridCode) &&
                Objects.equals(resouresType, that.resouresType) &&
                Objects.equals(schoolNum, that.schoolNum) &&
                Objects.equals(gyyNum, that.gyyNum) &&
                Objects.equals(houseNum, that.houseNum) &&
                Objects.equals(busiNum, that.busiNum) &&
                Objects.equals(schoolUserNum, that.schoolUserNum) &&
                Objects.equals(gyyUserNum, that.gyyUserNum) &&
                Objects.equals(houseUserNum, that.houseUserNum) &&
                Objects.equals(busiUserNum, that.busiUserNum) &&
                Objects.equals(extendColumn, that.extendColumn) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(modifyUser, that.modifyUser) &&
                Objects.equals(modifyDate, that.modifyDate) &&
                Objects.equals(schoolCoverNum, that.schoolCoverNum) &&
                Objects.equals(gyyCoverNum, that.gyyCoverNum) &&
                Objects.equals(houseCoverNum, that.houseCoverNum) &&
                Objects.equals(busiCoverNum, that.busiCoverNum) &&
                Objects.equals(schoolCustomNum, that.schoolCustomNum) &&
                Objects.equals(gyyCustomNum, that.gyyCustomNum) &&
                Objects.equals(houseCustomNum, that.houseCustomNum) &&
                Objects.equals(busiCustomNum, that.busiCustomNum) &&
                Objects.equals(schoolDevNum, that.schoolDevNum) &&
                Objects.equals(gyyDevNum, that.gyyDevNum) &&
                Objects.equals(houseDevNum, that.houseDevNum) &&
                Objects.equals(busiDevNum, that.busiDevNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gridCode, resouresType, schoolNum, gyyNum, houseNum, busiNum, schoolUserNum, gyyUserNum, houseUserNum, busiUserNum, extendColumn, createUser, createDate, modifyUser, modifyDate, schoolCoverNum, gyyCoverNum, houseCoverNum, busiCoverNum, schoolCustomNum, gyyCustomNum, houseCustomNum, busiCustomNum, schoolDevNum, gyyDevNum, houseDevNum, busiDevNum);
    }
}
