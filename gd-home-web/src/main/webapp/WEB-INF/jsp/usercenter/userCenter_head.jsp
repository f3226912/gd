<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%
	String marketId = (String)request.getAttribute("marketId");
	if(StringUtils.isEmpty(marketId)){
	    String cookieMarketId=CookieUtil.getValue("marketId");
		marketId=StringUtils.isEmpty(cookieMarketId) ? "1" : cookieMarketId;
	}
	request.setAttribute("marketId", marketId);
%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/head.inc" %>
<%@ include file="../pub/tags.inc" %>
<link rel="stylesheet" href="${CONTEXT }v1.0/css/member.css?v=<%=System.currentTimeMillis()%>"/>
<script lang="javaScript" src="${CONTEXT }js/login.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>
<script src="${CONTEXT }v1.0/js/kissy-min.js" charset="utf-8"></script>
<script src="${CONTEXT }v1.0/js/gd.kissy.uploader.js"></script>
<!--头部-->
	<div id="hear">
		<div class="hear-np-lef">
			<h3 class="hear-gd"><a href="${CONTEXT }userCenter" style="color:#069139;">谷登会员中心</a></h3>
			<i class="hear-line"></i>
		</div>
		
		<div class="hear-search">
			<form id="formHeadsearch" action="${CONTEXT }${gd:formatMarket(marketId) }/market.html" method="post">
			<input id="keyWord" type="text" class="sea-tet" placeholder="" value="${keyWord}"/>
			<input id="headSearch" name="keyWord" value="${keyWord}" type="hidden" class="headSearch" />
			<button class="sea-btn"><span class="sea-sr">搜索</span><em class="sea-ico"></em></button>
			<label class="input-place" for="keyWord">请输入你要查询的产品</label>
			</form>
		</div>

		<div class="hear-cent">
			<div class="hear-cent-rgt">
				 <a href="${CONTEXT }userCenter/msg/getList">消息中心 (<span class="cent-ml" id="_msgUnRead">${msgUnRead }</span>)</a> 
			</div>
			<ul class="hear-login">
				<li><a href="${CONTEXT}">谷登农批网</a></li>
				<li>
				<a onclick="Login.loginout()" href="javascript:;" class="ml10">退出登录</a>
				</li>
			</ul>
		</div>
	</div>
	<!--头部-->
	
<script>
$(function(){
	$(".sea-btn").click(function(){
		if (!$.trim($("#keyWord").val())) {
			return false;
		}
		var keyword=$("#keyWord").val();
		
// 		keyword=encodeURI(keyword);
		$("#headSearch").val($.trim(keyword));
		$('#formHeadsearch').submit();
	});
	checkSearchhander();
	function checkSearchhander(){
		if($("#keyWord").val()==''){
			$(".input-place").show()
		}else{
			$(".input-place").hide()
		}
	}
	$("#keyWord").bind("focus",function(){
		checkSearchhander()
	}).bind("blur",function(){
		checkSearchhander()
	}).bind("keyup",function(){
		checkSearchhander()
	})
});
		var url = CONTEXT + "userCenter/msg/getMsgCount";
		$.ajax({
			type : 'POST',
			url : url,
			dataType:'json', 
			data : {
			},
			success : function(data) {  
				 $("#_msgUnRead").html(data.msgUnRead);
			},
			error : function() {  
				alert("更新失败");
			}
		});
	
</script>
