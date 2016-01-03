$(function(){
	exam_users.initial();
});
var exam_users = {
		initial : function initial(){
			this.bindApprove();
			this.bindDisapprove();
			this.searchPaper();
			this.bindAddGroupUser();
			this.bindAddUser();
		},
		bindAddGroupUser : function bindAddGroupUser(){
			$("#link-user-group-btn").click(function(){
				
				var groupIds = new Array();
				var checkedGroup = $("#link-user-group-form :checked");
				checkedGroup.each(function(i,value){
					groupIds.push($(this).val());
				});
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : util.getCurrentRole() + "/exam/add-exam-group/" + $("#exam-id-hidden").val(),
					data : JSON.stringify(groupIds),
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
					url : util.getCurrentRole() + "/exam/add-exam-user/" + $("#exam-id-hidden").val(),
					data : data,
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							util.success("添加成功！", function() {
								window.location.reload();

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
		bindApprove : function bindApprove(){
			$(".approved-btn").click(function(){

				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : util.getCurrentRole() + "/exam/mark-hist/" + $(this).data("id") + "/1",
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
		
		bindDisapprove : function bindDisapprove(){
			$(".disapproved-btn").click(function(){

				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : util.getCurrentRole() + "/exam/delete-hist/" + $(this).data("id"),
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
		searchPaper : function searchPaper(){
			$("#btn-search").click(function(){
				var params = "";
				if(exam_users.getQueryString("limit") != null)
					params += "limit=" + exam_users.getQueryString("limit");
				if(exam_users.getQueryString("order") != null)
					params += "&order=" + exam_users.getQueryString("order");
				var searchStr = $("#txt-search").val();
				
				if(searchStr != ""){
					var paramStr = "searchStr=" + searchStr + (params == "" ? "" : "&" + params);
					document.location.href = document.getElementsByTagName('base')[0].href
					+ util.getCurrentRole() + "/exam/exam-student-list/" + $("#exam-id-hidden").val() + "?" + paramStr;
				}
				else
					document.location.href = document.getElementsByTagName('base')[0].href
					+ util.getCurrentRole() + "/exam/exam-student-list/" + $("#exam-id-hidden").val() + "?" + params;
			});
		},
		
		genrateParamOld :function genrateParamOld(){
			var pagetype = $("#question-filter-pagetype dd .label").data("id");
			var page = 1;
			
			var data = new Object();
			data.pagetype= pagetype;
			data.page = page;
			
			return data;
		},
		getQueryString : function getQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r != null)
		    	 return unescape(r[2]); 
		     return null;
		}
}