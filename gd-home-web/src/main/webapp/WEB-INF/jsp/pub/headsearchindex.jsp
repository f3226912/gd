<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--head star-->

<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
<script src="${CONTEXT }v1.0/js/home.js"></script>
<div class="head-search">
	<div class="wrap-1170 head-logo clearfix">
		<div class="logo-wrap">
			<a href="${CONTEXT}"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国领先的O2O农业电商平台"></a>
		</div>
		<div class="h-location clearfix">
			<div class="h-market-wrap">
				<span class="h-market-name" id="marketName"></span><br/>
				<a class="h-market-link js-showmap2" href="javascript:;">实景逛市场>></a>
			</div>
			<div class="h-market-l">
				<div class="h-markett">
					<span>更多市场</span><span class="h-arrow"></span>
					<input type="hidden" id="indexmarketid" value="${indexmarketid}" />
				</div>
				<div class="h-market-subm">
					<ul id="marketul"></ul>
				</div>
			</div>
		</div>
		<div class="seach-box">
			<form id="formHeadsearch" action="${CONTEXT}product/list" method="post">
			<div class="h-search">
				<div class="h-search-twrap">
					<div class="h-search-cat">
						<!-- searchModel用来标识搜索的类型，偶数为搜索产品，奇数为搜索商户 -->
						<input type="hidden" value="${searchModel }" id="searchModel" name="searchModel"/>
						<li><p class="h-search-catt"><span class="h-search-tit"><a href="javascript:;" id="title1">产品</a></span><span class="h-arrow2"></span></p></li>
						<ul class="h-search-catlist">
							<li><a href="javascript:;" id="title2">商户</a></li>
						</ul>	
					</div>	
				</div>
				
<!-- 				<input name="act" id="search_act" value="discount" type="hidden">		 -->
<%-- 				<input id="headSearch2" value="${keyWord}" type="text" class="headSearch" /> --%>
				<input id="headSearch" name="keyWord" value="${keyWord}" type="text" class="headSearch" />
				<input name="isBig" value="${isBig}" type="hidden" class="isBig" />
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
		path="${CONTEXT}business/list";
	}else{
		$("#title1").text("产品");
		$("#title2").text("商户");
		path="${CONTEXT}product/list";
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
		$("#searchModel").val(parseInt(searchModel)+1);
		if ($("#searchModel").val()%2==0) {
			$("#title2").html("产品");
			$("#title1").html("商户");
			path="${CONTEXT}product/list";
			$('#formHeadsearch').attr("action", path);
			while($('#formHeadsearch').attr("action").indexOf("product")==-1){
				$('#formHeadsearch').attr("action", path);
			}
		}else{
			$("#title2").html("商户");
			$("#title1").html("产品");
			path="${CONTEXT}business/list";
			$('#formHeadsearch').attr("action", path);
			while($('#formHeadsearch').attr("action").indexOf("business")==-1){
				$('#formHeadsearch').attr("action", path);
			}
		}
		$(".h-search-catlist").hide();
		$('#formHeadsearch').attr("action", path);
	});
	$(".h-search-catt").mouseover(function(){
		$(".h-search-catlist").show();
	});
 	$(".h-arrow2").click(function(){
 		$(".h-search-catlist").toggle();
 	});
	$(".h-search-catlist").mouseleave(function(){
		$(".h-search-catlist").hide();
	});
});

function changeMarket(marketId){
	$.cookie('marketId',marketId, {expires:7,path:'/'});
	if("1" == marketId){
		window.location.href = CONTEXT2 + "baishazhou.html";
	}else if("2" == marketId){
		window.location.href = CONTEXT2 + "yulin.html";
	}
}

function getMarketList(){
	$.ajax({
		type:'POST',
		url:CONTEXT+'index/getMarketList/',
		dataType: 'json',
		error:function(data){
			//alert(data);
		},
		success:function(data){
			if(null != data){
				var addstr = "";
		    	if(data.length > 0){
		    		var indexmarketid = $.cookie('marketId');
		    		if(null == indexmarketid || '' == indexmarketid){
		    			indexmarketid = "1";
		    		}
		    		
		    		if(indexmarketid == "1"){
		    			$(".js-showmap2").html("实景逛市场>>");
		    		}else{
		    			$(".js-showmap2").html("");
		    		}
		    		
		    		for(var i = 0; i < data.length; i ++){
			    		var marketId = data[i].id;
			    		var marketName = data[i].marketName;
			    		var strtemp = "<li><a href='javascript:changeMarket("+marketId+");'>"+marketName+"</a></li>";
						addstr = addstr + strtemp;
						if(marketId == indexmarketid){
							$("#marketName").html(marketName);
						}
			    	}
		    	}
		    	var hidetemp = "<li><a href='javascript:;' class='not-inserver'>黄石宏进农副产品物流中心<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>徐州农副产品市场<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>淮安宏进农副产品物流中心<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>中国-东盟（钦州）市场<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>抚州宏进农副产品物流中心<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>盘锦宏进农副产品商贸城<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>郴州宏进农副产品物流中心<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>濮阳宏进农副产品物流中心<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='javascript:;' class='not-inserver'>洛阳宏进农副产品物流中心<br/><span class='not-inserver-tips'>（线上入驻中）</span></a></li>"+
				"<li><a href='"+CONTEXT+"marketMap' target='_blank' class='h-mar-linkall'>全国农贸市场》</a></li>";
	    		addstr = addstr + hidetemp;
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