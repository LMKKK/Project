package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.DormBuild502;
import com.model.Student517;
import com.util.MD5Util;
import com.util.StringUtil;

public class StudentDao517 {

    public List<Student517> studentList(Connection con, Student517 s_student517) throws Exception {
        List<Student517> student517List = new ArrayList<Student517>();
        StringBuffer sb = new StringBuffer("select * from t_student t1");
        if (StringUtil.isNotEmpty(s_student517.getName())) {
            sb.append(" and t1.name like '%" + s_student517.getName() + "%'");
        } else if (StringUtil.isNotEmpty(s_student517.getStuNumber())) {
            sb.append(" and t1.stuNum like '%" + s_student517.getStuNumber() + "%'");
        } else if (StringUtil.isNotEmpty(s_student517.getDormName())) {
            sb.append(" and t1.dormName like '%" + s_student517.getDormName() + "%'");
        }
        if (s_student517.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + s_student517.getDormBuildId());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Student517 student517 = new Student517();
            student517.setStudentId(rs.getInt("studentId"));
            int dormBuildId = rs.getInt("dormBuildId");
            student517.setDormBuildId(dormBuildId);
            student517.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            student517.setDormName(rs.getString("dormName"));
            student517.setName(rs.getString("name"));
            student517.setSex(rs.getString("sex"));
            student517.setStuNumber(rs.getString("stuNum"));
            student517.setTel(rs.getString("tel"));
            student517.setPassword(rs.getString("password"));
            student517List.add(student517);
        }
        return student517List;
    }

    public static Student517 getNameById(Connection con, String studentNumber, int dormBuildId) throws Exception {
        String sql = "select * from t_student t1 where t1.stuNum=? and t1.dormBuildId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, studentNumber);
        pstmt.setInt(2, dormBuildId);
        ResultSet rs = pstmt.executeQuery();
        Student517 student517 = new Student517();
        if (rs.next()) {
            student517.setName(rs.getString("name"));
            student517.setDormBuildId(rs.getInt("dormBuildId"));
            student517.setDormName(rs.getString("dormName"));
        }
        return student517;
    }

    public boolean haveNameByNumber(Connection con, String studentNumber) throws Exception {
        String sql = "select * from t_student t1 where t1.stuNum=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, studentNumber);
        ResultSet rs = pstmt.executeQuery();
        Student517 student517 = new Student517();
        if (rs.next()) {
            student517.setName(rs.getString("name"));
            student517.setDormBuildId(rs.getInt("dormBuildId"));
            student517.setDormName(rs.getString("dormName"));
            return true;
        }
        return false;
    }

    public List<Student517> studentListWithBuild(Connection con, Student517 s_student517, int buildId) throws Exception {
        List<Student517> student517List = new ArrayList<Student517>();
        StringBuffer sb = new StringBuffer("select * from t_student t1");
        if (StringUtil.isNotEmpty(s_student517.getName())) {
            sb.append(" and t1.name like '%" + s_student517.getName() + "%'");
        } else if (StringUtil.isNotEmpty(s_student517.getStuNumber())) {
            sb.append(" and t1.stuNum like '%" + s_student517.getStuNumber() + "%'");
        } else if (StringUtil.isNotEmpty(s_student517.getDormName())) {
            sb.append(" and t1.dormName like '%" + s_student517.getDormName() + "%'");
        }
        sb.append(" and t1.dormBuildId=" + buildId);
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Student517 student517 = new Student517();
            student517.setStudentId(rs.getInt("studentId"));
            int dormBuildId = rs.getInt("dormBuildId");
            student517.setDormBuildId(dormBuildId);
            student517.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            student517.setDormName(rs.getString("dormName"));
            student517.setName(rs.getString("name"));
            student517.setSex(rs.getString("sex"));
            student517.setStuNumber(rs.getString("stuNum"));
            student517.setTel(rs.getString("tel"));
            student517.setPassword(rs.getString("password"));
            student517List.add(student517);
        }
        return student517List;
    }

    public List<DormBuild502> dormBuildList(Connection con) throws Exception {
        List<DormBuild502> dormBuild502List = new ArrayList<DormBuild502>();
        String sql = "select * from t_dormBuild";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            DormBuild502 dormBuild502 = new DormBuild502();
            dormBuild502.setDormBuildId(rs.getInt("dormBuildId"));
            dormBuild502.setDormBuildName(rs.getString("dormBuildName"));
            dormBuild502.setDetail(rs.getString("dormBuildDetail"));
            dormBuild502List.add(dormBuild502);
        }
        return dormBuild502List;
    }

    public int studentCount(Connection con, Student517 s_student517) throws Exception {
        StringBuffer sb = new StringBuffer("select count(*) as total from t_student t1");
        if (StringUtil.isNotEmpty(s_student517.getName())) {
            sb.append(" and t1.name like '%" + s_student517.getName() + "%'");
        } else if (StringUtil.isNotEmpty(s_student517.getStuNumber())) {
            sb.append(" and t1.stuNum like '%" + s_student517.getStuNumber() + "%'");
        } else if (StringUtil.isNotEmpty(s_student517.getDormName())) {
            sb.append(" and t1.dormName like '%" + s_student517.getDormName() + "%'");
        }
        if (s_student517.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + s_student517.getDormBuildId());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    public Student517 studentShow(Connection con, String studentId) throws Exception {
        String sql = "select * from t_student t1 where t1.studentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, studentId);
        ResultSet rs = pstmt.executeQuery();
        Student517 student517 = new Student517();
        if (rs.next()) {
            student517.setStudentId(rs.getInt("studentId"));
            int dormBuildId = rs.getInt("dormBuildId");
            student517.setDormBuildId(dormBuildId);
            student517.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            student517.setDormName(rs.getString("dormName"));
            student517.setName(rs.getString("name"));
            student517.setSex(rs.getString("sex"));
            student517.setStuNumber(rs.getString("stuNum"));
            student517.setTel(rs.getString("tel"));
            student517.setPassword(rs.getString("password"));
        }
        return student517;
    }

    public int studentAdd(Connection con, Student517 student517) throws Exception {
        String sql = "insert into t_student values(null,?,?,?,?,?,?,?)";
        student517.setPassword(MD5Util.encoderPwdByMD5(student517.getPassword()));
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, student517.getStuNumber());
        pstmt.setString(2, student517.getPassword());
        pstmt.setString(3, student517.getName());
        pstmt.setInt(4, student517.getDormBuildId());
        pstmt.setString(5, student517.getDormName());
        pstmt.setString(6, student517.getSex());
        pstmt.setString(7, student517.getTel());
        return pstmt.executeUpdate();
    }

    public int studentDelete(Connection con, String studentId) throws Exception {
        String sql = "delete from t_student where studentId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, studentId);
        return pstmt.executeUpdate();
    }

    public int studentUpdate(Connection con, Student517 student517) throws Exception {
        String sql = "update t_student set stuNum=?,password=?,name=?,dormBuildId=?,dormName=?,sex=?,tel=? where studentId=?";
        student517.setPassword(MD5Util.encoderPwdByMD5(student517.getPassword()));
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, student517.getStuNumber());
        pstmt.setString(2, student517.getPassword());
        pstmt.setString(3, student517.getName());
        pstmt.setInt(4, student517.getDormBuildId());
        pstmt.setString(5, student517.getDormName());
        pstmt.setString(6, student517.getSex());
        pstmt.setString(7, student517.getTel());
        pstmt.setInt(8, student517.getStudentId());
        return pstmt.executeUpdate();
    }


}
