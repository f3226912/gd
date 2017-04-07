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
	String queryString1=request.getQueryString();
	if(StringUtils.isNotEmpty(queryString1)){
	    queryString1="?"+queryString1;
	}
	request.setAttribute("queryString1", queryString1);
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
<title>共有${gd:formatMarket2(marketId)}${keyWord}供应商/采购商/批发商信息${totalCount }条_谷登农批网</title>
<meta name="keywords" content="${keyWord},${keyWord}供应商,${keyWord}采购商,${keyWord}批发商"/>
<meta name="description" content="谷登农批网为您找到${totalCount }条${gd:formatMarket2(marketId)}${keyWord}店铺信息，包括${gd:formatMarket2(marketId)}${keyWord}供应商，,${gd:formatMarket2(marketId)}供应商${totalCount }批发价格等；全面了解${gd:formatMarket2(marketId)}${keyWord}批发市场信息就在谷登农批网。"/>
</c:when>
<c:otherwise>
<c:if test="${!empty cateName }">
<title>${gd:formatMarket2(marketId)}最全${cateName }供应商/采购商/批发商信息_谷登农批网</title>
<meta name="keywords" content="${gd:formatMarket2(marketId)},${cateName }供应商,${cateName }采购商,${cateName }批发商,${cateName }批发行,${cateName }"/>
<meta name="description" content="谷登农批网为您找到${gd:formatMarket2(marketId)}${cateName }商家信息${totalCount }条。可查看${gd:formatMarket2(marketId)}${cateName }供应商详情、批发行主营分类、采购商所在地等；谷登农批网-第一O2O农批平台。"/>
</c:if>
<c:if test="${empty cateName }">
<title>海量农产品供应商/采购商/批发商资源_谷登农批网${gd:formatMarket2(marketId)}</title>
<meta name="keywords" content="${gd:formatMarket2(marketId)},农批商家,农产品供应商,农产品采购商,农产品批发商,农产品批发行,农产品商"/>
<meta name="description" content="谷登${gd:formatMarket2(marketId)}聚集海量${gd:formatMarket2(marketId)}农产品批发商家信息，包括${gd:formatMarket2(marketId)}农产品供应商详情、农产品批发行主营分类及农产品采购商聚集地等；谷登农批网-第一O2O农批平台。"/>
</c:if>
</c:otherwise>
</c:choose>
<%@ include file="../../pub/head.inc" %>
<!-- <link rel="stylesheet" href="v1.0/css/global.css"> -->
<link rel="stylesheet" href="v1.0/css/index-shop.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/js/skin/gduiPage.css">
</head>
<body>
<c:set var="pageNavigateModel" value="2" />
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
	<!-- 主体 -->
	<c:if test="${!empty businessSolrDTOList}">
	<div class="main clearfix">
		<div class="content mgtop clearfix">
			<div class="mark-company">
				<div class="con-right line-bottom mag-top" style="width: 1170px; margin: 0;">
					<div class="right-m">
						<p class="right-m-t"><a href="${CONTEXT }${gd:formatMarket(marketId) }/business.html"><span class="mark-company-ico01" style="margin-right: 5px;"></span>所有商铺</a></p>
					</div>
				</div>
				<div class="con-right line-top " style="width: 1170px; margin: 0;">
					<div class="right-m">
						<form id="form-seach" action="${CONTEXT }${gd:formatMarket(marketId) }/business.html" method="get">
						<p class="right-m-t pro-new">
							<span style="margin-right: 10px;">
								<select name="businessModel" id="businessModel" class="mark-sty">
									<option value="">经营模式</option>
									<option value="0" <c:if test="${businessQueryBean.businessModel eq '0'}">selected="selected"</c:if>>个人经营</option>
									<option value="1" <c:if test="${businessQueryBean.businessModel eq '1'}">selected="selected"</c:if>>企业经营</option>
								</select>
								<select name="status" id="status" class="mark-sty mark-sty-time">
									<option value="">所有会员</option>
									<option value="1" <c:if test="${businessQueryBean.status eq '1'}">selected="selected"</c:if>>认证会员</option>
									<option value="0" <c:if test="${businessQueryBean.status eq '0'}">selected="selected"</c:if>>未认证会员</option>
								</select>
							</span>
							<span class="fr">共有 <span class="span-yellow2" style="margin-right: 0;">${totalCount}</span> 条商户信息</span>
						</p>
