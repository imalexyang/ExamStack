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
		<title>用户中心</title>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<!--[if lte IE 8]>
			<script type="text/javascript" src="resources/chartjs/excanvas.js"></script>
		<![endif]-->
		<style>
			.table-striped a{
				text-decoration: underline;
			}
			
			.span-success{
				color:#5cb85c;
				font-weight:bolder;
			}
			
			.span-danger{
				color:#d9534f;
				font-weight:bolder;
			}
			
			.span-info{
				color:#5bc0de;
				font-weight:bolder;
			}
			
			h6{
				font-weight:bold !important; 
			}
			
			.radar-legend li span {
				display: block;
				position: absolute;
				left: 0;
				top: 0;
				width: 20px;
				height: 100%;
				border-radius: 5px;
			}
			
			.radar-legend li {
				display: block;
				padding-left: 30px;
				position: relative;
				margin-bottom: 4px;
				border-radius: 5px;
				padding: 2px 8px 2px 28px;
				font-size: 14px;
				cursor: default;
				-webkit-transition: background-color 200ms ease-in-out;
				-moz-transition: background-color 200ms ease-in-out;
				-o-transition: background-color 200ms ease-in-out;
				transition: background-color 200ms ease-in-out;
			}				
			
			#field-switch{
				margin:15px 0 10px 0px;;
				height:34px;
				width:200px;
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
							<li class="active">
								<a> <i class="fa fa-dashboard"></i> 用户中心 </a>
							</li>
							<li>
								<a href="student/analysis"> <i class="fa fa-bar-chart-o"></i> 统计分析 </a>
							</li>
							<li>
								<a href="student/exam-history"> <i class="fa fa-history"></i> 考试历史 </a>
							</li>
							<li>
								<a href="student/training-history"> <i class="fa fa-book"></i> 培训历史 </a>
							</li>
							
						</ul>
					</div>
					<div class="col-xs-10">
						<div class="page-header">
							<h1><i class="fa fa-dashboard"></i> 用户中心</h1>
						</div>
						<div class="page-content row">
							<div class="col-xs-4">
								<h6>个人信息</h6>
								<div>
									<span >姓名：</span>
									<span> ${username }</span>
								</div>
								<div>
									<span >邮箱：</span>
									<span> ${email } </span>
								</div>
								<%-- <div>
									<span >专业：</span>
									<span> ${field } </span>
								</div>
								<div>
									<span >上次登录：</span>
									<span> <fmt:formatDate value="${lastLoginTime }" pattern="yyyy-MM-dd"/> </span>
								</div> --%>
								
							</div>
							
							

						</div>
						<div class="page-content row">
							<div class="col-xs-12" id="radar-div">
								<h6>知识掌握情况</h6>
								<select id="field-switch" class="form-control">
										<c:forEach items="${fieldList }" var="item">
											<option value="${item.fieldId }">${item.fieldName }</option>
										</c:forEach>
										<!-- <option value="4">公务员申论</option>
										<option value="5">医药行业考试</option> -->
								</select>
								<div class="page-content row">
									
									<div class="col-xs-8">
										<div id="mychart" style="height:450px;"></div>
										<p>此统计数据不包括简答、论述、分析等主观题</p>
									</div>
									<div class="col-xs-4" id="radar-legend">
										
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
		<script type="text/javascript" src="resources/js/echarts-all.js"></script>
		<!-- <script type="text/javascript" src="resources/chartjs/Chart.min.js"></script> -->
		
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		
		<script type="text/javascript">

		$("#field-switch").val("${fieldId}");
		$("#field-switch").change(function(){
			window.location.href="student/usercenter/"+ $(this).val();
		});
		
			
			$(function(){
				var option = {
					    
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        orient : 'vertical',
					        x : 'right',
					        y : 'bottom',
					        data:['完成率','正确率']
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: true},
					            dataView : {show: true, readOnly: false},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    polar : [
					       {
					           indicator : ${labels}
					        }
					    ],
					    calculable : true,
					    series : [
					        {
					            name: '预算 vs 开销（Budget vs spending）',
					            type: 'radar',
					            data : [
					                {
					                    value : ${finishrate},
					                    name : '完成率'
					                },
					                 {
					                    value : ${correctrate},
					                    name : '正确率'
					                }
					            ]
					        }
					    ]
					};
				var myChart = echarts.init(document.getElementById('mychart'));
				myChart.setOption(option);
			});
		</script>
	</body>
</html>