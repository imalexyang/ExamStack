$(function(){
	add_exam.initial();
});
var add_exam={
		
		initial : function initial(){
			this.bindSubmit();
		},
		
		bindSubmit : function bindSubmit(){
			var form = $("#form-examp-add");
			$("#exam-add-btn").click(function(){
				var result = add_exam.verifyInput();
				if (result) {
					
					var datefrom = $("#exam-eff-date").val();
					var dateto = $("#exam-exp-date").val();
					var timefrom = $("#exam-eff-time").val();
					var timeto = $("#exam-exp-time").val();
					
					var timestampfrom = add_exam.getTimestamp(datefrom + " " +timefrom);
					var timestampto =  add_exam.getTimestamp(dateto + " " +timeto);
					
					var data = new Object();
					data.examName = $(".add-update-examname input").val();
					data.examType = $(".add-update-exam-type select").val();
					data.examPaperId = $(".add-update-exam-paper select").val();
					data.effTime = timestampfrom;
					data.expTime = timestampto;
					
					var groups = $(".add-update-group-list :checked");
					var groupIds = new Array();
					groups.each(function(){
						groupIds.push($(this).val());
					});
					data.groupIdList = groupIds;
					jQuery.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : util.getCurrentRole() + "/exam/add-exam",
						data : JSON.stringify(data),
						success : function(message, tst, jqXHR) {
							if (message.result == "success") {
								document.location.href = document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/exam/exam-list';
							} else {
								util.error("操作失败请稍后尝试:" + message.result);
							}
						},
						error : function(jqXHR, textStatus) {
							util.error("操作失败请稍后尝试");
						}
					});
				}

				return false;
			});
		},
		
		verifyInput : function verifyInput() {
			$(".form-message").empty();
			var result = true;
			var check_n = this.checkName();
			var check_t = this.checkType();
			var check_p = this.checkPaper();
			var check_d = this.checkDuration();
			
			result = check_n && check_t && check_p && check_d;
			return result;
		},
		
		checkName : function checkName() {
			var f_name = $(".add-update-examname input").val();
			if (f_name == "") {
				$(".add-update-examname .form-message").text("考试名不能为空");
				return false;
			} else if (f_name.length > 40 || f_name.length < 5) {
				$(".add-update-examname .form-message").text("请保持在5-40个字符以内");
				return false;
			} 
			return true;
		},
		
		checkType : function checkType() {
			var typeId = $(".add-update-exam-type select").val();
			if (typeId == -1) {
				$(".add-update-exam-type .form-message").text("请选择考试类别");
				return false;
			} else
				return true;
		},
		
		checkPaper : function checkPaper() {
			var paperId = $(".add-update-exam-paper select").val();
			if (paperId == -1) {
				$(".add-update-exam-paper .form-message").text("请选择考试试卷");
				return false;
			} else
				return true;
		},
		
		checkDuration : function checkDuration(){
			var datefrom = $("#exam-eff-date").val();
			var dateto = $("#exam-exp-date").val();
			var timefrom = $("#exam-eff-time").val();
			var timeto = $("#exam-exp-time").val();
			
			if(datefrom=="" ||dateto == ""){
				$(".form-exam-duration .form-message").text("日期不能为空");
				return false;
			}
			
			var timestampfrom = this.getTimestamp(datefrom + " " +timefrom);
			var timestampto =  this.getTimestamp(dateto + " " +timeto);
			
			var currentTimeStamp = new Date().getTime();
			
		/*	if(currentTimeStamp > timestampfrom){
				$(".form-exam-duration .form-message").text("考试起始时间应该在当前时间之后");
				return false;
			}else */
				
			if(timestampfrom > timestampto){
				$(".form-exam-duration .form-message").text("起始时间不能大于结束时间");
				return false;
			}else if((timestampto - timestampfrom)< 1801){
				$(".form-exam-duration .form-message").text("考试有效期小于30分钟");
				return false;
			}else{
				return true;
			}
		},
		
		getTimestamp : function getTimestamp(str) {
			  var d = str.match(/\d+/g); 
			  return +new Date(d[0], d[1] - 1, d[2], d[3], d[4]); 
		}
}