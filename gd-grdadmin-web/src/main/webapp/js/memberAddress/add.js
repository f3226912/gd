function save() {
	
	if (!$("#addForm").form('validate')) {
		return $("#addForm").form('validate');
	}
	if($("#sendDate").val() != '' && $("#endDate").val() != ''){
		var sendDateType = $("#sendDateType").val();
		var endDateType = $("#endDateType").val();
		if($("#sendDate").val() == $("#endDate").val() && sendDateType !='4' && endDateType!='4'){
		if(sendDateType > endDateType){
			warningMessage("到达时间不能早于装车时间");
			return;
		}
	   }
	   if($("#sendDate").val() > $("#endDate").val())
	   {
		   warningMessage("到达时间不能早于装车时间");
		   return;
	   }
	}
	
	if (checkData()) {
		var url = CONTEXT + "consignment/save";
		jQuery.post(url, $('#addForm').serialize(), function(data) {
			if (data == "success") {
				slideMessage("操作成功！");
				// 刷新父页面列表
				$("#consignmentdg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else if (data == "exception") {
				warningMessage("该会员不存在，请输入正确的手机号码！");
				return;
			} else {
				warningMessage("系统异常！");
				return;
			}
		});
	}
}

function displayMemberName() {
	var userMobile = $("#memberMobile").val();
	$.ajax({
		type : "POST",
		url : CONTEXT + "consignment/queryMemberByMobile?userMobile="
				+ userMobile,
		cache : false,
		dataType : "json",
		success : function(data) {
			if (userMobile != '') {
				if (data != null) {
					$("#userType").val(data.userType);
					$("#memberName").html(data.realName);
					$("#memberName").attr("style", "color:green;");
					if (data.userType == '1' || data.userType == null) {
						//$(".tr_memberId").show();
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
					//$(".tr_memberId").hide();
					$("#memberName").html("会员不存在！");
					$("#memberName").attr("style", "color:red;");
//					$("input[type='radio'][name='userType']").removeAttr("disabled");
//					$("input[type='radio'][name='userType']").removeAttr("checked");
				}
			}
		}
	});
}

function initLink(companyContact, companyMobile) {
	//alert(companyContact + "----" + companyMobile);
	$("#companyLinkMan").html(companyContact);
	$("#companyLinkMobile").html(companyMobile);
}

function initCompany() {
	var createUserId = $("#createUserId").val();
	$.ajax({
		type : "GET",
		url : CONTEXT + "consignment/initCompany",
		dataType : "json",
		data : {

		},
		success : function(data) {
			if (data.length != 0) {
				$('#memberId').empty();
				for (var n = 0; n < data.length; n++) {
					if (createUserId == data[n].memberId) {
						$('#memberId').append("<option selected='true' value='"+ data[n].memberId+ "' onclick='initLink("+ data[n].companyContact + ","+ data[n].companyMobile + ")'>"+ data[n].companyName + "</option>");
					} else {
						$('#memberId').append("<option value='" + data[n].memberId+ "' onclick='initLink("+ data[n].companyContact + ","+ data[n].companyMobile + ")'>"+ data[n].companyName + "</option>");
					}
				}
			}
		}
	});
}


function checkData() {
	var test1 = /^\d+$/;
	// var test1 =/^\+?[1-9][0-9]*$/;
	var totalWeight = $("#totalWeight").val();
	if (totalWeight != '') {
		if (totalWeight.search(test1) == -1) {
			warningMessage("总重量请输入正整数!");
			return false;
		}
	}

	var totalSize = $("#totalSize").val();
	if (totalSize != '') {
		if (totalSize.search(test1) == -1) {
			warningMessage("总体积请输入正整数!");
			return false;
		}
	}
	
	 if (totalWeight =='' && totalSize =='' ) {
			warningMessage("请输入总重量或总体积!");
			return false;
	 }
	 
	 /*var testCarLength =/^[0-9]+(.[0-9]{1,1})?$/;
		var carLength=$("#carLength").val();
		if(carLength !=''){
		if (carLength.search(testCarLength) == -1) {
			warningMessage("车长请输入整数或一位小数!");
			return false;
		}
	  }*/
	
	var test2 = /^[0-9]+(.[0-9]{1,2})?$/;
	var price = $("#price").val();
	if (price != '') {
		if (price.search(test2) == -1) {
			warningMessage("价格请输入整数或两位小数!");
			return false;
		}
	}
	/*
	 * var sendDate=$("#sendDate").val(); if (sendDate =='') {
	 * warningMessage("请选择发货时间！"); return false; } var
	 * endDate=$("#endDate").val(); if (endDate =='') {
	 * warningMessage("请选择截止时间！"); return false; }
	 */
	/* var sendGoodsType=$("#sendGoodsType").val();
     if (sendGoodsType =='0' || sendGoodsType =='1' ) {
    	   if(price == ''){
			warningMessage("请输入价格!");
			return false;
    	   }
	 }*/
     
    /* var companyName=$("#companyName").val();
     if(companyName == ''){
		 warningMessage("请选择公司!");
		 return false;
	 }*/

	return true;

}

function typeChange() {
	var sendGoodsType = $("#sendGoodsType").val();
	if (sendGoodsType == '0') {
		$("#sendGoodsType").attr("value", "0");
		$('#price').removeAttr("disabled");
		$("#priceUnitType").removeAttr("disabled");
		$("#div1").show();
		$("#div2").hide();
	} else if (sendGoodsType == '1') {
		$("#sendGoodsType").attr("value", "1");
		$("#price").removeAttr("disabled");
		$('#priceUnitType').attr('disabled', 'true');
		$("#div1").hide();
		$("#div2").show();
	} else {
		$("#div1").hide();
		$("#div2").show();
		$('#price').attr('disabled', 'true');
		$('#priceUnitType').attr('disabled', 'true');
	}
}

$(function(){
	
	/***选择发货企业***/
	$('#queryEntList').click(function(){
		$('#entDialog').dialog({'title':'选择发货企业', 'width':700, 'height':400, 'href':CONTEXT+'consignment/entList'}).dialog('open');
	});
	var userMobile = $("#memberMobile").val();
	//直接选择的企业，没有输入手机号码
	if(userMobile == '')
	{
	 $("#userType").val(2);
	}
	
});