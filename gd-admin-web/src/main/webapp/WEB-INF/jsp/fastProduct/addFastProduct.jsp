<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>


<form id="addForm" method="post" action="product/add">

	<div style="padding: 20px;">
			<p style="height: 20px;">
				农批市场&nbsp; :
				<input type="hidden" id="cmarket" value="-1" />
				<input type="text" id="cmarketName" value="" readonly/> 
				<input type="button" id="marketBtn" value="选择市场" />
			</p>
		<div id="categoryPanel">
			<p style="height: 20px;">
				商品类目&nbsp; :
				<input id="productType1_add" >
				<input id="productType2_add" >
				<input id="productType3_add" >
				<input type="hidden" id="typeLevels" name="typeLevels" value="3">
				<input type="hidden" id="categoryFirst" value="">
				<input type="hidden" id="categorySecond" value="">
				<input type="hidden" id="categoryThird" value="">
				<input type="hidden" id="categoryId" name="categoryId">
				
			</p>
		</div>
		<p style="height: 20px;">
		
		商品名称&nbsp; :
		<input type="text" id="productName" name="productName" value="" required="true"
			class="easyui-validatebox"  missingMessage="商品名称不能为空" />
			
<!-- 		农批中心&nbsp; :
			<input type="button" id="showMarketWin" value="选择街市">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" id="marketNameInput" name="marketNameInput" value="" readonly>
			<input type="hidden" id="marketId_add" name="marketId" value="" readonly> -->
			
<!-- 		会员&nbsp; :
			<input type="button" id="showMemberWin" value="选择会员">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="hidden" id="roleType" name="roleType" value="" >
			<input type="hidden" id="memberId_add" name="memberId" value="" > -->
			
		<!-- 商铺&nbsp; : -->
			<!-- <input id="businessId_select_add" > -->
			<!-- <input type="hidden" id="businessId_add" name="businessId" value="" > -->
			
			<!-- <input type="button" id="showBusinessWin" value="选择商铺"><br> -->
			
			<!-- <span><a href="javascript:void(0)" onclick="businessCallback()">回调测试</a></span> -->
<!-- 		发布渠道&nbsp; :
		<select id="channel_add" name="channel" class="easyui-combobox"  style="width:200px;">
		    <option value="0">农批市场</option>
		    <option value="1">谷登物流</option>
		</select> -->
		
		</p>

<!-- 		<p style="height: 20px;">
			联系地址&nbsp; :
			<input id="province_add" readonly>
			<input id="city_add"  readonly >
			<input id="area_add"  readonly>
			<input type="hidden" id="province_new" name="provinceId">
			<input type="hidden" id="city_new" name="cityId">
			<input type="hidden" id="area_new" name="areaId">具体地址
			<input type="text" id="address_add" name="address">
		</p> -->
		<p style="height: 20px;">
			商品产地&nbsp; :
			<input id="originProvince_comp" >
			<input id="originCity_comp"  >
			<input id="originArea_comp"  >
			
			<input type="hidden" id="originProvince_typeIn" name="originProvinceId">
			<input type="hidden" id="originCity_typeIn" name="originCityId">
			<input type="hidden" id="originArea_typeIn" name="originAreaId">
		</p>
		<p style="height: 20px;">
			供应量&nbsp; :
			<input class="easyui-validatebox" data-options="min:0,precision:2" 
				type="text" id="stockCount" name="stockCount" value="" maxlength="9"/>
			<!-- 单位 -->
			<input id="unit_add" name="unit" />
		</p>
		<p style="height: 20px;">
			价格&nbsp; : 
			<input id="priceType" name="priceType" />
				&nbsp;&nbsp;<span id="priceUnitSpan"></span>
	    </p>
	    <p id="priceAddContainer">
		   <input data-options="min:0,precision:2" style="width:150px"
				type="text" id="price" name="price"  value="" maxlength="11"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
		</p>
		<div id="priceTableContainer" class="priceTableContainer">
		    <table id="priceTable" style="text-align: center;">
			    <thead id="tdHead">
			        <tr>
			            <th><div style="width: 120px;">最小起订量</div></th>
			            <th><div style="width: 120px;">截止起订量</div></th>
			            <th><div style="width: 120px;">单价</div></th>
			            <th><div style="width: 200px;">备注</div></th>
			            <th><span><a href="javascript:void(0)" onclick="addPriceRow()">+增加价格区间</a></span></th>
			        </tr>
			    </thead>
			    <tbody id="priceTableBody">
			    	
			    </tbody>
			</table>
		</div>
		
		
	    <div class="user-iteam">
