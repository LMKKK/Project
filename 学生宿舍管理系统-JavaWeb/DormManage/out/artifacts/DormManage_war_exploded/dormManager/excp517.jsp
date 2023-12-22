<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    $(document).ready(function () {
        $("ul li:eq(1)").addClass("active");
        $('.form_date').datetimepicker({
            language: 'en',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
        $('.datatable').dataTable({
            "oLanguage": {
                "sUrl": "${pageContext.request.contextPath}/resources/media/zh_CN.json"
            },
            "bLengthChange": false, //改变每页显示数据数量
            "bFilter": false, //过滤功能
        });
        $("#DataTables_Table_0_wrapper .row-fluid").remove();
    });

    window.onload = function () {
        $("#DataTables_Table_0_wrapper .row-fluid").remove();
    };

    function recordDelete(recordId) {
        if (confirm("您确定要删除这条记录吗？")) {
            window.location = "excp?action=delete&excpId=" + recordId;
        }
    }
</script>

<div class="data_list">
    <div class="data_list_title">
        异常报备记录
    </div>
    <form name="myForm" class="form-search" method="post" action="excp?action=search"
          style="padding-bottom: 0px">
        <%--        <button class="btn btn-success" type="button" style="margin-right: 50px;float:left;"--%>
        <%--                onclick="javascript:window.location='excp?action=preSave'">添加--%>
        <%--        </button>--%>
        <div class="text-right" style="display: inline-block;width: 70%;float:right">
            <span class="input-group date form_date col-md-2 text-right" data-date="" data-date-format="yyyy-mm-dd"
                  data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control" size="16" name="startDate" value="${startDate}" type="text" value=""
                           readonly style="width:120px;height: 30px;" placeholder="起始日期">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				<input type="hidden" id="dtp_input2" value=""/><br/>
            </span>
            <span class="input-group date form_date col-md-2 text-right" data-date="" data-date-format="yyyy-mm-dd"
                  data-link-field="dtp_input3" data-link-format="yyyy-mm-dd">
                    <input class="form-control" size="16" name="endDate" value="${endDate}" type="text" value=""
                           readonly style="width:120px;height: 30px;" placeholder="终止日期">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				<input type="hidden" id="dtp_input3" value=""/><br/>
            </span>
            <span><font style="font-family: '黑体'; font-style: 'bold'; font-size: 20px"
                        color="blue">${dormBuildName }&nbsp;&nbsp;</font></span>

            &nbsp;<input id="s_studentText" name="s_studentText" type="text" style="width:120px;height: 30px;"
                         class="input-medium search-query" value="${s_studentText }">
            &nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索
        </button>
        </div>
    </form>
    <div>
        <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <td>序号</td>
                <th>日期</th>
                <th>宿舍楼名称</th>
                <th>联系电话</th>
                <th>状态</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach varStatus="i" var="exception" items="${exceptionList }">
                <tr>
                    <td>${i.count}</td>
                    <td>${exception.date }</td>
                    <td>${exception.dormBuildName }</td>
                    <td>${exception.tel }</td>
                    <td>${exception.state }</td>
                    <td>${exception.detail==null?"无":exception.detail }</td>
                    <td>
                        <button class="btn btn-mini btn-danger" type="button"
                                onclick="javascript:window.location='excp?action=show&excpId=${exception.excpId }'">
                            查看详情
                        </button>
                        <button class="btn btn-mini btn-danger" type="button"
                                onclick="recordDelete(${exception.excpId })">撤销
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div align="center"><font color="red">${error }</font></div>
</div>