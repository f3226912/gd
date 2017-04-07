<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../pub/constants.inc"%>
<%@ include file="../../pub/tags.inc"%>
<!doctype html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=UTF-8">
<base href="${CONTEXT }">
<title>我要进货${keyWord }</title>
<meta name="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="keywords" content="" />
<%@ include file="../../pub/head.inc"%>
<link rel="stylesheet" href="v1.0/css/index-shop.css"/>
<style type="text/css">
.tab-gridtable td {
/*     background-color: #FFF; */
/*     text-align: center; */
/*     height: 85px; */
    line-height: 20px !important;
/*     border-bottom: 1px solid #CFCFCF; */
}
</style>
</head>

<body>
	<!--头部-->
	<jsp:include page="../userCenter_head.jsp" flush="true" />
	<!--左边菜单栏-->
	<jsp:include page="../userCenter_left.jsp" flush="true" />
	<!--产品发布产品发布产品发布产品发布产品发-->
	<div class="mid-right bg-white">
		<h1 class="mid-right-store">所有货源</h1>
		<div class="tab_box-cet tab-box-cet-lt20">
			<form id="form-search" action="${CONTEXT }userCenter/purchaseList"
				method="post">
				<div class="tab_box-cet-ser tab_box-cet-ser1">
					<em class="tab_box-cet-ser-nam">选择分类：</em>
					<div class="tab_box-cet-ser-box">
						<select id="cate-level-1" name="cate-level-1" class="increase-pop-tet1">
							<option>全部</option>
						</select>
						<select id="cate-level-2" name="cate-level-2" class="increase-pop-tet1">
							<option>全部</option>
						</select>
<!-- 						<button class="tab_box-cet-ser-soi searchBtn" type="button">搜索</button> -->
					</div>
				</div>
				<div class="tab_box-cet-ser tab_box-cet-ser1">
					<em class="tab_box-cet-ser-nam">产品名称：</em>
					<div class="tab_box-cet-ser-box">
						<input id="productName2" name="keyWord2" value="${keyWord2 }"
							class="tab_box-cet-ser-tit" type="text" /> <input
							id="productName" name="keyWord" value="${keyWord2 }" type="hidden">
						<button class="tab_box-cet-ser-so searchBtn" type="button">
							<i class="tab_box-cet-ser-ico"></i>搜索
						</button>
					</div>
				</div>
				<input id="pageNow" name="pageNow" type="hidden" value="${pageNow }"/>
			</form>
			<table class="tab-gridtable">
				<thead>
					<tr>
						<th>商品名</th>
						<th>店铺名</th>
						<th>价格</th>
						<th>发布时间</th>
						<th>联系方式</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${productSolrDTOList}" var="productSolrDTO" varStatus="i">
					<tr>
						<td><a href="${CONTEXT}market/${productSolrDTO.id}.html">${productSolrDTO.name }</a></td>
						<td><a href="${CONTEXT}${gd:formatMarket(marketId) }/business/shop/${productSolrDTO.businessId }.html">${productSolrDTO.shopsName}</a><span><img src="v1.0/images/shop-images/renzhen.jpg" alt="已通过实名认证" title="已通过实名认证" style=" cursor: pointer;vertical-align: middle;"></span><span class="shop-gong"></span></td>
						<td>
							<!-- 单价 -->
							
							<c:if test="${productSolrDTO.priceType eq '0' }">
								<span style="color: #e6971f; font-weight: bold;">${gd:formatNumber(productSolrDTO.price) } 元</span>/${gd:showValueByCode('ProductUnit',productSolrDTO.unit) }
							</c:if>
							<!-- 多价 -->
							<c:if test="${productSolrDTO.priceType ne '0' }">
								<c:forEach var="buyCountStart" items="${productSolrDTO.buyCountStart}" varStatus="loop" end="0">  
							   		<span style="color: #e6971f; font-weight: bold;">
								   		${gd:formatNumber(productSolrDTO.price) }
							   		元</span>/${gd:showValueByCode('ProductUnit',productSolrDTO.unit) }
								</c:forEach>
							</c:if>
						</td>
						<td><fmt:formatDate value="${productSolrDTO.createTime}" pattern="yyyy-MM-dd"/></td>
						<td>
						<a id="mobile_${i.index}" style="display: none;">${productSolrDTO.mobile}</a>
						<a id="mobile_view_${i.index}" onclick="javascript:$('#mobile_${i.index}').show();$(this).hide();">查看电话</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="clear"></div>
			<c:if test="${!empty productSolrDTOList}">
				<div id="page-next">
					<div class="gduiPage_main"></div>
				</div>
			</c:if>
			<c:if test="${empty productSolrDTOList}">
			<div class="fl" style="width: 100%;">
				<!-- 无搜索结果页面开始 -->
				<div class="con-right products-con logi-main" style="width: 100%;margin-left: 0px;border: none;">
					<div style="width: 100%;text-align: center;margin: 0 auto;">
					<p class="logi-tips"><span class="nosearch-ico"></span>抱歉，没有找到符合<span class="logi-tips-span">
						<c:if test="${!empty cate1Name }">
							${cate1Name }、
						</c:if>
						<c:if test="${!empty cate2Name }">
							${cate2Name }、
						</c:if>
					${keyWord2 }
					</span>的查询结果。</p>
						<div style="width: 100%;text-align: left;padding-left: 28%;margin-bottom: 80px;margin-top: -30px;">
