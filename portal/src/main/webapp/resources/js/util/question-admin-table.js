/**
 * 绘制table
 * @param table_entity
 * @returns {Boolean}
 */
function drawTable(table_entity){
	var questionlist = table_entity.questions;
	if(questionlist.length == 0){
		$("#question-list").empty();
		return false;
	}
	var pagelink = table_entity.pagelink;
	
	$("#page-link-content").html(pagelink);
	
	var tablehtml = "<table class=\"de-table\">";
	tablehtml = tablehtml + "<thead><tr><td></td><td>ID</td><td>试题名称</td><td>试题类型</td><td>职业种类</td><td>能力种类</td><td>能力项</td><td>能力级别</td><td>默认分数</td><td>答题时间</td><td>创建人</td></tr></thead>";
	tablehtml = tablehtml + "<tbody>";
	for(var i = 0; i < questionlist.length;i++){
		tablehtml = tablehtml + "<tr>";
		tablehtml = tablehtml + "<td><input type=\"hidden\" value=\"" + questionlist[i].id + "\" /></td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].id + "</td>";
		tablehtml = tablehtml + "<td><a href=\"admin/question-detail/" + questionlist[i].id + "\" target=\"_blank\" title=\"预览\">"+ questionlist[i].name +"</a></td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].question_type_name +"</td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].job_name +"</td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].ability_type_name +"</td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].ability_item_name +"</td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].ability_level_name +"</td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].points +"</td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].duration +"</td>";
		tablehtml = tablehtml + "<td>" + questionlist[i].creator +"</td>";
		tablehtml = tablehtml + "</tr>";
	}
	
	tablehtml = tablehtml + "</tbody><tfoot></tfoot></table>";
	
	
	$("#question-list").html(tablehtml);
}
