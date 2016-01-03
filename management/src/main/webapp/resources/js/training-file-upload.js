$(function() {
	question_upload_img.init();
	
	
});

var question_upload_img = {
		
		init: function init(){
//			this.drawModal();
			this.prepareUploadify();
		},
		
		drawModal : function drawModal(){
//			$("#question-add-form").append("<div class=\"modal\" id=\"add-question-img-dialog\"><div title=\"图片上传工具\" class=\"modal-dialog modal-lg\"><div class=\"modal-content\"><form><div class=\"form-line img-destination\"><span class=\"form-label\">添加至：</span><label></label><input type=\"hidden\" value=\"\"/></div><div class=\"form-line add-update-quetstionfile\"><span class=\"form-label\">上传图片：</span><div id=\"div-file-list\"></div><div class=\"form-line\" id=\"uploadify\"></div><span class=\"form-message\">请上传png、jpg图片文件，且不能大于100KB。为了使得图片显示正常，请上传的图片长宽比例为2:1</span></div></form></div></div></div>");
			
			
		},
		
		prepareUploadify : function prepareUploadify(){
			$("#uploadify").uploadify({
				
				'debug'	 : false,
				'buttonText'	: '点击上传',
				'buttonCursor'	: 'pointer',
				'uploader'	 : document.getElementsByTagName('base')[0].href + 'secure/upload-uploadify-file/',
				'swf'	 : document.getElementsByTagName('base')[0].href + 'resources/js/uploadify/uploadify.swf',
				'multi'	 : false,
				'auto'	 : true,
				'height'	 : '26',
				'width'	 : '60',
				'fileTypeDesc': "请选择mp4 flv pdf文件",
			    'fileTypeExts': '*.mp4;*.flv;*.pdf',
				'requeueErrors'	: false,
				'fileSizeLimit'	: '204800000', // expects input in kb
				'cancelImage'	: document.getElementsByTagName('base')[0].href + 'resources/js/uploadify/cancel.png',
				
				overrideEvents:['onSelectError','onDialogClose'],
				onUploadProgress: function() {
//					$('#loader').show();
				},
				onUploadComplete: function(file) {
					
					//$('#maincontent').load(location.href+' #maincontent >');
					//$(".fade").modal('hide');
				},
				onUploadSuccess : function(file, data, response) {
			        $("#div-file-list").html("<a id=\'file-name\' data-type=\'" + file.type + "\' data-path=\'" + data + "\'>" + file.name + "</a>");
					$('#loader').fadeOut(100);
				},
				onSelectError: function(file,errorCode,errorMsg) {
					if(errorCode==-110){
						util.error("文件大小限制。");
						return false;
					}
				},
				onUploadError: function(file,errorCode,errorMsg, errorString) {
					util.error(errorMsg);
				}
			});
		}
};