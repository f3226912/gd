<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>物流订单详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/global.m.css">
    <link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/dist/css/mui.min.css">
    <link rel="stylesheet" href="${CONTEXT }v1.0/css/form-del.css">
   
    <script type="text/javascript">
    	function changeSendOrder(memberAddressId) {
    		$.ajax({
				url:"${CONTEXT }transfer/status/" + memberAddressId + "/${mem.source}",
				type:"get",
				dataType:"text",
				success:function(data){
					if(data == ""){
						aonClick(0,'memberAddressId='+memberAddressId);
						
					}else{
						mui.toast('订单已不是已发布状态');
					}
				},
				error:function(){
						mui.toast('操作失败，请重新操作一次！');
				}
			});
    	}
    
    
    	function aonClick(urlType,params){
			switch(urlType){
			case 0:JsBridge.edit(params);break;
			}
		}
    </script>
</head>
<body>
<div class="pullrefreshWrap-form mui-scroll-wrapper">
<!--已发布订单详情-->
<div class="mui-scroll">
		<div class="refreshcon">
			<div class="main">
				<div class="main-con">
					<p class="tit clearfix">${mem.timebefore }						
					<c:if test="${mem.nstRule==1 }">
						<span class="tit-rig fr">已分配至物流公司</span>
					</c:if>
					<span class="tit-rig fr">${mem.source==0?'[干线]':'[同城]' }</span>
					</p>
					<div class="con-img">
						<p class="img-p">
							<span class="addr span-addr1">
								<span class="add-con addr1 collapse">
									<c:if  test="${mem.source==0 }">
										${mem.s_provinceName }${mem.s_cityName }${mem.s_areaName }	
									</c:if>
									<c:if test="${mem.source==1 }">			
										<i class="glyphicon glyphicon-arrow"><i class="glyphicon"></i></i>								
										<span class="addr-less">
										</span>	
										<span class="addr-detail">															
										</span>											
									</c:if>							
								</span>															
							</span>							
							<span class="bus-ico"></span>	
							<span class="addr span-addr2">								
								<span class="add-con addr2">
									<c:if  test="${mem.source==0 }">
										${mem.f_provinceName }${mem.f_cityName }${mem.f_areaName }
									</c:if>									
									<c:if test="${mem.source==1 }">	
										<i class="glyphicon glyphicon-arrow"><i class="glyphicon"></i></i>									
										<span class="addr-less">
										</span>	
										<span class="addr-detail">															
										</span>									
									</c:if>	
								</span>																					
							</span>
						</p>
					</div>
					<c:if test="${mem.PDDNum > 0 }">
						<div class="cont-more co-ff6c00" id="cont-more2">
							货物详情&gt;&gt;
						</div>
					</c:if>
				</div>
				
				<jsp:include page="/WEB-INF/jsp/pub/transfer/detailTemplate.jsp"></jsp:include>
				<c:choose>
					<c:when test="${mem.nstRule!=1}">
						<div class="main-con mar-top btn-con clearfix">						
							<c:if test="${mem.source==0}">
								<p id="confirmBtn2" class="btn-qx fl btn-1 btn-2">修改重发</p>														
								<p id="confirmBtn3" class="btn-qx fl btn-1 btn-2">
									<span class="left-ico">
										<span class="left-ico-con"></span>
										删除	
									</span>
								</p>
							</c:if>		
							<c:if test="${mem.source==1}">
								<p id="confirmBtn3" class="btn-qx fl btn-1">
									<span class="left-ico">
										<span class="left-ico-con"></span>
										删除	
									</span>
								</p>
							</c:if>
						</div>
					</c:when>
					<c:otherwise>
						<div class="main-con mar-top btn-con clearfix">						
							<p id="confirmBtn3" class="btn-qx btn-1">删除</p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>

	<script type="text/javascript" src="${CONTEXT }v1.0/js/zepto.min.js"></script>
	<script type="text/javascript" src="${CONTEXT }v1.0/module/mui/dist/js/mui.min.js"></script>
	<!--<script src="${CONTEXT }v1.0/js/touch.js"></script>-->
	<script type="text/javascript" charset="utf-8">
	mui.init({
		pullRefresh: {
			container: '.pullrefreshWrap-form',
			down: {
				callback: pulldownRefresh
			}
		}
	});
	mui.init({
		swipeBack: true //启用右滑关闭功能
	});
	
