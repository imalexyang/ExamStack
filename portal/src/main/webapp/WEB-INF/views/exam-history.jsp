<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- <%@taglib uri="spring.tld" prefix="spring"%> --%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    	<base href="<%=basePath%>">
    
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>练习历史</title>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style>
			a.btn{
				    margin-top: 0px; 
			}
		</style>
	</head>
	<body>
		<header>
			<div class="container">
				<div class="row">
					<div class="col-xs-5">
						<div class="logo">
							<h1><a href="#"><img alt="" src="resources/images/logo.png"></a></h1>
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
					<ul class="nav navbar-nav">
						<li>
							<a href="home"><i class="fa fa-home"></i>主页</a>
						</li>
						<li>
							<a href="student/practice-list"><i class="fa fa-edit"></i>试题练习</a>
						</li>
						<li>
							<a href="exam-list"><i class="fa  fa-paper-plane-o"></i>在线考试</a>
						</li>
						<li>
							<a href="training-list"><i class="fa fa-book"></i>培训资料</a>
						</li>
						<li class="active">
							<a href="student/usercenter"><i class="fa fa-dashboard"></i>会员中心</a>
						</li>
						<li>
							<a href="student/setting"><i class="fa fa-cogs"></i>个人设置</a>
						</li>
					</ul>
				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-2">
						<ul class="nav default-sidenav">
							<li>
								<a href="student/usercenter"> <i class="fa fa-dashboard"></i> 用户中心 </a>
							</li>
							<li>
								<a href="student/analysis"> <i class="fa fa-bar-chart-o"></i> 统计分析 </a>
							</li>
							<li class="active">
								<a> <i class="fa fa-history"></i> 考试历史 </a>
							</li>
							<li>
								<a href="student/training-history"> <i class="fa fa-book"></i> 培训历史 </a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10">
						<div class="page-header">
							<h1><i class="fa fa-history"></i> 考试历史</h1>
						</div>
						<div class="page-content row">
							<div id="question-list">
								<table class="table-striped table">
									<thead>
										<tr>
											<td>试卷名称</td><td>状态</td><td>类型</td><td>交卷时间</td><td>及格分</td><td>得分</td><td>操作</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${hisList }" var="item">
											<tr>
											
												<td>
													${item.examName }
												</td>
												<td>
													<c:choose>
														<c:when test="${item.approved == 0 }"><span class="label-warning label-sm label">未审核</span></c:when>
														<c:when test="${item.approved == 1 }"><span class="label-default label-sm label">已审核</span></c:when>
														<c:when test="${item.approved == 2 }"><span class="label-info label-sm label">已交卷</span></c:when>
														<c:when test="${item.approved == 3 }"><span class="label-success label-sm label">已阅卷</span></c:when>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${item.examType == 1 }">私有考试</c:when>
														<c:when test="${item.examType == 2 }">公有考试</c:when>
														<c:when test="${item.examType == 3 }">模拟考试</c:when>
													</c:choose>
												</td>
												<td>
													<fmt:formatDate value="${item.submitTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td>
													${item.passPoint }
												</td>
												<td>
													<c:choose>
														<c:when test="${item.pointGet lt item.passPoint }">
															<font color="red">${item.pointGet }</font>
														</c:when>
														<c:otherwise>
															<font color="green">${item.pointGet }</font>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${item.approved == 3 or (item.examType == 3 and item.approved != 1)}">
															<a class="btn btn-success btn-sm" href="student/student-answer-sheet/${item.examId }" target="_blank" title="预览">详细解答</a>
														</c:when>
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
		<script type="text/javascript" src="resources/chart/raphael-min.js"></script>
		<script type="text/javascript" src="resources/chart/morris.js"></script>
		<script type="text/javascript" src="resources/js/exam-finished.js"></script>
	</body>
</html>