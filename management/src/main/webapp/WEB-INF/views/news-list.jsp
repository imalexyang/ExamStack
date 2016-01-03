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
		<title>用户管理</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style type="text/css">
			.disable-btn, .enable-btn{
				text-decoration: underline;
			}
			.disable-btn, .enable-btn{
				cursor:pointer;
			}
		</style>
	</head>
	<body>
		<header>
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
							<h1><i class="fa fa-bar-chart-o"></i> 管理员列表 </h1>
						</div>
						<div class="page-content">
							<div id="news-list">
								<div class="table-controller">
									<div class="btn-group table-controller-item" style="float:left">
										<button class="btn btn-default btn-sm" id="add-news-modal-btn">
											<i class="fa fa-plus-square"></i> 发布公告
										</button>
									</div>
								</div>
								<table class="table-striped table">
									<thead>
										<tr>
											<td>ID</td>
											<td>标题</td>
											<td>创建时间</td>
											<td>创建人</td>
											
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${newsList }" var="item">
											<tr>
												<td><span class="r-id">${item.newsId }</span></td>
												<td><span class="r-name">${item.title }</span></td>
												<td>
													<span class="r-createtime">
														<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm"/>
													</span>
												</td>
												<td>
												<div class="r-truename">
													${item.creator }
												</div>
																								
													
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
			<div class="modal fade" id="add-news-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								&times;
							</button>
							<h6 class="modal-title" id="myModalLabel">发布公告</h6>
						</div>
						<div class="modal-body">
							<form id="teacher-add-form" style="margin-top:40px;"  action="admin/add-news">
								<div class="form-line form-news-title" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>公告标题：</span>
									<input type="text" class="df-input-narrow" id="name-add" maxlength="50">
									<span class="form-message"></span>
									<br>
								</div>
								<div class="form-line form-news-content" style="display: block;">
									<span class="form-label"><span class="warning-label">*</span>公告内容：</span>
									<textarea rows="15" cols="50" id="news-content"></textarea>
									<span class="form-message"></span>
									<br>
								</div>
								
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								关闭窗口
							</button>
							<button id="add-news-btn" data-action="admin/add-news" data-group="0" type="button" class="btn btn-primary">
								确定添加
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
		<script type="text/javascript" src="resources/js/all.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/news-list.js"></script>
		<script type="text/javascript" src="resources/js/add-news.js"></script>
		<script>
			$(function() {
				$("#add-news-modal-btn").click(function() {
					$("#add-news-modal").modal({
						backdrop : true,
						keyboard : true
					});

				});
			});
		</script>
	</body>
</html>