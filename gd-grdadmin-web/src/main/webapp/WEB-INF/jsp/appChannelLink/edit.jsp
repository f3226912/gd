<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="appChannelLink/save">
<input type="hidden" id="id" name="id" value="${dto.id}" />
<div>
	<table style="border: none;width: 100%;margin-top: 20px;">
		<tr>
			<td style="text-align: right;">客户端名称<span style="color: red">*</span></td>
			<td style="width: 75%;">
			<select id="clientChannel" name="clientChannel" style="width: 50%;" required="true" class="easyui-validatebox"></select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">渠道名称<span style="color: red">*</span></td>
			<td>
			<input type="text" id="channelName" name="channelName"  value="${dto.channelName}" required="true" class="easyui-validatebox" validType="unnormal" maxlength="20" missingMessage="channelName必填" style="width: 50%;">
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">平台<span style="color: red">*</span></td>
			<td>
				<select id="platform" name="platform" style="width: 50%;" required="true" class="easyui-validatebox"></select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">安装包下载链接<span style="color: red">*</span></td>
			<td><input type="text" id="downloadLink" name="downloadLink"  value="${dto.downloadLink}" required="true" class="easyui-validatebox" validType="unnormal" maxlength="100" missingMessage="downloadLink必填" style="width: 90%;"></td>
		</tr>
		<tr>
			<td style="text-align: right;">着落页链接<span style="color: red"></span></td>
			<td><input type="text" id="pageLink" name="pageLink"  value="${dto.pageLink}" class="easyui-validatebox" validType="unnormal" maxlength="100" style="width: 90%;"></td>
		</tr>
	</table>
</div>
</form>
<script>
var clientChannel2='${dto.clientChannel}';
function clientChannel(){
	$.ajax({
		type: "GET",
		url: CONTEXT+"appChannelLink/clientChannel",
		dataType: "json",
		success: function(data) {
			if(data){
				if (data.length != 0) {
					$('#clientChannel').empty();
					for ( var n = 0; n < data.length; n++) {
						if(clientChannel2 == data[n].codeKey){
							$('#clientChannel').append("<option selected='selected' value='"+data[n].codeKey+"'>"+data[n].codeValue+"</option>");
						}else {
							$('#clientChannel').append("<option value='"+data[n].codeKey+"'>"+data[n].codeValue+"</option>");
						}
					}
				}
			}
		}
	});
}
clientChannel();

var platform2='${dto.platform}';
function platform(){
	$.ajax({
		type: "GET",
		url: CONTEXT+"appChannelLink/platform",
		dataType: "json",
		success: function(data) {
			if(data){
				if (data.length != 0) {
					$('#platform').empty();
					for ( var n = 0; n < data.length; n++) {
						if(platform2==data[n].codeKey){
							$('#platform').append("<option selected='selected' value='"+data[n].codeKey+"'>"+data[n].codeValue+"</option>");
						}else {
							$('#platform').append("<option value='"+data[n].codeKey+"'>"+data[n].codeValue+"</option>");
						}
					}
				}
			}
		}
	});
}
platform();
</script>