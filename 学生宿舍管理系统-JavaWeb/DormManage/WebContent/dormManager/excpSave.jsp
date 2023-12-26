<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    function checkForm() {
        var studentNumber = document.getElementById("studentNumber").value;
        var detail = document.getElementById("detail").value;
        if (studentNumber == "" || detail == "") {
            document.getElementById("error").innerHTML = "信息填写不完整！";
            return false;
        }
        return true;
    }

    $(document).ready(function () {
        $("ul li:eq(2)").addClass("active");
    });
</script>
<div class="data_list">
    <div class="data_list_title">
        异常情况报备
    </div>
    <form action="excp?action=update" method="post" onsubmit="return checkForm()">
        <div class="data_form">
            <input type="hidden" id="recordId" name="excpId" value="${exception.excpId }"/>
            <table align="center" style="height: 500px;width: 300px">
                <tr>
                    <td>日期</td>
                    <td><input type="text" id="date" name="date" value="${exception.date==null?date:exception.date }"
                               style="margin-top:5px;height:30px;" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td><font color="red">*</font>地点</td>
                    <td><input type="text" id="dormBuildName" name="date"
                               value="${exception.dormBuildName==null?dormBuildName:exception.dormBuildName }"
                               style="margin-top:5px;height:30px;" ${exception.dormBuildName!=null?'readonly':'' }/>
                    </td>
                </tr>
                <tr>
                    <td><font color="red">*</font>联系方式</td>
                    <td><input type="text" id="tel" name="date"
                               value="${exception.tel==null?tel:exception.tel }"
                               style="margin-top:5px;height:30px;" ${exception.tel!=null?'readonly':'' }/>
                    </td>
                </tr>
                <tr>
                    <td><font color="red">*</font>状态</td>
                    <td>
                        <select name="state">
                            <option value="1">处理中</option>
                            <option value="2">已完成</option>
                        </select>
                    </td>
                    <%--                    <td><input type="text" value="${exception.state}" readonly></td>--%>
                </tr>
                <tr>
                    <td><font color="red">*</font>备注</td>
                    <td><input type="text" id="detail" name="detail" value="${exception.detail }"
                               style="margin-top:5px;height:30px;"/></td>
                </tr>
                <tr>
                    <td><font>图片</font></td>
                    <td>
                        <img src="${pageContext.request.contextPath}/resources/img/${exception.imgUrl}"
                             style="width:240px;height: 400px">
                    </td>
                </tr>
            </table>
            <div align="center">
                <input type="submit" class="btn btn-primary" value="保存"/>
                &nbsp;<button class="btn btn-primary" type="button" onclick="javascript:window.location='excp'">返回
            </button>
            </div>
            <div align="center">
                <font id="error" color="red">${error }</font>
            </div>
        </div>
    </form>
</div>