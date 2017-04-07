<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<meta name="format-detection" content="telephone=no" />
		<title></title>
		<link rel="stylesheet" href="${CONTEXT }/v1.0/css/data/reportData.css">
		
		
		<style type="text/css">				
		</style>
	</head>
	<body>
		<div class="row head-row">
			<div class="head-outline clearfix">
				<div id="oneDay" class="one-day head-day not-tapped lyHeadDays">今日</div>
				<div id="sevenDays" class="seven-days head-day tapped lyHeadDays">7日</div>
				<div id="fifDays" class="fif-days head-day not-tapped lyHeadDays">15日</div>
				<div id="thirtyDays" class="thirty-days head-day not-tapped lyHeadDays">30日</div>
			</div>
		</div>
		<div class="row chart-row">
			<div id="main" class="chart-inner"></div>
			<div class="chart-instruction">
				注：1.纵线左侧为交易额 &nbsp &nbsp2.横线表示商品排名顺序
			</div>		
		</div>
		<div  id="listCont" class="table-outline row">
			<div class="table-ins">
				<span class="table-in1">表单详细数据</span>
				<span class="table-in2">（交易额单位：元）</span>
			</div>
			<table class="table" border="1" cellpadding="10" cellspacing="0">
				<thead>
					<tr>
						<th>排名</th><th>商品名称</th><th>销量</th><th>交易额</th>
					</tr>
				</thead>
				<tbody id="tbody">
					<!-- <tr>
						<td class="td-rank">1</td><td class="td-goods">大香蕉</td><td class="td-sales">20顿</td><td class="td-amount">2,333,000</td>
					</tr>
					-->
					
				</tbody>
			</table>
		</div>
		<script src="${CONTEXT }/v1.0/js/jquery-2.1.4.min.js"></script>
		<script src="${CONTEXT }/v1.0/js/jquery.mobile.custom.min.js"></script>
		<script src="${CONTEXT }/v1.0/js/echarts.min.js"></script>
		<script src="${CONTEXT }/v1.0/js/data/common.js"></script>
		<script src="${CONTEXT }/v1.0/js/data/goodsRanking.js"></script>

		
	</body>
</html>
