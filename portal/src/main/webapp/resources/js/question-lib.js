$(function() {
	$(".delete-btn").click(function() {

		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : "admin/delete-field-" + $(this).data("id"),
			success : function(message, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				if (message.result == "success") {
					util.success("删除成功", function() {
						window.location.reload();
					});
				} else {
					util.error("操作失败请稍后尝试:" + message.result);
				}

			},
			error : function(jqXHR, textStatus) {
				util.error("操作失败请稍后尝试");
			}
		});

		return false;

	});
});
