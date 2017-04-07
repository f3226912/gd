<link rel="stylesheet" href="${CONTEXT}/js/kindeditor4.1/themes/default/default.css" />
	<link rel="stylesheet" href="${CONTEXT}/js/kindeditor4.1/plugins/code/prettify.css" />
	<script charset="utf-8" src="${CONTEXT}/js/kindeditor4.1/kindeditor.js"></script>
	<script charset="utf-8" src="${CONTEXT}/js/kindeditor4.1/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${CONTEXT}/js/kindeditor4.1/plugins/code/prettify.js"></script>
<script>
		KindEditor.ready(function(K) {			
			var editor1 = K.create('textarea[name="companyDesc"]', {
				cssPath : '../js/kindeditor4.1/kind/plugins/code/prettify.css',
				uploadJson : '${CONTEXT}/company/upDetailPicture',
				fileManagerJson : '../js/kindeditor4.1/jsp/file_manager_json.jsp',
				afterBlur: function(){this.sync();}
			});			
		});
	</script>