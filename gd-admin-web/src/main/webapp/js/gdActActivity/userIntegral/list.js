/**
 * 积分变更记录列表
 */

$(function(){
	//加载数据
	var params = {};
	loadListData(params);
	
	//新增
	$("#btnAdd").click(clickAdd);
	//查询
	$("#btnSearch").click(clickQuery);
	//导出
	$("#btnExport").click(clickExport);
	//刷新
	$('#btnRefresh').click(refresh);
	//重置
	$('#btnReset').click(reset);
});

/**
 * 加载列表数据
 * @param params
 */
function loadListData(params){
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'activityIntegral/queryList',
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
			{field:'id',title:'',width:50, checkbox:true},
			{field:'memberAccount',title:'用户账号',width:100,align:'center'},
			{field:'mobile',title:'手机号码',width : 100,align:'center'},
			{field:'activityCode',title:'活动编号',width:100,align:'center'},
			{field:'activityName',title:'活动名称',width:100,align:'center'},
			{field:'integral',title:'变更积分',width:100,align:'center'},
			{field:'integralTypeName',title:'变更类型',width:100,align:'center'},
			{field:'createTimeStr',title:'积分获取时间',width:100,align:'center'},
			{field:'createUser',title:'创建人',width:100,align:'center'},
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}	
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}

/**
 * 根据条件查询列表
 */
function clickQuery()
{
	var params = initParams();
	loadListData(params);
}

/**
 * 获取查询导出参数
 */
function initParams()
{   
	var memberAccount = $('#datagrid-form #memberAccount').val();
	var memberMobile = $('#datagrid-form #memberMobile').val();
	var activityCode = $('#datagrid-form #activityCode').val();
	var activityName = $('#datagrid-form #activityName').val();
	var startTime = $('#datagrid-form #startTime').val();
	var endTime = $('#datagrid-form #endTime').val();

	var params = {
		"memberAccount" : memberAccount,
		"mobile" : memberMobile,
		"activityCode" : activityCode,
		"activityName" : activityName,
		"startTime" : startTime,
		"endTime" : endTime
	};
	
	return params;
}

function optformat(value,row,index)
{
	var options = "<a  href='javascript:;' onclick=clickDetail('"+row.id+"')>查看</a>";
	return options;
}

/**
 * 点击新增按钮
 * @param id
 */
function clickAdd()
{
	$('#addDialog').dialog(
		{	
			title:"新增积分变更", 
			width:500, 
			height:300, 
			href:CONTEXT+'activityIntegral/addPage'
		}
	).dialog('open');
}

/**
 * 查看
 * @param id
 */
function clickDetail(id)
{
	$('<div></div>').dialog({
	      id : 'detailDialog',
	      title : '查看积分变更',
	      width : 500,
	      height : 300,
	      closed : false,
	      cache : false,
	      href:CONTEXT+'activityIntegral/detailPage?id='+id,
	      modal : true,
	      onLoad : function() {
	          //初始化表单数据
	    	  //initbuyerProd();
	      },
	      onClose : function() {
	          $(this).dialog('destroy');
	      },
	      buttons : [ {
	          text : '关闭',
	          iconCls : 'icon-cancel',
	          handler : function() {
	        	  $("#detailDialog").dialog('destroy');
	          }
	      }]
	  });
}

var disableExport = false ;
/**
 * 数据导出
 */
function clickExport(){
	var params = initParams();
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);
	$.ajax({
		url: CONTEXT+'activityIntegral/checkExport',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data.result == "success"){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...") ;
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'activityIntegral/exportData',paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage("error : " + data);
		}
	});
};

function reset(){
	$('#datagrid-form')[0].reset();
	disableExport = false ;
}

function refresh(){
	var params = {};
	loadListData(params);
	//重启导出功能
	disableExport = false ;
}