<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
	<input type="hidden" id="actId" value="${actId}"/>
	<input type="hidden" id="flag" value="${flag}"/>
	<div id="tt" class="easyui-tabs" style="width:840px;height:390px;padding:10px;">
		<div title="活动信息" style="padding:20px;" data-options="href:'${CONTEXT}active/toPromBaseinfo'">
		</div>
		<div title="活动规则" data-options="closable:false,href:'${CONTEXT}active/toRule'" style="overflow:auto;padding:20px;">
		</div> 
		<div title="供应商信息" data-options="closable:false,href:'${CONTEXT}active/toPromSupplerinfo?actId='" style="">
		</div>
		<div title="商品信息" data-options="closable:false,href:'${CONTEXT}active/toPromProduct'" style="">
		</div>
	</div>
	<script type="text/javascript">
		var flag = ${flag};//1 新增 2修改
	</script>