<!-- 	        <p>上传主图</p>
	        <div class="fl mr_10" id="uploadContainer">
	            <input type="file" class="g-u input01" id="upload_btn" value="" name="productPicture" />
	            <input type="text" id="J_Urls" name="masterPicture" />
	        </div>
	        <div class="form-context">
	            <div class="clear"></div>
	            <ul id="picture_queen" class="grid"></ul>
	        </div> 
	        <BR> -->
	        <p>上传App图</p>
	        <div class="fl mr_10" id="uploadContainerApp">
	            <input type="file" class="g-u input01" id="upload_btn_app" value="" name="productPicture" />
	            <input type="hidden" id="J_Urls_App" name="appPicture" />
	        </div>
	        <div class="form-context">
	            <div class="clear"></div>
	            <ul id="picture_queen_app" class="multPic-box"></ul>
	        </div> 
	        <BR>        
	        
	        <p>上传多角度图</p>
	        <div class="fl mr_10" id="uploadContainerMultiAngle">
	            <input type="file" class="g-u input01" id="upload_btn_multiAngle" value="" name="productPicture" />
	            <input type="hidden" id="J_Urls_MultiAngle" name="multiplePicture" />
	        </div>
	        <div class="form-context">
	            <div class="clear"></div>
	            <ul id="picture_queen_MultiAngle" class="grid multPic-box"></ul>
	        </div> 
	        <BR>   
	        
	    </div>
	    
		<p style="height: 20px;">
			详细信息&nbsp; :
			<textarea rows="3" cols="30" style="width:100%;font-size:14px;" 
					name="description" ></textarea>
		</p>
		<br><br><br><br><br><br><br><br><br><br>

		<p style="height: 20px;">
			信息有效期&nbsp; :
			7天<input type="radio"  name="infoLifeDay" value="7" >&nbsp;&nbsp;
			15天<input type="radio"  name="infoLifeDay" value="15" >&nbsp;&nbsp;
			30天<input type="radio"  name="infoLifeDay" value="30" checked="checked">&nbsp;&nbsp;
		</p>
		
	</div>
</form>

<script type="text/javascript" >

var masterUrl = CONTEXT+'product/uploadProductPic';
var multiAngleUrl = CONTEXT+'product/uploadProductPic';
var webUrl = CONTEXT+'product/uploadProductPic';
var appUrl = CONTEXT+'product/uploadProductPic';

var provinceId ;
var cityId ;
var areaId ;
var address ;

function initPriceType(){
	
 	$('#priceType').combobox({
		valueField:'valueString',
		textField:'keyString',
		editable:false ,
		url: CONTEXT+'product/priceKeyValuePair',
		onSelect:function(record){
			if(record.valueString == '1'){
				$('#priceType').val("1");
	         	$('#priceAddContainer').hide();
	         	$('#priceTableContainer').show();
	         	addPriceRow();
	         }else{
	        	$('#priceType').val("0");
	         	$('#priceAddContainer').show();
	         	$('#priceTableBody').empty();
	         	$('#priceTableContainer').hide();
	         }
		},
		onLoadSuccess : function(){
			var data = $('#priceType').combobox('getData');
			if (data.length > 0) {
				$('#priceType').combobox('select', data[0].valueString);
				$('#priceType').val(data[0].valueString);
			}
		}
	}); 
}

