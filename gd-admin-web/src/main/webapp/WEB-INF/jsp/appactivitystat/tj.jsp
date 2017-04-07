<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title>铁军</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="datagrid-table" title=""></table>
<div id="datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="datagrid-form" method="post">
	<div>
				<table  cellpadding="1">
					<tr>	
	
		<td align="right">手机号码：</td>
		<td><input type="text" id="mobile" name="mobile" placeholder="手机号码" class="easyui-validatebox" style="width:150px" ></td>
		<td align="right">姓&nbsp;&nbsp;名：</td>
		<td><input type="text" id="account" name="account" placeholder="姓名" class="easyui-validatebox" style="width:150px" ></td>
		<!-- onchange="team()" -->
		<td align="right">所属市场：</td>
		<td><select id="marketId" name="marketId"  style="width:150px;height:20px;margin-right: 10px;">
		<option value=''>全部</option>
		</select>
		</td>
		</tr>
<!-- 		所属团队：
		<select id="teamId" name="teamId" style="width:150px;height:20px;margin-right: 10px;">
		<option value=''>全部</option>
		</select> -->
		<tr>	
		<td align="right">所属平台：</td>
		<td><select name="system" id="system" style="width:150px;height:20px;margin-right: 10px;">
		<!-- 1IOS,2Android -->
				<option value="" selected="selected">全部</option>	
				<option value="1" >IOS</option>		
				<option value="2" >Android</option>		
		</select></td>
		
		<td align="right">客户端版本号：</td>
		<td><input type="text" id="appVersion" name="appVersion" placeholder="客户端版本号" class="easyui-validatebox" style="width:150px" ></td>

		
		<td align="right">统计时间：</td>
		<td><input  type="text"  id="queryStartDate" name="queryStartDate"  
			onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}',dateFmt:'yyyy-MM-dd'})"    
			onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}',dateFmt:'yyyy-MM-dd'})"    style="width:120px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   
			onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}',dateFmt:'yyyy-MM-dd'})"   
			onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}',dateFmt:'yyyy-MM-dd'})"   style="width:120px"></td> 
								
		
		<td align="right"><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a></td>
		</tr>
		</table>
	</div>
	</form>
	<div style="margin-bottom:5px">
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
	</div>
</div>
</body>
<script type="text/javascript">
var appType = 4;
initMarket(2);
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#marketId').empty();
				$('#marketId').append("<option value=''>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#marketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}

function team(){
	var marketId = $('#marketId').val();
	if(marketId == null || marketId == ""){
		$('#teamId').empty();
		$('#teamId').append("<option value=''>全部</option>");
	} else {
		$.ajax({
			type: "GET",
			url: "${CONTEXT }appactivitystat/team?marketId=" + marketId,
			dataType: "json",
			success: function(data) {
				$('#teamId').empty();
				$('#teamId').append("<option value=''>全部</option>");
				if (data.length != 0) {
					for ( var n = 0; n < data.length; n++) {
						$('#teamId').append("<option value='"+data[n].id+"'>"+data[n].name+"</option>");
					}
				}
			}
		});
	}
}


function optformat(value,row,index){
	var html="";
	html+="<a class='operate' href='javascript:;' onclick=view('"+row.id+"');>查看</a>";
	html+="<a class='operate' href='javascript:;' onclick=edit('"+row.id+"');>修改</a>&nbsp;";
	html+="<a class='operate' href='javascript:;' onclick=delete('"+row.id+"');>删除</a>";
	return html;
}
var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var teamName = $('#datagrid-form #teamName').val();
	var createTime = $('#datagrid-form #createTime').val();
	var cellphoneRAM = $('#datagrid-form #cellphoneRAM').val();
	var mainProduct = $('#datagrid-form #mainProduct').val();
	var type = $('#datagrid-form #type').val();
	var teamId = $('#datagrid-form #teamId').val();
	var id = $('#datagrid-form #id').val();
	var marketName = $('#datagrid-form #marketName').val();
	var deviceIMEI = $('#datagrid-form #deviceIMEI').val();
	var nsyUserType = $('#datagrid-form #nsyUserType').val();
	var deviceUUID = $('#datagrid-form #deviceUUID').val();
	var appType = $('#datagrid-form #appType').val();
	var isLogin = $('#datagrid-form #isLogin').val();
	var marketId = $('#datagrid-form #marketId').val();
	var managementtype = $('#datagrid-form #managementtype').val();
	var cellphoneModel = $('#datagrid-form #cellphoneModel').val();
	var cellphoneROM = $('#datagrid-form #cellphoneROM').val();
	var deviceICCID = $('#datagrid-form #deviceICCID').val();
	var system = $('#datagrid-form #system').val();
	var systemVersion = $('#datagrid-form #systemVersion').val();
	var appVersion = $('#datagrid-form #appVersion').val();
	var deviceMEID = $('#datagrid-form #deviceMEID').val();
	var account = $('#datagrid-form #account').val();
	var memberId = $('#datagrid-form #memberId').val();
	var regetype = $('#datagrid-form #regetype').val();
	var appChannel = $('#datagrid-form #appChannel').val();
	var userCreateTime = $('#datagrid-form #userCreateTime').val();
	var mobile = $('#datagrid-form #mobile').val();

	var queryStartDate = $("#queryStartDate").val();
	var queryEndDate = $("#queryEndDate").val();
	if(queryStartDate==null||queryStartDate==""
			||queryEndDate==null||queryEndDate==""){
		warningMessage("请选择正确的日期范围，系统最大支持为31天！");
		return false;
	}
	
	var params = {
			"mobile" : $("#mobile").val(),
			"account" : $("#account").val(),
			"marketId" : $("#marketId").val(),
			"teamId" : $("#teamId").val(),
			"system" :$("#system").val(),
			"appVersion" :$("#appVersion").val(),
			"queryStartDate" :$("#queryStartDate").val(),
			"queryEndDate" :$("#queryEndDate").val()
	};
	//var paramList = 'teamName=' + teamName + '&createTime=' + createTime +'&cellphoneRAM=' + cellphoneRAM +'&mainProduct=' + mainProduct +'&type=' + type +'&teamId=' + teamId +'&id=' + id +'&marketName=' + marketName +'&deviceIMEI=' + deviceIMEI +'&nsyUserType=' + nsyUserType +'&deviceUUID=' + deviceUUID +'&appType=' + appType +'&isLogin=' + isLogin +'&marketId=' + marketId +'&managementtype=' + managementtype +'&cellphoneModel=' + cellphoneModel +'&cellphoneROM=' + cellphoneROM +'&deviceICCID=' + deviceICCID +'&system=' + system +'&systemVersion=' + systemVersion +'&appVersion=' + appVersion +'&deviceMEID=' + deviceMEID +'&account=' + account +'&memberId=' + memberId +'&regetype=' + regetype +'&appChannel=' + appChannel +'&userCreateTime=' + userCreateTime +'&mobile=' + mobile;
	$.ajax({
		url: CONTEXT+'appactivitystat/checkExportParams?appType=4',
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
					$.download(CONTEXT+'appactivitystat/exportData?appType=4',params,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage("error : " + data);
		}
	});
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
<script type="text/javascript" src="${CONTEXT}js/appactivitystat/tj.js"></script>
</html>