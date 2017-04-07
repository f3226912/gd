<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>

</style>
<form id="edit_form" method="post" action="grdPurchase/save">
	<div style="height:300px">
		<table style="border: none;width: 100%;">
			<tr>
				<td style="text-align:right;"><span style="color: red">*</span>采购单编号:</td>
				<td><input type="text" id="edit_purchaseNO" name="edit_purchaseNO" readonly="readonly"  value="${dto.purchaseNO}"  style="width: 90%;"></td>

				<td style="text-align:right;"><span style="color: red">*</span>采购申请人:</td>
				<td><input type="text" id="edit_purchaser" name="edit_purchaser"  value="${dto.purchaser}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="申请人必填" style="width: 90%;"></td>

			</tr>
			<tr>
				<td style="text-align:right;"><span style="color: red">*</span>所属市场:</td>
				
				<td>
						<select id="edit_marketId" name="edit_marketId" style="width:90%">
						<c:forEach var="item" items="${marketList}" varStatus="status">
						<c:if test="${item.id eq dto.marketId }" >
							<option  selected value="${item.id}">${item.marketName }</option>
							</c:if> 
						</c:forEach>
						</select>&nbsp;&nbsp;
				</td>
				
				<td style="text-align:right;"><span style="color: red">*</span>所属仓库:</td>
				<td>
						<select id="edit_warehouseId" name="edit_warehouseId" style="width:90%">
						<c:forEach var="item" items="${storeList}" varStatus="status">
						<c:if test="${item.id eq dto.warehouseId }" >
							<option  selected value="${item.id}">${item.name }</option>
							</c:if>
						</c:forEach>
						</select>&nbsp;&nbsp;
				</td>
			</tr>
			
			<tr>
				<td style="text-align:right;"><span style="color: red"></span>备注:</td>
				
				<td>
						<textarea name="edit_remark" cols=61 rows=4>${dto.remark}</textarea>
				</td>
				
				<td style="text-align:right;"><span style="color: red"></span>订单金额:</td>
				<td><input type="text" id="totalAmount" name="totalAmount"  value="" readonly="readonly"  style="width: 90%;"></td>
			</tr>			
		</table>

		<span>&nbsp;采购礼品：</span>
		<table id="editpurchase_table"  style="padding:5px;height:100px;width:600px"></table>
		<div id="editpurchase_too_bar" style="padding:5px;height:30px;">
			<div style="float: left; font-size: 14px;">采购礼品列表</div>
				<div style="float: right; margin-right: 20px;">
							<a class="easyui-linkbutton icon-search-btn"  id="btn-selgift" >选择礼品</a>
							<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-remove" id="edit_del">删除</a>
				</div>
			</div>
		</div>		
		<input type="hidden" id="seledgiftId" name="seledgiftId" value="" /> 
	</div>
</form>
<script>
function accMul(arg1,arg2,arg3) 
{ 
	var m=0,s1=arg1+"",s2=arg2+""; 
	try{m+=s1.split(".")[1].length}catch(e){} 
	try{m+=s2.split(".")[1].length}catch(e){} 
	var result = Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
	if(arg3>0){
		return result.toFixed(arg3);
	}else{
		return result;
	}
	return 
} 

function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)) 
	var result = (arg1*m+arg2*m)/m ;
	return result;
/* 	result = result+"";
	if(result.split(".")[1]&&result.split(".")[1].length>2){
		return result.toFixed(2);
	}else{
		return reslut;
	} */
	} 
	
function accDiv(arg1,arg2,arg3){ 
	var t1=0,t2=0,r1,r2; 
	try{t1=arg1.toString().split(".")[1].length}catch(e){} 
	try{t2=arg2.toString().split(".")[1].length}catch(e){} 
	with(Math){ 
			r1=Number(arg1.toString().replace(".","")) 
			r2=Number(arg2.toString().replace(".","")) 
			var result = (r1/r2)*pow(10,t2-t1); 
			if(arg3>0){
				return result.toFixed(arg3);
			}else{
				return parseInt(result);
			}
	
		} 
	} 
	
// 更新订单总金额

function updateTotalAmount(){
	
	var total=0.0;
	$.each($(".amountcla"),function(){
		var pri = $(this).val();
		total = accAdd(total,pri);
	})
	$("#totalAmount").val(total);
	
}



function initStore(marketId){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }grdGdGiftstore/query?marketId="+marketId,
		dataType: "json",
		success: function(data) {
			var warehouse=data.rows;
			if (warehouse.length != 0) {
				$('#edit_warehouseId').empty();
				$('#edit_warehouseId').append("<option value=''>全部</option>");
				for ( var n = 0; n < warehouse.length; n++) {
					$('#edit_warehouseId').append("<option value='"+warehouse[n].id+"'>"+warehouse[n].name+"</option>");
				}
			}
		}
	});
}

