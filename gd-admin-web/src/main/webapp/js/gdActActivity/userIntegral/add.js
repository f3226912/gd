
$(function(){
	$("#selectMember").click(clickAddMember);
	defineValid();
});


/**
 * 点击添加用户
 */
function clickAddMember()
{
	$('<div></div>').dialog({
      id : 'addBuyerDialog',
      title : '添加活动买家',
      width : 800,
      height : 500,
      closed : false,
      cache : false,
      href : CONTEXT+'activityIntegral/addMemberPage',
      modal : true,
      onLoad : function() {
          //初始化表单数据
    	  //initbuyerProd();
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '确认',
          iconCls : 'icon-save',
          handler : function() {
        	  $("#addBuyerDialog").dialog('destroy');
          }
      }]
  });
}

function clickSave()
{
	debugger;
	
	var account = $("#account1").val();
	if(account == "")
	{
		warningMessage("请选择用户！");
		return;
	}
	
	//表单校验通过
	if(!$("#addForm").form('validate')){
		return;
	}
	
	//校验活动是否存在
	var activityCode = $("#code").val();
	var activityId = 0;
	var activityType = 0;
	$.ajax({
		url: CONTEXT + 'activityIntegral/getActivityId?activityCode='+activityCode,
		type: 'GET',
		async: false,
		dataType: 'json',
		success: function(data) {
			if (data.result == "success") {
				activityId = data.activityId;
				activityType = data.activityType;
			} else {
				warningMessage(data.msg);
			}
		}
	});
	
	if(activityId == 0 ){
		return;
	}
	
	//保存
	var url = CONTEXT + "activityIntegral/save";
	var para={
				'memberId':$("#memberId").val(),
				'mobile':$("#mobile").val(),
				'activityId':activityId,
				'activityType':activityType,
				'integralType': $("#integralType").val(),
				'integral':parseInt($("#integral").val())
			};

	//ajaxLoading();
	
	$.ajax({
		url: url,
		data: para,
		dataType: 'json',
		type: 'POST',
		success: function(data) {
			if (data.result == "success") { //活动基本信息
				//ajaxLoadEnd();
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#datagrid-table").datagrid('reload');
				$("#addDialog").dialog('close');
			} else {
				warningMessage(data.msg);
				//ajaxLoadEnd();
				return;
			}
		}
	});
}

/**
 * 用户自定义校验规则
 */
function defineValid()
{
	  $.extend($.fn.validatebox.defaults.rules, {
			range:{
				validator:function(value,param){
					var regValue = /^(([1-9]\d{0,6})|0)(\.\d{1,2})?$/;
					if(regValue.test(value)){
						return value >= param[0] && value <= param[1]
					}else{
						return false;
					}
				},
				message:'输入的数字在{0}到{1}之间'
			}
	  });
}