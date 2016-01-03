$(function() {
	examing.initial();
});

var examing = {
		initial : function initial() {
			this.refreshNavi();
			this.bindNaviBehavior();
			this.addNumber();
			this.updateSummery();
			this.bindQuestionFilter();
			this.bindfocus();
//			this.bindOpenModal();
//			this.addRemoveBtn();
//			this.bindRemoveQustionFromPaper();
//			this.blindChangePoint();
//			this.bindAddQustionToPaper();
			this.bindFinish();
			
			this.loadAnswerSheet();
			
			this.culatePoint();
			
			this.bindChangePoint();

		},
		loadAnswerSheet : function loadAnswerSheet(){
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				async : false,
				url : util.getCurrentRole() + "/exam/get-answersheet/" + $("#hist-id").val(),
				success : function(answerSheet, tst, jqXHR) {
					if (!util.checkSessionOut(jqXHR))
						return false;
					examing.mergeAnswerSheet(answerSheet);

				},
				error : function(jqXHR, textStatus) {
					util.error("操作失败请稍后尝试");
				}
			});
		},
		
		mergeAnswerSheet : function mergeAnswerSheet (answerSheet){
			var answerSheetItems = answerSheet.answerSheetItems;
			var questions = $(".question");
			if(questions.length!=answerSheetItems.length){
				util.error("读取考生答题数据异常！");
				return false;
			}
			
			for(var i = 0 ; i<questions.length; i++){
				var str = "<span>  考生答案：</span>" + "<span class=\"raw-answer-item\">" +answerSheetItems[i].answer + "</span><br>";
				str = str + "<span>  考生得分：</span>" + "<input class=\"raw-answer-point\" type=\"text\" value=\"" + answerSheetItems[i].point + "\"></input><br>";
				if(answerSheetItems[i].comment == null){
					answerSheetItems[i].comment = "";
				}
				str = str + "<span>  教师批注：</span><br>" + "<textarea class=\"raw-answer-comment\" style=\"width:100%;\">  " + answerSheetItems[i].comment +" </textarea><br>";
				$(questions[i]).find(".answer-desc-summary").append(str);
				
			}
		},
		culatePoint : function culatePoint(){
			var pointList = $(".raw-answer-point");
			var rawPoint = 0;
			for(i= 0 ; i < pointList.length; i++){
				rawPoint = rawPoint + parseInt($(pointList[i]).val());
			}
			$("#exampaper-raw-point").text(rawPoint);
		},
		bindChangePoint : function bindChangePoint(){
			$("#exampaper-body").on("change",".raw-answer-point",function(){
				examing.culatePoint();
			})
		},
		
		bindNaviBehavior : function bindNaviBehavior() {
			var nav = $("#question-navi");
			var naviheight = $("#question-navi").height() - 33;
			$("#exampaper-footer").height($("#question-navi").height());
			nav.css({
				position : 'fixed',
				bottom : '0px',
				"z-index" : '1'	
			});

			$("#question-navi-controller").click(function() {
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
		 * 刷新试题导航
		 */
		refreshNavi : function refreshNavi() {
			$("#question-navi-content").empty();
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
				$("#exampaper-desc").empty();
				$("#exampaper-total-point").text(0);
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
					+ questiontypes[i].name + "[<span class=\"efi-tno\">" 
					+ $("." + questiontypes[i].code).length + "</span>]<span class=\"efi-qcode\" style=\"display:none;\">" 
					+ questiontypes[i].code + "</span></span>";
				}
			}
			$("#exampaper-desc").html(summery);
			
			examing.doQuestionFilt($($(".exampaper-filter-item")[0]).find(".efi-qcode").text());
			
			
			examing.refreshTotalPoint();
		},
		/**
		 * 计算总分
		 */
		refreshTotalPoint : function refreshTotalPoint(){
			var question_sum_p_all = 0;
			var point_array = $(".question-point");
			for(var i = 0; i<point_array.length;i++){
				var pointtmp = parseFloat($(point_array[i]).text());
				if(isNaN(pointtmp)){
					continue;
				}else{
					question_sum_p_all = question_sum_p_all + pointtmp * 10;
				}
			}
			$("#exampaper-total-point").text(question_sum_p_all / 10);
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
//				$(targetQuestion).focus();
				
				examing.scrollToElement($(targetQuestion));
			});
		},
		

		
		bindRemoveQustionFromPaper : function bindRemoveQustionFromPaper(){
			$("#exampaper-body").on("click", "a.tmp-ques-remove", function(){
				$(this).parent().parent().remove();
				examing.refreshNavi();
				examing.addNumber();
				examing.updateSummery();
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
		

		
		composeEntity : function composeEntity(){
			var forms = $(".question");
			var map = new Object();
			forms.each(function(){
				var question_point = $(this).find("span.question-point").text();
				var question_id = $(this).find("span.question-id").text();
				map[question_id] = question_point;
			});
			
			return map;
		},
		
		bindFinish : function bindFinish(){
			var btn = $("#exampaper-footer button");
			btn.click(function(){
				var data = new Object();
				var answerSheetItems = examing.genrateAnswerSheet();
				var examHistroyId = $("#hist-id").val();
				data.examHistroyId = examHistroyId;
				data.answerSheetItems = answerSheetItems;
				data.examPaperId=$("#paper-id").val();
				data.pointRaw = $("#exampaper-raw-point").text();
				data.pointMax = $("#exampaper-total-point").text();
				
				$("#exampaper-footer button").attr("disabled", "disabled");
				var request = $.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : util.getCurrentRole() + "/exam/answersheet",
					data : JSON.stringify(data)
				});

				request.done(function(message, tst, jqXHR) {
					if (!util.checkSessionOut(jqXHR))
						return false;
					if (message.result == "success") {
						$(window).unbind('beforeunload');
						util.success("阅卷完成！", function() {
							window.location.replace(document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/exam/exam-student-list/' + $("#exam-id").val());
						});
					} else {
						util.error(message.result);
						$("#question-submit button").removeAttr("disabled");
					}
				});
				request.fail(function(jqXHR, textStatus) {
					alert("系统繁忙请稍后尝试");
					$("#exampaper-footer button").removeAttr("disabled");
				});
			});
		},
		
		genrateAnswerSheet : function genrateAnswerSheet() {
			var answerSheetItems = new Array();
			var questions = $(".question");

			for (var i = 0; i < questions.length; i++) {
				var answerSheetItem = new Object();
				if ($(questions[i]).hasClass("qt-singlechoice")) {
					answerSheetItem.questionTypeId = 1;
				} else if ($(questions[i]).hasClass("qt-multiplechoice")) {
					answerSheetItem.questionTypeId = 2;
				} else if ($(questions[i]).hasClass("qt-trueorfalse")) {
					answerSheetItem.questionTypeId = 3;
				} else if ($(questions[i]).hasClass("qt-fillblank")) {
					answerSheetItem.questionTypeId = 4;
				} else if ($(questions[i]).hasClass("qt-shortanswer")) {
					answerSheetItem.questionTypeId = 5;
				} else if ($(questions[i]).hasClass("qt-essay")) {
					answerSheetItem.questionTypeId = 6;
				} else if ($(questions[i]).hasClass("qt-analytical")) {
					answerSheetItem.questionTypeId = 7;
				}
				answerSheetItem.answer = $(questions[i]).find(".raw-answer-item").text();
				answerSheetItem.point = $(questions[i]).find(".raw-answer-point").val();
				answerSheetItem.comment = $.trim($(questions[i]).find(".raw-answer-comment").val());
				answerSheetItem.questionId = $(questions[i]).find(".question-id").text();
				answerSheetItems.push(answerSheetItem);
			}
			return answerSheetItems;
		}
};













