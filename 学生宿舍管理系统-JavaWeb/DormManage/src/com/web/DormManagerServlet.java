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

    public static final String SEARCH_TYPE = "managerSearchType";
    public static final String SEARCH_KEY = "s_dormManagerText";

    public static final String ACTION_TYPE = "action";

    public static final String SEARCH_TYPE_NAME = "name";

    public static final String SEARCH_TYPE_USERNAME = "userName";

    public static final String PAGE_MANGER = "admin/dormManger.jsp";

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
        String searchKey = request.getParameter(SEARCH_KEY); // 搜索关键字
        String searchType = request.getParameter(SEARCH_TYPE); // 搜索类型
        String action = request.getParameter(ACTION_TYPE); // 操作类型
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
                switch (searchType) {
                    case SEARCH_TYPE_NAME:
                        dormManager.setName(searchKey);
                        break;
                    case SEARCH_TYPE_USERNAME:
                        dormManager.setUserName(searchKey);
                        break;
                }
                session.setAttribute(SEARCH_TYPE, searchType);
                session.setAttribute(SEARCH_KEY, searchKey);
            } else {
                session.removeAttribute(SEARCH_TYPE);
                session.removeAttribute(SEARCH_KEY);
            }
            request.setAttribute(SEARCH_KEY, searchKey);
            request.setAttribute(SEARCH_TYPE, searchType);
        } else {
            // action为空时， 防止系统崩溃白页， 兜底方案
            if (StringUtil.isEmpty(searchKey)) {
                searchKey = (String) session.getAttribute(SEARCH_KEY);
            }

            if (StringUtil.isEmpty(searchType)) {
                searchType = (String) session.getAttribute(SEARCH_TYPE);
            }

            if (StringUtil.isNotEmpty(searchKey)) {
                switch (searchType) {
                    case SEARCH_TYPE_NAME:
                        dormManager.setName(searchKey);
                        break;
                    case SEARCH_TYPE_USERNAME:
                        dormManager.setUserName(searchKey);
                        break;
                }
                session.setAttribute(SEARCH_TYPE, searchType);
                session.setAttribute(SEARCH_KEY, searchKey);
            }
        }
        // 刷新页面
        flushPage(dormManager, request, response);
    }

    private void flushPage(DormManager dormManager, HttpServletRequest request, HttpServletResponse response) {
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

        String errMsg = null; // 页面的错误信息
        boolean flag = true; // 标志错误信息
        if (flag && (StringUtil.isEmpty(userName) || StringUtil.isEmpty(name))) {
            errMsg = "用户名或姓名不可以为空";
            flag = false;
        }


        Connection con = null;
        try {
            con = dbUtil.getCon();
            int saveNum = 0;
            if (flag && StringUtil.isNotEmpty(dormManagerId)) {
                // dormMangerId不为空，则是修改信息
                saveNum = dormManagerDao.dormManagerUpdate(con, dormManager);
            } else {
                // dormMangerID为空,则是新增
                // 检查用户名是否已存在
                if (flag && dormManagerDao.haveManagerByUser(con, dormManager.getUserName())) {
                    errMsg = "用户名不可重复，请重新输入";
                    flag = false;
                }

                if (flag && StringUtil.isEmpty(errMsg)) {
                    saveNum = dormManagerDao.dormManagerAdd(con, dormManager);
                    if (saveNum <= 0) {
                        errMsg = "保存失败！请重新检查录入的信息";
                        flag = false;
                    }
                }
            }

            if (!flag || StringUtil.isNotEmpty(errMsg) || saveNum <= 0) {
                // 发生了错误，重新填写
                request.setAttribute(PageMeta.DORM_MANAGER, dormManager);
                request.setAttribute("error", errMsg);
                request.setAttribute(PageConstrant.MAIN_PAGE, "admin/dormManagerSave.jsp");
                request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
            } else {
                // 新增成功，跳转到列表页
                request.getRequestDispatcher("dormManager?action=list").forward(request, response);
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
            // dormMangeId 不为空，则是修改
            // 需要填充信息
            Connection con = null;
            try {
                con = dbUtil.getCon();
                DormManager dormManager = dormManagerDao.dormManagerShow(con, dormManagerId);
                request.setAttribute(PageMeta.DORM_MANAGER, dormManager);
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
        request.setAttribute(PageConstrant.MAIN_PAGE, "admin/dormManagerSave.jsp");
        request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
    }
}
