$(function() {
	update_account.initial();
});

var update_account = {

	initial : function initial() {
		this.bindSubmitForm();
	},

	bindSubmitForm : function bindSubmitForm() {
		$("#update-teacher-btn").click(function() {
			var result = update_account.verifyInput();
			if (result) {
				var data = new Object();
				data.userId = $("#id-update").val();
				data.userName = $(".form-username-u input").val();
				data.email = $(".form-email-u input").val();
				/*data.password = $(".form-password-u input").val();*/
				data.company = $(".form-company-u input").val();
				data.phoneNum = $(".form-phone-u input").val();
				data.nationalId = $(".form-national-id-u input").val();
				data.depId = $("#department-input-select-u").val();
				data.trueName = $(".form-truename-u input").val();
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : "secure/update-user",
					data : JSON.stringify(data),
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							util.success("修改成功！", function() {
								window.location.reload();
							});
						} else {
							if (message.result == "duplicate-username") {
								$(".form-username-u .form-message").text(message.messageInfo);
							} else if (message.result == "duplicate-national-id") {
								$(".form-national-id-u .form-message").text(message.messageInfo);
							} else if (message.result == "duplicate-email") {
								$(".form-email-u .form-message").text(message.messageInfo);
							} else if (message.result == "duplicate-phone") {
								$(".form-phone-u .form-message").text(message.messageInfo);
							} else {
								alert(message.result);
							}
						}
					}
				});
			}
			return false;
		});
		
		$("#reset-pwd-btn").click(function() {
		
			var username = $("#reset-pwd-form #username-reset").val()
			var password =  $("#reset-pwd-form #pwd-reset").val()
			if(password.length<6){
				$(".form-user-name-reset .form-message").text("密码不能小于6位");
				return false;
			}
			jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : "secure/reset-pwd-user/" + password + "/" + username,
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							util.success("修改成功！", function() {
								window.location.reload();
							});
						} else {
							util.error("修改失败！");
						}
					}
			});
			
			return false;
		});
		
	},

	verifyInput : function verifyInput() {
		$(".form-message").empty();
		var result = true;
		var check_u = this.checkUsername();
		var check_t = this.checkTrueName();
		var check_e = this.checkEmail();
		var check_p = /*this.checkPassword()*/true;
		var check_com = /*this.checkCompany();*/true;
		var check_id = this.checkNationalId();
		var check_phone = this.checkPhoneNum();
		var check_dep = this.checkDepartment();
		result = check_u && check_t && check_e && check_p && check_com && check_id && check_phone && check_dep;
		return result;
	},

	checkUsername : function checkUsername() {
		var username = $(".form-username-u input").val();
		if (username == "") {
			$(".form-username-u .form-message").text("用户名不能为空");
			return false;
		} else if (username.length > 20 || username.length < 4) {
			$(".form-username-u .form-message").text("请保持在4-20个字符以内");
			return false;
		} else {
			var re = /[\+|\-|\\|\/||&|!|~|@|#|\$|%|\^|\*|\(|\)|=|\?|´|"|<|>|\.|,|:|;|\]|\[|\{|\}|\|]+/;
			if (re.test(username)) {
				$(".form-username .form-message").text("只能是数字字母或者下划线的组合");
				return false;
			} else
				return true;

		}
		return true;
	},
	checkTrueName : function checkTrueName() {
		var truename = $(".form-truename-u input").val();
		if (truename == "") {
			$(".form-truename-u .form-message").text("真实姓名不能为空");
			return false;
		} else if (truename.length > 20 || truename.length < 2) {
			$(".form-truename-u .form-message").text("请保持在2-20个字符以内");
			return false;
		} else {
			var re = /[\+|\-|\\|\/||&|!|~|@|#|\$|%|\^|\*|\(|\)|=|\?|´|"|<|>|\.|,|:|;|\]|\[|\{|\}|\|]+/;
			if (re.test(truename)) {
				$(".form-truename-u .form-message").text("只能是数字字母或者下划线的组合");
				return false;
			} else
				return true;

		}
		return true;
	},
	checkCompany : function checkCompany() {
		var company = $(".form-company-u input").val();
		if (company == "") {
			$(".form-company-u .form-message").text("单位不能为空");
			return false;
		} else if (company.length > 20 || company.length < 2) {
			$(".form-company-u .form-message").text("请保持在2-20个字符以内");
			return false;
		} else {
			var re = /[\+|\-|\\|\/||&|!|~|@|#|\$|%|\^|\*|\(|\)|=|\?|´|"|<|>|\.|,|:|;|\]|\[|\{|\}|\|]+/;
			if (re.test(company)) {
				$(".form-company-u .form-message").text("只能是数字字母或者下划线的组合");
				return false;
			} else
				return true;

		}
		return true;
	},
	checkDepartment : function checkDepartment() {
		var department = $("#department-input-select-u").val();
		if (department == "-1") {
			$(".form-department-u .form-message").text("请选择一个部门");
			return false;
		}
		return true;
	},
	checkEmail : function checkEmail() {
		var email = $(".form-email-u input").val();
		if (email == "") {
			$(".form-email-u .form-message").text("邮箱不能为空");
			return false;
		} else if (email.length > 60 || email.length < 9) {
			$(".form-email-u .form-message").text("请保持在9-60个字符以内");
			return false;
		} else {
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if (re.test(email)) {
				return true;
			} else {
				$(".form-email-u .form-message").text("无效的邮箱");
				return false;
			}

		}
		return true;
	},

	checkPhoneNum : function checkPhoneNum() {
		var phonenum = $(".form-phone-u input").val();
		if (phonenum == "") {
			$(".form-phone-u .form-message").text("号码不能为空");
			return false;
		} else {
			var re = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
			if (!re.test(phonenum)) {
				$(".form-phone-u .form-message").text("手机不合法");
				return false;
			} else
				return true;

		}
		return true;
	},

	checkPassword : function checkPassword() {
		var password = $(".form-password-u input").val();
		if (password == "") {
			return true;
		} else if (password.length < 6 || password.length > 20) {
			$(".form-password-u .form-message").text("密码请保持在6到20个字符以内");
			return false;
		} else {
			return true;
		}
		return true;
	},

	checkNationalId : function checkNationalId() {
		var idcard = $(".form-national-id-u input").val();
		if (idcard == "") {
			$(".form-national-id-u .form-message").text("单位不能为空");
			return false;
		} else {
			var re = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			if (!re.test(idcard)) {
				$(".form-national-id-u .form-message").text("身份证信息不合法");
				return false;
			} else
				return true;

		}
		return true;
	}
}; 