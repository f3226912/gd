<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<script type="text/javascript">
	
	$(document).ready(function(){
		//数据加载
		var queryUrl="";
		//0系统，1联采中心，2配送站，3学校，4食堂，5基地
		if("${type}"=="1"){
			//1联采中心
			queryUrl=CONTEXT+"purchaseCenter/query";
		}
		if("${type}"=="2"){
			//2配送站
			queryUrl=CONTEXT+"distribCenter/query";
		}
		if("${type}"=="3"){
			//3学校
			queryUrl=CONTEXT+"school/query";
		}
		if("${type}"=="4"){
			//4食堂
			queryUrl=CONTEXT+"canteen/query";
		}
		if("${type}"=="5"){
			//5基地
			queryUrl=CONTEXT+"productbase/page";
		}
		$('#orgdg').datagrid({
			url:queryUrl,
			width: 400,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#orgtb',
			pageSize:20,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			singleSelect:true
		}); 
		
		//分页加载
		$("#orgdg").datagrid("getPager").pagination({   
			pageList: [10,20,50,100]
	    }); 
	});

	//选择组织
	function saveOrg(){
		//判断是否选中
		var row = $('#orgdg').datagrid("getSelections");
        if($(row).length < 1 ) {
        	slideMessage("请选择要操作的数据！");
        	return ;
        }
        var id = singleChose("id");
        var name=$('#orgdg').datagrid('getSelected').name;
		$("#editForm #orgUnitId").val(id);
		$('#editForm #orgUnitName').val(name);
		$('#choseOrgDialog').dialog('close');
	}
	
</script>
<table id="orgdg" title="" >
	<thead>
		<tr>
			<th data-options="field:'id',checkbox:true"></th>
			<c:if test="${!(type eq 4)}">
				<th data-options="field:'number',width:100,align:'left'">编号</th>
			</c:if>
			<th data-options="field:'name',width:150,align:'left'">名称</th>
		</tr>
	</thead>
</table>
<div id="orgtb" style="padding:5px;height:auto">
	<div style="margin-bottom:5px">
		<a style="margin-left:15px" class="easyui-linkbutton" iconCls="icon-group-link" onclick="saveOrg()">确定</a>
	</div>
</div>
