<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
 
</head>
<body>
   <jsp:include page="./userCenter_head.jsp" flush="true"/> 
   <jsp:include page="./userCenter_left.jsp" flush="true"/> 
 
		
		<div class="mid-right">
			<h1 class="mid-right-store">商铺信息</h1>
			<div class="mid-right-store-att">
				<p class="p-store-att"><strong class="p-store-att-stg">欢迎您：${bdto.shopsName}商铺</strong>
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
					</em></span>
				</p>
				<div class="div-sub" ><input type="submit" value="查看店铺" class="p-sub"/></div>
				<!--弹出层-->
					<div class="dn test">欢迎您来到谷登农批网会员中心完善您的商铺信息后可以开通商铺哦!
						<ul class="openshop">
							<li class="test-confirm"><a href="javascript: business();">马上完善</a></li>
							<li class="test-wait"><a href="javascript:;">先看看</a></li>
						</ul>
					</div>
				<!--弹出层-->
			</div>
		
			<div class="right-store-por">
				<div class="right-store-por-sell">
					<i class="store-por-sell-ico"></i>
					<strong class="store-por-sell-str">销售中的产品：<em class="store-por-sell-str-item">${countLine}</em></strong>
					<!-- <input type="button" value="发布" class="store-por-sell-but" onclick="publish()"/> -->
					
					<c:if test="${loginMember.level !=4 }">
					<input type="button" value="发布" class="store-por-sell-but" onclick="publish()"/>
					</c:if>
				</div>
				<div class="right-store-por-sell">
					<i class="store-por-sell-ico store-por-sell-ico1"></i>
					<strong class="store-por-sell-str">未上架的产品：<em class="store-por-sell-str-item">${countOffLine}</em></strong>
					<input type="submit" value="查看" class="store-por-sell-but" onclick="unpublish()" />
				</div>
				<div class="right-store-por-sell">
					<i class="store-por-sell-ico store-por-sell-ico2"></i>
					<strong class="store-por-sell-str">发布物流需求</strong>
					<input type="submit" value="发布" class="store-por-sell-but" onclick="transportation()" />
				</div>
				<div class="right-store-por-sell right-store-por-sell-bor-0">
					<i class="store-por-sell-ico store-por-sell-ico3"></i>
					<strong class="store-por-sell-str">关注我的客户：<em class="store-por-sell-str-item">${countAttant}</em></strong>
					<input type="submit" value="查看" class="store-por-sell-but"  onclick="focusme()"/>
				</div>
			</div>
		</div>
		 <%-- <%@ include file="navigation.jsp" %> --%>
	<div class="clear"></div>
	</div>
	
	<!--简版底部 star-->
	
	<!--简版底部 end-->
<script type="text/javascript">
$(".p-sub").click(
	function () {
		window.location.href="${CONTEXT}${gd:formatMarket(marketId) }/business/shop/${bdto.businessId }.html";
	/**	layer.open({
			type: 1,
		    title: "开通商铺", //显示标题
		    content: $('.test'), //捕获的元素
		    cancel: function(index){
		    layer.close(index);

		    }

		});
	*/
	});

	$(".p-store-att-spn-ico2").click(
		function(){
			window.location.href="${CONTEXT }userCenter/attest/ent";
		});
		
function publish(){
	window.location.href="${CONTEXT }userCenter/product/chooseCategory";
}
function business(){
	window.location.href="${CONTEXT }userCenter/business";
}
function unpublish(){
	window.location.href="${CONTEXT }userCenter/product/saleList/4";
}
function transportation(){
	window.location.href="${CONTEXT }userCenter/transportation/query";
}
function focusme(){
	window.location.href="${CONTEXT }userCenter/collect/focusme";
}

</script>
</html>
