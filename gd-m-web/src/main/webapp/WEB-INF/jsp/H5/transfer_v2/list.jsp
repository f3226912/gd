<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<meta name="format-detection" content="telephone=no" />
		<title>物流管理</title>
		
		<link rel="stylesheet" href="${CONTEXT }v1.0/css/transport/mui.min.css">
		<link rel="stylesheet" href="${CONTEXT }v1.0/css/transport/logisticsManage.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT }v1.0/css/transport/style.css"/>
		<style type="text/css">
			.mui-fs-18p{
				font-size: 18px;
			}
			.pic_phone > .mui-icon-phone{
				font-size: 45px;
			}
			
			.mark {
			    position: fixed;
			    z-index: 998;
			    left: 0;
			    top: 0;
			    width: 100%;
			    height: 100%;
			    background: #000;
			    opacity: .1;
			}
			.markAll img{width:32px;height:32px;position: absolute;left:50%;top:50%;margin-left:-16px;margin-top:-16px;z-index:99;}
		</style>
	</head>
<body>
		<div class="markAll" style="display:none">
			<div class="mark"></div>
			<img src="${CONTEXT }v1.0/images/transport/loading.gif"/>
		</div>
		
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<div id="topHead" class="row plr_10p mui-clearfix">
						<div class="column20 mui-text-center " id="aPublished">
							<a href="#" class="bottom_red top_a">已发布</a>
						</div>
						<div class="column20 mui-text-center" id="aDeal">
							<a href="#" class="top_a">待确认</a>
						</div>
						<div class="column20 mui-text-center" id="aDealed">
							<a href="#" class="top_a">待收货</a>
						</div>
						<div class="column20 mui-text-center" id="aFinished">
							<a href="#" class="top_a">已完成</a>
						</div>
						<div class="column20 mui-text-center" id="aUnfinished">
							<a href="#" class="top_a">已关闭</a>
						</div>
						<!-- <div class="cl"></div> -->				
					</div>
				<!-- 已发布模板开始处 -->
				 <ul id="published" class="outline" style="display: block;">
					<%--<li>
						<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">
							<span>装货时间: 2016-06-26</span>
							<div class="color_ff6c00 ab_right ">已发布</div>	
						</div>
						<article class="address-con bor-bottom">
							<i class="bg-icon add-img"></i>
							<div class="fhd-text">
								<span><i class="bg-icon"></i>发货地</span>
								<span class="input-text">深圳市福田区麓龙路98卓越大厦</span>
							</div>
							<div class="mhd-text">
								<span><i class="bg-icon"></i>目的地</span>
								<span class="input-text">武汉市洪山区白沙洲大市场</span>
							</div>
						</article>
						<p class="bottomOne">删除</p>
					</li>
					
					<li>
						<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">
							<span>装货时间: 2016-06-26</span>
							<div class="color_ff6c00 ab_right ">已发布</div>	
						</div>
						<article class="address-con bor-bottom">
							<i class="bg-icon add-img"></i>
							<div class="fhd-text">
								<span><i class="bg-icon"></i>发货地</span>
								<span class="input-text">深圳市福田区麓龙路98卓越大厦</span>
							</div>
							<div class="mhd-text">
								<span><i class="bg-icon"></i>目的地</span>
								<span class="input-text">武汉市洪山区白沙洲大市场</span>
							</div>
						</article>
						<p class="bottomOne">拨打电话</p>
					</li>
					
					<li>
						<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">
							<span>装货时间: 2016-06-26</span>
							<div class="color_ff6c00 ab_right ">已发布</div>	
						</div>
						<article class="address-con bor-bottom">
							<i class="bg-icon add-img"></i>
							<div class="fhd-text">
								<span><i class="bg-icon"></i>发货地</span>
								<span class="input-text">深圳市福田区麓龙路98卓越大厦</span>
							</div>
							<div class="mhd-text">
								<span><i class="bg-icon"></i>目的地</span>
								<span class="input-text">武汉市洪山区白沙洲大市场</span>
							</div>
						</article>
						<div class="bottomAll">
							<p class="bottomThree">拨打电话</p>
							<p class="bottomThree"><img class="bottomMiddle" style="left:0;" src="${CONTEXT }v1.0/images/transport/bottomMiddle.jpg"/>拒绝<img class="bottomMiddle" style="right:0;" src="${CONTEXT }v1.0/images/transport/bottomMiddle.jpg"/></p>
							<p class="color_ff6c00 bottomThree">接受</p>
						</div>
					</li>
					
					<li>
						<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">
							<span>装货时间: 2016-06-26</span>
							<div class="color_ff6c00 ab_right ">已发布</div>	
						</div>
						<article class="address-con bor-bottom">
							<i class="bg-icon add-img"></i>
							<div class="fhd-text">
								<span><i class="bg-icon"></i>发货地</span>
								<span class="input-text">深圳市福田区麓龙路98卓越大厦</span>
							</div>
							<div class="mhd-text">
								<span><i class="bg-icon"></i>目的地</span>
								<span class="input-text">武汉市洪山区白沙洲大市场</span>
							</div>
						</article>
						<div class="bottomAll">
							<p class="bottomTwo">拨打电话</p>
							<p class="bottomTwo color_ff6c00"><img class="bottomMiddle" style="left:0;" src="${CONTEXT }v1.0/images/transport/bottomMiddle.jpg"/>确认收货</p>
						</div>
					</li>
					
					<li>
						<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">
							<span>装货时间: 2016-06-26</span>
							<div class="color_ff6c00 ab_right ">已发布</div>	
						</div>
						<article class="address-con bor-bottom">
							<i class="bg-icon add-img"></i>
							<div class="fhd-text">
								<span><i class="bg-icon"></i>发货地</span>
								<span class="input-text">深圳市福田区麓龙路98卓越大厦</span>
							</div>
							<div class="mhd-text">
								<span><i class="bg-icon"></i>目的地</span>
								<span class="input-text">武汉市洪山区白沙洲大市场</span>
							</div>
						</article>
						<div class="bottomAll">
							<p class="bottomTwo">拨打电话</p>
							<p class="bottomTwo color_ff6c00"><img class="bottomMiddle" style="left:0;" src="${CONTEXT }v1.0/images/transport/bottomMiddle.jpg"/>支付运费</p>
						</div>
					</li>--%>
					
				</ul>	  
				<!-- 已发布模板结束处 -->
				
		</div>
		
		<script>
			var context="${CONTEXT }";
		</script>
		<script type="text/javascript" src="${CONTEXT }v1.0/module/mui/dist/js/mui.min.js"></script>
		<script src="${CONTEXT }js/transfer/jquery-2.1.4.min.js"></script>
		<script src="${CONTEXT}js/layer.js"></script> 
		<script src="${CONTEXT }js/transfer/mui.min.js"></script>
		<script src="${CONTEXT }js/transfer/jquery.mobile.custom.min.js"></script>
		<script src="${CONTEXT }js/transfer/global.js"></script>
		<script src="${CONTEXT }js/transfer/my.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>