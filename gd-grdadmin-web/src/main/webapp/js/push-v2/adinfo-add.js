$(document).ready(function(){
	if($("#optionType").val()=='view'){
		$("#addFormDiv input,#addFormDiv select").each(function(){
			$(this).attr("readonly",true);
			$(this).attr("disabled",true);
			$("#uploadContainer").hide();
		});
	}
    initUploadModule("upload_btn1",masterUrl, "picture_queen1", "J_Urls1");
	$('#showProWin2').click(function(){
		var categoryIdval = $("#categoryId").val();
//		$('#proDialog').dialog({'title':'选择产品', 'width':700, 'height':300, 'href':CONTEXT+'pushV2/proInitList?categoryId=' + categoryIdval + '&selectType=2'}).dialog('open');
		$('<div></div>').dialog({
	          id : 'proDialog',
	          title : '选择产品',
	          width : 800,
	          height : 450,
	          closed : false,
	          cache : false,
	          href : CONTEXT+'pushV2/proInitList?categoryId=' + categoryIdval + '&selectType=2',
	          modal : true,
	          onLoad : function() {
	              //初始化表单数据
	          },
	          onClose : function() {
	              $(this).dialog('destroy');
	          },
	          buttons : [{
	              text : '取消',
	              iconCls : 'icon-cancel',
	              handler : function() {
	                  $("#proDialog").dialog('destroy');
	              }
	          } ],
	      });
	});
	
	$(".imageUploader_del").live("click",function(){
	    var originalUrl = $(this).parent().siblings().find("a img").attr("data-original-url");
	    $("input[name='pictureUrl']").each(function(){
	        if($(this).val()==originalUrl){
	            $(this).remove();
	        }
	    });
	});
	
	$('#marketId2').change(function(){
		categoryId1 = "";
		categoryId2 = "";
		categoryId3 = "";
   		$("#categoryId").val("");
   		$("#productId").val("");
   		$("#proview").html("");
		updatePtype1($(this).val(),null,$("#productType1"));
		updatePtype2(null,null,$("#productType2"));
		updatePtype2(null,null,$("#productType3"));
	});
	
	$("#productType1,#productType2,#productType3").change(function () {
		categoryId1 = "";
		categoryId2 = "";
		categoryId3 = "";
		var categoryIdTmp=$(this).val();
		var id=$(this).attr("id");
		if(id=='productType3'){
			if(!$(this).val()){
				categoryIdTmp=$("#productType2").val();
			}
		}else if(id=='productType2'){
			if(!$(this).val()){
				categoryIdTmp=$("#productType1").val();
			}
		}
		$("#categoryId").val(categoryIdTmp);
		$("#productId").val("");
		$("#proview").html("");
		var id=$(this).attr("id");
		if(id=='productType1'){
		 	updatePtype2($(this).val(),null,$("#productType2"));
		 	updatePtype2(null,null,$("#productType3"));
		}else if(id=='productType2'){
		 	updatePtype2($(this).val(),null,$("#productType3"));
		}
    });
});


function initUploadModule(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr, certification){
	var config_mutiple = {
	        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
	        uploadBtn : uploadBtn,//初始的button
	        queueId : queueId,//图片装载容器id
	        JUrls : JUrls,//成功后返回url参数写入id
	        action : uploadUrl ,//上传地址
	        max : pictureCountLimit,//允许上传个数
	        type :"ajax",//上传类型
	        imgUrlPrefix : imgHostUrl,
	        filesStr : filesStr,//此处是附加参数，已经上传需要第二次修的图片参数
	        certification : certification//此处是附加参数，已经上传需要第二次修，是否可以修改
	};
	gdKissySingleUploader.init(config_mutiple ,function(uploader){
	     var filesStr = config_mutiple.filesStr;
	     var certification = config_mutiple.certification;
	     if(filesStr != ''&&filesStr!=undefined){
	        var fileList = eval("("+  filesStr + ")");
	        gdKissySingleUploader.addFiles(fileList,uploader);
	        if(certification == 0){
	        	$("#"+JUrls).parent().find(".file-input").attr("disabled","disabled");
	        }else{
        		/**可以修改时注册删除事件**/
 	        	$(".form-context").delegate(".imageUploader_del","click",function(){
 	        		$("#JUrls1").val("");
 				    $(this).parents("li").get(0).remove();
 				    var originalUrl = $(this).parent().siblings().find("a img").attr("data-original-url");
 				    $("input[name*='"+JUrls+"']").each(function(){
 				        if($(this).val()==originalUrl){
 				            $(this).val("");
 				        }
 				    });
 				});
	        }
	     }
	    
	},function(url){
 	    
	});
}

