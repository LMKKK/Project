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
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    /**
     *
     */
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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action"); // 用户操作类型

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
            studentDelete(request, response);
//            studentService.deleteStudentById(con, studentId);
            request.getRequestDispatcher("student?action=list").forward(request, response);
            return;
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
            if (UserType.ADMIN.equals((String) currentUserType)) {
                if (StringUtil.isNotEmpty(searchKey)) {
                    if ("name".equals(searchType)) {
                        student.setName(searchKey);
                    } else if ("number".equals(searchType)) {
                        student.setStuNumber(searchKey);
                    } else if ("dorm".equals(searchType)) {
                        student.setDormName(searchKey);
                    }
                    session.setAttribute("s_studentText", searchKey);
                    session.setAttribute("searchType", searchType);
                }
                if (StringUtil.isNotEmpty(dormBuildId)) {
                    student.setDormBuildId(Integer.parseInt(dormBuildId));
                    session.setAttribute("buildToSelect", dormBuildId);
                }
                if (StringUtil.isEmpty(searchKey) && StringUtil.isEmpty(dormBuildId)) {
                    Object o1 = session.getAttribute("s_studentText");
                    Object o2 = session.getAttribute("searchType");
                    Object o3 = session.getAttribute("buildToSelect");
                    if (o1 != null) {
                        if ("name".equals((String) o2)) {
                            student.setName((String) o1);
                        } else if ("number".equals((String) o2)) {
                            student.setStuNumber((String) o1);
                        } else if ("dorm".equals((String) o2)) {
                            student.setDormName((String) o1);
                        }
                    }
                    if (o3 != null) {
                        student.setDormBuildId(Integer.parseInt((String) o3));
                    }
                }
            } else if (UserType.DORM_MANAGER.equals((String) currentUserType)) {
                if (StringUtil.isNotEmpty(searchKey)) {
                    if ("name".equals(searchType)) {
                        student.setName(searchKey);
                    } else if ("number".equals(searchType)) {
                        student.setStuNumber(searchKey);
                    } else if ("dorm".equals(searchType)) {
                        student.setDormName(searchKey);
                    }
                    session.setAttribute("s_studentText", searchKey);
                    session.setAttribute("searchType", searchType);
                }
                if (StringUtil.isEmpty(searchKey)) {
                    Object o1 = session.getAttribute("s_studentText");
                    Object o2 = session.getAttribute("searchType");
                    if (o1 != null) {
                        if ("name".equals((String) o2)) {
                            student.setName((String) o1);
                        } else if ("number".equals((String) o2)) {
                            student.setStuNumber((String) o1);
                        } else if ("dorm".equals((String) o2)) {
                            student.setDormName((String) o1);
                        }
                    }
                }
            }
        }

        // 上面的操作完成之后，重新展示最新数据
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (UserType.ADMIN.equals((String) currentUserType)) {
                List<Student> studentList = studentDao.studentList(con, student);
                request.setAttribute(PageMeta.DORM_BUILD_LIST, studentDao.dormBuildList(con));
                request.setAttribute(PageMeta.STUDENT_LIST, studentList);
                request.setAttribute(PageConstrant.MAIN_PAGE, "admin/student517.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            } else if (UserType.DORM_MANAGER.equals((String) currentUserType)) {
                DormManager manager = (DormManager) (session.getAttribute("currentUser"));
                int buildId = manager.getDormBuildId();
                String buildName = DormBuildDao.dormBuildName(con, buildId);
                List<Student> studentList = studentDao.studentListWithBuild(con, student, buildId);
                // 宿管人员只能查看自己所管辖的宿舍楼的学生
                request.setAttribute(PageMeta.DORM_BUILD_NAME, buildName);
                request.setAttribute(PageMeta.STUDENT_LIST, studentList);
                request.setAttribute(PageConstrant.MAIN_PAGE, "dormManager/student517.jsp");
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

    private void studentDelete(HttpServletRequest request,
                               HttpServletResponse response) {

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
            if (StringUtil.isNotEmpty(studentId)) {
                saveNum = studentDao.studentUpdate(con, student);
            } else if (studentDao.haveNameByNumber(con, student.getStuNumber())) {
                request.setAttribute("student", student);
                request.setAttribute("error", "该学号已存在");
                request.setAttribute("mainPage", "admin/studentSave.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
                try {
                    dbUtil.closeCon(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
                saveNum = studentDao.studentAdd(con, student);
            }
            if (saveNum > 0) {
                request.getRequestDispatcher("student?action=list").forward(request, response);
            } else {
                request.setAttribute("student", student);
                request.setAttribute("error", "保存失败");
                request.setAttribute("mainPage", "admin/studentSave502.jsp");
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

    private void studentPreSave(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            request.setAttribute("dormBuildList", studentDao.dormBuildList(con));
            if (StringUtil.isNotEmpty(studentId)) {
                Student student = studentDao.getStudentById(con, studentId);
                request.setAttribute("student517", student);
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
        request.setAttribute("mainPage", "admin/studentSave517.jsp");
        request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
    }
}
