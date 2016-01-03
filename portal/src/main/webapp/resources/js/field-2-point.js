$(function(){
		var selection = $("#aq-course1").find("select");
		// alert("111");
		var point_list = $("#aq-course2").find("select");
		selection.change(function(){
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				url : "admin/get-knowledge-point/" + selection.val(),
				success : function(message,tst,jqXHR) {
					if(!util.checkSessionOut(jqXHR))return false;
					if (message.result == "success") {
						point_list.empty();
						$.each(message.object,function(key,values){
							point_list.append("<option value=\"" + key + "\">" + values + "</option>");
						});
					} else {
						util.error("操作失败请稍后尝试");
					}
				},
				error : function(xhr) {
					util.error("操作失败请稍后尝试");
				}
			});
		});
});

