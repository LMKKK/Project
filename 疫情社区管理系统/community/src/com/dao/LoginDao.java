package com.dao;

import com.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName : LoginDao  //类名
 * @Description : 登录数据访问层  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/14 0014  22:43
 */

public class LoginDao {
    private Connection conn = DBUtils.getConnection();

    /*
    * 管理员登录
    * */
    public boolean adminLogin(String username, String password) {
        boolean flag = false;
        String sql = "select * from admin where username=" + username + " and password=" + password;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            /*
            有查询结果
            * */
            if (rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /*
    * 居民登录
    * */
    public boolean residentLogin(int id,String password){
        boolean flag = false;
        String sql = "select * from resident where id=" + id + " and password=" + password;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            /*如果有数据，说明是正确的*/
            if(rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /*
     * 管理员登录
     * */
    public boolean managerLogin(int id,String password){
        boolean flag = false;
        String sql = "select * from manager where manage_id = "+ id + " and password =  "+ password + ";" ;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            /*如果有数据，说明是正确的*/
            if(rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

}
