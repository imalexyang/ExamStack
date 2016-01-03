<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>ExamStack</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="keywords" content="">
<!--<link rel="shortcut icon" href="http://localhost:8080/Portal/../resources/images/favicon.ico" />-->
<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
<link href="resources/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="resources/css/style.css" rel="stylesheet">

<style>
.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td,
	.table>tbody>tr>td, .table>tfoot>tr>td {
	padding: 8px 0;
	line-height: 1.42857143;
	vertical-align: middle;
	border-top: 1px solid #ddd;
}

a.join-practice-btn {
	margin-top: 0;
}
</style>

</head>

<body>
	<header>
		<div class="container">
			<div class="row">
				<div class="col-xs-5">
					<div class="logo">
						<h1>
							<a href="#"><img alt="" src="resources/images/logo.png"></a>
						</h1>
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
			<nav class="collapse navbar-collapse bs-navbar-collapse"
				role="navigation">
				<ul class="nav navbar-nav">
						<li>
							<a href="home"><i class="fa fa-home"></i>主页</a>
						</li>
						<li class="active">
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

	<!-- Slider starts -->
	<div class="content" style="margin-bottom: 100px;">

		<div class="container">
			<ul class="nav nav-pills " style="margin: 20px 0;">
				<c:forEach items="${fieldList }" var="item">
					<li role="presentation" <c:if test="${item.fieldId == fieldId }"> class="active"</c:if>><a href="student/practice-list?fieldId=${item.fieldId }">${item.fieldName }</a></li>
				</c:forEach>
			</ul>
			<div class="row">
				<div class="col-xs-6">
					<div style="border-bottom: 1px solid #ddd;">
						<h3 class="title">
							<i class="fa fa-cloud-upload"></i> 强化练习
						</h3>
						<p>自主选择具体考点，各个击破</p>
					</div>

					<div class="question-list">

						<c:forEach items="${kparl}" var="item">

							<table class="table-striped table">
								<thead>
									<tr>
										<td colspan="4"><h6>${item.knowledgePointName }</h6>
											<span style="color: #428bca;">学习进度：<fmt:formatNumber
													value="${item.finishRate }" type="percent" /></span></td>
									</tr>
									<tr>
										<td>题型</td>
										<td>全部</td>
										<td>已做题目</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${item.typeAnalysis }" var="tp">
										<tr>
											<td>${tp.questionTypeName }</td>
											<td><span class="span-info question-number">${tp.restAmount + tp.rightAmount + tp.wrongAmount }</span></td>
											<td><span class="span-success question-number-2">${tp.rightAmount + tp.wrongAmount }</span></td>
											<td><a href="student/practice-improve/${fieldId }/${item.knowledgePointId }/${tp.questionTypeId }"
												class="btn btn-success btn-sm join-practice-btn">参加练习</a></td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot></tfoot>
							</table>
						</c:forEach>

					</div>
				</div>
				<div class="col-xs-6">
					<div style="border-bottom: 1px solid #ddd;">
						<h3 class="title">
							<i class="fa fa-eraser"></i> 错题练习
						</h3>
						<p>收录做过的所有错题</p>
					</div>
					<div class="question-list">

						<table class="table-striped table">
							<thead>

								<tr>
									<td>知识分类</td>
									<td>题目数量</td>
									<td></td>
									<td></td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${historyMap }" var="item">
									<tr>
										<td>${pointMap[item.key].pointName }</td>
										<td><span class="span-info question-number">${item.value.wrongAmount }</span></td>
										<td><a href="student/practice-incorrect/${item.value.fieldId }/${item.value.pointId }"
											class="btn btn-success btn-sm join-practice-btn">参加练习</a></td>
									</tr>
								</c:forEach>

							</tbody>
							<tfoot></tfoot>
						</table>

					</div>
					<div style="border-bottom: 1px solid #ddd; margin-bottom: 20px;">
						<h3 class="title">
							<i class="fa fa-superscript"></i> 随机练习
						</h3>
						<p>从题库中随机取出试题练习</p>

					</div>
					<a class="btn btn-success " href="student/practice-test/${fieldId }">随机来20道 </i>
					</a>
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
							ExamStack Copyright © <a href="http://www.examstack.com/"
								target="_blank">ExamStack</a> - <a href="." target="_blank">主页</a>
							| <a href="http://www.examstack.com/" target="_blank">关于我们</a> | <a
								href="http://www.examstack.com/" target="_blank">FAQ</a> | <a
								href="http://www.examstack.com/" target="_blank">联系我们</a>
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
		$(function() {
			bindQuestionKnowledage();
			var result = checkBrowser();
			if (!result) {
				alert("请至少更新浏览器版本至IE8或以上版本");
			}
		});

		function checkBrowser() {
			var browser = navigator.appName;
			var b_version = navigator.appVersion;
			var version = b_version.split(";");
			var trim_Version = version[1].replace(/[ ]/g, "");
			if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE7.0") {
				return false;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE6.0") {
				return false;
			} else
				return true;
		}

		function bindQuestionKnowledage() {
			$(".knowledge-title").click(function() {
				var ul = $(this).parent().find(".question-list-knowledge");

				if (ul.is(":visible")) {
					$(this).find(".fa-chevron-down").hide();
					$(this).find(".fa-chevron-up").show();

					$(".question-list-knowledge").slideUp();

				} else {
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
	<script type="text/javascript">
		var cnzz_protocol = (("https:" == document.location.protocol) ? " https://"
				: " http://");
		document
				.write(unescape("%3Cspan id='cnzz_stat_icon_1252987997'%3E%3C/span%3E%3Cscript src='"
						+ cnzz_protocol
						+ "s19.cnzz.com/z_stat.php%3Fid%3D1252987997' type='text/javascript'%3E%3C/script%3E"));
	</script>
</body>
</html>

