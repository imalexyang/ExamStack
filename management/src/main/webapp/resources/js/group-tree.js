$(function() {

	var request = $.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		cache: false,
		type : "GET",
		url : "group-tree/1"
	});

	request.done(requestionDone);

	request.fail(function(jqXHR, textStatus) {
		util.error("系统繁忙请稍后尝试");
		return false;
	});
	
	$("#delete-group-btn").click(DeleteGroup);
	$("#add-group-btn").click(AddGroup);

	genrateUserTable();
	bindDisableEnableUser();
	bindPageEvent();
});

function requestionDone(treeData, tst, jqXHR) {
	if (!util.checkSessionOut(jqXHR))
		return false;

	$.tree.initial({
		domObject : $(".def-tree-content-list"),
		treeData : treeData,
		checkBox : false,
		expanded : true,
		clickNode : genrateUserTable
	});

}


function DeleteGroup(){
	if($(".node-selected").length ==0){
		util.notify("请选中需删除的单位");
	}else{
		if($(".node-selected").parent("li").find("li").length>0){
			util.notify("不能删除包含有下级单位的组");
			return false;
		}
		
		
		var group_id = $(".node-selected").next().text();
		var values = new Array();
		values.push(group_id);
		
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : 'admin/delete-group',
			data : JSON.stringify(values),
		});

		request.done(function(message,tst,jqXHR) {
			if(!util.checkSessionOut(jqXHR))return false;
			if (message.result == "success") {
				util.success("删除成功！", function(){
					$(".node-selected").parent("li").remove();
				});
			} else {
				util.error(message.result);
			}
			
		});

		request.fail(function(jqXHR, textStatus) {
			util.error("系统繁忙请稍后尝试");
			return false;
		});
	}
	
}


function AddGroup(){
	if($(".node-selected").length ==0){
		util.notify("请选中需要添加子组的单位");
		return false;
	}else{
		var parent_level = parseInt( $(".node-selected").parent("li").children(".group-level-id").text());
		if( parent_level == 1){
			util.error("不能在此单位下添加");
			return false;
		}
		var group_id = $(".node-selected").next().text();
		
		var data = new Object();
		
		data.name = $("#txt-group-name").val();
		
		if(data.name == ""){
			$("#add-group-form .form-message").text("不能为空");
			return false;
		}else if(data.name.length > 20){
			$("#add-group-form .form-message").text("用户组名字过长");
			return false;
			
		}
		
		data.group_level_id = parent_level - 1;
		data.parent = group_id;
		
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "admin/add-group2",
			data : JSON.stringify(data),
		});

		request.done(function(message,tst,jqXHR) {
			if(!util.checkSessionOut(jqXHR))return false;
			util.success("添加成功！", function(){
				if($(".node-selected").parent().children("ul").length == 0){
					var liHtml = "<ul><li>";
					liHtml += "<span class=\"leaf\"> </span>";
					liHtml += "<span class=\"tree-node-text\">" + data.name +"</span>";
					liHtml += "<span class=\"data-value\">" + message.generatedId + "</span>";
					liHtml += "<span class=\"group-level-id\">" + data.group_level_id + "</span>";
					liHtml += "</li></ul>";
					$(".node-selected").parent().append(liHtml);
					
					$(".node-selected").parent().children(".leaf").addClass("expander");
					$(".node-selected").parent().children(".leaf").addClass("expander-open");
					$(".node-selected").parent().children(".leaf").removeClass("leaf");
				}else{
					var liHtml = "<li>";
					liHtml += "<span class=\"leaf\"> </span>";
					liHtml += "<span class=\"tree-node-text\">" + data.name +"</span>";
					liHtml += "<span class=\"data-value\">" + message.generatedId + "</span>";
					liHtml += "<span class=\"group-level-id\">" + data.group_level_id + "</span>";
					liHtml += "</li>";
					
					$(".node-selected").parent().children("ul").append(liHtml);
				}
			});
			$("form.inline-form").hide();
			$(".show-form").show();
			return false;
		});

		request.fail(function(jqXHR, textStatus) {
			util.error("系统繁忙请稍后尝试");
			return false;
		});
		
		return false;
	}
}



function genrateUserTable() {
	var entity = new Object();
	var groupId = $(".node-selected").parent().children(".data-value").text()==null? 1 : $(".node-selected").parent().children(".data-value").text();
	var page = $("#page-link-content .pressed-button").val() == null ? 1: $("#page-link-content .pressed-button").val() ;
	entity.groupId = parseInt(groupId);
	entity.page = parseInt(page);
	
	var request = $.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : "POST",
		url : "api2/get-user-table",
		data : JSON.stringify(entity)
	});
	
	request.done(function(table_entity,tst,jqXHR) {
		if(!util.checkSessionOut(jqXHR))return false;
		drawUserTable(table_entity);
	});

	request.fail(function(jqXHR, textStatus) {
		util.error("系统繁忙请稍后尝试");
		return false;
	});
	
}


