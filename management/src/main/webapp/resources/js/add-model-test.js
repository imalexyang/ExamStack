$(function(){
	add_model_test.initial();
});
var add_model_test={
		
		initial : function initial(){
			this.bindSubmit();
		},
		
		bindSubmit : function bindSubmit(){
			var form = $("#form-examp-add");
			$("#exam-add-btn").click(function(){
				var result = add_model_test.verifyInput();
				if (result) {
					
					var datefrom = $("#exam-eff-date").val();
					var dateto = $("#exam-exp-date").val();
					
					
					var data = new Object();
					data.examName = $(".add-update-examname input").val();
					data.examType = 3;
					data.examPaperId = $(".add-update-exam-paper select").val();
					
					jQuery.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : util.getCurrentRole() + "/exam/add-model-test",
						data : JSON.stringify(data),
						success : function(message, tst, jqXHR) {
							if (message.result == "success") {
								document.location.href = document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/exam/model-test-list';
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
			var check_p = this.checkPaper();
			
			
			result = check_n && check_p;
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
		
		checkPaper : function checkPaper() {
			var paperId = $(".add-update-exam-paper select").val();
			if (paperId == -1) {
				$(".add-update-exam-paper .form-message").text("请选择考试试卷");
				return false;
			} else
				return true;
		}
}