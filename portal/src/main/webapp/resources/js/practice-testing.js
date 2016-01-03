$(function() {
	modal.prepare();
	examing.initial();

});

var examing = {
	initial : function initial() {
		this.refreshNavi();
		this.bindNaviBehavior();
		this.addNumber();
		this.securityHandler();

		this.updateSummery();
		this.bindQuestionFilter();
		this.bindfocus();
		this.bindFinishOne();
		this.startTimer();

		this.bindSubmit();
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
//		 右键禁用
		
		if (document.addEventListener) {
			document.addEventListener("contextmenu", function(e) {
				 e.preventDefault();
			 }, false);
		} else {
			document.attachEvent("contextmenu", function(e) {
				 e.preventDefault();
			 });
		}
		
//		 document.addEventListener('contextmenu', function(e) {
//		 e.preventDefault();
//		 }, false);

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
			
			examing.scrollToElement($(targetQuestion));
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
	 * 完成一道题触发的function
	 */
	bindFinishOne : function bindFinishOne() {
		$(".question input[type=radio]").change(function() {
			var current_index = $("li.question").index($(this).parent().parent());
			$($("a.question-navi-item")[current_index]).addClass("pressed");
		});

		$(".question input[type=checkbox]").change(function() {
			var current_question = $(this).parent().parent();
			var current_index = $("li.question").index(current_question);
			var checkedboxs = current_question.find("input[type=checkbox]:checked");
			if (checkedboxs.length > 0) {
				$($("a.question-navi-item")[current_index]).addClass("pressed");
			} else {
				$($("a.question-navi-item")[current_index]).removeClass("pressed");
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

	/**
	 * 开始倒计时
	 */
	startTimer : function startTimer() {
		var timestamp = parseInt($("#exam-timestamp").text());
		var int = setInterval(function() {
			$("#exam-timestamp").text(timestamp);
			$("#exam-clock").text(examing.toHHMMSS(timestamp));

			timestamp++;
		}, 1000);
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
	 * 完成一道题触发的function
	 */
	bindFinishOne : function bindFinishOne() {
		$(".question input[type=radio]").change(function() {
			var current_index = $("li.question").index($(this).parent().parent());
			$($("a.question-navi-item")[current_index]).addClass("pressed");
		});

		$(".question input[type=checkbox]").change(function() {
			var current_question = $(this).parent().parent();
			var current_index = $("li.question").index(current_question);
			var checkedboxs = current_question.find("input[type=checkbox]:checked");
			if (checkedboxs.length > 0) {
				$($("a.question-navi-item")[current_index]).addClass("pressed");
			} else {
				$($("a.question-navi-item")[current_index]).removeClass("pressed");
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
		var answerSheet = examing.genrateAnswerSheet();
		var data = new Object();
		var exam_history_id = $("#current-list-id").val();
		data.exam_history_id = exam_history_id;
		data.as = answerSheet;
		$("#question-submit button").attr("disabled", "disabled");
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "student/practice-finished",
			data : JSON.stringify(data)
		});

		request.done(function(message, tst, jqXHR) {
			if (!util.checkSessionOut(jqXHR))
				return false;
			if (message.result == "success") {
				$(window).unbind('beforeunload');
				util.success("交卷成功！", function() {
					window.location.replace(document.getElementsByTagName('base')[0].href + 'student/finish-exam');
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
		var as = {};
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
				answerSheetItem.question_type_id = 1;
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
				answerSheetItem.question_type_id = 2;
			} else if ($(questions[i]).hasClass("qt-trueorfalse")) {

				var radio_checked = $(questions[i]).find("input[type=radio]:checked");
				var radio_all = $(questions[i]).find("input[type=radio]");
				if (radio_checked.length == 0) {
					answerSheetItem.answer = "";
				} else {
					var current_index = $(radio_all).index(radio_checked);
					answerSheetItem.answer = (current_index == 0) ? "T" : "F";
				}
				answerSheetItem.question_type_id = 3;
			} else if ($(questions[i]).hasClass("qt-fillblank")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.question_type_id = 4;
			} else if ($(questions[i]).hasClass("qt-shortanswer")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.question_type_id = 5;
			} else if ($(questions[i]).hasClass("qt-essay")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.question_type_id = 6;
			} else if ($(questions[i]).hasClass("qt-analytical")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.question_type_id = 7;
			}
			answerSheetItem.point = 0;

			var tmpkey = $(questions[i]).find(".question-id").text();
			var tmpvalue = answerSheetItem;

			as[tmpkey] = tmpvalue;
		}
		return as;
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
