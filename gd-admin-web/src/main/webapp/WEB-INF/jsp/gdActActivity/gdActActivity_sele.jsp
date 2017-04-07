<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>

<div>
	<div style="padding: 20px; font-size: 14px;">
		提示：点击下一步按钮后，若需要调整活动类型，需要重新创建活动。
	</div>
	<div style="padding: 20px; font-size: 14px;">
		<span style="color: red">*</span>请选择所要创建的活动类型：&nbsp;&nbsp;&nbsp;&nbsp;
		<select id="selectType">
			<option value="1">刷卡补贴</option>
			<option value="2">市场佣金</option>
			<option value="3">平台佣金</option>
			<option value="4">预付款/违约金</option>
			<option value="5">物流配送</option>
			<option value="6">采购积分</option>
		</select>
	</div>
	<div style="text-align: center;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="next()">下一步</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeSel()">关闭</a>
	</div>
</div>
<script type="text/javascript">

	function closeSel() {
		debugger;
		$("#selectActivity").dialog('close');
	}
	
	function next() {
		var type = $("#selectType").val();
		
		$("#selectActivity").dialog('close');
		
		if(type==1) {
			$('#activityDialog').dialog({'title':'新增活动', 'width':800, 'height':400, 'href':CONTEXT+'gdActActivityShaKa/add'}).dialog('open');
		} else if(type==2) {
			$('#activityDialog').dialog({'title':'新增活动', 'width':800, 'height':400, 'href':CONTEXT+'gdactMarketCommission/gdActActivity_edit_add'}).dialog('open');
		} else if(type==3) {
			$('#activityDialog').dialog({'title':'新增活动', 'width':800, 'height':400, 'href':CONTEXT+'gdActivityInformation/toAddInformation'}).dialog('open');
		} else if(type==4) {
			$('#activityDialog').dialog({'title':'新增活动', 'width':800, 'height':400, 'href':CONTEXT+'gdAdvancePayPenalSum/gdAdvance_edit_add'}).dialog('open');
		} else if(type==5) {
			$('#activityDialog').dialog({'title':'新增活动', 'width':800, 'height':400, 'href':CONTEXT+'gdDistActivity/toAddDistActivity'}).dialog('open');
		}
		 else if(type==6) {
				$('#activityDialog').dialog({'title':'新增活动', 'width':800, 'height':400, 'href':CONTEXT+'gdProcurementIntegration/toAddProcurementIntegration'}).dialog('open');
			}
		
	}
</script>
