package com.web;

import com.constrant.UserType;
import com.dao.DormBuildDao;
import com.dao.ExceptionDao;
import com.model.DormManager;
import com.model.Excp;
import com.model.Student;
import com.util.DBUtils;
import com.util.DateUtil;
import com.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet("/excp")
public class ExceptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    ExceptionDao recordDao = new ExceptionDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String currentUserType = (String) session.getAttribute("currentUserType");
        String s_studentText = request.getParameter("s_studentText");
        String dormBuildId = request.getParameter("buildToSelect");
        String searchType = request.getParameter("searchType");
        String action = request.getParameter("action");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Excp record = new Excp();
        if ("preSave".equals(action)) {
            recordPreSave(request, response);
            return;
        } else if ("save".equals(action)) {
            upload(request, response);
            Student student = (Student) (session.getAttribute("currentUser"));
            List<Excp> recordList = null;
            try {
                recordList = recordDao.recordListWithNumber(dbUtil.getCon(), record, student.getStuNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("exceptionList", recordList);
            request.setAttribute("mainPage", "student/exception.jsp");
            request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
            return;
        } else if ("update".equals(action)) {
            update(request, response);
            return;
        } else if ("show".equals(action)) {
            String excpId = request.getParameter("excpId");
            String stuNum = request.getParameter("stuNum");
            Connection con = null;
            try {
                con = dbUtil.getCon();
                if (StringUtil.isNotEmpty(excpId)) {
                    Excp excp = recordDao.recordShow(con, excpId);
                    System.out.println(excp);
                    request.setAttribute("exception", excp);
                } else {
                    Calendar rightNow = Calendar.getInstance();
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    String sysDatetime = fmt.format(rightNow.getTime());
                    request.setAttribute("studentNumber", stuNum);
                    request.setAttribute("date", sysDatetime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (UserType.ADMIN.equals(currentUserType)) {

                request.setAttribute("mainPage", "admin/exceptionSave.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
<<<<<<< HEAD
            } else if (UserType.DORM_MANAGER.equals(currentUserType)) {
                request.setAttribute("mainPage", "dormManager/excpSave517.jsp");
=======
            } else if ("dormManager".equals((String) currentUserType)) {
                request.setAttribute("mainPage", "dormManager/excpSave.jsp");
>>>>>>> 5a743df5e40267ea0343c3405a19ef0b961d98ac
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
            } else if (UserType.STUDENT.equals(currentUserType)) {
                Student student = (Student) (session.getAttribute("currentUser"));
                request.setAttribute("mainPage", "student/exceptionSave.jsp");
                request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
            }
            return;
        } else if ("delete".equals(action)) {
            recordDelete(request, response);
            return;

        } else if ("list".equals(action)) {
            if (StringUtil.isNotEmpty(s_studentText)) {
                if ("name".equals(searchType)) {
//                    record.setStudentName(s_studentText);
                } else if ("number".equals(searchType)) {
//                    record.setStudentNumber(s_studentText);
                } else if ("dorm".equals(searchType)) {
//                    record.setDormName(s_studentText);
                }
            }
            if (StringUtil.isNotEmpty(dormBuildId)) {
                record.setDormBuildId(Integer.parseInt(dormBuildId));
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
//                    record.setStudentName(s_studentText);
                } else if ("number".equals(searchType)) {
                    record.setStuNum(s_studentText);
                } else if ("dorm".equals(searchType)) {
//                    record.setDormName(s_studentText);
                }
                session.setAttribute("s_studentText", s_studentText);
                session.setAttribute("searchType", searchType);
            } else {
                session.removeAttribute("s_studentText");
                session.removeAttribute("searchType");
            }
            if (StringUtil.isNotEmpty(startDate)) {
                record.setStartDate(startDate);
                session.setAttribute("startDate", startDate);
            } else {
                session.removeAttribute("startDate");
            }
            if (StringUtil.isNotEmpty(endDate)) {
                record.setEndDate(endDate);
                session.setAttribute("endDate", endDate);
            } else {
                session.removeAttribute("endDate");
            }
            if (StringUtil.isNotEmpty(dormBuildId)) {
                record.setDormBuildId(Integer.parseInt(dormBuildId));
                session.setAttribute("buildToSelect", dormBuildId);
            } else {
                session.removeAttribute("buildToSelect");
            }
        }

        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (UserType.ADMIN.equals((String) currentUserType)) {
                List<Excp> recordList = recordDao.recordList(con, record);
                request.setAttribute("dormBuildList", recordDao.dormBuildList(con));
                request.setAttribute("exceptionList", recordList);
                request.setAttribute("mainPage", "admin/excp.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            } else if (UserType.DORM_MANAGER.equals((String) currentUserType)) {
                DormManager manager = (DormManager) (session.getAttribute("currentUser"));
                int buildId = manager.getDormBuildId();
                String buildName = DormBuildDao.dormBuildName(con, buildId);
                List<Excp> recordList = recordDao.recordListWithBuild(con, record, buildId);
                request.setAttribute("dormBuildName", buildName);
                request.setAttribute("exceptionList", recordList);
                request.setAttribute("mainPage", "dormManager/excp.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
            } else if (UserType.STUDENT.equals((String) currentUserType)) {
                Student student = (Student) (session.getAttribute("currentUser"));
                List<Excp> recordList = recordDao.recordListWithNumber(con, record, student.getStuNumber());
                request.setAttribute("exceptionList", recordList);
                request.setAttribute("mainPage", "student/exception.jsp");
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

    private void update(HttpServletRequest request, HttpServletResponse response) {
        String excpId = request.getParameter("excpId");
        String state = request.getParameter("state");
        String detail = request.getParameter("detail");

        if ("1".equals(state)) {
            state = "������";
        } else {
            state = "�����";
        }
        Excp excp = new Excp();
        excp.setExcpId(Integer.parseInt(excpId));
        excp.setState(state);
        excp.setDetail(detail);
//        excp.setDormBuildId();
        try {
            Connection con = dbUtil.getCon();

            int lines = recordDao.recordUpdate(con, excp);
            if (lines != 0) {
                System.out.println("�޸ĳɹ�");
            } else {
                System.out.println("�޸�ʧ��");
            }
            HttpSession session = request.getSession();
            DormManager manager = (DormManager) (session.getAttribute("currentUser"));
            int buildId = manager.getDormBuildId();
            String buildName = DormBuildDao.dormBuildName(con, buildId);
            List<Excp> recordList = recordDao.recordListWithBuild(con, excp, buildId);
            request.setAttribute("dormBuildName", buildName);
            request.setAttribute("exceptionList", recordList);
            request.setAttribute("mainPage", "dormManager/excp.jsp");
            request.getRequestDispatcher("mainManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void upload(HttpServletRequest request, HttpServletResponse response) {

        try {
            Excp excp = new Excp();
            String date = null;
            String stuNum = null;
            String dormBuildId = null;
            String tel = null;
            String detail = null;
            String imgUrl = null;
            String filename = null;
            //����ContentType�ֶ�ֵ
            response.setContentType("text/html;charset=utf-8");
            // ����DiskFileItemFactory��������
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //�����ļ�����Ŀ¼�������Ŀ¼���������´���һ��
            File f = new File("C:\\Test");
            if (!f.exists()) {
                f.mkdirs();
            }
            // �����ļ��Ļ���·��
            factory.setRepository(f);
            // ���� ServletFileUpload����
            ServletFileUpload fileupload = new ServletFileUpload(factory);
            //�����ַ�����
            fileupload.setHeaderEncoding("utf-8");
            // ���� request���õ��ϴ��ļ���FileItem����
            List<FileItem> fileitems = fileupload.parseRequest(request);
            //��ȡ�ַ���
            PrintWriter writer = response.getWriter();
            // ��������
            for (FileItem fileitem : fileitems) {
                // �ж��Ƿ�Ϊ��ͨ�ֶ�
                if (fileitem.isFormField()) {
                    //����ͨ�ֶ�,��ȡ�ֶ���
                    String tmp_name = fileitem.getFieldName();
                    if ("stuNum".equals(tmp_name)) {
                        stuNum = fileitem.getString("utf-8");
                        excp.setStuNum(stuNum);
                    } else if ("tel".equals(tmp_name)) {
                        tel = fileitem.getString("utf-8");
                        excp.setTel(tel);
                    } else if ("dormBuildId".equals(tmp_name)) {
                        dormBuildId = fileitem.getString("utf-8");
                        excp.setDormBuildId(Integer.parseInt(dormBuildId));
                    } else if ("detail".equals(tmp_name)) {
                        detail = fileitem.getString("utf-8");
                        excp.setDetail(detail);
                    } else if ("date".equals(tmp_name)) {
                        date = fileitem.getString("utf-8");
                        excp.setDate(date);
                    }
                } else {
                    // ��ȡ�ϴ����ļ���
                    filename = fileitem.getName();
                    //�����ϴ��ļ�
                    if (StringUtil.isNotEmpty(filename)) {
//                        writer.print("�ϴ����ļ������ǣ�" + filename + "<br>");
                        // ��ȡ���ļ���
                        filename = filename.substring(filename.lastIndexOf("\\") + 1);
                        // �ļ�����ҪΨһ
                        filename = UUID.randomUUID().toString() + "_" + filename;
                        imgUrl = filename;
                        excp.setImgUrl(imgUrl);
                        // �ڷ���������ͬ���ļ�
                        String webPath = "/resources/img/";
                        //�����������ļ���·�����ļ�����ϳ������ķ�������·��
                        String filepath = getServletContext().getRealPath(webPath + filename);
                        // �����ļ�
                        File file = new File(filepath);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        // ����ϴ��ļ���
                        InputStream in = fileitem.getInputStream();
                        // ʹ��FileOutputStream�򿪷������˵��ϴ��ļ�
                        FileOutputStream out = new FileOutputStream(file);
                        // ���ĶԿ�
                        byte[] buffer = new byte[1024];//ÿ�ζ�ȡ1���ֽ�
                        int len;
                        //��ʼ��ȡ�ϴ��ļ����ֽڣ����������������˵��ϴ��ļ��������
                        while ((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        System.out.println("�ļ�·����" + filepath);
                        // �ر���
                        in.close();
                        out.close();
                        // ɾ����ʱ�ļ�
                        fileitem.delete();
                        System.out.println("�ϴ��ļ��ɹ�");
                    }
                }
            }
            Connection con = dbUtil.getCon();
            excp.setDormBuildName(DormBuildDao.dormBuildName(con, excp.getDormBuildId()));
            // �����ݲ��뵽���ݿ���
            int s = recordDao.recordAdd(con, excp);
            if (s != 0) {
                System.out.println("��ӳɹ�");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void recordShow(HttpServletRequest request, HttpServletResponse response) {
        String excpId = request.getParameter("excpId");
        String stuNum = request.getParameter("stuNum");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            if (StringUtil.isNotEmpty(excpId)) {
                Excp record = recordDao.recordShow(con, excpId);
                request.setAttribute("exception", record);
            } else {
                Calendar rightNow = Calendar.getInstance();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String sysDatetime = fmt.format(rightNow.getTime());
                request.setAttribute("studentNumber", stuNum);
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
    }

    private void recordPreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) (session.getAttribute("currentUser"));
        String stuNum = student.getStuNumber();
        int dormBuildId = student.getDormBuildId();
        Excp excp = new Excp();
        excp.setStuNum(stuNum);
        excp.setDormBuildId(dormBuildId);
        excp.setDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));

        Connection con = null;
        try {
            con = dbUtil.getCon();
            Calendar rightNow = Calendar.getInstance();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            excp.setDormBuildName(DormBuildDao.dormBuildName(con, dormBuildId));
            String sysDatetime = fmt.format(rightNow.getTime());
            request.setAttribute("studentNumber", stuNum);
            request.setAttribute("date", sysDatetime);
            request.setAttribute("exception", excp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("mainPage", "student/upload.jsp");
        request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
    }

    private void recordDelete(HttpServletRequest request, HttpServletResponse response) {
        String recordId = request.getParameter("excpId");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            recordDao.recordDelete(con, recordId);
            request.getRequestDispatcher("excp?action=list").forward(request, response);
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
}
