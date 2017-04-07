<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE>
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>市场交易额管理主页</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="pricesDatagrid" title="市场交易额">
	</table>
	<div id="pricetb" style="padding:5px;height:auto">
		<form id="pricesSearchForm" method="post">
		<div>
			发布时间：
			<input  type="text"  id="startDate" name="startDate" placeholder="开始时间" 
			class="easyui-validatebox easyui-datebox" editable="false" style="width:150px" >
			至
			<input  type="text"    id="endDate" name="endDate" placeholder="结束时间"
			class="easyui-validatebox easyui-datebox" editable="false" style="width:150px"> &nbsp;&nbsp;
<!-- 			<br> -->
<!-- 			采集地点： -->
<!-- 			<input type="text" id="locale" placeholder="采集地点" -->
<!-- 			class="easyui-validatebox" name="locale" style="width:150px" >&nbsp;&nbsp; -->
<!-- 			商品名称： -->
<!-- 			<input type="text" id="productName" placeholder="商品名称" -->
<!-- 			class="easyui-validatebox" name="productName" style="width:150px" >&nbsp;&nbsp; -->
			<gd:btn btncode='BTNXXFBSCJYE05'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#pricesSearchForm').form('validate');" >查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNXXFBSCJYE01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
<!-- 			<xlc:btn btncode='XZ002'><a class="easyui-linkbutton icon-edit-btn" iconCls="icon-edit" id="icon-edit" >编辑</a></xlc:btn> -->
			<gd:btn btncode='BTNXXFBSCJYE02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
<!-- 			<a id="importData" style="margin-left: 50px;" class="easyui-linkbutton" iconCls="icon-save">导入数据</a> -->
<!-- 			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a> -->
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:320px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
	<div id='Loading' style="position: absolute;opacity: 0.5;filter:Alpha(opacity=50); z-index: 10000; top: 0px; left: 0px;  
        width: 100%; height: 100%; background: gray; text-align: center;display: none;">  
        <h1 style="top: 48%; position: relative;">  
            <font color="#15428B">导入中,请稍候···</font>  
        </h1>  
    </div>  
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNXXFBSCJYE03'><a class='operate' href='javascript:;'onclick=editObj('"+row.id+"');>修改</a></gd:btn>&nbsp;" +
		"<gd:btn btncode='BTNXXFBSCJYE02'><a class='operate' href='javascript:;'onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/jquery.form.js"></script>
<script type="text/javascript" src="${CONTEXT}js/marketSale/index.js"></script>
<script type="text/javascript" src="${CONTEXT}js/marketSale/add.js"></script>

<script type="text/javascript">

$("#importData").click(function(){
	  $('<div></div>').dialog({
          id : 'importDialog',
          title : '导入数据',
          width : 600,
          height : 400,
          closed : false,
          cache : false,
          href : CONTEXT+'marketprice/toImportData',
          modal : true,
          onLoad : function() {
              //初始化表单数据
          },
          onClose : function() {
              $(this).dialog('destroy');
          },
          buttons : [ {
              text : '导入',
              iconCls : 'icon-save',
              handler : function() {
            	   $(this).hide();
                  //提交表单,由于导入需要花一定的时间，因此提交过程中，要有“导入中。。。。”提示，
                    $("#Loading").show();
                    $("#importDataForm").ajaxSubmit({
                		type:'post',
                		url:CONTEXT+'marketprice/importData',
              			success: function (data) {
              		        $("#Loading").hide();
              				if (data == "success") {
              					slideMessage("导入成功！");
              					// 刷新父页面列表
              					$("#pricesDatagrid").datagrid('reload');
              					$('#importDialog').dialog('destroy');
              				} else {
              					warningMessage("导入失败！");
              					return;
              				}
              	        }
              		});
                    return false; // 阻止表单自动提交事件
              }
          }, {
              text : '取消',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#importDialog").dialog('destroy');
              }
          } ],
      });
});

/***数据导出功能***/
$("#exportData").click(function(){
	var productName = $('#pricesSearchForm #productName').val();
	var locale = $('#pricesSearchForm #locale').val();
	var startDate = $("#startDate").datetimebox("getValue");
	var endDate = $("#endDate").datetimebox("getValue");
	window.location.href=CONTEXT+'marketprice/exportData?productName='+productName+
			"&locale="+locale+"&startDate="+startDate+"&endDate="+endDate;
});
</script>
</html>

