<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/tags.inc"%>
<!DOCTYPE html>
<html>
<head>
<base href="${CONTEXT }">
<%@ include file="../pub/head.inc"%>
<meta name="Content-Type" content="text/html; charset=utf-8" />
<meta name="pragma" content="no-cache" />
<meta name="cache-control" content="no-cache" />
<meta name="expires" content="0" />
<title>司机找货_货源干线支线专线信息_货源物流服务_谷登农批网</title>
<meta name="keywords" content="空车配货,空车找货源,货车找货源,货车配货,物流配货,货源信息" />
<meta name="description" content="物流服务司机找货频道汇聚全国货源信息，在此可找到相关货源信息，适合干线、支线、专线货车配货或空车配货，空车找货源配货,司机货车找货源,农产品物流配货等，谷登农批网是农产品交易物流运输信息服务的开放平台。" />
<meta name="renderer" content="webkit">
<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/css/cityLayout.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/js/skin/gduiPage.css">
</head>
<body>
<c:set var="pageNavigateModel" value="2"></c:set>
<!--head star-->
<%@ include file="/WEB-INF/jsp/pub/loginForm.jsp" %>
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
<!--nav start-->
<div class="nav">
<div class="nav_inside clearfix">
<div class="main_menu check-nav">
<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
</div>
</div>
</div>
<div class="main clearfix">
	<div class="content mgtop">
		<div class="con-left fl">
			<div class="con-left-tit check-img logi-right">
				<p>查询路线</p>
				<div class="logi-rm">
					<p>
						<span class="logi-city-hack">发货地</span>&nbsp<input id="sel1"
							type="text" readonly="readonly"
							value="<c:if test="${!empty map.beginPlace && map.beginPlace ne 'null'&& map.beginPlace ne 'nullnull'}">${map.beginPlace }</c:if>"
							autocomplete="off"
							class="city_input proCityQueryAll proCitySelAll" ov="城市名（中文/拼音）">
					</p>
					<p>
						<span class="logi-city-hack">收货地</span>&nbsp<input id="sel2"
							type="text" readonly="readonly"
							value="<c:if test="${!empty map.destPlace && map.destPlace ne 'null'&& map.destPlace ne 'nullnull'}">${map.destPlace }</c:if>"
							autocomplete="off"
							class="city_input proCityQueryAll proCitySelAll" ov="城市名（中文/拼音）">
					</p>
					<p>
						<span class="logi-search" onclick="logiSearchClick();">开始查询</span>
					</p>
				</div>
			</div>
			<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}"> 
			<input type="hidden" id="currPage" name="currPage" value="${currPage}"> 
<!-- 				<input type="hidden" id="page" name="page" value="">  -->
			<form id="form-search" action="${CONTEXT}logistics/1-1.html" method="get">
<!-- 		        <input type="hidden" id="searchFlag" name="searchFlag" value="">  -->
				<input type="hidden" id="s_provinceId" name="s_provinceId" value="${map.s_provinceId }">
				<input type="hidden" id="s_provinceName" name="s_provinceName"value="${map.s_provinceName }"> 
				<input type="hidden" id="s_cityId" name="s_cityId" value="${map.s_cityId }"> 
				<input type="hidden" id="s_cityName" name="s_cityName" value="${map.s_cityName }"> 
				<input type="hidden" id="s_areaId" name="s_areaId" value="${map.s_areaId }"> 
				<input type="hidden" id="s_areaName" name="s_areaName" value="${map.s_areaName }"> 
				<input type="hidden" id="e_provinceId" name="e_provinceId" value="${map.e_provinceId }">
				<input type="hidden" id="e_provinceName" name="e_provinceName" value="${map.e_provinceName }"> 
				<input type="hidden" id="e_cityId" name="e_cityId" value="${map.e_cityId }"> 
				<input type="hidden" id="e_cityName" name="e_cityName" value="${map.e_cityName }"> 
				<input type="hidden" id="e_areaId" name="e_areaId" value="${map.e_areaId }"> 
				<input type="hidden" id="e_areaName" name="e_areaName" value="${map.e_areaName }">
			</form>
		</div>
	</div>
	<div class="fl">
		<div class="logistics-tit clearfix">
			<a href="${CONTEXT}logistics/0-1.html">
			<h4 class="products-h4 fl logi-tit-1 logi-bacg-white"><span class="products-h4-span">货主找车</span></h4></a>
			<a href="${CONTEXT}logistics/1-1.html">
			<h4 class="products-h4 fl logi-tit-2 logi-bacg-gray"><span class="products-h4-span">司机找货</span></h4></a>
		</div>

		<!-- 司机找货开始 -->
		<c:if test="${!empty goodsList }">
		<div class="con-right products-con logi-main logi-change1">
			<c:forEach items="${goodsList}" var="obj">
				<div class="mark-list clearfix logi-con">
					<div class="logi-list-left fl logi-width1">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr class="logi-city">
								<td><div class="logi-city-tit">从：${obj.s_provinceName}&nbsp;&nbsp;${obj.s_cityName}&nbsp;&nbsp;${obj.s_areaName}</div></td>
								<td width="90"><img
									src="v1.0/images/shop-images/logi-img.jpg" alt=""></td>
								<td width="205">到：${obj.f_provinceName}&nbsp;&nbsp;${obj.f_cityName}&nbsp;&nbsp;${obj.f_areaName}</td>
							</tr>
						</table>
						<p class="logi-left-tit">
							<span class="logi-sty">${obj.goodsName}
								&nbsp;&nbsp;&nbsp;&nbsp;${obj.goodsTypeString} &nbsp;&nbsp;</span> <span
								class="logi-car">运输要求：
								&nbsp;&nbsp;${obj.sendGoodsTypeString}&nbsp;&nbsp;</span> 
						</p>
						<c:if
							test="${obj.sendDateString !=null &&  obj.sendDateString !=''}">
							<p class="logi-left-tit">
								<span class="logi-sty">发车时间：<span class="logi-span-col">${obj.sendDateString}</span>${obj.sendDateTypeString}</span>
							</p>
						</c:if>
					</div>
					<div class="logi-list-center fl logi-width2">
						<p class="logi-span-col">
							<c:choose>
								<c:when test="${obj.price >0}"> ${obj.priceUnitTypeString}</c:when>
								<c:otherwise>
				                   面议
				                </c:otherwise>
							</c:choose>
						</p>

					</div>
					<div class="logi-list-right fr logi-width3">
						<c:choose>
							<c:when test="${isLogin==true}">
								<span class="logi-tel-show"><span
									class="logi-tel-ico-show"></span>${obj.companyMobile}</span>
							</c:when>
							<c:otherwise>
								<a><span class="logi-tel showLoginDialog"><span
										class="logi-tel-ico"></span>联系方式</span></a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
			<div id="mar-com-fenye" class="fenye" style="display: none;"></div>
			<div id="searchPage" class="fenyetest" style="text-align: center;margin-bottom: 20px;">
				<%@ include file="../pub/pagination/transPage.jsp" %>
			</div>
		</div>
		</c:if>
		<!-- 司机找货结束 -->

		<!-- 无搜索结果页面开始 -->
		<c:if test="${empty goodsList }">
			<div class="con-right products-con logi-main">
				<p class="logi-tips"><span class="nosearch-ico"></span>抱歉，没有找到符合 <span class="logi-tips-span">
				<c:if test="${!empty map.beginPlace && map.beginPlace ne 'null'&& map.beginPlace ne 'nullnull'}">${map.beginPlace }</c:if>
				<span style="color: black;"> 至</span>
				<c:if test="${!empty map.destPlace && map.destPlace ne 'null'&& map.destPlace ne 'nullnull'}">${map.destPlace }</c:if>
				</span> 相关路线的查询结果。</p>
			</div> 
		</c:if>
		<!-- 无搜索结果页面结束 -->
	</div>
	<div class="cl"></div>
