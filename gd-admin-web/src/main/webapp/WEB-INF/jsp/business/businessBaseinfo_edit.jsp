<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>

	    <div id="tt" class="easyui-tabs">  
			<div title="商铺信息"	>
			<form id="addForm" method="post" action="business/save">
			<input type="hidden" id="businessId" name="businessId"    value="${dto.businessId}"/>
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
					<select name="businessModel"  id="businessModel" >
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
						<input type="hidden" value="${mbdto.level}" name="level" />
 							<c:choose>
		 						<c:when test="${mbdto.level==4}">
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
						<input type="hidden" id="marketId_add" name="marketId" value="${mdto.id}" >
							${mdto.marketName}
					</td>
				</tr>
				<tr>
					<td>主营分类：</td>
			      <td> 
					<c:forEach  items="${lsitAll}" var="productCategoryDTO" >
							<input  class="pro-cit-area-lab" type="radio"   name="zycategoryId"   onchange="setJyCheckStatus(${productCategoryDTO.categoryId})"  id="zycategoryId"  value="${productCategoryDTO.categoryId}"
							<c:forEach  items="${listRBC}" var="reBusinessCategory" >
						 		<c:if test="${reBusinessCategory.categoryId == productCategoryDTO.categoryId && reBusinessCategory.businessType=='0' }">checked</c:if>
							</c:forEach>>
							<label>${productCategoryDTO.cateName}</label>
					</c:forEach>	 
					</td>
				</tr>
				<tr>
					<td>兼营分类：</td>
			      <td> 
					<c:forEach  items="${lsitAll}" var="productCategoryDTO" >
							<input  class="pro-cit-area-lab" type="checkbox"
							 name="jycategoryId"
							 id="jycategoryId" 
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
						<input  type="text" id="name"  name="name"   value="${dto.name}"   style="width:150px" >
					</td>
				</tr> 
				<tr>
					<td class="mleft" >
						商铺名称:
					</td>
					<td class="mright">
						<input  type="text" id="shopsName"  name="shopsName" maxlength="50"   value="${dto.shopsName}" required="true"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						主营商品:
					</td>
					<td class="mright">
				 					<input    type="text"  name="mainProduct" value="${dto.mainProduct}">
					</td>
				</tr>
				<tr>
					<td  class="mleft">
					商铺所在地：
					</td>
					<td class="mright">
						<select name="provinceId" id="provinceId" onselect="checkArea(this);"  class="i-app-far-mar-c3" >
						</select>			 	
						<select name="cityId" id="cityId"  class="i-app-far-mar-c3">
						</select>
						<select name="areaId" id="areaId"  class="i-app-far-mar-c3">
						</select>
					</td>
				</tr>
				<tr>
					<td  >
					商铺地址：
					</td>
					<td class="mright">
						<input class="i-app-far-mar-ph" type="text"  value="${dto.address}" maxlength="200"   name="address" id="address"/>
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
					<tr>
					<td class="mleft" >
						线下认证 :
					</td>
					<td class="mright">
						<select name="offlineStatus" id="offlineStatus" >
							<option value="1" <c:if test="${dto.offlineStatus==1}">selected</c:if> >已认证</option>
							<option value="2" <c:if test="${dto.offlineStatus==2}">selected</c:if> >未认证</option>
						</select>						
					</td>
				</tr>
				</c:if>				<c:choose>
					<c:when test="${mbdto.level==4}">
						<tr>
					<td class="mleft" >
						经营类型 :
					</td>
					<td class="mright">
						<input type="hidden" value="" name="areaType" id="areaType" />
						<select name="managementType"  id="managementType" >
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
					<textarea name="shopsDesc" style="width: 400px;height:200px">${dto.shopsDesc} </textarea>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
			        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
					</td>
				</tr>
			</table>
			</form> 
			
			</div>
			<div title="POS终端信息">
				</br>
				<form  id="posForm" method="post"  action="business/savePos">
				<input type="hidden" id="businessId" name="businessId"    value="${dto.businessId}"/>
				<div  >
