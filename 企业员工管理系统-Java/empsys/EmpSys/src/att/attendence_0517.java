package att;

import Role_0509.emp_0509;
import sys.ui_0512;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class attendence_0517 {
    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;
    static Scanner sc = new Scanner(System.in);

    //静态代码块连接数据库
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

    //查看所有员工的出勤情况
    public static void print_0517() {
        String sql = "select emp_id_0509,emp_name_0509,emp_time_0517,emp_case_0517 from emp_0509,att_0517 where emp_id_0509=emp_id_0517 ;";
        try {
            System.out.println("工号\t\t|\t\t姓名\t\t|\t\t缺勤次数\t\t|\t\t是否满勤\t\t");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                emp_0509 y=set_user_0517(rs);
                print_one(y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //设置员工的考勤情况
    public static emp_0509 set_user_0517(ResultSet rs) {
        emp_0509 e = new emp_0509();
        try {
            e.set_id_0509(rs.getInt("emp_id_0509"));
            e.set_name_0509(rs.getString("emp_name_0509"));
            e.set_att_time_0509(rs.getInt("emp_time_0517"));
            e.set_att_case(rs.getString("emp_case_0517"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return e;
    }

    //打印每个员工的考勤情况
    public static void print_one(emp_0509 x) {
        System.out.print(x.get_id_0509() + "\t\t\t\t" + x.get_name_0509());
        if (x.get_name_0509().length() > 2)
            System.out.print("\t\t\t" + x.getAtt_time());
        else
            System.out.print("\t\t\t\t" + x.getAtt_time());
        System.out.println("\t\t\t\t\t" + x.getAtt_case());
    }

    //查询员工的考勤情况
    public static void query_0517(int key) {
        try {
            String sql = "SELECT emp_id_0509,emp_name_0509,emp_time_0517,emp_case_0517,dept_id_0517 FROM emp_0509,att_0517 WHERE emp_id_0509=emp_id_0517 AND emp_id_0517=" + key + " ;";
            rs = st.executeQuery(sql);
            if(rs.next()) {
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t缺勤次数\t\t|\t\t是否满勤\t\t");
                emp_0509 y=set_user_0517(rs);
                print_one(y);
            }else{
                System.out.println("根本没有这个人，好吧？");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加员工缺勤记录
    public static void add_lack_0517() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入员工ID");
        int key = sc.nextInt();
        String sql = "SELECT emp_id_0509,emp_name_0509,emp_time_0517,emp_case_0517,dept_id_0517 FROM emp_0509,att_0517 WHERE emp_id_0509=emp_id_0517 AND emp_id_0517=" + key + " ;";
        try {
            rs = st.executeQuery(sql);
            if(rs.next()) {
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t缺勤次数\t\t|\t\t是否满勤\t\t");
                emp_0509 y=set_user_0517(rs);
                print_one(y);
            }else {
                System.out.println("根本没有这个人，好吧？");
                return;
            }
            System.out.println("请输入添加缺勤次数");
            int key2 = sc.nextInt();
            if (key2 >= 0) {
                String sql1 = "update att_0517 set emp_time_0517=emp_time_0517+" + key2 + " ,emp_case_0517='否' where emp_id_0517=" + key;
                st.executeUpdate(sql1);
                System.out.println("添加成功");
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t缺勤次数\t\t|\t\t是否满勤\t\t");
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    emp_0509 y=set_user_0517(rs);
                    print_one(y);
                }
            } else {
                System.out.println("添加失败(添加一个负数，玩呢？别闹)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除员工缺勤记录
    public static void delete_0517() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入员工ID");
        int key = sc.nextInt();
        String sql = "SELECT emp_id_0509,emp_name_0509,emp_time_0517,emp_case_0517,dept_id_0517 FROM emp_0509,att_0517 WHERE emp_id_0509=emp_id_0517 AND emp_id_0517=" + key + " ;";
        try {
            System.out.println("工号\t\t|\t\t姓名\t\t|\t\t缺勤次数\t\t|\t\t是否满勤\t\t");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                emp_0509 y=set_user_0517(rs);
                print_one(y);
            }
            System.out.println("请输入删除缺勤的次数");
            rs = st.executeQuery(sql);
            rs.next();
            int key2 = sc.nextInt();
            if (rs.getInt("emp_time_0517") - key2 > 0) {
                String sql1 = "update att_0517 set emp_time_0517=emp_time_0517-" + key2 + " where emp_id_0517=" + key;
                st.executeUpdate(sql1);
                System.out.println("删除成功");
                String sql2 = "SELECT emp_id_0509,emp_name_0509,emp_time_0517,emp_case_0517,dept_id_0517 FROM emp_0509,att_0517 WHERE emp_id_0509=emp_id_0517 AND emp_id_0517=" + key + " ;";
                rs = st.executeQuery(sql2);
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t缺勤次数\t\t|\t\t是否满勤\t\t");
                while (rs.next()) {
                    emp_0509 y=set_user_0517(rs);
                    print_one(y);
                }
            } else {
                String sql3 = "update att_0517 set emp_time_0517=0,emp_case_0517='是' where emp_id_0517=" + key;
                st.executeUpdate(sql3);
                System.out.println("删除成功");
                String sql4 = "SELECT emp_id_0509,emp_name_0509,emp_time_0517,emp_case_0517,dept_id_0517 FROM emp_0509,att_0517 WHERE emp_id_0509=emp_id_0517 AND emp_id_0517=" + key + " ;";
                rs = st.executeQuery(sql4);
                System.out.println("工号\t\t|\t\t姓名\t\t|\t\t缺勤次数\t\t|\t\t是否满勤\t\t");
                while (rs.next()) {
                    emp_0509 y=set_user_0517(rs);
                    print_one(y);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //部门平均缺勤情况
    public static void dept_lack_0517() {
        String sql = "SELECT dept_id_0517,dept_name_0509,AVG(emp_time_0517) FROM att_0517,dept_0509 WHERE dept_id_0517=dept_id_0509 GROUP BY dept_id_0517;";
        try {
            rs = st.executeQuery(sql);
            System.out.println("部门号\t\t|\t\t部门名\t\t|\t\t平均缺勤次数");
            while (rs.next()) {
                System.out.println(rs.getInt("dept_id_0517") + "\t\t\t\t\t" + rs.getString("dept_name_0509") + "\t\t\t\t" + rs.getInt("AVG(emp_time_0517)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  考勤管理模块功能汇总
    public static void garther_0517() throws Exception {
        try {
            while (true) {
                ui_0512.att_ui_0517();
                int key = sc.nextInt();
                switch (key) {
                    case 1:
                        print_0517();
                        break;
                    case 2:
                        System.out.println("请输入与员工ID");
                        int x = sc.nextInt();
                        query_0517(x);
                        break;
                    case 3:
                        add_lack_0517();
                        break;
                    case 4:
                        delete_0517();
                        break;
                    case 5:
                        dept_lack_0517();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("瞪大你的眼睛看清楚了！没有这个选项，别瞎按");
                }
                if (key == 6)
                    break;
                for (int i = 0; i < 5; i++) {
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (conn != null)
                conn.close();
        }
    }

    public static void main(String[] args) {
        try {
            garther_0517();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
