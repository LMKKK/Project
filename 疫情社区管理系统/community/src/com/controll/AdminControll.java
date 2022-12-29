package com.controll;

import com.dao.ApplyDao;
import com.dao.Dao;
import com.model.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

import static com.view.view.*;

/**
 * @ClassName : AdminControll  //类名
 * @Description : 系统管理员控制  //描述
 * @Author : 刘明凯的专属computer //作者
 * @Date: 2022/12/13 0013  23:47
 */

public class AdminControll {
    private Dao dao = new Dao();
    private Scanner sc = new Scanner(System.in);

    /*
     * 查询社区
     * */
    public void queryCommunity() {
        boolean flag = true;
        String commName = null;
        String maangerName = null;
        int comId = 0;
        int managerId = 0;
        String sql = null;
        List<Community> list = null;
        while (true) {
            queryCommunityPage();
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    System.out.println("请输入社区名称");
                    commName = sc.next();
                    sql = "select com_id,com_name,community.manage_id,name,tel from community,manager WHERE community.manage_id = manager.manage_id and com_name like ?; ";
                    list = dao.queryCommunity(sql, "%" + commName + "%");
                    System.out.println("社区ID\t\t\t社区名称\t\t\t负责人id\t\t\t\t负责人姓名\t\t\t\t负责人电话");
                    for (Community community : list) {
                        System.out.println(community.getComId() + "\t\t\t\t" + community.getComName() + "\t\t\t" + community.getManageId() + "\t\t\t\t\t" + community.getManagerName() + "\t\t\t\t\t" + community.getTel());
                    }
                    break;
                case 2:
                    System.out.println("请输出社区ID");
                    comId = sc.nextInt();
                    sql = "select com_id,com_name,community.manage_id,name,tel from community,manager WHERE community.manage_id = manager.manage_id and com_id =?;";
                    list = dao.queryCommunity(sql, Integer.toString(comId));
                    System.out.println("社区ID\t\t\t社区名称\t\t\t负责人id\t\t\t\t负责人姓名\t\t\t\t负责人电话");
                    for (Community community : list) {
                        System.out.println(community.getComId() + "\t\t\t\t" + community.getComName() + "\t\t\t" + community.getManageId() + "\t\t\t\t\t" + community.getManagerName() + "\t\t\t\t\t" + community.getTel());
                    }
                    break;
                case 3:
                    System.out.println("请输出社区负责人名称");
                    maangerName = sc.next();
                    sql = "select com_id,com_name,community.manage_id,name,tel from community,manager WHERE community.manage_id = manager.manage_id and manager.`name` like ?;";
                    list = dao.queryCommunity(sql, "%" + maangerName + "%");
                    System.out.println("社区ID\t\t\t社区名称\t\t\t负责人id\t\t\t\t负责人姓名\t\t\t\t负责人电话");
                    for (Community community : list) {
                        System.out.println(community.getComId() + "\t\t\t\t" + community.getComName() + "\t\t\t" + community.getManageId() + "\t\t\t\t\t" + community.getManagerName() + "\t\t\t\t\t" + community.getTel());
                    }
                    break;
                case 4:
                    System.out.println("请输出社区负责人ID");
                    managerId = sc.nextInt();
                    sql = "select com_id,com_name,community.manage_id,name,tel from community,manager WHERE community.manage_id = manager.manage_id and manager.manage_id = ?;";
                    list = dao.queryCommunity(sql, Integer.toString(managerId));
                    System.out.println("社区ID\t\t\t社区名称\t\t\t负责人id\t\t\t\t负责人姓名\t\t\t\t负责人电话");
                    for (Community community : list) {
                        System.out.println(community.getComId() + "\t\t\t\t" + community.getComName() + "\t\t\t" + community.getManageId() + "\t\t\t\t\t" + community.getManagerName() + "\t\t\t\t\t" + community.getTel());
                    }
                    break;
                case 5:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱按");
                    break;
            }
            if (!flag) {
                break;
            }
        }
    }

    /*
     * 列出所有的社区
     * */
    public void listAllCommunity() {
        System.out.println("社区ID\t\t\t社区名称\t\t\t负责人id\t\t\t\t负责人姓名\t\t\t\t负责人电话");
        List<Community> list = dao.getAllCommunity();
        for (Community community : list) {
            System.out.println(community.getComId() + "\t\t\t\t" + community.getComName() + "\t\t\t" + community.getManageId() + "\t\t\t\t\t" + community.getManagerName() + "\t\t\t\t\t" + community.getTel());
        }
    }

    /*
     * 社区管理所有的功能汇总
     * */
    public void community() {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (true) {
            communityManage();
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    listAllCommunity();
                    break;
                case 2:
//                    查询社区
                    queryCommunity();
                    break;
                case 3:
//                    添加社区
                    addCommunity();
                    break;
                case 4:
                    deleteCommunity();
                    break;
                case 5:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱按");
                    break;
            }
            if (!flag) {
                break;
            }
        }

    }

    /*
     * 添加社区
     * */
    public void addCommunity() {
        System.out.println("请为新的社区设置名字名字");
        String comName = sc.next();
        System.out.println("请为这个新的社区设置一个负责人");
        System.out.println("现在给出还没有社区的负责人的信息");
        String sql = "select * from manager where manage_id not in (\n" +
                "\t\tselect manager.manage_id from manager,community where manager.manage_id = community.manage_id\n" +
                ");";
        List<Manager> list = dao.queryManager(sql);
        System.out.println("负责人id\t\t\t负责人名称\t\t\t负责人电话");
        for (Manager manager : list) {
            System.out.println(manager.getId() + "\t\t\t\t" + manager.getName() + "\t\t\t\t" + manager.getTel());
        }
        System.out.println("请输入负责人的id");
        int manageId = 0;
        boolean flag = false;
        while (true) {
            manageId = sc.nextInt();
            for (Manager manager : list) {
                if (manager.getId() == manageId) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
            if (!flag) {
                System.out.println("请看清楚了再选！请输入正确的ID");
            }
        }
        String sqlx = "insert into community values(null,?,?);";
        dao.insert(sqlx, comName, Integer.toString(manageId));
        System.out.println("添加成功");
    }

    /*
     * 删除社区
     * */
    public void deleteCommunity() {
        System.out.println("下面是所有的社区");
        listAllCommunity();
        System.out.println("请输入要删除的社区的id");
        boolean flag = false;
        int comId = 0;
        List<Community> list = dao.getAllCommunity();
        while (true) {
            comId = sc.nextInt();
            for (Community community : list) {
                if (community.getComId() == comId) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
            if (!flag) {
                System.out.println("请看清楚了再选！请输入正确的ID");
            }
        }
        int sum = dao.getCountByCommunityId(Integer.toString(comId));
        if (sum != 0) {
            System.out.println("该社区目前还有人住！不可以删除");
        } else {
            String sql = "delete from community where com_id = ?";
            dao.execute(sql, Integer.toString(comId));

            System.out.println("删除成功");
        }

    }

    /*
     * 列出所有的负责人
     * */
    public void listAllManager() {
        List<Manager> list = dao.getAllManager();
        System.out.println("负责人id\t\t\t负责人姓名\t\t\t负责人电话");
        for (Manager manager : list) {
            System.out.println(manager.getId() + "\t\t\t\t" + manager.getName() + "\t\t\t\t" + manager.getTel());
        }

    }

    /*
     * 查询负责人
     * */
    public void queryManager() {

        System.out.println("1.根据负责人id查询");
        System.out.println("2. 根据负责人姓名查找");

        System.out.println("请输入");
        int key2 = sc.nextInt();
        String sql = null;
        switch (key2) {
            case 1:
                System.out.println("请输入负责人id");
                int manageId = sc.nextInt();
                sql = "select * from manager where manage_id = ?";
                List<Manager> list = dao.queryManager(sql, Integer.toString(manageId));
                System.out.println("负责人id\t\t\t负责人姓名\t\t\t负责人电话");
                for (Manager manager : list) {
                    System.out.println(manager.getId() + "\t\t\t\t" + manager.getName() + "\t\t\t\t" + manager.getTel());
                }
                break;
            case 2:
                System.out.println("请输入负责人姓名");
                String name = sc.next();
                sql = "select * from manager where name like ?";
                list = dao.queryManager(sql, "%" + name + "%");
                System.out.println("负责人id\t\t\t负责人姓名\t\t\t负责人电话");
                for (Manager manager : list) {
                    System.out.println(manager.getId() + "\t\t\t\t" + manager.getName() + "\t\t\t\t" + manager.getTel());
                }
                break;
            default:
                System.out.println("没有这个选项,别乱按");
                break;
        }
    }


    /*
     * 汇总负责人管理的所有功能
     * */
    public void manager() {
        boolean flag = true;
        int key = 0;
        while (true) {
            managerManagePage();
            key = sc.nextInt();
            switch (key) {
                case 1:
                    addManager();
                    break;
                case 2:
                    listAllManager();
                    break;
                case 3:
                    queryManager();
                    break;
                case 4:
                    updateManager();
                    break;
                case 5:
                    deleteManager();
                    break;
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱按");
                    break;
            }
            if (!flag) {
                break;
            }
        }

    }

    /*
     * 修改负责人信息
     * */
    public void updateManager() {

        System.out.println("请输入负责人id");
        int managerId = sc.nextInt();
        List<Manager> list = dao.getAllManager();
        Manager people = null;
        boolean flag = false;
        for (Manager manager : list) {
            if (manager.getId() == managerId) {
                flag = true;
                people = manager;
                break;
            }
        }
        if (!flag) {
            System.out.println("请输入正确的负责人id，此id不存在");
        } else {
            System.out.println("负责人id\t\t\t负责人姓名\t\t\t负责人电话\t\t\t负责人密码");
            System.out.println(people.getId() + "\t\t\t\t" + people.getName() + "\t\t\t\t" + people.getTel() + "\t\t\t\t" + people.getPassword());
            System.out.println("请依次输入负责人的修改后的姓名、电话、密码");
            System.out.println("请输入修改后的姓名");
            String name = sc.next();
            System.out.println("请输入修改后的电话");
            String tel = sc.next();
            System.out.println("请输入修改后的密码");
            String password = sc.next();
            String sql = "update manager set name = ?, tel = ?, password = ?  where manage_id = ?";
            dao.execute(sql, name, tel, password, Integer.toString(managerId));
            System.out.println("修改成功");
        }
    }

    /*
     * 删除负责人
     * */
    public void deleteManager() {

        System.out.println("在删除负责人之前，请确保该负责人已经没有负责的社区!!!");
        System.out.println("请输入负责人id");
        int managerId = sc.nextInt();
        List<Manager> list = dao.getAllManager();
        boolean flag = false;
        for (Manager manager : list) {
            if (manager.getId() == managerId) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("请输入正确的负责人id，此id不存在");
        } else {
            String sql = "select * from manager where manage_id not in (\n" +
                    "\t\tselect manager.manage_id from manager,community where manager.manage_id = community.manage_id\n" +
                    ") and manage_id = ?;";
            list = dao.queryManager(sql, Integer.toString(managerId));
            if (list.size() == 0) {
                System.out.println("该负责人仍然负责某一社区，不可删除");
            } else {
                String sqlx = "delete from manager where manage_id = ?";
                dao.execute(sqlx, Integer.toString(managerId));
                System.out.println("删除成功");
            }
        }
    }

    /*
     * 添加负责人
     * */
    public void addManager() {
        System.out.println("请依次输入负责人的姓名、电话、密码");
        System.out.println("请输入负责人的姓名");
        String name = sc.next();
        System.out.println("请输入负责人电话");
        String tel = sc.next();
        System.out.println("请设置负责人的密码");
        String password = sc.next();
        String sql = "insert into manager(password,name,tel) values(?,?,?);";
        dao.execute(sql, password, name, tel);
        System.out.println("添加成功");
    }

    /*
     *
     * 修改管理员密码
     * */
    /*
     * 记录一下登录状态
     * */
    private boolean loginState = true;

    public boolean isLoginState() {
        return loginState;
    }

    /*
     * 系统管理员修改密码
     * */
    public void updatePassword() {
        System.out.println("请输入修改后的新密码");
        String p1 = sc.next();
        System.out.println("请再输入一次");
        String p2 = sc.next();
        if (p1.equals(p2)) {
            String sql = "update admin set password = ? where username = 001";
            dao.execute(sql, p1);
            System.out.println("修改成功！请重新登录");
            loginState = false;
        } else {
            System.out.println("两次的输入不一样！修改失败");
        }
    }


    /*
     * 列出所有居民
     * */
    public void getAllResident() {
        int key;
        boolean flag = true;
        while (true) {
            System.out.println("1. 根据社区id查看");
            System.out.println("2. 根据社区名称查看");
            System.out.println("3. 返回");
            key = sc.nextInt();
            switch (key) {
                case 1:
                    System.out.println("下面是所有社区的信息");
                    System.out.println("社区ID\t\t\t社区名称\t\t\t负责人id\t\t\t\t负责人姓名\t\t\t\t负责人电话");
                    List<Community> list = dao.getAllCommunity();
                    for (Community community : list) {
                        System.out.println(community.getComId() + "\t\t\t\t" + community.getComName() + "\t\t\t" + community.getManageId() + "\t\t\t\t\t" + community.getManagerName() + "\t\t\t\t\t" + community.getTel());
                    }
                    System.out.println("请输入社区id");
                    int comId = sc.nextInt();
                    String sql = "select * from resident where com_id = ?";
                    List<Resident> listp = dao.queryResident(sql, Integer.toString(comId));
                    System.out.println("id\t\t\t姓名\t\t\t性别\t\t\t电话\t\t\t详细地址");
                    for (Resident resident : listp) {
                        System.out.println(resident.getId() + "\t\t\t" + resident.getName() + "\t\t\t" + resident.getSex() + "\t\t\t" + resident.getTel() + "\t\t\t" + resident.getAddress());
                    }
                    break;
                case 2:
                    System.out.println("下面是所有社区的信息");
                    System.out.println("社区ID\t\t\t社区名称\t\t\t负责人id\t\t\t\t负责人姓名\t\t\t\t负责人电话");
                    List<Community> listc = dao.getAllCommunity();
                    for (Community community : listc) {
                        System.out.println(community.getComId() + "\t\t\t\t" + community.getComName() + "\t\t\t" + community.getManageId() + "\t\t\t\t\t" + community.getManagerName() + "\t\t\t\t\t" + community.getTel());
                    }
                    System.out.println("请输入社区名称");
                    String comName = sc.next();
                    sql = "select id,name,sex,tel,address from community,resident where community.com_id = resident.com_id and community.com_name like ?;";
                    List<Resident> listpp = dao.queryResident(sql, "%" + comName + "%");
                    System.out.println("id\t\t\t姓名\t\t\t性别\t\t\t电话\t\t\t详细地址");
                    for (Resident resident : listpp) {
                        System.out.println(resident.getId()+ "\t\t\t"+resident.getName() + "\t\t\t" + resident.getSex() + "\t\t\t" + resident.getTel() + "\t\t\t" + resident.getAddress());
                    }
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱按");
                    break;
            }
            if (!flag) {
                break;
            }
        }

    }

    /*
     * 查询居民
     * */
    public void queryResident() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 根据id查找");
            System.out.println("2. 根据名称查找");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    System.out.println("请输入要查询人员的id");
                    int id = sc.nextInt();
                    String sql = "select id,name,community.com_id,com_name,sex,tel,address from community,resident where community.com_id = resident.com_id and id = ?;";
                    ResultSet rs = dao.queryOneResident(sql, Integer.toString(id));
                    System.out.println("id\t\t\t姓名\t\t\t社区id\t\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t详细地址");
                    try {
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + "\t\t\t" + rs.getString("name") + "\t\t\t"
                                    + rs.getInt("com_id") + "\t\t\t" + rs.getString("com_name") + "\t\t\t" + rs.getString("sex")
                                    + "\t\t\t" + rs.getString("tel") + "\t\t\t" + rs.getString("address"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("请输入要查询人员的姓名");
                    String name = sc.next();
                    sql = "select id,name,community.com_id,com_name,sex,tel,address from community,resident where community.com_id = resident.com_id and name like ? ;";
                    rs = dao.queryOneResident(sql, "%" + name + "%");
                    System.out.println("id\t\t\t姓名\t\t\t社区id\t\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t详细地址");
                    try {
                        while (rs.next()) {
                            System.out.println(rs.getInt("id") + "\t\t\t" + rs.getString("name") + "\t\t\t"
                                    + rs.getInt("com_id") + "\t\t\t" + rs.getString("com_name") + "\t\t\t" + rs.getString("sex")
                                    + "\t\t\t" + rs.getString("tel") + "\t\t\t" + rs.getString("address"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

    /*
     * 修改居民信息
     * */
    public void updateResident() {
        System.out.println("请输入要修改的居民的id");
        int id = sc.nextInt();

        String sql = "select id,name,community.com_id,com_name,sex,tel,address from community,resident where community.com_id = resident.com_id and id = ?;";
        ResultSet rs = dao.queryOneResident(sql, Integer.toString(id));
        try {
            if (!rs.next()) {
                System.out.println("没有这个人呦！！！");
            } else {
                System.out.println("这是这个人的信息");
                System.out.println("id\t\t\t姓名\t\t\t社区id\t\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t详细地址");
                System.out.println(rs.getInt("id") + "\t\t\t" + rs.getString("name") + "\t\t\t"
                        + rs.getInt("com_id") + "\t\t\t" + rs.getString("com_name") + "\t\t\t" + rs.getString("sex")
                        + "\t\t\t" + rs.getString("tel") + "\t\t\t" + rs.getString("address"));

                System.out.println("请按照如下特定顺序输入修改后的信息");
                System.out.println("姓名\t\t\t社区id\t\t\t性别\t\t\t电话\t\t\t详细地址");
                String name = sc.next();
                int comId = sc.nextInt();
                String sex = sc.next();
                String tel = sc.next();
                String address = sc.next();
                sql = "update resident set name = ?, com_id = ?, sex = ?, tel = ? , address = ? where id = ?";
                dao.execute(sql, name, Integer.toString(comId), sex, tel, address, Integer.toString(id));
                System.out.println("修改成功");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     *
     * 删除 居民
     * */
    public void deleteResident() {
        System.out.println("请输入要删除的人的id");
        int id = sc.nextInt();
        String sql = "select * from resident where id = ?";
        List<Resident> list = dao.queryResident(sql, Integer.toString(id));
        if (list.size() == 0) {
            System.out.println("没有这个人");
        } else {
            System.out.println("id\t\t\t姓名\t\t\t性别\t\t\t电话\t\t\t详细地址");
            for (Resident resident : list) {
                System.out.println(resident.getName() + "\t\t\t" + resident.getSex() + "\t\t\t" + resident.getTel() + "\t\t\t" + resident.getAddress());
            }
            System.out.println("您确定删除吗？");
            System.out.println("请输入 是 或 否");
            String key = sc.next();
            if ("是".equals(key)) {
                sql = "delete from resident where id= ?";
                dao.execute(sql, Integer.toString(id));
                System.out.println("删除成功");
            } else {
                System.out.println("您已经取消删除");
            }
        }

    }

    /*
     * 添加居民
     * */
    public void addResident() {
        System.out.println("请依次输入要添加人员的信息");
        System.out.println("姓名\t\t\t性别\t\t\t电话\t\t\t详细地址\t\t\t社区id\t\t\t密码");
        String name = sc.next();
        String sex = sc.next();
        String tel = sc.next();
        String address = sc.next();
        int comId = sc.nextInt();
        String password = sc.next();
        String sql = "insert into resident(password,name,com_id,sex,tel,address) values(?,?,?,?,?,?);";
        dao.execute(sql, password, name, Integer.toString(comId), sex, tel, address);
        System.out.println("添加成功");
    }


    /*
     * 居民管理功能汇总
     * */
    public void resident() {
        int key;
        boolean flag = true;
        while (true) {
            residentAdminPage();
            key = sc.nextInt();
            switch (key) {
                case 1:
                    getAllResident();
                    break;
                case 2:
                    queryResident();
                    break;
                case 3:
                    updateResident();
                    break;
                case 4:
                    deleteResident();
                    break;
                case 5:
                    addResident();
                    break;
                case 6:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项！别乱按");
                    break;

            }

            if (!flag) {
                break;
            }
        }

    }

    /*
     * 查看返回社区申请
     * */
    public void returnApply() {
        boolean flag = true;
        ApplyDao dao = new ApplyDao();
        while (true) {
            System.out.println("1. 查看所有的返回社区申请");
            System.out.println("2. 根据id查看某人返回社区申请");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    List<ApplyIn> list = dao.getAllApplyIn();
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyIn apply : list) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getArrTime()
                                + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }

                    break;
                case 2:
                    System.out.println("请输入要查询人员的id");
                    int id = sc.nextInt();
                    String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address , nowtime,arrtime,start,end,state,remark " +
                            "from community,resident,applyin " +
                            "where resident.id = applyin.id and community.com_id = resident.com_id and resident.id = ?;";
                    list = dao.queryApplyIn(sql, Integer.toString(id));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t预计到达时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyIn apply : list) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getArrTime()
                                + "\t\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项，别乱按");
                    break;
            }
            if (!flag) {
                break;
            }
        }
    }

    /*
     * 查看离开社区申请
     * */
    public void leaveApply() {
        boolean flag = true;
        ApplyDao dao = new ApplyDao();
        while (true) {
            System.out.println("1. 查看所有的离开社区申请");
            System.out.println("2. 根据id查看某人离开社区申请");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    List<ApplyOut> list = dao.getAllApplyOut();
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t离开时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyOut apply : list) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getLeaveTime()
                                + "\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }

                    break;
                case 2:
                    System.out.println("请输入要查询人员的id");
                    int id = sc.nextInt();
                    String sql = "select idx, resident.id,name,resident.com_id,com_name,sex,tel,address,nowtime,leavetime,start,end,state,remark from community,resident,applyout \n" +
                            "where resident.id = applyout.id and community.com_id = resident.com_id and resident.id = ?;";
                    List<ApplyOut> listp = dao.queryApplyOut(sql, Integer.toString(id));
                    System.out.println("记录编号\t\t人员id\t\t人员姓名\t\t社区id\t\t社区名称\t\t\t性别\t\t\t电话\t\t\t\t家庭地址" +
                            "\t\t\t提交时间\t\t\t\t\t离开时间\t\t\t出发地\t\t\t目的地\t\t\t状态\t\t\t备注");
                    for (ApplyOut apply : listp) {
                        System.out.println(apply.getIdx() + "\t\t\t" + apply.getId() + "\t\t\t" + apply.getName() + "\t\t\t" + apply.getComId() + "\t\t\t" + apply.getComName()
                                + "\t\t\t" + apply.getSex() + "\t\t\t" + apply.getTel() + "\t" + apply.getAddress() + "\t" + apply.getNowTime() + "\t\t" + apply.getLeaveTime()
                                + "\t\t" + apply.getStart() + "\t\t\t" + apply.getEnd() + "\t\t\t" + apply.getState() + "\t\t" + apply.getRemark());
                    }
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("没有这个选项，别乱按");
                    break;
            }
            if (!flag) {
                break;
            }
        }


    }


    /*
     * 申请管理功能汇总
     *
     * */
    public void apply() {
        boolean flag = true;
        while (true) {
            System.out.println("1. 查看返回社区申请");
            System.out.println("2. 查看离开社区申请");
            System.out.println("3. 返回");
            int key = sc.nextInt();
            switch (key) {
                case 1:
                    returnApply();
                    break;
                case 2:
                    leaveApply();
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

    /*
     * 测试
     * */
    public static void main(String[] args) {
        AdminControll con = new AdminControll();
//        con.manager();
//        con.resident();
        con.apply();
    }
}
