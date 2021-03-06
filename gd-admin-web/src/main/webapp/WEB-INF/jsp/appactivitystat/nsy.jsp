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
		手机号码：
		<input type="text" id="mobile" name="mobile" placeholder="手机号码" class="easyui-validatebox" style="width:150px" >
		注册来源：
		<select name="regetype" id="regetype" style="width:150px;height:20px;margin-right: 10px;">
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
		</select>	
		
		农商友用户类型:
				<select name="nsyUserType" id="nsyUserType" style="width:150px;height:20px;margin-right: 10px;">
		<!-- 1下游批发商,2生鲜超市,3学校食堂,4食品加工工厂,5社区门店,6餐厅,7垂直生鲜,8其它 -->
				<option value="" selected="selected">全部</option>	
				<option value="1" >下游批发商</option>		
				<option value="2" >生鲜超市</option>		
				<option value="3" >学校食堂</option>
				<option value="4" >食品加工工厂</option>
				<option value="5" >社区门店</option>
				<option value="6" >餐厅</option>
				<option value="7" >垂直生鲜</option>
				<option value="8" >其他</option>
		</select>	
		所属平台：
		<select name="system" id="system" style="width:150px;height:20px;margin-right: 10px;">
		<!-- 1IOS,2Android -->
				<option value="" selected="selected">全部</option>	
				<option value="1" >IOS</option>		
				<option value="2" >Android</option>		
		</select>
		
		客户端版本号：
		<input type="text" id="appVersion" name="appVersion" placeholder="客户端版本号" class="easyui-validatebox" style="width:150px" ><br/>
		所属渠道：
		<select name="appChannel" id="appChannel" style="width:150px;height:20px;margin-right: 10px;">
				<option value="" selected="selected">全部</option>	
				<c:forEach var="record"   items="${channelList }">
				<option value="${record.channelName }">${record.channelName }</option>	
				</c:forEach>
				 
		</select>
		
		统计时间：
			<input  type="text"  id="queryStartDate" name="queryStartDate"  
			onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}',dateFmt:'yyyy-MM-dd'})"    
			onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}',dateFmt:'yyyy-MM-dd'})"    style="width:120px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   
			onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}',dateFmt:'yyyy-MM-dd'})"   
			onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}',dateFmt:'yyyy-MM-dd'})"   style="width:120px"> 
								
		
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
	</div>
	</form>
	<div style="margin-bottom:5px">
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
	</div>
</div>
</body>
<script type="text/javascript">
var appType = 1;
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
			"nsyUserType" : $("#nsyUserType").val(),
			"system" :$("#system").val(),
			"appVersion" :$("#appVersion").val(),
			"appChannel" :$("#appChannel").val(),
			"queryStartDate" :$("#queryStartDate").val(),
			"queryEndDate" :$("#queryEndDate").val()
	};
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
<script type="text/javascript" src="${CONTEXT}js/appactivitystat/nsy.js"></script>
</html>