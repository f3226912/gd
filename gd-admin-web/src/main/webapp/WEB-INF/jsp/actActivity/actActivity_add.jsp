<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div style="margin:20px">
	<form id="addActActivityForm" action="actGift/saveAdd" method="post">
		<table>
			<tr>
				<td>活动名称：</td>
				<td><input type="text" name="name" class="easyui-validatebox" required="true" 
					missingMessage="活动名称不能为空" validType="length[1,20]"></td>
			</tr>
			<tr>
				<td>活动类型：</td>
				<td>
					<select name="type" class="easyui-validatebox" required="true" missingMessage="活动类型不能为空">
						<option value="">请选择活动类型</option>
						<option value="1">刮刮卡</option>
						<option value="2">幸运大转盘</option>
						<option value="3">摇一摇</option>
						<option value="4">疯狂抢红包</option>
						<option value="5">砸金蛋</option>
					</select>
				</td>
			</tr>
			</tr>
				<td>活动礼品：</td>
			</tr>
			<tr>
				<td colspan="3"> 
					<table id="gift_table">
						<tr>
							<td><input type="hidden" value="1" name="giftRowNum"><input type="checkbox" name="giftId" id="giftId1"/>礼品1&nbsp;&nbsp;</td>
							<td><input type="button" value="选择礼品" onclick="openSelectGift(1)" id="giftButton1">&nbsp;&nbsp;</td>
							<td>活动预算：<input type="text" name="cost" id="cost1"></td>
							<td>所需积分：<input type="text" name="exchangeScore" id="exchangeScore1"></td>
						</tr>
						<tr>
							<td><input type="hidden" value="2" name="giftRowNum"><input type="checkbox" name="giftId" id="giftId2"/>礼品2&nbsp;&nbsp;</td>
							<td><input type="button" value="选择礼品" onclick="openSelectGift(2)" id="giftButton2">&nbsp;&nbsp;</td>
							<td>活动预算：<input type="text" name="cost" id="cost2"></td>
							<td>所需积分：<input type="text" name="exchangeScore" id="exchangeScore2"></td>
						</tr>
						<tr>
							<td><input type="hidden" value="3" name="giftRowNum"><input type="checkbox" name="giftId" id="giftId3"/>礼品3&nbsp;&nbsp;</td>
							<td><input type="button" value="选择礼品" onclick="openSelectGift(3)" id="giftButton3">&nbsp;&nbsp;</td>
							<td>活动预算：<input type="text" name="cost" id="cost3"></td>
							<td>所需积分：<input type="text" name="exchangeScore" id="exchangeScore3"></td>
						</tr>
					</table>
				</td>			
			</tr>
			<tr>
				<td style="padding:10px 0"><a href="javascript:addGiftRow()">+添加更多礼品</a></td>
			</tr>
			<tr>
				<td>活动渠道：</td>
				<td><input name="channel" type="checkbox" value="1"/>H5-农商友</td>
			</tr>
			<tr>
				<td>参与用户：</td>
				<td><input name="userGroup" type="checkbox" value="1"/>微信绑定用户</td>
			</tr>
			<!--  h5 金牌会员营销活动取消该字段   by xiezhongGuo 2016年11月21日15:12:19
			<tr>
				<td>礼品兑换限制：</td>
				<td><input name="exchangeTime" type="checkbox" value="1"/>全场仅兑换一次</td>
			</tr>
			-->
			<input name="exchangeTime" type="hidden" value="0"/>
			<tr>
				<td>积分/礼品有效期：</td>
				<td>
					<input type="text" name="effectiveStartTime" id="effectiveStartTime"
						onFocus="WdatePicker({onpicked:function(){effectiveStartTime.focus();},maxDate:'#F{$dp.$D(\'effectiveEndTime\')}'})" 
						onClick="WdatePicker({onpicked:function(){effectiveStartTime.focus();},maxDate:'#F{$dp.$D(\'effectiveEndTime\')}'})"/> - 
					<input type="text" name="effectiveEndTime" id="effectiveEndTime"
						onFocus="var minDate = effectiveEndTimeMinDate();WdatePicker({onpicked:function(){effectiveEndTime.focus();},minDate:minDate})" 
						onClick="var minDate = effectiveEndTimeMinDate();WdatePicker({onpicked:function(){effectiveEndTime.focus();},minDate:minDate})">
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="giftDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsGift">
	<div id="#dlg-buttonsGift" style="text-align:center">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#giftDialog').dialog('close')">关闭</a>
    </div>
</div>
<script type="text/javascript">
	function dateToStr(myDate){
		var year = myDate.getFullYear();//获取完整的年份(4位,1970-????)
		var month = myDate.getMonth()+1;//获取当前月份(0-11,0代表1月)
		var date = myDate.getDate();
		return year+"-"+month+"-"+date;
	}

	function effectiveEndTimeMinDate(){
		var effectiveStartTime = $("#effectiveStartTime").val();
		var date = new Date();
		date.setDate(date.getDate()+1);
		
		if(effectiveStartTime == ''){
			return dateToStr(date);
		}
		var effectiveStartDate = new Date(effectiveStartTime);
		if(date > effectiveStartDate){
			return dateToStr(date);
		}
		return dateToStr(effectiveStartDate);
	}

 	var rowCount = 3;
	function addGiftRow(){
		rowCount++;
		var tr = "<tr>"+
				 "<td><input type='hidden' value='"+rowCount+"' name='giftRowNum'><input type='checkbox' name='giftId' id='giftId"+rowCount+"'/>礼品"+rowCount+"</td>"+
				 "<td><input type='button' value='选择礼品' onclick='openSelectGift("+rowCount+")' id='giftButton"+rowCount+"'></td>"+
				 "<td>活动预算：<input type='text' name='cost' id='cost"+rowCount+"'></td>"+
				 "<td>所需积分：<input type='text' name='exchangeScore' id='exchangeScore"+rowCount+"'></td>"+
				 "</tr>";
		$("#gift_table").append(tr);
	}
	
	function openSelectGift(giftRowId){
		$('#giftDialog').dialog({'title':'选择礼品', 'width':700, 'height':300, 'href':CONTEXT+'actActivity/giftSelect?giftRowId='+giftRowId}).dialog('open');
	}
	
	function selectGiftCallBack(giftRowId, giftId, giftName){
		$('#giftDialog').dialog("close");
		var exist = false;
		$("#addActActivityForm input[name='giftId']").each(function(){
			if($(this).val() == giftId){
				warningMessage("已存在相同的礼品");
				exist = true;
			}
		});
		if(!exist){
			$("#addActActivityForm #giftId"+giftRowId).val(giftId);
			$("#addActActivityForm #giftButton"+giftRowId).val(giftName);
		}
	}
</script>