function drawUserTable(table_entity){
//	util.notify("drawing...");
	if(table_entity.users==null){
		$("#user-list").empty();
		$("#page-link-content").empty();
		return false;
	}
	var userlist = table_entity.users;
	if(userlist.length == 0){
		$("#user-list").empty();
		$("#page-link-content").empty();
		return false;
	}
	var pagelink = table_entity.pagelink;
	$("#page-link-content").html(pagelink);
	
	var tablehtml = "<table class=\"de-table\">";
	tablehtml = tablehtml + "<thead><tr><td></td><td>账号</td><td>姓名</td><td>用户组</td><td>岗位</td><td>角色</td><td>状态</td><td>添加人</td><td>操作</td></tr></thead>";
	tablehtml = tablehtml + "<tbody>";
	
	for(var i = 0; i < userlist.length;i++){
		tablehtml = tablehtml + "<tr>";
		tablehtml = tablehtml + "<td><input type=\"checkbox\" value=\"" + userlist[i].id + "\" /></td>";
		tablehtml = tablehtml + "<td><a href=\"user-detail/" + userlist[i].username + "\" target=\"_blank\" title=\"查看用户详细信息\">" + userlist[i].username + "</a></td>";
		tablehtml = tablehtml + "<td>" + userlist[i].truename + "</td>";
		tablehtml = tablehtml + "<td>" + userlist[i].group_name + "</td>";
		tablehtml = tablehtml + "<td>" + userlist[i].job_name + "</td>";
		tablehtml = tablehtml + "<td>" + userlist[i].role_name + "</td>";
		tablehtml = tablehtml + "<td>" + (parseInt(userlist[i].enable_flag) == 0?"禁用":"") + "</td>";
		
		tablehtml = tablehtml + "<td>" + userlist[i].add_by + "</td>";
		
		
		tablehtml = tablehtml + "<td><a href=\"admin/update-user2/" + userlist[i].username + "\" target=\"_blank\" title=\"修改此用户\">修改</a></td>";

		tablehtml = tablehtml + "</tr>";
	}
	
	tablehtml = tablehtml + "</tbody><tfoot></tfoot></table>";
	$("#user-list").html(tablehtml);
}


function bindDisableEnableUser(){
	$("span#btn_disable").click(function() {
		var values = new Array();
		var checkboxs = $("input:checked");
		
		$.each(checkboxs, function() {
			//if($(this).val() != -1){
				var user = new Object();
				user.id = $(this).val();
				values.push(user);
			//}
		});
		
		if (checkboxs.length == 0) {
			util.notify("请选择需注销的用户");
		} else {
			var message = "确定要注销吗？";
			var answer = confirm(message);
			if(!answer){
				return false;
			}
			
			jQuery.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "POST",
				url : 'admin/user-disable',
				data : JSON.stringify(values),
				success : function(message,tst,jqXHR) {
					if(!util.checkSessionOut(jqXHR))return false;
					if (message.result == "success") {
						util.success("注销成功！",function(){
							genrateUserTable();
						});
					} else {
						util.error(message.result);
					}
				},
				error : function(jqXHR, textStatus) {
					util.error("操作失败请稍后尝试");
				}
			});
		}
		return false;
	});
	
	
	$("span#btn_enable").click(function() {
		var values = new Array();
		var checkboxs = $("input:checked");
		
		$.each(checkboxs, function() {
			//if($(this) != $("input#ck-select-all")){
				var user = new Object();
				user.id = $(this).val();
				values.push(user);
			//}
			
		});
		
		if (checkboxs.length == 0) {
			util.notify("请选择需启用的用户");
		} else {
			var message = "确定要启用吗？";
			var answer = confirm(message);
			if(!answer){
				return false;
			}
			
			jQuery.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "POST",
				url : 'admin/user-enable',
				data : JSON.stringify(values),
				success : function(message,tst,jqXHR) {
					if(!util.checkSessionOut(jqXHR))return false;
					if (message.result == "success") {
						util.success("启用成功！", function(){
							genrateUserTable();
						});
					} else {
						util.error(message.result);
					}
				},
				error : function(jqXHR, textStatus) {
					util.error("操作失败请稍后尝试");
				}
			});
		}
		return false;
	});
	
}

function bindPageEvent(){
	$("#page-link-content").delegate("button", "click",	function() {
			$("#page-link-content .pressed-button").removeClass("pressed-button");
			$(this).addClass("pressed-button");
			genrateUserTable();
	});
}









