<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
<%-- 		<%@ include file="../pub/kindeditor.inc" %> --%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>product</title>
		<link href="${CONTEXT}css/uploader.css" media="screen" rel="stylesheet" type="text/css" />
	</head>
<body>

	<input type="hidden" id="initState" name="initState" value="${state}"/>
	<table id="productdg" title="">
	</table>
	<div id="producttb" style="padding:5px;height:auto">
		<form id="productSearchForm" method="post" action="">
		<div>
			<!-- 用户账号: <input  type="text" id="mobile" name="mobile" style="width:150px" data-options="prompt:'请输入名称">&nbsp; -->
			账号/手机号: <input  type="text" id="account_q" name="account" style="width:150px" data-options="prompt:'请输入名称">&nbsp;
			商品名称: <input  type="text" id="productName_q" name="productName" style="width:150px" data-options="prompt:'请输入名称">&nbsp;
			创建时间 :
			<input  type="text"  id="startDate" name="startDate"
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
				style="width:150px" >~
			<input  type="text"    id="endDate" name="endDate"
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">
		<br>
		<gd:btn btncode='BTNSPGLQBCP10'>农批中心 : <input type="button" id="showMarketWin_q" value="选择农批中心"></gd:btn>
				<input type="hidden" id="marketId_q" name="marketId_q" value="">
		商品类目&nbsp; :
		<input id="categorySwitch" name="categorySwitch" value="" class="easyui-combobox"
	        data-options="
	        valueField: 'value',
			textField: 'label',
			data: [{
				label: '选择分类',
				value: '1'
			},{
				label: '全部分类',
				value: '0'
			}],
			editable:false,
	         onSelect: function(rec){
		         $('#categorySwitch').val(rec.value);
	        },onLoadSuccess : function(){
		        var data = $('#categorySwitch').combobox('getData');
				if (data.length > 0) {
					$('#categorySwitch').combobox('select', data[0].value);
				}
	        }" />
	     </form>
	     <div style="display:inline;" id="categoryTypeDiv">
			<input id="categoryType_q1" class="easyui-combobox" data-options="editable:false">
			<input id="categoryType_q2" class="easyui-combobox" data-options="editable:false">
			<input id="categoryType_q3" class="easyui-combobox" data-options="editable:false">
	     </div>
		<!-- <input id="categoryType_q4" > -->
		<input type="hidden" id="cateId_q" name="cateId_q" value="" >

		<input type="checkbox" id="vendorBox" name="vendorBox" >产地供应商
		<input type="hidden" id="vendor" name="vendor" value="0">
		<br>

		<div style="display:inline;">
			商品产地 :
			<input id="originProvinceId_comp" style="width:120px" class="easyui-combobox" data-options="editable:false">
			<input id="originCityId_comp" style="width:120px" class="easyui-combobox" data-options="editable:false">
			<input id="originAreaId_comp" style="width:120px" class="easyui-combobox" data-options="editable:false">
			<input type="hidden" id="originProvinceId" name="originProvinceId"/>
			<input type="hidden" id="originCityId" name="originAreaId"/>
			<input type="hidden" id="originAreaId" name="originAreaId"/>
	    </div>
		创建人 : <input  type="text" id="createUserName_q" name="createUserName" style="width:150px" data-options="prompt:'请输入创建人">
		审核状态 : <input id="state" name="state" value="" class="easyui-combobox"  style="width:100px"
				        data-options="
				        valueField: 'value',
						textField: 'label',
						data: [{
							label: '全部',
							value: '-1'
						},{
							label: '待审核',
							value: '1'
						},{
							label: '审核不通过',
							value: '2'
						},{
							label: '已上架',
							value: '3'
						},{
							label: '已下架',
							value: '4'
						},{
							label: '已删除',
							value: '5'
						}],
						editable:false,
				         onSelect: function(rec){
					         $('#state').val(rec.value);
				        },onLoadSuccess : function(){
					        var data = $('#state').combobox('getData');
							if (data.length > 0) {
								var s = $('#initState').val();
								if (s != -1){
									$('#state').combobox('select', s);
								}else{
									$('#state').combobox('select', data[0].value);
								}
							}
				        }" />
			<gd:btn btncode='BTNSPGLQBCP08'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="serachList">查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="reloadList">重置</a>

		</div>

		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNSPGLQBCP01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="toAdd">新增</a></gd:btn>
			<gd:btn btncode='BTNSPGLQBCP02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="removeBtn">删除</a></gd:btn>
			<gd:btn btncode='BTNSPGLQBCP05'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="offLine">下架</a></gd:btn>
			<gd:btn btncode='BTNSPGLQBCP06'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="onLine">上架</a></gd:btn>
			<gd:btn btncode='BTNSPGLQBCP07'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="batchOnLine">批量上架</a></gd:btn>
			<xlc:btn btncode='XZ003'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="batchRefreshExpire">批量刷新</a></xlc:btn>
			<gd:btn btncode='BTNSPGLQBCP09'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<!-- 产品标签 -->
			<input id="product_labels" name="product_labels" />
			<input type="hidden" id="product_label_choosed" name="product_label_choosed" />
			<input type="hidden" id="loadPlaceholderLabel" value="1"/>
			<xlc:btn btncode='XZ003'><a href="#" class="easyui-linkbutton" plain="true" id="batchUpdatePlabels">批量更新标签</a></xlc:btn>
			<xlc:btn btncode='XZ003'><a href="#" class="easyui-linkbutton" plain="true" id="batchRemovePlabels">批量清除标签</a></xlc:btn>
		</div>

