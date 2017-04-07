<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input type="hidden" id="giftRowId" value="${giftRowId }">
<table id="actGiftdg" title=""></table>
<div id="actGifttb" style="padding:10px;height:auto">
	<form id="selectGiftSearchForm" method="post">
		<div>
			<label>礼品名称：<input type="text" name="name" id="name"/></label>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="gift-search">查询</a>
		</div>
	</form>
</div>
<script type="text/javascript">
	function loadList(params){
		params = !params ? {}: params;
		//数据加载
		$('#actGiftdg').datagrid({
			url:CONTEXT+'actGift/query',
			queryParams: params,
			height: 'auto', 
			nowrap:true,
			toolbar:'#actGifttb',
			pageSize:50,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			onLoadSuccess:function(){
				$("#actGiftdg").datagrid('clearSelections');
			},
			columns:[[
				{field:'id',title:'',width:0,checkbox:true},
				{field:'name',title:'礼品名称',width:100,align:'center'},
				{field:'stockTotal',title:'当前库存',width:100,align:'center' },
				{field:'stockAvailable',title:'可用库存',width:100,align:'center'},
				{field:'createTime',title:'创建时间',width:100,align:'center'},
				{field:'createUserName',title:'添加人',width:100,align:'center'},
				{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
			]]
		}); 
	}
	function optformat(value,row,index){
		var giftRowId = $("#giftRowId").val();
		return "<a class='operate' href='javascript:;' onclick=\"selectGiftCallBack('"+giftRowId+"','"+row.id+"','"+row.name+"');\">选择礼品</a>&nbsp;";
	}
	$(document).ready(function(){
		initList();
		//查询
		$('#gift-search').click(function(){
			var params = {
				"name":$("#selectGiftSearchForm #name").val()
			};
			loadList(params);
		});
	});
</script>