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
		<title>member page statistic</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
		<div>
		统计时间:
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">
			
		商铺名称:
			<input  type="text" id="shopsName"    class="easyui-validatebox"    name="shopsName" style="width:150px" >
				用户账号:
			<input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" >
			手机号：<input  type="text" id="mobile"    class="easyui-validatebox"    name="mobile" style="width:150px" >
				会员等级:<select name="memberGrade"  id="memberGrade" >
					<option value="">全部</option>
					<option value="0">普通会员</option>
					<option value="1">金牌供应商</option>
				</select>
						
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#memberSearchForm').form('validate');"    >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>

		<div style="margin-bottom:5px">
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
 			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
		</div>

		<div id="editDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>

	</div>
</body>
<script type="text/javascript">
	function optFormat(value,row,index){
		return "<a class='operate' href='javascript:;' onclick=editObj('"+row.memberId+"');>编辑</a>";
	}

	$(document).ready(function(){
		//数据加载
		$('#memberdg').datagrid({
			url:CONTEXT+'memberPageStatistic/querybysearch',
			//width: 1000,
			height: 'auto',
			nowrap:true,
			toolbar:'#membertb',
			pageSize:50,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			onLoadSuccess:function(){
				$("#memberdg").datagrid('clearSelections');
			},
			columns:[[
						{field:'shopsName',title:'商铺名称',width:100,align:'center'},
						{field:'account',title:'用户账号',width:100,align:'center'},
						{field:'mobile',title:'手机号',width:100,align:'center'},
						{field:'createTime',title:'注册时间',width:100,align:'center'},
						{field:'cateName',title:'主营分类',width:100,align:'center'},
						{field:'memberGrade',title:'会员等级',width:100,align:'center',formatter:formatterMemberGrade },
						{field:'pageType',title:'访问类型',width:100,align:'center',formatter:formatterPageType },
						{field:'statisTime',title:'统计时间',width:100,align:'center'},
						{field:'status',title:'账号状态',width:100,align:'center',formatter:formatterStatus }
					]]
		});
		//分页加载
		$("#memberdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	
	});
	
	// 查询按钮,根据name查询
	$('#icon-search').click(function(){
	 var queryParams = {};
		queryParams.account = $('#memberSearchForm #account').val();
		queryParams.mobile = $('#memberSearchForm #mobile').val();
		queryParams.shopsName = $('#memberSearchForm #shopsName').val();
		queryParams.memberGrade = $('#memberSearchForm #memberGrade').val();
		queryParams.startDate = $('#memberSearchForm #startDate').val();
		queryParams.endDate = $('#memberSearchForm #endDate').val();

		var queryUrl=CONTEXT+'memberPageStatistic/querybysearch';
		//if(queryParams.name==''){
		//	queryUrl=CONTEXT+'member/querybybirthday';
	 //}
		//alert(queryParams.endDate)
		$('#memberdg').datagrid({
			url:queryUrl,
			queryParams: queryParams,
			height: 'auto',
			nowrap:true,
			toolbar:'#membertb',
			pageSize:50,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
						{field:'shopsName',title:'商铺名称',width:100,align:'center'},
						{field:'account',title:'用户账号',width:100,align:'center'},
						{field:'mobile',title:'手机号',width:100,align:'center'},
						{field:'createTime',title:'注册时间',width:100,align:'center'},
						{field:'cateName',title:'主营分类',width:100,align:'center'},
						{field:'memberGrade',title:'会员等级',width:100,align:'center',formatter:formatterMemberGrade },
						{field:'pageType',title:'访问类型',width:100,align:'center',formatter:formatterPageType },
						{field:'statisTime',title:'统计时间',width:100,align:'center'},
						{field:'status',title:'账号状态',width:100,align:'center',formatter:formatterStatus }
					]]
		});
		//分页加载
		$("#memberdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});

	});
	
	function formatterPageType(val, row) {
		if (val == 1) {
			return "页面浏览";
		}else if(val == 2){
			return "点击支付";
		}
		return "";
	}

	function formatterdate(val, row) {
			if (val != null) {
			   var  str=val.toString();
			   str =  str.replace(/-/g,"/");
			   var date = new Date(str);
			   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
				+ date.getDate();
			}
	}
	
	function formatterMemberGrade(val, row) {
		if (val == 0) {
		   return "普通会员";
		}else if(val == 1){
		   return "金牌供应商";
		}
	}
	
	function formatterStatus(val, row) {
		if (val != null) {
			var  str=val.toString();
			if(str=="1"){
				return "启用";
			}else if(str=="0"){
				return "禁用";
			}
		}else{
			return "";
		}
	}

	var disableExport = false ;

	$("#exportData").click(function(){
			var params = {
				"account" : $('#memberSearchForm #account').val(),
				"mobile" : $('#memberSearchForm #mobile').val(),
				"shopsName" : $('#memberSearchForm #shopsName').val(),
				"memberGrade" : $('#memberSearchForm #memberGrade').val(),
				"startDate" : $('#memberSearchForm #satrtDate').val(),
				"endDate" : $('#memberSearchForm #endDate').val()
			};
			var paramList = "account=" + params.account + "&mobile="+params.mobile+"&shopsName="+params.shopsName+"&memberGrade="+params.memberGrade
			+"&satrtDate="+params.satrtDate+"&endDate="+params.endDate;

			$.ajax({
				url: CONTEXT+'memberPageStatistic/checkExportParams',
				data : params,
				type:'post',
				async:false,
				success : function(data){
					//检测通过
					if (data && data.status == 1){
						if (!disableExport){
							slideMessage("数据正在导出中, 请耐心等待...");
							disableExport = true ;
							//启动下载
							$.download(CONTEXT+'memberPageStatistic/exportData',paramList,'post' );
						}else{
							slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
						}
					}else{
						warningMessage(data.message);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					warningMessage(textStatus);
				}
			});
/* 			window.location.href=CONTEXT+'member/exportData?account='+queryParams.account+
							"&mobile="+queryParams.mobile+"&level="+queryParams.level+"&isAuth="+queryParams.isAuth+
							"&marketId="+queryParams.marketId+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate+"&regetype="+queryParams.regetype; */
	});
	
	//重置
	$('#btn-reset').click(function(){
		$('#memberSearchForm')[0].reset();
	});

	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#memberSearchForm')[0].reset();
		$("#memberdg").datagrid('load',{});
		//重启导出功能
		disableExport = false ;
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

