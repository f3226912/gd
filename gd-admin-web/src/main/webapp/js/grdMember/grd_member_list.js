/*是否禁止导出数据*/
var disableExport = false;

$(document).ready(function(){
	//数据表格绑定
	initDataGridBind();
	
	//根据权限判断是否加载数据
	initLoadDataGrid();
	
	//事件绑定
	initEventBind();
});


/**数据表格绑定
 * 
 */
function initDataGridBind() {
	$('#grdMembertDataGrid').datagrid({
		//url:CONTEXT+'grdMember/queryBySearch', 禁止自动加载数据
		height: 'auto',
		nowrap:true,
		toolbar:'#grdMemberToolBar',
		pageSize:20,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		checked:true,
		fit:true,
		pageList:[10,20,50,100],
		pagination:true,
		onBeforeLoad:setParams,
		onLoadError:function(){warningMessage("查询失败!");},
		onLoadSuccess:function(data){if(!data.isSuccess){warningMessage(data.msg);}},
		columns:[[
		    {field:'id',title:'',width:0,checkbox:true},
		    {field:'name',title:'姓名',width:120,align:'left',halign:'center'},
			{field:'mobile',title:'手机',width:90,align:'center',halign:'center',formatter:formatterMobile},
			{field:'giftteamName',title:'所属团队',width:150,align:'left',halign:'center'},
			{field:'market',title:'所属市场',width:150,align:'left',halign:'center'},
			{field:'registerAmount',title:'邀请注册客户',width:90,align:'center',halign:'center',formatter:formatterRegisterAmount },
			{field:'orderAmount',title:'累计促成订单',width:90,align:'center',halign:'center',formatter:formatterOrderAmount},
			{field:'orderPriceAmount',title:'累计总额',width:90,align:'right',halign:'center',formatter:formatterOrderPriceAmount },
			{field:'status',title:'状态',width:90,align:'right',halign:'center',formatter : function(v, d, i) {
				if (v == '0') {
					return "禁用";
				} else if (v == '1') {
					return "启用";
				} 
			}},
			{field:'context',title:'备注',width:180,align:'left',halign:'center',formatter:formatterContext }
		]]
	});
}

/**设置查询参数
 * @param p
 */
function setParams(p){
	p.marketId=$('#marketId').val();
	p.mobile=$('#mobile').val();
	p.name=$('#name').val();
	p.startDate =$('#queryStartDate').val();
	p.endDate =$('#queryEndDate').val();
	p.giftteamId =$('#giftteamId').val();

}



/**事件绑定初始化
 * 
 */
