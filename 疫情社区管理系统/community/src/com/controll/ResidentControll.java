package com.controll;

import com.dao.ApplyDao;
import com.dao.Dao;
import com.model.ApplyIn;
import com.model.ApplyOut;
import com.utils.DateUtils;

import java.util.List;
import java.util.Scanner;

/**
 * @ClassName : ResidentControll  //类名
 * @Description : 居民控制类  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/14 0014  23:36
 */

public class ResidentControll {
    private int id;
    private Scanner sc = new Scanner(System.in);

    private Dao dao = new Dao();
    private ApplyDao applyDao = new ApplyDao();

    public ResidentControll(int id) {
        this.id = id;
    }


    //    维持一个登录的状态
    boolean loginState = true;

    public boolean isLoginState() {
        return loginState;
    }

    /*
     * 修改密码
     * */
    public void updatePassword() {
        System.out.println("请输入修改后的密码");
        String p1 = sc.next();
        System.out.println("请再次输入");
        System.out.println("(请保证两次输入的密码一致)");
        String p2 = sc.next();
        if (p1.equals(p2)) {
            String sql = "update resident set password = ? where id = ?";
            dao.execute(sql, p1, Integer.toString(id));
            System.out.println("修改成功！请重新登录");
            loginState = false;
        } else {
            System.out.println("两次输入的密码不一致！修改失败");
        }
    }

    /*
     * 提交返回社区报备
     * */
    public void submitApplyReturn() {
        System.out.println("请输入返回社区的日期(注意格式:例如2022-02-12");
        String arrtime = null;
        while (true) {
            arrtime = sc.next();
            if (DateUtils.checkDateFormat(arrtime)) {
                // 格式正确
                if (DateUtils.isRightDate(arrtime)) {
                    break;
                } else {
                    System.out.println("只能登记今天及以后30天内的报备！请重新输入");
                }
            } else {
                System.out.println("登记的日期格式不正确！请严格按照xxxx-MM-dd的格式");
                System.out.println("请重新输入");
            }

        }
        System.out.println("请输入具体出发地");
        String start = sc.next();
        System.out.println("请输入具体返回地点");
        String end = sc.next();
        System.out.println("请输入备注信息");
        String remark = sc.next();
        String nowtime = DateUtils.getNowTime();

        String sql = "insert into applyin(id,nowtime,arrtime,start,end,remark) values(?,?,?,?,?,?);";
        applyDao.execute(sql, Integer.toString(id), nowtime, arrtime, start, end, remark);

        System.out.println("报备成功！这是你的报备详细信息");
        sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address , nowtime, arrtime,start,end,state,remark " +
                "from community,resident,applyin " +
                "where resident.id = applyin.id and community.com_id = resident.com_id and resident.id = ? and nowtime = ?;";
        List<ApplyIn> list = applyDao.queryApplyIn(sql, Integer.toString(id), nowtime);
        System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
        for (ApplyIn apply : list) {
            System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                    + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t\t\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getArrTime()
                    + "\t\t\t" + apply.getStart() + "\t\t" + apply.getEnd() + "\t\t" + apply.getState() + "\t\t" + apply.getRemark());
        }
    }

    /*
     * 提交离开社区报备
     * */
    public void submitApplyOut() {
        System.out.println("请离开离开社区的日期(注意格式:例如2022-02-12");
        String leaveTime = null;
        while (true) {
            leaveTime = sc.next();
            if (DateUtils.checkDateFormat(leaveTime)) {
                // 格式正确
                if (DateUtils.isRightDate(leaveTime)) {
                    break;
                } else {
                    System.out.println("只能登记今天及以后30天内的报备！请重新输入");
                }
            } else {
                System.out.println("登记的日期格式不正确！请严格按照xxxx-MM-dd的格式");
                System.out.println("请重新输入");
            }

        }
        System.out.println("请输入具体出发地");
        String start = sc.next();
        System.out.println("请输入具体目的地");
        String end = sc.next();
        System.out.println("请输入备注信息");
        String remark = sc.next();
        String nowtime = DateUtils.getNowTime();
        String sql = "insert into applyout(id,nowtime,leavetime,start,end,remark) values(?,?,?,?,?,?);";
        applyDao.execute(sql, Integer.toString(id), nowtime, leaveTime, start, end, remark);
        System.out.println("提交成功！这是你的报备详细信息");
        sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,leavetime,start,end,state,remark from community,resident,applyout \n" +
                "where resident.id = applyout.id and community.com_id = resident.com_id and resident.id = ? and nowtime = ?;";
        List<ApplyOut> listp = applyDao.queryApplyOut(sql, Integer.toString(id), nowtime);
        System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                "\t\t\t提交时间\t\t\t\t\t离开时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
        for (ApplyOut apply : listp) {
            System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                    + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t\t\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getLeaveTime()
                    + "\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
        }
    }

    /*
     * 查看我的报备
     * */
    public void getMyApply() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 查看我的返回社区报备");
            System.out.println("2. 查看我的离开社区报备");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address , nowtime,arrtime,start,end,state,remark " +
                            "from community,resident,applyin " +
                            "where resident.id = applyin.id and community.com_id = resident.com_id and resident.id = ?;";
                    List<ApplyIn> list = applyDao.queryApplyIn(sql, Integer.toString(id));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyIn apply : list) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t\t\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getArrTime()
                                + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
;                    break;
                case 2:
                    sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,leavetime,start,end,state,remark from community,resident,applyout \n" +
                            "where resident.id = applyout.id and community.com_id = resident.com_id and resident.id = ?;";
                    List<ApplyOut> listp = applyDao.queryApplyOut(sql, Integer.toString(id));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t离开时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyOut apply : listp) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t\t\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getLeaveTime()
                                + "\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱按");
                    break;
            }
            if(!flag){
                break;
            }
        }

    }
}
