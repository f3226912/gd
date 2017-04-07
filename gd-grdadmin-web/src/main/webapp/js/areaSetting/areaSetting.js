
$(document).ready(function(){
	//数据加载
	$('#areaSettingdg').datagrid({
		url:CONTEXT+'areaSetting/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#areaSettingtb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#areaSettingdg").datagrid('clearSelections');
		},
		columns:[[
		          
			{field:'id',title:'',width:100,checkbox:true},
			{field:'areaName',title:'名称',width:100,align:'center'},
			{field:'mobile',title:'手机号码',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optFormatter}
		]]
	}); 
	//分页加载
	$("#areaSettingdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


function save() {
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	
	var test =/^(1[0-9][0-9])\d{8}$/;
	var phone=$("#addForm #mobile").val();
	if (!test.test(phone)) {
		warningMessage("输入的手机号码格式不正确!");
		return ;
	}
	
	var url=CONTEXT+"areaSetting/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#areaSettingdg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else if (data == "not exist") {
				warningMessage("会员不存在！");
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
        var queryParams = $('#areaSettingdg').datagrid('options').queryParams;
		queryParams.queryName = $('#areaSettingSearchForm #queryName').val();
		queryParams.queryMobile = $('#areaSettingSearchForm #queryMobile').val();

		var queryUrl=CONTEXT+'areaSetting/query?queryMobile='+queryParams.queryMobile+'&queryName='+queryParams.queryName;
		$('#areaSettingdg').datagrid({
			url:queryUrl,
			//width: 1000,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#areaSettingtb',
			pageSize:10,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
			      	{field:'id',title:'',width:100,checkbox:true},
					{field:'areaName',title:'名称',width:100,align:'center'},
					{field:'mobile',title:'手机号码',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
						return "<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
					}}
			          
				]]
		}); 
		//分页加载
		$("#areaSettingdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	});
	
	
	
	
	
	
	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#areaSettingdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("id");
	    		jQuery.post(CONTEXT+"areaSetting/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#areaSettingdg').datagrid('reload');
						$('#areaSettingdg').datagrid("uncheckAll");

					}else{
						warningMessage(data);
						return;
					}
	    		});
			}else{
				return;
			}
		});
	});
	
	

	//编辑
	$("#icon-edit").click(function(){
		
		
		var row = $('#areaSettingdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
	    
	    var areaSettingId = getSelections("id");
		if(areaSettingId.indexOf(",")>0){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
	    
		$('#addDialog').dialog({'title':'编辑', 'width': 500,    
		    'height': 300, 'href':CONTEXT+'areaSetting/edit?id='+areaSettingId}).dialog('open');

	});
	
	
	
	
	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"areaSetting/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#areaSettingdg').datagrid('reload');
						$('#areaSettingdg').datagrid("uncheckAll");
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

    //新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'新增','width': 500,    
		    'height': 300, 'href':CONTEXT+'areaSetting/addDto'}).dialog('open');
	});
	
	//重置
	$('#btn-reset').click(function(){
		$('#areaSettingSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#areaSettingSearchForm')[0].reset();
		$('#areaSettingdg').datagrid('options').pageNumber=1;//设置页码初始值为1
		var queryUrl='areaSetting/query?queryMobile='+'&queryName=';
		$('#areaSettingdg').datagrid({url:CONTEXT+queryUrl});
	});