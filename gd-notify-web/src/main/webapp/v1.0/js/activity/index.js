/***产生图片验证码***/
function initRand() {
	var rand = Math.random();
	var url = CONTEXT + "userManage/initImg?r=" + rand;
	$("#init-img-rand").attr("src", url);
}
/*赚钱秘籍 活动提示切换*/

$(document).ready(function() {
	//点击赚钱秘籍
	$("#cheats").tap(function(){
		$(this).children('p').removeClass('cheats_hidden_p').addClass('cheats_show_p');
		$("#tip p").removeClass('tip_show_p').addClass('tip_hidden_p');
		$("#tips_content").fadeOut();
		$("#cheats_content").fadeIn();
	})
	//点击活动提示
	$("#tip").tap(function(){

		$(this).children('p').removeClass('tip_hidden_p').addClass('tip_show_p');
		$("#cheats p").removeClass('cheats_show_p').addClass('cheats_hidden_p');
		$("#cheats_content").fadeOut();
		$("#tips_content").fadeIn();
	})
});

/*图层消失*/
function layerHide(){
	$("body").css({"overflow-y": "auto"});
	$(".bgLayer").fadeOut('400', function() {
	});
	$(".layerCont").fadeOut('400', function() {
	});
}

/*背景图层弹出*/
function layerShow(){
	$("body").css({"overflow-y": "hidden"});
	$(".bgLayer").fadeIn('400', function() {
	});
}

/*点击背景图层*/
$(document).ready(function() {
	$("#bgLayer").tap(function(){
		layerHide();
	})
});



/*点击关注我们关闭按钮*/
$(document).ready(function() {
	$(".pocketClose").tap(function(){
		layerHide();
	})
});

/* 消息关闭按钮*/
$(document).ready(function() {
	$(".messageOk").tap(function(){
		layerHide();
	})
});


/*点击关注我们*/
$(document).ready(function() {
	$("#attBut").tap(function(){
		layerHide();
		layerShow();
		$("#fingerPrint").fadeIn('400');
		return false;
	})
});

/*长按识别二维码*/
/*$(document).ready(function() {
	$("#fingerImg").on("taphold",function(){
	 	layerHide();
		layerShow();
		$("#userLogin").fadeIn('400');
		return false;
	});
});*/

/*点击注册*/
$(document).ready(function() {
	$("#register").tap(function(){
		layerHide();
		layerShow();
		initRand();
		$("#accountReg").fadeIn('400');
		return false;
	})
});
/*点击抢红包*/
/*$(document).ready(function() {
	$(".redPocket").tap(function(){
		layerHide();
		layerShow();
		$("#getMoney").fadeIn('400');
		return false;
	})
});*/

/*点击登入*/
$(document).ready(function() {
	$("#toLog").tap(function(){
		layerHide();
		layerShow();
		$("#userLogin").fadeIn('400');
		return false;
	})
});
/*点击抢红包*/

$(document).ready(function(){
	$("#attUs").click(function(){
		toGrabRedPacket();
		//设置标志, 禁止频繁点击抢红包按钮
		preventMultiGrabSign = false ;
	})
});

/*点击分享给好友*/
$(document).ready(function() {
	$(".inviteFriends").tap(function(){
		layerHide();
		layerShow();
		$("#shareTip").fadeIn('400');
		return false;

	})
});

function gameOver(){
	// 弹出活动结束，尽快兑换礼物
	layerHide();
	layerShow();
	$("#gameOver").fadeIn('400');
	return false;
}
function toGrabRedPacket(){
	if (!preventMultiGrabSign){
		inFo("您正在抢红包中, 请耐心等待..");
		return ;
	}
	 $.ajax( {
		    url:CONTEXT+'activity/grabRedPacket',
		    data:{
		    	"activityId" : activityId, "userid" : userid
		    },
		    type:'post',
		    dataType:'json',
		    success:function(data) {
		    	preventMultiGrabSign = true ;
				if (data){
					if (data.status == 0){
						if (data.errorCode == 1){//未登录
							layerShow();
							$("#userLogin").fadeIn('400');
							return;
						}else if (data.errorCode == 2){//未关注
							layerShow();
							$("#attentionUs").fadeIn('400');
							return false;
						}else if (data.errorCode == 3){//红包空了
								layerShow();
								$("#noRedPacket").fadeIn('400');
								return false;
						}else if (data.errorCode == 4){//活动结束(launch == 0)
							//弹框
							gameOver();
						}else if (data.errorCode == 5){//兑奖时间结束
							window.location.href = CONTEXT+'activity/activityEnd?activityId=' + activityId ;
						}else{
							inFo("errorCode : " + data.errorCode + ", message :  " + data.message);
						}
					}else if (data.status == 1){
						if (data.isFirst){
							$(".prosperity").html("恭喜发财!");
						}else{
							$(".prosperity").html("人品爆发!");
						}
						$(".money").html(data.score + "元");
						$("#userScore").text(data.userScore);
						$("#chanceCount").text(data.chanceCount);
						$(".prosperity").html("恭喜发财!");
						layerShow();
						$("#getMoney").fadeIn('400');
					}
				}else{
					inFo("error");
				}
		     },
		     error : function(data) {
		    	 inFo("error!");
		     }
		});
}

/*轮转定时器*/

var tempSc;
$(document).ready(function() {
	setInterval("myInterval()",4000);//4000为4秒钟
});

function myInterval()
{
	var scrollMark = $(".scrollOne").data('scroll');
	/*console.log(scrollMark);*/
	if(scrollMark==0){ //第一个层的内容
		var $_scr=$(".scrollOne").next();
		$(".scrollOne").hide('400');
		$_scr.show();
		$(".scrollOne").data('scroll','1');
		tempSc = $_scr;
	}else if (scrollMark==1) { //最后一个层的内容
		var $_scr=tempSc;
		if($_scr.hasClass('scrollLast')){
			$_scr.hide("400");
			$(".scrollOne").show('400');
			$(".scrollOne").data('scroll','0');
			tempSc=$(".scrollOne");
		}else{					//中间层的内容
			var $_scrNext=$_scr.next();
			$_scr.hide("400");
			$_scrNext.show("400");
			$(".scrollOne").data('scroll','1');
			tempSc=$_scrNext;
		}

	}
}

