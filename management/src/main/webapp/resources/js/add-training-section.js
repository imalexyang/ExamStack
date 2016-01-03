$(function(){
	add_training_section.initial();
});
var add_training_section={
		
		initial : function initial(){
			this.bindSubmit();
		},
		
		bindSubmit : function bindSubmit(){
			var form = $("#section-add-form");
			$("#add-section-btn").click(function(){
				var result = add_training_section.verifyInput();
				if (result) {
					
										
					var data = new Object();
					data.trainingId = $("#training-add-id").val();
					data.sectionDesc = $(".add-section-desc textarea").val();
					data.sectionName = $("#section-name").val();
					data.fileName = $("#file-name").text();
					data.fileType = $("#file-name").data("type");
					data.filePath = $("#file-name").data("path");
					
					jQuery.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : util.getCurrentRole() + "/training/add-training-section",
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
			var check_f = this.checkFileName();
			
			result = check_n && check_d && check_f;
			return result;
		},
		
		checkName : function checkName() {
			var f_name = $("#section-name").val();
			if (f_name == "") {
				$(".add-section-name .form-message").text("章节名不能为空");
				return false;
			} else if (f_name.length > 40 || f_name.length < 3) {
				$(".add-section-name .form-message").text("请保持在3-40个字符以内");
				return false;
			} 
			return true;
		},
		
		checkDesc : function checkDesc() {
			var desc = $(".add-section-desc textarea").val();
			if (desc.length > 2000) {
				$(".add-section-desc .form-message").text("请保持在2000个字符以内");
				return false;
			} 
			return true;
		},
		
		checkFileName : function checkFileName(){
			var file_name = $("#file-name").text();
			if(file_name == ""){
				$(".add-section-file .form-message").text("请上传附件！");
				return false;
			}
			return true;
		}
}