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

	<table id="productdg" title="">
	</table>
	<div id="producttb" style="padding:5px;height:auto">
		<div>
			<form id="productSearchForm" method="post" action="">
				商品名称: <input  type="text" id="productName" name="productName" style="width:150px" data-options="prompt:'请输入名称">&nbsp;
				农批市场 : <input type="button" id="chooseMarket" value="选择农批市场">
				商品类目&nbsp; :
		        <input id="categorySwitch" name="categorySwitch" class="easyui-combobox"/>
		        <!-- 分类Panel -->
				<div style="display:inline;" id="categoryTypeDiv">
					<input id="categoryType_q1" class="easyui-combobox" data-options="editable:false">
					<input id="categoryType_q2" class="easyui-combobox" data-options="editable:false">
					<input id="categoryType_q3" class="easyui-combobox" data-options="editable:false">
				</div>
			     <!-- 选中的三级分类 -->
				<input type="hidden" id="selectedCategoryId" name="selectedCategoryId" value="" >
				<br>
				创建人 : <input  type="text" id="createUserName" name="createUserName" style="width:150px" data-options="prompt:'请输入创建人">
				创建时间 :
				<input  type="text"  id="startDate" name="startDate"
					onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
					onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
					style="width:150px" >~
				<input  type="text"    id="endDate" name="endDate"
					onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"
					onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">
				<!-- 查询 -->
				<gd:btn btncode='BTNSPGLQBCP08'>
					<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="serachList">查询</a>
				</gd:btn>
					<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="reloadList">重置</a>
		     </form>
		</div>
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNSPGLQBCP01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="toAddProduct">新增</a></gd:btn>
			<gd:btn btncode='BTNSPGLQBCP02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="removeBtn">删除</a></gd:btn>
			<gd:btn btncode='BTNSPGLQBCP09'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		<div id="detailDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
	
</body>


<script type="text/javascript">

/* 全局环境变量 start */
// 导出开关
var disableExport = false ;
// 运营分类市场
var marketIdForList = -1 ;

</script>

<script type="text/javascript" src="${CONTEXT}js/fastProduct/fastProductList.js"></script>
<script type="text/javascript" src="${CONTEXT}js/jquery.cookie.js"></script>
<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>

<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>

<script type="text/javascript">

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

	
	//分类查询开关(全部分类/选择分类)
	initProductCategorySwitch();
	// 初始化产品列表
	initProductList(); 
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

	// 查询
	$("#serachList").click(function(){
		var switchItem = $('#productSearchForm #categorySwitch').val();
		var categoryId = $("#productSearchForm input[name='selectedCategoryId']").val();
		if(switchItem != 1){
			categoryId = "";
		}	
		var params = {
				"productName" : $("#productSearchForm #productName").val(),
				"startDate" : $("#productSearchForm #startDate").val(),
				"endDate" : $("#productSearchForm #endDate").val(),
				"categoryId" : categoryId,
				"createUserName" : $("#productSearchForm #createUserName").val(),
				"sort" : "createTime",
				"order" : "d"
			};
		loadProduct(params);
	});

	// 重置
	$("#reloadList").click(function(){
		resetSearchForm();
	});
	
	function resetSearchForm(){
		// 重新加载 商品分类开关
		$('#productSearchForm #categorySwitch').combobox('reload');

		// 查询条件(商品名称/创建人/创建时间)
		$("#productSearchForm input[type='text']").val('');
		// 重启导出功能
		disableExport = false ;
		// 市场
		$("#chooseMarket").val('选择农批市场');
		marketIdForList = -1;
		// 商品分类
		resetCategoryTypeInput();
	}

	function resetCategoryTypeInput(){
		if (marketIdForList && marketIdForList != -1){
			//重置分类组合框(销毁原组件-->添加新组件)
			$("#productSearchForm #categoryType_q1").combobox('destroy');
			$("#productSearchForm #categoryType_q2").combobox('destroy');
			$("#productSearchForm #categoryType_q3").combobox('destroy');
			$("#categoryTypeDiv").html('');
			// 一级分类
			var category = $('<input id="categoryType_q1" class="easyui-combobox" data-options="editable:false">');
			$(category).combobox({});
			$("#categoryTypeDiv").append(category).append("&nbsp;");
			// 二级分类
			category = $('<input id="categoryType_q2" class="easyui-combobox" data-options="editable:false">');
			$("#categoryTypeDiv").append(category).append("&nbsp;");
			$(category).combobox({});
			//三级分类
			category = $('<input id="categoryType_q3" class="easyui-combobox" data-options="editable:false">');
			$("#categoryTypeDiv").append(category);
			$(category).combobox({});
			//初始化分类组件
			initProductCategory(marketIdForList);
			$("input[name='selectedCategoryId']").val('');
		}
	}


	$("#toAddProduct").click(function(){
		  // 动态创建新增页面(动态销毁)
		  $('<div></div>').dialog({
              id : 'addDialog',
              title : '新增产品',
              width : 1000,
              height : 500,
              closed : false,
              cache : false,
              href : CONTEXT+'fastProduct/toAddProduct',
              modal : true,
              onLoad : function() {
              },
              onClose : function() {
                  $(this).dialog('destroy');
              },
              onOpen : function() {
            	  $('#priceType').combobox("reload");
              },
              buttons : [ 
            	  {
	                  text : '新增',
	                  iconCls : 'icon-save',
	                  handler : function() {
	                      //提交表单
	                      addFastProduct();
	                  }
              	  }, 
	              	  {
	                  text : '关闭',
	                  iconCls : 'icon-cancel',
	                  handler : function() {
	                      $("#addDialog").dialog('destroy');
	                  }
              	  } ],
          });
	});
	
	$("#chooseMarket").click(function(){
		  // 动态创建新增页面(动态销毁)
		  $('<div></div>').dialog({
	        id : 'marketSelectDialog',
	        title : '选择市场',
	        width : 1000,
	        height : 500,
	        closed : false,
	        cache : false,
	        href : CONTEXT+'fastProduct/toMarketSelect/0', // 0-调用marketSelectCallBackForList回调函数, 1-调用marketSelectCallBack
	        modal : true,
	        onLoad : function() {
	        },
	        onClose : function() {
	            $(this).dialog('destroy');
	        },
	        onOpen : function() {
	        }
	    });		
	});
	
});

function marketSelectCallBackForList(index, row){
	$("#chooseMarket").val(row.marketName);
	//初始化分类组件
	marketIdForList = row.id ;
	initProductCategory(marketIdForList);
}



</script>
</html>

