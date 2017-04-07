$(function(){
	$("#datagrid").datagrid({
		url : CONTEXT+'marketberth/marketberthlist',
		title:"",						//表格标题
		iconCls:"icon-search",			//表格图标
		nowrap: false,					//是否只显示一行，即文本过多是否省略部分。
		striped: true,					//显示斑马线效果(默认false)
		nowrap: true, 					//设置信息在同一行显示(默认true)
		showHeader: true, 				//是否显示行头(默认true)
        showFooter: true, 				//是否显示行尾(默认false)
		sortName: "t.updateTime",
		sortOrder: "desc",
		idField: "id",					//设置标识字段
		loadMsg:"拼命加载中 请稍后...",
		fitColumns:true,				//自动展开/收缩列(默认false,在列比较少的时候设置此属性) 
		fit:true,						//datagrid的宽高自适应
		pagination:true, 				//包含分页
		pageSize: 20,
		pageList: [20,30,50,100],		//可以设置每页记录条数的列表  
		rownumbers:true,
		singleSelect:true,
		toolbar : "#tab-tools",			//工具栏
		queryParams:{ },
		frozenColumns : [[
			{checkbox:true},
			{field : "marketName",title : "市场名称",width : 500,align : "center"}
		]],
		columns : [[
			{field : "updateTime",title : "更新时间",width : 100,align : "center"},
			{field : "updateUser", title : "修改人", width : 100, align : "center"}
			
		]],//单选
		onCheck:function(rowIndex,row){
			//if(row.auditState == 1002){
				//取消选择
			//	$("#datagrid").datagrid("unselectRow',rowIndex).datagrid('uncheckRow',rowIndex);
			//}
			//$('#tt').datagrid("addToolbarItem",[{"text":"xxx"},"-",{"text":"xxxsss","iconCls":"icon-ok"}])
		},
		onClickRow:function(rowIndex,row){
			//if(row.auditState == 1002){
				//取消选择
			//	$('#datagrid').datagrid('unselectRow',rowIndex).datagrid('uncheckRow',rowIndex);
			//}
		},
		//全选
		onCheckAll:function(rows){
			//判断能不能选中
			//for(var i=0;i<rows.length;i++){
				//var row = rows[i];
				//if(row.auditState == 1002){
					//取消选择
				//	var rowIndex = $('#datagrid').datagrid('getRowIndex',row);
				//	$('#datagrid').datagrid('unselectRow',rowIndex).datagrid('uncheckRow',rowIndex);

				//}
			//}
		}, 
		toolbar:[
		{
			text:"查询",  
			iconCls:"icon-search",  
			handler:function(){
				$('#datagrid').datagrid('load',{
					marketId: $('#market').combobox('getValue')
				});
			}  
		},"-",{
			text:"刷新",  
			iconCls:"icon-reload",  
			handler:function(){
				$('#datagrid').datagrid('load',{});
				var data = $('#market').combobox('getData');
				$("#market").combobox('select',data[0].id);
			}  
		},"-",{
			text:"查看",  
			iconCls:"icon-zoom",  
			handler:function(){
				var row = $("#datagrid").datagrid("getSelected");
				if (row){
					$('#fm').form('load',row);
					$('#dlg').dialog('open').dialog('setTitle',"查看市场街区");
					//加载当前已经拥有的市场街区
					queryMarketGroup(row.marketId,1);
					//加载市场街区明细
					berthdatagrid(row.marketId, 1);
				}else{
					$.messager.alert("提示信息","请选选择一行进行操作！","info");  	
				}
			}  
		},"-",{
			text:"添加",  
			iconCls:"icon-add",  
			handler:function(){
				$('#market_add').combobox({
					url:CONTEXT+'marketberth/marketlist/1',
				    valueField:'id',
				    textField:'marketName',
				    onLoadSuccess:function(){
				    	var data = $('#market_add').combobox('getData');
				    	$("#market_add").combobox('select',data[0].id);
			    	}
				});
				$("#addMarketMax").html("<div style='width: 80px; float: left; margin-right: 10px;'>" +
						"	<input name='marketBerthAddDTOs[0].addArea' id='' style='width: 50px;' class='easyui-validatebox' data-options='required:true'/>&nbsp;&nbsp;区</div>" +
						"<div style='width: 80px; float: left; margin-right: 10px;'>" +
						"	<input name='marketBerthAddDTOs[0].addBuild' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;栋</div>" +
						"<div style='width: 80px; float: left; margin-right: 10px;'>" +
						"	<input name='marketBerthAddDTOs[0].addLayer' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;层</div>" +
						"<div style='width: 80px; float: left; margin-right: 10px;'>" +
						"	<input name='marketBerthAddDTOs[0].addBerth' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;铺</div>" +
						"<div style='width: 100%; height: 20px; clear: both;'></div>");
				$('#adddlg').dialog('open').dialog('setTitle',"添加市场街区");
			}  
		},"-",{
			text:"编辑",  
			iconCls:"icon-edit",  
			handler:function(){
				var row = $("#datagrid").datagrid("getSelected");
				if (row){
					$('#editfm').form('load',row);
					$('#editdlg').dialog('open').dialog('setTitle',"编辑市场街区");
					//加载当前已经拥有的市场街区
					queryMarketGroup(row.marketId,2);
					//加载市场街区明细
					berthdatagrid(row.marketId, 2);
					$("#editMarketId").val(row.marketId);
				}else{
					$.messager.alert("提示信息","请选选择一行进行操作！","info");  	
				}
			}  
		}],
		onLoadSuccess:function(data){
			if(data.result == "error"){
				$.messager.show({title:"提示信息",msg:data.msg,timeout:3000});
			}
		},onLoadError:function(data){
			$.messager.alert("提示信息","请求服务器失败!");
		}
	});	
	
	
	$(".datagrid-toolbar").before($("#searchbox"));
	$(".datagrid-toolbar table").before($(".toggle"));
	
	//加载市场
	$('#market').combobox({
		url:CONTEXT+'marketberth/marketlist/0',
	    valueField:'id',
	    textField:'marketName',
	    onLoadSuccess:function(){
	    	var data = $('#market').combobox('getData');
	    	$("#market ").combobox('select',data[0].id);
    	}
	});
	
	//添加市场街区
	$(".submit_btn").click(function(){
		$('#addfm').form('submit',{
			url:CONTEXT+'marketberth/addmarketlist',
			onSubmit: function(){
				return $(this).form('validate');
			},success: function(data){
				var json = eval('(' + data + ')');  
				if(json.result == "SUCCESS"){
					$('#adddlg').dialog('close');
					$.messager.show({title:'提示信息',msg:"操作成功",timeout:3000});
					$('#datagrid').datagrid('load');
				}else{
					$.messager.show({title:'提示信息',msg:json.msg,timeout:3000});
				}
			},error: function(){
				$.messager.alert('提示信息','请求服务器失败!');
			}
		});
	});
	//编辑
	$(".edit_submit_btn").click(function(){	
		$('#editfm').form('submit',{
			url:CONTEXT+'marketberth/editmarketlist',
			onSubmit: function(){
				return $(this).form('validate');
			},success: function(data){
				var json = eval('(' + data + ')');  
				if(json.result == "SUCCESS"){
					$('#editdlg').dialog('close');
					$.messager.show({title:'提示信息',msg:"操作成功",timeout:3000});
					$('#datagrid').datagrid('load');
					editCount = 0;
				}else{
					$.messager.show({title:'提示信息',msg:json.msg,timeout:3000});
				}
			},error: function(){
				$.messager.alert('提示信息','请求服务器失败!');
			}
		});
	});
	
	
	$(".toggle").toggle(function(){
		$(".toggle").css({"background-position":"-18px -15px"});
		$("#searchbox").hide();
	}, function(){
		$(".toggle").css({"background-position":"-18px 0px"});
		$("#searchbox").show();
	});
});