function initEventBind() {
	//刷新
	$("#reBtn").click(function(){
		 $("#grdMembertDataGrid").datagrid('load');
	 disableExport=false;
	});
	
	// 查询按钮 点击事件
	$('#searchBtn').click(function(){
		$('#grdMembertDataGrid').datagrid('load');
	});
	
	// 重置按钮 点击事件
	$('#resetBtn').click(function(){
		$('#grdMemberForm')[0].reset();
	});
	
	$("#marketId").change(function(){
		var marketId = $(this).val();
		if(marketId){
			$.ajax({
					 type: 'POST',
				     url:CONTEXT+'grdMember/getChildTeamInfo/'+marketId ,
				     dataType:'json',
				     success: function(data) {
				    	 $("#giftteamId").html("");
				    	 $("#giftteamId").append("<option value=''>全部</option>");
				    	 $.each(data.rows, function(index, item){
				    		$("#giftteamId").append("<option value='"+item.giftteamId+"'>"+item.giftteamName+"</option");
				    	 });
				     }
				});
		}else{
			 $("#giftteamId").html("");
	    	 $("#giftteamId").append("<option value=''>全部</option>");
		}
	});
	
/*	// 点击"导出"按钮
	$('#exportDataBtn').click(function(){
		
		exportData();
	});*/
	
	// 数据导出
	$("#exportDataBtn")
			.click(
					function() {
						var queryParams = {
								marketId:$('#marketId').val(),
								giftteamId:$('#giftteamId').val(),
								mobile:$('#mobile').val(),
								name:$('#name').val(),
								startDate :$('#queryStartDate').val(),
								endDate:$('#queryEndDate').val()
						};
						var paramList = "marketId=" + queryParams.marketId
								+"&giftteamId="+queryParams.giftteamId
								+ "&mobile=" + queryParams.mobile
								+ "&name=" + queryParams.name + "&startDate="
								+ queryParams.startDate+"&endDate="
								+ queryParams.endDate;
						$.ajax({
									url : CONTEXT + 'grdMember/checkExportExcelParams',
									data : queryParams,
									type : 'post',
									success : function(data) {
										// 检测通过
										if (data && data.status == 1) {
											if (!disableExport) {
												slideMessage("数据正在导出中, 请耐心等待...");
												disableExport = true;
												// 启动下载
												$.download(CONTEXT
														+ 'grdMember/exportExcelData',
														paramList, 'post');
											} else {
												slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
											}
										} else {
											warningMessage(data.message);
										}
									},
									error : function(data) {
										warningMessage(data);
									}
								});
					});
	
	
	
	
	
	
	
	//删除按钮 点击事件
	$("#removeDataBtn").click(function(){
		//获取选择的行
		var rows = $('#grdMembertDataGrid').datagrid("getSelections");
	    if(rows.length < 1 ) {
	    	warningMessage("请选择要操作的记录！");
	        return false;
	    }
	    
	    var promptForRemove = "您选择了【"+rows.length+"】条数据，确定要删除吗?";
	    
		jQuery.messager.confirm('提示', promptForRemove, function(result){
			if (result){
	    		var idStr = idInRowsConvertStr(rows);
	    		jQuery.post(CONTEXT+"grdMember/deleteByIds", {"ids":idStr}, function(data){
	    			if(data.isSuccess) {
	    				$('#grdMembertDataGrid').datagrid('load');
	    				slideMessage("删除成功！");
						
	    			}else{
						warningMessage(data.msg);
					}
	    		}, "json");
			}
		});

	});

	//禁用 点击事件
	$("#disableBtn").click(function(){
		var rows = $('#grdMembertDataGrid').datagrid("getSelections");
	    if(rows.length < 1 ) {
	    	warningMessage("请选择要操作的记录！");
	        return false;
	    }
	    if(rows.length > 1 ) {
	    	warningMessage("操作的记录只能为1条！");
	        return false;
	    }
	    var status=rows[0].status;
	    var statusName="";
	    var statusValue=0;
	    if(status==0){
	    	statusName=	"启用";
	    	statusValue=1;
	    }else{
	    	statusName="禁用";
	    	statusValue=0;
	    }
	    var name=rows[0].name;
        var promptForRemove = " 确认"+statusName+"用户      "+name+"";
		jQuery.messager.confirm('提示', promptForRemove, function(result){
			if (result){
	    		var idStr = idInRowsConvertStr(rows);
	    		jQuery.post(CONTEXT+"grdMember/resetStatusById", {"ids":idStr,"status":statusValue,"userName":name}, function(data){
	    			if(data.isSuccess) {
	    				$('#grdMembertDataGrid').datagrid('load');
	    				slideMessage(statusName+"成功！");
	    			}else{
						warningMessage(data.msg);
					}
	    		}, "json");
			}
		});
	});
	//启用 点击事件
	$("#enableBtn").click(function(){
		var rows = $('#grdMembertDataGrid').datagrid("getSelections");
	    if(rows.length < 1 ) {
	    	warningMessage("请选择要操作的记录！");
	        return false;
	    }
        var promptForRemove = "您选择了【"+rows.length+"】条数据，确定要启用吗?";
		jQuery.messager.confirm('提示', promptForRemove, function(result){
			if (result){
	    		var idStr = idInRowsConvertStr(rows);
	    		jQuery.post(CONTEXT+"grdMember/resetStatusById", {"ids":idStr,"status":"1"}, function(data){
	    			if(data.isSuccess) {
	    				slideMessage("启用成功！");
	    				$("#grdMembertDataGrid").datagrid('reload');
	    			}else{
						warningMessage(data.msg);
					}
	    		}, "json");
			}
		});
	});
	
	
	//重置密码按钮 点击事件
	$("#resetPwdBtn").click(function(){
		//获取选择的行
		var rows = $('#grdMembertDataGrid').datagrid("getSelections");
	    if(rows.length < 1 ) {
	    	warningMessage("请选择要操作的记录！");
	        return false;
	    }
	    
	    var promptForRemove = "您选择了【"+rows.length+"】条数据，确定要重置密码吗?";
	    
		jQuery.messager.confirm('提示', promptForRemove, function(result){
			if (result){
	    		var idStr = idInRowsConvertStr(rows);
	    		jQuery.post(CONTEXT+"grdMember/resetPwdById", {"ids":idStr}, function(data){
	    			if(data.isSuccess) {
	    				slideMessage("重置密码成功！默认密码为: abc123");
	    			}else{
						warningMessage(data.msg);
					}
	    		}, "json");
			}
		});

	});
	
	
	//监听回车事件
	$(document).keypress(function(e) {
		if(e.which == 13) {
			$('#searchBtn').trigger("click");
		}  
	});
	
	
	// 新增按钮  点击事件
	$("#addDataBtn").click(function(){
		//使用新增div的形式来绑定弹框，以防止jQuery事件的重复绑定问题
		$('<div></div>').dialog({
			id:'saveDialog',
			title:'新增',
			href:CONTEXT+'grdMember/transferToAddPage',
			width: 800,
			height: 360,
            cache : false,
			resizable:true,
			modal:true,
			maximizable:true,
			onClose : function() {
	            $(this).dialog('destroy');
	         },
	         buttons : [{
                 text : '新增',
                 iconCls : 'icon-add',
                 handler : function() {
                     //此方法在弹出框页面(grd_member_editor_save)的js中定义。
                	 addGrdMember();
                 }
             }, {
                 text : '关闭',
                 iconCls : 'icon-cancel',
                 handler : function() {
                     $("#saveDialog").dialog('destroy');
                 }
             }]
		});
	});

}


