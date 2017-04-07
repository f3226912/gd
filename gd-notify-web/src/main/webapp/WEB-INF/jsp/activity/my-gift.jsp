<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>我的礼品</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/layer.css"/>
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/gift.css"/>
</head>

<body>
	<div class="main-wrap">
			<div class="main-gift">
				<div class="my-gift">
					<h4 class="integral-h4">我的兑换记录</h4>
					<c:choose>
					   <c:when test="${excuteStatus == 1}">
							<div class="gift-name clearfix">
								<img class="gift-img" src="${CONTEXT}v1.0/images/gift-2.png"/>
								<div class="gift-name-p">
									<p class="gift-name-p1">礼品名称：${gift.giftName}</p>
									<p class="gift-name-p1">消费积分：${gift.exchangeScore}</p>
									<p class="gift-name-p1">兑换时间：${gift.exchangeTime}</p>
									<p class="gift-name-p1">发放状态：
										<c:choose>
										   <c:when test="${gift.status == 1}">未发</c:when>
										   <c:when test="${gift.status == 2}">已发</c:when>
										   <c:otherwise>不予发放</c:otherwise>
										</c:choose>
									</p>
								</div>
							</div>
					   </c:when>
					   <c:otherwise>
							<div class="gift-none">您还没有兑换过礼品哦！</div>
					   </c:otherwise>
					</c:choose>
					<div class="tips-a">
						<p class="gift-name-p2">温馨提示：</p>
						<p class="gift-name-p2">1、礼品兑换时间为6月18日-6月30日</p>
						<p class="gift-name-p2">2、礼品数量以库存为准，先到先得哦！</p>
						<p class="gift-name-p2">3、兑换结束后所有用户的红包金额清零。</p>
						<p class="gift-name-p2">4、所有礼品均在兑换结束后3个工作日后统一发放。</p>
					</div>

				</div>

				<div class="mui-bot"></div>

			</div>
		<div class="footer">
			<footer>
				<ul>
					<li><a class="footer-a" href="javascript:void(0);" onclick="toGrabRedPacket()">抢红包</a></li>
					<li><a class="footer-a" href="javascript:void(0);" onclick="toExchange()">兑换礼品</a></li>
					<li><a class="footer-a" href="javascript:void(0);" >我的礼品</a></li>
				</ul>
			</footer>
		</div>
	</div>

<script src="${CONTEXT}v1.0/js/jquery-1.8.3.min.js"></script>
<script src="${CONTEXT}v1.0/js/activity/layer.js"></script>
<script type="text/javascript">
var CONTEXT = "${CONTEXT}";
var activityId = '${activityId}';
var unionid = '${unionid}';
var openId = '${openId}';
var userid = '${userid}';
function inFo(str) {
	layer.open({
	title:'1',
    content: str,
    btn: ['好'],
	shadeClose: true,
	});
}
function toGrabRedPacket(){
	window.location.href = CONTEXT+'activity/initRedPacketActivityData?activityId=' + activityId + "&unionid=" + unionid + "&openId=" + openId;
}

function toExchange(){
	if (!userid){
		inFo("您还未登录..");
		return false ;
	}
	window.location.href = CONTEXT+'activity/turnToExchangeGiftPage?activityId=' + activityId + "&userid=" + userid;
}

	var itH = $(document).height();
	var wH = $('.main-wrap').height();
	if(itH>wH){
		$('.mui-bot').css({"padding-top":itH-wH+"px"})

	};

function pop() {
	layer.open({
	title:'1',
    content: '礼品已经被抢光啦！<br/>赶紧兑换其它礼品吧！',
    btn: ['确认', '取消'],
    shadeClose: true,
});

};
</script>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${CONTEXT}js/weixin.js"></script>
<script src="${CONTEXT}v1.0/js/activity/weixin.js"></script>
</html>