<!-- 						<p>建议您： </p> -->
<!-- 						<p>缩短或修改您的搜索词，重新搜索。</p> -->
						</div>
					</div>
				</div>
		    </div>
		    </c:if>
		</div>
	</div>
	<!--简版底部 star-->

	<!--简版底部 end-->


	<script src="v1.0/js/jquery-1.8.3.min.js"></script>
	<script src="v1.0/js/gdui.js"></script>
	<script type="text/javascript">
	var cateId1='${cateId1}';
	var cateId2='${cateId2}';
	$(function(){
		$('.searchBtn').click(function(){
			var keyword=$("#productName2").val();
// 			if(!$.trim(keyword)){
// 				return false;
// 			}
// 			keyword=encodeURI(keyword);
			$("#pageNow").val(1);
			$("#productName").val($.trim(keyword));
			$("#form-search").submit();
		});
		
		$('#cate-level-1').change(function(){
			var cateId=$('#cate-level-1').val();
			if(!isNaN(cateId)){
				changeCate(cateId);
			}else{
				$('#cate-level-2').empty();
				$('#cate-level-2').append("<option>全部</option>");
			}
		});
		
		function changeCate(cateId){
			$.ajax({
				type: "GET",
				url: "${CONTEXT }product/getChildProductCategory/"+ cateId,
				dataType: "json",
				success: function(data) {
					if (data.length != 0) {
						$('#cate-level-2').empty();
						$('#cate-level-2').append("<option>全部</option>");
						for ( var n = 0; n < data.length; n++) {
							if(cateId2==data[n].categoryId){
								$('#cate-level-2').append("<option selected='selected' value='"+data[n].categoryId+"'>"+data[n].cateName+"</option>");
							}else{
								$('#cate-level-2').append("<option value='"+data[n].categoryId+"'>"+data[n].cateName+"</option>");
							}
						}
					}
				}
			});
		}
		
		function changeTopCate(){
			$.ajax({
				type: "GET",
				url: "${CONTEXT }product/listProductCategoryByLvAndMarketId",
				dataType: "json",
				data:{
					"curLevel":0,
					"marketId":3
				},
				success: function(data) {
					if (data.length != 0) {
						$('#cate-level-1').empty();
						$('#cate-level-1').append("<option>全部</option>");
						for ( var n = 0; n < data.length; n++) {
							if(cateId1==data[n].categoryId){
								$('#cate-level-1').append("<option selected='selected' value='"+data[n].categoryId+"'>"+data[n].cateName+"</option>");
							}else{
								$('#cate-level-1').append("<option value='"+data[n].categoryId+"'>"+data[n].cateName+"</option>");
							}
						}
					}
				}
			});
		}
		
		changeTopCate();
		if(cateId1==-1){
			
		}else{
			changeCate(cateId1);
		}
	});
	
		$('.rse-pro-tt').change(function(){
			isMarketChange($(this))

		});
		var isMarketChange = function(ele){
			var val = $(ele).val();
			if(val==0){
				$(".edit-cont-i").show();
				$(".edit-cont-sel").hide();
			}else{
				$(".edit-cont-i").hide();
				$(".edit-cont-sel").show();
			}
		}
		$(function(){
			isMarketChange($('.rse-pro-tt'))
		});

		$('.i-delete').click(
			function(){
			layer.confirm('确定删除吗？', {
			cusClass:'',//类名
		    btn: ['确定','取消'] //按钮
			});
		})

	</script>
	<script type="text/javascript">
		$('.i-shelves').click(
			function(){
			layer.confirm('上架成功', {
			cusClass:'',//类名
		    btn: ['确定','取消'] //按钮
			});
		})

	</script>
	<script type="text/javascript">
		$(document).ready(function(){
			var $tab_li = $('#tab ul li');
			$tab_li.click(function(){
			$(this).addClass('selected').siblings().removeClass('selected');
			var index = $tab_li.index(this);
			$('div.tab_box > .tab_box-cet').eq(index).show().siblings().hide();
			});	
		});
	</script>
	<script type="text/javascript">
	var pageTotal=${pageTotal};
	var pageNow=${pageNow};
	//console.log(pageTotal);
	//console.log(pageNow);
	gduiPage({
	    cont: 'page-next',
	    pages: pageTotal, //
	    skip: true, //是否开启跳页
	    /* curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(),  */
	    curr:pageNow,
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	            //location.href = '?page='+e.curr;
	        	$("#pageNow").val(e.curr);
	        	var keyword=$("#productName2").val();
// 				keyword=encodeURI(keyword);
				$("#productName").val(keyword);
				$("#form-search").submit();
	        }
	    },
	    staticPage:true 
	});	
	</script>
</html>
