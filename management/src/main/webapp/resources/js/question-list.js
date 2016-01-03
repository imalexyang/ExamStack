$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
		this.bindAddPoint();
		this.bindUpdate();
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
				
			}else if($(this).parent().parent().attr("id") == "question-filter-tag" ){
				genrateParamOld.tag = $(this).data("id");
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
		
		
		$("#btn-search").click(function(){
			var searchParam = $("#txt-search").val();
			if(searchParam==""||searchParam==null)
				searchParam = 0;
			var genrateParamOld = question_list.genrateParamOld();
			genrateParamOld.searchParam = searchParam;
			question_list.redirectUrl(genrateParamOld);
		});
	},
	
	genrateParamOld :function genrateParamOld(){
		
		var field = $("#question-filter-field dd .label").data("id");
		var knowledge = $("#question-filter-knowledge dd .label").data("id");
		var questiontype = $("#question-filter-qt dd .label").data("id");
		var tag = $("#question-filter-tag dd .label").data("id");
		var searchParam = $("#txt-search").val();
		if(searchParam==""||searchParam==null)
			searchParam = 0;
		var page = 1;
		
		var data = new Object();
		data.field = field;
		data.knowledge = knowledge==null?0:knowledge;
		data.questiontype= questiontype;
		data.tag= tag;
		data.searchParam = searchParam;
		data.page = page;
		
		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.field;
		paramurl = paramurl + "-" + newparam.knowledge;
		paramurl = paramurl + "-" + newparam.questiontype;
		paramurl = paramurl + "-" + newparam.tag;
		paramurl = paramurl + "-" + newparam.searchParam;
		paramurl = paramurl + "-" + newparam.page;
		paramurl = paramurl + ".html";

		document.location.href = document.getElementsByTagName('base')[0].href
				+ util.getCurrentRole() + '/question/question-list/filter-' + paramurl;
	},
	
	

	/**
	 *
	 */
	bindAddPoint : function bindAddPoint() {
		$("#add-point-btn").click(function() {
			var field = $("#field-select > option:selected");
			var point = $("#point-from-select > option:selected");
			if (field.length == 0 || point.length == 0) {
				util.error("请选择需要添加的知识点");
				return false;
			}

			var html = "<option value=\"" + point.attr("value") + "\">" + field.text() + " > " + point.text() + "</option>";
			var p = point.attr("value");
			if (!question_list.checkPointDuplicate(p)) {
				util.error("不能重复添加");
				return false;
			}

			$("#point-to-select").append(html);
			return false;
		});

		$("#del-point-btn").click(function() {
			$("#point-to-select > option:selected").remove();
			return false;
		});

		$("#remove-all-point-btn").click(function() {
			$("#point-to-select").empty();
			return false;
		});
	},
	
	checkPointDuplicate : function checkPointDuplicate(p) {
		var points = $("#point-to-select option");
		for (var i = 0; i < points.length; i++) {
			var point = $(points[i]).attr("value");
			if (point == p)
				return false;
		}

		return true;
	},
	
	
	checkReference : function checkReference() {
		var content = $(".form-question-reference input").val();
		if (content.length > 50) {
			$(".form-question-reference input").focus();
			$(".form-question-reference input").addClass("has-error");
			$(".form-question-reference .form-message").text("内容过长，请保持在50个字符以内");
			return false;
		} else
			return true;
	},
	
	checkExamingPoint : function checkExamingPoint() {
		var content = $(".form-question-examingpoint input").val();
		if (content.length > 50) {
			$(".form-question-examingpoint input").focus();
			$(".form-question-examingpoint input").addClass("has-error");
			$(".form-question-examingpoint .form-message").text("内容过长，请保持在50个字符以内");
			return false;
		} else
			return true;
	},
	checkKeyword : function checkKeyword() {
		var content = $(".form-question-keyword input").val();
		if (content.length > 50) {
			$(".form-question-keyword input").focus();
			$(".form-question-keyword input").addClass("has-error");
			$(".form-question-keyword .form-message").text("内容过长，请保持在50个字符以内");
			return false;
		} else
			return true;
	},

	checkAnalysis : function checkAnalysis() {
		var content = $(".form-question-analysis textarea").val();
		if (content.length > 500) {
			$(".form-question-analysis textarea").focus();
			$(".form-question-analysis textarea").addClass("has-error");
			$(".form-question-analysis .form-message").text("内容过长，请保持在500个字符以内");
			return false;
		} else
			return true;
	},

	
	
	
	composeEntity : function composeEntity() {
		var data = new Object();
		var tags = new Array();

		$(".q-label-item").each(function() {
			var tag = new Object();
			tag.tagId = $(this).data("id");
			tag.questionId = $("#add-update-questionid").text();
			tags.push(tag);
		});
		
		data.tags = tags;
		var question_entity = new Object();
		
		var pointList = new Array();
		var pointOpts = $("#point-to-select option");
		for (var i = 0; i < pointOpts.length; i++) {
			pointList.push($(pointOpts[i]).attr("value"));
		}

		question_entity.pointList = pointList;
		
		question_entity.id = $("#add-update-questionid").text();
		question_entity.analysis = $(".form-question-analysis textarea").val();
		question_entity.referenceName = $(".form-question-reference input").val();
		question_entity.examingPoint = $(".form-question-examingpoint input").val();
		question_entity.keyword = $(".form-question-keyword input").val();
		
		data.question = question_entity;
		return data;
	},
	
	bindUpdate : function bindUpdate(){
		$("#update-exampaper-btn").click(function() {

			if ($("#kn-selected option").length == 0) {
				util.error("请选择知识类");
				return false;
			}
			var data = question_list.composeEntity();
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "POST",
				url : "secure/question/question-update",
				data : JSON.stringify(data),
				success : function(message, tst, jqXHR) {
					if (!util.checkSessionOut(jqXHR))
						return false;
					if (message.result == "success") {
						util.success("修改成功", function() {
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