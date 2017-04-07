<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>member</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
		<div>
			创建时间：
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px"> 
			<select name="level" id="level"  >
				<!--  （1谷登农批，2农速通，3农商友，其余待补） -->
							<option value="">--角色类型--</option>
							<option value="1" >谷登农批</option>
							<option value="2" >农速通</option>
							<option value="3" >农商友</option>
							<option value="4" >产地供应商</option>
							<option value="5" >农批友</option>
			</select>
			<select name="regetype" id="regetype"  >
				<!--  0管理后台，1谷登农批网，2农速通，3农商友，4农商友-农批商，5 农批友  -->
							<option value="">--注册来源--</option>
							<option value="0" >管理后台</option>
							<option value="1" >谷登农批网</option>
							<option value="2" >农速通</option>
							<option value="3" >农商友</option>
							<option value="4" >农商友-农批商</option>
							<option value="5" >农批友  </option>
							<option value="6" >供应商</option>
							<option value="7" >POS刷卡</option>
							<option value="8" >微信授权</option>
			</select>
				注册账号: 
			<input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" >
			手机号：<input  type="text" id="mobile"    class="easyui-validatebox"    name="mobile" style="width:150px" >
			<br/>
			第一次登陆时间：
		     <input  type="text"  id="loginStartDate" name="loginStartDate"  onFocus="WdatePicker({onpicked:function(){loginStartDate.focus();},maxDate:'#F{$dp.$D(\'loginEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){loginStartDate.focus();},maxDate:'#F{$dp.$D(\'loginEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="loginEndDate" name="loginEndDate"   onFocus="WdatePicker({onpicked:function(){loginEndDate.focus();},minDate:'#F{$dp.$D(\'loginStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){loginEndDate.focus();},minDate:'#F{$dp.$D(\'loginStartDate\')}'})"   style="width:150px">  &nbsp;&nbsp;&nbsp;&nbsp;
			
			帐号状态: 
			
				<select name="status"  id="status" >
  							<option value="">全部</option>
 							<option value="1">启用</option>
							<option value="0">禁用</option>
						</select>
				认证状态: 
			
				<select name="isAuth"  id="isAuth" >
  							<option value="">全部</option>
 							<option value="1">已验证</option>
							<option value="0">未验证</option>
							<option value="2">已驳回</option>
						</select>
			  区域名称: 
		    <select id="areaName" name="areaName" class="text_sketch" >
		      <option selected="selected" value="">全部</option>
		      <c:forEach items="${areaNameList}" var="obj">
		    <option value="${obj.areaName}">${obj.areaName}</option>        
		   </c:forEach>
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
		     常用城市: 
		 <input  type="text" id="cityName"  name="cityName"  style="width:150px" ></br>
			<gd:btn btncode='BTNWLHYGLHYGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#memberSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>&nbsp;&nbsp;
		    <a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>&nbsp;&nbsp;
 			<gd:btn btncode='BTNWLHYGLHYGL02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
 			
		</div>
		</form>
	 
		<%-- <div style="margin-bottom:5px">
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
 			<gd:btn btncode='BTNHYGLHYLB07'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
 			
		</div> --%>
		
		<div id="showDialog" class="easyui-dialog"  closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeRefresh();">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function optFormat(value,row,index){
		return "<gd:btn btncode='BTNWLHYGLHYGL03'><a class='operate' href='javascript:;' onclick=showObj('"+row.memberId+"');>查看</a></gd:btn>" ;
	}

	function closeRefresh(){
		$('#showDialog').dialog('close');
		$('#memberdg').datagrid('reload',{
			'mobile': $('#memberSearchForm #mobile').val(),
			'account': $('#memberSearchForm #account').val(),
			'level':$('#memberSearchForm #level').val(),
			'isAuth':$('#memberSearchForm #isAuth').val(),
			'startDate':$('#memberSearchForm #startDate').val(),
			'endDate':$('#memberSearchForm #endDate').val(),
			'areaName':$("#memberSearchForm #areaName").val(),
			'regetype':$("#memberSearchForm #regetype").val(),
			'status':$("#memberSearchForm #status").val(),
			'loginStartDate':$("#memberSearchForm #loginStartDate").val(),
			'loginEndDate':$("#memberSearchForm #loginEndDate").val(),
			'cityName':$("#memberSearchForm #cityName").val()
			});
	}

</script>
<script type="text/javascript" src="${CONTEXT}js/nst_member/main.js"></script>
<script type="text/javascript">
	var disableExport = false ;
	$("#exportData").click(function(){
/* 	 var queryParams = $('#memberdg').datagrid('options').queryParams;
		queryParams.account = $('#memberSearchForm #account').val();
		queryParams.mobile = $('#memberSearchForm #mobile').val();
		queryParams.level = $('#memberSearchForm #level').val();
		queryParams.isAuth = $('#memberSearchForm #isAuth').val();
		queryParams.startDate =  $('#memberSearchForm #startDate').val();
		queryParams.endDate =  $('#memberSearchForm #endDate').val();
		queryParams.areaName = $("#memberSearchForm #areaName").val();
		queryParams.regetype = $("#memberSearchForm #regetype").val();
		queryParams.loginStartDate = $("#memberSearchForm #loginStartDate").val();
		queryParams.loginEndDate = $("#memberSearchForm #loginEndDate").val();
	    queryParams.cityName = $("#memberSearchForm #cityName").val();
		
		window.location.href=CONTEXT+'nst_member/exportData?account='+queryParams.account+
						"&mobile="+queryParams.mobile+"&level="+queryParams.level+"&isAuth="+queryParams.isAuth+
						"&areaName="+queryParams.areaName+"&startDate="+queryParams.startDate+
						"&endDate="+queryParams.endDate+"&regetype="+queryParams.regetype+
						"&cityName="+queryParams.cityName+
						"&loginStartDate="+queryParams.loginStartDate+"&loginEndDate="+queryParams.loginEndDate; */
	
		var queryParams = {
			"account" : $('#memberSearchForm #account').val(),
			"mobile" : $('#memberSearchForm #mobile').val(),
			"level" : $('#memberSearchForm #level').val(),
			"isAuth" : $('#memberSearchForm #isAuth').val(),
			"areaName" : $('#memberSearchForm #areaName').val(),
			"startDate" : $('#memberSearchForm #startDate').val(), 
			"endDate" : $('#memberSearchForm #endDate').val(),
			"regetype" : $("#memberSearchForm #regetype").val(),
			"cityName" : $("#memberSearchForm #cityName").val(),
			"loginStartDate" : $("#memberSearchForm #loginStartDate").val(),
			"loginEndDate" : $("#memberSearchForm #loginEndDate").val()
		};
		
		var paramList = "account="+queryParams.account+
			"&mobile="+queryParams.mobile+"&level="+queryParams.level+"&isAuth="+queryParams.isAuth+
			"&areaName="+queryParams.areaName+"&startDate="+queryParams.startDate+
			"&endDate="+queryParams.endDate+"&regetype="+queryParams.regetype+
			"&cityName="+queryParams.cityName+
			"&loginStartDate="+queryParams.loginStartDate+"&loginEndDate="+queryParams.loginEndDate;
	
		$.ajax({
			url: CONTEXT+'nst_member/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'nst_member/exportData',paramList,'post');
					}else{
						slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
					}
				}else{
					warningMessage(data.message);
				}
			},
			error : function(data){
				warningMessage(data);
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
</html>

