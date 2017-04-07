var disableExport = false;
$(document).ready(function(){
	//数据加载
	$('#carLinedg').datagrid({
		url:CONTEXT+'carLine/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#carLinetb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#carLinedg").datagrid('clearSelections');
		},
		columns:[[
		          
			{field:'id',title:'',width:100,checkbox:true},
			{field:'carNumber',title:'车牌号码',width:100,align:'center'},
			{field:'carTypeString',title:'车辆类型',width:100,align:'center'},
			{field:'maxLoad',title:'载重(吨)',width:100,align:'center'},
			{field:'carLength',title:'车长(米)',width:100,align:'center'},

			{field:'startPlace',title:'出发地',width:100,align:'center'},
			{field:'endPlace',title:'目的地',width:100,align:'center'},
			{field:'userTypeName',title:'用户类型',width:100,align:'center'},
		    {field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'nickName',title:'联系人',width:100,align:'center'},
			{field:'phone',title:'电话号码',width:100,align:'center'},
			{field:'createTimeString',title:'发布时间',width:100,align:'center'},
			{field:'sendDateString',title:'发车时间',width:100,align:'center'},
			{field:'onLineHours',title:'在途时间(天)',width:100,align:'center'},
			{field:'endDateString',title:'截止时间',width:100,align:'center'},
			{field:'sendGoodsTypeString',title:'发运方式',width:100,align:'center'},
			{field:'unitTypeString',title:'价格',width:100,align:'center'},
			{field:'areaName',title:'所属区域',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
	//分页加载
	$("#carLinedg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


	// 查询按钮,根据name查询
	$('#icon-search').click(function(){ 
        var queryParams = $('#carLinedg').datagrid('options').queryParams;
		queryParams.carType = $('#carLineSearchForm #queryCarType').val();
		queryParams.mobile  = $('#carLineSearchForm #mobile').val();
		queryParams.startDate =  $('#carLineSearchForm #queryStartDate').val();
		queryParams.endDate =  $('#carLineSearchForm #queryEndDate').val();
		queryParams.queryType =  $('#carLineSearchForm #queryType').val();
		queryParams.areaName =  $('#carLineSearchForm #areaName').val();
		var queryUrl=CONTEXT+'carLine/queryByParameters';
		$('#carLinedg').datagrid({
			url:queryUrl,
			//width: 1000,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#carLinetb',
			pageSize:10,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
			{field:'id',title:'',width:100,checkbox:true},
			{field:'carNumber',title:'车牌号码',width:100,align:'center'},
			{field:'carTypeString',title:'车辆类型',width:100,align:'center'},
			{field:'maxLoad',title:'载重(吨)',width:100,align:'center'},
			{field:'carLength',title:'车长(米)',width:100,align:'center'},
			{field:'startPlace',title:'出发地',width:100,align:'center'},
			{field:'endPlace',title:'目的地',width:100,align:'center'},
			{field:'userTypeName',title:'用户类型',width:100,align:'center'},
			{field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'nickName',title:'联系人',width:100,align:'center'},
			{field:'phone',title:'电话号码',width:100,align:'center'},
			{field:'createTimeString',title:'发布时间',width:100,align:'center'},
			{field:'sendDateString',title:'发车时间',width:100,align:'center'},
			{field:'onLineHours',title:'在途时间(天)',width:100,align:'center'},
			{field:'endDateString',title:'截止时间',width:100,align:'center'},
			{field:'sendGoodsTypeString',title:'发运方式',width:100,align:'center'},
			{field:'unitTypeString',title:'价格',width:100,align:'center'},
			{field:'areaName',title:'所属区域',width:100,align:'center'},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
			]]
		}); 
		//分页加载
		$("#carLinedg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	});
	
	
	
	//导出
	$('#btn-export').click(function(){
//		$.messager.confirm('提示', '您确定要导出查询数据吗?', function(r){
//			if (r){
//				$('#carLineSearchForm').submit();
//			}
//		});
		var queryParams = {
			"carType" : $("#carLineSearchForm #queryCarType").val(),
			"mobile" : $("#carLineSearchForm #mobile").val(),
			"queryStartDate" : $("#carLineSearchForm #queryStartDate").val(),
			"queryEndDate" : $("#carLineSearchForm #queryEndDate").val(),
			"queryType" : $("#carLineSearchForm #queryType").val(),
			"areaName" : $("#carLineSearchForm #areaName").val()
		};
		
		var paramList = "carType="+queryParams.carType+
						"&mobile="+queryParams.mobile+
						"&queryStartDate="+queryParams.queryStartDate+
						"&queryEndDate="+queryParams.queryEndDate+
						"&queryType="+queryParams.queryType+
						"&areaName="+queryParams.areaName;
		
		$.ajax({
			url: CONTEXT+'carLine/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'carLine/export',paramList,'post');
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
		});
	});
	
	//导出已删除数据
	$('#isDelExportData').click(function(){
//		window.location.href=CONTEXT+'carLine/export?isDel=1';
		var queryParams = {
			"queryStartDate" : $("#carLineSearchForm #queryStartDate").val(),
			"queryEndDate" : $("#carLineSearchForm #queryEndDate").val(),
			"isDel" : 1
		};
		
		var paramList = "&queryStartDate="+queryParams.queryStartDate+
						"&queryEndDate="+queryParams.queryEndDate+
						"&isDel="+queryParams.isDel;
		$.ajax({
			url: CONTEXT+'carLine/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'carLine/export',paramList,'post');
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
		});
	});
	
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
	
	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#carLinedg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("id");
	    		jQuery.post(CONTEXT+"carLine/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#carLinedg').datagrid('reload');
						$('#carLinedg').datagrid("uncheckAll");

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
		
		var row = $('#carLinedg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }
	    
	    var carLineId = getSelections("id");
		if(carLineId.indexOf(",")>0){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
	    
		$('#addDialog').dialog({'title':'编辑', 'width': 700,     
		    'height': 400, 'href':CONTEXT+'carLine/edit?id='+carLineId}).dialog('open');

	});
	
	
	
	
	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"carLine/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#carLinedg').datagrid('reload');
						$('#carLinedg').datagrid("uncheckAll");
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
		    'height':400, 'href':CONTEXT+'carLine/addDto'}).dialog('open');
	});
	
	$('#queryCarList').click(function(){
		$('#carDialog').dialog({'title':'选择车牌号码', 'width':700, 'height':300, 'href':CONTEXT+'carLine/carList'}).dialog('open');
	});
	
	//重置
	$('#btn-reset').click(function(){
		$('#carLineSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#carLineSearchForm')[0].reset();
	    $('#carLinedg').datagrid('options').pageNumber=1;//设置页码初始值为1
		$('#carLinedg').datagrid({url:CONTEXT+'carLine/query'});
		disableExport = false;
	});