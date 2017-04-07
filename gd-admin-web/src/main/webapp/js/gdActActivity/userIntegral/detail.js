
$(function(){
	
	loadData(id);
});

function loadData(id)
{
	$.ajax({
		url: CONTEXT + "activityIntegral/getById?id="+id,
		dataType: 'json',
		type: 'POST',
		success: function(data) {
			if (data.result == "success") { //活动基本信息
				initForm(data.data);
			} else {
				warningMessage(data.msg);
				return;
			}
		}
	});
}

function initForm(obj)
{
	debugger;
	if(obj){
		for(var k in obj) {
			var value = obj[k];
			if(k == "integralType")
			{
				if(value == "1")
				{
					value = "新增积分";
				}else if(value == 2)
				{
					value ="扣减积分";
				}
			}
			$('#detailForm #'+k).text(value);
		}
	}
}
