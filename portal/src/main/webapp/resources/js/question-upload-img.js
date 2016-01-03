$(function() {
	question_upload_img.init();
	
	
});

var question_upload_img = {
		
		init: function init(){
//			this.drawModal();
			this.prepareUploadify();
			this.prepareDialog();
			this.bindDisplayImg();
		},
		
		drawModal : function drawModal(){
//			$("#question-add-form").append("<div class=\"modal\" id=\"add-question-img-dialog\"><div title=\"图片上传工具\" class=\"modal-dialog modal-lg\"><div class=\"modal-content\"><form><div class=\"form-line img-destination\"><span class=\"form-label\">添加至：</span><label></label><input type=\"hidden\" value=\"\"/></div><div class=\"form-line add-update-quetstionfile\"><span class=\"form-label\">上传图片：</span><div id=\"div-file-list\"></div><div class=\"form-line\" id=\"uploadify\"></div><span class=\"form-message\">请上传png、jpg图片文件，且不能大于100KB。为了使得图片显示正常，请上传的图片长宽比例为2:1</span></div></form></div></div></div>");
			
			
		},
		
		prepareDialog : function prepareDialog() {
			$("#question-add-form").on("click",".add-img",function() {
				
				$(".fade").modal({backdrop:true,keyboard:true});
				
//				$("#add-question-img-dialog").dialog("open");
				$("#file-name").empty();
				
				if($(this).hasClass("add-content-img")){
					$(".img-destination label").text("试题内容");
					$(".img-destination input").val(-1);
				}else if($(this).hasClass("add-opt-img")){
					$(".img-destination label").text("试题选项 ");
					var this_index = $(".add-opt-img").index($(this));
					$(".img-destination label").append(String.fromCharCode(65 + this_index));
					$(".img-destination input").val(this_index);
				}
			});
		},
		prepareUploadify : function prepareUploadify(){
			$("#uploadify").uploadify({
				
				'debug'	 : false,
				'buttonText'	: '点击上传',
				'buttonCursor'	: 'pointer',
				'uploader'	 : document.getElementsByTagName('base')[0].href + 'admin/upload-uploadify-img/',
				'swf'	 : document.getElementsByTagName('base')[0].href + 'resources/js/uploadify/uploadify.swf',
				'multi'	 : false,
				'auto'	 : true,
				'height'	 : '26',
				'width'	 : '60',
				'requeueErrors'	: false,
				'fileSizeLimit'	: '100', // expects input in kb
				'cancelImage'	: document.getElementsByTagName('base')[0].href + 'resources/js/uploadify/cancel.png',
				
				overrideEvents:['onSelectError','onDialogClose'],
				onUploadProgress: function() {
//					$('#loader').show();
				},
				onUploadComplete: function(file) {
//					$('#div-file-list').html('<a id=\'file-name\'>' + file.name + '</a>');
//					$('#loader').fadeOut(100);
					$('#maincontent').load(location.href+' #maincontent > *');
					$(".fade").modal('hide');
				},
				onUploadSuccess : function(file, data, response) {
//			            alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
			        	var fileurl = data;
			        	var destination  = $(".img-destination input").val();
			        	if(destination == -1){
//			        		var textareaval = $(".question-content textarea").val();
//			        		$(".question-content textarea").val( textareaval + "<img class=\"question-content-img\" src=\"" +  fileurl + "\">");
			        		
			        		var displayImg = $(".question-content").find(".diaplay-img");
			        		if(displayImg.length  == 0){
			        			$(".question-content textarea").after("<a href=\"..\\" + fileurl + "\" class=\"diaplay-img display-content-img\" target=\"_blank\" data-url=\"" + fileurl + "\">预览图片</a>");
			        		}else{
			        			displayImg.attr("href", fileurl);
			        		}
			        		
			        	}else{
			        		var thisopt =  $($(".add-opt-item")[destination]);
			        		var displayImg = thisopt.find(".diaplay-img");
			        		
			        		if(displayImg.length  == 0){
			        			thisopt.find("input.form-question-opt-item").after("<a href=\"..\\" + fileurl + "\" class=\"diaplay-img display-opt-img\" target=\"_blank\" data-url=\"" + fileurl + "\">预览图片</a>");
			        		}else{
			        			displayImg.attr("href", fileurl);
			        		}
			        		
//			        		var optval = thisopt.val();
//			        		thisopt.val(optval + "<img class=\"question-opt-img\" src=\"" +  fileurl + "\">");
			        	}
			        	
			        	/*$("#add-question-img-dialog").dialog( "close" );*/
				},
				onSelectError: function(file,errorCode,errorMsg) {
					if(errorCode==-110){
						util.error("只能上传100KB以下的图片。");
						return false;
					}
				},
				onUploadError: function(file,errorCode,errorMsg, errorString) {
					util.error(errorMsg);
				}
			});
		},
		
		bindDisplayImg : function bindDisplayImg(){
			$("#bk-conent-question-content").delegate(".diaplay-img","click",function() {
				window.open(location.protocol + "//" + location.host + "/" + $(this).attr("href"));
				e.preventDefault();
			});
		}
		
};