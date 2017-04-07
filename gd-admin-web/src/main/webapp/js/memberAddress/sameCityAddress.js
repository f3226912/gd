
$(document).ready(function(){
	//数据加载
	$('#same_city_addressdg').datagrid({
		url:CONTEXT+'same_city_address/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#same_city_addresstb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#same_city_addressdg").datagrid('clearSelections');
		},
		columns:[[
		          
			{field:'id',title:'',width:100,checkbox:true},
			{field:'s_name',title:'始发地',width:100,align:'center'},
			{field:'f_name',title:'目的地',width:100,align:'center'},
			{field:'linkMan',title:'姓名',width:100,align:'center'},
			{field:'mobile',title:'手机号码',width:100,align:'center'},
			{field:'userType',title:'用户类型',width:100,align:'center'},
			{field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'companyMobile',title:'企业联系电话',width:100,align:'center'},
			{field:'goodsTypeString',title:'货物类型',width:100,align:'center'},
			{field:'totalWeightString',title:'总重量',width:100,align:'center'},
			{field:'createTime',title:'发布时间',width:100,align:'center'},
			{field:'clients',title:'发布来源',width:100,align:'center',formatter:formatSource},
			{field:'mcity',title:'常用城市',width:100,align:'center'},
			{field:'showGoodsStatus',title:'订单状态',width:100,align:'center'},
			{field:'isOrderDeleted',title:'订单是否删除',width:100,align:'center' ,formatter:isOrderDeleted}/*,
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}*/
		]]
	}); 
	//分页加载
	$("#same_city_addressdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});

	// 查询按钮,根据name查询
	$('#icon-search').click(function(){ 
		displayList();
	});
	
	function displayList()
	{
		 var queryParams = $('#same_city_addressdg').datagrid('options').queryParams;
			queryParams.realName = $('#same_city_addressSearchForm #realName').val();
			queryParams.startDate =  $('#same_city_addressSearchForm #queryStartDate').val();
			queryParams.endDate =  $('#same_city_addressSearchForm #queryEndDate').val();
			queryParams.queryType =  $('#same_city_addressSearchForm #queryType').val();
			queryParams.cityName =  $('#same_city_addressSearchForm #cityName').val();
			queryParams.orderStatus =  $('#same_city_addressSearchForm #orderStatus').val();
			queryParams.isOrderDeleted =  $('#same_city_addressSearchForm #isOrderDeleted').val();
			queryParams.clients = $('#same_city_addressSearchForm #clients').val();
			var queryUrl=CONTEXT+'same_city_address/queryByParams';
			$('#same_city_addressdg').datagrid({
				url:queryUrl,
				//width: 1000,  
				height: 'auto', 
				nowrap:true,
				toolbar:'#same_city_addresstb',
				pageSize:10,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				columns:[[
{field:'id',title:'',width:100,checkbox:true},
{field:'s_name',title:'始发地',width:100,align:'center'},
{field:'f_name',title:'目的地',width:100,align:'center'},
{field:'linkMan',title:'姓名',width:100,align:'center'},
{field:'mobile',title:'手机号码',width:100,align:'center'},
{field:'userType',title:'用户类型',width:100,align:'center'},
{field:'companyName',title:'企业名称',width:100,align:'center'},
{field:'companyMobile',title:'企业联系电话',width:100,align:'center'},
{field:'goodsTypeString',title:'货物类型',width:100,align:'center'},
{field:'totalWeightString',title:'总重量',width:100,align:'center'},
{field:'createTime',title:'发布时间',width:100,align:'center'},
{field:'clients',title:'发布来源',width:100,align:'center',formatter:formatSource},
{field:'mcity',title:'常用城市',width:100,align:'center'},
{field:'showGoodsStatus',title:'订单状态',width:100,align:'center'},
{field:'isOrderDeleted',title:'订单是否删除',width:100,align:'center' ,formatter:isOrderDeleted}
		    /*{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				  return "<a class='operate' href='javascript:;'onclick=editObj('"+row.id+"');>修改</a>&nbsp;" +
						 "<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
					}}*/
				]]
			}); 
			//分页加载
			$("#same_city_addressdg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
	}
	
	
	
	
	
	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#same_city_addressdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("id");
	    		jQuery.post(CONTEXT+"same_city_address/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#same_city_addressdg').datagrid('reload');
						$('#same_city_addressdg').datagrid("uncheckAll");

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
		
		
		var row = $('#same_city_addressdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
	    
	    var same_city_addressId = getSelections("id");
		if(same_city_addressId.indexOf(",")>0){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
	    
		$('#addDialog').dialog({'title':'编辑', 'width': 620,    
		    'height': 500,'href':CONTEXT+'same_city_address/edit?id='+same_city_addressId}).dialog('open');

	});
	
	
	
	
	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"same_city_address/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#same_city_addressdg').datagrid('reload');
						$('#same_city_addressdg').datagrid("uncheckAll");
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

 
  //编辑操作
 function editObj(id){
	 $('#addDialog').dialog({'title':'编辑', 'width': 620,    
		    'height': 500,'href':CONTEXT+'same_city_address/edit?id='+id}).dialog('open');
 }

 

	//重置
	$('#btn-reset').click(function(){
		$('#same_city_addressSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#same_city_addressSearchForm')[0].reset();

		$('#same_city_addressdg').datagrid('options').pageNumber=1;//设置页码初始值为1
		//数据加载
		$('#same_city_addressdg').datagrid({url:CONTEXT+'same_city_address/query'});
	});
	
	
	//数据导出
	$("#exportData").click(function(){
		    var queryParams = $('#same_city_addressdg').datagrid('options').queryParams;
			queryParams.realName = $('#same_city_addressSearchForm #realName').val();
			queryParams.startDate =  $('#same_city_addressSearchForm #queryStartDate').val();
			queryParams.endDate =  $('#same_city_addressSearchForm #queryEndDate').val();
			queryParams.queryType =  $('#same_city_addressSearchForm #queryType').val();
			queryParams.cityName =  $('#same_city_addressSearchForm #cityName').val();
			queryParams.orderStatus =  $('#same_city_addressSearchForm #orderStatus').val();
			queryParams.isOrderDeleted =  $('#same_city_addressSearchForm #isOrderDeleted').val();
			queryParams.clients = $('#same_city_addressSearchForm #clients').val();


		window.location.href=CONTEXT+'same_city_address/exportData?realName='+queryParams.realName+
				"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
				+"&cityName="+queryParams.cityName +"&queryType="+queryParams.queryType
				+"&orderStatus="+queryParams.orderStatus +"&isOrderDeleted="+queryParams.isOrderDeleted+"&clients="+queryParams.clients;
	});
	
	//导出已删除数据
	$("#exportIsDelData").click(function(){
		window.location.href=CONTEXT+'same_city_address/exportData?isDel=1';
	});
	
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
             //查询
        	 displayList();
        }
    }; 
	
    
    function isOrderDeleted(val, row) {
 	   if(val == null || val.toString()=="0"){
 		   return "未删除";
 	   }else {
 		   return "已删除";
 	   }
    }
    
    function formatSource(val, row){
       if(val.toString()=="1"){
 		   return "谷登农批";
 	   }else if(val==null||val.toString()=="2"){
 		  return "农速通";
 	   }else if(val.toString()=="3"){
 		  return "农商友";
 	   }else if(val.toString()=="4"){
 		  return "产地供应商";
 	   }else if(val.toString()=="5"){
 		  return "农商友-农批商";
 	   }
    }
