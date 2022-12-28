package sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import att.attendence_0517;
import eval.eval_0502;
import info.info_0509;
import salary.salary_0524;

public class system_0512 {
    static Connection conn = null;
    static Statement st = null;
    static ResultSet rs = null;
    static Scanner sc = new Scanner(System.in);
    static int user_id;

    //静态代码块链接数据库
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


    //管理员登录
    public static boolean mang_login_0512() {
        System.out.println("请输入管理员密码");
        int key1 = sc.nextInt();
        if (key1 == 123321) {
            System.out.println("登录成功");
            return true;
        } else {
            System.out.println("密码错误！");
            return false;
        }
    }




    //用户登录
    public static boolean user_login_0512() {
        System.out.println("请输入员工ID");
        int key2 = sc.nextInt();
        user_id = key2;
        System.out.println("请输入密码");
        int key3 = sc.nextInt();
        String sql = "select * from user_0512 where user_id_0512=" + key2;
        try {
            rs = st.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("不存在此员工");
                return false;
            } else {
                if (key3 == rs.getInt("password_0512")) {
                    System.out.println("登录成功");
                    return true;
                } else {
                    System.out.println("密码错误");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("捕获到一下异常信息");
            System.out.println(e.getMessage());
        }
        return true;
    }


//所有功能汇总
    public static void main_0512() {
        try {
            while (true) {
                ui_0512.login_ui_0512();
                int key = sc.nextInt();
                switch (key) {
                    case 1:
                        if (mang_login_0512())
                            mang_fun_0512();
                        break;
                    case 2:
                        if (user_login_0512())
                            user_fun_0512();
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("没这个选项，别瞎按！");
                }
                if (key == 3)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //用户功能
    public static void user_fun_0512() {
        try {
            while (true) {
                ui_0512.emp_ui_0512();
                int key = sc.nextInt();
                switch (key) {
                    case 1:
                        info_0509.query_0509(user_id);
                        break;
                    case 2:
                        info_0509.update_0509(user_id);
                        break;
                    case 3:
                        salary_0524.query_0524(user_id);
                        break;
                    case 4:
                        attendence_0517.query_0517(user_id);
                        break;
                    case 5:
                        eval_0502.query_0502(user_id);
                        break;
                    case 6:
                        info_0509.update_password_0509(user_id);
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("没有这个选项，别瞎按");
                        break;
                }
                if (key == 7)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //管理员功能
    public static void mang_fun_0512() {
        try {
            while (true) {
                ui_0512.manag_ui_0512();
                int key = sc.nextInt();
                switch (key) {
                    case 1:
                        info_0509.garther_0509();
                        break;
                    case 2:
                        salary_0524.garther_0524();
                        break;
                    case 3:
                        attendence_0517.garther_0517();
                        break;
                    case 4:
                        eval_0502.garther_0502();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("瞪大你的眼睛看清楚喽！没有这个选项，别瞎按");
                        break;
                }
                if (key == 5)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        try {
                main_0512();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}