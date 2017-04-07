<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>补贴限制规则</title>
	</head>
<body>
<form id="subForm" method="post" action="${CONTEXT }sublimitrule/saveOrUpdate">
	<c:if test="${subLimitRule.status eq '1'}">
	<input name="ruleId" type="hidden" value="${subLimitRule.ruleId }" /></c:if>
<c:if test="${subLimitRule.status eq '0' }">
	<input name="ruleId" type="hidden" value="" /></c:if>	
	<div>
		<table style="width: 100%; height: 200px; font-size: 12px;border-spacing:10px;padding-left:200px;">
			<tr>
             <td class="left" style="width: 200px;" colspan="2">
             	市场：
             	<c:if test="${!empty markets }">
             		<select id="marketId" name="marketId" class="text_sketch" style="width: 200px;">
             			<c:forEach items="${markets }" var="market">
             				<c:if test="${subLimitRule.marketId eq market.id}"><option value="${market.id }" selected='selected'>${market.marketName }</option></c:if>
             			</c:forEach>
             		</select>
             	</c:if>
             </td>
            </tr>
            <tr>
             <td class="left" style="width: 200px;" colspan="2">
             	补贴周期：
             	<input type="text" id="timeStart" name="timeStartStr" <c:if test="${empty subLimitRule.status or subLimitRule.status eq '0'}"> onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){timeStart.focus();},minDate:'%y-%M-#{%d+1}',maxDate:'#F{$dp.$D(\'timeEnd\')}'})"</c:if> class="easyui-validatebox validatebox-text" data-options="required:true" style="width:120px" <c:if test="${subLimitRule.status eq '1' }">readonly="readonly"</c:if> value="${fn:substring(subLimitRule.timeStartStr,0,10) }">
             	~
				<input type="text" id="timeEnd" name="timeEndStr" <c:if test="${empty subLimitRule.status or subLimitRule.status eq '0'}">onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){timeEnd.focus();},minDate:'#F{$dp.$D(\'timeStart\',{d:2})}'})"</c:if> class="easyui-validatebox validatebox-text" data-options="required:true" style="width:120px" <c:if test="${subLimitRule.status eq '1' }">readonly="readonly"</c:if>  value="${fn:substring(subLimitRule.timeEndStr,0,10) }"  >
             </td>
            </tr>
            <tr>
             <td class="left" style="width: 200px;" colspan="2">
             	补贴总额发放超过<input id="subAmout_id" name="subAmount" type="text" class="easyui-validatebox validatebox-text" data-options="required:true,validType:'checkAmt'" style="width:100px" <c:if test="${subLimitRule.status eq '1'}">readonly="readonly"</c:if> value="${subLimitRule.subAmountView }" >元，自动终止补贴项目
             </td>
            </tr>
          <c:forEach items="${subLimitRule.subRangeLimitRules }" var="subRangeLimitRule" varStatus="st">
				<c:if test="${subRangeLimitRule.limitType ne subLimitRule.subRangeLimitRules[st.index-1 ].limitType}">
					<c:choose>
