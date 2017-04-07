<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ include file="/WEB-INF/jsp/pub/cache.inc" %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${CONTEXT}">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="format-detection" content="telephone=no">
	<title>相约谷登年度产销盛会</title>
	<link rel="stylesheet" href="activity/jpgys/css/index.css" />
</head>
<body>
<img src="activity/jpgys/images/share.png" alt="" style="display: none;">
<div class="main-panel">
	<div id="mainWrap" class="main-wrap">
		<img id="ruleBtn" src="activity/jpgys/images/rule-btn.png" alt="" class="rule-btn">
		<img class="text-zan" src="activity/jpgys/images/text-zan.png" />
		<!-- 分享未登录 -->
		<div class="share-no-login option" style="display: none">
			<p>好友<em class="dark-yellow star-nickname">微信</em>邀请你为他点赞</p>
			<img class="btn-praise btn" src="activity/jpgys/images/btn-praise.png" />
			<p>好友总点赞数：<em class="yellow star-count">0</em>人</p>
			<img id="joinBtn" class="btn-join btn" src="activity/jpgys/images/btn-join.png" />
			<p>还没有账号？现在去 <a id="registerBtn" class="regi">注册</a></p>
		</div>
		<!-- 分享已登录 -->
		<div class="share-is-login option" style="display: none">
			<p>好友<em class="dark-yellow star-nickname">微信</em>邀请你为他点赞</p>
			<img class="btn-praise btn" src="activity/jpgys/images/btn-praise.png" />
			<p>好友总点赞数：<em class="yellow star-count">0</em>人</p>
			<img id="shareBtn" class="btn shareBtn" src="activity/jpgys/images/share-btn.png" />
			<p><a id="returnBtn" class="return">返回个人活动界面</a></p>
		</div>
		<!-- 个人首页未登录 -->
		<div class="main-no-login option" style="display: none">
			<img class="btn shareBtn" src="activity/jpgys/images/share-btn.png" />
			<img id="joinBtn2" class="btn-join btn" src="activity/jpgys/images/btn-join.png" />
			<p>还没有账号？现在去 <a id="registerBtn2" class="regi">注册</a></p>
		</div>
		<!-- 个人首页已登录 -->
		<div class="main-is-login option" style="display: none">
			<p>我获得的点赞数：<em class="yellow star-count-my">0</em>人</p>
			<p>我的积分<em class="yellow score-total" id="score-total">0</em>分，已邀请<em class="yellow register-total">0</em>个好友注册</p>
			<img class="btn shareBtn" src="activity/jpgys/images/share-btn.png" />
		</div>
		<img class="text-main" src="activity/jpgys/images/text-main.png" />
		<a href="http://www.gdeng.cn/cdgys/nsyGys.html"><img class="btn-jin btn" src="activity/jpgys/images/btn-jin.png" /></a>
	</div>
