<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
	<table>
		<!-- <tr>
			<td>
				<b>皮重登记时间：</b>
			</td>
			<td>
				<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.tareCreateTime }" />
			</td>
		</tr> -->
		<tr>
			<td>
				<b>出场登记时间：</b>
			</td>
			<td>
				<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.totalCreateTime }" />
			</td>
		</tr>
		<tr>
			<td>
				<b>用户账号:</b>
			</td>
			<td>
				${dto.account }
			</td>
		</tr>
		<tr>
			<td>
				<b>姓名：</b>
			</td>
			<td>
				${dto.memberName }
			</td>
		</tr>
	</table>
	<hr/>
	<!-- <table>
		<tr>
			<td>
				一天出场次数：
			</td>
			<td>
				${counts.dailySubOutmarket }
			</td>
		</tr>
		<tr>
			<td>
				一周出场次数：
			</td>
			<td>
				${counts.weeklySubOutmarket }
			</td>
		</tr>
		<tr>
			<td>
				一月出场次数：
			</td>
			<td>
				${counts.monthlySubOutmarket }
			</td>
		</tr>
	</table> -->
	<hr/>
</div>

<div>
	<b>订单信息：</b>
	<br />
	<c:if test="${not empty dto.orders}">
		<c:forEach items="${dto.orders }" var="order">
			订单编号:${order.orderNo }
			<div>
				<table id="productListdg_${order.orderNo }" title="">
				</table>
				<div id="productListtb_${order.orderNo }" style="padding:5px;height:auto">
				</div>
			</div>
			<script>
				function purFormatter(v,r) {
					return v.toFixed(2) + " " + r.unit;
				}
				
				function priceFormatter(v,r) {
					return v.toFixed(2) + "元/" + r.unit;
				}
				
				function tradingPriceFormatter(v,r) {
					return v.toFixed(2) + "元/" + r.unit;
				}
				
				function needToPayAmountPriceFormatter(v,r) {
					return v.toFixed(2);
				}
				
				$('#productListdg_${order.orderNo }').datagrid({
					url:CONTEXT+'outer/showProductList/${order.orderNo }',
					height: 220, 
					nowrap:true,
					toolbar:'productListtb_${order.orderNo }',
					pageSize:10,
					rownumbers:true,
					pagination:true,
					fitColumns:true,
					pageList:[10,20,50,100],
					columns:[[
						{field:'productName',title:'产品名称',align:'center'},
						{field:'purQuantity',title:'采购数量',width:90,align:'center',formatter:purFormatter },
						{field:'price',title:'单价',width:90,align:'center',formatter:priceFormatter},
						{field:'tradingPrice',title:'交易价',width:90,align:'center',formatter:tradingPriceFormatter},
						{field:'needToPayAmount',title:'需付款金额（元）',width:90,align:'center',formatter:needToPayAmountPriceFormatter}
					]]
				});
			</script>
		</c:forEach>
	</c:if>
	<c:if test="${empty dto.orders}">
		采购商入场无订单	<hr/>
	</c:if>
</div>
<div>
	<b>车辆拍照：</b>
	<br/>
	<c:if test="${not empty carNumberImages }">
		<c:forEach items="${carNumberImages }" var="image">
			<img alt="" src="${imgHostUrl }${image}" width="200" height="200">
		</c:forEach>
	</c:if>
	<c:if test="${empty carNumberImages }">
		暂无图片!
	</c:if>
	<hr/>
	<br/>
