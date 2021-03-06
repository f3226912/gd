<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
					<c:if test="${mem.orderStatusInt==4 }">
						<span class="tit-rig fr">已拒绝车主接单</span>
					</c:if>
					<c:if test="${mem.orderStatusInt==5 }">
						<span class="tit-rig fr">车主已取消接单</span>
					</c:if>
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
							<span class="addr span-addr1"><span class="add-con addr1">${mem.s_provinceName }${mem.s_cityName }${mem.s_areaName }</span></span>
							<span class="bus-ico"></span>
							<span class="addr span-addr2"><span class="add-con addr2">${mem.f_provinceName }${mem.f_cityName }${mem.f_areaName }</span></span>
						</p>
					</div>
					<c:if test="${mem.PDDNum > 0 }">
						<div class="cont-more co-ff6c00" id="cont-more2">
							查看货物详情>>
						</div>
					</c:if>
				</div>
				
				<jsp:include page="/WEB-INF/jsp/pub/transfer/detailTemplate.jsp"></jsp:include>
				<div class="main-con mar-top btn-con clearfix">
					<p id="confirmBtn9" class="btn-qx fl btn-1 co-ff6c00-01">
						<span class="left-ico-con"></span>
						<a class="db btn-pho2">
							<span id="confirmBtn111" class="btn-ico-con co-ff6c00">
								<span class="btn-ico04"></span>拨打电话
							</span>
						</a>
					</p>
				</div>
				
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
    	var add1Height = $(".addr1").height(),
    		add2Height = $(".addr2").height(),
    		icoHeight = $(".bus-ico").height();
    	if(add1Height > add2Height){
    		var textHeight1 = (add1Height - add2Height)/2,
    			icoHeight1 = (add1Height - icoHeight)/2;
    		$(".span-addr2").css("bottom",textHeight1+"px");
    		$(".bus-ico").css("margin-bottom",icoHeight1+"px");
    	}else{
    		var textHeight2 = (add2Height - add1Height)/2,
    			icoHeight2 = (add2Height - icoHeight)/2;
    		$(".span-addr1").css("bottom",textHeight2+"px");
    		$(".bus-ico").css("margin-bottom",icoHeight2+"px");
    	}
    });
	
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
					url: "${CONTEXT }transferDetail/${mem.id }?fromCode=${fromCode}&memberId=${userId}&c=${c}&pa=showufTranDetail&orderNo=${orderNo}&nobId=${mem.nobId}",
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
		$("#confirmBtn111").on("tap",function(){
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
</body>
</html>
