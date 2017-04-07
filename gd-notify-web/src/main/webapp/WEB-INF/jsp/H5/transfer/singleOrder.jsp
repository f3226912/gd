<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<meta name="format-detection" content="telephone=no" />
		<title>物流订单</title>
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.min.css">
		<link rel="stylesheet" href="${CONTEXT }v1.0/css/logisticsOrder.css"/>
		<style type="text/css">
			.mui-fs-18p{
				font-size: 18px;
			}
			.pic_phone > .mui-icon-phone{
				font-size: 45px;
			}
			/* .mui-scroll{
				height: 100%;
			} */
			a{
				color: #ff6c00;
			}			
		</style>
	</head>
	<body>
		<script type="text/javascript" src="${CONTEXT }js/transfer/single.js"></script>
				
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
			<jsp:include page="/WEB-INF/jsp/pub/transfer/template.jsp"></jsp:include>
			<!-- 已发布模板开始处 -->
			<div id="published" class="outline">
				
			</div>
			</div>
		</div>	 
		<!-- 已发布模板结束处 -->
		<script src="${CONTEXT }v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
		<script src="${CONTEXT }v1.0/js/jquery.mobile.custom.min.js"></script>
		<script>
			var page = 1;
			var pageTotal = 1;
			var fromCode = ${fromCode};
		/**
		 * 上拉加载，下拉刷新的js
		 */
			mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
						callback: pullupRefresh
					}
				}
			});
			
			function pulldownRefresh() {
				getList(${orderNo},page++,${userId});
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh();
			}
			

			function pullupRefresh() {	
				page = 1;
				getList(${orderNo},page++,${userId});
				mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); 
			
			}
			if (mui.os.plus) {
				mui.plusReady(function() {
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().pullupLoading();
					}, 1000);

				});
			} else {
				mui.ready(function() {
					mui('#pullrefresh').pullRefresh().pullupLoading();
				});
			}
			
			$(document).ready(function(){
				$("body").on("tap",".ahref",function(){
					location.href=$(this).data("ahref");
				})		
			})
		</script> 
		
	</body>
</html>
