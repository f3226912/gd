<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="pub/constants.inc" %>
<%@ include file="pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=utf-8" />
<meta name="pragma" content="no-cache"/>
<meta name="cache-control" content="no-cache"/>
<meta name="expires" content="0"/>
<title>中国领先的O2O农业电商平台_谷登农批网${gd:formatMarket2(marketId)}</title>
<meta name="keywords" content="农产品,农产品批发,农产品交易,农产品采购,农产品供应,谷登农批网${gd:formatMarket2(marketId)}"/>
<meta name="description" content="谷登农批网${gd:formatMarket2(marketId)}，提供${gd:formatMarket2(marketId)}最全农产品及商户信息，及时发布${gd:formatMarket2(marketId)}最新农产品价格和供需资讯，并提供高效农产品物流服务，帮助解决农产品交易中资金问题—想赚钱，就上谷登农批网。"/>
<meta name="renderer" content="webkit">
<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<%@ include file="pub/head.inc" %>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/home.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="nav_title"><span>全部分类</span>
				<div class="nav_title_show"></div>
			</div>
			<!-- 主导航栏 -->
			<div class="main_menu">
			<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
			<!-- 二级分类 -->
			<jsp:include page="/WEB-INF/jsp/pub/headerSidenav.jsp"></jsp:include>
		</div>
	</div>
	<!--head end-->
	<!--slide start-->
	<div id="nav_home"></div>
	<div class="i-slide-wrap mt15">
		<div class="wrap-1170 i-slide-box clearfix">
			<div id="" class="ty_picScroll mgauto index-banner">		
				<div class="ty_tabInfo">
					<ul>
						<c:forEach items="${adInfoList}" var="adInfo" varStatus="s" begin="0" end="5">
							<li class="ty_pic">
								<c:choose>
									<c:when test="${adInfo.adLinkUrl == '' || adInfo.adLinkUrl == null}">
										<img src="${imgHostUrl}${adInfo.adUrl}" width="672" height="401" >
									</c:when>
									<c:otherwise>
										<a href="${adInfo.adLinkUrl}" target="_blank">
											<img src="${imgHostUrl}${adInfo.adUrl}" width="672" height="401" >
										</a>
									</c:otherwise>
								</c:choose>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<!--公告 start-->
			<div class="notice-tool">
				<div class="totalDet">
					<div class="totalNum">
						<div class="totalNumT"><span class="totalNumTit">市场动态</span><span class="i-nowtime"></span></div>
						<div class="totalNumlist">
							<p>总产品数<span class="numImportant"><span class="totalProduct">${pint }</span>件</span></p>
							<!-- <p>昨日交易额<span class="numImportant">¥<span class="yesterdayAmounts">${gd:formatNumber(msdto.yestodaySale) }</span></span></p>
							<p>今日交易额<span class="numImportant">¥<span class="todayAmounts">${gd:formatNumber(msdto.todaySale) }</span></span></p> -->
						</div>
					</div>	
					<div class="i-scroll-wrap ">
						<ul class="i-scroll-name clearfix">
							<li class="i-scrn-item1" style="color: #069139">品名</li>
							<li class="i-scrn-item2" style="color: #069139">高价</li>
							<li class="i-scrn-item3" style="color: #069139">低价</li>
							<li class="i-scrn-item4" style="color: #069139">平均价</li>
							<li class="i-scrn-item5" style="color: #069139">涨跌</li>
						</ul>
						<div class="i-scroll-box">
							<ul class="i-scroll-list clearfix">
								<c:forEach items="${pricesList}" var="obj" varStatus="s">
									<li class="clearfix">
										<div class="i-scrn-item i-scrn-item1">
											<c:choose>
												<c:when test="${obj.targetUrl == '' || obj.targetUrl == null}">
													${obj.productName}
												</c:when>
												<c:otherwise>
													<a href="${obj.targetUrl}" target="_blank">${obj.productName}</a>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="i-scrn-item i-scrn-item2">￥${obj.maxPrice}</div>
										<div class="i-scrn-item i-scrn-item3">￥${obj.minPrice}</div>
										<div class="i-scrn-item i-scrn-item4">￥${obj.averagePrice}</div>
										<div class="i-scrn-item i-scrn-item5">
											<fmt:formatNumber value="${obj.averagePrice}" pattern="0" var="flag"/>
											<c:choose>
												<c:when test="${flag%2==0 }">
													<span class="dows-ico-up"></span>
												</c:when>
												<c:otherwise>
													<span class="dows-ico-down"></span>
												</c:otherwise>
											</c:choose>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!--公告 end-->
		</div>
	</div>
	<!--slide end-->
	<!--main start-->
	<div class="wrap-1170 clearfix">
		<!--推荐 start-->
		<div class="i-recommend-list clearfix">
			<ul>
				<c:forEach items="${adInfoFList}" var="adInfoF" varStatus="s" begin="0" end="4">
					<li>
						<c:choose>
							<c:when test="${adInfoF.adLinkUrl == '' || adInfoF.adLinkUrl == null}">
								<img src="${imgHostUrl}${adInfoF.adUrl}" width="232" height="98" >
							</c:when>
							<c:otherwise>
								<a href="${adInfoF.adLinkUrl}" target="_blank">
									<img src="${imgHostUrl}${adInfoF.adUrl}" width="232" height="98" >
								</a>
							</c:otherwise>
						</c:choose>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!--推荐 end-->
		<!--类目 start-->
		<div class="clearfix i-com-cat-wrap">
			<!--冻品区-->
			<c:forEach items="${pclist}" var="category" varStatus="s">
				<div class="i-com-cat-box com-cat-bdt${s.count}"><!--  -->
					<div class="i-com-tbox clearfix">
						<h2 class="i-com-t"><span class="i-com-t-ico"><img src="${imgHostUrl}${category.webTypeIcon}" width="25" height="25" /></span>${category.cateName}区</h2>
						<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${category.categoryId }.html" target="_blank" title="更多" class="i-com-more">更多>></a>
					</div>
					<div class="i-com-cbox clearfix">
						<div class="i-com-catl">
							<div class="i-com-catlList">
								<c:forEach items="${category.childList}" var="category2" varStatus="s" begin="0" end="2">
									<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${category2.categoryId }.html" target="_blank" title="${category2.cateName}">${category2.cateName}</a>
								</c:forEach>						
							</div>
							<c:forEach items="${category.adInfoList}" var="adinfo" varStatus="s" begin="0" end="0">
								<a href="${adinfo.adLinkUrl}"><img src="${imgHostUrl}${adinfo.adUrl}" target="_blank" width="179" height="192" ></a>
							</c:forEach>
						</div>
						<div class="i-com-catr clearfix">
							<div class="i-com-catlList">
								<c:forEach items="${category.childList3}" var="childList3" varStatus="s" begin="0" end="4">
									<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${childList3.categoryId }.html" title="${childList3.cateName}">${childList3.cateName}</a>
								</c:forEach>
								<c:if test="${fn:length(category.childList3) > 5}">
									<span class="i-com-catrmw">
										<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${category.categoryId }.html" title="更多" class="i-com-catrmore">>></a>
										<div class="i-com-catmlist">
											<c:forEach items="${category.childList3}" var="childList3" varStatus="s" begin="5">
												<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${childList3.categoryId }.html" title="${childList3.cateName}">${childList3.cateName}</a>
												<span class="header-sidenav-spilt">|</span>
											</c:forEach>
										</div>
									</span>
								</c:if>
							</div>
							<ul class="i-com-neslist clearfix">
								<c:forEach items="${category.childList[0].childList[0].productList}" var="product" varStatus="s" begin="0" end="2">
									<li><span class="i-com-time"><fmt:formatDate pattern="MM-dd" value="${product.createTime}" /></span><a href="${CONTEXT}market/${product.productId}.html" target="_blank" title="${product.productName}">
										<c:set var="productNameStr" value="${product.productName}" />
										<c:choose>
											<c:when test="${fn:length(productNameStr) > 5}">
												<c:out value="${fn:substring(productNameStr, 0, 5)}" />...
											</c:when>
											<c:otherwise>
												<c:out value="${productNameStr}" />
											</c:otherwise>
										</c:choose>
									</a><span class="i-com-price"><c:set var="pricedec" value="${gd:formatNumber(product.price)}" /><c:choose><c:when test="${pricedec == '面议'}"><c:out value="${pricedec}" /></c:when><c:otherwise>¥<c:out value="${pricedec}" /></c:otherwise></c:choose></span></li>
								</c:forEach>
								<c:forEach items="${category.childList[0].childList[1].productList}" var="product" varStatus="s" begin="0" end="2">
									<li><span class="i-com-time"><fmt:formatDate pattern="MM-dd" value="${product.createTime}" /></span><a href="${CONTEXT}market/${product.productId}.html" target="_blank" title="${product.productName}">
										<c:set var="productNameStr" value="${product.productName}" />
										<c:choose>
											<c:when test="${fn:length(productNameStr) > 5}">
												<c:out value="${fn:substring(productNameStr, 0, 5)}" />...
											</c:when>
											<c:otherwise>
												<c:out value="${productNameStr}" />
											</c:otherwise>
										</c:choose>
									</a><span class="i-com-price"><c:set var="pricedec2" value="${gd:formatNumber(product.price)}" /><c:choose><c:when test="${pricedec2 == '面议'}"><c:out value="${pricedec2}" /></c:when><c:otherwise>¥<c:out value="${pricedec2}" /></c:otherwise></c:choose></span></li>
								</c:forEach>
							</ul>
							<ul class="loc-map-list clearfix">
								<c:forEach items="${category.businessList}" var="business" varStatus="s" begin="0" end="3">
									<li class="loc-map-ico fn-text"><a href="${CONTEXT}${gd:formatMarket(marketId) }/business/shop/${business.businessId }.html">
										<c:set var="shopsNameStr" value="${business.shopsName}" />
										<c:choose>
											<c:when test="${fn:length(shopsNameStr) > 11}">
												<c:out value="${fn:substring(shopsNameStr, 0, 11)}" />...
											</c:when>
											<c:otherwise>
												<c:out value="${shopsNameStr}" />
											</c:otherwise>
										</c:choose>
									</a></li>
								</c:forEach>
							</ul>
						</div>	
					</div>
				</div>
			</c:forEach>
			<c:if test="${pclistsize%2!=0 }">
				<div class="yulin-img fl" style="margin-top:55px;">
					<img src="v1.0/images/yulin-img.jpg" >
				</div>
			</c:if>
			<!--冻品区 end-->
		</div>
		<!--类目 end-->
		<!--货主找车-->
		<div class="i-wl-box clearfix">
			<div class="i-wl-wrap">
				<div class="i-com-tbox clearfix">
					<h2 class="i-com-t"><span class="i-com-wl-ico i-com-wl-ico1"></span>货主找车</h2>
					<a href="${CONTEXT}logistics.html" target="_blank" title="更多" class="i-com-more">更多>></a>
				</div>
				<div class="wuliu-list">
					<table width="200" border="0" cellspacing="0" cellpadding="0">
						<c:forEach items="${cllist}" var="carline" varStatus="s" begin="0" end="4">
							<tr>
							    <td><span class="wl-start">${carline.startPlace }</span></td>
							    <td><span class="wl-ico"></span></td>
							    <td><span class="wl-start">${carline.endPlace }</span></td>
							    <td><div class="wl-width">
							    	<c:choose>
								        <c:when test="${carline.price >0}">${gd:formatNumber(carline.price)} 元/吨</c:when>
								        <c:otherwise>面议</c:otherwise>	
								    </c:choose>
							    </div></td>
							    <td>发车时间</td>
							    <td>${carline.sendDateString}</td>
							    <td>${carline.sentDateTypeString}</td>
							</tr>
						</c:forEach>
					</table>	
				</div>				
			</div>
			<div class="i-wl-wrap">
				<div class="i-com-tbox clearfix">
					<h2 class="i-com-t"><span class="i-com-wl-ico i-com-wl-ico2"></span>司机找货</h2>
					<a href="${CONTEXT}logistics/queryGoodsList" target="_blank" title="更多" class="i-com-more">更多>></a>
				</div>
				<div class="wuliu-list">
					<table width="200" border="0" cellspacing="0" cellpadding="0">
						<c:forEach items="${goodsList}" var="goods" varStatus="s" begin="0" end="4">
							<tr>
							    <td><span class="wl-start">
							    <c:choose>
							    <c:when test="${empty goods.s_cityName || goods.s_cityName eq '' || goods.s_cityName eq '市辖区' || goods.s_cityName eq '县' || goods.s_cityName eq '市' }">
							     ${goods.s_provinceName}
							    </c:when>
							    <c:otherwise>
							    ${goods.s_cityName}
							    </c:otherwise>
							    </c:choose>
							    </span></td>
							    <td><span class="wl-ico"></span></td>
							    <td><span class="wl-start">
							    <c:choose>
							    <c:when test="${empty goods.f_cityName || goods.f_cityName eq '' || goods.f_cityName eq '市辖区' || goods.f_cityName eq '县' || goods.f_cityName eq '市' }">
							     ${goods.f_provinceName}
							    </c:when>
							    <c:otherwise>
							    ${goods.f_cityName}
							    </c:otherwise>
							    </c:choose>
							    </span></td>
							    <td><div class="wl-width2">${goods.goodsName}</div></td>
							    <td>装车时间</td>
							    <td>${goods.sendDateString}</td>
							    <td>${goods.sendDateTypeString}</td>
							</tr>
						</c:forEach>
					</table>	
				</div>				
			</div>
		</div>
		<!--货主找车 end-->
	</div>
	<!--main end-->
	<div class="bottom-banner">
		<div class="wrap-1170 bottom-ban">
			<ul class="bottom-ban-l clearfix">
				<li class="clearfix bottom-ban-item1">
					<span  class="b-ban-ico b-ban-ico1"></span>
					<dl class="b-ban-txt">
						<dt>专注农批</dt>
						<dd>全国13家知名一级批发市场率先入驻</dd>
					</dl>
				</li>
				<li class="clearfix">
					<span  class="b-ban-ico b-ban-ico2"></span>
					<dl class="b-ban-txt">
						<dt>精准货源</dt>
						<dd>批发市场精准农产信息</dd>
					</dl>
				</li>
				<li class="clearfix bottom-ban-item3">
					<span  class="b-ban-ico b-ban-ico3"></span>
					<dl class="b-ban-txt">
						<dt>实时价格</dt>
						<dd>每日更新，全国最新</dd>
					</dl>
				</li>
				<li class="clearfix">
					<span  class="b-ban-ico b-ban-ico4"></span>
					<dl class="b-ban-txt">
						<dt>全国物流</dt>
						<dd>一站解决货源，车源</dd>
					</dl>
				</li>
				<li class="clearfix bottom-ban-item5">
					<span  class="b-ban-ico b-ban-ico5"></span>
					<dl class="b-ban-txt">
						<dt>源头好货</dt>
						<dd>买手遍布全国，提供源头好货</dd>
					</dl>
				</li>
			</ul>
		</div>
	</div>
	<!-- 友情链接start -->
	<div class="txtScroll-top">
    <h3 class="txtScroll-nav">友情链接：</h3>
  	<div class="bd">
        <div class="tempWrap" style="overflow:hidden; position:relative; height:24px">        	
        	<div class="infoList clearfix">
