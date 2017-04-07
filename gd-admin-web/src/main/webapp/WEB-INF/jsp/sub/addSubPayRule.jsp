<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/zTree/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/zTree/demo.css">
<script type="text/javascript" src="${CONTEXT}/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${CONTEXT}js/sub/addRule.js"></script>
<style type="text/css">

	.mleft{
		font-size:12px;
		text-align: left;
		valign:middle;
		/* background:#ccc;  */
		
	}
	
	.mright{
		font-size:12px;
	    align:left;
	    valign:middle;
	}
#u329_input {
    left: 0px;
    top: 0px;
    width: 120px;
    height: 22px;
    text-decoration: none;
    color: #000000;
}
#cartypeID{
    left: 0px;
    top: 0px;
    width: 120px;
    height: 22px;
    text-decoration: none;
    color: #000000;
}
#u330_input {
    left: 0px;
    top: 0px;
    width: 132px;
    height: 22px;
    font-weight: 400;
    font-style: normal;
    text-decoration: none;
    color: #000000;
}	
.u184 {
    left: 776px;
    top: 935px;
    width: 59px;
    height: 22px;
    color: #000000;
    text-decoration: none;
}
.tableaddr 
  {border-collapse:separate;   border-spacing:15px;   } 
</style>
<form id="addForm" method="post" action="2">
 					<div>
			<table class="tableaddr" >
				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>补贴发放规则</strong>
					</td>
					
				</tr>
							
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>创建新补贴规则:
					</td>
					<td class="mright">
						<input  type="text" id="subRuleName"  name="subPayRule.subRuleName"   value="" required="true" maxlength="20" class="easyui-validatebox"  style="width:150px" missingMessage="必须填写">
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>选择需要补贴的市场:
					</td>
					<td class="mright">
						<select id="u165_input" data-select="1" name="subPayRule.marketId" class="text_sketch">
				 				<c:forEach var="item" items="${mList}">
									<option <c:if test="${marketId eq item.id }">selected</c:if> value="${item.id}">${item.marketName }</option>
								</c:forEach>
					        </select>
					</td>
				</tr>	
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>平台补贴对象:
					</td>
					<td class="mright">
						<select id="u165_inputm" name="subPayRule.memberType" class="text_sketch">
								<option selected value="3">采购商</option>
								<option value="2">农批商</option>
								<option value="1">产地供应商</option>
					     </select>
					</td>
				</tr>	
				
				
							
				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>选择需要进行补贴的产品种类</strong>
					</td>
					<td class="mright">
					</td>
				</tr>							
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>
					</td>
					<td class="mright">
						<ul id="cat-tree" class="ztree" style="float:left">
						</ul>
					</td>
					<td class="mright">
						<ul id="cat-tree-sel" class="ztree" style="float:left">
						</ul>
						<input id="lastTree" name="subPayRule.cateJson" value="" type="hidden">
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>选择需要设置的补贴规则</strong>
					</td>
					<td class="mright">
					</td>
				</tr>					
				<!-- 周期 -->
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>添加补贴周期
					</td>
					<td style="overflow:hidden;">
					<input  type="text"  id="timeStart" name="subPayRule.timeStart" onFocus="WdatePicker({onpicked:function(){timeStart.focus();},dateFmt:'yyyy-MM-dd 00:00:00',minDate:'${sublimit.timeStartStr}',maxDate:'${sublimit.timeEndStr}'})"    onClick="WdatePicker({onpicked:function(){timeStart.focus();},dateFmt:'yyyy-MM-dd 00:00:00',maxDate:'#F{$dp.$D(\'timeEnd\')}'})" maxlength="20" style="width:150px" >
					--
					<input  type="text" id="timeEnd"   name="subPayRule.timeEnd"   onFocus="WdatePicker({onpicked:function(){timeEnd.focus();},dateFmt:'yyyy-MM-dd 00:00:00',minDate:'#F{$dp.$D(\'timeStart\')}',maxDate:'${sublimit.timeEndStr}'})"   onClick="WdatePicker({onpicked:function(){timeEnd.focus();},dateFmt:'yyyy-MM-dd 00:00:00',minDate:'#F{$dp.$D(\'timeStart\')}'})"  maxlength="20"  style="width:150px" >
					</td>
				</tr>	
				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>按交易金额补贴</strong>
					</td>
					<td class="mright">
					</td>
				</tr>	
				
				<tr>
					<td class="mleft"  >
						<input type="radio" name="subPayRule.subType"    value="1"   >添加补贴比例
					</td>
					<td class="mright">
					订单交易成交金额&nbsp;&nbsp;<input  type="text" id="subPercent"  name="subPercent"  onkeyup="clearNoNum(this)"   value="" maxlength="20"   style="width:50px" >&nbsp;%

 					</td>
				</tr>
				
				<tr class="rangcn" >
					<td class="mleft"  >
						<input type="radio" name="subPayRule.subType"    value="4"  >按采购金额区间进行补贴
					</td>
					<td class="mright">
					<input  type="text" id="minOrderAmount0"  name="rList[0].lowerLimit"   value=""  onkeyup="clearNoNum(this)"  maxlength="20"  style="width:100px">&nbsp;元
					--
					<input  type="text" id="orderAmount0"  name="rList[0].upperLimit"   value=""  onkeyup="clearNoNum(this)"  maxlength="20"  style="width:100px">&nbsp;元
 					</td>
 					<td  class="mright">
 					补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="rList[0].subAmount"  onkeyup="clearNoNum(this)"   value="" maxlength="20"   style="width:70px" >&nbsp;
						<select name="rList[0].subUnit" id="u184" class="u184">
								<option selected value="1">元</option>
								<option value="41">/千</option>
								<option value="42">/万</option>
								<option value="43">/十万</option>
								<option value="44">/百万</option>
								<option value="45">/千万</option>
						</select>
 					</td>
				</tr>
				<tr id="rangbn">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<a href="javascript:addRange();"> >>增加新补贴区间</a>
 					</td>
				</tr>										
				
