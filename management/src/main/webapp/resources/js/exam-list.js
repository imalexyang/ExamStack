$(function(){
	exams.initial();
});
var exams = {
		initial : function initial(){
			this.bindApprove();
			this.bindDisapprove();
			this.bindDelete();
			this.bindAddUser();
		},
		bindApprove : function bindApprove(){
			$(".approved-btn").click(function(){
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : util.getCurrentRole() + "/exam/mark-exam/" + $(this).data("id") + "/1",
					success : function(message, tst, jqXHR) {
						if (!util.checkSessionOut(jqXHR))
							return false;
						if (message.result == "success") {
							util.success("操作成功!", function(){
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
		},
		bindAddUser : function bindAddUser(){
			$("#link-user-btn").click(function(){

				var data = $("#name-add-link").val();
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : util.getCurrentRole() + "/exam/add-exam-user/" + $("#link-user-btn").data("id"),
					data : data,
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							util.success("添加成功！", function() {
								//window.location.reload();

							});
						} else {
							alert(message.result);
						}
					},
					error : function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					}
				});
				return false;
			});
		},
		bindDisapprove : function bindDisapprove(){
			$(".disapproved-btn").click(function(){

				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : util.getCurrentRole() + "/exam/mark-exam/" + $(this).data("id") + "/2",
					success : function(message, tst, jqXHR) {
						if (!util.checkSessionOut(jqXHR))
							return false;
						if (message.result == "success") {
							util.success("操作成功!", function(){
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
		},
		
		bindDelete : function bindDelete(){
			$(".delete-btn").click(function(){

				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : util.getCurrentRole() + "/exam/delete-exam/" + $(this).data("id"),
					success : function(message, tst, jqXHR) {
						if (!util.checkSessionOut(jqXHR))
							return false;
						if (message.result == "success") {
							util.success("操作成功!", function(){
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
		}
}