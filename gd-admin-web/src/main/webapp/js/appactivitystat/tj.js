//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [50,100,200,300,500]
	});
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'appactivitystat/query?appType='+appType,
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datagrid-tool-bar',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#datagrid-table").datagrid('clearSelections');
		},
		columns:[[
		    {field:'mobile',title:'手机号码',width:100,align:'center'},
		    {field:'account',title:'姓名',width:100,align:'center'},		
		    {field:'marketName',title:'所属市场',width:100,align:'center'},
		    /*{field:'teamName',title:'所属团队',width:100,align:'center'},*/
		    {field:'deviceUUID',title:'设备UUID',width:100,align:'center'},
			{field:'deviceIMEI',title:'设备IMEI',width:100,align:'center'},
			{field:'deviceMEID',title:'设备MEID',width:100,align:'center'},
			{field:'deviceICCID',title:'设备ICCID',width:100,align:'center'},
			{field:'cellphoneModel',title:'手机型号',width:100,align:'center'},			
			{field:'cellphoneRAM',title:'手机内存',width:100,align:'center'},
			{field:'cellphoneROM',title:'手机容量',width:100,align:'center'},
			{field:'system',title:'所属平台',width:100,align:'center',formatter:formatterSystem },
			{field:'systemVersion',title:'平台版本号',width:100,align:'center'},
			{field:'appVersion',title:'客户端版本号',width:100,align:'center'},			
			/*{field:'appChannel',title:'所属渠道',width:100,align:'center'},*/
			{field:'type',title:'统计项目',width:100,align:'center',formatter:formatterType },
			{field:'isLogin',title:'登录状态',width:100,align:'center',formatter:formatterIslogin},
			{field:'userCreateTime',title:'账号创建时间',width:100,align:'center'},
			{field:'createTime',title:'统计时间',width:100,align:'center'}
		]]
	}); 
}

function formatterIslogin(val,row){
	if (val == false) {
		return "否";
	}else{
		return "是";
	}
}

function formatterSystem(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "IOS";
		}else if(str=="2"){
			return "Android";
		}
	}
}
function formatterType(val, row) {
	if (val != null) {
		return "启动APP";
	}
}

function query(){
	var params = {
			"mobile" : $("#mobile").val(),
			"account" : $("#account").val(),
			"marketId" : $("#marketId").val(),
			"teamId" : $("#teamId").val(),
			"system" :$("#system").val(),
			"appVersion" :$("#appVersion").val(),
			"queryStartDate" :$("#queryStartDate").val(),
			"queryEndDate" :$("#queryEndDate").val()
	};
	loadList(params);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
}

function refresh(){
	$('#datagrid-form')[0].reset();
	$("#datagrid-table").datagrid('load', {});
	//重启导出功能
	disableExport = false ;
}

$(function(){
	initList();
	//查询按钮
	$('#btn-search').click(function(){
		query();
	});
	
	//刷新按钮
	$('#btn-refresh').click(function(){
		refresh();
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		reset();
	});
	
	// 新增
	$('#btn-add').click(function() {
		$('<div></div>').dialog({
          id : 'saveDialog',
          title : '新增数据',
          width : 800,
          height : 450,
          closed : false,
          cache : false,
          href : CONTEXT+'appactivitystat/beforeSave',
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
            	  save();
                  return false; // 阻止表单自动提交事件
              }
          }, {
              text : '取消',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#saveDialog").dialog('destroy');
              }
          } ],
      });
	});
	
	// 修改
	$('#btn-edit').click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		} else if($(row).length == 1){
			edit(getSelections("id"));
		} else if($(row).length > 1){
			warningMessage("请不要选择多条数据！");
			return false;
		}
	});
	
	// 删除操作
	$("#btn-remove").click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		}
		delete(getSelections("id"));
	});
});

function save() {
	if($('#save-form').form('validate')){
		var url = CONTEXT + "appactivitystat/save";
		jQuery.post(url, $('#save-form').serialize(), function(data) {
			if (data.msg == "success") {
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#datagrid-table").datagrid('reload');
				$('#saveDialog').dialog('close');
			} else {
				warningMessage(data.msg);
				return;
			}
		});
	}
}

/**修改操作
 * @param id 当前对象ID
 */
function edit(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '修改数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'appactivitystat/edit/'+id,
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
        	  save();
              return false; // 阻止表单自动提交事件
          }
      }, {
          text : '取消',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog").dialog('destroy');
          }
      } ],
  });
}
/**查看操作
 * @param id 当前对象ID
 */
function view(id){
	$('<div></div>').dialog({
      id : 'saveDialog',
      title : '查看数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT+'appactivitystat/view/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '确定',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog").dialog('destroy');
          }
      } ],
  });
}