<%-- 						<c:when test="${subRangeLimitRule.limitType eq '1' }">
							<tr>
				             <td class="left" style="width: 200px;"><input pid="cbx_${subRangeLimitRule.limitType }" name="vehLimit" type="checkbox" value="1" <c:if test="${subLimitRule.vehLimit eq '1' }">checked='checked'</c:if> />同一车辆进出市场补贴次数不超过</td>
				            </tr>
						</c:when> --%>
						<c:when test="${subRangeLimitRule.limitType eq '2' }">
							<tr>
				             <td class="left" style="width: 200px;"><input pid="cbx_${subRangeLimitRule.limitType }" name="uamountLimit" type="checkbox" value="1" <c:if test="${subLimitRule.uamountLimit eq '1' }">checked='checked'</c:if> />单一用户补贴上限管理</td>
				            </tr>
						</c:when>
						<c:when test="${subRangeLimitRule.limitType eq '3' }">
							<tr>
				             <td class="left" style="width: 200px;"><input pid="cbx_${subRangeLimitRule.limitType }" name="utimesLimit" type="checkbox" value="1" <c:if test="${subLimitRule.utimesLimit eq '1' }">checked='checked'</c:if>/>单一用户总交易频率管理</td>
				            </tr>
						</c:when>
						<c:when test="${subRangeLimitRule.limitType eq '4' }">
							<tr>
				             <td class="left" style="width: 200px;"><input pid="cbx_${subRangeLimitRule.limitType }" name="twoUtimesLimit" type="checkbox" value="1" <c:if test="${subLimitRule.twoUtimesLimit eq '1' }">checked='checked'</c:if> />两个用户间交易频率管理</td>
				            </tr>
						</c:when>
						<c:when test="${subRangeLimitRule.limitType eq '5' }">
							<tr>
				             <td class="left" style="width: 200px;"><input pid="cbx_${subRangeLimitRule.limitType }" name="tamountLimit" type="checkbox" value="1" <c:if test="${subLimitRule.tamountLimit eq '1' }">checked='checked'</c:if> />补贴总额管理</td>
				            </tr>
						</c:when>
					</c:choose>
				</c:if>
				<tr>
		      	 <td></td>
	             <td class="left">
	             	 <input name="subRangeLimitRules[${st.index }].ruleId" type="hidden" value="${subRangeLimitRule.ruleId }" />
	             	 <input name="subRangeLimitRules[${st.index }].limitType" type="hidden" value="${subRangeLimitRule.limitType }" />
<c:if test="${subRangeLimitRule.limitType>1 }"><!-- 暂时先隐藏  同一车辆进出市场补贴次数不超过次数限制  20160112 -->
	             	 <c:choose>
		             	<c:when test="${ subRangeLimitRule.limitType eq 3 ||  subRangeLimitRule.limitType eq 4}">
		             		<input id="mod_${subRangeLimitRule.limitType }_${st.index}" pid="mod_${subRangeLimitRule.limitType }" index="${st.index }" name="subRangeLimitRules[${st.index }].count" type="text" maxlength="8" class="easyui-validatebox validatebox-text" 
		             		<c:choose>
		             			<c:when test="${st.index == 0 || st.index==7 || st.index==11 }">validType="checkCount"</c:when>
		             			<c:otherwise>validType="checkCount['#mod_${subRangeLimitRule.limitType }_${st.index-1}']"</c:otherwise>
		             		</c:choose> 
		             		style="width:100px" value="${subRangeLimitRule.count }" >次
		             	</c:when>
		             	<c:otherwise>
		             		<input id="mod_${subRangeLimitRule.limitType }_${st.index}" pid="mod_${subRangeLimitRule.limitType }" name="subRangeLimitRules[${st.index }].amount" type="text" maxlength="8" class="easyui-validatebox validatebox-text" 
		             		<c:choose>
		             			<c:when test="${st.index == 3 || st.index==14 }">validType="checkAmt"</c:when>
		             			<c:otherwise>validType="checkAmt['#mod_${subRangeLimitRule.limitType }_${st.index-1}']"</c:otherwise>
		             		</c:choose> 
		             		style="width:100px" value="${subRangeLimitRule.amountView }">元
		             	</c:otherwise>
		             </c:choose>
		             /
		             <input name="subRangeLimitRules[${st.index }].timeRange" type="hidden" value="${subRangeLimitRule.timeRange }" /> 
		             <c:choose>
		             	<c:when test="${subRangeLimitRule.timeRange eq 1 }">
		             		每天
		             	</c:when>
		             	<c:when test="${subRangeLimitRule.timeRange eq 2 }">
		             		每周
		             	</c:when>
		             	<c:when test="${subRangeLimitRule.timeRange eq 3 }">
		             		每月
		             	</c:when>
		             	<c:when test="${subRangeLimitRule.timeRange eq 4 }">
		             		整个活动期内
		             	</c:when>		             	
		             </c:choose>
</c:if>		             
		         </td>
	          	</tr>
		  </c:forEach>
