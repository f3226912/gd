<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<meta name="format-detection" content="telephone=no" />
		<title></title>
		<link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/reportData.css">
		<link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/swiper.min.css"> 
		<style type="text/css">					
		</style>
	</head>
	<body>
		<div class="left-arrow" id="left">
			<img src="../../../../v1.0/images/data/leftArrow.png" alt="">
		</div>
		<div id="right" class="right-arrow">
			<img src="../../../../v1.0/images/data/rightArrow.png" alt="">
		</div>
		<header class="row head-row">
			<div class="head-outline clearfix">
				<div id="oneDay" class="one-day head-day not-tapped lyHeadDays">今日</div>
				<div id="sevenDays" class="seven-days head-day tapped lyHeadDays">7日</div>
				<div id="fifDays" class="fif-days head-day not-tapped lyHeadDays">15日</div>
				<div id="thirtyDays" class="thirty-days head-day not-tapped lyHeadDays">30日</div>
			</div>
		</header>
		<div class="swiper-container">
			<div class="swiper-wrapper trans-time">
				<div class="swiper-slide">
					<article class="row">
						<img class="top-trade" src="../../../../v1.0/images/data/tradeBackground.png" alt="">
						<section id="trade-num-tip" class="trade-num-tip">
							<img src="../../../../v1.0/images/data/tradeAmount.png" alt="" class="trade-num-pic">
							<div class="trade-num-words">
								交易分析
							</div>	 				
						</section>
					</article>
					<article class="row clearfix">
						<section class="wid-33">
							<div id="pieOne" class="pie-one">
								
							</div>
							<div class="amount-words">
								<div class="received">已收款</div>
								<div><span id="receiveAm">250.23</span>元</div>
							</div>
							
							
						</section>
						<section class="wid-33">
							<div id="pieTwo" class="pie-two" >
								
							</div>
							<div class="amount-words">
								<div class="received">待收款</div>
								<div><span id="notRec"></span>元</div>
							</div>
						</section>
						<section class="wid-33">
							<div id="pieThree" class="pie-three">
								
							</div>
							<div class="amount-words">
								<div class="received">已关闭</div>
								<div><span id="hasClose"></span>元</div>
							</div>
						</section>
					</article>
					<article class="row">
						<section class="proportion">
							各状态订单交易额占比情况
							<hr class="hr">
						</section>
					</article>
					<article class="row">
						<div id="pieFour" class="pie-four" style="width:20rem;height:20rem;">
								
						</div>
						<div class="ratio-sit"> 已收款交易额比例情况</div>	

					</article>
				</div>
				<div class="swiper-slide">
					<article class="row">
						<img class="top-trade" src="../../../../v1.0/images/data/tradeBackground.png" alt="">			
						<section class="trade-num-tip">
							<img src="../../../../v1.0/images/data/orderNum.png" alt="" class="trade-num-pic">
							<div class="trade-num-words">
								<span>总订单量</span><a class="trade-num-a" href="#"><span id="orderNumAll"></span><span>单</span></a>
							</div>	 				
						</section>
					</article>
					<article class="row clearfix">
						<section class="wid-33">
							<div id="pieFive" class="pie-one">
								
							</div>
							<div class="amount-words">
								<div class="received">已收款</div>
								<div><span id="receiveNum"></span>单</div>
							</div>
												
						</section>
						<section class="wid-33">
							<div id="pieSix" class="pie-two" >
								
							</div>
							<div class="amount-words">
								<div class="received">待收款</div>
								<div><span id="notRecNum"></span>单</div>
							</div>
						</section>
						<section class="wid-33">
							<div id="pieSeven" class="pie-three">
								
							</div>
							<div class="amount-words">
								<div class="received">已关闭</div>
								<div><span id="hasCloseNum"></span>单</div>
							</div>
						</section>
					</article>
					<article class="row">
						<section class="proportion">
							各订单状态占比情况
							<hr class="hr">
						</section>
					</article>
					<article class="row">
						<div id="pieEight" class="pie-four" style="width:20rem;height:20rem;">
								
						</div>
						<div class="ratio-sit"> 已收款订单占比情况</div>	

					</article>				
				</div>
			</div>			
		</div>
		<script src="${CONTEXT }/v1.0/js/jquery-2.1.4.min.js"></script>	
		<!-- <script src="../../../../v1.0/js/jquery-2.1.4.min.js"></script> --> 
		<script src="${CONTEXT }/v1.0/js/jquery.mobile.custom.min.js"></script>
		<script src="${CONTEXT }/v1.0/js/data/swiper.js"></script>
		<script src="${CONTEXT }/v1.0/js/echarts.min.js"></script>
		<script src="${CONTEXT }/v1.0/js/data/tradeAnalysisView.js"></script>
		
	<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>
