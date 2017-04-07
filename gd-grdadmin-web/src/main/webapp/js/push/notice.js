//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#noticedg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#noticedg').datagrid({
		url:CONTEXT+'push/noticeQuery',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#noticetb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#noticedg").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'',width:0,checkbox:true,hidden:true},
			{field:'title',title:'消息名称',width:100,align:'center' },
			{field:'client',title:'消息发布对象',width:100,align:'center',formatter:formatterClient},
			{field:'createTime',title:'消息发布时间',width:100,align:'center'},
			{field:'createUserName',title:'发布人',width:100,align:'center'},
			{field:'state',title:'发送状态',width:100,align:'center',formatter:formatterState },
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
}

function formatterClient(val,row){
	if (val != null) {
		var  str=val.toString();
		var splitStr =str.split(",");
		var returnStr = "";
		for(var i=0;i<splitStr.length;i++){
			if(splitStr[i]=="1"){
				returnStr+= ",农批商";
			}
			if(splitStr[i]=="2"){
				returnStr+= ",供应商";
			}
			if(splitStr[i]=="3"){
				returnStr+= ",农速通";
			}
			if(splitStr[i]=="4"){
				returnStr+= ",农商友";
			}
			if(splitStr[i]=="5"){
				returnStr+= ",谷登农批网";
			}
		}
		return returnStr.substring(1, returnStr.length);
	}else{
		return "";
	}
}
//状态(1:已发送，2:待发送)
function formatterState(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "已发送";
		}else if(str=="2"){
			return "待发送";
		}
	}else{
		return "未知";
	}
}

//发送操作
function editObj(id){
		jQuery.post(CONTEXT+"push/noticeSaveEdit",{"state":"1","id":id},function(data){
			if (data == "success"){
				slideMessage("发送成功！");
				$('#noticedg').datagrid('reload');
				$('#noticedg').datagrid("uncheckAll");
			}else{
				warningMessage(data);
				return;
			}
		});

}

//编辑
function updateObj(id){
	$('#editDialog').dialog({'title':'编辑DTO','href':CONTEXT+'push/noticeEditById/'+id,'width': 800,'height': 430}).dialog('open');
}

//详情
function detailObj(id){
	$('#detailDialog').dialog({'title':'详情DTO','href':CONTEXT+'push/noticeDetailById/'+id,'width': 800,'height': 430}).dialog('open');
}