/**
 * 显示编辑对话框
 * @param id
 */
function showEditorDialog(id){
	$('<div></div>').dialog({
		id:'editorDialog',
		title:'更新',
		href:CONTEXT+'grdMember/transferToEditorPage?id=' + id,
		width: 800,
		height: 360,
        cache : false,
		resizable:true,
		modal:true,
		maximizable:true,
		onClose : function() {
            $(this).dialog('destroy');
         },
         buttons : [{
             text : '保存',
             iconCls : 'icon-save',
             handler : function() {
                 //此方法在弹出框页面(grd_member_editor_save)的js中定义。
            	 updateGrdMember();
             }
         }, {
             text : '关闭',
             iconCls : 'icon-cancel',
             handler : function() {
                 $("#editorDialog").dialog('destroy');
             }
         }]
	});
}

/**
 * 显示邀请用户信息列表对话框
 * @param id
 */
function showInviteUserInfoListDialog(id){
	$('<div style="overflow:hidden"></div>').dialog({
		id:'invateUserInfoListDialog',
		title:'查看客户信息',
		href:CONTEXT+'grdMember/transferToInviteUserInfoListPage?userId=' + id,
		width: 900,
		height: 520,
		cache : false,
		resizable:false,
		modal:true,
		maximizable:true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#invateUserInfoListDialog").dialog('destroy');
			}
		}]
	});
}

/**
 * 显示订单信息列表对话框
 * @param id
 */
function showOrderInfoListDialog(id){
	$('<div style="overflow:hidden"></div>').dialog({
		id:'orderInfoListDialog',
		title:'查看促成订单信息',
		href:CONTEXT+'grdMember/transferToOrderInfoListPage?userId=' + id,
		width: 900,
		height: 520,
		cache : false,
		resizable:false,
		modal:true,
		maximizable:true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$("#orderInfoListDialog").dialog('destroy');
			}
		}]
	});
}