//顶级菜单列表
function updatePtype1(marketId,cate,$id){
	$id.empty();
	$id.append($("<option/>").text("请选择").attr("value",""));
 	if(!marketId){
 		return;
 	}
	var productType1url = CONTEXT+'product/listTopProductCategory/' + marketId;
	$.getJSON(productType1url,function (data) {
        $(data).each(function () {
        	if(this.categoryId == cate){
        		$id.append($("<option/>").text(this.cateName).attr("value",this.categoryId).attr("selected",true));
        	}else{
        		$id.append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        	}
        });
    });
}

//下级菜单列表
function updatePtype2(parentId,cateId,$id){
	$id.empty();
	$id.append($("<option/>").text("请选择").attr("value",""));
	if(!parentId){
		return;
	}
	var productType2url = CONTEXT+'product/getChildProductCategory/' + parentId;
	$.getJSON(productType2url,function (data) {
        $(data).each(function () {
        	if(cateId != '' && null != cateId && this.categoryId == cateId){
        		$id.append($("<option/>").text(this.cateName).attr("value",this.categoryId).attr("selected",true));
        	}else{
        		$id.append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        	}
        });
    });
}

function initMarket2(marketType,marketId){
	$.ajax({
		type: "GET",
		url: CONTEXT+"market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#marketId2').empty();
				//$('#marketId2').append("<option value=''>==全部==</option>");
				for ( var n = 0; n < markets.length; n++) {
					if(marketId==markets[n].id){
						$('#marketId2').append("<option selected value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}else{
						$('#marketId2').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
					}
				}
			}
			if(marketId==3){
				$('#marketId2').append("<option selected value='3'>产地供应商市场</option>");
			}else{
				$('#marketId2').append("<option value='3'>产地供应商市场</option>");
			}
		}
	});
}
initMarket2(2,marketId);
//updatePtype1(marketId,categoryId1,$("#productType1"));
updatePtype1(3,categoryId1,$("#productType1"));
updatePtype2(categoryId1,categoryId2,$("#productType2"));
updatePtype2(categoryId2,categoryId3,$("#productType3"));

function loadProductLabels(){
	$.ajax({
		type: "GET",
		url: CONTEXT+"product/loadProductLabels/0",
		success: function(data) {
			$('#productSign').empty();
			$('#productSign').append("<option value=''>请选择</option>");
			if (data.length != 0) {
				for ( var n = 0; n < data.length; n++) {
					if(productSign==data[n].keyString){
						$('#productSign').append("<option selected value='"+data[n].keyString+"'>"+data[n].valueString+"</option>");
					}else{
						$('#productSign').append("<option value='"+data[n].keyString+"'>"+data[n].valueString+"</option>");
					}
				}
			}
		}
	});
}

function getState(state){
	var stateStr;
	switch (state) {
	 case "1":
         stateStr = "正常";
         break;
     case "2":
         stateStr = "等待";
         break;
     case "3":
         stateStr = "到期";
         break;
     case "4":
         stateStr = "下架";
         break;
     default:
         stateStr = "无状态";
         break;
     }
     return stateStr;
}

