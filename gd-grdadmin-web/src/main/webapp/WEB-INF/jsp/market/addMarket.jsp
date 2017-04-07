<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<form id="addForm" method="post" action="market/save">
	<div>
		<table style="width: 100%; height: 100%;">
			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span> 街市名称:</td>
				<td><input type="text" id="marketName" name="marketName" maxlength="50"
					value="${marketName}" required="true" class="easyui-validatebox"
					missingMessage="街市名称必须填写" style="width: 150px"></td>
			</tr>
			
		    <tr>
				<td style="width: 100px;"><span style="color: red;">*</span>街市类型:</td>
				<td>
				<input type="radio" name="marketType"  checked="checked" value="0">产地供应商
				<input type="radio" name="marketType"   value="1">街市
				<input type="radio" name="marketType"   value="2">市场（农批中心）
				</td>
			</tr>	
			  <tr>                                                             
             <td class="left"><span style="color: red;">*</span>省:</td>   
             <td><input name="province"  id="province"  required="true" class="easyui-validatebox"
					missingMessage="必须填写"   style="width: 174px;" ></td>         
            </tr> 
            <tr>  
             <td class="left"><span style="color: red;">&nbsp;</span>市:</td>                    
             <td><input name="city" id="city"   class="easyui-validatebox"
					  style="width: 174px;"></td>                                             
            </tr>
            <tr> 
             <td class="left">&nbsp;县区:</td>   
             <td><input name="county"  id="county"  class="easyui-validatebox"
					 style="width: 174px;" ></td>
            </tr> 
        
             <tr>
			<td style="width: 100px;"><span style="color: red;">*</span> 详细地址:</td>
			   <td>
				<input type="text" id="address" name="address" maxlength="200"
					required="true" class="easyui-validatebox"
					missingMessage="详细地址必须填写" style="width: 150px">
			  </td>
	        </tr>
			
			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span> 状态:</td>
				<td>
				<input type="radio" name="status"  checked="checked" value="0"> 启用
				<input type="radio" name="status"   value="1"> 停用
				</td>
			</tr>

			<tr>
				<td style="width: 100px;">备注:</td>
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













