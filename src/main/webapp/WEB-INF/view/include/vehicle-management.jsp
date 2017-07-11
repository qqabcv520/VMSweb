<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/elfunctions.tld" prefix="my"%>  
	


<c:set var="context_" scope="page" value="${pageContext.request.contextPath}"/>
<c:set var="tabName" scope="page" value="vehicle"/>





<div id="${tabName }">
	<div id="${tabName }-table-toolbar">
		<c:if test="${my:contains(LOGIN_USER_KEY.permissionsNames, 'vehicle_edit') }">
			<button type="button" class="btn btn-default" id="add-${tabName }"
				data-toggle="modal" data-target="#${tabName }-add-modal">
				<i class="icon icon-plus-sign"></i>
				增加车辆
			</button>
			<button type="button" class="btn btn-default only-more" id="edit-${tabName }"
				disabled="disabled" data-toggle="modal" data-target="#${tabName }-edit-modal">
				<i class="icon icon-edit"></i>
				编辑车辆
			</button>
			<button type="button" class="btn btn-default only-more"
				id="delete-${tabName }" disabled="disabled">
				<i class="icon icon-trash"></i>
				删除车辆
			</button>
		</c:if>
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
				<th data-field="vehicleid" data-sortable="true">ID</th>
				<th data-field="vehiclephoto" data-formatter="${tabName }ImgFormatter" data-align="center">车辆照片</th>
				<th data-field="vehicleplatenumber" data-sortable="true">车牌号</th>
				<th data-field="vehicleinfo" data-sortable="true">车辆信息</th>
				<th data-field="vehiclestate" data-formatter="${tabName }StateFormatter" data-sortable="true">车辆状态</th>
				<th data-field="vehiclenote" data-sortable="true">备注</th>
			</tr>
		</thead>
	
	</table>
	
	<div class="modal fade" id="${tabName }-add-modal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${tabName }-add-label">
		<div class="modal-dialog" role="document">
			<form class="modal-content" action="${context_}/main/${tabName }/add.do" method="post"  enctype="multipart/form-data">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="${tabName }-add-label">增加车辆</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>车牌号</label>
						<input type="text" class="form-control" datatype="*2-30" name="platenumber">
					</div>
					<div class="form-group">
						<label>车辆信息</label>
						<input type="text" class="form-control" datatype="*0-120" name="info">
					</div>
					<div class="form-group">
						<label>车辆照片</label>
						<input type="file" class="form-control" datatype="*" name="img" accept="image/*" >
					</div>
					<div class="form-group">
						<label>状态</label>
						<select class="form-control" name="state" datatype="*">
							<option value="0">可用</option>
							<option value="1">使用中</option>
							<option value="2">不可用</option>
						</select>
					</div>
					<div class="form-group">
						<label>备注</label>
						<input type="text" class="form-control" name="note" datatype="*0-120">
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">提交</button>
				</div>
			</form>
		</div>
	</div>
	
	<div class="modal fade" id="${tabName }-edit-modal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${tabName }-edit-label">
		<div class="modal-dialog" role="document">
			<form class="modal-content" action="${context_}/main/${tabName }/edit.do" method="post"  enctype="multipart/form-data">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="${tabName }-edit-label">编辑车辆</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="${tabName }-edit-id" name="idArray">
					<div class="form-group">
						<label>车牌号：</label>
						<span id="platenumber"></span>
					</div>
					<div class="form-group">
						<label>车辆信息</label>
						<input type="text" class="form-control" name="info" datatype="*0-120">
					</div>
					<div class="form-group">
						<label>车辆照片</label>
						<input class="form-control" type="file" name="img" accept="image/*">
					</div>
					<div class="form-group">
						<label>状态</label>
						<select class="form-control" name="state">
							<option value="0">可用</option>
							<option value="1">使用中</option>
							<option value="2">不可用</option>
						</select>
					</div>
					<div class="form-group">
						<label>备注</label>
						<input type="text" class="form-control" name="note"  datatype="*0-120">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="submit" class="btn btn-primary">提交</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script>

function ${tabName }StateFormatter(value, row, index) {
	switch (value) {
	case 0:
		return "可用";
	case 1:
		return "使用中";
	case 2:
		return "不可用";
	}
}

function ${tabName }ImgFormatter(value, row, index) {
	if(value){
		return '<img class="img-thumbnail" data-group="' + Math.random() + '" width="50px" height="50px" src="${context_ }' + value + '">';
	}
}

$(function(){

	var $table = $("#${tabName }-table");
	$table.bootstrapTable();
	
	
	$("#${tabName } form").iframeUpload({
		onComplate: function(data) {
			if(data.statusCode == 0) {
				$("#${tabName }-add-modal").modal('hide');
				$("#${tabName }-edit-modal").modal('hide');
				$table.bootstrapTable('refresh');
				$.zui.messager.show("修改成功");
			} else {
				$.zui.messager.show(data.status);
			}
			
		}
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
	
	$("#edit-${tabName }").click(function() {
		
		var rows = $table.bootstrapTable("getSelections");

		var IDArray = [];
		for (var i in rows) {
			IDArray.push(rows[i].${tabName}id);
		}
		
		
		$("#${tabName }-edit-id").val("[" + IDArray.join() + "]");

	 	$("#${tabName }-edit-modal [id='platenumber']").html(equals("vehicleplatenumber"));
		$("#${tabName }-edit-modal [name='info']").val(equals("vehicleinfo"));
		$("#${tabName }-edit-modal [name='state']").val(equals("vehiclestate"));
		$("#${tabName }-edit-modal [name='note']").val(equals("vehiclenote"));
		
		
		//比较选择的字段的值是否相等
		function equals(field) {
			var fields = field.split(".");
			var temp = "";
			try {
				var t = rows[0];
				for ( var i in fields) {
					t = t[fields[i]];
				}
				temp = t;
				for (var i = 1; i < rows.length; i++) {
					t = rows[i];
					for ( var j in fields) {
						t = t[fields[j]];
					}
					if(temp != t) {
						temp = "";
						break;
					}
				}
			} catch (e) {
				temp = "";
			}
			return temp;
		}
	});
	
	//删除数据
	$("#delete-${tabName }").click(function() {
		
		var rows = $table.bootstrapTable("getSelections");

		var IDArray = [];
		for (var i in rows) {
			IDArray.push(rows[i].${tabName}id);
		}
		
		if(!confirm("确定删除这 " + IDArray.length + " 项吗？")) {
			return;
		}

		
		var URL = "${context_ }/main/${tabName }/delete.do";
		$.post(URL, 
			{idArray: IDArray}, 
			function(data){
				if(data.statusCode == 0){
					$table.bootstrapTable("remove", {field:"${tabName }id", values:IDArray});
					$.zui.messager.show(data.data + "条数据被删除");
					$(".only-one").attr("disabled", "disabled");
					$(".only-more").attr("disabled", "disabled");
				} else {
					$.zui.messager.show(data.status);
				}
			}
		);
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
	
	$table.on('all.bs.table', function() {
		$(this).find('img').lightbox();
	})
});
</script>