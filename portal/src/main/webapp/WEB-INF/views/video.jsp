<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="keywords" content="">
	<script type="text/javascript"
		src="resources/js/jquery/jquery-1.9.0.min.js"></script>
	<script type="text/javascript" src="resources/js/mediaelement/mediaelement-and-player.min.js"></script>
	<link href="resources/js/mediaelement/mediaelementplayer.css" rel="stylesheet">
	<link href="resources/js/mediaelement/mejs-skins.css" rel="stylesheet">
</head>
<body>
	<video id="example_video_1" controls="controls" preload="none"
		style="margin: auto;" width="100%" height="100%"
		poster="resources/images/logo.png">
		<!-- <source src="resources/flash/oceans-clip.mp4" type='video/mp4'>
				<source src="resources/flash/1.flv" type='video/flv'> -->
		<source src="resources/flash/1.flv" type='video/flv'>
		<object type="application/x-shockwave-flash"
			data="resources/js/mediaelement/flashmediaelement.swf">
			<param name="movie" value="flashmediaelement.swf" />
			Image as a last resort <img src="resources/images/logo.png"
				width="100%" height="100%" title="No video playback capabilities" />
		</object>
	</video>
	<script>
		$('video,audio').mediaelementplayer(/* Options */);
	</script>
</body>
</html>