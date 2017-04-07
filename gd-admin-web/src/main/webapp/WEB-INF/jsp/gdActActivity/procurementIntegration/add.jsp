<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/procurementIntegration/add.js"></script>
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
				    <input type="hidden" id="hid_view" name="id" value="${view}" />
					<td class="mleft"><span style="color: red">*</span>活动名称：</td>
					<td class="mright"><input type="text" id="name" name="name" maxlength="30"
						value="${activity.name}" required="true" class="easyui-validatebox" validType="validateActName"
						style="width: 150px"/></td>
				</tr>
				<tr>
					<td class="mleft"><span style="color: red">*</span>活动类型：</td>
					<td class="mright">
						<input type="hidden" name="type" value="6" />
					<select name="type" id="b_type"  required="true" class="easyui-validatebox" disabled
						style="width: 150px">
							<option value="6">采购积分</option>
							
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
				   
				</tr>
				
				
			</table>
		</form>
		<c:if test="${empty view }">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="nextStepCheck('basicRule',1, checkActTime)">下一步 </a>
				<c:if test="${not empty activity}">
			<gd:btn btncode='BTNHYGLHYLB11'>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="save()">提交</a>
			</gd:btn>
		</c:if>
		</c:if>
	
	</div>
	
		
	<div title="商品用户" style="margin-left: 10px;">
		<form id="userRule" method="post" action="#">
			<table style="width: 700px;" id="tb_userRule">
				<tr>
					<td><span style="color: red">*</span><span style="font-size: 15px;">活动所属市场：</span></td>
					<td>
					<c:if test="${not empty activity}">
					<input type="hidden" value="${activity.marketId }" name="market_type" />
					</c:if>
					<select name="market_type"  onchange="initCategory();" id="market_type" required="true" labelPosition="top" class="easyui-validatebox"
						style="width: 150px">
							<option value="">-请选择-</option>
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
					<td>
					</td>
				</tr>
				
				<tr>
					<td><input type="checkbox" id="buyerBox0" name="buyerBox0" onchange="buyerBoxChange(this,0)" value="0"/><span>指定买家1：</span></td>
					<td>
					 全部用户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    积分倍率：<input type="text" id="integralRate" name="integralRate" disabled="disabled" />
					<span style="font-size: large;"></span></td>
				</tr>
					
			</table>
	            <c:if test="${empty view }">
					<div style="padding: 8px;">
						<a href="javascript:addUserRule('tb_userRule');">&gt;&gt;指定更多的活动积分参与对象</a>
					</div>
					</c:if>
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
<div title="积分规则">
		<form id="marketComm" method="post" action="#">
			<div style="margin: 10px;">
				<h1>买家交易积分设置<input type="hidden" value="0" name="hid_buyer_id" id="hid_id" /></h1>
				
				<div id="buyerLayer" style='border: solid 1px #E6DDDD; width: 740px;'>
					<table id="buyer_tb_comm">					
				         <tr>
				         <td colspan="2" style=" padding: 5px;">
				           <h> 订单实付金额:</h>
				         </td>
				         </tr>
				         <tr>
				         <td colspan="2" style=" padding: 5px;">
				         <input type="checkbox" id="minIntegralBox" name="minIntegralBox" value="1"/>
				               首单最低积分奖励:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				         <input type="text" style="width: 60px;" id="minIntegral" name="minIntegral" value="" disabled="disabled"/> 
				         </td>
				         </tr>
						<tr id="template_id" value="0">
							<td colspan="2" style=" padding: 5px;">
								<div style="margin-left:3px; border: solid 1px #E6DDDD;">
									<input type="text" id="startAmt" name="buyerStartAmt_id" style="width: 60px;"/>&nbsp;元~&nbsp;<input id="endAmt" name="buyerEndAmt_id" type="text"  style="width: 60px;"/>元 ,获得积分值为
								<input id="integral" name="integral_id" type="text" style="width: 60px;"   /> 
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
					<input type="checkbox" id="dayIntegralBox" name="dayIntegralBox" value="1"/>当日最高累计积分:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text" style="width: 60px;" id="dayMaxIntegral" disabled="disabled" name="dayMaxIntegral" value=""/>&nbsp;&nbsp;
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
			'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/2?inedx=1',
			onLoad : function() {
		          //初始化表单数据
		          
		          initShopRuleList();
		          
		      }
		}).dialog('open');

	});

	function showAddBuyer(index) {
		$('#shopDialog').dialog({
			'title' : '买家信息',
			'width' : 900,
			'height' : 300,
			'href' : CONTEXT + 'gdProcurementIntegration/gdActActivity_shop_buyerJF/1?index='+index,
			onLoad : function() {
		          //初始化表单数据
		     for(var i=0;i<addBuyerData.length;i++){
			  if(addBuyerData[i]["addBuyerData"+index]){
				buyerData=addBuyerData[i]["addBuyerData"+index];
				initShopRuleList(buyerData,index);
 			}
			
		}
		          
		      }
		}).dialog('open');
	}

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
	
	var changedUserData = false;
	

	var oldMarketId = '${activity.marketId }';
	
	
</script>
<c:if test="${not empty activity}">
	<script type="text/javascript">
		$("#market_type").attr("disabled","disabled");
		$("#b_type").attr("disabled","disabled");
		
		var activityJson = jQuery.parseJSON('${activityJson}');
		var buyerArray = jQuery.parseJSON('${buyerArray}');
		var solderArray = jQuery.parseJSON('${solderArray}');
		var ruleArray = jQuery.parseJSON('${ruleArray}');
		var solderRule = jQuery.parseJSON('${solderRule}');
		var buyerRules = jQuery.parseJSON('${buyerRules}');
		
	</script>
	<script type="text/javascript" src="${CONTEXT}js/gdActActivity/procurementIntegration/initedit.js"></script>
</c:if>