<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="谷登农批网,公告信息"/>
		<meta name="description" content="${dto.title}-信息公告"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css"/>
		<title>${dto.title}_谷登农批网</title>
	</head>
<body>
	<!--head start-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>

	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="main_menu check-nav">
				<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<!--head end-->
	
	<!--slide start-->
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop">
			<div class="con-left fl">
				<div class="con-left-tit check-img logi-right helps">
					<p>公告信息</p>
				</div>
				<div class="con-left-tit helps-left helps-left1 helpa-aboutus">
					<h2 class="shopname helps-left-tit public-meg"><a href="${CONTEXT}notice-list">公告信息</a><span class="helps-ico02"></span></h2>
				</div>
			</div>
			<div class="fl">
				<div class="con-right products-con helps-cont">
					<p class="helps-txt helps-public-con helps-cont-title">${dto.title}</p>
					<p class="helps-txt helps-public-con helps-cont-time">时间：<fmt:formatDate value="${dto.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss EEEE"/></p>
					<p class="helps-txt helps-public-con">${dto.content}</p>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>


	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	
	<script type="text/javascript" src="${CONTEXT}v1.0/js/gdui.js"></script>
	<script type="text/javascript" src="${CONTEXT}v1.0/js/common.js"></script>
	<script>
		gduiPage({
	    cont: 'mar-com-fenye',
	    pages: 18, //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	            location.href = '?page='+e.curr;
	        }
	    },
	    staticPage:true 
	});

		gduiPage({
	    cont: 'mar-com-fenye2',
	    pages: 18, //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
	    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
	        var page = location.search.match(/page=(\d+)/);
	        return page ? page[1] : 1;
	    }(), 
	    jump: function(e, first){ //触发分页后的回调
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	            location.href = '?page='+e.curr;
	        }
	    },
	    staticPage:true 
	});

		$(".logi-tit-1").click(function(){
			$(this).addClass("logi-bacg-gray");
			$(".logi-tit-2").removeClass("logi-bacg-gray");
			$(".logi-change1").show();
			$(".logi-change2").hide();
		});
		$(".logi-tit-2").click(function(){
			$(this).addClass("logi-bacg-gray");
			$(".logi-tit-1").removeClass("logi-bacg-gray");
			$(".logi-tit-1").addClass("logi-bacg-white");
			$(".logi-change1").hide();
			$(".logi-change2").show();
		});
	</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>