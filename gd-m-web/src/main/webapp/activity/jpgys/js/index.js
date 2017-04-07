// 提示框工具
$.extend($, {
	exchangeTip : function(title, text){
		$('.exchange-tip').remove();
		var html = '<div class="exchange-tip">'
					+ '<div class="alert-box">'
						+ '<img class="tip-close" src="activity/jpgys/images/close.png" alt="">'
						+ '<p class="tip-title">' + title + '</p>'
						+ '<p class="tip-content">' + text + '</p>'
					+ '</div>'
				+ '</div>';
		$("body").append(html);
		$('.exchange-tip').show();
	},
	confirm: function(title, text, callback){
		$('.exchange-tip').remove();
		var html = '<div class="exchange-tip confirm">'
					+ '<div class="alert-box">'
						+ '<img class="tip-close" src="activity/jpgys/images/close.png" alt="">'
						+ '<p class="tip-title">' + title + '</p>'
						+ '<p class="tip-content">' + text + '</p>'
						+ '<div class="tool-bar">'
							+ '<div class="btn cancel">取消</div>'
							+ '<div class="btn ok">确认</div>'
						+ '</div>'
					+ '</div>'
				+ '</div>';
		$("body").append(html);
		$('.exchange-tip').show();
		$('.alert-box .ok').off('touchstart').on('touchstart', callback);
	}
});

