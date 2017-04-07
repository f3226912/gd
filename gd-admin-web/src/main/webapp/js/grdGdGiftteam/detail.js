$(function(){
	$("#esDialogMarketId").change(function(){
		queryGiftstoreDetail();
	})
})

//获取关联仓库
function queryGiftstoreDetail(){
	$.ajax({
		url: CONTEXT+'grdGdGiftteam/queryGiftstore',
		data: {"marketId":$("#esDialogMarketId").val()},
		type: 'POST',
		dataType:"json",
		success: function(data) {
			$("#esDialogGiftstoreId").empty();
			$("#esDialogGiftstoreId").append("<option value=''>请选择</option>");
			if(data){
				$.each(data.rows,function(index,row){
					$("#esDialogGiftstoreId").append("<option value='"+row.id+"'>"+row.name+"</option>");
				})
			}
		},
		error: function(data){
			warningMessage(data);
		}
	});
}