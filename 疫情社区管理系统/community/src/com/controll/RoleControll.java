package com.controll;

import com.dao.LoginDao;

import java.util.Scanner;

import static com.view.view.*;

/**
 * @ClassName : RoleControll  //类名
 * @Description : 汇总了所有的角色控制  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  23:39
 */

public class RoleControll {
    private static final LoginDao login = new LoginDao();
    private static final Scanner sc = new Scanner(System.in);


    /*
     * 管理员模块汇总
     * */
    public static void adminControll() {

        while (true) {
            System.out.println("请输入管理员账号");
            String username = sc.next();
            System.out.println("请输入管理员密码");
            String password = sc.next();
            if (login.adminLogin(username, password)) {
                break;
            }
            System.out.println("用户名或密码错误，请重新输入");
        }
        AdminControll admin = new AdminControll();

        boolean flag = true;
        while (true) {
            adminPage();
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    admin.community();
                    break;
                case 2:
                    admin.manager();
                    break;
                case 3:
                    admin.resident();
                    break;
                case 4:
                    admin.apply();
                    break;
                case 5:
                    admin.updatePassword();
                    break;
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱按");
                    break;
            }
            if (!admin.isLoginState()) {
                break;
            }
            if (!flag) {
                break;
            }
        }
    }

    /*
     * 社区负责人模块汇总
     * */
    public static void managerControll() {
        int manageId = 0;
        while (true) {
            System.out.println("请输入社区负责人员id");
            manageId = sc.nextInt();
            System.out.println("请输入社区负责人密码");
            String password = sc.next();
            if (login.managerLogin(manageId, password)) {
                break;
            }
            System.out.println("id或密码错误，请重新输入");
        }

        ManagerControll manager = new ManagerControll(manageId);
        boolean flag = true;
        while (true) {

            managerPage();
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    manager.resident();
                    break;
                case 2:
                    manager.apply();
                    break;
                case 3:
                    manager.updatePassword();
                    break;
                case 4:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！");
                    break;
            }
            if(!manager.isLoginState()){
                break   ;
            }
            if(!flag) {
                break;
            }

        }

    }

    /*
     * 居民模块汇总
     * */
    public static void residentControll() {
        int id;
        while (true) {
            System.out.println("请输入居民id");
            id = sc.nextInt();
            System.out.println("请输入居民密码");
            String password = sc.next();
            if (login.residentLogin(id, password)) {
                break;
            }
            System.out.println("id或密码错误，请重新输入");
        }
        ResidentControll resident = new ResidentControll(id);

        while (true) {
            boolean flag = true;
            residentPage();
            int key = sc.nextInt();
            switch (key) {

                case 1:
                    resident.updatePassword();
                    break;
                case 2:
                    resident.submitApplyReturn();
                    break;
                case 3:
                    resident.submitApplyOut();
                    break;
                case 4:
                    resident.getMyApply();
                    break;
                case 5:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项，别乱按");
                    break;
            }
            if (!resident.isLoginState()) {
                break;
            }
            if (!flag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        adminControll();
    }
}
