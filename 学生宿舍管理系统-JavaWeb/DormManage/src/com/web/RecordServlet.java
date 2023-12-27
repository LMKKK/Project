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

import com.constrant.*;
import com.dao.DormBuildDao;
import com.dao.RecordDao;
import com.dao.StudentDao;
import com.model.DormManager;
import com.model.Record;
import com.model.Student;
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/record")
public class RecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    RecordDao recordDao = new RecordDao();

    public static final String BUILD_TO_SELECT = "recorde_buildToSelect";
    public static final String SEARCH_TYPE = "recordSearchType";
    public static final String RECORD_SEARCH_TEXT = "recordSearchText";
    public static final String ACTION = "action";

    public static final String RECORD_START_DATE = "recordStartDate";
    public static final String RECORD_END_DATE = "recordEndDate";

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
        String currentUserType = (String) session.getAttribute(LoginConstrant.CURRENT_USER_TYPE);
        String searchText = request.getParameter(RECORD_SEARCH_TEXT);
        String dormBuildId = request.getParameter(BUILD_TO_SELECT);
        String searchType = request.getParameter(SEARCH_TYPE);
        String action = request.getParameter(ACTION);
        // TODO 按照日期区间 检索
        String startDate = request.getParameter(RECORD_START_DATE);
        String endDate = request.getParameter(RECORD_END_DATE);

        Record record = new Record();
        if (OptConstrant.PRE_SAVE.equals(action)) {
            recordPreSave(request, response);
            return;
        } else if (OptConstrant.SAVE.equals(action)) {
            recordSave(request, response);
            return;
        } else if (OptConstrant.DELETE.equals(action)) {
            recordDelete(request, response);
            return;
        } else if (OptConstrant.LIST.equals(action)) {
            if (StringUtil.isNotEmpty(searchText)) {
                if ("name".equals(searchType)) {
                    record.setStudentName(searchText);
                } else if ("number".equals(searchType)) {
                    record.setStudentNumber(searchText);
                } else if ("dorm".equals(searchType)) {
                    record.setDormName(searchText);
                }
                session.setAttribute(SEARCH_TYPE, searchType);
                session.setAttribute(RECORD_SEARCH_TEXT, searchText);
            } else {
                session.removeAttribute(SEARCH_TYPE);
                session.removeAttribute(SEARCH_TYPE);
            }

            if (StringUtil.isNotEmpty(dormBuildId)) {
                record.setDormBuildId(Integer.parseInt(dormBuildId));
                session.setAttribute(BUILD_TO_SELECT, dormBuildId);
            } else {
                session.removeAttribute(BUILD_TO_SELECT);
            }
            request.setAttribute(RECORD_SEARCH_TEXT, searchText);
            request.setAttribute("searchType", searchType);
            request.setAttribute("buildToSelect", dormBuildId);
        }

        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (UserType.ADMIN.equals(currentUserType)) {
                // 当前用户是系统管理员
                List<Record> recordList = recordDao.recordList(con, record);
                request.setAttribute(PageMeta.DORM_BUILD_LIST, recordDao.dormBuildList(con));
                request.setAttribute(PageMeta.RECORD_LIST, recordList);
                request.setAttribute(PageConstrant.MAIN_PAGE, "admin/record.jsp");
                request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
            } else if (UserType.DORM_MANAGER.equals(currentUserType)) {
                // 当前用户是宿舍管理员
                DormManager manager = (DormManager) (session.getAttribute("currentUser"));
                int buildId = manager.getDormBuildId();
                String buildName = DormBuildDao.dormBuildName(con, buildId);
                List<Record> recordList = recordDao.recordListWithBuild(con, record, buildId);
                request.setAttribute("dormBuildName", buildName);
                request.setAttribute(PageMeta.RECORD_LIST, recordList);
                request.setAttribute(PageConstrant.MAIN_PAGE, "dormManager/record.jsp");
                request.getRequestDispatcher(PageConstrant.MANAGER_MAIN_PAGE).forward(request, response);
            } else if (UserType.STUDENT.equals(currentUserType)) {
                // 当前用户是学生
                Student student = (Student) (session.getAttribute("currentUser"));
                List<Record> recordList = recordDao.recordListWithNumber(con, record, student.getStuNumber());
                request.setAttribute(PageMeta.RECORD_LIST, recordList);
                request.setAttribute(PageConstrant.MAIN_PAGE, "student/record.jsp");
                request.getRequestDispatcher(PageConstrant.STUDENT_MAIN_PAGE).forward(request, response);
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
            recordDao.recordDelete(con, recordId);
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
        Record record = new Record(studentNumber, date, detail);
        if (StringUtil.isNotEmpty(recordId)) {
            if (Integer.parseInt(recordId) != 0) {
                record.setRecordId(Integer.parseInt(recordId));
            }
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            HttpSession session = request.getSession();
            DormManager manager = (DormManager) (session.getAttribute("currentUser"));
            int buildId = manager.getDormBuildId();
            Student student = StudentDao.getNameById(con, studentNumber, buildId);
            if (student.getName() == null) {
                request.setAttribute("record", record);
                request.setAttribute("error", "????????????????????");
                request.setAttribute("mainPage", "dormManager/recordSave.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
            } else {
                record.setDormBuildId(student.getDormBuildId());
                record.setStudentName(student.getName());
                record.setDormName(student.getDormName());
                if (StringUtil.isNotEmpty(recordId) && Integer.parseInt(recordId) != 0) {
                    saveNum = recordDao.recordUpdate(con, record);
                } else {
                    saveNum = recordDao.recordAdd(con, record);
                }
                if (saveNum > 0) {
                    request.getRequestDispatcher("record?action=list").forward(request, response);
                } else {
                    request.setAttribute("record", record);
                    request.setAttribute("error", "???????");
                    request.setAttribute("mainPage", "dormManager/recordSave.jsp");
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
                Record record = recordDao.recordShow(con, recordId);
                request.setAttribute("record", record);
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
        request.setAttribute("mainPage", "dormManager/recordSave.jsp");
        request.getRequestDispatcher("mainManager.jsp").forward(request, response);
    }
}
