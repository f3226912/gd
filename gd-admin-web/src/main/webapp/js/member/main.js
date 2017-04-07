
$(document).ready(function(){
	//数据加载
	$('#memberdg').datagrid({
		url:CONTEXT+'member/query',
		//width: 1000,
		height: 'auto',
		nowrap:true,
		toolbar:'#membertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#memberdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'memberId',title:'',width:0,checkbox:true},
					{field:'level',title:'用户类型',width:100,align:'center',formatter:formatterLevel },
					{field:'regetype',title:'注册来源',width:100,align:'center',formatter:formatterRegeType },
					{field:'account',title:'用户账号',width:100,align:'center'},
					{field:'mobile',title:'手机号',width:100,align:'center'},
					{field:'memberGrade',title:'会员等级',width:100,align:'center',formatter:formatterMemberGrade },
//					{field:'nickName',title:'昵称',width:100,align:'center'},
					{field:'createTime',title:'注册时间',width:100,align:'center',formatter:formatterdate   },
					{field:'marketName',title:'商铺所属市场',width:100,align:'center'   },
//					{field:'mobile',title:'手机',width:100,align:'center'},
//					{field:'isAuth',title:'认证状态',width:100,align:'center',formatter:formatterAuth },
					{field:'status',title:'账号状态',width:100,align:'center',formatter:formatterStatus },
//					{field:'subShow',title:'补贴展示状态',width:100,align:'center',formatter:formatterSub },
					{field:'createUserName',title:'录入人',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	});
	//分页加载
	$("#memberdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

});


// 查询按钮,根据name查询
$('#icon-search').click(function(){
 var queryParams = $('#memberdg').datagrid('options').queryParams;
	queryParams.account = $('#memberSearchForm #account').val();
	queryParams.mobile = $('#memberSearchForm #mobile').val();
	queryParams.level = $('#memberSearchForm #level').val();
	queryParams.memberGrade = $('#memberSearchForm #memberGrade').val();
	queryParams.startDate =  $('#memberSearchForm #startDate').val();
	queryParams.endDate =  $('#memberSearchForm #endDate').val();
	queryParams.marketId = $("#memberSearchForm #marketId").val();
	queryParams.regetype = $("#memberSearchForm #regetype").val();
	queryParams.status = $("#memberSearchForm #status").val();

	var queryUrl=CONTEXT+'member/querybysearch';
	//if(queryParams.name==''){
	//	queryUrl=CONTEXT+'member/querybybirthday';
 //}
	$('#memberdg').datagrid({
		url:queryUrl,
		height: 'auto',
		nowrap:true,
		toolbar:'#membertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'memberId',title:'',width:0,checkbox:true},
					{field:'level',title:'用户类型',width:100,align:'center',formatter:formatterLevel },
					{field:'regetype',title:'注册来源',width:100,align:'center',formatter:formatterRegeType },
					{field:'account',title:'用户账号',width:100,align:'center'},
					{field:'mobile',title:'手机号',width:100,align:'center'},
					{field:'memberGrade',title:'会员等级',width:100,align:'center',formatter:formatterMemberGrade },
//					{field:'nickName',title:'昵称',width:100,align:'center'},
					{field:'createTime',title:'注册时间',width:100,align:'center',formatter:formatterdate   },
					{field:'marketName',title:'商铺所属市场',width:100,align:'center'   },
//					{field:'mobile',title:'手机',width:100,align:'center'},
//					{field:'isAuth',title:'认证状态',width:100,align:'center',formatter:formatterAuth },
					{field:'status',title:'账号状态',width:100,align:'center',formatter:formatterStatus },
//					{field:'subShow',title:'补贴展示状态',width:100,align:'center',formatter:formatterSub },
					{field:'createUserName',title:'录入人',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	});
	//分页加载
	$("#memberdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

});

//注册来源 '0管理后台,1谷登农批网,2农速通(旧),3农商友,4农商友-农批商,5农批友,6供应商,7POS刷卡 ,8微信授权,9农速通-货主,10农速通-司机,11农速通-物流公司'
function formatterRegeType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="0"){
			return "管理后台";
		}else if(str=="1"){
			return "谷登农批网";
		}else if(str=="2"){
			return "农速通";
		}else if(str=="3"){
			return "农商友";
		} else if(str=="4"){
			return "农商友-农批商";
		} else if(str=="5"){
			return "农批友";
		} else if(str=="6"){
			return "供应商";
		} else if(str=="7"){
			return "POS刷卡";
		}else if(str=="8"){
			return "微信注册用户";
		}else if(str=="9"){
			return "农速通-货主";
		}else if(str=="10"){
			return "农速通-司机";
		}else if(str=="11"){
			return "农速通-物流公司";
		}
	}else{
		return "未知来源";
	}
}
//用户类别（1谷登农批，2农速通，3农商友，4产地供应商 ，其余待补）
function formatterLevel(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "谷登农批";
		}else if(str=="2"){
			return "农速通";
		}else if(str=="3"){
			return "农商友";
		} else if(str=="4"){
			return "产地供应商";
		} else if(str=="5"){
			return "农批友";
		}
	}
}
function formatterMemberGrade(val, row) {
	if (val == 0) {
	   return "普通会员";
	}else if(val == 1){
	   return "金牌供应商";
	}
}

