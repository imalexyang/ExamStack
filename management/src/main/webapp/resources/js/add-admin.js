$(function() {
	create_account.initial();
});

var create_account = {

	initial : function initial() {
		this.bindSubmitForm();
	},

	bindSubmitForm : function bindSubmitForm() {
		$("#add-user-btn").click(function() {
			var result = create_account.verifyInput();
			if (result) {
				var data = new Object();
				data.userName = $(".form-username input").val();
				data.email = $(".form-email input").val();
				data.password = $(".form-password input").val();
				data.fieldId = $("#job-type-input-select").val();
				data.company = $(".form-company input").val();
				data.phoneNum = $(".form-phone input").val();
				data.nationalId = $(".form-national-id input").val();
				data.trueName = $(".form-truename input").val();
				data.depId = $("#department-input-select").val();
				var action = $(this).data("action");
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : action,
					data : JSON.stringify(data),
					success : function(message, tst, jqXHR) {
						if (message.result == "success") {
							util.success("添加成功！", function() {
								window.location.reload();

							});

						} else {
							if (message.result == "duplicate-username") {
								$(".form-username .form-message").text(message.messageInfo);
							} else if (message.result == "duplicate-national-id") {
								$(".form-national-id .form-message").text(message.messageInfo);
							} else if (message.result == "duplicate-email") {
								$(".form-email .form-message").text(message.messageInfo);
							} else if (message.result == "duplicate-phone") {
								$(".form-phone .form-message").text(message.messageInfo);
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
		var check_t = this.checkTrueName();
		var check_e = this.checkEmail();
		var check_p = this.checkPassword();
		var check_com = /*this.checkCompany();*/true;
		var check_id = this.checkNationalId();
		var check_phone = this.checkPhoneNum();
		var check_dep = /*this.checkDepartment()*/true;
		result = check_u && check_t && check_e && check_p && check_com && check_id && check_phone && check_dep;
		return result;
	},

	checkUsername : function checkUsername() {
		var username = $(".form-username input").val();
		if (username == "") {
			$(".form-username .form-message").text("用户名不能为空");
			return false;
		} else if (username.length > 20 || username.length < 4) {
			$(".form-username .form-message").text("请保持在4-20个字符以内");
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
		var truename = $(".form-truename input").val();
		if (truename == "") {
			$(".form-truename .form-message").text("姓名不能为空");
			return false;
		} else if (truename.length > 20 || truename.length < 2) {
			$(".form-truename .form-message").text("请保持在2-20个字符以内");
			return false;
		}
		return true;
	},
	checkCompany : function checkCompany() {
		var company = $(".form-company input").val();
		if (company == "") {
			$(".form-company .form-message").text("单位不能为空");
			return false;
		} else if (company.length > 20 || company.length < 2) {
			$(".form-company .form-message").text("请保持在2-20个字符以内");
			return false;
		}
		return true;
	},
	checkDepartment : function checkDepartment() {
		var department = $("#department-input-select").val();
		if (department == "-1") {
			$(".form-department .form-message").text("请选择一个部门");
			return false;
		}
		return true;
	},
	checkEmail : function checkEmail() {
		var email = $(".form-email input").val();
		if (email == "") {
			$(".form-email .form-message").text("邮箱不能为空");
			return false;
		} else if (email.length > 60 || email.length < 9) {
			$(".form-email .form-message").text("请保持在9-60个字符以内");
			return false;
		} else {
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if (re.test(email)) {
				return true;
			} else {
				$(".form-email .form-message").text("无效的邮箱");
				return false;
			}

		}
		return true;
	},

	checkPhoneNum : function checkPhoneNum() {
		var phonenum = $(".form-phone input").val();
		if (phonenum == "") {
			$(".form-phone .form-message").text("号码不能为空");
			return false;
		} else {
			var re = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
			if (!re.test(phonenum)) {
				$(".form-phone .form-message").text("手机不合法");
				return false;
			} else
				return true;

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

	checkNationalId : function checkNationalId() {
		var idcard = $(".form-national-id input").val();
		if (idcard == "") {
			$(".form-national-id .form-message").text("单位不能为空");
			return false;
		} else if (idcard.length > 20 || idcard.idcard < 3) {
			$(".form-national-id .form-message").text("请保持在3-20个字符以内");
			return false;
		} else {
			var re = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			if (!re.test(idcard)) {
				$(".form-national-id .form-message").text("身份证信息不合法");
				return false;
			} else
				return true;

		}
		return true;
	}
}; 