<!-- 		<div id="addDialog" class="easyui-dialog" style="width:600px;height:320px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="add()">新增</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div> -->
		<div id="detailDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="dlg-buttonsDetail" style="text-align:center">
<!-- 				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-detail-btn">确定</a>
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-refuse-btn">驳回</a> -->
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
<!-- 		<div id="auditPassDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="dlg-buttonsAuditPass">
		</div>	 -->
<!-- 		<div id="editDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit"> -->
<!-- 			<div id="dlg-buttonsEdit" style="text-align:center"> -->
<!-- 	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-edit-btn" onclick="edit()">确定</a> -->
<!-- 	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a> -->
<!-- 	        </div> -->
<!-- 		</div>		 -->
		<div id="memberDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsMember">
			<div id="#dlg-buttonsMember" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-member-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#memberDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="marketDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsMarket">
			<div id="#dlg-buttonsMarket" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#marketDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="businessDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsBusiness">
			<div id="#dlg-buttonsBusiness" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#businessDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="uploadDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsUpload">
			<div id="dlg-buttonsUpload" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-upload-btn">确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#uploadDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/product/product.js"></script>
<script type="text/javascript" src="${CONTEXT}js/jquery.cookie.js"></script>
<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>

<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>

<script type="text/javascript">

var disableExport = false ;

/* function hideOriginPlace(hideCity, hideArea){
	if (hideCity){
		$('#originCityId_comp').next(".combo").hide();
	}else{
		$('#originCityId_comp').next(".combo").show();
	}
	if (hideArea){
		$('#originAreaId_comp').next(".combo").hide();
	}else{
		$('#originAreaId_comp').next(".combo").show();
	}
} */
function initOriginPlaceQueryComp(){

	$.ajax({
		url: CONTEXT+'product/queryProvince',
		dataType: 'json',
		success: function(jsonstr){
			jsonstr.unshift({
			'areaID':'0',
			'area':'请选择'
			});
			$('#originProvinceId_comp').combobox({
				data:jsonstr,
				valueField:'areaID',
				textField:'area',
				editable:false,
				onSelect:function(record){
					if(record.areaID == 0){
						$('#originProvinceId').val("");
						$('#originCityId').val("");
						$('#originAreaId').val("");
					}else{
						$('#originProvinceId').val(record.areaID);
					}
					updateOriginCity(record.areaID);
				},
				onLoadSuccess : function(){
					var data = $('#originProvinceId_comp').combobox('getData');
					$('#originProvinceId_comp').combobox('select', data[0].areaID);
				}
			});
		}
	});
/* 	$('#originProvinceId_comp').combobox({
		valueField:'areaID',
		textField:'area',
		url: CONTEXT+'product/queryProvince',
		editable:false,
		onSelect:function(record){
			$('#originProvinceId').val(record.areaID);
			$('#originCityId_comp').next(".combo").show();
			$('#originAreaId_comp').next(".combo").show();
			updateOriginCity(record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#originProvinceId_comp').combobox('getData');
			$('#originProvinceId_comp').combobox('select', data[0].areaID);
			$('#originProvinceId').val(data[0].areaID);
		}
	}); */
}

