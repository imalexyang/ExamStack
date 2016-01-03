$(function() {
	dashboard.initial();
});

var dashboard = {
	initial : function initial() {
		this.loadInfo();
		this.loadStudentApprovedList();
		this.loadStudentMarkList();
		this.loadChart();
		this.bindApprove();
		this.bindDisapprove();
		this.bindFieldChange();
		this.bindMark();
	},

	loadInfo : function loadInfo() {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			async : false,
			url : "secure/dashboard/baseinfo",
			success : function(list, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				dashboard.drawInfoIcon(list);

			},
			error : function(jqXHR, textStatus) {
				util.error("操作失败请稍后尝试");
			}
		});

	},

	loadStudentApprovedList : function loadStudentApprovedList() {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			async : false,
			url : "secure/dashboard/studentApprovedList",
			success : function(ehlist, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				dashboard.drawStudentApprovedTable(ehlist);

			},
			error : function(jqXHR, textStatus) {
				util.error("操作失败请稍后尝试");
			}
		});
	},

	loadStudentMarkList : function loadStudentMarkList() {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			async : false,
			url : "secure/dashboard/StudentMarkList",
			success : function(ehlist, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				dashboard.drawStudentMarkTable(ehlist);

			},
			error : function(jqXHR, textStatus) {
				util.error("操作失败请稍后尝试");
			}
		});

	},
	bindFieldChange : function bindFieldChange(){
		$("#field-select").change(function(){
			dashboard.loadChart();
		});
	},
	loadChart : function loadChart() {
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			async : false,
			url : "secure/dashboard/chartinfo/" + $("#field-select").val(),
			success : function(object, tst, jqXHR) {
				if (!util.checkSessionOut(jqXHR))
					return false;
				dashboard.drawChart(object);

			},
			error : function(jqXHR, textStatus) {
				util.error("操作失败请稍后尝试");
			}
		});

	},

	loadCalendar : function loadCalendar() {
		$('#calendar').fullCalendar({
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month,agendaWeek,agendaDay'
			},
			//		defaultDate : dateString,
			lang : "zh-cn",
			buttonIcons : false, // show the prev/next text
			weekNumbers : true,
			editable : true,
			eventLimit : true // allow "more" link when too many events
			//	events : myevent
		});

	},

	drawInfoIcon : function drawInfoIcon(list) {
		$("#question-num").text(list[0]);
		$("#questionpaper-num").text(list[1]);
		$("#student-num").text(list[2]);
		

	},
	drawStudentApprovedTable : function drawStudentApprovedTable(ehlist) {
		var html;
		for(var i = 0 ; i < ehlist.length ; i++){
			html += "<tr>";
			html += "<td>";
			html += ehlist[i].userName;
			html += "</td>";
			html += "<td>";
			html += ehlist[i].trueName;
			html += "</td>";
			html += "<td>";
			html += ehlist[i].depName;
			html += "</td>";
			html += "<td>";
			html += "<span class=\"approved-btn btn-sm btn-success\" data-id=\"" + ehlist[i].histId + "\">审核</button><button class=\"disapproved-btn btn btn-warning\" data-id=\"" + ehlist[i].histId + "\">拒绝</span>"
			html += "</td>";
			html += "</tr>";
			
		}
		
		$("#studentApprovedTable").append(html);
	},
	drawStudentMarkTable : function drawStudentMarkTable(ehlist) {
		var html;
		for(var i = 0 ; i < ehlist.length ; i++){
			html += "<tr>";
			html += "<td>";
			html += ehlist[i].userName;
			html += "</td>";
			html += "<td>";
			html += ehlist[i].trueName;
			html += "</td>";
			html += "<td>";
			html += ehlist[i].depName;
			html += "</td>";
			html += "<td>";
			html +=  util.parseDate(ehlist[i].submitTime);           
			html += "</td>";
			html += "<td>";
			html += "<span class=\"mark-btn btn-sm btn-info\" data-id=\"" + ehlist[i].histId + "\">阅卷</span>"
			html += "</td>";
			html += "</tr>";
			
		}
		
		$("#studentMarkTable").append(html);
	},

	drawChart : function drawChart(object) {
		
		var keyList = new Array();
		var valueList = new Array();
		
		
		for(var i = 0 ; i < object.length; i++){
			keyList.push(object[i].name);
			valueList.push(object[i].amount);
		}
		
		var option = {
			    title: {
			        x: 'center',
			        text: '专业知识分布',
			        subtext: 'Field knowledge coverage'
			    },
			    tooltip: {
			        trigger: 'item'
			    },
			    toolbox: {
			        show: true,
			        feature: {
			            dataView: {show: true, readOnly: false},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    calculable: true,
			    grid: {
			        borderWidth: 0,
			        y: 80,
			        y2: 60
			    },
			    xAxis: [
			        {
			            type: 'category',
			            show: false,
			            data: keyList
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            show: false
			        }
			    ],
			    series: [
			        {
			            name: 'ECharts例子个数统计',
			            type: 'bar',
			            itemStyle: {
			                normal: {
			                    color: function(params) {
			                        // build a color map as your need.
			                        var colorList = [
			                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
			                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
			                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
			                        ];
			                        return colorList[params.dataIndex]
			                    },
			                    label: {
			                        show: true,
			                        position: 'top',
			                        formatter: '{b}\n{c}'
			                    }
			                }
			            },
			            data: valueList
			           
			        }
			    ]
			};
		var myChart = echarts.init(document.getElementById('mychart'));
		myChart.setOption(option);
	},
	bindApprove : function bindApprove(){
		$(".approved-btn").click(function(){

			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				url : util.getCurrentRole() + "/exam/mark-hist/" + $(this).data("id") + "/1",
				success : function(message, tst, jqXHR) {
					if (!util.checkSessionOut(jqXHR))
						return false;
					if (message.result == "success") {
						util.success("操作成功!", function(){
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
	},
	
	bindDisapprove : function bindDisapprove(){
		$(".disapproved-btn").click(function(){

			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "GET",
				url : util.getCurrentRole() + "/exam/delete-hist/" + $(this).data("id"),
				success : function(message, tst, jqXHR) {
					if (!util.checkSessionOut(jqXHR))
						return false;
					if (message.result == "success") {
						util.success("操作成功!", function(){
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
	},
	
	bindMark : function bindMark(){
		$(".mark-btn").click(function(){
			window.location.href = util.getCurrentRole() + "/exam/mark-exampaper/" + $(this).data("id");
		});
	}
}; 