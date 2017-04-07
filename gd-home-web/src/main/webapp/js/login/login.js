$("body").delegate(".btn-dia-login","click",function(){
    saveUserInfo();
    dialogin();
});
$(".input-text").bind("focus",function(){
    check_empty($(this),'请输入'+$(this).attr("placeholder")+'！',$(".login-tips"));
}).bind("blur",function(){
    check_empty($(this),'请输入'+$(this).attr("placeholder")+'！',$(".login-tips"));
}).bind("keydown",function(){
    check_empty($(this),'请输入'+$(this).attr("placeholder")+'！',$(".login-tips"));
});     


function changeAuthCode() {
    var num =   new Date().getTime();
    var rand = Math.round(Math.random() * 10000);
    num = num + rand;
    $("#code_img").attr('src',$("#code_img").attr("data-img") + "?" + num);
        
}
var dialogin=function(){
    $("span.bor").each(function(index, element) {
    	var currText=$(this).find("input").val();
        if(currText==""){
            $(".login-tips").addClass("info-err").show().html("请输入"+$(this).find("input").attr("placeholder")+"！");
            return false;
        }
        if(index==0&&!/^1[34578]\d{9}$/.test(currText)){
        	$(".login-tips").addClass("info-err").show().html($(this).find("input").attr("data-err")+"！");
        	return false;
        }
    });
    if($(".info-err").length>0){
        return false;
    }else {
        $('.btn-dia-login').val('登录中...');
        //设置密码加密
        var pwdval=$("input[name='loginPassword']").val();

        $("form[name='login'] input[name='loginPassword']").val(pwdval);
        var param=$("form[name='login']").serialize();
        $.ajax({
			type: 'POST',
			url: CONTEXT+'login/commitLogin' ,
		    data: param ,
		    async: false,
		    dataType: 'json' ,
		    success: function(data) {
	        	if(data.msg==0){
	                var BackUrl = $("#BackUrl").val();
					if (BackUrl != "") {
						window.location.href = BackUrl;
					} else {
						window.location.href = CONTEXT+"index";
					}
	            }else{
	                $(".login-tips").addClass("info info-err").show().html(data.msg);
	                $('.btn-dia-login').val('立即登录');
	                changeAuthCode();
	            }
		    } ,
		    error: function(err) {
		    	alert('系统维护中。。。');
		    	changeAuthCode();
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
    var username = $.cookie('username');
    //var password = $.cookie('password');

    //将获取的值填充入输入框中
    $("input[name='loginName']").val(username);
    if(username != null && username != ''){//选中保存密码的复选框
        $(".ck-temp").attr('checked',true);
    }
    
    $(document).keydown(function(event){
		if(event.keyCode==13){					
			dialogin();
		}
	});
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