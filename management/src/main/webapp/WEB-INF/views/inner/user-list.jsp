<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>用户管理</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon"
		href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css"
		rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">

		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style type="text/css">

		</style>
	</head>
	<body>
		<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
		<div id="question-list">
			<div class="table-controller">

				<div class="btn-group table-controller-item" style="float: left">
					<button class="btn btn-default btn-sm" id="add-user-modal-btn">
						<i class="fa fa-plus-square"></i> 添加用户
					</button>

					<!-- <button class="btn btn-default" id="add-user-modal-btn">
					<i class="icon-plus"></i> 添加用户组
					</button> -->
					<!-- <button class="btn btn-default btn-enable ">
					<i class="icon-ok"></i> 启用
					</button>
					<button class="btn btn-danger btn-disable ">
					<i class="icon-remove"></i> 禁用
					</button> -->
				</div>
				<div class="btn-group table-controller-item" style="float: left">
					<button class="btn btn-default btn-sm" id="link-user-modal-btn">
						<i class="fa fa-random"></i> 关联用户
					</button>

				</div>
				<div class="table-search table-controller-item"
				style="float: left; width: 200px;">
					<div class="input-group search-form">
						<input type="text" class="form-control" placeholder="搜索用户" value="${searchStr }"
						id="txt-search">
						<span class="input-group-btn">
							<button class="btn btn-sm btn-default" type="button"
							id="btn-search" data-id="${groupId }">
								<i class="fa fa-search"></i>搜索
							</button> </span>
					</div>
				</div>
				<div class="table-search table-controller-item-page">
					<ul class="pagination pagination-sm">
						${pageStr}
					</ul>
				</div>

			</div>
			<table class="table-striped table">
				<thead>
					<tr>
						<td>ID</td>
						<td>用户名</td>
						<td>姓名和身份证</td>
						<td>手机邮箱</td>
						<td>部门单位</td>
						<td>创建时间</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userList }" var="item">
						<tr>
							<td><span class="r-id">${item.userId }</span></td>
							<td><span class="r-name">${item.userName }</span>
								<div>
									<%-- <a href="javascript:parent.location.href='admin/training/student-practice-status/${item.userId}'">学习进度</a> --%>
									<a href="javascript:parent.open('admin/training/student-practice-status/${item.userId}','_blank' )">学习进度</a>
								</div>
							
							</td>
							<td>
								<div class="r-truename">
									${item.trueName }
								</div>
								<div class="r-national-id">
									${item.nationalId }
								</div>
							</td>
							<td>
								<div class="r-phone">
									${item.phoneNum }
								</div>
								<div class="r-email">
									${item.email }
								</div>
							</td>
							<td>
								<div class="r-company" style="display: none;">
									${item.company }
								</div>
								<div class="r-dept">
									${item.department }
								</div>
							</td>
							<td>
								<span class="r-createtime">
									<fmt:formatDate
									value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" />
								</span>
							</td>
							<td>
								<c:choose>
									<c:when test="${item.enabled }">
										<span>启用</span>
									</c:when>
									<c:otherwise>
										<span>禁用</span>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<span class="r-update-btn btn-sm btn-success" data-id="21321" data-depid="${item.depId }">修改</span><span class="disable-btn btn-sm btn-danger" data-id="${item.userId }"
								data-status="${!item.enabled }">
								<c:choose>
									<c:when test="${!item.enabled }">
										启用
									</c:when>
									<c:otherwise>
										禁用
									</c:otherwise>
								</c:choose> 
								</span>
								<span class="r-reset-pwd-btn btn-sm btn-warning" data-id="21321" data-depid="${item.depId }">密码</span>
								<c:choose>
									<c:when test="${ groupId == 0 }">
										<span class="link-user-r-btn btn-sm btn-info" title="关联用户">
													<i class="fa fa-random"></i>
										</span>
									</c:when>
									<c:otherwise>
										<span class="unlink-user-r-btn btn-sm btn-danger" title="移出分组" data-id="${item.userId }" data-group="${groupId }">
													<i class="fa fa-times-circle"></i>
									</span>
									</c:otherwise>
								</c:choose>	
								
								<%-- <span class="link-user-r-btn simple-btn" title="关联用户">
													<i class="fa fa-random"></i>
								</span>
								<span class="unlink-user-r-btn simple-btn" title="移出分组" data-id="${item.userId }" data-group="${groupId }">
													<i class="fa fa-times-circle"></i>
								</span> --%>
							</td>
						</tr>

					</c:forEach>

				</tbody>
				<tfoot></tfoot>
			</table>
			<input id="authority-type" type="hidden" value="${authority }">
		</div>
		<div id="page-link-content">
			<ul class="pagination pagination-sm">
				${pageStr}
			</ul>
		</div>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript"
		src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/user-list-inner.js"></script>

		<!-- Bootstrap JS -->
		<script type="text/javascript"
		src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(function() {
				var btnSearch = $("#btn-search");
				btnSearch.click(function() {
					var groupId = $(this).data("id");
					var searchStr = $("#txt-search").val();
					var authority = $("#authority-type").val();
					var url = util.getCurrentRole() + "/user/inner/user-list/" + groupId  + "-" + authority + "?searchStr=" + searchStr;
					window.parent.document.getElementById('user-list-iframe').src = url;
				});
				
				$(".unlink-user-r-btn").click(function(){
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "GET",
						url : "secure/delete-user-group-" + $(this).data("id") + "-" + $(this).data("group"),
						success : function(message, tst, jqXHR) {
							if (!util.checkSessionOut(jqXHR))
								return false;
							if (message.result == "success") {
								util.success("操作成功", function() {
									window.location.reload();
								});
							} else {
								util.error("操作失败请稍后尝试:" + message.result);
							}

						},
						error : function(jqXHR, textStatus) {
							util.error("操作失败请稍后尝试");
						}
					});

					return false;
				});
			});

			function getUrlParam(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
				//构造一个含有目标参数的正则表达式对象
				var r = window.location.search.substr(1).match(reg);
				//匹配目标参数
				if (r != null)
					return unescape(r[2]);
				return null;
				//返回参数值
			}
		</script>
	</body>
</html>