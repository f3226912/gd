
$(document).ready(function(){
	//数据加载
	$('#areaConfigdg').datagrid({
		url:CONTEXT+'areaConfig/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#areaConfigtb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#areaConfigdg").datagrid('clearSelections');
		},
		columns:[[
		          
			{field:'id',title:'',width:100,checkbox:true},
			{field:'areaName',title:'城市',width:100,align:'center'},
			{field:'status',title:'状态',width:100,align:'center' ,formatter:formatterStatus},
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=update('"+row.id+"');>修改</a>";
			}}
		]]
	}); 
	//分页加载
	$("#areaConfigdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


function save() {
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	

	var url=CONTEXT+"areaConfig/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#areaConfigdg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else if (data == "not exist") {
				warningMessage("城市名称不正确！");
				return;
			}
			else {
				warningMessage(data);
				return;
			}
		});
}





	// 查询按钮,根据name查询
	$('#icon-search').click(function(){ 
        var queryParams = $('#areaConfigdg').datagrid('options').queryParams;
		queryParams.queryName = $('#areaConfigSearchForm #queryName').val();
		queryParams.queryType = $('#areaConfigSearchForm #queryType').val();

		var queryUrl=CONTEXT+'areaConfig/query?queryType='+queryParams.queryType+'&queryParams.queryName='+queryParams.queryName;
		$('#areaConfigdg').datagrid({
			url:queryUrl,
			//width: 1000,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#areaConfigtb',
			pageSize:10,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
			      	{field:'id',title:'',width:100,checkbox:true},
					{field:'areaName',title:'城市',width:100,align:'center'},
					{field:'status',title:'状态',width:100,align:'center' ,formatter:formatterStatus},
					{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
						return "<a class='operate' href='javascript:;' onclick=update('"+row.id+"');>修改</a>";
					}}
			          
				]]
		}); 
		//分页加载
		$("#areaConfigdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	});
	

	function formatterStatus(val, row) {
		if (val != null) {
		   var  str=val.toString();
		   if(str=="0"){
			   return "启用";
		   }else if(str=="1"){
			   return "停用";
		   }
		} 
	}
	
	//编辑
	 function update(areaConfigId){
		
		$('#addDialog').dialog({'title':'编辑', 'width': 500,    
		    'height': 300, 'href':CONTEXT+'areaConfig/edit?id='+areaConfigId}).dialog('open');

	};
	

    //新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'新增','width': 500,    
		    'height': 300, 'href':CONTEXT+'areaConfig/addDto'}).dialog('open');
	});
	
	//重置
	$('#btn-reset').click(function(){
		$('#areaConfigSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#areaConfigSearchForm')[0].reset();
		$('#areaConfigdg').datagrid('options').pageNumber=1;//设置页码初始值为1
		$('#areaConfigdg').datagrid({url:CONTEXT+'areaConfig/query?queryType=&queryName='});
	});