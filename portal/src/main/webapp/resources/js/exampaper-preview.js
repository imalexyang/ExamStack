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
			this.bindOpenModal();
//			this.addRemoveBtn();
			this.bindRemoveQustionFromPaper();
//			this.blindChangePoint();
			this.bindAddQustionToPaper();
			this.bindSavePaper();

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
		
		bindOpenModal : function bindOpenModal(){
				$("#add-more-qt-to-paper").click(function() {
				
					$("#question-selector-modal").modal({backdrop:true,keyboard:true});
				
				});
		},
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
		
		addRemoveBtn : function(){
			var deletehtml = "<a class=\"tmp-ques-remove\" title=\"删除此题\">删除</a>";
			$(".question-title").append(deletehtml);
			
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
		
		blindChangePoint : function blindChangePoint() {
			$("#exampaper-body").on("click", "span.question-point", function(){
				$("#question-point-modal").modal({backdrop:true,keyboard:true});
				
				$("#question-point-modal .qt-point-destination input").val($(this).text());
				
				var questions = $("li.question");
				var indexno = questions.index($(this).parent().parent());
				
				$("#qt-point-target-index").text(indexno + 1);
				
				
			});
			
			$("#update-point-btn").click(function(){
				var targetno = parseInt($("#qt-point-target-index").text());
				var newPoint = parseInt($(".qt-point-destination input").val());
				if(targetno<=0 || isNaN(targetno)||newPoint<=0||isNaN(newPoint)){
					return false;
				}else{
					$($("li.question")[targetno-1]).find(".question-point").text(newPoint);
					examing.refreshTotalPoint();
					$("#question-point-modal").modal('hide');
					return false;
				}
				
			});
			
			$("#update-point-type-btn").click(function(){
				var targetno = parseInt($("#qt-point-target-index").text());
				var newPoint = parseInt($(".qt-point-destination input").val());
				if(targetno<=0 || isNaN(targetno)||newPoint<=0||isNaN(newPoint)){
					return false;
				}else{
					
					var qt_type = $($("li.question")[targetno-1]).find(".question-type").text();
					$("li.qt-" + qt_type + " .question-point").text(newPoint);
					examing.refreshTotalPoint();
					$("#question-point-modal").modal('hide');
					return false;
				}
				
			});
			
			
			
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
		
		bindSavePaper : function bindSavePaper(){
			var btn = $("#exampaper-footer button");
			btn.click(function(){
				
				var map = examing.composeEntity();
				
				var count = 0;
				for (var k in map) {
				    if (map.hasOwnProperty(k)) {
				       ++count;
				    }
				}
				
				if(count!=  $(".question").length){
					util.error("存在重复的题目，请检查");
					return false;
				}else{
					var request = $.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : 'admin/update-exampaper/' + $("#exampaper-id").text(),
						data : JSON.stringify(map)
					});
					
					request.done(function(message,tst,jqXHR) {
						if(!util.checkSessionOut(jqXHR))return false;
						if (message.result == "success") {
							util.success("修改成功", function() {
								document.location.href = document.getElementsByTagName('base')[0].href + 'admin/exampaper-list';
							});
						}else {
							util.error("操作失败请稍后尝试");
						}
					});
					
					request.fail(function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					});
				}
				
				
				
			});
		},
		
		bindAddQustionToPaper : function bindAddQustionToPaper(){
			$("button#add-list-to-exampaper").click(function() {
				var values = new Array();
				var checkboxs = $("#qt-selector-iframe").contents().find("table input:checked");
				$.each(checkboxs, function() {
					var id = $(this).val();
					values.push(id);
				});
				if (checkboxs.length == 0) {
					util.notify("请选择需要添加的试题!");
				}else{
					var request = $.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : 'admin/get-question-detail4add',
						data : JSON.stringify(values)
					});
					request.done(function(questionList,tst,jqXHR) {
						if(!util.checkSessionOut(jqXHR))return false;
						for(var i=0;i<questionList.length;i++){
							var question=questionList[i];
							var deletehtml = "<a class=\"tmp-ques-remove\" title=\"删除此题\">删除</a>";
							
							/*var current_answer = "";
							if(question.question_type_id==3){
								current_answer = question.answer == "T"? "正确":"错误";
							}else{
								current_answer = question.answer;
							}*/
							
							/*var answerhtml = "<div class=\"tmp-correct-answer\"><span>正确答案：</span><p>"+ current_answer +"</p></div>";*/
							var newquestion = $('<div/>').html(question.content).contents();
						/*	newquestion.append(answerhtml);*/
							newquestion.find(".question-title").append(deletehtml);
							$("#exampaper-body").append(newquestion);
						}
						examing.refreshNavi();
						examing.addNumber();
						examing.updateSummery();
						$("#question-selector-modal").modal('hide');

						var questions = $("li.question");
						examing.scrollToElement($(questions[questions.length-1]));
						
					});
						 
					request.fail(function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					});
				}
			});
		}
};













