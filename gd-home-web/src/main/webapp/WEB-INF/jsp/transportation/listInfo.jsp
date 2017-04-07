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
<meta name="keywords" content="农产品物流" />
<meta name="description" content="物流服务货主找车频道为货主提供农产品物流需求信息，用户可根据需要找到货物零担运输、货物整车运输、冷链运输、整车零担货运等方式，谷登农批网货主找车服务是农产品交易顺利完成的重要保障。" />
<meta name="renderer" content="webkit">
<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12" />
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/source-info.css">
</head>

<body>

	<div class="source-info">
		<div class="cource-info-i">
			<div class="cource-info-box">
				<span class="cource-info-l"><i class="cou-info-i"><img src="v1.0/images/direction/img-3.png"/></i>货源信息</span>
				<span class="cource-info-r">使用农速通，找车找货更轻松！</span>
			</div>
			<div class="cource-tab-100" border="0" cellspacing="0">
					<ul class="tab-tr-10">
						<li class="i-scroll-wth">起运地</li><li>  →</li><li class="i-scroll-wth">目的地</li>
						<li>货物名称</li>
						<li>重量</li>
						<li>体积</li>
						<li>所需车型</li>
						<li>所需车长</li>
						<li>会员类型</li>
						<li>发运方式</li>
						<li>意向价格</li>
						<li>发布时间</li>
						<li>联系人</li>
						<li>联系方式</li>
					</ul>
			</div>
			<div class="box-scroll-ul">
				<div class="i-scroll-tr">
					
					<c:forEach items="${goodsList}" var="obj">
					<ul>
						<li class="i-scroll-wth">${obj.startPlace}</li><li><img src="v1.0/images/direction/direction.png"/></li><li class="i-scroll-wth">${obj.endPlace}&nbsp;&nbsp;</li>
						<li>${obj.goodsName}</li>
					
						<li>
							<c:choose>
									<c:when test="${obj.totalWeight >0}"> ${obj.totalWeight}吨</c:when>
									<c:otherwise>
					                  --
					                </c:otherwise>
							</c:choose>
						</li>
						
						<li>
							<c:choose>
									<c:when test="${obj.totalSize >0}"> ${obj.totalSize}m³</c:when>
									<c:otherwise>
					                  --
					                </c:otherwise>
							</c:choose>
						</li>
						<li>${obj.carTypeString}</li>
						
						<li>
							<c:choose>
									<c:when test="${obj.carLength >0}"> ${obj.carLength}米</c:when>
									<c:otherwise>
					                  --
					                </c:otherwise>
							</c:choose>
						</li>
						<li>${obj.isCertify}</li>
						<li>${obj.sendGoodsTypeString}</li>
						<li>
							<c:choose>
									<c:when test="${obj.price >0}"> ${obj.priceUnitTypeString}</c:when>
									<c:otherwise>
					                   面议
					                </c:otherwise>
								</c:choose>
						</li>
						<li>
							<c:choose>
									<c:when test="${obj.createTimeString ==null || obj.createTimeString ==''}"> -- </c:when>
									<c:otherwise>
					                    ${obj.createTimeString}
					                </c:otherwise>
							</c:choose>
						</li>
						<li>
						 <c:choose>
									<c:when test="${obj.nickName ==null || obj.nickName ==''}"> -- </c:when>
									<c:otherwise>
					                  	${obj.nickName}
					                </c:otherwise>
						 </c:choose>
						</li>
						<li>${obj.userMobile}</li>
					</ul>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="cource-info-i">
			<div class="cource-info-box">
				<span class="cource-info-l"><i class="cou-info-b"><img src="v1.0/images/direction/img-4.png"/></i>车源信息</span>
				<span class="cource-info-r">客服热线: 4007-600-800</span>
			</div>
			<div class="cource-tab-100" border="0" cellspacing="0">
					<ul class="tab-tr-10 i-scroll-tp">
						<li class="i-scroll-wth">起运地</li><li>  →</li><li class="i-scroll-wth">目的地</li>
						<li>当前位置</li>
						<li>车辆类型</li>
						<li>载重</li>
						<li>车长</li>
						<li>车牌</li>
						<li>会员类型</li>
						<li>发车时间</li>
						<li>发运方式</li>
						<li>意向价格</li>
						<li>发布时间</li>
						<li>联系人</li>
						<li>联系方式</li>
					</ul>
			</div>
			<div class="box-scroll-ul">
				<div class="i-scroll-tr i-scroll-l">
					<c:forEach items="${carLineList}" var="carLine">
					<ul>
						<li class="i-scroll-wth">${carLine.startPlace}</li><li><img src="v1.0/images/direction/direction.png"/></li><li class="i-scroll-wth">${carLine.endPlace}</li>
						<li>${carLine.mCity}</li>
						<li>${carLine.carType}</li>
						<li>${carLine.maxLoad}吨</li>
						<li>${carLine.carLength}米</li>
						<li>${carLine.carNumber}</li>
						<li>${carLine.isCertify}</li>
						<li>
						<c:choose>
							    <c:when test="${carLine.sendDateString !=null &&  carLine.sendDateString !=''}"> 
								  ${carLine.sendDateString}
								   </c:when>
								 <c:otherwise>面议</c:otherwise>
				    	</c:choose>
						</li>
						<li>${carLine.sendGoodsTypeString}</li>
						<li>
						<c:choose>
								   <c:when test="${carLine.price >0}"> ${carLine.unitTypeString}</c:when>
										<c:otherwise>面议</c:otherwise>
									</c:choose>
						</li>
						<li>
					    <c:choose>
									<c:when test="${carLine.createTimeString ==null || carLine.createTimeString ==''}"> -- </c:when>
									<c:otherwise>
					                  	${carLine.createTimeString}
					                </c:otherwise>
						 </c:choose>
						</li>
						<li>
						 <c:choose>
									<c:when test="${carLine.nickName ==null || carLine.nickName ==''}"> -- </c:when>
									<c:otherwise>
					                  	${carLine.nickName}
					                </c:otherwise>
						 </c:choose>
						</li>
						<li>${carLine.phone}</li>
					</ul>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>


	<script src="${CONTEXT}v1.0/js/jquery-1.8.3.min.js"></script>
	<script>
		function autoScroll(obj,scrollHeight){
			var scrollFlag = true,cleatimer;
			clearTimeout(cleatimer);
		    
		    checkAutoScroll(); 
		    $(obj).hover(function(){
		    	scrollFlag = false;
		    	clearInterval(cleatimer);
		    },function(){
		    	scrollFlag = true;
		    	checkAutoScroll()
		    });
			function autoPlay(){
				$(obj).find(".i-scroll-tr:first").stop().animate({
		            marginTop:"-"+scrollHeight+"px"
			    },500,function(){
			            $(this).css({marginTop:"0px"}).find("ul:first").appendTo(this);
			    });
			}
			function checkAutoScroll(){
				clearInterval(cleatimer);
				if(scrollFlag){
					cleatimer = setInterval(autoPlay, 2000);						
				}	
			}
		};

		$(function(){

			var $liitem = $(".i-scroll-tr").find("ul"),
				$linum = $liitem.length,
				$linheight = $liitem.height(),
				scrollWrap = $(".box-scroll-ul").height(); 
			if($linum*$linheight>scrollWrap){
				autoScroll($(".box-scroll-ul"),"28");
			}

		});

	</script>
</html>
