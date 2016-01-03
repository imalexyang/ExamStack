$(function() {

	$("#add-teacher-modal-btn").click(function() {
		$("#add-teacher-modal").modal({
			backdrop : true,
			keyboard : true
		});

	});

	$(".r-update-btn").click(function() {
		$("#update-teacher-modal").modal({
			backdrop : true,
			keyboard : true
		});
		var depId = $(this).data("depid");
		$("#teacher-update-form #id-update").val($(this).parent().parent().find(".r-id").text().trim());
		$("#teacher-update-form #name-update").val($(this).parent().parent().find(".r-name").text().trim());
		$("#teacher-update-form #truename-update").val($(this).parent().parent().find(".r-truename").text().trim());
		$("#teacher-update-form #national-id-update").val($(this).parent().parent().find(".r-national-id").text().trim());
		$("#teacher-update-form #phone-update").val($(this).parent().parent().find(".r-phone").text().trim());
		$("#teacher-update-form #email-update").val($(this).parent().parent().find(".r-email").text().trim());
		$("#teacher-update-form #company-update").val($(this).parent().parent().find(".r-company").text().trim());
		$("#teacher-update-form #dept-update").val($(this).parent().parent().find(".r-dept").text().trim());
		$("#teacher-update-form #department-input-select-u option[value='-1']").attr("selected", "selected"); 
		$("#teacher-update-form #department-input-select-u").children("option").each(function(){
			if($(this).val() == depId){
				$(this).attr("selected","selected");
			}
		});
	});

	$(".disable-btn").click(function() {

		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : "admin/change-teacher-status-" + $(this).data("id") + "-" + $(this).data("status"),
			success : function(message, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				if (message.result == "success") {
					util.success("操作成功", function() {
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

