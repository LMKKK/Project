package com.model;

/**
 * @ClassName : Count  //类名
 * @Description : 出入记录  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/17 0017  21:08
 */

public class Count509 {

    private int recordId;
    private String stuNum;
    private String stuName;
    private String date;
    private int dormBuildId;
    private String dormBuildName;
    private String dormName;
    private String state;
    private String detail;
    private String startDate;
    private String endDate;


    public Count509() {
    }

    public Count509(String stuNum, String date) {
        this.stuNum = stuNum;
        this.date = date;
    }

    public Count509(String studentNumber, String date, String detail) {
        this.stuNum = studentNumber;
        this.date = date;
        this.detail = detail;
    }

    public String getDormBuildName() {
        return dormBuildName;
    }

    public void setDormBuildName(String dormBuildName) {
        this.dormBuildName = dormBuildName;
    }

    public String getDormName() {
        return dormName;
    }

    public void setDormName(String dormName) {
        this.dormName = dormName;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDormBuildId() {
        return dormBuildId;
    }

    public void setDormBuildId(int dormBuildId) {
        this.dormBuildId = dormBuildId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
