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
		<meta charset="utf-8" />
		<base href="<%=basePath%>">

		<!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame
		Remove this if you use the .htaccess -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>ExamStack</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/exam.css" rel="stylesheet" type="text/css">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<style type="text/css">
			.question-body {
				padding: 30px 30px 20px 30px;
				background: #FFF;
			}
			
			ul#exampaper-body{
				margin-bottom: 0px;
			}
			
			ul#exampaper-body li{
				padding-bottom:0px;
			}
			.question-body{
				min-height:300px;
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
						<li>
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

		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div class="row">
					<div class="col-xs-3" style="padding-right: 0px;padding-bottom:15px;">
						<div class="def-bk" id="bk-exam-control">

							<div class="def-bk-content" id="exam-control">

								<div>

									<span style="color:#428bca;">知识类型：</span>
									<div>
										<span>${fieldName }</span>
										<span id="knowledgePointId" style="display:none;">${knowledgePointId }</span>
										<span id="questionTypeId" style="display:none;">${questionTypeId }</span>
										<span id="fieldId" style="display:none;">${fieldId }</span>
									</div>
									<span style="color:#428bca;">题型库：</span>
									<div>
										<span id="practice-type" class="pt-singlechoice">${questionTypeName }[ 共 <span class="pt-tno">${amount }</span> 题 ]<span class="pt-qcode" style="display:none;">qt-singlechoice</span></span>
									</div>
									<span style="color:#428bca;">学习进度：</span>
										<div class="h-progress" style="margin-top:5px;" title="50%">
											<span></span>
										</div>
									<span id="exam-timestamp" style="display:none;">0</span>
									<div id="answer-save-info"></div>

								</div>
								<div id="question-submit">
									<button class="btn-success btn" style="width:100%;" id="switch-model-btn" data-exam="true"> 
										答题模式
									</button>
								</div>
								<div id="exam-info" style="display:none;">
									<span id="answer-hashcode"></span>
								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-9" style="padding-right: 0px;min-height:800px;">
						<div class="def-bk" id="bk-exampaper">
							<div class="expand-bk-content" id="bk-conent-exampaper">
								<div id="exampaper-header">
									<div id="exampaper-title"  style="margin-bottom:15px;">
										<i class="fa fa-paper-plane"></i>
										<span id="exampaper-title-name"> ${fieldName } - ${practiceName } </span>

									</div>
									<!-- <div id="exampaper-desc-container">
									<div id="exampaper-desc" class="exampaper-filter">

									</div>
									</div> -->

								</div>
								<ul id="exampaper-body">
									${questionStr }
								</ul>
								<div id="exampaper-footer">
										
									
									<div id="question-switch">
										<button class="btn-success btn" id="previous-q-btn" style="width:160px;">
												<i class="fa fa-chevron-circle-left"></i>上一题

										</button>
										<button class="btn-success btn" id="next-q-btn" style="margin-left:60px;width:160px;">
												下一题 <i class="fa fa-chevron-circle-right"></i>
										</button>
										<button class="btn-warning btn" id="submit-q-btn" style="width:160px;float:right;">
												<i class="fa fa-check-circle-o"></i>提交答案
										</button>
									</div>
									<div id="question-navi">
										<div id="question-navi-controller">
											<i class="fa fa-arrow-circle-down"></i>
											<span>答题卡</span>
										</div>
										<div id="question-navi-content" ></div>
									</div>
								</div>
							</div>
							<div class="expand-bk-content" id="bk-conent-comment" style="margin-top:40px;">
								<div id="comment-title" style="margin-bottom:15px;">
									<i class="fa fa-comments"></i>
									<span> 学员评论 </span>

								</div>
								<form class="comment-form">
									<textarea rows="" cols="" style="width:100%;height:95px;" placeholder="随便说点什么吧..."></textarea>
									<input class="btn btn-primary" type="submit" value="发表评论">
								</form>
								<div class="comment-total"><span class="comment-total-num">18</span>条评论</div>
								<ul class="comment-list">
									<!-- <li class="comment-list-item">
										<div class="comment-user-container">
											<div >
												<img src="resources/images/photo.jpg" class="comment-user-img">
											</div>
											<div class="comment-user-info">
												<div>
													yanhuan [电能计量]
												</div>
												<div class="comment-date">
													发表于 1天前
												</div>
											</div>
										</div>
										<p class="comment-user-text">
											应该选B不是吗？
										</p>
										
									</li>
									<li class="comment-list-item">
										<div class="comment-user-container">
											<div >
												<img src="resources/images/photo.jpg" class="comment-user-img">
											</div>
											<div class="comment-user-info">
												<div>
													yanhuan [电能计量]
												</div>
												<div class="comment-date">
													发表于 1天前
												</div>
											</div>
										</div>
										<p class="comment-user-text">
											应该选B不是吗？
										</p>
										
									</li> -->
								</ul>
								<div id="show-more-div">
									<input type="hidden" id="idx-hidden" value="1">
									<input type="hidden" id="last-floor-hidden" value="0">
									<button id="show-more-btn">更多评论</button>
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
		<script type="text/javascript" src="resources/js/all.js?v=0712"></script>
		<script type="text/javascript" src="resources/js/practice-improve-qh.js"></script>
		<script type="text/javascript" src="resources/js/comment.js"></script>

	</body>
</html>