</div>
	<!-- 登录弹出框 -->
	<div class="login-wrap">
		<div class="alert-box">
			<img id="loginClose" class="close" src="activity/jpgys/images/close.png" alt="">
			<p class="title">谷登账户登录</p>
			<form class="login-form">
				<div class="field">
					<label>手机号码</label>
					<input id="login-mobile" type="tel" maxLength="11" />
				</div>
				<div class="field">
					<label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label>
					<input id="login-password" type="password" />
				</div>
				<div>
					<img id="loginSubmit" class="submit sm-btn submit-login" src="activity/jpgys/images/login-btn.png" alt="登录">
				</div>
			</form>
			<p class="tip">还没有谷登账号？先去 <a id="regiBtn" class="regi">注册</a> 拿积分吧！</p>
		</div>
	</div>
	<!-- 注册弹出框 -->
	<div class="register-wrap">
		<div class="alert-box">
			<img id="registerClose" class="close" src="activity/jpgys/images/close.png" alt="">
			<p class="title">谷登账户注册</p>
			<form class="login-form">
				<div class="field">
					<label>手机号码</label>
					<input id="register-mobile" type="tel" maxLength="11" />
				</div>
				<div class="field">
					<label>验&nbsp;&nbsp;证&nbsp;&nbsp;码</label>
					<div class="verify">发送验证码</div>
					<input id="register-ver-code" class="input-verify" type="tel" maxlength="6" />
				</div>
				<div class="field">
					<label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 码</label>
					<input id="register-password" type="password" />
				</div>
				<div class="field">
					<label>确认密码</label>
					<input id="register-password-confirm" type="password" />
				</div>
				<div>
					<img id="registerSubmit" class="submit sm-btn submit-register" src="activity/jpgys/images/register.png" alt="注册">
				</div>
			</form>
			<p class="tip">已经有谷登账号，现在去 <a id="loginBtn" class="regi">登录</a></p>
		</div>
	</div>
	<!-- 活动规则，排行榜，兑换弹出框 -->
	<div class="rule-wrap">
		<img id="ruleClose" class="rule-close" src="activity/jpgys/images/close2.png" alt="">
		<header id="tabs" class="head">
			<span class="tab active" tab="rule">活动说明</span>
			<span class="tab" tab="list" id="rankingList">排行榜</span>
			<span class="tab" tab="exchange" id="goExchange">礼品兑换</span>
			<span class="tab" tab="myGift">我的礼品</span>
		</header>
		<div class="rule-content">
		<div class="tab-rule tab-wrap">
			<p class="title">活动流程 </p>
			<p>a)	分享：通过金牌供应商会员活动页面的分享按钮，将活动成功分享至微信朋友圈。<br/>
				b)	积分：活动期间，满足积分要求即可获得相应积分，累计不同积分可兑换相应礼品 。<br/>
				c)	未注册用户需注册成为供应商会员再参与活动，已注册用户（包括农商友、供应商、农批商用户）可直接参与活动。</p>
			<p class="title">活动参与有效日期</p>
			<p>2016年11月23日—2016年12月31日</p>
			<p class="title">礼品兑换有效日期</p>
			<p>2016年12月6日—2017年1月20日<br/>
				（温馨提示：在有效期内，积分累计越高可兑换的礼品越丰富）</p>
			<p class="title">积分累计规则</p>
			<table>
				<thead>
				<tr>
					<td style="width: 1rem;">积分内容</td>
					<td style="width: 1rem;">积分标准</td>
					<td>积分规则</td>
				</tr>
				</thead>
				<tr>
					<td>分享活动</td>
					<td>10积分</td>
					<td>在活动页面通过分享按钮将活动成功分享至朋友圈，即可得10积分</td>
				</tr>
				<tr>
					<td>登录账号</td>
					<td>5积分</td>
					<td>在活动页面成功登录供应商用户，即可得5积分</td>
				</tr>
				<tr>
					<td>注册用户</td>
					<td>10积分</td>
					<td>在活动页面成功注册供应商用户，即可得10积分；被邀请注册成功后，双方均可得10积分</td>
				</tr>
				<tr>
					<td>购买会员</td>
					<td>100积分</td>
					<td>用户购买金牌会员，即可得100积分；通过首位分享链接成功购买金牌会员，双方均可得100积分</td>
				</tr>
			</table>
			<p>（注：已兑换的积分则会相应减去，未用积分可在有效期内一直累计） </p>

			<p class="title">积分兑换标准</p>
			<table>
				<thead>
				<tr>
					<td style="width: 1rem;">所需积分</td>
					<td>服务奖</td>
					<td>礼品奖</td>
				</tr>
				</thead>
				<tr>
					<td>30积分</td>
					<td>10元红包</td>
					<td>10元红包</td>
				</tr>
				<tr>
					<td>50积分</td>
					<td>15元红包</td>
					<td>15元红包</td>
				</tr>
				<tr>
					<td>130积分</td>
					<td>2个农批商电话+3个月会员服务期间</td>
					<td>80元话费</td>
				</tr>
				<tr>
					<td>380积分</td>
					<td>1个市场产销对接会名额</td>
					<td>富安娜4件套</td>
				</tr>
				<tr>
					<td>580积分</td>
					<td>2个市场产销对接会名额</td>
					<td>九阳豆浆机</td>
				</tr>
				<tr>
					<td>1080积分</td>
					<td>4个市场产销对接会</td>
					<td>红米手机</td>
				</tr>
				<tr>
					<td>1500积分</td>
					<td>6个市场产销对接会名额</td>
					<td>100g 999千足银</td>
				</tr>
			</table>
            <p>（注：同一奖品只可兑换一次；同一积分兑换，服务奖和礼品奖二选一 ）  </p>

			<p class="title">其他说明</p>
			<p>1)	本次活动的最终解释权归主办方深圳谷登科技有限公司所有。<br/>
				2)	对于任何通过不正当手段参加活动者，主办方有权在不事先通知的前提下取消其参加活动的资格。<br/>
				3)	如遇不可抗因素，主办方有权提前结束或终止本次活动。<br/>
				4)	每个用户只能获得一次分享积分。<br/>
				5)	如有任何疑问，可以联系客服，电话：<a href="tel:4007-600-800">4007-600-800</a></p>
		</div>
		<div class="tab-list tab-wrap" style="display: none;">
			<p>当前排名：<span id="cuRanking">无</span></p>
			<table style="display: none" id="listRankingTable">
				<thead>
				<tr>
					<td>排行</td>
					<td>头像</td>
					<td>昵称</td>
					<td>累计积分</td>
				</tr>
				</thead>
				<tbody>
				
				<!-- <tr>
					<td>1</td>
					<td><img src="activity/jpgys/images/pic.png" alt="" class="user-pic"></td>
					<td>张三</td>
					<td>30积分</td>
				</tr>
				<tr>
					<td>1</td>
					<td><img src="activity/jpgys/images/pic.png" alt="" class="user-pic"></td>
					<td>张三</td>
					<td>30积分</td>
				</tr>
				<tr>
					<td>1</td>
					<td><img src="activity/jpgys/images/pic.png" alt="" class="user-pic"></td>
					<td>张三</td>
					<td>30积分</td>
				</tr> -->
				</tbody>
			</table>
		</div>
		<div class="tab-exchange tab-wrap">
			<p class="title">礼品兑换方式 </p>
			<p>兑换方式：统一在农商友微信公众号中兑换。<br/>
				兑奖流程：进入农商友微信公众号，点击底部菜单栏“近期活动”，然后点击“活动礼品兑换”，进入活动礼品兑换页面。<br/>
				已关注农商友微信公众号用户，可直接进入页面兑换礼品。<br/>
				未关注农商友微信公众号用户，需先关注公众号。</p>
			<p class="title">礼品兑换</p>
			<p>当前可用积分：<em class="t-yellow"><span id="currentScore">--</span>分</em></p>
			<table class="align-l-col-1" id="exchangeGiftTable">
				<thead>
				<tr>
					<td>礼品名称</td>
					<td>所需积分</td>
					<td>兑换操作</td>
				</tr>
				</thead>
				<tbody>
				<!-- <tr>
					<td>10元红包</td>
					<td>30积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>15元红包</td>
					<td>50积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>80元话费</td>
					<td>130积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>2个农批商电话+3个月会员服务期间</td>
					<td>130积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>富安娜4件套</td>
					<td>380积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>1个市场产销对接会名额</td>
					<td>380积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>九阳豆浆机</td>
					<td>580积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>2个市场产销对接会名额</td>
					<td>580积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>红米手机</td>
					<td>1080积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>4个市场产销对接会名额</td>
					<td>1080积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>100g 999千足银</td>
					<td>1500积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>
				<tr>
					<td>6个市场产销对接会名额</td>
					<td>1500积分</td>
					<td><a class="exchange-btn exchange-follow">兑换</a></td>
				</tr>  -->
				</tbody>
			</table>
			<p class="title">兑奖说明</p>
			<p>请在有效期内及时兑换，否则积分将自动失效。同一礼品，仅限兑换一次。</p>
			
			<p class="title">最新兑换记录</p>
			<!-- 没有兑换记录 -->
			<p id="noRecordList"></p>
			<!-- 有兑换记录时 -->
			<table class="align-l-col-2" id="newExchangeGiftTable">
				<thead>
				<tr>
					<td>手机号码</td>
					<td>兑换礼品</td>
					<td>兑换时间</td>
				</tr>
				</thead>
				<tbody>
				<!-- <tr>
					<td>156****1234</td>
					<td>10元红包</td>
					<td>2016-12-08</td>
				</tr>
				<tr>
					<td>156****1234</td>
					<td>1个市场产销对接会名额</td>
					<td>2016-12-08</td>
				</tr>
				<tr>
					<td>156****1234</td>
					<td>10元红包</td>
					<td>2016-12-08</td>
				</tr> -->
				</tbody>
			</table>
		</div>
		<div class="tab-myGift tab-wrap">
			<p>当前可用积分：<em class="t-yellow"><span id="myGiftCurrentScore">--</span>分</em></p>
			<p class="title">我的兑换记录 </p>
			<ul class="record-list">
				<!-- <li class="record-item">
					<img src="activity/jpgys/images/gift.png" alt="" class="gift-pic" />
					<div class="record-content">
						<p>礼品名称：10元红包</p>
						<p>消费积分：50积分</p>
						<p>兑换时间：2016年12月7日</p>
					</div>
				</li>
				<li class="record-item">
					<img src="activity/jpgys/images/gift.png" alt="" class="gift-pic" />
					<div class="record-content">
						<p>礼品名称：10元红包</p>
						<p>消费积分：50积分</p>
						<p>兑换时间：2016年12月7日</p>
					</div>
				</li> -->
			</ul>
			<div class="no-record" style="display: none;">
				<p>我还没有兑换过任何礼品</p>
			</div>
			<div class="reminder">
				<p class="title">温馨提示：</p>
				<p>1. 礼品兑换时间为2016年12月6日-2017年1月20日；<br/>
					2. 礼品数量以库存为准，先到先得哦！<br/>
					3. 兑换结束后所有用户的积分将会清零；<br/>
					4. 赶快关注“农商友”微信公众号，查看底部菜单栏的“近期活动”领取礼品吧！</p>
			</div>
			<div align="center"><img class="btn shareBtn" src="activity/jpgys/images/share-btn.png" /></div>
		</div>
		</div>
	</div>
	<!-- 提示框 -->
	<div class="tip-wrap">
		<div class="tip-login alert-box">
			<img class="tip-close" src="activity/jpgys/images/close.png" alt="">
			<p class="tip-title">登录成功！</p>
			<p class="tip-content">恭喜您获得5个登录积分！<br/>快去邀请您的好友，赚取更多积分吧！</p>
			<img class="sm-btn shareBtn" src="activity/jpgys/images/share-friend.png" alt="">
		</div>
		<div class="tip-register alert-box">
			<img class="tip-close" src="activity/jpgys/images/close.png" alt="">
			<p class="tip-title">注册成功！</p>
			<p class="tip-content">恭喜您获得10个注册积分！<br/>快去邀请您的好友，赚取更多积分吧！</p>
			<img class="sm-btn shareBtn" src="activity/jpgys/images/share-friend.png" alt="">
		</div>
		<div class="tip-follow alert-box">
			<img class="tip-close" src="activity/jpgys/images/close.png" alt="">
			<p class="tip-content">关注公众号，领取奖品</p>
			<img id="followBtn" class="sm-btn" src="activity/jpgys/images/follow.png" alt="">
		</div>
		<div class="tip-qrcode alert-box">
			<img class="tip-close" src="activity/jpgys/images/close.png" alt="">
			<img class="sm-btn" src="activity/jpgys/images/qrcode.png" alt="">
		</div>
	</div>
	<div class="share-wrap">
		<img src="activity/jpgys/images/share-tip.png" alt="" class="share-img">
	</div>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
<script>
	var CONTEXT = "${CONTEXT}";
	var activityId = '${activityId}';
	var unionid = '${unionid}';
	var openId = '${openId}';
	var invitorOpenid = '${invitorOpenid}';
	var userid = '${userid}';
</script>
	<script src="activity/jpgys/lib/jquery-3.1.1.min.js"></script>
	<script src="activity/jpgys/lib/jweixin-1.0.0.js"></script>
	<script src="activity/jpgys/js/weixin.js"></script>
	<script src="activity/jpgys/js/resize.js"></script>
	<script src="activity/jpgys/js/index.js"></script>
	<script src="activity/jpgys/js/customer.js"></script>
</body>


</html>