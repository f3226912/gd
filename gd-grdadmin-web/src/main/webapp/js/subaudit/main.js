$(document).ready(function(){
	//数据加载
	$('#subauditdg').datagrid({
		url:CONTEXT+'subaudit/querybysearch',
		height: 'auto',
		nowrap:true,
		toolbar:'#pushrectb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		pageList:[10,20,50,100],
		onBeforeLoad:function(p){setParams(p);},
		columns:[[
			{field:'auditId',title:'',width:0,checkbox:true},
			{field:'orderNo',title:'订单号',width:120,align:'center' },
			{field:'orderAmount',title:'订单金额',width:90,align:'center'},
			{field:'discountAmount',title:'抵扣金额',width:90,align:'center'},
			{field:'payAmount',title:'付款金额',width:90,align:'center'},
			{field:'payTypeStr',title:'付款方式',width:90,align:'center'  },
			{field:'buyerAccount',title:'买家账号',width:90,align:'center' },
			{field:'buyerName',title:'买家姓名',width:90,align:'center' },
			{field:'orderTime',title:'订单时间',width:100,align:'center' },
			{field:'buyerShop',title:'卖家商铺',width:120,align:'center' },
			{field:'subStatusStr',title:'补贴状态',width:90,align:'center'},
			{field:'opt',title:'操作',width:60,align:'center',formatter:optformat}
		]]
	});

	// 点击按钮是触发查询事件
	$('#icon-search').click(function(){
		$('#subauditdg').datagrid('load',getParams());
	});
	// 点击"导出"按钮
	$('#exportData').click(function(){
		exportData();
	});
	// 批量审核
	$('#verifySelected').click(function(){
		verifyBatch();
	});
});
// 设置查询参数
function setParams(p){
	p.subStatus=$('#subauditSearchForm #subStatus').val();
	p.orderNo=$('#subauditSearchForm #orderNo').val();
	p.orderAmount=$('#subauditSearchForm #orderAmount').val();
	p.payType=$('#subauditSearchForm #payType').val();
	p.buyerName=$('#subauditSearchForm #buyerName').val();
	//p.suberStatus=$('#subauditSearchForm #suberStatus').val();
	p.buyerShop=$('#subauditSearchForm #buyerShop').val();
	p.orderLike=$('#subauditSearchForm #orderLike').val();
}
// 获取查询参数
function getParams(){
	var p = $('#subauditdg').datagrid('options').queryParams;
	setParams(p);
	return p;
}

// 导出数据
function exportData(){
	var p = getParams();
	var paramList = 'subStatus='+p.subStatus+'&orderNo='+ p.orderNo+'&orderAmount='+p.orderAmount
		+'&payType='+p.payType+'&buyerName='+p.buyerName+'&buyerShop='+p.buyerShop+'&orderLike='+p.orderLike;
	$.ajax({
		url: CONTEXT+'subaudit/checkExportParams',
		data : p,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'subaudit/exportData',paramList,'post' );
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
	/*window.location.href=CONTEXT+'subaudit/exportData?subStatus='+p.subStatus+'&orderNo='+ p.orderNo+'&orderAmount='+p.orderAmount+'&payType='+p.payType+'&buyerName='+p.buyerName+'&buyerShop='+p.buyerShop+'&orderLike='+p.orderLike;*/
}

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
function fmtOrderTime(val, row) {
	if (val != null) {
	   var  str=val.toString();
	   str =  str.replace(/-/g,"/");
	   var date = new Date(str);
	   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
}

// 保存审核结果(flag 0:不通过；1：通过)
var isSubmited = false;
function verify(flag) {
	if(isSubmited){return;}

	var subComment=$('#subComment_').val().replace(/(^\s*)(\s*$)/g,'');
	var auditId=$('#auditId_').val();
	var orderNo=$('#orderNo_').val();


	// audit check
	var cr = auditCheck(flag);
	if(null!=cr){warningMessage(cr); return;}

	//如果不通过,必须写原因
	if(0==flag && ''==subComment){warningMessage("请填写审核不通过的原因！");return;}

	if(!$('#verifyForm').form('validate')){return;}

	var cmsg='';
	if(1==flag){cmsg='你确认要进行 "通过审核" 操作？';}else if(0==flag){cmsg='你确认要进行 "审核不通过" 操作？';}
	// 参数设置
	isSubmited = true;
	$.messager.confirm('操作确认',cmsg,function(r){
	    if (r){
	    	$.ajax({
    		   type: 'POST',
    		   url: CONTEXT+'subaudit/verify/save',
    		   data: $('#verifyForm').serialize()+'&flag='+flag,
    		   success: function(msg){
    			  if('SUCCESS'==msg){
    				  $.messager.alert('处理结果','操作成功！','info');
    				  $('#subauditdg').datagrid('reload');
    				  isSubmited = false;
    			  }else{
    				  $.messager.alert('处理结果',msg,'error');
    			  }
    			  $('#showDialog').dialog('close');
    		   }
	    	});
	    }else{
	    	isSubmited = false;
	    }
	});
}

function auditCheck(flag){
	var uiStatus=$('#subStatus_').val();
	if(uiStatus=='3'){
		return '不能对"已补贴"订单进行再次审批！';
	}else if(uiStatus=='4' && 0==flag){
		return '该补贴状态已经是"不予补贴"状态!';
	}
	return null;
}

function verifyBatch(){
	if(isSubmited){return;}
	var rows = $('#subauditdg').datagrid('getSelections');
	if(rows.length==0){
		$.messager.alert('提示', '请选择待补贴订单！');
	}else{
		$.messager.confirm('操作确认','确认要进行批量操作吗？',function(r){
			if (r){
				var keys=new Array();
				$.each(rows,function(i,f){
					keys.push(f.auditId);
				});
				isSubmited = true;
				$.ajax({
					type:'POST', url:CONTEXT+'subaudit/verifyBatch/save',
					data:'keys='+keys.join(),
					success:function(msg){
						if('SUCCESS'==msg){$.messager.alert('处理结果','操作成功！','info');
						}else{$.messager.alert('处理结果',msg,'warning');}

						$('#subauditdg').datagrid('reload');
						isSubmited = false;
					}
				});
			}else{
				isSubmited = false;
			}
		});
	}
}

 //查看
function showDetail(id){
	$('#showDialog').dialog({'title':'订单详细','href':CONTEXT+'subaudit/'+id+'/verify','width': 900,'height': 500}).dialog('open');
}
function resumeIsSubmited(){
	isSubmited=false;
}
//重置
$('#btn-reset').click(function(){
	$('#subauditSearchForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#subauditSearchForm')[0].reset();
	$("#subauditdg").datagrid('load',{});
	//重启导出功能
	disableExport = false ;
});