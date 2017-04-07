<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<form id="addForm" method="post" action="product/add">

	<div style="padding: 20px;">
		<p style="height: 20px;">
		
		产品标题&nbsp; :
		<input type="text" id="productName_add" name="productName" value="" required="true"
			class="easyui-validatebox"  missingMessage="商品名称不能为空"   />
		农批中心&nbsp; :
			<!-- <input type="button" id="showMarketWin" value="选择街市">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
			<input type="text" id="marketNameInput" name="marketNameInput" value="" readonly>
			<input type="hidden" id="marketId_add" name="marketId" value="" readonly>
		会员&nbsp; :
			<input type="button" id="showMemberWin" value="选择会员">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="hidden" id="roleType" name="roleType" value="" >
			<input type="hidden" id="memberId_add" name="memberId" value="" >
			
		商铺&nbsp; :
			<input id="businessId_select_add" >
			<!-- <input type="button" id="showBusinessWin" value="选择商铺"><br> -->
			<input type="hidden" id="businessId_add" name="businessId" value="" >
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
		<div id="categoryPanel">
			<p style="height: 20px;">
				商品类目&nbsp; :
				<input id="productType1_add" >
				<input id="productType2_add" >
				<input id="productType3_add">
				<input id="productType4_add">
				<input type="hidden" id="typeLevels" name="typeLevels" value="3">
				<input type="hidden" id="categoryFirst" value="">
				<input type="hidden" id="categorySecond" value="">
				<input type="hidden" id="categoryThird" value="">
				<input type="hidden" id="categoryFourth" value="">
				<input type="hidden" id="categoryId_add" name="categoryId">
				
			</p>
		</div>
		<p style="height: 20px;">
			供应量&nbsp; :
			<input type="text" id="stockCount_add" name="stockCount" value="" data-options="required:true,min:0,precision:2"
					class="easyui-validatebox"	missingMessage="供应量不能为空"   maxlength="9" />
			<!-- 	单位 -->
			<input id="unit_add" name="unit" />
		</p>
		<p style="height: 20px;">
			价格&nbsp; : 
			<input id="priceType" name="priceType" />&nbsp;&nbsp;<span id="priceUnitSpan"></span>
	    </p>
	    <p id="priceAddContainer">
		    <input  type="text" id="price_add" name="price"  value="" data-options="required:true,min:0,precision:2" maxlength="11"
		    	  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
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
				//$('#price_add').val('0');
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
			console.log(record);
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
	
	initStockUnit();
	initPriceType();
	updateOriginProvince();
	
	$("#categoryPanel").hide();
	$("#productType1_add").hide();
	$("#productType2_add").hide();
	$("#productType3_add").hide();
	$("#productType4_add").hide();
	
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
	

	$('#showMarketWin').click(function(){
		$('#marketDialog').dialog({'title':'选择街市', 'width':700, 'height':300, 'href':CONTEXT+'product/marketSelect'}).dialog('open');
	});
	
	$("body").delegate("#showMemberWin","click",function(){
		$('#memberDialog').dialog({'title':'选择会员', 'width':700, 'height':300, 'href':CONTEXT+'product/memberSelect/1'}).dialog('open');
	});
/* 	$('#showBusinessWin').click(function(){
		$('#businessDialog').dialog({'title':'选择商铺', 'width':700, 'height':300, 'href':CONTEXT+'product/businessSelect'}).dialog('open');
	});	 */
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
	$('#productType1_add').next(".combo").hide();
	$('#productType2_add').next(".combo").hide();
	$('#productType3_add').next(".combo").hide();
	$('#productType4_add').next(".combo").hide();
}

//分类级联菜单start...
function updatePtype1ByMarkId(marketId){
	
	$("#categoryPanel").show();
	//初始化时先隐藏所有商品分类组件, 每加载一级则显示一级
	hideProductCategory();
	//商品分类
 	$('#productType1_add').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'product/listTopProductCategory/' + marketId ,
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
				$('#categoryId_add').val(record.categoryId);
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
			$('#productType1_add').next(".combo").show();
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
				$('#categoryId_add').val(record.categoryId);
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
			$('#productType2_add').next(".combo").show();
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
				$('#categoryId_add').val(record.categoryId);
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
			$('#productType3_add').next(".combo").show();
		}
	}); 
}