function initStockUnit(){
	
 	$('#unit_add').combobox({
		valueField:'valueString',
		textField:'keyString',
		editable:false ,
		url: CONTEXT+'product/unitKeyValuePair',
		onSelect:function(record){
			$("#priceUnitSpan").text("元/"+record.keyString);
		},
		onLoadSuccess : function(){
			var data = $('#unit_add').combobox('getData');
			if (data.length > 0) {
				$('#unit_add').combobox('select', data[0].valueString);
			}
		}
	}); 
}

$(document).ready(function(){
	
	//单位
	initStockUnit();
	// 价格类型
	initPriceType();
	//产地
	updateOriginProvince();
	// 初始化隐藏分类
	hideProductCategory();
	
	KindEditor.create('textarea[name="description"]', {
		cssPath : CONTEXT+'js/kindeditor-4.1.10/plugins/code/prettify.css',
		uploadJson: CONTEXT+'js/kindeditor-4.1.10/jsp/upload_json.jsp',
		fileManagerJson : CONTEXT+'js/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		allowFileManager: true,
		allowImageUpload : true,
		//把编辑器里的内容同步到textarea @_@ @_@ @_@  
		afterBlur: function(){this.sync();},
		afterChange:function(){
			var editor = this;
			var limit = 5000;
			if(editor.count('text') > limit){
				//warningMessage("详细信息最多可输入5000字符(包含图片路径等元素的长度)");
				/* var strValue = editor.text();
				strValue = strValue.substring(0,limit);
			    editor.text(strValue);  */
			}
		}
	});
	
	//图片上传 start...
	initUploadModule("upload_btn_multiAngle",multiAngleUrl, "picture_queen_MultiAngle", "J_Urls_MultiAngle", 4);
	//app
	initUploadSignle("upload_btn_app",appUrl, "picture_queen_app", "J_Urls_App");
	
 	var filesStr = '';
     if(filesStr != ''){
        var fileList = eval("("+  filesStr + ")");
        for (var i = 0; i < fileList.length; i++) {
            $("#picContainer").append("<input name=\"pictureUrl\" id=\"url+"+i+"\" type=\"hidden\" value=\""+fileList[i].dataOriginalUrl+"\" />");
        }
     }
     $(".imageUploader_del").live("click",function(){
    	    var originalUrl = $(this).parent().siblings().find("a img").attr("data-original-url");
    	    $("input[name='pictureUrl']").each(function(){
    	        if($(this).val()==originalUrl){
    	            $(this).remove();
    	        }
    	    });
      });
   //图片上传 end...
   
});

function hideOriginPlace(hideCity, hideArea){
	if (hideCity){
		$('#originCity_comp').next(".combo").hide();
	}
	if (hideArea){
		$('#originArea_comp').next(".combo").hide();
	}
}

//隐藏商品分类组件
function hideProductCategory(){
	$("#categoryPanel").hide();
	$('#productType1_add').next(".combo").hide();
	$('#productType2_add').next(".combo").hide();
	$('#productType3_add').next(".combo").hide();
}
//显示商品分类组件
function showProductCategory(){
	$("#categoryPanel").show();
	$('#productType1_add').next(".combo").show();
	$('#productType2_add').next(".combo").show();
	$('#productType3_add').next(".combo").show();
}

//  初始化一级分类(级联子分类菜单)
function initCategoryForAddPage(marketId){
	//商品分类
 	$('#productType1_add').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'fastProduct/listTopProductCategory/' + marketId ,
		onSelect:function(record){
			$("#categoryFirst").val(record.categoryId);
			var typeLevels = parseInt($("#typeLevels").val());
			if ( typeLevels >= 2){
				updatePtype2(record.categoryId);
			}else{
				if (useCategoryCookie){
					useCategoryCookie = false ;
				}
				//设置商品分类
				$('#addForm #categoryId').val(record.categoryId);
			}
		},
		onLoadSuccess : function(){
			var data = $('#productType1_add').combobox('getData');
			if (data.length > 0) {
				if (useCategoryCookie){
					$('#productType1_add').combobox('select', $.trim(categoryCData[0]));
				}else{
					$('#productType1_add').combobox('select', data[0].categoryId);
				}
			}
			//$('#productType1_add').next(".combo").show();
		}
	}); 
}

