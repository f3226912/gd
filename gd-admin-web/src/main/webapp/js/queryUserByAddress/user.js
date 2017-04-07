var disableExport = false;
$(document).ready(function(){
	
	//数据加载
	$('#userdg').datagrid({
		url:CONTEXT+'queryUserByAddress/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#usertb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#userdg").datagrid('clearSelections');
		},
		columns:[[
		          
			{field:'id',title:'',width:70,checkbox:true},
			{field:'mobile',title:'推荐人手机号',width:100,align:'center'},
			{field:'userName',title:'推荐人姓名',width:100,align:'center'},
			{field:'recommendedMobile',title:'被推荐人手机号',width:100,align:'center'},
			{field:'recommendedUserName',title:'被推荐人姓名',width:100,align:'center'},
			{field:'levelType',title:'被推荐人角色',width:100,align:'center'},
			{field:'userType',title:'用户类型',width:100,align:'center'},
			{field:'companyName',title:'公司名称',width:100,align:'center'},
			{field:'linkMan',title:'联系人',width:100,align:'center'},
			{field:'createUserTime',title:'被推荐人注册时间',width:100,align:'center'},
			{field:'createAddressTime',title:'被推荐人发布货源时间',width:100,align:'center'},
			{field:'nstStatus',title:'认证状态',width:100,align:'center'},
			{field:'isAddressDeleted',title:'货源状态',width:100,align:'center'},
			{field:'areaName',title:'所属区域',width:100,align:'center'},
			{field:'startPlace',title:'始发地',width:100,align:'center'},
			{field:'endPlace',title:'目的地',width:100,align:'center'},
			{field:'isReferees',title:'是否被推荐',width:100,align:'center',formatter:isRefereesFormat}

		]]
	}); 
	//分页加载
	$("#userdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});




	// 查询按钮,根据条件查询
	$('#icon-search').click(function(){ 
        var queryParams = $('#userdg').datagrid('options').queryParams;
        queryParams.areaName =$('#userSearchForm #areaName').val();
		queryParams.mobile =  $('#userSearchForm #mobile').val();
		queryParams.recommendedMobile =  $('#userSearchForm #recommendedMobile').val();
		queryParams.startDate =  $('#userSearchForm #queryStartDate').val();
		queryParams.endDate =  $('#userSearchForm #queryEndDate').val();
		queryParams.queryAddressStartDate =  $('#userSearchForm #queryAddressStartDate').val();
		queryParams.queryAddressEndDate =  $('#userSearchForm #queryAddressEndDate').val();
		
		//始发地，目的地
	    queryParams.start_provinceId =$('#userSearchForm #start_provinceId').val();
	    queryParams.start_cityId =$('#userSearchForm #start_cityId').val();
	    queryParams.start_areaId =$('#userSearchForm #start_areaId').val();
	    queryParams.end_provinceId =$('#userSearchForm #end_provinceId').val();
	    queryParams.end_cityId =$('#userSearchForm #end_cityId').val();
	    queryParams.end_areaId =$('#userSearchForm #end_areaId').val();
	    queryParams.isReferees =$('#userSearchForm #isReferees').val();


		var queryUrl=CONTEXT+'queryUserByAddress/query?areaName='+queryParams.areaName+"&mobile="+queryParams.mobile
		+"&recommendedMobile="+queryParams.recommendedMobile		
		+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
		+"&queryAddressStartDate="+queryParams.queryAddressStartDate+"&queryAddressEndDate="+queryParams.queryAddressEndDate
		+"&start_provinceId="+queryParams.start_provinceId	
		+"&start_cityId="+queryParams.start_cityId	
		+"&start_areaId="+queryParams.start_areaId	
		+"&end_provinceId="+queryParams.end_provinceId	
		+"&end_cityId="+queryParams.end_cityId	
		+"&end_areaId="+queryParams.end_areaId
		+"&isReferees="+queryParams.isReferees;
		$('#userdg').datagrid({
			url:queryUrl,
			//width: 1000,  
			height: 'auto', 
			nowrap:true,
			toolbar:'#usertb',
			pageSize:10,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			fit:true,
			columns:[[
			      	{field:'id',title:'',width:70,checkbox:true},
					{field:'mobile',title:'推荐人手机号',width:100,align:'center'},
					{field:'userName',title:'推荐人姓名',width:100,align:'center'},
					{field:'recommendedMobile',title:'被推荐人手机号',width:100,align:'center'},
					{field:'recommendedUserName',title:'被推荐人姓名',width:100,align:'center'},
					{field:'levelType',title:'被推荐人角色',width:100,align:'center'},
					{field:'userType',title:'用户类型',width:100,align:'center'},
					{field:'companyName',title:'公司名称',width:100,align:'center'},
					{field:'linkMan',title:'联系人',width:100,align:'center'},
					{field:'createUserTime',title:'被推荐人注册时间',width:100,align:'center'},
					{field:'createAddressTime',title:'被推荐人发布货源时间',width:100,align:'center'},
					{field:'nstStatus',title:'认证状态',width:100,align:'center'},
					{field:'isAddressDeleted',title:'货源状态',width:100,align:'center'},
					{field:'areaName',title:'所属区域',width:100,align:'center'},
					{field:'startPlace',title:'始发地',width:100,align:'center'},
					{field:'endPlace',title:'目的地',width:100,align:'center'},
					{field:'isReferees',title:'是否被推荐',width:100,align:'center',formatter:isRefereesFormat}
					]]
		}); 
		//分页加载
		$("#userdg").datagrid("getPager").pagination({
			pageList: [10,20,50,100]
		});
	});

	//重置
	$('#btn-reset').click(function(){
		$('#userSearchForm').form('reset');
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){	
		$('#userSearchForm').form('reset');
		$('#userdg').datagrid('options').pageNumber=1;//设置页码初始值为1
		var queryUrl='queryUserByAddress/query?areaName='+"&mobile="
		+"&recommendedMobile="		
		+"&startDate="+"&endDate="
		+"&queryAddressStartDate="+"&queryAddressEndDate="
		+"&start_provinceId="+"&start_cityId="+"&start_areaId="
		+"&end_provinceId="+"&end_cityId="+"&end_areaId="+"&isReferees=";
		$('#userdg').datagrid({url:CONTEXT+queryUrl});
		disableExport = false;
	});
	
	//数据导出
	$("#exportData").click(function(){
		
/*		    var queryParams = $('#userdg').datagrid('options').queryParams;
		    queryParams.areaName =$('#userSearchForm #areaName').val();
			queryParams.mobile =  $('#userSearchForm #mobile').val();
			queryParams.recommendedMobile =  $('#userSearchForm #recommendedMobile').val();
			queryParams.startDate =  $('#userSearchForm #queryStartDate').val();
			queryParams.endDate =  $('#userSearchForm #queryEndDate').val();
			queryParams.queryAddressStartDate =  $('#userSearchForm #queryAddressStartDate').val();
			queryParams.queryAddressEndDate =  $('#userSearchForm #queryAddressEndDate').val();
		
			//始发地，目的地
		    queryParams.start_provinceId =$('#userSearchForm #start_provinceId').val();
		    queryParams.start_cityId =$('#userSearchForm #start_cityId').val();
		    queryParams.start_areaId =$('#userSearchForm #start_areaId').val();
		    queryParams.end_provinceId =$('#userSearchForm #end_provinceId').val();
		    queryParams.end_cityId =$('#userSearchForm #end_cityId').val();
		    queryParams.end_areaId =$('#userSearchForm #end_areaId').val();
			
			window.location.href=CONTEXT+'queryUserByAddress/exportData?areaName='+queryParams.areaName+"&mobile="+queryParams.mobile
		+"&recommendedMobile="+queryParams.recommendedMobile		
		+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
		+"&queryAddressStartDate="+queryParams.queryAddressStartDate+"&queryAddressEndDate="+queryParams.queryAddressEndDate
		+"&start_provinceId="+queryParams.start_provinceId	
		+"&start_cityId="+queryParams.start_cityId	
		+"&start_areaId="+queryParams.start_areaId	
		+"&end_provinceId="+queryParams.end_provinceId	
		+"&end_cityId="+queryParams.end_cityId	
		+"&end_areaId="+queryParams.end_areaId;*/
			
			var queryParams = {
				"areaName" : $('#userSearchForm #areaName').val(),
				"mobile" : $('#userSearchForm #mobile').val(),
				"recommendedMobile" : $('#userSearchForm #recommendedMobile').val(),
				"startDate" : $('#userSearchForm #queryStartDate').val(),
				"endDate" : $('#userSearchForm #queryEndDate').val(),
				"queryAddressStartDate" : $('#userSearchForm #queryAddressStartDate').val(),
				"queryAddressEndDate" : $('#userSearchForm #queryAddressEndDate').val(),
				"start_provinceId" : $('#userSearchForm #start_provinceId').val(),
				"start_cityId" : $('#userSearchForm #start_cityId').val(),
				"start_areaId" : $('#userSearchForm #start_areaId').val(),
				"end_provinceId" : $('#userSearchForm #end_provinceId').val(),
				"end_cityId" : $('#userSearchForm #end_cityId').val(),
				"end_areaId" : $('#userSearchForm #end_areaId').val(),
				"isReferees": $('#userSearchForm #isReferees').val()
			};
			
			var paramList = "areaName="+queryParams.areaName+"&mobile="+queryParams.mobile
				+"&recommendedMobile="+queryParams.recommendedMobile		
				+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
				+"&queryAddressStartDate="+queryParams.queryAddressStartDate+"&queryAddressEndDate="+queryParams.queryAddressEndDate
				+"&start_provinceId="+queryParams.start_provinceId	
				+"&start_cityId="+queryParams.start_cityId	
				+"&start_areaId="+queryParams.start_areaId	
				+"&end_provinceId="+queryParams.end_provinceId	
				+"&end_cityId="+queryParams.end_cityId	
				+"&end_areaId="+queryParams.end_areaId
				+"&isReferees="+queryParams.isReferees;
			$.ajax({
				url: CONTEXT+'queryUserByAddress/checkExportParams/2',
				data : queryParams,
				type:'post',
				success : function(data){
					//检测通过
					if (data && data.status == 1){
						if (!disableExport){
							slideMessage("数据正在导出中, 请耐心等待...");
							disableExport = true ;
							//启动下载
							window.location.href=CONTEXT+'queryUserByAddress/exportData?'+paramList;
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
	
	function isRefereesFormat(val, row){
		if (val != null && val != "") {
			return "是";
		}else{
			return "否";
		}
	}
	
