
$.extend($.fn.datagrid.methods, {
	fixRownumber : function (jq) {
		return jq.each(function () {
			var panel = $(this).datagrid("getPanel");
			//获取最后一行的number容器,并拷贝一份
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			//由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			//默认宽度是25,所以只有大于25的时候才进行fix
			if (width > 25) {
				//多加5个像素,保持一点边距
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
				//修改了宽度之后,需要对容器进行重新计算,所以调用resize
				$(this).datagrid("resize");
				//一些清理工作
				clone.remove();
				clone = null;
			} else {
				//还原成默认状态
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
			}
		});
	}
});

//初始化加载页面列表
function initList(){
	loadList(null);
	$(".datagrid-header-rownumber").text("序号");
	//分页加载
}
//加载列表数据
function loadList(params){	
	params = !params ? {}: params;
	//数据加载
	$('#grdUserCustomer-datagrid-table').datagrid({
		url:CONTEXT+'grdUserCustomer/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#grdUserCustomer-datagrid-tool-bar',
		pageSize:20,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$(this).datagrid("fixRownumber");
			$("#grdUserCustomer-datagrid-table").datagrid('clearSelections');
		},
		columns:[[
		    {field:'memberId',title:'客户Id',hidden:true,width:120,align:'left'},
			{field:'memberName',title:'客户姓名',width:120,align:'left'},
			{field:'memberMobile',title:'客户手机',width:120,align:'left'},
			{field:'grdUserName',title:'地推人员',width:120,align:'left'},
			{field:'grdMobile',title:'地推人员手机',width:120,align:'left'},
			{field:'marketName',title:'所属市场',width:150,align:'left'},
			{field:'remarks',title:'备注',width:200,align:'left',formatter : remarksformat}
		]]
	}); 
	$("#grdUserCustomer-datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function remarksformat(value, row, index) {
	var remarks = row.remarks;
	if (remarks && remarks.length > 30) {
		remarks = remarks.substring(0, 30) + "...";
	}
	return remarks;
}


var disableExport = false ;

//设置查询参数
function setParams(p){
	p.marketId = $('#grdUserCustomer-datagrid-form #marketId').val();
	p.memberName = $('#grdUserCustomer-datagrid-form #memberName').val();
	p.memberMobile = $('#grdUserCustomer-datagrid-form #memberMobile').val();
	p.grdUserName = $('#grdUserCustomer-datagrid-form #grdUserName').val();
	p.grdMobile = $('#grdUserCustomer-datagrid-form #grdMobile').val();
}
//获取查询参数
function getParams(){
	var p = $('#grdUserCustomer-datagrid-table').datagrid('options').queryParams;
	setParams(p);
	return p;
}
/***数据导出功能***/
$("#exportData").click(function(){
	var requestParam = getParams();
	if (disableExport){
		slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,刷新当前页面..");
		return false;
	}
	
	var paramList = 'marketId=' + requestParam.marketId + '&memberName=' + requestParam.memberName 
	+ '&grdUserName=' + requestParam.grdUserName
	+ '&grdMobile=' + requestParam.grdMobile + '&memberMobile=' + requestParam.memberMobile;
	$.ajax({
		url: CONTEXT+'grdUserCustomer/checkExportParams',
		data : requestParam,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				/* $("#Loading2").show(); */
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'grdUserCustomer/exportData',paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage("error : " + data);
			//恢复导出功能
			disableExport = false;
		}
	});
});

function query(){
	var requestParam = getParams();
	loadList(requestParam);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#grdUserCustomer-datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
}

//修改
$('#assignMember').click(function() {
	var row = $('#grdUserCustomer-datagrid-table').datagrid("getSelections");
	if ($(row).length < 1) {
		warningMessage("请选择要操作的数据！");
		return false;
	} else if($(row).length == 1){
		edit($('#grdUserCustomer-datagrid-table').datagrid('getSelected').memberId);
	} else if($(row).length > 1){
		warningMessage("请不要选择多条数据！");
		return false;
	}
});


function save() {	
	var row = $('#assignMemberDataGrid').datagrid("getSelections");
	if ($(row).length < 1) {
		warningMessage("请选择要绑定的地推人员!");
		return false;
	}else if($(row).length == 1){
		var grdUserId=$('#assignMemberDataGrid').datagrid('getSelected').id;
		var memberId = $("#assignMemberId").val();
		var url = CONTEXT + "grdUserCustomer/save";
		jQuery.post(url, {"memberId":memberId,"grdUserId":grdUserId}, function(data) {
			if (data.msg == "success") {
				slideMessage("操作成功！");
				// 刷新父页面列表
				$("#grdUserCustomer-datagrid-table").datagrid('reload');
				$('#saveDialog').dialog('close');
			} else {
				warningMessage(data.msg);
				return;
			}
		});
	}else if($(row).length > 1){
		warningMessage("请不要选择多条数据！");
		return false;
	}
}

/**修改操作
* @param id 当前对象ID
*/
function edit(memberId){
$('<div></div>').dialog({
  id : 'saveDialog',
  title : '修改',
  width : 900,
  height : 450,
  closed : false,
  cache : false,
  href : CONTEXT+'grdUserCustomer/edit/'+memberId,
  modal : true,
  onLoad : function() {
      //初始化表单数据
  },
  onClose : function() {
      $(this).dialog('destroy');
  },
  buttons : [ {
      text : '确认',
      iconCls : 'icon-save',
      handler : function() {
    	  save();
          return false; // 阻止表单自动提交事件
      }
  }, {
      text : '关闭',
      iconCls : 'icon-cancel',
      handler : function() {
          $("#saveDialog").dialog('destroy');
      }
  } ],
});
}

$(function(){
	initList();
	//查询按钮
	$('#btn-search').click(function(){
		//query();
		var requestParam = getParams();
		$('#grdUserCustomer-datagrid-table').datagrid('load',requestParam);
		//重启导出功能
		disableExport = false ;
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		reset();
	});

	
});



jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};
