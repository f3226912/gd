<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/tags.inc" %>
<%@ include file="../pub/head.inc" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="谷登农批网,谷登电商,农产品交易,谷登"/>
	<meta name="description" content="谷登农批网线上交易系统强势来袭，农产品交易买卖，一网打尽。谷登农批网提供最全农产品及商户信息，及时发布批发市场最新农产品价格和供需资讯。"/>
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>企业会员</title>

	<style>
		.head-vip{margin:45px 0;color:#ffffff;font-size:18px;line-height:36px;overflow:hidden;background: url(${CONTEXT }/images/img_tsg/head.png) 100% 0 no-repeat;}
		.header-logo{padding: 75px 35px 0 35px;}
		/*.header-logo img{border-radius: 50%;border: #fff 5px solid;}*/
		.header-logo p{text-align:center;font-size:18px;color: #666666;background:url('${CONTEXT }/images/img_tsg/datingicon_01.jpg') no-repeat 170px center;}
		.detail{margin-top: 180px;}
		.head-vip span{margin-left:65px;}
		.detail-content{color:#069139;margin-top: 25px;}
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
                <a href="dating" class="main-menu-link">供应大厅</a>
            </li>
            <li class="main-menu-item">
                <a class="active" href="vip" class="main-menu-link">企业会员</a>
            </li>
        </ul>
    </div>
	<div class="main-wrap wrap-1200">
		<div class='head-vip clear'>
			<div class='header-logo fl'>
				<a href="http://www.gdeng.cn/baishazhou/business/shop/32004.html">
					<img src="${CONTEXT }/images/img_tsg/apple.png">
					<p>地方特色馆</p>
				</a>
			</div>
			
			<div class='detail'>
				<p>主营产品:&nbsp;&nbsp;&nbsp;&nbsp;吉县苹果<span>所在地:&nbsp;&nbsp;&nbsp;&nbsp;山西省  吉县</span></p>
				<p>经营模式:&nbsp;&nbsp;&nbsp;&nbsp;企业经营<span>经营类型:&nbsp;&nbsp;&nbsp;&nbsp;合作社</span></p>
				<p class='detail-content'>吉县苹果先后被农业部确定为全国无公害苹果生产示范县、全国苹果标准化示范县、“国家级出口苹果质量安全示范区”和“中国苹果之乡”。 吉县苹果以其果型高桩、色泽鲜艳、皮薄质脆、香甜可口的独特品质，先后荣获了首届全国农博会苹果类金奖、“中国十大苹果品牌”、“中华名果”等国家级16项认证和27项大奖。</p>
			</div>
		</div>
		<p class="content">
			<img src="${CONTEXT }/images/img_tsg/vip.png" />
		</p>
	</div>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<!-- 友情链接end -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!-- fixed tool -->
	<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>  
</html>

