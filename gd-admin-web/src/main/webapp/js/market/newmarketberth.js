


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
			//{checkbox:true},
			{field : "marketName",title : "市场名称",width : 500,align : "center"}
		]],
		columns : [[
			{field : "updateTime",title : "更新时间",width : 100, sortable:true, align : "center"},
			{field : "updateUser", title : "修改人", width : 100, align : "center"},
			{field : "opt", title : "操作", width : 100, align : "center", formatter:function(v,r,i){
				return "<gd:btn btncode='BTNSCPWGL04'><a class='operate' href='javascript:void(0);' onclick=seeObj('"+r.marketId+"','"+r.marketName+"');>查看</a></gd:btn>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
						"<gd:btn btncode='BTNSCPWGL05'><a class='operate' href='javascript:void(0);' onclick=editObj('"+r.marketId+"','"+r.marketName+"');>编辑</a></gd:btn>";
			}},
			
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
		toolbar:[],
		onLoadSuccess:function(data){
			if(data.result == "error"){
				$.messager.show({title:"提示信息",msg:data.msg,timeout:3000});
			}
		},onLoadError:function(data){
			$.messager.alert("提示信息","请求服务器失败!");
		}
	});	
	
	
	$(".datagrid-toolbar").append($("#searchbox"));
	//$(".datagrid-toolbar table").before($(".toggle"));
	
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
		$.ajax({
			type : "post", // 以post方式与后台沟通
			url : CONTEXT+'marketberth/addmarketlist',
			dataType : "json",
			async : false,
			data: $('#addfm').serialize(),
			success : function(data) {
				//var json = eval('(' + data + ')');  
				if(data.result == "SUCCESS"){
					$('#adddlg').dialog('close');
					$.messager.show({title:'提示信息',msg:"操作成功",timeout:3000});
					$('#datagrid').datagrid('load');
				}else{
					$.messager.show({title:'提示信息',msg:data.msg,timeout:3000});
				}
			},error: function(){
				$.messager.alert('提示信息','请求服务器失败!');
			}
		});
	});
	//编辑
	$(".edit_submit_btn").click(function(){	
		$.ajax({
			type : "post", // 以post方式与后台沟通
			url : CONTEXT+'marketberth/editmarketlist',
			dataType : "json",
			async : false,
			data: $('#editfm').serialize(),
			success : function(data) {
				//var json = eval('(' + data + ')');  
				if(data.result == "SUCCESS"){
					$('#editdlg').dialog('close');
					$.messager.show({title:'提示信息',msg:"操作成功",timeout:3000});
					$('#datagrid').datagrid('load');
				}else{
					$.messager.show({title:'提示信息',msg:data.msg,timeout:3000});
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
		onClickCell: onClickCell,
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
			}},
			{field : "berthCode", title : "市场铺位编号", width : 100, align : "center",editor:'text'}
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

var editState=false;

function seeObj(marketId, marketName){
	//var row = $("#datagrid").datagrid("getSelected");
	//$('#fm').form('load',row);
	$('#seeMarketId').combobox('setValue', marketId);
	$('#seeMarketId').combobox('setText',marketName);
	$('#dlg').dialog('open').dialog('setTitle',"查看市场街区");
	$("#seeMarketId").combobox({ disabled:true });
	//加载当前已经拥有的市场街区
	queryMarketGroup(marketId,1);
	//加载市场街区明细
	berthdatagrid(marketId, 1);
	editState=false;
}



function editObj(marketId, marketName){
	//var row = $("#datagrid").datagrid("getSelected");
	//$('#editfm').form('load',row);
	$('#editMarketId_2').combobox('setValue', marketId);
	$('#editMarketId_2').combobox('setText',marketName);
	$('#editdlg').dialog('open').dialog('setTitle',"编辑市场街区");
	$("#editMarketId_2").combobox({ disabled:true });
	//加载当前已经拥有的市场街区
	queryMarketGroup(marketId,2);
	//加载市场街区明细
	berthdatagrid(marketId, 2);
	editState=true;
	$("#editMarketId").val(marketId);
}

function searchMarket(){
	$('#datagrid').datagrid('load',{
		marketId: $('#market').combobox('getValue')
	});
	disableExport=false;
}

function addMarket(){
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

function reloadMarket(){
	$('#datagrid').datagrid('load',{});
	var data = $('#market').combobox('getData');
	$("#market").combobox('select',data[0].id);
	disableExport=false;
}

/**
 * 更新市场铺位编号
 * @author lidong dli@gdeng.cn
 * @time 2016年8月17日 下午3:56:16
 */
var updateCode=function(id,berthCode){
	$.ajax({
		type:'post',
		data:{
			"id":id,
			"berthCode":berthCode
		},
		url:CONTEXT+'marketberth/updateMarketBerthCode',
		success: function (data) {
			slideMessage(data.msg);
			if (data.result == "success") {
				
			} else {
				return;
			}
      }
	});
}

/**=========================extend begin========================================**/
/**
 * 重写datagrid编辑事件，给单元格加上点击编辑事件
 * @author lidong dli@gdeng.cn
 * @time 2016年8月17日 下午3:56:16
 */

$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

/**
 * 只对市场铺位编号单元格进行编辑
 * @param index 行数
 * @param field 字段名
 * @author lidong
 */
var editing=false;//是否是编辑模式  true:是
var editRowNum=-1;//正在编辑的行数
var sberthCode="";//berthCode原始数据
function onClickCell(index, field, value){
	if(!editState){
		return;//非编辑状态,点击不触发事件,该字段值由列表页面点击"编辑"或"查看"赋值
	}
	if(!editing){
		if('berthCode' == field){
			editing=true;
			editRowNum=index;
			$('#editberthdatagrid').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
			var row = $('#editberthdatagrid').datagrid('getData').rows[editRowNum]
//			console.log("开启编辑模式");
			sberthCode=row.berthCode;//记录原始数据
		}
	} else {
		if('berthCode' == field){
			if(editRowNum == index){
				
			} else {
//				console.log("click berthCode列保存数据");
				$('#editberthdatagrid').datagrid('endEdit', editRowNum);
				var row = $('#editberthdatagrid').datagrid('getData').rows[editRowNum]
//				console.log(row);
//				console.log(row.id);
//				console.log(row.berthCode);
				var dberthCode=row.berthCode;
				//保存数据.对比原始数据发生变化则执行
				if(sberthCode!=dberthCode){
					updateCode(row.id, dberthCode);
					sberthCode="";
				}
				editing=false;
				editRowNum=-1;//
			}
		}else {
//			console.log("click 非berthCode列保存数据");
			$('#editberthdatagrid').datagrid('endEdit', editRowNum);
			var row = $('#editberthdatagrid').datagrid('getData').rows[editRowNum]
//			console.log(row);
//			console.log(row.id);
//			console.log(row.berthCode);
			var dberthCode=row.berthCode;
			//保存数据
			if(sberthCode!=dberthCode){
				updateCode(row.id, dberthCode);
				sberthCode="";
			}
			//保存数据
			editing=false;
			editRowNum=-1;//
		}
	}
}


/**=========================extend end========================================**/
