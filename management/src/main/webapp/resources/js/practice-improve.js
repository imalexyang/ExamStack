$(function() {
	modal.prepare();
	examing.initial();

});

var examing = {
	initial : function initial() {
		this.initialModel();
		this.refreshNavi();
		this.bindNaviBehavior();
		this.addNumber();
		this.bindOptClick();
//		this.securityHandler();
//		this.updateSummery();
		this.bindfocus();
//		this.bindFinishOne();
//		this.startTimer();
		this.bindSwitchQuestion();
		this.bindSubmitQuestion();
	},
	
	examModel : examModel = true,
	
	initialModel : function initialModel(){
		$(".answer-desc").hide();
		$(".question-point-content").hide();
		
		$("#switch-model-btn").click(function(){
			if($(this).data("exam") == true){
				$(this).data("exam",false);
				examing.examModel = false;
				$(this).removeClass("btn-success");
				$(this).addClass("btn-info");
				$(this).text("背题模式");
//				$("#bk-conent-comment").show();
				$(".answer-desc").show();
				
				$(".question-body input").attr("disabled","disabled");
				
				$("#submit-q-btn").hide();
				
			}else{
				$(this).data("exam",true);
				examing.examModel = true;
				$(this).removeClass("btn-info");
				$(this).addClass("btn-success");
				$(this).text("答题模式");
				
//				$("#bk-conent-comment").hide();
				$(".answer-desc").hide();
				$(".qt-finished .answer-desc").show();
				
				$(".question-body input").removeAttr("disabled");
				$(".qt-finished .question-body input").attr("disabled","disabled");
				$("#submit-q-btn").show();
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
			bottom : "-" + naviheight + "px",
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
			
			questions.hide();
			$(questions[clickindex]).show();
			
			$(".qni-selected").removeClass("qni-selected");
			$($("a.question-navi-item")[clickindex]).addClass("qni-selected");
			
		});
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
	
	bindSubmitQuestion : function bindSubmitQuestion(){
		$("#submit-q-btn").click(function(){
			var thisquestion  = $(".question:visible");
			
			if(thisquestion.hasClass("qt-finished")){
				util.error("此题已经做完");
				return false;
			}
			
			var answer = examing.getAnswerValue();
			
			if(answer == "" || answer== null){
				util.error("只有完成该题后才能提交答案！");
				return false;
			}else{
				if(answer == $(thisquestion).find(".answer_value").text()){
					$(thisquestion).find(".answer-desc-summary").addClass("answer-desc-success");
					$("#question-navi-content .qni-selected").addClass("qni-success");
					
				}else{
					$(thisquestion).find(".answer-desc-summary").addClass("answer-desc-error");
					$("#question-navi-content .qni-selected").addClass("qni-error");
				}
				
			}
			
			thisquestion.addClass("qt-finished");
			thisquestion.find(".question-body input").attr("disabled","disabled");
			thisquestion.find(".answer-desc").show();
			
			//TODO ajax request
			var data = new Object();
			data.myAnswer = answer;
			data.questionId = thisquestion.find(".question-id").text();
			data.questionTypeId = thisquestion.find(".question-type-id").text();
			data.pointId = thisquestion.find(".knowledge-point-id").text();
			data.answer = thisquestion.find(".answer_value").text();
			
//			modal.showProgress();
			$(this).attr("disabled","disabled");
			var request = $.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "POST",
				async:false,
				url : "student/practice-improve",
				data : JSON.stringify(data)
			});

			request.done(function(message, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				if (message.result == "success") {
					$(window).unbind('beforeunload');
//					util.success("答案提交成功！");
					var thisquestion  = $(".question:visible");
					if(thisquestion.find(".answer-desc-summary").hasClass("answer-desc-success")){
						$("#next-q-btn").click();
					}
				} else {
					util.error(message.result);
				}
//				modal.hideProgress();
				$("#submit-q-btn").removeAttr("disabled");
			});
			request.fail(function(jqXHR, textStatus) {
				util.error("系统繁忙请稍后尝试");
//				modal.hideProgress();
				$("#submit-q-btn").removeAttr("disabled");
			});
		});
		
		
	},
	getAnswerValue : function(){
		var thisquestion  = $(".question:visible");
		
		var answer;
		
		if(thisquestion.hasClass("qt-singlechoice")){
			var radio_checked = $(thisquestion).find("input[type=radio]:checked");
			var radio_all = $(thisquestion).find("input[type=radio]");
			if(radio_checked.length == 0){
				answer = "";
			}else{
				var current_index = $(radio_all).index(radio_checked);
				answer = String.fromCharCode(65 + current_index);
			}
			
		}else 	if( $(thisquestion).hasClass("qt-multiplechoice")){
			
			var checkbox_checked = $(thisquestion).find("input[type=checkbox]:checked");
			var checkbox_all = $(thisquestion).find("input[type=checkbox]");
			if(checkbox_checked.length == 0){
				answer = "";
			}else{
				var tm_answer = "";
				for(var l = 0 ; l < checkbox_checked.length; l++){
					var current_index = $(checkbox_all).index($(checkbox_checked[l]));
					tm_answer = tm_answer + String.fromCharCode(65 + current_index);
				}
				answer = tm_answer;
			}
		} else 	if( $(thisquestion).hasClass("qt-trueorfalse")){
			
			var radio_checked = $(thisquestion).find("input[type=radio]:checked");
			var radio_all = $(thisquestion).find("input[type=radio]");
			if(radio_checked.length == 0){
				answer = "";
			}else{
				var current_index = $(radio_all).index(radio_checked);
				answer = (current_index==0)?"对":"错";
			}
		}else{
			answer = $(thisquestion).find("textarea").val();
		}
		return answer;
	},
	
	disableInput : function disableInput(questionIndex){
		
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


	
	
	bindSwitchQuestion : function bindSwitchQuestion(){
		$(".question").hide();
		$($(".question")[0]).show();
		
		$($("a.question-navi-item")[0]).addClass("qni-selected");
			
		$("#previous-q-btn").click(function(){
//			examing.saveAnswerSheet();
			var allQuestion = $(".question");
			var thisquestion  = $(".question:visible");
			var thisindex = $(".question").index(thisquestion);
			if(thisindex == 0){
				return false;
			}else{
				thisquestion.hide();
				$(allQuestion[thisindex - 1]).show();
				$(".qni-selected").removeClass("qni-selected");
				$($("a.question-navi-item")[thisindex - 1]).addClass("qni-selected");
			}
		});
		
		$("#next-q-btn").click(function(){
//			examing.saveAnswerSheet();
			var allQuestion = $(".question");
			var thisquestion  = $(".question:visible");
			var thisindex = $(".question").index(thisquestion);
			var allQuestionLength = allQuestion.length;
			if(thisindex == allQuestionLength - 1){
				return false;
			}else{
				thisquestion.hide();
				$(allQuestion[thisindex + 1]).show();
				$(".qni-selected").removeClass("qni-selected");
				$($("a.question-navi-item")[thisindex + 1]).addClass("qni-selected");
			}
		});
	},
	bindOptClick : function bindOptClick(){
		$(".question-list-item").click(function(){
			$(this).parent().find(".question-list-item-selected").removeClass("question-list-item-selected");
			$(this).addClass("question-list-item-selected");
			$(this).find("input").prop("checked", true);
		});
		
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
