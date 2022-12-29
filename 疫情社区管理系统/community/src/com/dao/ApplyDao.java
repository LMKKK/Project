package com.dao;

import com.model.ApplyIn;
import com.model.ApplyOut;
import com.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : ApplyDao  //类名
 * @Description : 申请的数据访问  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/14 0014  21:48
 */

public class ApplyDao {
    private Connection conn = DBUtils.getConnection();

    /*
    * 获取所有人的返回报备记录
    * */
    public List<ApplyIn> getAllApplyIn() {
        ArrayList<ApplyIn> list = new ArrayList<>();
        String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,arrtime,start,end,state,remark " +
                "from community,resident,applyin " +
                "where resident.id = applyin.id and community.com_id = resident.com_id;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ApplyIn apply = new ApplyIn();
                apply.setIdx(rs.getInt("idx"));
                apply.setId(rs.getInt("id"));
                apply.setName(rs.getString("name"));
                apply.setComId(rs.getInt("com_id"));
                apply.setComName(rs.getString("com_name"));
                apply.setSex(rs.getString("sex"));
                apply.setTel(rs.getString("tel"));
                apply.setAddress(rs.getString("address"));
                apply.setNowTime(rs.getString("nowtime"));
                apply.setArrTime(rs.getString("arrtime"));
                apply.setStart(rs.getString("start"));
                apply.setEnd(rs.getString("end"));
                apply.setState(rs.getString("state"));
                apply.setRemark(rs.getString("remark"));
                list.add(apply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /*
    * 查询某人的返回报备，根据id查询
    * */
    public List<ApplyIn> queryApplyIn(String sql, String... params) {
        ArrayList<ApplyIn> list = new ArrayList<>();

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                pstm.setString(i, params[i - 1]);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ApplyIn apply = new ApplyIn();
                apply.setIdx(rs.getInt("idx"));
                apply.setId(rs.getInt("id"));
                apply.setName(rs.getString("name"));
                apply.setComId(rs.getInt("com_id"));
                apply.setComName(rs.getString("com_name"));
                apply.setSex(rs.getString("sex"));
                apply.setTel(rs.getString("tel"));
                apply.setAddress(rs.getString("address"));
                apply.setNowTime(rs.getString("nowtime"));
                apply.setArrTime(rs.getString("arrtime"));
                apply.setStart(rs.getString("start"));
                apply.setEnd(rs.getString("end"));
                apply.setState(rs.getString("state"));
                apply.setRemark(rs.getString("remark"));
                list.add(apply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

/*
* 查看所有人的离开报备记录
* */
    public List<ApplyOut> getAllApplyOut() {
        ArrayList<ApplyOut> list = new ArrayList<>();
        String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,leavetime,start,end,state,remark " +
                "from community,resident,applyout " +
                "where resident.id = applyout.id and community.com_id = resident.com_id;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ApplyOut apply = new ApplyOut();
                apply.setIdx(rs.getInt("idx"));
                apply.setId(rs.getInt("id"));
                apply.setName(rs.getString("name"));
                apply.setComId(rs.getInt("com_id"));
                apply.setComName(rs.getString("com_name"));
                apply.setSex(rs.getString("sex"));
                apply.setTel(rs.getString("tel"));
                apply.setAddress(rs.getString("address"));
                apply.setNowTime(rs.getString("nowtime"));
                apply.setLeaveTime(rs.getString("leavetime"));
                apply.setStart(rs.getString("start"));
                apply.setEnd(rs.getString("end"));
                apply.setState(rs.getString("state"));
                apply.setRemark(rs.getString("remark"));
                list.add(apply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
    * 查询某人的离开社区报备
    * */
    public List<ApplyOut> queryApplyOut(String sql, String... params) {
        ArrayList<ApplyOut> list = new ArrayList<>();

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                pstm.setString(i, params[i - 1]);
            }
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ApplyOut apply = new ApplyOut();
                apply.setIdx(rs.getInt("idx"));
                apply.setId(rs.getInt("id"));
                apply.setName(rs.getString("name"));
                apply.setComId(rs.getInt("com_id"));
                apply.setComName(rs.getString("com_name"));
                apply.setSex(rs.getString("sex"));
                apply.setTel(rs.getString("tel"));
                apply.setAddress(rs.getString("address"));
                apply.setNowTime(rs.getString("nowtime"));
                apply.setLeaveTime(rs.getString("leavetime"));
                apply.setStart(rs.getString("start"));
                apply.setEnd(rs.getString("end"));
                apply.setState(rs.getString("state"));
                apply.setRemark(rs.getString("remark"));
                list.add(apply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
    * 执行增删改的操作
    * */
    public void execute(String sql, String ...params){
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            for(int i = 1;i <= params.length;i++){
                psmt.setString(i, params[i-1]);
            }
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