/**
 * 查看或编辑已有的市场街区
 * @param marketId
 * @param type 1:查看, 2:编辑
 */
function queryMarketGroup(marketId, type){
	$.ajax({
		type : "post", // 以post方式与后台沟通
		url : CONTEXT+'marketberth/marketberthgroup',
		dataType : "json",
		async : false,
		data: {"marketId":marketId},
		success : function(data) {
			if(data.result == "error"){
				$.messager.show({title:'提示信息',msg:data.msg,timeout:3000});
			}
			editCount = data.total - 1;
			if(type == 1){
				$("#marketMax").html("");
				for(var i=0; i<data.rows.length; i++){
					$("#marketMax").append("<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='' id='' value='"+data.rows[i].maxArea+"' style='width: 50px;' readonly='readonly'/>&nbsp;&nbsp;区</div>" +
							"<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='' id='' value='"+data.rows[i].maxBuild+"' style='width: 50px;' readonly='readonly'/>&nbsp;&nbsp;栋</div>" +
							"<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='' id='' value='"+data.rows[i].maxLayer+"' style='width: 50px;' readonly='readonly'/>&nbsp;&nbsp;层</div>" +
							"<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='' id='' value='"+data.rows[i].maxBerth+"' style='width: 50px;' readonly='readonly'/>&nbsp;&nbsp;铺</div>" +
							"<div style='width: 100%; height: 20px; clear: both;'></div>");
				}
			}else{
				$("#editMarketMax").html("");
				for(var i=0; i<data.rows.length; i++){
					$("#editMarketMax").append("<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='marketBerthAddDTOs["+i+"].addArea' id='' value='"+data.rows[i].maxArea+"' style='width: 50px;'/>&nbsp;&nbsp;区</div>" +
							"<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='marketBerthAddDTOs["+i+"].addBuild' id='' value='"+data.rows[i].maxBuild+"' style='width: 50px;'/>&nbsp;&nbsp;栋</div>" +
							"<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='marketBerthAddDTOs["+i+"].addLayer' id='' value='"+data.rows[i].maxLayer+"' style='width: 50px;'/>&nbsp;&nbsp;层</div>" +
							"<div style='width: 80px; float: left; margin-right: 10px;'>" +
							"	<input name='marketBerthAddDTOs["+i+"].addBerth' id='' value='"+data.rows[i].maxBerth+"' style='width: 50px;'/>&nbsp;&nbsp;铺</div>" +
							"<div style='width: 100%; height: 20px; clear: both;'></div>");
				}
			}
		},
		error : function() {
			$.messager.alert('提示信息','请求服务器失败!');
		}
	});
}

