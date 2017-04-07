<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<script type="text/javascript">
	var view = "${view}";
</script>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/marketCommission/add.js"></script>
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
					<td class="mleft"><span style="color: red">*</span>活动名称：</td>
					<td class="mright"><input type="text" id="name" name="name" maxlength="30"
						value="${activity.name}" required="true" class="easyui-validatebox" validType="validateActName"
						style="width: 150px"/></td>
				</tr>
				<tr>
					<td class="mleft"><span style="color: red">*</span>活动类型：</td>
					<td class="mright">
						<input type="hidden" name="type" value="2" />
					<select name="type" id="b_type"  required="true" class="easyui-validatebox" disabled
						style="width: 150px">
							<option value="2">市场佣金</option>
							
					</select></td>
					<script>
					var choice=$("select[id='b_type'] option")["${activity.activityType}"];
					if(choice){
						choice.selected=true;
					}
					</script>
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
					<tr>
				    <input type="hidden" id="activityId" name="id" value="${activity.id}" />
                     <c:if test="${activity.isReverse ==0 || activity.isReverse ==null }">
					<td class="mleft" colspan=3><input type="checkbox" id="isReverse_cb" name="isReverse"   onclick="isReverseCk()"/>可收取直接刷卡，无商品的订单的佣金</td>
					</c:if>
					   <c:if test="${activity.isReverse ==1 }">
					<td class="mleft" colspan=3><input type="checkbox" id="isReverse_cb" name="isReverse"  checked="checked"  onclick="isReverseCk()"/>可收取直接刷卡，无商品的订单的佣金</td>
					</c:if>					
				</tr>
				
				
			</table>
		</form>
		<c:if test="${empty view }">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStepCheck('basicRule',1, checkActTime)">下一步 </a>
		</c:if>
		<c:if test="${not empty activity}">
			<c:if test="${empty view }">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="save()">提交</a>
			</gd:btn>
			</c:if>
		</c:if>
	</div>
	
	<div title="商品用户" style="margin-left: 10px;">
		<form id="userRule" method="post" action="#">
			<table style="width: 700px;">
				<tr>
					<td><span style="color: red">*</span><span style="font-size: 15px;">所属市场：</span></td>
					<td>
					<c:if test="${not empty activity}">
					<input type="hidden" value="${activity.marketId }" name="market_type" />
					</c:if>
					<select name="market_type"  onchange="initCategory();" id="market_type" required="true" labelPosition="top" class="easyui-validatebox"
						style="width: 150px">
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
						<a id="showBuyerWin" href="javascript:void(0)" >活动买家信息</a></span>
						&nbsp;
						<span style="font-size: large;"><a id="showProdWin" href="javascript:void(0)">活动商品信息</a></span></td>
					</td>
				</tr>
				<tr style='display: none;'>
				    <input name="id" type="hidden" value="${activity.activity_participation_rule_id}">
					<td><span style="color: red">*</span>订单金额：</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="min_cost" name="min_cost" class="easyui-validatebox" value="${activity.minAmount}"
						style="width: 50px;" validType="validate_mincost"/>元~<input id="max_cost" name="max_cost"  value="${activity.maxAmount}" validType="validate_maxcost"
						class="easyui-validatebox" style="width: 50px;" />元，参加本次物流配送活动</td>
						<input type="hidden" id="refer_id" name="refer_id" value="1,2"/>
				</tr>
				<c:if test="${empty view }">
				<tr>
					<td><span>指定买家：</span></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" onclick="addBuyer()" value="添加买家" id="tjmj" /></td>
				</tr>
				<tr>
					<td><input type="radio" name="userRuleBuyer" id="userRuleBuyerProd" value="4" tag="0" onclick="changeAdd();"/><span>添加活动商品：</span></td>
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
						<input type="radio" name="userRuleBuyer" id="userRuleBuyerCate" tag="0" value="3" onclick="changeAdd();" />&nbsp;
						指定商品类目：
					</td>
					<td>
					<span id="categoryPanel">
						<select id="cate1" onchange="cateChange(1,1)" onclick="checkMarket();" disabled="disabled" >
							<option value="-1">-请选择-</option>
						</select>
						<select id="cate2" onchange="cateChange(2,1)" disabled="disabled" >
							<option value="-1">-请选择-</option>
						</select>
						<select id="cate3" onchange="cateChange(3,1)" disabled="disabled" >
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
			</table>
		</form>
			<c:if test="${empty view }">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStep('userRule',0)">上一步 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStepCheck('userRule',2, checkProdUser)">下一步 </a>
			<c:if test="${not empty activity}">
				<gd:btn btncode='BTNHYGLHYLB11'>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						data-options="iconCls:'icon-save'" onclick="save()">提交</a>
				</gd:btn>
			</c:if> 
		</c:if>
	</div>
	
	<div title="市场佣金">
		<form id="marketComm" method="post" action="#">
			<div style="margin: 10px;">
				<h1>市场收取佣金设置</h1>
				<h2>买家佣金设置</h2>
				<div id="buyerLayer" style='border: solid 1px #E6DDDD; width: 740px;'>
					<table id="buyer_tb_comm">
						
					   <tr>
							<td width="202"><input type="radio" name="buyer" value="0" id="prodRadio" tag="0" />按订单中的商品ID收取 ：</td>
							<td>
								<input type="text" name="buyerOrderProd" id="buyerOrderProd"/>&nbsp;元/商品ID
							</td>
						</tr>
						<tr>
							<td colspan="2" width="118">
							
							<input type="radio" name="buyer" value="3" tag="0" id="orderAmtCertRadio" />
							<input type="hidden" value="0" name="hid_buyer_id" id="hid_id" />
								按商品小计金额所属区间收取：
							</td>
						</tr>
						<tr id="template_id" value="0">
							<td colspan="2" style=" padding: 5px;">
								<div style="margin-left:3px; border: solid 1px #E6DDDD;">
									<!-- 区间 -->
									<input type="text" id="startAmt" name="buyerStartAmt_id" style="width: 60px;" />&nbsp;元~&nbsp;
									<input id="endAmt" name="buyerEndAmt_id" type="text"  style="width: 60px;" />元&nbsp;&nbsp;&nbsp;&nbsp;
									
									<!-- 按比例 -->
									<input type="radio" value="0" name="buyer_order_id" checked id="orderCertRadio" tag="0" onclick="disabledTrue(this)"/>
									按比例&nbsp;<input id="propAmt" name="buyerPropAmt_id" type="text" maxlength="5" size="5" width="3"/>%，
									佣金上限<input id="limitAmt" name="buyerLimitAmt_id" type="text" style="width: 60px;"/>元/商品ID
									&nbsp;&nbsp;&nbsp;
									
									<!-- 按固定金额 -->
									<input type="radio" value="1" name="buyer_order_id" id="orderFixedRadio" tag="0" onclick="disabledTrue(this)"/>按固定金额
									<input id="fixedAmt" name="buyerFixedAmt_id" type="text" style="width: 60px;" />元/商品ID
									
									<!-- 编辑状态下显示删除该区间设置 -->
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
					<div style="padding: 8px;">
						保底佣金:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" style="width: 60px;" id="buyerAmt" name="buyerAmt" />&nbsp;&nbsp;元/单
					</div>
				</div>
				<h2>卖家佣金设置</h2>
				<div id="solderLayer" style='border: solid 1px #E6DDDD; width: 740px;'>
					<table id="solder_tb_comm">
						<tr>
							<td colspan="2">
                                <b> 正向制单-卖家佣金设置：</b>
 							</td>
						</tr>
					
						<tr>
							<td colspan="2">
							<input type="hidden" name="solder" value="3" id="orderAmtCertRadio" tag="0" />
							<input type="hidden" value="0" name="hid_solder_id" id="hid_id" />
							按商品小计金额所属区间收取：</td>
						</tr>
						<tr id="template_id" value="0">
							<td colspan="2" style=" padding: 5px;">
								<div style="margin-left:3px; border: solid 1px #E6DDDD;">
								
								    <input type="hidden" id="isReverse" name="solderStartAmt_isReverse_id" value="0">
									<input type="text" id="startAmt" name="solderStartAmt_id" style="width: 60px;"/>&nbsp;元~&nbsp;<input id="endAmt" name="solderEndAmt_id" type="text" style="width: 60px;" />元&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" value="0" name="solder_order_id" checked id="orderCertRadio" tag="0"  onclick="disabledTrue(this)"/>
									按比例&nbsp;<input type="text" id="propAmt" name="solderPropAmt_id" maxlength="5" size="5" width="3" />%，
									佣金上限<input id="limitAmt"  name="solderLimitAmt_id" type="text" style="width: 60px;" />元/商品ID
									&nbsp;&nbsp;&nbsp;
									<input type="radio" value="1" name="solder_order_id" id="orderFixedRadio" tag="0"  onclick="disabledTrue(this)"/>按固定金额<input type="text" id="fixedAmt" name="solderFixedAmt_id"  style="width: 60px;" disabled="disabled"/>元/商品ID
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
					<div id="nxDiv" style="display: none">
					<table id="reverse_tb_comm">
						<tr>
							<td colspan="2">
                                <b> 逆向制单-卖家佣金设置：</b>
 							</td>
						</tr>
					
						<tr>
						<td colspan="2">
						<input type="hidden" value="0" name="reverse_hid_solder_id" id="hid_id" />
						按商品小计金额所属区间收取：</td>
						</tr>
						<tr id="template_id" value="0">
							<td colspan="2" style=" padding: 5px;">
								<div style="margin-left:3px; border: solid 1px #E6DDDD;">
								    <input type="hidden" id="isReverse" name="solderStartAmt1_isReverse_id" value="1">
									<input type="text" id="startAmt" name="solderStartAmt1_id" style="width: 60px;"/>&nbsp;元~&nbsp;<input id="endAmt" name="solderEndAmt1_id" type="text" style="width: 60px;" />元&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" value="0" name="solder_order1_id" checked id="orderCertRadio" tag="0"  onclick="disabledTrue(this)"/>
									按比例&nbsp;<input type="text" id="propAmt" name="solderPropAmt1_id" maxlength="5" size="5" width="3" />%，
									佣金上限<input id="limitAmt"  name="solderLimitAmt1_id" type="text" style="width: 60px;" />元/单
									&nbsp;&nbsp;&nbsp;
									<input type="radio" value="1" name="solder_order1_id" id="orderFixedRadio" tag="0" onclick="disabledTrue(this)" />按固定金额<input type="text" id="fixedAmt" name="solderFixedAmt1_id"  style="width: 60px;" disabled="disabled"/>元
									<c:if test="${empty view }">
									<a id="del_a_id" href="javascript:delRul('reverse_tb_comm', _id);" style="display:none;">&gt;&gt;删除</a>
									</c:if>
								</div>
							</td>
						</tr>
					</table>
				
					<c:if test="${empty view }">
					<div style="padding: 8px;">
						<a href="javascript:addMoreRule('reverse_tb_comm');">&gt;&gt;增加更多佣金区间</a>
					</div>
					</c:if>
					
					
				</div>
			
			<div style="padding: 8px;">
					保底佣金:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="width: 60px;" id="sellerAmt" name="sellerAmt" value=""/>&nbsp;&nbsp;元/单
					</div>
					</div>
				</div>
		</form>
		<c:if test="${empty view }">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="nextStep('marketComm',1)">上一步 </a>
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="save()">提交</a>
			</gd:btn>
			
		</c:if>
	</div>
		
	