<%-- 				商户号：<input type="text" id="businessNo" name="businessNo" value="${dto.businessNo}" onblur="checkBusinessNo(this,${dto.businessId});" ></input>
 --%>				</div>
				<div class="form-pos">
				</div>
				<div>
					<table>
						<tr>
							<td></td>
							<td>
					        	<a href="javascript:void(0)"     id="icon-save-btn" onclick="insert_pos();">添加更多POS终端设备</a>
							</td>
						</tr>
					</table>
				</div>
				<div id="a-save">
	        	<a href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-save'" 
	        	id="icon-save-btn" onclick="savePos()">保存</a>
	        	</div>
				</form>				
			</div>
			<div title="智能秤信息">
				</br>
				<form  id="steelForm" method="post"  action="business/saveSteelyard">
				<input type="hidden" id="businessId" name="businessId" value="${dto.businessId}"/>
				<!--
				<input type="hidden" id="stlydId" name="stlydId" value=""/>
				<input type="hidden" id="macAddr" name="macAddr" value=""/>
				-->
				<div class="form-steel">
				</div>
				<div>
					<table>
						<tr>
							<td></td>
							<td>
					        	<a href="javascript:void(0)" id="icon-save-btn" onclick="insertSteelyard();">添加更多智能秤信息</a>
							</td>
						</tr>
					</table>
				</div>
				<div id="a-save">
	        	<a href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-save'" 
	        	id="icon-save-btn" onclick="saveSteelyard()">保存</a>
	        	</div>
				</form>				
			</div>
		</div>
<script type="text/javascript" >

$('#showMarketWin').click(function(){
	$('#marketDialog').dialog({'title':'选择街市', 'width':700, 'height':300, 'href':CONTEXT+'product/marketSelect'}).dialog('open');
});

var posCount=${posCount};
var steelCount=${steelyardCount};

