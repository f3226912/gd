//按钮已提款操作
function  confirmPay(){
	if($("#accountflowId").val()==''){
		warningMessage("流水号不能为空");
		return;
	}
	var pattern=/^([0-9]{12})$/;
	if(!pattern.test($("#accountflowId").val())){
		warningMessage("流水号只能输入12位数字");
		return;
	}
	var flowdata={cashRequestId:$("#cashRequestId").val(),accountflowId:$("#accountflowId").val()};
	$.messager.confirm("操作提示", "确认要执行已提款操作么?", function(data){
		if(data){
			$.ajax({
				type:'POST',
				url:CONTEXT+'cash/alreadyPayOperation',
				data:flowdata,
				dataType: 'json',
				success:function(data){
					if(data.isSuccess == "success"){
						 slideMessage("已提款操作成功！");
						$('#payDialog').dialog("close");
						//查询加载，查询出为待提现的信息
						$("#account").val("");
						$("#realName").val("");
						$("#bankCardNo").val("");
						$("#applyBeginTime").val("");
						$("#applyEndTime").val("");
						$("#payBeginTime").val("");
						$("#isABC").val("");
						$("#payEndTime").val("");
						$("#cashdg").datagrid('load',{status:0}); 
					}else if(data.isSame == "same"){
						warningMessage("此流水号已使用过，请重新输入");
						return;
					}else{
						warningMessage("已提款操作失败！");
						return;
					}
				}
			});
		}
		
	});	
}

//返回操作
function comeBack(){
	$('#payDialog').dialog("close");
	//查询加载，查询出为待提现的信息
	$("#account").val("");
	$("#realName").val("");
	$("#bankCardNo").val("");
	$("#applyBeginTime").val("");
	$("#applyEndTime").val("");
	$("#payBeginTime").val("");
	$("#isABC").val("");
	$("#payEndTime").val("");
	$("#cashdg").datagrid('load',{status:0});
}
