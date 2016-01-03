$(function() {
	Morris.Donut({
		element : 'graph-base',
		data : [{
			label : "答对题目",
			value : parseInt($(".exam-report-correct .label-success").text())
		}, {
			label : "答错题目",
			value : parseInt($(".exam-report-error .label-danger").text())
		}],
		colors : ['#5cb85c', '#da4f49'],
		labelColor : '#1ba1e2'
	});
});
