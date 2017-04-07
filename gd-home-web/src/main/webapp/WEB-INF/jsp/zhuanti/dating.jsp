<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/tags.inc" %>
<%@ include file="../pub/head.inc" %>
<!doctype html>
<html>
<head>
	<title>供应大厅</title>
	<meta charset="utf-8" /> 
	<meta name="keywords" content="谷登农批网,谷登电商,谷登,白沙洲市场,补贴方案"/>
	<meta name="description" content="谷登农批网推出武汉白沙周市场补贴方案，更多更全的农产品价格信息尽在谷登农批网。" />
	<meta name="Content-Type" content="text/html; charset=UTF-8">	
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">	
	<!--#include virtual="pub/head.inc"-->
	<style>
.pro-info{
	padding:45px 0;
	color:#666666;
	font-size:18px;
	line-height:36px;
	overflow:hidden;
}
.ap-img p{
	text-align:center;
	font-size:18px;
	background:url('${CONTEXT }/images/img_tsg/datingicon_01.jpg') no-repeat 170px center;
}
.detail{
	margin-left:270px;
	margin-top: 25px;
}
.pro-info span{
	margin-left:65px;
}
.green{
	color:#069139;
}

.pro-list{
	padding:60px;
	border:1px solid #cccccc;
	width:1080px;
	margin:0 auto;
	position:relative;
}
.arrow-top{
	display:block;
	height:30px;
	width:55px;
	background:url('${CONTEXT }/images/img_tsg/datingicon_02.jpg') no-repeat center center;
	position:absolute;
	top:-28px;
	left:85px;
}
.pro-list ul{
	overflow:visible;
	width:1200px;
}
.pro-list li{
	width:310px;
	margin:8px 60px 8px 8px;
	float:left;
}
.pro-list li a{
	color:#666666;
	display:block;
}
.pro-list li a:hover{
	box-shadow: 0px 0px 5px #888888;
	-webkit-box-shadow: 0px 0px 5px #888888;
	-ms-box-shadow: 0px 0px 5px #888888;
	-o-box-shadow: 0px 0px 5px #888888;
	-moz-box-shadow: 0px 0px 5px #888888;
}
.pro-list li img{
	width:100%;
	height:314px;
	text-align:center;
}
.pro-list .ap-para{
	padding-left:20px;
}
.ap-para p{
	margin-top:20px;
	font-size:16px;
	line-height:30px;
}
.ap-para p.price{
	color:#ff994d;
	font-size:22px;
	font-family:'宋体';
}
.pro-list li h3{
	font-size:28px;
	height:45px;
	line-height:45px;
}
	</style>
</head>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/home.css">	
<link rel="stylesheet" href="${CONTEXT}/css/global_tsg.css">	
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
 <body>
    <div class="main-menu">
        <ul class="main-menu-list wrap-1200">
            <li class="main-menu-item">
                <a href="teseguan" class="main-menu-link">首页</a>
            </li>
            <li class="main-menu-item">
                <a href="cultural" class="main-menu-link">吉县苹果</a>
            </li>
            <li class="main-menu-item">
                <a class="active" href="dating" class="main-menu-link">供应大厅</a>
            </li>
            <li class="main-menu-item">
                <a href="vip" class="main-menu-link">企业会员</a>
            </li>
        </ul>
    </div>
	<!--head star-->
	<!--#include virtual="pub/head.html"-->
	<!--head end-->	
	<div class='wrap-1200 pro-info clear'>
		<div class='ap-img fl'>
			<a href="http://www.gdeng.cn/baishazhou/business/shop/32004.html">
				<img src="${CONTEXT }/images/img_tsg/img_01.jpg">
				<p>地方特色馆</p>
			</a>
		</div>
		
		<div class='detail'>
			<p>主营产品:&nbsp;&nbsp;&nbsp;&nbsp;吉县苹果<span>所在地:&nbsp;&nbsp;&nbsp;&nbsp;山西省  吉县</span></p>
			<p>经营模式:&nbsp;&nbsp;&nbsp;&nbsp;企业经营<span>经营类型:&nbsp;&nbsp;&nbsp;&nbsp;合作社</span></p>
			<p class='green'>吉县苹果先后被农业部确定为全国无公害苹果生产示范县、全国苹果标准化示范县、“国家级出口苹果质量安全示范区”和“中国苹果之乡”。 吉县苹果以其果型高桩、色泽鲜艳、皮薄质脆、香甜可口的独特品质，先后荣获了首届全国农博会苹果类金奖、“中国十大苹果品牌”、“中华名果”等国家级16项认证和27项大奖。</p>
		</div>
	</div>
	<div class='pro-list'>
		<div class='arrow-top'></div>
		<div style='overflow:hidden;'>
			<ul class='clear'>
				<li>
					<a href='http://www.gdeng.cn/market/74226.html'  target="_black">
						<img src="${CONTEXT }/images/img_tsg/img_02.jpg">
						<div class='ap-para'>
							<h3>噶啦果</h3>
							<p> 
								规格：12-24个/6公斤/箱</br>
								单果重：200-225g
							</p>
							<p class='price'>
								5.00 元/公斤
							</p>
						</div>
						
					</a>
					
				</li>
				<li>
					<a href='http://www.gdeng.cn/market/74223.html'  target="_black">
						<img src="${CONTEXT }/images/img_tsg/img_03.jpg">
						<div class='ap-para'>
							<h3>红将军</h3>
							<p> 
								规格：12-24个/6公斤/箱</br>
								单果重：200-225g
							</p>
	
							<p class='price'>
								5.00 元/公斤
							</p>
						</div>
						
					</a>
				</li>
				<li>
					<a href='http://www.gdeng.cn/market/74228.html'  target="_black">
						<img src="${CONTEXT }/images/img_tsg/img_04.jpg">
						<div class='ap-para'>
							<h3>红富士</h3>
							<p> 
								规格：12-24个/6公斤/箱</br>
								单果重：200-225g
							</p>
							<p class='price'>
								5.00 元/公斤
							</p>
						</div>
						
					</a>
				</li>
			</ul>
		</div>
		
	</div>

<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<!-- 友情链接end -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!-- fixed tool -->
	<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>  
</html>