function updatePtype2(parentId){
	
	$('#productType2_add').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		onSelect:function(record){
			$("#categorySecond").val(record.categoryId);
			var typeLevels = parseInt($("#typeLevels").val());
			if ( typeLevels >= 3){
				updatePtype3(record.categoryId);
			}else{
				useCategoryCookie = false ;
				//设置商品分类
				$('#addForm #categoryId').val(record.categoryId);
			}
		},
		onLoadSuccess : function(){
			var data = $('#productType2_add').combobox('getData');
			if (data.length > 0) {
				if (useCategoryCookie){
					$('#productType2_add').combobox('select', $.trim(categoryCData[1]));
				}else{
					$('#productType2_add').combobox('select', data[0].categoryId);
				}
			}
			//$('#productType2_add').next(".combo").show();
		}
	}); 
}

function updatePtype3(parentId){
	
	$('#productType3_add').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		onSelect:function(record){
			$("#categoryThird").val(record.categoryId);
			var typeLevels = parseInt($("#typeLevels").val());
			if ( typeLevels >= 4){
				updatePtype4(record.categoryId);
			}else{
				useCategoryCookie = false ;
				//设置商品分类
				$('#addForm #categoryId').val(record.categoryId);
			}
		},
		onLoadSuccess : function(){
			var data = $('#productType3_add').combobox('getData');
			if (data.length > 0) {
				if (useCategoryCookie){
					$('#productType3_add').combobox('select', $.trim(categoryCData[2]));
				}else{
					$('#productType3_add').combobox('select', data[0].categoryId);
				}
			}
			//$('#productType3_add').next(".combo").show();
			//显示分类组件
			showProductCategory();
		}
	}); 
}

//分类级联菜单end...

//图片上传
function initUploadModule(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit){
	var config_mutiple = {
	        galleryUrl : CONTEXT+"js/uploader/",
	        uploadBtn : uploadBtn,
	        queueId : queueId,
	        JUrls : JUrls,
	        action : uploadUrl ,
	        maxSize:2048,
	        imgUrlPrefix : '${imgHostUrl}',
	        max : pictureCountLimit,
	        type :"ajax"
	};
	gdKissyMultipleUploader.init(config_mutiple ,function(uploader){
	     var filesStr = '';
	     var certification = '';
	     if(filesStr != ''){
	        var fileList = eval("("+  filesStr + ")");
	        
	        gdKissyMultipleUploader.addFiles(fileList,uploader);
	        
	        if(certification == 0){
	                $("#uploadContainer").find(".file-input").attr("disabled","disabled");
	        }
	     }
	},function(url){
 	    if(url!='' && typeof(url!='undefined')){
	        var urlarray = url.split(",");
	        for (var i = 0; i < urlarray.length; i++) {
	            $("#picContainer").append("<input name=\"pictureUrl\" id=\"url+"+i+"\" type=\"hidden\" value=\""+urlarray[i]+"\" />");
	        }
	        
	    } 
	});
}

