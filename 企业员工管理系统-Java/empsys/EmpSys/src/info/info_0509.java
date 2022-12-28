package info;


import Role_0509.emp_0509;
import sys.ui_0512;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class info_0509 {
    static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;
    static Scanner sc = new Scanner(System.in);

    //静态代码块链接数据库
    static {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/empsys";
            String username = "root";
            String password = "root";
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//设置员工的的基本信息
public static emp_0509 set_emp_0509(ResultSet rs){
        emp_0509 x=new emp_0509();
        try{
            if(rs.next()){
                x.set_id_0509(rs.getInt("emp_id_0509"));
                x.set_name_0509(rs.getString("emp_name_0509"));
                x.set_sex_0509(rs.getString("sex_0509"));
                x.set_age_0509(rs.getInt("emp_age_0509"));
                x.set_phone_0509(rs.getInt("phone_0509"));
                x.set_dept_0509(rs.getInt("dept_id_0509"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return x;
}
//打印一个员工的基本信息
    public static void print_emp_0509(emp_0509 x){

        System.out.print(x.get_id_0509() + "\t\t\t\t\t");
        if (x.get_name_0509().length() > 2)
            System.out.print(x.get_name_0509() + "\t");
        else
            System.out.print(x.get_name_0509() + "\t\t");
        System.out.print("\t\t" + x.get_sex_0509() + "\t\t\t\t");
        System.out.print(x.get_age_0509() + "\t\t\t\t");
        System.out.print(x.get_phone_0509() + "\t\t\t");
        System.out.print(x.get_dept_0509() + "\t\t");
        System.out.println();
    }

//    输出员工信息
    public static void print_one_0509(ResultSet rsx) {
        try {
            rs = rsx;
            System.out.println("员工ID\t\t|\t\t姓名\t\t|\t\t性别\t\t|\t\t年龄\t\t|\t\t电话\t\t|\t\t部门ID");
            while (rs.next()) {
//                emp_0509 y=set_emp_0509(rs);
//                print_emp_0509(y);
                System.out.print(rs.getInt("emp_id_0509") + "\t\t\t\t\t");

                if (rs.getString("emp_name_0509").length() > 2)
                    System.out.print(rs.getString("emp_name_0509") + "\t");
                else
                    System.out.print(rs.getString("emp_name_0509") + "\t\t");
                System.out.print("\t\t" + rs.getString("sex_0509") + "\t\t\t\t");
                System.out.print(rs.getInt("emp_age_0509") + "\t\t\t\t");
                System.out.print(rs.getInt("phone_0509") + "\t\t\t");
                System.out.print(rs.getInt("dept_id_0509") + "\t\t");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查看所有员工信息
    public static void print_all_0509() {
        try {
            String sql = "select * from emp_0509;";
            rs = st.executeQuery(sql);
            print_one_0509(rs);
//            System.out.println("员工ID\t\t|\t\t姓名\t\t|\t\t性别\t\t|\t\t年龄\t\t|\t\t电话\t\t|\t\t部门ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查找某个员工
    public static int query_0509(int key) {
        try {

            String sql = "select * from emp_0509 where emp_id_0509=" + key + ";";
            rs = st.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("查无此人，可能已经被开除了，也可能还没来到这个公司,哈哈");
                return 0;
            } else {
                rs.previous();
                print_one_0509(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    //修改员工信息
    public static void update_0509(int key) {
        try {
            String sql = "select * from emp_0509 where emp_id_0509=" + key + ";";
            rs = st.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("查无此人，可能已经被开除了，也可能还没来到这个公司,哈哈");
                return;
            } else {
                rs.previous();
                print_one_0509(rs);
            }
            System.out.println("请依次输入一下新的信息");
            System.out.println("姓名\t\t|性别\t\t|年龄\t\t|电话\t\t|部门ID");
            String name = sc.next();
            String sex = sc.next();
            int age = sc.nextInt();
            int phone = sc.nextInt();
            int dept = sc.nextInt();
            String sqlx = "update emp_0509 set emp_name_0509='" + name + "',sex_0509='" + sex + "',emp_age_0509=" + age + ",phone_0509=" + phone + ",dept_id_0509=" + dept + " where emp_id_0509=" + key + ";";
            st.executeUpdate(sqlx);
            rs = st.executeQuery(sql);
            print_one_0509(rs);
//            emp_0509 y=set_emp_0509(rs);
//            print_emp_0509(y);
            System.out.println();
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除员工
    public static void delete_0509() {
        try {
            System.out.println("请输入需要删除的员工ID");
            int key = sc.nextInt();
            String sql = "select * from emp_0509 where emp_id_0509=" + key + ";";
            rs = st.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("查无此人，可能已经被开除了，也可能还没来到这个公司,哈哈");
                return;
            } else {
                rs.previous();
                print_one_0509(rs);
//                print_one_0509(rs);
                System.out.println("你确定删除此员工吗?");
                System.out.println("此员工的所有信息将消失！！");
                System.out.println("此员工的所有信息将消失！！");
                System.out.println("此员工的所有信息将消失！！");
                System.out.println("(重要的事情说三遍)");
                String scx = sc.next();
                if (scx.equals("确定")) {
                    String sqlx = "delete from emp_0509 where emp_id_0509=" + key + ";";
                    st.executeUpdate(sqlx);
                    System.out.println("删除成功！已移除此员工的所有信息");
                } else {
                    System.out.println("删除失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //添加员工
    public static void add_0509() {
        try {
            System.out.println("请输入员工的id、姓名、性别、年龄、电话、部门号");
            String name, sex;
            int id, age, phone, dept;
            id = sc.nextInt();
            name = sc.next();
            sex = sc.next();
            age = sc.nextInt();
            phone = sc.nextInt();
            dept = sc.nextInt();
            String sql = "insert into emp_0509 values(" + id + ",'" + name + "','" + sex + "'," + age + "," + phone + "," + dept + ");";
            st.executeUpdate(sql);
            String sql1 = "insert into att_0517 values(" + id + ",0,'是'," + dept + ");";
            String sql2 = "insert into evalu_0502 values(" + id + ",'良');";
            String sql3 = "insert into salary_0524 values(" + id + ",0)";
            String sql4 = "insert into user_0512 values(" + id + ",123456);";
            System.out.println("插入成功");
            //同步其他的表
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);
            st.executeUpdate(sql4);
            System.out.println();
            String sqlx = "select * from emp_0509 where emp_id_0509=" + id + ";";
            rs = st.executeQuery(sqlx);
            System.out.println("此员工基本信息如下所示");
            print_one_0509(rs);
//            emp_0509 y=set_emp_0509(rs);
//            print_emp_0509(y);
        } catch (Exception e) {
            String ex = e.getMessage().substring(0, 9);
            if (ex.equals("Duplicate")) {
                System.out.println("这个id已经存在了,咱们换一个id吧！");
            } else {
                System.out.println("捕获的的异常信息是" + e.getMessage());
            }
        }
    }

    //员工基本信息管理汇总
    public static void garther_0509() {
        try {

            while (true) {
                ui_0512.info_ui_0509();
                int key = sc.nextInt();
                switch (key) {
                    case 1:
                        print_all_0509();
                        break;
                    case 2:
                        System.out.println("请输入需要查找的员工ID");
                        int keyx = sc.nextInt();
                        query_0509(keyx);
                        break;
                    case 3:
                        System.out.println("请输入需要查找的员工ID");
                        int keyy = sc.nextInt();
                        update_0509(keyy);
                        break;
                    case 4:
                        delete_0509();
                        break;
                    case 5:
                        add_0509();
                        break;
                    case 6:
                        dept_query_0509();
                        break;
                    case 7:
                        System.out.println("请输入要修改的id");
                        int x=sc.nextInt();
                        update_password_0509(x);
                        break;
                    case 8:
                        break;
                    default:
                        System.out.println("看清楚喽！没有这个选项,别瞎按");
                        break;
                }
                if (key == 8)
                    break;
                for (int i = 0; i < 5; i++)
                    System.out.println();
            }
        } catch (Exception e) {
            System.out.println("捕获到的异常信息" + e.getMessage());
        }
    }

    //查看部门员工的基本信息
    public static void dept_query_0509() {
        try {
            System.out.println("请输入部门id");
            int dept = sc.nextInt();
            String sql = "SELECT e.dept_id_0509,dept_name_0509,emp_id_0509,emp_name_0509,sex_0509,emp_age_0509,phone_0509 FROM emp_0509 e,dept_0509 d WHERE e.dept_id_0509=d.dept_id_0509 AND e.dept_id_0509=" + dept + ";";
            rs = st.executeQuery(sql);
            print_dept_0509(rs);

        } catch (Exception e) {
            System.out.println("捕获到的异常信息如下:");
            System.out.println(e.getMessage());
        }
    }

    //打印部门信息
    public static void print_dept_0509(ResultSet rs){
            try{
                System.out.println("部门id\t\t|\t\t部门名\t\t|\t\t员工id\t\t|\t\t姓名\t\t|\t\t性别\t\t|\t\t年龄\t\t|\t\t电话\t\t|");
                while(rs.next()){
                    System.out.print(rs.getInt("dept_id_0509") + "\t\t\t\t\t"+rs.getString("dept_name_0509")+"\t\t\t\t"+rs.getInt("emp_id_0509")+"\t\t\t\t\t");

                    if (rs.getString("emp_name_0509").length() > 2)
                        System.out.print(rs.getString("emp_name_0509") + "\t");
                    else
                        System.out.print(rs.getString("emp_name_0509") + "\t\t");
                    System.out.print("\t\t" + rs.getString("sex_0509") + "\t\t\t\t");
                    System.out.print(rs.getInt("emp_age_0509") + "\t\t\t\t");
                    System.out.println(rs.getInt("phone_0509") + "\t\t\t");
                }

            }catch (Exception e){
                System.out.println("捕获到的异常信息如下所示");
                System.out.println(e.getMessage());
            }
    }
    //修改密码
    public static void update_password_0509(int key){
        try {
            String sql="select * from user_0512 where user_id_0512="+key+";";
            rs=st.executeQuery(sql);
            if(!rs.next()){
                System.out.println("无此id");
                return;
            }
            else {
                while (true){
                    System.out.println("请输入修改后的密码(只能包含数字)");
                    int p1=sc.nextInt();
                    System.out.println("请再次输入修改后的密码");
                    int p2=sc.nextInt();
                    if(p1==p2){
                        String sqlx="update user_0512 set password_0512="+p2+" where user_id_0512="+key+";";
                        st.executeUpdate(sqlx);
                        System.out.println("修改成功");
                        break;
                    }else{
                        System.out.println("什么情况？两次的密码不一致！");
                        System.out.println("别慌，重新来");
                        System.out.println();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        try {
            garther_0509();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
