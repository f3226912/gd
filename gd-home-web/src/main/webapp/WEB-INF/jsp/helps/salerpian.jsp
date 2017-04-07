<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
	<meta charset="UTF-8">
	<jsp:include page="/WEB-INF/jsp/pub/helps/title.jsp"></jsp:include>
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css"/>
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	
	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="main_menu check-nav">
			<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<!--head end-->
	
	<!--slide start-->
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop">
			<jsp:include page="/WEB-INF/jsp/pub/helps/leftnav.jsp"></jsp:include>
			<div class="fl">
				<div class="con-right products-con helps-cont">
					<h5 class="helps-right-tit">卖家防骗</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<p class="helps-txt">卖家如何防骗？</p>
					<p class="helps-txt">根据我们以往的经验总结了以下几点，您多加提防，上当受骗的概率就会低很多：</p>
					<p class="helps-txt">（1）建议在对方未打货款前，不要馈赠任何礼品或其他形式的金钱支出，比如还没有打货款要求送烟酒或要回扣等；</p>
					<p class="helps-txt">（2）建议不要轻易听信买家，拿产品到指定地方做检测，有可能检测单位是骗子个人开的皮包公司，专骗取检测费；</p>
					<p class="helps-txt">（3）如果买家要求去其所在地签合同，建议可以让买家到您这边当面交易，这样买家能看到货，一手交钱一手交货，对双方都更有利；</p>
					<p class="helps-txt">（4）如果买家称要购买保险等，建议买家没有打货款前，也不要听信先购买保险，更不要将交的保险费给买家；</p>	
					<p class="helps-txt">（5）建议交易时最好采取当面交易或物流代收货款或在线交易等方式，以保障双方权益。&nbsp;</p>
					<p class="helps-txt">以上五种情况，是目前我们总结的不诚信买家行为，仅供参考，骗子的骗术经常更新，不局限于以上五种情况，请您多提防。我们相信绝大部分的买家都是诚信交易的，以上总结的五个经验教训只是以防万一。</p>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>


	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>