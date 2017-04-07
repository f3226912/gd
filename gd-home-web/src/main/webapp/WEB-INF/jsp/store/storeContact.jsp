<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=UTF-8">
<title>${busiInfo.shopsName }商户联系方式_谷登农批网</title>
<meta name="keywords" content="${busiInfo.shopsName }联系方式,${busiInfo.shopsName }电话,｛${busiInfo.shopsName }地址">
<meta name="description" content="谷登农批网批发商${busiInfo.shopsName }联系方式，联系人：${busiInfo.contact }，所在市场：${busiInfo.marketName }，更多详情欢迎联系洽谈！谷登农批网第一的农产品交易O2O平台。">
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/head.inc"%>
<%@ include file="../pub/tags.inc"%> 
<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css">

</head>
<body>
	<!--head page-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	
	<!-- 主体 -->
	<div class="main clearfix">
		<!-- Navigate -->
		<jsp:include page="/WEB-INF/jsp/pub/store/storenavigate.jsp"></jsp:include>
		
		<div class="content">
			<!-- 右边主要框架 显示商铺信息 -->
			<jsp:include page="/WEB-INF/jsp/pub/store/storeLeft.jsp"></jsp:include>
			<div class="fl">
				<!-- contact us -->
				<jsp:include page="/WEB-INF/jsp/pub/store/contactus.jsp"></jsp:include>
		    </div>
			<div class="cl"></div>
		</div>
	</div>
	<!-- foot page -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!-- fixed tool -->
	<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>

<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>