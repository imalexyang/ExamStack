$(function() {
	create_account.initial();
});

var create_account = {

	initial : function initial() {
		this.bindSubmitForm();
	},

	bindSubmitForm : function bindSubmitForm() {
		var form = $("form#form-create-account");

		form
				.submit(function() {
					var result = create_account.verifyInput();
					if (result) {
						var data = new Object();
						data.username = $("#name").val();
						data.email = $("#email").val();
						data.password = $("#password").val();
						data.fieldId = $("#job-type-input").val();
						jQuery
								.ajax({
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
													+ "regist-success/"
													+ data.username;
										} else {
											if (message.result == "duplicate-username") {
												$(
														".form-username .form-message")
														.text(
																message.messageInfo);
											} else if (message.result == "captch-error") {
												
											} else if (message.result == "duplicate-email") {
												$(
														".form-email .form-message")
														.text(
																message.messageInfo);
											} else {
												alert(message.result);
											}
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
		var check_u = this.checkUsername();
		var check_e = this.checkEmail();

		var check_p = this.checkPassword();
		var check_cp = this.checkConfirmPassword();
		var check_job = this.checkJob();

		
		result = check_u && check_e && check_p && check_cp && check_job;
		return result;
	},

	checkUsername : function checkUsername() {
		var username = $(".form-username input").val();
		if (username == "") {
			$(".form-username .form-message").text("用户名不能为空");
			return false;
		} else if (username.length > 20 || username.length < 5) {
			$(".form-username .form-message").text("请保持在5-20个字符以内");
			return false;
		} else {
			var re=/[\+|\-|\\|\/||&|!|~|@|#|\$|%|\^|\*|\(|\)|=|\?|´|"|<|>|\.|,|:|;|\]|\[|\{|\}|\|]+/;
			if(re.test(username)){
				$(".form-username .form-message").text("只能是数字字母或者下划线的组合");
				return false;
			}else return true; 
			
			
		}
		return true;
	},

	checkEmail : function checkEmail() {
		var email = $(".form-email input").val();
		if (email == "") {
			$(".form-email .form-message").text("邮箱不能为空");
			return false;
		} else if (email.length > 40 || email.length < 5) {
			$(".form-email .form-message").text("请保持在5-40个字符以内");
			return false;
		} else {
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		   if(re.test(email)){
			   return true;
		   }else{
			   $(".form-email .form-message").text("无效的邮箱");
				return false;
		   }
			
		}
		return true;
	},

	checkPassword : function checkPassword() {
		var password = $(".form-password input").val();
		if (password == "") {
			$(".form-password .form-message").text("密码不能为空");
			return false;
		} else if (password.length < 6 || password.length > 20) {
			$(".form-password .form-message").text("密码请保持在6到20个字符以内");
			return false;
		} else {
			return true;
		}
		return true;
	},

	checkConfirmPassword : function checkConfirmPassword() {
		var password_confirm = $(".form-password-confirm input").val();
		var password = $(".form-password-confirm input").val();
		if (password_confirm == "") {
			$(".form-password-confirm .form-message").text("请再输入一次密码");
			return false;
		} else if (password_confirm.length > 20) {
			$(".form-password-confirm .form-message").text(
					"内容过长，请保持在20个字符以内");
			return false;
		} else if (password_confirm != password) {
			$(".form-password-confirm .form-message").text("2次密码输入不一致");
			return false;
		} else {
			return true;
		}
	},
	checkJob : function(){
		var jobid = $("#job-type-input").val();
		if(jobid == -1){
			$(".form-job-type .form-message").text("请选择专业");
			return false;
		}else{
			return true;
		}
		return false;
	},
	
	checkTerm : function checkTerm() {

		if ($('.form-confirm input[type=checkbox]').is(':checked')) {
			return true;

		} else {
			$(".form-confirm .form-message").text("请选择");
			return false;
		}
	}

};