$(document).ready(function(){
	debugger;
	var posNosJSON = '${posNosJSON}';
	var posNosObj = $.parseJSON(posNosJSON);
	var posCount = ${posCount};
	var businessId='${businessId}';
	
	if(posCount == 0){
		insert_pos();
	}else{
		for(var i=0;i<posCount;i++){
			var type=posNosObj[i].type;  //银行类型
			var hasClear=posNosObj[i].hasClear; //是否谷登结算
			
			var tdbank='<td><select name="type"><option value="1">农行（E农）</option><option value="2">南宁建行</option><option value="3">广西农信</option><option value="4">杉德</option></select></td>';
			replaceObj='value="'+type+'"';
			tdbank=tdbank.replace(replaceObj,replaceObj+' selected');
			
			var checkedStr='<input type="checkbox" checked  name="hasClear" value="1">';
			if(hasClear==0){
			    checkedStr='<input type="checkbox"  name="hasClear" value="0">';
			}
			if(type==4){
				checkedStr='<input type="checkbox" checked  disabled="disabled" name="hasClear" value="1">';
			}
			
			var posNumber=posNosObj[i].posNumber;
			var _Text = '<table>POS终端信息'+(i+1)+'<tr><td>所属于银行：	</td>'+
							tdbank+
						'</tr>'+                       
						'<tr>'+
							'<td>'+
								'POS终端号：'+
							'</td>'+
							'<td>'+
							'<input id="'+(i+1)+'" class="easyui-validatebox" type="text" name="posNumber" validType="checkPosNumber[${businessId},'+(i+1)+']" orivalue="'+posNumber+'" value="'+posNumber+'"  />'+
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
		insertSteelyard();
	}else{
		var steelyardListJSON = '${steelyardJSON}';
		var steelyardJsonObj = $.parseJSON(steelyardListJSON);
		var htmlText = "";
		for(var i=0; i<tmpSteelCount; i++){
			var steelYardId = steelyardJsonObj[i].id;         /**ID */
			var stlydId = steelyardJsonObj[i].stlydId;         /**秤ID */
			var macAddr = steelyardJsonObj[i].macAddr;   /**秤mac */
			htmlText += '<table>智能秤信息' + (i+1) + '   <input id="steelYard'+steelYardId+'" type="button" name="steelYardId" onclick="deleteStellYard('+steelYardId+');" value="删除"/><tr><td>秤号：	</td>'+
							'<td>'+
							'<input id="stlydId'+(i+1)+'" type="text" name="stlydId" value="'+stlydId+'"/>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td>'+
								'MAC地址：'+
							'</td>'+
							'<td>'+
							'<input id="macAddr'+(i+1)+'" type="text" name="macAddr" value="'+macAddr+'"/>'+
							'</td>'+
						'</tr><tr><td>&nbsp;</td></tr>'+
					'</table>';
		}
		$(".form-steel").append(htmlText);
	}
	//================== 智能秤模块 end ===============
});


function insert_pos(){
	debugger;
	posCount+=1;
	var _Text = '<table>POS终端信息'+posCount+'<tr><td>所属于银行：	</td>'+
			'<td><select name="type"><option value="1">农行（E农）</option><option value="2">南宁建行</option><option value="3">广西农信</option><option value="4">杉德</option></select></td>'+
		'</tr>'+
		'<tr>'+
			'<td>'+
				'POS终端号：'+
			'</td>'+
			'<td>'+
			'<input  id="'+posCount+'" class="easyui-validatebox" type="text" name="posNumber" validType="checkPosNumber[${businessId},'+posCount+']"/>'+
			'</td>'+
		'</tr>'+
		'<tr>'+
		'<td>'+
			'是否谷登清算：'+
		'</td>'+
		'<td>'+
		'<input type="checkbox" name="hasClear" value="0">'+
		'</td>'+
	'</tr>'+
	'</table>';
	$(".form-pos").append(_Text);
	$("#"+posCount).validatebox();  //对应新增的元素重新绑定验证事件
	$("[name=type]").change(function(){
		debugger;
		var checkIsclear=$(this).parents("table").find("[name=hasClear]");
		if($(this).val()==4){
			checkIsclear.prop("checked",true).attr("disabled","disabled");
		}else{
			checkIsclear.prop("checked",false).removeAttr("disabled");
		}
	})
};

//如果选中了 杉德 则下面的 是否谷登清算 必选不可修改
function isSelect4(){
	debugger;
	
}

function savePos() {
	debugger
	$(":checkbox[name=hasClear]").val(0); 
	$(":checkbox[name=hasClear]:checked").val('1'); //将选中的 是否有谷登结算 的checkbox的value 设置为1 ，否则就是默认的0
	/*
	var $valAdd = $("input[name='posNumber']");
	var $valAdd1 = $("input[name='posNumber']");
	var bool=false;
	var id='';
	var emptyId='';
	var isEmpty=false;
	var isCorrect=false;
	var correctId='';
	var reg = new RegExp("^[a-zA-Z0-9]{8}$");
	$valAdd.each(function(){
        var i = $(this);
		var data=i.val();
		if(data == "" && i.attr("id")!="posNumber"){
			emptyId=i.attr("id");
			isEmpty=true;
		} 
		if(data != "" && !reg.test(data) && i.attr("id")!="posNumber" ){
			isCorrect=true;
			correctId=i.attr("id");
		}
    	$valAdd1.each(function(){
    		var j=$(this);
            if (i.attr("id")!= j.attr("id")&& j.attr("id")!="posNumber" && i.attr("id")!="posNumber" && j.val() == i.val() && j.val() != "") {
            	id=i.attr("id");
            	bool=true;
            }
    	});
	});
	/* if(isEmpty){
	   alert("终端号不能为空！");
  	   $("#"+emptyId).focus();
  	   return false;
	} */
/* 	if(isCorrect){
	   alert("终端号格式不正确，请输入8位的英文或数字组合");
	   $("#"+correctId).focus();
  	   return false;
	}
	if(bool){
    	   alert("不可输入相同终端号！");
    	   $("#"+id).focus();
    	   return false;
	} */
	/* var businessNo=$("#businessNo").val();
	if(!businessNo){
		alert("商户号不能为空，请输入。"); 
		$("#businessNo").focus(); 	
 	    return false;
	} */ 
	if(!$("#posForm").form('validate')){
		return;
	}
	var url=CONTEXT+"business/savePos"; 
	var hasClear=$.map($(":checkbox[name=hasClear]"),function(x,y){return $(x).val()});
	//下面是统计pos终端号发生改变的数据
	var updateData=$.map($("[name=posNumber]"),function(x,y){if($(x).attr('orivalue')&&$(x).val()!=$(x).attr('orivalue')){return $(x).attr('orivalue')+'@'+$(x).val()}});
	var params=$('#posForm').serialize()+"&hasClears="+hasClear+"&updateData="+updateData;
	jQuery.post(url,params , function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#businessdg").datagrid('reload');
			$('#addDialog').dialog('close');
			$('#editDialog').dialog('close');
		} else {
			slideMessage("保存失败！");
	    	return false;
		}
	});
}

