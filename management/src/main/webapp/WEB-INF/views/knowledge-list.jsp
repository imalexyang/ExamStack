<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>知识分类管理</title>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
	</head>
	<body>
		<header>
			<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
			<div class="container">
				<div class="row">
					<div class="col-xs-5">
						<div class="logo">
							<h1><a href="#">网站管理系统</a></h1>
							<div class="hmeta">
								专注互联网在线考试解决方案
							</div>
						</div>
					</div>
					<div class="col-xs-7" id="login-info">
						<c:choose>
							<c:when test="${not empty sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}">
								<div id="login-info-user">
									
									<a href="user-detail/${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}" id="system-info-account" target="_blank">${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}</a>
									<span>|</span>
									<a href="j_spring_security_logout"><i class="fa fa-sign-out"></i> 退出</a>
								</div>
							</c:when>
							<c:otherwise>
								<a class="btn btn-primary" href="user-register">用户注册</a>
								<a class="btn btn-success" href="user-login-page">登录</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</header>
		<!-- Navigation bar starts -->

		<div class="navbar bs-docs-nav" role="banner">
			<div class="container">
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
					<c:import url="/common-page/top-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}" charEncoding="UTF-8" />
				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-2" id="left-menu">
						<c:import url="/common-page/left-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}" charEncoding="UTF-8" />
					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1> <i class="fa fa-bar-chart-o"></i> 知识分类管理 </h1>
						</div>
						<div class="page-content">
							<div id="question-filter">
								<dl id="question-filter-field">
									<dt>
										题库：
									</dt>
									<dd>	
										<c:choose>
											<c:when test="${fieldId == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										
										
										<c:forEach items="${fieldList }" var="item">
										
											<c:choose>
												<c:when test="${fieldId == item.fieldId }">
													<span data-id="${item.fieldId}" class="label label-info">${item.fieldName}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${item.fieldId }">${item.fieldName }</span>
												</c:otherwise>
											</c:choose>
											
										</c:forEach>
									</dd>
								</dl>
								<%-- <div class="page-link-content">
									<ul class="pagination pagination-sm">${pageStr}</ul>
								</div> --%>
							</div>
							
							
							<div id="field-list">
								<div class="table-controller">
									<div class="btn-group table-controller-item" style="float:left">
										<button class="btn btn-default btn-sm" id="add-knowledge-modal-btn">
											<i class="fa fa-plus-square"></i> 添加知识分类
										</button>
									</div>
								</div>
								<table class="table-striped table">
									<thead>
										<tr>
											<td>ID</td>
											<td>知识分类名</td>
											<td>属于题库</td>
											<td>描述</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pointList }" var="item">
											<tr>
												
												<td>${item.pointId }</td>
												<td>${item.pointName }</td>
												<td>${item.fieldName }</td>
												<td>${item.memo }</td>
												<td>
													<c:choose>
														<c:when test="${item.removeable }">
															<span class="delete-btn btn-sm btn-danger" data-id="${item.pointId }"><i class="ace-icon fa fa-trash-o"></i></span>
														</c:when>
														<c:otherwise>
															<span class="btn-sm" data-id="${item.pointId }" disabled="disabled" title="无法删除"><i class="ace-icon fa fa-ban"></i></span>
														</c:otherwise>
													</c:choose>	
												</td>
											</tr>
										</c:forEach>
										
									</tbody><tfoot></tfoot>
								</table>
							</div>
							<div id="page-link-content">
								<ul class="pagination pagination-sm">${pageStr}</ul>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
							<div class="modal fade" id="add-knowledge-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
												&times;
											</button>
											<h6 class="modal-title" id="myModalLabel">添加知识分类</h6>
										</div>
										<div class="modal-body">
											<form id="knowledge-add-form" style="margin-top:40px;"  action="admin/common/point-add">
												<div class="form-line form-field" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>默认题库：</span>
													<select id="job-type-input-select" class="df-input-narrow">
														<!-- <option value="-1">--请选择--</option> -->
														<c:forEach items="${fieldList }" var="item">
															<option value="${item.fieldId }">${item.fieldName }</option>
														</c:forEach>
													</select>
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-knowledge-name" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>名称：</span>
													<input type="text" class="df-input-narrow" id="name">
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-knowledge-desc" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>描述：</span>
													<input type="text" class="df-input-narrow" id="password">
													<span class="form-message"></span>
													<br>
												</div>
												
											</form>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">
												关闭窗口
											</button>
											<button id="add-knowledge-btn" type="button" class="btn btn-primary">
												确定添加
											</button>
										</div>
									</div>
								</div>
							</div>		
		<footer>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="copy">
							<p>
								ExamStack Copyright © <a href="http://www.examstack.com/" target="_blank">ExamStack</a> - <a href="." target="_blank">主页</a> | <a href="http://www.examstack.com/" target="_blank">关于我们</a> | <a href="http://www.examstack.com/" target="_blank">FAQ</a> | <a href="http://www.examstack.com/" target="_blank">联系我们</a>
							</p>
						</div>
					</div>
				</div>

			</div>

		</footer>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript" src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/point-list.js"></script>
		<script type="text/javascript" src="resources/js/add-point.js"></script>
		<script>
			$(function() {
				$("#add-knowledge-modal-btn").click(function() {
					$("#add-knowledge-modal").modal({
						backdrop : true,
						keyboard : true
					});
					
					$("#job-type-input-select").val($("#question-filter-field .label-info").data("id"));
					
					
	
				});
			});
		</script>
		
	</body>
</html>