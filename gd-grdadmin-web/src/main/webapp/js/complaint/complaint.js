
$(window).load(function(){	
	initcomplaintList();	
});

function initcomplaintList(){
	var params ;
	loadComplaintList(params);
	//分页加载
	$("#complaintdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function loadComplaintList(params){
	params = !params ? {}: params;
	//数据加载
	$('#complaintdg').datagrid({
		url:CONTEXT+'complaint/complaintListShow',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#friendslinkstb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#complaintdg").datagrid('clearSelections');
		},
		columns:[[
//			{field:'id',title:'',width:50,checkbox:true},
			{field:'content',title:'投诉建议内容',width:200,align:'center',formatter:function(value,row,index){
			if(value.length<=10){
				return "<a class='csshiden' 　 href='javascript:;' onclick=contentShowDialog('"+row.id+"');>"+value+"</a>&nbsp;";
			}else{
				var values =value.substring(0,10); 
				return "<a class='csshiden' 　 href='javascript:;' onclick=contentShowDialog('"+row.id+"');>"+values+"...</a>&nbsp;";
			}
			}},
			{field:'source',title:'信息来源',width:100,align:'center'},
			{field:'level',title:'用户类型',width:100,align:'center'},
			{field:'marketName',title:'商铺所属市场',width:100,align:'center'}, 
			{field:'member',title:'账号',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=showMember('"+row.memberId+"');>"+value+"</a>&nbsp;";
			}},
			{field:'mobile',title:'手机号',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center'}, 
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='csshiden' 　 href='javascript:;' onclick=contentShowDialog('"+row.id+"');>查看</a>&nbsp;";
			}}
		]],
		onDblClickRow: function(index,row){
			
		}
	}); 
}

//查看
function contentShowDialog(id){
	$('#contentDialog').dialog({'title':'查看','href':CONTEXT+'complaint/complaintContentShow/'+id,'width': 500,'height': 300}).dialog('open');
}


//查看会员信息
function showMember(id){
	$('#showDialog').dialog({'title':'会员信息','href':CONTEXT+'member/showMemberById/'+id,'width': 800,'height': 500}).dialog('open');
}

//回复窗口
function editObj(id){
	$('#eidtDialog').dialog({'title':'回复','href':CONTEXT+'complaint/replyComplaint/'+id,'width': 500,'height': 300}).dialog('open');
}



//回复
function reply(){
	var url=CONTEXT+"complaint/replyComplaintSave";
	jQuery.post(url, $('#editForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("回复成功！");
			//刷新父页面列表
			$("#complaintdg").datagrid('reload');
			$('#eidtDialog').dialog('close');
		}else {
			warningMessage("操作失败");
			return;
		}
	});
}




