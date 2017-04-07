<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
td {
	padding-left: 12px;
}
</style>
<form id="addForm" method="post" action="consignment/save">
	<div>
		<table style="width: 100%; height: 500px;">
			<tr>
				<td><input type="hidden" id="id" name="id" value="${id}"
					style="width: 150px"></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>始发地省:</td>
				<td><input name="start_provinceId" id="start_provinceId"
					value="${start_provinceId}" style="width: 174px;" required="true"
					missingMessage="必须填写" class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>始发地市:</td>
				<td><input name="start_cityId" id="start_cityId"
					value="${start_cityId}" style="width: 174px;" 
					missingMessage="必须填写" class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td>始发地县区:</td>
				<td><input name="start_areaId" id="start_areaId"
					value="${start_areaId}" style="width: 174px;"
					class="easyui-validatebox"></td>
			</tr>


			<tr>
				<td><span style="color: red;">*</span>目的地省:</td>
				<td><input name="end_provinceId" id="end_provinceId"
					value="${end_provinceId}" style="width: 174px;" required="true"
					missingMessage="必须填写" class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>目的地市:</td>
				<td><input name="end_cityId" id="end_cityId"
					value="${end_cityId}" style="width: 174px;" 
					missingMessage="必须填写" class="easyui-validatebox"></td>
			</tr>
			<tr>
				<td>目的地县区:</td>
				<td><input name="end_areaId" id="end_areaId"
					value="${end_areaId}" style="width: 174px;"
					class="easyui-validatebox"></td>
			</tr>

			<tr>
				<td style="width: 100px;">货物名称:</td>
				<td><input type="text" id="goodsName" name="goodsName"
					value="${goodsName}" maxlength="20"  class="easyui-validatebox"
					style="width: 150px"></td>
			</tr> 

			<%-- <tr>
				<td style="width: 100px;"><span style="color: red;"></span>装车时间:</td>
				<td><input type="text" id="sendDate" name="sendDate"
					onfocus="WdatePicker({minDate:'%y-%M-{%d+0}'})"
					value="${sendDate}"  style="width: 150px">&nbsp;
					<select id="sendDateType" name="sendDateType" class="text_sketch">
						<option
							<c:if test="${sendDateType == 4}"> selected="selected" </c:if>
							value="">不限</option>
						<option
							<c:if test="${sendDateType==0}"> selected="selected" </c:if>
							value="0">上午</option>
						<option
							<c:if test="${sendDateType==1}"> selected="selected" </c:if>
							value="1">中午</option>
						<option
							<c:if test="${sendDateType==2}"> selected="selected" </c:if>
							value="2">下午</option>
						<option
							<c:if test="${sendDateType==3}"> selected="selected" </c:if>
							value="3">晚上</option>
				</select></td>
			</tr>

			<tr>
				<td style="width: 100px;"><span style="color: red;"></span>到达时间:</td>
				<td><input type="text" id="endDate" name="endDate"
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'sendDate\')}'})"
					value="${endDate}"  style="width: 150px">&nbsp;
					<select id="endDateType" name="endDateType" class="text_sketch">
					
					   <option
							<c:if test="${endDateType == 4}"> selected="selected" </c:if>
							value="">不限</option>
						<option
							<c:if test="${endDateType==0}"> selected="selected" </c:if>
							value="0">上午</option>
						<option
							<c:if test="${endDateType==1}"> selected="selected" </c:if>
							value="1">中午</option>
						<option
							<c:if test="${endDateType==2}"> selected="selected" </c:if>
							value="2">下午</option>
						<option
							<c:if test="${endDateType==3}"> selected="selected" </c:if>
							value="3">晚上</option>
				</select></td>
			</tr>
 --%>
 
			<tr>
				<td style="width: 100px;">货物类型:</td>
				<td><select id="goodsType" name="goodsType" class="text_sketch">
						<option <c:if test="${goodsType==0}"> selected="selected" </c:if>
							value="0">普货</option>
						<option <c:if test="${goodsType==1}"> selected="selected" </c:if>
							value="1">冷藏</option>
						<option <c:if test="${goodsType==2}"> selected="selected" </c:if>
							value="2">鲜活水产</option>
						<option <c:if test="${goodsType==3}"> selected="selected" </c:if>
							value="3">其他</option>
					    <option  <c:if test="${goodsType==4}"> selected="selected" </c:if> value="4">重货</option>
						<option  <c:if test="${goodsType==5}"> selected="selected" </c:if> value="5">抛货</option>
						<option  <c:if test="${goodsType==6}"> selected="selected" </c:if> value="6">蔬菜</option>
					    <option  <c:if test="${goodsType==7}"> selected="selected" </c:if> value="7">水果</option>
					    <option  <c:if test="${goodsType==8}"> selected="selected" </c:if> value="8">农副产品</option>
					    <option  <c:if test="${goodsType==9}"> selected="selected" </c:if> value="9">日用品</option>
					    <option  <c:if test="${goodsType==10}"> selected="selected" </c:if> value="10">纺织</option>
					    <option  <c:if test="${goodsType==11}"> selected="selected" </c:if> value="11">木材</option>	
							
				</select></td>
			</tr>

			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span>总重量:</td>
				<td><input type="text" id="totalWeight" name="totalWeight"
					value="${totalWeight}" maxlength="8" style="width: 150px">&nbsp;
					<select id="hundredweight" name="hundredweight" class="text_sketch">
						<option selected="selected" value="0">吨</option>
						<option value="1">公斤</option>
				</select>&nbsp; 总体积<input type="text" id="totalSize" name="totalSize"
					value="${totalSize}" maxlength="8" style="width: 150px">立方米
				</td>
			</tr>

		<tr>
		<td style="width: 100px;">车辆类型:</td>
	   <td><select id="carType" name="carType" class="text_sketch">
		  <option <c:if test="${carType==0}"> selected="selected" </c:if> value="0">厢式货车</option>
          <option <c:if test="${carType==1}"> selected="selected" </c:if> value="1">敞车</option>
          <option <c:if test="${carType==5}"> selected="selected" </c:if> value="5">高栏车</option>
          <option <c:if test="${carType==6}"> selected="selected" </c:if> value="6">平板车</option>
          <option <c:if test="${carType==2}"> selected="selected" </c:if> value="2">冷藏车</option>
          <option <c:if test="${carType==3}"> selected="selected" </c:if> value="3">保温车</option>
          <option <c:if test="${carType==7}"> selected="selected" </c:if> value="7">活鲜水车</option>
          <option <c:if test="${carType==8}"> selected="selected" </c:if> value="8">集装箱</option>
          <option <c:if test="${carType==4}"> selected="selected" </c:if> value="4">其他</option>
          <option <c:if test="${carType==9}"> selected="selected" </c:if> value="9">不限</option>         
          
         </select>
			</tr>
			
			<tr>
				<td style="width: 100px;"><span style="color:red;"></span> 车长:</td>
			  <td>
		      <select id="carLength" name="carLength" class="text_sketch" >
		            <option <c:if test="${carLength==0}"> selected="selected" </c:if>value="0">不限</option> 
		            <option <c:if test="${carLength==-1}"> selected="selected" </c:if> value="-1">小于4.2米</option>
		            <option <c:if test="${carLength==4.2}"> selected="selected" </c:if> value="4.2">4.2米</option>
                    <option <c:if test="${carLength==4.5}"> selected="selected" </c:if> value="4.5">4.5米</option>
                    <option <c:if test="${carLength==5}"> selected="selected" </c:if> value="5">5米</option>
                    <option <c:if test="${carLength==6.2}"> selected="selected" </c:if> value="6.2">6.2米</option>
                    <option <c:if test="${carLength==6.8}"> selected="selected" </c:if> value="6.8">6.8米</option>
                    <option <c:if test="${carLength==7.2}"> selected="selected" </c:if> value="7.2">7.2米</option>
                    <option <c:if test="${carLength==7.7}"> selected="selected" </c:if> value="7.7">7.7米</option>
                    <option <c:if test="${carLength==7.8}"> selected="selected" </c:if> value="7.8">7.8米</option>
                    <option <c:if test="${carLength==8.2}"> selected="selected" </c:if> value="8.2">8.2米</option>
                    
                    <option <c:if test="${carLength==8.6}"> selected="selected" </c:if> value="8.6">8.6米</option>
                    <option <c:if test="${carLength==8.7}"> selected="selected" </c:if> value="8.7">8.7米</option>
                    <option <c:if test="${carLength==9.6}"> selected="selected" </c:if> value="9.6">9.6米</option>
                    <option <c:if test="${carLength==11.7}"> selected="selected" </c:if> value="11.7">11.7米</option>
                    <option <c:if test="${carLength==12.5}"> selected="selected" </c:if> value="12.5">12.5米</option>
                    <option <c:if test="${carLength==13}"> selected="selected" </c:if> value="13">13米</option>
                    <option <c:if test="${carLength==13.5}"> selected="selected" </c:if> value="13.5">13.5米</option>
                    <option <c:if test="${carLength==14}"> selected="selected" </c:if> value="14">14米</option>
                    <option <c:if test="${carLength==15}"> selected="selected" </c:if> value="15">15米</option>
                    <option <c:if test="${carLength==16}"> selected="selected" </c:if> value="16">16米</option>
                    <option <c:if test="${carLength==17}"> selected="selected" </c:if> value="17">17米</option>
                    <option <c:if test="${carLength==17.5}"> selected="selected" </c:if> value="17.5">17.5米</option>
                    <option <c:if test="${carLength==18}"> selected="selected" </c:if> value="18">18米</option> 
                    <option <c:if test="${carLength==-2}"> selected="selected" </c:if>  value="-2">大于18米</option>    
           </select>
	        </td>
		    </tr>

			<tr>
				<td style="width: 100px;">发运方式:</td>
				<td><select id="sendGoodsType" name="sendGoodsType"
					class="text_sketch" onchange="typeChange()">
						<option
							<c:if test="${sendGoodsType==0}"> selected="selected" </c:if>
							value="0">零担</option>
						<option
							<c:if test="${sendGoodsType==1}"> selected="selected" </c:if>
							value="1">整车</option>
						<option
							<c:if test="${sendGoodsType==2}"> selected="selected" </c:if>
							value="2">不限</option>
				</select></td>
			</tr>

			<tr>
				<td style="width: 100px;">价格:</td>
				<td><input type="text" id="price" name="price" value="${price}"
					maxlength="8" style="width: 150px">&nbsp;
					<div id="div1" style="margin-right: 220px; float: right;">
						<select id="priceUnitType" name="priceUnitType">
							<option
								<c:if test="${priceUnitType==0}"> selected="selected" </c:if>
								value="0">元/吨</option>
							<option
								<c:if test="${priceUnitType==1}"> selected="selected" </c:if>
								value="1">元/公斤</option>
							<option
								<c:if test="${priceUnitType==2}"> selected="selected" </c:if>
								value="2">元/立方</option>
						</select>
					</div>
					<div id="div2"
						style="display: none; margin-right: 280px; float: right;">
						<span>元</span>
					</div></td>
			</tr>

			<tr>
				<td style="width: 100px;"><span style="color: red;"></span>手机号码:</td>
				<td><input type="text" id="memberMobile" name="memberMobile"
					value="${userMobile}" onblur="displayMemberName()" 
					class="easyui-validatebox" <c:if test="${userType !=1 }"> readonly="readonly" </c:if>
					style="width: 150px"></td>
			</tr>

			<tr>
				<td><span style="color: red;"></span>发货人:</td>
				<td><span id="memberName">${memberName}</span></td>
			</tr>

           <c:if test="${userType ==2}">
			<tr class="tr_memberId">
				<td><span style="color: red;"></span>公司名称:</td>
				 <input type="hidden" id="memberId" name="memberId" value="${memberId }" />
				 <input type="hidden" id="userMobile" name="userMobile" value="${companyMobile }" />
				 <input type="hidden" id="userType" name="userType" value="" />
				<td>
				<!-- <input type="button" id="queryEntList" value="选择公司" > -->
				<input type="text" id="companyName" name="companyName" value="${companyName }" readonly="readonly"/></td>
			</tr>
			</c:if>
			
			<tr>
				<td style="width: 100px;">备注:</td>
				<td><textarea id="remark" name="remark"  maxlength="50"
					class="easyui-validatebox" 
					style="width: 150px">${remark} </textarea></td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/memberAddress/add.js"></script>
<script type="text/javascript" src="${CONTEXT}js/memberAddress/area.js"></script>

<script type="text/javascript">
	var sendGoodsType = $("#sendGoodsType").val();
	$(document)
			.ready(
					function() {
						if (sendGoodsType == '0') {
							$('#price').removeAttr("disabled");
							$("#priceUnitType").removeAttr("disabled");
							$("#div1").show();
							$("#div2").hide();
						} else if (sendGoodsType == '1') {
							$("#sendGoodsType").attr("value", "1");
							$("#price").removeAttr("disabled");
							$('#priceUnitType').attr('disabled', 'true');
							$("#div1").hide();
							$("#div2").show();
						} else {
							$("#div1").hide();
							$("#div2").show();
							$('#price').attr('disabled', 'true');
							$('#priceUnitType').attr('disabled', 'true');
						}


					});
</script>

