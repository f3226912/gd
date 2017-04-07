//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#adinfodg").datagrid("getPager").pagination({
		pageList: [50,100,200,300,500]
	});
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#adinfodg').datagrid({
		url:CONTEXT+'pushV2/adInfoQuery',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#adinfotb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#adinfodg").datagrid('clearSelections');
		},
		columns:[[
//			{field:'id',title:'',width:0},
			{field:'adName',title:'广告名称',width:100,align:'center'},
//			{field:'marketName',title:'所属市场',width:80,align:'center'},
			{field:'spaceSign',title:'广告位',width:80,align:'center' },
			{field:'jumpType',title:'跳转类型',width:80,align:'center',formatter:formatterAdType },
			{field:'startTime',title:'开始时间',width:60,align:'center',formatter:formatterdate},
			{field:'endTime',title:'结束时间',width:60,align:'center',formatter:formatterdate},
			{field:'state',title:'状态',width:30,align:'center',formatter:formatterState },
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]]
	}); 
}
function formatterAdType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "类目-商品列表";
		}else if(str=="2"){
			return "商铺首页";
		}else if(str=="3"){
			return "URL";
		}else if(str=="4"){
			return "搜索-商品列表";
		}else if(str=="5"){
			return "商品标签";
		}else if(str=="6"){
			return "商品详情";
		}else if(str=="7"){
			return "专题活动";
		}
	}
}
function formatterdate(val, row) {
	if (val != null) {
		var str = val.toString();
		str = str.replace(/-/g, "/");
		var date = new Date(str);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
				+ date.getDate();
	}
}
//状态(01开始,02截至,03下架)
function formatterState(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "启用";
		}else if(str=="2"){
			return "等待";
		}else if(str=="3"){
			return "截止";
		} else if(str=="4"){
			return "禁用";
		} 
	}
}

function query(){
	var params = {
		"startTimeStr" : $("#startDate").val(),
		"endTimeStr" : $("#endDate").val(),
		"adName" : $("#adName").val(),
		"state" : $("#state").val(),
		"adspaceName" : $("#adspaceName").val(),
		"adSpaceId" : $("#adSpaceId").val(),
		"spaceSign" : $("#spaceSign").val(),
		"menuId" : $("#menuId").val()
	};
	loadList(params);
}


$(function(){
	initList();
	//查询按钮
	$('#icon-search').click(function(){
		query();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		refresh();
	});
	
	//重置按钮
	$('#icon-reload').click(function(){
		//$('#adinfoSearchForm')[0].reset();
		reset();
	});
});

function reset(){
	$('#adinfoSearchForm')[0].reset();
}

function refresh(){
	$('#adinfoSearchForm')[0].reset();
	$("#adinfodg").datagrid('load', {});
//	location.reload(true);
}

