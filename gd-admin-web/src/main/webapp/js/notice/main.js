$(document).ready(function(){
	load(null,CONTEXT+'notice/getNstNoticeList');
});
$('#icon-search').click(function(){ 
	var params={"startBeginTime":$("#startBeginTime").val(),
			"startEndTime":$("#startEndTime").val(),
			"endBeginTime":$("#endBeginTime").val(),
			"endEndTime":$("#endEndTime").val(),};
	load(params,CONTEXT+'notice/getNstNoticeList');
});

function load(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#nstNoticedg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#nstNoticetb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'', hidden:true},
					{field:'notice',title:'公告',width:200,align:'center'},
					{field:'startTime',title:'开始时间',width:100,align:'center'},
					{field:'endTime',title:'结束时间',width:100,align:'center'}
				]]
	}); 
	
}
$("#icon-refresh").click(function(){
	$("#nstNoticeSearchForm")[0].reset();
	$("#nstNoticedg").datagrid('load',{});
});
$("#btn-reset").click(function(){
	$("#nstNoticeSearchForm")[0].reset();
});

$("#btn-add").click(function(){
	$('#nstNoticeDialog').dialog({'title':'添加公告', 'width':600, 'height':400, 'href':CONTEXT+'notice/addNstNotice'}).dialog('open');
});

$("#btn-save").click(function(){
	var notice=$('#notice').val();
	var start=$('#startTime').val();
	var end=$('#endTime').val();
	
	if(notice=="" || start=="" || end==""){
		alert("输入内容有空,请重新输入");
		return;
	}
	
	var url = CONTEXT + "notice/save";
	jQuery.post(url, $('#addForm').serialize(), function(data) {
		if (data == "success") {
			slideMessage("保存成功！");
			$("#nstNoticedg").datagrid('reload');
			$('#nstNoticeDialog').dialog('close');
		} else {
			warningMessage("保存失败！");
			return;
		}
	});
});




