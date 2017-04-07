$(function(){
	$.extend($.fn.validatebox.defaults.rules, {
        requireRadio: {  
            validator: function(value, param){  
                var isSs = $(param[0] + ':checked').val()!=undefined;
                if(!isSs){isSs=$(param[1] + ':checked').val()!=undefined};
                return isSs;
            },  
            message: '请选择审核状态！'  
        }  
    });
	initReason();
	
})


function initReason(){
	$("input[type='radio']").each(function(){
	  $(this).bind("click",function(){
		  if($(this).val()=="3"){
			  $("#rejectReason").removeAttr("readonly");
			  $("#rejectReason").validatebox({    
				    required: true    
				});
			  $('#save-form').form('validate');
		  }
		  if($(this).val()=="2"){
			  $("#rejectReason").attr("readonly","readonly");
			  $("#rejectReason").val("");
			  $("#rejectReason").validatebox({    
				    required: false    
				});
			  $('#save-form').form('validate');
		  }
	  })
	})
}
//获取供应商报价
function getOfferPrice(type){
	$('<div></div>').dialog({
      id : 'saveDialog2',
      title : '全部报价信息',
      width : 650,
      height : 300,
      closed : false,
      cache : false,
      href : CONTEXT+'npsPurchase/offerList/'+purchaseId+"?type="+type,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '关闭',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#saveDialog2").dialog('destroy');
          }
      } ],
  });
	$(".window-header").css("border-bottom", "1px solid #d3d3d3");
}
