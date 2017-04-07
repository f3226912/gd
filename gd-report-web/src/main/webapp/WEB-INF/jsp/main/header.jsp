<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/index.css"/>
<div class="navigation">
    <div class="logo"></div>
    <div class="logininfo">
        <div class="logout">
            <a href="../j_spring_cas_security_logout" id="loginOut">退出</a>
            <a href="#" onclick="editPassword()">[修改密码]</a>
        </div>
        <div class="userinfo">
                                    欢迎，<a>${userSummaryForAuthority.name }</a>
        </div>
        <div class="usericon"></div>
    </div>
</div>