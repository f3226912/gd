<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<table id="datasourcedetaildg" title="">
	</table>
	<div id="datasourcedetailtb" style="padding:5px;height:auto">
		<div>
			数据源名称:${dto.name}    &nbsp;&nbsp;&nbsp;&nbsp;数据最后更新时间:${dto.lastUpdateTimeStr }
			<br>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-save" id="icon-dowloadData">模版下载</a>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-save" id="icon-importData">导入数据</a>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-save" id="icon-exportData">导出数据</a>
		</div>
	</div>
	<div id='Loading' style="position: absolute;opacity: 0.5;filter:Alpha(opacity=50); z-index: 10000; top: 0px; left: 0px;  
        width: 100%; height: 100%; background: gray; text-align: center;display: none;">  
        <h1 style="top: 48%; position: relative;">  
            <font color="#15428B">导入中,请稍候···</font>  
        </h1>  
    </div>

<script type="text/javascript">

function loadProList(){
	//数据加载
	$('#datasourcedetaildg').datagrid({
		url:CONTEXT+'datasource/queryDatasourceBaidu',
		//width: 1000,  
		queryParams: '',
		height: 'auto', 
		nowrap:true,
		toolbar:'#datasourcedetailtb',
		pageSize:10,
		rownumbers:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'pVcount',title:'PV浏览量',width:100,align:'center'},
			{field:'uVcount',title:'UV访客数',width:100,align:'center'},
			{field:'iPcount',title:'IP数',width:100,align:'center'},
			{field:'lastUpdateTime',title:'最后数据更新时间',width:100,align:'center'},
			{field:'signout',title:'退出率',width:100,align:'center'},
			{field:'avstopStr',title:'当日平均停留时长',width:100,align:'center'},
			{field:'newuser',title:'新访客(百分比)',width:100,align:'center'},
			{field:'olduser',title:'老访客(百分比)',width:100,align:'center'},
			{field:'client',title:'所属客户端',width:100,align:'center'}
		]]
	});
	//分页加载
	//$("#datasourcedetaildg").datagrid("getPager").pagination({
	//	pageList: [10,20,50,100]
	//});
}

$(document).ready(function(){
	loadProList();
	$("#icon-exportData").click(function(){
		window.location.href = CONTEXT + 'datasource/exportBaidu';
	});
	
	$("#icon-dowloadData").click(function(){
		window.location.href = CONTEXT + 'datasource/exportBaidu?type=1';
	});
	
	$("#icon-importData").click(function(){
		  $('<div></div>').dialog({
	          id : 'importDialog',
	          title : '导入数据',
	          width : 600,
	          height : 400,
	          closed : false,
	          cache : false,
	          href : CONTEXT+'datasource/toImportBaidu',
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
	                		url:CONTEXT+'datasource/importBaidu',
	              			success: function (data) {
	              		        $("#Loading").hide();
	              				if (data == "success") {
	              					slideMessage("导入成功！");
	              					// 刷新父页面列表
	              					$("#datasourcedetaildg").datagrid('reload');
	              					$('#importDialog').dialog('destroy');
	              				}else if(data == "doubi"){
	              					warningMessage("导入失败！能不传空文件么?");
	              					return;
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
});
</script>