</div>

<!--底部 star-->
<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<div class="provinceCityAll">
	<div class="tabs clearfix">
		<ul class="">
			<li><a href="javascript:" class="current" tb="hotCityAll">热门城市</a></li>
			<li><a href="javascript:" tb="provinceAll">省份</a></li>
			<li><a href="javascript:" tb="cityAll" id="cityAll">城市</a></li>
			<li><a href="javascript:" tb="countyAll" id="countyAll">区县</a></li>
		</ul>
	</div>
	<div class="con">
		<div class="hotCityAll invis">
			<div class="pre">
				<a></a>
			</div>
			<div class="list">
				<ul>
				</ul>
			</div>
			<div class="next">
				<a class="can"></a>
			</div>
		</div>
		<div class="provinceAll invis">
			<div class="pre">
				<a></a>
			</div>
			<div class="list">
				<ul>
				</ul>
			</div>
			<div class="next">
				<a class="can"></a>
			</div>
		</div>
		<div class="cityAll invis">
			<div class="pre">
				<a></a>
			</div>
			<div class="list">
				<ul>
				</ul>
			</div>
			<div class="next">
				<a class="can"></a>
			</div>
		</div>
		<div class="countyAll invis">
			<div class="pre">
				<a></a>
			</div>
			<div class="list">
				<ul>
				</ul>
			</div>
			<div class="next">
				<a class="can"></a>
			</div>
		</div>
	</div>
</div>
<!--底部 end-->

<!-- fixed tool -->
<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>

<script src="${CONTEXT}v1.0/js/common.js"></script>
<script src="${CONTEXT}js/trans/citySelect.js"></script>
<script>
	var totalPage = $("#totalPage").val();
	var currPage = $("#currPage").val();
	if(totalPage<1){
		totalPage=1;
	}
	function logiSearchClick(){
		$("#page").val(1);
		if ($("#sel1").val() == '' || $("#sel2").val() == '') {
			layer.msg("请输入完整的查询条件!");
			return false;
		}
		if (!$("#s_provinceId").val() && !$("#e_provinceId").val()) {
			return false;
		}
		$("#searchFlag").val("1");
		$("#form-search").submit();
	}
	gduiPage({
		cont : 'mar-com-fenye',
		pages : totalPage, //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
		curr : currPage,
		jump : function(e, first) { //触发分页后的回调
			if (!first) { //一定要加此判断，否则初始时会无限刷新
				//location.href = '${CONTEXT}transportation/queryCarLineList?page='+e.curr;
				$("#page").val(e.curr);
				$("#form-search").submit();
			}
		},
		staticPage : true
	});
	$(".logi-tit-2").removeClass("logi-bacg-white");
	$(".logi-tit-2").addClass("logi-bacg-gray");
	$(".logi-tit-1").removeClass("logi-bacg-gray");
	$(".logi-tit-1").addClass("logi-bacg-white");
	$(".logi-con").last().addClass("mark-bor");
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>