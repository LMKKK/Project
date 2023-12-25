<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

    $(document).ready(function () {
        // $("ul li:eq(1)").addClass("active");
        $(".dataTable").dataTable({
            "oLanguage": {
                "sUrl": "${pageContext.request.contextPath}/resources/media/zh_CN.json"
            },
            "bLengthChange": false, //改变每页显示数据数量
            "bFilter": false, //过滤功能
        });
    });

    window.onload = function () {
        $("#DataTables_Table_0_wrapper .row-fluid").remove();
    };

    function dormBuildDelete(dormBuildId) {
        if (confirm("您确定要删除这个宿舍楼吗？")) {
            window.location = "dormBuild?action=delete&dormBuildId=" + dormBuildId;
        }
    }

    $(document).ready(function () {
        $("ul li:eq(3)").addClass("active");
    });
</script>

<div class="data_list">
    <div class="data_list_title">
        宿舍楼管理
    </div>
    <form name="myForm" class="form-search" method="post" action="dormBuild?action=list">
        <button class="btn btn-success" type="button" style="margin-right: 50px;"
                onclick="javascript:window.location='dormBuild?action=preSave'">添加
        </button>
        <span class="data_search">
					名称:&nbsp;&nbsp;<input id="s_dormBuildName" name="searchBuildName" type="text"
                                          style="width:120px;height: 30px;" class="input-medium search-query"
                                          value="${ searchBuildName }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
    </form>
    <div>
        <table class="table table-striped table-bordered table-hover dataTable">
            <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>简介</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach varStatus="i" var="dormBuild" items="${ dormBuildList }">
                <tr>
                    <td>${dormBuild.dormBuildId}</td>
                    <td>${dormBuild.dormBuildName }</td>
                    <td>${dormBuild.detail==null||dormBuild.detail==""?"无":dormBuild.detail }</td>
                    <td>
                        <button class="btn btn-mini btn-info" type="button"
                                onclick="javascript:window.location='dormBuild?action=assignManager&dormBuildId=${dormBuild.dormBuildId }'">
                            管理员
                        </button>&nbsp;
                        <button class="btn btn-mini btn-info" type="button"
                                onclick="javascript:window.location='dormBuild?action=preSave&dormBuildId=${dormBuild.dormBuildId }'">
                            修改
                        </button>&nbsp;
                        <button class="btn btn-mini btn-danger" type="button"
                                onclick="dormBuildDelete(${dormBuild.dormBuildId})">删除
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div align="center"><font color="red">${error }</font></div>
    <div class="pagination pagination-centered">
    </div>
</div>
