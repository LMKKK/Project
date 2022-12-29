package com.model;

/**
 * @ClassName : Resident  //类名
 * @Description : 居民类  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  23:06
 */

public class Resident {

    private int id;
    private String password;
    private int comId;
    private String name;
    private String sex;
    private String tel;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Resident{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", comId=" + comId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
