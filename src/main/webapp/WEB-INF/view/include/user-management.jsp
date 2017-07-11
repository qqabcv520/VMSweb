<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/elfunctions.tld" prefix="my"%>  
	


<c:set var="context_" scope="page" value="${pageContext.request.contextPath}"/>
<c:set var="tabName" scope="page" value="user"/>





<div id="${tabName }">
	<div id="${tabName }-table-toolbar">
		<c:if test="${my:contains(LOGIN_USER_KEY.permissionsNames, 'user_edit') }">
			<button type="button" class="btn btn-default" id="add-${tabName }"
				data-toggle="modal" data-target="#${tabName }-add-modal">
				<i class="icon icon-plus-sign"></i>
				增加用户
			</button>
			<button type="button" class="btn btn-default only-more" id="edit-${tabName }"
				disabled="disabled" data-toggle="modal" data-target="#${tabName }-edit-modal">
				<i class="icon icon-edit"></i>
				编辑用户
			</button>
			<button type="button" class="btn btn-default only-more"
				id="delete-${tabName }" disabled="disabled">
				<i class="icon icon-trash"></i>
				删除用户
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
				<th data-field="userid" data-sortable="true">ID</th>
				<th data-field="userimg" data-formatter="${tabName }ImgFormatter" data-align="center">头像</th>
				<th data-field="username" data-sortable="true">用户名</th>
				<th data-field="userrealname" data-sortable="true">姓名</th>
				<th data-field="userworkid" data-sortable="true">工号</th>
				<th data-field="userdepartment" data-sortable="true">部门</th>
				<th data-field="userduty" data-sortable="true">职务</th>
				<th data-field="userphonenumber" data-sortable="true">电话</th>
				<th data-field="userlastlogintime" data-sortable="true">最后登录时间</th>
				<th data-field="role.rolename"  data-sortable="true">权限</th>
				<th data-field="usernote" data-sortable="true">备注</th>
			</tr>
		</thead>
	
	</table>
	
	<div class="modal fade" id="${tabName }-add-modal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${tabName }-add-label">
		<div class="modal-dialog" role="document">
			<form class="modal-content" action="${context_}/main/${tabName }/add.do" method="post" enctype="multipart/form-data">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="${tabName }-add-label">增加用户</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>用户名</label>
						<input type="text" class="form-control" datatype="s1-32" name="username">
					</div>
					<div class="form-group">
						<label>工号</label>
						<input type="text" class="form-control" datatype="*1-32" name="workid">
					</div>
					<div class="form-group">
						<label>姓名</label>
						<input type="text" class="form-control" datatype="*1-32" name="realname">
					</div>
					
					<div class="form-group">
						<label>部门</label>
						<input type="text" class="form-control" datatype="*1-32" name="department">
					</div>
					<div class="form-group">
						<label>职位</label>
						<input type="text" class="form-control" datatype="*1-32" name="duty">
					</div>
					<div class="form-group">
						<label>手机号码</label>
						<input type="text" class="form-control" datatype="m" name="phonenumber">
					</div>
					<div class="form-group">
						<label>照片</label> <input class="form-control" type="file"
							name="img" accept="image/*" datatype="*">
					</div>
					<div class="form-group">
						<label>权限组</label>
						<select class="form-control" value="" name="roleid" datatype="*">
							<option value="">请选择权限组</option>
						 	<c:forEach var="role" items="${roleList}" >
								<option value="${role.roleid}">${role.rolename}</option>
	                 		</c:forEach>
							
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
	
	<div class="modal fade" id="${tabName }-edit-modal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="${tabName }-edit-label">
		<div class="modal-dialog" role="document">
			<form class="modal-content" action="${context_}/main/${tabName }/edit.do" method="post" enctype="multipart/form-data">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="${tabName }-edit-label">编辑用户</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="${tabName }-edit-id" name="idArray">
					<div class="form-group">
						<label>用户名：</label>
						<span id="username"></span>
					</div>
					<div class="form-group">
						<label>密码</label>
						<input type="password" class="form-control" name="pwd"  datatype="*0-0|*8-32">
					</div>
					<div class="form-group">
						<label>工号</label>
						<input type="text" class="form-control" name="workid"  datatype="*0-32">
					</div>
					<div class="form-group">
						<label>姓名</label>
						<input type="text" class="form-control" name="realname" datatype="*0-32">
					</div>
					<div class="form-group">
						<label>部门</label>
						<input type="text" class="form-control" name="department" datatype="*0-32">
					</div>
					<div class="form-group">
						<label>职位</label>
						<input type="text" class="form-control" name="duty" datatype="*0-32">
					</div>
					<div class="form-group">
						<label>手机号码</label>
						<input type="text" class="form-control" name="phonenumber" datatype="*0-0|m">
					</div>
					<div class="form-group">
						<label>照片</label>
						<input type="file" accept="image/*" class="form-control" name="img">
					</div>
					<div class="form-group">
						<label>权限组</label>
						<select class="form-control" value="" name="roleid">
						 	<c:forEach var="role" items="${roleList}" >
								<option value="${role.roleid}">${role.rolename}</option>
	                 		</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label>备注</label>
						<input type="text" class="form-control"  name="note" datatype="*0-120">
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


function ${tabName}ImgFormatter(value, row, index) {
	return '<img class="img-thumbnail" data-group="' + Math.random() + '" width="50px" height="50px" src="${context_ }' + value + '">';
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
				$.zui.messager.show("提交成功");
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

	 	$("#${tabName }-edit-modal [id='username']").html(equals("username"));
		$("#${tabName }-edit-modal [name='workid']").val(equals("userworkid"));
		$("#${tabName }-edit-modal [name='realname']").val(equals("userrealname"));
		$("#${tabName }-edit-modal [name='department']").val(equals("userdepartment"));
		$("#${tabName }-edit-modal [name='duty']").val(equals("userduty"));
		$("#${tabName }-edit-modal [name='phonenumber']").val(equals("userphonenumber"));
		$("#${tabName }-edit-modal [name='note']").val(equals("usernote"));
		$("#${tabName }-edit-modal [name='roleid']").val(equals("role.roleid"));
		
		
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