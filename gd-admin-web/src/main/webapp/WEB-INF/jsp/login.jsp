<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String BASE_PATH = "/";
    String path = request.getContextPath();
    String CONTEXT = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
	request.setAttribute("CONTEXT",CONTEXT);
%>
<!DOCTYPE html>
<html class="columnsCss">
<head>
	<meta charset="utf-8">
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="${CONTEXT }css/login/global.css" rel="stylesheet">
	<link href="${CONTEXT }css/login/layout.css" rel="stylesheet">
	<link href="${CONTEXT }css/login/login.css" rel="stylesheet">
	<link href="${CONTEXT }css/login/style.css" rel="stylesheet" />
	<link href="${CONTEXT }css/login/perspectiveRules.css" rel="stylesheet" />
	<link href="${CONTEXT }images/favicon.ico" type="image/x-icon" rel="icon" /> 
	<script type="text/javascript" src="${CONTEXT}js/jquery.min.js"></script> 
	<script type="text/javascript" src="${CONTEXT }js/login/login.js"></script> 
	<script src="${CONTEXT }js/lhgdialog/lhgdialog.min.js?skin=dblue"></script>
	<script src="${CONTEXT }js/login/jquery.logosDistort.min.js"></script> 
	<script src="${CONTEXT }js/login/jquery.particleground.min.js"></script> 
	<title>谷登运营后台登录</title>
	<style>
	.header .logo {
	    top: 30px;
	    position: fixed;
	    margin-left: -146px;
	    left: 50%;
	}
	.p_02, .p_03 {
	    width: 560px;
	    height: 352px;
    	margin: -180px 0 0 -280px;
    }
	.login_head {
    	background-color: #94C315;
    }
    .btn_div {
    	height: 96px;
    	text-align: center;
    }
	.input_div input {
		border:none;
	    float: left;
	    width: 326px;
	    margin-left: 20px;
	}
	</style>
</head>
<body>
<div id="login" style="text-align:left;"> 
<img id="loginBg" alt="background" src="${CONTEXT }images/background.jpg" />
  <div id="particle-target" ></div>
</div>
<!--header-->
<div class="header">
  <div class="headerNr">
    <div class="logo"><a href="#"></a></div>
  </div>
</div>
<!--end header-->
<form action="${CONTEXT }sys/login" method="post" id="loginForm">
<div class="content">
  <div class="div_bg"></div>
  <div class="p_02" style="display:block;">
    <div class="login_head"><span class="hf_left">谷登运营后台登录</span></div>
    <div class="div_bg01"></div>
    <div class="form_div">
      <div class="welcome p_04"> 你好,欢迎回来 </div>
      <div class="input_div"> <span>帐号：</span>
        <input type="text" id="userCode" name="userCode" value=""/>
      </div>
      <div class="input_div"> <span>密码：</span>
        <input type="password" id="userPassword" name="userPassword" value=""/>
      </div>
      <div class="btn_div">
        <input type="submit" class="greenBtn" value="登录" onclick="return loginFun()" /> 
        <div style="color:#FFE274" id="error">${error }</div>
        <!-- <a href="#" onclick="fogetPwd();">忘记密码</a> --> 
       </div>
    </div>
  </div>
</div>
</form>
<!--[if lte IE 8]>
<script type="text/javascript" src="${CONTEXT }js/jquery.min.js"></script> 
<![endif]-->
<script>
    var particles = true,
        particleDensity,
        options = {
            effectWeight: 1,
            outerBuffer: 1.08,
            elementDepth: 220
        };

    $(document).ready(function() {
    	
    	$("#userCode").focus();
    	
    	if (window != top){
    		top.location.href = location.href; 
    	}

        $("#login").logosDistort(options);

        if (particles) {
            particleDensity = window.outerWidth * 20;
            if (particleDensity < 10000) {
                particleDensity = 10000;
            } else if (particleDensity > 50000) {
                particleDensity = 50000;
            }
            return $('#particle-target').particleground({
				parallaxMultiplier: 6,
				particleRadius: 12,	  
				proximity: 160,
				lineWidth: 1,
				curvedLines: false,
				directionX: 'center',
				directionY: 'down',
				minSpeedX: 0.1,
				maxSpeedX: 0.7,
				minSpeedY: 0.1,
				maxSpeedY: 0.7,
//                 dotColor: 'rgba(179,223,168,0.6)',
//                 lineColor: 'rgba(179,223,168,0.4)',
                dotColor: 'rgba(255, 255, 255, 0.7)',
                lineColor: 'rgba(255, 255, 255, 0.5)',
                density: particleDensity.toFixed(0),
                parallax: true
            });
        }

    });
    
    //验证
    function loginFun(){
    	if($("#userCode").val()==""){
    		$("#error").html("请输入用户名");
    		return false;
    	}else if($("#userPassword").val()==""){
    		$("#error").html("请输入登录密码");
    		return false;
    	}
    	$("#loginForm").submit();
    }
    function fogetPwd(){
    	$.dialog({
    		title:"忘记密码！",
    		width:640,
    		height: 220,
    		fixed: true,
    		max: false,
    		min: false,
    		resize: false,
    		drag: false,
    		lock: true,
    		top: '48%',
    		content: '<p class="fs16">密码忘记了，需要进行密码重置操作。请咨询湘联采客服电话：0731-82309249.</p>',
    		ok: function(){
    		}
    	});
    	return false;
    }   
    
</script>
</body>
</html>