<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style type="text/css">
.rtd {
	text-align: right;
	width: 10%;
}

.ltd {
	text-align: left;
	width: 5%;
}

.rtd_wl {
	text-align: right;
	width: 15%;
}

.ltd_wl {
	text-align: left;
	width: 10%;
}
</style>
<div id="tb-edit" class="easyui-tabs"
	style="width: 786px; height: 400px;" data-options="border:false">
	<div title="订单信息">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable3">
			<tr>
				<td width="15%" style="text-align: right;">订单编号：</td>
				<td width="35%">${dto.orderNo }</td>
				<%-- <td>订单来源：</td>
				<td>${dto.orderSourceView }</td> --%>
				<td width="15%" style="text-align: right;">所属市场：</td>
				<td width="35%">${dto.marketRealName}</td>
			</tr>
			<tr>
				<td style="text-align: right;">订单状态：</td>
				<td>${dto.orderStatusView }</td>

				<td style="text-align: right;">状态词：</td>
				<td>${dto.statusWord}</td>
			</tr>
			<tr>
				<td style="text-align: right;">配送方式：</td>
				<td><c:if test="${not empty dto.distributeMode}">
				       ${dto.distributeMode=="1" ?"平台配送":"自提"}
				    </c:if> 
				    <c:if test="${empty dto.distributeMode}">
				                     自提
				    </c:if>
				</td>
				<td style="text-align: right;">买家留言：</td>
				<td>${dto.message }</td>
			</tr>
			<tr>
				<td style="text-align: right;">创建时间：</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
						value="${dto.orderTime}" /></td>
				<td style="text-align: right;">成交时间：</td>
				<td><c:if test="${dto.distributeMode=='1'}">
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${dto.paySerialnumberPosFinal.payTime}" />
					</c:if> <c:if test="${dto.distributeMode!='1'}">
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${dto.paySerialnumberPosAll.payTime}" />
					</c:if></td>
			</tr>
			<tr>
				<td style="text-align: right;">关闭时间：</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
						value="${dto.closeTime}" /></td>
				<td colspan="2"></td>
			</tr>
			<%-- <tr>
					<td style="text-align: right;">POS终端号：</td>
				<td>${dto.paySerialnumberDTO.posNumber }</td>
				<td colspan="2"></td>
			</tr>
			<tr>
					<td style="text-align: right;">支付卡号：</td>
				<td>${dto.paySerialnumberDTO.paymentAcc }</td>
					<td style="text-align: right;">交易流水号：</td>
				<td>${dto.paySerialnumberDTO.statementId }</td>
				</tr> --%>
		</table>

		<h2 class="bt-order-ct">&nbsp;商品信息</h2>
		<div class="bt-order-list">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable4">
				<tr>
					<th>序号</th>
					<th>商品名称</th>
					<th>商品分类</th>
					<th>采购量</th>
					<th>单价（元）</th>
					<th>小计1（元）</th>
					<th>其他费用1采购量</th>
					<th>单价</th>
					<th>小计2</th>
					<th>小计3</th>
				</tr>
				<c:forEach items="${dto.detailList}" var="detail" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${detail.productName}</td>
						<td>${detail.cateName}</td>
						<td><fmt:formatNumber value="${detail.purQuantity}" pattern="0.00" /><c:if test="${not empty detail.purQuantity}">${detail.unitName}</c:if></td>
						<td><fmt:formatNumber value="${detail.price}" pattern="0.00" /><c:if test="${not empty detail.price}">/${detail.unitName}</c:if></td>
						<td><fmt:formatNumber value="${detail.subTotal}" pattern="0.00" /></td>
						<td><fmt:formatNumber value="${detail.purQuantity1}" pattern="0.00" /><c:if test="${not empty detail.purQuantity1}">${detail.unitName}</c:if></td>
						<td><fmt:formatNumber value="${detail.price1}" pattern="0.00" /><c:if test="${not empty detail.price1}">/${detail.unitName}</c:if></td>
						<td><fmt:formatNumber value="${detail.subTotal1 }" pattern="0.00" /></td>
						<td><fmt:formatNumber value="${detail.price2 }" pattern="0.00" /></td>
					</tr>
				</c:forEach>
			</table>
		
		<h2 class="bt-order-ct">&nbsp;活动信息</h2>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable3">
				<tr>
					<td>活动名称:</td>
					<td>
						<c:if test="${dto.activityType == '1'}">
					                     无活动
					    </c:if>
						<c:if test="${dto.activityType == '2'}">
					                     现场采销
					    </c:if>
					</td>
					<td>活动积分:</td>
					<td>
						<c:if test="${dto.activityIntegral != null}">
							${dto.activityIntegral} 积分
						</c:if>
						<c:if test="${dto.activityIntegral == null}">
							0积分
						</c:if>
					</td>
				</tr>
			</table> 

			<!-- 买家买家付款信息 -->
			<h2 class="bt-order-ct">&nbsp;买家付款信息</h2>
			<div class="bt-order-list">
				<table width="100%" cellspacing="0" cellpadding="0" class="comtable3">
					<tr>
						<td style="text-align: right; width: 5%;"><b>商品</b></td>
						<td class="rtd">商品总价</td>
						<td class="ltd">
						<fmt:formatNumber type="number" value="${dto.payAmount}" pattern="0.00" maxFractionDigits="2"/>
						</td>
						<td class="rtd">商品预付款</td>
						<td class="ltd">
						<c:if test="${dto.distributeMode=='1'}">
							${dto.prepayAmt}
						</c:if>
						</td>
						<td class="rtd">商品尾款</td>
						<td class="ltd"><c:if test="${dto.distributeMode=='1'}">
								<fmt:formatNumber type="number"
									value="${dto.payAmount-dto.prepayAmt}" maxFractionDigits="2" />
							</c:if></td>
					</tr>
					<!-- <tr>
						<td style="text-align: right;"><b>物流</b></td>
						<td class="rtd">运费（卖家）</td>
						<td class="ltd"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr> -->
					<tr>
						<td style="text-align: right;"><b>佣金</b></td>
						<td class="rtd">买家平台佣金</td>
						<td class="ltd">${dto.buyerPlatformCommision}</td>
						<td class="rtd">买家市场佣金</td>
						<td class="ltd">${dto.buyerMarketCommision}</td>
						<%-- <td class="rtd">物流公司平台佣金</td>
						<td class="ltd">${dto.buyerLogisticsCommision}</td> --%>
						<td class="rtd"></td>
						<td class="ltd"></td>
					</tr>
					<tr>
						<td style="text-align: right;"><b>应付</b></td>
						<td class="rtd">订单总价</td>
						<td class="ltd">
						<fmt:formatNumber type="number" value="${dto.totalPayAmt }" pattern="0.00" maxFractionDigits="2"/>
						</td>
						<td class="rtd">预付款</td>
						<td class="ltd"><c:if test="${dto.distributeMode=='1'}">${dto.prepayAmt}</c:if></td>
						<td class="rtd">尾款</td>
						<td class="ltd"><c:if test="${dto.distributeMode=='1'}">
								<fmt:formatNumber type="number"
									value="${dto.totalPayAmt-dto.prepayAmt}" maxFractionDigits="2" />
							</c:if></td>

					</tr>
					<tr>
						<td style="text-align: right;"><b>违约</b></td>
						<td class="rtd">违约金（卖家）</td>
						<td class="ltd">${dto.buyerPenaltyToSeller}</td>
						<td class="rtd">违约金（物流公司）</td>
						<td class="ltd">${dto.buyerPenaltyToWLCompany }</td>
						<td class="rtd">违约金（平台）</td>
						<td class="ltd">${dto.buyerPenaltyToPlatform }</td>
					</tr>
					<tr>
						<td style="text-align: right;"><b>实付</b></td>
						<td class="rtd">实付款</td>
						<td class="ltd">
						<fmt:formatNumber type="number" value="${dto.totalPayAmt }" pattern="0.00" maxFractionDigits="2"/>
						</td>
						<td class="rtd">实付预付款</td>
						<td class="ltd">${dto.paySerialnumberAllipay.tradeAmount}</td>
						<td class="rtd">实付尾款</td>
						<td class="ltd">${dto.paySerialnumberPosFinal.tradeAmount}</td>
					</tr>

				</table>
				<!--END 买家买家付款信息 -->

				<!-- 卖家收款信息-->
				<h2 class="bt-order-ct">&nbsp; 卖家收款信息</h2>
				<div class="bt-order-list">
					<table width="100%" cellspacing="0" cellpadding="0"
						class="comtable3">
						<tr>
							<td style="text-align: right; width: 5%;"><b>佣金</b></td>
							<td class="rtd">卖家平台佣金</td>
							<td class="ltd">${dto.sellerPlatformCommision }</td>
							<td class="rtd">卖家市场佣金</td>
							<td class="ltd">${dto.sellerMarketCommision }</td>
							<td class="rtd">&nbsp;</td>
							<td class="ltd">&nbsp;</td>
						</tr>
						<tr>
							<td style="text-align: right;"><b>应收</b></td>
							<td class="rtd">应收款</td>
							<td class="ltd"><c:set var="receivableAmt"
									value="${dto.payAmount-dto.sellerPlatformCommision-dto.sellerMarketCommision}"></c:set>
								<fmt:formatNumber type="number" value="${receivableAmt}" pattern="0.00" maxFractionDigits="2"/>
								</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b>订单实收</b></td>
							<td class="rtd">实收款</td>
							<td class="ltd"><fmt:formatNumber type="number" value="${receivableAmt}" pattern="0.00" maxFractionDigits="2"/></td>
							<td class="rtd"></td>
							<td class="ltd"></td>
							<td class="rtd"></td>
							<td class="ltd"></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b>手续费</b></td>
							<td class="rtd">手续费</td>
							<td class="ltd">${clearingLog.feeAmt}</td>
							<td class="rtd"></td>
							<td class="ltd"></td>
							<td class="rtd"></td>
							<td class="ltd"></td>

						</tr>
						<tr>
							<td style="text-align: right;"><b>补贴</b></td>
							<td class="rtd">刷卡补贴</td>
							<td class="ltd">${clearingLog.subsidyAmt}</td>
							<td class="rtd"></td>
							<td class="ltd"></td>
							<td class="rtd"></td>
							<td class="ltd"></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b>实收</b></td>
							<td class="rtd">实收款</td>
							<td class="ltd">
							<fmt:formatNumber type="number" value="${clearingLog.orderAmt-clearingLog.commissionAmt-clearingLog.platCommissionAmt-clearingLog.feeAmt+clearingLog.subsidyAmt}" pattern="0.00" maxFractionDigits="2"/>
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td style="text-align: right;"><b>其他</b></td>
							<td class="rtd">违约金（卖家）</td>
							<td class="ltd">${clearingLog.penaltyAmt}</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<!--END 卖家收款信息-->
				</div>
			</div>
		</div>

	</div>
	
	<!-- 买卖家信息 -->
	<div title="买卖家信息">
		<h2 class="bt-order-ct">&nbsp;买家信息</h2>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">
			<tr>
				<td width="15%" style="text-align: right;">买家账号：</td>
				<td width="33%">${dto.account }</td>
				<td width="15%" style="text-align: right;">手机号码：</td>
				<td width="35%">${dto.buyerMobile}</td>
			</tr>
			<tr>

				<td width="15%" style="text-align: right;">买家姓名：</td>
				<td><c:if test="${empty dto.realName }">
					农商友用户
				</c:if> ${dto.realName }</td>
				<td colspan="2"></td>
			</tr>

		</table>
		<h2 class="bt-order-ct">&nbsp;卖家信息</h2>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">
			<tr>
				<td width="15%" style="text-align: right;">卖家账号：</td>
				<td width="35%">${dto.sellAccount }</td>
				<td width="15%" style="text-align: right;">手机号码：</td>
				<td width="35%">${dto.sellMobile }</td>
			</tr>
			<td width="15%" style="text-align: right;">商铺名称：</td>
			<td>${dto.shopName}</td>
			<td colspan="2"></td>
		</table>
	</div>
	<!--END 买卖家信息 -->
	
	<!--物流信息 -->
	<div title="物流信息">

		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">

			<tr>
				<td class="rtd_wl">货运订单：</td>
				<td class="ltd_wl">${dto.orderDelivery.nstOrderNo }</td>
				<td class="rtd_wl">配送方式：</td>
				<td class="ltd_wl"><c:choose>
						<c:when test="${dto.distributeMode=='0'}">自提</c:when>
						<c:when test="${dto.distributeMode=='1'}">平台配送</c:when>
						<c:when test="${dto.distributeMode=='2'}">商家配送</c:when>
					</c:choose></td>
				<td class="rtd_wl">物流状态：</td>
				<td class="ltd_wl">${logisticalProgress.status}</td>
			</tr>
			<tr>
				<td class="rtd_wl">重量：</td>
				<td class="ltd_wl">${logisticalProgress.totalWeight}</td>
				<td class="rtd_wl">收货人：</td>
				<td class="ltd_wl">${logisticalProgress.shipperName}</td>
				<td class="rtd_wl">联系电话：</td>
				<td class="ltd_wl">${logisticalProgress.shipperMobile}</td>
			</tr>

			<tr>
				<td class="rtd_wl">收货地：</td>
				<td class="ltd_wl">${logisticalProgress.e_detail}</td>
				<td class="rtd_wl">详细地址：</td>
				<td class="ltd_wl">${logisticalProgress.e_detailed_address}</td>
				<td class="rtd_wl"></td>
				<td></td>
			</tr>
			<tr>
				<td class="rtd_wl">物流公司：</td>
				<td class="ltd_wl">${logisticalProgress.companyName}</td>
				<td class="rtd_wl">手机号码：</td>
				<td class="ltd_wl">${logisticalProgress.companMobile}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="rtd_wl">司机姓名：</td>
				<td class="ltd_wl">${logisticalProgress.driverName}</td>
				<td class="rtd_wl">手机号码：</td>
				<td class="ltd_wl">${logisticalProgress.driverMobile}</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="rtd_wl">创建时间：</td>
				<td class="ltd_wl">${logisticalProgress.confirmOrderTime}</td>
				<td class="rtd_wl">完成时间：</td>
				<td class="ltd_wl">${logisticalProgress.confirmGoodsTime}</td>
				<td class="rtd_wl">关闭时间：</td>
				<td class="ltd_wl">${logisticalProgress.closeTime}</td>
			</tr>
		</table>
	</div>
	<!--END 物流信息 -->
	
	<!--支付信息 -->
	<div title="支付信息">
		<c:if test="${dto.distributeMode!='1' }">
			<h2 class="bt-order-ct">&nbsp;订单总价支付</h2>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="comtable3">

				<tr>
					<td class="rtd_wl">实付款：</td>
					<td style="width: 35%;">${dto.paySerialnumberPosAll.tradeAmount }</td>
					<td class="rtd_wl"></td>
					<td style="width: 35%;"></td>
				</tr>
				<tr>
					<td class="rtd_wl">支付状态：</td>
					<td class="ltd_wl"><c:choose>
							<c:when test="${dto.paySerialnumberPosAll.payStatus=='0'}">未支付</c:when>
							<c:when test="${dto.paySerialnumberPosAll.payStatus=='1'}">已支付</c:when>
							<c:when test="${dto.paySerialnumberPosAll.payStatus=='9'}">支付失败</c:when>
						</c:choose></td>
					<td class="rtd_wl">支付方式：</td>
					<td class="ltd_wl"><c:choose>
							<c:when test="${dto.paySerialnumberPosAll.payType=='1'}">钱包余额</c:when>
							<c:when test="${dto.paySerialnumberPosAll.payType=='2'}">线下刷卡</c:when>
							<c:when test="${dto.paySerialnumberPosAll.payType=='3'}">现金交易</c:when>
							<c:when test="${dto.paySerialnumberPosAll.payType=='4'}">支付宝</c:when>
							<c:when test="${dto.paySerialnumberPosAll.payType=='5'}">微信</c:when>
						</c:choose></td>

				</tr>
				<tr>
					<td class="rtd_wl">平台支付流水：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosAll.statementId}</td>
					<td class="rtd_wl">参考号：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosAll.thirdStatementId}</td>

				</tr>
				<tr>
					<td class="rtd_wl">终端号：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosAll.posNumber}</td>
					<td class="rtd_wl">支付银行：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosAll.payBank}</td>

				</tr>
				<tr>
					<td class="rtd_wl">支付卡号：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosAll.paymentAcc}</td>
					<td class="rtd_wl">支付时间：</td>
					<td class="ltd_wl"><fmt:formatDate
							value="${dto.paySerialnumberPosAll.payTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>

				</tr>

			</table>
		</c:if>
		<c:if test="${dto.distributeMode=='1' }">
			<h2 class="bt-order-ct">&nbsp;预付款支付</h2>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="comtable3">

				<tr>
					<td class="rtd_wl">预付款：</td>
					<td style="width: 35%;">${dto.prepayAmt}</td>
					<td class="rtd_wl"></td>
					<td style="width: 38%;"></td>
				</tr>
				<tr>
					<td class="rtd_wl">支付状态：</td>
					<td class="ltd_wl"><c:choose>
							<c:when test="${dto.paySerialnumberAllipay.payStatus=='0'}">未支付</c:when>
							<c:when test="${dto.paySerialnumberAllipay.payStatus=='1'}">已支付</c:when>
							<c:when test="${dto.paySerialnumberAllipay.payStatus=='9'}">支付失败</c:when>
						</c:choose></td>
					<td class="rtd_wl">支付方式：</td>
					<td class="ltd_wl"><c:choose>
							<c:when test="${dto.paySerialnumberAllipay.payType=='1'}">钱包余额</c:when>
							<c:when test="${dto.paySerialnumberAllipay.payType=='2'}">线下刷卡</c:when>
							<c:when test="${dto.paySerialnumberAllipay.payType=='3'}">现金交易</c:when>
							<c:when test="${dto.paySerialnumberAllipay.payType=='4'}">支付宝</c:when>
							<c:when test="${dto.paySerialnumberAllipay.payType=='5'}">微信</c:when>
						</c:choose></td>

				</tr>
				<tr>
					<td class="rtd_wl">平台支付流水：</td>
					<td class="ltd_wl">${dto.paySerialnumberAllipay.statementId}</td>
					<td class="rtd_wl">第三方支付流水：</td>
					<td class="ltd_wl">${dto.paySerialnumberAllipay.thirdStatementId}</td>

				</tr>
				<tr>
					<td class="rtd_wl">付款人账号：</td>
					<td class="ltd_wl">${dto.paySerialnumberAllipay.paymentAcc}</td>
					<td class="rtd_wl"><!-- 付款人姓名： --></td>
					<td class="ltd_wl"><%-- ${dto.realName} --%></td>

				</tr>
				<tr>
					<td class="rtd_wl">收款人账号：</td>
					<td class="ltd_wl">${dto.sellAccount}</td>
					<td class="rtd_wl">收款人姓名：</td>
					<td class="ltd_wl">${dto.sellRealName}</td>

				</tr>
				<tr>
					<td class="rtd_wl">支付时间：</td>
					<td class="ltd_wl"><fmt:formatDate
							value="${dto.paySerialnumberAllipay.payTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="rtd_wl"></td>
					<td class="ltd_wl"></td>

				</tr>

			</table>
			<h2 class="bt-order-ct">&nbsp;尾款（商品尾款+佣金）支付</h2>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="comtable3">

				<tr>
					<td class="rtd_wl">商品尾款：</td>
					<td style="width: 35%;">${dto.payAmount-dto.prepayAmt}</td>
					<td class="rtd_wl">佣金：</td>
					<td style="width: 35%;">${dto.buyerCommision }</td>
				</tr>
				<tr>
					<td class="rtd_wl">支付状态：</td>
					<td class="ltd_wl"><c:choose>
							<c:when test="${dto.paySerialnumberPosFinal.payStatus=='0'}">未支付</c:when>
							<c:when test="${dto.paySerialnumberPosFinal.payStatus=='1'}">已支付</c:when>
							<c:when test="${dto.paySerialnumberPosFinal.payStatus=='9'}">支付失败</c:when>
						</c:choose></td>
					<td class="rtd_wl">支付方式：</td>
					<td class="ltd_wl"><c:choose>
							<c:when test="${dto.paySerialnumberPosFinal.payType=='1'}">钱包余额</c:when>
							<c:when test="${dto.paySerialnumberPosFinal.payType=='2'}">线下刷卡</c:when>
							<c:when test="${dto.paySerialnumberPosFinal.payType=='3'}">现金交易</c:when>
							<c:when test="${dto.paySerialnumberPosFinal.payType=='4'}">支付宝</c:when>
							<c:when test="${dto.paySerialnumberPosFinal.payType=='5'}">微信</c:when>
						</c:choose></td>

				</tr>
				<tr>
					<td class="rtd_wl">平台支付流水：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosFinal.statementId}</td>
					<td class="rtd_wl">参考号：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosFinal.thirdStatementId}</td>

				</tr>
				<tr>
					<td class="rtd_wl">终端号：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosFinal.posNumber}</td>
					<td class="rtd_wl">支付银行：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosFinal.payBank}</td>

				</tr>
				<tr>
					<td class="rtd_wl">支付卡号：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosFinal.paymentAcc}</td>
					<td class="rtd_wl"><!-- 付款人姓名： --></td>
					<td class="ltd_wl">
					<%-- <c:if test="${not empty dto.paySerialnumberPosFinal}">${dto.realName}</c:if> --%>
					</td>

				</tr>
				<tr>
					<td class="rtd_wl">收款人账号：</td>
					<td class="ltd_wl">${dto.paySerialnumberPosFinal.recipientAcc}</td>
					<td class="rtd_wl"><!-- 收款人姓名： --></td>
					<td class="ltd_wl"><%-- <c:if test="${not empty dto.paySerialnumberPosFinal}">${dto.sellRealName}</c:if> --%></td>

				</tr>
				<tr>
					<td class="rtd_wl">支付时间：</td>
					<td class="ltd_wl"><fmt:formatDate
							value="${dto.paySerialnumberPosFinal.payTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="rtd_wl"></td>
					<td class="ltd_wl"></td>

				</tr>
			</table>
		</c:if>
	</div>
	<!--END 支付信息 -->

	<!--退款信息 -->
	<div title="退款信息">
		<h2 class="bt-order-ct">&nbsp;打给买家</h2>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">

			<tr>
				<td class="rtd_wl">退款编号：</td>
				<td style="width: 35%;">${dto.refundLog.refundNo}</td>
				<td class="rtd_wl">退款金额：</td>
				<td style="width: 35%;">${dto.refundLog.refundAmt}</td>
			</tr>
			<tr>
				<td class="rtd_wl">退款理由：</td>
				<td class="ltd_wl">${dto.refundLog.refundReason}</td>
				<td class="rtd_wl">退款状态：</td>
				<td class="ltd_wl"><c:choose>
						<c:when test="${dto.refundLog.status=='1'}">待退款</c:when>
						<c:when test="${dto.refundLog.status=='2'}">已退款</c:when>
						<c:when test="${dto.refundLog.status=='3'}">退款失败</c:when>
						<c:when test="${dto.refundLog.status=='4'}">已清算</c:when>
					</c:choose></td>
			</tr>
			<tr>
				<td class="rtd_wl">退款方式：</td>
				<td class="ltd_wl"><c:if
						test="${dto.refundLog.refundType=='1'}">
				              原路返回
				    </c:if></td>
				<td class="rtd_wl"></td>
				<td class="ltd_wl"></td>
			</tr>
			<%-- <tr>
				<td class="rtd_wl">退款人账号：</td>
				<td class="ltd_wl"><c:if test="${not empty dto.refundLog}">
				    ${dto.refundLog.refundUserId}
				</c:if></td>
				<td class="rtd_wl">退款人姓名：</td>
				<td class="ltd_wl"><c:if test="${not empty dto.refundLog}"></c:if></td>
			</tr> --%>
			<tr>
				<td class="rtd_wl">收款人账号：</td>
				<td class="ltd_wl"><c:if test="${not empty dto.refundLog}">${dto.paySerialnumberAllipay.paymentAcc}</c:if></td>
				<td class="rtd_wl">收款人姓名：</td>
				<td class="ltd_wl"><c:if test="${not empty dto.refundLog }">${dto.realName}</c:if></td>

			</tr>
			<tr>
				<td class="rtd_wl">创建时间：</td>
				<td class="ltd_wl">${dto.refundLog.createTime}</td>
				<td class="rtd_wl">退款时间：</td>
				<td class="ltd_wl">${dto.refundLog.refundTime}</td>

			</tr>

		</table>
	</div>
	<!--END 退款信息 -->

	<!--订单操作日志 -->
	<div title="订单操作日志">

		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">

			<tr>
				<td class="rtd_wl"></td>
				<td class="ltd_wl"></td>
				<td class="rtd_wl"></td>
				<td class="ltd_wl"></td>
				<td class="rtd_wl"></td>
				<td class="ltd_wl"></td>
			</tr>
		</table>
	</div>
	<!--END 订单操作日志 -->
</div>