gudeng.grdMember = {};

//var currentTeamLength = 1;
gudeng.grdMember.addTeam = function(){
	var marketId = $("#esDialogMarketId").val();
	if (!marketId){
		alert("请先选择市场..");
		return ;
	}
	var tr = $("<tr></tr>");
	var td = $('<td align="right"><label class="mark"></label></td>');
	var td2 = $('<td align="left"></td>');
	var select = $('<select name="teamGroup" style="height: 26px;width: 304px;" ></select>');
	$.ajax( {
	    url:CONTEXT+'grdMember/getTeamsOfMarket',
	    data : {"marketId" : marketId},
	    type:'post',
	    dataType:'json',
	    success:function(data) {
	    	var option = "";
	    	//当currentTeamLength = n 时表示第n个元素已经添加, 即将添加第n+1个元素; 
	    	//data.length包含了“全部”这一个选项, (data.length - 1)表示该市场的实际团队数目
	    	if (currentTeamLength >= (data.length - 1) ){
	    		alert("当前市场没有更多的团队选择了..");
	    	}else{
	    		currentTeamLength++ ;
	    		for(item in data){
	    			option = $("<option value=''></option>");
	    			option.val(data[item].keyString);
	    			option.text(data[item].valueString);
	    			option.appendTo(select);
	    		}
	    		select.appendTo(td2);
	    		td.appendTo(tr);
	    		td2.appendTo(tr);
	    		tr.insertBefore("#markTr");
	    		$("#addTeamLink").insertAfter(select);
	    	}
	     },
	     error : function(data) {
	    	 warningMessage(data);
	     }
	});
}
/**
 * 更新地推人员
 */
function updateGrdMember(){
	addOrUpdateGrdMember(function(data){
		$('#editorDialog').dialog('close');
		slideMessage(data.msg);
		//重新加载列表数据
		$('#grdMembertDataGrid').datagrid('reload');
	});
}

$("#esDialogMarketId").change(function(){
	
	$("#addTeamLink").insertAfter("select[name='teamGroup']:first");
	//删除从第二个开始的团队
	var selects = $("select[name='teamGroup']:not(select[name='teamGroup']:first)");
	$.each(selects, function(index, item){
		$(item).parent().parent().remove();
	});
	currentTeamLength = 1;
	
	var marketId = $(this).val();
	if(marketId){
		$.ajax({
			 type: 'POST',
		     url:CONTEXT+'grdMember/getChildTeamInfo/'+marketId ,
		     dataType:'json',
		     success: function(data) {
		    	 $("select[name='teamGroup']:first").html("");
		    	 $("select[name='teamGroup']:first").append("<option value=''>————全部————</option>");
		    	 $.each(data.rows, function(index, item){
		    		$("select[name='teamGroup']:first").append("<option value='"+item.giftteamId+"'>"+item.giftteamName+"</option");
		    	 });
		     }
		});
	}else{
		 $("select[name='teamGroup']:first").html("");
    	 $("select[name='teamGroup']:first").append("<option value=''>————全部————</option>");
	}
});

/**
 * 添加地推人员
 */
function addGrdMember(){
	addOrUpdateGrdMember(function(data){
		$('#saveDialog').dialog('close');
		slideMessage(data.msg);
		//重新加载列表数据
		$('#grdMembertDataGrid').datagrid('reload');
	});
}

/** 增加或更新地推人员
 * @param callBackafterSuccess 请求成功后的回调函数
 */
function addOrUpdateGrdMember(callBackafterSuccess){
	//先校验数据
	if(!$("#esDialogForm").form('validate')){
		return false;
	};
	var hasTeam = false ;
	var selects = $("select[name='teamGroup']");
	$.each(selects, function(index, item){
		if ($(item).val()){
			hasTeam = true ;
			return ;
		}
	});
	if (!hasTeam){
		alert("必须选择地推人员所属团队");
		return ;
	}
	$.ajax({
		url: CONTEXT+'grdMember/saveOrUpdate',
		data: $("#esDialogForm").serialize(),
		type: 'POST',
		dataType:"json",
		success: function(data) {
			if(data.isSuccess) {
				callBackafterSuccess(data);
			} else {
				warningMessage(data.msg);
			}
		},
		error: function(data){
			warningMessage(data);
		}
	});
}