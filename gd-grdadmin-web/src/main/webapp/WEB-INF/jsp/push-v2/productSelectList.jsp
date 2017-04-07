<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<table id="productdg" title="">
	</table>
	<div id="producttb" style="padding:5px;height:auto">
		<form id="productSearchForm" method="post">
		<div>
			<input type="hidden" id="categoryId" value="${categoryId}" />
			<input type="hidden" id="selectType" value="${selectType}" />
			商品名称: <input  type="text" id="productName_q" name="productName" style="width:150px" data-options="prompt:'请输入名称">&nbsp;
			用户帐号: <input  type="text" id="account_q" name="account" style="width:150px" data-options="prompt:'请输入用户账号">&nbsp;
			<br>
			商品产地 : 
			<input id="originProvinceId_comp" style="width:120px" class="easyui-combobox" data-options="editable:false">
			<input id="originCityId_comp" style="width:120px" class="easyui-combobox" data-options="editable:false">
			<input id="originAreaId_comp" style="width:120px" class="easyui-combobox" data-options="editable:false">
			<input type="hidden" id="originProvinceId" name="originProvinceId"/>
			<input type="hidden" id="originCityId" name="originAreaId"/>
			<input type="hidden" id="originAreaId" name="originAreaId"/>
			<br>
			所属市场: <select id="marketId3" name="marketId" style="width:150px"></select>&nbsp;
			商品类目: 
			<select id="productType4" style="width:100px"><option value="">请选择</option></select>
			<select id="productType5" style="width:100px"><option value="">请选择</option></select>
			<select id="productType6" style="width:100px"><option value="">请选择</option></select>
			<input type="hidden" id="categoryId7" name="categoryId">
			<br>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search2">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick="fromreset('productSearchForm');">重置</a>
		</div>
		</form>
	</div>

<script type="text/javascript">

function loadProList(params){
	params = !params ? {}: params;
	//数据加载
	$('#productdg').datagrid({
		url:CONTEXT+'product/list',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#producttb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'id',title:'',width:0,checkbox:true},
			{field:'productName',title:'商品名称',width:100,align:'center'},
			{field:'cateName',title:'商品类目',width:100,align:'center'},
			{field:'realName',title:'真实姓名',width:100,align:'center'},
			{field:'account',title:'会员账号',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center'},
			{field:'infoLifeDay',title:'信息有效期',width:100,align:'center'},
			{field:'state',title:'审核状态',width:100,align:'center', formatter: formatState},
			
			{field:'opt',title:'操作',width:100,align:'center',formatter:function(value,row,index){
				return "<a class='operate' href='javascript:;' onclick=productSelect('"+row.productId+"')>选择产品</a>";
			}}
		]]
	});
	//分页加载
	$("#productdg").datagrid("getPager").pagination({
		pageList: [50,100,200,300,500]
	});
}

function productSelect(id){
	var selectType = $("#selectType").val();
		$("#productId2").val(id);
		$("#proview2").html("已选产品:id:" + id);
// 		$("#adLinkUrl2").val("${webhomeUrl}product/"+id);
		$("#proview2").show();
		$("#proDialog").dialog('destroy');
}

function formatState(value,row,index){
	if (row.state == 0){
		return "草稿";
	}else if (row.state == 1){
		return "待审核";
	}else if (row.state == 2){
		return "审核不通过";
	}else if (row.state == 3){
		return "已上架";
	}else if (row.state == 4){
		return "已下架";
	}else if (row.state == 5){
		return "已删除";
	}else if (row.state == 6){
		return "历史版本";
	}
}

function fromreset(fromid){
	document.getElementById(fromid).reset();
}

$(document).ready(function(){
	$("#proview").hide();
	var paramsz = {
			"categoryId" : $("#categoryId").val(),
			"state":3
			};
	loadProList(paramsz);
	//查询按钮
	$('#icon-search2').click(function(){
		var params = {
			"productName" : $("#productName_q").val(),
			"categoryId" : $("#categoryId7").val(),
			"originProvinceId" : $("#originProvinceId").val(),
			"originCityId" : $("#originCityId").val(),
			"originAreaId" : $("#originAreaId").val(),
			"marketId" : $("#marketId3").val(),
			"state":3,
			"account":$("#account_q").val()
		};
		loadProList(params);
	});
});

