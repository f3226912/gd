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
<title>货主找车_整车零担冷链常温运输_农产品物流服务-谷登农批网</title>
<meta name="keywords" content="农产品物流,零担货运,零担运输,整车零担,零担,零担物流,整车运输,冷链运输" />
<meta name="description" content="物流服务货主找车频道为货主提供农产品物流需求信息，用户可根据需要找到货物零担运输、货物整车运输、冷链运输、整车零担货运等方式，谷登农批网货主找车服务是农产品交易顺利完成的重要保障。" />
<meta name="renderer" content="webkit">
<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/css/cityLayout.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/js/skin/gduiPage.css">
</head>
<body>
<c:set var="pageNavigateModel" value="1"></c:set>
<!--head star-->
<%@ include file="/WEB-INF/jsp/pub/loginForm.jsp" %>
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
<div class="nav">
<div class="nav_inside clearfix">
<div class="main_menu check-nav">
<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
</div>
</div>
</div>
<!--head end-->
<!--slide start-->
<!-- 主体 -->
<div class="main clearfix">
	<div class="content mgtop">
		<div class="con-left fl">
			<div class="con-left-tit check-img logi-right">
				<div class="con-left-tit check-img logi-right"
					style="margin-top: -15px;">
					<p>查询路线</p>
					<div class="logi-rm">
						<p>
							<span class="logi-city-hack">发货地</span>&nbsp<input id="sel1"
								readonly="readonly" type="text"
								value='<c:if test="${!empty map.beginPlace&& map.beginPlace ne 'null'&& map.beginPlace ne 'nullnull'}">${map.beginPlace }</c:if>'
								autocomplete="off"
								class="city_input proCityQueryAll proCitySelAll" ov="城市名">
						</p>
						<p>
							<span class="logi-city-hack">收货地</span>&nbsp<input id="sel2"
								readonly="readonly" type="text"
								value="<c:if test="${!empty map.destPlace&& map.destPlace ne 'null'&& map.destPlace ne 'nullnull' }">${map.destPlace }</c:if>"
								autocomplete="off"
								class="city_input proCityQueryAll proCitySelAll" ov="城市名">
						</p>
						<p>
							<span class="logi-search" onclick="logiSearchClick();">开始查询</span>
						</p>
					</div>
				</div>
				<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}"> 
				<input type="hidden" id="currPage" name="currPage" value="${currPage}"> 
<!-- 						<input type="hidden" id="page" name="page" value="">  -->
				<form id="form-search" action="${CONTEXT}logistics/0-1.html" method="get">
<!-- 					<input type="hidden" id="searchFlag" name="searchFlag" value="">  -->
					<input type="hidden" id="s_provinceId" name="s_provinceId" value="${map.s_provinceId }"> 
					<input type="hidden" id="s_provinceName" name="s_provinceName" value="${map.s_provinceName }"> 
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
				<h4 class="products-h4 fl logi-tit-1 logi-bacg-gray"><span class="products-h4-span">货主找车</span></h4></a>
				<a href="${CONTEXT}logistics/1-1.html">
				<h4 class="products-h4 fl logi-tit-2 logi-bacg-white"><span class="products-h4-span">司机找货</span></h4></a>
			</div>
			<!-- <div class="logistics-tit clearfix">
			<select id="sortName" name="sortName">
				     <option value="id">默认发布</option>
				     <option value="createTime">最新发布</option>
				     </select>
			</div> -->
			<!-- 货主找车 开始-->
			<c:if test="${!empty list }">
			<div class="con-right products-con logi-main logi-change1">
				<c:forEach items="${list}" var="carLine">
					<div class="mark-list clearfix logi-con">
						<div class="logi-list-left fl logi-width1">
							<table border="0" cellspacing="0" cellpadding="0">
								<tr class="logi-city">
									<td><div class="logi-city-tit">从：${carLine.startPlace}</div></td>
									<td width="90"><img
										src="v1.0/images/shop-images/logi-img.jpg" alt="查询"></td>
									<td width="205">到：${carLine.endPlace}</td>
								</tr>
							</table>
							<p class="logi-left-tit">
								<span class="logi-sty">发运方式：${carLine.sendGoodsTypeString}</span>
								<span class="logi-car">车辆类型：${gd:formatCarType(carLine.carType)}
									&nbsp;&nbsp;
									${carLine.maxLoad}吨&nbsp;&nbsp;${carLine.carLength}米 </span>
							</p>
							<p class="logi-left-tit">
								<c:if
									test="${carLine.sendDateString !=null &&  carLine.sendDateString !=''}">
									<span class="logi-sty">发车时间：<span class="logi-span-col">${carLine.sendDateString}</span>${carLine.sentDateTypeString}</span>
								</c:if>
								<c:if
									test="${carLine.onLineHours !=null &&  carLine.onLineHours >0}">
									<span class="logi-time">在途时间：<span
										class="logi-span-col">${carLine.onLineHours}</span> 天
									</span>
								</c:if>
								<c:if
									test="${carLine.endDateString !=null &&  carLine.endDateString !=''}">
									<span class="logi-day">截止日期：<span class="logi-span-col">${carLine.endDateString}</span></span>
								</c:if>
							</p>
						</div>
						<div class="logi-list-center fl logi-width2">
							<p class="logi-span-col">
								<c:choose>
<%-- 								<c:when test="${carLine.price >0}"> ${carLine.price}${carLine.unitTypeString}</c:when> --%>
									<c:when test="${carLine.price >0}"> ${carLine.unitTypeString}</c:when>
									<c:otherwise>面议</c:otherwise>
								</c:choose>
							</p>
						</div>
						<div class="logi-list-right fr logi-width3">
							<!-- 是否登录系统 -->
							<c:choose>
								<c:when test="${isLogin==true}">
									<span class="logi-tel-show"><span
										class="logi-tel-ico-show"></span>${carLine.phone}</span>
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
			<!-- 货主找车 结束-->
			<!-- 无搜索结果页面开始 -->
			<c:if test="${empty list }">
			<div class="con-right products-con logi-main">
				<p class="logi-tips"><span class="nosearch-ico"></span>抱歉，没有找到符合 <span class="logi-tips-span">
				<c:if test="${!empty map.beginPlace && map.beginPlace ne 'null'&& map.beginPlace ne 'nullnull'}">${map.beginPlace } </c:if>
				<span style="color: black;"> 至</span>
				<c:if test="${!empty map.destPlace && map.destPlace ne 'null'&& map.destPlace ne 'nullnull'}">${map.destPlace }</c:if>
				</span> 相关路线的查询结果。</p>
			</div> 
			</c:if>
			<!-- 无搜索结果页面结束 -->
		</div>
		<div class="cl"></div>
	</div>
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
<script type="text/javascript">
	var totalPage = $("#totalPage").val();
	var currPage = $("#currPage").val();
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

	$(".logi-tit-1").removeClass("logi-bacg-white");
	$(".logi-tit-1").addClass("logi-bacg-gray");
	$(".logi-tit-2").removeClass("logi-bacg-gray");
	$(".logi-tit-2").addClass("logi-bacg-white");
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
	$(".logi-con").last().addClass("mark-bor");
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>