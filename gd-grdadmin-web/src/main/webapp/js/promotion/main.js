
$(document).ready(function(){
	//数据加载
	$('#promotiondg').datagrid({
		url:CONTEXT+'promotion/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#promotiontb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			var table = $("#promotiondg");
			var tableRows = table.datagrid("getRows").length;
			var curOrderId = "";
			var nextOrderId = "";
			for(var i=0; i<tableRows; i++){
				if (i != tableRows - 1){
					rowSpan = 1;
					curOrderId = table.datagrid("getRows")[i].id;
					nextOrderId = table.datagrid("getRows")[i + 1].id;
					while(curOrderId == nextOrderId){
						rowSpan = rowSpan + 1;
						i = i + 1;
						if((i + 1) < tableRows){
							nextOrderId = table.datagrid("getRows")[i + 1].id;
						}else{
							break ;
						}
					}
					if (rowSpan > 1){
						index = i-rowSpan+1;
						mergeCells(index, rowSpan);
					}
				}
			}
//			
//			for(var i=0; i<tableRows; i++){
//				if (i != tableRows - 1){
//					for(var j=i+1; j<tableRows; j++){
//						//循环遍历比i行数大的数行，直到curOrderId！=nextOrderId时，进行行合并以及退出此次j循环，j-i刚好为相同行的个数 。
//						curOrderId = table.datagrid("getRows")[i].id;
//						nextOrderId = table.datagrid("getRows")[j].id;
//						if (curOrderId == nextOrderId){
//							continue;
//						}else{
//							if(j-i>1){
//								mergeCells(i,j-i);
//								i=j-1;
//							}
//							break;
//						}
//					}
//				}
//			}
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'名称',width:100,align:'center' },
					{field:'typeName',title:'来源',width:100,align:'center' },
					{field:'url',title:'链接',width:100,align:'center' },
					{field:'urlImg',title:'图片地址',width:100,align:'center' },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#promotiondg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});





// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
 var queryParams = $('#promotiondg').datagrid('options').queryParams;
	queryParams.name = $('#promotionSearchForm #name').val();
	
	var queryUrl=CONTEXT+'promotion/query';
	
	$('#promotiondg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#promotiontb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'名称',width:100,align:'center' },
					{field:'typeName',title:'来源',width:100,align:'center' },
					{field:'url',title:'链接',width:100,align:'center' },
					{field:'urlImg',title:'图片地址',width:100,align:'center' },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#promotiondg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});




function mergeCells(i,j){
	//i为行号，j为合并行的个数
	$('#promotiondg').datagrid('mergeCells',{
		index:i,
		field:'id',
		rowspan:j
	});
	$('#promotiondg').datagrid('mergeCells',{
		index:i,
		field:'name',
		rowspan:j
	});
	$('#promotiondg').datagrid('mergeCells',{
		index:i,
		field:'opt',
		rowspan:j
	});
	
}



var isSubmited = false;

function save() {
	if(!$("#addForm").form('validate')){
		return;
	}
	var name=$('#addForm #name').val();
	if(name.length == 0 || name.match(/^\s+$/g)){
		warningMessage("渠道名称不能为空或者全为空格！");
		return;
	}
	if(isSubmited){
		return;
	}
	
	var url=CONTEXT+"promotion/save";
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		isSubmited = false;
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#promotiondg").datagrid('reload');
			//$('#addDialog').dialog('close');
			$('#editDialog').dialog('close');
		} else if(data == "mobile"){
			warningMessage("手机号码不能为空");
			return;
		}  else if(data == "mobileE"){
			warningMessage("请输入正确的手机号");
			return;
		} else if(data == "existM"){
			warningMessage("该手机号已经被注册，请更换手机号");
			return;
		}else  if(data == "exist"){
			warningMessage("账号或者手机号已被注册,请修改");
			return;
		} else {
			warningMessage(data);
			return;
		}
	});
	isSubmited = true;
}


//删除操作
$("#icon-remove").click(function(){
var row = $('#promotiondg').datagrid("getSelections");
if($(row).length < 1 ) {
	warningMessage("请选择要操作的数据！");
    return ;
}

jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
	if (r){
		var deleteStr = getSelections("promotionId");
		jQuery.post(CONTEXT+"promotion/deletebyid",{"id":deleteStr},function(data){
			if (data == "success"){
				slideMessage("操作成功！");
				$('#promotiondg').datagrid('reload');
				$('#promotiondg').datagrid("uncheckAll");

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
		
		
		var row = $('#promotiondg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
	    
	    var deleteStr = getSelections("promotionId");
		if($(row).length  >1){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
		$('#editDialog').dialog({'title':'编辑DTO','href':CONTEXT+'promotion/editbyid/'+deleteStr,'width': 400,'height': 500}).dialog('open');
	});

	
	
	//删除操作
 function deletebyid(id){
	 	if(id==0){
			warningMessage("自营推广，请勿删除");
			return;
	 	}
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"promotion/deletebyid/"+id,{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#promotiondg').datagrid('reload');
						$('#promotiondg').datagrid("uncheckAll");
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
 
 
//会员类别（1谷登农批，2农速通，3农商友，4产地供应商 ，其余待补）
///* function formatterLevel(val, row) {
// 	if (val != null) {
// 		var  str=val.toString();
// 		if(str=="1"){
// 			return "农速通";
// 		}else if(str=="2"){
// 			return "农商友";
// 		} 
// 	} 
// }*/
 
// 编辑
 function editObj(id){
	 	if(id==0){
			warningMessage("自营推广，请勿修改");
			return;
	 	}
	    isSubmited = false;
		$('#editDialog').dialog({'title':'渠道信息','href':CONTEXT+'promotion/editbyid/'+id,'width': 300,'height': 200}).dialog('open');
 }
 

//新增
$('#icon-add').click(function(){
	isSubmited = false;
	$('#editDialog').dialog({'title':'新增渠道','href':CONTEXT+'promotion/addPromotion', 'width': 300,'height': 200}).dialog('open');
});

//重置
$('#btn-reset').click(function(){
	$('#promotionSearchForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#promotionSearchForm')[0].reset();
	$("#promotiondg").datagrid('load',{});
});