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

import com.constrant.OptConstrant;
import com.constrant.PageConstrant;
import com.constrant.PageMeta;
import com.dao.DormManagerDao;
import com.model.DormManager;
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/dormManager")
public class DormManagerServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    DormManagerDao dormManagerDao = new DormManagerDao();

    public static final String PAGE_MANAGER_ADMIN = "admin/dormManager.jsp";

    public static final String SEARCH_TYPE = "searchType";
    public static final String SEARCH_KEY = "s_dormManagerText";

    public static final String ACTION_TYPE = "action";

    public static final String SEARCH_TYPE_NAME = "name";

    public static final String SEARCH_TYPE_USERNAME = "userName";

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
        String searchKey = request.getParameter(SEARCH_KEY); // ËÑË÷¹Ø¼ü×Ö
        String searchType = request.getParameter(SEARCH_TYPE); // ËÑË÷ÀàÐÍ
        String action = request.getParameter(ACTION_TYPE); // ²Ù×÷ÀàÐÍ
        DormManager dormManager = new DormManager();
        if (OptConstrant.PRE_SAVE.equals(action)) {
            dormManagerPreSave(request, response);
            return;
        } else if (OptConstrant.SAVE.equals(action)) {
            dormManagerSave(request, response);
            return;
        } else if (OptConstrant.DELETE.equals(action)) {
            dormManagerDelete(request, response);
            return;
        } else if (OptConstrant.LIST.equals(action)) {
            if (StringUtil.isNotEmpty(searchKey)) {
                switch (searchType){
                    case SEARCH_TYPE_NAME:
                        dormManager.setName(searchKey);
                        break;
                    case SEARCH_TYPE_USERNAME:
                        dormManager.setUserName(searchKey);
                        break;
                }
                session.setAttribute("searchType", searchType);
                session.setAttribute("s_dormManagerText", searchKey);
            }
            session.removeAttribute("s_dormManagerText");
            session.removeAttribute("searchType");
            request.setAttribute("s_dormManagerText", searchKey);
            request.setAttribute("searchType", searchType);
        } else if ("search".equals(action)) {
            if (StringUtil.isNotEmpty(searchKey)) {
            } else {
                session.removeAttribute("s_dormManagerText");
                session.removeAttribute("searchType");
            }
        } else {
            if (StringUtil.isNotEmpty(searchKey)) {
                if ("name".equals(searchType)) {
                    dormManager.setName(searchKey);
                } else if ("userName".equals(searchType)) {
                    dormManager.setUserName(searchKey);
                }
                session.setAttribute("searchType", searchType);
                session.setAttribute("s_dormManagerText", searchKey);
            }
            if (StringUtil.isEmpty(searchKey)) {
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
            request.setAttribute(PageMeta.DORM_MANAGER_LIST, dormManagerList);
            request.setAttribute(PageConstrant.MAIN_PAGE, PAGE_MANAGER_ADMIN);
            request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
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
                request.setAttribute("error", "±£´æÊ§°Ü");
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
                request.setAttribute("error", "±£´æÊ§°Ü");
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
