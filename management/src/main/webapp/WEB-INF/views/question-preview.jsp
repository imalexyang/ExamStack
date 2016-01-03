<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- <%@taglib uri="spring.tld" prefix="spring"%> --%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String servletPath = (String) request
			.getAttribute("javax.servlet.forward.servlet_path");
	String[] list = servletPath.split("\\/");
	request.setAttribute("role", list[1]);
	request.setAttribute("topMenuId", list[2]);
	request.setAttribute("leftMenuId", list[3]);
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>试题预览</title>
<meta name="keywords" content="">
<link rel="shortcut icon"
	href="<%=basePath%>resources/images/favicon.ico" />
<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
<link href="resources/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="resources/css/style.css" rel="stylesheet">
<link href="resources/css/exam.css" rel="stylesheet" type="text/css">

</head>
<body>
	<header>
		<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
		<div class="container">
			<div class="row">
				<div class="col-xs-5">
					<div class="logo">
						<h1>
							<a href="#">网站管理系统</a>
						</h1>
						<div class="hmeta">专注互联网在线考试解决方案</div>
					</div>
				</div>
				<div class="col-xs-7" id="login-info">
					<c:choose>
						<c:when
							test="${not empty sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}">
							<div id="login-info-user">

								<a
									href="user-detail/${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}"
									id="system-info-account" target="_blank">${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}</a>
								<span>|</span> <a href="j_spring_security_logout"><i
									class="fa fa-sign-out"></i> 退出</a>
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
			<nav class="collapse navbar-collapse bs-navbar-collapse"
				role="navigation">
				<c:import
					url="/common-page/top-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}"
					charEncoding="UTF-8" />
			</nav>
		</div>
	</div>

	<!-- Navigation bar ends -->

	<!-- Slider starts -->

	<div>
		<!-- Slider (Flex Slider) -->

		<div class="container" style="min-height: 800px;">

			<div class="row">
				<div class="col-xs-2" id="left-menu">
					<c:import
						url="/common-page/left-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}"
						charEncoding="UTF-8" />
				</div>
				<div class="col-xs-10" id="right-content">
					<div class="page-header">
						<h1>
							<i class="fa fa-file-text"></i> 试题预览
						</h1>
					</div>
					<div class="page-content">
						<div class="def-bk" id="bk-exampaper">

							<div class="expand-bk-content" id="bk-conent-exampaper">
								<ul id="exampaper-body" style="padding: 0px;">
									${strHtml }
									<div class="answer-desc-detail">
										<label class="label label-primary"><i
											class="fa fa-check-square-o"></i><span> 添加人</span></label>
										<div class="answer-desc-content">${question.creator }</div>
									</div>
								</ul>
								<div id="exampaper-footer"
									style="height: 187px; text-align: center; margin-top: 40px;">
									<c:choose>
										<c:when
											test="${question.creator == sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}">
											<button class="btn btn-danger" id="delete-question-btn">
												<i class="fa fa-trash-o"></i> 删除该题
											</button>
											<button class="btn btn-info"
												onclick="javascript:window.close();">
												<i class="fa fa-times"></i> 关闭页面
											</button>
										</c:when>
										<c:otherwise>
											<button class="btn btn-danger" id="delete-question-btn"
												disabled="disabled" title="您只能删除你自己添加的题">
												<i class="fa fa-trash-o"></i> 删除该题
											</button>
											<button class="btn btn-info"
												onclick="javascript:window.close();">
												<i class="fa fa-times"></i> 关闭页面
											</button>
											<p>您只能删除你自己添加的题</p>
										</c:otherwise>
									</c:choose>

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
							ExamStack Copyright © <a href="http://www.examstack.com/" target="_blank">ExamStack</a>
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
	<script type="text/javascript" src="resources/js/all.js"></script>
	<script type="text/javascript"
		src="resources/js/jquery-ui-1.9.2.custom.min.js"></script>

	<script type="text/javascript"
		src="resources/js/uploadify/jquery.uploadify3.1Fixed.js"></script>
	<script type="text/javascript"
		src="resources/js/question-upload-img.js"></script>
	<script type="text/javascript" src="resources/js/field-2-point.js"></script>
	<script type="text/javascript" src="resources/js/question-add.js"></script>

	<!-- Bootstrap JS -->
	<script type="text/javascript"
		src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script>
		$(function() {
			$("#delete-question-btn").click(
					function() {
						var result = confirm("确定删除吗？删除后将不可恢复");
						if (result == true) {
							jQuery.ajax({
								headers : {
									'Accept' : 'application/json',
									'Content-Type' : 'application/json'
								},
								type : "GET",
								url : 'secure/question/delete-question/'
										+ $(".question-id").text(),
								success : function(message, tst, jqXHR) {
									if (!util.checkSessionOut(jqXHR))
										return false;
									if (message.result == "success") {
										util.success("删除成功！", function() {
											window.opener.location
													.reload(false);
											window.close();
										});
									} else {
										util.error("操作失败请稍后尝试");
									}
								},
								error : function(jqXHR, textStatus) {
									util.error("操作失败请稍后尝试");
								}
							});
						}

					});

		});
	</script>
</body>
</html>