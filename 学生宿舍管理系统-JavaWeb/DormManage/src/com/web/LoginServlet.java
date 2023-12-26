package com.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.constrant.LoginConstrant;
import com.constrant.PageConstrant;
import com.constrant.UserType;
import com.dao.UserDao;
import com.model.Admin;
import com.model.DormManager;
import com.model.Student;
import com.service.LoginService;
import com.util.DBUtils;
import com.util.StringUtil;

/**
 * 介绍：实现用户登录
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private LoginService loginService = new LoginService();

    DBUtils dbUtil = new DBUtils();
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        String userType = request.getParameter("userType");
        // 前端已经限制了用户名、密码、用户类型 必填， 此处再做一次参数过滤
        String errMsg = null;
        if (StringUtil.isEmpty(userName)) {
            errMsg = "用户名不能为空";
        }
        if (StringUtil.isEmpty(password)) {
            errMsg = "密码不能为空";
        }
        if (StringUtil.isEmpty(userType)) {
            errMsg = "请选择用户类型";
        }

        if (StringUtil.isNotEmpty(errMsg)) {
            request.setAttribute("user", null);
            request.setAttribute("error", errMsg);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // 到此为止，参数过滤完成，开始真正的业务操作
        Connection con = null;
        try {
            con = dbUtil.getCon();
            /*      声明三个系统角色，然后接下来根据页面传过来的userType属性来new     */
            Admin currentAdmin = null;
            DormManager currentDormManager = null;
            Student currentStudent = null;
            String loginErrMsg = "用户名或密码错误！"; // 登录错误信息
            // 判断是否是系统管理员
            if (UserType.ADMIN.equals(userType)) {
                Admin admin = new Admin(userName, password);
                currentAdmin = loginService.adminLogin(con, admin);
                if (currentAdmin == null) {
                    /*登录失败，重定向到登录页面*/
                    admin.setPassword(password); // 为了保证密码框中的密码不被清空，将密码重新设置到admin对象中
                    request.setAttribute("user", admin);
                    request.setAttribute("error", loginErrMsg);
                    request.setAttribute("userType", userType);
                    System.out.println("[info]----管理员密码错误!");
                    // 重新返回登录页面
                    request.getRequestDispatcher(PageConstrant.LOGIN_PAGE).forward(request, response);
                } else {
                    rememberMe(remember, userName, password, userType, request, response);
                    session.setAttribute(LoginConstrant.CURRENT_USER_TYPE, UserType.ADMIN);
                    session.setAttribute(LoginConstrant.CURRENT_USER, currentAdmin);
                    request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.ADMIN_BLANK_PAGE);
                    System.out.println("[info]----管理员成功登录");
                    // 登录成功，转发到到管理员页面
                    request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
                }
            } else if (UserType.DORM_MANAGER.equals(userType)) {
//                判断是否是宿舍管理员
                DormManager dormManager = new DormManager(userName, password);
                currentDormManager = loginService.managerLogin(con, dormManager);
                if (currentDormManager == null) {
                    dormManager.setPassword(password);
                    request.setAttribute("user", dormManager);
                    request.setAttribute("error", loginErrMsg);
                    request.setAttribute("userType", userType);
                    System.out.println("[info]----宿管人员密码错误！");

                    request.getRequestDispatcher(PageConstrant.LOGIN_PAGE).forward(request, response);
                } else {
                    rememberMe(remember, userName, password, userType, request, response);
                    session.setAttribute(LoginConstrant.CURRENT_USER_TYPE, UserType.DORM_MANAGER);
                    session.setAttribute(LoginConstrant.CURRENT_USER, currentDormManager);
                    request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.MANAGER_BLANK_PAGE);
                    System.out.println("[info]----宿管人员成功登录");
                    request.getRequestDispatcher(PageConstrant.MANAGER_MAIN_PAGE).forward(request, response);
                }
            } else if (UserType.STUDENT.equals(userType)) {
//                判断是否是学生
                Student student = new Student(userName, password);
                currentStudent = userDao.Login(con, student);
                if (currentStudent == null) {
                    student.setPassword(password);
                    request.setAttribute("user", student);
                    request.setAttribute("error", "用户名或密码错误！");
                    request.setAttribute("userType", userType);
                    System.out.println("[info]----学生登录失败");
                    request.getRequestDispatcher(PageConstrant.LOGIN_PAGE).forward(request, response);
                } else {
                    rememberMe(remember, userName, password, userType, request, response);
                    session.setAttribute(LoginConstrant.CURRENT_USER_TYPE, UserType.STUDENT);
                    session.setAttribute(LoginConstrant.CURRENT_USER, currentStudent);
                    request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.STUDENT_BLANK_PAGE);
                    System.out.println("[info]----学生登录成功");
                    request.getRequestDispatcher(PageConstrant.STUDENT_MAIN_PAGE).forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放数据库连接
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 自定义方法：
     *          处理"记住我"这个功能，如果用户选择了"记住我"，则保存7的Cookie
     *          否则删除客户端中与本系统相关的Cookie
     * dorumUser: 账号名-密码-角色类型-yes
     * */
    private void rememberMe(String remember,
                            String userName,
                            String password,
                            String userType,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (LoginConstrant.REMEMBER_ME.equals(remember)) {
            // 记住我
            Cookie user = new Cookie(LoginConstrant.COOKIE_KEY, userName + "-" + password + "-" + userType + "-" + "yes");
            /*设置Cookie的存活时间为7天  */
            user.setMaxAge(LoginConstrant.COOKIE_TIME_OUT);
            response.addCookie(user);
        } else {
            // 删除客户端Cookie
            deleteCookie(userName, request, response);
        }
    }

    /*
     * 自定义方法：删除客户端Cookie
     * */
    private void deleteCookie(String userName, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (LoginConstrant.COOKIE_KEY.equals(cookies[i].getName())) {
                if (userName.equals(userName = cookies[i].getValue().split("-")[0])) {
                    Cookie cookie = new Cookie(cookies[i].getName(), null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
}