<!-- 交易金额区间-------------------------end -->								

				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>按交易重量补贴</strong>
					</td>
					<td class="mright">
					</td>
				</tr>	
				
				<%-- <tr class="rangmg">
					<td class="mleft"  >
						<input type="radio" name="subPayRule.subType"    value="5"   >门岗目测审查
					</td>
					<td class="mleft" style="float:left;">
					<select id="cartypeID" name="mList[0].carType" class="text_sketch">
						<option  value="0">选择出货车型</option>
				 				<c:forEach var="item" items="${carList}">
									<option value="${item.cwpid}">${item.type }</option>
								</c:forEach>						
					 </select> 						
					&nbsp;&nbsp;
			
					<select id="u329_input" name="mList[0].truck" class="text_sketch">
							<option selected value="0">选择装载量</option>
							<option value="1">明显少量</option>
							<option value="2">低于半车</option>
							<option value="3">大概半车</option>
							<option value="4">满车</option>
					</select> 
					</td>
					<td class="mleft">
 					补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="mList[0].subAmount"   value="" maxlength="20"   style="width:70px" >&nbsp;
						<select name="mList[0].subUnit" id="u184" class="u184">
								<option selected value="1">元</option>
								<option value="51">/元/天/车</option>

						</select>
 					</td>
				</tr> --%>
				
			
				<tr id="rcountbn">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<a href="javascript:addRange2();"> >>增加新补贴区间</a>
 					</td>
				</tr>					
				
				
				<tr class="cu179" >
					<td class="mleft"  >
						<input type="radio" name="subPayRule.subType"    value="3"  >按采购重量区间进行补贴
					</td>
					<td class="mright">
					<input  type="text" id="minOrderAmount0"  name="zList[0].lowerLimit" onkeyup="clearNoNum(this)"  value=""  maxlength="20"  style="width:70px">&nbsp;
					--
					<input  type="text" id="orderAmount0"  name="zList[0].upperLimit" onkeyup="clearNoNum(this)"   value=""  maxlength="20"  style="width:70px">&nbsp;
 					
					<select id="u181r" name="zList[0].unit">
						<option value="1">吨</option>
						<option value="3">公斤</option>
					</select> 					
 					</td>
 					<td  class="mright">
 					补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="zList[0].subAmount" onkeyup="clearNoNum(this)"   value="" maxlength="20"   style="width:70px" >&nbsp;
						<select id="u184r" name="zList[0].subUnit" class="u184">
								<option selected="" value="1">元</option>
								<option value="31">元/吨</option>
								<option value="32">元/公斤</option>
						</select>
						
 					</td>
				</tr>
				<tr id="u172">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<input id="u179" style="" name="subPayRule.subForTon" type="checkbox" value="1">按整数补贴，未满一吨，不计入补贴
 					</td>
				</tr>					
				<tr id="u171">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<a href="javascript:addRangWeight();"> >>增加新补贴区间</a>
 					</td>
				</tr>					
