<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
#basicInfo img{max-width:400px;_width:400px;}
</style>
	<div>
		<input type="hidden" id="productId" name="productId" value="${product.productId}">
	<table class="easyui-datagrid" id="basicInfo" title="产品基本信息" style="width:700px" data-options='width:"702"'>
	    <thead>
	        <tr>
	            <th data-options="field:'code',width:'100'"></th>
	            <th data-options="field:'name',width:'600'"></th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr>
	            <td >产品名称</td><td>${product.productName }</td>
	        </tr>
	        <tr>
	            <td>产品分类</td><td>${product.cateName }</td>
	        </tr>
	        <tr>
	            <td>发布到商铺</td><td>${product.shopsName }</td>
	        </tr>
	    </tbody>
	</table>
		<!-- <table class="easyui-datagrid" id="basicInfo" style="width:700px;height:250px" title="产品详细信息"> -->
	<table id="basicInfo" style="width:702px;height:250px" title="产品详细信息">
		<thead>
	        <tr>
	            <th data-options="field:'code'" style="width:100px"></th>
	            <th data-options="field:'name'" style="width:600px"></th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr >
	            <td >产品图片</td><td><img alt="" src="${imgHostUrl }${product.urlOrg }" ></td>
	        </tr>
	        <tr >
	            <td >产品APP图</td><td><img alt="" src="${imgHostUrl }${appPicture}" ></td>
	        </tr>
	    	<c:forEach var="item" items ="${multiplePictureList}" varStatus="status">
		    	<tr >
		            <td >多角度图${status.index}</td><td><img alt="" src="${imgHostUrl }${item.url}" ></td>
		        </tr>
			</c:forEach>
	        <tr>
	            <td>详细信息</td><td><textarea rows="3" cols="30" style="width:100%;font-size:14px;" 
					name="description" id="description_detail" >${product.description}</textarea></td>
	        </tr>
	    </tbody>
	</table>
	<table class="easyui-datagrid" id="basicInfo" data-options='width:"702"' title="交易信息">
		<thead>
	        <tr>
	            <th data-options="field:'code',width:'100'"></th>
	            <th data-options="field:'name',width:'600',align:'left'"></th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr >
	            <td >供应量</td>
	            <td>${product.stockCount } <!-- 单位 --><span style="width:200px;">
	            	<c:forEach var="item" items ="${units}" varStatus="status">
		           		<c:if test="${product.unit == item.codeKey}">${item.codeValue}</c:if>
					</c:forEach>
					</span>
				</td>
	        </tr>
	        
	        <tr >
	            <td> 
					<c:choose>
					   <c:when test="${product.priceType == 0}">  
					    	单价
					   </c:when>
					   <c:when test="${product.priceType == 1}"> 
					   		区间价格
					   </c:when>
					   <c:otherwise> 
					   </c:otherwise>
					</c:choose>
				</td>
				<td> 
					<c:choose>
					   <c:when test="${product.priceType == 0}">  
					   		<!-- 内层start.. -->
							<c:choose>
								<c:when test="${product.price == 0 }" >面议</c:when>
								<c:otherwise><span id="priceTempSpan"></span>
										<script type="text/javascript">
											function fixNum(num){
												var numStr = num + "";
												var index = numStr.indexOf(".");
												if ( index != -1){
														var savingNum = numStr.substring(index+1).length;
														return (num * Math.pow(10, savingNum))/ Math.pow(10, savingNum)
												}
												return num;
											}
											var formatNum = fixNum(new Number(parseFloat("${product.price}")));
											$("#priceTempSpan").text(formatNum);
										</script>
								</c:otherwise>
							</c:choose>
					   		<!-- 内层end... -->
					   </c:when>
					   <c:when test="${product.priceType == 1}">  
					   		<%-- <table id="buyCountTable">
					   			<thead>
							        <tr>
							            <th >起始起订量</th>
							            <th >截止起订量</th>
							            <th >价格</th>
							        </tr>
							    </thead>
							    <tbody>
							   		<c:forEach var="priceDto" items="${mutiplePrices}">  
							   		<tr>
							   			<td>${priceDto.buyCountStart }</td>
							   			<td>${priceDto.buyCountEnd }</td>
							   			<td>${priceDto.price }</td>
							   		</tr>
									</c:forEach>  
							    </tbody>
					   		</table> --%>
					   		<%-- <ul id="buyCountTable">
							        <li>
							            <ul ><li>起始起订量</li></ul>
							            <ul ><li>截止起订量</li></ul>
							            <ul ><li>价格</li></ul>
							        </li>
							   		<c:forEach var="priceDto" items="${mutiplePrices}">  
							   		<li>
							   			<ul ><li>${priceDto.buyCountStart }</li></ul>
							   			<ul ><li>${priceDto.buyCountEnd }</li></ul>
							   			<ul ><li>${priceDto.price }</li></ul>
							   		</li>
									</c:forEach>  
					   		</ul> --%>
					   	<%-- 	<ul id="buyCountTable">
							        <li>
							            <ul ><li>起始起订量</li></ul>
							            <ul ><li>截止起订量</li></ul>
							            <ul ><li>价格</li></ul>
							        </li>
							   		<c:forEach var="priceDto" items="${mutiplePrices}">  
							   		<li>
							   			<ul ><li>${priceDto.buyCountStart }</li></ul>
							   			<ul ><li>${priceDto.buyCountEnd }</li></ul>
							   			<ul ><li>${priceDto.price }</li></ul>
							   		</li>
									</c:forEach>  
					   		</ul> --%>
					   		<dl>
					   			<dt>起始起订量</dt>
					   			<dt>截止起订量</dt>
					   			<dt>价格</dt>
					   		</dl>
					   		<c:forEach var="priceDto" items="${mutiplePrices}" varStatus="status">  
					   		<dl>
					   			<dd>${priceDto.buyCountStart }</dd>
					   			<dd>${priceDto.buyCountEnd }</dd>
					   			<dd><!-- 内层start.. -->
									<c:choose>
										<c:when test="${priceDto.price == 0 }" >面议</c:when>
										<c:otherwise>${priceDto.price}</c:otherwise>
									</c:choose>
							   		<!-- 内层end... -->
							   	</dd>
					   		</dl>
					   		</c:forEach>  
					   </c:when>
					</c:choose>
				</td>
	        </tr>	      
	          
	        <tr>
	            <td>最小起订量</td><td>${product.minBuyCount }</td>
	        </tr>
	        <tr>
	            <td>所在地</td>
	            <td>${product.provinceName }${product.cityName }${product.areaName }<br>
	            	${product.address }
	            </td>
	        </tr>
	        <tr>
	            <td>信息有效期</td><td>${product.infoLifeDay }</td>
	        </tr>
	        <tr>
	            <td><!-- 审核状态 --></td>
	            <td>
				</td>
	        </tr>
	    </tbody>
	</table>
		<c:choose>
		   <c:when test="${status == 0}">  
		   		<table class="easyui-datagrid" title="审核信息" data-options='width:"702"'>
		   		<thead>
				        <tr>
				            <th data-options="width:'233'">${message}</th>
				        </tr>
				    </thead>
		   		</table>
		   </c:when>
		   <c:when test="${status == 1}"> 
		   		<table class="easyui-datagrid" id="auditPassTable" title="审核不通过" data-options='width:"702"'>
				    <thead>
				        <tr>
				            <th data-options="field:'reason',width:'233'">不通过原因</th>
				            <th data-options="field:'code',width:'233'">审批人</th>
				            <th data-options="field:'name',width:'233'">审批时间</th>
				        </tr>
				    </thead>
				    <tbody>
				        <c:forEach var="auditInfo" items="${auditInfoList}">  
					   		<tr>
					   			<td>${auditInfo.reason }<br>${auditInfo.otherReason }</td>
					   			<td>${auditInfo.memberName }</td>
					   			<td>${auditInfo.auditTime_string }</td>
					   		</tr>
						</c:forEach>  
				    </tbody>
				</table>
		   </c:when>
		   <c:otherwise> 
		   </c:otherwise>
		</c:choose>

	</div>
<script type="text/javascript" >
$(document).ready(function(){
	KindEditor.create('textarea[name="description"]', {
		cssPath : CONTEXT+'js/kindeditor-4.1.10/plugins/code/prettify.css',
		uploadJson: CONTEXT+'js/kindeditor-4.1.10/jsp/upload_json.jsp',
		fileManagerJson : CONTEXT+'js/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		allowFileManager: true,
		allowImageUpload : true,
		//把编辑器里的内容同步到textarea @_@ @_@ @_@  
		afterBlur: function(){this.sync();}
	});
});
function reAudit(){
	var productId = $("#productId").val();
	$('#detailDialog').dialog('close');
	$('#detailDialog').dialog({'title':'商品详情', 'width':900, 'height':400, 'href':CONTEXT+'product/reAudit/' + productId }).dialog('open');
}
</script>