</div>
<script type="text/javascript">

	function changeAdd() {
		addShopData = jQuery.parseJSON("[]");
		addProdData = jQuery.parseJSON("[]");
		
		$("#addprod_span input").attr("disabled", true);
		$("#categoryPanel select").attr("disabled", true);
		$("#addshop_span input").attr("disabled", true);
		debugger;
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
			'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/0',
			onLoad : function() {
		          //初始化表单数据
		          
		          initShopRuleList();
		      }
		}).dialog('open');

	});
	
	$("body").delegate("#showProdWin","click",function(){
		$('#shopDialog').dialog(
		{
			'title' : '活动信息',
			'width' : 900,
			'height' : 300,
			'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/2',
			onLoad : function() {
		          //初始化表单数据
		          
		          initShopRuleList();
		          
		      }
		}).dialog('open');

	});

	$("body").delegate("#showBuyerWin", "click", function() {
		$('#shopDialog').dialog({
			'title' : '买家信息',
			'width' : 900,
			'height' : 300,
			'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/1',
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
	
	function checkMarket() {
		var shop=$("#market_type").val();
		if(!shop){
			warningMessage('请选择所属市场');
			return;
		}
	}
	
	function initCategory() {
		
		var marketId = $("#market_type").val();
		if(marketId != "")
		{
			//类目下拉框重新初始化
			cateChange(0);
			
			//如果已经选择商铺和商品数据，则清空
			if(addShopData.length > 0 || addProdData.length > 0)
			{
				//提示是否清空数据
				warningMessage("更改市场，相关数据将全部更新.");
				//清空选择的数据
				addShopData = jQuery.parseJSON("[]");
				addProdData = jQuery.parseJSON("[]");
			}
		}
	}
	
	var topCateId = null;
	var secCateId = null;
	var ThirCateId = null;
	var oldMarketId = '${activity.marketId }';
	
var oldCateId = null;
	
	function cateChange(index) {
		debugger;
		var url = "";
		var val;
		if(index!=0) {
			val = $("#cate"+index).val();
			//将当前类目的下一级、下下级类目清空
			for(var i=index;i<3;i++)
			{
				$("#cate"+(i+1)).find("option").remove();
				$("#cate"+(i+1)).append("<option value='-1'>请选择</option>");
			}
			
			if(val==-1) {
				if(index!=1) {
					$('#categoryId_add').val($("#cate"+(index-1)).val());
				} else {
					$('#categoryId_add').val(-1);
				}
				
				return;
			}
			
			
			
			$('#categoryId_add').val(val);
			
			if(index==1) {
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
	<script type="text/javascript" src="${CONTEXT}js/gdActActivity/marketCommission/initedit.js"></script>
</c:if>