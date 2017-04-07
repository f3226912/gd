<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
td{
    padding-left: 12px;
}
</style>
<form id="addForm" method="post" action="cars/save">
	<div>
		<table style="width: 100%; height: 200px;">
		 <tr>
				<td><input type="hidden" id="id" name="id"
					value="${id}"  style="width: 150px"></td>
		</tr>
		<tr>
				<td style="width: 100px;"><span style="color:red;">*</span>车牌号码:</td>
				<td><input type="text" id="number" name="number"
					value="${carNumber}" required="true" class="easyui-validatebox"
					missingMessage="车牌号码必须填写" style="width: 150px"></td>
	    </tr>
	  
        <tr>
			<td style="width: 100px;"><span style="color:red;">*</span> 车辆类型:</td>
		 <td>
		 <input type="hidden" name="transportType" value="${transportType}">
		 <select id="carType" name="carType" class="text_sketch" >
		 <!-- 展示顺序： 1）厢式车2）敞车3）高栏车4）平板车5）冷藏车6）保温车7）活鲜水车8）集装箱9）其他 -->
		  <!-- 数据库值 0:厢式车;1:敞车;2:冷藏车;3:保温车;4:其他;5:高栏车6:平板车;7:活鲜水车;8:集装箱 -->
		   <c:if test="${transportType==0 || transportType==null}">
          <option <c:if test="${carType==0}"> selected="selected" </c:if> value="0">厢式货车</option>
          <option <c:if test="${carType==1}"> selected="selected" </c:if> value="1">敞车</option>
          <option <c:if test="${carType==5}"> selected="selected" </c:if> value="5">高栏车</option>
          <option <c:if test="${carType==6}"> selected="selected" </c:if> value="6">平板车</option>
          <option <c:if test="${carType==2}"> selected="selected" </c:if> value="2">冷藏车</option>
          <option <c:if test="${carType==3}"> selected="selected" </c:if> value="3">保温车</option>
          <option <c:if test="${carType==7}"> selected="selected" </c:if> value="7">活鲜水车</option>
          <option <c:if test="${carType==8}"> selected="selected" </c:if> value="8">集装箱</option>
          <option <c:if test="${carType==4}"> selected="selected" </c:if> value="4">其他</option>
          </c:if>
          <c:if test="${transportType==1}">
          <option <c:if test="${transportCarType==0}"> selected="selected" </c:if> value="0">小型面包</option>
          <option <c:if test="${transportCarType==1}"> selected="selected" </c:if> value="1">金杯</option>
          <option <c:if test="${transportCarType==2}"> selected="selected" </c:if> value="2">小型平板</option>
          <option <c:if test="${transportCarType==3}"> selected="selected" </c:if> value="3">中型平板</option>
          <option <c:if test="${transportCarType==4}"> selected="selected" </c:if> value="4">小型厢货</option>
          <option <c:if test="${transportCarType==5}"> selected="selected" </c:if> value="5">大型厢货</option>
          </c:if>
          </select>
	      </td>
	      </tr>
			
		   <tr>
		  		<td style="width: 100px;"><span style="color:red;">*</span> 载重:</td>
				<td><input type="text" id="maxLoad" name="maxLoad" maxlength="5"
						value="${maxLoad}" required="true" class="easyui-validatebox"
						missingMessage="必须填写" style="width: 150px">&nbsp;吨</td>
			</tr>

		     <tr>
				<td style="width: 100px;"><span style="color:red;">*</span> 车长:</td>
				<c:if test="${transportType==1}">
					<td><input type="text" id="carLength" name="carLength" maxlength="5"
					value="${carLength}" required="true" class="easyui-validatebox"
					missingMessage="必须填写" style="width: 150px">&nbsp;米</td>
				</c:if>
		   <td>
		  <c:if test="${transportType==0}">
				 <select id="carLength" name="carLength" class="text_sketch" >
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
		                    <option <c:if test="${carLength==16}"> selected="selected" </c:if> value="16">16米</option>
		                    <option <c:if test="${carLength==17}"> selected="selected" </c:if> value="17">17米</option>
		                    <option <c:if test="${carLength==17.5}"> selected="selected" </c:if> value="17.5">17.5米</option>
		                    <option <c:if test="${carLength==18}"> selected="selected" </c:if> value="18">18米</option> 
		         </select>
         </c:if>
	      </td>
			 </tr>
		<%-- 	<tr>
				<td style="width: 100px;"><span style="color:red;">*</span> 联系人:</td>
				<td>
				<input type="text" id="userName" name="userName" maxlength="30"
					value="${userName}" required="true" class="easyui-validatebox"
					missingMessage="必须填写" style="width: 150px">
				</td>
			</tr> --%>
			
			<tr>
				<td style="width: 100px;"><span style="color:red;">*</span> 电话:</td>
				<td>
				<input type="text" id="phone" name="phone" maxlength="11"
					value="${phone}" required="true" class="easyui-validatebox"
					missingMessage="必须填写" style="width: 150px">
				</td>
			</tr>
			
		</table>
	</div>
</form>
<script type="text/javascript">
function save() {
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	if(checkData())
	{
	 var url=CONTEXT+"cars/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#carsdg").datagrid('reload');
				$('#addDialog').dialog('close');
			}else if (data == "not exist") {
				warningMessage("联系人不存在，请重新输入！");
				return;
			 }else if(data=="carNumberExist"){
				 warningMessage("车牌号已经存在！");
				 return;
			 } else{
				warningMessage("系统异常！");
				return;
			}
		});
	}
}

function checkData()
{  
	var re = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
	var carNumber=$("#number").val();
	if (!re.test(carNumber)) {
		warningMessage("输入的车牌号格式不正确!");
		return false;
	}
	
	var weightReg =/^[0-9]+(.[0-9]{1,2})?$/;
	var maxLoad=$("#maxLoad").val();	
	if (!weightReg.test(maxLoad)) {
		warningMessage("载重请输入整数或两位小数!");
		return false;
	}
	
 	var lengthReg =/^[0-9]+(.[0-9]{1,1})?$/;
	var carLength=$("#carLength").val();
	if (!lengthReg.test(carLength)) {
		warningMessage("车长请输入整数或一位小数!");
		return false;
	}
	
	var type = "${transportType}" || 0;
	
	if(type == 0){	
		
		if (parseFloat(maxLoad) > 60) {
			warningMessage("载重最大值请小于60吨!");
			return false;
		}
	}
	if(type == 1){
		
		if (parseFloat(maxLoad) > 5) {
			warningMessage("载重最大值请小于5吨!");
			return false;
		}
		
		if (parseFloat(carLength) >4.2) {
			warningMessage("车长最大值请小于4.2米!");
			return false;
		}
	}	
	
  /*	var test3 =/^[\u0391-\uFFE5\w]+$/;
	var userName=$("#addForm #userName").val();
	if (!test3.test(userName)) {
		warningMessage("输入的联系人不能包含特殊字符!");
		return false;
	}*/
	
	var test4 =/^(1[0-9][0-9])\d{8}$/;
	var phone=$("#addForm #phone").val();
	if (!test4.test(phone)) {
		warningMessage("输入的手机号码格式不正确!");
		return false;
	}	
	return true;	
}
</script>













