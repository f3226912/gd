<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.gdeng.cn/gd" prefix="gd" %>
<%@ include file="constants.inc" %>
<!--head star-->
<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
<script src="${CONTEXT }v1.0/js/home.js"></script>
<div class="head-search">
	<div class="wrap-1170 head-logo clearfix">
		<div class="logo-wrap">
			<a href="${CONTEXT2}${gd:formatMarket(marketId) }.html"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国领先的O2O农业电商平台"></a>
		</div>
		<div class="h-location clearfix">
			<div class="h-market-wrap">
				<span class="h-market-name" id="marketName"></span><br/>
				<a class="h-market-link js-showmap2" href="javascript:;">实景逛市场>></a>
			</div>
			<div class="h-market-l">
				<div class="h-markett">
					<span>切换市场</span><span class="h-arrow"></span>
					<input type="hidden" id="marketId" value="${marketId}" />
				</div>
				<div class="h-market-subm">
					<ul id="marketul">
					</ul>
				</div>
			</div>
		</div>
		<div class="seach-box">
			<form id="formHeadsearch" action="${CONTEXT }${gd:formatMarket(marketId) }/market.html" method="get">
			<input type="hidden" value="${searchModel }" id="searchModel" name="searchModel"/>
						<!-- searchModel用来标识搜索的类型，偶数为搜索产品，奇数为搜索商户 -->	
			<div class="h-search">
				<div class="h-search-twrap">
					<div class="h-search-cat">					
						<p class="h-search-catt"><a href="javascript:;" id="title1">产品</a><span class="h-arrow2"></span></p>
						<ul class="h-search-catlist">
							<li><a href="javascript:;" id="title2">商户</a></li>
						</ul>	
					</div>	
				</div>
<!-- 				<input name="act" id="search_act" value="discount" type="hidden">		 -->
<%-- 				<input id="headSearch2" value="${keyWord}" type="text" class="headSearch" /> --%>
				<input id="headSearch" name="keyWord" value="${keyWord}" type="text" class="headSearch" />
				<input name="isBig" value="${isBig}" type="hidden"/>
				<input id="headSearch-btn" type="button" value="搜索" class="headSearch-btn" style="cursor:pointer;"/>
			</div>
			</form>
			<!-- <div class="quick-search">
				<a href="#">白菜</a><a href="#">玉米</a><a href="#">西兰花</a><a href="#">冬枣</a>
			</div> -->
		</div>
	</div>
</div>

<script>
$(function(){
	getMarketList();
	if ($("#searchModel").val()%2!=0) {
		$("#title1").html("商户");
		$("#title2").html("产品");
		path="${CONTEXT }${gd:formatMarket(marketId) }/business.html";
	}else{
		$("#title1").text("产品");
		$("#title2").text("商户");
		path="${CONTEXT }${gd:formatMarket(marketId) }/market.html";
	}
	$('#formHeadsearch').attr("action", path);
	
	$("#headSearch-btn").click(function(){
		if (!$.trim($("#headSearch").val())) {
			return false;
		}
		var keyword=$("#headSearch").val();
//  		keyword=encodeURI(keyword);
		$("#headSearch").val($.trim(keyword));
		//var path =${CONTEXT}+"product/list"; 
		$('#formHeadsearch').submit();
	});
	
	/****搜索框键盘回车按键提交查询****/
	$("#headSearch").keypress(function(e) {
		if (e.which == 13){
			if (!$.trim($("#headSearch").val())) {
				return false;
			}
			var keyword=$("#headSearch").val();
// 			keyword=encodeURI(keyword);
			$("#headSearch").val($.trim(keyword));
			$('#formHeadsearch').submit();
		}
	})
	
	$("#title2").click(function(){
		var path =""; 
		var searchModel=$("#searchModel").val();
		if(!searchModel){
			searchModel=0;
		}
		
		if ($("#searchModel").val()%2==0) {
			$("#title2").html("产品");
			$("#title1").html("商户");
			path="${CONTEXT }${gd:formatMarket(marketId) }/business.html";
			$('#formHeadsearch').attr("action", path);
			while($('#formHeadsearch').attr("action").indexOf("business.html")==-1){
				$('#formHeadsearch').attr("action", path);
			}
		}else{
			$("#title2").html("商户");
			$("#title1").html("产品");
			path="${CONTEXT }${gd:formatMarket(marketId) }/market.html";
			$('#formHeadsearch').attr("action", path);
			while($('#formHeadsearch').attr("action").indexOf("market.html")==-1){
				$('#formHeadsearch').attr("action", path);
			}
		}
		$("#searchModel").val(parseInt(searchModel)+1);
		$(".h-search-catlist").hide();
		$('#formHeadsearch').attr("action", path);
	});
	$(".h-search-cat").mouseenter(function(){
		$(".h-search-catlist").show();
	});
 	$(".h-arrow2").click(function(){
 		$(".h-search-catlist").toggle();
 	});
	$(".h-search-cat").mouseleave(function(){
		$(".h-search-catlist").hide();
	}); 
});

