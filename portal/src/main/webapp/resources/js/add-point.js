$(function() {
	add_point.initial();
});

var add_point = {

	initial : function initial() {
		this.bindSubmitForm();
	},

	bindSubmitForm : function bindSubmitForm() {
		var form = $("form#point-add-form");

		form.submit(function() {
			var result = add_point.verifyInput();
			if (result) {
				var data = new Object();
				data.pointName = $("#name").val();
				data.memo = $("#memo").val();
				data.fieldId = $("#field-input-select").val();
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : form.attr("action"),
					data : JSON.stringify(data),
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							document.location.href = document
									.getElementsByTagName('base')[0].href
									+ "admin/point-list-0-1";
						} else {
							alert(message.result);
						}
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
		// var check_e = this.checkEmail();
		var check_m = this.checkMemo();
		
		result = check_n && check_m;
		return result;
	},

	checkName : function checkName() {
		var f_name = $(".form-name input").val();
		if (f_name == "") {
			$(".form-name .form-message").text("知识分类名不能为空");
			return false;
		} else if (f_name.length > 40 || f_name.length < 3) {
			$(".form-name .form-message").text("请保持在3-40个字符以内");
			return false;
		} 
		return true;
	},
	
	checkMemo : function checkMemo() {
		var memo = $(".form-memo input").val();
		if (memo == "") {
			$(".form-memo .form-message").text("描述不能为空");
			return false;
		} else if (memo.length > 40 || memo.length < 3) {
			$(".form-memo .form-message").text("请保持在3-40个字符以内");
			return false;
		} 
		return true;
	},
	
	checkField : function checkField() {
		var fieldId = $("#field-input-select").val();
		if (fieldId == -1) {
			$(".form-field .form-message").text("请选择题库");
			return false;
		} else
			return true;
	}

};