/**
 * 
 * @param marketId	
 * @param type	1:查看, 2:编辑
 */
function berthdatagrid(marketId, type){
	var divStr = "";
	if(type == 1){
		divStr="#berthdatagrid";
	}else{
		divStr="#editberthdatagrid";
	}
	$(divStr).datagrid({
		url : CONTEXT+'marketberth/marketberthdtl',
		nowrap: true, 					//设置信息在同一行显示(默认true)
		nowrap: false,					//是否只显示一行，即文本过多是否省略部分。
		striped: true,					//显示斑马线效果(默认false)
		nowrap: true, 					//设置信息在同一行显示(默认true)
		showHeader: true, 				//是否显示行头(默认true)
        showFooter: true, 				//是否显示行尾(默认false)
		sortName: "t.area,t.build,t.layer,t.berth",
		sortOrder: "asc",
		idField: "id",				//设置标识字段
		loadMsg:"拼命加载中 请稍后...",
		fitColumns:true,				//自动展开/收缩列(默认false,在列比较少的时候设置此属性) 
		fit:false,						//datagrid的宽高自适应
		pagination:true, 				//包含分页
		pageSize: 10,
		pageList: [10,20,50,100],		//可以设置每页记录条数的列表  
		rownumbers:true,
		singleSelect:true,
		queryParams:{marketId:marketId},
		frozenColumns : [[
			
		]],
		columns : [[
			{field : "area",title : "区",width : 100,align : "center",formatter:function(v,d,i){
				return v+"区"
			}},
			{field : "build",title : "栋",width : 100,align : "center",formatter:function(v,d,i){
				return v+"栋"
			}},
			{field : "layer", title : "层", width : 100, align : "center",formatter:function(v,d,i){
				return v+"层"
			}},
			{field : "berth", title : "铺位", width : 100, align : "center",formatter:function(v,d,i){
				return v+"号"
			}}
		]],
		toolbar:[],
		onLoadSuccess:function(data){
			if(data.result == "error"){
				$.messager.show({title:"提示信息",msg:data.msg,timeout:3000});
			}
		},onLoadError:function(data){
			$.messager.alert("提示信息","请求服务器失败!");
		}
	});	
}
var count = 0;
function addMax(){
	count++;
	$("#addMarketMax").append("<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+count+"].addArea' id='' style='width: 50px;' class='easyui-validatebox' data-options='required:true'/>&nbsp;&nbsp;区</div>" +
			"<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+count+"].addBuild' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;栋</div>" +
			"<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+count+"].addLayer' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;层</div>" +
			"<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+count+"].addBerth' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;铺</div>" +
			"<div style='width: 100%; height: 20px; clear: both;'></div>");
}
var editCount = 0;
function editMax(){
	editCount++;
	$("#editMarketMax").append("<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+editCount+"].addArea' id='' style='width: 50px;' class='easyui-validatebox' data-options='required:true'/>&nbsp;&nbsp;区</div>" +
			"<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+editCount+"].addBuild' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;栋</div>" +
			"<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+editCount+"].addLayer' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;层</div>" +
			"<div style='width: 80px; float: left; margin-right: 10px;'>" +
			"	<input name='marketBerthAddDTOs["+editCount+"].addBerth' id='' style='width: 50px;' class='easyui-numberbox' data-options='required:true'/>&nbsp;&nbsp;铺</div>" +
			"<div style='width: 100%; height: 20px; clear: both;'></div>");
}

