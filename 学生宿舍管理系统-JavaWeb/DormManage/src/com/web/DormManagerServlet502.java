package com.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DormManagerDao;
import com.model.DormManager;
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/dormManager")
public class DormManagerServlet502 extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    DormManagerDao dormManagerDao = new DormManagerDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String s_dormManagerText = request.getParameter("s_dormManagerText");
        String searchType = request.getParameter("searchType");
//        String page = request.getParameter("page");
        String action = request.getParameter("action");
        DormManager dormManager = new DormManager();
        if ("preSave".equals(action)) {
            dormManagerPreSave(request, response);
            return;
        } else if ("save".equals(action)) {
            dormManagerSave(request, response);
            return;
        } else if ("delete".equals(action)) {
            dormManagerDelete(request, response);
            return;
        } else if ("list".equals(action)) {
            if (StringUtil.isNotEmpty(s_dormManagerText)) {
                if ("name".equals(searchType)) {
                    dormManager.setName(s_dormManagerText);
                } else if ("userName".equals(searchType)) {
                    dormManager.setUserName(s_dormManagerText);
                }
            }
            session.removeAttribute("s_dormManagerText");
            session.removeAttribute("searchType");
            request.setAttribute("s_dormManagerText", s_dormManagerText);
            request.setAttribute("searchType", searchType);
        } else if ("search".equals(action)) {
            if (StringUtil.isNotEmpty(s_dormManagerText)) {
                if ("name".equals(searchType)) {
                    dormManager.setName(s_dormManagerText);
                } else if ("userName".equals(searchType)) {
                    dormManager.setUserName(s_dormManagerText);
                }
                session.setAttribute("searchType", searchType);
                session.setAttribute("s_dormManagerText", s_dormManagerText);
            } else {
                session.removeAttribute("s_dormManagerText");
                session.removeAttribute("searchType");
            }
        } else {
            if (StringUtil.isNotEmpty(s_dormManagerText)) {
                if ("name".equals(searchType)) {
                    dormManager.setName(s_dormManagerText);
                } else if ("userName".equals(searchType)) {
                    dormManager.setUserName(s_dormManagerText);
                }
                session.setAttribute("searchType", searchType);
                session.setAttribute("s_dormManagerText", s_dormManagerText);
            }
            if (StringUtil.isEmpty(s_dormManagerText)) {
                Object o1 = session.getAttribute("s_dormManagerText");
                Object o2 = session.getAttribute("searchType");
                if (o1 != null) {
                    if ("name".equals((String) o2)) {
                        dormManager.setName((String) o1);
                    } else if ("userName".equals((String) o2)) {
                        dormManager.setUserName((String) o1);
                    }
                }
            }
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            List<DormManager> dormManagerList = dormManagerDao.dormManagerList(con, dormManager);
            request.setAttribute("dormManager502List", dormManagerList);
            request.setAttribute("mainPage", "admin/dormManager502.jsp");
            request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
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

    private void dormManagerDelete(HttpServletRequest request,
                                   HttpServletResponse response) {
        String dormManagerId = request.getParameter("dormManagerId");
        response.setContentType("text/html;charset=utf-8");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            dormManagerDao.dormManagerDelete(con, dormManagerId);
            request.getRequestDispatcher("dormManager?action=list").forward(request, response);
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

    private void dormManagerSave(HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException, IOException {
        String dormManagerId = request.getParameter("dormManagerId");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        DormManager dormManager = new DormManager(userName, password, name, sex, tel);
        if (StringUtil.isNotEmpty(dormManagerId)) {
            dormManager.setDormManagerId(Integer.parseInt(dormManagerId));
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            if (StringUtil.isNotEmpty(dormManagerId)) {
                saveNum = dormManagerDao.dormManagerUpdate(con, dormManager);
            } else if (dormManagerDao.haveManagerByUser(con, dormManager.getUserName())) {
                request.setAttribute("dormManager502", dormManager);
                request.setAttribute("error", "±£¥Ê ß∞‹");
                request.setAttribute("mainPage", "admin/dormManagerSave502.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                try {
                    dbUtil.closeCon(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
                saveNum = dormManagerDao.dormManagerAdd(con, dormManager);
            }
            if (saveNum > 0) {
                request.getRequestDispatcher("dormManager?action=list").forward(request, response);
            } else {
                request.setAttribute("dormManager", dormManager);
                request.setAttribute("error", "±£¥Ê ß∞‹");
                request.setAttribute("mainPage", "dormManager/dormManagerSave502.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
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

    private void dormManagerPreSave(HttpServletRequest request,
                                    HttpServletResponse response) throws ServletException, IOException {
        String dormManagerId = request.getParameter("dormManagerId");
        if (StringUtil.isNotEmpty(dormManagerId)) {
            Connection con = null;
            try {
                con = dbUtil.getCon();
                DormManager dormManager = dormManagerDao.dormManagerShow(con, dormManagerId);
                request.setAttribute("dormManager502", dormManager);
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
        request.setAttribute("mainPage", "admin/dormManagerSave502.jsp");
        request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
    }
}