function initMarket3(marketType,marketId){
	$.ajax({
		type: "GET",
		url: CONTEXT+"market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#marketId3').empty();
				$('#marketId3').append("<option value=''>请选择</option>");
				for ( var n = 0; n < markets.length; n++) {
					if(marketId==markets[n].id){
						$('#marketId3').append("<option selected value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}else{
						$('#marketId3').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}
				}
			}
			if(marketId==3){
				$('#marketId3').append("<option selected value='3'>产地供应商市场</option>");
			}else{
				$('#marketId3').append("<option value='3'>产地供应商市场</option>");
			}
		}
	});
}
initMarket3(2,null);
function initOriginPlaceQueryComp(){
	$.ajax({
		url: CONTEXT+'product/queryProvince',
		dataType: 'json', 
		success: function(jsonstr){
			jsonstr.unshift({
			'areaID':'0',
			'area':'请选择'
			});
			$('#originProvinceId_comp').combobox({
				data:jsonstr,
				valueField:'areaID',
				textField:'area',
				editable:false,
				onSelect:function(record){
					if(record.areaID == 0){
						$('#originProvinceId').val("");
						$('#originCityId').val("");
						$('#originAreaId').val("");
					}else{
						$('#originProvinceId').val(record.areaID);
					}
					updateOriginCity(record.areaID);
				},
				onLoadSuccess : function(){
					var data = $('#originProvinceId_comp').combobox('getData');
					$('#originProvinceId_comp').combobox('select', data[0].areaID);
				}
			});
		}
	});
}
function updateOriginCity(originProvinceId){
	$.ajax({
		url: CONTEXT+'product/queryCity/' + originProvinceId,
		dataType: 'json', 
		success: function(jsonstr){
			jsonstr.unshift({
			'areaID':'0',
			'area':'请选择'
			});
			$('#originCityId_comp').combobox({
				data:jsonstr,
				valueField:'areaID',
				textField:'area',
				editable:false,
				onSelect:function(record){
					if (record.areaID == 0){
						$('#originCityId').val("");
						$('#originAreaId').val("");
					}else{
						$('#originCityId').val(record.areaID);
					}
					updateOriginArea(record.areaID);
				},
				onLoadSuccess : function(){
					var data = $('#originCityId_comp').combobox('getData');
					$('#originCityId_comp').combobox('select', data[0].areaID);
				}
			});
		}
	});
}
function updateOriginArea(originCityId){
	$.ajax({
		url: CONTEXT+'product/queryArea/' + originCityId,
		dataType: 'json', 
		success: function(jsonstr){
			jsonstr.unshift({
			'areaID':'0',
			'area':'请选择'
			});
			$('#originAreaId_comp').combobox({
				data:jsonstr,
				valueField:'areaID',
				textField:'area',
				editable:false,
				onSelect:function(record){
					if (record.areaID == 0){
						$('#originAreaId').val("");
					}else{
						$('#originAreaId').val(record.areaID);
					}
				},
				onLoadSuccess : function(){
					var data = $('#originAreaId_comp').combobox('getData');
					$('#originAreaId_comp').combobox('select', data[0].areaID);
				}
			});
		}
	});
}
initOriginPlaceQueryComp();
$('#marketId3').change(function(){
	updatePtype1($(this).val(),null,$("#productType4"));
	updatePtype2(null,null,$("#productType5"));
	updatePtype2(null,null,$("#productType6"));
});

$("#productType4,#productType5,#productType6").change(function () {
	$("#categoryId7").val($(this).val());
	var id=$(this).attr("id");
	if(id=='productType4'){
	 	updatePtype2($(this).val(),null,$("#productType5"));
	 	updatePtype2(null,null,$("#productType6"));
	}else if(id=='productType5'){
	 	updatePtype2($(this).val(),null,$("#productType6"));
	}
});
</script>

