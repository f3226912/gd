var checkLogin = true;
function popupLoginDlg2(){
    layer.open({
        type: 1,
        title: false, 
        skin: 'layui-layer-rim',
        content: $(".dla-login"), //捕获的元素
        area:["472px","342px"],
        cusClass:'comDialog comDig-notit',
        cancel: function(index){
            layer.close(index);
        }
    });
    $("body").delegate(".btn-dia-login","click",function(){
        saveUserInfo();
        //dialogin();
    });
    $(".input-text").bind("focus",function(){
    	checkLogin = check_empty($(this),'请输入'+$(this).attr("placeholder")+'!',$(".login-tips"));
    }).bind("blur",function(){
    	checkLogin = check_empty($(this),'请输入'+$(this).attr("placeholder")+'!',$(".login-tips"))&& checkLogin;
    });
    /*
    $(document).keydown(function(event){
        if(event.keyCode==13){
            login();
        }
    });*/
}

$(function(){
    
});

var dialogin=function(){
    
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
        $leb.removeClass("info-err").html('&nbsp;');
    }
    return true;
}
//cookie记住帐号
$(document).ready(function() {
    //获取cookie的值
	if($.cookie) {
		var username = $.cookie('username');
	    //var password = $.cookie('password');
	
	    //将获取的值填充入输入框中
	    $("input[name='loginName']").val(username);
	    if(username != null && username != ''){//选中保存密码的复选框
	        $(".ck-temp").attr('checked',true);
	    }
	}
});

function saveUserInfo() {
    var uName = $("input[name='loginName']").val();
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

$(function(){
	(function(){
		if($(".helps-menu").length>0){
			var curLocation = window.location.pathname;
			$(".con-left-tit").find(".helps-menu a[href*='"+curLocation+"']").parent().addClass("curr");
		}
	})()
	
});

/**
 * add by wonlim
 * 自动添加url参数
 * **/
function addParameter(url, parameterName, parameterValue, atStart){
    replaceDuplicates = true;
    if(url.indexOf('#') > 0){
        var cl = url.indexOf('#');
        urlhash = url.substring(url.indexOf('#'),url.length);
    } else {
        urlhash = '';
        cl = url.length;
    }
    sourceUrl = url.substring(0,cl);

    var urlParts = sourceUrl.split("?");
    var newQueryString = "";

    if (urlParts.length > 1)
    {
        var parameters = urlParts[1].split("&");
        for (var i=0; (i < parameters.length); i++)
        {
            var parameterParts = parameters[i].split("=");
            if (!(replaceDuplicates && parameterParts[0] == parameterName))
            {
                if (newQueryString == "")
                    newQueryString = "?";
                else
                    newQueryString += "&";
                newQueryString += parameterParts[0] + "=" + (parameterParts[1]?parameterParts[1]:'');
            }
        }
    }
    if (newQueryString == "")
        newQueryString = "?";

    if(atStart){
        newQueryString = '?'+ parameterName + "=" + parameterValue + (newQueryString.length>1?'&'+newQueryString.substring(1):'');
    } else {
        if (newQueryString !== "" && newQueryString != '?')
            newQueryString += "&";
        newQueryString += parameterName + "=" + (parameterValue?parameterValue:'');
    }
    var urlnew = urlParts[0] + newQueryString + urlhash;
    return urlnew;
};