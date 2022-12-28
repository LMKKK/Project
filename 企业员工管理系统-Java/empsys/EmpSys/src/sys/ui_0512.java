package sys;

public class ui_0512 {
    //登录页面
    public static void login_ui_0512() {
        System.out.println("*******************企业人事管理系统************************");
        System.out.println("1.管理员登录");
        System.out.println("2.员工登录");
        System.out.println("3.退出");
        System.out.println("请输入:");
    }



    //管理员页面
    public static void manag_ui_0512() {
        System.out.println("******************管理员页面******************************");
        System.out.println("1.员工基本信息管理");
        System.out.println("2.员工工资管理");
        System.out.println("3.员工考勤管理");
        System.out.println("4.员工评价管理");
        System.out.println("5.退出");

    }

    //员工页面
    public static void emp_ui_0512() {
        System.out.println("******************员工界面**********************************");
        System.out.println("1.查看自己的信息");
        System.out.println("2.修改自己的信息");
        System.out.println("3.查看自己的工资情况");
        System.out.println("4.考勤情况");
        System.out.println("5.工作评价情况");
        System.out.println("6.修改密码");
        System.out.println("7.返回");
        System.out.println("请输入：");
    }
    //工资管理模块页面
    public static void salary_ui_0524() {
        System.out.println("***********工资管理页面*********");
        System.out.println("1.所有员工的工资");
        System.out.println("2.查询员工工资");
        System.out.println("3.修改员工工资");
        System.out.println("4.查看部门工资");
        System.out.println("5.返回");
    }

    //界面输出
    public static void info_ui_0509() {
        System.out.println("*******************员工基本信息管理************************");
        System.out.println("1.查看基本员工基本信息");
        System.out.println("2.查找员工");
        System.out.println("3.修改员工信息");
        System.out.println("4.删除员工");
        System.out.println("5.添加员工信息");
        System.out.println("6.查看部门信息");
        System.out.println("7.修改员工密码");
        System.out.println("8.返回");
    }
    //评价管理页面
    public static void evalu_ui_0502() {
        System.out.println("***********评价管理页面*********");
        System.out.println("1.查看所有员工的评价");
        System.out.println("2.查询员工评价");
        System.out.println("3.查看员工级别分类");
        System.out.println("4.对员工做出评价");
        System.out.println("5.返回");
    }
    //考勤管理页面
    public static void att_ui_0517() {
        System.out.println("***********考勤管理页面*********");
        System.out.println("1.查看所有员工的考勤");
        System.out.println("2.查询员工的考勤情况");
        System.out.println("3.添加员工缺勤记录");
        System.out.println("4.删除员工缺勤记录");
        System.out.println("5.查看部门平均缺勤情况");
        System.out.println("6.返回");
    }
}
