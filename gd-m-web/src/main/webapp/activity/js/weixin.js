function share(link,title,desc,icon){
    var shareLink = link;
    var shareTitle = title;
    var shareDesc = desc;
    var shareIcon = icon;
    $.post("/weixin/signatureJSSDK",{
        url: location.href
    },function(data) {
    	var res = data.result;
        wx.config({
            debug:false,                         // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId:res.appId,         // 必填，公众号的唯一标识
            timestamp:res.timestamp, // 必填，生成签名的时间戳
            nonceStr:res.nonceStr,   // 必填，生成签名的随机串
            signature:res.signature, // 必填，签名，见附录1
            jsApiList:[ "onMenuShareTimeline", "onMenuShareAppMessage", "onMenuShareQQ", "onMenuShareWeibo", "closeWindow" ]
        });
        wx.error(function(res) {
            console.log(res);
        });
        wx.ready(function() {
            wx.onMenuShareTimeline({
                title:shareTitle,   // 分享标题
                link:shareLink,     // 分享链接
                imgUrl:shareIcon,   // 分享图标
                success:function() {},
                cancel:function() {}
            });
            wx.onMenuShareAppMessage({
                title:shareTitle,  // 分享标题
                desc:shareDesc,    // 分享描述
                link:shareLink,    // 分享链接
                imgUrl:shareIcon,  // 分享图标
                type:"link",       // 分享类型,music、video或link，不填默认为link
                dataUrl:"",        // 如果type是music或video，则要提供数据链接，默认为空
                success:function() {},
                cancel:function() {}
            });
            wx.onMenuShareQQ({
                title:shareTitle,  // 分享标题
                desc:shareDesc,    // 分享描述
                link:shareLink,    // 分享链接
                imgUrl:shareIcon,  // 分享图标
                success:function() {},
                cancel:function() {}
            });
            wx.onMenuShareWeibo({
                title:shareTitle,  // 分享标题
                desc:shareDesc,    // 分享描述
                link:shareLink,    // 分享链接
                imgUrl:shareIcon,  // 分享图标
                success:function() {},
                cancel:function() {}
            });
        });
    }, 'json');
}