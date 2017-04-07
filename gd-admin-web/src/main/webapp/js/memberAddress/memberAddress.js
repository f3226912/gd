
$(document).ready(function(){
	//数据加载
	$('#consignmentdg').datagrid({
		url:CONTEXT+'consignment/query',
		//width: 1000,
		height: 'auto',
		nowrap:true,
		toolbar:'#consignmenttb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#consignmentdg").datagrid('clearSelections');
		},
		columns:[[

			{field:'id',title:'',width:100,checkbox:true},
			{field:'startPlace',title:'始发地',width:100,align:'center'},
			{field:'endPlace',title:'目的地',width:100,align:'center'},
			{field:'nickName',title:'姓名',width:100,align:'center'},
			{field:'userMobile',title:'手机号码',width:100,align:'center'},
			{field:'userTypeName',title:'用户类型',width:100,align:'center'},
			{field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'companyMobile',title:'企业联系电话',width:100,align:'center'},
			//{field:'goodsName',title:'货物名称',width:100,align:'center'},
			{field:'goodsTypeString',title:'货物类型',width:100,align:'center'},
			{field:'hundredweightString',title:'总重量',width:100,align:'center'},
			//{field:'totalSize',title:'总体积(立方)',width:100,align:'center'},
			{field:'createTimeString',title:'发布时间',width:100,align:'center'},
			{field:'baseCity',title:'常用城市',width:100,align:'center'},
			{field:'orderStatus',title:'订单状态',width:100,align:'center'},
			{field:'isOrderDeleted',title:'订单是否删除',width:100,align:'center'},
			{field:'clients',title:'发布来源',width:100,align:'center' },

			//{field:'sendDateString',title:'装车时间',width:100,align:'center'},
			//{field:'sendGoodsTypeString',title:'发运方式',width:100,align:'center'},
			//{field:'priceString',title:'价格(元)',width:100,align:'center'},

			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	});
	//分页加载
	$("#consignmentdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

});

	// 查询按钮,根据name查询
	$('#icon-search').click(function(){
		displayList();
	});

	function displayList()
	{
		 var queryParams = $('#consignmentdg').datagrid('options').queryParams;
			queryParams.realName = $('#consignmentSearchForm #realName').val();
			queryParams.startDate =  $('#consignmentSearchForm #queryStartDate').val();
			queryParams.endDate =  $('#consignmentSearchForm #queryEndDate').val();
			queryParams.queryType =  $('#consignmentSearchForm #queryType').val();
			queryParams.cityName =  $('#consignmentSearchForm #cityName').val();
			queryParams.orderStatus =  $('#consignmentSearchForm #orderStatus').val();
			queryParams.isOrderDeleted =  $('#consignmentSearchForm #isOrderDeleted').val();
			queryParams.clients = $("#consignmentSearchForm #clients").val();
			var queryUrl=CONTEXT+'consignment/queryByName';
			$('#consignmentdg').datagrid({
				url:queryUrl,
				//width: 1000,
				height: 'auto',
				nowrap:true,
				toolbar:'#consignmenttb',
				pageSize:10,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				columns:[[
				      	{field:'id',title:'',width:200,checkbox:true},
						{field:'startPlace',title:'始发地',width:100,align:'center'},
						{field:'endPlace',title:'目的地',width:100,align:'center'},
						{field:'nickName',title:'姓名',width:100,align:'center'},
						{field:'userMobile',title:'手机号码',width:100,align:'center'},
						{field:'userTypeName',title:'用户类型',width:100,align:'center'},
						{field:'companyName',title:'企业名称',width:100,align:'center'},
						{field:'companyMobile',title:'企业联系电话',width:100,align:'center'},
						//{field:'goodsName',title:'货物名称',width:100,align:'center'},
						{field:'goodsTypeString',title:'货物类型',width:100,align:'center'},
						{field:'hundredweightString',title:'总重量',width:100,align:'center'},
						//{field:'totalSize',title:'总体积(立方)',width:100,align:'center'},
						{field:'createTimeString',title:'发布时间',width:100,align:'center'},
					    {field:'baseCity',title:'常用城市',width:100,align:'center'},
					    {field:'orderStatus',title:'订单状态',width:100,align:'center'},
						{field:'isOrderDeleted',title:'订单是否删除',width:100,align:'center'},
						{field:'clients',title:'发布来源',width:100,align:'center' },
					  //{field:'sendDateString',title:'装车时间',width:100,align:'center'},
					  //{field:'sendGoodsTypeString',title:'发运方式',width:100,align:'center'},
					  //{field:'priceString',title:'价格(元)',width:100,align:'center'},
					    {field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				  return "<a class='operate' href='javascript:;'onclick=editObj('"+row.id+"');>修改</a>&nbsp;" +
						 "<a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a>";
					}}
				]]
			});
			//分页加载
			$("#consignmentdg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
	}





	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#consignmentdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }

		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("id");
	    		jQuery.post(CONTEXT+"consignment/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#consignmentdg').datagrid('reload');
						$('#consignmentdg').datagrid("uncheckAll");

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


		var row = $('#consignmentdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }


	    var consignmentId = getSelections("id");
		if(consignmentId.indexOf(",")>0){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}

		$('#addDialog').dialog({'title':'编辑', 'width': 620,
		    'height': 500,'href':CONTEXT+'consignment/edit?id='+consignmentId}).dialog('open');

	});




	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"consignment/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#consignmentdg').datagrid('reload');
						$('#consignmentdg').datagrid("uncheckAll");
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
		    'height': 500,'href':CONTEXT+'consignment/edit?id='+id}).dialog('open');
 }


