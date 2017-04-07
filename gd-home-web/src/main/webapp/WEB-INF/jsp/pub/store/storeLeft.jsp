<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="leftTit" class="con-left fl">
	<div class="con-left-tit" id="leftTit-1">
		<h2 class="shopname" id="shopname">商铺信息</h2>
		<div class="shopname1" id="shopname1">
			<h3>${dto.busiInfo.shopsName }
			<c:if test="${dto.busiInfo.cerifiStatus==1 }">
				<span id="isIdCard_img" class="shop-realname"></span>
			</c:if>
			<c:if test="${dto.busiInfo.level==1 }">
				<span id="shop-pi" class="shop-pi" style="display:none;"></span>
			</c:if>
			<c:if test="${dto.busiInfo.level==4 }">
				<span id="shop-gong" class="shop-gong" style="display:none;"></span>
			</c:if>
			</h3>
			<p>${dto.busiInfo.legalRepresentative}</p>
			<p>经营模式：${dto.busiInfo.businessModelStr}</p>
			<p>所在市场：${dto.busiInfo.marketName}</p>
		</div>
		<div class="shopname2">
			<p>浏览次数： ${dto.busiInfo.browseCount}</p>
			<p>批发信息数量：${dto.busiProductTotal}条</p>
		<c:if test="${dto.isFocusStore }">
			<p class="mark js-uncollect" style="background-color: #666; display:none;">
				<span class="reg-mark"></span>取消关注</p>
		</c:if>
		<c:if test="${!dto.isFocusStore }">
			<p class="mark js-collect">
				<span class="reg-mark"></span>关注店铺</p>
		</c:if>
		</div>
	</div>
	<div class="con-left-tit" id="leftTit-2">
		<h2 class="shopname">联系我们</h2>
		<div class="shopname2">
	
			<p>联 系 人：${dto.busiInfo.contact}</p>
			<p>电 话：${dto.busiInfo.telephone}</p>
			<p>
				手 机：
				
			<c:if test="${!dto.isLogin }">
				<span class="mob-btn showLoginDialog">
					<span class="mob-mark"></span>登录后可见
				</span>
			</c:if>
			<c:if test="${dto.isLogin }">
				<span id="mobile_span">${dto.busiInfo.mobile }</span>
			</c:if>
			</p>
			<p>电子邮箱：${dto.busiInfo.email}</p>
			<p>地 址：${dto.busiInfo.province} ${dto.busiInfo.city} ${dto.busiInfo.area} ${dto.busiInfo.address} </p>
			<p>邮 编：${dto.busiInfo.zipCode}</p>
		</div>
	</div>
	<div class="con-left-tit" id="leftTit-3" style="display: none;">
		<h2 class="shopname">产品类型</h2>
		<div class="shopname2">
	
			<p>
				<a href="">全新产品</a>
			</p>
			<p>
				<a href="">最新上架</a>
			</p>
			<p>
				<a href="">最热产品</a>
			</p>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/jsp/pub/collectAccess.jsp" %>

<script type="text/javascript">
function focusFunc() {
	debugger;
	$.ajax({
		type: 'POST',
		url: '${CONTEXT }business/shop/focus/'+ '${bid }' ,
	    cache: 'false' ,
	    dataType: 'json' ,
	    success: function(data) {
	    	
	    	if(data.statusCode==0) {
	    		$("#collect_number").html(data.number);
	    		
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

	    		$(".js-collect").hide();
	    		$(".js-uncollect").show();
	    	} else {
	    		alert(data.msg);
	    	}
	    } ,
	    error: function(err) {
	    	alert('系统维护中。。。');
	    }
	});
}

function blurFunc() {
	$.ajax({
		type: 'POST',
		url: '${CONTEXT }business/shop/blur/'+ '${bid }' ,
	    cache: 'false' ,
	    dataType: 'json' ,
	    success: function(data) {
	    	if(data.statusCode==0) {
	    		$(".js-collect").show();
	    		$(".js-uncollect").hide();
	    		
	    	} else {
	    		alert(data.msg);
	    	}
	    } ,
	    error: function(err) {
	    	alert('系统维护中。。。');
	    }
	});
}

function retStr(str) {
	if(str==null) {
		return "";
	}
	return str;
}

var sl = $("#leftTit-1");
sl.find(".js-collect").click(function(){
	Login.checkLogin(focusFunc);
});

sl.find(".js-uncollect").click(function(){
	Login.checkLogin(blurFunc);
});

</script>
