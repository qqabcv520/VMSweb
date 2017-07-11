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


<link rel="stylesheet" type="text/css" href="${context_}/plugin/zui/css/zui.min.css" />
<link rel="stylesheet" type="text/css" href="${context_}/plugin/bootstrap-table/css/bootstrap-table.min.css" />
<link rel="stylesheet" type="text/css" href="${context_}/css/index.css" />

<script type="text/javascript" src="${context_}/plugin/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${context_}/plugin/zui/js/zui.min.js"></script>
<script type="text/javascript" src="${context_}/plugin/bootstrap-table/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${context_}/plugin/bootstrap-table/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${context_}/plugin/validform/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="${context_}/plugin/jquery.iframeUpload.js"></script>

<script type="text/javascript" src="${context_}/js/index.js"></script>

<!--[if lt IE 9]>
<script type="text/javascript" src="${context_}/plugin/respond.js"></script>
<![endif]-->


<script type="text/javascript">
		var context_ = '${context_}';
		var templateUrl = '${moduleName}';
</script>

</head>

<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<!--[if lt IE 8]>
	    	<div class="alert alert-danger">您正在使用 <strong>过时的</strong> 浏览器. 是时候 <a href="http://browsehappy.com/">更换一个更好的浏览器</a> 来提升用户体验.</div>
		<![endif]-->
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand"
					href="${context_}/main/index.do">车辆管理系统</a>
			</div>
			<div class="navbar-collapse collapse" id="navbar">

				<ul class="nav navbar-nav visible-xs">
					<jsp:include page="include/menu.jsp"></jsp:include>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li><a href="javascript:void(0)" data-toggle="modal"
						data-target="#user-info-modal"> <c:out
								value="${LOGIN_USER_KEY.username }" escapeXml="false" default="未登录" />
					</a></li>
					<li><a href="javascript:void(0)" class="logout">注销</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="container-fluid">
			<div class="col-sm-3 col-md-2 hidden-xs" id="menu">
				<ul class="nav nav-secondary nav-stacked">
					<jsp:include page="include/menu.jsp"></jsp:include>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2"
				id="content">
				<div id="${content }" class="contentTab">
					<c:choose>
						<c:when test="${empty content }">
							<h2 class="sub-header text-center">欢迎使用本系统</h2>
						</c:when>
						<c:otherwise>
							<jsp:include page="include/${content }.jsp"></jsp:include>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
	</div>
	
	<!-- UserInfoModal -->
	<div class="modal fade" id="user-info-modal" tabindex="-1" role="dialog"
		aria-labelledby="user-info-modal-label">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="user-info-modal-label">用户信息</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-4 control-label">用户名：</label>
							<div class="col-sm-8">
								<p class="form-control-static">
									<c:out value="${LOGIN_USER_KEY.username }" escapeXml="false" default="未登录"/>
								</p>
								
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">系统权限：</label>
							<div class="col-sm-8">
								<p class="form-control-static">
									<c:out value="${LOGIN_USER_KEY.roleName }" escapeXml="false" default="未知"/>
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
</body>
</html>
