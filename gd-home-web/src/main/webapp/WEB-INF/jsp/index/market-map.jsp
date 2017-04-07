<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>全国农批市场</title>
	<%@ include file="../pub/head.inc" %>
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/index-shop.css">
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="nav_title"><span>全国农批市场</span>
				<div class="nav_title_show"></div>
			</div>
			<div class="main_menu">
				<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
			<!-- 二级分类 -->
		</div>
	</div>
	<!--head end-->
	
	
	<!--slide start-->
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop clearfix">
			<div class="mark-company">
			
				<div class="con-right line-top" style="width: 1170px; margin: 0;">
					<div class="line-top-map">
					 <!-- <p class="p-top-map">以农批市场为中心,构建中国最大O2O农批平台，整合全国农批市场<br/>
建立信息化农批市场，打造全国农贸市场综合信息集散中心，实现农产品流通的高效化与降低资源损耗；<br/>
精准及时推送农产品价格信息，货运信息，让一手掌控行情动态；<br/>
建立安全型农批市场，打造防伪溯源食品安全体系，规范市场并全力支持国家“菜篮子”及“米袋子”民生工程；<br/>   
建立小微金融体系，通过信用评估体制，为农户、经营户提供专项贷款，帮助实现增产，增收。</p>
					<p class="cet-top-map">谷登农批网以成就客户为己任 ，已锐意创新为指南，诚信负责为准则，做农业电商先锋！</p> -->
					<div class="pd43-top-map">
						<p class="top-map-gr">截止2015年，全国共有亿级农产品批发市场
							<strong class="top-map-str">4522</strong><span class="top-map-spn">家</span>
						</p>
						<p class="top-map-gr">市场农产品交易额达
							<strong class="top-map-str">4万亿</strong><span class="top-map-spn">元</span>
						</p>
						<p class="top-map-gr">
							<strong class="top-map-str top-map-str2">谷登农批网正在整合中……</strong>
						</p>
						<p class="por-map-bsz">
							<a class="por-map-yul1" href="http://www.gdeng.cn/baishazhou.html" title="武汉"></a>
							<a class="por-map-yul1 por-map-yul2" href="http://www.gdeng.cn/yulin.html" title="玉林"></a>
						<img src="${CONTEXT }v1.0/images/shop-images/map.png"/>
						</p>
					</div>
					</div>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>
	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
	<script src="${CONTEXT }v1.0/js/gdui.js"></script>
	<script src="${CONTEXT }v1.0/js/common.js"></script>
	<script src="${CONTEXT }v1.0/js/home.js"></script>
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
	</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>