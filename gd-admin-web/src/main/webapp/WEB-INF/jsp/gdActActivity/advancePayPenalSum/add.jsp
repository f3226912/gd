<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<script type="text/javascript">
	var view = "${view}";
	var id = "${activity.id}";
</script>

<script type="text/javascript" src="${CONTEXT}js/gdActActivity/advancePayPenalSum/validate.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/advancePayPenalSum/add.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/advancePayPenalSum/goodsUser.js"></script>
<style>
table {
	
	border-collapse: collapse;
}

#tt tr {
	line-height: 50px;
}

#tt td {
	padding-left: 10px;
}
</style>

<div id="tt" class="easyui-tabs">
	<div title="基本信息" style="margin-left: 10px;">
		<form id="basicRule" method="post" action="#">
			<table>
				<tr>
				    <input type="hidden" id="activityId" name="id" value="${activity.id}" />
					<td class="mleft">
						<span style="color: red">*</span>活动名称：
					</td>
					<td class="mright">
						<input type="text" id="name" name="name" maxlength="30"
						value="${activity.name}" required="true" class="easyui-validatebox" validType="validateActName"
						style="width: 150px" />
					</td>
				</tr>
				<tr>
					<td class="mleft"><span style="color: red">*</span>活动类型：</td>
					<td class="mright">
						<input type="hidden" name="type" value="4" />
						<select id="b_type" required="true" class="easyui-validatebox" style="width: 150px" disabled="disabled">
							<option value="4">预付款/违约金</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft"><span style="color: red">*</span>活动时间：</td>
					<td class="mright">
						<!-- 开始时间 -->
						<input type="text" id="startTime" placeholder="开始时间"
						required="true" class="easyui-validatebox" name="startTime" value="${activity.startTimeString}"
						<c:if test="${empty view }">
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}',minDate:'%y-%M-%d'})"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}',minDate:'%y-%M-%d'})"
						</c:if>
						style="width: 150px">~ 
						
						<!-- 结束时间 -->
						<input type="text" id="endTime" placeholder="结束时间"
						required="true" class="easyui-validatebox" name="endTime" value="${activity.endTimeString}"
						<c:if test="${empty view }">
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"
						</c:if>
						style="width: 150px">
					</td>
				</tr>
			</table>
		</form>
		<c:if test="${empty view }">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="nextStepCheck('basicRule',1, checkActTime)">下一步 </a>
			<c:if test="${not empty activity}">
				<gd:btn btncode='BTNHYGLHYLB11'>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">提交</a>
				</gd:btn>
			</c:if>
		</c:if>
	</div>
	
	<div title="商品用户" style="margin-left: 10px;">
		<form id="userRule" method="post" action="#">
			<table style="width: 700px;">
				<tr>
					<td>
						<span style="color: red">*</span><span style="font-size: 15px;">所属市场：</span>
					</td>
					<td>
						<c:if test="${not empty activity}">
						<input type="hidden" value="${activity.marketId }" name="market_type" />
						</c:if>
						<select name="market_type"  onchange="initCategory();" id="market_type" required="true" labelPosition="top" class="easyui-validatebox" style="width: 150px">
							<option value="" >-请选择-</option>
							<c:forEach items="${markets}" var="item" varStatus="status">
								<c:if test="${activity.marketId eq item.id}">
									<option value="${item.id}" selected="selected">${item.marketName}</option>
								</c:if>
								<c:if test="${activity.marketId != item.id}">
									<option value="${item.id}">${item.marketName}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><span style="font-size: 15px;">买家设置</span></td>
					<td><span style="font-size: large;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="showBuyerWin" href="javascript:void(0)">活动买家信息</a></span>
						&nbsp;
						<span style="font-size: large;"><a id="showProdWin" href="javascript:void(0)">活动商品信息</a></span></td>
					</td>
				</tr>
				<c:if test="${empty view }">
				<tr>
					<td><span>指定买家：</span></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" onclick="addBuyer()" value="添加买家" /></td>
				</tr>
				<tr>
					<td><input type="radio" name="userRuleBuyer" id="userRuleBuyerProd" value="4" tag="0"/><span>添加活动商品：</span></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span id="addprod_span"><input type="button" value="添加商品" onclick="addProd();" disabled="disabled" /></span>&nbsp;</td>
					<input type="hidden" name="product_id" id="product_id" value=""/>
				</tr>
				</c:if>
			</table>
			<table style="width: 700px;">
				<tr>
					<td><span style="font-size: 15px;">卖家设置</span></td>
					<td><span style="font-size: large;"><a id="showShopWin" href="javascript:void(0)">活动商铺信息</a></span>
				</tr>
				<tr>
					<td>
						<input type="hidden" name="market_id" id="market_id" value=""/>
						<input type="radio" name="userRuleBuyer" id="userRuleBuyerCate" tag="0" value="3" />&nbsp;
						指定商品类目：
					</td>
					<td>
						<span id="categoryPanel">
							<select id="cate1" onchange="cateChange(1)" onclick="checkMarket();" disabled="disabled" >
								<option value="-1">-请选择-</option>
							</select>
							<select id="cate2" onchange="cateChange(2)" disabled="disabled" >
								<option value="-1">-请选择-</option>
							</select>
							<select id="cate3" onchange="cateChange(3)" disabled="disabled" >
								<option value="-1">-请选择-</option>
							</select>
							<input type="hidden" id="categoryId_add" name="categoryId">
						</span>
					</td> 
				</tr>
				<c:if test="${empty view }">
				<tr>
					<td>
						<input type="radio" name="userRuleBuyer" id="userRuleBuyerShop" value="2" tag="0"/>&nbsp;
						<span>添加活动商铺：</span>
					</td>
					<td>
						<span id="addshop_span"><input type="button" value="添加商铺" onclick="addShop()" disabled="disabled"/></span>
					</td> 
				</tr>
				</c:if>
			</table>
		</form>
		<c:if test="${empty view }">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="nextStep('userRule',0)">上一步 </a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="nextStepCheck('userRule',2, checkProdUser)">下一步 </a>
			<c:if test="${not empty activity}">
				<gd:btn btncode='BTNHYGLHYLB11'>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">提交</a>
				</gd:btn>
			</c:if>
		</c:if>
	</div>
	<div title="预付款">
		<form id="advancePayment" method="post" action="#">
			<div style="padding: 10px;">
				<h1>用户预付款设置</h1>
				<h2><span style="color:red">*</span>买家预付款设置</h2>
				<div style='border: solid 1px #E6DDDD; width: 700px;'>
					<table>
						<tr>
							<td width="202">
								<input type="radio" name="advancePayment" value="0" id="fixedAmt" tag="0" checked="checked"/>按固定金额收取 ：
							</td>
							<td>
								<input type="text" name="advancePaymentAmt" id="advancePaymentAmt" class="easyui-validatebox" data-options="required:true,validType:['num','range[0,2000.00]']" placeholder=" &le;2000"/>&nbsp;元/商品ID
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="radio" name="advancePayment" value="2" tag="0" id="advancePaymentByPercent"/>按订单商品小计金额百分比收取：&nbsp;&nbsp;&nbsp;
								<input type="text" id="advancePaymentPercent" name="advancePaymentPercent" class="easyui-validatebox" data-options="required:true,validType:['num','range[0,100.00]']"/>&nbsp;%，
								预付款上限&nbsp;<input type="text" id="advancePaymentLimit" name="advancePaymentLimit" class="easyui-validatebox" data-options="validType:['num','range[0,2000.00]']"/>&nbsp;元/商品ID
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
		<c:if test="${empty view }">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="nextStep('advancePayment',1)">上一步 </a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="nextStep('advancePayment',3);">下一步 </a>
			<c:if test="${not empty activity}">
				<gd:btn btncode='BTNHYGLHYLB11'>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">提交</a>
				</gd:btn>
			</c:if>
		</c:if>
	</div>
	<div title="违约金">
		<div style="padding: 10px;">
			<h1>用户违约金设置</h1>
			<form id="penalSumForPlateform" method="post" action="#">
				<h2>买家给平台的违约金设置</h2>
					按预付款百分比：&nbsp;&nbsp;&nbsp;
					<input type="text" id="penalSumPlatFormPercent" name="penalSumPlatFormPercent" class="easyui-validatebox" 
					data-options="validType:['num','range[0,2000.00]','penalSumTotal[\'#penalSumSellerPercent\',\'#penalSumLogisticsPercent\']']"/>&nbsp;%
			</form>
			<form id="penalSumForSeller" method="post" action="#">
				<h2>买家给卖家的违约金设置</h2>
					按预付款百分比：&nbsp;&nbsp;&nbsp;
					<input type="text" id="penalSumSellerPercent" name="penalSumSellerPercent" class="easyui-validatebox" 
					data-options="validType:['num','range[0,2000.00]','penalSumTotal[\'#penalSumPlatFormPercent\',\'#penalSumLogisticsPercent\']']"/>&nbsp;%
			</form>
			<form id="penalSumForLogistics" method="post" action="#">
				<h2>买家给物流公司的违约金设置</h2>
					按预付款百分比：&nbsp;&nbsp;&nbsp;
					<input type="text" id="penalSumLogisticsPercent" name="penalSumLogisticsPercent" class="easyui-validatebox" 
					data-options="validType:['num','range[0,2000.00]','penalSumTotal[\'#penalSumPlatFormPercent\',\'#penalSumSellerPercent\']']"/>&nbsp;%
			</form>
		</div>
		<c:if test="${empty view }">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">提交</a>
			</gd:btn>
		</c:if>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		//查看状态
		<c:if test="${not empty view }">
			$("select").attr("disabled","disabled");
			$("input").attr("readonly", true);
			$("input[type=radio]").attr("disabled","disabled");
			$("input[type=checkbox]").attr("disabled","disabled");
			$("#datagrid-form #activityCode").removeAttr("readonly");
			$("#datagrid-form #activityName").removeAttr("readonly");
			$("#datagrid-form #activityType").removeAttr("disabled");
			$("#datagrid-form #state").removeAttr("disabled");
		</c:if>

	});
	
</script>
<c:if test="${not empty activity}">
	<script type="text/javascript">
		//卖家信息
		var solderRule = jQuery.parseJSON('${solderRule}');
		//买家信息
		var buyerRules = jQuery.parseJSON('${buyerRules}');
		
		//预付款
		var advancePaymentRule = jQuery.parseJSON('${advancePaymentRule}');
		//三个违约金
		var penalSumPlatFormRule = jQuery.parseJSON('${penalSumPlatFormRule}');
		var penalSumSellerRule = jQuery.parseJSON('${penalSumSellerRule}');
		var penalSumLogisticsRule = jQuery.parseJSON('${penalSumLogisticsRule}');
	</script>
	<script type="text/javascript" src="${CONTEXT}js/gdActActivity/advancePayPenalSum/initedit.js"></script>
</c:if>