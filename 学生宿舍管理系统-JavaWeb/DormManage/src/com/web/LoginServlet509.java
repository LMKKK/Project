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

import com.dao.UserDao509;
import com.model.Admin509;
import com.model.DormManager502;
import com.model.Student517;
import com.util.DBUtils;

/**
 * ���ܣ�ʵ���û���¼
 */
@WebServlet("/login")
public class LoginServlet509 extends HttpServlet {

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
        HttpSession session = request.getSession();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        String userType = request.getParameter("userType");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            /*      ��������ϵͳ��ɫ��Ȼ�����������ҳ�洫������userType������new     */
            Admin509 currentAdmin509 = null;
            DormManager502 currentDormManager502 = null;
            Student517 currentStudent517 = null;
//            �ж��Ƿ���ϵͳ����Ա��
            if ("admin".equals(userType)) {
                Admin509 admin509 = new Admin509(userName, password);
                currentAdmin509 = userDao509.Login(con, admin509);
                /*  �û�������������޷�ʵ�������󣬶���Ϊnull  */
                if (currentAdmin509 == null) {
                    /*��¼ʧ�ܣ��ض��򵽵�¼ҳ��*/
//                    request.setAttribute("admin", admin);
                    request.setAttribute("user", admin509);
                    request.setAttribute("error", "�û������������");
                    System.out.println("����Ա�������!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    /*
                     * ��¼�ɹ���ת����������Աҳ��
                     * */
                    if ("remember-me".equals(remember)) {
                        rememberMe(userName, password, userType, response);
                    } else {
                        deleteCookie(userName, request, response);
                    }
                    session.setAttribute("currentUserType", "admin");
                    session.setAttribute("currentUser", currentAdmin509);
                    request.setAttribute("mainPage", "admin/blank.jsp");
                    System.out.println("����Ա�ɹ���¼");
                    request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                }
            } else if ("dormManager".equals(userType)) {
//                �ж��Ƿ����������Ա
                DormManager502 dormManager502 = new DormManager502(userName, password);
                currentDormManager502 = userDao509.Login(con, dormManager502);
                if (currentDormManager502 == null) {
//                    request.setAttribute("dormManager", dormManager);
                    request.setAttribute("user", dormManager502);
                    request.setAttribute("error", "�û������������");
                    System.out.println("����Ա�������");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    if ("remember-me".equals(remember)) {
                        rememberMe(userName, password, userType, response);
                    } else {
                        deleteCookie(userName, request, response);
                    }
                    session.setAttribute("currentUserType", "dormManager");
                    session.setAttribute("currentUser", currentDormManager502);
                    request.setAttribute("mainPage", "dormManager/blank.jsp");
                    System.out.println("����Ա�ɹ���¼");
                    request.getRequestDispatcher("mainManager.jsp").forward(request, response);
                }
            } else if ("student".equals(userType)) {
//                �ж��Ƿ���ѧ��
                Student517 student517 = new Student517(userName, password);
                currentStudent517 = userDao509.Login(con, student517);
                if (currentStudent517 == null) {
//                    request.setAttribute("student", student);
                    request.setAttribute("user", student517);
                    request.setAttribute("error", "�û������������");
                    System.out.println("ѧ����¼ʧ��");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    if ("remember-me".equals(remember)) {
                        rememberMe(userName, password, userType, response);
                    } else {
                        deleteCookie(userName, request, response);
                    }
                    session.setAttribute("currentUserType", "student");
                    session.setAttribute("currentUser", currentStudent517);
                    request.setAttribute("mainPage", "student/blank.jsp");
                    System.out.println("ѧ����¼�ɹ�");
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

    /*
     * �Զ��巽����������ͻ��˴洢�û���Cookie��Ϣ
     * dorumuser: �˺���-����-��ɫ����-yes
     * */
    private void rememberMe(String userName, String password, String userType, HttpServletResponse response) {
        Cookie user = new Cookie("dormuser", userName + "-" + password + "-" + userType + "-" + "yes");
        /*����Cookie�Ĵ��ʱ��Ϊ7��  */
        user.setMaxAge(1 * 60 * 60 * 24 * 7);
        response.addCookie(user);
    }

    /*
     * �Զ��巽����ɾ���ͻ���Cookie
     * */
    private void deleteCookie(String userName, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("dormuser")) {
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
