<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!doctype html>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
<%-- 	<link rel="stylesheet" href="${CONTEXT }v1.0/css/global.css"/>	 --%>
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/member.css"/>
	<script type="text/javascript" src="${CONTEXT}js/page.js"></script>	
<%-- 	<script type="text/javascript" src="${CONTEXT }js/jquery-1.8.3.min.js"></script> --%>

</head>
<body>
	<!--头部-->
	<jsp:include page="/WEB-INF/jsp/usercenter/userCenter_head.jsp" flush="true"/> 
   	<jsp:include page="/WEB-INF/jsp/usercenter/userCenter_left.jsp" flush="true"/> 
	<!--头部-->
		
		<!-- 收藏的产品 -->
		<div class="mid-right bg-white">
			
			<div class="tab-box-col">
				<ul class="tab_col_menu">
				    	<c:if test="${ not empty businessId }"><li class=""><a href="${CONTEXT }userCenter/collect/focusme">关注我的客户</a></li></c:if>
				        <li class="selected"><a>关注的农批产品</a></li>
				        <li class=""><a href="${CONTEXT }userCenter/collect/shops">关注的商铺</a></li>
				</ul>
				
				<div class="tab-box-c">
					<c:if test="${page==null || empty page.pageData }">
						<p style="margin: 50px 0; text-align: center; font-size: 14px;">亲，你还没有关注任何产品哦！</p>
					</c:if>
					<c:if test="${page!=null && not empty page.pageData}">
						<form id="pageForm" action="${bid }" method="post">
							<input type="hidden" id="page" name="page" value="${page.currentPage}">
							<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
							<input type="hidden" id="pageTotal" name="pageTotal" value="${page.pageTotal}">
							<input type="hidden" id="sort" name="sort" value="${param.sort}">
							<input type="hidden" id="order" name="order" value="${param.order}">
							
						</form>
						<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set> 
						<div class="tab-box-cet-cl hide1" style="display: block;">
							<table class="tab-att">
								<thead class="tab-att-thd">
									<tr>
										<th class="tab-att-a2"><input class="tb-att-chx" type="checkbox" onclick="check()"><a href="javascript:allSelect()">全选</a><a href="javascript:delSel()">删除</a></th>
										<th>商品名</th>
										<th>市场</th>
										<th>店铺名</th>
										<th>价格</th>
										<th>操作</th>
									</tr>
								</thead>
								
								<c:forEach items="${page.pageData}" var="collect" varStatus="s">
									<tbody class="tab-att-tb">
										<tr>
											<td class="tab-att-a2"><input class="tab-att-i" type="checkbox" value="${collect.productId }" style="height:80px;">
												<c:if test="${collect.status==0 }">
													<img class="tan-att-img1" width="80px" height="80px" src="${imgHostUrl }${collect.imageUrl }" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';">
												</c:if>
												<c:if test="${ collect.status != 0 }">
													<a href="${CONTEXT }market/${collect.productId}.html"><img class="tan-att-img1" width="80px" height="80px" src="${imgHostUrl }${collect.imageUrl }" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';"></a>
												</c:if>
											</td>
											<td>
												<c:if test="${collect.status==0 }">
													<span style="color:#808080">(商铺已关闭)${collect.productName }</span>
												</c:if>
												<c:if test="${ collect.status != 0 }">
													<c:if test="${collect.state eq '0'}"> 
													         （已下架）
													</c:if> 
													<c:if test="${collect.state eq '2'}"> 
													         （已下架）
													</c:if> 
													<c:if test="${collect.state eq '1'}"> 
													         （已下架）
													</c:if> 
													<c:if test="${collect.state eq '6'}"> 
													         （已下架）
													</c:if> 
													<c:if test="${collect.state eq '4'}"> 
													         （已下架）
													</c:if> 
													<c:if test="${collect.state eq '5'}"> 
													         （已删除）
													</c:if> 
													<a href="${CONTEXT }market/${collect.productId}.html">${collect.productName }</a>
												</c:if>
											</td>
											<td>${collect.marketName }</td>
											<td>${collect.shopsName }</td>
											<td><span style="color: #ff4500;">￥${gd:formatNumber(collect.price) }</span>/${gd:showValueByCode('ProductUnit',collect.unit) }</td>
											<td><a class="da-a" href="javascript:del(${collect.productId });">删除</a><br><span class="da-a-data" style="display:none;">剩余：8天5小时3分</span></td>
										</tr>
									</tbody>
								</c:forEach>
								
							</table>
							<!-- 分页 页码标签 根据具体UI修改 start -->
							<jsp:include page="/WEB-INF/jsp/pub/PageNumTemplate.jsp"></jsp:include>
							<!-- 分页 页码标签 根据具体UI修改 end -->
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<!--简版底部 star-->
	
	<!--简版底部 end-->
	<script>
	
	function delSel() {
		var ids = '';
		$(".tab-att-i").each(function(i,v) {
			if($(v).attr("checked")) {
				ids += ($(v).val() + ",");
			}
			
		});
		
		if(ids=="") {
			layer.msg('请选择要删除的项',{cusClass:' sss'});
			return;
		}
		
		$.ajax({
			type: 'POST',
			url: '${CONTEXT }userCenter/collect/bathblurpro/' ,
			data: {"productIds": ids } ,
		    cache: 'false' ,
		    dataType: 'json' ,
		    success: function(data) {
		    	if(data==-1) {
		    		alert("系统维护中。。。");
		    	} else if(data==-2) {
		    		location.href="${CONTEXT}login/initRegister";
		    	} else {
		    		layer.msg('亲，你再也看不到他们了',{cusClass:' sss'},function(){location.reload();});
		    	}
		    } ,
		    error: function(err) {
		    	alert('系统维护中。。。');
		    }
		});
		
	}
	
	function allSelect() {
		$(".tb-att-chx").attr("checked", true);
		check();
	}
	
	function check() {
		debugger;
		if($(".tb-att-chx").attr("checked")) {
			$(".tab-att-i").attr("checked","checked");
		} else {
			$(".tab-att-i").attr("checked",false);
		}
	}
	
	function blurFuncProduct(pid) {
		$.ajax({
			type: 'POST',
			url: '${CONTEXT }userCenter/blur/'+ pid ,
			cache: 'false' ,
		    dataType: 'json' ,
		    success: function(data) {
		    	if(data.statusCode==0) {
		    		layer.msg('亲，你再也看不到他了',{cusClass:' sss'},function(){location.reload();});
		    		
		    	} else {
		    		alert(data.msg);
		    	}
		    } ,
		    error: function(err) {
		    	alert('系统维护中。。。');
		    }
		});
	}
	
	function del(pid) {
		Login.checkLogin(function (){ blurFuncProduct(pid); });
	}
	</script>
	<%@ include file="/WEB-INF/jsp/pub/loginForm.jsp" %>
</html>
