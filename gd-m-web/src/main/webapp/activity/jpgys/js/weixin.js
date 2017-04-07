function shareCallBack(url,param){
    $.ajax({
        type : 'POST',
        url : url,
        data : param,
        dataType : 'json',
        success : function(data) {
            var result = data.result;
            if (result) {

            }
        }
    });
}

function share(link,title,desc,icon){
    var shareLink = link;
    var shareTitle = title;
    var shareDesc = desc;
    var shareIcon = icon;
    $.getJSON( CONTEXT+ "weixin/signatureJSSDK",{
        url: location.href
    },function(data) {
    	var res = data.result;
        wx.config({
            debug:false,             // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId:res.appId,         // 必填，公众号的唯一标识
            timestamp:res.timestamp, // 必填，生成签名的时间戳
            nonceStr:res.nonceStr,   // 必填，生成签名的随机串
            signature:res.signature, // 必填，签名，见附录1
            jsApiList:["showMenuItems", "hideAllNonBaseMenuItem",
                "onMenuShareTimeline","onMenuShareAppMessage","onMenuShareQQ","onMenuShareWeibo","onMenuShareQZone",
                "closeWindow" ]
        });
        wx.error(function(res) {
            console.log("微信验证失败："+res);
        });
        wx.ready(function() {
            wx.hideAllNonBaseMenuItem();//隐藏所有菜单
            wx.showMenuItems({
                menuList : [ "menuItem:share:appMessage", "menuItem:share:timeline","menuItem:share:weiboApp","menuItem:share:qq","menuItem:share:QZone",
                    "menuItem:profile","menuItem:openWithQQBrowser","menuItem:openWithSafari", "menuItem:favorite"]
                // 要显示的菜单项，所有menu项见附录3
            });
            wx.onMenuShareTimeline({
                title:shareTitle,   // 分享标题
                link: openId == null ? CONTEXT+"jpgys/weixin/activity/5" : CONTEXT+"jpgys/invite/2/" + activityId + "/" + openId,     // 分享链接
                imgUrl:shareIcon,   // 分享图标
                success:function() {
                    $(".share-wrap").hide();
                    var params = {
                        activityId : activityId,
                        shareType : 2,
                        unionid : unionid,
                        openid : openId,
                        userid : userid
                    };
                    shareCallBack(CONTEXT + "jpgys/share", params);
                    location.reload();
                    /*$.ajax({
                        type : 'POST',
                        url : CONTEXT + "jpgys/star/score",
                        data : {
                            "actId" : activityId
                        },
                        dataType : 'json',
                        success : function(data) {
                            var result = data.msg;
                            if (result == 'success') {
                                $(".score-total").html(data.score);//当前用户活动总积分
                            }
                        }
                    });*/
                },
                cancel:function() {$(".share-wrap").hide();}
            });
            wx.onMenuShareAppMessage({
                title:shareTitle,  // 分享标题
                desc:shareDesc,    // 分享描述
                link:openId == null ? CONTEXT+"jpgys/weixin/activity/5" : CONTEXT+"jpgys/invite/1/" + activityId + "/" + openId,    // 分享链接
                imgUrl:shareIcon,  // 分享图标
                type:"link",       // 分享类型,music、video或link，不填默认为link
                dataUrl:"",        // 如果type是music或video，则要提供数据链接，默认为空
                success:function() {
                    $(".share-wrap").hide();
                    var params = {
                        activityId : activityId,
                        shareType : 1,
                        unionid : unionid,
                        openid : openId,
                        userid : userid
                    };
                    shareCallBack(CONTEXT + "jpgys/share", params);
                },
                cancel:function() {$(".share-wrap").hide();}
            });
            wx.onMenuShareQQ({
                title:shareTitle,  // 分享标题
                desc:shareDesc,    // 分享描述
                link:openId == null ? CONTEXT+"jpgys/weixin/activity/5" : CONTEXT+"jpgys/invite/3/" + activityId + "/" + openId,     // 分享链接
                imgUrl:shareIcon,  // 分享图标
                success:function() {
                    $(".share-wrap").hide();
                    var params = {
                        activityId : activityId,
                        shareType : 3,
                        unionid : unionid,
                        openid : openId,
                        userid : userid
                    };
                    shareCallBack(CONTEXT + "jpgys/share", params);
                },
                cancel:function() {$(".share-wrap").hide();}
            });
            wx.onMenuShareWeibo({
                title:shareTitle,  // 分享标题
                desc:shareDesc,    // 分享描述
                link:openId == null ? CONTEXT+"jpgys/weixin/activity/5" : CONTEXT+"jpgys/invite/4/" + activityId + "/" + openId,     // 分享链接
                imgUrl:shareIcon,  // 分享图标
                success:function() {
                    $(".share-wrap").hide();
                    var params = {
                        activityId : activityId,
                        shareType : 4,
                        unionid : unionid,
                        openid : openId,
                        userid : userid
                    };
                    shareCallBack(CONTEXT + "jpgys/share", params);
                },
                cancel:function() {$(".share-wrap").hide();}
            });
            wx.onMenuShareQZone({
                title: shareTitle, // 分享标题
                desc: shareDesc, // 分享描述
                link: openId == null ? CONTEXT+"jpgys/weixin/activity/5" : CONTEXT+"jpgys/invite/5/" + activityId + "/" + openId, // 分享链接
                imgUrl: shareIcon, // 分享图标
                success: function () {
                    $(".share-wrap").hide();
                    // 用户确认分享后执行的回调函数
                    var params = {
                        activityId : activityId,
                        shareType : 5,
                        unionid : unionid,
                        openid : openId,
                        userid : userid
                    };
                    shareCallBack(CONTEXT + "jpgys/share", params);
                },
                cancel: function () {
                    // 用户取消分享后执行的回调函数
                    $(".share-wrap").hide();
                }
            });
        });
    });
}