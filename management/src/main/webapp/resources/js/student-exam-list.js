$(function() {

	student_list.initial();
});

var student_list = {
	initial : function initial() {
		this.searchPaper();
	},
	searchPaper : function searchPaper(){
		$("#btn-search").click(function(){
			var params = "";
			if(student_list.getQueryString("limit") != null)
				params += "limit=" + student_list.getQueryString("limit");
			if(student_list.getQueryString("order") != null)
				params += "&order=" + student_list.getQueryString("order");
			var searchStr = $("#txt-search").val();
			
			if(searchStr != ""){
				var paramStr = searchStr + "&" + params;
				document.location.href = document.getElementsByTagName('base')[0].href
				+ util.getCurrentRole() + "/exam/exam-student-list/" + $("#exam-id-hidden").val() + "?" + paramStr;
			}
			else
				document.location.href = document.getElementsByTagName('base')[0].href
				+ util.getCurrentRole() +"/exam/exam-student-list/" + $("#exam-id-hidden").val() + "?" + params;
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
};



















