package com.web;

import com.dao.CountDao;
import com.dao.DormBuildDao;
import com.model.Count;
import com.model.DormManager;
import com.util.DBUtils;
import com.util.DateUtil;
import com.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;


@WebServlet("/count")
public class CountServlet extends HttpServlet {
    // 序列化版本号
    private static final long serialVersionUID = 1L;
    DBUtils dbUtil = new DBUtils();
    CountDao countDao = new CountDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();

        Object currentUserType = session.getAttribute("currentUserType");
        String s_studentText = request.getParameter("s_studentText");
        String dormBuildId = request.getParameter("buildToSelect");
        String searchType = request.getParameter("searchType");
        String action = request.getParameter("action");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");


        Count count = new Count();
        if ("list".equals(action)) {
            if (StringUtil.isNotEmpty(s_studentText)) {
                if ("name".equals(searchType)) {
                    count.setStuName(s_studentText);
                } else if ("number".equals(searchType)) {
                    count.setStuNum(s_studentText);
                } else if ("dorm".equals(searchType)) {
                    count.setDormName(s_studentText);
                }
            }

            if (StringUtil.isNotEmpty(dormBuildId)) {
                count.setDormBuildId(Integer.parseInt(dormBuildId));
            }

            session.removeAttribute("s_studentText");
            session.removeAttribute("searchType");
            session.removeAttribute("buildToSelect");

            request.setAttribute("s_studentText", s_studentText);
            request.setAttribute("searchType", searchType);
            request.setAttribute("buildToSelect", dormBuildId);


        } else if ("preSave".equals(action)) {
            countPreSave(request, response);
            return;
        } else if ("save".equals(action)) {
            // 添加出入记录
            countSave(request, response);
            return;
        } else if ("search".equals(action)) {
            System.out.println("搜索");
            if (StringUtil.isNotEmpty(s_studentText)) {
                if ("name".equals(searchType)) {
                    count.setStuName(s_studentText);
                } else if ("number".equals(searchType)) {
                    count.setStuNum(s_studentText);
                } else if ("dorm".equals(searchType)) {
                    count.setDormName(s_studentText);
                }
                session.setAttribute("s_studentText", s_studentText);
                session.setAttribute("searchType", searchType);
            } else {
                session.removeAttribute("s_studentText");
                session.removeAttribute("searchType");
            }
            if (StringUtil.isNotEmpty(startDate)) {
                count.setStartDate(startDate);
                session.setAttribute("startDate", startDate);
            } else {
                session.removeAttribute("startDate");
            }
            if (StringUtil.isNotEmpty(endDate)) {
                count.setEndDate(endDate);
                session.setAttribute("endDate", endDate);
            } else {
                session.removeAttribute("endDate");
            }
            if (StringUtil.isNotEmpty(dormBuildId)) {
                count.setDormBuildId(Integer.parseInt(dormBuildId));
                session.setAttribute("buildToSelect", dormBuildId);
            } else {
                session.removeAttribute("buildToSelect");
            }
        }

        Connection con = null;
        try {
            con = dbUtil.getCon();
            if ("admin".equals((String) currentUserType)) {
                List<Count> countList = countDao.countList(con, count);
                System.out.println(countList);
                request.setAttribute("dormBuildList", countDao.dormBuildList(con));
                request.setAttribute("countList", countList);
                request.setAttribute("mainPage", "admin/count509.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            } else if ("dormManager".equals((String) currentUserType)) {
                DormManager manager = (DormManager) (session.getAttribute("currentUser"));
                int buildId = manager.getDormBuildId();
                String buildName = DormBuildDao.dormBuildName(con, buildId);
                List<Count> countList = countDao.countListWithBuild(con, count, buildId);
                System.out.println(countList);
                request.setAttribute("dormBuildName", buildName);
                request.setAttribute("countList", countList);
                request.setAttribute("mainPage", "dormManager/count509.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
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

    private void countSave(HttpServletRequest request,
                           HttpServletResponse response) {
        String dormBuildId = request.getParameter("dormBuildId");
        String stuNum = request.getParameter("stuNum");
        String stuName = request.getParameter("stuName");
        String dormBuildName = request.getParameter("dormBuildName");
        String state = request.getParameter("state");
        if ("0".equals(state)) {
            state = "出";
        } else {
            state = "入";
        }
        String date = request.getParameter("date");
        String detail = request.getParameter("detail");
        Count count = new Count();
        count.setDormBuildId(Integer.parseInt(dormBuildId));
        count.setStuNum(stuNum);
        count.setStuName(stuName);
        count.setDormBuildName(dormBuildName);
        count.setState(state);
        count.setDetail(detail);
        count.setDate(date);
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            HttpSession session = request.getSession();
            DormManager manager = (DormManager) (session.getAttribute("currentUser"));
            int buildId = manager.getDormBuildId();
            saveNum = countDao.countAdd(con, count);
            if (saveNum > 0) {
                request.getRequestDispatcher("count?action=list").forward(request, response);
            } else {
                request.setAttribute("count", count);
                request.setAttribute("error", "添加失败");
                request.setAttribute("mainPage", "dormManager/countSave509.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
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

    private void countPreSave(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
        String recordId = request.getParameter("recordId");
        String studentNumber = request.getParameter("studentNumber");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            String sysDatetime = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            HttpSession session = request.getSession();

            DormManager manager = (DormManager) (session.getAttribute("currentUser"));
            int buildId = manager.getDormBuildId();
            String buildName = DormBuildDao.dormBuildName(con, buildId);
            Count count = new Count();
            count.setDate(sysDatetime);
            count.setDormBuildId(buildId);
            count.setDormBuildName(buildName);
            request.setAttribute("count509", count);
//            request.setAttribute("date", sysDatetime);
//            request.setAttribute("",buildName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("mainPage", "dormManager/countSave509.jsp");
        request.getRequestDispatcher("mainManager.jsp").forward(request, response);
    }


}