<!-- 白名单 -->
				<tr>
				   <td class="left" style="width: 200px;"><input id="whitechk" pid="cbx_" name="whiteLimit" type="checkbox" value="1" <c:if test="${subLimitRule.whiteLimit eq '1' }">checked='checked'</c:if> />启用白名单</td>
				</tr>	
				<tr>
				   <td class="left" style="width: 500px;color:red;">白名单用户将不受系统审核规则限制，请慎重添加！(填用户名)</td>
				 </tr>				            
				<tr>
					<td class="mright">
					<textarea id="whiteList" placeholder="请输入不受系统审核规则限制的用户账号，用,隔开；任何添加操作都将被记录！" name="whiteList" style="width:400px;height:300px;"></textarea>
					</td>
					<td class="mright">
					<div id="condiv" name="content2" style="width:400px;height:300px;    border: solid 1px #A0A0A0;    overflow-y: scroll;">
					
				<c:if test="${!empty subLimitRule.wlist}">
				<c:forEach items="${subLimitRule.wlist }" var="item" varStatus="windex">
				<span style="display: block; float: left; margin-left: 15px;border: solid 1px #444; margin-top: 10px; padding: 8px;" id="wspan${item.memberId}">${item.account } <a href="javascript:delW(${subLimitRule.ruleId},${item.memberId})">删除</a></span>				
				</c:forEach>
				</c:if>					
					</div>
					</td>
				</tr>
				<c:if test="${!empty subLimitRule.contacts}">
				<c:forEach items="${subLimitRule.contacts }" var="record" varStatus="cindex">
				<tr>
				   <td class="left" style="width: 200px;">紧急联系人
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <input  type="text" id=""  name="contact"   value="${record}"  maxlength="20" class="contact_c"   style="width:150px">
				   <c:if test="${cindex.index>0 }">--<span><a href="javascript:void(0)" class="row-del">删除</a></span></c:if>
				   </td>
				   <td class="left" style="width: 200px;color:red;"><c:if test="${cindex.index eq 0 }">若出现紧急情况，系统会以邮件的方式通知联系人</c:if></td>
				</tr>
				</c:forEach>
				</c:if>
				<c:if test="${empty subLimitRule.contacts}">
				<tr>
				   <td class="left" style="width: 200px;">紧急联系人
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <input  type="text" id=""  name="contact"  placeholder="填写邮箱"   value=""  maxlength="20" class="contact_c"  style="width:150px">
				   </td>
				   <td class="left" style="width: 200px;color:red;">若出现紧急情况，系统会以邮件的方式通知联系人</td>
				</tr>
				</c:if>				
				<tr id="addContact">
					<td class="mleft"  >
					<a href="javascript:addContact();"> 添加更多联系人</a>
					</td>
					<td class="mright">
 					</td>
				</tr>							  
		<c:if test="${type eq '2' }">	  
          <tr>
             <td colspan="2" align="center">
             	<div>
             		<gd:btn btncode='BTNBTGZGLBTXZGZ01'><a class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-save" id="btn-save">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</gd:btn>
             		<c:choose >
             			<c:when test="${empty subLimitRule.ruleId or subLimitRule.status eq '0' }" >
             				<a class="easyui-linkbutton icon-reload-btn" iconcls="icon-reload" id="btn-reset">重置</a>
             			</c:when>
             			<c:otherwise>
             			<gd:btn btncode='BTNBTGZGLBTXZGZ03'><a class="easyui-linkbutton icon-reload-btn" iconcls="icon-edit" onclick="forbidden(${subLimitRule.ruleId},${subLimitRule.marketId })" id="btn-reset">禁用</a></gd:btn>
             			</c:otherwise>
             		</c:choose>	
             	</div>
             </td>
          </tr>
          </c:if>
		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/formValidatorRegex.js"></script>
<script type="text/javascript" src="${CONTEXT}js/sub/easyui-extend-sublimitrule.js"></script>
<script type="text/javascript" src="${CONTEXT}js/sub/sublimitrule.js"></script>
</body>
</html>