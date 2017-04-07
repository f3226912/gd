<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
    width: 80px;
    height: 22px;
    color: #000000;
    text-decoration: none;
}
#cartypeID{
    left: 0px;
    top: 0px;
    width: 120px;
    height: 22px;
    text-decoration: none;
    color: #000000;
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
					
					<td class="mright"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="copyRule(${rule.ruleId});">复制本条补贴方案</a></td>
				</tr>
							
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>创建新补贴规则:
					</td>
					<td class="mright">
						<input  type="text" id="subRuleName"  name="${rule.subRuleName}"   value="${rule.subRuleName}" required="true" maxlength="20" class="easyui-validatebox"  style="width:150px" missingMessage="必须填写">
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>选择需要补贴的市场:
					</td>
					<td class="mright">
						<select id="u165_input" name="subPayRule.marketId" class="text_sketch">
				 				<c:forEach var="item" items="${mList}">
									<option <c:if test="${item.id eq rule.marketId }">selected</c:if> value="${item.id}">${item.marketName }</option>
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
								<option <c:if test="${rule.memberType eq 3 }">selected</c:if> value="3">采购商</option>
								<option <c:if test="${rule.memberType eq 2 }">selected</c:if> value="2">农批商</option>
								<option <c:if test="${rule.memberType eq 1 }">selected</c:if> value="1">产地供应商</option>
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
						<ul id="cat-tree-info" class="ztree" style="float:left">
						</ul>
					</td>
					<td class="mright">
						<input id="lastTree-info" name="subPayRule.cateJson"  type="hidden">
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="font-weight:bold"></span><strong>选择需要设置的补贴规则</strong>
					</td>
					<td class="mright">
					</td>
				</tr>	

				<tr>
					<td class="mleft"  >
						<span style="color: red;"></span>支付方式补贴系数
					</td>
					<td class="mright">
					POS刷卡&nbsp;&nbsp;<input  type="text" id=""  name="posCoefficient" <fmt:formatNumber value="${rule.posCoefficient/1000 }" var="pos"  pattern="0.0#"/>  value="${pos }" maxlength="20"   style="width:50px" >&nbsp;

 					</td>
					<td class="mright">
					余额支付&nbsp;&nbsp;<input  type="text" id=""  name="walletCoefficient" <fmt:formatNumber value="${rule.walletCoefficient/1000 }" var="wallet"  pattern="0.0#"/>  value="${wallet }" maxlength="20"   style="width:50px" >&nbsp;

 					</td>
					<td class="mright">
					现金支付&nbsp;&nbsp;<input  type="text" id=""  name="cashCoefficient" <fmt:formatNumber value="${rule.cashCoefficient/1000 }" var="cash" pattern="0.0#"/>  value="${cash }" maxlength="20"   style="width:50px" >&nbsp;

 					</td> 					 					
				</tr>	
								
				<!-- 周期 -->
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>添加补贴周期
					</td>
					<td style="overflow:hidden;">
					
					<input  type="text"  id="timeStart" name="subPayRule.timeStart" <fmt:formatDate value="${rule.timeStart}" var="sdate" pattern="yyyy-MM-dd HH:mm:ss" /> value="${sdate }"  maxlength="20" style="width:150px" >
					--
					<input  type="text" id="timeEnd"   name="subPayRule.timeEnd"  <fmt:formatDate value="${rule.timeEnd}" var="edate" pattern="yyyy-MM-dd  HH:mm:ss" /> value="${edate }"    maxlength="20"  style="width:150px" >
					</td>
				</tr>			
					
	<c:if test="${rule.subType eq 1 }">			
				<tr>
					<td class="mleft"  >
						<input type="radio" name="subPayRule.subType" checked  value="1"   >添加补贴比例
					</td>
					<td class="mright">
					订单交易成交金额&nbsp;&nbsp;<input  type="text" id="subPercent"  name="subPayRule.subPercent" <fmt:formatNumber value="${rule.subPercent/1000 }" var="subPercent" pattern="0.0#"/>   value="${subPercent }" maxlength="20"   style="width:50px" >&nbsp;%

 					</td>
				</tr>					
	</c:if>			
	<c:if test="${rule.subType eq 2 }">					
				<tr>
					<td class="mleft"  >
						<input type="radio" name="subPayRule.subType" checked   value="2"  >添加补贴金额
					</td>
					<td class="mright">
					&nbsp;&nbsp;每笔订单补贴&nbsp;&nbsp;<input  type="text" id="subAmount"  name="subPayRule.subAmount"   value="${rule.subAmount }" maxlength="20"  style="width:50px" >&nbsp;元

 					</td>
				</tr>					