<!--         		<a href="http://www.xgzgo.com" target="_blank">纯茶油</a> -->
<!-- 				<a target="_blank" href="http://www.cnnclm.com ">家庭农场</a> -->
<!-- 				<a href="http://www.ruyinyuanlin.com/" target="_blank">江颜园林</a> -->
<!-- 				<a href="http://www.szkzs.com/" target="_blank">食品调料网</a> -->
<!-- 				<a href="http://www.wfgxsyxx.com" target="_blank">蔬菜大棚建设</a> -->
<!-- 				<a href="http://www.gjmmyy.com" target="_blank">花卉苗木</a> -->
<!-- 				<a href="http://www.hqnjl.cn" target="_blank">长兴农家乐</a> -->
<!-- 				<a href="http://www.lanhaizi.cn" target="_blank">蓝孩子农资招商网</a> -->
<!-- 				<a href="http://www.c1-c1.com" target="_blank">银杏树</a> -->
<!-- 				<a href="http://www.hbmmjd.com" target="_blank">河北苗木基地</a>				 -->
<!-- 				<a target="_blank" href="http://www.yucmall.cn">粘虫板</a> -->
<!-- 				<a target="_blank" href="http://www.xxhr.org">杀虫灯</a> -->
<!-- 				<a target="_blank" href="http://www.mqybj.com">灭蚊器</a> -->
<!-- 				<a target="_blank" href="http://www.yubangbang.com">渔病医院</a> -->
<!-- 				<a target="_blank" href="http://www.jinhunongye.com">铁皮石斛种植</a> -->
<!-- 				<a target="_blank" href="http://www.boxing6.com">肉牛价格</a> -->
<!-- 				<a target="_blank" href="http://www.shumiao.tw">苗木</a> -->
<!-- 				<a target="_blank" href="http://www.hywjsljd.com">河阴石榴树苗</a> -->
<!-- 				<a target="_blank" href="http://www.tianzhugui.net">天竺桂</a> -->
<!-- 				<a target="_blank" href="http://www.hszhushu.com">广西竹鼠</a> -->
<!-- 				<a target="_blank" href="http://www.zzt369.com">韭蛆特效药</a> -->
<!-- 				<a href="http://www.jlsjsm.com" target="_blank">长春蔬菜配送</a> -->
<!-- 				<a href="http://www.seed17.com" target="_blank">种子仪器</a> -->
<!-- 				<a target="_blank" href="http://www.gsnjl.cn">长兴农家乐</a> -->
<!-- 				<a target="_blank" href="http://www.b8gy.com">肉鸽养殖</a> -->
<!-- 				<a target="_blank" href="http://www.51wyj.net">大樱桃苗</a> -->
<!-- 				<a target="_blank" href="http://www.grgyedu.com">冬枣苗</a> -->
<!-- 				<a target="_blank" href="http://www.jkyfs.com">进口鱼粉价格</a> -->
<!-- 				<a target="_blank" href="http://www.35838.com">养猪网</a> -->
<!-- 				<a target="_blank" href="http://www.ahcfyl.com">北美红栎小苗</a> -->
<!-- 				<a target="_blank" href="http://www.nongyeguihua.com">休闲农业规划</a> -->
<!-- 				<a target="_blank" href="http://nongyao.1988.tv">农药价格</a> -->
<!-- 				<a target="_blank" href="http://www.nykcw.com">农业快车网</a> -->
<!-- 				<a target="_blank" href="http://www.inongjishu.com">农业技术网</a> -->
<!-- 				<a target="_blank" href="http://www.ddlhmm.com">高杆红叶石楠</a> -->
<!-- 				<a target="_blank" href="http://www.xiangcunmm.com">香椿苗</a> -->
<!-- 				<a target="_blank" href="http://www.imiansha.com">色纱</a> -->
<!-- 				<a target="_blank" href="http://www.guo52.com">板栗批发</a> -->
	        	<c:forEach var="friendsLinks" items="${friendsLinksList}">
						<a href="${friendsLinks.linkUrl }" target="_blank">${friendsLinks.linkName }</a>
				</c:forEach>

        	</div>
        </div>
  	</div>
  	<p class="txtScroll-more"><a href="${CONTEXT}about/marketlink" target="_bank">更多&gt;&gt;</a></p>
  </div>
	<!-- 友情链接end -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!-- fixed tool -->
	<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>
	
<script type="text/javascript">
$(function(){
	$.ajax({
		type:'POST',
		url:CONTEXT+'login/isLogin',
		dataType: 'json',
		success:function(data){
			if(data.mbdto!=null){
				centertemp="<a href='${CONTEXT}userCenter' title='进入我的会员中心' class='i-go-meb fl'>进入我的会员中心</a>";
				$("#memberChoose").html(centertemp);
			}else{
				logintemp="<a href='${CONTEXT}login/initRegister' title='注册' class='i-signin-btn fl'>注  册</a>"+
				"<a href='${CONTEXT}login/initLogin' title='登录' class='i-signout-btn fr'>登  录</a>";
				$("#memberChoose").html(logintemp);
			}
		}
	});
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth() + 1;
	var day = time.getDate(); 
	$(".i-nowtime").html(year+"/"+month+"/"+day);
});
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>

