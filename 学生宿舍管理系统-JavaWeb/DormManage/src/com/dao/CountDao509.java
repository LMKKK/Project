package com.dao;


import com.model.Count509;
import com.model.DormBuild502;
import com.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CountDao509 {

    public List<Count509> countList(Connection con, Count509 s_count509) throws Exception {
        List<Count509> count509List = new ArrayList<Count509>();
        StringBuffer sb = new StringBuffer("select * from t_count t1");
        if (StringUtil.isNotEmpty(s_count509.getStuNum())) {
            sb.append(" and t1.stuNum like '%" + s_count509.getStuNum() + "%'");
        } else if (StringUtil.isNotEmpty(s_count509.getStuName())) {
            sb.append(" and t1.stuName like '%" + s_count509.getStuName() + "%'");
        }
        if (s_count509.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + s_count509.getDormBuildId());
        }
        if (StringUtil.isNotEmpty(s_count509.getDate())) {
            sb.append(" and t1.date=" + s_count509.getDate());
        }
        if (StringUtil.isNotEmpty(s_count509.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_count509.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_count509.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_count509.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Count509 count509 = new Count509();
            count509.setRecordId((rs.getInt("countId")));
            count509.setStuNum(rs.getString("stuNum"));
            count509.setStuName(rs.getString("stuName"));
            int dormBuildId = rs.getInt("dormBuildId");
            count509.setDormBuildId(dormBuildId);
            count509.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
//            count.setDormName(rs.getString("dormName"));
            count509.setState(rs.getString("state"));
            count509.setDate(rs.getString("date"));
            count509.setDetail(rs.getString("detail"));
            count509List.add(count509);
        }
        return count509List;
    }

    public List<Count509> countListWithBuild(Connection con, Count509 s_count509, int buildId) throws Exception {

        List<Count509> count509List = new ArrayList<Count509>();
        StringBuffer sb = new StringBuffer("select * from t_count t1");
        if (StringUtil.isNotEmpty(s_count509.getStuNum())) {
            sb.append(" and t1.stuNum like '%" + s_count509.getStuNum() + "%'");
        } else if (StringUtil.isNotEmpty(s_count509.getStuName())) {
            sb.append(" and t1.stuName like '%" + s_count509.getStuName() + "%'");
        }
        if (s_count509.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + s_count509.getDormBuildId());
        }
        if (StringUtil.isNotEmpty(s_count509.getDate())) {
            sb.append(" and t1.date=" + s_count509.getDate());
        }
        if (StringUtil.isNotEmpty(s_count509.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_count509.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_count509.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_count509.getEndDate() + "')");
        }
        sb.append(" and t1.dormBuildId=" + buildId);
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Count509 count509 = new Count509();
            count509.setRecordId((rs.getInt("countId")));
            count509.setStuNum(rs.getString("stuNum"));
            count509.setStuName(rs.getString("stuName"));
            int dormBuildId = rs.getInt("dormBuildId");
            count509.setDormBuildId(dormBuildId);
            count509.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
//            count.setDormName(rs.getString("dormName"));
            count509.setState(rs.getString("state"));
            count509.setDate(rs.getString("date"));
            count509.setDetail(rs.getString("detail"));
            count509List.add(count509);
        }
        return count509List;
    }

    public List<Count509> countListWithNumber(Connection con, Count509 s_count509, String stuNum) throws Exception {

        List<Count509> count509List = new ArrayList<Count509>();
        StringBuffer sb = new StringBuffer("select * from t_count t1");
        if (StringUtil.isNotEmpty(stuNum)) {
            sb.append(" and t1.studentNumber =" + stuNum);
        }
        if (StringUtil.isNotEmpty(s_count509.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_count509.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_count509.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_count509.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Count509 count509 = new Count509();
            count509.setRecordId((rs.getInt("countId")));
            count509.setStuNum(rs.getString("stuNum"));
            count509.setStuName(rs.getString("stuName"));
            int dormBuildId = rs.getInt("dormBuildId");
            count509.setDormBuildId(dormBuildId);
            count509.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
//            count.setDormName(rs.getString("dormName"));
            count509.setState(rs.getString("state"));
            count509.setDate(rs.getString("date"));
            count509.setDetail(rs.getString("detail"));
            count509List.add(count509);
        }
        return count509List;
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

    public int countAdd(Connection con, Count509 count509) throws Exception {
        String sql = "insert into `t_count`(`stuNum`,`stuName`,`date`,`state`,`dormBuildId`,`detail`) " +
                "values(?,?,?,?,?,?);";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, count509.getStuNum());
        pstmt.setString(2, count509.getStuName());
        pstmt.setString(3, count509.getDate());
        pstmt.setString(4, count509.getState());
        pstmt.setString(5, Integer.toString(count509.getDormBuildId()));
        pstmt.setString(6, count509.getDetail());

        return pstmt.executeUpdate();
    }

}