</c:if>	
<c:if test="${rule.subType eq 3 }">
	<c:forEach var="item" items="${rule.ranges}" varStatus="status">
				<tr class="cu179" >
					<td class="mleft"  >
						<c:if test="${status.index==0 }"><input type="radio" name="subPayRule.subType" checked   value="3"  >按采购重量区间进行补贴</c:if>
					</td>
					<td class="mright">
					<input  type="text" id="minOrderAmount0"  name="zList[0].lowerLimit"   value="${item.lowerLimit }"  maxlength="20"  style="width:70px">&nbsp;
					--
					<input  type="text" id="orderAmount0"  name="zList[0].upperLimit"   value="${item.upperLimit }"  maxlength="20"  style="width:70px">&nbsp;
 					
					<select id="u181r" name="zList[0].unit">
						<option <c:if test="${item.unit==1 }">selected </c:if> value="1">吨</option>
						<option <c:if test="${item.unit==3 }">selected </c:if> value="3">公斤</option>
					</select> 					
 					</td>
 					<td  class="mright">
 					补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="zList[0].subAmount"   value="${item.subAmount }" maxlength="20"   style="width:70px" >&nbsp;
						<select id="u184r" name="zList[0].subUnit" class="u184">
								<option <c:if test="${item.subUnit==1 }">selected </c:if> value="1">元</option>
								<option <c:if test="${item.subUnit==31 }">selected </c:if> value="31">元/吨</option>
								<option <c:if test="${item.subUnit==32 }">selected </c:if> value="32">元/公斤</option>
						</select>
						
 					</td>
				</tr>
		</c:forEach>	
				<tr id="u172">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<input id="u179" style="" <c:if test="${rule.subForTon eq 1 }">checked</c:if> name="subPayRule.subForTon" type="checkbox" value="1">按整吨补贴，未满一吨，不计入补贴
 					</td>
				</tr>				
</c:if>					

							
<!-- 				<tr id="u171">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<a href="javascript:addRangWeight();"> >>增加新补贴区间</a>
 					</td>
				</tr>	 -->

<c:if test="${rule.subType eq 4 }">
	<c:forEach var="item" items="${rule.ranges}" varStatus="status">				
				<tr class="rangcn" >
					<td class="mleft"  >
						<c:if test="${status.index==0 }"><input type="radio" name="subPayRule.subType" checked    value="4"  >按采购金额区间进行补贴</c:if>
					</td>
					<td class="mright">
					<input  type="text" id="minOrderAmount0"  name="rList[0].lowerLimit"   value="${item.lowerLimit }"  maxlength="20"  style="width:100px">&nbsp;元
					--
					<input  type="text" id="orderAmount0"  name="rList[0].upperLimit"   value="${item.upperLimit }"  maxlength="20"  style="width:100px">&nbsp;元
 					</td>
 					<td  class="mright">
 					补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="rList[0].subAmount"   value="${item.subAmount }" maxlength="20"   style="width:70px" >&nbsp;
						<select name="rList[0].subUnit" id="u184" class="u184">
								<option  <c:if test="${item.subUnit==1 }">selected </c:if>  value="1">元</option>
								<option  <c:if test="${item.subUnit==41 }">selected </c:if>  value="41">/千</option>
								<option  <c:if test="${item.subUnit==42 }">selected </c:if>  value="42">/万</option>
								<option <c:if test="${item.subUnit==43 }">selected </c:if>  value="43">/十万</option>
								<option <c:if test="${item.subUnit==44 }">selected </c:if>  value="44">/百万</option>
								<option <c:if test="${item.subUnit==45 }">selected </c:if>  value="45">/千万</option>
						</select>
 					</td>
				</tr>
		</c:forEach>		
</c:if>				
<!-- 				<tr id="rangbn">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<a href="javascript:addRange();"> >>增加新补贴区间</a>
 					</td>
				</tr>	 -->			
				
