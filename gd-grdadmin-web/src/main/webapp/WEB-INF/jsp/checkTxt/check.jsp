<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<base href="${CONTEXT }">
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>

<form id="checkForm" method="post" action="checkTxt/check">
	<div style="padding: 20px;">
		请粘贴txt的内容&nbsp; :
		<textarea rows="3" cols="30" style="height:500px;width:100%;font-size:14px;" 
				name="description" ></textarea>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="checkTxt()">
	        		检查文件
        	</a>
	</div>
</form>

<script type="text/javascript" >


	function checkTxt(){
		var url=CONTEXT+"checkTxt/check";
		jQuery.post(url, $('#checkForm').serialize(), function (data) {
			if (data == "success") {
				alert("基础校验通过。")
			} else 	if (data == "falied") {
				alert("检查过程发生异常，请确认输入的文本有效.")
				return;
			} else  {
				alert("第"+data+"行数据可能有问题，请检查该行数据")
				return;
			}
		});
	}


</script>











