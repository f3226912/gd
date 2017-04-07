/**
 * 活动页面与微信相关操作
 *
 * @author lidong
 */
// 只能微信浏览器打开
isWeiXin();
wx.ready(function() {
	wx.hideAllNonBaseMenuItem();
	wx.showMenuItems({
		menuList : [ "menuItem:share:appMessage", "menuItem:share:timeline",
				"menuItem:profile" ]
	// 要显示的菜单项，所有menu项见附录3
	});

	// 获取“分享给朋友”按钮点击状态及自定义分享内容接口
	wx.onMenuShareAppMessage({
		title : '谷登送红包，根本停不下来', // 分享标题
		desc : '谷登狂欢，一大波儿红包来袭，友谊的小船不会翻！', // 分享描述
		link : CONTEXT + "invite/1/" + activityId + "/" + openId, // 分享链接
		imgUrl : CONTEXT + 'images/activity/share1.jpg', // 分享图标
		type : '', // 分享类型,music、video或link，不填默认为link
		dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
		success : function(res) {
			// 用户确认分享后执行的回调函数
//			if (res.errMsg.indexOf("sendAppMessage:ok") > -1) {
//				console.log("分享成功:" + res.errMsg);
				var params = {
					activityId : activityId,
					shareType : 1,
					unionid : unionid,
					openid : openId,
					userid : userid
				};
				shareCallBack(CONTEXT + "share", params);
//			} else {
//				console.log("分享失败：" + res);
//			}
		}
	});
	// 分享给朋友圈
	wx.onMenuShareTimeline({
		title : '谷登送红包，根本停不下来', // 分享标题
		link : CONTEXT + "invite/2/" + activityId + "/" + openId, // 分享链接
		imgUrl : CONTEXT + 'images/activity/share1.jpg', // 分享图标
		success : function(res) {
			// 用户确认分享后执行的回调函数
//			if (res.errMsg.indexOf("shareTimeline:ok") > -1) {
				var params = {
					activityId : activityId,
					shareType : 2,
					unionid : unionid,
					openid : openId,
					userid : userid
				};
				shareCallBack(CONTEXT + "share", params);
//			} else {
//				console.log("分享失败：" + res);
//			}
		}
	});

});

document.oncontextmenu = new Function("event.returnValue=false;");
document.onselectstart = new Function("event.returnValue=false;");

$(function() {
	/**
	 * 登录绑定
	 */
	$("#login-getPocket").click(
			function() {
				var mobile = $("#login-mobile").val().trim();
				var password = $("#login-password").val().trim();
				if (mobile && password) {
					$.ajax({
						type : 'POST',
						url : CONTEXT + "userManage/login",
						data : {
							"mobile" : mobile,
							"password" : password,
							"openId" : openId,
							"activityId" : activityId,
							"unionid" : unionid,
							"memberId" : userid,
							"invitorOpenid" : invitorOpenid
						},
						dataType : 'json',
						success : function(data) {
							var result = data.msg;
							if (result == 'success') {
								layerHide();
								layerShow();
								$("#getMoney").fadeIn('400');
								userid = data.userid;
								$("#userScore").text(2);
								return false;
							} else if (result == 'gameover') {
								// 活动结束
								window.location.href = CONTEXT
										+ "activity/activityEnd?activityId="
										+ activityId;
							} else if (result == 'gift') {
								// 弹出活动结束，尽快兑换礼物
								gameOver();
							} else {
								layerHide();
								inFo(result);
							}
						}
					});
				} else {
					return;
				}
			});

	/**
	 * 注册绑定
	 */
	$("#register-getPocket").click(
			function() {
				var mobile = $("#register-mobile").val().trim();
				var password = $("#register-password").val().trim();
				var code = $("#register-ver-code").val().trim();
				var password = $("#register-password").val().trim();
				var confirmPassword = $("#register-password-confirm").val()
						.trim();
				if (mobile && code && password && confirmPassword) {
					if (password != confirmPassword) {
						inFo("两次密码不一致");
						return;
					}
					$.ajax({
						type : 'POST',
						url : CONTEXT + "userManage/register",
						data : {
							"mobile" : mobile,
							"password" : password,
							"openId" : openId,
							"activityId" : activityId,
							"unionid" : unionid,
							"invitorOpenid" : invitorOpenid,
							"code" : code
						},
						dataType : 'json',
						success : function(data) {
							var result = data.msg;
							if (result == 'success') {
								layerHide();
								layerShow();
								$("#getMoney").fadeIn('400');
								userid = data.userid;
								$("#userScore").text(2);
								return false;
							} else if (result == 'gameover') {
								// 活动结束
								window.location.href = CONTEXT
										+ "activity/activityEnd?activityId="
										+ activityId;
							} else if (result == 'gift') {
								// 弹出活动结束，尽快兑换礼物
								gameOver();
							} else {
								layerHide();
								inFo(result);
							}
						}
					});
				} else {
					return;
				}
			});


	//可以发送验证码
	var flag=false;

	function expireTime(flag,seconds){
		if(flag){
			$("#send-ver").text(seconds+"(s)");
		}else{
			$("#send-ver").text("发送验证码");
		}
	}

	/** 发送验证码** */
	$("#send-ver").click(function() {
		var mobile = $("#register-mobile").val().trim();
		var rand = $("#register-rand").val().trim();
		if (mobile && rand) {
			$.ajax({
				type : 'POST',
				url : CONTEXT + "userManage/sendCode",
				data : {
					"mobile" : mobile,
					"rand" : rand
				},
				dataType : 'json',
				success : function(data) {
					var result = data.msg;
					if (result == 'success') {
						return;
					} else {
						inFo(result);
					}
				}
			});
		} else {
			if(!mobile){
				inFo("手机号不可为空");
			}else if(!rand){
				inFo("图片验证码不可为空");
			}else {
				inFo("未知错误");
			}
			return;
		}
	});

	/** 产生验证码** */
	$("#init-img-rand").click(function() {
		initRand();
	});
});
