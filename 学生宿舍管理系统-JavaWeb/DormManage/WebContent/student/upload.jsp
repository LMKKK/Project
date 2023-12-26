<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="data_list">

    <form action="excp?action=save" method="post" enctype="multipart/form-data">
        <table align="center">
            <input type="hidden" name="stuNum" value="${exception.stuNum}">
            <input type="hidden" name="dormBuildId" value="${exception.dormBuildId}">
            <tr>
                <td>时间</td>
                <td><input readonly type="text" value="${exception.date}" name="date"></td>
            </tr>
            <tr>
                <td>地点</td>
                <td><input readonly type="text" value="${exception.dormBuildName}" name=""></td>
            </tr>
            <tr>
                <td>联系电话</td>
                <td>
                    <input type="text" value="${exception.tel}" name="tel">
                </td>
            </tr>
            <tr>
                <td>备注</td>
                <td>
                    <input type="text" name="detail">
                </td>
            </tr>
            <tr>
                <td>图片</td>
                <td>
                    <input type="file" name="myPhoto" accept="image/png,image/jpeg,image/bmp,image/webp">
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="保存">
                </td>
            </tr>
        </table>
    </form>

</div>
