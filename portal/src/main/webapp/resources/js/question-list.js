$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
	},
	
	bindChangeSearchParam : function bindChangeSearchParam(){
		$("#question-filter dl dd span").click(function(){
			if($(this).hasClass("label"))return false;
			
			
			var genrateParamOld = question_list.genrateParamOld();
			
			if($(this).parent().parent().attr("id") == "question-filter-field" ){
				genrateParamOld.field = $(this).data("id");
				question_list.redirectUrl(genrateParamOld);
				
			}else if($(this).parent().parent().attr("id") == "question-filter-knowledge" ){
				genrateParamOld.knowledge = $(this).data("id");
				question_list.redirectUrl(genrateParamOld);
				
			}else{
				genrateParamOld.questiontype = $(this).data("id");
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
		var knowledge = $("#question-filter-knowledge dd .label").data("id");
		var questiontype = $("#question-filter-qt dd .label").data("id");
		var searchParam = 0;
		var page = 1;
		
		var data = new Object();
		data.field = field;
		data.knowledge = knowledge==null?0:knowledge;
		data.questiontype= questiontype;
		data.searchParam = searchParam;
		data.page = page;
		
		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.field;
		paramurl = paramurl + "-" + newparam.knowledge;
		paramurl = paramurl + "-" + newparam.questiontype;
		paramurl = paramurl + "-" + newparam.searchParam;
		paramurl = paramurl + "-" + newparam.page;
		paramurl = paramurl + ".html";

		document.location.href = document.getElementsByTagName('base')[0].href
				+ 'admin/questionfilter-' + paramurl;
	}
};