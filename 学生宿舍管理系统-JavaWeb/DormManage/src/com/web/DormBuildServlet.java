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
import com.dao.DormBuildDao;
import com.mchange.v2.codegen.bean.PropsToStringGeneratorExtension;
import com.model.DormBuild;
import com.model.DormManager;
import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;
import com.util.DBUtils;
import com.util.StringUtil;

@WebServlet("/dormBuild")
public class DormBuildServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    DormBuildDao dormBuildDao = new DormBuildDao();

    public static final String ACTION = "action";

    public static final String ADMIN_DORM_BUILD = "admin/DormBuild.jsp";

    public static final String DORM_BUILD_SEARCH_NAME = "searchBuildName";

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
        String searchBuildName = request.getParameter(DORM_BUILD_SEARCH_NAME);
        String page = request.getParameter("page");
        String action = request.getParameter("action");
        DormBuild dormBuild = new DormBuild();
        if (OptConstrant.PRE_SAVE.equals(action)) {
            dormBuildPreSave(request, response);
            return;
        } else if (OptConstrant.SAVE.equals(action)) {
            dormBuildSave(request, response);
            return;
        } else if (OptConstrant.DELETE.equals(action)) {
            dormBuildDelete(request, response);
            return;
        } else if (OptConstrant.ASSIGN_Manager.equals(action)) {
            // 跳转到分配宿舍管理员
            dormBuildManager(request, response);
            return;
        } else if ("addManager".equals(action)) {
            // 添加宿舍管理员
            dormBuildAddManager(request, response);
        } else if (OptConstrant.MOVE_Manager.equals(action)) {
            managerMove(request, response);
        } else if (OptConstrant.LIST.equals(action)) {
            if (StringUtil.isNotEmpty(searchBuildName)) {
                dormBuild.setDormBuildName(searchBuildName);
                session.setAttribute(DORM_BUILD_SEARCH_NAME, searchBuildName);
            } else {
                session.removeAttribute(DORM_BUILD_SEARCH_NAME);
            }
            request.setAttribute(DORM_BUILD_SEARCH_NAME, searchBuildName);
        } else {
            // 防止action参数为空时，系统崩溃白页
            // 补充参数
            if (StringUtil.isNotEmpty(searchBuildName)) {
                dormBuild.setDormBuildName(searchBuildName);
                session.setAttribute(DORM_BUILD_SEARCH_NAME, searchBuildName);
            } else {
                String historySearchBuildName = (String) session.getAttribute(DORM_BUILD_SEARCH_NAME);
                if (StringUtil.isNotEmpty(historySearchBuildName)) {
                    searchBuildName = historySearchBuildName;
                    dormBuild.setDormBuildName(searchBuildName);
                    request.setAttribute(DORM_BUILD_SEARCH_NAME, searchBuildName);
                }
            }
        }

        // 刷新页面
        flushPage(dormBuild, request, response);
    }

    private void flushPage(DormBuild dormBuild, HttpServletRequest request, HttpServletResponse response) {
        Connection con = null;
        try {
            con = dbUtil.getCon();
            List<DormBuild> dormBuildList = dormBuildDao.dormBuildList(con, dormBuild);
            int total = dormBuildDao.dormBuildCount(con, dormBuild);
            request.setAttribute(PageMeta.DORM_BUILD_LIST, dormBuildList);
            request.setAttribute(PageConstrant.MAIN_PAGE, "admin/dormBuild.jsp");
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

    private void managerMove(HttpServletRequest request,
                             HttpServletResponse response) {
        String dormBuildId = request.getParameter("dormBuildId");
        String dormManagerId = request.getParameter("dormManagerId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            dormBuildDao.managerUpdateWithId(con, dormManagerId, "0");
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
            dormBuildDao.managerUpdateWithId(con, dormManagerId, dormBuildId);
            request.getRequestDispatcher("dormBuild?action=manager&dormBuildId=" + dormBuildId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dormBuildManager(HttpServletRequest request,
                                  HttpServletResponse response) {
        String dormBuildId = request.getParameter("dormBuildId");
        // ！！！注意：一栋宿舍楼可以有多个宿舍管理员，但是一个宿舍管理员只能 管理一个宿舍楼
        // 1. 获取这栋楼中的宿舍管理员
        // 2. 查找未分配的宿舍管理员
        Connection con = null;
        try {
            con = dbUtil.getCon();
            // managerListWithBuild 这栋楼中的所有宿舍管理员
            List<DormManager> managerListWithBuild = dormBuildDao.dormManWithBuildId(con, dormBuildId);
            // managerListToSelect 未分配的宿舍管理员
            List<DormManager> managerListToSelect = dormBuildDao.dormManWithoutBuild(con);
            request.setAttribute("dormBuildId", dormBuildId);
            request.setAttribute(PageMeta.DORM_MANAGER_WITH_BUILD, managerListWithBuild);
            request.setAttribute(PageMeta.DORM_MANAGER_TO_SELECT, managerListToSelect);
            request.setAttribute(PageConstrant.MAIN_PAGE, "admin/selectManager.jsp");
            request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
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
            if (dormBuildDao.existManOrDormWithId(con, dormBuildId)) {
                request.setAttribute("error", "宿舍楼下有宿舍或宿管，不能删除该宿舍楼");
            } else {
                dormBuildDao.dormBuildDelete(con, dormBuildId);
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
        DormBuild dormBuild = new DormBuild(dormBuildName, detail);
        if (StringUtil.isNotEmpty(dormBuildId)) {
            dormBuild.setDormBuildId(Integer.parseInt(dormBuildId));
        }
        Connection con = null;

        try {
            con = dbUtil.getCon();
            String errMsg = null;
            boolean flag = true;
            int saveNum = 0;
            if (StringUtil.isNotEmpty(dormBuildId)) {
                // dormBuildId不为空，是修改
                saveNum = dormBuildDao.dormBuildUpdate(con, dormBuild);
                if (saveNum <= 0) {
                    errMsg = "修改失败";
                    flag = false;
                }
            } else {
                // dormBuildId为空，是新增

                if (StringUtil.isEmpty(dormBuildName)) {
                    errMsg = "宿舍楼名称不能为空";
                    flag = false;
                }

                // 需要考虑这个 公寓名称是否已经有了
                if (flag && dormBuildDao.isExistBuildWithName(con, dormBuildName)) {
                    // 已存在同名的宿舍楼
                    errMsg = "已存在同名的宿舍楼, 请换个名称吧！";
                    flag = false;
                }
                if (flag && StringUtil.isEmpty(errMsg)) {
                    // 检验完成，可以新增了
                    saveNum = dormBuildDao.dormBuildAdd(con, dormBuild);
                    if (saveNum <= 0) {
                        errMsg = "保存失败";
                        flag = false;
                    }
                }
            }

            if (flag && StringUtil.isEmpty(errMsg)) {
                // 操作成功，跳回到列表页
                request.getRequestDispatcher("dormBuild?action=list").forward(request, response);
                return;
            } else {
                request.setAttribute("dormBuild", dormBuild);
                request.setAttribute("error", errMsg);
                request.setAttribute(PageConstrant.MAIN_PAGE, "dormBuild/dormBuildSave.jsp");
                request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
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
            // 不为空，则是修改，需要填充数据
            Connection con = null;
            try {
                con = dbUtil.getCon();
                DormBuild dormBuild = dormBuildDao.dormBuildShow(con, dormBuildId);
                request.setAttribute("dormBuild", dormBuild);
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

        request.setAttribute(PageConstrant.MAIN_PAGE, "admin/dormBuildSave.jsp");
        request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
    }


}
