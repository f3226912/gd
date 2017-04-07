
$(document).ready(function(){
	//数据加载
	$('#businessdg').datagrid({
		url:CONTEXT+'business/query',
		//width: 1000,
		height: 'auto',
		nowrap:true,
		toolbar:'#businesstb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#businessdg").datagrid('clearSelections');
		},
		columns:[[
					//{field:'businessId',title:'',width:0,checkbox:true},
					{field:'businessId',title:'商铺ID',width:100,align:'center' },
					//{field:'name',title:'公司名称',width:100,align:'center' },
					{field:'shopsName',title:'商铺名称',width:100,align:'center' },
					{field:'account',title:'用户账号',width:100,align:'center' },
					{field:'mobile',title:'电话号码',width:100,align:'center' },
					{field:'level',title:'用户类型',width:100,align:'center',formatter:formatterLevel },
					{field:'marketName',title:'所属市场',width:100,align:'center' },
					{field:'cateNames',title:'主营分类',width:100,align:'center' },
					//{field:'mainProduct',title:'主营商品',width:100,align:'center' },
					{field:'posNumber',title:'终端号',width:100,align:'center'},
					{field:'offlineStatus',title:'线下认证',width:100,align:'center',formatter:formatterOfflineStatus},
					//{field:'createTime',title:'开通时间',width:100,align:'center' },
					{field:'shopsUrl',title:'商铺地址',width:200,align:'center' },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	});
	//分页加载
	$("#businessdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

});


// 查询按钮,根据name查询
$('#icon-search').click(function(){
 var queryParams = $('#businessdg').datagrid('options').queryParams;
	queryParams.businessId = $('#businessSearchForm #businessId1').val();
//	queryParams.name = $('#businessSearchForm #name').val();
	queryParams.shopsName = $('#businessSearchForm #shopsName').val();
//	queryParams.startDate = $('#businessSearchForm #startDate').val();
//	queryParams.endDate = $('#businessSearchForm #endDate').val();
	queryParams.level = $('#businessSearchForm #level').val();
	queryParams.marketId = $('#businessSearchForm #marketId_add').val();
	//queryParams.mainProduct = $('#businessSearchForm #mainProduct').val();
	queryParams.account = $('#businessSearchForm #account').val();
	queryParams.mobile = $('#businessSearchForm #mobile').val();
	queryParams.posNumber = $('#businessSearchForm #posNumber').val();
	queryParams.offlineStatus = $('#businessSearchForm #offlineStatus').val();
	queryParams.categoryID = 	$('#businessSearchForm input[name="categoryID"]:checked ').val();


	queryParams.provinceId = $('#businessSearchForm #provinceId1').val();
	queryParams.cityId = $('#businessSearchForm #cityId1').val();
	queryParams.areaId = $('#businessSearchForm #areaId1').val();
	queryParams.termType = $('#businessSearchForm #termType').val();
	/*if($("#checkAddress").is(':checked')){
		queryParams.provinceId = $('#businessSearchForm #provinceId1').val();
		queryParams.cityId = $('#businessSearchForm #cityId1').val();
		queryParams.areaId = $('#businessSearchForm #areaId1').val();
	}else{
		queryParams.provinceId = "";
		queryParams.cityId = "";
		queryParams.areaId = "";
	}*/

	queryParams.address = $('#businessSearchForm #address1').val();
	var queryUrl=CONTEXT+'business/querybysearch';

	$('#businessdg').datagrid({
		url:queryUrl,
		height: 'auto',
		nowrap:true,
		toolbar:'#businesstb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					//{field:'businessId',title:'',width:0,checkbox:true},
					{field:'businessId',title:'商铺ID',width:100,align:'center' },
					//{field:'name',title:'公司名称',width:100,align:'center' },
					{field:'shopsName',title:'商铺名称',width:100,align:'center' },
					{field:'account',title:'用户账号',width:100,align:'center' },
					{field:'mobile',title:'电话号码',width:100,align:'center' },
					//{field:'createTime',title:'开通时间',width:100,align:'center' },
					{field:'level',title:'用户类型',width:100,align:'center',formatter:formatterLevel },
					{field:'marketName',title:'所属市场',width:100,align:'center' },
					{field:'cateNames',title:'主营分类',width:100,align:'center' },
					//{field:'mainProduct',title:'主营商品',width:100,align:'center' },
					{field:'posNumber',title:'终端号',width:100,align:'center' },
					{field:'offlineStatus',title:'线下认证',width:100,align:'center',formatter:formatterOfflineStatus},
					{field:'shopsUrl',title:'商铺地址',width:200,align:'center' },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	});
	//分页加载
	$("#businessdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

});

function formatterLevel(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="4"){
			return "产地供应商";
		} else  {
			return "农批商";
		}
	}
}

function formatterOfflineStatus(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "已认证";
		} else if(str=="2")  {
			return "未认证";
		} else if(str=="-1"){
			return "";
		}
	} 
}







