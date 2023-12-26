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
 * ���ܣ�ʵ���û���¼
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
        // ǰ���Ѿ��������û��������롢�û����� ��� �˴�����һ�β�������
        String errMsg = null;
        if (StringUtil.isEmpty(userName)) {
            errMsg = "�û�������Ϊ��";
        }
        if (StringUtil.isEmpty(password)) {
            errMsg = "���벻��Ϊ��";
        }
        if (StringUtil.isEmpty(userType)) {
            errMsg = "��ѡ���û�����";
        }

        if (StringUtil.isNotEmpty(errMsg)) {
            request.setAttribute("user", null);
            request.setAttribute("error", errMsg);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // ����Ϊֹ������������ɣ���ʼ������ҵ�����
        Connection con = null;
        try {
            con = dbUtil.getCon();
            /*      ��������ϵͳ��ɫ��Ȼ�����������ҳ�洫������userType������new     */
            Admin currentAdmin = null;
            DormManager currentDormManager = null;
            Student currentStudent = null;
            String loginErrMsg = "�û������������"; // ��¼������Ϣ
            // �ж��Ƿ���ϵͳ����Ա
            if (UserType.ADMIN.equals(userType)) {
                Admin admin = new Admin(userName, password);
                currentAdmin = loginService.adminLogin(con, admin);
                if (currentAdmin == null) {
                    /*��¼ʧ�ܣ��ض��򵽵�¼ҳ��*/
                    admin.setPassword(password); // Ϊ�˱�֤������е����벻����գ��������������õ�admin������
                    request.setAttribute("user", admin);
                    request.setAttribute("error", loginErrMsg);
                    request.setAttribute("userType", userType);
                    System.out.println("[info]----����Ա�������!");
                    // ���·��ص�¼ҳ��
                    request.getRequestDispatcher(PageConstrant.LOGIN_PAGE).forward(request, response);
                } else {
                    rememberMe(remember, userName, password, userType, request, response);
                    session.setAttribute(LoginConstrant.CURRENT_USER_TYPE, UserType.ADMIN);
                    session.setAttribute(LoginConstrant.CURRENT_USER, currentAdmin);
                    request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.ADMIN_BLANK_PAGE);
                    System.out.println("[info]----����Ա�ɹ���¼");
                    // ��¼�ɹ���ת����������Աҳ��
                    request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
                }
            } else if (UserType.DORM_MANAGER.equals(userType)) {
//                �ж��Ƿ����������Ա
                DormManager dormManager = new DormManager(userName, password);
                currentDormManager = loginService.managerLogin(con, dormManager);
                if (currentDormManager == null) {
                    dormManager.setPassword(password);
                    request.setAttribute("user", dormManager);
                    request.setAttribute("error", loginErrMsg);
                    request.setAttribute("userType", userType);
                    System.out.println("[info]----�޹���Ա�������");

                    request.getRequestDispatcher(PageConstrant.LOGIN_PAGE).forward(request, response);
                } else {
                    rememberMe(remember, userName, password, userType, request, response);
                    session.setAttribute(LoginConstrant.CURRENT_USER_TYPE, UserType.DORM_MANAGER);
                    session.setAttribute(LoginConstrant.CURRENT_USER, currentDormManager);
                    request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.MANAGER_BLANK_PAGE);
                    System.out.println("[info]----�޹���Ա�ɹ���¼");
                    request.getRequestDispatcher(PageConstrant.MANAGER_MAIN_PAGE).forward(request, response);
                }
            } else if (UserType.STUDENT.equals(userType)) {
//                �ж��Ƿ���ѧ��
                Student student = new Student(userName, password);
                currentStudent = userDao.Login(con, student);
                if (currentStudent == null) {
                    student.setPassword(password);
                    request.setAttribute("user", student);
                    request.setAttribute("error", "�û������������");
                    request.setAttribute("userType", userType);
                    System.out.println("[info]----ѧ����¼ʧ��");
                    request.getRequestDispatcher(PageConstrant.LOGIN_PAGE).forward(request, response);
                } else {
                    rememberMe(remember, userName, password, userType, request, response);
                    session.setAttribute(LoginConstrant.CURRENT_USER_TYPE, UserType.STUDENT);
                    session.setAttribute(LoginConstrant.CURRENT_USER, currentStudent);
                    request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.STUDENT_BLANK_PAGE);
                    System.out.println("[info]----ѧ����¼�ɹ�");
                    request.getRequestDispatcher(PageConstrant.STUDENT_MAIN_PAGE).forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // �ͷ����ݿ�����
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * �Զ��巽����
     *          ����"��ס��"������ܣ�����û�ѡ����"��ס��"���򱣴�7��Cookie
     *          ����ɾ���ͻ������뱾ϵͳ��ص�Cookie
     * dorumUser: �˺���-����-��ɫ����-yes
     * */
    private void rememberMe(String remember,
                            String userName,
                            String password,
                            String userType,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (LoginConstrant.REMEMBER_ME.equals(remember)) {
            // ��ס��
            Cookie user = new Cookie(LoginConstrant.COOKIE_KEY, userName + "-" + password + "-" + userType + "-" + "yes");
            /*����Cookie�Ĵ��ʱ��Ϊ7��  */
            user.setMaxAge(LoginConstrant.COOKIE_TIME_OUT);
            response.addCookie(user);
        } else {
            // ɾ���ͻ���Cookie
            deleteCookie(userName, request, response);
        }
    }

    /*
     * �Զ��巽����ɾ���ͻ���Cookie
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