<%-- 						<input type="hidden" id="pageNow" name="pageNow" value="${pageNow}"> --%>
<%-- 						<input type="hidden" name="marketId" id="marketId" value="${businessQueryBean.marketId}"/> --%>
						<input type="hidden" id="keyWord2" name="keyWord2" value="${keyWord}">
						<input type="hidden" name="keyWord" value="${keyWord}">
						<input type="hidden" id="categoryId" name="categoryId" value="${businessQueryBean.categoryId}">
						<!-- 用以标识是在列表页面进行搜索，而不是在页面头部进行搜索 -->
<!-- 						<input type="hidden" name="searchType" value="1"> -->
						<p id="cateMenu" class="right-m-t" style="height:80px; line-height: 30px; margin-top: 10px;">
							<c:choose>
								<c:when test="${empty categories || empty businessQueryBean.categoryId}">
									<a href="${CONTEXT}${gd:formatMarket(marketId) }/business.html${queryString1}" style="color:#068139;font-weight: 600;"><span class="mark-ico01" style="margin-right: 5px;"></span>所有类目</a><br>
								</c:when>
								<c:otherwise>
									<a href="${CONTEXT}${gd:formatMarket(marketId) }/business.html${queryString1}"><span class="mark-ico01" style="margin-right: 5px;"></span>所有类目</a><br>
								</c:otherwise>
							</c:choose>
							<c:forEach items="${categories}" var="cate" varStatus="i">
								<c:if test="${ cate.categoryId eq businessQueryBean.categoryId}">
								<a href="${CONTEXT}${gd:formatMarket(marketId) }/business/${cate.categoryId}.html${queryString1}" style="color:#068139;" title="${cate.cateName}"><span class="mar-por-sty2" style="font-weight: 600;">${cate.cateName}</span></a>
								</c:if>
								<c:if test="${ cate.categoryId ne businessQueryBean.categoryId}">
								<a href="${CONTEXT}${gd:formatMarket(marketId) }/business/${cate.categoryId}.html${queryString1}" title="${cate.cateName}"><span class="mar-por-sty2">${cate.cateName}</span></a>
								</c:if>
							</c:forEach>
						</p>
						</form>
						<div class="product">
							<!-- 商铺信息 -->
							<c:forEach items="${businessSolrDTOList}" var="businessSolrDTO" varStatus="i">
							<div class="mark-list clearfix">
								<div class="list1 fl mar-com-data">
									<div class="shopname1 mar-com-bor">
											<input type="hidden" name="businessId" value="${businessSolrDTO.businessId }"/>
											<h3 class="mar-com-real"><a href="${CONTEXT}${gd:formatMarket(marketId) }/business/shop/${businessSolrDTO.businessId }.html" title="${businessSolrDTO.shopsName }" target="_blank">${businessSolrDTO.shopsName }</a>
											<span class="shop-realname"></span>
											<span class="shop-pi"></span></h3>
											<p>经营模式：<c:if test="${businessSolrDTO.businessModel eq '0' }">个人经营</c:if><c:if test="${businessSolrDTO.businessModel eq '1' }">企业经营</c:if></p>
											<a onclick ="javascript:layer.msg('${businessSolrDTO.shopDesc }');"><p title="${businessSolrDTO.shopDesc }" class="mark-shop-tit" >商铺介绍：${businessSolrDTO.shopDesc }</p></a>
											<p title="${businessSolrDTO.marketName }">所在市场：${businessSolrDTO.marketName }</p>
											<a onclick ="javascript:layer.msg('${businessSolrDTO.mainProduct }');"><p title="${businessSolrDTO.mainProduct }..." class="mark-shop-tit">主营商品： ${businessSolrDTO.mainProduct }...</p></a>
									</div>
								</div>
								<!-- 商铺产品信息 -->
								<c:forEach items="${businessSolrDTO.productId}" var="pid" varStatus="i" end="3">
									<div class="list1 fl mar-com-img">
										<a href="${CONTEXT}market/${pid}.html" target="_blank">
										<img width="150px" height="150px" src="${imgHostUrl}${gd:showImgBySize(businessSolrDTO.urlOrg[i.index],150)}" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';" title="${businessSolrDTO.productName[i.index]}">
										</a>
										<p class="mark-com-pri"><span class="price">￥${gd:formatNumber(businessSolrDTO.price[i.index])} </span><span class="how">/${gd:showValueByCode('ProductUnit',businessSolrDTO.unit[i.index]) }</span></p>
										<p class="key-tit mark-com-tit"><a href="${CONTEXT}market/${pid}.html" title="${businessSolrDTO.productName[i.index]}" target="_blank">${businessSolrDTO.productName[i.index]}</a></p>
									</div>
								</c:forEach>
								<c:if test="${fn:length(businessSolrDTO.productId) <1}">
								<p class="none-data">该店铺目前没有商品!</p>
								</c:if>
							</div>
							</c:forEach>
							<!-- 商铺信息end -->
						</div>
					</div>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>
	<div id="mar-com-fenye" class="fenye" style="display: none;"></div>
	<div id="searchPage" class="fenyetest" style="text-align: center;margin-bottom: 20px;">
		<%@ include file="../../pub/pagination/searchPage.jsp" %>
	</div>
	</c:if>
	<c:if test="${empty businessSolrDTOList}">
	<div class="main clearfix">
		<div class="content mgtop clearfix">
			<div class="mark-company">
				<div class="con-right line-bottom mag-top" style="width: 1170px; margin: 0;">
					<div class="right-m">
						<p class="right-m-t"><a href="${CONTEXT }${gd:formatMarket(marketId) }/business.html"><span class="mark-company-ico01" style="margin-right: 5px;"></span>所有商铺</a></p>
					</div>
				</div>
				<div class="con-right line-top " style="width: 1170px; margin: 0;">
					<div class="right-m">
						<form id="form-seach" action="${CONTEXT }${gd:formatMarket(marketId) }/business.html" method="get">
						<p class="right-m-t pro-new">
							<span style="margin-right: 10px;">
								<select name="businessModel" id="businessModel" class="mark-sty">
									<option value="">经营模式</option>
									<option value="0" <c:if test="${businessQueryBean.businessModel eq '0'}">selected="selected"</c:if>>个人经营</option>
									<option value="1" <c:if test="${businessQueryBean.businessModel eq '1'}">selected="selected"</c:if>>企业经营</option>
								</select>
								<select name="status" id="status" class="mark-sty mark-sty-time">
									<option value="">所有会员</option>
									<option value="1" <c:if test="${businessQueryBean.status eq '1'}">selected="selected"</c:if>>认证会员</option>
									<option value="0" <c:if test="${businessQueryBean.status eq '0'}">selected="selected"</c:if>>未认证会员</option>
								</select>
							</span>
							<span class="fr">共有 <span class="span-yellow2" style="margin-right: 0;">${totalCount}</span> 条商户信息</span>
						</p>
						<input type="hidden" id="pageNow" name="pageNow" value="${pageNow}">
						<input type="hidden" name="marketId" id="marketId" value="${businessQueryBean.marketId}"/>
						<input type="hidden" id="keyWord2" name="keyWord2" value="${keyWord}">
						<input type="hidden" name="keyWord" value="${keyWord}">
						<input type="hidden" id="categoryId" name="categoryId" value="${businessQueryBean.categoryId}">
						<!-- 用以标识是在列表页面进行搜索，而不是在页面头部进行搜索 -->
						<input type="hidden" name="searchType" value="1">
						<input type="hidden" name="searchModel" value="1">
						<p id="cateMenu" class="right-m-t" style="height:80px; line-height: 30px; margin-top: 10px;">
						<c:choose>
							<c:when test="${empty categories || empty businessQueryBean.categoryId}">
								<a href="${CONTEXT}${gd:formatMarket(marketId) }/business.html" style="color:#068139;font-weight: 600;"><span class="mark-ico01" style="margin-right: 5px;"></span>所有类目</a><br>
							</c:when>
							<c:otherwise>
								<a href="${CONTEXT}${gd:formatMarket(marketId) }/business.html"><span class="mark-ico01" style="margin-right: 5px;"></span>所有类目</a><br>
							</c:otherwise>
						</c:choose>
						<c:forEach items="${categories}" var="cate" varStatus="i">
							<c:if test="${ cate.categoryId eq businessQueryBean.categoryId}">
							<a href="${CONTEXT}${gd:formatMarket(marketId) }/business/${cate.categoryId}.html" style="color:#068139;" title="${cate.cateName}"><span class="mar-por-sty2" style="font-weight: 600;">${cate.cateName}</span></a>
							</c:if>
							<c:if test="${ cate.categoryId ne businessQueryBean.categoryId}">
							<a href="${CONTEXT}${gd:formatMarket(marketId) }/business/${cate.categoryId}.html" title="${cate.cateName}"><span class="mar-por-sty2">${cate.cateName}</span></a>
							</c:if>
						</c:forEach>
						</p>
						</form>
						<div class="main clearfix" style="width: 1170px;">
							<div class="content mgtop">
								<div class="fl" style="width: 100%;height: 400px;">
									<!-- 无搜索结果页面开始 -->
									<div class="con-right products-con logi-main" style="width: 100%;margin-left: 0px;border: none;">
										<div style="width: 100%;text-align: center;margin: 0 auto;">
										<p class="logi-tips"><span class="nosearch-ico"></span>抱歉，没有找到符合<span class="logi-tips-span">
										<c:choose>
											<c:when test="${businessQueryBean.businessModel eq '0'}">
												个人经营、
											</c:when>
											<c:when test="${businessQueryBean.businessModel eq '1'}">
												企业经营、
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${businessQueryBean.status eq '1'}">
												认证会员、
											</c:when>
											<c:when test="${businessQueryBean.status eq '0'}">
												未认证会员、
											</c:when>
										</c:choose>
										${cateName}${keyWord }</span>的查询结果。</p>
											<div style="width: 100%;text-align: left;padding-left: 28%;margin-bottom: 80px;margin-top: -30px;">
					<!-- 						<p>建议您： </p> -->
					<!-- 						<p>缩短或修改您的搜索词，重新搜索。</p> -->
											</div>
										</div>
									</div>
							    </div>
							    <!-- 无搜索结果页面结束 -->
								<div class="cl"></div>
							</div>
						</div>
					</div>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>