<c:if test="${rule.subType eq 5 }">
	<c:forEach var="item" items="${rule.ranges}" varStatus="status">					
				<tr class="rangmg">
					<td class="mleft"  >
						<c:if test="${status.index eq 0 }"><input type="radio" name="subPayRule.subType"  checked   value="5"   >门岗目测审查</c:if>
					</td>
					<td class="mleft" style="float:left;">
					<select id="cartypeID" name="mList[0].carType" class="text_sketch">
				 				<c:forEach var="caritem" items="${carList}">
									<option <c:if test="${item.carType eq caritem.cwpid }">selected </c:if> value="${caritem.cwpid}">${caritem.type }</option>
								</c:forEach>						
					 </select> 						
					&nbsp;&nbsp;
			
					<select id="u329_input" name="mList[0].truck" class="text_sketch">
							<option <c:if test="${item.truck==0 }">selected </c:if>  value="0">选择装载量</option>
							<option  <c:if test="${item.truck==1 }">selected </c:if>  value="1">明显少量</option>
							<option  <c:if test="${item.truck==2 }">selected </c:if>  value="2">低于半车</option>
							<option <c:if test="${item.truck==3 }">selected </c:if>  value="3">大概半车</option>
							<option <c:if test="${item.truck==4 }">selected </c:if>  value="4">满车</option>
					</select> 
					</td>
					<td class="mleft">
 					补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="mList[0].subAmount"   value="${item.subAmount }" maxlength="20"   style="width:70px" >&nbsp;
						<select name="mList[0].subUnit" id="u184" class="u184">
								<option  <c:if test="${item.subUnit==1 }">selected </c:if>  value="1">元</option>
								<option  <c:if test="${item.subUnit==51 }">selected </c:if>  value="51">/元/天/车</option>
						</select>
 					</td>
				</tr>
		</c:forEach>		
</c:if>					
			
<!-- 				<tr id="rcountbn">
					<td class="mleft"  >
					</td>
					<td class="mright">
					<a href="javascript:addRange2();"> >>增加新补贴区间</a>
 					</td>
		 -->		</tr>	

			</table>
			
		
<!-- 			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="smit()">保存</a>
	            <a href="javascript:void(0)" style="margin-left:20px;" class="easyui-linkbutton" iconCls="icon-cancel" >重置</a>
	        </div> -->
 	</div>	
