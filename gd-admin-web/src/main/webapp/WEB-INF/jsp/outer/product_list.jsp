<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="gd" uri="http://www.gdeng.cn/gd"%>

<div>
	<table id="productListdg" title="">
	</table>
	<div id="productListtb" style="padding:5px;height:auto">
		
	</div>
	<script type="text/javascript">
		var id = ${id };
		
		$(document).ready(function() {
			$('#productListdg').datagrid({
				url:CONTEXT+'outer/showProductList/'+id,
				height: 220, 
				nowrap:true,
				toolbar:'productListtb',
				pageSize:10,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				pageList:[10,20,50,100],
				columns:[[
					{field:'productName',title:'产品名称',align:'center'},
					{field:'purQuantity',title:'采购数量',width:90,align:'center'},
					{field:'price',title:'单价',width:90,align:'center',formatter:priceFormatter},
					{field:'tradingPrice',title:'交易价',width:90,align:'center',formatter:tradingPriceFormatter},
					{field:'needToPayAmount',title:'需付款金额（元）',width:90,align:'center'}
				]]
			});
		});
		
		function priceFormatter(v,r) {
			return r.price.toFixed(2) + "/" + r.unit;
		}
		
		function tradingPriceFormatter(v,r) {
			return r.tradingPrice.toFixed(2) + "/" + r.unit;
		}
	</script>
</div>