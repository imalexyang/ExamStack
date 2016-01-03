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
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>题库管理</title>
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
							<h1><i class="fa fa-bar-chart-o"></i> 教师管理 </h1>
						</div>
						<div class="page-content">
							<div id="teacher-list">
								<div class="table-controller">
									<div class="btn-group table-controller-item" style="float:left">
										<button class="btn btn-default btn-sm" id="add-teacher-modal-btn">
											<i class="fa fa-plus-square"></i> 创建教师账号
										</button>
									</div>
								</div>
								<table class="table-striped table">
									<thead>
										<tr>
											<td>ID</td>
											<td>用户名</td>
											<td>姓名和身份证</td>
											<td>手机邮箱</td>
											<td>创建时间</td>
											<td>状态</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${userList }" var="item">
											<tr>
												<td><span class="r-id">${item.userId }</span></td>
												<td><span class="r-name">${item.userName }</span></td>
												<td>
												<div class="r-truename">
													${item.trueName }
												</div>
												<div class="r-national-id">
													${item.nationalId }
												</div></td>
												<td>
												<div class="r-phone">
													${item.phoneNum }
												</div>
												<div class="r-email">
													${item.email }
												</div></td>
												<td style="display:none;">
												<div class="r-company" style="display:none;">
													${item.company }
												</div>
												<div class="r-dept" style="display:none;">
													${item.department }
												</div></td>
												<td><span class="r-createtime">
													<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm"/>
												</span></td>
												<td>
												<c:choose>
													<c:when test="${item.enabled }">
														<span>启用</span>
													</c:when>
													<c:otherwise>
														<span>禁用</span>
													</c:otherwise>
												</c:choose></td>
												<td><span class="r-update-btn btn-sm btn-success" data-id="21321" data-depid="${item.depId }">修改</span><span class="disable-btn btn-sm btn-danger" data-id="${item.userId }" data-status="${!item.enabled }">
													<c:choose>
														<c:when test="${!item.enabled }">
															启用
														</c:when>
														<c:otherwise>
															禁用
														</c:otherwise>
													</c:choose> </span>
													<span class="r-reset-pwd-btn btn-sm btn-warning" data-id="21321" data-depid="6">密码</span>
													
												
												</td>
													
													
											</tr>

										</c:forEach>

									</tbody><tfoot></tfoot>
								</table>
							</div>
							<div id="page-link-content">
								<ul class="pagination pagination-sm">
									${pageStr}
								</ul>
							</div>

						</div>
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
			<div class="modal fade" id="add-teacher-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								&times;
							</button>
							<h6 class="modal-title" id="myModalLabel">创建教师账号</h6>
						</div>
						<div class="modal-body">
							<form id="teacher-add-form" style="margin-top:40px;"  action="admin/add-user-ROLE_TEACHER-0">
								<div class="form-line form-username" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>用户名：</span>
									<input type="text" class="df-input-narrow" id="name-add" maxlength="20">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-password" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>密码：</span>
									<input type="text" class="df-input-narrow" id="password-add" maxlength="20">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-truename" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>真实姓名：</span>
									<input type="text" class="df-input-narrow" id="truename-add" maxlength="20">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-national-id" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>身份证号：</span>
									<input type="text" class="df-input-narrow" id="national-id-add" maxlength="18">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-phone" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>手机：</span>
									<input type="text" class="df-input-narrow" id="phone-add" maxlength="18">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-email" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>邮箱：</span>
									<input type="text" class="df-input-narrow" id="email-add" maxlength="90">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-company" style="display: none;">
									<span class="form-label"><span class="warning-label"></span>单位：</span>
									<input type="text" class="df-input-narrow" id="company-add" maxlength="30">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-department" style="display: none;">
									<span class="form-label"><span class="warning-label"></span>部门单位：</span>
									<select id="department-input-select" class="df-input-narrow">
										<option value="-1">--请选择--</option>
										<c:forEach items="${depList }" var="item">
											<option value="${item.depId }">${item.depName }</option>
										</c:forEach>
									</select>
									<span class="form-message"></span>
									<br>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								关闭窗口
							</button>
							<button id="add-user-btn" data-action="admin/add-user-ROLE_TEACHER" data-url="admin/user/teacher-list" data-group="0" type="button" class="btn btn-primary">
								确定添加
							</button>
						</div>
					</div>
				</div>
			</div>
			<div class="modal fade" id="reset-pwd-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
												&times;
											</button>
											<h6 class="modal-title" >重置密码</h6>
										</div>
										<div class="modal-body">
											<form id="reset-pwd-form" style="margin-top:40px;" action="secure/reset-pwd-user">
												<div class="form-line form-user-name-reset">
													<span class="form-label"><span class="warning-label">*</span>用户名：</span>
													<input type="text" class="df-input-narrow" id="username-reset" maxlength="20" disabled="disabled">
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-user-pwd-reset">
													<span class="form-label"><span class="warning-label">*</span>新密码：</span>
													<input type="text" class="df-input-narrow" id="pwd-reset" maxlength="20">
													<span class="form-message"></span>
													<br>
												</div>
											</form>
											
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">
												关闭窗口
											</button>
											<button id="reset-pwd-btn" type="button" class="btn btn-primary">
												确定修改
											</button>
										</div>
										
									</div>
								</div>
			</div>	
			<div class="modal fade" id="update-teacher-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								&times;
							</button>
							<h6 class="modal-title" id="myModalLabel">修改教师账号</h6>
						</div>
						<div class="modal-body">
							<form id="teacher-update-form" style="margin-top:40px;"  action="admin/update-teacher">
								<div class="form-line form-user-id" style="display: none;">
									<span class="form-label"><span class="warning-label">*</span>用户名：</span>
									<input type="text" class="df-input-narrow" id="id-update">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-username-u" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>用户名：</span>
									<input type="text" class="df-input-narrow" id="name-update" disabled="disabled">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-password-u" style="display: none;">
									<span class="form-label"><span class="warning-label">*</span>密码：</span>
									<input type="text" class="df-input-narrow" id="password-update" maxlength="20">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-truename-u" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>真实姓名：</span>
									<input type="text" class="df-input-narrow" id="truename-update" maxlength="20">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-national-id-u" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>身份证号：</span>
									<input type="text" class="df-input-narrow" id="national-id-update" maxlength="18">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-phone-u" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>手机：</span>
									<input type="text" class="df-input-narrow" id="phone-update" maxlength="18">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-email-u" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>邮箱：</span>
									<input type="text" class="df-input-narrow" id="email-update" maxlength="90">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-company-u" style="display: none;">
									<span class="form-label"><span class="warning-label"></span>单位：</span>
									<input type="text" class="df-input-narrow" id="company-update" maxlength=30>
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-department-u" style="display: none;">
									<span class="form-label"><span class="warning-label"></span>部门单位：</span>
									<select id="department-input-select-u" class="df-input-narrow">
										<option value="-1">--请选择--</option>
										<c:forEach items="${depList }" var="item">
											<option value="${item.depId }">${item.depName }</option>
										</c:forEach>
									</select>
									<span class="form-message"></span>
									<br>
								</div>
							</form>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								关闭窗口
							</button>
							<button id="update-teacher-btn" type="button" class="btn btn-primary">
								确定修改
							</button>
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
		<script type="text/javascript" src="resources/js/teacher-list.js"></script>
		<script type="text/javascript" src="resources/js/add-teacher.js"></script>
		<script type="text/javascript" src="resources/js/update-teacher.js"></script>
		<script>
			$(function() {
				$("#add-teacher-modal-btn").click(function() {
					$("#add-teacher-modal").modal({
						backdrop : true,
						keyboard : true
					});

				});
			});
			
			$(".r-reset-pwd-btn").click(function() {
				$("#reset-pwd-modal").modal({
					backdrop : true,
					keyboard : true
				});
				$("#reset-pwd-form #username-reset").val($(this).parent().parent().find(".r-name").text().trim());
			});


		</script>
	</body>
</html>