function formatterStatus(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "启用";
		}else if(str=="0"){
			return "禁用";
		}
	}else{
		return "";
	}
}

function formatterdate(val, row) {
		if (val != null) {
		   var  str=val.toString();
		   str =  str.replace(/-/g,"/");
		   var date = new Date(str);
		   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
		}
	}

var isSubmited = false;

function save() {
	if($('#changeDescriptionTr').length > 0 && $('#changeDescriptionTr').css('display') != 'none'){
		if($('#changeDescription').val() == '') {
			warningMessage("变更说明不能为空");
			return false;
		}
		if($('#changeDescription').val().length > 200) {
			warningMessage("变更说明最多输入200个字符");
			return false;
		}
	}
	if($('#shopRecommend').attr('checked')=="checked"){
		$("input[name='shopRecommend']").val("1");
	} else {
		$("input[name='shopRecommend']").val("0");
	}
	
	var isAuth=$("#isStatus").val();
	
	if(!$("#addForm").form('validate')){
		warningMessage("该用户有数据尚未完善，请先完善后再进行提交");
		return;
	}
	
	if(isAuth==-1){
		$.messager.confirm("操作提示", "选择(未提交认证)你的所有认证信息都会删除", function(data){
			if(data){
				sub();
			}
		});
	}else{
		sub();
	}
}
function nextStep(){
	if(!$("#addForm").form('validate')){
		return;
	}
	if($("#addForm #level").val() == 4){
		var content = "<select name='memberGrade' onchange='changeMemberGrade(this.value);'><option value='0' selected>普通会员 </option><option value='1'>金牌供应商 </option></select>";
		$("#memberGradeTd").html(content);
	} else {
		$("#memberGradeTd").html("<span style='font-size: 12px'>普通会员</span>");
	}
	$("#divMore").show();//新增时，保存成功后，跳转到下一步（会员资质）
	$("#tt").tabs("select", 1);//新增时，保存成功后，跳转到下一步（会员资质）
}

function sub(){
	if(isSubmited){
		return;
	}
	var url=CONTEXT+"member/save";
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		isSubmited = false;
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#memberdg").datagrid('reload');
			//$('#addDialog').dialog('close');
			//$('#editDialog').dialog('close');
			$("#userEditDialog").dialog('destroy');
		}else if(data == "mobile"){
			warningMessage("手机号码不能为空");
			return;
		}else if(data == "mobileE"){
			warningMessage("请输入正确的手机号");
			return;
		}else if(data == "existM"){
			warningMessage("该手机号已经被注册，请更换手机号");
			return;
		}else if(data == "exist"){
			warningMessage("账号或者手机号已被注册,请修改");
			return;
		}else if(data == "companyName"){
			warningMessage("企业名称(物流)不能为空");
			return;
		}else if(data == "toLong"){
			warningMessage("姓名不能超过14字符");
			return;
		}else if(data == "cCityName"){
			warningMessage("所输入常用城市不存在");
			return;
		}else {
			warningMessage(data);
			return;
		}
	});
	isSubmited = true;
}



function saveMore(){
	var url=CONTEXT+"member/saveMore";
	jQuery.post(url, $('#addFormMore').serialize(), function (data) {
		isSubmited = false;
		if (data == "success") {
			slideMessage("操作成功！");
			$("#memberdg").datagrid('reload');
			$('#editDialog').dialog('close');
            $("#editDialog").dialog('destroy');

		}else if(data == "companyName"){
			warningMessage("企业名称(物流)不能为空");
			return;
		}else  if(data == "cCityName"){
			warningMessage("所输入常用城市不存在");
			return;
		}else {
			warningMessage(data);
			return;
		}
	});
	isSubmited = true;
}


//启用
$("#icon-ok").click(function(){
	var row = $('#memberdg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }else if($(row).length > 1 ){
    	warningMessage("请不要操作的多条数据！");
        return ;
    }
    var deleteStr = getSelections("memberId");
	jQuery.post(CONTEXT+"member/updatestatus/"+deleteStr+"-"+1 ,{"id":deleteStr},function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#memberdg').datagrid('reload');
			$('#memberdg').datagrid("uncheckAll");
		}else{
			warningMessage(data);
			return;
		}
	});

});

//禁用
$("#icon-no").click(function(){

	var row = $('#memberdg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }else if($(row).length > 1 ){
    	warningMessage("请不要操作的多条数据！");
        return ;
    }
    var deleteStr = getSelections("memberId");
	jQuery.post(CONTEXT+"member/updatestatus/"+deleteStr+"-"+0 ,{"id":deleteStr},function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#memberdg').datagrid('reload');
			$('#memberdg').datagrid("uncheckAll");

		}else{
			warningMessage(data);
			return;
		}
	});
});


