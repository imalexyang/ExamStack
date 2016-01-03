$(function(){
	add_training.initial();
});
var add_training={
		
		initial : function initial(){
			this.bindSubmit();
		},
		
		bindSubmit : function bindSubmit(){
			var form = $("#form-training-add");
			$("#training-add-btn").click(function(){
				var result = add_training.verifyInput();
				if (result) {
					
										
					var data = new Object();
					data.trainingName = $(".add-update-training-name input").val();
					data.trainingDesc = $(".add-update-training-desc textarea").val();
					data.fieldId = $(".add-update-training-field select").val();
					data.effTime = $("#training-exp-date").val();
					data.trainingType = $("#training-type").attr("checked") == true ? true : false;
					
					jQuery.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : util.getCurrentRole() + "/training/add-training",
						data : JSON.stringify(data),
						success : function(message, tst, jqXHR) {
							if (message.result == "success") {
								document.location.href = document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/training/training-list';
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
			var check_d = this.checkDesc();
			var check_f = this.checkField();
			var check_e = this.checkExpTime();
			
			result = check_n && check_d && check_f && check_e;
			return result;
		},
		
		checkName : function checkName() {
			var f_name = $(".add-update-training-name input").val();
			if (f_name == "") {
				$(".add-update-training-name .form-message").text("培训名不能为空");
				return false;
			} else if (f_name.length > 40 || f_name.length < 3) {
				$(".add-update-training-name .form-message").text("请保持在3-40个字符以内");
				return false;
			} 
			return true;
		},
		
		checkDesc : function checkDesc() {
			var f_name = $(".add-update-training-desc textarea").val();
			if (f_name.length > 2000) {
				$(".add-update-training-desc .form-message").text("请保持在2000个字符以内");
				return false;
			} 
			return true;
		},
		
		checkField : function checkField() {
			var fieldId = $(".add-update-training-field select").val();
			if (fieldId == -1) {
				$(".add-update-training-field .form-message").text("请选择培训专业");
				return false;
			} else
				return true;
		},
		
		checkExpTime : function checkExpTime(){
			var expTime = $("#training-exp-date").val();
			var re = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/;
			if(expTime == ""){
				$(".add-update-training-name .form-message").text("过期时间不能为空");
			} else if(!re.test(expTime)){
				$(".add-update-training-name .form-message").text("日期格式为YYYY-MM-DD");
				return false;
			}
			return true;
		}
}