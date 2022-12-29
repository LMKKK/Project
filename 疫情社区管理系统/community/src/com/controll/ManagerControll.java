package com.controll;

import com.dao.ApplyDao;
import com.dao.Dao;
import com.model.ApplyIn;
import com.model.ApplyOut;
import com.model.Resident;

import java.util.List;
import java.util.Scanner;

/**
 * @ClassName : ManagerControll  //类名
 * @Description : 社区负责人控制  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/15 0015  1:40
 */

public class ManagerControll {
    private int manageId;
    private Dao dao = new Dao();
    private ApplyDao applyDao = new ApplyDao();
    private Scanner sc = new Scanner(System.in);

    public ManagerControll(int manageId) {
        this.manageId = manageId;
    }

    /*
     * 维持一个登录状态
     * */
    private boolean loginState = true;

    public boolean isLoginState() {
        return loginState;
    }

    /*
     * 社区负责人修改密码
     * */
    public void updatePassword() {
        System.out.println("请输入修改后的新密码");
        String p1 = sc.next();
        System.out.println("请再输入一次");
        String p2 = sc.next();
        if (p1.equals(p2)) {
            String sql = "update manager set password = ? where manage_id = ?";
            dao.execute(sql, p1, Integer.toString(manageId));
            System.out.println("修改成功！请重新登录");
            loginState = false;
        } else {
            System.out.println("两次的输入不一样！修改失败");
        }
    }

