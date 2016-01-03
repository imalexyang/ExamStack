<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<title>ExamStack</title>
		
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
							<li class="active">
								<a href="student/finish-exam/${examId }"> <i class="fa fa-bar-chart-o"></i> 分析报告 </a>

							</li>
							<li>
								<a href="student/student-answer-sheet/${examId }"> <i class="fa fa-file-text"></i> 详细解答 </a>
							</li>
						</ul>

					</div>
					<div class="col-xs-10">
						<div class="page-header">
							<h1><i class="fa fa-bar-chart-o"></i> 分析报告 </h1>
						</div>
						<div class="page-content row">

							<div id="graph-base" class="col-xs-8" style="height:200px;width: 200px;">

							</div>
							<div  class="col-xs-7" style="margin-top: 24px;">
								
								<div class="form-line add-role">
									<span class="form-label">考试名称：</span>
									<span> 练习考试 </span>
								</div>
								<div class="form-line add-role">
									<span class="form-label">交卷时间：</span>
									<span >
										<fmt:formatDate value="${create_time }" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/>
									</span>
								</div>
								<div class="form-line add-role">
									<span class="form-label">总题目：</span>
									<span class="label label-info"> ${total } </span>
								</div>
								<div class="form-line exam-report-correct">
									<span class="form-label">正确题目：</span>
									<span class="label label-success"> ${right } </span>
								</div>
								<div class="form-line exam-report-error">
									<span class="form-label">错误题目：</span>
									<span class="label label-danger"> ${wrong } </span>
								</div>

							</div>

						</div>
						<div class="page-content row">
							<table class="table table-bordered" style="margin-top: 25px;">
								<thead>
									<tr>
										<th>考点</th>
										<th>答题情况</th>
										<th>正确率</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${reportResultMap }" var="result">
										<tr>
											<td>${result.value.pointName }</td>
											<td>${result.value.rightAmount } / ${result.value.amount }</td>
											<td>
												<fmt:formatNumber value="${result.value.rightAmount/result.value.amount }" type="number" pattern="0.00%"/>
											</td>
										</tr>
									</c:forEach>
									
								</tbody>
							</table>
						</div>
						<div class="page-content row">
							<div id="question-navi" style="margin: 24px 0;width: 100%;">

								<div id="question-navi-content" style="padding: 15px 12px;">
									<c:forEach items="${idList }" var="item" varStatus="s">
										<c:forEach items="${answer }" var="answerItem">
											<c:if test="${item == answerItem.key }">
												<c:if test="${answerItem.value }">
													<a class="question-navi-item">${s.index + 1 }</a>
												</c:if>
												<c:if test="${!answerItem.value }">
													<a class="question-navi-item navi-item-error">${s.index + 1 }</a>
												</c:if>
											</c:if>
										</c:forEach>
										
									</c:forEach>
									
								</div>
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
