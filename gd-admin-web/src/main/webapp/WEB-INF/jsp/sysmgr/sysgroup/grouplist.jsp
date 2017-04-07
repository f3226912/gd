<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<title>系统资源组管理</title>
	</head>
	<script>
	
		$(document).ready(function(){
			//数据加载
			$('#dg').datagrid({
	    		url:CONTEXT+'sysmgr/sysGroup/query',
				width: 1000,  
	    		height: 'auto', 
				nowrap:true,
				toolbar:'#tb',
				pageSize:20,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				sortOrder:''
			}); 
			//分页加载
			$("#dg").datagrid("getPager").pagination({   
				pageList: [10,20,50,100]
	        }); 
		});
	
		//编缉
		function editObj(action){
			if(action=="add"){
				$('#editDialog').dialog({'title':'新增','href':CONTEXT+'sysmgr/sysGroup/addInit'}).dialog('open');
			}else{
				var row = $('#dg').datagrid("getSelections");
	            if($(row).length < 1 ) {
					slideMessage("请选择要操作的数据！");
	                return ;
	            }
				if($(row).length>1){
					slideMessage("只能操作一行记录！");
                	return ;
				}
				var selected = $('#dg').datagrid('getSelected');
				if (selected){
					$('#editDialog').dialog({'title':'修改','href':CONTEXT+'sysmgr/sysGroup/updateInit?resGroupID='+selected.resGroupID}).dialog('open');
				}
			}
		}

		//单独查询
		function query2(value,name){
			 var queryParams = $('#dg').datagrid('options').queryParams;  
			 if(name=='resGroupName'){
			 	queryParams.resGroupName = value;
			 }
             $("#dg").datagrid('reload'); 
		}
		
		//重置
		function cleardata(){
			$('#resGroupName').searchbox('setValue',"");
		}
		
		
		//删除
		function delObj(){
			//判断是否选中
			var row = $('#dg').datagrid("getSelections");
            if($(row).length < 1 ) {
				slideMessage("请选择要操作的数据！");
                return ;
            }
    		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
    			if (r){
            		var deleteStr = getSelections("resGroupID");
            		jQuery.post(CONTEXT+"sysmgr/sysGroup/delete",{"groupIDs":deleteStr},function(data){
        				if (data == "success"){
							slideMessage("操作成功！");
        					$("#dg").datagrid('reload');
        					$('#dg').datagrid("uncheckAll");
        				}else{
							warningMessage(data);
        					return;
        				}
            		});
    			}else{
					return;
				}
    		});
		}
   
		
    </script>
<body>
	<table id="dg" title="">
		<thead>
			<tr>
				<th data-options="field:'resGroupID',checkbox:true"></th>
				<th data-options="field:'resGroupName',width:80,align:'left',sortable:true">资源组名称</th>
				<th data-options="field:'remark',width:200,align:'left',sortable:true">备注</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<form id="searchform" method="post">
		<div>
			资源组名称: <input class="easyui-searchbox" type="text" id="resGroupName" name="resGroupName" style="width:150px" data-options="prompt:'请输入资源组名称',  
            searcher:function(value,name){query2(value,name)}">&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick="cleardata()">重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton" iconCls="icon-add" onclick="editObj('add')">新增</a>
			<a class="easyui-linkbutton" iconCls="icon-edit" onclick="editObj('edit')">修改</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" onclick="delObj()">删除</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick='location.reload(true)'">刷新</a>
		</div>
	</div>
	<div id="editDialog" class="easyui-dialog" style="width:400px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
		<div id="dlg-buttonsEdit" style="text-align:center">
        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
        </div>
	</div>
</body>
</html>