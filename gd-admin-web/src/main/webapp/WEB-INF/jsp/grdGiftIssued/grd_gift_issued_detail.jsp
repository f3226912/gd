<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../pub/constants.inc" %>

<style type="text/css">
	.dialogMainDiv{border-top: thin solid #d3d3d3;text-align: center;min-height: 99%;overflow: visible;}
	.dialogMainDiv table{border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;}
	.dialogMainDiv th , .dialogMainDiv td{border-right:1px solid #888;border-bottom:1px solid #888;line-height: 25px;font-weight: normal;}
	.dialogMainDiv th{background:#efefef;}
	.dialogMainDiv .impcel{background:#efefef;width: 112px;}
	.multPic-ul {margin:2px auto;width: 156px;height: 80px;}
</style>

<div class="dialogMainDiv">
	<input id="popWinRecordId" type="hidden" value="${requestScope.grdGiftRecord.id }" />

	<div style="width:90%;font-size: 25px;margin:0px auto 5px auto;border-bottom: thin solid #E4E4E4;">
		<span style="margin-right: 5%;">所属市场：${requestScope.grdGiftRecord.marketName }</span>
		<span style="margin-right: 5%;">所属仓库：${requestScope.grdGiftRecord.giftstoreName }</span>
		<span style="margin-right: 5%;">地推：${requestScope.grdGiftRecord.createUser }</span>
		<span style="margin-right: 5%;">礼品发放人：${requestScope.grdGiftRecord.grantUser }</span>
	</div>
	<div style="width:85%;margin:0px auto;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:3px;">
			<tr>
				<td class="impcel">发放的礼品</td>
				<td>
					<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="border: none;">
						<tr>
							<th width="10%">序号</th>
							<th width="20%">礼品编号</th>
							<th width="50%">礼品名称</th>
							<th width="20%" style="border-right:none">数量</th>
						</tr>
						<c:forEach items="${requestScope.grdPresentDetils}" var="detail" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td align="left">${detail.giftNo}</td>
								<td align="left">${detail.giftName}</td>
								<td data-role="popWinGiftCount">${detail.countNo}</td>
							</tr>
						</c:forEach>
						<tr><td colspan="4" style="border:none;" align="right">合计 ：<span id="popGiftCountSum" style="margin-right: 50px;"></span></td></tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="impcel">兑换礼品的订单</td>
				<td>
					<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="border: none;">
						<tr>
							<th  width="10%">序号</th>
							<th  width="20%">订单号</th>
							<th  width="50%">交易时间</th>
							<th  width="20%" style="border-right:none">金额</th>
						</tr>
						<c:forEach items="${requestScope.grdOrderDetails}" var="detail" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td align="left">${detail.orderNo}</td>
								<td align="left"><fmt:formatDate value="${detail.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td align="right"  data-role="popWinGiftPrice"><fmt:formatNumber value="${detail.orderPrice}" pattern="0.00" /></td>
							</tr>
						</c:forEach>
						<tr><td colspan="4" style="border:none" align="right">合计 ： ￥ <span id="popGiftPriceSum" style="margin-right: 30px;"></span></td></tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="impcel">车辆图片</td>
				<td>
					<table width="100%" border="0" height="100%"  cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<div class="user-iteam">
							        <div class="fl mr_10">
							            <input type="file" class="g-u input01" id="uploadFirstBtn" value="" name="carPicture" />
							            <input type="hidden" id="urlWshowImgFirst" name="urlWshowImgFirst"  data-role="carPicturePath"/>
							        </div>
							        <div class="form-context">
							            <div class="clear"></div>
							            <ul id="pictureShowFirst" class="multPic-box multPic-ul">
							            	<c:if test="${not empty grdGiftRecord.carPictures and carPicCount > 0}">
								           	 	<c:set var="carPicUrlStrFirst" value='[{"url":"${grdGiftRecord.carPictures[0] }"}]' scope="page" />
								            	<script type="text/uploader-files">
													${carPicUrlStrFirst}
    											</script>
						    					<script type="text/uploader-theme">
													<li id="queue-file-{id}" class="g-u" data-name="{name}">
                										<div class="pic">
                    										<a href="javascript:void(0);" style="display:block;"><img class="J_Pic_{id} preview-img" src="" style="height:100%"/></a>
                										</div>
                										<div class=" J_Mask_{id} pic-mask"></div>
                										<div class="status-wrapper">
                    										<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    										<div class="status start-status progress-status success-status">
                        									<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>加载中...</div>
                    									</div>
                    									<div class="status error-status">
                        										<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                										</div>
                										<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            										</li>
    											</script>
					    					</c:if>
							            </ul>
							        </div> 
								</div>
							</td>
							<td>
								<div class="user-iteam">
							        <div class="fl mr_10">
							            <input type="file" class="g-u input01" id="uploadSecondBtn" value="" name="carPicture" />
							            <input type="hidden" id="urlWshowImgSecond" name="urlWshowImgSecond" data-role="carPicturePath"/>
							        </div>
							        <div class="form-context">
							            <div class="clear"></div>
							            <ul id="pictureShowSecond" class="multPic-box multPic-ul">
							            	<c:if test="${not empty grdGiftRecord.carPictures and carPicCount > 1}">
								           	 	<c:set var="carPicUrlStrSecond" value='[{"url":"${grdGiftRecord.carPictures[1] }"}]' scope="page" />
								            	<script type="text/uploader-files">
													${carPicUrlStrSecond}
    											</script>
						    					<script type="text/uploader-theme">
													<li id="queue-file-{id}" class="g-u" data-name="{name}">
                										<div class="pic">
                    										<a href="javascript:void(0);" style="display:block;"><img class="J_Pic_{id} preview-img" src="" style="height:100%"/></a>
                										</div>
                										<div class=" J_Mask_{id} pic-mask"></div>
                										<div class="status-wrapper">
                    										<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    										<div class="status start-status progress-status success-status">
                        									<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>加载中...</div>
                    									</div>
                    									<div class="status error-status">
                        										<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                										</div>
                										<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            										</li>
    											</script>
					    					</c:if>
							            </ul>
							        </div> 
								</div>
							</td>
							<td>
								<div class="user-iteam">
							        <div class="fl mr_10">
							            <input type="file" class="g-u input01" id="uploadThirdBtn" value="" name="carPicture" />
							            <input type="hidden" id="urlWshowImgThird" name="urlWshowImgThird" data-role="carPicturePath"/>
							        </div>
							        <div class="form-context">
							            <div class="clear"></div>
							            <ul id="pictureShowThird" class="multPic-box multPic-ul">
							            	<c:if test="${not empty grdGiftRecord.carPictures and carPicCount > 2}">
								           	 	<c:set var="carPicUrlStrThird" value='[{"url":"${grdGiftRecord.carPictures[2] }"}]' scope="page" />
								            	<script type="text/uploader-files">
													${carPicUrlStrThird}
    											</script>
						    					<script type="text/uploader-theme">
													<li id="queue-file-{id}" class="g-u" data-name="{name}">
                										<div class="pic">
                    										<a href="javascript:void(0);" style="display:block;"><img class="J_Pic_{id} preview-img" src="" style="height:100%"/></a>
                										</div>
                										<div class=" J_Mask_{id} pic-mask"></div>
                										<div class="status-wrapper">
                    										<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    										<div class="status start-status progress-status success-status">
                        									<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>加载中...</div>
                    									</div>
                    									<div class="status error-status">
                        										<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                										</div>
                										<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            										</li>
    											</script>
					    					</c:if>
							            </ul>
							        </div> 
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>	
</div>

<script type="text/javascript">
	var imgHostUrl = "${imgHostUrl}";
	var carNo="${requestScope.grdGiftRecord.carNo }";
</script>
<script type="text/javascript" src="${CONTEXT}js/grdGiftIssued/grd_gift_issued_detail.js"></script>











