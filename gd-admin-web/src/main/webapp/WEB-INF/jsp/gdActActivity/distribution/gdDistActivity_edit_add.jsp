<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/distribution/activity_edit.js"></script>
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

<!-- <div id="tt" class="easyui-tabs"> -->
<div id="tt" class="easyui-tabs">
	<div title="基本信息" style="margin-left: 10px;">
		<form id="basicRule" method="post" action="#">
			<input type="hidden" id="activityId" name="id" value="${activity.id}" />
			<table>
				<tr>
					<td class="mleft"><span style="color: red">*</span>活动名称：</td>
					<td class="mright"><input type="text" id="name" name="name" maxlength="30"
						value="${activity.name}" required="true" class="easyui-validatebox" validType="validateActName"
						style="width: 150px"></td>
				</tr>
				<tr>
					<td class="mleft"><span style="color: red">*</span>活动类型：</td>
					<td class="mright">
<%-- 					<c:if test="${not empty activity}">
						<input type="hidden" name="type" value="5" />
					</c:if> --%>
					<select name="b_type" id="b_type"  required="true" class="easyui-validatebox"
						style="width: 150px" disabled>
							<!-- <option value="1">刷卡补贴</option>
							<option value="2">微信H5活动??</option>
							<option value="3">微信分享??</option>
							<option value="4">预售??</option>
							 -->
							 <option value="5">物流配送</option>
					</select>
					<!-- 物流配送 -->
					<input type="hidden" name="type" value="5" />
					
<!-- 						<script>
						var choice=$("select[id='b_type'] option")["${activity.activityType}"];
						if(choice){
							choice.selected=true;
						}
						</script> -->
						
					</td>
				</tr>
				<tr>
					<td class="mleft"><span style="color: red">*</span>活动时间：</td>
					<td class="mright"><input type="text" id="startTime" placeholder="开始时间"
						required="true" class="easyui-validatebox" name="startTime" value="${activity.startTimeString}"
						<c:if test="${empty view }">
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}',minDate:'%y-%M-%d'})"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}',minDate:'%y-%M-%d'})"
						</c:if>
						style="width: 150px">~ <input type="text" id="endTime" placeholder="结束时间"
						required="true" class="easyui-validatebox" name="endTime" value="${activity.endTimeString}"
						<c:if test="${empty view }">
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"
						</c:if>
						style="width: 150px"></td>

				</tr>
			</table>
		</form>
		<c:if test="${empty view }">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStepCheck('basicRule',1, checkActTime)">下一步 </a>
		</c:if>
		<c:if test="${not empty activity}">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" 
					<c:if test="${!empty view }">
						onclick="save()"
					</c:if>
					>提交</a>
			</gd:btn>
		</c:if>
	</div>
	
	
