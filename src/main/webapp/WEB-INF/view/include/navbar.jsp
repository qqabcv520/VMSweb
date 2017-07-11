<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/index.do">车辆管理系统</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			
			<ul class="nav navbar-nav visible-xs">
				<jsp:include page="menu.jsp"></jsp:include>
			</ul>
		
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="" data-toggle="modal" data-target="#userInfoModal">
						<c:out value="${loginedUser.username }" escapeXml="false" default="未登录" />
					</a>
					
				</li>
				<li><a href="javascript:if(confirm('是否注销')){location.href='logout.do'} ">注销</a></li>
			</ul>
			

		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="userInfoModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">用户信息</h4>
			</div>
			<div class="modal-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-4 control-label">用户名：</label>
						<div class="col-sm-8">
							<p class="form-control-static">
								<c:out value="${loginedUser.username}" escapeXml="false" default="未登录"/>
							</p>
							
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">系统权限：</label>
						<div class="col-sm-8">
							<p class="form-control-static">
								<c:choose>
								    <c:when test="${loginedUser.type==0}">
								       	超级管理员
								    </c:when>
								    <c:when test="${loginedUser.type==1}">
								       	系统管理员
								    </c:when>
								    <c:when test="${loginedUser.type==2}">
								       	管理员
								    </c:when>
								    <c:otherwise>
								    	未知
								    </c:otherwise>
								</c:choose>
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
