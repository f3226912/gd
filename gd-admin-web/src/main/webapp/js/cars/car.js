var disableExport = false;
$(document).ready(function(){
	var transportType =$("#transportType").val();
	//数据加载
	$('#carsdg').datagrid({
		url:CONTEXT+'cars/query?transportType='+transportType,
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#carstb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#carsdg").datagrid('clearSelections');
		},
		columns:[[
		          
			{field:'id',title:'',width:70,checkbox:true},
			{field:'carNumber',title:'车牌号码',width:100,align:'center'},
			{field:'carTypeName',title:'车辆类型',width:100,align:'center'},
			{field:'maxLoad',title:'载重(吨)',width:100,align:'center'},
			{field:'carLength',title:'车长(米)',width:100,align:'center'},
			{field:'userTypeName',title:'用户类型',width:100,align:'center'},
			{field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'nickName',title:'联系人',width:100,align:'center'},
			{field:'phone',title:'电话号码',width:100,align:'center'},
			{field:'createTimeString',title:'发布时间',width:100,align:'center'},
			{field:'isDeleted',title:'删除状态',width:100,align:'center',formatter:formatDelete},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
	//分页加载
	$("#carsdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});





	// 查询按钮,根据条件查询
	$('#icon-search').click(function(){ 
        var queryParams = $('#carsdg').datagrid('options').queryParams;
		queryParams.carNumber = $('#carsSearchForm #carNumber').val();
		queryParams.carType =  $('#carsSearchForm #carType').val();
		queryParams.phone =  $('#carsSearchForm #phone').val();
		queryParams.queryType =  $('#carsSearchForm #queryType').val();
		queryParams.startDate =  $('#carsSearchForm #queryStartDate').val();
		queryParams.endDate =  $('#carsSearchForm #queryEndDate').val();
		queryParams.transportType =$('#carsSearchForm #transportType').val();
		
		var queryUrl=CONTEXT+'cars/queryByParameters';
		$('#carsdg').datagrid({
			url:queryUrl,
			//width: 1000,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#carstb',
			pageSize:10,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
                     {field:'id',title:'',width:70,checkbox:true},
                     {field:'carNumber',title:'车牌号码',width:100,align:'center'},
                     {field:'carTypeName',title:'车辆类型',width:100,align:'center'},
                     {field:'maxLoad',title:'载重(吨)',width:100,align:'center'},
                     {field:'carLength',title:'车长(米)',width:100,align:'center'},
         			 {field:'userTypeName',title:'用户类型',width:100,align:'center'},
         			 {field:'companyName',title:'企业名称',width:100,align:'center'},
                     {field:'nickName',title:'联系人',width:100,align:'center'},
                     {field:'phone',title:'电话号码',width:100,align:'center'},
                     {field:'createTimeString',title:'发布时间',width:100,align:'center'},
                     {field:'isDeleted',title:'删除状态',width:100,align:'center',formatter:formatDelete},
					 {field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
					]]
		}); 
		//分页加载
		$("#carsdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	});
	
	
	
	
	
	
	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#carsdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("id");
	    		jQuery.post(CONTEXT+"cars/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#carsdg').datagrid('reload');
						$('#carsdg').datagrid("uncheckAll");

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
		
		
		var row = $('#carsdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
	    
	    var carsId = getSelections("id");
		if(carsId.indexOf(",")>0){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
	    
		$('#addDialog').dialog({'title':'编辑', 'width': 400,    
		    'height': 300, 'href':CONTEXT+'cars/edit?id='+carsId}).dialog('open');

	});
	
	
	
	
	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"cars/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#carsdg').datagrid('reload');
						$('#carsdg').datagrid("uncheckAll");
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
		var transportType =$("#transportType").val();
		$('#addDialog').dialog({'title':'新增车辆','width': 400,    
		    'height': 300, 'href':CONTEXT+'cars/addDto?transportType='+transportType}).dialog('open');
	});
	
	//重置
	$('#btn-reset').click(function(){
		$('#carsSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		var transportType =$("#transportType").val();
		$('#carsSearchForm')[0].reset();
		$('#carsdg').datagrid('options').pageNumber=1;//设置页码初始值为1
		$('#carsdg').datagrid({url:CONTEXT+'cars/query?transportType='+transportType});
		disableExport = false;
	});
	
	//数据导出
	$("#exportData").click(function(){
		  /* var queryParams = $('#carsdg').datagrid('options').queryParams;
			queryParams.carNumber = $('#carsSearchForm #carNumber').val();
			queryParams.carType =  $('#carsSearchForm #carType').val();
			queryParams.phone =  $('#carsSearchForm #phone').val();
			queryParams.queryType =  $('#carsSearchForm #queryType').val();
			queryParams.startDate =  $('#carsSearchForm #queryStartDate').val();
			queryParams.endDate =  $('#carsSearchForm #queryEndDate').val();
		    queryParams.transportType =$('#carsSearchForm #transportType').val();

			
		window.location.href=CONTEXT+'cars/exportData?carNumber='+queryParams.carNumber+
				"&carType="+queryParams.carType+"&phone="+queryParams.phone+"&queryType="+queryParams.queryType
				+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
				+"&transportType="+queryParams.transportType;*/
		var queryParams = {
			"carNumber" : $('#carsSearchForm #carNumber').val(),
			"carType" : $('#carsSearchForm #carType').val(),
			"phone" : $('#carsSearchForm #phone').val(),
			"queryType" : $('#carsSearchForm #queryType').val(),
			"startDate" : $('#carsSearchForm #queryStartDate').val(),
			"endDate" : $('#carsSearchForm #queryEndDate').val(),
			"transportType":$('#carsSearchForm #transportType').val()
		};
		
		var paramList = "carNumber="+queryParams.carNumber+
			"&carType="+queryParams.carType+"&phone="+queryParams.phone+"&queryType="+queryParams.queryType
			+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate+"&transportType="+queryParams.transportType;
		
		$.ajax({
			url: CONTEXT+'cars/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'cars/exportData',paramList,'post');
					}else{
						slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
					}
				}else{
					warningMessage(data.message);
				}
			},
			error : function(data){
				warningMessage(data);
			}
		});	});

	jQuery.download = function(url, data, method){
		// 获得url和data
	    if( url && data ){
	        // data 是 string或者 array/object
	        data = typeof data == 'string' ? data : jQuery.param(data);
	        // 把参数组装成 form的  input
	        var inputs = '';
	        jQuery.each(data.split('&'), function(){
	            var pair = this.split('=');
	            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
	        });
	        // request发送请求
	        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
	        	.appendTo('body').submit().remove();
	    };
	};
	
	function formatDelete(val,row){
		if(val=="1"){
			return "已删除";
		}else if(val=="0"){
			return "未删除";
		}
	}