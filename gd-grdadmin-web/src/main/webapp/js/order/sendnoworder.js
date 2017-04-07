$(document).ready(function(){
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#orderSearchForm')[0].reset();
		//重启导出功能
		disableExport = false ;
	});

	/***数据导出功能***/
	$("#exportData").click(function(){

		var params = {
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val()
			};
		var paramList = "startDate="	+ params.startDate	+ "&endDate=" + params.endDate;

		$.ajax({
			url: CONTEXT+'sendnoworder/checkExportParams',
			data : params,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){

					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'sendnoworder/exportData',paramList,'post' );
					}else{
						slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
					}
				}else{
					if (data.message) {
						warningMessage(data.message);
					} else {
						warningMessage("超时，请重新登录系统");
					}
					
				}
			},
			error : function(data){
				warningMessage(data);
			}
		});
	});
});
jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};