</c:if>
<!--底部 star-->
<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<!--底部 end-->

<!-- fixed tool -->
<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>
	
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
	};
	var categoryId;
	if($("#categoryId").val()){
		categoryId=$("#categoryId").val();
	}else{
		categoryId=0;
	}
	
	$("#businessModel,#status").change(function(){
		var keyWord=$("#keyWord2").val();
		var categoryId=$("#categoryId").val();
		var status=$("#status").val();
		var businessModel=$("#businessModel").val();
		var url='${CONTEXT }${gd:formatMarket(marketId) }/business';
		if(categoryId){
			url=url+"/"+categoryId+".html?";
		}else{
			url=url+".html?";
		}
		if(businessModel){
			url=url+"&businessModel="+businessModel;
		}
		if(keyWord){
			url=url+"&keyWord="+keyWord;
		}
		if(status){
			url=url+"&status="+status;
		}
		window.location.href=formatUrl(url);
	});
	
	function changeMarket(marketId){
		$("#cateId,#cateId1,#cateId2,#cateId3,#categoryId").val("");
		$.cookie('marketId',marketId, {expires:7,path:'/'});
		if("1" == marketId){
			window.location.href = CONTEXT + "baishazhou/business.html";
		}else if("2" == marketId){
			window.location.href = CONTEXT + "yulin/business.html";
		}else{
			window.location.href = CONTEXT + "baishazhou/business.html";
		}
	}
	
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
	var pageTotal=${pageTotal};
	var pageNow=${pageNow};
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
	        str2 = firstPath +"/"+categoryId+"-"+$("#pageNow").val()+ lastPath;
	        
	        console.log(str2);
	        str2=setValues(str2);
			currentLocation=str2;
	        return str2
	    })() 
	});
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>