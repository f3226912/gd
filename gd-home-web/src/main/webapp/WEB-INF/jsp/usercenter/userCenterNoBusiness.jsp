<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>	
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/member.css"/>	
	
</head>
<body>
   <jsp:include page="./userCenter_head.jsp" flush="true"/> 
   <jsp:include page="./userCenter_left.jsp" flush="true"/> 
 
 
 
 
		<div class="mid-right">
			<h1 class="mid-right-store">商铺信息</h1>
			<div class="mid-right-store-att">
		
				<p class="p-store-att"><strong class="p-store-att-stg">欢迎您：${mdto.mobile}</strong>
				<span class="p-store-att-spn">
					<em class="spn-store-att-em">
						<c:if test="${mdto.isAuth ==1}">
						<i class="p-store-att-spn-ico1">
						</i>
						已认证
						</c:if>
						<c:if test="${mdto.isAuth !=1}">
						<i class="p-store-att-spn-ico2" style="cursor:pointer;">
						</i>
						未认证
						</c:if>
					</em>
				</span>
				</p>
				<div class="div-sub" ><span class="div-sub-kt">完善商铺信息就可以开通店铺哦</span>
					<a id="p-sub-aut" class="p-sub-kt" href="#">开通店铺</a>
				</div>
				<!--弹出层-->
				<div class="dn test">欢迎您来到谷登农批网会员中心完善您的商铺信息后可以开通商铺哦!
					<ul class="openshop">
						<li class="test-confirm"><a href="${CONTEXT }userCenter/business">马上完善</a></li>
						<li class="test-wait"><a href="javascript:;">先看看</a></li>
					</ul>
				</div>
				<!--弹出层-->
			</div>
  
			
			<div class="div-img02"><img src="${CONTEXT}v1.0/images/member/img-02.jpg"/></div>
			 
		</div>
	<div class="clear"></div>
	</div>
		
	 
</html>
 <c:if test="${ empty businessId &&  !empty reason }">
				 <script type="text/javascript">
		(function () {	 		
			layer.open({
				type: 1,
				title: "提示", //显示标题
				area:["360px","255px"],
				btn:["确定"],
				btn1:function(){
					window.location.href="${CONTEXT }userCenter/business";
				},
				content: '<div class="openshop1" style="padding-top:50px; font-size: 12px; line-height: 26px; font-weight: 600; text-align:center;">${reason}</div>', //捕获的元素
				cancel: function(){
					window.location.href = "#"

				}

			});
		})()
		</script>
				 </c:if>	

<script type="text/javascript">
		$(".p-store-att-spn-ico2").click(
			function(){
				window.location.href="${CONTEXT }userCenter/attest/ent";
			});

		$("#p-sub-aut").click(
		 
		function () {
			layer.open({
				type: 1,
			    title: "开通商铺", //显示标题
				area:["360px","255px"],
				btn:["马上完善","先看看"],
				btn1:function(){
					window.location.href="${CONTEXT }userCenter/business";
				},
				btn2:function(index){
					layer.close(index);
				},
			    content: '<div class="openshop1" style="padding-top:50px; font-size: 12px; line-height: 26px; font-weight: 600; text-align:center;">欢迎您来到谷登农批网会员中心完善您的商<br/>铺信息后可以开通商铺哦!</div>', //捕获的元素
			    cancel: function(index){
			    layer.close(index);

			    }

			});
		});
		</script>
	
		