    public void queryResident() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 根据人员id查询");
            System.out.println("2. 根据人员姓名查找");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    System.out.println("请输入要查询人员的id");
                    int id = sc.nextInt();
                    String sql = "select id,name,sex,tel,address from community, resident where community.com_id = resident.com_id and resident.id = ? ;";
                    List<Resident> listp = dao.queryResident(sql, Integer.toString(id));
                    System.out.println("id\t\t\t姓名\t\t\t性别\t\t\t电话\t\t\t详细地址");
                    for (Resident resident : listp) {
                        System.out.println(resident.getId() + "\t\t\t" + resident.getName() + "\t\t\t" + resident.getSex() + "\t\t\t" + resident.getTel() + "\t\t\t" + resident.getAddress());
                    }
                    break;
                case 2:
                    System.out.println("请输入要查找人员的姓名");
                    String name = sc.next();
                    sql = "select id,name,sex,tel,address from community, resident where community.com_id = resident.com_id and name like ? ;";
                    List<Resident> list = dao.queryResident(sql, "%" + name + "%");
                    System.out.println("id\t\t\t姓名\t\t\t性别\t\t\t电话\t\t\t详细地址");
                    for (Resident resident : list) {
                        System.out.println(resident.getId() + "\t\t\t" + resident.getName() + "\t\t\t" + resident.getSex() + "\t\t\t" + resident.getTel() + "\t\t\t" + resident.getAddress());
                    }
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！");
                    break;
            }
            if (!flag) {
                break;
            }
        }


    }

    /*
     * 社区居民管理功能汇总
     * */
    public void resident() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 查看我负责的社区成员");
            System.out.println("2. 查询某个社区成员信息");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    String sql = "select id,name,sex,tel,address from community, resident where community.com_id = resident.com_id and community.manage_id = ? ;";
                    List<Resident> listp = dao.queryResident(sql, Integer.toString(manageId));
                    System.out.println("id\t\t\t姓名\t\t\t性别\t\t\t电话\t\t\t\t详细地址");
                    for (Resident resident : listp) {
                        System.out.println(resident.getId() + "\t\t\t" + resident.getName() + "\t\t\t" + resident.getSex() + "\t\t\t" + resident.getTel() + "\t\t\t" + resident.getAddress());
                    }
                    break;
                case 2:
                    queryResident();
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！");
                    break;
            }
            if (!flag) {
                break;
            }
        }


    }

    public void getAllApplyIn() {
        String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address , nowtime,arrtime,start,end,state,remark " +
                "                from applyin,community,resident " +
                "                where resident.id = applyin.id and resident.com_id = community.com_id and community.manage_id =?; ";
        List<ApplyIn> list = applyDao.queryApplyIn(sql, Integer.toString(manageId));
        System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
        for (ApplyIn apply : list) {
            System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                    + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getArrTime()
                    + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
        }
    }

    /*
     * 返回社区报备管理
     * */
    public void applyReturn() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 查看所有的返回社区报备");
            System.out.println("2. 查询某人的返回社区报备");
            System.out.println("3. 审批");
            System.out.println("4. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    getAllApplyIn();
                    break;
                case 2:
                    System.out.println("请输入要查询人员的id");
                    int id = sc.nextInt();
                    String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address , nowtime,arrtime,start,end,state,remark " +
                            "from community,resident,applyin " +
                            "where resident.id = applyin.id and community.com_id = resident.com_id and resident.id = ?;";
                    List<ApplyIn> list = applyDao.queryApplyIn(sql, Integer.toString(id));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyIn apply : list) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getArrTime()
                                + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
                    break;
                case 3:
                    getAllApplyIn();
                    System.out.println("请输入要审批的记录编号");
                    int idx = sc.nextInt();
                    String state = null;
                    while (true) {
                        System.out.println("请选择审批状态");
                        System.out.println("1. 同意");
                        System.out.println("2. 拒绝");
                        int choice = sc.nextInt();
                        if (choice == 1) {
                            state = "同意";
                            break;
                        } else if (choice == 2) {
                            state = "拒绝";
                            break;
                        } else {
                            System.out.println("没有这个选项！请重新选择");
                        }
                    }
                    sql = "update applyin set state = ? where idx = ?";
                    applyDao.execute(sql, state, Integer.toString(idx));
                    System.out.println("审批成功");
                    sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address , nowtime,arrtime,start,end,state,remark " +
                            "from community,resident,applyin " +
                            "where resident.id = applyin.id and community.com_id = resident.com_id and idx = ?;";
                    List<ApplyIn> list1 = applyDao.queryApplyIn(sql, Integer.toString(idx));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyIn apply : list1) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getArrTime()
                                + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
                    break;
                case 4:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项");
                    break;
            }
            if (!flag) {
                break;
            }
        }


    }

    public void getAllApplyOut() {

        String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,leavetime,start,end,state,remark " +
                "from community,resident,applyout " +
                "where resident.id = applyout.id and community.com_id = resident.com_id and community.manage_id = ?;";
        List<ApplyOut> list = applyDao.queryApplyOut(sql, Integer.toString(manageId));
        System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
        for (ApplyOut apply : list) {
            System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                    + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getLeaveTime()
                    + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
        }
    }

    public void applyOut() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 查看所有的离开社区报备");
            System.out.println("2. 查询某人的离开社区报备");
            System.out.println("3. 审批");
            System.out.println("4. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    getAllApplyOut();
                    break;
                case 2:
                    System.out.println("请输入要查询人员的id");
                    int id = sc.nextInt();
                    String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,leavetime,start,end,state,remark from community,resident,applyout \n" +
                            "where resident.id = applyout.id and community.com_id = resident.com_id and resident.id = ?;";
                    List<ApplyOut> listp = applyDao.queryApplyOut(sql, Integer.toString(id));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t离开时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyOut apply : listp) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getLeaveTime()
                                + "\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
                    break;
                case 3:
                    getAllApplyOut();
                    System.out.println("请输入要审批的记录编号");
                    int idx = sc.nextInt();
                    String state = null;
                    while (true) {
                        System.out.println("请选择审批状态");
                        System.out.println("1. 同意");
                        System.out.println("2. 拒绝");
                        int choice = sc.nextInt();
                        if (choice == 1) {
                            state = "同意";
                            break;
                        } else if (choice == 2) {
                            state = "拒绝";
                            break;
                        } else {
                            System.out.println("没有这个选项！请重新选择");
                        }
                    }
                    sql = "update applyout set state = ? where idx = ?";
                    applyDao.execute(sql, state, Integer.toString(idx));
                    System.out.println("审批成功");

                    sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,leavetime,start,end,state,remark from community,resident,applyout \n" +
                            "where resident.id = applyout.id and community.com_id = resident.com_id and idx = ?;";
                    List<ApplyOut> list1 = applyDao.queryApplyOut(sql, Integer.toString(idx));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t离开时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyOut apply : list1) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getLeaveTime()
                                + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
                    break;
                case 4:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项");
                    break;
            }
            if (!flag) {
                break;
            }
        }
    }

    /*
     * 报备审批
     *
     * */
    public void apply() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 返回社区报备管理");
            System.out.println("2. 离开社区报备管理");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    applyReturn();
                    break;
                case 2:
                    applyOut();
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项");
                    break;
            }
            if (!flag) {
                break;
            }
        }

    }


}
