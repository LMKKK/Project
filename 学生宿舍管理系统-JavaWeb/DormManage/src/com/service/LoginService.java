package com.service;

import com.dao.UserDao;
import com.model.Admin;
import com.model.DormManager;
import com.model.Student;

import java.sql.Connection;

/**
 * 用户业务层
 */
public class LoginService {

    private UserDao userDao = new UserDao();

    /**
     * 学生登录
     *
     * @param con
     * @param stu
     * @return
     */
    public Student stuLogin(Connection con, Student stu) {
        Student user = null;
        try {
            user = userDao.Login(con, stu);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * 系统管理员登录
     *
     * @param con
     * @param admin
     * @return
     */
    public Admin adminLogin(Connection con, Admin admin) {
        Admin user = null;
        try {
            user = userDao.Login(con, admin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * 宿舍管理员 登录
     *
     * @param con
     * @param manager
     * @return
     */
    public DormManager managerLogin(Connection con, DormManager manager) {
        DormManager user = null;
        try {
            user = userDao.Login(con, manager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

}