function updateOriginCity(originProvinceId){

	$.ajax({
		url: CONTEXT+'product/queryCity/' + originProvinceId,
		dataType: 'json',
		success: function(jsonstr){
			jsonstr.unshift({
			'areaID':'0',
			'area':'请选择'
			});
			$('#originCityId_comp').combobox({
				data:jsonstr,
				valueField:'areaID',
				textField:'area',
				editable:false,
				onSelect:function(record){
					if (record.areaID == 0){
						$('#originCityId').val("");
						$('#originAreaId').val("");
					}else{
						$('#originCityId').val(record.areaID);
					}
					updateOriginArea(record.areaID);
				},
				onLoadSuccess : function(){
					var data = $('#originCityId_comp').combobox('getData');
					$('#originCityId_comp').combobox('select', data[0].areaID);
				}
			});
		}
	});
/* 	$('#originCityId_comp').combobox({
		valueField:'areaID',
		textField:'area',
		url: CONTEXT+'product/queryCity/' + originProvinceId,
		editable:false,
		onSelect:function(record){
			$('#originCityId').val(record.areaID);
			updateOriginArea(record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#originCityId_comp').combobox('getData');
			//如果市级数据不存在, 则为港澳台
			if (data.length > 0) {
				$('#originCityId_comp').combobox('select', data[0].areaID);
				$('#originCityId').val(data[0].areaID);
			}else{
				hideOriginPlace(true,true);
				$('#originCityId').val("");
				$('#originAreaId').val("");
			}
		}
	}); */
}

function updateOriginArea(originCityId){

	$.ajax({
		url: CONTEXT+'product/queryCity/' + originCityId,
		dataType: 'json',
		success: function(jsonstr){
			jsonstr.unshift({
			'areaID':'0',
			'area':'请选择'
			});
			$('#originAreaId_comp').combobox({
				data:jsonstr,
				valueField:'areaID',
				textField:'area',
				editable:false,
				onSelect:function(record){
					if (record.areaID == 0){
						$('#originAreaId').val("");
					}else{
						$('#originAreaId').val(record.areaID);
					}
				},
				onLoadSuccess : function(){
					var data = $('#originAreaId_comp').combobox('getData');
					$('#originAreaId_comp').combobox('select', data[0].areaID);
				}
			});
		}
	});

/* 	$('#originAreaId_comp').combobox({
		valueField:'areaID',
		textField:'area',
		url: CONTEXT+'product/queryCity/' + originCityId,
		editable:false,
		onSelect:function(record){
			$('#originAreaId').val(record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#originAreaId_comp').combobox('getData');
			$('#originAreaId_comp').combobox('select', data[0].areaID);
			$('#originAreaId').val(data[0].areaID);
		}
	}); */
}

function resetCategory(){
	//隐藏商品分类组件
	hideProductCategory();
	var marketId = $("#marketId_q").val();
	if (!marketId) return ;
	//商品分类
 	$('#categoryType_q1').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/listTopProductCategory/' + marketId,
		editable:false,
		onSelect:function(record){
			updateSecondLevel(record.categoryId);
		},
		onLoadSuccess : function(){
			var data = $('#categoryType_q1').combobox('getData');
			if (data.length > 0) {
				$('#categoryType_q1').combobox('select', data[0].categoryId);
			}
			$('#categoryType_q1').next(".combo").show();
		}
	});
}