<%-- 	<div title="市场佣金">
		<form id="marketComm" method="post" action="#">
			<div style="margin: 10px;">
				<h1>市场收取佣金设置</h1>
				<h2>买家佣金设置</h2>
				<div id="buyerLayer" style='border: solid 1px #E6DDDD; width: 700px;'>
					<table id="buyer_tb_comm">
						<tr>
							<td width="202"><input type="radio" name="buyer" value="0" id="prodRadio" tag="0" />按订单中的商品ID收取 ：</td>
							<td>
								<input type="text" name="buyerOrderProd" id="buyerOrderProd" />&nbsp;元/商品ID
							</td>
						</tr>
						<tr>
							<td width="202"><input type="hidden" value="0" name="hid_buyer_id" id="hid_id" />
							<input type="radio" name="buyer" value="1" id="orderRadio" tag="0" />按订单固定金额收取：</td>
							<td>
								<input type="text" id="orderAmt" name="buyerOrderAmt" id="buyerOrderAmt" />&nbsp;元/单
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="radio" name="buyer" value="2" tag="0" id="orderCertRadio" />按订单商品小计金额百分比收取：&nbsp;&nbsp;&nbsp;<input type="text" id="orderBuyerSub" name="orderBuyerSub" />&nbsp;%，佣金上限&nbsp;<input type="text" id="orderBuyerLimit" name="orderBuyerLimit" />&nbsp;元/单
							</td>
						</tr>
						<tr>
							<td colspan="2" width="118">
								<input type="radio" name="buyer" value="3" tag="0" id="orderAmtCertRadio" />按订单商品小计金额所属区间收取：
							</td>
						</tr>
						<tr id="template_id" value="0">
							<td colspan="2" style=" padding: 5px;">
								<div style="margin-left:3px; border: solid 1px #E6DDDD;">
									<input type="text" id="startAmt" name="buyerStartAmt_id" style="width: 60px;"/>&nbsp;元~&nbsp;<input id="endAmt" name="buyerEndAmt_id" type="text"  style="width: 60px;"/>元&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" value="0" name="buyer_order_id" checked id="orderCertRadio" tag="0" />
									按比例&nbsp;<input id="propAmt" name="buyerPropAmt_id" type="text" maxlength="5" size="5" width="3" />%，
									佣金上限<input id="limitAmt" name="buyerLimitAmt_id" type="text" style="width: 60px;" />元/单
									&nbsp;&nbsp;&nbsp;
									<input type="radio" value="1" name="buyer_order_id"id="orderFixedRadio" tag="0" />按固定金额<input id="fixedAmt" name="buyerFixedAmt_id" type="text" style="width: 60px;" />元
									<c:if test="${empty view }">
									<a id="del_a_id" href="javascript:delRul('buyer_tb_comm', _id);" style="display:none;">&gt;&gt;删除</a>
									</c:if>
								</div>
							</td>
						</tr>
					</table>
					<c:if test="${empty view }">
					<div style="padding: 8px;">
						<a href="javascript:addMoreRule('buyer_tb_comm');">&gt;&gt;增加更多佣金区间</a>
					</div>
					</c:if>
				</div>
				<h2>卖家佣金设置</h2>
				<div id="solderLayer" style='border: solid 1px #E6DDDD; width: 700px;'>
					<table id="solder_tb_comm">
						<tr>
							<td width="202"><input type="hidden" value="0" name="hid_solder_id" id="hid_id" />
							<input type="radio" name="solder" value="1" id="orderRadio" tag="0" />按订单固定金额收取：</td>
							<td>
								<input type="text" id="orderAmt" name="solderOrderAmt" />&nbsp;元/单
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="radio" name="solder" value="2" tag="0" id="orderCertRadio" />按订单商品小计金额百分比收取：&nbsp;&nbsp;&nbsp;<input type="text" id="orderSolderSub" name="orderSolderSub" />&nbsp;%，佣金上限&nbsp;<input type="text" id="orderSolderLimit" name="orderSolderLimit" />&nbsp;元/单
							</td>
						</tr>
						<tr>
							<td colspan="2"><input type="radio" name="solder" value="3" id="orderAmtCertRadio" tag="0" />按订单商品小计金额所属区间收取：</td>
						</tr>
						<tr id="template_id" value="0">
							<td colspan="2" style=" padding: 5px;">
								<div style="margin-left:3px; border: solid 1px #E6DDDD;">
									<input type="text" id="startAmt" name="solderStartAmt_id" style="width: 60px;"/>&nbsp;元~&nbsp;<input id="endAmt" name="solderEndAmt_id" type="text" style="width: 60px;" />元&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" value="0" name="solder_order_id" checked id="orderCertRadio" tag="0" />
									按比例&nbsp;<input type="text" id="propAmt" name="solderPropAmt_id" maxlength="5" size="5" width="3" />%，
									佣金上限<input id="limitAmt"  name="solderLimitAmt_id" type="text" style="width: 60px;" />元/单
									&nbsp;&nbsp;&nbsp;
									<input type="radio" value="1" name="solder_order_id" id="orderFixedRadio" tag="0" />按固定金额<input type="text" id="fixedAmt" name="solderFixedAmt_id"  style="width: 60px;"/>元
									<c:if test="${empty view }">
									<a id="del_a_id" href="javascript:delRul('solder_tb_comm', _id);" style="display:none;">&gt;&gt;删除</a>
									</c:if>
								</div>
							</td>
						</tr>
					</table>
					<c:if test="${empty view }">
					<div style="padding: 8px;">
						<a href="javascript:addMoreRule('solder_tb_comm');">&gt;&gt;增加更多佣金区间</a>
					</div>
					</c:if>
				</div>
			</div>
		</form>
		<c:if test="${empty view }">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="nextStep('marketComm',0)">上一步 </a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="nextStepCheck('marketComm',2,checkComm)">下一步 </a>
		</c:if>
		<c:if test="${not empty activity}">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="save()">提交</a>
			</gd:btn>
		</c:if>
	</div>
	--%>

