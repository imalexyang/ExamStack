<%@ page language="java" contentType="text/html;   charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="nav navbar-nav">
<!-- 	<li><a href="#"><i class="fa fa-home"></i>网站首页</a></li>
	<li><a href="admin/question-list"><i class="fa fa-edit"></i>试题管理</a>
	</li>

	<li><a href="admin/exampaper-list"><i
			class="fa fa-file-text-o"></i>试卷管理</a></li>
	<li><a href="admin/user-list"><i class="fa fa-user"></i>会员管理</a></li>
	<li><a href="admin/field-list-1"><i class="fa fa-cloud"></i>题库管理</a>
	</li>
	<li><a href="admin/sys-backup"><i class="fa fa-cogs"></i>网站设置</a>
	</li> -->

	<c:forEach items="${topMenuList }" var="topMenuListItem">
		<li <c:if test="${topMenuListItem.menuId eq topMenuId}">class="active"</c:if>>
			<a href="${topMenuListItem.menuHref }"><i class="fa ${topMenuListItem.icon }"></i>${topMenuListItem.menuName }</a>
		</li>
	</c:forEach>
</ul>