</form>
 	<script type="text/javascript" >
 		 function addRange(){
 			 var len = $(".rangcn").length;
 			 //alert(len);
 			 var temphtml='<tr class="rangcn" ><td class="mleft"  ></td>';
 				temphtml += '<td class="mright"><input  type="text" id="minOrderAmount'+len+'"  name="rList['+len+'].lowerLimit"   value="" required="true" maxlength="20" class="easyui-validatebox"  style="width:100px" missingMessage="必须填写">&nbsp;元';
 				temphtml += '--';
 				temphtml += '<input  type="text" id="orderAmount'+len+'"  name="rList['+len+'].upperLimit"   value="" required="true" maxlength="20" class="easyui-validatebox"  style="width:100px" missingMessage="必须填写">&nbsp;元';
 				temphtml += '</td>';
 				temphtml += '<td  class="mright">';
 				temphtml += '补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount'+len+'"  name="rList['+len+'].subAmount"   value="" required="true" maxlength="20"   style="width:70px" missingMessage="必须填写">&nbsp;';
 				temphtml += '<select name="rList['+len+'].subUnit" id="u184" class="u184">';
 				temphtml += '<option selected value="1">元</option>';
 				temphtml += '<option value="41">/千</option>';
 				temphtml += '<option value="42">/万</option>';
 				temphtml += '<option value="43">/十万</option>';
 				temphtml += '<option value="44">/百万</option>';
 				temphtml += '<option value="45">/千万</option>';
 				temphtml += '</select>';
 				temphtml += '</td>';
 				temphtml += '</tr>';
 			 $("#rangbn").before(temphtml);
 			 
 		 }
 		 
 		 function addRange2(){
 			var len = $(".rangmg").length;
 			 var html = '<tr class="rangmg"><td class="mleft"  >';
				html +='</td>';
				html +='<td class="mleft" style="float:left;">';
				html +='<select id="u329_input" name="mList['+len+'].carType" class="text_sketch">';
				html +='<option selected="" value="0">选择出货车型</option>';
				html +='<option value="1">二轮摩托车</option>';
				html +='<option value="2">非人力三轮车</option>';
				html +='<option value="3">微型车</option>';
				html +='<option value="4">中型车</option>';
				html +='<option value="5">大型车</option>';
				html +='</select>&nbsp;&nbsp;';
				html +='<select id="u329_input" name="mList['+len+'].truck" class="text_sketch">';
				html +='<option selected="" value="0">选择装载量</option>';
				html +='<option value="1">明显少量</option>';
				html +='<option value="2">低于半车</option>';
				html +='<option value="3">大概半车</option>';
				html +='<option value="4">满车</option>';
				html +='</select></td>';
				html +='<td class="mleft">补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="mList['+len+'].subAmount"   value="" maxlength="20"   style="width:70px" >&nbsp;';
				html +='<select name="mList['+len+'].subUnit" id="u184" class="u184">';
				html +='<option selected value="1">元</option>';
					html +='<option value="51">/元/天/车</option>';
				html +='</select>';
				html +='</td></tr>';
				
 			$("#rcountbn").before(html);
 		 }
 		 
 		 function addRangWeight(){
 			var len = $(".cu179").length;
 			var sel = $("#u181r").html();
 			var temp = '<tr class="cu179" >';
				temp += '<td class="mleft"  >';
				temp += '<td class="mright">';
				temp += '<input  type="text" id="minOrderAmount0"  name="zList['+len+'].lowerLimit"   value=""  maxlength="20"  style="width:70px">&nbsp;';
				temp += '--';
				temp += '<input  type="text" id="orderAmount0"  name="zList['+len+'].upperLimit"   value=""  maxlength="20"  style="width:70px">&nbsp;&nbsp;';
				temp += '<select  name="zList['+len+'].unit">';
				temp += sel;
				temp += '</select>';			
				temp += '</td>';
				temp += '<td  class="mright">';
				temp += '补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="zList['+len+'].subAmount"   value="" maxlength="20"   style="width:70px" >&nbsp;';
				temp += '<select id="u184" name="zList['+len+'].subUnit" class="u184">';
				temp += '<option selected=""  value="1">元</option>';
				temp += '<option value="31">元/吨</option>';
				temp += '<option value="32">元/公斤</option>';				
				temp += '</select>';
				temp += '</td></tr>';
				$("#u172").before(temp);
 		 }
 		 
 		 function smit(){
 			var timeStart= $('#timeStart').val();
 			var timeEnd= $('#timeEnd').val();
 			if(timeStart==null ||  $.trim(timeStart)==""
 					||timeEnd==null ||  $.trim(timeEnd)==""){
 				warningMessage("补贴周期不能为空!");
 				return false;
 			}
 			
 			var type=  $("input[name='subPayRule.subType']:checked").val();
 			
 			if(type ==null || $.trim(type)== ""){
 				warningMessage("选择补贴方案!");
 				return false;
 			}
 			type = type*1;
 			alert(type);
			if(type==1){
				var subPercent = $('#subPercent').val();
	 			if(subPercent ==null || $.trim(subPercent)== ""){
	 				warningMessage("订单交易成交金额百分比不能为空!");
	 				return false;
	 			}
				
			}else if(type==2){
				var subAmount = $('#subAmount').val();
	 			if(subAmount ==null || $.trim(subAmount)== ""){
	 				warningMessage("每笔金额补贴金额不能为空!");
	 				return false;
	 			}
			}else if(type==3){
				var re = true;
				$("input[name^='zList']").each(function(){
					var value = ($(this).val());
		 			if(value ==null || $.trim(value)== ""){
		 				warningMessage("总交易额区间不能为空!");
		 				re  = false;
		 				//return false;
		 			}
				})
				//return re;
				
			}else if(type==4){
				
			}
 			
 			
 			var url=CONTEXT+"/subpayrule/addRule/2";
 			jQuery.post(url, $('#addForm').serialize(), function (data) {
 				if (data == "success") {
 					slideMessage("操作成功！");
 					//刷新父页面列表
 					$("#marketdg").datagrid('reload');
 					$('#addDialog').dialog('close');
 				}else if(data == "exists")
 				{
 					warningMessage("街市名称已存在，请重新输入！");
 					return;
 				}	
 				else {
 					warningMessage("系统异常！");
 					return;
 				}
 			});
 			//$('#addForm').submit();
 			 
 		 }
	

 		 
 		 
  		var settingInfo = {
  				data: {
  					key: {
  						title:"name"
  					},
  					simpleData: {
  						enable: true
  					}
  				}
  				/* ,
  				callback: {
  					//beforeClick: beforeClick,
  					onClick: onClick
  				} */
  			};
			//var cjson = ${rule.cateJson};
  			var zNodes =${rule.cateJson};
  			/* var clickflag = 0;
  			$.ajax({
  			  	type: "GET",
  			  	url: CONTEXT+"subpayrule/getProductCategory?marketId=1",
  			  	dataType: "json",
  			  	async:false,
  			  	success:function(data){
  			  		zNodes = data
  			  	}
  			}); */

  				
  			

  			$(document).ready(function(){
  				$.fn.zTree.init($("#cat-tree-info"), setting, zNodes);
  			});		
  					 
  		 	//查看
  		 	function copyRule(id){
  	         	$('#copyRuleIframe')[0].src=CONTEXT+'subpayrule/copyRule/'+id+'?t='+(new Date()).getTime();
  	  			$('#copyDialog').dialog('open');
  	  		$('#detailDialog').dialog('close');
  	  		
  		 	};	 		 
	</script>