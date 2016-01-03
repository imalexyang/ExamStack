$(function() {
	group_manage.initial();
});

var group_manage = {

	initial : function initial() {
		this.bindGroupAdd();
		this.bindGroupUpdate();
		this.bindGroupDelete();
	},
	
	bindGroupAdd : function bindGroupAdd(){
		$("#add-group-btn").click(function(){
			var data = new Object();
			data.groupName = $("#group-name").val();
			$(".df-submit").attr("disabled","disabled");
			jQuery.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "POST",
				url : "secure/add-group",
				data : JSON.stringify(data),
				success : function(message, tst, jqXHR) {
					if (message.result == "success") {
						util.success("添加成功", function() {
							window.location.reload();
						});
					} else {
						util.error("操作失败请稍后尝试:" + message.result);
						$(".df-submit").removeAttr("disabled");
					}
				},
				error : function(jqXHR, textStatus) {
					util.error("操作失败请稍后尝试");
					$(".df-submit").removeAttr("disabled");
				}
			});
		});
	},
	
	bindGroupUpdate : function bindGroupUpdate(){
		$("#update-group-btn").click(function(){
			var groupName =$("#group-name-edit").val();
			var  groupId =$("#group-name-edit").data("id");
			jQuery.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				url : "secure/modify-group-" + groupId + "-" + groupName,
				success : function(message, tst, jqXHR) {
					if (message.result == "success") {
						window.location.reload();
					} else {
						util.error("操作失败请稍后尝试:" + message.result);
						$(".df-submit").removeAttr("disabled");
					}
				},
				error : function(jqXHR, textStatus) {
					util.error("操作失败请稍后尝试");
					$(".df-submit").removeAttr("disabled");
				}
			});
		});
		
		
		
		
	},
	
	bindGroupDelete: function bindGroupDelete(){
		
		$(".delete-group-btn").click(function(e) {
			if (confirm("确认要删除吗？")) {
				
				var groupId= $(this).parent().parent().data("id");
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : "secure/delete-group-" + groupId,
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							window.location.reload();
						} else {
							util.error("操作失败请稍后尝试:" + message.result);
						}
					},
					error : function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					}
				});
				e.preventDefault();
				return false;
			}
			e.preventDefault();
			return false;
		});
		
		
	
		
		
		
		
	},
	
	
}