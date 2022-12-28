package com.dao;


import com.model.DormBuild502;
import com.model.Excp517;
import com.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExceptionDao517 {

    /*返回指定条件的查询异常报备记录*/
    public List<Excp517> recordList(Connection con, Excp517 excp517) throws Exception {
        List<Excp517> excp517List = new ArrayList<Excp517>();
        StringBuffer sb = new StringBuffer("select * from t_exception t1");
        if (StringUtil.isNotEmpty(excp517.getStuNum())) {
            sb.append(" and t1.stuNum like '%" + excp517.getStuNum() + "%'");
        }
        if (excp517.getDormBuildId() != 0) {
            sb.append(" and t1.dormBuildId=" + excp517.getDormBuildId());
        }
        if (StringUtil.isNotEmpty(excp517.getDate())) {
            sb.append(" and t1.date=" + excp517.getDate());
        }
        if (StringUtil.isNotEmpty(excp517.getStartDate())) {
            sb.append(" and TO_DAYS(t1.date)>=TO_DAYS('" + excp517.getStartDate() + "')");
        }
        if (StringUtil.isNotEmpty(excp517.getEndDate())) {
            sb.append(" and TO_DAYS(t1.date)<=TO_DAYS('" + excp517.getEndDate() + "')");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Excp517 ex = new Excp517();
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            ex.setDate(rs.getString("date"));
            ex.setTel(rs.getString("tel"));
            ex.setState(rs.getString("state"));
            ex.setDetail(rs.getString("detail"));
            ex.setImgUrl(rs.getString("imgUrl"));
            excp517List.add(ex);
        }
        return excp517List;
    }

    //    根据宿舍楼ID返回查询结果
    public List<Excp517> recordListWithBuild(Connection con, Excp517 s_record, int buildId) throws Exception {
        List<Excp517> recordList = new ArrayList<Excp517>();
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
            Excp517 ex = new Excp517();
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
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
    public List<Excp517> recordListWithNumber(Connection con, Excp517 s_record, String studentNumber) throws Exception {
        List<Excp517> recordList = new ArrayList<Excp517>();
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
            Excp517 ex = new Excp517();
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
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

    //
    public Excp517 recordShow(Connection con, String recordId) throws Exception {
        String sql = "select * from t_exception t1 where t1.excpId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, recordId);
        ResultSet rs = pstmt.executeQuery();
        Excp517 ex = new Excp517();
        if (rs.next()) {
            ex.setExcpId(rs.getInt("excpId"));
            ex.setStuNum(rs.getString("stuNum"));
            int dormBuildId = rs.getInt("dormBuildId");
            ex.setDormBuildId(dormBuildId);
            ex.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            ex.setDate(rs.getString("date"));
            ex.setState(rs.getString("state"));
            ex.setTel(rs.getString("tel"));
            ex.setDetail(rs.getString("detail"));
            ex.setImgUrl(rs.getString("imgUrl"));
        }
        return ex;
    }

    public int recordAdd(Connection con, Excp517 record) throws Exception {
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

    public int recordUpdate(Connection con, Excp517 excp517) throws Exception {
        String sql = "update t_exception set state = ? , detail = ? where excpId = ?; ";

        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, excp517.getState());
        pstmt.setString(2, excp517.getDetail());
        pstmt.setString(3, Integer.toString(excp517.getExcpId()));


        return pstmt.executeUpdate();
    }


}
