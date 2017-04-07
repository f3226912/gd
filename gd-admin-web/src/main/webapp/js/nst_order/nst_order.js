var disableExport = false;
$(document).ready(function(){
	var transportType =$("#transportType").val();
	//数据加载
	$('#nstOrderdg').datagrid({
		url:CONTEXT+'nst_order/queryByCondition?transportType='+transportType,
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#nstOrdertb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#nstOrderdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'orderNo',title:'运单号',width:100,align:'center'},
					{field:'f_address_detail',title:'起运地',width:100,align:'center'},
					{field:'s_address_detail',title:'目的地',width:100,align:'center'},
					{field:'shipperName',title:'发运人',width:100,align:'center'},
					{field:'shipperMobile',title:'发运人手机号码',width:100,align:'center'},
					{field:'ownerName',title:'承运人',width:100,align:'center'},
					{field:'ownerMobile',title:'承运人手机号码',width:100,align:'center'},
					{field:'orderStatus',title:'订单状态',width:100,align:'center',formatter:formatterStatus },
					{field:'orderTime',title:'接单时间',width:100,align:'center',formatter:formatterdate    },
					{field:'isDeleted',title:'是否删除',width:100,align:'center' , formatter:formatterDelete}
				]]
	}); 
	//分页加载
	$("#nstOrderdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});



// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
 var queryParams = $('#nstOrderdg').datagrid('options').queryParams;
	queryParams.orderNo = $('#nstOrderSearchForm #orderNo').val();
	queryParams.name1 =  $('#nstOrderSearchForm #name1').val();
	queryParams.mobile1 =  $('#nstOrderSearchForm #mobile1').val();
	queryParams.name2 =  $('#nstOrderSearchForm #name2').val();
	queryParams.mobile2 =  $('#nstOrderSearchForm #mobile2').val();
	queryParams.isDeleted = $('#nstOrderSearchForm #isDeleted').val();
	queryParams.orderStatus = $('#nstOrderSearchForm #orderStatus').val();
	queryParams.startDate =  $('#nstOrderSearchForm #startDate').val();
	queryParams.endDate =  $('#nstOrderSearchForm #endDate').val();
	queryParams.transportType = $('#nstOrderSearchForm #transportType').val();
	
	var queryUrl=CONTEXT+'nst_order/queryByCondition';
	$('#nstOrderdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#nstOrdertb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'orderNo',title:'运单号',width:100,align:'center'},
					{field:'f_address_detail',title:'起运地',width:100,align:'center'},
					{field:'s_address_detail',title:'目的地',width:100,align:'center'},
					{field:'shipperName',title:'发运人',width:100,align:'center'},
					{field:'shipperMobile',title:'发运人手机号码',width:100,align:'center'},
					{field:'ownerName',title:'承运人',width:100,align:'center'},
					{field:'ownerMobile',title:'承运人手机号码',width:100,align:'center'},
					{field:'orderStatus',title:'订单状态',width:100,align:'center',formatter:formatterStatus },
					{field:'orderTime',title:'接单时间',width:100,align:'center',formatter:formatterdate    },
					{field:'isDeleted',title:'是否删除',width:100,align:'center' , formatter:formatterDelete}
				]]
	}); 
	//分页加载
	$("#nstOrderdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


//导出
$("#exportData").click(function(){
	/* var queryParams = $('#nstOrderdg').datagrid('options').queryParams;
	queryParams.orderNo = $('#nstOrderSearchForm #orderNo').val();
	queryParams.name1 =  $('#nstOrderSearchForm #name1').val();
	queryParams.mobile1 =  $('#nstOrderSearchForm #mobile1').val();
	queryParams.name2 =  $('#nstOrderSearchForm #name2').val();
	queryParams.mobile2 =  $('#nstOrderSearchForm #mobile2').val();
	queryParams.isDeleted = $('#nstOrderSearchForm #isDeleted').val();
	queryParams.orderStatus = $('#nstOrderSearchForm #orderStatus').val();
	queryParams.startDate =  $('#nstOrderSearchForm #startDate').val();
	queryParams.endDate =  $('#nstOrderSearchForm #endDate').val();
	queryParams.transportType = $('#nstOrderSearchForm #transportType').val();
	
  window.location.href=CONTEXT+'nst_order/exportData?orderNo='+queryParams.orderNo+
						"&name1="+queryParams.name1+"&mobile1="+queryParams.mobile1+
						"&name2="+queryParams.name2+"&mobile2="+queryParams.mobile2+
						"&isDeleted="+queryParams.isDeleted+
						"&orderStatus="+queryParams.orderStatus+
						"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate
						"&transportType="+queryParams.transportType;   */
	
	var queryParams = {
		"orderNo" : $('#nstOrderSearchForm #orderNo').val(),
		"name1" : $('#nstOrderSearchForm #name1').val(), 
		"mobile1" : $('#nstOrderSearchForm #mobile1').val(), 
		"name2" : $('#nstOrderSearchForm #name2').val(), 
		"mobile2" : $('#nstOrderSearchForm #mobile2').val(), 
		"isDeleted" : $('#nstOrderSearchForm #isDeleted').val(), 
		"orderStatus" : $('#nstOrderSearchForm #orderStatus').val(), 
		"startDate" : $('#nstOrderSearchForm #startDate').val(), 
		"endDate" : $('#nstOrderSearchForm #endDate').val(),
		"transportType":$('#nstOrderSearchForm #transportType').val()
   };
   
   var paramList ="orderNo="+queryParams.orderNo+
		"&name1="+queryParams.name1+"&mobile1="+queryParams.mobile1+
		"&name2="+queryParams.name2+"&mobile2="+queryParams.mobile2+
		"&isDeleted="+queryParams.isDeleted+
		"&orderStatus="+queryParams.orderStatus+
		"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate+"&transportType="+queryParams.transportType;
   
   $.ajax({
		url: CONTEXT+'nst_order/checkExportParams',
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'nst_order/exportData',paramList,'post');
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
	});});
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


 
function formatterDelete(val, row) {
	   if(val == null || val.toString()=="0"){
		   return "未删除";
	   }else {
		   return "已删除";
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

 
 
//重置
$('#btn-reset').click(function(){
	$('#nstOrderSearchForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#nstOrderSearchForm')[0].reset();
	var transportType =$("#transportType").val();
	$("#nstOrderdg").datagrid('load', {"transportType":transportType});
	disableExport = false;
});
 