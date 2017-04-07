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
		<title>bussiness pv</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
		<div>
		商铺名称:
			<input  type="text" id="shopsName"    class="easyui-validatebox"    name="shopsName" style="width:150px" >
				用户账号:
			<input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" >
			手机号：<input  type="text" id="mobile"    class="easyui-validatebox"    name="mobile" style="width:150px" >
				商铺所属市场：
				<select name="marketId" id="marketId">
					<option value="">全部</option>
					<c:forEach items="${marketList }" var="market">
					<option value="${market.id }">${market.marketName }</option>
					</c:forEach>
				</select>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#memberSearchForm').form('validate');"    >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>

		<div style="margin-bottom:5px">
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
 			<!-- 
 			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
 			 -->
		</div>

		<div id="editDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
	<div id="editDialog2" class="easyui-dialog"  closed="true" modal="true">
		<div style="margin-left:20px;margin-top:20px;width:300px;">
		<input type="hidden" name="id" value=""/>
		<table width="100%" border="0" class="comtable5">
			<tr>
				<td><span style="color:red">*</span>浏览量:</td>
				<td><input name="shopPv" maxlength="10" onkeyup="this.value=this.value.replace(/\D/g,'')"/></td>
			</tr>
		</table>
		</div>
		<div style="text-align:center;margin-top:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#editDialog2').dialog('close')">取消</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="javascript:editDialog2Save()">保存</a>
		</div>
	</div>

	</div>
</body>
<script type="text/javascript">
function optFormat(value,row,index){
	return "<a class='operate' href='javascript:;' onclick=editObj2("+row.id+","+row.shopPv+");>编辑</a>";
}
</script>
<script type="text/javascript">

	$(document).ready(function(){
		//数据加载
		$('#memberdg').datagrid({
			url:CONTEXT+'pvStatisticBusiness/querybysearch',
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
						{field:'id',title:'',width:100,hidden:true, align:'center'},
						{field:'shopsName',title:'商铺名称',width:100,align:'center'},
						{field:'account',title:'用户账号',width:100,align:'center'},
						{field:'mobile',title:'手机号',width:100,align:'center'},
						{field:'createTime',title:'注册时间',width:100,align:'center'},
						{field:'marketName',title:'商铺所属市场',width:100,align:'center'},
						{field:'fromPage',title:'统计界面',width:100,align:'center',formatter:formatterFromPage },
						{field:'shopPv',title:'当前浏览量',width:100,align:'center'},
						{field:'statisTime',title:'统计时间',width:100,align:'center',formatter:formatterdate},
						{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
					]]
		});
		//分页加载
		$("#memberdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	
	});
	
	// 查询按钮,根据name查询
	$('#icon-search').click(function(){
	 var queryParams = $('#memberdg').datagrid('options').queryParams;
		queryParams.account = $('#memberSearchForm #account').val();
		queryParams.mobile = $('#memberSearchForm #mobile').val();
		queryParams.shopsName = $('#memberSearchForm #shopsName').val();
		queryParams.marketId =  $('#memberSearchForm #marketId').val();

		var queryUrl=CONTEXT+'pvStatisticBusiness/querybysearch';
		//if(queryParams.name==''){
		//	queryUrl=CONTEXT+'member/querybybirthday';
	 //}
		$('#memberdg').datagrid({
			url:queryUrl,
			height: 'auto',
			nowrap:true,
			toolbar:'#membertb',
			pageSize:50,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
						{field:'id',title:'',width:100,hidden:true, align:'center'},
						{field:'shopsName',title:'商铺名称',width:100,align:'center'},
						{field:'account',title:'用户账号',width:100,align:'center'},
						{field:'mobile',title:'手机号',width:100,align:'center'},
						{field:'createTime',title:'注册时间',width:100,align:'center'},
						{field:'marketName',title:'商铺所属市场',width:100,align:'center'},
						{field:'fromPage',title:'统计界面',width:100,align:'center',formatter:formatterFromPage },
						{field:'shopPv',title:'当前浏览量',width:100,align:'center'},
						{field:'statisTime',title:'统计时间',width:100,align:'center',formatter:formatterdate},
						{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
					]]
		});
		//分页加载
		$("#memberdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});

	});
	
	function formatterFromPage(val, row) {
		if (val == 1) {
			return "商铺首页";
		}else if(val == 2){
			return "商品详情页";
		}else if(val == 3){
			return "商铺详情页";
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
	
	function editObj2(id,shopPv){
		$("#editDialog2").find("input[name='id']").val(id);
		$("#editDialog2").find("input[name='shopPv']").val(shopPv);
		$('#editDialog2').dialog({'title':'编辑浏览量','width': 380,'height': 180}).dialog('open');
	}
	
	function editDialog2Save(){
		var id = $("#editDialog2").find("input[name='id']").val();
		var shopPv = $("#editDialog2").find("input[name='shopPv']").val();
		if(shopPv == null || shopPv == ""){
			$.messager.alert("提示信息","浏览量不能为空","warn");
			return false;
		}
		$.ajax({
			type : "post",
			url : CONTEXT+"pvStatisticBusiness/updatePv",
			data : {"id":id,"shopPv":shopPv},
			dataType : "json",
			success : function(data){
		    	if(data.res == "success"){
		    		//保存成功，重新load该页面
		    		slideMessage("操作成功！");
		    		$("#memberdg").datagrid('reload');
		    		$('#editDialog2').dialog('close');
		    	} else {
		    		$.messager.alert("提示信息",data.msg,"warn");
		    	}
			}
		});
	}

	var disableExport = false ;

	$("#exportData").click(function(){
/* 		 var queryParams = $('#memberdg').datagrid('options').queryParams;
			queryParams.account = $('#memberSearchForm #account').val();
			queryParams.mobile = $('#memberSearchForm #mobile').val();
			queryParams.level = $('#memberSearchForm #level').val();
			queryParams.isAuth = $('#memberSearchForm #isAuth').val();
			queryParams.startDate =  $('#memberSearchForm #startDate').val();
			queryParams.endDate =  $('#memberSearchForm #endDate').val();
			queryParams.marketId = $("#memberSearchForm #marketId").val();
			queryParams.regetype = $("#memberSearchForm #regetype").val(); */

			var params = {
				"account" : $('#memberSearchForm #account').val(),
				"mobile" : $('#memberSearchForm #mobile').val(),
				"sendFlag" : $('#memberSearchForm #sendFlag').val(),
				"startValidDate" : $('#memberSearchForm #startValidDate').val(),
				"endValidDate" : $('#memberSearchForm #endValidDate').val(),
				"startExpireDate" : $('#memberSearchForm #startExpireDate').val(),
				"endExpireDate" : $('#memberSearchForm #endExpireDate').val(),
				"status" : $('#memberSearchForm #status').val()
			};
			var paramList = "account=" + params.account + "&mobile="+params.mobile+"&sendFlag="+params.sendFlag+"&startValidDate="+params.startValidDate+
				"&endValidDate="+params.endValidDate+"&startExpireDate="+params.startExpireDate+"&endExpireDate="+params.endExpireDate+"&status="+params.status;

			$.ajax({
				url: CONTEXT+'member/checkExportGoldSupplierParams',
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
							$.download(CONTEXT+'member/exportGoldSupplierData',paramList,'post' );
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

