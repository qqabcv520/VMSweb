<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String context = request.getContextPath();
	pageContext.setAttribute("context_", context);
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/zui/css/zui.min.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/jquery-1.12.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/zui/js/zui.js"></script>



<!-- Latest compiled and minified JavaScript -->
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/bootstrap-table.min.js"></script>
<!-- Latest compiled and minified Locales -->
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.10.1/locale/bootstrap-table-zh-CN.min.js"></script>


<script type="text/javascript">
		var context_ = '${context_}';
		var templateUrl = '${moduleName}';
</script>
