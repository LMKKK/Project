package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model.Admin509;
import com.model.DormManager502;
import com.model.Student517;
import com.util.MD5Util;

/*		系统角色的数据访问层操作
 * 		1. 角色登录
 * 		2. 角色修改信息
 * 	    3.
 * */
public class UserDao509 {

    /*			根据方法参数中， Bean的不同类型实现了方法的重载	*/
    public Admin509 Login(Connection con, Admin509 admin509) throws Exception {
        Admin509 resultAdmin509 = null;
        String sql = "select * from t_admin where userName=? and password=?";
        admin509.setPassword(MD5Util.encoderPwdByMD5(admin509.getPassword()));
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, admin509.getUserName());
        pstmt.setString(2, admin509.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultAdmin509 = new Admin509();
            resultAdmin509.setAdminId(rs.getInt("adminId"));
            resultAdmin509.setUserName(rs.getString("userName"));
            resultAdmin509.setPassword(rs.getString("password"));
            resultAdmin509.setName(rs.getString("name"));
            resultAdmin509.setSex(rs.getString("sex"));
            resultAdmin509.setTel(rs.getString("tel"));
        }
        return resultAdmin509;
    }

    public DormManager502 Login(Connection con, DormManager502 dormManager502) throws Exception {
        DormManager502 resultDormManager502 = null;
        dormManager502.setPassword(MD5Util.encoderPwdByMD5(dormManager502.getPassword()));
        String sql = "select * from t_dormmanager where userName=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, dormManager502.getUserName());
        pstmt.setString(2, dormManager502.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultDormManager502 = new DormManager502();
            resultDormManager502.setDormManagerId(rs.getInt("dormManId"));
            resultDormManager502.setUserName(rs.getString("userName"));
            resultDormManager502.setPassword(rs.getString("password"));
            resultDormManager502.setDormBuildId(rs.getInt("dormBuildId"));
            resultDormManager502.setName(rs.getString("name"));
            resultDormManager502.setSex(rs.getString("sex"));
            resultDormManager502.setTel(rs.getString("tel"));
        }
        return resultDormManager502;
    }

    public Student517 Login(Connection con, Student517 student517) throws Exception {
        Student517 resultStudent517 = null;
        student517.setPassword(MD5Util.encoderPwdByMD5(student517.getPassword()));
        String sql = "select * from t_student where stuNum=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, student517.getStuNumber());
        pstmt.setString(2, student517.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultStudent517 = new Student517();
            resultStudent517.setStudentId(rs.getInt("studentId"));
            resultStudent517.setStuNumber(rs.getString("stuNum"));
            resultStudent517.setPassword(rs.getString("password"));
            int dormBuildId = rs.getInt("dormBuildId");
            resultStudent517.setDormBuildId(dormBuildId);
            resultStudent517.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            resultStudent517.setDormName(rs.getString("dormName"));
            resultStudent517.setName(rs.getString("name"));
            resultStudent517.setSex(rs.getString("sex"));
            resultStudent517.setTel(rs.getString("tel"));
        }
        return resultStudent517;
    }

    /*	系统管理员修改密码	*/
    public int adminUpdate(Connection con, int adminId, String password) throws Exception {
        String sql = "update t_admin set password=? where adminId=?";
        String enPassword = MD5Util.encoderPwdByMD5(password);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, enPassword);
        pstmt.setInt(2, adminId);
        return pstmt.executeUpdate();
    }

    /*		宿管人员修改密码	*/
    public int managerUpdate(Connection con, int managerId, String password) throws Exception {
        String sql = "update t_dormmanager set password=? where dormManId=?";
        password = MD5Util.encoderPwdByMD5(password);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setInt(2, managerId);
        return pstmt.executeUpdate();
    }

    /*		学生修改密码	*/
    public int studentUpdate(Connection con, int studentId, String password) throws Exception {
        String sql = "update t_student set password=? where studentId=?";
        password = MD5Util.encoderPwdByMD5(password);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setInt(2, studentId);
        return pstmt.executeUpdate();
    }

}
