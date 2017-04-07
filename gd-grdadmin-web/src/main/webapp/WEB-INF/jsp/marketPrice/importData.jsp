<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc"%>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc"%>
<div style="margin:auto 0px;text-align: center;">
<form id="importDataForm" action="" enctype="multipart/form-data" method="post" style="margin-top: 20px;">
	<p>目前该功能还待完善，对于Excel 2007及以上版本，需要将excel另存为“Excel 97-2003工作簿(*.xls)”才可以进行导入</p>
	<input id="datafile" type="file" accept=".xls" name="datafile" style="width:155px"
	required="true" class="easyui-validatebox" missingMessage="未选择Excel文件" />
	
	</br>
	</br>
	<div>采集市场
		<select name="maketId" id="maketId2">
		</select>
	</div>
</form>
</div>
<script type="text/javascript">
// $('#maketId').val("");
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#maketId2').empty();
				for ( var n = 0; n < markets.length; n++) {
					$('#maketId2').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
initMarket(2);
</script>