$(function(){
	initeditGiftList();

	$("body").delegate("#edit_marketId","change", function(){
		$('#edit_warehouseId').empty();
		$('#edit_warehouseId').append("<option value=''>全部</option>");
		var marketId = $(this).children('option:selected').val();
		initStore(marketId);
	})
	

	// 新增
	$('#btn-selgift').click(function() {
		$('<div></div>').dialog({
          id : 'selGiftDialog',
          title : '选择礼品',
          width : 650,
          height : 500,
          closed : false,
          cache : false,
          href : CONTEXT+'grdPurchase/selgift',
          modal : true,
          onLoad : function() {
              //初始化表单数据
          },
          onClose : function() {
              $(this).dialog('destroy');
          },
          buttons : [ {
              text : '保存',
              iconCls : 'icon-save',
              handler : function() {
            	  selGiftSave();
                  return false; // 阻止表单自动提交事件
              }
          }, {
              text : '取消',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#selGiftDialog").dialog('destroy');
              }
          } ],
      });
	});
	
	
	$("body").delegate(".unitPricecla","blur",function(){
		var price = $(this).val();
		var giftNo= $(this).attr("data");
		var count = $("#count"+giftNo).val();
		var amount = $("#amount"+giftNo).val();
		if(price==null||price==""){
			if(count!=null&&count!=""&&amount!=null&&amount!=""){
				$(this).val(accDiv(amount,count,2));
				updateTotalAmount();
				return false;
			}else{
				return false
			}	
		}
		var reg = /^\d+(?:\.\d{1,2})?$/;
		if(!reg.test(price)||price<=0||price=="0"){
			warningMessage("请输入最多带两位小数的值");
			 $(this).val("");
			return false;
		}
		if(count==null&&count==""&&amount==null&&amount=="")return false;
		
		if(count!=null&&count!=""){
			$("#amount"+giftNo).val(accMul(count,price,2));
			updateTotalAmount();
			return false;
		}
		
		if(amount!=null && amount!=""){
			$("#count"+giftNo).val(parseInt(amount/price));
			updateTotalAmount();
			return false;
		}		 
		
/* 		if(count!=null&&count!=""&&amount!=null&&amount!=""){
			$(this).val(accDiv(amount,count,2));
			return false;
		}	 */
		});
	
 	$("body").delegate(".countcla","blur",function(){
		var count = $(this).val();
		var giftNo= $(this).attr("data");
		var price = $("#unitPrice"+giftNo).val();
		var amount = $("#amount"+giftNo).val();
		if(count==null||count==""){
			if(price!=null&&price!=""&&amount!=null&&amount!=""){
				$(this).val(accDiv(amount,count,0));
				updateTotalAmount();
				return false;
			}else{
				return false
			}				
			
		}
		var reg = /^[0-9]*[1-9][0-9]*$/;
		if(!reg.test(count)||count<=0||count=="0"){
			warningMessage("请输入大于0的整数");
			 $(this).val("");
			return false;
		}
		
		if(price==null&&price==""&&amount==null&&amount=="")return false;
		
		if(price!=null&&price!=""){
			$("#amount"+giftNo).val(accMul(price,count,2));
			updateTotalAmount();
			return false;
		}
		
		if(amount!=null && amount!=""){
			$("#unitPrice"+giftNo).val(accDiv(amount,count,2));
			updateTotalAmount();
			return false;
		}	
		});
	
 	
 	
	/* $("body").delegate(".amountcla","blur",function(){
		var amount = $(this).val();
		var giftNo= $(this).attr("data");
		var price = $("#unitPrice"+giftNo).val();
		var count = $("#count"+giftNo).val();		
		
		if(amount==null||amount==""){
			if(price!=null&&price!=""&&count!=null&&count!=""){
				$(this).val(accMul(count,price,2));
				updateTotalAmount();
				return false;
			}else{
				return false
			}				
			
		}
		var reg = /^\d+(?:\.\d{1,2})?$/;
		if(!reg.test(amount)||amount=="0"){
			warningMessage("请输入最多带两位小数的值");
			 $(this).val("");
			return false;
		}

		if(price==null&&price==""&&count==null&&count==""){
			return false;
		}
		
		if(count!=null&&count!=""){
			$("#unitPrice"+giftNo).val(accDiv(amount,count,2));
			updateTotalAmount();
			return false;
		}
		
		if(price!=null && price!=""){
			$("#count"+giftNo).val(accDiv(amount,price,0));
			updateTotalAmount();
			return false;
		}	
		});	 
	 */

	// 删除操作
	$("#edit_del").click(function() {
		var rows = $('#editpurchase_table').datagrid("getSelections");
		if ($(rows).length < 1) {
			warningMessage("请选择礼品");
			return false;
		}
/* 		var ids = getSelections("giftNo");
		alert(row);
		var arrId = ids.split(","); */

		var selectedIds = $("#seledgiftId").val();
		var selArrIds = selectedIds.split(",");
		for(var i=0; i<rows.length; i++){
			//ids.push(rows[i].giftNo);
			selArrIds.splice($.inArray(rows[i].giftNo+"",selArrIds),1);
		}		
		
/* 		$.each(ids,function(){
			alert($(this));
			selArrIds.splice($.inArray($(this)+"",selArrIds),1);
		}) */
		var newIds = selArrIds.join(",");
		$("#seledgiftId").val(newIds);
		if(newIds==null||newIds==""){
			newIds = "noid";
		}
		var params = {"ids":newIds,"purchaseNo":$("#edit_purchaseNO").val()};
		$("#totalAmount").val(0);
		editGiftList(params);
		
	});	
	
	
	})
	