/**单图上传**/
function initUploadSignle(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr){
	var config_mutiple = {
	        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
	        uploadBtn : uploadBtn,//初始的button
	        queueId : queueId,//图片装载容器id
	        JUrls : JUrls,//成功后返回url参数写入id
	        action : uploadUrl ,//上传地址
	        maxSize:2048,
	        imgUrlPrefix : '${imgHostUrl}',
	        max : pictureCountLimit,//允许上传个数
	        type :"ajax",//上传类型
	        filesStr : filesStr,//此处是附加参数，已经上传需要第二次修的图片参数
	};
	gdKissySingleUploader.init(config_mutiple ,function(uploader){
	     var filesStr = config_mutiple.filesStr;
	     var certification = config_mutiple.certification;
	     if(filesStr != ''&&filesStr!=undefined){
	        
	        gdKissySingleUploader.addFiles(filesStr,uploader);
	        
	        if(certification == 0){
	                $("#"+JUrls).parent().find(".file-input").attr("disabled","disabled");
	        }else{
	        	/**可以修改时注册删除事件**/
	        	$(".form-context").delegate(".imageUploader_del","click",function(){
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

/**
 * 计算多价列表中的价格区间的行数
 */
function countRow(tableId){
	 return document.getElementById(tableId).getElementsByTagName('tr').length-1;//减去表头所占的一行
}

function addPriceRow(){
	 var len = countRow("priceTable");//$('#priceTable').datagrid('getRows').length;
	 if (len > 0){
		 if(len>=3){
			 warningMessage("最多只能添加三个区间价格！");
			 return false;
		 }
		 var buyCountStarts=$(".CbuyCountStart"); 
		 var buyCountEnds=$(".CbuyCountEnd"); 
		 var prices=$(".Cprice"); 
		 for (var i = 0; i < len ; i++){
			if (!buyCountStarts[i].value || !buyCountEnds[i].value || !prices[i].value ){
				warningMessage("存在未填写内容的行, 不能添加新行");
				return false;
			}
		}
	 }
 var trID="priceDtoList_"+len;
 var innerHtml='<tr id="'+trID+'">';
 innerHtml+='<td><input type="text" style="width: 80px;" id="priceDtoList['+len+'].buyCountStart" name="priceDtoList['+len+'].buyCountStart" class="CbuyCountStart" value=""  maxlength="9"></td>';
 innerHtml+='<td><input type="text" style="width: 80px;" id="priceDtoList['+len+'].buyCountEnd" name="priceDtoList['+len+'].buyCountEnd" class="CbuyCountEnd"value="" maxlength="9">';
 innerHtml+='<td><input type="text" style="width: 80px;" id="priceDtoList['+len+'].price" name="priceDtoList['+len+'].price" class="Cprice" value="" maxlength="11"></td>';
 innerHtml+='<td><label>截止起订量为空表示大于当前行起始起订量者,均使用当前行价格</label></td>';
 innerHtml+='<td><span><a href="javascript:void(0)" onClick="deletePriceRow(\''+trID+'\')">-删除</a></span></td>';
 innerHtml+='</tr>';
 $("#priceTable").append(innerHtml);  
//  $("#tdHead").after(innerHtml);  
 $("#priceTable").show();
}

function deletePriceRow(r){
	$("#"+r).remove();
}

function checkDecimal(strVal,validNum, savingNum){
	strVal = strVal + "";
	var len = strVal.length;
	var index = strVal.indexOf(".");
	if ( index != -1){
		if (len > (validNum +1) ){
			//warningMessage("数值长度过长");
			return 1;
		}
		var flen = strVal.substring(0,index).length;
		if (flen > (validNum - savingNum)){
			//warningMessage("数值长度过长");
			return 1;
		}
		var tlen = strVal.substring(index+1).length;
		if (tlen > savingNum){
			//warningMessage("小数点后位数过长");
			return 2;
		}
	}else{
		if (len > validNum - savingNum ){
			//warningMessage("数值长度过长");
			return 1;
		}
	}
	return 0;
}

function checkRow(){
	var starts=$(".CbuyCountStart"); 
	var ends=$(".CbuyCountEnd"); 
	var prices=$(".Cprice"); 
	var len = starts.length;
	if (len < 1){
		warningMessage("请添加价格区间以维护区间价格");
		return false;
	}
	for (var i = 0; i < len ; i++){
		//价格监测
		if (isNaN(prices[i].value)){
			warningMessage("第" + (i+1) + "行的数据输入有误, 价格必须为数值型");
			return false;
		}
		if (prices[i].value != '' && parseInt(prices[i].value) < 0){
			warningMessage("第" + (i+1) + "行的数据输入有误, 价格不能为负数");
			return false;
		}
		if (checkDecimal(prices[i].value, 10, 2) == 1){
			warningMessage("第" + (i+1) + "行的价格输入有误, 价格的数值长度过长,整数部分最多输入8位和小数点后最多2位");
			return false;
		} else if (checkDecimal(prices[i].value, 10, 2) == 2){
			warningMessage("第" + (i+1) + "行的价格输入有误, 小数点后位数过长(小数点后保留两位)");
			return false;
		}
		//起订量监测
		if (!(starts[i].value) || ( !(ends[i].value) && i != len-1 )){
			warningMessage("第" + (i+1) + "行起订量不能为空");
			return false;
		}
		if (isNaN(starts[i].value) || isNaN(ends[i].value)){
			warningMessage("第" + (i+1) + "行的数据输入有误, 起订量必须为数值型");
			return false;
		}
		if ((starts[i].value - ends[i].value)>=0 && $.trim(ends[i].value) != ""){
			warningMessage("第" + (i+1) + "行的数据输入有误, 截止起订量必须大于起始起订量");
			return false;
		}
		
		if (checkDecimal(starts[i].value, 8, 2) == 1 || checkDecimal(ends[i].value, 8, 2) == 1 ){
			warningMessage("第" + (i+1) + "行的起订量输入有误, 数值长度过长,整数部分最多输入6位和小数点后最多2位");
			return false;
		}else if (checkDecimal(starts[i].value, 8, 2) == 2 || checkDecimal(ends[i].value, 8, 2) == 2 ){
			warningMessage("第" + (i+1) + "行的起订量输入有误, 小数点后位数过长(小数点后保留两位)");
			return false;
		}
	}
	if (len > 1){
		for( var j=0; j < len-1; j++){
			if((ends[j].value - starts[j+1].value)>=0){
				warningMessage("第" + (j+1) + "行以及第"+ (j+2)+ "行的数据输入有误, 第"+(j+2)+"行起始起订量必须大于第"+(j+1)+"行截止起订量");
				return false;
			}
		}
	}
	
	return true;
}

//省市区
var useSSQCookie = false;
var ssqData ;
var addressData;

var useCategoryCookie = false;
var marketIdCookie ;
var categoryCData;

/* //加载产地供应商对应的市场
function loadMarketInfoByMarketId(marketId){
	 $.ajax( {  
		    url:CONTEXT+'product/queryMarketInfoByMarketId',
		    data:{  
		    	marketId : marketId
		    },  
		    type:'post',  
		    dataType:'json',  
		    success:function(data) { 
		    	//设置农批市场
				$("#marketId_add").val(data.id);
				$("#marketNameInput").val(data.marketName);
		     },  
		     error : function() {  
		    	 warningMessage("loadMarketInfoByMarketId failed");
		     }  
		});
} */

function loadProductCategory(marketId){
	initCategoryForAddPage(marketId);
}

function updateProvince(){
	//省
	$('#province_add').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryProvince' ,
		onSelect:function(record){
			$('#province_new').val(record.areaID);
			updateCity(record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#province_add').combobox('getData');
			if (useSSQCookie){
				if (data.length > 0) {
					$('#province_add').combobox('select', provinceId);
				}
			}else{
				if (data.length > 0) {
					$('#province_add').combobox('select', data[0].areaID);
				}
			}
		}
	});
}

function updateCity(provinceId){
	//
	$('#city_add').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryCity/'+ provinceId,
		onSelect:function(record){
			$('#city_new').val(record.areaID);
			updateArea(record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#city_add').combobox('getData');
			if (useSSQCookie){
				if (data.length > 0) {
					$('#city_add').combobox('select', cityId);
				}
			}else{
				if (data.length > 0) {
					$('#city_add').combobox('select', data[0].areaID);
				}
			}
		}
	});
}

function updateArea(cityId){
	//
	$('#area_add').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryArea/' + cityId,
		onSelect:function(record){
			$('#area_new').val(record.areaID);
			if(useSSQCookie){
				useSSQCookie = false;
				$('#address_add').val(address);
			}
		},
		onLoadSuccess : function(){
			var data = $('#area_add').combobox('getData');
			if (useSSQCookie){
				if (data.length > 0) {
					$('#area_add').combobox('select', areaId);
				}
			}else{
				if (data.length > 0) {
					$('#area_add').combobox('select', data[0].areaID);
				}
			}
		}
	});
}
//判断中英混合字符的字节数, 一个中文字符两个字节
function cnLength(Str) {   
    var escStr = escape(Str);   
    var numI = 0;   
    var escStrlen = escStr.length;   
    for (var i = 0; i < escStrlen; i++)   
        if (escStr.charAt(i) == '%')   
        if (escStr.charAt(++i) == 'u')   
        numI++;   
    return Str.length + numI;   
} 

// 新增表单提交数据校验 
function checkAddInfo(){
	
	// 商品名称校验
	if ($.trim($("#addForm #productName").val()) == ""){
		warningMessage("商品名称不能为空");
		return false;
	}
	if (cnLength($("#addForm #productName").val()) > 60){
		warningMessage("商品名称过长");
		return false;
	}
	// 供应量校验
/*  	if (isNaN($("#addForm #stockCount").val())){
		warningMessage("供应量必须是数字");
		return false;
	} 
	if ($("#addForm #stockCount").val() <= 0){
		warningMessage("供应量必须大于0");
		return false;
	}  */
 	if (checkDecimal($("#addForm #stockCount").val(), 8, 2) == 1){
		warningMessage("供应量输入有误, 数值长度过长,整数部分最多输入6位和小数点后最多2位");
		return false;
	} else if (checkDecimal($("#addForm #stockCount").val(), 8, 2) == 2){
		warningMessage("供应量输入有误, 小数点后位数过长(小数点后保留两位)");
		return false;
	} 
	
 	if (!$("#addForm #cmarket").val() || $("#addForm #cmarket").val() == -1){
		warningMessage("请选择农批市场!");
		return false;
	}  	
 	if (!$("#addForm #categoryId").val() || $("#addForm #categoryId").val() == -1){
		warningMessage("请选择商品分类!");
		return false;
	} 
	// 价格校验
	if ($("#priceType").val()=='0') {
		// 单价检测
		if (isNaN($("#addForm #price").val())){
			warningMessage("单价必须是数字");
			return false;
		}
		if($("#addForm #price").val() != '' && parseInt($("#addForm #price").val()) < 0){
			warningMessage("单价不能为负数");
			return false;
		}
		if (checkDecimal($("#addForm #price").val(), 10, 2) == 1){
			warningMessage("单价输入有误, 数值长度过长,整数部分最多输入8位和小数点后最多2位");
			return false;
		} else if (checkDecimal($("#addForm #price").val() == 2)){
			warningMessage("单价输入有误, 小数点后位数过长(小数点后保留两位)");
			return false;
		}
	}else {
		//多价检测
		$("#addForm #price").val("0");//提交数据时，单价必须要有值，否则后台无法注入
		if (!checkRow()){
			return false;
		}
	}

	// 详细信息
	if ($('textarea[name="description"]').val() && cnLength($('textarea[name="description"]').val()) > 5000){
		warningMessage("详细信息最多可输入5000字符(包含图片路径等元素的长度)");
		return false;
	}
	return true;
}

function isSpecialChar(value){
	var regex = /[~!@$%^&_+<>?:"{},\\;'[\]]/im;
	//regex = /[`~!@#$%^&*()+=|\\\]\[\]\{\}:;'\,.<>/?]{1,}/; 
	return regex.test(value);
}

//产地-省
function updateOriginProvince(){
	
	//省
	$('#originProvince_comp').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryProvince' ,
		onSelect:function(record){
			$('#originCity_comp').next(".combo").show();
			$('#originArea_comp').next(".combo").show();
			
			$('#originProvince_typeIn').val(record.areaID);
			updateOriginCity(record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#originProvince_comp').combobox('getData');
			//省份的数据肯定存在
			$('#originProvince_comp').combobox('select', data[0].areaID);
		}
	});
}

//产地-市
function updateOriginCity(provinceId){
	//
	$('#originCity_comp').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryCity/'+ provinceId,
		onSelect:function(record){
			var data = $('#originCity_comp').combobox('getData');
			if (data.length > 0) {
				$('#originCity_typeIn').val(record.areaID);
				updateOriginArea(record.areaID);
			}
		},
		onLoadSuccess : function(){
			var data = $('#originCity_comp').combobox('getData');
			//当市级数据不存在时, 则为港澳台;
			if (data.length > 0) {
				$('#originCity_comp').combobox('select', data[0].areaID);
			}else {
				hideOriginPlace(true, true);
				$('#originCity_typeIn').val(0);
				$('#originArea_typeIn').val(0);
			}
		}
	});
}

//产地-区/县
function updateOriginArea(cityId){
	//
	$('#originArea_comp').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryArea/' + cityId,
		onSelect:function(record){
			$('#originArea_typeIn').val(record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#originArea_comp').combobox('getData');
			$('#originArea_comp').combobox('select', data[0].areaID);
		}
	});
}

