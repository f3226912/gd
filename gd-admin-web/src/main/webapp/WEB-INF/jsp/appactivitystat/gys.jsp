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
<title></title>
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
		<td align="right">注册来源：</td>
		<td><select name="regetype" id="regetype" style="width:150px;height:20px;margin-right: 10px;">
		<!-- 0管理后台,1谷登农批网,2农速通(旧),3农商友,4农商友-农批商,5农批友,6供应商,7POS刷卡 ,8微信授权,9农速通-货主,10农速通-司机,11农速通-物流公司 -->
				<option value="" selected="selected">全部</option>	
				<option value="0" >管理后台</option>		
				<option value="1" >谷登农批网</option>		
				<option value="2" >农速通(旧)</option>		
				<option value="3" >农商友</option>
				<option value="4" >农商友-农批商</option>
				<option value="5" >农批商</option>
				<option value="6" >供应商</option>
				<option value="7" >POS刷卡</option>
				<option value="8" >微信授权</option>
				<option value="9" >货主</option>
				<option value="10" >车主</option>
				<option value="11" >物流公司</option>
		</select>	</td>
		
		<td align="right">经营类型：</td>
		<td><select name="managementtype" id="managementtype" style="width:150px;height:20px;">
		<!-- '经营类型，1表示种养大户，2表示合作社，3表示基地' -->
				<option value="" selected="selected">全部</option>	
				<option value="1" >种养大户</option>		
				<option value="2" >合作社</option>		
				<option value="3" >基地</option>
		</select></td>
		<td align="right">主营分类：</td>
		<td><select name="categoryId" id="categoryId" style="width:150px;height:20px;">
				<option value="" selected="selected">全部</option>	
				<c:forEach var="record"   items="${catelist }">
				<option value="${record.categoryId }">${record.cateName }</option>	
				</c:forEach>
		</select></td>	
		</tr>
		
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
		<td>	<input  type="text"  id="queryStartDate" name="queryStartDate"  
			onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}',dateFmt:'yyyy-MM-dd'})"    
			onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}',dateFmt:'yyyy-MM-dd'})"    style="width:120px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   
			onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}',dateFmt:'yyyy-MM-dd'})"   
			onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}',dateFmt:'yyyy-MM-dd'})"   style="width:120px"> 
		</td>						
		<td>
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a></td>
		</tr></table>
	</div>
	</form>
	<div style="margin-bottom:5px">
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
	</div>
</div>
</body>
<script type="text/javascript">
var appType = 3;
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
			"regetype" : $("#regetype").val(),
			"managementtype" : $("#managementtype").val(),
			"categoryId" : $("#categoryId").val(),
			"system" :$("#system").val(),
			"appVersion" :$("#appVersion").val(),
			"queryStartDate" :$("#queryStartDate").val(),
			"queryEndDate" :$("#queryEndDate").val()
	};
	var appType = 3;
	//var paramList = 'teamName=' + teamName + '&createTime=' + createTime +'&cellphoneRAM=' + cellphoneRAM +'&mainProduct=' + mainProduct +'&type=' + type +'&teamId=' + teamId +'&id=' + id +'&marketName=' + marketName +'&deviceIMEI=' + deviceIMEI +'&nsyUserType=' + nsyUserType +'&deviceUUID=' + deviceUUID +'&appType=' + appType +'&isLogin=' + isLogin +'&marketId=' + marketId +'&managementtype=' + managementtype +'&cellphoneModel=' + cellphoneModel +'&cellphoneROM=' + cellphoneROM +'&deviceICCID=' + deviceICCID +'&system=' + system +'&systemVersion=' + systemVersion +'&appVersion=' + appVersion +'&deviceMEID=' + deviceMEID +'&account=' + account +'&memberId=' + memberId +'&regetype=' + regetype +'&appChannel=' + appChannel +'&userCreateTime=' + userCreateTime +'&mobile=' + mobile;
	$.ajax({
		url: CONTEXT+'appactivitystat/checkExportParams?appType='+appType,
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
					$.download(CONTEXT+'appactivitystat/exportData?appType='+appType,params,'post' );
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
<script type="text/javascript" src="${CONTEXT}js/appactivitystat/gys.js"></script>
</html>