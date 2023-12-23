package com.web;

import com.constrant.LoginConstrant;
import com.constrant.PageConstrant;
import com.constrant.UserType;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/blank")
public class BlankServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object currentUserType = session.getAttribute(LoginConstrant.CURRENT_USER_TYPE);
        if (UserType.ADMIN.equals((String) currentUserType)) {
            request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.ADMIN_BLANK_PAGE);
            request.getRequestDispatcher(PageConstrant.ADMIN_MAIN_PAGE).forward(request, response);
        } else if (UserType.DORM_MANAGER.equals((String) currentUserType)) {
            request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.MANAGER_BLANK_PAGE);
            request.getRequestDispatcher(PageConstrant.MANAGER_MAIN_PAGE).forward(request, response);
        } else if (UserType.STUDENT.equals((String) currentUserType)) {
            request.setAttribute(PageConstrant.MAIN_PAGE, PageConstrant.STUDENT_BLANK_PAGE);
            request.getRequestDispatcher(PageConstrant.STUDENT_MAIN_PAGE).forward(request, response);
        }
    }


}
