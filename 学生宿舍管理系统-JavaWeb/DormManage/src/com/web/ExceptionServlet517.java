package com.web;

import com.dao.DormBuildDao502;
import com.dao.ExceptionDao517;
import com.model.DormManager502;
import com.model.Excp517;
import com.model.Student517;
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
public class ExceptionServlet517 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    DBUtils dbUtil = new DBUtils();
    ExceptionDao517 recordDao = new ExceptionDao517();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Object currentUserType = session.getAttribute("currentUserType");
        String s_studentText = request.getParameter("s_studentText");
        String dormBuildId = request.getParameter("buildToSelect");
        String searchType = request.getParameter("searchType");
        String action = request.getParameter("action");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Excp517 record = new Excp517();
        if ("preSave".equals(action)) {
            recordPreSave(request, response);
            return;
        } else if ("save".equals(action)) {
            upload(request, response);
            Student517 student517 = (Student517) (session.getAttribute("currentUser"));
            List<Excp517> recordList = null;
            try {
                recordList = recordDao.recordListWithNumber(dbUtil.getCon(), record, student517.getStuNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("exceptionList", recordList);
            request.setAttribute("mainPage", "student/exception517.jsp");
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
                    Excp517 excp517 = recordDao.recordShow(con, excpId);
                    System.out.println(excp517);
                    request.setAttribute("exception", excp517);
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

            if ("admin".equals((String) currentUserType)) {

                request.setAttribute("mainPage", "admin/exceptionSave517.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            } else if ("dormManager".equals((String) currentUserType)) {
                request.setAttribute("mainPage", "dormManager/excpSave517.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
            } else if ("student".equals((String) currentUserType)) {
                Student517 student517 = (Student517) (session.getAttribute("currentUser"));
                request.setAttribute("mainPage", "student/exceptionSave517.jsp");
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
            if ("admin".equals((String) currentUserType)) {
                List<Excp517> recordList = recordDao.recordList(con, record);
                request.setAttribute("dormBuildList", recordDao.dormBuildList(con));
                request.setAttribute("exceptionList", recordList);
                request.setAttribute("mainPage", "admin/excp517.jsp");
                request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
            } else if ("dormManager".equals((String) currentUserType)) {
                DormManager502 manager = (DormManager502) (session.getAttribute("currentUser"));
                int buildId = manager.getDormBuildId();
                String buildName = DormBuildDao502.dormBuildName(con, buildId);
                List<Excp517> recordList = recordDao.recordListWithBuild(con, record, buildId);
                request.setAttribute("dormBuildName", buildName);
                request.setAttribute("exceptionList", recordList);
                request.setAttribute("mainPage", "dormManager/excp517.jsp");
                request.getRequestDispatcher("mainManager.jsp").forward(request, response);
            } else if ("student".equals((String) currentUserType)) {
                Student517 student517 = (Student517) (session.getAttribute("currentUser"));
                List<Excp517> recordList = recordDao.recordListWithNumber(con, record, student517.getStuNumber());
                request.setAttribute("exceptionList", recordList);
                request.setAttribute("mainPage", "student/exception517.jsp");
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
        Excp517 excp517 = new Excp517();
        excp517.setExcpId(Integer.parseInt(excpId));
        excp517.setState(state);
        excp517.setDetail(detail);
//        excp.setDormBuildId();
        try {
            Connection con = dbUtil.getCon();

            int lines = recordDao.recordUpdate(con, excp517);
            if (lines != 0) {
                System.out.println("�޸ĳɹ�");
            } else {
                System.out.println("�޸�ʧ��");
            }
            HttpSession session = request.getSession();
            DormManager502 manager = (DormManager502) (session.getAttribute("currentUser"));
            int buildId = manager.getDormBuildId();
            String buildName = DormBuildDao502.dormBuildName(con, buildId);
            List<Excp517> recordList = recordDao.recordListWithBuild(con, excp517, buildId);
            request.setAttribute("dormBuildName", buildName);
            request.setAttribute("exceptionList", recordList);
            request.setAttribute("mainPage", "dormManager/excp517.jsp");
            request.getRequestDispatcher("mainManager.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void upload(HttpServletRequest request, HttpServletResponse response) {

        try {
            Excp517 excp517 = new Excp517();
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
                        excp517.setStuNum(stuNum);
                    } else if ("tel".equals(tmp_name)) {
                        tel = fileitem.getString("utf-8");
                        excp517.setTel(tel);
                    } else if ("dormBuildId".equals(tmp_name)) {
                        dormBuildId = fileitem.getString("utf-8");
                        excp517.setDormBuildId(Integer.parseInt(dormBuildId));
                    } else if ("detail".equals(tmp_name)) {
                        detail = fileitem.getString("utf-8");
                        excp517.setDetail(detail);
                    } else if ("date".equals(tmp_name)) {
                        date = fileitem.getString("utf-8");
                        excp517.setDate(date);
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
                        excp517.setImgUrl(imgUrl);
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
            excp517.setDormBuildName(DormBuildDao502.dormBuildName(con, excp517.getDormBuildId()));
            // �����ݲ��뵽���ݿ���
            int s = recordDao.recordAdd(con, excp517);
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
                Excp517 record = recordDao.recordShow(con, excpId);
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
        Student517 student517 = (Student517) (session.getAttribute("currentUser"));
        String stuNum = student517.getStuNumber();
        int dormBuildId = student517.getDormBuildId();
        Excp517 excp517 = new Excp517();
        excp517.setStuNum(stuNum);
        excp517.setDormBuildId(dormBuildId);
        excp517.setDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));

        Connection con = null;
        try {
            con = dbUtil.getCon();
            Calendar rightNow = Calendar.getInstance();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            excp517.setDormBuildName(DormBuildDao502.dormBuildName(con, dormBuildId));
            String sysDatetime = fmt.format(rightNow.getTime());
            request.setAttribute("studentNumber", stuNum);
            request.setAttribute("date", sysDatetime);
            request.setAttribute("exception", excp517);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("mainPage", "student/upload517.jsp");
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
