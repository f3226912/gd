<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>

	  <script type="text/javascript" src="${CONTEXT}js/jquery.zclip.min.js"></script> 
	    <div id="tt" class="easyui-tabs">  
			<div title="商铺信息"	>
			<table>
				<tr>
					<td class="mleft" >
						用户账号:
					</td>
					<td class="mright">
					 		${dto.account}		
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						经营模式:
					</td>
					<td class="mright">
					<select name="businessModel"  id="businessModel"  disabled="disabled">
 							<option value="">请选择经营模式</option>
							<option value="0" <c:if test="${dto.businessModel==0 }">selected</c:if>   >个人经营</option>
							<option value="1" <c:if test="${dto.businessModel==1 }">selected</c:if> >企业经营</option>
						</select>						
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						类型:
					</td>
					<td class="mright">
 							<c:choose>
		 						<c:when test="${dto.level==4}">
										产地供应商
		 						</c:when>
		 						<c:otherwise>
				 						批发商
		 						</c:otherwise>
	 						 </c:choose>
					</td>
				</tr>
				<tr <c:if test="${mbdto.level==4 }">style="display:none;"</c:if> >
					<td class="mleft" >
						所属市场:
					</td>
					<td class="mright">
						${mdto.marketName}
					</td>
				</tr>
					<tr >
					<td>主营分类：</td>
			      <td> 
					<c:forEach  items="${lsitAll}" var="productCategoryDTO" >
							<input  class="pro-cit-area-lab" type="radio"
							 name="zycategoryId"
							 id="zycategoryId"  disabled="disabled"
							value="${productCategoryDTO.categoryId}"
							<c:forEach  items="${listRBC}" var="reBusinessCategory" >
						 		<c:if test="${reBusinessCategory.categoryId == productCategoryDTO.categoryId && reBusinessCategory.businessType=='0'}">checked</c:if>
							</c:forEach>>
							<label>${productCategoryDTO.cateName}</label>
					</c:forEach>	 
					</td>
				</tr>
				<tr >
					<td>兼营分类：</td>
			      <td> 
					<c:forEach  items="${lsitAll}" var="productCategoryDTO" >
							<input  class="pro-cit-area-lab" type="checkbox"
							 name="yjcategoryId"
							 id="jycategoryId"  disabled="disabled"
							value="${productCategoryDTO.categoryId}"
							<c:forEach  items="${listRBC}" var="reBusinessCategory" >
						 		<c:if test="${reBusinessCategory.categoryId == productCategoryDTO.categoryId && reBusinessCategory.businessType=='1'}">checked</c:if>
							</c:forEach>>
							<label>${productCategoryDTO.cateName}</label>
					</c:forEach>	 
					</td>
				</tr>
			 	<tr>
					<td class="mleft"  >
						公司名称:
					</td>
					<td class="mright">
						${dto.name}
					</td>
				</tr> 
				<tr>
					<td class="mleft" >
						商铺名称:
					</td>
					<td class="mright">
						${dto.shopsName}
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						主营产品:
					</td>
					<td class="mright">
				 			${dto.mainProduct}
					</td>
				</tr>
				<c:choose>
					<c:when test="${mbdto.level==4}">
						<tr>
					<td class="mleft" >
						经营类型 :
					</td>
					<td class="mright">
						<input type="hidden" value="" name="areaType" id="areaType" />
						<select name="managementType" disabled  id="managementType" >
 							<option value="">请选择经营类型</option>
							<option value="1" <c:if test="${dto.managementType==1 }">selected</c:if> >种养大户</option>
							<option value="2" <c:if test="${dto.managementType==2 }">selected</c:if> >合作社</option>
							<option value="3" <c:if test="${dto.managementType==3 }">selected</c:if> >基地</option>
						</select>						
					</td>
				</tr>
					</c:when>
				</c:choose>
				<tr>
					<td class="mleft" >
						商铺简介:
					</td>
					<td class="mright">
					${dto.shopsDesc}
					</td>
				</tr>
				<c:if test="${mbdto.level!=4}">
					<tr>
					<td  >
					铺位编号：
					</td>
					<td class="mright">
						<input class="i-app-far-mar-ph" type="text"  value="${dto.bunkCode}"  maxlength="20"   name="bunkCode"  id="bunkCode" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="mleft" >
						线下认证 :
					</td>
					<td class="mright">
					<c:if test="${dto.offlineStatus==1}">已认证</c:if>
					<c:if test="${dto.offlineStatus==2}">未认证</c:if>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						商铺分享地址：
					</td>
					<td class="mright">
					${dto.shopsUrl}
					<input type="hidden"  id="shopUrl"  value="${dto.shopsUrl}"/>
				<button id="copy-button">复制链接</button>
					</td>
				</tr>
			
			</table>
			</div>
			<div title="POS终端信息">
			<br/>
				<div class="form-pos">
				</div>
			</div>
			<div title="智能秤信息">
			<br/>
				<form  id="steelForm" method="post"  action="business/saveSteelyard">
				<div class="form-steel">
				</div>
				</form>				
			</div>
		</div>
		
