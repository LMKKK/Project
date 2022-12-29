package com.model;

/**
 * @ClassName : Manager  //类名
 * @Description : 社区负责人类  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  23:10
 */

public class Manager {

    private int id;
    private String password;
    private String name;
    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
