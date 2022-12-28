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
</script>

<div class="data_list">
    <div class="data_list_title">
        违禁记录
    </div>
    <form name="myForm" class="form-search" method="post" action="record?action=search" style="padding-bottom: 0px">
        <div class="text-right" style="display: inline-block;width: 70%;float:right">
                   <span class="input-group date form_date col-md-2 text-right" data-date=""
                         data-date-format="yyyy-mm-dd"
                         data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control" size="16" name="startDate" value="${startDate}" type="text" value=""
                           readonly style="width:120px;height: 30px;" placeholder="起始日期">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				<input type="hidden" id="dtp_input2" value=""/><br/>
            </span>
            <span class="input-group date form_date col-md-2 text-right" data-date=""
                  data-date-format="yyyy-mm-dd"
                  data-link-field="dtp_input3" data-link-format="yyyy-mm-dd">
                    <input class="form-control" size="16" name="endDate" value="${endDate}" type="text" value=""
                           readonly style="width:120px;height: 30px;" placeholder="终止日期">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				<input type="hidden" id="dtp_input3" value=""/><br/>
            </span>
            &nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索
        </button>
        </div>
    </form>
    <div>
        <table class="table table-striped table-bordered table-hover datatable">
            <thead>
            <tr>
                <th>日期</th>
                <th>学号</th>
                <th>姓名</th>
                <th>宿舍楼</th>
                <th>寝室</th>
                <th>备注</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach varStatus="i" var="record509" items="${recordList }">
                <tr>
                    <td>${record509.date }</td>
                    <td>${record509.studentNumber }</td>
                    <td>${record509.studentName }</td>
                    <td>${record509.dormBuildName==null?"无":record509.dormBuildName }</td>
                    <td>${record509.dormName }</td>
                    <td>${record509.detail }</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div align="center"><font color="red">${error }</font></div>
</div>