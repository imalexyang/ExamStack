$(function() {
	$(".jstree li a").contextMenu(user_menu.menu, {
		 
		
		theme : 'vista'
		
	});
});
var user_menu = {

	menu : [{
		'添加用户' : function(menuItem, menu) {
			user_menu.add_user();
			$("#group-code").val($(this).text());
			$("#group-code").data("id", $(this).parent().data("id"));
		}
	},
	// $.contextMenu.separator,
	{
		'设置管理员' : function(menuItem, menu) {
			user_menu.grant_manager($(this).parent().data("id"));
		}
	}, {
		'添加组' : function(menuItem, menu) {
			user_menu.add_group();
			$("#parent-group-code").val($(this).text());
			$("#parent-group-code").data("id", $(this).parent().data("id"));
		}
	}, {
		'删除该组' : function(menuItem, menu) {
			user_menu.delete_group($(this).parent().data("id"));
		}
	},{
		'查看id' : function(menuItem, menu){
			alert($(this).parent().data("id"));
		}
	}
	
	
	],

	add_user : function add_user() {
		$("#add-user-modal").modal();
	},

	add_group : function add_group() {
		$("#add-group-modal").modal();
	},

	delete_group : function delete_group(group_id) {
		if (confirm("确认要删除吗？")) {
			var request = $.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				url : util.getCurrentRole() + "/delete-group/" + group_id
			});

			request.done(function(message, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				if (message.result == "success") {
					util.success("删除成功");
					document.location.href = document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/group-user';
				} else {
					util.error("操作失败请稍后尝试:" + message.result);
				}
			});
			request.fail(function(jqXHR, textStatus) {
				util.error("操作失败请稍后尝试");
			});
		}

	},

	grant_manager : function grant_manager(group_id) {
		var manager_name = prompt("输入管理员账号", "");
		if ("" == manager_name ||null == manager_name|| manager_name.length > 10)
			return false;

		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : util.getCurrentRole() + "/grant-manager/" + group_id + "/" + manager_name
		});

		request.done(function(message, tst, jqXHR) {
			if (!util.checkSessionOut(jqXHR))
				return false;
			if (message.result == "success") {
				util.success("授权成功");
				document.location.href = document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/group-user';
			} else {
				util.error("操作失败请稍后尝试:" + message.result);
			}
		});
		request.fail(function(jqXHR, textStatus) {
			util.error("操作失败请稍后尝试");
		});
	}
};
