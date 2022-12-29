package com.view;

/**
 * @ClassName : view  //类名
 * @Description : 样式类  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  22:42
 */

public class view {

    public static void firstPage() {
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 居民登录");
        System.out.println("2. 社区负责人登录");
        System.out.println("3. 系统管理员登录");
        System.out.println("4. 退出系统");
        System.out.println("请选择:");
    }

    public static void adminPage() {
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 社区管理");
        System.out.println("2. 社区负责人管理");
        System.out.println("3. 社区人员管理");
        System.out.println("4. 报备管理");
        System.out.println("5. 修改管理员密码");
        System.out.println("6. 退出系统");
        System.out.println("请选择:");
    }


    public static void communityManage() {
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 查看所有的社区");
        System.out.println("2. 查询社区");
        System.out.println("3. 添加社区");
        System.out.println("4. 删除社区");
        System.out.println("5. 返回");
        System.out.println("请输入:");
    }

    public static void queryCommunityPage() {
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 根据社区名称查找");
        System.out.println("2. 根据社区社区id查找");
        System.out.println("3. 根据负责人姓名查找");
        System.out.println("4. 根据负责人id查找");
        System.out.println("5. 返回");
        System.out.println("请输入:");
    }
    public static void managerManagePage(){
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 添加社区负责人");
        System.out.println("2. 查看所有社区负责人");
        System.out.println("3. 查询负责人");
        System.out.println("4. 修改负责人信息");
        System.out.println("5. 删除负责人");
        System.out.println("6. 返回");
        System.out.println("请输入:");
    }

    public static void residentAdminPage(){
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 查看某社区的所有居民");
        System.out.println("2. 查询某个居民");
        System.out.println("3. 修改居民信息");
        System.out.println("4. 删除居民");
        System.out.println("5. 添加居民");
        System.out.println("6. 返回");
    }

    public static void managerPage() {
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 社区人员管理");
        System.out.println("2. 报备审批");
        System.out.println("3. 修改密码");
        System.out.println("4. 退出系统");
        System.out.println("请选择:");
    }

    public static void residentPage() {
        System.out.println("*****社区人员管理系统*****");
        System.out.println("********欢迎你**********");
        System.out.println("1. 修改密码");
        System.out.println("2. 提交返回社区报备");
        System.out.println("3. 提交离开社区报备");
        System.out.println("4. 查看我的报备");
        System.out.println("5.退出系统");
        System.out.println("请选择:");
    }

}
