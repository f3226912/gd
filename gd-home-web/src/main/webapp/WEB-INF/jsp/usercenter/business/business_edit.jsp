<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>
<%@ include file="../../pub/head.inc" %>
<html>
 <head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="../v1.0/css/global.css"/>	
	<link rel="stylesheet" href="../v1.0/css/member.css"/>	
</head>

<body>
   <jsp:include page="../../usercenter/userCenter_head.jsp" flush="true"/> 
   <jsp:include page="../../usercenter/userCenter_left.jsp" flush="true"/> 
 <form action="business/save" name="addForm" id="addForm">
 
 <input type="hidden" id="businessId" name="businessId"    value="${dto.businessId}"/>
   

   
<!--产品发布产品发布产品发布产品发布产品发-->
		<div class="mid-right bg-white">
			<h1 class="mid-right-store">商铺基本信息</h1>
			<div class="edit-cont-box-dis edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>
				<strong style="font-size:14px;">商铺名称：</strong></span>
				<div class="right-contact">
				
					<input class="right-contact-us" placeholder="商铺名称"  type="text" maxlength="10" name="shopsName"  value="${dto.shopsName}" >
				</div>
			</div>

			<!--经营模式-->
			<div class="edit-cont-box-dis edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>经营模式：</span>
				<div class="con-pro-cit-area">
					 
						<select name="businessModel"  id="businessModel" class="i-app-far-ope-mode" >
 							<option value="">请选择经营模式</option>
							<option value="0" <c:if test="${dto.businessModel==0 }">selected</c:if>   >个人经营</option>
							<option value="1" <c:if test="${dto.businessModel==1 }">selected</c:if> >企业经营</option>
						</select>	
				</div>
			</div>
			
			
			<!--经营类型-->
			<div class="edit-cont-box-dis edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>经销类型：</span>
				<div class="con-pro-cit-area">
					<c:if test="${ empty dto.businessId }">
					<select class="i-app-far-ope-mode tradeType" name="level" id="level">
