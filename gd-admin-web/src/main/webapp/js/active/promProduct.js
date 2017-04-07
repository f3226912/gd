

$.extend($.fn.validatebox.defaults.rules, {
	floatPoint: {    
        validator: function(value, param){

        	return /^\d+$/.test(value)|| /^[0-9]\d*\.\d{1,2}$/.test(value);

        },    
        message: '请输入正确的数字，最多两位小数'
    },
}); 

var pctrl = new Contrl();
$(function(){
	pctrl.initEvent();
	pctrl.initTable();
	//默认查询
	pctrl.query();
});

function Contrl(){
	
	this.initEvent=function(){
		//查询事件url:CONTEXT+'active/query',
		$('#prodSearch').click(function(){
			pctrl.query();
		});
		//保存事件
		$("#btn-saveProd").click(function(){
			var data = $("#proForm").serialize();
			data = data+"&actId="+$.trim($("#actId").val());
			$.ajax({
				type : "post",
				url : CONTEXT+"active/saveProduct",
				data : data,
				dataType : "json",
				success : function(data){
			    	if(data.msg){
			    		$.messager.alert("错误信息",data.msg,"error");
			    	} else {
			        	$.messager.show({title:"提示信息",msg:"保存成功",timeout:3000,showType:'slide'});
			        	//reload 当前页
			        	$('#ppdg').datagrid("reload");
			    	}
				}
			});
		});
		
		$("#btn-exportProduct").click(function(){
			//绑定导出事件
			if(flag == 1){
				alert("没有数据");
			} else {
				$("#if2").attr("src",CONTEXT+"active/exportProduct?actId="+$.trim($("#actId").val()));
			}
		});
	}
	
	this.query=function(){
		var params = {
				"supplierName":$("#pptb input[name='supplierName']").val(),
				"prodName":$("#pptb input[name='prodName']").val(),
				"actId":$.trim($("#actId").val())
				//"actId":1
			};
			$('#ppdg').datagrid("options").url=CONTEXT+'active/queryProduct';
			$('#ppdg').datagrid("reload",params);
			
	}
	
	this.initTable=function(){
		$('#ppdg').datagrid({
			queryParams: {},
			height: 'auto', 
			nowrap:true,
			toolbar:'#pptb',
			pageSize:50,
			rownumbers:true,
			pagination:true,
			singleSelect:true,
			fitColumns:true,
			fit:true,
			onLoadSuccess:function(){
				$("#ppdg").datagrid('clearSelections');
				//初始化校验
				$("#proForm").find("input[name$='actPrice']").validatebox({
					required: true,
					validType: 'floatPoint'
				});
				
			},
			columns:[[
				{field:'supplierName',title:'供应商名称',width:100,align:'center'},
				{field:'prodName',title:'商品名称',width:100,align:'center',formatter:pctrl.formatterProduct},
				{field:'stockCount',title:'库存',width:60,align:'center'},
				{field:'unitName',title:'单位',width:60,align:'center'},
				{field:'price',title:'正常价格',width:80,align:'center'},
				{field:'actPrice',title:'活动价',width:80,align:'center',formatter:pctrl.formatterActPrice},
				{field:'auditStauts',title:'是否参加活动',width:100,align:'center',formatter:pctrl.formatterAudit},
				{field:'memberName',title:'操作人',width:100,align:'center'}
				
			]]
		}); 

		
	}
	
	this.formatterActPrice=function(value,row,index){
		if(row.auditStatus == 3){
			return "<input type='hidden' name='promProdInfoList["+index+"].auditId' value='"+row.auditId+"'/>"+
			"<input type='hidden' name='promProdInfoList["+index+"].id' value='"+row.id+"'/>"+
			 "<input type='text' disabled style='width:70px' name='promProdInfoList["+index+"].actPrice' value='"+row.actPrice+"'/>";
		}
		return "<input type='hidden' name='promProdInfoList["+index+"].auditId' value='"+row.auditId+"'/>"+
		"<input type='hidden' name='promProdInfoList["+index+"].id' value='"+row.id+"'/>"+
		 "<input type='text' style='width:70px' name='promProdInfoList["+index+"].actPrice' value='"+row.actPrice+"'/>";
	}
	
	this.formatterAudit=function(value,row,index){
		if(row.auditStatus == 1){
			return "<input type='checkbox' name='promProdInfoList["+index+"].auditStatus' checked value='1'/>";
		} else if(row.auditStatus == 2 || row.auditStatus == 0){
			return "<input type='checkbox' name='promProdInfoList["+index+"].auditStatus' value='1'/>";
		} else if(row.auditStatus == 3) {
			return "<input type='checkbox' name='auditStatus' disabled/>"+
			"<input type='hidden' name='promProdInfoList["+index+"].auditStatus'  value='3'/>";
		}
		
	}
	
	this.formatterProduct=function(value,row,index){
		return "<a href='javascript:productDetail("+row.prodId+")'>"+row.prodName+"</a>";
	}
	
}

function productDetail(productId){
	window.parent.openTab(CONTEXT+'product/toDetail/'+productId+'?source=act','商品详情');
}