//新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'新增', 'width': 620,
		    'height': 500,'href':CONTEXT+'consignment/addDto'}).dialog('open');
	});

	//重置
	$('#btn-reset').click(function(){
		$('#consignmentSearchForm')[0].reset();
	});

	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#consignmentSearchForm')[0].reset();

		$('#consignmentdg').datagrid('options').pageNumber=1;//设置页码初始值为1
		//数据加载
		$('#consignmentdg').datagrid({url:CONTEXT+'consignment/query'});
		disableExport = false ;
	});

	var disableExport = false ;
	//数据导出
	$("#exportData").click(function(){
		   /* var queryParams = $('#consignmentdg').datagrid('options').queryParams;
			queryParams.realName = $('#consignmentSearchForm #realName').val();
			queryParams.startDate =  $('#consignmentSearchForm #queryStartDate').val();
			queryParams.endDate =  $('#consignmentSearchForm #queryEndDate').val();
			queryParams.queryType =  $('#consignmentSearchForm #queryType').val();
			queryParams.cityName =  $('#consignmentSearchForm #cityName').val();
			queryParams.orderStatus =  $('#consignmentSearchForm #orderStatus').val();
			queryParams.isOrderDeleted =  $('#consignmentSearchForm #isOrderDeleted').val();*/

		var queryParams = {
			"realName" : $('#consignmentSearchForm #realName').val(),
			"startDate" : $('#consignmentSearchForm #queryStartDate').val(),
			"endDate" : $('#consignmentSearchForm #queryEndDate').val(),
			"queryType" : $('#consignmentSearchForm #queryType').val(),
			"cityName" : $('#consignmentSearchForm #cityName').val(),
			"orderStatus" : $('#consignmentSearchForm #orderStatus').val(),
			"isOrderDeleted" :$('#consignmentSearchForm #isOrderDeleted').val(),
			"clients":$("#consignmentSearchForm #clients").val()

		}

		var paramList = "realName="+queryParams.realName+
			"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
			+"&cityName="+queryParams.cityName +"&queryType="+queryParams.queryType
			+"&orderStatus="+queryParams.orderStatus+"&isOrderDeleted="+queryParams.isOrderDeleted
			+"&clients="+queryParams.clients;

		/*window.location.href=CONTEXT+'consignment/exportData?realName='+queryParams.realName+
				"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
				+"&cityName="+queryParams.cityName +"&queryType="+queryParams.queryType
				+"&orderStatus="+queryParams.orderStatus
				+"&isOrderDeleted="+queryParams.isOrderDeleted;	*/
		$.ajax({
			url: CONTEXT+'consignment/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'consignment/exportData',paramList,'post');
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

	//导出已删除数据
	$("#exportIsDelData").click(function(){

		var queryParams = {
			"startDate" : $('#consignmentSearchForm #queryStartDate').val(),
			"endDate" : $('#consignmentSearchForm #queryEndDate').val(),
			"isDel" : 1
		}
		var paramList = "isDel="+queryParams.isDel+
			"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;

//		window.location.href=CONTEXT+'consignment/exportData?isDel=1';
		$.ajax({
			url: CONTEXT+'consignment/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					//启动下载
					$.download(CONTEXT+'consignment/exportData',paramList,'post');
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

    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
             //查询
        	 displayList();
        }
    };

