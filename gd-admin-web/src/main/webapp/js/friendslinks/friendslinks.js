

	initFriendsLinksList();	


function initFriendsLinksList(){
	var params ;
	loadFriendsLinks(params);
	//分页加载
	$("#friendslinksdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function loadFriendsLinks(params){
	params = !params ? {}: params;
	//数据加载
	$('#friendslinksdg').datagrid({
		url:CONTEXT+'friendslinks/list',
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
			$("#friendslinksdg").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'',width:50,checkbox:true},
			{field:'linkCate',title:'链接栏目',width:100,align:'center',formatter:formatlinkCate},
			{field:'linkType',title:'链接类型',width:100,align:'center',formatter:formatlinkType},
			{field:'linkUrl',title:'链接URL',width:100,align:'center'},
			{field:'linkName',title:'链接标题',width:100,align:'center'}, 
			{field:'description',title:'说明',width:100,align:'center'}, 
			{field:'status',title:'状态',width:100,align:'center',formatter:formatStatus},
			{field:'updateUserId',title:'最近操作人',width:100,align:'center'}, 
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]],
		onDblClickRow: function(index,row){
			
		}
	}); 
}

function formatlinkCate(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "友情链接";
		}else if(str=="2"){
			return "合作媒体";
		}
	} 
}

function formatlinkType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "文字";
		}else if(str=="2"){
			return "图片";
		}else if(str=="3"){
			return "文字加图片";
		}
	} 
}

function formatStatus(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="0"){
			return "停用";
		}else if(str=="1"){
			return "启用";
		}else if(str=="2"){
			return "待审核";
		}else if(str=="3"){
			return "审核不通过";
		}else if(str=="4"){
			return "审核通过";
		}
	} 
}
function edit() {
		 if($("#linkWord_edit").val().trim()==""){
 				warningMessage("链接标题不能为空");
				return false;
			}
		if($("#linkUrl_edit").val().trim()==""){
			warningMessage("链接URL不能为空");
			return false;
		}else{
			 var strRegex =regexEnum.url;
		  	var reg=new RegExp(strRegex);        
    
			var url =$("#linkUrl_edit").val().trim();
			if(!reg.test(url)){
				warningMessage("请输入正确的URL链接");
				return false;
			}
		}
		if($("#linkType_edit_hiden").val()==2){
			var dataurl;
			var img =$("[data-original-url]").each(function(){
				dataurl=$(this).attr("data-original-url");
			});
			if(dataurl==undefined||dataurl==""){
				warningMessage("请上传图片！");
				return false;
			}
		}
		
	if(!$("#editForm").form('validate')){
		return $("#editForm").form('validate');
	}
	var url=CONTEXT+"friendslinks/edit";
		jQuery.post(url, $('#editForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#friendslinksdg").datagrid('reload');
				$('#editDialog').dialog('close');
			}else {
				warningMessage("操作失败");
				$('#editDialog').dialog('close');
				return;
			}
		});
}

function view() {
	
	//if(!$("#viewForm").form('validate')){
		//return $("#viewForm").form('validate');
	//}
	var url=CONTEXT+"friendslinks/view";
		jQuery.post(url, $('#viewForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#friendslinksdg").datagrid('reload');
				$('#viewDialog').dialog('close');
			}else {
				warningMessage(data);
				return;
			}
		});
}