function formatterAuth(val, row) {
	if (val != null) {
	   var  str=val.toString();
	   if(str=="1"){
		   return "已认证";
	   }else if(str=="0"){
		   return "待认证";
	   }else if(str=="2"){
		   return "已驳回";
	   }
	}else{
		   return "待认证";
	}
}

function formatterStatus(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "启用";
		}else if(str=="0"){
			return "禁用";
		}
	}else{
		return "禁用";
	}
}

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
	var url=CONTEXT+"business/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#businessdg").datagrid('reload');
				$('#addDialog').dialog('close');
				$('#editDialog').dialog('close');
			} else if(data == "shopsName"){
				warningMessage('商铺名称不能为空！');
				return;
			} else if(data == "categoryId"){
				warningMessage('主营分类不能为空！');
				return;
			} else if(data == "model"){
				warningMessage('经营模式不能为空！');
				return;
			}else if(data == "marketId"){
				warningMessage('所属市场不能为空！');
				return;
			}else if(data == "level"){
				warningMessage('经销类型不能为空！');
				return;
			}else if(data == "managementType"){
				warningMessage('产地供应商经营类型不能为空！');
				return;
			}else {
				warningMessage(data);
				return;
			}
		});
}



	//编辑
	$("#icon-edit").click(function(){


		var row = $('#businessdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }


	    var deleteStr = getSelections("businessId");
		if($(row).length  >1){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
		$('#editDialog').dialog({'title':'编辑DTO','href':CONTEXT+'business/editbyid/'+deleteStr,'width': 400,'height': 500}).dialog('open');
	});



// 编辑
 function editObj(id){
		$('#editDialog').dialog({'title':'商铺信息','href':CONTEXT+'business/editbyid/'+id,'width': 1000,'height': 500}).dialog('open');
//		$('<div></div>').dialog({
//		   id : 'editDialog',
//		   title : '商铺信息',
//		   width : 1000,
//		   height : 500,
//		   closed : false,
//		   cache : false,
//		   href : CONTEXT+'business/editbyid/'+id,
//		   modal : true,
//		   onLoad : function() {
//		   },
//		   onClose : function() {
//			   $(this).dialog('destroy');
//		   },
//		   buttons : [  {
//			   text : '关闭',
//			   iconCls : 'icon-cancel',
//			   handler : function() {
//				   $("#editDialog").dialog('destroy');
//			   }
//		   } ],
//	   });
 }

 //查看
 function showObj(id){
		$('<div></div>').dialog({
		   id : 'showDialog',
		   title : '商铺信息',
		   width : 600,
		   height : 500,
		   closed : false,
		   cache : false,
		   href : CONTEXT+'business/showbyid/'+id,
		   modal : true,
		   onLoad : function() {
		   },
		   onClose : function() {
			   $(this).dialog('destroy');
		   },
		   buttons : [  {
			   text : '关闭',
			   iconCls : 'icon-cancel',
			   handler : function() {
				   $("#showDialog").dialog('destroy');
			   }
		   } ],
	   });
//		$('<div></div>').dialog({
//		   id : 'showDialog',
//		   title : '商铺信息',
//		   width : 600,
//		   height : 500,
//		   closed : false,
//		   cache : false,
//		   href : CONTEXT+'business/showbyid/'+id,
//		   modal : true,
//		   onLoad : function() {
//		   },
//		   onClose : function() {
//			   $(this).dialog('destroy');
//		   },
//		   buttons : [  {
//			   text : '关闭',
//			   iconCls : 'icon-cancel',
//			   handler : function() {
//				   $("#showDialog").dialog('destroy');
//			   }
//		   } ],
//	   });
 }


//新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'新增商铺','href':CONTEXT+'business/addDto', 'width': 400,'height': 500}).dialog('open');
	});

	//重置
	$('#btn-reset').click(function(){
		$('#showMarketWin').val("选择市场");
		$('#marketId_add').val("");
		$("#categorys").empty();
		var city = new City($("#provinceId1"),$("#cityId1"),$("#areaId1"));
 		city.init('0','0','0');
		$('#businessSearchForm')[0].reset();
	});

	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#showMarketWin').val("选择市场");
		$('#marketId_add').val("");
		$("#categorys").empty();
		var city = new City($("#provinceId1"),$("#cityId1"),$("#areaId1"));
 		city.init('0','0','0');
		$('#businessSearchForm')[0].reset();
		$("#businessdg").datagrid('load',{});
		//重启导出功能
		disableExport = false ;
	});

	$("#exportData").click(function(){
/*		var queryParams = $('#businessdg').datagrid('options').queryParams;
		queryParams.businessId = $('#businessSearchForm #businessId1').val();
//		queryParams.name = $('#businessSearchForm #name').val();
		queryParams.shopsName = $('#businessSearchForm #shopsName').val();
//		queryParams.startDate = $('#businessSearchForm #startDate').val();
//		queryParams.endDate = $('#businessSearchForm #endDate').val();
		queryParams.level = $('#businessSearchForm #level').val();
		queryParams.marketId = $('#businessSearchForm #marketId_add').val();
		//queryParams.mainProduct = $('#businessSearchForm #mainProduct').val();
		queryParams.account = $('#businessSearchForm #account').val();
		queryParams.posNumber = $('#businessSearchForm #posNumber').val();
		queryParams.offlineStatus = $('#businessSearchForm #offlineStatus').val();
		queryParams.categoryID = 	$('#businessSearchForm input[name="categoryID"]:checked ').val();
		//queryParams.provinceId = $('#businessSearchForm #provinceId1').val();
		//queryParams.cityId = $('#businessSearchForm #cityId1').val();
		//queryParams.areaId = $('#businessSearchForm #areaId1').val();
		//queryParams.address = $('#businessSearchForm #address1').val();
*/
		var params = {
				"businessId" : $('#businessSearchForm #businessId1').val(),
				"shopsName" : $('#businessSearchForm #shopsName').val(),
				"level" : $('#businessSearchForm #level').val(),
				"marketId" : $('#businessSearchForm #marketId_add').val(),
				"account" : $('#businessSearchForm #account').val(),
				"posNumber" : $('#businessSearchForm #posNumber').val(),
				"offlineStatus" : $('#businessSearchForm #offlineStatus').val(),
				"termType" : $('#businessSearchForm #termType').val(),
				"categoryID" : $('#businessSearchForm input[name="categoryID"]:checked ').val()
			};
		var paramList = 'shopsName='+params.shopsName +"&level="+params.level+ "&businessId="+params.businessId
	       +"&marketId="+params.marketId +"&account="+params.account +"&posNumber="+params.posNumber
	       +"&offlineStatus="+params.offlineStatus+"&termType="+params.termType;

/*		var url=CONTEXT+'business/exportdata?shopsName='+queryParams.shopsName
		       +"&level="+queryParams.level
		       +"&marketId="+queryParams.marketId
		       +"&account="+queryParams.account
		       +"&posNumber="+queryParams.posNumber
		// +"&provinceId="+queryParams.provinceId
		      // +"&cityId="+queryParams.cityId
			  // +"&areaId="+queryParams.areaId
			  // +"&address="+queryParams.address
		;*/

		if(params.categoryID){
			paramList = paramList + "&categoryID="+params.categoryID;
		}

		$.ajax({
			url: CONTEXT+'business/checkExportParams',
			data : params,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){

					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'business/exportdata',paramList,'post' );
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
		//window.location.href=url;
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



	function ajaxGetCategory(marketId) {
		$("#categorys").empty();
		var categorysurl = CONTEXT+'product/listTopProductCategory/' + marketId;
		$.getJSON(categorysurl,function (data) {
	        $(data).each(function () {
					$("#categorys").append(
								$("<input />").attr(
										"value", this.categoryId).attr("name",
										"categoryID").attr("type", "radio")).append(this.cateName).append("   ");
					});
		});
	}