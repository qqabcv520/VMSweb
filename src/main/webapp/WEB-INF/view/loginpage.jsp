<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="context_" scope="page" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<meta name="description" content="车辆管理系统">
<meta name="author" content="睿意科技">
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   

<title>车辆管理系统</title>

<link rel="stylesheet" type="text/css" href="${context_}/plugin/zui/css/zui.min.css">
<link rel="stylesheet" type="text/css" href="${context_}/plugin/bootstrap-table/css/bootstrap-table.min.css">
<link rel="stylesheet" type="text/css" href="${context_}/css/loginpage.css">

<script type="text/javascript" src="${context_}/plugin/jquery-1.12.3.js"></script>
<script type="text/javascript" src="${context_}/plugin/zui/js/zui.js"></script>
<script type="text/javascript" src="${context_}/plugin/bootstrap-table/js/bootstrap-table.js"></script>
<script type="text/javascript" src="${context_}/plugin/bootstrap-table/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${context_}/plugin/validform/js/Validform_v5.3.2.js"></script>
<!--[if lt IE 9]>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/respond.js"></script>
<![endif]-->




<script type="text/javascript">
		var context_ = '${context_}';
		var templateUrl = '${moduleName}';
</script>

</head>

<body>

	<div class="container">
		<form class="form-signin" action="${context_}/login.do" method="post">
			
			<div class="row">
				<h2 class="form-signin-heading">管理员登录</h2>
			</div>
			<div class="row">
				<label for="inputEmail" class="">用户名:</label>
				<input type="text" id="inputEmail" class="form-control form-focus input-lg"
				 autofocus name="username" datatype="*"> 
			</div>
			
			<div class="row">
				<label for="inputPassword">密码:</label> 
				<input type="password" id="inputPassword" class="form-control input-lg"
					 name="password" datatype="*">
			</div>
			<!-- <div class="row"> 
				<label >
					<input type="checkbox" name="rememberMe" >
				记住我</label>
			</div> -->
			<div class="row">
				<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
				
			</div>
		</form>

	</div>
	<script type="text/javascript">
		//验证表单
		$("form").Validform({
			tiptype: function(msg,o,cssctl){
				o.obj.popover({
					content: msg,
					trigger: 'manual'
					});
				if(o.type == 3){
					o.obj.popover('show');
				} else {
					o.obj.popover('hide');
				}
			},
			ajaxPost: true,
			callback: function(data){
				if(data.statusCode==0){
					window.location.href="${context_}/main/index.do";
				} else {
					$.zui.messager.show(data.status);
				}
				
			}

		});
	
		
	</script>
</body>

</html>
