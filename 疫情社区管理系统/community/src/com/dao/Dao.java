package com.dao;

import com.model.Community;
import com.model.Manager;
import com.model.Resident;
import com.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : Dao  //类名
 * @Description : 数据库访问类  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  22:42
 */

public class Dao {
    private Connection conn = DBUtils.getConnection();

    public List<Resident> getAllResident() {
        ArrayList<Resident> list = new ArrayList<Resident>();
//        Connection conn = DBUtils.getConnection();
        String sql = "select * from resident;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Resident people = new Resident();
                people.setId(rs.getInt("id"));
                people.setPassword(rs.getString("password"));
                people.setComId(rs.getInt("com_id"));
                people.setName(rs.getString("name"));
                people.setAddress(rs.getString("address"));
                people.setTel(rs.getString("tel"));
                people.setSex(rs.getString("sex"));
                list.add(people);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Manager> queryManager(String sql, String... params) {
        ArrayList<Manager> list = new ArrayList<>();
//        Connection conn = DBUtils.getConnection();
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                psmt.setString(i, params[i - 1]);
            }

            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Manager manager = new Manager();
                manager.setId(rs.getInt("manage_id"));
                manager.setPassword(rs.getString("password"));
                manager.setName(rs.getString("name"));
                manager.setTel(rs.getString("tel"));
                list.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getCountByCommunityId(String comId) {
        String sql = " select count(*) from community,resident where resident.com_id = community.com_id and resident.com_id = ? ;";

//        Connection conn = DBUtils.getConnection();
        int sum = 0;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, comId);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            sum = rs.getInt("count(*)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public void execute(String sql, String... params) {
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                pstm.setString(i, params[i - 1]);
            }
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insert(String sql, String... params) {

//        Connection conn = DBUtils.getConnection();
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                psmt.setString(i, params[i - 1]);
            }

            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public List<Manager> getAllManager() {
        List<Manager> list = new ArrayList<Manager>();
//        Connection conn = DBUtils.getConnection();
        String sql = "select * from manager";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Manager manager = new Manager();
                manager.setId(rs.getInt("manage_id"));
                manager.setPassword(rs.getString("password"));
                manager.setName(rs.getString("name"));
                manager.setTel(rs.getString("tel"));
                list.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Community> getAllCommunity() {
        ArrayList<Community> list = new ArrayList<>();

        String sql = "select com_id,com_name,community.manage_id,name,tel from community,manager WHERE community.manage_id = manager.manage_id; ";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Community comm = new Community();
                comm.setComId(rs.getInt("com_id"));
                comm.setComName(rs.getString("com_name"));
                comm.setManageId(rs.getInt("community.manage_id"));
                comm.setManagerName(rs.getString("name"));
                comm.setTel(rs.getString("tel"));
                list.add(comm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;

    }

    public List<Community> queryCommunity(String sql, String... params) {
        ArrayList<Community> list = new ArrayList<>();
        PreparedStatement psmt = null;
        try {
            psmt = conn.prepareStatement(sql);


            for (int i = 1; i <= params.length; i++) {
                psmt.setString(i, params[i - 1]);
            }
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Community comm = new Community();
                comm.setComId(rs.getInt("com_id"));
                comm.setComName(rs.getString("com_name"));
                comm.setManageId(rs.getInt("community.manage_id"));
                comm.setManagerName(rs.getString("name"));
                comm.setTel(rs.getString("tel"));
                list.add(comm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ResultSet queryOneResident(String sql, String... params) {
        ResultSet rs = null;
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                pstm.setString(i, params[i - 1]);
            }
            rs = pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public List<Resident> queryResident(String sql, String... params) {
        ArrayList<Resident> list = new ArrayList<>();
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                pstm.setString(i, params[i - 1]);
            }

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Resident p = new Resident();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setTel(rs.getString("tel"));
                p.setAddress(rs.getString("address"));
                p.setSex(rs.getString("sex"));
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    public static void main(String[] args) {
        Dao dao = new Dao();
        String sql = "select com_id,com_name,community.manage_id,name,tel from community,manager WHERE community.manage_id = manager.manage_id and com_name like ?; ";
        System.out.println(sql);
        List<Community> lsit = dao.queryCommunity(sql, "%幸福%");
        for (Community community : lsit) {
            System.out.println(
                    community
            );
        }
    }
}
