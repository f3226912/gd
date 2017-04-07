<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
td {
	padding-left: 12px;
}
</style>
<form id="addForm" method="post" action="consignment/save">
	<div>
		<table style="width: 100%; height: 500px;">

			<tr>
				<td><span style="color: red;">*</span>始发地省:</td>
				<td><input name="start_provinceId" id="start_provinceId"
					style="width: 174px;" required="true" missingMessage="必须填写"
					class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>始发地市:</td>
				<td><input name="start_cityId" id="start_cityId"
					style="width: 174px;" 
					class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td>始发地县区:</td>
				<td><input name="start_areaId" id="start_areaId"
					style="width: 174px;" class="easyui-validatebox"></td>
			</tr>

			<%--  <tr>
			<td style="width: 100px;">收货详细地址:</td>
			   <td>
				<input type="text" id="start_detail" name="start_detail" value="${start_detail}"
					required="true" class="easyui-validatebox"
					missingMessage="详细地址必须填写" style="width: 150px">
			  </td>
	      </tr> --%>

			<tr>
				<td><span style="color: red;">*</span>目的地省:</td>
				<td><input name="end_provinceId" id="end_provinceId"
					style="width: 174px;" required="true" missingMessage="必须填写"
					class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>目的地市:</td>
				<td><input name="end_cityId" id="end_cityId"
					style="width: 174px;" 
					class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td>目的地县区:</td>
				<td><input name="end_areaId" id="end_areaId"
					style="width: 174px;" class="easyui-validatebox"></td>
			</tr>

			 <tr>
				<td style="width: 100px;">货物名称:</td>
				<td><input type="text" id="goodsName" name="goodsName" maxlength="20" 
					class="easyui-validatebox" style="width: 150px"></td>
			 </tr>

			<!-- <tr>
				<td style="width: 100px;"><span style="color: red;"></span>装车时间:</td>
				<td>
					   <input  type="text"  id="sendDate"  name="sendDate"   class="easyui-datebox"  style="width:150px" >&nbsp; 
					<input type="text" id="sendDate" name="sendDate"
					onfocus="WdatePicker({minDate:'%y-%M-{%d+0}',maxDate:'#F{$dp.$D(\'endDate\')}'})"
					style="width: 150px">&nbsp; <select id="sendDateType"
					name="sendDateType" class="text_sketch">
					    <option selected="selected" value="4">不限</option>
						<option value="0">上午</option>
						<option value="1">中午</option>
						<option value="2">下午</option>
						<option value="3">晚上</option>
				</select>
				</td>
			</tr>

			<tr>
				<td style="width: 100px;"><span style="color: red;"></span>到达时间:</td>
				<td>
						  <input  type="text"  id="endDate"  name="endDate"   class="easyui-datebox"  style="width:150px" >&nbsp; 
					<input type="text" id="endDate" name="endDate"
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'sendDate\')}'})"
					style="width: 150px">&nbsp; <select id="endDateType"
					name="endDateType" class="text_sketch">
						<option selected="selected" value="4">不限</option>
						<option value="0">上午</option>
						<option value="1">中午</option>
						<option value="2">下午</option>
						<option value="3">晚上</option>
				</select>
				</td>
			</tr>
              -->
              
			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span>货物类型:</td>
				<td><select id="goodsType" name="goodsType" class="text_sketch">
				      <!--   <option selected="selected" value="0">常温货</option> -->
						<option selected="selected" value="0">普货</option>
						<option value="1">冷藏</option>
						<option value="2">鲜活水产</option>
						<option value="3">其他</option>
						<option value="4">重货</option>
						<option value="5">抛货</option>
						<option value="6">蔬菜</option>
					    <option value="7">水果</option>
					    <option value="8">农副产品</option>
					    <option value="9">日用品</option>
					    <option value="10">纺织</option>
					    <option value="11">木材</option>
				</select></td>
			</tr>

			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span>总重量:</td>
				<td><input type="text" id="totalWeight" name="totalWeight"
					value="" maxlength="8" style="width: 150px">&nbsp; <select
					id="hundredweight" name="hundredweight" class="text_sketch">
						<option selected="selected" value="0">吨</option>
						<option value="1">公斤</option>
				</select>&nbsp; 总体积<input type="text" id="totalSize" name="totalSize"
					value="" maxlength="8" style="width: 150px">立方米</td>
			</tr>

            
			<tr>
			<td style="width: 100px;">车辆类型:</td>
			<td><select id="carType" name="carType" class="text_sketch">
		   <option value="9" selected="selected">不限</option>         
		   <option value="0">厢式货车</option>
           <option value="1">敞车</option>            
           <option value="5">高栏车</option>          
           <option value="6">平板车</option>          
           <option value="2">冷藏车</option>          
           <option value="3">保温车</option>          
           <option value="7">活鲜水车</option>        
           <option value="8">集装箱</option>     
           <option value="4">其他</option>         
				</select></td>
			</tr>
			
			 <tr>
				<td style="width: 100px;"><span style="color:red;"></span> 车长:</td>
			<!-- 	<td><input type="text" id="carLength" name="carLength" maxlength="5"
					value=""  class="easyui-validatebox"
					 style="width: 150px">&nbsp;米</td> -->
					  <td>
		 <select id="carLength" name="carLength" class="text_sketch" >
    <option value="0">不限</option> 
    <option value="-1">小于4.2米</option>    
    <option value="4.2">4.2米</option>                            
    <option value="4.5">4.5米</option>                             
    <option value="5">5米</option>                                 
    <option value="6.2">6.2米</option>                             
    <option value="6.8">6.8米</option>                             
    <option value="7.2">7.2米</option>                             
    <option value="7.7">7.7米</option>                             
    <option value="7.8">7.8米</option>                             
    <option value="8.2">8.2米</option>                             
    <option value="8.6">8.6米</option>                             
    <option value="8.7">8.7米</option>                             
    <option value="9.6">9.6米</option>                             
    <option value="11.7">11.7米</option>                           
    <option value="12.5">12.5米</option>                           
    <option value="13">13米</option>                               
    <option value="13.5">13.5米</option>                           
    <option value="14">14米</option>
    <option value="15">15米</option>                                  
    <option value="16">16米</option>                               
    <option value="17">17米</option>                               
    <option value="17.5">17.5米</option>                           
    <option value="18">18米</option>  
    <option value="-2">大于18米</option>    
    </select>  
          </td>
			 </tr>

			<tr>
				<td style="width: 100px;">发运方式:</td>
				<td><select id="sendGoodsType" name="sendGoodsType"
					class="text_sketch" onchange="typeChange()">
					    <option value="2" selected="selected" >不限</option>
						<option value="1">整车</option>
						<option value="0">零担</option>
						
				</select></td>
			</tr>

			<tr>
				<td style="width: 100px;">价格:</td>
				<td><input type="text" id="price" name="price" maxlength="8" disabled="disabled"
					value="" style="width: 150px">&nbsp;
					<div id="div1" style="display: none; margin-right: 220px; float: right;">
						<select id="priceUnitType" name="priceUnitType">
							<option selected="selected" value="0">元/吨</option>
							<option value="1">元/公斤</option>
							<option value="2">元/立方</option>
						</select>
					</div>
					<div id="div2"
						style="margin-right: 280px; float: right;">
						<span>元</span>
					</div></td>
			</tr>
			<tr>
				<td style="width: 100px;">手机号码:</td>
				<td><input type="text" id="memberMobile" name="memberMobile"
					value="${userMobile}" onblur="displayMemberName()" 
					class="easyui-validatebox" 
					style="width: 150px"></td>
			</tr>

			<tr>
				<td style="width: 100px;">发货人:</td>
				<td><span id="memberName"></span></td>
			</tr>
			<!-- 可以不选择公司  modified on 2016-04-22 -->
			<tr class="tr_memberId" style="display: none">
				<td><span style="color: red;">*</span>公司名称:</td>
				 <input type="hidden" id="memberId" name="memberId" value="" />
				 <input type="hidden" id="userMobile" name="userMobile" value="" />
				 <input type="hidden" id="userType" name="userType" value="" />
				<td>
				<div id="per">
				<input type="button" id="queryEntList" value="选择公司" >
				</div>
				<div id="ent">
				<input type="text" id="companyName" name="companyName" value=""  readonly="readonly"/>
				</div>
				</td>
			</tr>
			
			<tr>
				<td style="width: 100px;">备注:</td>
				<td><textarea id="remark" name="remark"  maxlength="50"
					class="easyui-validatebox" 
					style="width: 150px"/></td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/memberAddress/add.js"></script>
<script type="text/javascript" src="${CONTEXT}js/memberAddress/area.js"></script>

