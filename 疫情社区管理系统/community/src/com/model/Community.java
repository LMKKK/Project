package com.model;

/**
 * @ClassName : Community  //类名
 * @Description : 社区类  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  23:04
 */

public class Community {
    private int comId ;
    private String comName;
    private int manageId;
    private String managerName;
    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
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

    public int getManageId() {
        return manageId;
    }

    public void setManageId(int manageId) {
        this.manageId = manageId;
    }

    @Override
    public String toString() {
        return "Community{" +
                "comId=" + comId +
                ", comName='" + comName + '\'' +
                ", manageId=" + manageId +
                ", managerName='" + managerName + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
