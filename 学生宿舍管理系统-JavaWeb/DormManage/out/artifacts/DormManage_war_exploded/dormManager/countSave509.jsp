<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    function checkForm() {
        var stuName = document.getElementById("stuName").value;
        var detail = document.getElementById("detail").value;
        if (stuName == "" || detail == "") {
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
        登记出入记录
    </div>
    <form action="count?action=save" method="post" onsubmit="return checkForm()">
        <div class="data_form">
            <input type="hidden" name="dormBuildId" value="${count509.dormBuildId}">
            <table align="center">
                <tr>
                    <td><font color="red"></font>学号：</td>
                    <td><input type="text" id="stuNum" name="stuNum"
                               style="margin-top:5px;height:30px;" placeholder="非必填" }/></td>
                </tr>
                <tr>
                    <td><font color="red">*</font>姓名</td>
                    <td>
                        <input type="text" id="stuName" name="stuName">
                    </td>
                </tr>
                <tr>
                    <td>地点</td>
                    <td>
                        <input type="text" readonly value="${count509.dormBuildName}" name="dormBuildName">
                    </td>
                </tr>
                <tr>
                    <td><font color="red">*</font>状态</td>
                    <td>
                        <select name="state">
                            <option value="0">出</option>
                            <option value="1">入</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><font color="red">*</font>时间：</td>
                    <td><input type="text" id="date" name="date" value="${count509.date }"
                               style="margin-top:5px;height:30px;" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td><font color="red">*</font>备注：</td>
                    <td><input type="text" id="detail" name="detail"
                               style="margin-top:5px;height:30px;"/></td>
                </tr>
            </table>
            <div align="center">
                <input type="submit" class="btn btn-primary" value="保存"/>
                &nbsp;<button class="btn btn-primary" type="button" onclick="javascript:window.location='count509'">返回
            </button>
            </div>
            <div align="center">
                <font id="error" color="red">${error }</font>
            </div>
        </div>
    </form>
</div>