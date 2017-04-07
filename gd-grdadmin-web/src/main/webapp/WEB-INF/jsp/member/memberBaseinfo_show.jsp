<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>

<form id="addForm" method="post" action="#">
	    <div id="tt" class="easyui-tabs">  
        <div title="用户信息">	
			<input type="hidden" id="memberId" name="memberId"    value="${dto.memberId}"/>
			<table>
				<tr>
					<td class="mleft"  >
						用户账号:
					</td>
					<td class="mright">
						<input disable type="text" id="account"  name="account"   value="${dto.account}" required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
					<td class="mleft" >
						真实姓名:
					</td>
					<td class="mright">
						<input  type="text" id="realName"  name="realName"   value="${dto.realName}" required="true"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						昵称:
					</td>
					<td class="mright">
						<input disable type="text" id="nickName"  name="nickName"   value="${dto.nickName}"   style="width:150px" >
					</td>
					<td class="mleft" >
					手机号:</td>
					<td class="mright">
						<input  type="text"  id="mobile" value="${dto.mobile}"   name="mobile"     style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						身份证号:
					</td>
					<td class="mright">
 						<input  disabled="disabled"  type="text" id="idCard"  name="idCard"   value="${dto.idCard}" required="true"  style="width:150px" >
					</td>
					<td class="mleft" >
						认证状态:
					</td>
					<td class="mright">
						<select name="isAuth" id="isStatus">
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_certifi 表得到）
							 -->
							<c:if test="${empty dto.isAuth}">						
							<option value="">请选择</option>
							</c:if>
 							<option value="1" <c:if test="${!empty dto.isAuth && dto.isAuth==1 }">selected</c:if>>已认证</option>
							<option value="0" <c:if test="${!empty dto.isAuth && dto.isAuth==0 }">selected</c:if>>待认证</option>
							<option value="2" <c:if test="${!empty dto.isAuth && dto.isAuth==2 }">selected</c:if>>已驳回</option>
							<c:if test="${!empty dto.isAuth}">						
							<option value="-1">未提交认证</option>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						用户类型:
					</td>
					<td class="mright">
						<c:choose>
						<c:when test="${ empty dto.memberId}">
						<select name="level"  id="level"  onchange="changeLevel(this.value);">
							<option value="1" <c:if test="${dto.level==1 }">selected</c:if>   >谷登农批</option>
							<option value="2" <c:if test="${dto.level==2 }">selected</c:if> >农速通</option>
							<option value="3" <c:if test="${dto.level==3 }">selected</c:if>  >农商友</option>
							<option value="4" <c:if test="${dto.level==4 }">selected</c:if>  >产地供应商</option>
							<option value="5" <c:if test="${dto.level==5 }">selected</c:if>  >农批友</option>
						</select>
						</c:when>
						<c:otherwise>
						<input type="hidden" id="level" name="level"    value="${dto.level}"/>
						 <c:if test="${dto.level==1 }">谷登农批</c:if> 
						 <c:if test="${dto.level==2 }">农速通</c:if> 
						 <c:if test="${dto.level==3 }">农商友</c:if> 
						 <c:if test="${dto.level==4 }">产地供应商</c:if> 
						 <c:if test="${dto.level==5 }">农批友</c:if> 
						</c:otherwise>
						</c:choose>
					</td>
					<td class="mleft" >
						激活状态:
						</td>
						<td class="mright">
							<select name="status">
								<!-- 0未启用，1已启用 ,无值也表示未启用 -->	
								<option value="1" <c:if test="${dto.status==1 }">selected</c:if>>启用</option>
	 							<option value="0" <c:if test="${dto.status==0 || (empty dto.status && !empty dto.memberId )}">selected</c:if>>禁用</option>
							</select>
					</td>
				</tr>
				<tr>
				<td class="mleft" >
						农商友用户类型:
					</td>
					<td>
				 		<c:if test="${dto.nsyUserType==1 }">下游采购商</c:if> 
						 <c:if test="${dto.nsyUserType==2 }">生鲜超市</c:if> 
						 <c:if test="${dto.nsyUserType==3 }">学校食堂</c:if> 
						 <c:if test="${dto.nsyUserType==4 }">食品加工厂</c:if> 
						 <c:if test="${dto.nsyUserType==5 }">社区门店</c:if>
						 <c:if test="${dto.nsyUserType==6 }">餐饮业者</c:if> 
						 <c:if test="${dto.nsyUserType==7 }">生鲜电商</c:if> 
						 <c:if test="${dto.nsyUserType==8 }">其他</c:if>  
					 </td>
					<td class="mleft" >
						密码:
					</td>
					<td class="mright">
						<input  type="password"  id="password" value="" 				
						 required="true" missingMessage="请输入密码，长度6-20位" class="easyui-validatebox" validType="checkpw"
						 name="password"   style="width:150px" >
					</td>
					</tr>
					<tr>
					<td class="mleft" >
					注册来源：
					</td>
					<td class="mright">
					<c:choose>
					<c:when test="${dto.regetype==0 }">管理后台</c:when>
					<c:when test="${dto.regetype==1 }">谷登农批网</c:when>
					<c:when test="${dto.regetype==2 }">农速通</c:when>
					<c:when test="${dto.regetype==3 }">农商友</c:when>
					<c:when test="${dto.regetype==4 }">农商友-农批商</c:when>
					<c:when test="${dto.regetype==5 }">农批友</c:when>
					<c:when test="${dto.regetype==6 }">供应商</c:when>
					<c:when test="${dto.regetype==7 }">POS刷卡</c:when>
					<c:when test="${dto.regetype==8 }">微信注册用户</c:when>
					<c:otherwise>未知来源</c:otherwise>
					</c:choose>
					</td>
				<td colspan="1">注册时间：</td>
				<td colspan="3"><fmt:formatDate value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
         </div>  
         <div title="会员资质">
    		 企业经营信息：
			<table>
				<tr>
					<td class="mleft" >
						商铺名称:
					</td>
					<td class="mright">
						<c:choose>
 						<c:when test="${ empty bdto.shopsName}">
 						<input  type="text" id="shopName"  name="shopName"  value="" maxlength="50"   style="width:150px" >
 						</c:when>
 						<c:otherwise>
 						<input  type="hidden" id="shopName"  name="shopName"  value="${bdto.shopsName}" style="width:150px" >
						${bdto.shopsName}
 						</c:otherwise>
 						</c:choose>
					</td>
					<td class="mleft" >
						所属市场:
					</td>
					<td class="mright">
						<table>
						<c:if test="${dto.level!=4 }">
							<tr  id="mar0"  >
								<td class="mleft" >
								</td>
								<td class="mright">
			 						<c:choose>
			 						<c:when test="${ empty mdto.marketName}">
			 						<input type="button" id="showMarketWin" value="选择市场"> 
									<input type="hidden" id="marketId_add" name="marketId" id="marketId" value="" >
			 						</c:when>
			 						<c:otherwise>
			 						${mdto.marketName}
			 						</c:otherwise>
			 						</c:choose>
								</td>
							</tr>
							</c:if>
						</table>
					</td>
				</tr>
				<%-- <tr>
					<td  class="mleft">
					地址：
					</td>
					<td class="mright">
						<select name="provinceId_show" id="provinceId_show"  class="i-app-far-mar-c3" >
						</select>			 	
						<select name="cityId_show" id="cityId_show"  class="i-app-far-mar-c3">
						</select>
						<select name="areaId_show" id="areaId_show"  class="i-app-far-mar-c3">
						</select>
					</td>
					<td>
					</td>
					<td class="mright">
						<input class="i-app-far-mar-ph" type="text"  value="${dto.address}" maxlength="200"   name="address" id="address"/>
					</td>
				</tr>	 --%>	
				</table>
				<br>
				认证信息：
				<table>
				<c:if test="${!empty dto.memberId }">
					<tr>
						<td class="mleft" >
						用户类型(物流):</td>
						<td class="mright">
							<input type="radio"  name="userType" id="userType" value="1" <c:if test="${dto.userType!=2 }">checked</c:if> onclick="showCompany(this.value)" >个人
							<input type="radio"  name="userType" id="userType" value="2"  <c:if test="${dto.userType==2 }">checked</c:if> onclick="showCompany(this.value)" >企业
							
						<%-- 	<input type="hidden" name="userType" id="userType" value="${dto.userType}" />
							<c:choose>
							<c:when test="${dto.userType==2 }">企业</c:when>
							<c:otherwise>个人</c:otherwise>
							</c:choose> --%>
						</td>
					</tr>
					<tr id="company">
						<td class="mleft" >
						企业名称(物流):</td>
						<td class="mright">
							<input  type="text"  id="companyName" value="${dto.companyName}"   name="companyName"     style="width:150px" >
						</td>
					</tr>
					<tr>
						<td class="mleft" >
						常用城市(物流):</td>
						<td class="mright">
							<input  type="hidden"  id="ccityId"    name="ccityId"  value="${dto.ccityId}"   style="width:150px" >
							<!-- cityName用于传递城市名称，懒得再写一个cityName -->
							<input  type="text"  id="cityName"    name="cityName"   value="${dto.cityName}"    style="width:150px" >
						</td>
					</tr>
				</c:if>
				
				</table>
				
		</div>
        <div title="银行卡号">
			<table class="easyui-datagrid" data-options="url:'${CONTEXT}member/queryCarNo?memberId=${memberId}&regetype=${dto.regetype}',fitColumns:true,singleSelect:true">
			<thead>
			<tr>
					<th data-options="field:'realName',resizable:false,align:'center'" width="180">持卡人姓名</th>
					<th data-options="field:'idCard',resizable:false,align:'center'" width="200">持卡人身份证号码</th>
					<th data-options="field:'depositBankName',resizable:false,align:'center'" width="200">开户行</th>
					<th data-options="field:'bankCardNo',resizable:false,align:'center'" width="200">银行卡号</th>
			</tr> 
			</thead>
			</table>
		 </div>  
        <div title="钱包">
        <div style="margin-top: 10px;">
        			<table>
        				<tr>
							<td class="mleft"  >
								用户账号：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.accId}"  class="easyui-validatebox"  style="width:150px" readOnly="true" >
							</td>
						</tr>
						<tr>
							<td class="mleft"  >
								真实姓名：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.realName}" class="easyui-validatebox"  style="width:150px" readOnly="true" >
							</td>
						</tr>
						<tr>
							<td class="mleft"  >
								身份证号码：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.idCard}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
							</td>
						</tr>
						<tr>
							<td class="mleft"  >
								账户余额：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.balTotal}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
							</td>
						</tr>
						<tr>
							<td class="mleft"  >
								可用余额：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.balAvailable }"  class="easyui-validatebox"  style="width:150px" readOnly="true">
							</td>
						</tr>
						<tr>
							<td class="mleft"  >
								冻结余额：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.balblock}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
							</td>
						</tr>
						<tr>
							<td class="mleft"  >
								待补贴金额：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.subAmountStay}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
							</td>
						</tr>
						<tr>
							<td class="mleft"  >
								已补贴金额：
							</td>
							<td class="mright">
								<input disable type="text" id="account"  name="account"   value="${accInfoDTO.subAmountPaid}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
							</td>
						</tr>
        			</table>
				</div>
        </div>  
         <div title="交易信息">
		        <jsp:include page="memberBaseinfo_waletInfo.jsp"></jsp:include>
		        </div>
    </div>  
