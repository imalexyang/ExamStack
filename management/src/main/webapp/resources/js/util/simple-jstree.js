$(function(){
	
	$(".jstree li a").attr("title","右键查看更多操作");
	
	$(".jstree").on("click","ins.jstree-icon",function(){
		var li_dom = $(this).parent();
		if(li_dom.hasClass("jstree-closed")){
			li_dom.removeClass("jstree-closed");
			li_dom.addClass("jstree-open");
		}else{
			li_dom.removeClass("jstree-open");
			li_dom.addClass("jstree-closed");
		}
	});
	
	
	$(".jstree li a").click(function(){
		var id = $(this).parent().data("id");
		
		var includeChild = 0;
		if($("#include-child").is(":checked"))
			includeChild = 1;
		document.getElementById('user-list-iframe').src = "admin/group-user-list/" + id + "-" + includeChild;
		
		document.getElementById("group-code").setAttribute("data-id", id);
		
		$("#group-code").val($(".jstree-clicked").text());
		return false;
	});
});