//添加菜单
	function addAdMenu(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var fatherName=node.text;
			var fatherId=node.id;
			$('#editButtonDialog').dialog({'title':'新增菜单','href':CONTEXT+'adMenu/adMenuAdd?fatherName='+fatherName+'&fatherId='+fatherId}).dialog('open');
		}else{
			warningMessage("请选择操作目标");
		}
	}
	//查看菜单
	function adMenuView(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			$('#editButtonDialog').dialog({'title':'查看菜单','href':CONTEXT+'adMenu/adMenuView/'+id}).dialog('open');
		}else{
			warningMessage("请选择操作目标");
		}
	}
	//修改菜单
	function adMenuUpdate(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			$('#editButtonDialog').dialog({'title':'修改菜单','href':CONTEXT+'adMenu/adMenuUpate/'+id}).dialog('open');
		}else{
			warningMessage("请选择操作目标");
		}
	}
	//删除菜单
	function adMenudelete(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
				if (r) {
					jQuery.post(CONTEXT + "adMenu/delete", {
						"id" : id
					}, function(data) {
						if (data == "success") {
							slideMessage("删除成功！");
							$('#treeMenu').tree('remove',node.target);
						} else {
							warningMessage("删除失败！");
							return false;
						}
					});
				} else {
					return false;
				}
			});
		}else{
			warningMessage("请选择操作目标");
		}
	}
	//添加广告位
	function addAdSpace(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var fatherName=node.text;
			var fatherId=node.id;
			$('<div></div>').dialog({
		          id : 'editAdSpaceDialog',
		          title : '新增广告位',
		          width : 800,
		          height : 450,
		          closed : false,
		          cache : false,
		          href : CONTEXT+'adSpace/add?fatherName='+fatherName+'&menuId='+fatherId,
		          modal : true,
		          onLoad : function() {
		              //初始化表单数据
		          },
		          onClose : function() {
		              $(this).dialog('destroy');
		          },
		          buttons : [ {
		              text : '保存',
		              iconCls : 'icon-save',
		              handler : function() {
		            	  saveAdd();
		                  return false; // 阻止表单自动提交事件
		              }
		          }, {
		              text : '取消',
		              iconCls : 'icon-cancel',
		              handler : function() {
		                  $("#editAdSpaceDialog").dialog('destroy');
		              }
		          } ],
		      });
		}else{
			warningMessage("请选择操作目标");
		}
	}
	
	//修改广告位
	function AdSpaceUpdate(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			$.ajax({
				type: "GET",
				url: CONTEXT+"pushV2/adInfoQuery",
				dataType: "json",
				async: false,
				data:{
					"adSpaceId":id,
					"state":1,
					"startRow":0,
					"endRow":10
				},
				success: function(data) {
					if(data.total > 0){
						warningMessage("该广告位下已有广告正在启用，不可以被编辑");
					}else{
						//无启用的广告
						$('<div></div>').dialog({
					          id : 'editAdSpaceDialog',
					          title : '修改广告位',
					          width : 800,
					          height : 450,
					          closed : false,
					          cache : false,
					          href : CONTEXT+'adSpace/edit?id='+id,
					          modal : true,
					          onLoad : function() {
					              //初始化表单数据
					          },
					          onClose : function() {
					              $(this).dialog('destroy');
					          },
					          buttons : [ {
					              text : '保存',
					              iconCls : 'icon-save',
					              handler : function() {
					            	  saveEdit();
					                  return false; // 阻止表单自动提交事件
					              }
					          }, {
					              text : '取消',
					              iconCls : 'icon-cancel',
					              handler : function() {
					                  $("#editAdSpaceDialog").dialog('destroy');
					              }
					          } ],
					      });
					}
				}
			});
		}else{
			warningMessage("请选择操作目标");
		}
	}
	
	function viewAdSpace(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			$('<div></div>').dialog({
		          id : 'editAdSpaceDialog',
		          title : '查看广告位',
		          width : 800,
		          height : 450,
		          closed : false,
		          cache : false,
		          href :  CONTEXT+'adSpace/view?id='+id,
		          modal : true,
		          onLoad : function() {
		              //初始化表单数据
		          },
		          onClose : function() {
		              $(this).dialog('destroy');
		          },
		          buttons : [{
		              text : '取消',
		              iconCls : 'icon-cancel',
		              handler : function() {
		                  $("#editAdSpaceDialog").dialog('destroy');
		              }
		          } ],
		      });
		}else{
			warningMessage("请选择操作目标");
		}
	}
	
	//删除广告位
	function adSpacedelete(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
				if (r) {
					jQuery.post(CONTEXT + "adSpace/delete?id="+id, {
						"id" : id
					}, function(data) {
						if (data.message == "success") {
							slideMessage("删除成功！");
							$('#treeMenu').tree('remove',node.target);
						} else {
							warningMessage(data.message);
							return false;
						}
					});
				} else {
					return false;
				}
			});
		}else{
			warningMessage("请选择操作目标");
		}
	}
	
	
	function addAdvertise(){
		var adSpaceId=$("#adSpaceId").val();
		if(!adSpaceId){
			warningMessage("请先选中广告位");
			return;
		}
		$('<div></div>').dialog({
	          id : 'editAdSpaceDialog',
	          title : '新增广告',
	          width : 800,
	          height : 450,
	          closed : false,
	          cache : false,
	          href : CONTEXT+'pushV2/adInfoAdd/'+adSpaceId,
	          modal : true,
	          onLoad : function() {
	              //初始化表单数据
	          },
	          onClose : function() {
	              $(this).dialog('destroy');
	          },
	          buttons : [ {
	              text : '保存',
	              iconCls : 'icon-save',
	              handler : function() {
	            	  saveadd2();
	                  return false; // 阻止表单自动提交事件
	              }
	          }, {
	              text : '取消',
	              iconCls : 'icon-cancel',
	              handler : function() {
	                  $("#editAdSpaceDialog").dialog('destroy');
	              }
	          }],
	      });
	}
	
	//修改广告
	function AdvertiseUpdate(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			var adSpaceId=node.attributes.adSpaceId
			$('<div></div>').dialog({
		          id : 'editAdSpaceDialog',
		          title : '修改广告',
		          width : 800,
		          height : 450,
		          closed : false,
		          cache : false,
		          href : CONTEXT+'pushV2/adInfoEditById/'+id+"?adSpaceId="+adSpaceId,
		          modal : true,
		          onLoad : function() {
		              //初始化表单数据
		          },
		          onClose : function() {
		              $(this).dialog('destroy');
		          },
		          buttons : [ {
		              text : '保存',
		              iconCls : 'icon-save',
		              handler : function() {
		            	  saveadd();
		            	  refresh();
		                  return false; // 阻止表单自动提交事件
		              }
		          }, {
		              text : '取消',
		              iconCls : 'icon-cancel',
		              handler : function() {
		                  $("#editAdSpaceDialog").dialog('destroy');
		              }
		          }],
		      });
		}else{
			warningMessage("请选择操作目标");
		}
	}
	
	function editObj(id,adSpaceId){
		$("#adinfodg").datagrid('clearSelections');
		$('<div></div>').dialog({
          id : 'editAdSpaceDialog',
          title : '修改广告',
          width : 800,
          height : 450,
          closed : false,
          cache : false,
          href : CONTEXT+'pushV2/adInfoEditById/'+id+"?adSpaceId="+adSpaceId,
          modal : true,
          onLoad : function() {
              //初始化表单数据
          },
          onClose : function() {
              $(this).dialog('destroy');
          },
          buttons : [ {
              text : '保存',
              iconCls : 'icon-save',
              handler : function() {
            	  saveadd2();
                  return false; // 阻止表单自动提交事件
              }
          }, {
              text : '取消',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#editAdSpaceDialog").dialog('destroy');
              }
          } ,{
              text : '启用',
              iconCls : 'icon-ok',
              handler : function() {
            	  delObj(id,'1',adSpaceId);
              }
          }],
      });
	}
	function saveadd2() {
		if(!$("#addForm").form('validate')){
			return $("#addForm").form('validate');
		}
		var url=CONTEXT+"pushV2/adInfoSaveAdd";
		if($("#optionType").val()=='update'){
			url=CONTEXT+"pushV2/adInfoSaveEdit";
		}
		$("#Loading").show();
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data.msg == "success") {
				 $("#Loading").hide();
				var adAdvertise=data.adAdvertise;
				slideMessage("操作成功！");
				query();
				$('#editAdSpaceDialog').dialog('destroy');
				return true;
			} else {
				 $("#Loading").hide();
				warningMessage(data.msg);
				return;
			}
		});
	}
	//查看广告
	function AdvertiseView(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			var adSpaceId=node.attributes.adSpaceId
			detailObj(id,adSpaceId);
		}else{
			warningMessage("请选择操作目标");
		}
	}
	
	function detailObj(id,adSpaceId){
		$("#adinfodg").datagrid('clearSelections');
		$('<div></div>').dialog({
	          id : 'editAdSpaceDialog',
	          title : '广告详情',
	          width : 800,
	          height : 450,
	          closed : false,
	          cache : false,
	          href : CONTEXT+'pushV2/adInfoDetailById/'+id+"?adSpaceId="+adSpaceId,
	          modal : true,
	          onLoad : function() {
	              //初始化表单数据
	          },
	          onClose : function() {
	              $(this).dialog('destroy');
	          },
	          buttons : [ {
	              text : '取消',
	              iconCls : 'icon-cancel',
	              handler : function() {
	                  $("#editAdSpaceDialog").dialog('destroy');
	              }
	          } ],
	      });
	}
	
	//禁用广告
	function advertiseDelete(){
		var node = $('#treeMenu').tree('getSelected');
		if (node){
			var id=node.id;
			delObj(id);
		}else{
			warningMessage("请选择操作目标");
		}
	}
	
	function delObj(id,state,adSpaceId){
		var stateStr="您确定要禁用所选广告吗?";
		if(state=='1'){
			stateStr="您确定要启用所选广告吗?";
		}
		jQuery.messager.confirm('提示', stateStr, function(r) {
			if (r) {
				jQuery.post(CONTEXT + "pushV2/adInfoDeleteById/"+id, {
					"id" : id,
					"state":state,
					"adSpaceId":adSpaceId
				}, function(data) {
					if (data.msg == "success") {
						var adAdvertise=data.adAdvertise;
						slideMessage("操作成功！");
						try {
							$('#treeMenu').tree('update', {
								target: node.target,
								text: adAdvertise.adName+"(禁用)"
							});
						} catch (e) {
						}
						query();
					} else {
						warningMessage(data.msg);
						return false;
					}
				});
			} else {
				return false;
			}
		});
	}