<!-- 重量区间-------------------------end -->				
				
				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>按订单数量补贴</strong>
					</td>
					<td class="mright">
					</td>
				</tr>				
				
				<tr>
					<td class="mleft"  >
						<input type="radio" name="subPayRule.subType"    value="2"  >添加补贴金额
					</td>
					<td class="mright">
					&nbsp;&nbsp;每笔交易补贴&nbsp;&nbsp;<input  type="text" id="subAmount"  name="subPayRule.subAmount"   onkeyup="clearNoNum(this)"  value="" maxlength="20"  style="width:50px" >&nbsp;元
					<!-- <br/><span style="color:red;font-weight:bold">本类型补贴方式，只支持单一类型的支付； --></span>
 					</td>
 					
				</tr>					
<!-- 按订单数量补贴-------------------------end -->	
				
				
				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>按支付方式设定补贴系数</strong>
					</td>
					<td class="mright">
					</td>
				</tr>											

				<tr>
					<td class="mleft"  >
						<span style="color: red;"></span>支付方式补贴系数
					</td>
					<td class="mright">
					POS刷卡&nbsp;&nbsp;<input  type="text" id=""  name="posCoefficient"  onkeyup="clearNoNum(this)"   value="" maxlength="20"   style="width:50px" >&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;余额支付&nbsp;&nbsp;<input  type="text" id=""  onkeyup="clearNoNum(this)"  name="walletCoefficient"   value="" maxlength="20"   style="width:50px" >&nbsp;
 					</td>
					<td class="mright">
					现金支付&nbsp;&nbsp;<input  type="text" id=""  name="cashCoefficient"  onkeyup="clearNoNum(this)"   value="" maxlength="20"   style="width:50px" >&nbsp;
					
 					</td>
 					<td class="mright">
					
					<span style="color:red;font-weight:bold">补贴系数为0时，该类型支付方式不进行补贴；</span>
 					</td> 					 					
				</tr>	
<!-- 按支付方式设定补贴系数-------------------------end -->
			</table>
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="smit();">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:parent.$('#openRoleDiv').window('close')">关闭</a>
	        </div>   			
		
 	</div>	
