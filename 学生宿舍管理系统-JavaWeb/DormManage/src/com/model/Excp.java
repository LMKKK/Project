package com.model;

public class Excp {


    private int excpId;
    private String stuNum;
    private int dormBuildId;
    private String dormBuildName;
    private String tel;
    private String date;
    private String startDate;
    private String endDate;
    private String detail;
    private String imgUrl;
    private String state;

    @Override
    public String toString() {
        return "Excp{" +
                "excpId=" + excpId +
                ", stuNum='" + stuNum + '\'' +
                ", dormBuildId=" + dormBuildId +
                ", dormBuildName='" + dormBuildName + '\'' +
                ", tel='" + tel + '\'' +
                ", date='" + date + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", detail='" + detail + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Excp() {

    }

    public Excp(String stuNum, String date) {
        this.stuNum = stuNum;
        this.date = date;
    }

    public int getExcpId() {
        return excpId;
    }

    public void setExcpId(int excpId) {
        this.excpId = excpId;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public int getDormBuildId() {
        return dormBuildId;
    }

    public void setDormBuildId(int dormBuildId) {
        this.dormBuildId = dormBuildId;
    }

    public String getDormBuildName() {
        return dormBuildName;
    }

    public void setDormBuildName(String dormBuildName) {
        this.dormBuildName = dormBuildName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
