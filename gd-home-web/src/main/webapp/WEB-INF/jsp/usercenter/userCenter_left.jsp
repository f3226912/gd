<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../pub/tags.inc" %>
<%   
  String uri=request.getRequestURI();   
  uri=uri.substring(uri.lastIndexOf("/")+1);
  request.setAttribute("uri", uri);
  %>
  
	<div class="middle-info">
		<div class="mid-left">
			<div class="mid-left-gdnp">
				<h1 class="gdnp-h1"><i class="gdnp-h1-ico"></i>我的谷登农批网</h1>
				<c:if test="${!empty businessId }">
				<ul class="list-sp">
					<h2 class="list-sp-h2"><i class="gdnp-h1-ico gdnp-h1-ico-h2"></i>					
					<span class="gdnp-ico-rt"></span>我的商品</h2>
					<ul class="list-sp-ic">
						<c:if test="${loginMember.level !=4 }">
							<li <% if (uri.equals("chooseCategory.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/product/chooseCategory">发布新产品</a></li>
						</c:if>
						<%-- <li <% if (uri.equals("chooseCategory.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/product/chooseCategory">发布新产品</a></li> --%>
						<li <% if (uri.equals("onSaleList.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/product/saleList/3">销售中的产品</a></li>
						<li <% if (uri.equals("offSaleList.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/product/saleList/4">未上架的产品</a></li>
					</ul>
				</ul>
				<ul class="list-sp">
					<h2 class="list-sp-h2"><i class="gdnp-h1-ico gdnp-h1-ico-h22"></i>
					<span class="gdnp-ico-rt"></span>我的商铺管理</h2>
					<ul class="list-sp-ic">
						<li><a href="${CONTEXT}${gd:formatMarket(marketId) }/business/shop/${businessId }.html">进入商铺</a></li>
						<li <% if (uri.equals("business_edit.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/business">商铺信息</a></li>
						<li <% if (uri.equals("member_contact.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/member/contact">联系我们</a></li>
						<li <% if (uri.equals("business_decorate.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/business/decorate/0">商铺装修</a></li>
				    </ul>
				</ul>
				</c:if>
				<ul class="list-sp">
					<h2 class="list-sp-h2"><i class="gdnp-h1-ico gdnp-h1-ico-h23"></i>
					<span class="gdnp-ico-rt"></span>我的关注</h2>
					<ul class="list-sp-ic">
						<li <% if (uri.equals("collectproduct.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/collect/product">关注的农批产品</a></li>
						<li <% if (uri.equals("collectshops.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/collect/shops">关注的商铺</a></li>
						<c:if test="${!empty businessId }">
						<li <% if (uri.equals("collectfocusme.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/collect/focusme">关注我的客户</a></li>
						</c:if>
				    </ul>
				</ul>
			</div>
			<div class="gdnp-list">
				<h1 class="gdnp-h1 gdnp-tp"><i class="gdnp-list-ico"></i>我的物流需求</h1>
				<ul class="list-sp">
					<li <% if (uri.equals("memberAddressList.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/transportation/query">发布物流需求</a></li>
				</ul>
			</div>
			<%-- <c:if test="${!empty businessId && loginMember.level !=4 }">
			<div id="product-menu-purchase" class="gdnp-list">
				<h1 class="gdnp-h1 gdnp-tp"><i class="gdnp-list-ico gdnp-list-ico2"></i>我的货源信息</h1>
				<ul class="list-sp">
					<li <% if (uri.equals("productPurchase.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/purchaseList">我要进货</a></li>
				</ul>
			</div>
			</c:if> --%>
			<div class="gdnp-list">
				<h1 class="gdnp-h1 gdnp-tp"><i class="gdnp-list-ico gdnp-list-ico1"></i>我的账户设置</h1>
				<ul class="list-sp">
					<li <% if (uri.equals("member_edit.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/member">基本信息管理</a></li>
					<li <% if (uri.equals("attestent.jsp")||uri.equals("attestpre.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/attest">实名认证</a></li>
					<li <% if (uri.equals("password.jsp")){ %>class="list-sp-selected"<% } %> ><a href="${CONTEXT }userCenter/member/password">修改密码</a></li>
				</ul>
			</div>	
		</div>
		
		<script type="text/javascript">

		$('.list-sp-h2').click(function(){

			var i_self = $(this),
					a_ico = i_self.find("span"),
					$next = i_self.next();
				if($next.is(":hidden")){
					a_ico.removeClass("gdnp-ico-rt1").addClass("gdnp-ico-rt");;
					$next.slideDown();
					
				}else{
					a_ico.removeClass("gdnp-ico-rt").addClass("gdnp-ico-rt1");
					$next.slideUp();
				}
		})

	</script>