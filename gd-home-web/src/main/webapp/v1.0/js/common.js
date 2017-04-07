function popupLoginDlg(){
    var diaLogTemp = '<div class="dla-login">'+
        '<h2 class="dla-login-t clearfix"><a class="dla-login-t-link" href="#">还没有账号，去注册</a><span class="dla-login-ttxt">登录谷登农批</span></h2>'+
        '<form name="login" action="logining.html" method="post">'+
        '<div class="login-tips dn"></div>'+
        '<ul class="login-list">'+
            '<li>'+
                '<div class="l-inp-wrap clearfix">'+
                    '<span class="bor">'+
                        '<input type="text" id="uname" name="paramMap.username" class="input-text input-text-login" placeholder="账户名/手机号码" maxlength="16" data-err="请输入6~16个字符的用户名（一个汉字占用2个字符）">'+  
                    '</span>'+
                '</div>'+
            '</li>'+
            '<li>'+
                '<div class="l-inp-wrap clearfix">'+    
                    '<span class="bor mr01">'+
                        '<input type="text" id="upwd" name="paramMap.password" class="input-text input-text-login" placeholder="密码">'+
                    '</span>'+
                '</div>'+
            '</li>'+
            '<li><input class="btn-red-dia btn-dia-login" type="button" value="立即登录"></li>'+
            '<li class="clearfix dia-log-tool">'+
                '<label class="leb-txt">'+
                    '<input type="checkbox" value="" checked="checked" class="ck-temp">记住账号</label>'+
                '<a class="login-link fr" href="#">忘记登录密码？</a>'+
            '</li>'+
        '</ul>'+
        '</form>'+
    '</div>';
    layer.open({
        type: 1,
        title: false, 
        skin: 'layui-layer-rim',
        content: diaLogTemp, //捕获的元素
        area:["472px","342px"],
        cusClass:'comDialog comDig-notit',
        cancel: function(index){
            layer.close(index);
        }
    });
    $("body").delegate(".btn-dia-login","click",function(){
        saveUserInfo();
        dialogin();
    });
    $(".input-text").bind("focus",function(){
        check_empty($(this),'请输入'+$(this).attr("placeholder")+'!',$(".login-tips"));
    }).bind("blur",function(){
        check_empty($(this),'请输入'+$(this).attr("placeholder")+'!',$(".login-tips"));
    });     
    
    $(document).keydown(function(event){
        if(event.keyCode==13){
            login();
        }
    });
}

//检查是否登录
function checkIsLogin(){
    var isLogined=false;
    $.ajax({
        type:"get",
        url:"/index.html?act=index&op=login",
        dataType:"text",
        async:false,
        success:function(result){
            isLogined= result;
        }
    });
    return isLogined;
}
$(function(){
    
});

var dialogin=function(){
    $("span.bor").each(function(index, element) {
        if($(".login-list li").eq(index).find("input").val()==""){
            $(".login-tips").addClass("info-err").show().html("请输入"+$("span.bor").eq(index).find("input").attr("placeholder")+"！");
            return false;
        }
    });
    if($(".info-err").length>0){
        return false;
    }else {
        $('.btn-dia-login').val('登录中...');
        //设置密码加密
        var pwdval=$("input[name='paramMap.password']").val();

        $("form[name='login'] input[name='paramMap.password']").val(pwdval);
        var param=$("form[name='login']").serialize();
        $.post("logining.html", param,function(data) {
            if(data.msg==1){
                //var loginBackUrl = $("#loginBackUrl").val();
                //if (loginBackUrl != "") {
                    location.href = location.href;
                // } else {
                //  location.href = basePath + "home.html";
                // }
            }else{
                $(".login-tips").addClass("info info-err").show().html(data.msg);
                $('.btn-login').val('立即登录');
            }
            
        });
    }
};

var check_empty=function(tmp,msg,target){
    var $leb=target,
        defaultvalue = tmp.attr("placeholder");
    if(tmp.val()==""||tmp.val()==defaultvalue){
        $leb.addClass("info-err").html(msg).show();
        return false;
    }else{
        $leb.removeClass("info-err").html('');
    }
    return true;
}
//cookie记住帐号
$(document).ready(function() {
    //获取cookie的值
    var username = $.cookie('username');
    //var password = $.cookie('password');

    //将获取的值填充入输入框中
    $("input[name='paramMap.username']").val(username);
    if(username != null && username != ''){//选中保存密码的复选框
        $(".ck-temp").attr('checked',true);
    }
});

function saveUserInfo() {
    var uName = $("input[name='paramMap.username']").val();
    //var psw = $("input[name='paramMap.password']").val();
    if($('.ck-temp').attr('checked')){//保存密码
        $.cookie('username',uName, {expires:7,path:'/'});
        //$.cookie('password',psw, {expires:7,path:'/'});
    }else{//删除cookie
        $.cookie('username', '', { expires: -1, path: '/' });
        //$.cookie('password', '', { expires: -1, path: '/' });
    }
}

/**收藏**/
function collect_store(fav_id, jstype, jsobj) { 
    var url = '/index.php?act=member_favorites&op=favoritesstore';
    $.getJSON(url, {
        'fid' : fav_id
    }, function (data) {
        if (data.done) {
            showDialog(data.msg, 'succ', '', '', '', '', '', '', '', '', 2);
            if (jstype == 'count') {
                $('[nctype="' + jsobj + '"]').each(function () {
                    $(this).html(parseInt($(this).text()) + 1);
                });
            }
            if (jstype == 'succ') {
                $('[nctype="' + jsobj + '"]').each(function () {
                    $(this).html("收藏成功");
                });
            }
            if (jstype == 'store') {
                $('[nc_store="' + fav_id + '"]').each(function () {
                    $(this).before('<span class="goods-favorite" title="该店铺已收藏"><i class="have">&nbsp;</i></span>');
                    $(this).remove();
                });
            }
        } else {
            showDialog(data.msg, 'notice');
        }
    });
}