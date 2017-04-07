<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<div class="bt-order">

	<h2 class="bt-order-bigt">订单详情</h2>
	<div class="clearfix bt-order-ct">
		<span class="bt-order-t">订单编号：${dto.orderNo } [<span
			class="f-c-068139">${dto.orderStatus }</span>]
		</span><span class="bt-order-time">订单时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.orderTime}" /> </span>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable1">
		<tr>
			<td>付款方式：${dto.payType }</td>
			<td>订单金额：<fmt:formatNumber value="${dto.orderAmount }" pattern="#0.##" /></td>
			<td></td>
			<td>订单创建人账号：${dto.account }</td>
		</tr>
	</table>
	
	<h2 class="bt-order-ct">采购单：</h2>
	<div class="bt-order-list">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable2">
			<tr>
				<th>序号</th>
				<th>产品名称</th>
				<th>采购数量</th>
				<th>单价</th>
				
				<th>买家补贴额</th>
				<th>卖家补贴额</th>
				<th>供应商补贴额</th>
				
				<th>补贴申请时间</th>
				<th>需付款金额</th>
			</tr>
			<c:forEach items="${dto.detailList}" var="detail" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${detail.productName}</td>
					<td>${detail.purQuantity}</td>
					<td><fmt:formatNumber value="${detail.price}" pattern="#0.##" /></td>
					
					<td><fmt:formatNumber value="${detail.subAmount}" pattern="#0.##" /></td>
					<td><fmt:formatNumber value="${detail.sellSubAmount}" pattern="#0.##" /></td>
					<td><fmt:formatNumber value="${detail.suppSubAmount}" pattern="#0.##" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.createTime}" /></td>
					<td><fmt:formatNumber value="${detail.needToPayAmount}" pattern="#0.##" /></td>
				</tr>
			</c:forEach>
		</table>
		<div class="bt-order-tal">
			<span class="pr40">
			
	
			&nbsp;抵扣金额:<fmt:formatNumber value="${dto.discountAmount }" pattern="#0.##" />
			</span>合计需付款金额:<fmt:formatNumber value="${dto.payAmount}" pattern="#0.##" />元
		</div>
		
		<div>
		
		<table>
			<tr>
				<td>合计补贴:</td>
				<td>
					<c:if test="${(dto.subAmount + dto.sellSubAmount + dto.suppSubAmount)!=subAmount}">
						<font color="red"><fmt:formatNumber value="${subAmount}" pattern="#0.##" /></font>元
					</c:if>
					<c:if test="${(dto.subAmount + dto.sellSubAmount + dto.suppSubAmount)==subAmount}">
						<fmt:formatNumber value="${subAmount}" pattern="#0.##" />元
					</c:if>
				</td>
				<td>${subStatusStr }</td>
			</tr>
			<tr>
				<td>买家合计补贴:</td>
				<td><c:if test="${dto.subAmount==null }">0.0</c:if><fmt:formatNumber value="${dto.subAmount }" pattern="#0.##" />元</td>
				<td>
				${dto.buySubStatus }</td>
			</tr>
			<tr>
				<td>卖家合计补贴:</td>
				<td><c:if test="${dto.sellSubAmount==null }">0.0</c:if><fmt:formatNumber value="${dto.sellSubAmount }" pattern="#0.##" />元</td>
				<td>${dto.sellSubStatus }</td>
			</tr>
			<tr>
				<td>供应商合计补贴:</td>
				<td><c:if test="${dto.suppSubAmount==null }">0.0</c:if><fmt:formatNumber value="${dto.suppSubAmount }" pattern="#0.##" />元</td>
				<td>${dto.suppSubStatus }</td>
			</tr>
		</table>
		</div>
	</div>
	
		<h2 class="bt-order-ct">订单付款凭证相片</h2>
		<c:if test="${not empty dto.paySerialnumberDTO && not empty dto.paySerialnumberDTO.payImage && dto.paySerialnumberDTO.payImage ne ''}">
			<div class="pz-img clearfix">
		    	<span class="pz-img-t">
		    		银行流水号：
		    	</span>
		    	<div class="pz-img-item clearfix">
		    		<c:set value="${fn:split(dto.paySerialnumberDTO.payImage,',')}" var="payImage" />
			    	<c:forEach items="${payImage}" var="pi" varStatus="status">
			    		<div class="pz-img-box">
				     		<a href="${imgHostUrl}${pi}" target="_blank"><img src="${imgHostUrl}${pi}" width="182" height="110"></a>
				     		<br/>
				     		<c:choose>
								<c:when test="${dto.orderStatus == '已付款' && dto.examineStatus == '待审核'}">
									<input type="text" name="statementId" class="input-text input-order easyui-validatebox" validType="number" required="true"  missingMessage="银行流水号不能为空">
								</c:when>
								<c:otherwise>
									<input type="text" name="statementId" readonly="readonly" value="${fn:split(dto.paySerialnumberDTO.statementId,',')[status.index]}" class="input-text input-order">
								</c:otherwise>
							</c:choose>
			    		</div>
			    	</c:forEach>
		    	</div>
		    </div>
	    </c:if>
	    
	    
	    <h2 class="bt-order-ct">车辆照片</h2>
	    <c:if test="${not empty outmarketImage && not empty outmarketImage.carNumberImage}">
	    	<div class="pz-img clearfix">
	    		<c:set value="${fn:split(outmarketImage.carNumberImage,'|')}" var="carImage" />
	    		<c:forEach items="${carImage}" var="ci" varStatus="status">
	    			<div class="pz-img-box">
	    				<a href="${imgHostUrl}${ci}" target="_blank"><img src="${imgHostUrl}${ci}" width="182" height="110"></a>
	    			</div>
	    		</c:forEach>
	    	</div>
		    车牌号：<input type="text" readonly="readonly" value="${outmarketImage.carNumber}" class="input-text input-order">
	    </c:if>
	    
</div>


<h2 class="bt-order-ct">备注：</h2>
		<c:forEach items="${dto.auditLogList}" var="auditLog" varStatus="status">
			<div class="bt-notice clearfix bt-notice-wrong">
				<span>${auditLog.description} </span> 
				<span>${auditLog.auditUserName}</span> 
				<span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${auditLog.auditTime}" /></span>
			</div>
		</c:forEach>
		<c:if test="${dto.orderStatus == '已付款'}">
			<h3 class="bh-tit">驳回原因：</h3>
			<p class="bh-reason">
				<label class="leb-txt2">
					<input class="bh-reason-chk"value="流水与订单金额不符" type="checkbox" name="description">流水与订单金额不符
					<input class="bh-reason-chk"value="照片不符" type="checkbox" name="description">照片不符
				</label>
			</p>
		</c:if>

	<form id="verifyForm" method="post">
	<div>
		<textarea rows="3" cols="100" id="subComment_" name="subComment" class="easyui-validatebox" data-options="validType:'length[0,64]'"></textarea>
		<input type="hidden" id="auditId_" name="auditId" value="${auditId}"/><br/>
		<input type="hidden" id="subStatus_" name="subStatus" value="${subStatus}">
	</div>
	</form>


<script type="text/javascript" >
	if($('#subStatus').val()=='3'){
		// 不需要对已补贴订单进行审批
		$('#verifyForm').hide();
		$('#dlg-buttonsEdit').hide();
	}else{
		$('#verifyForm').show();
		$('#dlg-buttonsEdit').show();
	}
	

</script>











