<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	


<c:set var="context_" scope="page" value="${pageContext.request.contextPath}"/>
<c:set var="tabName" scope="page" value="application"/>

<link rel="stylesheet" type="text/css" href="${context_}/plugin/datetimepicker/datetimepicker.min.css">

<script type="text/javascript" src="${context_}/plugin/datetimepicker/datetimepicker.js"></script>



<div id="${tabName }">
	<div id="${tabName }-table-toolbar">
			<button type="button" class="btn btn-default" id="export"
			data-toggle="modal" data-target="#export-modal">
				<i class="icon icon-edit"></i>
				导出报表
			</button>
	
	</div>
	<table id="${tabName }-table"
		data-url="${context_}/main/${tabName }/data.do" 
		data-click-to-select="true"
		data-toolbar="#${tabName }-table-toolbar" 
		data-smart-display="true"
		data-pagination="true" 
		data-page-list="" 
		data-page-size="50"
		data-cache="false" 
		data-maintain-selected="true" 
		data-show-columns="true" 
		data-show-refresh="true"
		data-show-toggle="true" 
		data-show-export="true"
		data-side-pagination="server" 
		data-escape="true"
		data-response-handler="tableResponseHandler">
		<!-- data-export-data-type="basic" -->
		<thead>
			<tr>
				<th data-field="check" data-checkbox="true"></th>
				<th data-field="applicationid" data-sortable="true">ID</th>
				<th data-field="usersByApplicationuser.username" data-sortable="true">申请人</th>
				<th data-field="usersByApplicationsendpeople.username" data-sortable="true">派车人</th>
				<th data-field="vehicle.vehicleplatenumber" data-sortable="true">车牌号</th>
				<th data-field="driver.drivername" data-sortable="true">驾驶员</th>
				<th data-field="applicationreason" data-sortable="true">用车事由</th>
				<th data-field="applicationstartposition" data-sortable="true">开始点</th>
				<th data-field="applicationendposition" data-sortable="true">结束点</th>
				<th data-field="applicationstarttime" data-sortable="true">开始时间</th>
				<th data-field="applicationendtime" data-sortable="true">结束时间</th>
				<th data-field="applicationdistance" data-sortable="true">公里数</th>
				<th data-field="applicationnote" data-sortable="true">备注</th>
				<th data-field="applicationispass"  data-formatter="${tabName }StateFormatter" data-sortable="true">审核状态</th>
			</tr>
		</thead>
	
	</table>
	
	<div class="modal fade" id="export-modal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="export-label">
		<div class="modal-dialog" role="document">
			<form class="modal-content" action="${context_}/main/${tabName }/export.do" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="export-label">导出报表</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>开始时间</label>
						<input type="text" class="form-control form-datetime" name="startTime" datatype="/(\d{4})\-(\d{2})\-(\d{2})/">
					</div>
					<div class="form-group">
						<label>结束时间</label>
						<input type="text" class="form-control form-datetime" name="endTime" datatype="/(\d{4})\-(\d{2})\-(\d{2})/">
					</div>
					<div class="form-group">
						<label>导出类型</label>
						<select class="form-control" name="type">
							<option value="0">所有记录</option>
							<option value="1">已完成记录</option>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">导出</button>
				</div>
			</form>
		</div>
	</div>
	
	
</div>
<script>

function ${tabName}StateFormatter(value, row, index) {
	
	switch (value) {
	case 0:
		return "待审核";
	case 1:
		return "已通过";
	case 2:
		return "已驳回";
	case 3:
		return "已还车";

	}
	
	
}


$(function(){

	var $table = $("#${tabName }-table");
	$table.bootstrapTable();
	
	
	$(".form-datetime").datetimepicker(
	{
	    language:  "zh-CN",
	    weekStart: 1,
	    todayBtn:  1,
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    minView: 2,
	    forceParse: 0,
	    format: "yyyy-mm-dd"
	});
	
	//表单验证
	var form = null;
	form = $("#${tabName } .modal form").Validform({
		tiptype: function(msg, o, cssctl){
			if(o.type == 3){
				o.obj.tooltip("destroy");
				o.obj.tooltip({
					title: msg,
					trigger: 'manual'
					});
				o.obj.tooltip('show');
			} else {
				o.obj.tooltip('hide');
			}
		},
		tipSweep: true
	});
	
	//关闭时重置表单
	$(".modal").on('hidden.zui.modal', function (e) {
		form.resetForm();
		$(this).find("form *").tooltip('hide');
	});
	
	
	
	
	
	//toolbar按钮的启用和禁用
	$table.on('load-success.bs.table check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function() {
		
		var rows = $(this).bootstrapTable("getSelections");
		if(rows.length == 1) {
			$(".only-one").removeAttr("disabled");
			$(".only-more").removeAttr("disabled");
		} else if(rows.length > 1){
			$(".only-more").removeAttr("disabled");
			$(".only-one").attr("disabled", "disabled");
		} else {
			$(".only-one").attr("disabled", "disabled");
			$(".only-more").attr("disabled", "disabled");
		}
	});
	
});
</script>