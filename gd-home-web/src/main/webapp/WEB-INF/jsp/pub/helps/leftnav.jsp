<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="con-left fl">
<div class="con-left-tit check-img logi-right helps">
	<p>帮助中心</p>
</div>
<div class="con-left-tit helps-left helps-left1">
	<h2 class="shopname helps-left-tit">新手指南<span class="helps-ico2"></span></h2>
	<div class="shopname1 helps-menu">
		<p class="helps-menu-2"><a href="${CONTEXT }help/index">注册新用户</a></p>
		<p class="helps-menu-2"><a href="${CONTEXT }help/accopass">账户密码</a></p>
		<p class="helps-menu-2"><a href="${CONTEXT }help/shiming">实名认证</a></p>
	</div>
</div>
<div class="con-left-tit helps-left">
	<h2 class="shopname helps-left-tit">会员入门<span class="helps-ico2"></span></h2>
	<div class="shopname1 helps-menu">
		<p class="helps-menu-2"><a href="${CONTEXT }help/sendproduct">发布批发商品</a></p>
		<p class="helps-menu-2"><a href="${CONTEXT }help/saleproduct">销售中的产品</a></p>
		<p class="helps-menu-2"><a href="${CONTEXT }help/unlineproduct">未上架的产品</a></p>
		<!-- <p class="helps-menu-2"><a href="">店铺管理</a></p>
	<p class="helps-menu-2"><a href="">产品分类</a></p>
	<p class="helps-menu-2">店铺装修</p> -->
		</div>
	</div>
	<div class="con-left-tit helps-left">
		<h2 class="shopname helps-left-tit">新手指南<span class="helps-ico2"></span></h2>
		<div class="shopname1 helps-menu">
			<p class="helps-menu-2"><a href="${CONTEXT }help/buyerpian">买家防骗</a></p>
			<p class="helps-menu-2"><a href="${CONTEXT }help/salerpian">卖家防骗</a></p>
			<!-- p class="helps-menu-2"><a href="${CONTEXT }help/tradedisputes">交易纠纷</a></p>
			<p class="helps-menu-2"><a href="${CONTEXT }help/complaint">投诉举报</a></p> -->
		</div>
	</div>
	
	<script>
		$(function(){
			$(".helps-left-tit").click(function(){
				var _self = $(this),
					_ico = _self.find("span"),
					$next = _self.next();
				if($next.is(":hidden")){
					_ico.removeClass("helps-ico").addClass("helps-ico2");;
					$next.slideDown();
					
				}else{
					_ico.removeClass("helps-ico2").addClass("helps-ico");
					$next.slideUp();
				}
			});
		})
	</script>
</div>