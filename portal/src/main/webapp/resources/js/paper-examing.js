$(function() {
	modal.prepare();
	examing.initial();

});

var examing = {
	initial : function initial() {
		$(window).scroll(examing.fixSideBar);
		
		this.refreshNavi();
		this.bindNaviBehavior();
		this.addNumber();
//		this.securityHandler();

		this.bindOptClick();
		this.updateSummery();
		this.bindQuestionFilter();
		this.bindfocus();
		this.bindFinishOne();
		this.initMarker();
		this.loadAnswerSheet();
		this.startTimer();
		this.bindSubmit();
	},
	
	fixSideBar : function fixSideBar() {
		var nav = $("#bk-exam-control");
		var title = $("#exampaper-title");
		var container = $("#exampaper-desc-container");
		var paper = $("#exampaper-body");
		if ($(this).scrollTop() > 147) {
			nav.addClass("fixed");
			title.addClass("exampaper-title-fixed");
			container.addClass("exampaper-desc-container-fixed");
			paper.addClass("exampaper-body-fixed");
		} else {
			nav.removeClass("fixed");
			title.removeClass("exampaper-title-fixed");
			container.removeClass("exampaper-desc-container-fixed");
			paper.removeClass("exampaper-body-fixed");
		}
	},
	/**
	 * 完成一道题触发的function
	 */
	bindFinishOne : function bindFinishOne() {
		$(".question input[type=radio]").change(function() {
			var current_index = $("li.question").index($(this).parent().parent().parent().parent());
			$($("a.question-navi-item")[current_index]).addClass("pressed");
			$(this).parent().parent().find(".question-list-item-selected").removeClass("question-list-item-selected");
			$(this).parent().addClass("question-list-item-selected");
		});

		$(".question input[type=checkbox]").change(function() {
			var current_question = $(this).parent().parent().parent().parent();
			var current_index = $("li.question").index(current_question);
			var checkedboxs = current_question.find("input[type=checkbox]:checked");
			if (checkedboxs.length > 0) {
				$($("a.question-navi-item")[current_index]).addClass("pressed");
			} else {
				$($("a.question-navi-item")[current_index]).removeClass("pressed");
			}
			
			if($(this).parent().hasClass("question-list-item-selected")){
				$(this).parent().removeClass("question-list-item-selected");
			}else{
				$(this).parent().addClass("question-list-item-selected");
			}
		});

		$(".question textarea").bind('input propertychange', function() {

			var current_index = $("li.question").index($(this).parent().parent());
			if ($(this).val() != "") {
				$($("a.question-navi-item")[current_index]).addClass("pressed");
			} else {
				$($("a.question-navi-item")[current_index]).removeClass("pressed");
			}
		});

	},

	bindNaviBehavior : function bindNaviBehavior() {

		var nav = $("#question-navi");
		var naviheight = $("#question-navi").height() - 33;
		var bottompx = "-" + naviheight + "px;";
		// alert(naviheight);

		var scrollBottomRated = $("footer").height() + 2 + 100 + naviheight;
		// alert($("footer").height() );
		// alert(scrollBottomRated);

		$("#exampaper-footer").height($("#question-navi").height());

		nav.css({
			position : 'fixed',
			bottom : '0px',
			"z-index" : '1'	
		});

		// nav.attr("style", "position : \"fixed\";bottom:-" + naviheight +
		// "px;");

		/*$(window).scroll(function() {
			var nav = $("#question-navi");
			var scrollBottom = document.body.scrollHeight - $(this).scrollTop() - $(window).height();
			if (scrollBottom > scrollBottomRated) {
				// nav.addClass("fixed-navi");
				var naviheight = $("#question-navi").height() - 33;
				// nav.attr("style", "bottom:-" + naviheight + "px;");
				if (nav.css("position") == "relative") {
					nav.css({
						position : 'fixed',
						bottom : "-" + naviheight + "px"
					});
				}
				// nav.css({
				// // position : 'fixed',
				// bottom : "-" + naviheight + "px"
				// });

			} else {
				// nav.removeClass("fixed-navi");
				// nav.attr("style", "");
				nav.css({
					position : 'relative',
					bottom : 0
				});
			}

		});*/

		$("#question-navi-controller").click(function() {
			var scrollBottom = document.body.scrollHeight - $(window).scrollTop() - $(window).height();

			var nav = $("#question-navi");
			var attr = nav.attr("style");

			if (nav.css("position") == "fixed") {
				if (nav.css("bottom") == "0px") {
					nav.css({
						bottom : "-" + naviheight + "px"
					});
				} else {
					nav.css({
						bottom : 0
					});
				}

			}

		});

	},

	securityHandler : function securityHandler() {
		// 右键禁用
		if (document.addEventListener) {
			document.addEventListener("contextmenu", function(e) {
				 e.preventDefault();
			 }, false);
		} else {
			document.attachEvent("contextmenu", function(e) {
				 e.preventDefault();
			 });
		}

		$(window).bind('beforeunload', function() {
			return "考试正在进行中...";
		});
	},

	/**
	 * 刷新试题导航
	 */
	refreshNavi : function refreshNavi() {
		$("#exam-control #question-navi").empty();
		var questions = $("li.question");

		questions.each(function(index) {
			var btnhtml = "<a class=\"question-navi-item\">" + (index + 1) + "</a>";
			$("#question-navi-content").append(btnhtml);
		});
	},

	/**
	 * 更新题目简介信息
	 */
	updateSummery : function updateSummery() {
		if ($(".question").length === 0) {
			return false;
		}
		var questiontypes = this.questiontypes;
		var summery = "";
		for (var i = 0; i < questiontypes.length; i++) {
			var question_sum_q = $("." + questiontypes[i].code).length;
			if (question_sum_q == 0) {
				continue;
			} else {
				summery = summery + "<span class=\"exampaper-filter-item efi-" + questiontypes[i].code + "\">" 
				+ questiontypes[i].name + "[<span class=\"efi-fno\">0</span>/<span class=\"efi-tno\">" 
				+ $("." + questiontypes[i].code).length + "</span>]<span class=\"efi-qcode\" style=\"display:none;\">" 
				+ questiontypes[i].code + "</span></span>";
			}
		}
		// summery = summery.substring(0, summery.length - 2);
		$("#exampaper-desc").html(summery);
		
		examing.doQuestionFilt($($(".exampaper-filter-item")[0]).find(".efi-qcode").text());
	},

	questiontypes : new Array({
		"name" : "单选题",
		"code" : "qt-singlechoice"
	}, {
		"name" : "多选题",
		"code" : "qt-multiplechoice"
	}, {
		"name" : "判断题",
		"code" : "qt-trueorfalse"
	}, {
		"name" : "填空题",
		"code" : "qt-fillblank"
	}, {
		"name" : "简答题",
		"code" : "qt-shortanswer"
	}, {
		"name" : "论述题",
		"code" : "qt-essay"
	}, {
		"name" : "分析题",
		"code" : "qt-analytical"
	}),
	/**
	 * 绑定考题focus事件(点击考题导航)
	 */
	bindfocus : function bindfocus() {
		$("#question-navi").delegate("a.question-navi-item ", "click", function() {
			var clickindex = $("a.question-navi-item").index(this);
			var questions = $("li.question");
			var targetQuestion = questions[clickindex];
			
			var targetQuestionType = $(questions[clickindex]).find(".question-type").text();
			
			examing.doQuestionFilt("qt-" + targetQuestionType);
			
			examing.scrollToElement($(targetQuestion),0,-100);
		});
	},

	scrollToElement : function scrollToElement(selector, time, verticalOffset) {
		time = typeof (time) != 'undefined' ? time : 500;
		verticalOffset = typeof (verticalOffset) != 'undefined' ? verticalOffset : 0;
		element = $(selector);
		offset = element.offset();
		offsetTop = offset.top + verticalOffset;
		$('html, body').animate({
			scrollTop : offsetTop
		}, time);
	},



	/**
	 * 开始倒计时
	 */
	startTimer : function startTimer() {
		var timestamp = parseInt($("#exam-timestamp").text());
		var int = setInterval(function() {
			$("#exam-timestamp").text(timestamp);
			$("#exam-clock").text(examing.toHHMMSS(timestamp));
			if(timestamp < 600){
				var exam_clock = $("#question-time");
				exam_clock.removeClass("question-time-normal");
				exam_clock.addClass("question-time-warning");
			}
			var period = timestamp % 60;
	//		console.log("period :" + period);
			if(period == 0)
				examing.saveAnswerSheet();
			timestamp-- || examing.examTimeOut(int); 
		}, 1000);
	},
	
	/**
	 * 考试时间到
	 * @param int
	 */
	examTimeOut : function examTimeOut (int){
		clearInterval(int); 
		examing.finishExam();
	},

	/**
	 * 时间formater
	 *
	 * @param timestamp
	 * @returns {String}
	 */
	toHHMMSS : function toHHMMSS(timestamp) {
		var sec_num = parseInt(timestamp, 10);
		var hours = Math.floor(sec_num / 3600);
		var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
		var seconds = sec_num - (hours * 3600) - (minutes * 60);

		if (hours < 10) {
			hours = "0" + hours;
		}
		if (minutes < 10) {
			minutes = "0" + minutes;
		}
		if (seconds < 10) {
			seconds = "0" + seconds;
		}
		var time = hours + ':' + minutes + ':' + seconds;
		return time;
	},

	
	/**
	 * 对题目重新编号排序
	 */
	addNumber : function addNumber() {
		var questions = $("li.question");

		questions.each(function(index) {
			$(this).find(".question-no").text(index + 1 + ".");
		});
	},

	/**
	 * 切换考题类型事件
	 */
	bindQuestionFilter : function bindQuestionFilter() {
		// $(".exampaper-filter-item").bi
// 		
		// $("span.efi-selected").find(".efi-qcode").text();
		$("#exampaper-desc").delegate("span.exampaper-filter-item", "click", function() {
			var qtype = $(this).find(".efi-qcode").text();
			// var questions = $("li.question");
			// questions.hide();
			// $("#exampaper-body ." + qtype).show();
			// $(".exampaper-filter-item").removeClass("efi-selected");
			// $(this).addClass("efi-selected");
			examing.doQuestionFilt(qtype);
		});
	},
	
	
	/**
	 *切换到指定的题型 
	 */
	doQuestionFilt : function doQuestionFilt(questiontype) {
		
		if($("#exampaper-desc .efi-" + questiontype).hasClass("efi-selected")){
			return false;
		}else{
			var questions = $("li.question");
			questions.hide();
			$("#exampaper-body ." + questiontype).show();
			
			$(".exampaper-filter-item").removeClass("efi-selected");
			$("#exampaper-desc .efi-" + questiontype).addClass("efi-selected");
		}
		
		
	},

	bindSubmit : function bindSubmit() {
		$("#question-submit button").click(function() {
			if (confirm("确认交卷吗？")) {
				examing.finishExam();
			}
		});
	},

	finishExam : function finishExam() {
		modal.showProgress();
		var answerSheetItems = examing.genrateAnswerSheet();
		var data = new Object();
		var examHistroyId = $("#hist-id").val();
		data.examHistroyId = examHistroyId;
		data.answerSheetItems = answerSheetItems;
		var timeStr = $("#exam-clock").text();
		var time = timeStr.split(":");
		var hours = parseInt(time[0]);
		var minutes = parseInt(time[1]);
		var seconds = parseInt(time[2]);
		data.duration = hours * 3600 + minutes * 60 + seconds;
		data.examId=$("#exam-id").val();
		data.examPaperId=$("#paper-id").val();
		//data.startTime=$("#start-time").val();
		
		$("#question-submit button").attr("disabled", "disabled");
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "student/exam-submit",
			data : JSON.stringify(data)
		});

		request.done(function(message, tst, jqXHR) {
			if (!util.checkSessionOut(jqXHR))
				return false;
			if (message.result == "success") {
				$(window).unbind('beforeunload');
				util.success("交卷成功！", function() {
					window.location.replace(document.getElementsByTagName('base')[0].href + 'student/finished-submit');

				});
			} else {
				util.error(message.result);
				$("#question-submit button").removeAttr("disabled");
			}
			modal.hideProgress();
		});
		request.fail(function(jqXHR, textStatus) {
			alert("系统繁忙请稍后尝试");
			$("#question-submit button").removeAttr("disabled");
			modal.hideProgress();
		});
	},

	genrateAnswerSheet : function genrateAnswerSheet() {
		//		var as = new Array();
		/*var as = {};*/
		var answerSheetItems = new Array();
		var questions = $(".question");

		for (var i = 0; i < questions.length; i++) {
			var answerSheetItem = new Object();

			if ($(questions[i]).hasClass("qt-singlechoice")) {
				var radio_checked = $(questions[i]).find("input[type=radio]:checked");
				var radio_all = $(questions[i]).find("input[type=radio]");
				if (radio_checked.length == 0) {
					answerSheetItem.answer = "";
				} else {
					var current_index = $(radio_all).index(radio_checked);
					answerSheetItem.answer = String.fromCharCode(65 + current_index);
				}
				answerSheetItem.questionTypeId = 1;
			} else if ($(questions[i]).hasClass("qt-multiplechoice")) {

				var checkbox_checked = $(questions[i]).find("input[type=checkbox]:checked");
				var checkbox_all = $(questions[i]).find("input[type=checkbox]");
				if (checkbox_checked.length == 0) {
					answerSheetItem.answer = "";
				} else {
					var tm_answer = "";
					for (var l = 0; l < checkbox_checked.length; l++) {
						var current_index = $(checkbox_all).index($(checkbox_checked[l]));
						tm_answer = tm_answer + String.fromCharCode(65 + current_index);
					}
					answerSheetItem.answer = tm_answer;
				}
				answerSheetItem.questionTypeId = 2;
			} else if ($(questions[i]).hasClass("qt-trueorfalse")) {

				var radio_checked = $(questions[i]).find("input[type=radio]:checked");
				var radio_all = $(questions[i]).find("input[type=radio]");
				if (radio_checked.length == 0) {
					answerSheetItem.answer = "";
				} else {
					var current_index = $(radio_all).index(radio_checked);
					answerSheetItem.answer = (current_index == 0) ? "T" : "F";
				}
				answerSheetItem.questionTypeId = 3;
			} else if ($(questions[i]).hasClass("qt-fillblank")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 4;
			} else if ($(questions[i]).hasClass("qt-shortanswer")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 5;
			} else if ($(questions[i]).hasClass("qt-essay")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 6;
			} else if ($(questions[i]).hasClass("qt-analytical")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 7;
			}
			answerSheetItem.point = 0;
			answerSheetItem.questionId = $(questions[i]).find(".question-id").text();
/*			var tmpkey = $(questions[i]).find(".question-id").text();
			var tmpvalue = answerSheetItem;

			as[tmpkey] = tmpvalue;*/
			
			answerSheetItems.push(answerSheetItem);
		}
		return answerSheetItems;
	},
	bindOptClick : function bindOptClick(){
		$("input[type=radio]").click(function(event){
			
			event.stopPropagation();
		});
		$("input[type=checkbox]").click(function(event){
			
			event.stopPropagation();
		});
		$(".question-list-item").click(function(){
			if($(this).find("input[type=radio]").length>0){
				$(this).find("input").click();
			}else if($(this).find("input[type=checkbox]").length>0){
				$(this).find("input").click();
			}
			
		/*	if($(this).find("input").prop("checked")){
				$(this).find("input").prop("checked", false);
			}else{
				$(this).find("input").prop("checked", true);
			}*/
			
		});
		
	},
	loadAnswerSheet : function loadAnswerSheet(){
		var answerSheet = eval('(' + localStorage.answerSheet + ')');
		console.log(eval(answerSheet));
		this.mergeQuestionAnswer(answerSheet);
	},
	saveAnswerSheet : function saveAnswerSheet(){
		console.log("answerSheet saving...");

		
		var answerSheetItems = examing.genrateAnswerSheet();
		var data = new Object();
		var examHistroyId = $("#hist-id").val();
		data.examHistroyId = examHistroyId;
		data.answerSheetItems = answerSheetItems;
		var timeStr = $("#exam-clock").text();
		var time = timeStr.split(":");
		var hours = parseInt(time[0]);
		var minutes = parseInt(time[1]);
		var seconds = parseInt(time[2]);
		data.duration = hours * 3600 + minutes * 60 + seconds;
		data.examId=$("#exam-id").val();
		data.examPaperId=$("#paper-id").val();
		
		localStorage.answerSheet = JSON.stringify(data);
		
	},
	
	mergeQuestionAnswer : function mergeQuestionAnswer(answerSheet){
		if (answerSheet==null||answerSheet.examHistroyId != $("#hist-id").val())
			return false;
		var questions = $("li.question");
		/*	var questionMap = new Object();
		
		for(var i=0;i<questions.length; i++){
			var key = $(questions[i]).find(".question-id").text();
			questionMap[key] = questions[i];
		}
		*/
		
		$("#exam-timestamp").text(answerSheet.duration);
//		var timestamp = parseInt($("#exam-timestamp").text());
		
		
		var list = answerSheet.answerSheetItems;
		var quetion_navis = $("a.question-navi-item");
		
		if(list.length != questions.length){
			return false;
		}
		for(var i = 0 ; i < list.length; i++){
			if(list[i].questionTypeId == 1){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				var tmp_char =tmp_answer.charAt(0);
				$(questions[i]).find("input[value='"+tmp_char+ "']").prop('checked', true);
				$(quetion_navis[i]).addClass("pressed");
				$(questions[i]).find("input[value='"+tmp_char+ "']").parent().addClass("question-list-item-selected");
			}else if(list[i].questionTypeId == 2){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				var answer_length = tmp_answer.length;
				for(var l = 0 ; l < answer_length; l++){
					var tmp_char =tmp_answer.charAt(l);
					$(questions[i]).find("input[value='"+tmp_char+ "']").prop('checked', true);
					$(questions[i]).find("input[value='"+tmp_char+ "']").parent().addClass("question-list-item-selected");
				}
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 3){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				var tmp_char =tmp_answer.charAt(0);
				$(questions[i]).find("input[value='"+tmp_char+ "']").prop('checked', true);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 4){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 5){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 6){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 7){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}
		}
	},
	initMarker : function initMarker(){
		$(".question-title").delegate(".question-mark", "click", function() {
			if($(this).find(".fa-bookmark").length > 0){
				$(this).html("<i class=\"fa fa-bookmark-o\">");
				
				var current_index = $("li.question").index($(this).parent().parent());
				$($("a.question-navi-item")[current_index]).removeClass("question-navi-item-marked");
				
			}else{
				$(this).html("<i class=\"fa fa-bookmark\">");
				var current_index = $("li.question").index($(this).parent().parent());
				$($("a.question-navi-item")[current_index]).addClass("question-navi-item-marked");
			}
			
			
		});

		var markhtml = "<span class=\"question-mark\" title=\"Marked as uncertain\"><i class=\"fa fa-bookmark-o\"></span>";
		$(".question-title").append(markhtml);
		
	
	}
};

var modal = {
	prepare : function prepare() {
		$(".content").append("<div id=\"loading-progress\" style=\"display:none;\"><div id=\"loading-content\"> <h2>正在提交您的答案</h2><img class=\"loading-gif\" src=\"resources/images/loading.gif\"/><div> </div>");

	},
	showProgress : function showProgress() {
		$("#loading-progress").show();
	},

	hideProgress : function hideProgress() {
		$("#loading-progress").hide();
	}
};
