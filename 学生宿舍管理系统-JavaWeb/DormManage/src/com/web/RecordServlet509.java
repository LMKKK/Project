package com.web;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DormBuildDao502;
import com.dao.RecordDao509;
import com.dao.StudentDao517;
import com.model.DormManager502;
import com.model.Record509;
import com.model.Student517;
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/record")
public class RecordServlet509 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    RecordDao509 recordDao509 = new RecordDao509();

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
        Object currentUserType = session.getAttribute("currentUserType");
        String s_studentText = request.getParameter("s_studentText");
        String dormBuildId = request.getParameter("buildToSelect");
        String searchType = request.getParameter("searchType");
        String action = request.getParameter("action");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Record509 record509 = new Record509();
        if ("preSave".equals(action)) {
            recordPreSave(request, response);
            return;
        } else if ("save".equals(action)) {
            recordSave(request, response);
            return;
        } else if ("delete".equals(action)) {
            recordDelete(request, response);
            return;
        } else if ("list".equals(action)) {
            if (StringUtil.isNotEmpty(s_studentText)) {
                if ("name".equals(searchType)) {
                    record509.setStudentName(s_studentText);
                } else if ("number".equals(searchType)) {
                    record509.setStudentNumber(s_studentText);
                } else if ("dorm".equals(searchType)) {
                    record509.setDormName(s_studentText);
                }
            }
            if (StringUtil.isNotEmpty(dormBuildId)) {
                record509.setDormBuildId(Integer.parseInt(dormBuildId));
            }
            session.removeAttribute("s_studentText");
            session.removeAttribute("searchType");
            session.removeAttribute("buildToSelect");
            request.setAttribute("s_studentText", s_studentText);
            request.setAttribute("searchType", searchType);
            request.setAttribute("buildToSelect", dormBuildId);
        } else if ("search".equals(action)) {
            if (StringUtil.isNotEmpty(s_studentText)) {
                if ("name".equals(searchType)) {
                    record509.setStudentName(s_studentText);
                } else if ("number".equals(searchType)) {
                    record509.setStudentNumber(s_studentText);
                } else if ("dorm".equals(searchType)) {
                    record509.setDormName(s_studentText);
                }
                session.setAttribute("s_studentText", s_studentText);
                session.setAttribute("searchType", searchType);
            } else {
                session.removeAttribute("s_studentText");
                session.removeAttribute("searchType");
            }
            if (StringUtil.isNotEmpty(startDate)) {
                record509.setStartDate(startDate);
                session.setAttribute("startDate", startDate);
            } else {
                session.removeAttribute("startDate");
            }
            if (StringUtil.isNotEmpty(endDate)) {
                record509.setEndDate(endDate);
                session.setAttribute("endDate", endDate);
            } else {
                session.removeAttribute("endDate");
            }
            if (StringUtil.isNotEmpty(dormBuildId)) {
                record509.setDormBuildId(Integer.parseInt(dormBuildId));
                session.setAttribute("buildToSelect", dormBuildId);
            } else {
                session.removeAttribute("buildToSelect");
            }
        }

        Connection con = null;
        try {
            con = dbUtil.getCon();
            if ("admin".equals((String) currentUserType)) {
                List<Record509> record509List = recordDao509.recordList(con, record509);
                request.setAttribute("dormBuildList", recordDao509.dormBuildList(con));
                request.setAttribute("recordList", record509List);
                request.setAttribute("mainPage", "admin/record509.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            } else if ("dormManager".equals((String) currentUserType)) {
                DormManager502 manager = (DormManager502) (session.getAttribute("currentUser"));
                int buildId = manager.getDormBuildId();
                String buildName = DormBuildDao502.dormBuildName(con, buildId);
                List<Record509> record509List = recordDao509.recordListWithBuild(con, record509, buildId);
                request.setAttribute("dormBuildName", buildName);
                request.setAttribute("recordList", record509List);
                request.setAttribute("mainPage", "dormManager/record509.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
            } else if ("student".equals((String) currentUserType)) {
                Student517 student517 = (Student517) (session.getAttribute("currentUser"));
                List<Record509> record509List = recordDao509.recordListWithNumber(con, record509, student517.getStuNumber());
                request.setAttribute("recordList", record509List);
                request.setAttribute("mainPage", "student/record509.jsp");
                request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
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

    private void recordDelete(HttpServletRequest request,
                              HttpServletResponse response) {
        String recordId = request.getParameter("recordId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            recordDao509.recordDelete(con, recordId);
            request.getRequestDispatcher("record?action=list").forward(request, response);
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

    private void recordSave(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        String recordId = request.getParameter("recordId");
        String studentNumber = request.getParameter("studentNumber");
        String date = request.getParameter("date");
        String detail = request.getParameter("detail");
        Record509 record509 = new Record509(studentNumber, date, detail);
        if (StringUtil.isNotEmpty(recordId)) {
            if (Integer.parseInt(recordId) != 0) {
                record509.setRecordId(Integer.parseInt(recordId));
            }
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            HttpSession session = request.getSession();
            DormManager502 manager = (DormManager502) (session.getAttribute("currentUser"));
            int buildId = manager.getDormBuildId();
            Student517 student517 = StudentDao517.getNameById(con, studentNumber, buildId);
            if (student517.getName() == null) {
                request.setAttribute("record", record509);
                request.setAttribute("error", "????????????????????");
                request.setAttribute("mainPage", "dormManager/recordSave509.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
            } else {
                record509.setDormBuildId(student517.getDormBuildId());
                record509.setStudentName(student517.getName());
                record509.setDormName(student517.getDormName());
                if (StringUtil.isNotEmpty(recordId) && Integer.parseInt(recordId) != 0) {
                    saveNum = recordDao509.recordUpdate(con, record509);
                } else {
                    saveNum = recordDao509.recordAdd(con, record509);
                }
                if (saveNum > 0) {
                    request.getRequestDispatcher("record?action=list").forward(request, response);
                } else {
                    request.setAttribute("record", record509);
                    request.setAttribute("error", "???????");
                    request.setAttribute("mainPage", "dormManager/recordSave509.jsp");
                    request.getRequestDispatcher("mainManager.jsp").forward(request, response);
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

    private void recordPreSave(HttpServletRequest request,
                               HttpServletResponse response) throws ServletException, IOException {
        String recordId = request.getParameter("recordId");
        String studentNumber = request.getParameter("studentNumber");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (StringUtil.isNotEmpty(recordId)) {
                Record509 record509 = recordDao509.recordShow(con, recordId);
                request.setAttribute("record", record509);
            } else {
                Calendar rightNow = Calendar.getInstance();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String sysDatetime = fmt.format(rightNow.getTime());
                request.setAttribute("studentNumber", studentNumber);
                request.setAttribute("date", sysDatetime);
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
        request.setAttribute("mainPage", "dormManager/recordSave509.jsp");
        request.getRequestDispatcher("mainManager.jsp").forward(request, response);
    }
}
