<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<script type="text/javascript">
	
	$(document).ready(function(){
		
		   $('#treeMenu').tree({
			   url:CONTEXT+'sysmgr/sysMenu/getMenuButtonTree?roleID=${dto.roleID}',
		        animate:true,
		        checkbox:true,
		   });
	});
	
	
	//确定分配
	function saveAssignRight(){
		//判断是否选中
		var row = $('#treeMenu').tree('getChecked');	// 获取选中的节点
		var nodes2 = $('#treeMenu').tree('getChecked','indeterminate');//获取未确定节点，即：已选中节点的父节点，处于非勾选状态的选中节点
		var msg='您确定要分配所选数据吗?';
        if($(row).length < 1 ) {
        	msg='您确定要取消所有分配数据吗?';
        }
		jQuery.messager.confirm('提示', msg, function(r){
			if (r){
				var menuIDs="";
				var btnIDs="";
				$(row).each(function(){
					var node=$(this);
					if(node[0].attributes.type=='9'){
						btnIDs+=node[0].id.toString()+",";
					}else if(node[0].attributes.type=='1'){
						menuIDs+=node[0].id.toString()+",";
					}
				});
				$(nodes2).each(function(){
					var node=$(this);
					if(node[0].attributes.type=='9'){
						btnIDs+=node[0].id.toString()+",";
					}else if(node[0].attributes.type=='1'){
						menuIDs+=node[0].id.toString()+",";
					}
				});
				
				jQuery.post(CONTEXT+"sysmgr/sysRole/assignMenu",
						{
							"roleID":"${dto.roleID}",
		    				"menuIDs":menuIDs,
		    				"btnIDs":btnIDs
						},
						function(data){
		    				if (data == "success"){
								slideMessage("操作成功！");
								$("#treeMenu").tree('reload');
		    				}else{
								warningMessage("操作失败！");
		    					return;
		    				}
						}
        		); 
			}else{
				return;
			}
		});
	}
	
	//查询
	function queryMenu(){
		queryParams = $('#rightdg').datagrid('options').queryParams;  
        queryParams.menuName = $('#menuName').searchbox('getValue');
		queryParams.menuCode = $("#menuCode").searchbox("getValue");
        $("#rightdg").datagrid('reload'); 
	}
	
	//单独查询
	function queryMenu2(value,name){
		 var queryParams = $('#rightdg').datagrid('options').queryParams;  
		 if(name=='menuName'){
		 	queryParams.menuName = value;
		 }
		 if(name=='menuCode'){
		 	queryParams.menuCode = value;
		 }
         $("#rightdg").datagrid('reload'); 
	}
	
	//重置
	function clearMenudata(){
		$('#menuName').searchbox('setValue',"");
		$("#menuCode").searchbox("setValue","");
	}
	
	//刷新
	function reloadCurPage(){
		$("#rightdg").datagrid('reload'); 
	}
</script>
<!-- <table id="rightdg" title="" > -->
<!-- 	<thead> -->
<!-- 		<tr> -->
<%-- 			<c:if test="${empty view }"> --%>
<!-- 			<th data-options="field:'menuID',checkbox:true"></th> -->
<!-- 			<th data-options="field:'menuTotal',width:80,align:'left'"></th> -->
<%-- 			</c:if> --%>
<!-- 			<th data-options="field:'menuCode',width:80,align:'left'">菜单编号</th> -->
<!-- 			<th data-options="field:'menuName',width:180,align:'left'">菜单钮名称</th> -->
<!-- 		</tr> -->
<!-- 	</thead> -->
<!-- </table> -->
<div id="righttb" style="padding:5px;height:auto">
<!-- 	<form id="searchMenuForm" method="post"> -->
<!-- 	<div style="display: none;"> -->
<!-- 		菜单编号: <input class="easyui-searchbox" type="text" id="menuCode" name="menuCode" style="width:150px" data-options="prompt:'请输入菜单编号',   -->
<!--            searcher:function(value,name){queryMenu2(value,name)}"> -->
<!-- 		菜单名称: <input class="easyui-searchbox" type="text" id="menuName" name="menuName" style="width:150px" data-options="prompt:'请输入菜单名称',   -->
<!--            searcher:function(value,name){queryMenu2(value,name)}"> -->
<!-- 		<a class="easyui-linkbutton" iconCls="icon-search" onclick="queryMenu()">查询</a> -->
<!-- 		<a class="easyui-linkbutton" iconCls="icon-reload" onclick="clearMenudata()">重置</a> -->
<!-- 		<a class="easyui-linkbutton" iconCls="icon-reload" onclick='reloadCurPage()'">刷新</a> -->
<!-- 	</div> -->
<!-- 	</form> -->
	<div style="margin-bottom:5px">
		当前角色是：${dto.roleName }
		<c:if test="${empty view }">
			<a style="margin-left:15px" class="easyui-linkbutton" iconCls="icon-group-link" onclick="saveAssignRight()">确定分配</a>
		</c:if>
	</div>
	<div>
		<ul id="treeMenu" class="ztree"></ul>
	</div>
</div>