function checkBusinessNo(obj,businessId){
	var businessNo=obj.value; 
	
	$.ajax({ 
	type:"POST", 
	cache:false, 
	url : CONTEXT+"business/checkBusinessNo", 
	dataType : "text", 
	data:{"businessNo":businessNo,"businessId":businessId}, 
	async:false, 
	success : function(data){ 
			if (data == "Exist"){
				alert("此商户号已存在,请重新输入"); 
				//$("#businessNo").attr("value",""); 
				$("#businessNo").focus(); 
			} 
		} 
	}); 
}

//================== 智能秤模块 start ===============
function insertSteelyard(){
	steelCount += 1;
	var htmlText = '<table>智能秤信息' + steelCount + '<tr><td>秤号：	</td>'+
				'<td>'+
				'<input id="stlydId'+steelCount+'" type="text" name="stlydId" value=""/>'+
				'</td>'+
			'</tr>'+
			'<tr>'+
				'<td>'+
					'MAC地址：'+
				'</td>'+
				'<td>'+
				'<input id="macAddr'+steelCount+'" type="text" name="macAddr" value=""/>'+
				'</td>'+
			'</tr><tr><td>&nbsp;</td></tr>'+
		'</table>';
	$(".form-steel").append(htmlText);
};

function saveSteelyard() {
	//debugger
	var $stlydIdObj = $("input[name='stlydId']");
	var $stlydIdObj2 = $stlydIdObj;  //用于判断是否重复
	var $macAddrObj = $("input[name='macAddr']");
	var $macAddrObj2 = $macAddrObj;  //用于判断是否重复
	var hasSame = false;	
	var isEmpty = false;
	var isCorrect = false;
	var isRepeat = false;
	var sameId = '';
	var emptyId = '';
	var correctId ='';	
	var repeatId ='';
	var reg = new RegExp("^[0-9]{8}$");
	$stlydIdObj.each(function(){
        var i = $(this);
		var value = i.val();
		if(value == undefined || value == "" || value == null){
			emptyId = i.attr("id");
			isEmpty = true;
		}
		if(!reg.test(value)){	
			correctId = i.attr("id");
			isCorrect = true;
		}
		//console.log("value: " + value);
		//console.log("result: " + checkStlydId(value));
		if(checkStlydId(value)){	
			repeatId = i.attr("id");
			isRepeat = true;
		}
    	$stlydIdObj2.each(function(){
    		var j = $(this);
            if (i.attr("id") != j.attr("id") && j.val() == i.val() && j.val() != "") {
            	sameId = i.attr("id");
            	hasSame = true;
            }
    	});
	});
	if(isEmpty){
	   alert("秤号不能为空！");
	   $("#" + emptyId).focus();
	   return false;
	}
	if(isCorrect){
	   alert("秤号位数必须为8位！");
	   $("#" + correctId).focus();
	   return false;
	}
	if(hasSame){
	   alert("不可输入相同秤号！");
	   $("#" + sameId).focus();
	   return false;
	}
	if(isRepeat){
	   alert("此秤号已存在,请重新输入");
	   $("#" + repeatId).focus();
	   return false;
	}
	var reg2 = new RegExp("^[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}$");
	$macAddrObj.each(function(){
        var i = $(this);
		var value = i.val();
		if(value == undefined || value == "" || value == null){
			emptyId = i.attr("id");
			isEmpty = true;
		}
		if(!reg2.test(value)){	
			correctId = i.attr("id");
			isCorrect = true;
		}
    	$macAddrObj2.each(function(){
    		var j = $(this);
            if (i.attr("id") != j.attr("id") && j.val() == i.val() && j.val() != "") {
            	sameId = i.attr("id");
            	hasSame = true;
            }
    	});
	});
	if(isEmpty){
    	   alert("MAC地址不能为空！");
    	   $("#" + emptyId).focus();
    	   return false;
	}
	if(isCorrect){
    	   alert("MAC地址不正确！");
    	   $("#" + correctId).focus();
    	   return false;
	}
	if(hasSame){
    	   alert("不可输入相同MAC地址！");
    	   $("#" + sameId).focus();
    	   return false;
	}
	
	var url = CONTEXT + "business/saveSteelyard";
	jQuery.post(url, $('#steelForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#businessdg").datagrid('reload');
			$('#addDialog').dialog('close');
			$('#editDialog').dialog('close');
		} else {
			alert(data);
	    	return false;
		}
	});
}

