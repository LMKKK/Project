<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="com.model.Admin" %>
<%@ page import="com.model.DormManager" %>
<%@ page import="com.model.Student" %>
<%@ page import="com.model.Admin" %>

<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>宿舍管理系统登录</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        function checkForm() {
            var userName = document.getElementById("userName").value;
            var password = document.getElementById("password").value;
            var userTypes = document.getElementsByName("userType");
            var userType = null;
            for (var i = 0; i < userTypes.length; i++) {
                if (userTypes[i].checked) {
                    userType = userTypes[i].value;
                    break;
                }
            }
            if (userName == null || userName == "") {
                document.getElementById("error").innerHTML = "用户名不能为空";
                return false;
            }
            if (password == null || password == "") {
                document.getElementById("error").innerHTML = "密码不能为空";
                return false;
            }
            if (userType == null || userType == "") {
                document.getElementById("error").innerHTML = "请选择用户类型";
                return false;
            }
            return true;
        }
    </script>

    <style type="text/css">
        body {
            padding-top: 200px;
            padding-bottom: 40px;
        }

        .radio {
            padding-top: 10px;
            padding-bottom: 10px;
        }

        .form-signin-heading {
            text-align: center;
        }

        .form-signin {
            max-width: 346px;
            padding: 20px 50px 35px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
            box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
        }

        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }

        .form-signin {
            border-radius: 48px;
            background: #dcd6d6;
            box-shadow: 34px -34px 68px #bbb6b6,
            -34px 34px 68px #fdf6f6;
        }

        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }

        .btn-container {
            display: flex;
            padding: 0;
            margin: 0;
            width: 100%;
            align-items: center;
            justify-content: space-around;
        }
    </style>

</head>
<body>
<%
    if (request.getAttribute("user") == null) {

        String userName = null;
        String password = null;
        String userType = null;
        String remember = null;

        /*  获取Cookie中存储的用户信息，在本页面中实现自动填充        */
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("dormuser")) {
                userName = cookies[i].getValue().split("-")[0];
                password = cookies[i].getValue().split("-")[1];
                userType = cookies[i].getValue().split("-")[2];
                remember = cookies[i].getValue().split("-")[3];
            }
        }

        if (userName == null) {
            userName = "";
        }

        if (password == null) {
            password = "";
        }

        if (userType == null) {
            userType = "";
        } else if ("admin".equals(userType)) {
            pageContext.setAttribute("user", new Admin(userName, password));
            pageContext.setAttribute("userType", 1);
        } else if ("dormManager".equals(userType)) {
            pageContext.setAttribute("user", new DormManager(userName, password));
            pageContext.setAttribute("userType", 2);
        } else if ("student".equals(userType)) {
            pageContext.setAttribute("user", new Student(userName, password));
            pageContext.setAttribute("userType", 3);
        }

        if ("yes".equals(remember)) {
            pageContext.setAttribute("remember", 1);
        }

    }
%>
<div class="container">
    <form name="myForm" class="form-signin" action="login" method="post" onsubmit="return checkForm()">
        <h2 class="form-signin-heading"><font color="gray">宿舍管理系统</font></h2>
        <input id="userName" name="userName" value="${user.userName }" type="text" class="input-block-level"
               placeholder="用户名...">
        <input id="password" name="password" value="${user.password }" type="password" class="input-block-level"
               placeholder="密码...">
        <label class="radio inline">
            <input id="admin" type="radio" name="userType" value="1" checked/> 系统管理员
        </label>
        <label class="radio inline">
            <input id="dormManager" type="radio" name="userType"
                   value="2" ${userType==2?'checked':''} />宿舍管理员
        </label>
        <label class="radio inline">
            <input id="student" type="radio" name="userType" value="3"  ${userType==3?'checked':''}/> 学生
        </label>
        <label class="checkbox">
            <input id="remember" name="remember" type="checkbox" value="1" ${remember==1?'checked':''}>记住我
            &nbsp;&nbsp;&nbsp;&nbsp; <font id="error" color="red">${error }</font>
        </label>
        <div class="btn-container">
            <button class="btn btn-large btn-primary" type="submit">登录</button>
            <input type="reset" class="btn btn-large btn-primary" value="重置">
        </div>
    </form>
</div>
</body>
</html>