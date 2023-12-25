package com.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.constrant.PageConstrant;
import com.constrant.UserType;
import com.dao.UserDao;
import com.model.Admin;
import com.model.DormManager;
import com.model.Student;
import com.util.DBUtils;
import com.util.MD5Util;

@WebServlet("/password")
public class PasswordServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");

        if ("preChange".equals(action)) {
            passwordPreChange(request, response);
            return;
        } else if ("change".equals(action)) {
            passwordChange(request, response);
            return;
        }
    }

    private void passwordChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentUserType = (String) session.getAttribute("currentUserType");
        String oldPassword = request.getParameter("oldPassword");
        // 由于MD5 加密是 单向的， 所以这里需要将用户输入的密码进行加密后再与数据库中的密码进行比较
        String encoderPwdByMD5 = MD5Util.encoderPwdByMD5(oldPassword); // MD5加密
        String newPassword = request.getParameter("newPassword");
        Connection con = null;
        String mainPage = "";
        String userPage = "";
        String successMsg = "修改成功";
        String errorMsg = "原密码错误, 请重新输入";
        try {
            con = dbUtil.getCon();
            String msg = "";
            if (UserType.ADMIN.equals(currentUserType)) {
                Admin admin = (Admin) (session.getAttribute("currentUser"));
                if (encoderPwdByMD5.equals(admin.getPassword())) {
                    userDao.adminUpdate(con, admin.getAdminId(), newPassword);
                    admin.setPassword(newPassword);
                    msg = successMsg;
                } else {
                    msg = errorMsg;
                }
                mainPage = "admin/passwordChange.jsp";
                userPage = PageConstrant.ADMIN_MAIN_PAGE;
            } else if (UserType.DORM_MANAGER.equals(currentUserType)) {
                DormManager manager = (DormManager) (session.getAttribute("currentUser"));
                if (encoderPwdByMD5.equals(manager.getPassword())) {
                    userDao.managerUpdate(con, manager.getDormManagerId(), newPassword);
                    msg = successMsg;
                } else {
                    msg = errorMsg;
                }
                mainPage = "dormManager/passwordChange.jsp";
                userPage = PageConstrant.MANAGER_MAIN_PAGE;
            } else if (UserType.STUDENT.equals(currentUserType)) {
                Student student = (Student) (session.getAttribute("currentUser"));
                if (encoderPwdByMD5.equals(student.getPassword())) {
                    userDao.studentUpdate(con, student.getStudentId(), newPassword);
                    msg = successMsg;
                } else {
                    msg = errorMsg;
                }
                mainPage = "student/passwordChange.jsp";
                userPage = PageConstrant.STUDENT_MAIN_PAGE;
            }

            request.setAttribute("oldPassword", oldPassword);
            request.setAttribute("newPassword", newPassword);
            request.setAttribute("rPassword", newPassword);
            request.setAttribute("error", msg);
            request.setAttribute(PageConstrant.MAIN_PAGE, mainPage);
            // 修改成功，重新登陆, 重新获取用户信息
            if (successMsg.equals(msg)) {
                request.getRequestDispatcher("logout").forward(request, response);
                return;
            }
            request.getRequestDispatcher(userPage).forward(request, response);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void passwordPreChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String currentUserType = (String) session.getAttribute("currentUserType");
        String mainPage = "";
        String userPage = "";
        if (UserType.ADMIN.equals(currentUserType)) {
            // 管理员
            mainPage = "admin/passwordChange.jsp";
            userPage = PageConstrant.ADMIN_MAIN_PAGE;
        } else if (UserType.DORM_MANAGER.equals(currentUserType)) {
            // 宿舍管理员
            mainPage = "dormManager/passwordChange.jsp";
            userPage = PageConstrant.MANAGER_MAIN_PAGE;
        } else if (UserType.STUDENT.equals(currentUserType)) {
            // 学生
            mainPage = "student/passwordChange.jsp";
            userPage = PageConstrant.STUDENT_MAIN_PAGE;
        }
        request.setAttribute(PageConstrant.MAIN_PAGE, mainPage);
        request.getRequestDispatcher(userPage).forward(request, response);
    }

}
