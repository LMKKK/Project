package com.dao;


import com.model.DormBuild;
import com.model.Excp;
import com.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExceptionDao {

    /*返回指定条件的查询异常报备记录*/
    public List<Excp> recordList(Connection con, Excp excp) throws Exception {
        List<Excp> excpList = new ArrayList<Excp>();
        StringBuffer sb = new StringBuffer("select * from t_exception t1");
        if (StringUtil.isNotEmpty(excp.getStuNum())) {
            sb.append(" and t1.stuNum like '%" + excp.getStuNum() + "%'");
        }
        if (excp.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + excp.getDormBuildId());
        }
        if (StringUtil.isNotEmpty(excp.getDate())) {
            sb.append(" and t1.date=" + excp.getDate());
        }
        if (StringUtil.isNotEmpty(excp.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + excp.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(excp.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + excp.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Excp ex = new Excp();
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
            ex.setDate(rs.getString("date"));
            ex.setTel(rs.getString("tel"));
            ex.setState(rs.getString("state"));
            ex.setDetail(rs.getString("detail"));
            ex.setImgUrl(rs.getString("imgUrl"));
            excpList.add(ex);
        }
        return excpList;
    }

    //    根据宿舍楼ID返回查询结果
    public List<Excp> recordListWithBuild(Connection con, Excp s_record, int buildId) throws Exception {
        List<Excp> recordList = new ArrayList<Excp>();
        StringBuffer sb = new StringBuffer("select * from t_exception t1");
        if (StringUtil.isNotEmpty(s_record.getStuNum())) {
            sb.append(" and t1.stuNum like '%" + s_record.getStuNum() + "%'");
        }
        sb.append(" and t1.dormBuildId=" + buildId);
        if (StringUtil.isNotEmpty(s_record.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_record.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_record.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_record.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Excp ex = new Excp();
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
            ex.setDate(rs.getString("date"));
            ex.setTel(rs.getString("tel"));
            ex.setState(rs.getString("state"));
            ex.setDetail(rs.getString("detail"));
            ex.setImgUrl(rs.getString("imgUrl"));
            recordList.add(ex);
        }
        return recordList;
    }

    //    根据学号返回报备记录
    public List<Excp> recordListWithNumber(Connection con, Excp s_record, String studentNumber) throws Exception {
        List<Excp> recordList = new ArrayList<Excp>();
        StringBuffer sb = new StringBuffer("select * from t_exception t1");
        if (StringUtil.isNotEmpty(studentNumber)) {
            sb.append(" and t1.stuNum =" + studentNumber);
        }
        if (StringUtil.isNotEmpty(s_record.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + s_record.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(s_record.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + s_record.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Excp ex = new Excp();
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
            ex.setTel(rs.getString("tel"));
            ex.setDate(rs.getString("date"));
            ex.setState(rs.getString("state"));
            ex.setDetail(rs.getString("detail"));
            ex.setImgUrl(rs.getString("imgUrl"));
            recordList.add(ex);
        }
        return recordList;
    }

    //    返回宿舍楼信息
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

    //
    public Excp recordShow(Connection con, String recordId) throws Exception {
        String sql = "select * from t_exception t1 where t1.excpId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, recordId);
        ResultSet rs = pstmt.executeQuery();
        Excp ex = new Excp();
        if (rs.next()) {
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
            ex.setDate(rs.getString("date"));
            ex.setState(rs.getString("state"));
            ex.setTel(rs.getString("tel"));
            ex.setDetail(rs.getString("detail"));
            ex.setImgUrl(rs.getString("imgUrl"));
        }
        return ex;
    }

    public int recordAdd(Connection con, Excp record) throws Exception {
        String sql = "insert into `t_exception`(`stuNum`,`dormBuildId`,`tel`,`date`,`state`,`detail`,`imgUrl`) " +
                "values(?,?,?,?,'处理中',?,?);";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, record.getStuNum());
        pstmt.setInt(2, record.getDormBuildId());
        pstmt.setString(3, record.getTel());
        pstmt.setString(4, record.getDate());
        pstmt.setString(5, record.getDetail());
        pstmt.setString(6, record.getImgUrl());
        return pstmt.executeUpdate();
    }

    public int recordDelete(Connection con, String recordId) throws Exception {
        String sql = "delete from t_exception where excpId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, recordId);
        return pstmt.executeUpdate();
    }

    public int recordUpdate(Connection con, Excp excp) throws Exception {
        String sql = "update t_exception set state = ? , detail = ? where excpId = ?; ";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, excp.getState());
        pstmt.setString(2, excp.getDetail());
        pstmt.setString(3, Integer.toString(excp.getExcpId()));


        return pstmt.executeUpdate();
    }


}