function changeMarket(marketId){
	$.cookie('marketId',marketId, {expires:7,path:'/'});
	var hreftemp = window.location.href;
	if(hreftemp == CONTEXT2 || hreftemp.indexOf("baishazhou.html") > 0 || hreftemp.indexOf("yulin.html") > 0){
		if("1" == marketId){
			window.location.href = CONTEXT2 + "baishazhou.html";
		}else if("2" == marketId){
			window.location.href = CONTEXT2 + "yulin.html";
		}
	}else if(hreftemp.indexOf("zhuanti/tuiguang_wuhan") > 0 || hreftemp.indexOf("zhuanti/tuiguang_yulin") > 0){
		if("1" == marketId){
			window.location.href = CONTEXT2 + "zhuanti/tuiguang_wuhan";
		}else if("2" == marketId){
			window.location.href = CONTEXT2 + "zhuanti/tuiguang_yulin";
		}
	}else{
		window.location.href = window.location.href;
	}
}

function getMarketList(){
	$.ajax({
		type:'POST',
		url:CONTEXT+'index/getMarketList',
		dataType: 'json',
		async: false,
		error:function(data){
			//alert(data);
		},
		success:function(data){
			if(null != data){
				var addstr = "";
		    	if(data.length > 0){
		    		var indexmarketid = $("#marketId").val();
		    		if(null == indexmarketid || '' == indexmarketid){
		    			indexmarketid = $.cookie('marketId');
		    		}
		    		
		    		if(indexmarketid == "1"){
		    			$(".js-showmap2").html("实景逛市场>>");
		    		}else{
		    			$(".js-showmap2").html("");
		    		}
		    		
		    		for(var i = 0; i < data.length; i ++){
			    		var marketId = data[i].id;
			    		var marketName = data[i].marketName;
			    		//var strtemp = "<li><a href='javascript:changeMarket("+marketId+");'>"+marketName+"</a></li>";
						//addstr = addstr + strtemp;
						if(marketId == indexmarketid){
							$("#marketName").html(marketName);
						}
			    	}
		    	}
		    	addstr = "<li><a href='javascript:changeMarket(1);'>武汉白沙洲批发市场</a></li>" + 
		    	"<li><a href='javascript:changeMarket(2);'>广西玉林批发市场</a></li>";


		    	var hidetemp ="<li class='entered'>已入驻农商友APP </li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>黄石宏进农副产品物流中心</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>开封宏进农副产品物流中心</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>黄冈宏港农副产品批发市场</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>中国-东盟（钦州）市场</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>濮阳宏进农副产品物流中心</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>淮安宏进农副产品物流中心</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>徐州农副产品市场</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>洛阳宏进农副产品物流中心</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>盘锦宏进农副产品商贸城</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>南阳中商农产品批发市场</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>恩施华硒生态农产品批发市场</a></li>"+
		    	"<li><a href='http://www.gdeng.cn/nsy.shtml'>十堰市大臣农产品批发市场</a></li>"
				;

				var notEntered="<li class='entered'>入驻中 </li>"+
				"<li><a href='javascript:;' class='not-inserver'>抚州宏进农副产品物流中心</a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>郴州宏进农副产品物流中心</a></li>"+
				"<li ><a href='"+CONTEXT+"marketMap' target='_blank' class='h-mar-linkall'>全国农批市场》</a></li>";
	    		addstr = addstr + hidetemp + notEntered;
		    	$("#marketul").html(addstr);
			}else{
				$("#marketul").html("");
			}
		}
	});
}
</script>
<script>
	$(".js-showmap2").click(function(){			
		layer.open({
			type: 2,
		    title: "湖北武汉白沙洲农批市场", //显示标题
		    skin: 'layui-layer-rim', //加上边框
	    	content: [CONTEXT + 'jjMap'],
	    	cusClass:'comDialog',
		    area:["900px","607px"],
		    cancel: function(index){
		    	layer.close(index);

		    }
		})
	});
</script>