<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<style>
td {
	padding-left: 10px;
}

.lt {
	text-align: left;
}

.rt {
	text-align: right;
}
.title{
font-weight: bold;font-size:15px;
}
</style>
<form id="save-form" method="post" action="npsPurchase/save">
	<br />
	<div style="width: 90%; margin: 0px auto;">
		<div><span class="title">&nbsp;&nbsp;&nbsp;询价信息</span>
				<span style="float:right;" class="title">编号：${purchaseDTO.id}</span>
			</div>
		<input type="hidden" id="id" name="id" value="${purchaseDTO.id}" />
		<table>

			<tr>
				<td class="rt">商品名称：</td>
				<td class="lt">${purchaseDTO.goodsName}</td>
			</tr>
			<tr>
				<td class="rt">商品图片：</td>
				<td class="lt"><c:if test="${not empty purchaseDTO.goodsImg}">
						<img alt="商品图片" src="${imgHostUrl}${purchaseDTO.goodsImg}" width="350" height="200" />
					</c:if></td>
			</tr>
			<tr>
				<td class="rt">采购量：</td>
				<td class="lt">${purchaseDTO.purchaseCount}${purchaseDTO.purchaseUnit}</td>
			</tr>
			<tr>
				<td class="rt">心理价位：</td>
				<td>${purchaseDTO.minPrice}~${purchaseDTO.maxPrice}</td>
			</tr>
			<tr>
				<td class="rt">提交时间：</td>
				<td><fmt:formatDate value="${purchaseDTO.createTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td class="rt">结束时间：</td>
				<td><fmt:formatDate value="${purchaseDTO.endTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td class="rt">采购时效：</td>
				<td>${purchaseDTO.purchaseCycle}天</td>
			</tr>
			<tr>
				<td class="rt">规格要求：</td>
				<td>${purchaseDTO.specRequire}</td>
			</tr>
			<tr>
				<td class="rt">指定产区：</td>
				<td>${purchaseDTO.areaName}</td>
			</tr>
			<tr>
				<td class="rt">其他要求：</td>
				<td>${purchaseDTO.remark}</td>
			</tr>
			<tr>
				<td class="rt">询价状态：</td>
				<td><c:choose>
						<c:when test="${purchaseDTO.status=='1'}">待审核 </c:when>
						<c:when test="${purchaseDTO.status=='2'}">审核通过 </c:when>
						<c:when test="${purchaseDTO.status=='3'}">已驳回 </c:when>
						<c:when test="${purchaseDTO.status=='4'}">用户撤销 </c:when>
						<c:when test="${purchaseDTO.status=='6'}">已结束 </c:when>
					</c:choose></td>
			</tr>
			<tr>
				<td class="rt">累计浏览次数：</td>
				<td>${purchaseDTO.visitCount}</td>
			</tr>
			<tr>
				<td class="rt">驳回原因：</td>
				<td>${purchaseDTO.rejectReason}</td>
			</tr>
			<tr>
				<td class="rt">发布时间：</td>
				<td><fmt:formatDate value="${purchaseDTO.releaseTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td class="rt">最近审核时间：</td>
				<td><fmt:formatDate value="${purchaseDTO.authTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td class="lt" colspan="2"><br/><span class="title">当前报价信息</span></td>
			</tr>


			<tr>
				<td></td>
				<td>
					
				</td>
			</tr>
			
		</table>
<table border='1' cellspacing="0" cellpadding="5"
						style="border-collapse: collapse;">
						<tr>
							<td>NO.</td>
							<td>手机号码</td>
							<td>询价编号</td>
							<td>上车价</td>
							<td>单位</td>
							<td>报价时间</td>
							<td>报价状态</td>
							<td>操作</td>
						</tr>
						<tr>
							<td id="showOfferId">${offerDTO.id}</td>
							<td>${offerDTO.mobilePhone}</td>
							<td>${offerDTO.purchaseId}</td>
							<td>${offerDTO.offerPriceStr}</td>
							<td>${purchaseDTO.purchaseUnit}</td>
							<td><fmt:formatDate value="${offerDTO.offerTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><c:choose>
									<c:when test="${offerDTO.status=='3'}"><span id="spStatus">隐藏</span> </c:when>
									<c:when test="${offerDTO.status=='2'}"><span id="spStatus">已删除</span> </c:when>
									<c:when test="${offerDTO.status=='1'}"><span id="spStatus">显示</span> </c:when>
								</c:choose></td>
							<td>
							
							<c:if test="${offerDTO.status=='3'}">
								<a id="linkHide" href="javascript:void(0)"  style="color:gray;">隐藏</a>
								<a id="linkShow" href="javascript:void(0)" onclick='updateOfferStatus(${offerDTO.id},1,${offerDTO.purchaseId})'>显示</a>
							</c:if>
							<c:if test="${offerDTO.status=='1'}">
								<a id="linkHide" href="javascript:void(0)"  onclick='updateOfferStatus(${offerDTO.id},3,${offerDTO.purchaseId})'>隐藏</a>
								<a id="linkShow" href="javascript:void(0)" style="color:gray;">显示</a> 
							</c:if>
							</td>
						</tr>
						
					</table>
					<br>
			<div calss="lt"> <a href="javascript:void(0)"
					onclick="getOfferPrice(1)">这条询价的全部报价信息</a>
			</div>
	</div>
</form>
<script type="text/javascript" charset="utf-8">
	var purchaseId = "${purchaseDTO.id}";
	
	function updateOfferStatus(id,status,purchaseId) {
		var url = CONTEXT + "npsPurchase/updateOffer";
		jQuery.post(url, {"id":id,"status":status,"purchaseId":purchaseId}, function(data) {
			if (data.msg == "success") {
				if(status==1){
					$("#linkShow").removeAttr("onclick").css("color","gray");
					$("#linkHide").attr("onclick","updateOfferStatus("+id+",3,"+purchaseId+")").removeAttr("style");
					$("#spStatus").html("显示");
				}
				else if(status==3){
					$("#linkHide").removeAttr("onclick").css("color","gray");
					$("#linkShow").attr("onclick","updateOfferStatus("+id+",1,"+purchaseId+")").removeAttr("style");
					$("#spStatus").html("隐藏");
				}
				slideMessage("保存成功！");
			} else {
				warningMessage(data.msg);
				return;
			}
		});
}
</script>
<script type="text/javascript" src="${CONTEXT}js/npsPurchase/edit.js"></script>
