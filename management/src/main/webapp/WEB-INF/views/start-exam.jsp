<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame
		Remove this if you use the .htaccess -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>ExamStack</title>
		<meta name="viewport"
		content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css"
		rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css"
		rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<style>
			.question-number{
				color: #5cb85c;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			
			.question-number-2{
				color: #5bc0de;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			.question-number-3{
				color: #d9534f;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			
			a.join-practice-btn{
				margin:0;
				margin-left:20px;
			}
			
			.content ul.question-list-knowledge{
				padding:8px 20px;
			}
			
			.knowledge-title{
				background-color:#EEE;
				padding:2px 10px;
				margin-bottom:20px;
				cursor:pointer;
				border:1px solid #FFF;
				border-radius:4px;
			}
			
			.knowledge-title-name{
				margin-left:8px;
			}
			
			.point-name{
				width:200px;
				display:inline-block;
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
						<li class="active">
							<a href="start-exam"><i class="fa fa-edit"></i>试题练习</a>
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

		<!-- Slider starts -->

		
		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div>
					<h3>开始测验</h3>
					<p>
						选择您想要参加的测验，考核下自己吧
					</p>
					<div class="row">
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-cloud-upload"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">强化练习</h3>
									<p>
										自主选择具体考点，各个击破
									</p>
									<a class="btn btn-primary" data-toggle="modal" data-target=".levelup-practice-modal"><i class="fa fa-arrow-right"></i>参加练习</a>
									<div class="modal fade levelup-practice-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									  <div class="modal-dialog">
									    <div class="modal-content">
									    	<div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h6 class="modal-title" id="myModalLabel">选择想要练习的知识分类</h6>
										     </div>
										     <div class="modal-body">
										     	<ul>
										     		<c:forEach items="${classifyMap}" var="item">
										     			<li>
										     				<div class="knowledge-title">
										     					<i class="fa fa-chevron-up"> </i><i class="fa fa-chevron-down" style="display:none;"> </i>  <span class="knowledge-title-name">${item.key}</span>
										     				</div>
										     				
										     				<ul class="question-list-knowledge" style="display:none;">
										     					<c:forEach items="${item.value }" var="tp">
										     						<li>${tp.questionTypeName } [共<span class="question-number">${tp.amount } </span>题]
										     							[已做<span class="question-number-2">${tp.rightTimes + tp.wrongTimes } </span> 题]
										     							<a href="student/practice-improve/${tp.questionPointId }/${tp.questionTypeId }" class="btn btn-success btn-sm join-practice-btn">参加练习</a>
										     						</li>
										     					</c:forEach>
										     				</ul>
										     			</li>
										     		</c:forEach>
										     	</ul>
										     </div>
										     <div class="modal-footer">
        										<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
      										 </div>
									    </div>
									  </div>
									</div>
								</div>
								<!--//content-->

							</div>
						</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-eraser"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">错题练习</h3>
									<p>
										收录做过的所有错题
									</p>
									<!-- <a class="btn btn-primary" href="student/practice-incorrect"><i class="fa fa-arrow-right"></i>参加练习</a> -->
									<a class="btn btn-primary" data-toggle="modal" data-target=".incorrect-modal"><i class="fa fa-arrow-right"></i>参加练习</a>
									<div class="modal fade incorrect-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									  <div class="modal-dialog">
									    <div class="modal-content">
									    	<div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h6 class="modal-title" id="myModalLabel">错题练习</h6>
										     </div>
										     <div class="modal-body">
										     	<ul>
										     		<c:forEach items="${wrongKnowledgeMap }" var="item" >
										     			<li><span class="point-name"> ${item.key }</span> [共<span class="question-number-3"><c:forEach items="${item.value }" var="v">${v.value }</c:forEach> </span>题]
							 			     							<a href="student/practice-incorrect/<c:forEach items="${item.value }" var="k">${k.key }</c:forEach>" class="btn btn-success btn-sm join-practice-btn">参加练习</a>
										     			</li>
										     		</c:forEach>
										     		
										     	</ul>
										     </div>
										     <div class="modal-footer">
        										<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
      										 </div>
									    	
									    </div>
									  </div>
									</div>
								</div>
								<!--//content-->

							</div>
						</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-superscript"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">随机练习</h3>
									<p>

										根据你对考点的掌握程度智能出题，提升综合能力
									</p>
									<a class="btn btn-primary" href="student/practice-test"><i class="fa fa-arrow-right"></i>参加练习</a>
								</div>
								<!--//content-->

							</div>
						</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-file-text"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">模拟考试</h3>
									<p>
										根据最新大纲的考查要求为你自动生成的模拟卷
									</p>
									<a class="btn btn-primary" data-toggle="modal" data-target=".practice-exampaper-modal"><i class="fa fa-arrow-right"></i>参加测验</a>
									<div class="modal fade practice-exampaper-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									  <div class="modal-dialog">
									    <div class="modal-content">
									    	<div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h6 class="modal-title" id="myModalLabel">选择试卷，参加考试</h6>
										     </div>
										     <div class="modal-body">
										     	<ul>
										     		<c:forEach items="${practicepaper}" var="item">
										     			<li>
										     				<a href="student/examing/${item.id}">${item.name}</a>
										     			</li>
										     		</c:forEach>
										     	</ul>
										     </div>
										     <div class="modal-footer">
        										<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
      										 </div>
									    	
									    </div>
									  </div>
									</div>
								</div>
								<!--//content-->
							</div>
						</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-book"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">随机组卷</h3>
									<p>
										随机组成试卷参加考试
									</p>
									<a class="btn btn-primary" data-toggle="modal" data-target=".history-exampaper-modal" disabled="disabled"><i class="fa fa-arrow-right"></i>即将开放</a>
									<div class="modal fade history-exampaper-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									  <div class="modal-dialog">
									    <div class="modal-content">
									    	<div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h6 class="modal-title" id="myModalLabel">选择试卷，参加考试</h6>
										     </div>
										     <div class="modal-body">
										     	<ul>
										     		<c:forEach items="${historypaper}" var="item">
										     			<li>
										     				<a href="student/examing/${item.id}">${item.name}</a>
										     			</li>
										     		</c:forEach>
										     	</ul>
										     </div>
										     <div class="modal-footer">
        										<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
      										 </div>
									    	
									    </div>
									  </div>
									</div>
								</div>
								<!--//content-->

							</div>
						</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-rocket"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">专家试卷</h3>
									<p>
										专家组卷，为你提供更权威的考题动向
									</p>
									<a class="btn btn-primary" data-toggle="modal" data-target=".expert-exampaper-modal" disabled="disabled"><i class="fa fa-arrow-right"></i>即将开放</a>
									<div class="modal fade expert-exampaper-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									  <div class="modal-dialog">
									    <div class="modal-content">
									    	<div class="modal-header">
										        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										        <h6 class="modal-title" id="myModalLabel">选择试卷，参加考试</h6>
										     </div>
										     <div class="modal-body">
										     	<ul>
										     		<c:forEach items="${expertpaper}" var="item">
										     			<li>
										     				<a href="student/examing/${item.id}">${item.name}</a>
										     			</li>
										     		</c:forEach>
										     	</ul>
										     </div>
										     <div class="modal-footer">
        										<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
      										 </div>
									    	
									    </div>
									  </div>
									</div>
								</div>
								<!--//content-->

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
		<script type="text/javascript"
		src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript"
		src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script>
		$(function(){
			bindQuestionKnowledage();
			var result = checkBrowser();
			if (!result){
				alert("请至少更新浏览器版本至IE8或以上版本");
			}
		});
		
		function checkBrowser() {
			var browser = navigator.appName;
			var b_version = navigator.appVersion;
			var version = b_version.split(";");
			var trim_Version = version[1].replace(/[ ]/g, "");
			if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0") {
				return false;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE6.0") {
				return false;
			} else
				return true;
		}
		
		function bindQuestionKnowledage(){
			$(".knowledge-title").click(function(){
				var ul = $(this).parent().find(".question-list-knowledge");
				
				if(ul.is(":visible")){
					$(this).find(".fa-chevron-down").hide();
					$(this).find(".fa-chevron-up").show();
					
					$(".question-list-knowledge").slideUp();
					
				}else{
					$(".fa-chevron-down").hide();
					$(".fa-chevron-up").show();
					
					$(this).find(".fa-chevron-up").hide();
					$(this).find(".fa-chevron-down").show();
					
					$(".question-list-knowledge").slideUp();
					ul.slideDown();
					
				}
				
				
			});
		}
		</script>
	</body>
</html>
