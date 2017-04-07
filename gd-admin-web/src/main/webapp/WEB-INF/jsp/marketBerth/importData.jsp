<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc"%>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc"%>
<div style="margin:auto 0px;text-align: center;">
<form id="importDataForm" action="" enctype="multipart/form-data" method="post" style="margin-top: 20px;">
	<input id="datafile" type="file" accept=".xls,.xlsx" name="datafile" style="width:155px"
	required="true" class="easyui-validatebox" missingMessage="未选择Excel文件" />
</form>
</div>











