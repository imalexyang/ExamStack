$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
		this.bindDelete();
	},
	
	bindChangeSearchParam : function bindChangeSearchParam(){
		$("#question-filter dl dd span").click(function(){
			if($(this).hasClass("label"))return false;
			var genrateParamOld = question_list.genrateParamOld();
			if($(this).parent().parent().attr("id") == "question-filter-field" ){
				genrateParamOld.field = $(this).data("id");
				question_list.redirectUrl(genrateParamOld);
				
			}
		});
		
		$(".pagination li a").click(function(){
			var pageId = $(this).data("id");
			if(pageId==null||pageId=="")return false;
			var genrateParamOld = question_list.genrateParamOld();
			genrateParamOld.page = pageId;
			question_list.redirectUrl(genrateParamOld);
			
		});
	},
	
	genrateParamOld :function genrateParamOld(){
		
		var field = $("#question-filter-field dd .label").data("id");
		var page = 1;
		var data = new Object();
		data.field = field;
		data.page = page;
		
		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.field;
		paramurl = paramurl + "-" + newparam.page;

		document.location.href = document.getElementsByTagName('base')[0].href
				+ 'admin/point-list-' + paramurl;
	},
	
	
	bindDelete : function bindDelete(){
		$(".delete-btn").click(function(){

			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				url : "admin/delete-point-" + $(this).data("id"),
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