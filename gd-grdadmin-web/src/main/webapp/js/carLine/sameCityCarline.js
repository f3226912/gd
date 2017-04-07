
$(document).ready(function(){
	//数据加载
	$('#same_city_carLinedg').datagrid({
		url:CONTEXT+'same_city_carLine/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#same_city_carLinetb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#same_city_carLinedg").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'',width:100,checkbox:true},
			{field:'carNumber',title:'车牌号码',width:100,align:'center'},
			{field:'transportCarType',title:'车辆类型',width:100,align:'center'},
			{field:'maxLoad',title:'载重(吨)',width:100,align:'center'},
			{field:'carLength',title:'车长(米)',width:100,align:'center'},
			{field:'s_detail',title:'出发地',width:100,align:'center'},
			{field:'e_detail',title:'目的地',width:100,align:'center'},
			{field:'userType',title:'用户类型',width:100,align:'center' ,formatter: formatUserType},
		    {field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'realName',title:'联系人',width:100,align:'center'},
			{field:'mobile',title:'电话号码',width:100,align:'center'},
			{field:'createTime',title:'发布时间',width:100,align:'center'},
			{field:'sendGoodsTypeString',title:'发运方式',width:100,align:'center'},
			{field:'priceString',title:'价格',width:100,align:'center'},
			{field:'areaName',title:'所属区域',width:100,align:'center'}/*,
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}*/
		]]
	}); 
	//分页加载
	$("#same_city_carLinedg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


	// 查询按钮,根据name查询
	$('#icon-search').click(function(){ 
        var queryParams = $('#same_city_carLinedg').datagrid('options').queryParams;
		queryParams.carType = $('#same_city_carLineSearchForm #queryCarType').val();
		queryParams.mobile  = $('#same_city_carLineSearchForm #mobile').val();
		queryParams.startDate =  $('#same_city_carLineSearchForm #queryStartDate').val();
		queryParams.endDate =  $('#same_city_carLineSearchForm #queryEndDate').val();
		queryParams.queryType =  $('#same_city_carLineSearchForm #queryType').val();
		queryParams.areaName =  $('#same_city_carLineSearchForm #areaName').val();
		var queryUrl=CONTEXT+'same_city_carLine/queryByParameters';
		$('#same_city_carLinedg').datagrid({
			url:queryUrl,
			//width: 1000,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#same_city_carLinetb',
			pageSize:10,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
			{field:'id',title:'',width:100,checkbox:true},
			{field:'carNumber',title:'车牌号码',width:100,align:'center'},
			{field:'transportCarType',title:'车辆类型',width:100,align:'center'},
			{field:'maxLoad',title:'载重(吨)',width:100,align:'center'},
			{field:'carLength',title:'车长(米)',width:100,align:'center'},
			{field:'s_detail',title:'出发地',width:100,align:'center'},
			{field:'e_detail',title:'目的地',width:100,align:'center'},
			{field:'userType',title:'用户类型',width:100,align:'center' ,formatter: formatUserType},
		    {field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'realName',title:'联系人',width:100,align:'center'},
			{field:'mobile',title:'电话号码',width:100,align:'center'},
			{field:'createTime',title:'发布时间',width:100,align:'center'},
			{field:'sendGoodsTypeString',title:'发运方式',width:100,align:'center'},
			{field:'priceString',title:'价格',width:100,align:'center'},
			{field:'areaName',title:'所属区域',width:100,align:'center'}/*,
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}*/
			]]
		}); 
		//分页加载
		$("#same_city_carLinedg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	});
	
	
	
	//导出
	$('#btn-export').click(function(){
		$.messager.confirm('提示', '您确定要导出查询数据吗?', function(r){
			if (r){
				$('#same_city_carLineSearchForm').submit();
			}
		});
		
	});
	
	//导出已删除数据
	$('#isDelExportData').click(function(){
		window.location.href=CONTEXT+'same_city_carLine/export?isDel=1';
	});
	
	
	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#same_city_carLinedg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("id");
	    		jQuery.post(CONTEXT+"same_city_carLine/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#same_city_carLinedg').datagrid('reload');
						$('#same_city_carLinedg').datagrid("uncheckAll");

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
		
		var row = $('#same_city_carLinedg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
	    var same_city_carLineId = getSelections("id");
		if(same_city_carLineId.indexOf(",")>0){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
	    
		$('#addDialog').dialog({'title':'编辑', 'width': 700,     
		    'height': 400, 'href':CONTEXT+'same_city_carLine/edit?id='+same_city_carLineId}).dialog('open');

	});
	
	
	
	
	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"same_city_carLine/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#same_city_carLinedg').datagrid('reload');
						$('#same_city_carLinedg').datagrid("uncheckAll");
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
		$('#addDialog').dialog({'title':'新增车辆专线','width': 700,    
		    'height':400, 'href':CONTEXT+'same_city_carLine/addDto'}).dialog('open');
	});
	
	$('#queryCarList').click(function(){
		$('#carDialog').dialog({'title':'选择车牌号码', 'width':700, 'height':300, 'href':CONTEXT+'same_city_carLine/carList'}).dialog('open');
	});
	
	//重置
	$('#btn-reset').click(function(){
		$('#same_city_carLineSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#same_city_carLineSearchForm')[0].reset();
	    $('#same_city_carLinedg').datagrid('options').pageNumber=1;//设置页码初始值为1
		$('#same_city_carLinedg').datagrid({url:CONTEXT+'same_city_carLine/query'});
	});
	

   function formatUserType(val, row) {
	if (val != null) {
		if (val.toString() == "1") {
			return "个人";
		} else if (val.toString() == "2") {
			return "企业";
		}
	}
}
	