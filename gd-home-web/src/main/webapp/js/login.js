var isLogon = false;

$("body").delegate(".btn-dia-login","click",function(){
    saveUserInfo();
    dialogin();
});
$(".input-text").bind("focus",function(){
    check_empty($(this),'请输入'+$(this).attr("placeholder")+'!',$(".login-tips"));
}).bind("blur",function(){
    check_empty($(this),'请输入'+$(this).attr("placeholder")+'!',$(".login-tips"));
});     

function changeAuthCode() {
    var num =   new Date().getTime();
    var rand = Math.round(Math.random() * 10000);
    num = num + rand;
    $("#code_img").attr('src',$("#code_img").attr("data-img") + "&tag=" + num);
        
}
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


/*
 * JS for login plug 
 */
$(document).ready(function() {
	$(".showLoginDialog").bind("click",function(){
		Login.login();
	});
	
	$(".btn-dia-login").click(function() {
		/*
		 * 验证
		 */
		debugger;
		checkLogin = true;
		$(".input-text").each(function(){
	    	checkLogin = check_empty($(this),'请输入'+$(this).attr("placeholder")+'!',$(".login-tips"));
	    });
		
		$("span.bor").each(function(index, element) {
	        if($(".login-list li").eq(index).find("input").val()==""){
	            $(".login-tips").addClass("info-err").show().html("请输入"+$("span.bor").eq(index).find("input").attr("placeholder")+"！");
	            return false;
	        }
	    });
		if($(".info-err").length>0){
	        return false;
	    }
		
		//设置密码加密
        var pwdval=$("input[name='loginPassword']").val();

        $("form[name='login'] input[name='loginPassword']").val(pwdval);
        
		$('.btn-dia-login').val('登录中...');
		
		if(checkLogin) {
			var param = $("#loginForm").serializeArray();
			$.ajax({
				type: 'POST',
				url: CONTEXT+'login/commitSmallLogin' ,
			    data: param ,
			    dataType: 'json' ,
			    success: function(data) {
			    	if(data.msg==0){
			    		location.reload();
		            }else{
		                alert(data.msg);
		                $('.btn-dia-login').val('立即登录');
		            }
			    } ,
			    error: function(err) {
			    	alert('系统维护中。。。');
			    }
			});
		}
	});
	
	/*
	 * head check
	 */
	Login.preCheck();
});

var Login = {};
/*
 * check login
 */

Login.checkLogin = (function(recall,recallUn) {
	$.ajax({
		type: 'POST',
		url: CONTEXT+'business/shop/checkLogon' ,
	    data: '' ,
	    dataType: 'json' ,
	    success: function(data) {
	    	if(data.statusCode==-1) {
	    		isLogon = false;
	    		if(recallUn!=null) 
	    			recallUn();
	    		else
	    			Login.login();
	    	} else {
	    		isLogon = true;
	    		if(recall!=null)
	    			recall();
	    	}
	    } ,
	    error: function(err) {
	    	alert('系统维护中。。。');
	    }
	});
});

/*
 * login function
 */
Login.login = (function() {
	popupLoginDlg2();
});

/*
 * login out function
 */
Login.loginout = (function() {
	$.ajax({
		type: 'POST',
		url: CONTEXT+'login/loginOut' ,
	    data: '' ,
	    dataType: 'json' ,
	    success: function(data) {
	    	location.reload();
	    } ,
	    error: function(err) {
	    	alert('系统维护中。。。');
	    }
	});
});

/*
 * head check
 */
Login.preCheck = (function() {
	Login.checkLogin(Login.headLogonInfo,Login.headUnLogonInfo);
});

/*
 * 登录显示头部信息
 */
Login.headLogonInfo = (function() {
	$(".sigin-box").show();
	$(".sigout-box").hide();
});

/*
 * 未登录显示头部信息
 */
Login.headUnLogonInfo = (function() {
	$(".sigout-box").show();
	$(".sigin-box").hide();
});