$(function(){
	// 为所有关闭按钮绑定点击事件
	$("body").on('touchstart', '.close', function(){
		$(this).parent().parent().hide();
	});

	// 登录框
	$("#joinBtn,#joinBtn2").on('touchstart', function(){
		$(".login-wrap").show();
	});
	$("#loginBtn").on('touchstart', function(){
		$(".register-wrap").hide();
		$(".login-wrap").show();
	});
	// 注册框
	$("#registerBtn,#registerBtn2").on('touchstart', function(){
		$(".register-wrap").show();
	});
	$("#regiBtn").on('touchstart', function(){
		$(".login-wrap").hide();
		$(".register-wrap").show();
	});
	// 详情框
	$("#ruleBtn").on('touchstart', function(){
		$(".rule-wrap").show();
	});
	$("#ruleClose").on('touchstart', function(){
		$(".rule-wrap").hide();
	});
	// 登录提示框
	/*$("#loginSubmit").on('touchstart', function(){
		$(".login-wrap").hide();
		$(".tip-wrap").show();
		$(".tip-login").show();
	});*/
	// 注册提示框
	/*$("#registerSubmit").on('touchstart', function(){
		$(".register-wrap").hide();
		$(".tip-wrap").show();
		$(".tip-register").show();
	});*/
	// 为所有提示框关闭按钮绑定点击事件
	$("body").on('touchstart', '.tip-close', function(){
		$(this).parent().parent().hide();
		$(this).parent().hide();
		$(".tip-wrap .alert-box").hide();
	});
	// 为所有确认框取消按钮绑定点击事件
	$("body").on('touchstart', '.alert-box .cancel', function(){
		$(".confirm").hide();
	});

	// 规则弹出框tab切换
	$("#tabs").on("touchstart", ".tab", function(){
		$(this).addClass("active").siblings().removeClass("active");
		$(".tab-wrap").hide();
		$(".tab-" + $(this).attr("tab")).show();
		if($(this).attr("tab")=="list"){
			getRankingList();
		}else if($(this).attr("tab")=="exchange"){
			getExchangeGift();
		}else if($(this).attr("tab")=="myGift"){
			getMyGift();
		}
	});
    /**
     * 排行榜页面
     */
     function getRankingList() {
    	$.ajax({
            type : 'POST',
            url : CONTEXT + "jpgys/activity/initRankingList",
            data : {
                "userid" : userid,
                "activityId" : activityId
            },
            dataType : 'json',
            success : function(data) {
                var result = data.status;
                if (result == 1) {
                	var currentUser = data.curActivityinfo;
                	if(currentUser){
                		var currentRanking = currentUser.rownum;
                		$("#cuRanking").text(currentRanking);
                	}else{
                		$("#cuRanking").text("无");
                	}
                	var rankingList = data.userActivityList;
                	if(rankingList && rankingList.length>0){
                		var htmlStr = "";
                		for (var i = 0;i<rankingList.length;i++) {
                			var nikeName = decodeURI(rankingList[i].nickname==null?"":rankingList[i].nickname);
                			var headUrl = rankingList[i].headimgurl==null?"":rankingList[i].headimgurl;
                			htmlStr+='<tr><td>'+(i+1)+'</td><td><img src="'+headUrl+'" alt="" class="user-pic"></td><td>'+nikeName+'</td><td>'+rankingList[i].score+'积分</td><tr>';
                		}
                		 $("#listRankingTable tbody").html("");
                		 $("#listRankingTable tbody").append(htmlStr);
                		 $("#listRankingTable").show();
                	}
         
                }else {
                    $.exchangeTip("提示",data.message);
                }
            },
            error : function() { 
            	 $.exchangeTip("提示",data.message);
             }  
        });
    }
   
    
    /**
     * 我的礼品页面
     */
     function getMyGift() {
    	$.ajax({
            type : 'POST',
            url : CONTEXT + "jpgys/activity/myGift",
            data : {
                "activityId" : activityId
            },
            dataType : 'json',
            success : function(data) {
            	//将关注的弹框隐藏
            	$(".tip-wrap").hide();
         		$(".tip-follow").hide();
                var result = data.status;
                $("#myGiftCurrentScore").text(data.score==null?"--":data.score);
                if(result == 1) {
                	//如果之前有显示让登录的界面，需要将其隐藏
               	 	$(".main-no-login").hide();
               	 	$(".login-wrap").hide();
               	 	$(".rule-wrap").show();
                	var giftList = data.myGiftList;
                	if(giftList && giftList.length>0){
                		var htmlStr = "";
	             		for (var i = 0;i<giftList.length;i++) {
	             			var dfExchangeTime = "";
	             			if(giftList[i].exchangeTime){
	             				var exchangeTime = giftList[i].exchangeTime;
	             				dfExchangeTime=exchangeTime.substring(0,4)+"年"+exchangeTime.substring(5,7)+"月"+exchangeTime.substring(8,10)+"日";
	             			}
	             			htmlStr+='<li class="record-item"><img src="activity/jpgys/images/gift.png" alt="" class="gift-pic" /><div class="record-content"><p>礼品名称：'+giftList[i].giftName+'</p><p>消费积分：'+giftList[i].score+'积分</p><p>兑换时间：'+dfExchangeTime+'</p></div></li>';
	             		}
	             		$(".record-list").html("");
	             		$(".record-list").append(htmlStr);
	             		$(".record-list").show();
                 		$(".no-record").hide();
                	}
                
                }else {
                	//未关注
                 	if(data.errorCode==2){
                 		$(".tip-wrap").show();
                 		$(".tip-follow").show();
                 	//未登录
                 	}else if(data.errorCode==1){
                 		 //alert("你还未登录，请先登录");
                 		 $(".option").hide();
                 		 $(".main-no-login").show();
                 		 $(".login-wrap").show();
                 		 $(".rule-wrap").hide();
                 	//礼品记录为空
                 	}else if(data.errorCode==3){
                 		$(".record-list").hide();
                 		$(".no-record").show();
                 	}else{
                 		$.exchangeTip("提示",data.message);
                 	}
                }
            },
            error : function() { 
            	 $.exchangeTip("提示",data.message);
             }  
        });
    }
	/**
     * 兑换礼品页面
     */
     function getExchangeGift(){
    	 $.ajax({
             type : 'POST',
             url : CONTEXT + "jpgys/activity/turnToExchangeGift",
             data : {
                 "activityId" : activityId
             },
             dataType : 'json',
             success : function(data) {
                 var result = data.status;
                 //将关注的弹框隐藏
                 $(".tip-wrap").hide();
          		 $(".tip-follow").hide();
          		 $(".align-l-col-2").hide();
                 $("#currentScore").text(data.score==null?"--":data.score);
                 if (result == 1) {
                	 //如果之前有显示让登录的界面，需要将其隐藏
                	 $(".main-no-login").hide();
             		 $(".login-wrap").hide();
             		 $(".rule-wrap").show();
             		 $("#noRecordList").hide();
                	//所有礼品
                 	var giftList = data.giftList;
                 	if(giftList && giftList.length>0){
                 		var htmlStr = "";
                 		for (var i = 0;i<giftList.length;i++) {
                 			htmlStr+='<tr><td style="text-align: left;">'+giftList[i].giftName+'</td><td>'+giftList[i].exchangeScore+'积分</td><td><a class="exchange-btn exchange-follow" dataId="'+giftList[i].giftId+'">兑换</a></td><tr>';
                 		}
                 		$("#exchangeGiftTable tbody").html("");
                 		$("#exchangeGiftTable tbody").append(htmlStr);
                 		
                 	}
                	//最新兑换记录
                 	var recordlist = data.recordlist;
                	if(recordlist && recordlist.length>0){
                		var htmlRecordStr = "";
	             		for (var i = 0;i<recordlist.length;i++) {
	             			var dfExchangeTime = "";
	             			if(recordlist[i].exchangeTime){
	             				dfExchangeTime= recordlist[i].exchangeTime.substring(0,10);
	             			}
	             			htmlRecordStr+='<tr><td>'+recordlist[i].mobile+'</td><td>'+recordlist[i].giftName+'</td><td>'+dfExchangeTime+'</td></tr>';
	             		}
	             		$(".align-l-col-2").show();
	             		$("#newExchangeGiftTable tbody").html("");
	             		$("#newExchangeGiftTable tbody").append(htmlRecordStr);
	             		
                	}else{
                		//没有兑换记录
                		$("#noRecordList").text("礼品兑换时间"+data.effectiveStartTime+"-"+data.effectiveEndTime);
                		$("#noRecordList").show();
                	}
                	
                 }else {
                 	//未关注
                 	if(data.errorCode==2){
                 		$(".tip-wrap").show();
                 		$(".tip-follow").show();
                 	//未登录
                 	}else if(data.errorCode==1){
                 		 //alert("你还未登录，请先登录");
                 		 $(".option").hide();
                 		 $(".main-no-login").show();
                 		 $(".login-wrap").show();
                 		 $(".rule-wrap").hide();
                 	}else{
                 		 $.exchangeTip("提示",data.message);
                 	}
                   
                 }
             },
             error : function() { 
            	 $.exchangeTip("提示",data.message);
             } 
         });
     }
	// 兑换礼品提示
	//$(".tab-exchange").on("touchstart", ".exchange-get", function(){
		
		// $.exchangeTip("礼品兑换成功！", "您已成功兑换微信红包10元，<br/>将扣除30积分");
		// $.exchangeTip("礼品已兑换光！", "该礼品已经兑换光啦， <br/>赶紧兑换其它礼品吧！");
		// $.exchangeTip("兑换机会没有啦！", "您已兑换过此礼品，请兑换其它礼品吧！");
		// $.exchangeTip("积分不足！", "您的积分不足以兑换礼品， <br/>赶快邀请更多好友获取红包吧！");
	 //});
	// 兑换礼品-关注公众号提示
	$(".tab-exchange").on("touchstart", ".exchange-follow", function(){
		/*$(".tip-wrap").show();
		$(".tip-follow").show();*/
		var giftId = $(this).attr("dataId");
		var giftName = $(this).parent().parent().find("td:eq(0)").text();
		var exchangeScore = $(this).parent().parent().find("td:eq(1)").text();
		var confirmStr ="您确认兑换该礼品，将扣除"+exchangeScore;
		$.confirm("确认兑换礼品", confirmStr, function(){
			$.ajax({
	            type : 'POST',
	            url : CONTEXT + "jpgys/activity/exchangeGift",
	            data : {
	                "activityId" : activityId,
	                "giftId":giftId,
	                "userid":userid
	            },
	            dataType : 'json',
	            success : function(data) {
	                var result = data.status;
	                if (result == 1) {
	                	$.exchangeTip("礼品兑换成功！", "您已成功兑换"+giftName+"，<br/>将扣除"+exchangeScore);
	                	$("#currentScore").text(data.score);
	                }else {
	                	if(data.errorCode==1){
	                		 $.exchangeTip("兑换机会没有啦！","您已兑换过此礼品，请兑换其它礼品吧！");
	                	}else if(data.errorCode==3){
	                		$.exchangeTip("礼品已兑换光！", "该礼品已经兑换光啦， <br/>赶紧兑换其它礼品吧！");
	                		
	                	}else if(data.errorCode==4){
	                		$.exchangeTip("积分不足！", "您的积分不足以兑换礼品， <br/>赶快邀请更多好友获取红包吧！");
	                    }else if(data.errorCode==5){
	                    	$.exchangeTip("", "礼品兑换时间为：<br/>"+data.effectiveStartTime+"-"+data.effectiveEndTime+"<br/>详情可关注公众号进行了解");
	                	}else{
	                    	$.exchangeTip("提示", data.message);
	                        
	                    }
	                }
	            },
	            error : function() { 
	           	 $.exchangeTip("提示",data.message);
	            } 
	        });
			$(".confirm").hide();
		});
		
	});

	$("#followBtn").on("touchstart", function(){
		$(".tip-follow").hide();
		$(".tip-qrcode").show();
	});

	// 分享提示
	$(".shareBtn").on("touchstart", function(){
		$(".share-wrap").show();
	});
	$(".share-wrap").on("touchstart", function(){
		$(this).hide();
	});
});