//隐藏商品分类组件
function hideProductCategory(){
	$('#categoryType_q1').next(".combo").hide();
	$('#categoryType_q2').next(".combo").hide();
	$('#categoryType_q3').next(".combo").hide();
}

function marketSelectCallBack(index,row){
	$("#marketId_q").val(row.id);
	$("#showMarketWin_q").val(row.marketName);
	$('#marketDialog').dialog("close");
	//隐藏商品分类组件
	hideProductCategory();

	$('#categorySwitch').combobox('select', '1');

	//商品分类
 	$('#categoryType_q1').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/listTopProductCategory/' + row.id,
		editable:false,
		onSelect:function(record){
			updateSecondLevel(record.categoryId);
		},
		onLoadSuccess : function(){
			var data = $('#categoryType_q1').combobox('getData');
			if (data.length > 0) {
				$('#categoryType_q1').combobox('select', data[0].categoryId);
			}
			$('#categoryType_q1').next(".combo").show();
		}
	});
}

var initState = $("#initState").val();

function batchOnline(ids){
	 $.ajax( {
		    url:CONTEXT+'product/batchUpdateProductStatus' ,
		    data:{
		    	"productIds" : ids.join(",")
		    },
		    type:'post',
		    dataType:'json',
		    success:function(data) {
		    	 if (data.status == 1){
			    	warningMessage("操作成功");
			    	$("#productdg").datagrid('reload');
		    	 }else {
		    		warningMessage("操作失败! " + data.message);
		    		$("#productdg").datagrid('reload');
		    	 }
		     },
		     error : function(data) {
		    	 warningMessage("操作失败");
		    	 $("#productdg").datagrid('reload');
		     }
		});
}

$("#batchUpdatePlabels").click(function(){
	var label = $("#product_label_choosed").val();
	if (!label || label == -1){
		warningMessage("请选择产品标签");
		return ;
	}
	var rows = $('#productdg').datagrid("getSelections");
	var len = $(rows).length ;
    if( len < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    } else {
		var ids = [];
		for(var i=0; i<len; i++){
	        var row = rows[i];
	        ids[i] = row.productId;
	    }
		batchUpdateProductlabels(ids, label,"1");
    }
});

$("#batchRemovePlabels").click(function(){
	var rows = $('#productdg').datagrid("getSelections");
	var len = $(rows).length ;
    if( len < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    } else {
		var ids = [];
		for(var i=0; i<len; i++){
	        var row = rows[i];
	        ids[i] = row.productId;
	    }
		batchUpdateProductlabels(ids, "", "0");
    }
});


function batchUpdateProductlabels(ids, label, option){
	 $.ajax( {
		    url:CONTEXT+'product/batchUpdatePlabels' ,
		    data:{
		    	"productIds" : ids.join(","),
		    	"label" : label,
		    	"option" : option
		    },
		    type:'post',
		    dataType:'json',
		    success:function(data) {
		    	 if (data.status == 1){
			    	warningMessage("操作成功");
			    	$("#productdg").datagrid('reload');
		    	 }else {
		    		warningMessage("操作失败! " + data.message);
		    		$("#productdg").datagrid('reload');
		    	 }
		     },
		     error : function(data) {
		    	 warningMessage("操作失败");
		    	 $("#productdg").datagrid('reload');
		     }
		});
}

function batchRefreshExpire(ids){
	 $.ajax( {
		    url:CONTEXT+'product/batchRefreshExpire' ,
		    data:{
		    	"productIds" : ids.join(",")
		    },
		    type:'post',
		    dataType:'json',
		    success:function(data) {
		    	 if (data.status == 1){
			    	warningMessage("操作成功");
			    	$("#productdg").datagrid('reload');
		    	 }else {
		    		warningMessage("操作失败! " + data.message);
		    		$("#productdg").datagrid('reload');
		    	 }
		     },
		     error : function(data) {
		    	 warningMessage("操作失败");
		    	 $("#productdg").datagrid('reload');
		     }
		});
}

