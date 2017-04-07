<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="农产品,农业产品,农产品批发,农产品交易网,农产品网上交易,谷登农批网"/>
		<meta name="description" content="谷登农批网是专业农产品网上交易平台，提供全面农产品批发市场信息，及时发布最新农业产品市场供需资讯，具有高效农产品物流体系，能够安全解决农产品交易中资金问题，作为第一O2O农批平台，谷登农批网为农产品行业人士推出一站式服务。"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>谷登农批网-专注农产品批发及农业产品网上交易平台</title>
	</head>
<body onLoad="init();">
    <div class="i-map-contain" style="" id="pano_container" align="center"></div>
    <div class="map-mark">
    	<div class="mmark-ico"></div>
    	<div class="mmark-tl"></div><!-- 左上 -->
    	<div class="mmark-tm"></div><!-- 上中 -->
    	<div class="mmark-tr"></div><!-- 右上 -->
    	<div class="mmark-rm"></div><!-- 右中 -->
    	<div class="mmark-br"></div><!-- 右下 -->
    	<div class="mmark-bm"></div><!-- 右下 -->
    	<div class="mmark-bl"></div><!-- 左下 -->
    	<div class="mmark-lm"></div><!-- 左中 -->
    </div>	
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&key=K76BZ-W3O2Q-RFL5S-GXOPR-3ARIT-6KFE5"></script>
<script>
    function init() {
    // 创建街景
        pano = new qq.maps.Panorama(document.getElementById('pano_container'), {
            "pano": "10141019131027101229900",
            "pov":{
                heading:240,
                pitch:7
            }
        });
    }
</script>
</html>



