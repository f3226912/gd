var disableExport = false;
$(document).ready(function(){
	//数据加载
	$('#nstOrderComplaintdg').datagrid({
		url:CONTEXT+'nst_order_complaint/queryByCondition',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#nstOrderComplainttb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#nstOrderComplaintdg").datagrid('clearSelections');
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
					{field:'complaintStatus',title:'受理状态',width:100,align:'center'   },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#nstOrderComplaintdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});



// 查询按钮,根据name查询
$('#icon-search').click(function(){
 var queryParams = $('#nstOrderComplaintdg').datagrid('options').queryParams;
	queryParams.orderNo = $('#nstOrderComplaintSearchForm #orderNo').val();
	queryParams.name1 =  $('#nstOrderComplaintSearchForm #name1').val();
	queryParams.mobile1 =  $('#nstOrderComplaintSearchForm #mobile1').val();
	queryParams.name2 =  $('#nstOrderComplaintSearchForm #name2').val();
	queryParams.mobile2 =  $('#nstOrderComplaintSearchForm #mobile2').val();
	queryParams.orderStatus = $('#nstOrderComplaintSearchForm #orderStatus').val();
	queryParams.replyStatus = $('#nstOrderComplaintSearchForm #replyStatus').val();
	queryParams.startDate = $('#nstOrderComplaintSearchForm #startDate').val();
	queryParams.endDate = $('#nstOrderComplaintSearchForm #endDate').val();
	var queryUrl=CONTEXT+'nst_order_complaint/queryByCondition';
	$('#nstOrderComplaintdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#nstOrderComplainttb',
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
					{field:'complaintStatus',title:'受理状态',width:100,align:'center'   },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#nstOrderComplaintdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});


//导出
$("#exportData").click(function(){
/*	var queryParams = $('#nstOrderComplaintdg').datagrid('options').queryParams;
	queryParams.orderNo = $('#nstOrderComplaintSearchForm #orderNo').val();
	queryParams.name1 =  $('#nstOrderComplaintSearchForm #name1').val();
	queryParams.mobile1 =  $('#nstOrderComplaintSearchForm #mobile1').val();
	queryParams.name2 =  $('#nstOrderComplaintSearchForm #name2').val();
	queryParams.mobile2 =  $('#nstOrderComplaintSearchForm #mobile2').val();
	queryParams.orderStatus = $('#nstOrderComplaintSearchForm #orderStatus').val();
	queryParams.replyStatus = $('#nstOrderComplaintSearchForm #replyStatus').val();
//	queryParams.startDate =  $('#nstOrderComplaintSearchForm #startDate').val();
//	queryParams.endDate =  $('#nstOrderComplaintSearchForm #endDate').val();

   window.location.href=CONTEXT+'nst_order_complaint/exportData?orderNo='+queryParams.orderNo+
						"&name1="+queryParams.name1+"&mobile1="+queryParams.mobile1+
						"&name2="+queryParams.name2+"&mobile2="+queryParams.mobile2+
						"&orderStatus="+queryParams.orderStatus+
						"&replyStatus="+queryParams.replyStatus;
						//"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;
*/   
   var queryParams = {
		"orderNo" : $('#nstOrderComplaintSearchForm #orderNo').val(),  
		"name1" : $('#nstOrderComplaintSearchForm #name1').val(), 
		"mobile1" : $('#nstOrderComplaintSearchForm #mobile1').val(), 
		"name2" : $('#nstOrderComplaintSearchForm #name2').val(), 
		"mobile2" : $('#nstOrderComplaintSearchForm #mobile2').val(), 
		"orderStatus" : $('#nstOrderComplaintSearchForm #orderStatus').val(), 
		"replyStatus" : $('#nstOrderComplaintSearchForm #replyStatus').val(), 
		"startDate" : $('#nstOrderComplaintSearchForm #startDate').val(),
		"endDate" : $('#nstOrderComplaintSearchForm #endDate').val()
   };
   
   var paramList = "orderNo="+queryParams.orderNo+
		"&name1="+queryParams.name1+"&mobile1="+queryParams.mobile1+
		"&name2="+queryParams.name2+"&mobile2="+queryParams.mobile2+
		"&orderStatus="+queryParams.orderStatus+
		"&replyStatus="+queryParams.replyStatus+
		"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;
	$.ajax({
		url: CONTEXT+'nst_order_complaint/checkExportParams',
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'nst_order_complaint/exportData',paramList,'post');
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

//查看
function showObj(id){
 $('#showDialog').dialog({'title':'查看','href':CONTEXT+'nst_order_complaint/showById/'+id,'width': 600,'height': 500}).dialog('open');
}

//提交回复
function updateStatus() {
	
	if(!$("#updateForm").form('validate')){
		return $("#updateForm").form('validate');
	}
	var url=CONTEXT+"nst_order_complaint/updateStatus";
	jQuery.post(url, $('#updateForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#nstOrderComplaintdg").datagrid('reload');
			$('#showDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}
 
//重置
$('#btn-reset').click(function(){
	$('#nstOrderComplaintSearchForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#nstOrderComplaintSearchForm')[0].reset();
	$("#nstOrderComplaintdg").datagrid('load', {});
	disableExport = false;
});
 