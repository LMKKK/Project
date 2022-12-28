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

import com.dao.DormBuildDao502;
import com.model.DormBuild502;
import com.model.DormManager502;
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/dormBuild")
public class DormBuildServlet502 extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    DormBuildDao502 dormBuildDao502 = new DormBuildDao502();

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
        String s_dormBuildName = request.getParameter("s_dormBuildName");
        String page = request.getParameter("page");
        String action = request.getParameter("action");
        DormBuild502 dormBuild502 = new DormBuild502();
        if ("preSave".equals(action)) {
            dormBuildPreSave(request, response);
            return;
        } else if ("save".equals(action)) {
            dormBuildSave(request, response);
            return;
        } else if ("delete".equals(action)) {
            dormBuildDelete(request, response);
            return;
        } else if ("manager".equals(action)) {
            dormBuildManager(request, response);
            return;
        } else if ("addManager".equals(action)) {
            dormBuildAddManager(request, response);
        } else if ("move".equals(action)) {
            managerMove(request, response);
        } else if ("list".equals(action)) {
            if (StringUtil.isNotEmpty(s_dormBuildName)) {
                dormBuild502.setDormBuildName(s_dormBuildName);
            }
            session.removeAttribute("s_dormBuildName");
            request.setAttribute("s_dormBuildName", s_dormBuildName);
        } else if ("search".equals(action)) {
            if (StringUtil.isNotEmpty(s_dormBuildName)) {
                dormBuild502.setDormBuildName(s_dormBuildName);
                session.setAttribute("s_dormBuildName", s_dormBuildName);
            } else {
                session.removeAttribute("s_dormBuildName");
            }
        } else {
            if (StringUtil.isNotEmpty(s_dormBuildName)) {
                dormBuild502.setDormBuildName(s_dormBuildName);
                session.setAttribute("s_dormBuildName", s_dormBuildName);
            }
            if (StringUtil.isEmpty(s_dormBuildName)) {
                Object o = session.getAttribute("s_dormBuildName");
                if (o != null) {
                    dormBuild502.setDormBuildName((String) o);
                }
            }
        }
        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            List<DormBuild502> dormBuild502List = dormBuildDao502.dormBuildList(con, dormBuild502);
            int total = dormBuildDao502.dormBuildCount(con, dormBuild502);
//            String pageCode = this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
//            request.setAttribute("pageCode", pageCode);
            request.setAttribute("dormBuild502List", dormBuild502List);
            request.setAttribute("mainPage", "admin/dormBuild502.jsp");
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

    private void managerMove(HttpServletRequest request,
                             HttpServletResponse response) {
        String dormBuildId = request.getParameter("dormBuildId");
        String dormManagerId = request.getParameter("dormManagerId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            dormBuildDao502.managerUpdateWithId(con, dormManagerId, "0");
            request.getRequestDispatcher("dormBuild?action=manager&dormBuildId=" + dormBuildId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dormBuildAddManager(HttpServletRequest request,
                                     HttpServletResponse response) {
        String dormBuildId = request.getParameter("dormBuildId");
        String dormManagerId = request.getParameter("dormManagerId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            dormBuildDao502.managerUpdateWithId(con, dormManagerId, dormBuildId);
            request.getRequestDispatcher("dormBuild?action=manager&dormBuildId=" + dormBuildId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dormBuildManager(HttpServletRequest request,
                                  HttpServletResponse response) {
        String dormBuildId = request.getParameter("dormBuildId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            List<DormManager502> managerListWithId = dormBuildDao502.dormManWithBuildId(con, dormBuildId);
            List<DormManager502> managerListToSelect = dormBuildDao502.dormManWithoutBuild(con);
            request.setAttribute("dormBuildId", dormBuildId);
            request.setAttribute("managerListWithId", managerListWithId);
            request.setAttribute("managerListToSelect", managerListToSelect);
            request.setAttribute("mainPage", "admin/selectManager502.jsp");
            request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dormBuildDelete(HttpServletRequest request,
                                 HttpServletResponse response) {
        String dormBuildId = request.getParameter("dormBuildId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (dormBuildDao502.existManOrDormWithId(con, dormBuildId)) {
                request.setAttribute("error", "Àﬁ…·¬•œ¬”–Àﬁ…·ªÚÀﬁπ‹£¨≤ªƒ‹…æ≥˝∏√Àﬁ…·¬•");
            } else {
                dormBuildDao502.dormBuildDelete(con, dormBuildId);
            }
            request.getRequestDispatcher("dormBuild?action=list").forward(request, response);
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

    private void dormBuildSave(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {
        String dormBuildId = request.getParameter("dormBuildId");
        String dormBuildName = request.getParameter("dormBuildName");
        String detail = request.getParameter("detail");
        DormBuild502 dormBuild502 = new DormBuild502(dormBuildName, detail);
        if (StringUtil.isNotEmpty(dormBuildId)) {
            dormBuild502.setDormBuildId(Integer.parseInt(dormBuildId));
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            if (StringUtil.isNotEmpty(dormBuildId)) {
                saveNum = dormBuildDao502.dormBuildUpdate(con, dormBuild502);
            } else {
                saveNum = dormBuildDao502.dormBuildAdd(con, dormBuild502);
            }
            if (saveNum > 0) {
                request.getRequestDispatcher("dormBuild?action=list").forward(request, response);
            } else {
                request.setAttribute("dormBuild", dormBuild502);
                request.setAttribute("error", "±£¥Ê ß∞‹");
                request.setAttribute("mainPage", "dormBuild/dormBuildSave502.jsp");
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

    private void dormBuildPreSave(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        String dormBuildId = request.getParameter("dormBuildId");
        if (StringUtil.isNotEmpty(dormBuildId)) {
            Connection con = null;
            try {
                con = dbUtil.getCon();
                DormBuild502 dormBuild502 = dormBuildDao502.dormBuildShow(con, dormBuildId);
                request.setAttribute("dormBuild502", dormBuild502);
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

        request.setAttribute("mainPage", "admin/dormBuildSave502.jsp");
        request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
    }


}
