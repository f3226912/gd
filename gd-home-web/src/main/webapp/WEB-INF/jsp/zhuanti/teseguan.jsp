<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<%@ include file="../pub/head.inc" %>
<!doctype html>
<html>
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>首页</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="谷登农批网,谷登电商,谷登客服代运营"/>
	<meta name="description" content="谷登农批网推出优质的客服代运营线上店铺服务，以保证商家可以及时更新产品价格、供应量、认证信息、新产品发布等。" />
	<style>
        .logo-img{width: 100%;min-width: 1200px;}
		.featured-h-img{ overflow: hidden;}
		.featured-h-img img{ width:100%; display: block; margin: 0 auto;}
		.featured-h{ width: 75%; text-align: center; margin: 0 auto; position: relative;}
		.featured-pot{ width: 855px; height: 218px; background: url(../images/line-right.jpg) no-repeat; margin: 0 auto; position:relative; top: 110px;}
		.featured-pot2{ width: 855px; height: 218px; background: url(../images/img-left.jpg) no-repeat; margin: 0 auto; position:relative; top: 110px;}
		.apple-left{ float: left; margin-left: 60px; margin-top:-90px;}
		.apple-right{ float: right; margin-top:-90px;}
		.abouts-right{ float: left; margin-left:98px; margin-top:-90px; width: 309px; text-align: left}
		.abouts-left{ float: left; margin-left:98px; margin-top:-90px; width: 309px; text-align: right}
		.abouts-right h3 ,.abouts-left h3{ font-size: 40px; padding-bottom: 20px; color: #4b4b4b; border-bottom: solid 1px #d2d2d2;}
		.p1{ padding-top: 22px; color: #4b4b4b; font-size: 24px;}
		.p2{ padding-top: 20px; padding-bottom: 34px;}
		.advisory{ background: #216b51; text-align: center; border-radius: 5px; padding:8px 25px; font-size: 28px; color: #fff; text-decoration: none;}

	</style>	
</head>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/home.css">	
<link rel="stylesheet" href="${CONTEXT}/css/global_tsg.css">	
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
<body>
    <div class="main-menu">
        <ul class="main-menu-list wrap-1200">
            <li class="main-menu-item">
                <a class="active" href="teseguan" class="main-menu-link">首页</a>
            </li>
            <li class="main-menu-item">
                <a href="cultural" class="main-menu-link">吉县苹果</a>
            </li>
            <li class="main-menu-item">
                <a href="dating" class="main-menu-link">供应大厅</a>
            </li>
            <li class="main-menu-item">
                <a href="vip" class="main-menu-link">企业会员</a>
            </li>
        </ul>
    </div>
    <header class="logo">
		<img class="logo-img" src="${CONTEXT }/images/img_tsg/FeaturedHouse_01.png"/>
    </header>
    <div class="featured-h">
        <div class="featured-h-img">
    		<img src="${CONTEXT }/images/img_tsg/FeaturedHouse_02.png"/>
    		<img src="${CONTEXT }/images/img_tsg/FeaturedHouse_03.png"/>
    		<img src="${CONTEXT }/images/img_tsg/FeaturedHouse_04.png"/>
    		<img src="${CONTEXT }/images/img_tsg/FeaturedHouse_05.png"/>
    		<img src="${CONTEXT }/images/img_tsg/FeaturedHouse_06.png"/>
    	</div>
    	<div class="featured-pot clearfix">
    		<div class="apple-left">
    		    <a href="http://www.gdeng.cn/market/73418.html" target="_blank">
    		        <img src="${CONTEXT }/images/img_tsg/apple1.png"/>
    		     </a>
    		</div>
    		<div class="abouts-right">
    			<h3>红富士</h3>
    			<p class="p1">规格：12-24个/6公斤/箱</p>
    			<p class="p1 p2">单果重：200-225g</p>
    			<a class="advisory" href="http://crm2.qq.com/page/portalpage/wpa.php?uin=4007600800&aty=0&a=0&curl=http://www.gdeng.cn&ty=1">采购咨询</a>
    		</div>
    	</div>

    	<div class="featured-pot2 clearfix" style="margin-top:160px;">
    		<div class="abouts-left">
    			<h3>红将军</h3>
    			<p class="p1">规格：12-24个/6公斤/箱</p>
    			<p class="p1 p2">单果重：200-225g</p>
    			<a class="advisory" href="http://crm2.qq.com/page/portalpage/wpa.php?uin=4007600800&aty=0&a=0&curl=http://www.gdeng.cn&ty=1">采购咨询</a>
    		</div>
    		<div class="apple-right">
    		    <a href="http://www.gdeng.cn/market/73424.html" target="_blank">
    		        <img src="${CONTEXT }/images/img_tsg/apple2.png"/>
    		    </a>
    		</div>
    	</div>

    	<div class="featured-pot clearfix" style="margin-top:160px;">
    		<div class="apple-left">
    		    <a href="http://www.gdeng.cn/market/73419.html" target="_blank">
    		        <img src="${CONTEXT }/images/img_tsg/apple3.jpg"/>
    		    </a>
    		</div>
    		<div class="abouts-right">
    			<h3>噶啦果</h3>
    			<p class="p1">规格：12-24个/6公斤/箱</p>
    			<p class="p1 p2">单果重：200-225g</p>
    			<a class="advisory" href="http://crm2.qq.com/page/portalpage/wpa.php?uin=4007600800&aty=0&a=0&curl=http://www.gdeng.cn&ty=1">采购咨询</a>
    		</div>
    	</div>
    	<div class="featured-h-img" style="margin-top:150px;"><img src="${CONTEXT }/images/img_tsg/FeaturedHouse_07.png"/></div>
    </div>
	  
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<!-- 友情链接end -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!-- fixed tool -->
	<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>  
</html>

