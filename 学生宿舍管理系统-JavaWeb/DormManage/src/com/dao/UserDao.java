package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.model.Admin;
import com.model.DormManager;
import com.model.Student;
import com.util.DBUtils;
import com.util.MD5Util;

/*		系统角色的数据访问层操作
 * 		1. 角色登录
 * 		2. 角色修改密码
 * */
public class UserDao {

    /*			根据方法参数中， Bean的不同类型实现了方法的重载	*/
    public Admin Login(Connection con, Admin admin) throws Exception {
        // 验证数据库连接是否可用，不可用不会去执行下面的操作。
        DBUtils.isValidCon(con);

        Admin resultAdmin = null;
        String sql = "select * from t_admin where userName=? and password=?";
        admin.setPassword(MD5Util.encoderPwdByMD5(admin.getPassword()));
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, admin.getUserName());
        pstmt.setString(2, admin.getPassword());
        ResultSet rs = pstmt.executeQuery(); // 执行SQL语句
        if (rs.next()) {
            resultAdmin = new Admin();
            resultAdmin.setAdminId(rs.getInt("adminId"));
            resultAdmin.setUserName(rs.getString("userName"));
            resultAdmin.setPassword(rs.getString("password"));
            resultAdmin.setName(rs.getString("name"));
            resultAdmin.setSex(rs.getString("sex"));
            resultAdmin.setTel(rs.getString("tel"));
        }
        return resultAdmin;
    }

    public DormManager Login(Connection con, DormManager dormManager) throws Exception {
        // 验证数据库连接是否可用，不可用不会去执行下面的操作。
        DBUtils.isValidCon(con);
        DormManager resultDormManager = null;
        dormManager.setPassword(MD5Util.encoderPwdByMD5(dormManager.getPassword()));
        String sql = "select * from t_dormManager where userName=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, dormManager.getUserName());
        pstmt.setString(2, dormManager.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultDormManager = new DormManager();
            resultDormManager.setDormManagerId(rs.getInt("dormManId"));
            resultDormManager.setUserName(rs.getString("userName"));
            resultDormManager.setPassword(rs.getString("password"));
            resultDormManager.setDormBuildId(rs.getInt("dormBuildId"));
            resultDormManager.setName(rs.getString("name"));
            resultDormManager.setSex(rs.getString("sex"));
            resultDormManager.setTel(rs.getString("tel"));
        }
        return resultDormManager;
    }

    public Student Login(Connection con, Student student) throws Exception {
        // 验证数据库连接是否可用，不可用不会去执行下面的操作。
        DBUtils.isValidCon(con);
        Student resultStudent = null;
        student.setPassword(MD5Util.encoderPwdByMD5(student.getPassword()));
        String sql = "select * from t_student where stuNum=? and password=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, student.getStuNumber());
        pstmt.setString(2, student.getPassword());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultStudent = new Student();
            resultStudent.setStudentId(rs.getInt("studentId"));
            resultStudent.setStuNumber(rs.getString("stuNum"));
            resultStudent.setPassword(rs.getString("password"));
            int dormBuildId = rs.getInt("dormBuildId");
            resultStudent.setDormBuildId(dormBuildId);
            resultStudent.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
            resultStudent.setDormName(rs.getString("dormName"));
            resultStudent.setName(rs.getString("name"));
            resultStudent.setSex(rs.getString("sex"));
            resultStudent.setTel(rs.getString("tel"));
        }
        return resultStudent;
    }

    /*	系统管理员修改密码	*/
    public int adminUpdate(Connection con, int adminId, String password) throws Exception {
        // 验证数据库连接是否可用，不可用不会去执行下面的操作。
        DBUtils.isValidCon(con);
        String sql = "update t_admin set password=? where adminId=?";
        String enPassword = MD5Util.encoderPwdByMD5(password);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, enPassword);
        pstmt.setInt(2, adminId);
        return pstmt.executeUpdate();
    }

    /*		宿管人员修改密码	*/
    public int managerUpdate(Connection con, int managerId, String password) throws Exception {
        // 验证数据库连接是否可用，不可用不会去执行下面的操作。
        DBUtils.isValidCon(con);
        String sql = "update t_dormmanager set password=? where dormManId=?";
        password = MD5Util.encoderPwdByMD5(password);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setInt(2, managerId);
        return pstmt.executeUpdate();
    }

    /*		学生修改密码	*/
    public int studentUpdate(Connection con, int studentId, String password) throws Exception {
        // 验证数据库连接是否可用，不可用不会去执行下面的操作。
        DBUtils.isValidCon(con);
        String sql = "update t_student set password=? where studentId=?";
        password = MD5Util.encoderPwdByMD5(password);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, password);
        pstmt.setInt(2, studentId);
        return pstmt.executeUpdate();
    }

}
