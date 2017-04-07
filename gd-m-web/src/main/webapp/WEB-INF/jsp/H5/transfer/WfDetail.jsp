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
    	function aonClick(urlType,params){
			switch(urlType){
			case 0:JsBridge.call(params);break;
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
					<p class="tit clearfix">运单号：<span class="for-id">${mem.orderNo }</span>
					<c:if test="${mem.orderStatusInt==1 }">
						<span class="tit-rig fr">已有车主接单</span>
					</c:if>
					<span class="tit-rig fr">${mem.source==0?'[干线]':'[同城]' }</span>
					</p>
					<div class="main-con bor-bot">
						<div class="det-lef clearfix">
							<p class="det-tit clearfix"><span class="det-t det-t2 fl bus-num1">${mem.ownerName } ：</span> <span class="det-txt fl bus-num2">${mem.carNumber }</span></p>
							<p class="det-tit clearfix"><span class="det-t det-t2 fl">身份证 ：</span>  
							<c:if test="${not empty mem.nst_cardPhotoUrl }">
							<span class="det-txt fl">已认证</span>
							</c:if>
							<c:if test="${empty mem.nst_cardPhotoUrl }">
							<span class="det-txt fl">未认证</span>
							</c:if>
							</p> 
							<p class="det-tit clearfix"><span class="det-t det-t2 fl">行驶证 ：</span>
								<c:if test="${not empty mem.nst_vehiclePhotoUrl }">
								<span class="det-txt fl">已认证</span>
								</c:if>
								<c:if test="${empty mem.nst_vehiclePhotoUrl }">
								<span class="det-txt fl">未认证</span>
								</c:if>
							</p>
							<p class="det-tit clearfix"><span class="det-t det-t2 fl">驾驶证  ：</span>
								<c:if test="${not empty mem.nst_driverPhotoUrl }">
								<span class="det-txt fl">已认证</span>
								</c:if>
								<c:if test="${empty mem.nst_driverPhotoUrl }">
								<span class="det-txt fl">未认证</span>
								</c:if>
							</p>
						</div>
					</div>
					<div class="con-img">
						<p class="img-p">
							<span class="addr span-addr1">
								<span class="add-con addr1">
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
							查看货物详情>>
						</div>
					</c:if>
				</div>
				
				<jsp:include page="/WEB-INF/jsp/pub/transfer/detailTemplate.jsp"></jsp:include>
				<c:if test="${mem.orderStatusInt==1 && mem.nstRule==0}">
				<div class="main-con mar-top btn-con clearfix">
					<p id="confirmBtn4" class="btn-qx fl btn-1 btn-2 btn-3">拒绝</p>
					<p id="confirmBtn5" class="btn-qx fl btn-1 btn-2 btn-3 co-ff6c00-01">
						<span class="left-ico">
							<span class="left-ico-con"></span>
							接受
						</span>
					</p>
					<p id="confirmBtn6" class="btn-qx fl btn-1 btn-3 co-ff6c00-01">
						<span class="left-ico-con"></span>
						<a class="db btn-pho1" >
							<span class="btn-ico-con co-ff6c00">
								<span class="btn-ico04"></span>拨打电话
							</span>
						</a>
					</p>
				</div>
				</c:if>
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
	
	if(document.getElementById('cont-more2')!=null) {
		document.getElementById('cont-more2').addEventListener('tap', function() {
			if (!mui.os.plus) {
				mui.openWindow({
					url: "${CONTEXT }transferDetail/${mem.id }/${mem.supplySourceType}/${mem.source}?fromCode=${fromCode}&memberId=${userId}&c=${c}&pa=showWfTranDetail&orderNo=${orderNo}&nobId=${mem.nobId}&source=${mem.source}",
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
	
	if(document.getElementById("confirmBtn4")!=null) {
		document.getElementById("confirmBtn4").addEventListener('tap', function() {
			var btnArray = ['取消', '确定'],
				formId = $(".for-id").text();
			mui.confirm('确认拒绝当前车主接单？', '', btnArray, function(e) {
				if (e.index == 1) {
						$.ajax({
							url:"${CONTEXT }transfer/refuseOrder",
							type:"get",
							data:{'memberId':'${mem.userId}','orderNo':'${mem.orderNo}'},
							dataType:"json",
							success:function(data){
								if(data.status == "0"){
									mui.toast('操作成功！');
									sessionStorage.setItem("countNum", "4");
									messageGoBack();
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
	}
	
	if(document.getElementById("confirmBtn5")!=null) {
		document.getElementById("confirmBtn5").addEventListener('tap', function() {
			var btnArray = ['取消', '确定'],
				formId = $(".for-id").text();
			mui.confirm('确认接受当前车主接单？', '', btnArray, function(e) {
				if (e.index == 1) {
						$.ajax({
							url:"${CONTEXT }transfer/acceptOrder",
							type:"get",
							data:{'memberId':'${mem.userId}','orderNo':'${mem.orderNo}'},
							dataType:"json",
							success:function(data){
								if(data.status == "0"){
									mui.toast('操作成功！');
									sessionStorage.setItem("countNum", "2");
									messageGoBack();
								}else{
									mui.toast(data.msg);
								}
									
							},
							error:function(){
									mui.toast('操作失败！');
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
	}
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
	}
	$(document).ready(function(){
		$("#confirmBtn6").on("tap",function(){
			aonClick(0, 'mobile=${mem.ownerMobile }' );
			
			//调用接口拨打电话
			$.ajax({
				url:"${CONTEXT }transfer/addCall",
				type:"post",
				data:{
					'sysCode':'${fromCode}',
					's_Mobile':'${mem.ownerMobile}',
					's_Name':'${mem.ownerName}',
					'e_Mobile':'${mem.contactMobile}',
					'e_Name':'${mem.contactName}',
					'fromCode':4,
					'memberid':'${mem.userId }'
				},
				dataType:"text",
				success:function(data){
						
				},
				error:function(){
				}
			});
			
		})
		
	})
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