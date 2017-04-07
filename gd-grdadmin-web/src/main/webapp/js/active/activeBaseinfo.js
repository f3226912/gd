
var bctrl = new Contrl();
$(function(){
	initPic=false;
	if(flag == 1){
		bctrl.initAdd();
		initAddPicture();
	} else {
		initPic=true;
		//修改，需要初始化数据
		bctrl.initUpdate($("#actId").val());
		initPic =false;//初始化完毕
	}

});

function Contrl(){
	//新增添动初始化
	this.initAdd=function(){
		//初始化文本域
		$("#introduction").val("");
	}
	
	//保存
	this.add=function(){
		//校验
		if($('#addForm').form("validate")){
			//是否有上传图片 
			if(!hasPicture()){
				alert("请至少上传一张图片!");
				return;
			}
			var data = $("#addForm").serialize();
			data = data+"&actId="+$.trim($("#actId").val());
			$.ajax({
				type : "post",
				url : CONTEXT+"active/addPromBaseinfo",
				data : data,
				dataType : "json",
				success : function(data){
			    	if(data.msg){
			    		$.messager.alert("错误信息",data.msg,"error");
			    	} else {
			    		//回写actId
			    		$("#actId").val(data.actId);
			    		$.messager.show({title:"提示信息",msg:"保存成功",timeout:3000,showType:'slide'});
			    	}
				}
			});
		}
	}
	
	this.initUpdate=function(actId){
		
		$.getJSON(CONTEXT+"active/queryBaseInfoById?actId="+actId,function(data){
			
			if(data.msg){
				alert(data.msg);
				return;
			} else {
				$("#addForm input[name='name']").val(data.name);//活动名称
				$("#addForm #marketId").combobox("setValue", data.marketList[0].marketId);//所属市场
				//活动时间
				$("#addForm #startDate2").val(data.startTime);
				$("#addForm #endDate2").val(data.endTime);
				$("#addForm #introduction").text(data.introduction);
				//初始化静态地址
				$("#addForm #activeUrl").val(data.url);
				//图片
				//alert(data.pictureRefList[0].urlOrg);
				var ps = data.pictureRefList;
				if(ps){
					for(var i=0;i<ps.length;i++){
						initEditPicture(ps[i].urlOrg);
					}
				}
				
			}
		});
	}
}

function hasPicture(){
	var $inputs = $("#user-iteam").find("input[name^='pictureRefList']");
	for(var i=0;i<$inputs.length;i++){
		var $input = $($inputs[i]);
		if($.trim($input.val())!=""){
			return true;
		}
	}
	return false;
}

/**
 * 初始化上传图片一个控件
 */
function initAddPicture(){
	//继续添加,大于等于10个不添加
	var length = $("#user-iteam").children().length;
	if(length<10){
		$("#user-iteam").append('<div style="position:relative;display:inline-block;vertical-align:top;margin-right:5px;">'+
			'<div class="fl mr_10" id="uploadContainerApp_'+length+'" style="position:absolute;top:30px;left:0px;z-index:99">'+
			'<input type="file" class="g-u input01" id="upload_btn_app_'+length+'" value="" name="productPicture" />'+
			'<input type="hidden" id="J_Urls_App_'+length+'" name="pictureRefList['+length+'].urlOrg" />'+
			'</div>'+
			'<div class="form-context">'+
				'<div class="clear"></div>'+
					'<ul id="picture_queen_app_'+length+'" class="multPic-box"></ul>'+
				'</div>'+
			'</div>');
		//初始化
		initUploadSignle("uploadContainerApp_"+length,"upload_btn_app_"+length,appUrl, "picture_queen_app_"+length, "J_Urls_App_"+length);
	}
}

/**
 * 初始化上传图片一个控件
 */
function initEditPicture(url){
	//继续添加,大于等于10个不添加
	var length = $("#user-iteam").children().length;
	if(length<10){
		$("#user-iteam").append('<div style="position:relative;display:inline-block;vertical-align:top;margin-right:5px;">'+
			'<div class="fl mr_10" id="uploadContainerApp_'+length+'" style="position:absolute;top:30px;left:0px;z-index:99">'+
			'<input type="file" class="g-u input01" id="upload_btn_app_'+length+'" value="" name="productPicture" />'+
			'<input type="hidden" id="J_Urls_App_'+length+'" name="pictureRefList['+length+'].urlOrg" />'+
			'</div>'+
			'<div class="form-context">'+
				'<div class="clear"></div>'+
					'<ul id="picture_queen_app_'+length+'" class="multPic-box">'+
						'<script type="text/uploader-files">'+
							'[{"url":"'+url+'"}]'+
						'</script>'+
					'</ul>'+
				'</div>'+
			'</div>');
		//初始化
		initUploadSignle("uploadContainerApp_"+length,"upload_btn_app_"+length,appUrl, "picture_queen_app_"+length, "J_Urls_App_"+length);
	
	}
}