<%-- 	<div title="补贴规则" id="subRuleDiv" style="margin-left: 10px; display:none">
		<form id="subRule" method="post" action="#">
			<div style="padding: 10px;">
				<h1>卖家刷卡补贴设置</h1>
				<table>
					<tr>
						<td colspan="2"><h2>刷卡补贴规则管理</h2></td>
					</tr>
					<c:if test="${empty view }">
					<tr>
						<td colspan="2">
							<a href="javascript:void(0)" class="easyui-linkbutton"
							 onclick="addSub()">添加新规则 </a><!-- <a
							href="javascript:void(0)" class="easyui-linkbutton"
							 onclick="fileIn('marketComm',3)">导出 </a> -->
						</td>
					</tr>
					</c:if>
					<tr>
						<table id="datagrid-subrule-table" title=""></table>
					</tr>
				</table>
			</div>
		</form>
		<c:if test="${empty view }">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStep('subRule',1)">上一步 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStep('subRule',3);">下一步 </a>
		</c:if>
		<c:if test="${not empty activity}">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="save()">提交</a>
			</gd:btn>
		</c:if>
	</div>  --%>
		
	<div title="商品用户" style="margin-left: 10px;">
		<form id="userRule" method="post" action="#">
			<table style="width: 700px;">
				<tr>
					<td><span style="color: red">*</span><span style="font-size: 15px;">所属市场：</span></td>
					<td>
					<c:if test="${not empty activity}">
					<input type="hidden" value="${activity.marketId }" name="market_type" />
					</c:if>
					<select name="market_type"  onchange="initCategory();"  id="market_type" required="true" labelPosition="top" class="easyui-validatebox"
						style="width: 150px">
						<option value="" >全部</option>
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
						<span style="font-size: large;"><a id="showProdWin" href="javascript:void(0)">活动商品信息</a></span>
					</td>
				</tr>
				<tr style='display: none;'>
				    
					<td>
						<input name="id" type="hidden" value="${activity.activity_participation_rule_id}">
						<span style="color: red">*</span>订单金额：
					</td>
