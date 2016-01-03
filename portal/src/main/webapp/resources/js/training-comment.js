$(function() {
	comment.initial();
	comment.queryComment();
});

var comment = {
	initial : function initial() {
		this.bindSubmit();
		this.bindShowMore();
	},
	bindSubmit : function bindSubmit() {
		$("form.comment-form").submit(function(){
			
			var content = $(".comment-form textarea").val();
			var thisquestion  = $(".question:visible");
			
			if(content==null||content==""){
				util.error("评论不能为空！");
				return false;
			}else if(content.length > 140){
				util.error("不能超过140个字！");
				return false;
			}
			
			var data = new Object();
			data.referId =  $("#section-id").val();
			data.contentMsg = content;
			data.commentType = 1;
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				async:false,
				type : "POST",
				url : "student/submit-comment",
				data : JSON.stringify(data),
				success : function(message, tst, jqXHR) {
					if (!util.checkSessionOut(jqXHR))
						return false;
					if (message.result == "success") {
						$(".comment-form textarea").val("");
						
						var total = parseInt($(".comment-total-num").text()) + 1;
						$(".comment-total-num").text(total);
						var lastFloor = parseInt($("#last-floor-hidden").val());
						$("#last-floor-hidden").val(lastFloor);
						var html = comment.generatMyComment(data.contentMsg);
						$(".comment-list").prepend(html);
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
	},
	bindShowMore : function bindShowMore(){
		var show_more = $("#show-more-btn");
		show_more.click(function(){
			comment.queryComment();
			return false;
		});
	},
	queryComment : function queryComment(){
		//this.clearComment();
		//var thisquestion  = $(".question:visible");
		var idx = $("#idx-hidden").val();
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : "student/comment-list/1/" + $("#section-id").val() + "/" + idx + "/" + $("#last-floor-hidden").val(),
			success : function(message, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR)){
					util.error("请重新登录");
					return false;
				}
					
				if (message.result == "success") {
					var html = comment.generatComment(message.object.comments);
					comment.appendHtml(html,message.object.size);
					//批次加一
					$("#idx-hidden").val(parseInt(idx) + 1);
					
					if (message.messageInfo == "not-has-next") {
						$("#show-more-btn").text("没有更多评论了...");
						$("#show-more-btn").attr("disabled","disabled");
					} else {
						$("#show-more-btn").text("查看更多评论");
						$("#show-more-btn").removeAttr("disabled");
					}
					
					
				} else {
					util.error("读取失败请稍后尝试:" + message.result);
				}
			},
			error : function(jqXHR, textStatus) {
				util.error("读取失败请稍后尝试");
			}
		});
		return false;
	},
	
	appendHtml : function appendHtml(html,size){
		$(".comment-list").append(html);
		$(".comment-total-num").text(size);
	},
	
	clearComment : function clearComment(){
		$(".comment-list").empty();
	},

	generatComment : function generatComment(commentList){
		
		
		if(commentList.length == 0)return "";
		var html = "";
		var last_floor = $(".comment-user-index:first").find("a").text();
		if(last_floor == "")
			last_floor = 0;
		
		for(var i = 0 ; i < commentList.length; i++){
			postDate = new Date(commentList[i].createTime);
			var dateString = postDate.getFullYear() + "-" + (postDate.getMonth() + 1) + "-" + postDate.getDate() + " " + postDate.getHours() + ":" + postDate.getMinutes();
			html = html + "<li class=\"comment-list-item\">";
			html = html + "<div class=\"comment-user-index\"><a>" + commentList[i].indexId + "</a><span>楼</span></div>";
			html = html + "<div class=\"comment-user-container\">";
			html = html + "<div class=\"comment-user-img-content\"><img src=\"resources/images/photo.jpg\" class=\"comment-user-img\"></div>";
			
			html = html + "<div class=\"comment-user-info\"><div>" + commentList[i].username + "</div>";
			html = html + "<div class=\"comment-date\"><span>发表于：</span>" + dateString + "</div>";
			
			html = html + 	"</div>";
			html = html + "</div>";
			html = html + "<p class=\"comment-user-text\">" + commentList[i].contentMsg + "</p>";
			html = html + "</li>";
		}
		
		
		$("#last-floor-hidden").val(last_floor);
		return html;
	},
	
	generatMyComment : function generatMyComment(comment){
		
		var currentDate = new Date();
		var dateString = currentDate.getFullYear() + "-" + (currentDate.getMonth() + 1) + "-" + currentDate.getDate() + " " + currentDate.getHours() + ":" + currentDate.getMinutes();
		var html = "";
			html = html + "<li class=\"comment-list-item\">";
			html = html + "<div class=\"comment-user-index\"><a>" + $(".comment-total-num").text() + "</a><span>楼</span></div>";
			html = html + "<div class=\"comment-user-container\">";
			html = html + "<div class=\"comment-user-img-content\"><img src=\"resources/images/photo.jpg\" class=\"comment-user-img\"></div>";
			html = html + "<div class=\"comment-user-info\"><div>" + "huan1" + "</div>";
			html = html + "<div class=\"comment-date\">发表于：" + dateString  + "</div>";
			html = html + 	"</div>";
			html = html + "</div>";
			html = html + "<p class=\"comment-user-text\">" + comment + "</p>";
			html = html + "</li>";
		return html;
	}
};





