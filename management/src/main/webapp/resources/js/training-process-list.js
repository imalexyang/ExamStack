$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
		this.searchHist();
		/*this.bindDelete();*/
	},
	
	bindChangeSearchParam : function bindChangeSearchParam(){
		
		$(".pagination li a").click(function(){
			var pageId = $(this).data("id");
			if(pageId==null||pageId=="")return false;
			var genrateParamOld = question_list.genrateParamOld();
			genrateParamOld.page = pageId;
			question_list.redirectUrl(genrateParamOld);
			
		});
	},
	
	genrateParamOld :function genrateParamOld(){
		
		var page = 1;
		var data = new Object();
		data.page = page;
		data.searchStr = $("#txt-search").val();
		
		return data;
	},
	
	searchHist : function searchHist(){
		$("#btn-search").click(function(){
			var searchStr = $("#txt-search").val();
			
			if(searchStr != ""){
				var paramStr = "searchStr=" + searchStr;
				document.location.href = document.getElementsByTagName('base')[0].href
				+ util.getCurrentRole() + "/training/student-training-history/" + $("#training-id-hidden").val() + "?" + paramStr;
			}
			else
				document.location.href = document.getElementsByTagName('base')[0].href
				+ util.getCurrentRole() + "/training/student-training-history/" + $("#training-id-hidden").val() + "?" + params;
		});
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.page;
		var trainingId = $("#training-id-hidden").val();
		document.location.href = document.getElementsByTagName('base')[0].href
				+ util.getCurrentRole() + '/training/student-training-history/' + trainingId + '?page=' + paramurl + "&searchStr=" + newparam.searchStr;
	},
	getQueryString : function getQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r != null)
	    	 return unescape(r[2]); 
	     return null;
	}
	
};