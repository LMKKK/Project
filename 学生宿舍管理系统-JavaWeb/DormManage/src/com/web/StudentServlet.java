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

import com.constrant.*;
import com.dao.DormBuildDao;
import com.dao.StudentDao;
import com.model.DormManager;
import com.model.Student;
import com.service.StudentService;
import com.sun.net.httpserver.HttpPrincipal;
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    private StudentDao studentDao = new StudentDao();

    private StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    // 选择的宿舍楼
    public static final String BUILD_SELECT = "buildToSelect";

    // 搜索关键字
    public static final String SEARCH_KEY = "s_studentText";

    // 搜索类型
    public static final String SEARCH_TYPE = "searchType";


    public static final String SEARCH_TYPE_NAME = "name";


    public static final String SEARCH_TYPE_NUMBER = "number";

    public static final String SEARCH_TYPE_DORM = "dorm";

    public static final String STUDENT_PAGE_ADMIN = "admin/student.jsp";

    public static final String STUDENT_PAGE_SAVE = "admin/studentSave.jsp";

    public static final String STUDENT_PAGE_MANAGER = "dormManager/student.jsp";

    public static final String ACTION_TYPE = "action";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); // 防止获取请求参数时乱码
        String action = request.getParameter(ACTION_TYPE); // 用户操作类型

        HttpSession session = request.getSession();
        String currentUserType = (String) session.getAttribute(LoginConstrant.CURRENT_USER_TYPE);

        String searchKey = request.getParameter(SEARCH_KEY);
        String dormBuildId = request.getParameter(BUILD_SELECT);
        String searchType = request.getParameter(SEARCH_TYPE);

        Student student = new Student(); // 构建搜索条件的实体
        if (OptConstrant.PRE_SAVE.equals(action)) {
            studentPreSave(request, response);
            return;
        } else if (OptConstrant.SAVE.equals(action)) {
            studentSave(request, response);
            return;
        } else if (OptConstrant.DELETE.equals(action)) {
            String studentId = request.getParameter("studentId");
            try{
                Connection con = dbUtil.getCon();
                studentService.deleteStudentById(con, studentId);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (OptConstrant.LIST.equals(action)) {
            // 构建搜索条件
            if (StringUtil.isNotEmpty(searchKey)) {
                switch (searchType) {
                    case SEARCH_TYPE_NAME:
                        student.setName(searchKey); // 学生姓名搜索的内容
                        break;
                    case SEARCH_TYPE_NUMBER:
                        student.setStuNumber(searchKey); // 学生学号搜索的内容
                        break;
                    case SEARCH_TYPE_DORM:
                        student.setDormName(searchKey); // 宿舍名称搜索的内容
                        break;
                }
                session.setAttribute(SEARCH_KEY, searchKey);
                session.setAttribute(SEARCH_TYPE, searchType);
            } else {
                session.removeAttribute(SEARCH_KEY);
                session.removeAttribute(SEARCH_TYPE);
            }
            if (StringUtil.isNotEmpty(dormBuildId)) {
                student.setDormBuildId(Integer.parseInt(dormBuildId));
                session.setAttribute(BUILD_SELECT, dormBuildId);
            } else {
                session.removeAttribute(BUILD_SELECT);
            }
            session.removeAttribute(SEARCH_KEY);
            session.removeAttribute(SEARCH_TYPE);
            session.removeAttribute(BUILD_SELECT);
            request.setAttribute(SEARCH_KEY, searchKey);
            request.setAttribute(SEARCH_TYPE, searchType);
            request.setAttribute(BUILD_SELECT, dormBuildId);
        } else {
            // 当action为空时的 兜底方案， 防止系统白页崩溃
            if (StringUtil.isEmpty(searchKey)) {
                String historySearchKey = (String) session.getAttribute(SEARCH_KEY);
                if (StringUtil.isNotEmpty(historySearchKey)) {
                    searchKey = historySearchKey;
                }
            }

            if (StringUtil.isEmpty(dormBuildId)) {
                String historSelectBuild = (String) session.getAttribute(BUILD_SELECT);
                if (StringUtil.isNotEmpty(historSelectBuild)) {
                    dormBuildId = historSelectBuild;
                }
            }

            if (StringUtil.isNotEmpty(searchKey)) {
                switch (searchType) {
                    case SEARCH_TYPE_NAME:
                        student.setName(searchKey); // 学生姓名搜索的内容
                        break;
                    case SEARCH_TYPE_NUMBER:
                        student.setStuNumber(searchKey); // 学生学号搜索的内容
                        break;
                    case SEARCH_TYPE_DORM:
                        student.setDormName(searchKey); // 宿舍名称搜索的内容
                        break;
                }
                session.setAttribute(SEARCH_KEY, searchKey);
                session.setAttribute(SEARCH_TYPE, searchType);
            }

            if (StringUtil.isNotEmpty(dormBuildId)) {
                student.setDormBuildId(Integer.parseInt(dormBuildId));
                session.setAttribute(BUILD_SELECT, dormBuildId);
            }

        }

        // 前面的逻辑只是为 刷新页面做铺垫

        flushPage(student, request, response);

    }

    /**
     * 刷新页面
     * @param request
     * @param response
     */
    private void flushPage(Student student, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String currentUserType = (String) session.getAttribute(LoginConstrant.CURRENT_USER_TYPE);
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (UserType.ADMIN.equals((String) currentUserType)) {
                List<Student> studentList = studentDao.studentList(con, student);
                request.setAttribute(PageMeta.DORM_BUILD_LIST, studentDao.dormBuildList(con));
                request.setAttribute(PageMeta.STUDENT_LIST, studentList);
                request.setAttribute(PageConstrant.MAIN_PAGE, STUDENT_PAGE_ADMIN);
                request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
            } else if (UserType.DORM_MANAGER.equals((String) currentUserType)) {
                DormManager manager = (DormManager) (session.getAttribute(LoginConstrant.CURRENT_USER));
                int buildId = manager.getDormBuildId();
                String buildName = DormBuildDao.dormBuildName(con, buildId);
                List<Student> studentList = studentDao.studentListWithBuild(con, student, buildId);
                // 宿管人员只能查看自己所管辖的宿舍楼的学生
                request.setAttribute(PageMeta.DORM_BUILD_NAME, buildName);
                request.setAttribute(PageMeta.STUDENT_LIST, studentList);
                request.setAttribute(PageConstrant.MAIN_PAGE, STUDENT_PAGE_MANAGER);
                request.getRequestDispatcher(PageConstrant.MANAGER_MAIN_PAGE).forward(request, response);
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


    private void studentSave(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String dormBuildId = request.getParameter("dormBuildId");
        String dormName = request.getParameter("dormName");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String tel = request.getParameter("tel");
        Student student = new Student(userName, password, Integer.parseInt(dormBuildId), dormName, name, sex, tel);
        if (StringUtil.isNotEmpty(studentId)) {
            student.setStudentId(Integer.parseInt(studentId));
        }
        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            String errMsg = null;
            if (StringUtil.isNotEmpty(studentId)) {
                // id不为空，则是修改学生
                saveNum = studentDao.studentUpdate(con, student);
            } else {
                // id为空，则是新增学生

                // 检验是否填了学号
                if (StringUtil.isEmpty(student.getStuNumber())) {
                    errMsg = "学号不可为空！";
                }

                // 判断学号是否已经被占用
                if (studentDao.haveNameByNumber(con, student.getStuNumber())) {
                    errMsg = "该学号已存在！";
                }

                if (StringUtil.isEmpty(errMsg)) {
                    // 校验通过，可以新增了
                    saveNum = studentDao.studentAdd(con, student);
                    if (saveNum <= 0) {
                        errMsg = "保存失败！请重新检查录入的信息??";
                    }
                }
            }

            if (StringUtil.isNotEmpty(errMsg) || saveNum <= 0) {
                // 保存失败，回到填写页，重新填写
                request.setAttribute(PageMeta.STUDENT, student);
                request.setAttribute(PageMeta.ERR_MSG, errMsg);
                request.setAttribute(PageConstrant.MAIN_PAGE, STUDENT_PAGE_SAVE);
                request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
            } else {
                // 保存成功，跳转到列表页
                request.getRequestDispatcher("student?action=list").forward(request, response);
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

    /**
     * 为填写页补充数据，并跳转到学生信息填写页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void studentPreSave(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            request.setAttribute(PageMeta.DORM_BUILD_LIST, studentDao.dormBuildList(con));
            if (StringUtil.isNotEmpty(studentId)) {
                Student student = studentDao.getStudentById(con, studentId);
                request.setAttribute(PageMeta.STUDENT, student);
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
        request.setAttribute(PageConstrant.MAIN_PAGE, STUDENT_PAGE_SAVE);
        request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
    }
}