</form>






<script type="text/javascript" >

$(function(){
	var userType = $("input[name='userType']:checked").val();
	showCompany(userType);
	
	$.extend($.fn.validatebox.defaults.rules, {
	    checkpw: {
	        validator: function(value, param) {
				var len = value.length;
				if(len==0){
					return false;
				}
				else if(len<6||len>20){
					return false;
				}else{
					return true;
				}
	        },
	        message: '请重新输入密码，可以是数字、英文、特殊符号或组合'
	    }
	});
})

function showCompany(userType){
	if(userType==2){
		//document.getElementById("company").style.display="block";
		$("#company").show();
	}else{
		$("#company").hide();
		//document.getElementById("company").style.display="none";
	}
}


function marketSelectCallBack(index,row){
	$("#marketId_add").val(row.id);
	$("#showMarketWin").val(row.marketName);
	$('#marketDialog').dialog('close');
}


$('#showMarketWin').click(function(){
	$('#marketDialog').dialog({'title':'选择街市', 'width':700, 'height':300, 'href':CONTEXT+'product/marketSelect'}).dialog('open');
});



function changeLevel(val){
	if(val==4){
		document.getElementById("mar0").style.display="none";
		$("#marketId_add").val(1);
	}else{
		document.getElementById("mar0").style.display="";
	}
	
}

</script>


<script type="text/javascript" src="${CONTEXT}js/member/city.js"></script>
<script>
 	$(document).ready(function() {
 		var city = new City($("#provinceId_show"),$("#cityId_show"),$("#areaId_show"));
 		city.init('${dto.provinceId}','${dto.cityId}','${dto.areaId}');
 		
 	});
					 	
					 	
</script>