<!-- 						<option value="0">选择您的类型</option>
 -->						
 						<c:choose>
	 						<c:when test="${level==4}">
									<option value="4">产地供应商</option>
	 						</c:when>
	 						<c:otherwise>
			 						<option value="1">批发商</option>
	 						</c:otherwise>
 						</c:choose>
					</select>
					 <i class="con-help-i"></i>
					</c:if>
					<c:if test="${ !empty dto.businessId }">
							 <input type="hidden" value="${level}" name="level" />
							 <span class="right-contact-us" style="border:none">
							 <c:choose>
		 						<c:when test="${level==4}">
										产地供应商
		 						</c:when>
		 						<c:otherwise>
				 						批发商
		 						</c:otherwise>
	 						 </c:choose>
			 	<%-- 
				 <c:if test="${level==1 }">谷登农批</c:if> 
				 <c:if test="${level==2 }">农速通</c:if> 
				 <c:if test="${level==3 }">农批宝</c:if> 
				 <c:if test="${level==4 }">产地供应商</c:if>  --%>
							 </span>
					</c:if>
				</div>
			</div>
			
			
			<!--您所在的农批市场-->
 
			<div class="cont-box-noe"   >
				<div class="edit-cont-box-dis edit-cont-box-location">
					<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>农批市场所在：</span>
					<div class="con-pro-cit-area">
						<c:if test="${ empty dto.businessId }">
						 <select class="i-app-far-ope-mode" name="marketId" id="marketId" onchange="changerV(this.value)" >
			    			
			    	<%-- 		<c:forEach  items="${markets}" var="market" >
							 <option value="${market.id}" 
							 <c:if test="${dto.marketId == market.id}">selected</c:if>
							 >${market.marketName}</option>
							</c:forEach>	 --%>
							
							 <option value="1" >武汉白沙洲批发市场</option>			 	
							 <option value="2" >广西玉林批发市场</option>			 	
 						</select>
						</c:if>
						<c:if test="${ !empty dto.businessId }">
							<input type="hidden" value="${dto.marketId}" name="marketId" />
			    			<c:forEach  items="${markets}" var="market" >
							 <c:if test="${dto.marketId == market.id}">
							 	<span class="right-contact-us" style="border:none">	 ${market.marketName} </span>
							 </c:if>
							</c:forEach>				 	
						</c:if>
						
					</div>
				</div>
			
			
			
				<!--主营分类-->
	 
				<div class="edit-cont-box-dis edit-cont-box-act">
						<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>主营分类：</span>
					<c:forEach var="map" items="${mapM}">
					<c:choose>
						<c:when test="${ empty dto.marketId && !empty market && market.id ==map.key }">
							<div class="con-pro-cit-area-bx"	 id="market${map.key}"  >
					            <c:forEach  items="${map.value}" var="productCategoryDTO" >
									<span class="con-pro-cit-area-sp">
										<input  class="pro-cit-area-lab" type="checkbox"
										 name="categoryId"
										 id="categoryId" 
										value="${productCategoryDTO.categoryId}"
										<c:forEach  items="${listRBC}" var="reBusinessCategory" >
									 		<c:if test="${reBusinessCategory.categoryId == productCategoryDTO.categoryId}">checked</c:if>
										</c:forEach>>
										<label>${productCategoryDTO.cateName}</label>
									</span>
								</c:forEach>	
							</div>
						</c:when>
						<c:otherwise>
						<div class="con-pro-cit-area-bx" id="market${map.key}" style="display:none;"  >
					            <c:forEach  items="${map.value}" var="productCategoryDTO"  >
									<span class="con-pro-cit-area-sp">
										<input  class="pro-cit-area-lab" type="checkbox"
										 name="categoryId"
										 id="categoryId" 
										value="${productCategoryDTO.categoryId}"
										<c:forEach  items="${listRBC}" var="reBusinessCategory" >
									 		<c:if test="${reBusinessCategory.categoryId == productCategoryDTO.categoryId}">checked</c:if>
										</c:forEach>>
										<label>${productCategoryDTO.cateName}</label>
									</span>
								</c:forEach>	
							</div>
						</c:otherwise>
					</c:choose>
					</c:forEach>
					<div class="con-pro-cit-area-bx" id="market${dto.marketId}" >
						<c:forEach  items="${lsitAll}" var="productCategoryDTO" >
							<span class="con-pro-cit-area-sp">
								<input  class="pro-cit-area-lab" type="checkbox"
								 name="categoryId"
								 id="categoryId" 
								value="${productCategoryDTO.categoryId}"
								<c:forEach  items="${listRBC}" var="reBusinessCategory" >
							 		<c:if test="${reBusinessCategory.categoryId == productCategoryDTO.categoryId}">checked</c:if>
								</c:forEach>>
								<label>${productCategoryDTO.cateName}</label>
							</span>
						</c:forEach>	
					</div>
				</div>
			
			</div>
			
			
			<!--主营商品-->
			<div class="edit-cont-box-dis edit-cont-box-act">
				<span class="rse-pro-tit">主营商品：</span>
				<div class="right-contact">
					<input class="right-contact-us" placeholder="产品"  type="text" maxlength="199" value="${dto.mainProduct}"  name="mainProduct">
					多个产品用 “空格”或“,” 隔开，可以帮助您提升搜索精准度
				</div>
			</div>
			
			<!--商铺简介-->
			<div class="edit-cont-box-dis edit-cont-box-act">
				<span class="rse-pro-tit">商铺简介：</span>
				<div class="right-contact-fol">
					<textarea class="right-contact-bri"  maxlength="499" name="shopsDesc">${dto.shopsDesc} </textarea>
					<p class="right-contact-bri-p">请用中文详细说明贵司的成立历史、主营商品、品牌、服务等优势；
						如果内容过于简单或仅填写单纯的产品介绍(50- 500个字)。</p>
					<button class="right-contact-bch" type="button">保存</button>
				</div>

			</div>
			

			
		</div>
	<div class="clear"></div>

	</div>