<%-- 					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input id="min_cost" name="min_cost" class="easyui-validatebox" value="${activity.minAmount}"
						style="width: 50px;" validType="validate_mincost"/>元~
						<input id="max_cost" name="max_cost"  value="${activity.maxAmount}" validType="validate_maxcost"
						class="easyui-validatebox" style="width: 50px;" />元，参加本次物流配送活动
						<input type="hidden" id="refer_id" name="refer_id" value="1,2"/>
					</td> --%>
				</tr>
				<c:if test="${empty view }">
				<tr>
					<td><span>指定买家：</span></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="addBuyer()" value="添加买家" />
					</td>
				</tr>
				<tr>
					<td><input type="radio" name="userRuleBuyer" id="userRuleBuyerProd" value="4" tag="0" onclick="changeAdd();"/><span>添加活动商品：</span></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="addprod_span"><input type="button" value="添加商品" onclick="addProd();" disabled="disabled" /></span>&nbsp;
						<input type="hidden" name="product_id" id="product_id" value=""/>
					</td>
				</tr>
				</c:if>
				<c:if test="${!empty view }">
				<tr>
					<td><span>指定买家：</span></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="添加买家" disabled="disabled"/>
					</td>
				</tr>
				<tr>
					<td><input type="radio" name="userRuleBuyer" id="userRuleBuyerProd" value="4" tag="0" /><span>添加活动商品：</span></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="addprod_span_view"><input type="button" value="添加商品"  disabled="disabled" /></span>&nbsp;
						<input type="hidden" name="product_id" id="product_id" value=""/>
					</td>
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
						<input type="radio" name="userRuleBuyer" id="userRuleBuyerCate" tag="0" value="3" onclick="changeAdd();" />&nbsp;
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
					<td><input type="radio" name="userRuleBuyer" id="userRuleBuyerShop" value="2" tag="0" onclick="changeAdd();" />&nbsp;<span>添加活动商铺：</span></td>
					<td><span id="addshop_span"><input type="button" value="添加商铺" onclick="addShop()" disabled="disabled"/></span></td> 
				</tr>
				</c:if>
				<c:if test="${!empty view }">
				<tr>
					<td><input type="radio" name="userRuleBuyer" id="userRuleBuyerShop" value="2" tag="0" />&nbsp;<span>添加活动商铺：</span></td>
					<td><span id="addshop_span"><input type="button" value="添加商铺" disabled="disabled"/></span></td> 
				</tr>
				</c:if>				
			</table>
		</form>
		<c:if test="${empty view }">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStep('userRule',0)">上一步 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStepCheck('userRule',2, checkProdUser)">下一步 </a>
		</c:if>
		<c:if test="${not empty activity}">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" 
					<c:if test="${empty view }">
						onclick="save()"
					</c:if>
					>提交</a>
			</gd:btn>
		</c:if>
	</div>
	
	<div title="物流配送规则" style="margin-left: 10px;">
		<form id="transferRule" method="post" action="#">
			<table style="width: 400px;">
				<tr>
					<td><span style="font-size: large;">物流配送规则设置</span></td>
				</tr>
				<tr style='border: solid 1px #E6DDDD'>
					<td>
						<span style="color: red">*</span>请选择活动支持的物流配送方式：<br>
						<span style="display:none"><!-- 总是选中并且隐藏自提 -->
							<input type="checkbox" checked="checked" name="m_type" value="0" 
							<c:forEach items="${types}" var="ctype" varStatus="status">
								<c:if test="${ctype.type eq 0}">checked</c:if>
							</c:forEach>
							>自提 
						</span>
						
						<input type="checkbox" name="m_type" value="1"  
						<c:forEach items="${types}" var="ctype" varStatus="status">
							<c:if test="${ctype.type eq 1}">checked</c:if>
						</c:forEach>
						>平台配送
						
						<input type="checkbox" name="m_type" value="2"  
						<c:forEach items="${types}" var="ctype" varStatus="status">
							<c:if test="${ctype.type eq 2}">checked</c:if>
						</c:forEach>
						>商家配送
					</td>
				</tr>
			</table>
		</form>
		<c:if test="${empty view }">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="save()">提交</a>
			</gd:btn>
		</c:if>
	</div>
	
</div>
<script type="text/javascript">

	//类目-商铺-商品 切换
	function changeAdd() {
		addShopData = jQuery.parseJSON("[]");
		addProdData = jQuery.parseJSON("[]");
		debugger;
		$("#addprod_span input").attr("disabled", true);
		$("#categoryPanel select").attr("disabled", true);
		$("#addshop_span input").attr("disabled", true);

		if($("#userRuleBuyerProd").attr("checked")) {
			$("#addprod_span input").removeAttr("disabled");
		} else if($("#userRuleBuyerCate").attr("checked")) {
			$("#categoryPanel select").removeAttr("disabled");
		} else if($("#userRuleBuyerShop").attr("checked")) {
			$("#addshop_span input").removeAttr("disabled");
		}
		
	}

	var payChannels = jQuery.parseJSON('${payChannels}');
	
	//为用户预付款设置 0.01-100之间的数
	$('#cost').numberbox({  
	    min:0.01,  
	    max:100,
	    precision:2  
	});
	/* //初始化市场
	var urlMarket =CONTEXT+'gdActActivity/marketPairs';
	$.ajax({
	    type: "GET",
	    url: urlMarket,
	    data: null,
	    dataType: "json",
	    contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	    success: function(data){
    	       $.each(data,function(x,y){
    	    	   console.debug(data[0].keyString+"--"+data[0].valueString);
    	    	   
    	    	   $("#market_type").append("<option value='"+y.keyString+"'>"+y.valueString+"</option>");
    	       });

             }
	}); */
	
	$("body").delegate("#showShopWin","click",function(){
		$('#shopDialog').dialog(
		{
			'title' : '活动信息',
			'width' : 900,
			'height' : 300,
			'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/0',//0-商铺,1-买家,2-商品, 与详细信息列表页面选项卡位置对应
			onLoad : function() {
		          //初始化表单数据
		          
		          initShopRuleList();
		      }
		}).dialog('open');

	});
	
	$("#showProdWin").click(function(){
/* 		var activityDialog = $('#activityDialog').dialog({'title':'新增活动', 'width':800, 'height':400, 'href':CONTEXT+'gdDistActivity/toAddDistActivity'});
		activityDialog.dialog('open')
		activityDialog.dialog('close');  */
		$('#shopDialog').dialog(
				{
					'title' : '活动信息',
					'width' : 900,
					'height' : 300,
					'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/2',//0-商铺,1-买家,2-商品, 与详细信息列表页面选项卡位置对应
					onLoad : function() {
				          //初始化表单数据
				          initShopRuleList();
				      }
				}).dialog('open');
	});