function deleteStellYard(steelyardId){
	//console.log("delete id: " + steelyardId)
	if(confirm('确认删除?')){
		var aj = $.ajax( {  
			url: CONTEXT + "business/delSteelyard",   
			data:{  
					 steelyardId : steelyardId 
			},  
			type:'get',  
			cache:false,
			success:function(data) {
				if (data == "true"){  
					alert("删除成功！");  
					window.location.reload();  
				}else{  
					alert(data);  
				}  
			 },  
			 error : function() {
				  alert("网络异常！");
			 }  
		});
		return true;
	} else {
		return false;
	}
}

function checkRepeatNumber(value){
	var isRepeat = false;
	var number = value;
	var numArr = number.split("");
	$.each(numArr,function(index1,num1) {
		var i1 = index1 + 0;
		var n1 = num1;
		$.each(numArr,function(index2,num2) {
			var i2 = index2 + 0;
			var n2 = num2;
			if((i1 != i2) && (n1 == n2)){
				isRepeat = true;
			}
		});
	});
	return isRepeat;
}

function checkStlydId(stlydId){
	var result = true;
	var businessId = ${dto.businessId};
	//console.log("businessId: " + businessId);
	if(stlydId == undefined || stlydId == "" || stlydId == null){
		return result;
	} 
		
	$.ajax({ 
		type:"POST", 
		cache:false, 
		url : CONTEXT + "business/checkSteelyard", 
		dataType : "text", 
		data:{
				"stlydId":stlydId,
				"businessId":businessId
			}, 
		async:false, 
		success : function(data){ 
			if (data == "notExist"){
				result = false;
			}
		} 
	});
	
	return result;
}
//================== 智能秤模块 end ===============
</script>
<script>
$(function(){
	//如果是选中 杉德，则 是否谷登结算必选，并且不可修改
	$("[name=type]").change(function(){
		debugger;
		var checkIsclear=$(this).parents("table").find("[name=hasClear]");
		if($(this).val()==4){
			checkIsclear.prop("checked",true).attr("disabled","disabled");
		}else{
			checkIsclear.prop("checked",false).removeAttr("disabled");
		}
	})
})
</script>
<script type="text/javascript" src="${CONTEXT}js/business/city-business.js"></script>
<script type="text/javascript" src="${CONTEXT}js/business/easyui-extend-business.js"></script>

<script>
 	$(document).ready(function() {
 		var city = new City($("#provinceId"),$("#cityId"),$("#areaId"));
 		if('${ empty dto.provinceId || dto.provinceId==0 }'=='true'){
 			city.init('0','0','0');
 		}else{
 	 		city.init('${dto.provinceId}','${dto.cityId}','${dto.areaId}');
 		}
 		setJyCheckStatus( $("input[name='zycategoryId']:checked").val());
 	});
 	
 	function setJyCheckStatus(zyCateId){
 		
 		var $jycategoryIds = $("input[name='jycategoryId']");
 		$jycategoryIds.each(function(){
    		var j=$(this);
    		if(j.val()==zyCateId){
    			j.attr("disabled",true);
    		}else{
    			j.attr("disabled",false);
    		}
    	});
 		
 		 
 	}
</script>









