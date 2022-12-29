package com.model;

/**
 * @ClassName : ApplyIn  //类名
 * @Description : 返回社区申请表  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  23:11
 */

public class ApplyIn {
    private int idx;
    private int id;
    private String name;
    private int comId;
    private String comName;
    private String sex;
    private String tel;
    private String address;
    private String nowTime;
    private String arrTime;
    private String start ;
    private String end;
    private String state;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ApplyIn{" +
                "idx=" + idx +
                ", id=" + id +
                ", nowTime='" + nowTime + '\'' +
                ", arrTime='" + arrTime + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", state='" + state + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
