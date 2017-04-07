
$(document).ready(function(){
	var transportType =$("#transportType").val();
	//数据加载
	$('#memberdg').datagrid({
		url:CONTEXT+'publishAddress/query?transportType='+transportType,
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#membertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#memberdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'userName',title:'信息发布人',width:100,align:'center' },
					{field:'account',title:'发布人账号',width:100,align:'center' },
					{field:'createTime',title:'发布时间',width:100,align:'center'},
					{field:'goodsType',title:'货物类型',width:100,align:'center' ,formatter:formatterGoodType },
					{field:'goodsName',title:'货物名称',width:100,align:'center'  },
					{field:'startPlace',title:'起运地',width:100,align:'center'   },
					{field:'endPlace',title:'目的地',width:100,align:'center'},
					{field:'weightString',title:'货物重量',width:100,align:'center' },
					{field:'totalSize',title:'货物体积',width:100,align:'center' ,formatter:formatterTotalSize },
					{field:'distributeType',title:'分配类型',width:100,align:'center' },
					{field:'publishUserName',title:'代发布人',width:100,align:'center' },
					{field:'publishAccount',title:'代发布人账号',width:100,align:'center' },
					{field:'companyName',title:'公司名称',width:100,align:'center' },
					{field:'orderStatus',title:'订单状态',width:100,align:'center' ,formatter:formatterStatus  },
					{field:'clients',title:'发布来源',width:100,align:'center' },
					{field:'cityName',title:'常用城市',width:100,align:'center' }
					//,{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	}); 
	//分页加载
	$("#memberdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});





// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
	var queryParams = $('#memberdg').datagrid('options').queryParams;
	queryParams.account = $('#memberSearchForm #account').val();
	queryParams.companyName = $('#memberSearchForm #companyName').val();
	queryParams.publishAccount = $('#memberSearchForm #publishAccount').val();
	queryParams.queryStartDate =  $('#memberSearchForm #queryStartDate').val();
	queryParams.queryEndDate =  $('#memberSearchForm #queryEndDate').val();
	queryParams.clients = $("#memberSearchForm #clients").val();
	queryParams.distributeType = $("#memberSearchForm #distributeType").val();
	queryParams.cityName = $("#memberSearchForm #cityName").val();
    queryParams.orderStatus = $("#memberSearchForm #orderStatus").val();

    //始发地，目的地
    queryParams.start_provinceId =$('#memberSearchForm #start_provinceId').val();
    queryParams.start_cityId =$('#memberSearchForm #start_cityId').val();
    queryParams.start_areaId =$('#memberSearchForm #start_areaId').val();
    queryParams.end_provinceId =$('#memberSearchForm #end_provinceId').val();
    queryParams.end_cityId =$('#memberSearchForm #end_cityId').val();
    queryParams.end_areaId =$('#memberSearchForm #end_areaId').val();
    
    var transportType  =$('#memberSearchForm #transportType').val();
	var queryUrl=CONTEXT+'publishAddress/querybysearch?transportType='+transportType;
	$('#memberdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#membertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'userName',title:'信息发布人',width:100,align:'center' },
					{field:'account',title:'发布人账号',width:100,align:'center' },
					{field:'createTime',title:'发布时间',width:100,align:'center'},
					{field:'goodsType',title:'货物类型',width:100,align:'center' ,formatter:formatterGoodType },
					{field:'goodsName',title:'货物名称',width:100,align:'center'  },
					{field:'startPlace',title:'起运地',width:100,align:'center'   },
					{field:'endPlace',title:'目的地',width:100,align:'center'},
					{field:'weightString',title:'货物重量',width:100,align:'center' },
					{field:'totalSize',title:'货物体积',width:100,align:'center' ,formatter:formatterTotalSize },
					{field:'distributeType',title:'分配类型',width:100,align:'center' },
					{field:'publishUserName',title:'代发布人',width:100,align:'center' },
					{field:'publishAccount',title:'代发布人账号',width:100,align:'center' },
					{field:'companyName',title:'公司名称',width:100,align:'center' },
					{field:'orderStatus',title:'订单状态',width:100,align:'center' ,formatter:formatterStatus  },
					{field:'clients',title:'发布来源',width:100,align:'center' },
					{field:'cityName',title:'常用城市',width:100,align:'center' }
					//,{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	}); 
	//分页加载
	$("#memberdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


function formatterGoodType(val, row) {
	if (val != null) {
		var  goodType=val.toString();
		switch (goodType) {
		case '0':
			 return "普货";
			break;
		case '1':
			 return "冷藏";
			break;
		case '2':
			 return "鲜活水产";
			break;
		case '3':
			 return "其他";
			break;
		case '4':
			 return "重货";
			break;
		case '5':
			 return "抛货";
			break;
		case '6':
			 return "蔬菜";
			break;
		case '7':
			 return "水果";
			break;
		case '8':
			 return "农副产品";
			break;
		case '9':
			 return "日用品";
			break;
		case '10':
			 return "纺织";
			break;
		case '11':
			 return "木材";
			 break;
		case '101':
			 return "蔬菜水果";
			 break;
		case '102':
			 return "干调粮油";
			 break;
		case '103':
			 return "活鲜水产";
			 break;
		case '104':
			 return "食品饮料";
			 break;
		case '105':
			 return "冷冻商品";
			 break;
		case '106':
			 return "化肥种子";
			 break;
		case '107':
			 return "农资农具";
			 break;
		case '108':
			 return "日用品";
			 break;
		case '109':
			 return "建材设备";
			 break;
		case '110':
			 return "其他商品";
			 break;
	}
  }
}

function formatterStatus(val, row) {
	if (val != null) {
	   var  str=val.toString();
	   if(str=="1"){
		   return "待成交";
	   }else if(str=="2"){
		   return "已成交";
	   }else if(str=="3"){
		   return "已完成";
	   } else if(str=="4"){
		   return "未完成";
	   }else if(str=="5"){
		   return "已取消";
	   }
	}else{
		return "待接单";
	}
}


function formatterTotalSize(val, row) {
	if (val != null &&  parseFloat(val) >0 ) {
	  return val+'立方米';
	}else{
		return "";
	}
}
 
 //查看
 function showObj(id){
		$('#showDialog').dialog({'title':'会员信息','href':CONTEXT+'publishAddress/showbyid/'+id,'width': 500,'height': 300}).dialog('open');
 }

	//重置
	$('#btn-reset').click(function(){
		$('#memberSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#memberSearchForm')[0].reset();
		$("#memberdg").datagrid('load',{});
		//重启导出功能
		disableExport = false ;
	});