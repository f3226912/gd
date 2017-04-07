$(function(){
	$('#btn-save').click(function(){
			var $contacts = $(".contact_c");
			var flag = true;
//			var obj = $("#condiv").html();
//			 if($("#whitechk").attr("checked")=="checked"&&$.trim(obj).length==0){
//				 if(null==$("#whiteList").val()||$("#whiteList").val()==""){
//					 $.messager.alert('提示', '请添加白名单人员，多个请用逗号隔开');
//					 return;
//					 flag = false;
//				 }
//			 }
			
			$contacts.each(function(index,ele){
				var valuem = $(this).val();
				if(valuem==null || valuem=="")return ;
				var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
				if(!reg.test(valuem)){
					$(this).val("");
					$.messager.alert('提示', '邮箱格式错误！');
					flag = false;
					return;
				}
			})
			
			if(!flag){
				return;
			}
			
		
		if ($("#subForm").form('validate')) {
			$.messager.confirm('提示', '您确定要保存规则吗?', function(r){
				if (r){
					$('#subForm').ajaxSubmit({
		                type:"post",  //提交方式  
		                dataType:"json", //数据类型  
		                url:CONTEXT + "sublimitrule/saveOrUpdate", //请求url  
		                success:function(data){ //提交成功的回调函数  
		                    if (data.flag == '1') {
		                    	$.messager.alert('提示', '操作成功');
		        				//刷新父页面列表
		        				parent.$("#limitruleList").datagrid('load', {});
		        				parent.$('#addLimitRuleDialog').window('close');
		        				parent.$('#detailDialog').window('close');
		        				parent.$('#editDialog').window('close');
		                    	//location.reload();
		                    }else if(data.flag == '3'){
		                    	if(data.data!=null || typeof(data.data)!="undefined"){
		                    		$.messager.alert('提示',data.data+"不是系统用户或者不属于该市场或者不是商家，保存失败");
		                    	}
		                    }
		                    else {
		                    	$.messager.alert('提示', '操作失败');
		                    }
		                }  
		            });  
				}
			});
		}
	});
	
	$('#btn-reset').click(function(){
		$('#subForm')[0].reset();
		$('input[pid^="cbx_"]').each(function() {
			handlerMod(this);
		});
	});
	
	$('input[pid^="cbx_"]').change(function(){
		handlerMod(this);
	});
	
	$('input[pid^="cbx_"]').each(function() {
		handlerMod(this);
	});
	
	$('#marketId').change(function(){
		window.location.href = CONTEXT + 'sublimitrule/init?marketId=' + $(this).val();
	});
	
	function handlerMod(cbx) {
		if ($(cbx)[0].checked) {
			$('input[pid=\"mod_' + $(cbx).attr('pid').split('_')[1] + '\"]').validatebox({required:true});
			$('input[pid=\"mod_' + $(cbx).attr('pid').split('_')[1] + '\"]').validatebox("enableValidation");
			$('input[pid=\"mod_' + $(cbx).attr('pid').split('_')[1] + '\"]').removeAttr("disabled");
		} else {
			$('input[pid=\"mod_' + $(cbx).attr('pid').split('_')[1] + '\"]').validatebox({required:false});
			$('input[pid=\"mod_' + $(cbx).attr('pid').split('_')[1] + '\"]').validatebox("disableValidation");
			$('input[pid=\"mod_' + $(cbx).attr('pid').split('_')[1] + '\"]').attr("disabled","disabled");
		}
	}

	$("body").delegate(".row-del","click",function(){
		$(this).parents("tr").remove();
	});
	
	$("body").delegate(".contact_c","blur",function(){
		var valuem = $(this).val();
		if(valuem==null || valuem=="")return ;
		var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
		if(!reg.test(valuem)){
			$(this).val("");
			$.messager.alert('提示', '邮箱格式错误！');	
			return;
		}
		
	});
	
});

function addContact(){
	var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	var $contact_c = $(".contact_c");
	var flag = true;
	$contact_c.each(function(index,ele){
		if(!reg.test($contact_c.eq(index).val())){
			$contact_c.eq(index).val("");
			flag = false;
			return;
		}
	});
	if(!flag){
		$.messager.alert('提示', '邮箱格式错误！');	
		return;
	}
	
	
	var html='<tr><td class="left" style="width: 200px;">紧急联系人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
	   '<input  placeholder="填写邮箱"   type="text" id=""  name="contact"   value=""  maxlength="20" class="contact_c"  style="width:150px">'+
	   '--<span><a href="javascript:void(0)" class="row-del">删除</a></span></td><td class="left" style="width: 200px;color:red;"></td></tr>';
	$("#addContact").before(html);
}

function delW(ruleId, memberId) {
	jQuery.messager.confirm('提示', '是否要删除该白名单？', function(r) {
		if (r) {
			var url = CONTEXT + "sublimitrule/delWhiteList";
			jQuery.post(url, "limitruleId=" + ruleId + "&memberId=" + memberId,
					function(data) {
						if (data.flag * 1 == 1) {
							slideMessage("操作成功！");
							$("#wspan" + memberId).remove();
						} else {
							warningMessage("系统异常！");
							return;
						}
					})
		}
	});	
}

function forbidden(ruleId,marketId) {
	jQuery.messager.confirm('提示', '是否要禁用该条规则', function(r) {
		if (r) {
			var url = CONTEXT + "sublimitrule/updateStatus";
			jQuery.post(url, "ruleId=" + ruleId + "&marketId=" + marketId,
					function(data) {
						if (data.flag * 1 == 1) {
	                    	$.messager.alert('提示', '操作成功');
	        				//刷新父页面列表
	        				parent.$("#limitruleList").datagrid('load', {});
	        				parent.$('#addLimitRuleDialog').window('close');
	        				parent.$('#detailDialog').window('close');
	        				parent.$('#editDialog').window('close');
						} else {
							warningMessage("系统异常！");
							return;
						}
					})
		}
	});	
}


javascript:window.history.forward(1); 