var hasSubmitInfos = false;

// 提交新增表单
function addFastProduct() {
	
	if (hasSubmitInfos){
		warningMessage("产品信息提交中, 请耐心等待...");
		return ;
	}
	
	if (!checkAddInfo()) return false;
	
	var url=CONTEXT+"fastProduct/addFastProduct";
	
	hasSubmitInfos = true ;
 	jQuery.post(url, $('#addForm').serialize(), function (data) {
 		data = $.parseJSON(data);
		if (data.status == 1) {
			slideMessage("操作成功！");
			//保存商品分类至cookie
			var typeLevels = $("#typeLevels").val();
			var categoryArrString = $("#categoryFirst").val();
			if (typeLevels >= 2){
				categoryArrString += "," + $("#categorySecond").val();
			} 
			if (typeLevels >= 3){
				categoryArrString += "," + $("#categoryThird").val();
			}
			//刷新父页面列表
			$("#productdg").datagrid('reload');
			$('#addDialog').dialog('close');
		} else {
			warningMessage(data.message);
			$('#addDialog').dialog('close');
			return;
		}
	}); 
}

// 选择市场
$("#marketBtn").click(function(){
	  // 动态创建新增页面(动态销毁)
	  $('<div></div>').dialog({
        id : 'marketSelectDialog',
        title : '选择市场',
        width : 600,
        height : 400,
        closed : false,
        cache : false,
        href : CONTEXT+'fastProduct/toMarketSelect/1', // 0-调用marketSelectCallBackForList回调函数, 1-调用marketSelectCallBack
        modal : true,
        onLoad : function() {
        },
        onClose : function() {
        	/* 
        	//市场设置为无效
        	$("#cmarket").val(-1);
        	$("#cmarketName").val("");
        	// 选中的分类设置为无效
        	$("#addForm #categoryId").val(-1);
        	//隐藏分类组件
        	$("#categoryPanel").hide();
        	 */
            $(this).dialog('destroy');
        },
        onOpen : function() {
        }
    });
});

// 选择市场回调
function marketSelectCallBack(index, row){
	$("#cmarket").val(row.id);
	$("#cmarketName").val(row.marketName);
	if (!row || !row.id || row.id == -1){
		hideProductCategory();
	}else{
		loadProductCategory(row.id);
	}
}

</script>

<style>
.priceTableContainer{display:none}
</style>


