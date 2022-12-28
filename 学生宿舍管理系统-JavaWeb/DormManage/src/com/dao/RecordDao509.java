package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.DormBuild502;
import com.model.Record509;
import com.util.StringUtil;

public class RecordDao509 {
    public List<Record509> recordList(Connection con, Record509 s_record509) throws Exception {
        List<Record509> record509List = new ArrayList<Record509>();
        StringBuffer sb = new StringBuffer("select * from t_record t1");
        if (StringUtil.isNotEmpty(s_record509.getStudentNumber())) {
            sb.append(" and t1.studentNumber like '%" + s_record509.getStudentNumber() + "%'");
        } else if (StringUtil.isNotEmpty(s_record509.getStudentName())) {
            sb.append(" and t1.studentName like '%" + s_record509.getStudentName() + "%'");
        }
        if (s_record509.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + s_record509.getDormBuildId());
        }
        if (StringUtil.isNotEmpty(s_record509.getDate())) {
            sb.append(" and t1.date=" + s_record509.getDate());
        }
        if (StringUtil.isNotEmpty(s_record509.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_record509.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_record509.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_record509.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Record509 record509 = new Record509();
            record509.setRecordId(rs.getInt("recordId"));
            record509.setStudentNumber(rs.getString("studentNumber"));
            record509.setStudentName(rs.getString("studentName"));
            int dormBuildId = rs.getInt("dormBuildId");
            record509.setDormBuildId(dormBuildId);
            record509.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            record509.setDormName(rs.getString("dormName"));
            record509.setDate(rs.getString("date"));
            record509.setDetail(rs.getString("detail"));
            record509List.add(record509);
        }
        return record509List;
    }

    public List<Record509> recordListWithBuild(Connection con, Record509 s_record509, int buildId) throws Exception {
        List<Record509> record509List = new ArrayList<Record509>();
        StringBuffer sb = new StringBuffer("select * from t_record t1");
        if (StringUtil.isNotEmpty(s_record509.getStudentNumber())) {
            sb.append(" and t1.studentNumber like '%" + s_record509.getStudentNumber() + "%'");
        } else if (StringUtil.isNotEmpty(s_record509.getStudentName())) {
            sb.append(" and t1.studentName like '%" + s_record509.getStudentName() + "%'");
        }
        sb.append(" and t1.dormBuildId=" + buildId);
        if (StringUtil.isNotEmpty(s_record509.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_record509.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_record509.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_record509.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Record509 record509 = new Record509();
            record509.setRecordId(rs.getInt("recordId"));
            record509.setStudentNumber(rs.getString("studentNumber"));
            record509.setStudentName(rs.getString("studentName"));
            int dormBuildId = rs.getInt("dormBuildId");
            record509.setDormBuildId(dormBuildId);
            record509.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            record509.setDormName(rs.getString("dormName"));
            record509.setDate(rs.getString("date"));
            record509.setDetail(rs.getString("detail"));
            record509List.add(record509);
        }
        return record509List;
    }

    public List<Record509> recordListWithNumber(Connection con, Record509 s_record509, String studentNumber) throws Exception {
        List<Record509> record509List = new ArrayList<Record509>();
        StringBuffer sb = new StringBuffer("select * from t_record t1");
        if (StringUtil.isNotEmpty(studentNumber)) {
            sb.append(" and t1.studentNumber =" + studentNumber);
        }
        if (StringUtil.isNotEmpty(s_record509.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_record509.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_record509.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_record509.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Record509 record509 = new Record509();
            record509.setRecordId(rs.getInt("recordId"));
            record509.setStudentNumber(rs.getString("studentNumber"));
            record509.setStudentName(rs.getString("studentName"));
            int dormBuildId = rs.getInt("dormBuildId");
            record509.setDormBuildId(dormBuildId);
            record509.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            record509.setDormName(rs.getString("dormName"));
            record509.setDate(rs.getString("date"));
            record509.setDetail(rs.getString("detail"));
            record509List.add(record509);
        }
        return record509List;
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

    public Record509 recordShow(Connection con, String recordId) throws Exception {
        String sql = "select * from t_record t1 where t1.recordId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, recordId);
        ResultSet rs = pstmt.executeQuery();
        Record509 record509 = new Record509();
        if (rs.next()) {
            record509.setRecordId(rs.getInt("recordId"));
            record509.setStudentNumber(rs.getString("studentNumber"));
            record509.setStudentName(rs.getString("studentName"));
            int dormBuildId = rs.getInt("dormBuildId");
            record509.setDormBuildId(dormBuildId);
            record509.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            record509.setDormName(rs.getString("dormName"));
            record509.setDate(rs.getString("date"));
            record509.setDetail(rs.getString("detail"));
        }
        return record509;
    }

    public int recordAdd(Connection con, Record509 record509) throws Exception {
        String sql = "insert into t_record values(null,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, record509.getStudentNumber());
        pstmt.setString(2, record509.getStudentName());
        pstmt.setInt(3, record509.getDormBuildId());
        pstmt.setString(4, record509.getDormName());
        pstmt.setString(5, record509.getDate());
        pstmt.setString(6, record509.getDetail());
        return pstmt.executeUpdate();
    }

    public int recordDelete(Connection con, String recordId) throws Exception {
        String sql = "delete from t_record where recordId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, recordId);
        return pstmt.executeUpdate();
    }

    public int recordUpdate(Connection con, Record509 record509) throws Exception {
        String sql = "update t_record set studentNumber=?,studentName=?,dormBuildId=?,dormName=?,detail=? where recordId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, record509.getStudentNumber());
        pstmt.setString(2, record509.getStudentName());
        pstmt.setInt(3, record509.getDormBuildId());
        pstmt.setString(4, record509.getDormName());
        pstmt.setString(5, record509.getDetail());
        pstmt.setInt(6, record509.getRecordId());
        return pstmt.executeUpdate();
    }


}
