<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>
<%
	String marketId = (String)request.getAttribute("marketId");
	if(StringUtils.isEmpty(marketId)){
	    String cookieMarketId=CookieUtil.getValue("marketId");
		marketId=StringUtils.isEmpty(cookieMarketId) ? "1" : cookieMarketId;
	}
	request.setAttribute("marketId", marketId);
%>
<!DOCTYPE html>
<html>
<head>
<base href="${CONTEXT }">
<meta charset="utf-8">
<meta name="pragma" content="no-cache"/>
<meta name="cache-control" content="no-cache"/>
<meta name="expires" content="0"/>
<c:choose>
<c:when test="${!empty keyWord}">
<title>共有${gd:formatMarket2(marketId)}${keyWord}供应/采购/批发价格/库存信息${totalCount}条_谷登农批网</title>
<meta name="keywords" content="${keyWord},${keyWord}价格,${keyWord}库存,${keyWord}销售量,${keyWord}供应商,${keyWord}市场,${keyWord}批发市场"/>
<meta name="description" content="谷登农批网为您找到${totalCount}条${gd:formatMarket2(marketId)}${keyWord}产品信息，包括${keyWord}供应商，${keyWord}采购市场,${keyWord}批发价格等；全面了解${gd:formatMarket2(marketId)}${keyWord}批发市场信息就在谷登农批网。"/>
</c:when>
<c:otherwise>
<c:if test="${!empty cateName }">
<title>${gd:formatMarket2(marketId)}${cateName }产品供应/采购/批发资源库_谷登农批网</title>
<meta name="keywords" content="${cateName }供应,${cateName }采购,${cateName }批发,${cateName }批发价格,${cateName }市场,${cateName }批发市场"/>
<meta name="description" content="谷登农批网为您找到${totalCount}条${gd:formatMarket2(marketId)}${cateName }产品信息，包括${gd:formatMarket2(marketId)}${cateName }产品价格，${cateName }产品商家,${cateName }产品库存等；全面了解${gd:formatMarket2(marketId)}${cateName }批发市场信息就在谷登农批网。"/>
</c:if>
<c:if test="${empty cateName }">
<title>谷登农批网最全农产品种类采购/批发/供应信息_${gd:formatMarket2(marketId)}</title>
<meta name="keywords" content="农批市场,农产品供应,农产品采购,农产品价格,农产品批发价格,农产品批发市场"/>
<meta name="description" content="谷登农批网${gd:formatMarket2(marketId)}频道聚集大量的${gd:formatMarket2(marketId)}农产品信息，用户可以在线掌握${gd:formatMarket2(marketId)}农产品供应商信息，找到最全${gd:formatMarket2(marketId)}农产品采购市场，了解最新${gd:formatMarket2(marketId)}农产品批发价格—农产品线上交易，就选谷登农批网。"/>
</c:if>
</c:otherwise>
</c:choose>
<%@ include file="../../pub/head.inc" %>
<link rel="stylesheet" href="v1.0/css/index-shop.css">
</head>
<body>
<c:set var="pageNavigateModel" value="1" />
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
<div class="nav">
<div class="nav_inside clearfix">
<div class="nav_title"><span>全部分类</span>
<div class="nav_title_show"></div>
</div>
<div class="main_menu">
<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/pub/headerSidenav.jsp"></jsp:include>
</div>
</div>
<div class="main clearfix">
	<div class="content mgtop clearfix">
		<div class="con-left fl">
			<div class="con-left-tit pa-bot-no">
				<c:forEach var="cate2" items="${secondCate }" varStatus="s2">
				<div class="mark-sidebar">
					<c:choose>
						<c:when test="${cateId2 eq cate2.categoryId || cate2.categoryId eq productCategory.categoryId || cate2.categoryId eq productCategory.parentId}">
							<h5 class="mark-sidebar-tit mark-list-bg"><a class="mark-list-bg" style="font-weight: 600;" href="${CONTEXT}${gd:formatMarket(marketId) }/market/${cate2.categoryId }.html" title="${cate2.cateName }">${cate2.cateName }</a><span class="fr mark-sidebar-ico-down"></span></h5>
							<ul class="mark-sidebar-tit-2" style=" display:block;">
						</c:when>
						<c:otherwise>
							<h5 class="mark-sidebar-tit"><a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${cate2.categoryId }.html" title="${cate2.cateName }">${cate2.cateName }</a><span class="mark-sidebar-ico fr"></span></h5>
							<ul class="mark-sidebar-tit-2" style=" display:none;">
						</c:otherwise>
					</c:choose>
						<c:forEach var="cate3" items="${thirdCate }" varStatus="s3">
							<c:if test="${cate3.parentId eq cate2.categoryId }">
							<li>
								<c:choose>
									<c:when test="${cate3.categoryId eq cateId3 || cate3.categoryId eq productCategory.categoryId}">
										<a class="mark-list-bg" style="font-weight: 600;" href="${CONTEXT}${gd:formatMarket(marketId) }/market/${cate3.categoryId }.html" title="${cate3.cateName }">${cate3.cateName }</a>
									</c:when>
									<c:otherwise>
										<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${cate3.categoryId }.html" title="${cate3.cateName }">${cate3.cateName }</a>
									</c:otherwise>
								</c:choose>
							</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				</c:forEach>
			</div>
		</div>
		<c:if test="${!empty productSolrDTOList}">
		<div class="fl">
			<div class="con-right line-bottom mag-top">
				<div class="right-m">
					<p class="right-m-t"><a href="${CONTEXT }${gd:formatMarket(marketId) }/market.html"><span class="mark-ico01" style="margin-right: 5px;"></span>所有产品</a></p>
				</div>
			</div>
			<div class="con-right line-top">
				<div class="right-m">
				<form id="form-seach" action="${CONTEXT }${gd:formatMarket(marketId) }/market.html" method="post">
					<p class="right-m-t pro-new">
						<span style="margin-right: 10px;">
							<select name="businessModel" id="businessModel" class="mark-sty">
								<option value="">经营模式</option>
								<option value="0" <c:if test="${productQueryBean.businessModel eq '0'}">selected="selected"</c:if>>个人经营</option>
								<option value="1" <c:if test="${productQueryBean.businessModel eq '1'}">selected="selected"</c:if>>企业经营</option>
							</select>
							<select name="rangeDays" id="rangeDays" class="mark-sty mark-sty-time">
								<option value="">所有发布时间</option>
								<option value="1" <c:if test="${productQueryBean.rangeDays eq '1'}">selected="selected"</c:if>>1天内的信息</option>
								<option value="3" <c:if test="${productQueryBean.rangeDays eq '3'}">selected="selected"</c:if>>3天内的信息</option>
								<option value="7" <c:if test="${productQueryBean.rangeDays eq '7'}">selected="selected"</c:if>>7天内的信息</option>
								<option value="30" <c:if test="${productQueryBean.rangeDays eq '30'}">selected="selected"</c:if>>30天内的信息</option>
							</select>
						</span>
						<a id="priceOrderLocation" href="${CONTEXT }${gd:formatMarket(marketId) }/market/0-1.html?priceOrder=asc">
						<span class="pro-seach-tit">价格：</span>
						<c:if test="${empty priceOrder }">
							<span id="priceOrderBtn" class="price-ico-bj3" style="margin-right: 10px;"></span>
						</c:if>
						<c:if test="${ priceOrder eq 'desc' }">
							<span id="priceOrderBtn" class="price-ico-bj1" style="margin-right: 10px;"></span>
						</c:if>
						<c:if test="${ priceOrder eq 'asc'}">
							<span id="priceOrderBtn" class="price-ico-bj2" style="margin-right: 10px;"></span>
						</c:if>
						</a>
						<span>
							<input id="priceLowest" name="priceLowest" type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" value="<c:if test='${!empty productQueryBean.priceLowest }'>${gd:formatNumber2(productQueryBean.priceLowest)}</c:if>" class="mark-pri" placeholder="最低价">&nbsp-&nbsp
							<input id="priceHighest" name="priceHighest" type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" value="<c:if test='${!empty productQueryBean.priceHighest }'>${gd:formatNumber2(productQueryBean.priceHighest)}</c:if>" class="mark-pri" placeholder="最高价">
							<a id="mark-seachLocation" href="${CONTEXT }${gd:formatMarket(marketId) }/market/0-1.html">
								<input id="mark-seach" type="button" class="mark-seach" value="筛选">
							</a>
						</span>
						<a id="bigButton2" href="${CONTEXT }${gd:formatMarket(marketId) }/market/0-1.html"><span class="mark-big-ico02a" style="margin-right: 0px;"></span></a>
						<span class="mark-big-ico01a"></span>
						<span class="fr">共有 <span class="span-yellow2" style="margin-right: 0;">${totalCount} </span> 条批发信息</span>
					</p>
					<input type="hidden" id="pageNow" name="pageNow" value="${pageNow}">
					<input type="hidden" name="cateId" id="cateId" value="${productQueryBean.cateId}"/>
					<input type="hidden" name="marketId" id="marketId" value="${productQueryBean.marketId}"/>
					<input type="hidden" id="priceOrder" name="priceOrder" value="${priceOrder}"/>
					<input type="hidden" name="keyWord2" id="keyword2" value="${keyWord}">
					<!-- 一级分类 -->
					<input type="hidden" name="cateId1" id="cateId1" value="${cateId1}">
					<!-- 二级分类 -->
					<input type="hidden" name="cateId2" id="cateId2" value="${cateId2}">
					<!-- 三级分类 -->
					<input type="hidden" name="cateId3" id="cateId3" value="${cateId3}">
					<!-- 不确定分类 -->
