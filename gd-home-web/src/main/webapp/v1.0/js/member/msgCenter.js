$(function() {
	// 删除消息
	$("#_del").click(del);
	$("#_msgUl").find("li").click(msgClick);
	$("#_markAll").click(markAll);
});

function del() {
	var msgId = [];
	var para = "";
	$("input[name='_msg']").each(function() {
		var thisNode = $(this);
		if (thisNode.is(':checked')) {
			msgId.push(thisNode.val());
		}
	});
	if(msgId.length<=0){
		return;
	}
	
	
	para = msgId.join(",");
	var url = CONTEXT + "userCenter/msg/batchDel?ids=" + para;
	$.ajax({
		type : 'POST',
		url : url,
		data : para,
		success : function(data) {  
			var currentPage=$("#page").val();
			location.href= CONTEXT + "userCenter/msg/getList?page="+currentPage;
		},
		error : function() {  
			alert("更新失败,请稍后再试");
		},
	});
}
function msgClick() {
	// var id=$(this).find("input[name='_msg_id']").val();
	// $(".meg-tit").find(id).show().siblings(".meg-txt").hide();

	var $self = $(this);
//	index = $self.index();
	var id=$self.find("input").val();
	$(this).addClass("meg-curr").siblings().removeClass("meg-curr");
	$("#"+id).show().siblings(".meg-txt").hide();
	
	//设置该消息为已读
	
	
	var url = CONTEXT + "userCenter/msg/update";
	$.ajax({
		type : 'POST',
		url : url,
		dataType:'json', 
		data : {
			state:1,
			ids:id
		},
		success : function(data) {  
			 $("#_msgUnRead").html(data.msgUnRead);
			 //设置为已读样式
			 $self.find("span").css("font-weight","");
			 $self.find("font").css("font-weight","");
			 $("#_msgUnRead").html(data.msgUnRead);
		},
		error : function() {  
			alert("更新失败");
		}
	});
	
	
	
}


function updateMsg(state){
	var msgId = [];
	var ids = "";
	var inputNodes=$("input[name='_msg']");
	inputNodes.each(function() {
		var thisNode = $(this);
		if (thisNode.is(':checked')) {
			msgId.push(thisNode.val());
		}
	});
	if(msgId.length<=0){
		return;
	}
	
	ids = msgId.join(",");
	var url = CONTEXT + "userCenter/msg/update";
	$.ajax({
		type : 'POST',
		url : url,
		dataType:'json', 
		data : {
			state:state,
			ids:ids
		},
		success : function(data) {  
			 $("#_msgUnRead").html(data.msgUnRead);
			 setReadMsgColor(inputNodes,state);
		},
		error : function() {  
			alert("更新失败");
		}
	});
}


function setReadMsgColor(inputNodes,state){
	var fontWeight="bold";
	if(state=='1'){
		fontWeight="";
	}
	else{
		fontWeight="bold";
	}
	inputNodes.each(function() {
		var thisNode = $(this);
		if (thisNode.is(':checked')) {
			thisNode.parent().find("span").css("font-weight",fontWeight);
			thisNode.parent().find("font").css("font-weight",fontWeight);
		}
	});
	
	
}

function markAll(){
	var isCheck=$("#_markAll").is(':checked');
	$("[name='_msg']").each(function () {  
          this.checked = isCheck;  

       })  ;
}






