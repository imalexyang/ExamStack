<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- <%@taglib uri="spring.tld" prefix="spring"%> --%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String servletPath = (String)request.getAttribute("javax.servlet.forward.servlet_path");
String[] list = servletPath.split("\\/");
request.setAttribute("role",list[1]);
request.setAttribute("topMenuId",list[2]);
request.setAttribute("leftMenuId",list[3]);
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>用户管理</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon"
		href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css"
		rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">

		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style type="text/css">
			.disable-btn, .enable-btn {
				text-decoration: underline;
			}

			.disable-btn, .enable-btn {
				cursor: pointer;
			}
		</style>
	</head>
	<body>
		<table class=" table">
										<!-- <h4>
											对应章节
										</h4> -->
										<thead>
											<tr>
												<td>章节内容</td>
											</tr>
										</thead>
										<!-- <thead>
											<tr>
												<td></td>
												<td style="display:none;">ID</td>
												<td>培训名</td>
												<td>创建时间</td>
												<td>创建人</td>
												<td>操作</td>
											</tr>
										</thead> -->
										<tbody>
											<c:forEach items="${sectionList }" var="item">
												<tr>
													<td><a class="training-sections" data-id="${item.sectionId }" >${item.sectionName }</a></td>
													
													<td>
														<a class="delete-paper btn-sm btn-danger">删除</a>
													</td>
												</tr>
											</c:forEach>
																																		
											
										</tbody>
										<tfoot>
											<!-- <tr>
												<td colspan="2">
												<button class="btn btn-sm btn-info add-section-btn">新增章节</button>
												</td>
											</tr> -->
										</tfoot>
									</table>
	</body>
	
</html>