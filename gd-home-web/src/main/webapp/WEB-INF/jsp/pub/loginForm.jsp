<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="dla-login dn">
    <h2 class="dla-login-t clearfix"><a class="dla-login-t-link" href="${CONTEXT}login/initRegister">还没有账号，去注册</a><span class="dla-login-ttxt">登录谷登农批</span></h2>
    <form id="loginForm" name="login" action="#" method="post">
    <div class="login-tips dn"></div>
    <ul class="login-list">
        <li>
            <div class="l-inp-wrap clearfix">
                <span class="bor">
                    <input type="text" id="uname" name="loginName" value="${cookie['username'].value}" class="input-text input-text-login" placeholder="账户名/手机号码" maxlength="16" data-err="请输入6~16个字符的用户名（一个汉字占用2个字符）">  
                </span>
            </div>
        </li>
        <li>
            <div class="l-inp-wrap clearfix">
                <span class="bor mr01">
                    <input type="password" id="upwd" name="loginPassword" class="input-text input-text-login" placeholder="密码">
                </span>
            </div>
        </li>
        <li><input class="btn-red-dia btn-dia-login" type="button" value="立即登录"></li>
        <li class="clearfix dia-log-tool">
            <label class="leb-txt">
                <input type="checkbox" value="" checked="checked" class="ck-temp rememberAccount">记住账号</label>
            <a class="login-link fr" href="${CONTEXT}login/initGetPassword1">忘记登录密码？</a>
        </li>
    </ul>
    </form>
</div>
<script type="text/javascript" src="${CONTEXT }js/login.js"></script>