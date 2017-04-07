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
					<td class="mleft">用户账号:</td>
					<td class="mright">
						<c:if test="${!empty dto.memberId}">
							<input  type="text" id="account"  name="account" readonly   value="${dto.account}"  style="width:150px" >
					    </c:if>
					    <c:if test="${empty dto.memberId}">系统生成</c:if>
					</td>
					<td class="mleft" >真实姓名:</td>
					<td class="mright">
						<input  type="text" id="realName"  name="realName"    value="${dto.realName}" class="easyui-validatebox" style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >昵称:</td>
					<td class="mright">
						<input  type="text" id="nickName"  name="nickName" class="easyui-validatebox"   value="${dto.nickName}"  style="width:150px" >
					</td>
					<td class="mleft" >
						<span style="color:red">*</span>手机号:</td>
					<td class="mright">
						<input  type="text"  id="mobile" value="${dto.mobile}"  required="true"  missingMessage="请输入11位手机号" class="easyui-validatebox" validType="phoneNum"  name="mobile"     style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >身份证号:</td>
					<td class="mright">
 						<input disabled="disabled"  type="text" id="idCard"  name="idCard"  value="${dto.idCard}"    style="width:150px" >
				    </td>
					<td class="mleft" >&nbsp;</td>
					<td class="mright">&nbsp;
						<select name="isAuth" id="isStatus" style="display:none;">
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
					<td class="mleft" >用户类型:</td>
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
					<td class="mleft" >激活状态:</td>
					<td class="mright">
						<select name="status">
							<!-- 0未启用，1已启用 ,无值也表示未启用 -->	
							<option value="1" <c:if test="${dto.status==1 }">selected</c:if>>启用</option>
 							<option value="0" <c:if test="${dto.status==0 || (empty dto.status && !empty dto.memberId )}">selected</c:if>>禁用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft" >农商友用户类型:</td>
					<td class="mright">
						<select name="nsyUserType"  id="nsyUserType"  >
						   <option value="">请选择</option>
							<option value="1" <c:if test="${dto.nsyUserType==1 }">selected</c:if>   >下游采购商</option>
							<option value="2" <c:if test="${dto.nsyUserType==2 }">selected</c:if> >生鲜超市</option>
							<option value="3" <c:if test="${dto.nsyUserType==3 }">selected</c:if>  >学校食堂</option>
							<option value="4" <c:if test="${dto.nsyUserType==4 }">selected</c:if>  >食品加工厂</option>
							<option value="5" <c:if test="${dto.nsyUserType==5 }">selected</c:if>  >社区门店</option>
							<option value="5" <c:if test="${dto.nsyUserType==6 }">selected</c:if>  >餐饮业者</option>
							<option value="5" <c:if test="${dto.nsyUserType==7 }">selected</c:if>  >生鲜电商</option>
							<option value="5" <c:if test="${dto.nsyUserType==8 }">selected</c:if>  >其它</option>
						</select>
					</td>
					<td class="mleft" ><span style="color:red">*</span>密码:</td>
					<td class="mright">
						<input  type="password"  id="password" name="password"   style="width:150px"
 						 	<c:if test="${!empty dto.memberId}">readonly="readonly"</c:if>
						 	<c:if test="${empty dto.memberId}"> 
						 		required="true" missingMessage="请输入密码，长度6-20位" class="easyui-validatebox" validType="checkpw"
						 	</c:if>
						 >
						 <c:if test="${!empty dto.memberId}">
						 	<input type="button" onclick="setPassword();" value="重置" />
						 </c:if>
					</td>
				</tr>
				<tr>
					<td class="mleft" >注册来源：</td>
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
							<c:when test="${dto.regetype==9 }">农速通-货主</c:when>
							<c:when test="${dto.regetype==10 }">农速通-司机</c:when>
							<c:when test="${dto.regetype==11 }">农速通-物流公司</c:when>
							<c:otherwise>未知来源</c:otherwise>
						</c:choose>
					</td>
					<td colspan="1">注册时间：</td>
					<td colspan="3"><fmt:formatDate value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
			<c:choose>
        		<c:when test="${ empty dto.memberId}">
	        		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="nextStep()">
		        		 下一步 
	        		</a>
        		</c:when>
        		<c:otherwise>
	        		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">
		        		 保存 
	        		</a>
        		</c:otherwise>
        	</c:choose>
        </div>  
     	<div title="会员资质" id="divMore" <c:if test="${ empty dto.memberId}">style="display:none"</c:if>  >
			<c:if test="${!empty dto.level}">
 				<input type="hidden" id="level" name="level"    value="${dto.level}"/>
			</c:if>
    		<!-- 企业经营信息： -->
			<table>
				<tr>
					<td class="mleft" >商铺名称:</td>
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
					<td class="mleft" >所属市场:</td>
					<td class="mright">
						<table>
						<c:if test="${dto.level!=4 }">
							<tr id="mar0"  >
								<td class="mleft" ></td>
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
				<tr>
					<td class="mleft" >会员等级:</td>
					<td id="memberGradeTd" class="mright" colspan="3">
						<c:if test="${dto.level==4 }">
							<input id="memberGradeOld" name="memberGradeOld" type="hidden" value="${dto.memberGrade}"/>
							<select name="memberGrade" onchange="changeMemberGrade(this.value);">
								<option value="0" <c:if test="${dto.memberGrade==0}">selected</c:if>>普通会员</option>
	 							<option value="1" <c:if test="${dto.memberGrade==1}">selected</c:if>>金牌供应商</option>
							</select>
						</c:if>
						<c:if test="${dto.level!=4 }"><span style='font-size: 12px'>普通会员</span></c:if>
					</td>
				</tr>
				<tr>
					<td class="mleft" >会员有效期:</td>
					<td class="mright" colspan="3" style="font-size: 12px">
						<c:if test="${!empty dto.validTime}"><fmt:formatDate value="${dto.validTime}" pattern="yyyyMMdd"/>-<fmt:formatDate value="${dto.expireTime}" pattern="yyyyMMdd"/></c:if>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						<input type="hidden" name="shopRecommend" value="">
						<input id="shopRecommend" type="checkbox" <c:if test="${empty dto.memberId ||  dto.shopRecommend == 1}">checked="checked"</c:if>>商铺推荐
					</td>
					<td class="mright" colspan="3"> &nbsp;</td>
				</tr>
				<c:if test="${dto.level==4 }">
				<tr id="changeDescriptionTr" style="display:none">
					<td class="mleft" >
						<font color="red">*</font>变更说明:
					</td>
					<td class="mright" colspan="3" style="font-size: 12px">
						<textarea id="changeDescription" name="changeDescription" rows="3" cols="50"></textarea>
					</td>
				</tr>
				</c:if>
			</table>
			<!-- <br>
			认证信息： -->
			<table style="display:none;">
			<c:if test="${!empty dto.memberId }"> 
				<tr>
					<td class="mleft" >
					用户类型(物流):</td>
					<td class="mright">
						<input type="radio"  name="userType" id="userType" value="1" <c:if test="${dto.userType!=2 }">checked</c:if> onclick="showCompany(this.value)" >个人
						<input type="radio"  name="userType" id="userType" value="2"  <c:if test="${dto.userType==2 }">checked</c:if> onclick="showCompany(this.value)" >企业
					</td>
				</tr>
				<tr id="company">
					<td class="mleft" >企业名称(物流):</td>
					<td class="mright">
						<input  type="text"  id="companyName" value="${dto.companyName}"   name="companyName"     style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >常用城市(物流):</td>
					<td class="mright">
						<input  type="hidden"  id="ccityId"    name="ccityId"  value="${dto.ccityId}"   style="width:150px" >
						<!-- cityName用于传递城市名称，懒得再写一个cityName -->
						<input  type="text"  id="cityName"    name="cityName"   value="${dto.cityName}"    style="width:150px" >
					</td>
				</tr>
			</c:if>
			</table>
        	<gd:btn btncode='BTNHYGLHYLB11'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a></gd:btn>
		</div>
        <div title="银行卡号"  <c:if test="${ empty dto.memberId}">style="display:none"</c:if>>
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
        <div title="钱包" <c:if test="${ empty dto.memberId}">style="display:none"</c:if>>
	        <div style="margin-top: 10px;">
     			<table>
     				<tr>
     					<td class="mleft">用户账号：</td>
						<td class="mright">
							<input disable type="text"   value="${accInfoDTO.accId}"  class="easyui-validatebox"  style="width:150px" readOnly="true" >
						</td>
					</tr>
					<tr>
						<td class="mleft">真实姓名：</td>
						<td class="mright">
							<input disable type="text" value="${accInfoDTO.realName}" class="easyui-validatebox"  style="width:150px" readOnly="true" >
						</td>
					</tr>
					<tr>
						<td class="mleft">身份证号码：</td>
						<td class="mright">
							<input disable type="text"   value="${accInfoDTO.idCard}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
						</td>
					</tr>
					<tr>
						<td class="mleft">账户余额：</td>
						<td class="mright">
							<input disable type="text"  value="${accInfoDTO.balTotal}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
						</td>
					</tr>
					<tr>
						<td class="mleft">可用余额：</td>
						<td class="mright">
							<input disable type="text"  value="${accInfoDTO.balAvailable }"  class="easyui-validatebox"  style="width:150px" readOnly="true">
						</td>
					</tr>
					<tr>
						<td class="mleft">冻结余额：</td>
						<td class="mright">
							<input disable type="text"  value="${accInfoDTO.balblock}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
						</td>
					</tr>
					<tr>
						<td class="mleft">待补贴金额：</td>
						<td class="mright">
							<input disable type="text"  value="${accInfoDTO.subAmountStay}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
						</td>
					</tr>
					<tr>
						<td class="mleft">已补贴金额：</td>
						<td class="mright">
							<input disable type="text" value="${accInfoDTO.subAmountPaid}"  class="easyui-validatebox"  style="width:150px" readOnly="true">
						</td>
					</tr>
     			</table>
			</div>
        </div>  
        <div title="交易信息" <c:if test="${ empty dto.memberId}">style="display:none"</c:if>>
	        <jsp:include page="memberBaseinfo_waletInfo.jsp"></jsp:include>
        </div>
        <div title="用户日志" <c:if test="${ empty dto.memberId}">style="display:none"</c:if>>
	        <jsp:include page="memberChangeLog_list.jsp"></jsp:include>
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
	    },
	    phoneNum: { //验证手机号   
	        validator: function(value, param){ 
	         return /^1[3-8]+\d{9}$/.test(value);
	        },    
	        message: '请输入正确的手机号码。'   
	    },
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
		var content = "<select name='memberGrade' onchange='changeMemberGrade(this.value);'><option value='0' selected>普通会员 </option><option value='1'>金牌供应商 </option></select>";
		$("#memberGradeTd").html(content);
	}else{
		document.getElementById("mar0").style.display="";
		$("#memberGradeTd").html("<span style='font-size: 12px'>普通会员</span>");
	}
}
function changeMemberGrade(val){
	if($("#memberGradeOld").length > 0){
		if($("#memberGradeOld").val() != val){
			$("#changeDescriptionTr").show();
		} else {
			$("#changeDescriptionTr").hide();
		}
	}
}
function setPassword(){
	
	  var r=confirm("确认要重置密码？")
	  if (r==true){
			document.getElementById("password").value="888888";
	    }
	
}

</script>


<script type="text/javascript" src="${CONTEXT}js/member/city.js"></script>
<script>
 	$(document).ready(function() {
 		var city = new City($("#provinceId"),$("#cityId"),$("#areaId"));
 		city.init('${dto.provinceId}','${dto.cityId}','${dto.areaId}');
 		
 	});
					 	
					 	
</script>

