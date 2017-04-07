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
					<h5 class="helps-right-tit">买家防骗</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<p class="helps-txt">买家如何防骗？</p>
					<p class="helps-txt">根据我们以往的经验总结了以下几点，您多加提防，上当受骗的概率就会低很多：</p>
					<p class="helps-txt">（1）优先考虑认证会员，会员通过了身份认证后信用等级更高；</p>
					<p class="helps-txt">（2）建议交易时最好选择在线交易或当面交易或物流代收货款方式，以保障双方权益；</p>
					<p class="helps-txt">（3）线下打款时，如果收款人姓名与店铺联系人姓名不一致时，建议谨慎，以防卖家不承认收到款。同时，请保存与卖家的相关联系记录及打款凭证，万一有纠纷有据可查；</p>
					<p class="helps-txt">（4）部分卖家电话沟通时报超低价，买家去到当地时价格就偏高、品质有问题、强卖、很多附加费用等，故请在电话沟通时，强调价格和品质，将一些细节约定好。</p>	
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