<script type="text/javascript" >

var posCount=${posCount};
$(function(){
	
	$("#copy-button").zclip({
	    path:CONTEXT+"/js/ZeroClipboard.swf", //记得把ZeroClipboard.swf引入到项目中 
	    copy:function(){
	      return $('#shopUrl').val();
	    }
	  });
	debugger;
	var posNosJSON='${posNosJSON}';
	var posNosObj=$.parseJSON(posNosJSON);
	var posCount=${posCount};
	if(posCount==0){
		//insert_pos();
	}else{
		for(var i=0;i<posCount;i++){
			var type=posNosObj[i].type;
			var posNumber=posNosObj[i].posNumber;
			var hasClear=posNosObj[i].hasClear; //是否谷登结算
			var checkedStr='<input type="checkbox" disabled="disabled" checked  name="hasClear" value="1">';
			var tdbank='<td><select name="type" disabled="disabled"><option value="1">农行（E农）</option><option value="2">南宁建行</option><option value="3">广西农信</option><option value="4">杉德</option></select></td>';
			replaceObj='value="'+type+'"';
			tdbank=tdbank.replace(replaceObj,replaceObj+' selected')
			if(hasClear==0){
			    checkedStr='<input type="checkbox" disabled="disabled"  name="hasClear" value="0">';
			}
			
			var _Text = '<table>POS终端信息'+(i+1)+'<tr><td>所属于银行：	</td>'+
			             tdbank+
						'</tr>'+
						'<tr>'+
							'<td>'+
								'POS终端号：'+
							'</td>'+
							'<td>'+posNumber+
							'</td>'+
						'</tr>'+
						'<tr><td>'+
						'是否谷登清算：'+
					'</td>'+
					'<td>'+
					checkedStr +
					'</td></tr>'+
					'</table>';
			$(".form-pos").append(_Text);
		}
	}
	
	//================== 智能秤模块 start ===============
	var tmpSteelCount = ${steelyardCount};
	
	if(tmpSteelCount == 0){
		//insertSteelyard();
	}else{
		var steelyardListJSON = '${steelyardJSON}';
		var steelyardJsonObj = $.parseJSON(steelyardListJSON);
		var htmlText = "";
		for(var i=0; i<tmpSteelCount; i++){
			var stlydId = steelyardJsonObj[i].stlydId;         /**秤ID */
			var macAddr = steelyardJsonObj[i].macAddr;   /**秤mac */
			htmlText += '<table>智能秤信息' + (i+1) + '<tr><td>秤号：	</td>'+
							'<td>'+ stlydId +
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td>'+
								'MAC地址：'+
							'</td>'+
							'<td>'+ macAddr +
							'</td>'+
						'</tr>'+
					'</table>';
		}
		$(".form-steel").append(htmlText);
	}
	//================== 智能秤模块 end ===============
});

function insert_pos(){
	posCount+=1;
	var _Text = '<table>POS终端信息'+posCount+'<tr><td>所属于银行：	</td>'+
			'<td>'+
				'农行<input type="hidden" name="type" value="1"/>'+
			'</td>'+
		'</tr>'+
		'<tr>'+
			'<td>'+
				'POS终端号：'+
			'</td>'+
			'<td>'+
			'<input  id="'+posCount+'" class="posNumber" type="text" name="posNumber" onblur="checkPosNumber(this,${dto.businessId});"/>'+
			'</td>'+
		'</tr>'+
	'</table>';
	$(".form-pos").append(_Text);
};

function checkPosNumber(obj,businessId){
	var posNumber=obj.value; 
	var id=obj.id; 
	 /*var reg = new RegExp("^[a-zA-Z0-9]{8}$");
	if(!reg.test(posNumber) ){
		alert("终端号格式不正确，请输入8位的英文或数字组合");
		  $("#"+id).focus();
	} */
	$.ajax({ 
	type:"POST", 
	cache:false, 
	url : CONTEXT+"business/checkPosNumber", 
	dataType : "text", 
	data:{"posNumber":posNumber,"businessId":businessId}, 
	async:false, 
	success : function(data){ 
			if (data == "Exist"){
				alert("此pos号已存在,请重新输入"); 
				$("#"+id).focus(); 
			} 
		} 
	}); 
}
</script>