/* 	$("body").delegate("#showProdWin","click",function(){
		$('#shopDialog').dialog(
		{
			'title' : '活动信息',
			'width' : 900,
			'height' : 300,
			'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/2',//0-商铺,1-买家,2-商品, 与详细信息列表页面选项卡位置对应
			onLoad : function() {
		          //初始化表单数据
		          initShopRuleList();
		      }
		}).dialog('open');
	}); */

	$("body").delegate("#showBuyerWin", "click", function() {
		
		
		$('#shopDialog').dialog({
			'title' : '买家信息',
			'width' : 900,
			'height' : 300,
			'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/1',//0-商铺,1-买家,2-商品, 与详细信息列表页面选项卡位置对应
			onLoad : function() {
		          //初始化表单数据
		          
		          initShopRuleList();
		      }
		}).dialog('open');
	});

	$(document).ready(function() {
		$("#marketComm").find("input[type=radio]").click(function() {
			$(this).removeAttr('checked');
			var bol = $(this).attr("tag");
			if (bol == "0") {
				$(this).attr('checked', 'checked');
				$("#marketComm").find("input[type=radio]").attr("tag", "0")
				$(this).attr("tag", "1");
			} else {
				$(this).removeAttr('checked');
				$(this).attr("tag", "0");
			}
		});
		
		$("#userRule").find("input[type=radio]").click(function() {
			$(this).removeAttr('checked');
			var bol = $(this).attr("tag");
			if (bol == "0") {
				$(this).attr('checked', 'checked');
				$("#userRule").find("input[type=radio]").attr("tag", "0")
				$(this).attr("tag", "1");
			} else {
				$(this).removeAttr('checked');
				$(this).attr("tag", "0");
			}
		});

		<% //如果不是编辑，则直接初始化补贴规则列表，如果存在编辑，则在初始化编辑补贴信息后 才初始化列表 %>
/* 		<c:if test="${empty activity}">
			initRuleList();
		</c:if> */

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

	function initRuleList() {
		$('#datagrid-subrule-table').datagrid({
			width : 'auto',
			queryParams : null,
			height : 'auto',
			nowrap : true,
			pageSize : 10,
			rownumbers : true,
			pagination : true,
			fitColumns : true,
			fit : true,
			singleSelect : true,
			onLoadSuccess : function() {
				$("#datagrid-subrule-table").datagrid('clearSelections');
			},
			data : subRuleJsonObject.slice(0, 10),
			columns : [ [ {
				field : 'id',
				title : '',
				width : 50
			}, {
				field : 'cardType',
				title : '卡类型',
				width : 200,
				align : 'center',
				formatter:optformatcardType
			}, {
				field : 'busiType',
				title : '业务类型',
				width : 200,
				align : 'center',
				formatter:optformatbusiType
			}, {
				field : 'payChannel',
				title : '支付渠道',
				width : 130,
				align : 'center',
				formatter:optformatpayChannel
			}, {
				field : 'createTimeString',
				title : '卖家刷卡补贴',
				width : 150,
				align : 'center',
				formatter:optformatsub
			}, {
				field : 'state',
				title : '封顶值/单',
				width : 100,
				align : 'center',
				formatter:optformatlimit
			}
			<c:if test="${empty view }">
			, {
				field : 'opt',
				title : '操作',
				width : 200,
				align : 'center',
				formatter : optformatsubrule
			} 
			</c:if>
			] ]
		});
		$('#datagrid-subrule-table').datagrid('hideColumn', 'id');
		$("#datagrid-subrule-table").datagrid("getPager").pagination({
			pageList : [ 10, 20, 30, 50 ]
		});

		var pager = $("#datagrid-subrule-table").datagrid("getPager");

		pager.pagination({
			total : subRuleJsonObject.length,
			onSelectPage : function(pageNo, pageSize) {
				
				var start = (pageNo - 1) * pageSize;
				var end = start + pageSize;
				$("#datagrid-subrule-table").datagrid("loadData",
						subRuleJsonObject.slice(start, end));
				pager.pagination('refresh', {
					total : subRuleJsonObject.length,
					pageNumber : pageNo
				});
			}
		});
	}
	function optformatcardType(value, row, index) {
		if(value==2) {
			return "借记卡";
		} else{
			return "贷记卡";
		}
	}
	
	function optformatpayChannel(value, row, index) {
		var payCode = "";
		if(payChannels.success) {
			$(payChannels.result).each(function(i,v) {
				if(v.payChannelCode==value) {
					payCode = v.payChannelName;
				}
			});
		} else {
			return "渠道error-"+value;
		}
		return payCode;
	}
	
	function optformatbusiType(value, row, index) {
		var busiType1;
		var busiType2;
		
		if(row.busiType1==1) {
			busiType1 = "本行";
		} else{
			busiType1 = "跨行";
		}
		
		if(row.busiType2==0) {
			busiType2 = "所有";
		}else if(row.busiType2==1) {
			busiType2 = "同城";
		} else{
			busiType2 = "异地";
		}
		
		return busiType1 +"-"+ busiType2;
	}
	
	function optformatsub(value, row, index) {
		if(row.sub_radio==1) {
			return row.orderAmt + "元/商品ID";
		} else if(row.sub_radio==2) {
			return row.orderSub + "%";
		} else if(row.sub_radio==3) {
			if(row.sub_lim_radio_id==0) {
				return row.subPerc_id + "%";
			} else {
				return row.subAmt_id + "元/商品ID";
			}
		}
	}
	
	function optformatlimit(value, row, index) {
		
		
		if(row.sub_radio==1) {
			return "";
		} else if(row.sub_radio==2) {
			if(row.orderLimit=="" || row.orderLimit==null) {
				return "";
			}
			return row.orderLimit + "元/商品ID";
		} else if(row.sub_radio==3) {
			if(row.sub_lim_radio_id==0) {
				if(row.subLimit_id=="" || row.subLimit_id==null) {
					return "";
				}
				return row.subLimit_id + "元";
			} else {
				return "";
			}
		}
	}
	
	function optformatsubrule(value, row, index) {
		var options = "<gd:btn btncode='BTNSPGLQBCP04'><a class='operate' href='javascript:;' onclick=editSub('"
				+ index + "')>编辑</a></gd:btn>";
		options += "&nbsp;<gd:btn btncode='BTNSPGLQBCP03'><a class='operate' href='javascript:;' onclick=delSub('"
				+ row.cardType
				+ "','"
				+ row.busiType1
				+ "','"
				+ row.busiType2
				+ "','"
				+ row.payChannel
				+ "') >删除</a></gd:btn>";
		return options;
	}

	function editSub(id) {
		addSub(id);
	}

	function delSub(cardType, busiType1, busiType2, payChannel) {
		clearMore(cardType, busiType1, busiType2, payChannel);

		$("#datagrid-subrule-table").datagrid("loadData", subRuleJsonObject);
	}

	function clearMore(cardType, busiType1, busiType2, payChannel) {
		for (var i = 0; i < subRuleJsonObject.length; i++) {
			if (subRuleJsonObject[i].payChannel == payChannel
					&& subRuleJsonObject[i].busiType1 == busiType1
					&& subRuleJsonObject[i].busiType2 == busiType2
					&& subRuleJsonObject[i].cardType == cardType) {
				subRuleJsonObject.splice(i, 1);
				i--;
			}

		}
	}
	function checkMarket() {
		var shop=$("#market_type").val();
		if(!shop){
			warningMessage('请选择所属市场');
			return;
		}
	}
	
	var changedUserData = false;
	
	function initCategory() {
		cateChange(0);
		if(changedUserData) {
			addShopData = jQuery.parseJSON("[]");
			addProdData = jQuery.parseJSON("[]");
		}

		changedUserData = true;
		
	}
	
	var topCateId = null;
	var secCateId = null;
	var ThirCateId = null;
	var oldMarketId = '${activity.marketId }';
	
	function cateChange(index) {
		var url = "";
		var val;
		if(index!=0) {
			val = $("#cate"+index).val();
			if(val==-1) {
				if(index!=3) {
					$("#cate"+(index+1)).find("option").remove();
					$("#cate"+(index+1)).append("<option value='-1'>请选择</option>");
				}
				if(index!=1) {
					$('#categoryId_add').val($("#cate"+(index-1)).val());
				} else {
					$('#categoryId_add').val(-1);
				}
				
				return;
			}
			$('#categoryId_add').val(val);
			
		}
		if(index==0) {
			url = CONTEXT + 'product/listTopProductCategory/' + $("#market_type").val();
		} else if(index==1) {
			url = CONTEXT+'product/getChildProductCategory/' + val;
		} else if(index==2) {
			url = CONTEXT+'product/getChildProductCategory/' + val;
		}
		
		if(index<3) {
			$.ajax({
	            type: "POST",
	            contentType: "application/json",
	            url: url,
	            data: "{}",
	            dataType: 'json',
	            async: true,
	            success: function(data) {
	            	debugger;
	            	$("#cate"+(index+1)).find("option").remove();
            		$("#cate"+(index+1)).append("<option value='-1'>请选择</option>");
            		
	                for(var i = 0; i < data.length; i++) {
	                	if(index==2&&data[i].categoryId==ThirCateId) {
	                		$("#cate"+(index+1)).append("<option value='"+data[i].categoryId+"' selected>"+data[i].cateName+"</option>");
	                	} else if(index==1&&data[i].categoryId==secCateId) {
	                		$("#cate"+(index+1)).append("<option value='"+data[i].categoryId+"' selected>"+data[i].cateName+"</option>");
	                		cateChange(2);
	                	} else if(index==0&&data[i].categoryId==topCateId) {
	                		$("#cate"+(index+1)).append("<option value='"+data[i].categoryId+"' selected>"+data[i].cateName+"</option>");
	                	} else {
		                	$("#cate"+(index+1)).append("<option value='"+data[i].categoryId+"'>"+data[i].cateName+"</option>");
	                	}
	                }

	        		//如果未0 则表示初始化需要加载二级
	        		if(index==0) {
	        			cateChange(1);
	        		}
	        		
	            }
	        });
		}
	}
</script>
<c:if test="${not empty activity}">
	<script type="text/javascript">
	debugger;
		$("#market_type").attr("disabled","disabled");
		$("#b_type").attr("disabled","disabled");
		
		var partType = jQuery.parseJSON('${typesJson}');
		var activityJson = jQuery.parseJSON('${activityJson}');
		var buyerArray = jQuery.parseJSON('${buyerArray}');
		var solderArray = jQuery.parseJSON('${solderArray}');
		var ruleArray = jQuery.parseJSON('${ruleArray}');
		var solderRule = jQuery.parseJSON('${solderRule}');
		var buyerRules = jQuery.parseJSON('${buyerRules}');
		var partRule = jQuery.parseJSON('${partRule}');
		
	</script>
	<script type="text/javascript" src="${CONTEXT}js/gdActActivity/distribution/initedit.js"></script>
</c:if>
		<c:if test="${!empty view }">
			<script type="text/javascript">
			$("#categoryPanel select").attr("disabled", "disabled");
			</script>
		</c:if>