function productUpAndDown(productId, option){


	 $.ajax( {
		    url:CONTEXT+'product/productUpAndDown' ,
		    data:{
		    	productId : productId,
		    	option : option
		    },
		    type:'post',
		    dataType:'json',
		    success:function(data) {
		    	 if (data.status && data.status == 1){
			    	warningMessage("操作成功");
			    	$("#productdg").datagrid('reload');
		    	 }else{
		    		 if (data.message){
			    		 warningMessage(data.message);
		    		 }else{
		    			 warningMessage("操作失败");
		    		 }
		    	 }
		     },
		     error : function(data) {
		    	 $("#productdg").datagrid('reload');
		    	 warningMessage("操作失败");
		     }
		});
}

$(document).ready(function(){

	initOriginPlaceQueryComp();
	initProductList(initState);
	initProductLabels();
	$("#removeBtn").click(function(){
		delProduct();
	});
	$("#offLine").click(function(){
		var rows = $('#productdg').datagrid("getSelections");
		var len = $(rows).length ;
	    if( len < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    } else if (len == 1){
	    	var row = $(rows)[0];
	    	var state = row.state ;
	    	var productId = row.productId;
	    	if (state != 3){
	    		warningMessage("只能操作在售的商品！");
	    		return ;
	    	}
	    	productUpAndDown(productId, "0");
	    } else {
	    	warningMessage("只能操作一条记录！");
	    }
	});

	$("#onLine").click(function(){
		var rows = $('#productdg').datagrid("getSelections");
		var len = $(rows).length ;
	    if( len < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    } else if (len == 1){
	    	var row = $(rows)[0];
	    	var state = row.state ;
	    	if (state != 4){
	    		warningMessage("只能操作已下架的产品！");
	    		return ;
	    	}
	    	var productId = row.productId;
	    	productUpAndDown(productId, "1");
	    } else {
	    	warningMessage("只能操作一条记录！");
	    }
	});

	$("#batchOnLine").click(function(){
		var rows = $('#productdg').datagrid("getSelections");
		var len = $(rows).length ;
		//var len = rows.length;
	    if( len < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    } else {
			var ids = [];
			for(var i=0; i<len; i++){
		        var row = rows[i];
		        ids[i] = row.productId;
		    }
		    batchOnline(ids);
	    }
	});
	$("#batchRefreshExpire").click(function(){
		var rows = $('#productdg').datagrid("getSelections");
		var len = $(rows).length ;
	    if( len < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    } else {
			var ids = [];
			for(var i=0; i<len; i++){
		        var row = rows[i];
		        ids[i] = row.productId;
		    }
			batchRefreshExpire(ids);
	    }
	});

	$("#vendorBox").on("click", function(event){
			if($(this).is(":checked")){
				$("#vendor").val("1");
			}else{
				$("#vendor").val("0");
			}
	});

	$("#serachList").click(function(){
		var switchItem = $('#categorySwitch').val(), categoryId = "";
		if(switchItem == 1){
			categoryId = $("input[name='cateId_q']").val();
			marketId = "";
		}else {
			marketId = $("#marketId_q").val();
			categoryId = "";
		}
		var params = {
				"mobile":$("#mobile").val(),
				"productName" : $("#productName_q").val(),
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				/* "account" : $("#account_q").val(),	//废弃 */
				"accountOrPhone" : $("#account_q").val(),
				"state" : $("#state").val(),
				"categoryId" : categoryId,
				"createUserName" : $("#createUserName_q").val(),
				"vendor":$("#vendor").val(),
				"originProvinceId" : $("#originProvinceId").val(),
				"originCityId" : $("#originCityId").val(),
				"originAreaId" : $("#originAreaId").val(),
				"marketId" : marketId,
				"productLabel" : $("#product_label_choosed").val(),
				"sort" : "createTime",
				"order" : "d"
			};
		loadProduct(params);
	});

	function resetSearchForm(){

		$("#productSearchForm").find('input').val('');
		$('#vendorBox').attr('checked', false);

		$('#showMarketWin_q').val("选择农批中心");
		resetCategoryTypeInput();

		//商品分类开关
		$('#categorySwitch').combobox('select', '1');
		$('#state').combobox('select', '-1');
		$('#product_labels').combobox('select', '-1');
		initOriginPlaceQueryComp();
		//重启导出功能
		disableExport = false ;
	}

	function resetCategoryTypeInput(){
		$("#categoryType_q1").combobox('destroy');
		$("#categoryType_q2").combobox('destroy');
		$("#categoryType_q3").combobox('destroy');
		$("#categoryTypeDiv").html('');
		var input = $('<input id="categoryType_q1" class="easyui-combobox" data-options="editable:false">');
		$("#categoryTypeDiv").append(input).append("&nbsp;");
		$(input).combobox({});
		input = $('<input id="categoryType_q2" class="easyui-combobox" data-options="editable:false">');
		$("#categoryTypeDiv").append(input).append("&nbsp;");
		$(input).combobox({});
		input = $('<input id="categoryType_q3" class="easyui-combobox" data-options="editable:false">');
		$("#categoryTypeDiv").append(input);
		$(input).combobox({});
		$("input[name='cateId_q']").val('');
		$("#marketId_q").val('');
	}

	$("#reloadList").click(function(){
		resetSearchForm();
	});

	//刷新按钮
	$('#icon-refresh').click(function(){
		resetSearchForm();
		var params = {"state" : initState, "sort" : "createTime", "order" : "d"};
		$("#productdg").datagrid('load',params);
		//重启导出功能
		//disableExport = false ;
	});

	$("#toAdd").click(function(){
		//$('#addDialog').dialog({'title':'新增产品', 'width':1000, 'height':500, 'href':CONTEXT+'product/toAdd'}).dialog('open');
		  $('<div></div>').dialog({
              id : 'addDialog',
              title : '新增产品',
              width : 1000,
              height : 500,
              closed : false,
              cache : false,
              href : CONTEXT+'product/toAdd',
              modal : true,
              onLoad : function() {
              },
              onClose : function() {
                  $(this).dialog('destroy');
              },
              onOpen : function() {
            	  $('#priceType').combobox("reload");
              },
              buttons : [ {
                  text : '新增',
                  iconCls : 'icon-save',
                  handler : function() {
                      //提交表单
                      add();
                  }
              }, {
                  text : '关闭',
                  iconCls : 'icon-cancel',
                  handler : function() {
                      $("#addDialog").dialog('destroy');
                  }
              } ],
          });
	});
	$("#icon-refuse-btn").click(function(){
		$('#auditDialog').dialog({'title':'驳回', 'width':900, 'height':400, 'href':CONTEXT+'product/toRefuse'}).dialog('open');
	});

	$('#showMarketWin_q').click(function(){
		$('#marketDialog').dialog({'title':'选择街市', 'width':700, 'height':300, 'href':CONTEXT+'product/marketSelect'}).dialog('open');
	});

});

//分类级联菜单start...
function updateSecondLevel(parentId){
	$('#categoryType_q2').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		editable:false,
		onSelect:function(record){
			updateThirdLevel(record.categoryId);
		},
		onLoadSuccess : function(){
			var data = $('#categoryType_q2').combobox('getData');
			if (data.length > 0) {
				$('#categoryType_q2').combobox('select', data[0].categoryId);
			}
			$('#categoryType_q2').next(".combo").show();
		}
	});
}
function updateThirdLevel(parentId){
	$('#categoryType_q3').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		editable:false,
		onSelect:function(record){
			//updateFourthLevel(record.categoryId);
			//设置商品分类
			$("input[name='cateId_q']").val(record.categoryId);
		},
		onLoadSuccess : function(){
			var data = $('#categoryType_q3').combobox('getData');
			if (data.length > 0) {
				$('#categoryType_q3').combobox('select', data[0].categoryId);
			}
			$('#categoryType_q3').next(".combo").show();
		}
	});
}
//分类级联菜单end...



</script>
</html>