/*
*//**导出数据
 * @returns {Boolean}
 *//*
function exportData(){
	var requestParam = getParams();
	if (disableExport){
		slideMessage("数据正在导出, 请勿重复点击...");
		return false;
	}
	
	//标记为禁用
	disableExport = true ;
	$.ajax({
		url: CONTEXT+'grdMember/checkExportExcelParams',
		data: requestParam,
		type: 'POST',
		dataType:"json",
		success: function(data) {
			if(!data.isSuccess) {
				warningMessage(data.msg);
				
			}else if (data.isPassed){
				slideMessage("数据正在导出中, 请耐心等待...");
				disableExport = true ;
				//启动下载
				$.download(CONTEXT+'grdMember/exportExcelData',requestParam,'post' );
			}else{
				warningMessage(data.reason);
			}
			//恢复导出功能
			disableExport = false;
		},
		error: function(data){
			warningMessage(data);
			//恢复导出功能
			disableExport = false;
		}
	});
}*/

/**获取查询参数
 * @returns
 */
function getParams(){
	var p = $('#grdMembertDataGrid').datagrid('options').queryParams;
	setParams(p);
	return p;
}

/**下载导出的excel数据文件
 * @returns
 */
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


/**
 * 讲datagrid的row的id数据转换为字符串格式，多个id之间用英文逗号隔开。
 */
function idInRowsConvertStr(rows) {
	var idArr = new Array();
	$.each(rows, function(index, obj){
		idArr.push(obj.id);
	})
	return idArr.join(",");
}


 //查看地推礼品详情
function showgrdMemberDetail(id){
	$('#showDialog').dialog({
			'title':'礼品发放明细',
			'href':CONTEXT+'grdMember/showDetail?recordId='+id,
			'width': 900,
			'height': 500,
			'resizable':true
		}).dialog('open');
}

/* ***********数据格式化***************** */

//格式化手机号码
function formatterMobile(val,row,index) {
	var str ="";
	if (val) {
		str += "<a href='javaScript:showEditorDialog(";
		str += row.id;
		str += ")' style='text-decoration:none;'>" ;
		str += val;
		str += "</a>";
	}
	return str;
}

//格式化邀请注册客户总计
function formatterRegisterAmount(val,row,index) {
	var str ="";
	if (val != "" && val !="0") {
		str += "<a href='javaScript:showInviteUserInfoListDialog(";
		str += row.id;
		str += ")' style='text-decoration:none;'>" ;
		str += val;
		str += "</a>";
	}
	return str;
}

//格式化促成订单数量总计
function formatterOrderAmount(val,row,index) {
	var str ="";
	if (val != ""  && val !="0") {
		str += "<a href='javaScript:showOrderInfoListDialog(";
		str += row.id;
		str += ")' style='text-decoration:none;'>" ;
		str += val;
		str += "</a>";
	}
	return str;
}

//格式化促成订单金额总计
function formatterOrderPriceAmount(val,row,index) {
	return val ? parseFloat(val).toFixed(2) : val;
}

//格式化地推人员备注
function formatterContext(val,row,index) {
	var resultStr = "";
		if (val) {
			resultStr += "<div style='width:178;overflow: hidden;white-space: nowrap;text-overflow: ellipsis'>";
			resultStr +=  val;
			resultStr += "</div>";
		}
	return resultStr;
}


// 获取更新礼品发放记录的参数
function getUpdategrdMemberRecordParam() {
	var requestParam ={};
	
	//礼品记录id
	requestParam.id=$("#popWinRecordId").val();
	
	//车牌id
	requestParam.carNo = $("#popWinCarNo").val();
	
	//车牌图片路径
	var carPicPathArr = new Array();
	$.each($("input[data-role='carPicturePath']"), function(index, obj){
		var picPath = $(obj).val();
		if (picPath != null && picPath != "") {
			carPicPathArr.push(picPath);
		}
	});
	requestParam.carPicture = carPicPathArr.join(",");
	
	return requestParam;
}

//根据权限判断是否加载数据
function initLoadDataGrid(){
	if($('#searchBtn').length > 0) {
		var options = $('#grdMembertDataGrid').datagrid('options');
		//直接赋值url即可，无需手动去load数据，datagrid会在页面渲染的时候自动加载。
		options.url = CONTEXT+'grdMember/queryBySearch';
	} else {
		warningMessage("您无地推人员管理模块查询权限！");
	}
};