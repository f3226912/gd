<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/constants.inc" %>

<style type="text/css">
	.esDialogMainDiv{border: thin solid #009DD9;text-align: center;min-height: 99%;overflow: visible;}
	.mark {color: red;}
	.widget{width: 300px;height: 20px;}
	.esDialogMainDiv table{line-height: 30px;width: 97%;margin: 0px auto;}
	#esDialogContext{width:600px;height:150px;}
</style>

<div class="esDialogMainDiv">
	<form id="esDialogForm">
		<input type="hidden" name="id" value="${requestScope.grdMemberDTO.id }"/>
		<table>
			<tr>
				<td align="right" width="90"><label class="mark">*</label>姓名：</td>
				<td align="left">
					<input id="esDialogName" name="name" type="text" class="widget easyui-validatebox" required="true" validtype="CHS"
						missingMessage="姓名不能为空" value="${requestScope.grdMemberDTO.name }" placeholder="请输入地推人员姓名"/>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="mark">*</label>手机：</td>
				<td align="left">
					<input id="esDialogMobile" name="mobile" type="text" class="widget easyui-validatebox" required="true" maxlength="11" validtype="mobile"
						missingMessage="手机号码不能为空" value="${requestScope.grdMemberDTO.mobile }" placeholder="请输入11位手机号码，用于登录"/>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="mark">*</label>所属市场：</td>
				<td align="left">
					<select id="esDialogMarketId" name="marketId"  style="height: 26px;width: 304px;" class="widget easyui-validatebox" required="true" missingMessage="必须选择所属市场">
						<option value="" >————请选择地推人员所属农批市场————</option>
						<c:forEach items="${requestScope.marketDTOs }" var="marketDTO">
							<option ${marketDTO.id == requestScope.grdMemberDTO.marketId ? "selected" : "" } value="${marketDTO.id }">${marketDTO.marketName }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
								
				<td align="right"><label class="mark">*</label>所属团队：
				<script type="text/javascript" >
						var currentTeamLength = 1;
					</script>	
				</td>
				<td align="left">
				<c:if test="${addOrEdit == 1}">
					<select name="teamGroup" style="height: 26px;width: 304px;" >
						<option value="" >————全部————</option>
					</select><a id="addTeamLink" href="javascript:void(0)" onclick="gudeng.grdMember.addTeam()">添加新团队</a>
				</c:if>
				<c:if test="${addOrEdit == 2}">
					<c:forEach items="${userteams}" var="curteam" varStatus="i">
						<c:if test="${i.index == 0}">
							<select name="teamGroup" style="height: 26px;width: 304px;" >
								<option value="" >————全部————</option>
								<c:forEach items="${giftteamList}" var="giftteam">
									<option ${giftteam.giftteamId == curteam.teamId ? "selected" : "" } 
										value="${giftteam.giftteamId }">${giftteam.giftteamName }</option>
								</c:forEach>
							</select><a id="addTeamLink" href="javascript:void(0)" onclick="gudeng.grdMember.addTeam()">添加新团队</a>
						</c:if>
					</c:forEach>
				</c:if>
				</td>
			</tr>			
			<c:forEach items="${userteams}" var="curteam" varStatus="i">
				<c:if test="${i.index > 0}">
					<script type="text/javascript" >
						currentTeamLength++;
					</script>
					<tr>
						<td align="right"><label class="mark"></label></td>
						<td align="left">
							<select name="teamGroup"  style="height: 26px;width: 304px;" >
								<option value="" >————全部————</option>
								<c:forEach items="${giftteamList}" var="giftteam">
									<option ${giftteam.giftteamId == curteam.teamId ? "selected" : "" } 
										value="${giftteam.giftteamId }">${giftteam.giftteamName }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
			</c:forEach>
			<tr id="markTr">
				<td valign="top" align="right">备注：</td>
				<td align="left">
					<textarea id="esDialogContext" name="context" >${requestScope.grdMemberDTO.context }</textarea>
				</td>
			</tr>
		</table>
		<!-- <input type="hidden" name="teamIds"> -->
	</form>
</div>



<script type="text/javascript" src="${CONTEXT}js/grdMember/grd_member_editor_save.js"></script>