function updatePtype4(parentId){

	$('#productType4_add').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		onSelect:function(record){
			useCategoryCookie = false ;
			$('#categoryFourth').val(record.categoryId);
			//设置商品分类
			$('#categoryId_add').val(record.categoryId);
		},
		onLoadSuccess : function(){
			var data = $('#productType4_add').combobox('getData');
			if (data.length > 0) {
				if (useCategoryCookie){
					$('#productType4_add').combobox('select', $.trim(categoryCData[3]));
				}else{
					$('#productType4_add').combobox('select', data[0].categoryId);
				}
			}
			$('#productType4_add').next(".combo").show();
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


function marketSelectCallBack(index,row){
	$("#marketId_add").val(row.id);
	$("#showMarketWin").val(row.marketName);
	$('#marketDialog').dialog('close');
}

var useSSQCookie = false;
var ssqData ;
var addressData;

var useCategoryCookie = false;
var marketIdCookie ;
var categoryCData;


function memberCallBack(index,row){
	var memberId = row.memberId;
	$("#showMemberWin").val(row.mobile);
	$('#memberDialog').dialog('close');
	
	//用户类型
	var roleType ;
	//会员用户类型为4, 则产品用户类型为4, 对于其他的会员用户类型, 产品均设置用户类型为1
	var memLevel = row.level;
	if (memLevel == 4){
		roleType = 4 ;
	}else{
		roleType = 1;
	}
	//设置产品用户类型
	$("#roleType").val(roleType);
	$("#memberId_add").val(row.memberId);
	var memberCookie = $.cookie('SSQData'+ row.memberId);
	if (memberCookie){
		ssqData = memberCookie.split(",");
		provinceId = $.trim(ssqData[0]);
		cityId = $.trim(ssqData[1]);
		areaId = $.trim(ssqData[2]);
		address = $.cookie('addressData'+ row.memberId);
		useSSQCookie = true ;
	}
	
	
	//加载产地供应商对应的市场
	if (roleType == 4){
		//设置两级分类
		$("#typeLevels").val(3);
		$("#marketId_add").val(3);
		var categorykey = 'categoryCData' + $("#marketId_add").val();
		//商品分类cookie
		var categoryCookie = $.cookie(categorykey);
		if (categoryCookie){
			categoryCData = categoryCookie.split(",");
			useCategoryCookie = true ;
		}
		//初始化商品分类, 产地供应商的市场id固定为3
		loadProductCategory(3);
		loadMarketInfoByMarketId(3);
	}else{
		//设置三级分类
		$("#typeLevels").val(3);
	}
	//加载商铺列表
	loadBusinessListByMemberId(memberId, roleType);
}

//加载产地供应商对应的市场
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
}

function loadProductCategory(marketId){
	updatePtype1ByMarkId(marketId);
}


//加载商铺列表
function loadBusinessListByMemberId(memberId, roleType){
	//商铺
	$('#businessId_select_add').combobox({
		valueField:'businessId',
		textField:'shopsName',
		editable:false ,
		url: CONTEXT+'product/queryBusinessByMemberId/' + memberId ,
		onSelect:function(record){
			
			var businessId = record.businessId;
			//设置商铺Id
			$("#businessId_add").val(businessId);
			//加载商铺信息以初始化省市区地址信息
			loadBusinessInfoByBusinessId(businessId);
			//商铺所属会员非产地供应商
			if (parseInt(roleType) != 4){
				//加载市场信息以初始化商品分类
				loadMarketInfoBybusinessId(businessId);
			}
			
		},
		onLoadSuccess : function(){
			//清空上个会员的商铺
			$("#businessId_add").val("");
			var data = $('#businessId_select_add').combobox('getData');
			//默认选择第一个
			if (data.length > 0) {
				$('#businessId_select_add').combobox('select', data[0].businessId);
			}else{
				warningMessage("该会员没有商铺, 请先开通商铺, 否则无法发布商品");
			}
			//$("#businessId_add").removeAttr("readonly");
		}
	});
}
function loadBusinessInfoByBusinessId(businessId){
	 $.ajax( {  
		    url:CONTEXT+'product/queryBusinessById',// 跳转到 action  
		    data:{  
		    	businessId : businessId
		    },  
		    type:'post',  
		    dataType:'json',  
		    success:function(data) {  
		    	
		    	$("#province_add").removeAttr("readonly");
		    	$("#city_add").removeAttr("readonly");
		    	$("#area_add").removeAttr("readonly");
		    	
		    	if (useSSQCookie){
			    	updateProvince();
		    	}else {
		    		updateProvince();
		    	}
		     },  
		     error : function() {  
		    	 warningMessage("loadBusinessInfo error");
		     }  
		});
}
//根据 商铺id 查询市场
function loadMarketInfoBybusinessId(businessId){
	 $.ajax( {  
		    url:CONTEXT+'product/queryMarketByBusinessId',// 跳转到 action  
		    data:{  
		    	businessId : businessId
		    },  
		    type:'post',  
		    dataType:'json',  
		    success:function(data) { 
		    	if (data){
					//设置农批市场
					$("#marketId_add").val(data.id);
					$("#marketNameInput").val(data.marketName);
					
					//商品分类cookie
					var categorykey = 'categoryCData' + data.id;
					var categoryCookie = $.cookie(categorykey);
					if (categoryCookie){
						categoryCData = categoryCookie.split(",");
						useCategoryCookie = true ;
					}
					//初始化商品分类
					loadProductCategory(data.id);
		    	}else{
		    		slideMessage("该商家未绑定农批市场");
		    	}
		     },  
		     error : function() {  
		    	 warningMessage("loadMarketInfoBybusinessId failed");
		     }  
		});
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
function check(){
	
/*  	if (isSpecialChar($("#productName_add").val())){
		warningMessage("商品名称包含特殊字符");
		return false;
	}  */
	if ($.trim($("#productName_add").val()) == ""){
		warningMessage("商品名称不能为空");
		return false;
	}
	if (cnLength($("#productName_add").val()) > 60){
		warningMessage("商品名称过长");
		return false;
	}
	if ($.trim($("#stockCount_add").val()) == ""){
		warningMessage("供应量不能为空");
		return false;
	}
 	if (isNaN($("#stockCount_add").val())){
		warningMessage("供应量必须是数字");
		return false;
	} 
	if ($("#stockCount_add").val() <= 0){
		warningMessage("供应量必须大于0");
		return false;
	} 
 	if (checkDecimal($("#stockCount_add").val(), 8, 2) == 1){
		warningMessage("供应量输入有误, 数值长度过长,整数部分最多输入6位和小数点后最多2位");
		return false;
	} else if (checkDecimal($("#stockCount_add").val(), 8, 2) == 2){
		warningMessage("供应量输入有误, 小数点后位数过长(小数点后保留两位)");
		return false;
	} 
	
/* 	if ($.trim($("#marketId_add").val()) == ""){
		warningMessage("请选择街市");
		return false;
	} */
	if ($.trim($("#businessId_add").val()) == ""){
		warningMessage("请选择商铺! (先选择会员, 然后可以选择商铺)");
		return false;
	}
	if ($("#priceType").val()=='0') {
		//单价检测
/* 		if ($.trim($("#price_add").val()) == ""){
			warningMessage("单价不能为空");
			return false;
		} */
		if (isNaN($("#price_add").val())){
			warningMessage("单价必须是数字");
			return false;
		}
		if($("#price_add").val() != '' && parseInt($("#price_add").val()) < 0){
			warningMessage("单价不能为负数");
			return false;
		}
		if (checkDecimal($("#price_add").val(), 10, 2) == 1){
			warningMessage("单价输入有误, 数值长度过长,整数部分最多输入8位和小数点后最多2位");
			return false;
		} else if (checkDecimal($("#price_add").val() == 2)){
			warningMessage("单价输入有误, 小数点后位数过长(小数点后保留两位)");
			return false;
		}
	}else {
		//多价检测
		$("#price_add").val("0");//提交数据时，单价必须要有值，否则后台无法注入
		if (!checkRow()){
			return false;
		}
	}
/* 	if ( $.trim($("input[name='masterPicture']").val()) == ""){
		warningMessage("必须上传主图");
		return false;
	} */
	if ( $.trim($("input[name='appPicture']").val()) == ""){
		warningMessage("必须上传App图");
		return false;
	}
	if ( $.trim($("input[name='multiplePicture']").val()) == ""){
		warningMessage("必须上传多角度图");
		return false;
	}

	if (cnLength($('textarea[name="description"]').val()) > 5000){
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
function add() {
	
	if (hasSubmitInfos){
		warningMessage("产品信息提交中, 请耐心等待...");
		return ;
	}
	/* if(!$("#addForm").form('validate')){
		//return $("#addForm").form('validate');
	} */
	if (!check()) return false;
	var url=CONTEXT+"product/add";
	
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
			if (typeLevels >= 4){
				categoryArrString += "," + $("#categoryFourth").val();
			}
			//设置商品分类cookie
			var categorykey = 'categoryCData' + $("#marketId_add").val();
			$.cookie(categorykey, categoryArrString);

			//设置地址cookie
			var ssq = $("#province_new").val() + "," + $("#city_new").val()+ "," + $("#area_new").val();
			var ssqKey = 'SSQData'+$("#memberId_add").val() ;
			var addressKey = 'addressData' + $("#memberId_add").val() ; 
			$.cookie(ssqKey, ssq);
			$.cookie(addressKey, $("#address_add").val());
			
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


</script>

<style>
.priceTableContainer{display:none}
</style>










