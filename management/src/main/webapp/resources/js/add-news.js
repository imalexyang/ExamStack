$(function() {
	create_news.initial();
});

var create_news = {

	initial : function initial() {
		this.bindSubmitForm();
	},

	bindSubmitForm : function bindSubmitForm() {
		$("#add-news-btn").click(function() {
			var result = create_news.verifyInput();
			if (result) {
				var data = new Object();
				data.title = $(".form-news-title input").val();
				data.content = $(".form-news-content textarea").val();
				
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
							} else if (message.result == "captch-error") {

							} else if (message.result == "duplicate-email") {
								$(".form-email .form-message").text(message.messageInfo);
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
		var check_t = this.checkNewsTitle();
		var check_c = this.checkContent();
		
		result = check_t && check_c;
		return result;
	},

	checkNewsTitle : function checkNewsTitle() {
		var title = $(".form-news-title input").val();
		if (title == "") {
			$(".form-news-title .form-message").text("公告标题不能为空");
			return false;
		} else if (title.length > 50 || title.length < 4) {
			$(".form-news-title .form-message").text("请保持在4-50个字符以内");
			return false;
		} else {
			var re = /[\+|\-|\\|\/||&|!|~|@|#|\$|%|\^|\*|\(|\)|=|\?|´|"|<|>|\.|,|:|;|\]|\[|\{|\}|\|]+/;
			if (re.test(title)) {
				$(".form-news-title .form-message").text("只能是数字字母或者下划线的组合");
				return false;
			} else
				return true;

		}
		return true;
	},
	checkContent : function checkContent() {
		var content = $(".form-news-content textarea").val();
		if (content == "") {
			$(".form-news-content .form-message").text("姓名不能为空");
			return false;
		} else if (content.length > 600 || content.length < 1) {
			$(".form-news-content .form-message").text("请保持在1-600个字符以内");
			return false;
		}
		return true;
	}
}; 