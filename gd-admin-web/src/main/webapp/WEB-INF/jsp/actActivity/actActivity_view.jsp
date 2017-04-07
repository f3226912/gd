<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="margin:20px">
	<table>
		<tr>
			<td>活动名称：</td>
			<td><input type="text" name="name" value="${dto.name }" disabled="disabled"></td>
		</tr>
		<tr>
			<td>活动类型：</td>
			<td>
				<select name="type" disabled="disabled">
					<option value="">请选择活动类型</option>
					<option value="1" ${dto.type == 1 ? "selected" : "" }>刮刮卡</option>
					<option value="2" ${dto.type == 2 ? "selected" : "" }>幸运大转盘</option>
					<option value="3" ${dto.type == 3 ? "selected" : "" }>摇一摇</option>
					<option value="4" ${dto.type == 4 ? "selected" : "" }>疯狂抢红包</option>
					<option value="5" ${dto.type == 5 ? "selected" : "" }>砸金蛋</option>
				</select>
			</td>
		</tr>
		</tr>
			<td>活动礼品：</td>
		</tr>
		<tr>
			<td colspan="3"> 
				<table id="gift_table">
					<c:forEach items="${dto.reActivityGiftList }" var="gift" varStatus="status">
					<tr>
						<td>礼品${status.index+1 }&nbsp;&nbsp;</td>
						<td><input type="button" value="${gift.giftName }" disabled="disabled">&nbsp;&nbsp;</td>
						<td>活动预算：<input type="text" name="cost" value="${gift.cost }" disabled="disabled"></td>
						<td>所需积分：<input type="text" name="exchangeScore" value="${gift.exchangeScore }" disabled="disabled"></td>
					</tr>
					</c:forEach>
				</table>
			</td>			
		</tr>
		<tr>
			<td>活动渠道：</td>
			<td><input name="channel" type="checkbox" value="1" ${dto.channel == 1 ? "checked" : "" } disabled="disabled"/>H5-农商友</td>
		</tr>
		<tr>
			<td>参与用户：</td>
			<td><input name="userGroup" type="checkbox" value="1"  ${dto.userGroup == 1 ? "checked" : "" } disabled="disabled"/>微信绑定用户</td>
		</tr>
		
		<tr>
			<td>积分/礼品有效期：</td>
			<td>
				<input type="text" name="effectiveStartTime" value="<fmt:formatDate value='${dto.effectiveStartTime }' pattern="yyyy-MM-dd"/>" disabled="disabled"/> - 
				<input type="text" name="effectiveEndTime" value="<fmt:formatDate value='${dto.effectiveEndTime }' pattern="yyyy-MM-dd"/>"  disabled="disabled" >
			</td>
		</tr>
	</table>
</div>