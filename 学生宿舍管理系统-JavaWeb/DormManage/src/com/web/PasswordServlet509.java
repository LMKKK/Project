package com.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao509;
import com.model.Admin509;
import com.model.DormManager502;
import com.model.Student517;
import com.util.DBUtils;
import com.util.MD5Util;

@WebServlet("/password")
public class PasswordServlet509 extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    UserDao509 userDao509 = new UserDao509();

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
        Object currentUserType = session.getAttribute("currentUserType");
        String oldPassword = request.getParameter("oldPassword");
        oldPassword = MD5Util.encoderPwdByMD5(oldPassword);
        String newPassword = request.getParameter("newPassword");
        Connection con = null;
        try {
            con = dbUtil.getCon();

            if ("admin".equals((String) currentUserType)) {
                Admin509 admin509 = (Admin509) (session.getAttribute("currentUser"));
                if (oldPassword.equals(admin509.getPassword())) {
                    userDao509.adminUpdate(con, admin509.getAdminId(), newPassword);
                    admin509.setPassword(newPassword);
                    request.setAttribute("oldPassword", oldPassword);
                    request.setAttribute("newPassword", newPassword);
                    request.setAttribute("rPassword", newPassword);
                    request.setAttribute("error", "修改成功 ");
                    request.setAttribute("mainPage", "admin/passwordChange509.jsp");
                    request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                } else {
                    request.setAttribute("oldPassword", oldPassword);
                    request.setAttribute("newPassword", newPassword);
                    request.setAttribute("rPassword", newPassword);
                    request.setAttribute("error", "原密码错误");
                    request.setAttribute("mainPage", "admin/passwordChange509.jsp");
                    request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                }
            } else if ("dormManager".equals((String) currentUserType)) {
                DormManager502 manager = (DormManager502) (session.getAttribute("currentUser"));
                if (oldPassword.equals(manager.getPassword())) {
                    userDao509.managerUpdate(con, manager.getDormManagerId(), newPassword);
                    manager.setPassword(newPassword);
                    request.setAttribute("oldPassword", oldPassword);
                    request.setAttribute("newPassword", newPassword);
                    request.setAttribute("rPassword", newPassword);
                    request.setAttribute("error", "修改成功 ");
                    request.setAttribute("mainPage", "dormManager/passwordChange509.jsp");
                    request.getRequestDispatcher("mainManager.jsp").forward(request, response);
                } else {
                    request.setAttribute("oldPassword", oldPassword);
                    request.setAttribute("newPassword", newPassword);
                    request.setAttribute("rPassword", newPassword);
                    request.setAttribute("error", "原密码错误");
                    request.setAttribute("mainPage", "dormManager/passwordChange509.jsp");
                    request.getRequestDispatcher("mainManager.jsp").forward(request, response);
                }
            } else if ("student".equals((String) currentUserType)) {
                Student517 student517 = (Student517) (session.getAttribute("currentUser"));
                if (oldPassword.equals(student517.getPassword())) {
                    userDao509.studentUpdate(con, student517.getStudentId(), newPassword);
                    student517.setPassword(newPassword);
                    request.setAttribute("oldPassword", oldPassword);
                    request.setAttribute("newPassword", newPassword);
                    request.setAttribute("rPassword", newPassword);
                    request.setAttribute("error", "修改成功 ");
                    request.setAttribute("mainPage", "student/passwordChange509.jsp");
                    request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
                } else {
                    request.setAttribute("oldPassword", oldPassword);
                    request.setAttribute("newPassword", newPassword);
                    request.setAttribute("rPassword", newPassword);
                    request.setAttribute("error", "原密码错误");
                    request.setAttribute("mainPage", "student/passwordChange509.jsp");
                    request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
                }
            }
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
        Object currentUserType = session.getAttribute("currentUserType");
        if ("admin".equals((String) currentUserType)) {
            request.setAttribute("mainPage", "admin/passwordChange509.jsp");
            request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
        } else if ("dormManager".equals((String) currentUserType)) {
            request.setAttribute("mainPage", "dormManager/passwordChange509.jsp");
            request.getRequestDispatcher("mainManager.jsp").forward(request, response);
        } else if ("student".equals((String) currentUserType)) {
            request.setAttribute("mainPage", "student/passwordChange509.jsp");
            request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
        }
    }

}
