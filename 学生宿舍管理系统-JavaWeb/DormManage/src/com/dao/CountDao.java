package com.dao;


import com.model.Count;
import com.model.DormBuild;
import com.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CountDao {

    public List<Count> countList(Connection con, Count s_count) throws Exception {
        List<Count> countList = new ArrayList<Count>();
        StringBuffer sb = new StringBuffer("select * from t_count t1");
        if (StringUtil.isNotEmpty(s_count.getStuNum())) {
            sb.append(" and t1.stuNum like '%" + s_count.getStuNum() + "%'");
        } else if (StringUtil.isNotEmpty(s_count.getStuName())) {
            sb.append(" and t1.stuName like '%" + s_count.getStuName() + "%'");
        }
        if (s_count.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + s_count.getDormBuildId());
        }
        if (StringUtil.isNotEmpty(s_count.getDate())) {
            sb.append(" and t1.date=" + s_count.getDate());
        }
        if (StringUtil.isNotEmpty(s_count.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_count.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_count.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_count.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Count count = new Count();
            count.setRecordId((rs.getInt("countId")));
            count.setStuNum(rs.getString("stuNum"));
            count.setStuName(rs.getString("stuName"));
            int dormBuildId = rs.getInt("dormBuildId");
            count.setDormBuildId(dormBuildId);
            count.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
//            count.setDormName(rs.getString("dormName"));
            count.setState(rs.getString("state"));
            count.setDate(rs.getString("date"));
            count.setDetail(rs.getString("detail"));
            countList.add(count);
        }
        return countList;
    }

    public List<Count> countListWithBuild(Connection con, Count s_count, int buildId) throws Exception {

        List<Count> countList = new ArrayList<Count>();
        StringBuffer sb = new StringBuffer("select * from t_count t1");
        if (StringUtil.isNotEmpty(s_count.getStuNum())) {
            sb.append(" and t1.stuNum like '%" + s_count.getStuNum() + "%'");
        } else if (StringUtil.isNotEmpty(s_count.getStuName())) {
            sb.append(" and t1.stuName like '%" + s_count.getStuName() + "%'");
        }
        if (s_count.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + s_count.getDormBuildId());
        }
        if (StringUtil.isNotEmpty(s_count.getDate())) {
            sb.append(" and t1.date=" + s_count.getDate());
        }
        if (StringUtil.isNotEmpty(s_count.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_count.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_count.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_count.getEndDate() + "')");
        }
        sb.append(" and t1.dormBuildId=" + buildId);
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Count count = new Count();
            count.setRecordId((rs.getInt("countId")));
            count.setStuNum(rs.getString("stuNum"));
            count.setStuName(rs.getString("stuName"));
            int dormBuildId = rs.getInt("dormBuildId");
            count.setDormBuildId(dormBuildId);
            count.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
//            count.setDormName(rs.getString("dormName"));
            count.setState(rs.getString("state"));
            count.setDate(rs.getString("date"));
            count.setDetail(rs.getString("detail"));
            countList.add(count);
        }
        return countList;
    }

    public List<Count> countListWithNumber(Connection con, Count s_count, String stuNum) throws Exception {

        List<Count> countList = new ArrayList<Count>();
        StringBuffer sb = new StringBuffer("select * from t_count t1");
        if (StringUtil.isNotEmpty(stuNum)) {
            sb.append(" and t1.studentNumber =" + stuNum);
        }
        if (StringUtil.isNotEmpty(s_count.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_count.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_count.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_count.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Count count = new Count();
            count.setRecordId((rs.getInt("countId")));
            count.setStuNum(rs.getString("stuNum"));
            count.setStuName(rs.getString("stuName"));
            int dormBuildId = rs.getInt("dormBuildId");
            count.setDormBuildId(dormBuildId);
            count.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
//            count.setDormName(rs.getString("dormName"));
            count.setState(rs.getString("state"));
            count.setDate(rs.getString("date"));
            count.setDetail(rs.getString("detail"));
            countList.add(count);
        }
        return countList;
    }

    public List<DormBuild> dormBuildList(Connection con) throws Exception {
        List<DormBuild> dormBuildList = new ArrayList<DormBuild>();
        String sql = "select * from t_dormBuild";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            DormBuild dormBuild = new DormBuild();
            dormBuild.setDormBuildId(rs.getInt("dormBuildId"));
            dormBuild.setDormBuildName(rs.getString("dormBuildName"));
            dormBuild.setDetail(rs.getString("dormBuildDetail"));
            dormBuildList.add(dormBuild);
        }
        return dormBuildList;
    }

    public int countAdd(Connection con, Count count) throws Exception {
        String sql = "insert into `t_count`(`stuNum`,`stuName`,`date`,`state`,`dormBuildId`,`detail`) " +
                "values(?,?,?,?,?,?);";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, count.getStuNum());
        pstmt.setString(2, count.getStuName());
        pstmt.setString(3, count.getDate());
        pstmt.setString(4, count.getState());
        pstmt.setString(5, Integer.toString(count.getDormBuildId()));
        pstmt.setString(6, count.getDetail());

        return pstmt.executeUpdate();
    }

}
