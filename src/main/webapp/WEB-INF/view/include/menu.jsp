<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/elfunctions.tld" prefix="my"%>  
<c:if test="${my:contains(LOGIN_USER_KEY.permissionsNames, 'user_info') }">
	<li class="menu-item <c:if test="${content=='user-management'}">active</c:if>" >
		<a href="${pageContext.request.contextPath}/main/user/management.do" data-id="user-management"><i class="icon icon-user"></i> 用户管理</a>
	</li>
</c:if>

<li class="menu-item <c:if test="${content=='vehicle-management'}">active</c:if>" >
	<a href="${pageContext.request.contextPath}/main/vehicle/management.do" data-id="vehicle-management"><i class="icon icon-dashboard"></i> 车辆管理</a>
</li>

<c:if test="${my:contains(LOGIN_USER_KEY.permissionsNames, 'driver_info') }">
	<li class="menu-item <c:if test="${content=='driver-managementment'}">active</c:if>" >
		<a href="${pageContext.request.contextPath}/main/driver/management.do" data-id="driver-management"><i class="icon icon-compass"></i> 司机管理</a>
	</li>
</c:if>

<c:if test="${my:contains(LOGIN_USER_KEY.permissionsNames, 'application_info') }">
	<li class="menu-item <c:if test="${content=='application-management'}">active</c:if>" >
		<a href="${pageContext.request.contextPath}/main/application/management.do" data-id="application-management"><i class="icon icon-file-excel"></i> 报表管理</a>
	</li>
</c:if>

<c:if test="${my:contains(LOGIN_USER_KEY.permissionsNames, 'role_info') }">
	<li class="menu-item <c:if test="${content=='role-management'}">active</c:if>" >
		<a href="${pageContext.request.contextPath}/main/role/management.do" data-id="role-management"><i class="icon icon-group"></i> 角色管理</a>
	</li>
</c:if>

