/**
 * Created by lidong on 2016/11/18.
 */

/**
 * 设置只能是微信浏览器才能打开页面
 */
function isWeiXin() {
    var browser = {
        versions : function() {
            var u = navigator.userAgent, app = navigator.appVersion;
            return { // 移动终端浏览器版本信息
                trident : u.indexOf('Trident') > -1, // IE内核
                presto : u.indexOf('Presto') > -1, // opera内核
                webKit : u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
                gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
                mobile : !!u.match(/AppleWebKit.*Mobile.*/), // 是否为移动终端
                ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
                android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或uc浏览器
                iPhone : u.indexOf('iPhone') > -1, // 是否为iPhone或者QQHD浏览器
                iPad : u.indexOf('iPad') > -1, // 是否iPad
                webApp : u.indexOf('Safari') == -1
                // 是否web应该程序，没有头部与底部
            };
        }(),
        language : (navigator.browserLanguage || navigator.language)
            .toLowerCase()
    }

    if (browser.versions.mobile) {// 判断是否是移动设备打开。browser代码在下面
        var ua = navigator.userAgent.toLowerCase();// 获取判断用的对象
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            // 在微信中打开
        } else {
            window.location.href = CONTEXT + 'weixin/invalidBS';
            // if (ua.match(/WeiBo/i) == "weibo") {
            // //在新浪微博客户端打开
            // }
            // if (ua.match(/QQ/i) == "qq") {
            // //在QQ空间打开
            // }
            // if (browser.versions.ios) {
            // //是否在IOS浏览器打开
            // }
            // if(browser.versions.android){
            // //是否在安卓浏览器打开
            // }
        }
    } else {
        // 否则就是PC浏览器打开
        window.location.href = CONTEXT + 'weixin/invalidBS';
    }
}
isWeiXin();

/**
 * 金牌供应商活动页面与微信相关操作
 *
 * @author lidong
 */
share(CONTEXT+"jpgys/invite/1/" + activityId + "/" + openId,"谷登邀您参加年度产销盛会","好货卖好价！直供17家一级农批市场，年度产销盛会等你来！",CONTEXT+"activity/jpgys/images/share.png");


document.oncontextmenu = new Function("event.returnValue=false;");
document.onselectstart = new Function("event.returnValue=false;");