</form>

	<!--简版底部 star-->
	
	<!--简版底部 end-->
	<script type="text/javascript">
	$('.right-contact-bch').click(function(){
		var url=CONTEXT+"userCenter/business/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				layer.msg('保存成功！'); 
			}else if(data == "success1"){
				success1();
			}else if(data == "shopsName"){
				layer.msg('店铺名称不能为空！'); 
				return;
			} else if(data == "categoryId"){
				layer.msg('主营分类不能为空！'); 
				return;
			} else if(data == "model"){
				layer.msg('经营模式不能为空！'); 
				return;
			}else if(data == "marketId"){
				layer.msg('所属市场不能为空！'); 
				return;
			}else if(data == "level"){
				layer.msg('经销类型不能为空！'); 
				return;
			} else if(data == "existBusiness"){
				layer.msg('店铺已经开通,请不要使用浏览器回退按钮，避免重复提交店铺信息！'); 
				return;
			} else {
				layer.msg('保存失败！'); 
				return;
			}
		});
		
	});
	
	
	

	$(".con-help-i").gduimsgTips({
		direction:1,//tips的方向
		content:"<strong>选择批发商：</strong>您发布的批发商品将在谷登农批网农批市场中看到。<br/><strong>选择产地供应商：</strong>为批发商提供货源，您发布的产品在批发商的会员中心我要进货中看到。",//tips内容
		bgColor:"#069139",
		time: 0,//自动关闭时间，0表示不自动关闭
		area:["330px",""],
		cusClass:""//自定义类名
	});

	$('.tradeType').change(function(){
			isMarketChange($(this))
		});
		var isMarketChange = function(ele){
			var val = $(ele).val();
			//console.log(val)
			if(val==4){
				$(".cont-box-noe").hide();
			}else if(val==1){
				$(".cont-box-noe").show();
			}
			/*
			else if($('#level').eq(0).value ==4){
				$(".cont-box-noe").hide();
			}else if($('#level').eq(0).value ==1){
				$(".cont-box-noe").show();
			}*/
		}
	$(function(){
			isMarketChange($('.tradeType'))
		})
	$('.change-item').change(function(){
		var val = $(this).val();
		if(val!=0){
			changeItem(val);
		}
	});
	var changeItem = function(type){
		$.ajax({
		    type: "get",
		    url: "item.json",
		    data: {type:type},
		    dataType:"json",
		    success: function(data){
		    	console.log(data.length);
		    	var str = '';
		    	for(var i = 0;i<data.length;i++){
		    		str += '<span class="con-pro-cit-area-sp"><input class="pro-cit-area-lab" type="checkbox"><label>'+data[i].cat+'</label></span>'
		    	}
		    	$(".con-pro-cit-area-bx").html(str);
		    }
		});
	};
		
	
	function changerV(marketId){
		document.getElementById("market"+marketId).style.display="";
		
		for(var i=0;i<document.getElementById("marketId").options.length;i++){
			
			var id=document.getElementById("marketId").options[i].value;
			if(id != marketId){       
				document.getElementById("market"+id).style.display="none";
				}    
			}
		}
			
		
 
		 
		function success1() {
			layer.open({
				type: 1,
			    title: "", //显示标题
				area:["360px","200px"],
				btn:["发布产品","商铺首页"],
				cusClass:'memdia',//类名
				yes:function(){
					window.location.href="${CONTEXT }userCenter/product/chooseCategory";
				},
				cancel:function(){
					window.location.href="${CONTEXT }business/shop";
				},
			    content: '<div class="top10-al">恭喜你，商铺已开通!</div>', //捕获的元素
			    onClose: function(index){
					window.location.href="${CONTEXT }userCenter";
			    }

			});
		}
		//$(".cont-box-noe").hide();//默认不展示 批发市场和经营类目

		</script>
 		<c:if test="${ empty dto.businessId || level ==4}">
	 		<script type="text/javascript">
	 			$(".cont-box-noe").hide();//默认不展示 批发市场和经营类目
			</script>
 		</c:if>
 
</html>

 
 						