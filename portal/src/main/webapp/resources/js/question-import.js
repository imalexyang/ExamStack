$(function(){
	question_import.initial();
});

var question_import={
		initial : function initial() {
			this.prepareUploadify();
			this.questionDataProcess();
		},
		prepareUploadify : function prepareUploadify(){
			setTimeout(function(){
				$("#uploadify").uploadify({
			    	'debug'	 : false,
					'buttonText'	: '点击上传附件',
					'buttonCursor'	: 'pointer',
					'uploader'	 : document.getElementsByTagName('base')[0].href + 'admin/upload-uploadify/',
					'queueID': 'fileQueue',
					'swf'	 : document.getElementsByTagName('base')[0].href + 'resources/js/uploadify/uploadify.swf',
					'multi'	 : false,
					'auto'	 : true,
					'height' : '26',
					'width'	 : '160',
					'requeueErrors'	: false,
					'fileSizeLimit'	: '20480', // expects input in kb
					'cancelImage'	: document.getElementsByTagName('base')[0].href + 'resources/js/uploadify/cancel.png',
					removeCompleted : true,
					overrideEvents:['onSelectError','onDialogClose'],
					onUploadComplete: function(file) {
					},
					onUploadSuccess : function(file, data, response) {  
						$('#div-file-list').html('<a class=\'file-name\'>' 
								+ file.name 
								+ '</a><input type=\'hidden\' value=\'' 
								+ file.name + '\' />');
			        },
					onSelectError: function(file,errorCode,errorMsg) {
						if(errorCode==-110){
							util.notify("只能上传20M以下的文件。");
							return false;
						}
					},
					onUploadError: function(file,errorCode,errorMsg, errorString) {
						util.error(errorMsg);
					}
			    });
			},2);
		},
		questionDataProcess : function questionDataProcess(){
			$("#from-question-import").submit(function(){
				var filePath = $("#div-file-list").find("input").val();
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : $("#from-question-import").attr("action") + "/" + $(".upload-question-group select").val(),
					data : filePath,
					success : function(message, tst, jqXHR) {
						if (!util.checkSessionOut(jqXHR))
							return false;
						if (message.result == "success") {
							util.success("导入成功", function() {
								$("#submit-div .form-message").text(message.messageInfo);
								//document.location.href = document.getElementsByTagName('base')[0].href + 'admin/course-list';
							});
						} else {
							util.error("操作失败请稍后尝试:" + message.result);
							$("#submit-div .form-message").text(message.messageInfo);
							$("#btn-add-submit").removeAttr("disabled");
						}
					},
					error : function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
						$("#btn-add-submit").removeAttr("disabled");
					}
				});
				return false;
			});
		}
};
