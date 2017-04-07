<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
	<title>市场铺位</title>
	<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css" rel="stylesheet"  >
		#searchbox{height: 80px; padding: 10px; margin: 0; }
	</style>
</head>

<body>
	<table id="datagrid"></table>
	<!-- <div class="toggle" style="height: 17px; width:20px; margin-top:7px;
		float: right; padding-right: 8px; cursor: pointer; 
		background-image: url('../images/layout_arrows.png'); background-repeat:no-repeat; 
		background-position: -18px 0px;">
	</div> -->
	<div id="searchbox">  
		<form id="search_fm" method="post">
			<div class="fitem">
			 	市场名称：<select id="market" class="easyui-combobox" name="market" style="width:150px;">
   					   </select>&nbsp;&nbsp;
			    <gd:btn btncode='BTNSCPWGL01'><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMarket()">查询</a></gd:btn><br/><br/>
			    <gd:btn btncode='BTNSCPWGL02'><a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addMarket()">新增</a></gd:btn>&nbsp;&nbsp;&nbsp;&nbsp;
			    <gd:btn btncode='BTNSCPWGL03'><a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadMarket()">刷新</a></gd:btn>
			</div>
		</form>
	</div>
	
	<!-- 查询市场街区 -->
	<div id="dlg" class="easyui-dialog"
		style="width: 600px; height: 450px; padding: 10px 20px; " closed="true"
		buttons="#dlg-buttons" title="dlg-title">
		<div class="ftitle">
			
		</div>
		<form id="fm" method="post" style="padding-top: 15px">
			<div style="width: 150px; height: 25px; float: left; line-height: 25px;">所属市场：</div>
			<div style="width: 300px; height: 25px; float: left;"><select id="seeMarketId" class="easyui-combobox" name="marketName" style="width:150px;"></select></div>
			<div style="width: 150px; height: 50px; float: left; line-height: 50px; clear: both;">市场街区商铺数量</div>
			<div id="marketMax" style="width: 100%; height: auto; clear: both;">
				
			</div>
			<div style="width:100%; border-bottom: 1px gray solid;"></div>
			<div style="width:100%; height: 25px;"></div>
			<table id="berthdatagrid"></table>
		</form>
	</div>
	
	<!-- 添加市场街区 -->
	<div id="adddlg" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px; " closed="true"
		buttons="#dlg-buttons" title="dlg-title">
		<div class="ftitle">
			
		</div>
		<form id="addfm" method="post" style="padding-top: 15px">
			<div style="width: 150px; height: 25px; float: left; line-height: 25px;">所属市场：</div>
			<div style="width: 150px; height: 25px; float: left;"><select id="market_add" class="easyui-combobox" name="market_add" style="width:150px;"></select></div>
			<div style="width: 150px; height: 50px; float: left; line-height: 50px; clear: both;">市场街区商铺数量</div>
			<div id="addMarketMax" style="width: 100%; height: auto; clear: both;">
				
			</div>
			<a href="javascript:void(0)" onClick="addMax()">添加更多市场街区</a>
		</form>
		<div id="dlg-buttons">
			<a href="#" class="easyui-linkbutton submit_btn" iconCls="icon-ok">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#adddlg').dialog('close')">取消</a>
		</div>
	</div>
	
	
	<!-- 编辑市场街区 -->
	<div id="editdlg" class="easyui-dialog"
		style="width: 600px; height: 450px; padding: 10px 20px; " closed="true"
		buttons="#edit-dlg-buttons" title="dlg-title">
		<div class="ftitle">
			
		</div>
		<form id="editfm" method="post" style="padding-top: 15px">
			<input type="hidden" id="editMarketId" name="editMarketId"/>
			<div style="width: 150px; height: 25px; float: left; line-height: 25px;">所属市场：</div>
			<div style="width: 300px; height: 25px; float: left;"><select id="editMarketId_2" class="easyui-combobox" name="marketName" style="width:150px;"></select></div>
			<div style="width: 150px; height: 50px; float: left; line-height: 50px; clear: both;">市场街区商铺数量</div>
			<div id="editMarketMax" style="width: 100%; height: auto; clear: both;">
				
			</div>
			<a href="javascript:void(0)" onClick="editMax()">添加更多市场街区</a>
			<a href="#" class="easyui-linkbutton edit_submit_btn" iconCls="icon-ok" style="margin-left: 5px;">提交</a>
			<div style="width:100%; height: 20px;"></div>
			<div style="width:100%; border-bottom: 1px gray solid;"></div>
			<div style="width:100%; margin: 5px;margin-right: 10px;">
			市场商铺编号
			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save" style="margin-left: 30px;">导出</a>
			<a id="importData" class="easyui-linkbutton" iconCls="icon-save" style="margin-left: 5px;">导入</a>
			</div>
			<table id="editberthdatagrid"></table>
		</form>
		
		<div id="edit-dlg-buttons">
<!-- 			<a href="#" class="easyui-linkbutton edit_submit_btn" iconCls="icon-ok">提交</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
				onclick="javascript:$('#editdlg').dialog('close')">取消</a>
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

var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var maketId = $('#editMarketId').val();
	var params = {
			"maketId" : maketId
		};
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);
	if (!disableExport){
		slideMessage("数据正在导出中, 请耐心等待...");
		disableExport = true ;
		//启动下载
		$.download(CONTEXT+'marketberth/exportData',paramList,'post' );
	}else{
		slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
	}
});

$("#importData").click(function(){
	  $('<div></div>').dialog({
        id : 'importDialog',
        title : '导入数据',
        width : 300,
        height : 200,
        closed : false,
        cache : false,
        href : CONTEXT+'marketberth/toImportData',
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
          	  if($('#importDataForm').form('validate')){
          	   $(this).hide();
                //提交表单,由于导入需要花一定的时间，因此提交过程中，要有“导入中。。。。”提示，
                  $("#Loading").show();
                  $("#importDataForm").ajaxSubmit({
              		type:'post',
              		url:CONTEXT+'marketberth/importData',
            			success: function (data) {
            		        $("#Loading").hide();
            				if (data == "success") {
            					slideMessage("导入成功！");
            					// 刷新父页面列表
            					$("#editberthdatagrid").datagrid('reload');
            					$('#importDialog').dialog('destroy');
            				} else {
            					warningMessage("导入失败！");
            					return;
            				}
            	        }
            		});
          	  }
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


</script>
<script type="text/javascript" src="${CONTEXT}js/market/newmarketberth.js"></script>
</html>