<%-- 						<input type="hidden" name="cateId" id="cateId" value="${cateId}"> --%>
					<!-- 用以标识是在列表页面进行搜索，而不是在页面头部进行搜索 -->
					<input type="hidden" name="searchType" value="1">
<%-- 						<input type="hidden" id="isBig" class="isBig" name="isBig" value="${isBig }"> --%>
				</form>
					<div class="product">
						<!-- 产品列表 -->
						<c:forEach items="${productSolrDTOList}" var="productSolrDTO" varStatus="i">
							<div class="mark-list clearfix">
								<div class="list1 fl">
									<a href="${CONTEXT}market/${productSolrDTO.id}.html" target="_blank" title="${productSolrDTO.name }">
									<img width="150px" height="150px" src="${imgHostUrl}${gd:showImgBySize(productSolrDTO.urlOrg,150)}" alt="${productSolrDTO.name }" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';">
									</a>
								</div>
								<div class="list2 fl">
									<div class="det-top-right">
										<h3 class="detail-pro-tit mark-linh"><a href="${CONTEXT}market/${productSolrDTO.id}.html" target="_blank" title="${productSolrDTO.name }">${productSolrDTO.name }</a></h3>
										<ul class="detail-pro-how list2-det clearfix">
											<!-- 单价 -->
											<c:if test="${productSolrDTO.priceType eq '0' }">
											 	<li class="mark-li">
											   	<span class="detail-price">≥1</span>
										   		<span class="detail-col">
										   		${gd:formatNumber(productSolrDTO.price) }
										   		</span>
										   		元/${gd:showValueByCode('ProductUnit',productSolrDTO.unit) } 
										   		</li> 
											</c:if>
											<!-- 多价 -->
											<c:forEach var="buyCountStart" items="${productSolrDTO.buyCountStart}" varStatus="loop">  
										   		<li class="mark-li">
										   		<c:if test="${empty productSolrDTO.buyCountEnd[loop.index] || empty productSolrDTO.buyCountEnd[loop.index] }">
											   		<span class="detail-price">≥${gd:formatNumber2(buyCountStart) }</span>
										   		</c:if>
										   		<c:if test="${!empty productSolrDTO.buyCountEnd[loop.index] }">
											   		<span class="detail-price">${gd:formatNumber2(buyCountStart)}-${gd:formatNumber2(productSolrDTO.buyCountEnd[loop.index] )}</span>
										   		</c:if>
										   		<span class="detail-col">
										   		${gd:formatNumber(productSolrDTO.rangePrice[loop.index]) }
										   		</span>
										   		元/${gd:showValueByCode('ProductUnit',productSolrDTO.unit) } 
										   		</li> 
											</c:forEach>  
											<li class="mark-li-time">发布时间：<fmt:formatDate value="${productSolrDTO.createTime}" pattern="yyyy年MM月dd日 "/></li>
										</ul>
									</div>
								</div>
								<div class="list3 fr">
									<p class="list3-tit" title="${productSolrDTO.marketName }">${productSolrDTO.marketName }</p>
									<h3 class="list3-company"><a href="${CONTEXT}${gd:formatMarket(marketId) }/business/shop/${productSolrDTO.businessId }.html" title="${productSolrDTO.shopsName }">${productSolrDTO.shopsName}</a>
										<span class="shop-realname"></span>
									<span class="shop-pi"></span></h3>
								</div>
							</div>
						</c:forEach>
						<div id="mar-com-fenye" class="fenye" style="display: none;"></div>
						<div id="searchPage" class="fenyetest" style="text-align: center;margin-bottom: 20px;">
							<%@ include file="../../pub/pagination/searchPage.jsp" %>
						</div>
					</div>
				</div>
			</div>
	    </div>
	    </c:if>
	    <c:if test="${empty productSolrDTOList}">
	    <div class="fl">
    		<div class="con-right line-bottom mag-top">
			<div class="right-m">
				<p class="right-m-t"><a href="${CONTEXT }${gd:formatMarket(marketId) }/market.html"><span class="mark-ico01" style="margin-right: 5px;"></span>所有产品</a></p>
			</div>
			</div>
			<form id="form-seach" action="${CONTEXT }${gd:formatMarket(marketId) }/market.html" method="post">
	    	<div class="con-right line-top" style="min-height:367px;">
				<p class="right-m-t pro-new">
					<span style="margin-right: 10px;">
						<select name="businessModel" id="businessModel" class="mark-sty">
							<option value="">经营模式</option>
							<option value="0" <c:if test="${productQueryBean.businessModel eq '0'}">selected="selected"</c:if>>个人经营</option>
							<option value="1" <c:if test="${productQueryBean.businessModel eq '1'}">selected="selected"</c:if>>企业经营</option>
						</select>
						<select name="rangeDays" id="rangeDays" class="mark-sty mark-sty-time">
							<option value="">所有发布时间</option>
							<option value="1" <c:if test="${productQueryBean.rangeDays eq '1'}">selected="selected"</c:if>>1天内的信息</option>
							<option value="3" <c:if test="${productQueryBean.rangeDays eq '3'}">selected="selected"</c:if>>3天内的信息</option>
							<option value="7" <c:if test="${productQueryBean.rangeDays eq '7'}">selected="selected"</c:if>>7天内的信息</option>
							<option value="30" <c:if test="${productQueryBean.rangeDays eq '30'}">selected="selected"</c:if>>30天内的信息</option>
						</select>
					</span>
					<span class="pro-seach-tit">价格：</span>
					<a id="priceOrderLocation" href="${CONTEXT }${gd:formatMarket(marketId) }/market/0-1.html?priceOrder=asc">
						<c:if test="${empty priceOrder }">
							<span id="priceOrderBtn" class="price-ico-bj3" style="margin-right: 10px;"></span>
						</c:if>
						<c:if test="${ priceOrder eq 'desc' }">
							<span id="priceOrderBtn" class="price-ico-bj1" style="margin-right: 10px;"></span>
						</c:if>
						<c:if test="${ priceOrder eq 'asc'}">
							<span id="priceOrderBtn" class="price-ico-bj2" style="margin-right: 10px;"></span>
						</c:if>
					</a>
					<span>
						<input id="priceLowest" name="priceLowest" type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" value="<c:if test='${!empty productQueryBean.priceLowest }'>${gd:formatNumber2(productQueryBean.priceLowest)}</c:if>" class="mark-pri" placeholder="最低价">&nbsp-&nbsp
						<input id="priceHighest" name="priceHighest" type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" value="<c:if test='${!empty productQueryBean.priceHighest }'>${gd:formatNumber2(productQueryBean.priceHighest)}</c:if>" class="mark-pri" placeholder="最高价">
						<a id="mark-seachLocation" href="${CONTEXT }${gd:formatMarket(marketId) }/market/0-1.html">
						<input id="mark-seach" type="button" class="mark-seach" value="筛选">
						</a>
					</span>
					<span class="mark-big-ico02a" style="margin-right: 0px;"></span>
					<a id="bigButton1" href="${CONTEXT }${gd:formatMarket(marketId) }/market/0-1.html"><span class="mark-big-ico01a"></span></a>
					<span class="fr">共有 <span class="span-yellow2" style="margin-right: 0;">${totalCount} </span> 条批发信息</span>
				</p>
				<div class="content mgtop clearfix"> 
						<!-- 无搜索结果页面开始 -->
						<div class="con-right products-con logi-main" style="width: 100%;margin-left: 0px;border: none;">
							<div style="width: 100%;text-align: center;margin: 0 auto;">
							<p class="logi-tips"><span class="nosearch-ico"></span>抱歉，没有找到符合<span class="logi-tips-span">${cateName}${keyWord }</span>的查询结果。</p>
								<div style="width: 100%;text-align: left;padding-left: 28%;margin-bottom: 80px;margin-top: -30px;">
		<!-- 						<p>建议您： </p> -->
		<!-- 						<p>缩短或修改您的搜索词，重新搜索。</p> -->
								</div>
							</div>
						</div>
				    <!-- 无搜索结果页面结束 -->
					<div class="cl"></div>
					<div id="mar-com-fenye" class="fenye" style="display: none;"></div>
				</div>
			</div>
				<input type="hidden" id="pageNow" name="pageNow" value="${pageNow}">
				<input type="hidden" name="cateId" id="cateId" value="${productQueryBean.cateId}"/>
				<input type="hidden" name="marketId" id="marketId" value="${productQueryBean.marketId}"/>
				<input type="hidden" id="priceOrder" name="priceOrder" value="${priceOrder}"/>
				<input type="hidden" name="keyWord2" id="keyword2" value="${keyWord}">
				<!-- 一级分类 -->
				<input type="hidden" name="cateId1" id="cateId1" value="${cateId1}">
				<!-- 二级分类 -->
				<input type="hidden" name="cateId2" id="cateId2" value="${cateId2}">
				<!-- 三级分类 -->
				<input type="hidden" name="cateId3" id="cateId3" value="${cateId3}">
				<!-- 不确定分类 -->