$(function() {

    /**
     * 初始化好友的被点赞总数
     */
    function initInviteStarCount() {
        $.getJSON(CONTEXT + "jpgys/invite/star/count",{"invitorOpenid" : invitorOpenid,"actId" : activityId},function (data) {
            var result = data.msg;
            if (result == 'success') {
                $(".star-count").html(data.starCount);//点赞总数
            }
        })
    }

    /**
     * 初始化好友昵称
     */
    function initInviteName() {
        $.getJSON(CONTEXT + "jpgys/invite/star/nickname",{"invitorOpenid" : invitorOpenid},function (data) {
            var result = data.msg;
            if (result == 'success') {
                $(".star-nickname").html(data.nickname);//好友昵称
            }
        })
    }

    /**
     * 初始化我的被点赞总数
     */
    function initMyStarCount() {
        $.getJSON(CONTEXT + "jpgys/star/count",{"actId" : activityId,"openid" : openId},function (data) {
            var result = data.msg;
            if (result == 'success') {
                $(".star-count-my").html(data.starCount);//点赞总数
            }
        })
    }
    /**
     * 初始化我的总积分
     */
    function initMyScore() {
        $.getJSON(CONTEXT + "jpgys/star/score",{"actId" : activityId},function (data) {
            var result = data.msg;
            if (result == 'success') {
                $(".score-total").html(data.score);//当前用户活动总积分
            }
        })
    }
    /**
     * 初始化邀请好友注册数
     */
    function initRegisterCount() {
        $.getJSON( CONTEXT + "jpgys/star/register",{"actId" : activityId},function (data) {
            var result = data.msg;
            if (result == 'success') {
                $(".register-total").html(data.register);// 邀请好友注册数
            }
        });
    }

    /**
     * 从邀请链接进入页面，执行该方法
     */
    function initInvite() {
        if(invitorOpenid){
             if (invitorOpenid==openId){
                 var url=location.href;
                 location.href =url.replace("invitorOpenid="+invitorOpenid+"&","");
             }else {
                 initInviteStarCount();//初始化好友被点赞总数
                 initInviteName();//初始化好友昵称
                 $(".option").hide();
                 if(userid){
                     $(".share-is-login").show();
                 }else {
                     $(".share-no-login").show();
                 }
             }
        }else {
            //1 好友总点赞数
            initMyStarCount();
            //2 我的积分
            initMyScore();
            //3 邀请好友注册数
            initRegisterCount();
            //非好友邀请链接
            $(".option").hide();
            if(userid){
                $(".main-is-login").show();
            }else {
                $(".main-no-login").show();
            }
        }
    }
    initInvite();

    /**
     * 登录绑定
     */
    var logingetPocketfalg = true;
    $(".submit-login").on('touchstart', function() {
        var mobile = $("#login-mobile").val().trim();
        var password = $("#login-password").val().trim();
        if (mobile && password) {
            if(logingetPocketfalg){
                logingetPocketfalg=false;
                $.getJSON(CONTEXT + "jpgys/userManage/login",{
                    "mobile" : mobile,
                    "password" : password,
                    "openId" : openId,
                    "activityId" : activityId,
                    "unionid" : unionid,
                    "memberId" : userid,
                    "invitorOpenid" : invitorOpenid
                },function (data) {
                    var result = data.msg;
                    if (result == 'success') {
                        userid = data.userid;
                        initInvite();
                        $(".login-wrap").hide();
                        $(".tip-wrap").show();
                        $(".tip-login").show();
                        return false;
                    } else if (result == 'gameover') {
                        // 活动结束
                        window.location.href = CONTEXT + "ipgys/activity/activityEnd?activityId=" + activityId;
                    } else if (result == 'gift') {
                        // 弹出活动结束，尽快兑换礼物
                        // gameOver();
                    } else {
                        $.exchangeTip("提示",result);
                    }
                    logingetPocketfalg = true;
                });
            }else {
                $.exchangeTip("提示","您正在登录, 请耐心等待");
                return;
            }
        } else {
            $.exchangeTip("提示","请填写完整信息");
            return;
        }
    });

    /**
     * 注册绑定
     */
    var registergetPocketflag = true;
    $(".submit-register").on('touchstart', function() {
        var mobile = $("#register-mobile").val().trim();
        var password = $("#register-password").val().trim();
        var code = $("#register-ver-code").val().trim();
        var confirmPassword = $("#register-password-confirm").val().trim();
        if (mobile && code && password && confirmPassword) {
            if (password != confirmPassword) {
                $.exchangeTip("提示","两次密码不一致");
                return;
            }
            if(registergetPocketflag){
                registergetPocketflag=false;
                $.getJSON(CONTEXT + "jpgys/userManage/register",{
                    "mobile" : mobile,
                    "password" : password,
                    "openId" : openId,
                    "activityId" : activityId,
                    "unionid" : unionid,
                    "invitorOpenid" : invitorOpenid,
                    "code" : code
                },function (data) {
                    var result = data.msg;
                    if (result == 'success') {
                        userid = data.userid;
                        initInvite();
                        $(".register-wrap").hide();
                        $(".tip-wrap").show();
                        $(".tip-register").show();
                        return false;
                    } else if (result == 'gameover') {
                        // 活动结束
                        window.location.href = CONTEXT + "activity/activityEnd?activityId=" + activityId;
                    } else if (result == 'gift') {
                        // 弹出活动结束，尽快兑换礼物
                        // gameOver();
                    } else {
                        $.exchangeTip("提示",result);
                    }
                    registergetPocketflag = true;
                });
            } else {
                $.exchangeTip("提示","您正在注册, 请耐心等待");
                return;
            }
        } else {
            $.exchangeTip("提示","请填写完整信息");
            return;
        }
    });

    /** 发送验证码** */
    var sendverflag = true;
    $(".verify").on('touchstart', function() {
        if(sendverflag){
            var mobile = $("#register-mobile").val().trim();
            if (mobile) {
                $.getJSON(CONTEXT + "jpgys/userManage/sendCode",{"mobile" : mobile},function (data) {
                    var result = data.msg;
                    if (result == 'success') {
                        $.exchangeTip("提示","验证码已发送, 请耐心等待");
                    } else {
                        $.exchangeTip("提示",result);
                    }
                    sendverflag = true;
                });
            } else {
                $.exchangeTip("提示","手机号不可为空");
                sendverflag = true;
                return;
            }
        } else {
            $.exchangeTip("提示","验证码已发送, 请耐心等待");
        }
    });

    /**
     * 为好友点赞
     */
    $(function () {
        $(".btn-praise").on('touchstart', function () {
            $.getJSON(CONTEXT + "jpgys/invite/star/click",{
                "openId1" : invitorOpenid,
                "openId2" : openId,
                "actId" : activityId
            },function (data) {
                var result = data.msg;
                if (result == 'success') {
                    var count = $(".star-count").html();//当前点赞数
                    count = 1 + Number(count);
                    $(".star-count").html(count);//点赞总数
                    $.exchangeTip("提示","点赞成功");
                }else {
                    $.exchangeTip("提示",result);
                }
            });
        });
    });

    /**
     * 返回个人活动页面
     */
    $("#returnBtn").on('touchstart', function () {
        //1 好友总点赞数
        // initMyStarCount();
        //2 我的积分
        // initMyScore();
        //3 邀请好友注册数
        // initRegisterCount();
        // $(".option").hide();
        // $(".main-is-login").show();
        //清除邀请人信息
        // invitorOpenid = null;
        var url=location.href;
        location.href =url.replace("invitorOpenid="+invitorOpenid+"&","");
    });
   
    // 兑换礼品-关注公众号提示
/*    $(".tab-exchange").on("touchstart", function(){
        $(".tip-wrap").show();
        $(".tip-follow").show();
    });*/

   /* $(".exchange-btn").click(function () {
        $(".tip-wrap").show();
        $(".tip-follow").show();
    });*/


});

