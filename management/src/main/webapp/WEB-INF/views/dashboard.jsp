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
request.setAttribute("leftMenuId","");
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>DashBoard</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		<link href="resources/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
		<link href='resources/fullcalendar-2.1.1/fullcalendar.css' rel='stylesheet' />
		<link href='resources/fullcalendar-2.1.1/fullcalendar.print.css' rel='stylesheet' media='print' />
	
		
		<link href="resources/css/question-add.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style>
			input.add-ques-amount,input.add-ques-score{
				width:50px;
				margin-right:0;
			}
			.bs-example-bg-classes p {
			    padding: 15px;
			}
		</style>
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
						<ul class="nav default-sidenav">
							<li>
								<a href="admin/question/question-add" title="添加试题"><i class="fa fa-plus">&nbsp;</i><span class="left-menu-item-name"> 添加试题</span></a>
							</li>
							<li>
								<a href="admin/exampaper/exampaper-add" title="创建新试卷"><i class="fa fa-leaf">&nbsp;</i><span class="left-menu-item-name"> 创建新试卷</span></a>
							<li>
								<a href="admin/exam/exam-add" title="发布考试"><i class="fa fa-plus-square-o">&nbsp;</i><span class="left-menu-item-name"> 发布考试</span></a>
							</li>
							<li>
								<a href="admin/exam/model-test-add" title="发布模拟考试"><i class="fa fa-flag">&nbsp;</i><span class="left-menu-item-name"> 发布模拟考试</span></a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1><i class="fa fa-dashboard"></i> DashBoard </h1>
						</div>
						<div class="page-content">
   							<div class="bs-example bs-example-bg-classes" data-example-id="contextual-backgrounds-helpers">
							    <p class="bg-success">
							    	<i class="ace-icon fa fa-check green"></i>
							    
							   		欢迎使用ExamStack后台管理系统，您可以在此页面快速查看您系统的状态。
							    </p>
							    
							    <div class="row" style="margin-top:20px;">
							    	<div class="col-xs-7">
							    		<div style="height:100px;">
							    			<div class="infobox infobox-green infobox-small">
											<div class="infobox-progress">
												<i class="fa fa-cloud"></i>
											</div>
											<div class="infobox-data">
												<div class="infobox-content">试题</div>
												<div class="infobox-content" id="question-num">-</div>
											</div>
											</div>
											<div class="infobox infobox-dark infobox-small">
												<div class="infobox-progress">
													<i class="fa fa-file-text-o"></i>
												</div>
												<div class="infobox-data">
													<div class="infobox-content">试卷</div>
													<div class="infobox-content" id="questionpaper-num">-</div>
												</div>
											</div>
											<div class="infobox infobox-blue infobox-small">
												<div class="infobox-progress">
													<i class="fa fa-user"></i>
												</div>
												<div class="infobox-data">
													<div class="infobox-content">学员</div>
													<div class="infobox-content" id="student-num">-</div>
												</div>
											</div>
							    		</div>
										
										
										
										<div class="widget-box transparent">
							    			<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-star orange"></i>
													需要审核
												</h4>
												
												<div class="widget-toolbar no-border">
													<a href="<%=list[1]%>/exam/exam-list" target="_blank">查看更多</a>
												</div>
											</div>
							    			<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table-striped table" id="studentApprovedTable">
									<thead>
										<tr>
											<td>学员ID</td>
											<td>姓名</td>
											<td>单位</td>
										
											<td>
												操作
											</td>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
									<tfoot>
	
									</tfoot>
								</table>
													
													
																									
												</div>
											</div>
							    		</div>
										<div class="widget-box transparent">
							    			<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-rss orange"></i>
													需要阅卷
												</h4>
												
												<div class="widget-toolbar no-border">
													<a href="<%=list[1]%>/exam/exam-list" target="_blank">查看更多</a>
												</div>
											</div>
							    			<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table-striped table" id="studentMarkTable">
									<thead>
										<tr>
											<td>学员ID</td>
											<td>姓名</td>
											<td>单位</td>
											<td>交卷时间</td>
											<td>
												操作
											</td>
										</tr>
									</thead>
									<tbody>
										
											
											
									</tbody>
									<tfoot>
	
									</tfoot>
								</table>
		 											
													
																									
												</div>
											</div>
							    		</div>
																			    		
							    	</div>
							    	<div class="col-xs-5">
							    		<div class="widget-box">
							    			<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-signal orange"></i>
													Popular Domains
												</h4>
												
												<div class="widget-toolbar no-border">
													<select id="field-select">
														<c:forEach items="${fieldList }" var="item">
															<option value="${item.fieldId}">${item.fieldName}</option>
														</c:forEach>
													</select>
												</div>
											</div>
							    			<div class="widget-body">
												<div class="widget-main no-padding">
													
													
													<div id="mychart" style="height:400px;"></div>
													
																									
												</div>
											</div>
							    		</div>
							    		
							    		<div id="calendar" style="margin-top:50px;">
							    		
							    		</div>
							    	</div>
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
		<script type="text/javascript" src="resources/js/all.js"></script>
		
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
		<script type="text/javascript" src="resources/js/echarts-all.js"></script>
		<script type="text/javascript" src="resources/fullcalendar-2.1.1/lib/moment.min.js"></script>
		<script type="text/javascript" src="resources/fullcalendar-2.1.1/fullcalendar.min.js"></script>
		<script type="text/javascript" src="resources/fullcalendar-2.1.1/lang-all.js"></script>
		<script type="text/javascript" src="resources/js/dashboard.js"></script>
	</body>
</html>