//	addr
    $(function(){
    	
    	initAddr();
    	toggleDetailAddr();
    	ajustBusIco();
    });
    
    /**
     * 调整图标的位置
     */
    var ajustBusIco = function(){   
    	
    	$(".glyphicon-arrow").css({
   			"margin" : "10px 0px" 
    	});  
    	
    	var icoH = $(".bus-ico").height(),
    	 	addr1H = $(".addr1").height(),
			addr2H = $(".addr2").height(),
    		addrH = addr1H > addr2H ? addr1H : addr2H;    		
    		
   		$(".bus-ico").css({
       		"margin-bottom" : ((addrH - icoH) / 2) + "px"
       	});
   		$(".span-addr1").css({
    		"bottom" : "0px"
    	});
   		$(".span-addr2").css({
    		"bottom" : "0px"
    	});   		
   		
   		/* $(".glyphicon-arrow").css({
   			"margin" : ((addrH-6) / 2) +  "px 0px" 
    	});  */
   		
   		/* $(".span-addr2 .glyphicon-arrow").css({
   			"margin" : ((addr2H-6) / 2) +  "px 0px" 
    	}); */
   		
    	if(addrH == addr1H){ 		
    		
    		$(".span-addr2").css({
        		"bottom" : ((addrH - addr2H) / 2) + "px"
        	}); 		
    		 
    	}else{
    		
    		$(".span-addr1").css({
        		"bottom" : ((addrH - addr1H) / 2) + "px"
        	});   		
    	}
    	
    	
   		$(".glyphicon-arrow").css({
      			"margin" : ((addrH-6) / 2) +  "px 0px"
       	});    	
    };
    
    /**
    * 初始化日期
    */
    var initAddr = function(){
    	
    	var sAddr = "${mem.s_detail }${mem.s_detailed_address }" || '';
    	var fAddr = "${mem.f_detail }${mem.f_detailed_address }" || '';
    	
    	$(".addr1").children(".addr-detail").eq(0).html(sAddr);
    	$(".addr2").children(".addr-detail").eq(0).html(fAddr);
    	
    	if(sAddr.length > 20){
    		$(".addr1").children(".addr-less").eq(0).html(sAddr.substring(0, 20) + "...");
    	}else{
    		$(".addr1").children(".addr-less").eq(0).html(sAddr);
    		$(".addr1").children(".glyphicon-arrow").hide();
    	}
    	if(fAddr.length > 20){
    		$(".addr2").children(".addr-less").eq(0).html(fAddr.substring(0, 20) + "...");
    	}else{
    		$(".addr2").children(".addr-less").eq(0).html(fAddr);
    		$(".addr2").children(".glyphicon-arrow").hide();
    	}
    };
    
    /**
    * 控制显示与隐藏详情地址
    */
    var toggleDetailAddr = function(){    	
    	
    	var inner = function($selector){    		
    		
    		if($selector.hasClass("expand")){
    			$selector.removeClass("expand");
    		}else{
    			$selector.addClass("expand");
    		} 
    		ajustBusIco();
    	}	    	
    	
    	$(".addr1").bind('tap', function(){
    		inner($(this));
    	});
    	
    	$(".addr2").bind('tap', function(){
    		inner($(this));
    	});    	
    };


	/**
	 * 下拉刷新具体业务实现
	 */
	function pulldownRefresh() {
		location.href = location.href
	}
	
	if(document.getElementById('cont-more2')!= null) {
		document.getElementById('cont-more2').addEventListener('tap', function() {
			if (!mui.os.plus) {
				mui.openWindow({
					url: "${CONTEXT }transferDetail/${mem.id }/${mem.supplySourceType}/${mem.source}?fromCode=${fromCode}&memberId=${userId}&c=${c}&pa=showSendTranDetail&orderNo=${orderNo}&source=${mem.source}",
					id: "cont-more2",
					show: {
						aniShow: 'zoom-fade-out',
						duration: 300
					}
				});
				return;
			}
		});
	}
	
	$("#confirmBtn3").on('tap', function() {
		var btnArray = ['取消', '确定'],
			formId = $(".for-id").text();
		mui.confirm('确认删除该物流信息？', '', btnArray, function(e) {
			if (e.index == 1) {
					$.ajax({
						url:"${CONTEXT }transfer/delete/${mem.id}/${mem.source}",
						type:"get",
						dataType:"json",
						success:function(data){
							if(data.status == "0"){
								//debugger;
								mui.toast('操作成功！');
								setTimeout(messageGoBack,1000);
							}else{
								mui.toast(data.msg);
							}
						},
						error:function(){
								mui.toast('操作失败，请重新操作一次！');
						}
					});
				}else{
					$(".mui-popup-backdrop").hide();
				}
		});
		
		(function(){
			//setTimeout(function(){
				var $popup = $(".mui-popup"),
					winheight = $(window).height(),
					popheight = $popup.height(),
					popOffsett = $popup.offset().top;
				if((winheight-popheight)/2<popOffsett){
					$popup.css({"margin-left":"-"+135+"px","margin-top":"-"+popheight/2+"px"})
				}
			//},405)
			
		})();
	});

	function messageGoBack(){
		if('${c}'=='1') {
		mui.openWindow({
			url:CONTEXT+'transfer/sendList?memberId=${userId}&fromCode=${fromCode}'
	  	});
		} else {
			mui.openWindow({
				url:CONTEXT+'transfer/showList?orderNo=${orderNo}&fromCode=${fromCode}&userId=${userId}'
		  	});
		}
	};
	$(document).ready(function(){
		$("#confirmBtn2").on("tap",function(){
			changeSendOrder(${mem.id});
		})
		
	});
	//	btn置低
	$(function(){
		var $btn = $(".btn-con"),
			$mainHeight = $(".main").height(),
			$winHeight = $(window).height(),
			btnHeight = ($winHeight-$mainHeight);
		if($mainHeight < $winHeight){
			$btn.css("margin-top",btnHeight+'px');
		}
	});

	</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>