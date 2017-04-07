<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>市场价格管理主页</title>
	</head>
		<%@ include file="../pub/head.inc" %>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="pricesDatagrid" title="市场价格">
	</table>
	<div id="pricetb" style="padding:5px;height:auto">
		<form id="pricesSearchForm" method="post">
		<div>
			发布时间：
			<input  type="text"  id="startDate" name="startDate" placeholder="开始时间"
			class="easyui-validatebox easyui-datetimebox" editable="false" style="width:150px" >
			至
			<input  type="text"    id="endDate" name="endDate" placeholder="结束时间"
			class="easyui-validatebox easyui-datetimebox" editable="false" style="width:150px">
			<br>
			采集市场：
			<select id="maketId" name="maketId">
			</select>&nbsp;&nbsp;
			商品名称：
			<input type="text" id="productName" placeholder="商品名称"
			class="easyui-validatebox" name="productName" style="width:150px" >&nbsp;&nbsp;
			<gd:btn btncode='BTNXXFBNSHQ06'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#pricesSearchForm').form('validate');" >查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNXXFBNSHQ01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
			<gd:btn btncode='BTNXXFBNSHQ03'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNXXFBNSHQ04'><a id="importData" class="easyui-linkbutton" iconCls="icon-save">导入数据</a></gd:btn>
			<gd:btn btncode='BTNXXFBNSHQ05'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>

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
  	<div id='Loading2' style="position: absolute;opacity: 0.5;filter:Alpha(opacity=50); z-index: 10000; top: 0px; left: 0px;
        width: 100%; height: 100%; background: gray; text-align: center;display: none;">
        <h1 style="top: 48%; position: relative;">
            <font color="#15428B">导入中,请稍候···</font>
        </h1>
    </div>
</body>

<script type="text/javascript">
function optformat(value,row,index){
	var html="";
	html+="<gd:btn btncode='BTNXXFBNSHQ02'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>修改</a></gd:btn>&nbsp;";
	html+="<gd:btn btncode='BTNXXFBNSHQ03'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
	return html;
}


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
            	  if($('#importDataForm').form('validate')){
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

var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var productName = $('#pricesSearchForm #productName').val();
	var maketId = $('#pricesSearchForm #maketId').val();
	var startDate = $("#startDate").datetimebox("getValue");
	var endDate = $("#endDate").datetimebox("getValue");

	var params = {
			"productName" : productName,
			"maketId" : maketId,
			"startDate" : startDate,
			"endDate" : endDate
		};
	var paramList = 'productName=' + productName+ "&maketId="+maketId+"&startDate="+startDate+"&endDate="+endDate;
	$.ajax({
		url: CONTEXT+'marketprice/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				/* $("#Loading2").show(); */

				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'marketprice/exportData',paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}
				/* $("#Loading2").hide(); */
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage("error : " + data);
		}
	});

/* 	window.location.href=CONTEXT+'marketprice/exportData?productName='+productName+
			"&maketId="+maketId+"&startDate="+startDate+"&endDate="+endDate; */
});

jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};
</script>
<script type="text/javascript" src="${CONTEXT}js/jquery.form.js"></script>
<script type="text/javascript" src="${CONTEXT}js/marketPrice/index.js"></script>
<script type="text/javascript" src="${CONTEXT}js/marketPrice/add.js"></script>
<script type="text/javascript">
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#maketId').empty();
				$('#maketId').append("<option value='-99'>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#maketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
initMarket(2);
</script>
</html>