function selGiftSave() {
	var selectedIds = $("#selgiftId").val();
	var purchaseNo = $("#edit_purchaseNO").val();
	//alert(selectedIds);
	$("#seledgiftId").val(selectedIds);
	var params = {"ids":selectedIds,"purchaseNo":purchaseNo};
	 $("#selGiftDialog").dialog('destroy');
	 $("#totalAmount").val(0);
	 editGiftList(params);
	
}	
	
	
//初始化加载页面列表
function initeditGiftList(){
	var purchaseNo = $("#edit_purchaseNO").val();
	var selectedIds = $("#selgiftId").val();
	var params = {"purchaseNo":purchaseNo,"ids":selectedIds};
	editGiftList(params);
	//分页加载
	$("#editpurchase_table").datagrid("getPager").pagination({
		pageList: [100]
	});
}

function editGiftList(params){
	//数据加载
	$('#editpurchase_table').datagrid({
		url:CONTEXT+'grdGdGift/queryGiftByPno',
		width: 'auto',  
		queryParams: params,
		height: '400', 
		nowrap:true,
		toolbar:'#editpurchase_too_bar',
		pageSize:100,
		pageList: [100,200],
		rownumbers:false,
		pagination:true,
		fitColumns:false,
		fit:false,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#editpurchase_table").datagrid('clearSelections');
		},
		columns:[[
		    {field:'id',title:'序号',width:30,align:'center'},
		    {field:'giftNo',title:'礼品编号',width:150,align:'left'},
			{field:'name',title:'礼品名称',width:200,align:'left'},
			{field:'unit',title:'单位',width:60,align:'center'},
			{field:'opt0',title:'单价',width:90,align:'center',formatter:optformatunit},
			{field:'opt1',title:'数量',width:90,align:'center',formatter:optformatcount},
			{field:'opt2',title:'金额',width:90,align:'center',formatter:optformatamount}
			//{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
}

function getObj(obj){
	var res = obj;
	if(obj==null||obj==""){
		res = ""
	}
	return res;
}

function optformatunit(value,row,index){
	var html='<input type="hidden" value="'+getObj(row.unitPrice)+'"  name="prototypePrice'+row.giftNo+'" >';
	html+='<input type="text" missingMessage="单价必填" class="unitPricecla" value="'+getObj(row.unitPrice)+'" data="'+row.giftNo+'" id="unitPrice'+row.giftNo+'"  name="unitPrice'+row.giftNo+'"  style="width:70px" >';
	return html;
}

function optformatcount(value,row,index){
	var html='';
	html+='<input type="text" missingMessage="数量必填" class="countcla" value="'+ getObj(row.count)+'" data="'+row.giftNo+'" id="count'+row.giftNo+'" name="count'+row.giftNo+'"  style="width:70px" >';
	return html;
}

function optformatamount(value,row,index){
	var html='';
	html+='<input type="text" missingMessage="金额必填" readonly="readonly" class="amountcla" value="'+ getObj(row.amount)+'" data="'+row.giftNo+'" id="amount'+row.giftNo+'" name="amount'+row.giftNo+'"  style="width:70px" >';
	var ginftNos = $("#seledgiftId").val();
	if(ginftNos==null||ginftNos==""){
		$("#seledgiftId").val(row.giftNo);
	}else{
		var arrGiftNos = ginftNos.split(",");
		if($.inArray(row.giftNo+"",arrGiftNos)<0){
			$("#seledgiftId").val(ginftNos+","+row.giftNo);
		}
	}
	
	var totalAmount = $("#totalAmount").val();
	if(null!=row.amount&& ""!=row.amount){
		totalAmount = accAdd(totalAmount,row.amount);
	}
	$("#totalAmount").val(totalAmount);
	
	
	return html;
}



</script>