</form>
 	<script type="text/javascript" >
 	
 	
 	
 	$(document).ready(function() { 	
 		var stime = "${sublimit.timeStartStr}";
 		var etime = "${sublimit.timeEndStr}";
 		if(stime==null||stime==''||etime==null||etime==''){
/*  			$.messager.confirm('提示', '请先建立该市场的系统规则！',function(r){
 				if(r){
 					parent.$('#openRoleDiv').window('close');
 				}else{
 					parent.$('#openRoleDiv').window('close');
 				}
 			}); */
 			alert('请先建立该市场的系统规则！');
 			parent.$('#openRoleDiv').window('close');
 			
 		}
 		
 		initTree($("#u165_input").val());
 		$.fn.zTree.init($("#cat-tree"), setting, zNodes);
 	});
 	/* var oldmarketId; */
 	$("#u165_input").change(function(){
 		window.location.href = CONTEXT + 'subpayrule/addRule/1?marketId=' + $(this).val();
/*  		var oldmarketId = $(this).attr("data-select");
 		var val = $(this).val();
 		if(val!=oldmarketId){
 			initTree(val);
 	 		$.fn.zTree.init($("#cat-tree"), setting, zNodes);
 	 		$(this).attr("data-select",$(this).val())
 		}
 		var zTree = $.fn.zTree.getZTreeObj("cat-tree-sel"),
 			nodes;
		if(zTree!=null){
			nodes = zTree.getNodes();
			for (var i = nodes.length-1; i >= 0; i--) {
			    zTree.removeNode(nodes[i]);
			}	
		}
		bakNodepath = [];  */		
 		
 	}); 	
 	$("body").delegate("#u184r","change",function(){
 		var val = $(this).val(),
 			limitval = $("#u181r").val();
 		if(limitval == 1){
 			if(val == 32){
 				$(this).val("1")
 			}
 		}else if(limitval == 3){
 			if(val == 31){
 				$(this).val("1")
 			}
 		}
 		
 	});
 	$("body").delegate("#u181r","change",function(){
 		var val = $(this).val(),
 			$cu179 = $(".cu179"),
 			$siblings = $(this).parents("tr").siblings(".cu179");
 		if(val == 1){
 			//$cu179.each(function(index){
 				$selects = $(this).parents("tr").find("select");
 				$selects.eq(1).val("31");
 			//});
 			$siblings.find("select").eq(0).val(val);
 			$siblings.find("select").eq(1).val("31");
 		}else if(val == 3){
 			$selects = $(this).parents("tr").find("select");
				$selects.eq(1).val("32");
			//});
			$siblings.find("select").eq(0).val(val);
			$siblings.find("select").eq(1).val("32");
 		}
 		
 	});
 	
 	$("body").delegate(".row-del","click",function(){
 		var parentCls = $(this).parents("tr").attr("class"),
 			siblip = $(this).parents("tr").siblings("."+parentCls),
 			nextp = $(this).parents("tr").next(),
 			prep = $(this).parents("tr").prev();
 		$(this).parents("tr").remove();
 		if(parentCls == 'cu179'){
 			siblip.each(function(index){
 	 			var $self = $(this);
 	 			var $inputs = $self.find("input:text");
 	 			var $inputsels = $self.find("select");
 	 			$inputs.eq(0).attr({"name":"zList["+index+"].lowerLimit"});
 	 			$inputs.eq(1).attr({"name":"zList["+index+"].upperLimit"});
 	 			$inputs.eq(2).attr({"name":"zList["+index+"].subAmount"});
 	 			$inputsels.eq(0).attr({"name":"zList["+index+"].subUnit"});
 	 		});
 		}else if(parentCls == 'rangcn'){
 			siblip.each(function(index){
 	 			var $self = $(this);
 	 			var $inputs = $self.find("input:text");
 	 			var $inputsels = $self.find("select");
 	 			$inputs.eq(0).attr({"name":"rList["+index+"].lowerLimit"});
 	 			$inputs.eq(1).attr({"name":"rList["+index+"].upperLimit"});
 	 			$inputs.eq(2).attr({"name":"rList["+index+"].subAmount"});
 	 			$inputsels.eq(0).attr({"name":"rList["+index+"].subUnit"});
 	 		});
 		}else if(parentCls == 'rangmg'){
 			siblip.each(function(index){
 	 			var $self = $(this);
 	 			var $inputs = $self.find("select");
 	 			var $inputtexts = $self.find("input:text");
 	 			$inputs.eq(0).attr({"name":"mList["+index+"].carType"});
 	 			$inputs.eq(1).attr({"name":"mList["+index+"].truck"});
 	 			$inputs.eq(2).attr({"name":"mList["+index+"].subUnit"});
 	 			$inputtexts.eq(0).attr({"name":"mList["+index+"].subAmount"});
 	 		});
 		}
 		
 		nextp.find("input:text").eq(0).val(prep.find("input:text").eq(1).val());
 	});
 	
	</script>