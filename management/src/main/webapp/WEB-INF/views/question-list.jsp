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
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>试题管理</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">

		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style>
			.examing-point {
				display: block;
				font-size: 10px;
				margin-top: 5px;
			}
			.question-name-td {
				width: 300px;
			}
			.change-property {
				cursor: pointer;
			}
			.add-tag-btn {
				cursor: pointer;
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
							<h1><i class="fa fa-list-ul"></i> 试题管理 </h1>
						</div>
						<div class="page-content">

							<div id="question-filter">

								<dl id="question-filter-field">
									<dt>
										专业题库：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.fieldId == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${fieldList}" var="field">
											<c:choose>
												<c:when test="${questionFilter.fieldId == field.fieldId }">
													<span class="label label-info" data-id="${field.fieldId}">${field.fieldName}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${field.fieldId}">${field.fieldName}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</dd>
								</dl>
								<dl id="question-filter-knowledge">
									<dt>
										知识分类：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.knowledge == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${knowledgeList}" var="knowledge">
											<c:choose>
												<c:when test="${questionFilter.knowledge == knowledge.pointId }">
													<span data-id="${knowledge.pointId}" class="label label-info">${knowledge.pointName}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${knowledge.pointId}">${knowledge.pointName}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</dd>
								</dl>

								<dl id="question-filter-qt">
									<dt>
										试题类型：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.questionType == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${questionTypeList}" var="questionType">
											<c:choose>
												<c:when test="${questionFilter.questionType == questionType.id }">
													<span data-id="${questionType.id}" class="label label-info">${questionType.name}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${questionType.id}">${questionType.name}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>

									</dd>
								</dl>
								<dl id="question-filter-tag">
									<dt>
										标签：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.tag == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${tagList}" var="tag">
											<c:choose>
												<c:when test="${questionFilter.tag == tag.tagId }">
													<span data-id="${tag.tagId}" class="label label-info">${tag.tagName}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${tag.tagId}">${tag.tagName}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>

									</dd>
								</dl>
								<div class="page-link-content" style="float:right;margin-left:30px;">
									<ul class="pagination pagination-sm">
										${pageStr}
									</ul>
								</div>
								<div class="input-group search-form" style="float: left;width: 300px;padding: 10px 0px;">
									<input type="text" class="form-control" placeholder="搜索试题" value="${searchParam }" id="txt-search">
									<span class="input-group-btn">
										<button class="btn btn-sm btn-default" type="button" id="btn-search" data-id="0">
											<i class="fa fa-search"></i>搜索
										</button> </span>
								</div>
							</div>
							<div id="question-list">
								<input id="field-id-hidden" value="${fieldId }" type="hidden">
								<input id="knowledge-hidden" value="${knowledge }" type="hidden">
								<input id="question-type-hidden" value="${questionType }" type="hidden">
								<input id="search-param-hidden" value="${searchParam }" type="hidden">
								<table class="table-striped table">
									<thead>
										<tr>
											<!-- <td></td> --><td>ID</td><td class="question-name-td" style="width:240px">试题名称</td><td style="width:60px">类型</td><td>专业</td><td>知识类</td><td>创建人</td><td style="width:90px;">操作</td>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${questionList }" var="items">
											<tr>
												<td style="display:none;">
												<input type="checkbox" value="${items.id }">
												</td><td>${items.id }</td>
												<td>
													<a href="<%=list[1]%>/question/question-preview/${items.id }" target="_blank" title="预览">${items.name }</a>
														<div class="question-tags">
															<span>${items.tags }</span>
															<!-- <span>易错题</span>
															<span>送分题</span> -->
														</div>
													
													</td>

												<td>${items.questionTypeName }</td><td>${items.fieldName }</td><td>${items.pointName }</td>
												<td>${items.creator }</td> 
												<td>
													<c:choose>
														<c:when test="${items.creator == sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}">
															<a class="change-property btn-sm btn-info"><i class="ace-icon fa fa-pencil bigger-120"></i></a>
															<a class="delete-question-btn btn-sm btn-danger"><i class="ace-icon fa fa-trash-o bigger-120"></i></a>
															
															<!--  <i class="fa fa-pencil change-property"></i>
															 <i class="fa fa-trash-o delete-question-btn"></i> -->
														</c:when>
														<c:otherwise>
															
														</c:otherwise>
													</c:choose>
												
												</td>
											</tr>
										</c:forEach>

									</tbody><tfoot></tfoot>
								</table>
								<!-- 修改试题modal -->
								<div class="modal fade" id="change-property-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
													&times;
												</button>
												<h6 class="modal-title" id="myModalLabel">修改分类</h6>
											</div>
											<div class="modal-body">
												<form id="question-edit-form">
													<span id="add-update-questionid" style="display:none;"></span>
													<div class="form-line question-knowledge">
														<span class="form-label"><span class="warning-label">*</span>知识分类：</span>
														<div>
															<div class="clearfix">
																<div id="aq-course1" style="padding:0px;float:left; width:48%;">
																	<select id="field-select" class="df-input-narrow" size="4" style="width:100%;">
																		<c:forEach items="${fieldList}" var="field">
																			<option value="${field.fieldId}">${field.fieldName} </option>
																		</c:forEach>
																	</select>
																</div>
																<div id="aq-course2" style="padding:0px; float:right;width:48%;">
																	<select id="point-from-select" class="df-input-narrow" size="4" style="width:100%;">
																	</select>
																</div>
															</div>
															
															<div style="text-align:center;margin:10px 0;">
																<button id="add-point-btn" class="btn btn-primary btn-xs">选择知识分类</button>
																<button id="del-point-btn" class="btn btn-danger btn-xs">删除知识分类</button>
																<button id="remove-all-point-btn" class="btn btn-warning btn-xs">清除列表</button>
															</div>
															<div id="kn-selected" style="padding-left:0px;text-align:center;margin-bottom:20px;">
																	<select id="point-to-select" class="df-input-narrow" size="4" style="width:80%;">
																	</select>
																	<p style="font-size:12px;color:#AAA;">您可以从上面选择4个知识分类</p>
															</div>
														</div>
														<span class="form-message"></span>
													</div>
													<%-- <div class="form-line exampaper-type" id="aq-course1">
														<span class="form-label"><span class="warning-label">*</span>专业：</span>
														<select id="field-select" class="df-input-narrow">
															<c:forEach items="${fieldList}" var="field">
																<option value="${field.fieldId}">${field.fieldName} </option>
															</c:forEach>
														</select><span class="form-message"></span>
													</div>
													<div class="form-line exampaper-type" id="aq-course2">
														<span class="form-label"><span class="warning-label">*</span>知识类：</span>
														<select id="point-from-select" class="df-input-narrow">
															<c:forEach items="${knowledgeList}" var="item">
																<option value="${item.pointId}">${item.pointName} </option>
															</c:forEach>
														</select><span class="form-message"></span>
													</div> --%>
													<div class="form-line form-question-reference" style="display: block;">
														<span class="form-label"><span class="warning-label"></span>来源：</span>
															<input type="text" class="df-input-narrow"><span class="form-message"></span>
														<br>
													</div>
													<div class="form-line form-question-examingpoint" style="display: block;">
														<span class="form-label"><span class="warning-label"></span>考点：</span>
															<input type="text" class="df-input-narrow"><span class="form-message"></span>
														<br>
													</div>
													<div class="form-line form-question-keyword" style="display: block;">
														<span class="form-label"><span class="warning-label"></span>关键字：</span>
															<input type="text" class="df-input-narrow"><span class="form-message"></span>
														<br>
													</div>
													<div class="form-line form-question-analysis" style="display: block;">
														<span class="form-label"><span class="warning-label"></span>题目解析：</span>
														<textarea class="add-question-ta" style="width: 100%;"></textarea><span class="form-message"></span>
														<br>
													</div>
													<div class="form-line exampaper-type" id="aq-tag">
														<span class="form-label"><span class="warning-label">*</span>标签：</span>
														<select id="tag-from-select" class="df-input-narrow">
															<c:forEach items="${tagList }" var="item">
																<option value="${item.tagId }" data-privatee="${item.privatee }" data-creator="${item.creator}" data-memo="${item.memo }" data-createtime="${item.createTime }">${item.tagName } </option>
															</c:forEach>

														</select><a class="add-tag-btn">添加</a><span class="form-message"></span>

														<div class="q-label-list"></div>
													</div>
												</form>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">
													关闭窗口
												</button>
												<button id="update-exampaper-btn" type="button" class="btn btn-primary">
													确定修改
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="page-link-content">
								<ul class="pagination pagination-sm">
									${pageStr}
								</ul>
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
		<script type="text/javascript" src="resources/js/all.js"></script>
		
		<script type="text/javascript" src="resources/js/question-list.js"></script>
		<script type="text/javascript" src="resources/js/field-2-point.js"></script>

		<script>
			$(function() {
				$(".change-property").click(function() {
					$("#point-to-select option").remove();
					$("#change-property-modal").modal({
						backdrop : true,
						keyboard : true
					});
					var paper_id = $(this).parent().parent().find(":checkbox").val();
					$("#add-update-questionid").text(paper_id);
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "GET",
						url : "secure/question/question-detail/" + paper_id,
						success : function(message, tst, jqXHR) {
							if (!util.checkSessionOut(jqXHR))
								return false;
							if (message.result == "success") {
								//将message.object里面的内容写到 div（class=q-label-list）里面
								var innerHtml = "";
								$.each(message.object.tagList, function(index, element) {
									innerHtml += "<span class=\"label label-info q-label-item\" data-privatee=" + element.privatee + " data-creator=" + element.creator + " data-memo=" + element.memo + " data-id=" + element.tagId + ">" + element.tagName + "  <i class=\"fa fa-times\"></i>	</span>";
								});
								$(".q-label-list").html(innerHtml);
								var point = $("#point-to-select");
								$.each(message.object.knowledgePoint, function(index, element){
									var html = "<option value=\"" + element.pointId + "\">" + element.fieldName + " > " + element.pointName + "</option>";
									point.append(html);
								});
								$(".form-question-analysis textarea").val(message.object.analysis);
								$(".form-question-reference input").val(message.object.referenceName);
								$(".form-question-examingpoint input").val(message.object.examingPoint);
								$(".form-question-keyword input").val(message.object.keyword);
							} else {
								util.error("获取标签失败请稍后尝试:" + message.result);
							}

						},
						error : function(jqXHR, textStatus) {
							util.error("操作失败请稍后尝试");
						}
					});
				});

				$(".add-tag-btn").click(function() {
					var label_ids = $(".q-label-item");
					var flag = 0;
					label_ids.each(function() {
						if ($(this).data("id") == $("#tag-from-select").val())
							flag = 1;
					});
					if (flag == 0) {
						var selected = $("#tag-from-select").find("option:selected");

						$(".q-label-list").append("<span class=\"label label-info q-label-item\" data-privatee=" + selected.data("privatee") + " data-creator=" + selected.data("creator") + " data-memo=" + selected.data("memo") + " data-id=" + $("#tag-from-select").val() + " data-createTime=" + selected.data("createTime") + ">" + $("#tag-from-select :selected").text() + "  <i class=\"fa fa-times\"></i>	</span>");
					} else {
						util.error("不能重复添加");
					}
				});


				$(".q-label-list").on("click", ".fa", function() {
					$(this).parent().remove();
				});

				
				$(".delete-question-btn").click(function(){
					if(confirm("确定要删除吗？删除后无法恢复")){
						jQuery.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : "GET",
							url : 'secure/question/delete-question/' + $(this).parent().parent().find(":checkbox").val(),
							success : function(message, tst, jqXHR) {
								if (!util.checkSessionOut(jqXHR))
									return false;
								if (message.result == "success") {
									util.success("删除成功！", function() {
										
										window.location.reload();
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
