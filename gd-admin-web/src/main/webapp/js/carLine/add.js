
function save() {
	
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	
    if(checkData()){ 
	var url=CONTEXT+"carLine/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {

			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#carLinedg").datagrid('reload');
				$('#addDialog').dialog('close');
			}
			else if(data == "prompt1"){
				warningMessage("发车时间不能早于当前时间,上午时间段是6:00~12:00");
				return;
			}
			else if(data == "prompt2"){
				warningMessage("发车时间不能早于当前时间,中午时间段是12:00~14:00");
				return;
			}
			else if(data == "prompt3"){
				warningMessage("发车时间不能早于当前时间,下午时间段是14:00~18:00");
				return;
			}
			else {
				warningMessage("系统异常！");
				return;
			}
		
		});
	}
}



function checkData()
{  
	var carNumber =$("#queryCarList").val();
	if(carNumber =='选择车牌号码'){
	    warningMessage("请选择车牌号码!");
		return false;
	 }
	
	var test1 =/^\+?[1-9][0-9]*$/;
	var onLineHours=$("#onLineHours").val();
	if(onLineHours !=''){
	 if (onLineHours.search(test1) == -1) {
		warningMessage("在途时间格式请输入正整数!");
		return false;
	 }
	 if (parseInt(onLineHours) > 30) {
			warningMessage("在途时间不能大于30天!");
			return false;
	  }
	}
	
	var test2 =/^[0-9]+(.[0-9]{1,2})?$/;
	var price=$("#price").val();
	if(price !=''){
	 if (price.search(test2) == -1) {
		warningMessage("价格格式不正确，请输入整数或两位小数!");
		return false;
	 }
	}
	
	return true;

}


function typeChange()
{  
	var sendGoodsType = $("#sendGoodsType").val();
	if(sendGoodsType=='0')
	{
		$('#price').removeAttr("disabled");  
	    $("#unitType").removeAttr("disabled");  
		$("#div1").show();
		$("#div2").hide();
	}
	else if(sendGoodsType=='1')
	{
		$("#price").removeAttr("disabled");
		$('#unitType').attr('disabled', 'true');
		$("#div1").hide();
		$("#div2").show();
	}
	else 
	{
		$("#div1").hide();
		$("#div2").show();
		$('#price').attr('disabled', 'true');
		$('#unitType').attr('disabled', 'true');
	}
}
