package com.web;

import com.constrant.LoginConstrant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 退出登录
        // 只需要清空Cookie和Session
        HttpSession session = request.getSession();
        session.removeAttribute("currentUser");

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(LoginConstrant.COOKIE_KEY.equals(cookie.getName())){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }

        // 重新登陆，跳转到登录页面
        response.sendRedirect("login.jsp");

    }
}
