package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.DormManager502;
import com.util.MD5Util;
import com.util.StringUtil;

public class DormManagerDao502 {

    public List<DormManager502> dormManagerList(Connection con, DormManager502 s_dormManager502) throws Exception {
        List<DormManager502> dormManager502List = new ArrayList<DormManager502>();
        StringBuffer sb = new StringBuffer("SELECT * FROM t_dormManager t1");
        if (StringUtil.isNotEmpty(s_dormManager502.getName())) {
            sb.append(" where t1.name like '%" + s_dormManager502.getName() + "%'");
        } else if (StringUtil.isNotEmpty(s_dormManager502.getUserName())) {
            sb.append(" where t1.userName like '%" + s_dormManager502.getUserName() + "%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            DormManager502 dormManager502 = new DormManager502();
            dormManager502.setDormManagerId(rs.getInt("dormManId"));
            int dormBuildId = rs.getInt("dormBuildId");
            dormManager502.setDormBuildId(dormBuildId);
            dormManager502.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            dormManager502.setName(rs.getString("name"));
            dormManager502.setSex(rs.getString("sex"));
            dormManager502.setUserName(rs.getString("userName"));
            dormManager502.setTel(rs.getString("tel"));
            dormManager502.setPassword(rs.getString("password"));
            dormManager502List.add(dormManager502);
        }
        return dormManager502List;
    }

    public int dormManagerCount(Connection con, DormManager502 s_dormManager502) throws Exception {
        StringBuffer sb = new StringBuffer("select count(*) as total from t_dormManager t1");
        if (StringUtil.isNotEmpty(s_dormManager502.getName())) {
            sb.append(" where t1.name like '%" + s_dormManager502.getName() + "%'");
        } else if (StringUtil.isNotEmpty(s_dormManager502.getUserName())) {
            sb.append(" where t1.userName like '%" + s_dormManager502.getUserName() + "%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString());
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    public DormManager502 dormManagerShow(Connection con, String dormManagerId) throws Exception {
        String sql = "select * from t_dormManager t1 where t1.dormManId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, dormManagerId);
        ResultSet rs = pstmt.executeQuery();
        DormManager502 dormManager502 = new DormManager502();
        if (rs.next()) {
            dormManager502.setDormManagerId(rs.getInt("dormManId"));
            dormManager502.setDormBuildId(rs.getInt("dormBuildId"));
            dormManager502.setName(rs.getString("name"));
            dormManager502.setSex(rs.getString("sex"));
            dormManager502.setUserName(rs.getString("userName"));
            dormManager502.setTel(rs.getString("tel"));
            dormManager502.setPassword(rs.getString("password"));
        }
        return dormManager502;
    }

    public int dormManagerAdd(Connection con, DormManager502 dormManager502) throws Exception {
        String sql = "insert into t_dormManager values(null,?,?,null,?,?,?)";
        dormManager502.setPassword(MD5Util.encoderPwdByMD5(dormManager502.getPassword()));
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, dormManager502.getUserName());
        pstmt.setString(2, dormManager502.getPassword());
        pstmt.setString(3, dormManager502.getName());
        pstmt.setString(4, dormManager502.getSex());
        pstmt.setString(5, dormManager502.getTel());
        return pstmt.executeUpdate();
    }

    public int dormManagerDelete(Connection con, String dormManagerId) throws Exception {
        String sql = "delete from t_dormManager where dormManId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, dormManagerId);
        return pstmt.executeUpdate();
    }

    public int dormManagerUpdate(Connection con, DormManager502 dormManager502) throws Exception {
        String sql = "update t_dormManager set userName=?,password=?,name=?,sex=?,tel=? where dormManId=?";
        dormManager502.setPassword(MD5Util.encoderPwdByMD5(dormManager502.getPassword()));
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, dormManager502.getUserName());
        pstmt.setString(2, dormManager502.getPassword());
        pstmt.setString(3, dormManager502.getName());
        pstmt.setString(4, dormManager502.getSex());
        pstmt.setString(5, dormManager502.getTel());
        pstmt.setInt(6, dormManager502.getDormManagerId());
        return pstmt.executeUpdate();
    }

    public boolean haveManagerByUser(Connection con, String userName) throws Exception {
        String sql = "select * from t_dormmanager t1 where t1.userName=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, userName);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }


}
