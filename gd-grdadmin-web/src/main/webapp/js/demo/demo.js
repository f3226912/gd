
$(document).ready(function(){
	//数据加载
	$('#demodg').datagrid({
		url:CONTEXT+'demo/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#demotb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'id',title:'',width:50,checkbox:true},
			{field:'name',title:'文件名称',width:100,align:'center'},
			{field:'birthday',title:'生日',width:100,align:'center',formatter:formatterdate   },
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
			}}
		]]
	}); 
	//分页加载
	$("#demodg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});

function formatterdate(val, row) {
		if (val != null) {
		   var  str=val.toString();
		   str =  str.replace(/-/g,"/");
		   var date = new Date(str);
		   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
		}
	}

function save() {
	var url=CONTEXT+"demo/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#demodg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else {
				warningMessage(data);
				return;
			}
		});
}








	// 查询按钮,根据name查询
	$('#icon-search').click(function(){ 
        var queryParams = $('#demodg').datagrid('options').queryParams;
		queryParams.name = $('#demoSearchForm #name').val();
		queryParams.startDate =  $('#demoSearchForm #startDate').val();
		queryParams.endDate =  $('#demoSearchForm #endDate').val();
		var queryUrl=CONTEXT+'demo/querybyname';
		//if(queryParams.name==''){
		//	queryUrl=CONTEXT+'demo/querybybirthday';
        //}
		$('#demodg').datagrid({
			url:queryUrl,
			//width: 1000,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#demotb',
			pageSize:10,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
				{field:'id',title:'',width:50,checkbox:true},
				{field:'name',title:'文件名称',width:100,align:'center'},
				{field:'birthday',title:'生日',width:100,align:'center',formatter:formatterdate   },
				{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
					return "<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
				}}
			]]
		}); 
		//分页加载
		$("#demodg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	});
	
	
	
	
	
	
	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#demodg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("id");
	    		jQuery.post(CONTEXT+"demo/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#demodg').datagrid('reload');
						$('#demodg').datagrid("uncheckAll");

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
		
		
		var row = $('#demodg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
	    
	    var deleteStr = getSelections("id");
		if(deleteStr.indexOf(",")>0){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
	    
		$('#addDialog').dialog({'title':'编辑DTO','href':CONTEXT+'demo/editbyid?id='+deleteStr}).dialog('open');

	});
	
	
	
	
	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"demo/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#demodg').datagrid('reload');
						$('#demodg').datagrid("uncheckAll");
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
		$('#addDialog').dialog({'title':'新增DTO','href':CONTEXT+'demo/addDto'}).dialog('open');
	});