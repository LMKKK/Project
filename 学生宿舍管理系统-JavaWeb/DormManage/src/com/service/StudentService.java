package com.service;

import com.dao.StudentDao;
import com.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class StudentService {

    private StudentDao studentDao = new StudentDao();


    public void deleteStudentById(Connection con, String studentId) {

        try {
            studentDao.studentDelete(con, studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据条件获取学生列表
     * @param request
     * @param response
     */
    public void getStudentList(HttpServletRequest request, HttpServletResponse response){



    }

}
