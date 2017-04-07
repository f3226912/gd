<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<div id="template_other_layout" style="display: none">
	<div class="ahref" data-ahref="#link">
		<div class="row plr_10p ptb_6p fontSize_13p mt_5p">
			<span class="color_ff6c00">#get</span> <span></span>
			<div class="color_ff6c00 ab_right">详情>></div>
			<hr class="hr">
		</div>
	
		<div class="row plr_10p ptb_15p mui-clearfix">
			<div class="column30 order_from mui-ellipsis-2">#send</div>
			<div class="column40 mui-text-center">
				<img src="${CONTEXT }v1.0/images/arrow_car.png" width="100">
			</div>
			<div class="column30 order_to mui-ellipsis-2">#for</div>
		</div>
	
		<div class="row plr_10p mui-clearfix fontSize_13p ptb_4p">
			<span class="ml_10p">车型：</span> <span>#carType</span>
			<div class="ab_right">#totalWeight #hundredweightString</div>
		</div>
	
		<div class="row plr_10p mui-clearfix fontSize_13p ptb_4_8p">
			<span class="ml_10p">货物分类：</span> <span>#goodsType</span>
		</div>
	</div>
</div>

<div id="template_layout" style="display: none">
	<div class="ahref" data-ahref="#link">
		<div class="row plr_10p ptb_6p fontSize_13p mt_5p">
			<span>#time</span> <span class="color_ff6c00 ml_10p">#comanySend</span>
			<div class="color_ff6c00 ab_right">详情>></div>
			<hr class="hr">
		</div>
	
		<div class="row plr_10p ptb_15p mui-clearfix">
			<div class="column30 order_from mui-ellipsis-2">#send</div>
			<div class="column40 mui-text-center">
				<img src="${CONTEXT }v1.0/images/arrow_car.png" width="100">
			</div>
			<div class="column30 order_to mui-ellipsis-2">#for</div>
		</div>
	
		<div class="row plr_10p mui-clearfix fontSize_13p ptb_4p">
			<span class="ml_10p">车型：</span> <span>#carType</span>
			<div class="ab_right">#totalWeight #hundredweightString</div>
		</div>
	
		<div class="row plr_10p mui-clearfix fontSize_13p ptb_4_8p">
			<span class="ml_10p">货物分类：</span> <span>#goodsType</span>
		</div>
	</div>
</div>

<div id="blank" style="display:none;">
<div id="noInfo" class="black_outline">
	<div class="mui-text-center">
		<img src="${CONTEXT }v1.0/images/blank_box.png" alt="" width="150"> 
	</div>
	<div class="mui-text-center blank">您还没有相关订单信息</div>
</div>
</div>
