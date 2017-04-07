<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="addForm" method="post" action="market/save">
	<div>
		<table style="width: 800px; height: 280px;">
		    <tr>
				<td><input type="hidden" id="id" name="id"
					value="${id}"  style="width: 150px"></td>
			</tr>
			<tr>
				<td style="width: 100px;"> <span style="color: red;">*</span>街市名称:</td>
				<td><input type="text" id="marketName" name="marketName" maxlength="50"
					value="${marketName}" required="true" class="easyui-validatebox"
					missingMessage="街市名称必须填写" style="width: 150px"></td>
			</tr>
			
		    <tr>
				<td style="width: 100px;"><span style="color: red;">*</span>街市类型:</td>
				<td>
				<input type="radio" name="marketType"  <c:if test="${marketType=='0'}"> checked="checked" </c:if>  value="0">产地供应商
				<input type="radio" name="marketType"  <c:if test="${marketType=='1'}"> checked="checked" </c:if>  value="1">街市
				<input type="radio" name="marketType"  <c:if test="${marketType=='2'}"> checked="checked" </c:if>  value="2">市场（农批中心）
				<input type="radio" name="marketType"  <c:if test="${marketType=='3'}"> checked="checked" </c:if>  value="3">用户自定义添加
				</td>
			</tr>	
			
	        <tr>                                                             
             <td class="left"><span style="color: red;">*</span>省:</td>   
             <td><input name="province"  id="province"  value="${provinceId}" required="true" class="easyui-validatebox"
					missingMessage="必须填写"   style="width: 174px;" ></td>         
            </tr> 
            <tr>  
             <td class="left"><span style="color: red;">&nbsp;</span>市:</td>                    
             <td><input name="city" id="city" value="${cityId}"   class="easyui-validatebox"
					    style="width: 174px;"></td>                                             
            </tr>
            <tr> 
             <td class="left">&nbsp;县区:</td>   
             <td><input name="county"  id="county" value="${areaId}"   class="easyui-validatebox"
					  style="width: 174px;" ></td>
            </tr> 
        
            <tr>
			<td style="width: 100px;"><span style="color: red;">*</span> 详细地址:</td>
			   <td>
				<input type="text" id="address" name="address" value="${address}" maxlength="200"
					required="true" class="easyui-validatebox"
					missingMessage="详细地址必须填写" style="width: 150px">
			  </td>
	        </tr>
			
			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span> 状态:</td>
				<td>
				<c:choose>
				<c:when test="${status==0}">
				<input type="radio" name="status"   value="0" checked="checked"> 启用
				<input type="radio" name="status"   value="1"> 停用
				</c:when>
				<c:otherwise>
				<input type="radio" name="status"   value="0"> 启用
				<input type="radio" name="status"   value="1" checked="checked"> 停用
				</c:otherwise>
				</c:choose>
				
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 经度:</td>
				<td>
				<input type="text" id="lon" name="lon" maxlength="50"
					value="${lon}"  style="width: 150px;" disabled="disabled" >
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 纬度:</td>
				<td>
				<input type="text" id="lat" name="lat" maxlength="50"
					value="${lat}"  style="width: 150px" disabled="disabled" >
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 备注:</td>
				<td>
				<input type="text" id="description" name="description" maxlength="50"
					value="${description}"  style="width: 150px">
				</td>
			</tr>

		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/market/add.js"></script>
<script type="text/javascript" src="${CONTEXT}js/market/area.js"></script>







