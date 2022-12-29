package com.utils;

import java.sql.*;

/**
 * @ClassName : DBUtils  //类名
 * @Description : 数据库工具类  //描述
 * @Date: 2022/12/13 0013  22:03
 */

public class DBUtils {

    /*
     * 获取数据库连接对象
     *
     *  */
    public static Connection getConnection() {
        Connection conn = null;
        try {
//        1.加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
//            2. 获取连接
            String url = "jdbc:mysql://localhost:3306/community";
            String username = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /*
     * 释放资源
     * */
    public static void release(Connection conn, Statement stmt, ResultSet rs) {
//        从小到大依次释放
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

}
