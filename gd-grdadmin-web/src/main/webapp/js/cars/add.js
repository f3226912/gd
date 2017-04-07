
function save() {
	
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	if(checkData())
	{
	 var url=CONTEXT+"cars/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#carsdg").datagrid('reload');
				$('#addDialog').dialog('close');
			}else if (data == "not exist") {
				warningMessage("联系人不存在，请重新输入！");
				return;
			 }else if(data=="carNumberExist"){
				 warningMessage("车牌号已经存在！");
				 return;
			 } else{
				warningMessage("系统异常！");
				return;
			}
		});
	}
}
 
function checkData()
{  
	var re = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
	var carNumber=$("#number").val();
	if (!re.test(carNumber)) {
		warningMessage("输入的车牌号格式不正确!");
		return false;
	}
	
	var test1 =/^[0-9]+(.[0-9]{1,2})?$/;
	var maxLoad=$("#maxLoad").val();
	if (maxLoad.search(test1) == -1) {
		warningMessage("载重请输入整数或两位小数!");
		return false;
	}
	
	if (parseFloat(maxLoad) >60) {
		warningMessage("载重最大值请小于60吨!");
		return false;
	}
	var test2 =/^[0-9]+(.[0-9]{1,1})?$/;
	var carLength=$("#carLength").val();
	if (carLength.search(test2) == -1) {
		warningMessage("车长请输入整数或一位小数!");
		return false;
	}
	
  /*	var test3 =/^[\u0391-\uFFE5\w]+$/;
	var userName=$("#addForm #userName").val();
	if (!test3.test(userName)) {
		warningMessage("输入的联系人不能包含特殊字符!");
		return false;
	}*/
	
	var test4 =/^(1[0-9][0-9])\d{8}$/;
	var phone=$("#addForm #phone").val();
	if (!test4.test(phone)) {
		warningMessage("输入的手机号码格式不正确!");
		return false;
	}
	
	return true;
	
}

function displayMemberName() {
	var userMobile = $("#addForm #phone").val();
	$.ajax({
		type : "POST",
		url : CONTEXT + "cars/queryMemberByMobile?userMobile="
				+ userMobile,
		cache : false,
		dataType : "json",
		success : function(data) {
			if (userMobile != '') {
				if (data != null) {
					$("#memberName").html(data.realName);
					$("#memberName").attr("style", "color:green;");
					if (data.userType == '1' || data.userType == null) {
						$("#per").show();
						$("#ent").show();
					} else if (data.userType == '2') {
						$("#ent").show();
						$("#per").hide();
						$("#memberId").val(data.memberId);
						$("#memberName").html(data.companyContact);
						$("#companyName").val(data.companyName);
					}else{
					  warningMessage("该会员不存在，请输入正确的手机号码！");
					} 
				} else {
					$("#memberName").html("会员不存在！");
					$("#companyName").val("");
					$("#memberName").attr("style", "color:red;");
				}
			}
		}
	});
}


