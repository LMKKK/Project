package salary;


import sys.ui_0512;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Scanner;


public class salary_0524 {
    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;
    static Scanner sc=new Scanner(System.in);
    static {
//        System.out.println("静态代码块执行了");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/empsys";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();
//            rs = st.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //打印所有员工工资
    public static void print_salary_0524() throws Exception{
        String sql="select emp_id_0524,emp_name_0509,salary_0524 from emp_0509,salary_0524 where emp_id_0524=emp_id_0509;";
        System.out.println("工号\t\t|\t\t姓名\t\t|\t\t月薪");
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.print(rs.getInt("emp_id_0524")+"\t\t\t\t"+rs.getString("emp_name_0509"));
                if(rs.getString("emp_name_0509").length()>2)
                    System.out.println("\t\t\t"+rs.getInt("salary_0524"));
                else
                    System.out.println("\t\t\t\t"+rs.getInt("salary_0524"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //查看某个部门
    public static void dept_query_0524(){
        try{
            System.out.println("请输入要查看的部门");
            int dept=sc.nextInt();

            String sql="SELECT d.dept_id_0509,dept_name_0509,emp_id_0509,emp_name_0509,salary_0524\n" +
                    "FROM emp_0509 e,salary_0524 s,dept_0509 d\n" +
                    "WHERE emp_id_0524=emp_id_0509 AND e.dept_id_0509=d.dept_id_0509\n" +
                    "AND d.dept_id_0509="+dept+";";
            rs=st.executeQuery(sql);
            System.out.println("部门id\t\t|\t\t部门名\t\t|\t\t员工id\t\t|\t\t员工名\t\t|\t薪资");
            while (rs.next()){
                System.out.print(rs.getInt("dept_id_0509")+"\t\t\t\t\t"+rs.getString("dept_name_0509")+"\t\t\t\t"+rs.getInt("emp_id_0509")+"\t\t\t\t\t");
                if(rs.getString("emp_name_0509").length()>2)
                    System.out.print(rs.getString("emp_name_0509")+"\t\t\t");
                else
                    System.out.print(rs.getString("emp_name_0509")+"\t\t\t\t");
                System.out.println(rs.getInt("salary_0524"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //查看部门平均工资
    public static void dept_avg_0524(){
        try {
            String sql="select * from salary_view_0524";
            rs=st.executeQuery(sql);
            System.out.println("部门id\t\t|\t\t部门名\t\t|\t\t平均工资");
            while (rs.next()){
                System.out.println(rs.getInt("dept_id_0509")+"\t\t\t\t\t"+rs.getString("dept_name_0509")+"\t\t\t\t"+rs.getInt("avg(salary_0524)"));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//查看部门工资情况
    public static void dept_salary_0524(){
        System.out.println("1.查看部门员工工资信息");
        System.out.println("2.查看部门平均工资");
        System.out.println("请输入：");
        int key=sc.nextInt();
        switch (key){
            case 1:
                dept_query_0524();
                break;
            case 2:
                dept_avg_0524();
                break;
            default:
                System.out.println("没有这个选项，别瞎按！");
        }
    }
    //查询员工工资
    public  static void query_0524(int key){
        try{
            String sql="select emp_id_0509,emp_name_0509,salary_0524 from salary_0524,emp_0509 where emp_id_0524=emp_id_0509 and emp_id_0509="+key;
            rs=st.executeQuery(sql);
            if(!rs.next()) {
                System.out.println("查无此人，或许已经被开除了！！");
            }
            else {
                rs.previous();
                while (rs.next()){
                    System.out.println("工号\t\t|\t\t姓名\t\t|\t\t月薪");
                    System.out.print(rs.getInt("emp_id_0509")+"\t\t\t\t"+rs.getString("emp_name_0509"));
                if(rs.getString("emp_name_0509").length()>2)
                    System.out.println("\t\t\t"+rs.getInt("salary_0524"));
                else
                    System.out.println("\t\t\t\t"+rs.getInt("salary_0524"));
            }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //修改员工工资
    public static void update_salary_0524() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入需要修改的员工id和修改完的工资");
        int key1 = sc.nextInt();
        int key2 = sc.nextInt();
        String sqlx = "update salary_0524 set salary_0524=" + key2 + " where emp_id_0524=" + key1;
        try {
            if (st.executeUpdate(sqlx) == 1) {
                System.out.println("修改成功");
            } else {
                System.out.println("查无此人,修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //功能汇总
    public static void garther_0524(){
        while(true) {
            ui_0512.salary_ui_0524();
            Scanner sc = new Scanner(System.in);
            int key = sc.nextInt();
            try {
                switch (key) {
                    case 1:
                        print_salary_0524();
                        break;
                    case 2:
                        System.out.println("请输入需要查询的员工ID");
                        int x=sc.nextInt();
                        query_0524(x);
                        break;
                    case 3:
                        update_salary_0524();
                        break;
                    case 4:
                        dept_salary_0524();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("瞪大你的眼睛看清楚喽！没有这个选项，别瞎按");
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if(key==5)
                break;
            //清屏
            for(int i=0;i<5;i++)
                System.out.println();
        }
    }
    public static void main(String[] args) throws Exception {
        try{
            garther_0524();
//            dept_avg_0524();
//                dept_query_0524();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(rs!=null)
            rs.close();
        if(st!=null)
            st.close();
        if(conn!=null)
            conn.close();
    }
}
