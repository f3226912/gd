
$(document).ready(function(){
	var params={"integral":$("#integral").val()};
	loadgiftData(params, CONTEXT+'conversion/giftlist');

});

$("#gift-search").click(function(){
	var params={"giftName":$("#giftName").val(),"integral":$("#integral").val()};
	loadgiftData(params, CONTEXT+'conversion/giftlist');
});

function loadgiftData(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#giftdg').datagrid({
		url: loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#gifttb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
		          
			{field:'giftId',title:'',width:80,hidden:true},
			{field:'giftName',title:'礼品名字',width:100,align:'center'},
			{field:'giftIntegral',title:'礼品积分数',width:100,align:'center'},
			{field:'activityId',title:'',width:100,hidden:true},
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=conversionGift('"+row.giftId+"','"+row.giftIntegral+"','"+row.giftName+"','"+row.activityId+"');>兑换</a>";
			}}
		]],
	}); 
	//分页加载
	$("#giftdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}
$("#gift-reset").click(function(){
	$("#giftName").val("");
});
function conversionGift(giftId,giftIntegral,giftName,activityId){
	$.messager.confirm("操作提示", "你确认需要兑换此"+giftName+"么?", function(data){
		if(data){
			var memberId=$("#memberId").val();
			var integral=$("#integral").val();
			jQuery.post(CONTEXT+"conversion/updateintegralinfo",{"memberId":memberId,"giftId":giftId,"giftIntegral":giftIntegral,"giftName":giftName,"integral":integral,"activityId":activityId},function(data){
				if (data == "success"){
					slideMessage("兑换成功！");
					$('#giftDialog').dialog("close");
					$("#conversiondg").datagrid('load',{});
				}else{
					warningMessage("兑换失败！");
					return;
				}
			});
		}
	});
	
}