$(function() {

	field_list.initial();
});

var field_list = {
	initial : function initial() {
		this.bindDelete();
	},
	genrateParamOld :function genrateParamOld(){
		
		var page = 1;
		var data = new Object();
		data.page = page;
		
		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.page;

		document.location.href = document.getElementsByTagName('base')[0].href
				+ 'admin/common/field-list-' + paramurl;
	},
	
	
	bindDelete : function bindDelete(){
		$(".delete-btn").click(function(){

			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				url : "admin/common/delete-field-" + $(this).data("id"),
				success : function(message, tst, jqXHR) {
					if (!util.checkSessionOut(jqXHR))
						return false;
					if (message.result == "success") {
						util.success("删除成功", function(){
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
};