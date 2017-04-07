<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<meta name="format-detection" content="telephone=no" />
		<title>谷登送红包</title>
		<link rel="stylesheet" href="${CONTEXT}/v1.0/css/grabEnvelope.css">
		<style type="text/css">

		</style>
	</head>
	<body oncontextmenu='return false' ondragstart='return false' onselectstart ='return false' onselect='document.selection.empty()' oncopy='document.selection.empty()' onbeforecopy='return false'>
		<div class="outline">
			<div class="row">
				<img class="width_100" src="${CONTEXT}v1.0/images/envTop.png" alt="谷登送红包">
				<div class="int_lot color_ffffff">
					我的积分<span class="bg_ffd800 integration color_ff2935 padding_1_4" id="userScore">${score}</span>我还有<span class="lottery" id="chanceCount">${chanceCount}</span>次抽奖机会
				</div>

				<div class="two_button">
					<img id="attUs" src="${CONTEXT}v1.0/images/wantGrab.png" alt="我要抢红包">
					<img class="inviteFriends" src="${CONTEXT}v1.0/images/inviteFriends.png" alt="邀请好友">
				</div>

			</div>
			<div class="row row_attention color_ffffff">
				关注农商友微信公众号：<span class="color_fff100">nsy166</span>报名参与活动，即可获得<span class="color_fff100">2</span>元红包！<span class="color_fff100">邀请好友参加得更多红包！</span>
			</div>
			<div class="row">
				<div class="tab ">
					<div class="tab_top clearfix">
						<div id="cheats" class="cheats">
							<p class="cheats_hidden_p">赚钱秘籍</p>
						</div>
						<div id="tip" class="tip">
							<p class="tip_show_p">活动提示</p>
						</div>
					</div>

					<div id="cheats_content" class="cheats_content display_n">
						<ul>
							<li>1. 参加活动需要关注农商友微信公众号：nsy166</li>
							<li>2. 进入公众号：点击—微信红包，报名即可得2元红包，不可重复报名；</li>
							<li>3. 想要获得更多红包，可邀多个微信好友参与，好友获得红包，邀请人也获得随机红包。
							比如，小明参加活动获得2元红包，邀请小红参加，那么小红和小明都能得到一个红包，小红又邀请小菊参加活动，那么小明、小红、小菊都能得到红包以此类推，朋友越多，红包越多。</li>
							<li>4、活动期间，每个用户只限兑换一种礼品，请根据红包金额选择礼品兑换。</li>
							<li>5、本活动最终解释权归深圳谷登科技有限公司。</li>
						</ul>

					</div>

					<div id="tips_content" class="tips_content">
						<ul>
							<li>1、活动时间：6月18日-6月27日</li>
							<li>2、礼品兑换时间：6月18日-6月30日</li>
							<li>3、礼品数量以库存为准，先到先得哦！</li>
							<li>4、兑换结束后所有用户的红包金额清零。</li>
							<li>5、所有礼品均在兑换结束后3个工作日后统一发放。</li>
						</ul>
					</div>

				</div>

			</div>
			<div class="row row_tel_per">
				<ul class="tel_per">
					<li>
						<div class="inner_tel_per">
							<div class="scrollOne" data-scroll="0">
								<span>181****5621</span><span> 刚获得3个积分</span>
							</div>
							<div class="scrollTwo display_n scrollMiddle">
								<span>158****2633</span><span> 刚获得1个积分</span>
							</div>
							<div class="scrollThree display_n scrollMiddle">
								<span>137****1100</span><span> 刚获得3个积分</span>
							</div>
							<div class="scrollFour display_n scrollMiddle">
								<span>136****2620</span><span> 刚获得2个积分</span>
							</div>
							<div class="scrollLast display_n">
								<span>158****1001</span><span> 刚获得2个积分</span>
							</div>
							<div class="inner_per">
								<span>参与人数</span>
								<span>${hitCount}</span>
								<span>人</span>
							</div>

						</div>
					</li>
				</ul>

			</div>

			<!-- 弹出层背景 -->
			<div id="bgLayer" class="bgLayer">

			</div>
			<!-- 关注我们弹出层 -->
			<div id="attentionUs" class="bgLayer_out layerCont">
				<div class="bgLayer_content bgLayerContent">
					<img id="attentionClose" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<p class="offical_account">关注农商友微信公众号 </p>
					<p class="red_pocket">报名参加活动即可获得2元红包</p>
					<button id="attBut" class="attention_us">关注我们</button>
				</div>
			</div>
			<!-- 长按指纹识别二维码 -->
			<div id="fingerPrint" class="finger_print bgLayer_out layerCont ">

				<div class="fin_con bgLayerContent">
					<p class="att_words">
					关注农商友公众号,立即报名抢红包
					</p>
					<div class="divCodeImg"><img class="codeImg" src="${CONTEXT}v1.0/images/QrCode.png" alt=""></div>
					<div class="divFingerImg">
						<img src="${CONTEXT}v1.0/images/fingerprint.png" alt="">
					</div>
					<p class="long_print">长按指纹&nbsp&nbsp识别二维码</p>

				</div>

			</div>

			<!-- 用户登入弹出层 -->
			<div id="userLogin" class="bgLayer_out layerCont">
				<div class="bgLayer_content bgLayerContent">
					<img id="attentionClose" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<p class="offical_account user_log">账户登录 </p>
					<div class="div-tel">
						<div class="tel-num">手机号码</div>
						<div class="num-input"><input id="login-mobile" type="text" minlength="6" maxlength="16"/></div>
					</div>
					<div class="div-pass">
						<div class="pas-words">密&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp码</div>
						<div class="num-input"><input id="login-password" type="password" minlength="6" maxlength="16"/></div>
					</div>
					<button id="login-getPocket" class="get-pocket">报名拿红包</button>
					<div class="to-register">还没有账号，现在去&nbsp&nbsp <a id="register" href="#">注册</a></div>
				</div>
			</div>
			<!-- 账户注册弹出层 -->
			<div id="accountReg" class="bgLayer_out layerCont">
				<div class="bgLayer_content bgLayerContent">
					<img id="" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<p class="offical_account user_log">账户注册 </p>
					<div class="div-tel">
						<div class="tel-num2">手机号码</div>
						<div class="num-input"><input id="register-mobile" type="text" /></div>
					</div>
					<div class="div-pass" style="padding-bottom: 10px;">
						<div class="pas-words2" style="line-height: 33px;">图片验证码</div>
						<div class="num-input"><input id="register-rand" class="ver-code" type="text" />
						<div class="send-ver send-pic" style="display: inline-block;width: 77px;position:relative;top:6px;height:26px;">
							<img id="init-img-rand" alt="图片验证码" src="">
						</div></div>
					</div>
					<div class="div-pass">
						<div class="pas-words2">手机验证码</div>
						<div class="num-input"><input id="register-ver-code" class="ver-code" type="text" />
						<span class="send-ver" id="send-ver">发送验证码</span></div>
					</div>

					<div class="div-pass">
						<div class="pas-words2">密码</div>
						<div class="num-input">
							<input type="password" id="register-password"/>
						</div>
					</div>

					<div class="div-con">
						<div class="tel-num">确认密码</div>
						<div class="num-input"><input type="password" id="register-password-confirm" /></div>
					</div>
					<button id="register-getPocket" class="get-poc ">报名拿红包</button>
					<div class="to-register">已经有账号，现在去&nbsp&nbsp <a id="toLog" href="#">登录</a></div>
				</div>
			</div>

			<!-- 2元红包弹出层 -->
			<div id="getMoney" class="bgLayer_out layerCont">
				<div class="bgLayer_content bgLayerContent">
					<img id="" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<div class="tow-yuan">
						<img class="grabBG" src="${CONTEXT}v1.0/images/grabBG.png" alt="">
						<div class="money">2元</div>
					</div>
					<p class="prosperity">恭喜发财 </p>
					<p class="red_pocket">邀请好友参加获得更多红包!</p>
					<button id="" class="attention_us inviteFriends">邀请好友</button>
				</div>
			</div>

			<!-- 分享提示弹出层 -->
			<!-- <div id="shareTip" class="bgLayer_out"> -->
				<div id="shareTip" class="share-tip layerCont">
					<img src="${CONTEXT}v1.0/images/shareTip.png" alt="">
				</div>
			<!-- </div> -->

			<!-- 红包空了弹出层 -->
			<div id="noRedPacket" class="bgLayer_out layerCont">
				<div class="bgLayer_content bgLayerContent">
					<img id="" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<div class="pocketNull">你的红包空了</div>
					<p class="red_pocket">邀请好友参加获得更多红包!</p>
					<button id="" class="attention_us  inviteFriends">邀请好友</button>
				</div>
			</div>
			<!-- 活动尚未开始弹出层 -->
			<div  class="bgLayer_out ">
				<div class="bgLayer_content layerCont">
					<img id="attentionClose" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<h1 class="activity-unstart">活动尚未开始 </h1>
					<p class="att-pub">请关注农商友公众号了解详情</p>
				</div>
			</div>
			<!-- 活动已结束弹出层 -->
			<div id="gameOver" class="bgLayer_out layerCont">
				<div class="bgLayer_content">
					<img id="attentionClose" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<h1 class="activity-unstart">红包已抢完</h1>
					<p class="att-pub">请抓紧时间兑换你的礼品哦！</p>
				</div>
			</div>
			<div  class="bgLayer_out layerCont msg-show">
				<div class="bgLayer_content message-out">
					<img id="attentionClose" class="pocketClose" src="${CONTEXT}v1.0/images/pocketClose.png" alt="">
					<h1 class="activity-unstart msg-title">msg</h1>
					<p class="attention_us msg-content messageOk message-ok">好</p>
				</div>
			</div>
		</div>
		<div class="row footer">
			<span class="footer-crab">抢红包</span>
			<span class="footer-lift" onclick="toExchange()">兑换礼品</span>
			<span class="footer-mine" onclick="toMyGift()">我的礼品</span>
		</div>

		<script src="${CONTEXT}v1.0/js/jquery-2.1.4.min.js"></script>
		<script src="${CONTEXT}v1.0/js/jquery.mobile.custom.min.js"></script>
		<script src="${CONTEXT}v1.0/js/activity/layer.js"></script>
		<script type="text/javascript">
		var CONTEXT = "${CONTEXT}";
		var activityId = '${activityId}';
		var unionid = '${unionid}';
		var openId = '${openId}';
		var invitorOpenid = '${invitorOpenid}';
		var userid = '${userid}';
		var preventMultiGrabSign = true ;
	/* 	function inFo(str) {
			layer.open({
			title:'1',
		    content: str,
		    btn: ['好'],
			shadeClose: true,
			});
		} */

		function inFo(str) {
			$(".msg-title").text(str);
			layerHide();
			layerShow();
			$(".msg-show").fadeIn('400');
			return false;
		}
		function toExchange(){
			if (!userid){
				inFo("您还未登录..");
				return false ;
			}
			window.location.href = CONTEXT+'activity/turnToExchangeGiftPage?activityId=' + activityId + "&userid=" + userid;
		}
		function toMyGift(){
			if (!userid){
				inFo("您还未登录..");
				return false ;
			}
			window.location.href = CONTEXT+'activity/myGift?activityId=' + activityId + "&userid=" + userid;
		}

		</script>

	</body>
	<script src="${CONTEXT}v1.0/js/activity/index.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="${CONTEXT}js/weixin.js"></script>
	<script src="${CONTEXT}v1.0/js/activity/weixin.js"></script>
</html>
