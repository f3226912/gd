<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/tags.inc"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>兑换礼品</title>
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
				<h3 class="integral-gift">我的积分 <strong>${score}</strong> 分</h3>
				<div class="awards">
					<p>活动奖项</p>
					<table class="gridtable">
						<tr>
							<th>礼品</th>
							<th>所需积分</th>
							<th>数量</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${giftList}" var="gift">
							<tr>
								<td>${gift.giftName}</td>
								<td>${gift.exchangeScore}</td>
								<td>${gift.cost}</td>
								<td><a href="#" onclick="exchange('${gift.activityId}','${gift.giftId}','${gift.giftName}','${gift.exchangeScore}')">兑换</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="awards awards-2">
					<p>最新兑换记录</p>
					<table class="gridtable">
						<tr>
							<th>手机号码</th>
							<th>兑换礼品</th>
							<th>兑换时间</th>
						</tr>
						<c:forEach items="${recordlist}" var="record">
							<tr>
								<td>${record.mobile}</td>
								<td>${record.giftName}</td>
								<td>${record.exchangeTime}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
	</div>

	<div class="footer">
		<footer>
			<ul>
				<li><a class="footer-a" href="javascript:void(0);" onclick="toGrabRedPacket()">抢红包</a></li>
				<li><a class="footer-a" href="javascript:void(0);">兑换礼品</a></li>
				<li><a class="footer-a" href="javascript:void(0);" onclick="toMyGift()">我的礼品</a></li>
			</ul>
		</footer>
	</div>
<!--<script src="static/js/jquery.mobile.custom.min.js"></script>-->
<script src="${CONTEXT}v1.0/js/activity/layer.js"></script>
<script src="${CONTEXT}v1.0/js/jquery-2.1.4.min.js"></script>
<script>
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
function toMyGift(){
	if (!userid){
		inFo("您还未登录..");
		return false ;
	}
	window.location.href = CONTEXT+'activity/myGift?activityId=' + activityId + "&userid=" + userid;
}

function confirm(text, callback, params) {
	var pop = layer.open({
		title:'',
	    content: text,
	    btn: ['确认', '取消'],
	    yes:function(){
	    	if (callback && typeof callback == "function"){
	    		callback(params.activityId, params.giftId);
				//doExchange(params.activityId, params.giftId);
	    	}
			layer.close(pop);
	    },
	    no:function(){
			layer.close(pop);
	    },
	    shadeClose: true,
	});
};

function doExchange(activityId, giftId){
	//兑换礼品
 	$.ajax({
		url : '${CONTEXT}'+'activity/exchangeGift',
		data : {"userid" : userid, "activityId" : activityId, "giftId" : giftId},
		type:'post',
		dataType:'json',
		success:function(data) {
			if (data){
				if (data.status == 0){
					if (data.errorCode == 3){
						confirm("礼品已经被抢光啦！<br/>赶紧兑换其它礼品吧！");
					}else if (data.errorCode == 4){
						confirm("您的红包金额不足<br/>赶紧邀请更多好友获得红包");
					}else if (data.errorCode == 5){
						confirm("不在兑换时间内, 不能兑换礼品<br/>");
					}else{
						//inFo("errorCode : " + data.errorCode + ", message :  " + data.message);
						inFo(data.message);
					}
				}else{
					inFo("兑换成功! ");
				}
			}else{
				inFo("error");
			}
		},
		error : function() {
		}
	});
}
function exchange(activityId, giftId, giftName, exchangeScore){
	var text = "您兑换" + giftName + "将扣除积分" + exchangeScore + "分";
	var params = {"activityId" : activityId, "giftId" : giftId};
	confirm(text,doExchange, params);
}




</script>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${CONTEXT}js/weixin.js"></script>
<script src="${CONTEXT}v1.0/js/activity/weixin.js"></script>
</html>
