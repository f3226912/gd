<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<!doctype html>
<html>
<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>谷登金牌特权</title>
	<link rel="stylesheet" type="text/css" href="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/css/eStyle.css"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="谷登农批网,谷登电商,谷登客服代运营"/>
	<meta name="description" content="谷登农批网推出优质的客服代运营线上店铺服务，以保证商家可以及时更新产品价格、供应量、认证信息、新产品发布等。" />
</head>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/home.css">	
<style>
  .ban_box{ position:relative; top:0; left:0; width:100%;}
  .wid100{position:absolute; width:1920px !important; height:536px !important; overflow: hidden;display:block;position:absolute;top:0;left:50%;margin-left:-960px}
  .eWrap .banner .little{ position:absolute;top:473px;  right:14%; width:184px; height:34px;}
  
</style>
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
<body>
<div class="main-menu">
        <ul class="main-menu-list wrap-1200" style="height:36px; width:100%; border-bottom:solid 2px #069139;">
            <li class="mainitem" style="position:relative; bottom:0; width:900px; margin:0 auto;">
                <a class="active" href="##" style="color: #069138;text-decoration: none; position: relative; top: 5px;">首页</a>
                <span style="position: absolute; bottom: -16px; left: 8px;"><img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/icon_img_03.png"></span>
            </li>
        </ul>
    </div>
		<section class="eWrap">
			<div class="fu" style="display: none;">
				<ul>
					<li class="nav-item">
						<a href="#cul1" id="cul11" class="active">三大特权</a>
					</li>
					<li class="nav-item">
						<a href="#cul2" id="cul22" class="">交易展示</a>
					</li>
					<li class="nav-item">
						<a href="#cul3" id="cul33">成功案例</a>
					</li>
				</ul>
				<div class="kefu">
					<a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&key=XzkzODAzNjk2N18zNDA1NjlfNDAwNzYwMDgwMF8yXw"><img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/kefu.png"/></a>
				</div>
				<div class="kefu" style="position:absolute; bottom:-58px; right:28px;z-index:999;">
				    <img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/gold_img.jpg"/>
			    </div>
				<img class="top" onclick="document.documentElement.scrollTop = document.body.scrollTop =0;" src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/top1.png"/>
				<div class="line"></div>
			</div>
			<%-- <div class="kefu" style="position:fixed; bottom:-5px; right:12px;z-index:999;">
			    <img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/gold_img.jpg"/>
		    </div> --%>
			<div class="banner ban_box">
				<img class="wid100" src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/banner.jpg"/>
				<a href="http://www.gdeng.cn/nsy_gys.shtml" class="go_abs"><img class="little" src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/indexButton.png"/></a>
			</div>
			<div class="choice" id="cul1">
				<div class="tital">
					<img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/efont1.png"/>
				</div>
				<div class="ebody">
					<div class="ones">
						<div class="left in">
							<h2>精准买家</h2>
							<h4>提高交易达成率</h4>
							<img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/left1.png"/>
						</div>
						<div class="right" style="width:438px;">
							<img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/right1.png"/>
						</div>
					</div>
					<div class="ones">
						<div class="left">
							<h2>精准推荐</h2>
							<h4>增加产品曝光率</h4>
							<img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/left2.png"/>
						</div>
						<div class="right">
							<img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/right2.png"/>
						</div>
					</div>
					<div class="ones">
						<div class="left">
							<h2>精准撮合</h2>
							<h4>节省你的宝贵时间</h4>
							<img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/left3.png"/>
						</div>
						<div class="right">
							<img src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/right3.png"/>
						</div>
					</div>
				</div>
			</div>
			<div class="con1" id="cul2">
				<img style="display: block;" src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/con1.jpg"/>
			</div>
			<div class="con2" id="cul3">
				<img style="display: block;" src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/images/con2.jpg"/>
			</div>
		</section>
		<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
	</body>
	<script src="${CONTEXT }zhuanti/goldMedal/gold_Medal_gys/js/ejs.js" type="text/javascript" charset="utf-8"></script>
<!-- 友情链接end -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!-- fixed tool -->
</html>