package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.model.DormBuild502;
import com.model.DormManager502;
import com.util.StringUtil;

public class DormBuildDao502 {

	public List<DormBuild502> dormBuildList(Connection con, DormBuild502 s_dormBuild502)throws Exception {
		List<DormBuild502> dormBuild502List = new ArrayList<DormBuild502>();
		StringBuffer sb = new StringBuffer("select * from t_dormBuild t1");
		if(StringUtil.isNotEmpty(s_dormBuild502.getDormBuildName())) {
			sb.append(" where t1.dormBuildName like '%"+ s_dormBuild502.getDormBuildName()+"%'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DormBuild502 dormBuild502 =new DormBuild502();
			dormBuild502.setDormBuildId(rs.getInt("dormBuildId"));
			dormBuild502.setDormBuildName(rs.getString("dormBuildName"));
			dormBuild502.setDetail(rs.getString("dormBuildDetail"));
			dormBuild502List.add(dormBuild502);
		}
		return dormBuild502List;
	}
	
	public static String dormBuildName(Connection con, int dormBuildId)throws Exception {
		String sql = "select * from t_dormBuild where dormBuildId=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, dormBuildId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getString("dormBuildName");
		}
		return null;
	}
	
	public int dormBuildCount(Connection con, DormBuild502 s_dormBuild502)throws Exception {
		StringBuffer sb = new StringBuffer("select count(*) as total from t_dormBuild t1");
		if(StringUtil.isNotEmpty(s_dormBuild502.getDormBuildName())) {
			sb.append(" where t1.dormBuildName like '%"+ s_dormBuild502.getDormBuildName()+"%'");
		}
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("total");
		} else {
			return 0;
		}
	}
	
	public DormBuild502 dormBuildShow(Connection con, String dormBuildId)throws Exception {
		String sql = "select * from t_dormBuild t1 where t1.dormBuildId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuildId);
		ResultSet rs=pstmt.executeQuery();
		DormBuild502 dormBuild502 = new DormBuild502();
		if(rs.next()) {
			dormBuild502.setDormBuildId(rs.getInt("dormBuildId"));
			dormBuild502.setDormBuildName(rs.getString("dormBuildName"));
			dormBuild502.setDetail(rs.getString("dormBuildDetail"));
		}
		return dormBuild502;
	}
	
	public int dormBuildAdd(Connection con, DormBuild502 dormBuild502)throws Exception {
		String sql = "insert into t_dormBuild values(null,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuild502.getDormBuildName());
		pstmt.setString(2, dormBuild502.getDetail());
		return pstmt.executeUpdate();
	}
	
	public int dormBuildDelete(Connection con, String dormBuildId)throws Exception {
		String sql = "delete from t_dormBuild where dormBuildId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuildId);
		return pstmt.executeUpdate();
	}
	
	public int dormBuildUpdate(Connection con, DormBuild502 dormBuild502)throws Exception {
		String sql = "update t_dormBuild set dormBuildName=?,dormBuildDetail=? where dormBuildId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuild502.getDormBuildName());
		pstmt.setString(2, dormBuild502.getDetail());
		pstmt.setInt(3, dormBuild502.getDormBuildId());
		return pstmt.executeUpdate();
	}
	
	public boolean existManOrDormWithId(Connection con, String dormBuildId)throws Exception {
		boolean isExist = false;
//		String sql="select * from t_dormBuild,t_dormManager,t_connection where dormManId=managerId and dormBuildId=buildId and dormBuildId=?";
		String sql = "select *from t_dormManager where dormBuildId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuildId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			isExist = true;
		} else {
			isExist = false;
		}
		String sql1="select * from t_dormBuild t1,t_dorm t2 where t1.dormBuildId=t2.dormBuildId and t1.dormBuildId=?";
		PreparedStatement p=con.prepareStatement(sql1);
		p.setString(1, dormBuildId);
		ResultSet r = pstmt.executeQuery();
		if(r.next()) {
			return isExist;
		} else {
			return false;
		}
	}
	
	public List<DormManager502> dormManWithoutBuild(Connection con)throws Exception {
		List<DormManager502> dormManager502List = new ArrayList<DormManager502>();
		String sql = "SELECT * FROM t_dormManager WHERE dormBuildId IS NULL OR dormBuildId=0";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DormManager502 dormManager502 =new DormManager502();
			dormManager502.setDormBuildId(rs.getInt("dormBuildId"));
			dormManager502.setDormManagerId(rs.getInt("dormManId"));
			dormManager502.setName(rs.getString("name"));
			dormManager502.setUserName(rs.getString("userName"));
			dormManager502.setSex(rs.getString("sex"));
			dormManager502.setTel(rs.getString("tel"));
			dormManager502List.add(dormManager502);
		}
		return dormManager502List;
	}
	
	public List<DormManager502> dormManWithBuildId(Connection con, String dormBuildId)throws Exception {
		List<DormManager502> dormManager502List = new ArrayList<DormManager502>();
		String sql = "select *from t_dormManager where dormBuildId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuildId);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			DormManager502 dormManager502 =new DormManager502();
			dormManager502.setDormBuildId(rs.getInt("dormBuildId"));
			dormManager502.setDormManagerId(rs.getInt("dormManId"));
			dormManager502.setName(rs.getString("name"));
			dormManager502.setUserName(rs.getString("userName"));
			dormManager502.setSex(rs.getString("sex"));
			dormManager502.setTel(rs.getString("tel"));
			dormManager502List.add(dormManager502);
		}
		return dormManager502List;
	}
	
	public int managerUpdateWithId (Connection con, String dormManagerId, String dormBuildId)throws Exception {
		String sql = "update t_dormManager set dormBuildId=? where dormManId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, dormBuildId);
		pstmt.setString(2, dormManagerId);
		return pstmt.executeUpdate();
	}
}