//显示补贴
$("#icon-sub-ok").click(function(){
	var row = $('#memberdg').datagrid("getSelections");
	if($(row).length < 1 ) {
		warningMessage("请选择要操作的数据！");
	    return ;
	}else if($(row).length > 1 ){
		warningMessage("请不要操作的多条数据！");
	    return ;
	}
	var deleteStr = getSelections("memberId");
	jQuery.post(CONTEXT+"member/updateSubShow/"+deleteStr+"-"+1 ,{"id":deleteStr},function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#memberdg').datagrid('reload');
			$('#memberdg').datagrid("uncheckAll");

		}else{
			warningMessage(data);
			return;
		}
	});
});

//隐藏补贴
$("#icon-sub-no").click(function(){
	var row = $('#memberdg').datagrid("getSelections");
	if($(row).length < 1 ) {
		warningMessage("请选择要操作的数据！");
		return ;
	}else if($(row).length > 1 ){
		warningMessage("请不要操作的多条数据！");
		return ;
	}
	var deleteStr = getSelections("memberId");
	jQuery.post(CONTEXT+"member/updateSubShow/"+deleteStr+"-"+0 ,{"id":deleteStr},function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#memberdg').datagrid('reload');
			$('#memberdg').datagrid("uncheckAll");

		}else{
			warningMessage(data);
			return;
		}
	});
});

	//删除操作
	$("#icon-remove").click(function(){
		var row = $('#memberdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }

		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		var deleteStr = getSelections("memberId");
	    		jQuery.post(CONTEXT+"member/deletebyid",{"id":deleteStr},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#memberdg').datagrid('reload');
						$('#memberdg').datagrid("uncheckAll");

					}else{
						warningMessage(data);
						return;
					}
	    		});
			}else{
				return;
			}
		});
	});



	//编辑
	$("#icon-edit").click(function(){


		var row = $('#memberdg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要操作的数据！");
	        return ;
	    }


	    var deleteStr = getSelections("memberId");
		if($(row).length  >1){
	    	warningMessage("请不要操作多条数据！");
		    return;
		}
		$('#editDialog').dialog({'title':'编辑DTO','href':CONTEXT+'member/editbyid/'+deleteStr,'width': 400,'height': 500}).dialog('open');
	});

	//删除操作
 function delObj(id){
		jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
			if (r){
	    		jQuery.post(CONTEXT+"member/deletebyid",{"id":id},function(data){
					if (data == "success"){
						slideMessage("操作成功！");
						$('#memberdg').datagrid('reload');
						$('#memberdg').datagrid("uncheckAll");
					}else{
						warningMessage(data);
						return;
					}
	    		});
			}else{
				return;
			}
		});
 }
// 编辑
 function editObj(id){
	    isSubmited = false;
	//	$('#editDialog').dialog({'title':'用户信息','href':CONTEXT+'member/editbyid/'+id,'width': 800,'height': 500}).dialog('open');
		  $('<div></div>').dialog({
	           id : 'userEditDialog',
	           title : '用户信息',
	           width : 800,
	           height : 500,
	           closed : false,
	           cache : false,
	           href : CONTEXT+'member/editbyid/'+id,
	           modal : true,
	           onLoad : function() {
	           },
	           onClose : function() {
	               $(this).dialog('destroy');
	           },
	           buttons : [  {
	               text : '关闭',
	               iconCls : 'icon-cancel',
	               handler : function() {
	                   $("#userEditDialog").dialog('destroy');
	               }
	           } ],
	       });

 }

 //查看
 function showObj(id){
		  $('<div></div>').dialog({
           id : 'showDialog',
           title : '用户信息',
           width : 800,
           height : 500,
           closed : false,
           cache : false,
           href : CONTEXT+'member/showbyid/'+id,
           modal : true,
           onLoad : function() {
           },
           onClose : function() {
               $(this).dialog('destroy');
           },
           buttons : [  {
               text : '关闭',
               iconCls : 'icon-cancel',
               handler : function() {
                   $("#showDialog").dialog('destroy');
               }
           } ],
       });
		//  $('#showDialog').dialog({'title':'用户信息','href':CONTEXT+'member/showbyid/'+id,'width': 800,'height': 500}).dialog('open');
 }

//新增
	$('#icon-add').click(function(){
		isSubmited = false;
		//$('#editDialog').dialog({'title':'新增用户','href':CONTEXT+'member/addDto', 'width': 800,'height': 500}).dialog('open');
		  $('<div></div>').dialog({
	           id : 'userEditDialog',
	           title : '新增用户',
	           width : 800,
	           height : 500,
	           closed : false,
	           cache : false,
	           href : CONTEXT+'member/addDto',
	           modal : true,
	           onLoad : function() {
	           },
	           onClose : function() {
	               $(this).dialog('destroy');
	           },
	           buttons : [  {
	               text : '关闭',
	               iconCls : 'icon-cancel',
	               handler : function() {
	                   $("#userEditDialog").dialog('destroy');
	               }
	           } ],
	       });

	});

	//重置
	$('#btn-reset').click(function(){
		$('#memberSearchForm')[0].reset();
	});

	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#memberSearchForm')[0].reset();
		$("#memberdg").datagrid('load',{});
		//重启导出功能
		disableExport = false ;
	});