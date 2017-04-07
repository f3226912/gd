<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
#basicInfo img{max-width:400px;_width:400px;}
</style>
<div>
	<table class="easyui-datagrid" id="basicInfo" title="产品基本信息" data-options='width:"702"'>
	    <thead>
	        <tr>
	            <th data-options="field:'code',width:'100'" ></th>
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
	    </tbody>
	</table>
	
	<table id="basicInfo" style="width:700px;height:250px" title="产品详细信息">
		<thead>
	        <tr>
	            <th data-options="field:'code',width:'100'" style="width:100px"></th>
	            <th data-options="field:'name',width:'600',align:'left'" style="width:600px"></th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr >
	            <td >产品主图</td><td><img alt="" src="${imgHostUrl }${masterPicture}" ></td>
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
	<table class="easyui-datagrid" id="basicInfo" style="width:705px;height:250px" title="交易信息">
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
	        <tr>
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
					   		<dl>
					   			<dt>起始起订量</dt>
					   			<dt>截止起订量</dt>
					   			<dt>价格</dt>
					   		</dl>
					   		<c:forEach var="priceDto" items="${mutiplePrices}" varStatus="status">  
					   		<dl>
					   			<dd>${priceDto.buyCountStart }</dd>
					   			<dd>${priceDto.buyCountEnd }</dd>
					   			<dd>
					   				<!-- 内层start.. -->
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
<%-- 	            <td>所在地</td>
	            <td>${product.provinceName }${product.cityName }${product.areaName }<br>
	            	${product.address }
	            </td> --%>
	            <td>商品产地</td>
	            <td>${originProvinceName}--${originCityName}--${originAreaName}<br>
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
		   <c:when test="${product.state == 1}">  
			    <div id="auditDataDiv">
					<form id="auditForm" method="post">
						<input type="hidden" id="productId" name="productId" value="${product.productId}">
						<%-- <input type="hidden" id="state" name="state" value="${product.state}"> --%>
						产品名称与图片不相符<input type="checkbox"  name="reson" value="产品名称与图片不相符" style="vertical-align: middle;">&nbsp;&nbsp;
						产品与类目不相符<input type="checkbox"  name="reson" value="产品与类目不相符" style="vertical-align: middle;">&nbsp;&nbsp;
						产品图片不符合规格<input type="checkbox"  name="reson" value="产品图片不符合规格" style="vertical-align: middle;">&nbsp;&nbsp;<br>
						其他原因&nbsp;&nbsp;<textarea rows="3" cols="50" style="font-size:14px;" 
						name="otherReason" id="otherReason" ></textarea><br>
						<input type="button" id="auditPass" value="审核通过" onclick="audit(1)">
						<input type="button" id="auditRefuse" value="审批驳回" onclick="audit(0)">
					</form>
				</div>
		   </c:when>
		   <c:when test="${product.state == 2}"> 
		   			<!-- 审核未通过部分	start -->
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
		   			<!-- 审核未通过部分	end -->
		   </c:when>
		   <c:when test="${product.state == 3}"> 
		   			<!-- 审核通过部分	start -->
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
					   				<table class="easyui-datagrid" id="auditPassTable" title="" data-options='width:"702"'>
									    <thead>
									        <tr>
									            <th data-options="field:'code',width:'100'"></th>
									            <th data-options="field:'name',width:'600'"></th>
									        </tr>
									    </thead>
									    <tbody>
									        <tr>
									            <td width="20%">结论</td><td>通过审核</td>
									        </tr>
									        <tr>
									            <td>审批人</td><td>${auditInfo.memberName }</td>
									        </tr>
									        <tr>
									            <td>审批时间</td>
									            <td>${auditInfo.auditTime_string }
									            </td>
									        </tr>
									    </tbody>
									</table>	
							   </c:when>
							   <c:otherwise> 
							   </c:otherwise>
							</c:choose>
		  		 	<!-- 审核通过部分     end -->
		   </c:when>
		   <c:when test="${product.state == 4}"> 
		   </c:when>
		   <c:when test="${product.state == 5}"> 
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
var proId = $("#productId").val();
function getReasons(){
	var reasonObjs = $('input[name="reson"]:checked');
	var len = reasonObjs.length ;
	var reasons ;
	if (len > 0){
		reasons = reasonObjs[0].value;
	}else {
		return "";
	}
	for(var i = 1; i < len; i++){
		reasons += "," + reasonObjs[i].value;
	}
	return reasons;
}
//判断中英混合字符的字节数, 一个中文字符两个字节
function cnLength(Str) {   
    var escStr = escape(Str);   
    var numI = 0;   
    var escStrlen = escStr.length;   
    for (var i = 0; i < escStrlen; i++)   
        if (escStr.charAt(i) == '%')   
        if (escStr.charAt(++i) == 'u')   
        numI++;   
    return Str.length + numI;   
} 

// 审核快单产品
function audit(status){
	var oldState = "${product.state}";
	if (oldState != 1){
		warningMessage("只能操作状态为待审核的产品");
		return false;
	}
	reasons = getReasons();
	if (status == 0 && cnLength($("#otherReason").val())> 200 ){
		warningMessage("其他原因输入过长, 请删减");
		return false;
	}
	 $.ajax( {  
		    url:CONTEXT+'fastProduct/auditFastProduct',// 跳转到 action  
		    data:{  
		    	productId : proId,
		    	status : status,
		    	oldState : oldState,
		    	reason : reasons,
		    	otherReason : $("#otherReason").val()
		    },  
		    type:'post',  
		    dataType:'json',  
		    success:function(data) {
		    	if (data.status == 1){
	    			window.location.reload();
		    	}else {
		    		warningMessage(data.message);
		    	}
		     },  
		     error : function(data) {  
		    	 warningMessage("审核操作失败");
		     }  
		});
}
</script>