</div>
<div>
	<c:if test="${not empty dto.carNumber }">
		已填写车牌号：${dto.carNumber }（空为未填写）
	</c:if>
	<c:if test="${empty dto.carNumber }">
		<form id="addForm" method="post" action="outer/changeCar">
			<input type="hidden" id="wcId" name="wcId" value="${dto.weighCarId }">
			<input type="hidden" id="carId" name="carId" value="${dto.carId }">
			<br/>
			填写车牌号
			<select id="carCity" name="carCity">
				<option value="鄂">鄂</option>
				<option value="京">京</option>
				<option value="津">津</option>
				<option value="冀">冀</option>
				<option value="晋">晋</option>
				<option value="蒙">蒙</option>
				<option value="辽">辽</option>
				<option value="吉">吉</option>
				<option value="黑">黑</option>
				<option value="沪">沪</option>
				<option value="苏">苏</option>
				<option value="浙">浙</option>
				<option value="皖">皖</option>
				<option value="闽">闽</option>
				<option value="赣">赣</option>
				<option value="鲁">鲁</option>
				<option value="豫">豫</option>
				<option value="湘">湘</option>
				<option value="粤">粤</option>
				<option value="桂">桂</option>
				<option value="琼">琼</option>
				<option value="渝">渝</option>
				<option value="川">川</option>
				<option value="贵">贵</option>
				<option value="云">云</option>
				<option value="藏">藏</option>
				<option value="陕">陕</option>
				<option value="甘">甘</option>
				<option value="青">青</option>
				<option value="宁">宁</option>
				<option value="新">新</option>
			</select>
			<input type="text" id="carNumber" name="carNumber" value="" maxlength="6" />
		
		</form>
	</c:if>
	<!-- <b>出入场车辆车牌： </b>${dto.carNumber }&nbsp;&nbsp;
	
	<c:if test="${not empty dto.carType }">
		<b>手动选择车型 ：</b>被选择的&nbsp;${dto.carType }&nbsp;&nbsp;
		<b>评判标准:</b>
		<c:if test="${dto.weighType=='1' }">
			明显少量
		</c:if>
		<c:if test="${dto.weighType=='2' }">
			低于半车
		</c:if>
		<c:if test="${dto.weighType=='3' }">
			大概半车
		</c:if>
		<c:if test="${dto.weighType=='4' }">
			大概满车
		</c:if>
		<c:if test="${dto.weighType==null }">
			未选择
		</c:if>
	</c:if>
	
	<br/>
	
	<b>皮重:</b> &nbsp;&nbsp;&nbsp;<fmt:formatNumber type="number" value="${dto.tare }" pattern="0.00" maxFractionDigits="2"/>&nbsp;吨&nbsp;&nbsp;&nbsp;
	
	<b>总重:</b>&nbsp;&nbsp;&nbsp;
	
	<c:if test="${dto.totalWeight!=null }">
		<fmt:formatNumber type="number" value="${dto.totalWeight }" pattern="0.00" maxFractionDigits="2"/>
	</c:if>
	<c:if test="${dto.totalWeight==null }">
		尚未过磅
	</c:if>
	
	&nbsp;吨&nbsp;&nbsp;&nbsp;
	
	<b>净重:</b>&nbsp;&nbsp;&nbsp;
	
	<c:if test="${dto.netWeight!=null }">
		<fmt:formatNumber type="number" value="${dto.netWeight }" pattern="0.00" maxFractionDigits="2"/>
	</c:if>
	<c:if test="${dto.netWeight==null }">
		尚未过磅
	</c:if>
	
	&nbsp;吨&nbsp;&nbsp;&nbsp;
	<!-->
	<br/>
	<c:if test="${not empty dto.orderWeigh }">
		<b>订单总重</b>（<fmt:formatNumber type="number" value="${dto.orderWeigh }" pattern="0.00" maxFractionDigits="2"/>吨）
	</c:if>
	<!-- 
	<br/>
	<b>系统允许正负10%的误差</b>
	<br/>
	<hr/>
	<br/>
	<c:if test="${not empty dto.netWeight }">
	
	<b>误差区间（吨）：</b><fmt:formatNumber type="number" value="${dto.orderWeigh==null?0:(dto.orderWeigh - dto.orderWeigh*0.1) }" pattern="0.00" maxFractionDigits="2"/> ~ <fmt:formatNumber type="number" value="${dto.orderWeigh==null?0:(dto.orderWeigh + dto.orderWeigh*0.1) }" pattern="0.00" maxFractionDigits="2"/> 
	</c:if>
	
	
	<c:if test="${type==1 }">
		门岗人员：${dto.tareMember }
	</c:if>
	-->
	
	门岗人员：${dto.totalMember }
</div>
<div id="showProDialog" class="easyui-dialog" closed="true" modal="true" buttons="#dlg-buttonsProEdit">
		<div id="dlg-buttonsProEdit" style="text-align:center">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="$('#showProDialog').dialog('close');">关闭</a>
	    </div>
	</div>
<script>
function showProDetail(id){
	$('#showProDialog').dialog({'title':'订单详细','href':CONTEXT+'outer/showProduct/'+id,'width': 560,'height': 300}).dialog('open');
}

</script>
