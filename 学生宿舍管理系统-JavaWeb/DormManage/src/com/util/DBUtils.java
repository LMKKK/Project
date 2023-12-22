package com.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {

    private static DataSource ds = null;

    /*
    静态代码块实例化数据库连接池对象
    * */
    static {
        ds = new ComboPooledDataSource("dorm");
    }

    public Connection getCon() throws Exception {
//		利用c3po获取数据库连接对象
        Connection conn = null;
        conn = ds.getConnection();
        return conn;
    }

    //	释放连接
    public void closeCon(Connection con) throws Exception {
        if (con != null) {
            con.close();
        }
    }

    /**
     * 判断数据库连接是否失效
     * @param con
     * @return
     */
    public static boolean isValidCon(Connection con) {
        try {
            if (con == null || con.isClosed()) {
                throw new RuntimeException("数据库连接已失效，请刷新或重新登录");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
