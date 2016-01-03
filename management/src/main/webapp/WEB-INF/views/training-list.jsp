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
		<title>培训管理</title>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style type="text/css">
			#training-table  > tbody{
				cursor:pointer;
				
			}
			#training-table > tbody tr:hover{
				background-color:#EEE;
			}
			.check-tr{
			background-color:#EEE;
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
						<c:import url="/common-page/left-menu?topMenuId=${topMenuId}&leftMenuId=${leftMenuId}" charEncoding="UTF-8" />

					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1> <i class="fa fa-bar-chart-o"></i> 培训管理 </h1>
						</div>
						<div class="page-content">
							<div class="row">
								<div class="col-xs-6">
								
									<table class=" table" id="training-table">
										<!-- <h4>
											培训列表
										</h4> -->
										<thead>
											<tr>
												<td>培训列表</td>
											</tr>
										</thead>
										<!-- <thead>
											<tr>
												<td></td>
												<td style="display:none;">ID</td>
												<td>培训名</td>
												<td>创建时间</td>
												<td>创建人</td>
												<td>操作</td>
											</tr>
										</thead> -->
										<tbody>
											<c:forEach items="${trainingList }" var="item">
												<tr>
													<td style="display:none;">${item.trainingId }</td>
													<td>
														<a class="training-sections" data-id="${item.trainingId }" >${item.trainingName }</a>
													
														<p>${item.creatorName } <i class="fa fa-clock-o"></i><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm"/></p>
													</td>
													<%-- <td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
													<td>${item.creatorName }</td> --%>
													<td>
														<a class="delete-paper btn-sm btn-danger"> 删除</a>
														<a href="<%=list[1]%>/training/student-training-history/${item.trainingId }" class="btn-sm btn-info">培训进度</a>
														<%-- <button class="btn btn-success add-section-btn btn-sm" data-id="${item.trainingId }">添加章节</button> --%>
													</td>
												</tr>
											<%-- 	<tr><td class="collapse" data-id="${item.trainingId }" colspan="6">11111</td></tr> --%>
												
											</c:forEach>
											
										</tbody><tfoot></tfoot>
									</table>
								</div>
								
								<div class="col-xs-6" id="section-content">
								
								
								
								</div>
								<div class="col-xs-6" >
									<button class="btn btn-sm btn-info add-section-btn" style="display:none;" >新增章节</button>
								</div>
								
							</div>
							
							<div id="page-link-content">
							
								<ul class="pagination pagination-sm">
									${pageStr}
								</ul>
							</div>
							
							<div class="modal fade" id="add-section-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
												&times;
											</button>
											<h6 class="modal-title" id="myModalLabel">添加章节</h6>
										</div>
										<div class="modal-body">
											<form id="section-add-form" style="margin-top:40px;"  action="secure/add-user">
												<div class="form-line form-group-id" style="display: none;">
													<span class="form-label"><span class="warning-label">*</span>id：</span>
													<input type="text" class="df-input-narrow" id="training-add-id" value="" disabled="disabled">
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-group" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>培训名称：</span>
													<input type="text" class="df-input-narrow" id="training-name" value="默认分组" disabled="disabled">
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-group add-section-name" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>章节名称：</span>
													<input type="text" class="df-input-narrow" id="section-name" value="" >
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-group add-section-desc" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>章节描述：</span>
													<textarea class="df-input-narrow" id="section-desc"></textarea>
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line add-section-file">
													<span class="form-label">上传附件：</span>
													<div id="div-file-list">
													</div>
													<div class="form-line" id="uploadify"></div>
													<span class="form-message">请上传mp4 flv pdf文件</span>
												</div>
											</form>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">
												关闭窗口
											</button>
											<button id="add-section-btn" type="button" class="btn btn-primary">
												确定添加
											</button>
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
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/uploadify/jquery.uploadify3.1Fixed.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/training-list.js"></script>
		<script type="text/javascript" src="resources/js/training-file-upload.js"></script>
		<script type="text/javascript" src="resources/js/add-training-section.js"></script>
		<script>
			$(function() {
				
				if($("tbody tr").length > 0){
					$("tbody tr:first").addClass("check-tr");
					$('#section-content').load(util.getCurrentRole() + '/training/section-list/' + $(    $("tbody tr:first").find("td")[1]     ).find("a").data("id"));
					  
					  $(".add-section-btn").show();
					  
					  $("#training-name").val($(    $("tbody tr:first").find("td")[1]     ).find("a").text()                                  );
					  $("#training-add-id").val($(    $("tbody tr:first").find("td")[1]     ).find("a").data("id")                                  );
					  console.log($("#training-add-id").val());
				}
				$("#training-table > tbody tr").click(function(){
					console.log("load section.......");
					$("tr").removeClass("check-tr");
					$(this).addClass("check-tr");
					  $('#section-content').load(util.getCurrentRole() + '/training/section-list/' + $(    $(this).find("td")[1]     ).find("a").data("id"));
					  
					  $(".add-section-btn").show();
					  
					  $("#training-name").val($(    $(this).find("td")[1]     ).find("a").text()                                  );
					  $("#training-add-id").val($(    $(this).find("td")[1]     ).find("a").data("id")                                  );
					  console.log($("#training-add-id").val());
				});
				
				
				$(".add-section-btn").click(function(){
						$("#add-section-modal").modal({
							backdrop : true,
							keyboard : true
						});
					
				});
			});
		</script>
	</body>
</html>