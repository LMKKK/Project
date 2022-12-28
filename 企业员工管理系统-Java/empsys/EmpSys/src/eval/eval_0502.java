package eval;


import Role_0509.emp_0509;
import sys.ui_0512;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class eval_0502 {

    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;
    static Scanner sc = new Scanner(System.in);

    //静态代码块，链接数据库
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/empsys";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//设置员工的出勤情况
    public static emp_0509 set_eval_0502(ResultSet rs){
        emp_0509 x=new emp_0509();
        try{
            x.set_id_0509(rs.getInt("emp_id_0509"));
            x.set_name_0509(rs.getString("emp_name_0509"));
            x.set_eval_0509(rs.getString("evalu_0502"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return x;
    }

    //打印一个员工的出勤情况
    public  static void print_eval_0502(emp_0509 x){
        System.out.print(x.get_id_0509() + "\t\t\t\t" + x.get_name_0509());
        if (x.get_name_0509().length() > 2)
            System.out.println("\t\t\t" + x.get_eval_0509());
        else
            System.out.println("\t\t\t\t" + x.get_eval_0509());
    }
    //查看所有员工的工作评价情况
    public static void print_0502() {
        try {
            String sql = "SELECT emp_id_0509,emp_name_0509,evalu_0502 FROM emp_0509,evalu_0502 WHERE emp_id_0509=emp_id_0502; ";
            System.out.println("工号\t\t|\t\t姓名\t\t|\t\t评价等级");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                    emp_0509 x=set_eval_0502(rs);
                    print_eval_0502(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //查看员工评价等级
    public static void query_class_0502() {
        try {
            System.out.println("1.查看所有等级为优的员工");
            System.out.println("2.查看所有等级为良的员工");
            System.out.println("3.查看所有等级为差的员工");
            System.out.println("请输入");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    String sql = "SELECT emp_id_0509,emp_name_0509,evalu_0502 FROM emp_0509,evalu_0502 WHERE emp_id_0509=emp_id_0502 and evalu_0502='优'; ";
                    rs = st.executeQuery(sql);
                    System.out.println("工号\t\t|\t\t姓名\t\t|\t\t评价等级");
                    while (rs.next()) {
                        emp_0509 x=set_eval_0502(rs);
                        print_eval_0502(x);
                    }
                    break;
                case 2:
                    String sql2 = "SELECT emp_id_0509,emp_name_0509,evalu_0502 FROM emp_0509,evalu_0502 WHERE emp_id_0509=emp_id_0502 and evalu_0502='良'; ";
                    rs = st.executeQuery(sql2);
                    System.out.println("工号\t\t|\t\t姓名\t\t|\t\t评价等级");
                    while (rs.next()) {
                        emp_0509 x=set_eval_0502(rs);
                        print_eval_0502(x);
                    }
                    break;
                case 3:
                    String sql3 = "SELECT emp_id_0509,emp_name_0509,evalu_0502 FROM emp_0509,evalu_0502 WHERE emp_id_0509=emp_id_0502 and evalu_0502='差'; ";
                    rs = st.executeQuery(sql3);
                    System.out.println("工号\t\t|\t\t姓名\t\t|\t\t评价等级");
                    while (rs.next()) {
                        emp_0509 x=set_eval_0502(rs);
                        print_eval_0502(x);
                    }
                    break;
                case 4:
                    System.out.println("没有这个选项，别瞎按");
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询员工的工作评价情况
    public static void query_0502(int key) {
        try {
            String sql = "SELECT emp_id_0509,emp_name_0509,evalu_0502 FROM emp_0509,evalu_0502 WHERE emp_id_0509=emp_id_0502 AND emp_id_0509=" + key + " ;";
            rs = st.executeQuery(sql);
            if (!rs.next())
                System.out.println("查无此人,可能离职了，可能还没来");
            else {
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t评价等级");
                emp_0509 x=set_eval_0502(rs);
                print_eval_0502(x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //对员工做出评价
    public static void update_0502() {
        try {
            System.out.println("请输入要做出评价的员工id");
            int key = sc.nextInt();
            String sql = "SELECT emp_id_0509,emp_name_0509,evalu_0502 FROM emp_0509,evalu_0502 WHERE emp_id_0509=emp_id_0502 AND emp_id_0509=" + key + " ;";
            rs = st.executeQuery(sql);
            if (!rs.next())
                System.out.println("查无此人,可能离职了，可能还没来");
            else {
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t评价等级");
                emp_0509 x=set_eval_0502(rs);
                print_eval_0502(x);
                System.out.println("请对这员工做出评价:(优、良、差)");

                String key2 = sc.next();
                String sqlx = "UPDATE evalu_0502 SET evalu_0502='" + key2 + "' WHERE emp_id_0502=" + key + " ;";

                st.executeUpdate(sqlx);
                rs = st.executeQuery(sql);
                rs.next();
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t评价等级");
                emp_0509 y=set_eval_0502(rs);
                print_eval_0502(y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//评价管理模块汇总
    public static void garther_0502() {
        try {
            int key;
            while(true){
                ui_0512.evalu_ui_0502();
                System.out.println("请输入");
                key=sc.nextInt();
                switch (key){
                    case 1:
                        print_0502();
                        break;
                    case 2:
                        System.out.println("请输入要查询员工的ID");
                        int x = sc.nextInt();
                        query_0502(x);
                        break;
                    case 3:
                        query_class_0502();
                        break;
                    case 4:
                        update_0502();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("看清楚喽！没有这个选项，别瞎按");
                        break;
                }
                if(key==5) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws  Exception{
        try{
            garther_0502();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs!=null)
                rs.close();
            if(st!=null)
                st.close();
            if(conn!=null)
                conn.close();
        }
    }
}