<%-- 					<input type="hidden" name="cateId" id="cateId" value="${cateId}"> --%>
				<!-- 用以标识是在列表页面进行搜索，而不是在页面头部进行搜索 -->
				<input type="hidden" name="searchType" value="1">
<%-- 					<input type="hidden" id="isBig" class="isBig" name="isBig" value="${isBig }"> --%>
			</form>
			</div>
	    </c:if>
		<div class="cl"></div>
	</div>
</div>
<!--底部 star-->
<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<!--底部 end-->

<!-- fixed tool -->
<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>

<div class="col-result dn">
<h3 class="col-result-t"><span class="result-ico"></span>收藏成功！</h3>
<p>收藏人气：<span>22</span><a href="#" class="f-c-00f ml30">查看我的收藏夹>></a></p>
</div>
<script src="v1.0/js/jquery-1.8.3.min.js"></script>
<script src="v1.0/js/gdui.js"></script>
<script src="v1.0/js/common.js"></script>
<!-- 	<script src="v1.0/js/home.js"></script> -->
	<script>
	$(".mark-list").last().addClass("mark-bor"); 
	var regObj = {};
	(function(){
        var searchStr = window.location.search.substr(1),
            arr = searchStr.split("&");
        for(var i = 0 ;i<arr.length;i++){
            regObj[arr[i].split("=")[0]] = arr[i].split("=")[1]
        }
	})();
	function GetQueryString(name){
	    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	    var r = window.location.search.substr(1).match(reg);
	    if(r!=null)return  unescape(r[2]); return null;
	};
	
	function formatUrl(url){
		url=url.replace("&&","&").replace("&=","").replace("?=","?").replace("?&","?").replace("&.0","");
		url = url.indexOf("?")==url.length-1?url.replace("?",""):url;
		url = url.indexOf("&")==url.length-1?url.substr(0,url.length-1):url;
		return url;
	}
	
	
	var priceOrderLocation=location.href;
	var markSeachLocation=location.href;
	var listTypeLocation=location.href;
	var cateId;
	if($("#cateId").val()){
    	cateId=$("#cateId").val();
    }else if($("#cateId3").val()){
    	cateId=$("#cateId3").val();
	}else if($("#cateId2").val()){
		cateId=$("#cateId2").val();
	}else if($("#cateId1").val()){
		cateId=$("#cateId1").val();
	}else{
		cateId=0;
	}

	$(function(){
		/*****价格区间、经营模式、发布时间改变 随之改变筛选URL****/
		$("#priceLowest,#priceHighest,#rangeDays,#businessModel").change(function(){
			var url = $("#mark-seachLocation").attr('href');
			var id=$(this).attr("id");
			var value=/^\d$/.test($(this).val())?parseFloat($(this).val()):$(this).val();
			
			
			if(value==''){
				url=url.replace(id+"="+GetQueryString(id),"");
			}
			if(GetQueryString(id)){
				url=url.replace(id+"="+GetQueryString(id),id+"="+value);
	 		}else{
	 			if(url.indexOf(id)>0){
	 				url=url.replace(id+"=","");
	 			}else{
		 			if(url.indexOf('?')>0){
		 				url=url+'&'+id+"="+value;
		 			}else{
		 				url=url+'?'+id+"="+value;
		 			}
	 			}
	 		}
			$("#mark-seachLocation").attr('href',formatUrl(url));
		});
		
		$(".mark-sidebar-tit").click(function(){
			var _self = $(this),
				$next = _self.next();
			if($next.is(":hidden")){
				_self.addClass("mark-list-bg");
				_self.find("span").removeClass("mark-sidebar-ico").addClass("mark-sidebar-ico-down");
				$next.slideDown();
				
			}else{
				_self.removeClass("mark-list-bg");
				_self.find("span").removeClass("mark-sidebar-ico-down").addClass("mark-sidebar-ico");
				$next.slideUp();
			}
		});
	});
	
	
	
	function changeMarket(marketId){
		$("#cateId,#cateId1,#cateId2,#cateId3").val("");
		$.cookie('marketId',marketId, {expires:7,path:'/'});
		if("1" == marketId){
			window.location.href = CONTEXT + "baishazhou/market.html";
		}else if("2" == marketId){
			window.location.href = CONTEXT + "yulin/market.html";
		}else{
			window.location.href = CONTEXT + "baishazhou/market.html";
		}
	}
	
	var pageTotal=${pageTotal};
	var pageNow=${pageNow};
	/* $(".js-showLoginDialog").click(function(){
		if(checkIsLogin() ){
			$(".js-showLoginDialog").click(function(){
				$(this).siblings(".tel-none").show();
				$(this).hide();
			})
		}else{
			popupLoginDlg()
		}
	});
	$(".js-collect").click(function(){
		if(checkIsLogin() ){
			
		}else{
			layer.open({
		        type: 1,
		        title: false, 
		        skin: 'layui-layer-rim',
		        content: $(".col-result"), //捕获的元素
		        area:["472px","166px"],
		        cancel: function(index){
		            layer.close(index);
		        }
		    });
		}
	}) */

	function setValues(str2){
		if($("#priceOrder").val()){
        	str2 = addParameter(str2,'priceOrder',$("#priceOrder").val());
        }
        if($("#keyWord2").val()){
        	str2 = addParameter(str2,'keyWord',$("#keyWord2").val());
        }else if($("#headSearch").val()){
        	str2 = addParameter(str2,'keyWord',$("#headSearch").val());
        }
        if($("#businessModel").val()){
        	str2 = addParameter(str2,'businessModel',$("#businessModel").val());
        }
        if($("#rangeDays").val()){
        	str2 = addParameter(str2,'rangeDays',$("#rangeDays").val());
        }
        if($("#priceLowest").val()){
        	str2 = addParameter(str2,'priceLowest',$("#priceLowest").val());
        }
        if($("#priceHighest").val()){
        	str2 = addParameter(str2,'priceHighest',$("#priceHighest").val());
        }
        return str2;
	}
	
	gduiPage({
	    cont: 'mar-com-fenye',
	    pages: pageTotal, //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
	    curr:pageNow,
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	            location.href = e.staticPath;
	        }
	    },
	    staticPage:true,
	    staticPath:(function(){
	        var str = window.location.href;
	        var str2;
	        var arr = [];
	        var lastPath='';
	        var firstPath = '';
	        var firtindex = 0;
	        var str3  = str.substr(str.lastIndexOf('/'),str.lastIndexOf('.'));
	        if(str3.lastIndexOf('-')>0){            
	            console.log("str3="+str3);
	            firtindex = str3.lastIndexOf('-') + str.lastIndexOf('/');
	        }else{
	            firtindex = str.lastIndexOf('.');
	        }
	        firstPath = str.substr(0,firtindex);
	        if(!/\/\d-/.test(firstPath)){
	        	if(/\/\d/.test(firstPath)){
	        		firstPath = str.substr(0,firstPath.lastIndexOf('/'));
	        	}
	        }
	        lastPath =  str.substr(str.lastIndexOf(".html"),str.length);
	        str2 = "${CONTEXT }${gd:formatMarket(marketId) }/market/"+cateId+"-"+$("#pageNow").val()+ lastPath;
	        console.log(str2);
	        str2=setValues(str2);
			currentLocation=str2;
	        return str2
	    })() 
	});
	/****初始化排序按钮URL****/
	if(currentLocation.indexOf('priceOrder')>0){
		if(currentLocation.indexOf('priceOrder=desc')>0){
			priceOrderLocation=currentLocation.replace("priceOrder=desc","priceOrder=asc");
		}else{
			priceOrderLocation=currentLocation.replace("priceOrder=asc","priceOrder=desc");
		}
	}else{
		if(currentLocation.indexOf('?')>0){
			priceOrderLocation=currentLocation+"&priceOrder=asc";
		}else{
		priceOrderLocation=currentLocation+"?priceOrder=asc";
		}
	}
	$("#priceOrderLocation").attr('href',priceOrderLocation);
	
	var firstPath=currentLocation.substr(0,currentLocation.lastIndexOf('/'));
	var lastPath =  currentLocation.substr(currentLocation.lastIndexOf(".html"),currentLocation.length);
	/***初始化筛选按钮URL***/
	$("#mark-seachLocation").attr('href',firstPath +"/"+cateId+"-1"+ lastPath);
// 	/****初始化列表模式URL****/
	
	currentLocation=firstPath +"/"+cateId+"-"+$("#pageNow").val()+ lastPath;
	var currentBigStyleLocation;//大图模式URL
	var currentListStyleLocation;//列表模式URL
	if(currentLocation.indexOf('isBig')>0){
		currentBigStyleLocation=currentLocation.replace("isBig="+GetQueryString("isBig"),"");
	}
	$("#bigButton2").attr("href",formatUrl(currentBigStyleLocation));
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>