//提交新增数据
function saveadd() {
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	var url=CONTEXT+"pushV2/adInfoSaveAdd";
	if($("#optionType").val()=='update'){
		url=CONTEXT+"pushV2/adInfoSaveEdit";
	}
	
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		if (data.msg == "success") {
			var adAdvertise=data.adAdvertise;
			slideMessage("操作成功！");
			try {
				var node = $('#treeMenu').tree('getSelected');
				if (node){
					if(data.option=='add'){
						$('#treeMenu').tree('append', {
							parent: node.target,
							data: {
								id: adAdvertise.id,
								text: adAdvertise.adName+"("+getState(adAdvertise.state)+")",
								iconCls : 'icon-adVertise',
								attributes:{
									"type":3,
									"adSpaceId":adAdvertise.adSpaceId
								}, 
							}
						});
					}else if(data.option=='update'){
						$('#treeMenu').tree('update', {
							target: node.target,
							text: adAdvertise.adName+"("+getState(adAdvertise.state)+")"
						});
						
					}else if(data.option=='delete'){
						$('#treeMenu').tree('remove', {
							target: node.target
						});
					}
				} 
			}catch (e) {
			}
			$('#editAdSpaceDialog').dialog('destroy');
			return true;
		} else {
			warningMessage(data.msg);
			return;
		}
	});
}
//广告类型(1图片2文字3app启动页)
function adType2SelChange(){
	var adType2Sel=$("#adType22").val();
	if(adType2Sel=='2'){
		$("#adType2Sel1").show();
		$("#adType2Sel2").hide();
		$("#J_Urls1").attr("disabled",true);
	}else if(adType2Sel=='1'){
		$("#adType2Sel1").hide();
		$("#adWord").attr("disabled",true);
		$("#adType2Sel2").show();
	}else if(adType2Sel=='3'){
		$("#tr-marketId").hide();//所属市场
		$("#marketId2").attr("disabled",true);
		$("#tr-productType").hide();//跳转类目
		$("#categoryId").attr("disabled",true);
		$("#tr-productId2").hide();//选择商品
		$("#productId2").attr("disabled",true);
		$("#adType2Sel1").hide();//广告文字
		$("#adWord").attr("disabled",true);
		$("#adType2Sel2").show();//上传图片
		$("#tr-productSign").hide();//商品标签
		$("#productSign").attr("disabled",true);
//		$(".tr-time").hide();//开始、结束时间
//		$("#startTime2,#endTime2").attr("disabled",true);
		$("#tr-jumpType2").hide();//跳转类型
		$("#jumpType2").attr("disabled",true);
	}
}
adType2SelChange();
$("#jumpType2").change(function () {
	var jumpType2 = $(this).val();
	$("#tr-marketId").hide();//所属市场
	$("#tr-productType").hide();//跳转类目
	$("#tr-productId2").hide();//选择商品
	$("#adType2Sel1").hide();//广告文字
	$("#adType2Sel2").hide();//上传图片
	$("#tr-productSign").hide();//商品标签
	$(".tr-jumpUrl").hide();//跳转地址
	$("#marketId2").attr("disabled",true);
	$("#categoryId").attr("disabled",true);
	$("#productId2").attr("disabled",true);
//	$("#adWord").attr("disabled",true);
//	$("#J_Urls1").attr("disabled",true);
	$("#productSign").attr("disabled",true);
	$("#adLinkUrl2").attr("disabled",true);
	
	//(1类目-商品列表、2商铺首页、3URL、4搜索-商品列表、5商品标签)
	if(jumpType2=='1'){
		$("#tr-marketId").show();
		$("#tr-productType").show();
		$("#marketId2").attr("disabled",false);
		$("#categoryId").attr("disabled",false);
	}else if(jumpType2=='2' || jumpType2=='6'){
		$("#tr-productId2").show();
		$("#productId2").attr("disabled",false);
		$("#proview").show();
	}else if(jumpType2=='3' || jumpType2=='7'){
		$(".tr-jumpUrl").show();//跳转地址
		$("#adLinkUrl2").attr("disabled",false);
	}else if(jumpType2=='4'){
		
	}else if(jumpType2=='5'){
		$("#tr-productSign").show();
		$("#productSign").attr("disabled",false);
		loadProductLabels();
	}
	adType2SelChange();
});
$("#jumpType2").trigger("change");
if($("#optionType").val()=='view'){
	$("#addForm input,#addForm select").each(function(){
		$(this).attr("readonly",true);
		$(this).attr("disabled",true);
	});
	$("#btn-save").hide();
}else{
	$("#btn-save").show();
}
