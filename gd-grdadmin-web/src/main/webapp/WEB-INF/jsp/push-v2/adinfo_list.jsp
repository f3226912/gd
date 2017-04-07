<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>push</title>
		<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
	</head>
<body>
<script type="text/javascript">
	$(document).ready(function(){
		   $('#treeMenu').tree({
			   url:CONTEXT+'pushV2/getTreeMenu',
		        animate:true,
		        onClick:function(node){
		        	if(node.attributes.type=='2'){
		        		reset();
		        		$("#menuId").val("");
		        		$("#adSpaceId").val(node.id);
		        		query();
		        	}else{
// 		        		reset();
		        		$("#adSpaceId").val("");
		        		$("#menuId").val(node.id);
// 		        		query();
		        	}
		        },
		        onContextMenu: function(e, node){
					e.preventDefault();
					// 查找节点
					$(this).tree('select', node.target);
					// 右键菜单
					if(node.attributes.type=='1'){
						$('#ContextAdMenu').menu('show', {
			    			left: e.pageX,
			    			top: e.pageY
				    	});
					}else if(node.attributes.type=='2'){
						//右键广告位
						$('#ContextAdSpace').menu('show', {
			    			left: e.pageX,
			    			top: e.pageY
				    	});
					}else if(node.attributes.type=='0'){
						//右键根节点
						$('#ContextAdRoot').menu('show', {
			    			left: e.pageX,
			    			top: e.pageY
				    	});
					}else if(node.attributes.type=='3'){
						//右键广告
						$('#ContextAdvertise').menu('show', {
			    			left: e.pageX,
			    			top: e.pageY
				    	});
					}
			    }
		   });
	});

	function optformat(value,row,index){
		var optStr="<a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a>";
		var state=row.state;
		//启用状态
		switch (state) {
		case "1": //启用状态
// 			optStr+="<a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a>";
			optStr+="<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"','4','"+row.adSpaceId+"');>禁用</a>";
	        break;
	     case "2"://等待状态
	    	 optStr+="<a class='operate' href='javascript:;' onclick=editObj('"+row.id+"','"+row.adSpaceId+"');>编辑</a>";
			 optStr+="<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"','1','"+row.adSpaceId+"');>启用</a>";
			 optStr+="<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"','4','"+row.adSpaceId+"');>禁用</a>";
	         break;
	     case "3"://截止状态
	         break;
	     case "4"://禁用状态
	    	 optStr+="<a class='operate' href='javascript:;' onclick=editObj('"+row.id+"','"+row.adSpaceId+"');>编辑</a>";
			 optStr+="<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"','1','"+row.adSpaceId+"');>启用</a>";
	         break;
	     default:
	         break;
	     }
		return optStr;
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'north'" style="padding-top: 10px;background: #fafafa;border-color: #ddd;">
		<form id="adinfoSearchForm" method="post">
			广告名称: 
			<input type="text" id="adName" class="easyui-validatebox" name="adName" style="width:150px" >
			广告位标识:
			<input name="spaceSign" id="spaceSign" style="width: 100px;"/>
			<input name="adSpaceId" id="adSpaceId" type="hidden"/>
			<input name="menuId" id="menuId" type="hidden"/>
			发布时间 :
			<input  type="text"  id="startDate" name="startDateStr"  
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="endDate" name="endDateStr"   
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px">
			广告状态:
			<select name="state" id="state" style="width: 100px;">
				<option value="">全部</option>
				<option value="1">上架</option>
				<option value="2">等待</option>
				<option value="3">到期</option>
				<option value="4">下架</option>
			</select>
			<br>
			<a class="easyui-linkbutton icon-search-btn" onclick="addAdvertise()" iconCls="icon-add">添加广告</a>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</form>
	</div>   
    <div data-options="region:'center'" style="padding-top: 10px;background: #fafafa;">   
        <div class="easyui-layout" data-options="fit:true">   
            <div data-options="region:'west',title:'谷登农批广告系统',split:true,border:true" style="width:250px;background-color:rgb(234, 234, 228);">
	            <ul id="treeMenu"></ul>
            </div>   
            <div data-options="region:'center'">
            	<table id="adinfodg" title="">
				</table>
            </div>   
        </div>   
    </div>
    <!-- 分配管理-->
	<div id="editButtonDialog" class="easyui-dialog" style="width:600px;height:350px;" closed="true" modal="true" buttons="#dlg-buttons2Edit">
		<div id="dlg-buttons2Edit" style="text-align:center">
	     	<a id="btn-save" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="save()">保存</a>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editButtonDialog').dialog('close')">关闭</a>
	     </div>
	</div>
</body>

<!--  菜单右键菜单定义如下： -->
<div id="ContextAdMenu" class="easyui-menu" style="width:120px;">
	<div onclick="adMenuView()" data-options="iconCls:'icon-search'">查看</div>
	<div onclick="addAdMenu()" data-options="iconCls:'icon-add'">添加菜单</div>
	<div onclick="addAdSpace()" data-options="iconCls:'icon-add'">添加广告位</div>
	<div onclick="adMenuUpdate()" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="adMenudelete()" data-options="iconCls:'icon-remove'">删除</div>
</div>

<!--  广告位右键菜单定义如下： -->
<div id="ContextAdSpace" class="easyui-menu" style="width:120px;">
	<div onclick="viewAdSpace()" data-options="iconCls:'icon-search'">查看</div>
<!-- 	<div onclick="addAdvertise()" data-options="iconCls:'icon-add'">添加广告</div> -->
	<div onclick="AdSpaceUpdate()" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="adSpacedelete()" data-options="iconCls:'icon-remove'">删除</div>
</div>
<!--  根节点右键菜单定义如下： -->
<div id="ContextAdRoot" class="easyui-menu" style="width:120px;">
	<div onclick="addAdMenu()" data-options="iconCls:'icon-add'">添加菜单</div>
</div>
<!--  广告右键菜单定义如下： -->
<div id="ContextAdvertise" class="easyui-menu" style="width:120px;">
	<div onclick="AdvertiseView()" data-options="iconCls:'icon-search'">查看</div>
	<div onclick="AdvertiseUpdate()" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="advertiseDelete()" data-options="iconCls:'icon-remove'">禁用</div>
</div>
<%-- <script type="text/javascript" src="${CONTEXT}js/push-v2/adinfo-add.js"></script> --%>
<script type="text/javascript" src="${CONTEXT}js/adMenu/adinfo.js"></script>
</html>