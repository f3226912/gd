<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
    <input type="hidden" id="webUrl" name="webUrl" value="http://hnuser.cnhnb.com:80/uc-web/" />
    <input type="hidden" id="showImg" name="showImg" value="http://img.cnhnb.com/"/>
    <input type="hidden" id="userPictures" name="userPictures" value="">
    <input type="hidden" id="types" name="types" value="${types}">
      
<form id="editForm" method="post">
	<div style="padding: 20px;">
		<!-- 初始化页面时产品分类所在市场 -->
		<input type="hidden" id="cmarketId" name="cmarketId" value="${cmarketId}" >
			<p style="height: 20px;">
				<input type="hidden" id="cmarket" value="-1" />
				农批市场&nbsp; :
				<input type="text" id="cmarketName" value="${marketName}" readonly/> <input type="button" id="marketBtn" value="选择市场" />
			</p>		
		<p style=" height: 20px;">
			<input type="hidden" id="typeLevels" name="typeLevels" value="">
			商品类目&nbsp; :
			<input id="productType1" >
			<input id="productType2" >
			<input id="productType3">
			<!-- <input id="productType4"> -->
			<input type="hidden" id="categoryId" name="categoryId" value="${product.cateId }">
			
			<c:forEach var="item" items ="${categorys}">
				<input type="hidden" name="categoryId_default" value="${item.categoryId}">
			</c:forEach>
		</p>
		<p style=" height: 20px;">
			<input type="hidden" id="productId" name="productId" value="${product.productId}">
			商品名称&nbsp; :
			<input type="text" id="productName" name="productName" value="${product.productName}" required="true"
				class="easyui-validatebox"	missingMessage="商品名称不能为空"   />
				
<%-- 			农批中心&nbsp; :
			<input type="button" id="showMarketWin_edit" value="${marketName}">
			<input type="text" id="marketNameInput" name="marketNameInput" value="${marketName}" readonly>
			<input type="hidden" id="marketId_edit" name="marketId" value="${marketId}" >
			 --%>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 会员&nbsp; : -->
			<!-- 用户名 -->
			<%-- <input  value="${business.account}" disabled />&nbsp;&nbsp;&nbsp; --%>
			
			<!-- 手机号 -->
			<%-- <input  value="${business.mobile}" disabled />&nbsp;&nbsp;&nbsp;
			<input type="hidden" id="roleType" name="roleType" value="${business.level}" >
			<input type="hidden" id="typeLevels" name="typeLevels" value=""> --%>
			<%-- <input type="hidden" id="userId" name="userId" value="${business.userId}"> --%>
			
			<%-- <input type="button" id="showMemberWin_edit" value="${business.account}">&nbsp;&nbsp;&nbsp; --%>
			<!-- 账户的userId -->
			<!-- <input type="hidden" id="memberId_edit" name="memberId" value="" > -->
			<%-- 商铺&nbsp; :
			<input id="businessId_select_edit">
			<input type="hidden" id="businessId_edit" name="businessId" value="${product.businessId}" > --%>
			<!-- <input type="button" id="showBusinessWin_edit" value="选择商铺"><br> -->
		</p>

<%-- 		<p style="height: 20px;">
			联系地址&nbsp; :
			<input id="province_edit"  >
			<input id="city_edit"   >
			<input id="area_edit" >
			<input type="hidden" id="province_new_edit" name="provinceId" value="${product.provinceId}" >
			<input type="hidden" id="city_new_edit" name="cityId" value="${product.cityId}"  >
			<input type="hidden" id="area_new_edit" name="areaId" value="${product.areaId}" >
			具体地址 : <input type="text" id="address_edit" name="address" value="${product.address}" >
		</p> --%>
		<p style="height: 20px;">
			商品产地&nbsp; :
			<input id="originProvince_comp" >
			<input id="originCity_comp"  >
			<input id="originArea_comp"  >
			
			<input type="hidden" id="originProvince_typeIn" name="originProvinceId" value="${product.originProvinceId}">
			<input type="hidden" id="originCity_typeIn" name="originCityId" value="${product.originCityId}">
			<input type="hidden" id="originArea_typeIn" name="originAreaId" value="${product.originAreaId}">
		</p>		

		<p style="height: 20px;">
			供应量&nbsp; :
			<input type="text" id="stockCount" name="stockCount" value="${product.stockCount}" required="true"
				class="easyui-validatebox" maxlength="9"   />
			<!-- 单位 -->
			<input id="unit_edit" name="unit" />
		</p>
	   <p style=" height: 20px;">
	   		价格&nbsp; : 
	   		<input id="priceType" name="priceType" value="${product.priceType}" class="easyui-combobox"/>
<%-- 	        <input id="priceType" name="priceType" value="${product.priceType}" class="easyui-combobox" 
	        data-options="
	        valueField: 'value',
			textField: 'label',
			data: [{
				label: '单价',
				value: '0'
			},{
				label: '多价',
				value: '1'
			}],
	         onSelect: function(rec){   
		         $('#editForm #priceType').val(rec.value);
		         if(rec.value == '1'){
		         	$('#editForm #priceEditContainer').hide();
		         	$('#editForm #priceEditTableContainer').show();
		         	addPriceRow();
		         }else{
		         	$('#editForm #priceEditContainer').show();
		         	$('#editForm #priceEditTableBody').empty();
		         	$('#editForm #priceEditTableContainer').hide();
		         }
	        },onLoadSuccess : function(){
		        var v = $('#editForm #priceType').val();
		        if(v=='1'){
		         	$('#editForm #priceEditContainer').hide();
		         	$('#editForm #priceEditTableContainer').show();
		        }else{
		        	$('#editForm #priceEditContainer').show();
		         	$('#editForm #priceEditTableBody').empty();
		         	$('#editForm #priceEditTableContainer').hide();
		        }
	        	$('#editForm #priceType').combobox('select', v);
	        }" />   --%>
	        &nbsp;&nbsp;<span id="priceUnitSpan"></span>
	        
		</p>
		<p id="priceEditContainer">
			 <input  type="text" id="price" name="price"  value="" data-options="required:true,min:0,precision:2" 
	      maxlength="11"	  style="width:150px" >&nbsp;
	    &nbsp;&nbsp;&nbsp;&nbsp;
		</p>
		
	</div>
	
	<div id="priceEditTableContainer" class="priceEditTableContainer">
		      <table id="priceEditTable" style="text-align: center;">
			    <thead id="tdHead">
			        <tr>
			            <th><div style="width: 120px;">最小起订量</div></th>
			            <th><div style="width: 120px;">截止起订量</div></th>
			            <th><div style="width: 120px;">单价</div></th>
			            <th><div style="width: 200px;">备注</div></th>
			            <th><span><a href="javascript:void(0)" onclick="addPriceRow()">+增加价格区间</a></span></th>
			        </tr>
			    </thead>
			    <tbody id="priceEditTableBody">
			    	<c:forEach var="unitPrice"   items="${product.priceDtoList}"   varStatus="i">
			    		<tr id="priceDtoList_${i.index }">
			    		<td><input type="text" style="width: 80px;" id="priceDtoList[${i.index }].buyCountStart" name="priceDtoList[${i.index }].buyCountStart" class="CbuyCountStart" value="${unitPrice.buyCountStart }"  ></td>
			    		<td><input type="text" style="width: 80px;" id="priceDtoList[${i.index }].buyCountEnd" name="priceDtoList[${i.index }].buyCountEnd" class="CbuyCountEnd" value="${unitPrice.buyCountEnd }"  ></td>
			    		<td><input type="text" style="width: 80px;" id="priceDtoList[${i.index }].price" name="priceDtoList[${i.index }].price" class="Cprice" value="${unitPrice.price }"  ></td>
			    		<td><label>截止起订量为空表示大于当前行起始起订量者,均使用当前行价格</label></td>
			    		<td><span><a href="javascript:void(0)" onClick="deletePriceRow('priceDtoList_${i.index }')">-删除</a></span></td>
			    		</tr>
			    	</c:forEach>
			    </tbody>
			</table>
	</div>
	
   <div class="user-iteam" style="padding-left: 20px;">
<%--         <p>更换主图</p>
        <div class="fl mr_10" id="uploadContainer_modify">
            <input type="file" class="g-u input01" id="upload_btn_modify" value="" name="productPicture" />
            <input type="hidden" id="J_Urls_modify" name="masterPicture" value="${masterPicture}" />
        </div>
        <div class="form-context">
            <div class="clear"></div>
            <ul id="picture_queen_modify" class="grid"></ul>
        </div> 
        <BR> --%>
        
        <p>更换App图</p>
        <div class="fl mr_10" id="uploadContainerApp_modify">
            <input type="file" class="g-u input01" id="upload_btn_app_modify" value="" name="productPicture" />
            <input type="hidden" id="J_Urls_App_modify" name="appPicture"  />
        </div>
        <div class="form-context">
            <div class="clear"></div>
            <ul id="picture_queen_app_modify" class="grid">
    			<script type="text/uploader-files">
							${appPicture}
    			</script>
				<script type="text/uploader-theme">
        
					<li id="queue-file-{id}" class="g-u" data-name="{name}">
                		<div class="pic">
                    		<a href="javascript:void(0);"><img class="J_Pic_{id} preview-img" src="" /></a>
                		</div>
                		<div class=" J_Mask_{id} pic-mask"></div>
                			<div class="status-wrapper">
                    			<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    			<div class="status start-status progress-status success-status">
                        		<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>上传中...</div>
                    		</div>
                    		<div class="status error-status">
                        	<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                		</div>
                		<div class="imageUploader_menu"> <a href="javascript:void(0);" class="imageUploader_but1" id="imageUploader_but1_{id}"  title="左移">&nbsp;</a><a href="javascript:void(0);" class="imageUploader_but2" title="右移"  id="imageUploader_but2_{id}">&nbsp;</a><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            		</li>
    					</script>
            </ul>
        </div> 
        <BR>        
        
        <p>更换多角度图</p>
        <div class="fl mr_10" id="uploadContainerMultiAngle_modify">
            <input type="file" class="g-u input01" id="upload_btn_multiAngle_modify" value="" name="productPicture" />
            <input type="hidden" id="J_Urls_MultiAngle_modify" name="multiplePicture" value="" />
        </div>
        <div class="form-context">
            <div class="clear"></div>
            <ul id="picture_queen_MultiAngle_modify" class="grid">
            				<script type="text/uploader-files">
								${multiplePicture}
    						</script>
	    					<script type="text/uploader-theme">
        
							<li id="queue-file-{id}" class="g-u" data-name="{name}">
                				<div class="pic">
                    				<a href="javascript:void(0);"><img class="J_Pic_{id} preview-img" src="" /></a>
                				</div>
                				<div class=" J_Mask_{id} pic-mask"></div>
                				<div class="status-wrapper">
                    				<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    					<div class="status start-status progress-status success-status">
                        					<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>上传中...</div>
                    					</div>
                    				<div class="status error-status">
                        				<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                					</div>
                				<div class="imageUploader_menu"> <a href="javascript:void(0);" class="imageUploader_but1" id="imageUploader_but1_{id}"  title="左移">&nbsp;</a><a href="javascript:void(0);" class="imageUploader_but2" title="右移"  id="imageUploader_but2_{id}">&nbsp;</a><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            				</li>
    					</script>
            </ul>
        </div> 
        <BR>   
    </div>
    		<c:forEach var="item" items ="${multiplePictureList}">
				<input type="hidden" name="multiplePictureListItem" value="${item.url}">
			</c:forEach>
    <div>
    	<p style="height: 20px;">
			详细信息&nbsp; :
			<textarea rows="3" cols="30" style="width:100%;font-size:14px;" 
					name="description" id="description_edit" >${product.description}</textarea>
		</p>
		<br><br><br><br><br><br><br><br><br><br>

		<p style="height: 20px;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			信息有效期&nbsp; :
			7天<input type="radio"  name="infoLifeDay" value="7" 
				<c:if test="${product.infoLifeDay == 7}">checked="checked"</c:if> >&nbsp;&nbsp;
			15天<input type="radio"  name="infoLifeDay" value="15" 
				<c:if test="${product.infoLifeDay == 15}">checked="checked"</c:if> >&nbsp;&nbsp;
			30天<input type="radio"  name="infoLifeDay" value="30" 
				<c:if test="${product.infoLifeDay == 30}">checked="checked"</c:if> >&nbsp;&nbsp;
		</p>
    </div>
    
</form>

<script type="text/javascript" >
debugger ;
function initStockUnit(){
	var initUnit = "${product.unit}";
 	$('#unit_edit').combobox({
		valueField:'valueString',
		textField:'keyString',
		editable:false ,
		url: CONTEXT+'product/unitKeyValuePair',
		onSelect:function(record){
			$("#priceUnitSpan").text("元/"+record.keyString);
		},
		onLoadSuccess : function(){
			var data = $('#unit_edit').combobox('getData');
			if (data.length > 0) {
				$('#unit_edit').combobox('select', initUnit);
				//$('#unit_edit').combobox('select', data[0].valueString);
			}
		}
	}); 
}

var multiplePictureArr = $("input[name='multiplePictureListItem']");
var multipicStrng = new Array();
for(var i = 0; i < multiplePictureArr.length; i++){
	var initurl = '${imgHostUrl}' + multiplePictureArr[i].value ;
	multipicStrng[i] = {"dataOriginalUrl" : initurl, "url": initurl};
	//console.log(multipicStrng);
}  

var provinceId ;
var cityId ;
var areaId ;
var address ;

var originProvinceId ;
var originCityId ;
var originAreaId ;

var productId = $("#editForm #productId").val();
var types = $("#editForm #types").val();

var masterUrl = CONTEXT+'product/uploadProductPic/';
var multiAngleUrl = CONTEXT+'product/uploadProductPic/';
var webUrl = CONTEXT+'product/uploadProductPic/';
var appUrl = CONTEXT+'product/uploadProductPic/';

// 仅供商品分类使用
var first = true;
var defaultCategorys = document.getElementsByName("categoryId_default");

function initAddress(){
/* 	//产品地址(与产地不同)
	provinceId = $("#province_new_edit").val();
	cityId = $("#city_new_edit").val();
	areaId = $("#area_new_edit").val();
	address = $("#address_edit").val();
	updateProvince(); */
	
	//产地
	originProvinceId = $("#originProvince_typeIn").val();
	originCityId = $("#originCity_typeIn").val();
	originAreaId = $("#originArea_typeIn").val();
	updateOriginProvince();
}

function fixNum(num){
	var numStr = num + "";
	var index = numStr.indexOf(".");
	if ( index != -1){
			var savingNum = numStr.substring(index+1).length;
			return (num * Math.pow(10, savingNum))/ Math.pow(10, savingNum)
	}
	return num;
}

$(document).ready(function(){
	
	// 价格
	var initPrice = "${product.price}";
	$("#price").val(new Number(initPrice));
	// 供应量
	var initStockCount = "${product.stockCount}";
	$("#editForm #stockCount").val(fixNum(new Number(initStockCount)));
	// 单位
	initStockUnit();
	// 产地
	initAddress();
	// 商品分类
	initProductCategorys();
	// 价格类型
	initPriceType();
	
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
	
	
	if (types){
		var typeArray = types.split(",");
		for(var item in typeArray){
			if (typeArray[item] == 1){
				//$("#upload_btn").val("更换主图");
				masterUrl = CONTEXT+'changeProductPic/';
			}else if (typeArray[item] == 2){
				//$("#upload_btn_multiAngle").val("更换多角度图");
				multiAngleUrl = CONTEXT+'changeProductPic/';
			}else if (typeArray[item] == 3){
				//$("#upload_btn_web").val("更换Web图");
				webUrl = CONTEXT+'changeProductPic/';
			}else if (typeArray[item] == 4){
				//$("#upload_btn_app").val("更换App图");
				appUrl = CONTEXT+'changeProductPic/';
			}
			
		}
	}
	
	initUploadModule("upload_btn_multiAngle_modify",multiAngleUrl,  "picture_queen_MultiAngle_modify", "J_Urls_MultiAngle_modify", 4);
    initUploadSignle("upload_btn_app_modify",appUrl, "picture_queen_app_modify", "J_Urls_App_modify");
	
	var filesStr = '';
     if(filesStr != ''){
        var fileList = eval("("+  filesStr + ")");
        for (var i = 0; i < fileList.length; i++) {
            $("#editForm #picContainer").append("<input name=\"pictureUrl\" id=\"url+"+i+"\" type=\"hidden\" value=\""+fileList[i].dataOriginalUrl+"\" />");
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
     
});

function initProductCategorys() {
	
/* 	$("#productType1").hide();
	$("#productType2").hide();
	$("#productType3").hide();
	$("#productType4").hide();
	//当会员的用户类型是4时, 新增产品时产品的roleType肯定也是4
	var marketId = $("#marketId_edit").val();
	if(parseInt($("#roleType").val()) == 4){
		//产地供应商设置两级分类	<-- 已过时
		//$("#typeLevels").val(2);
		//产地供应商设置三级分类
		$("#typeLevels").val(3);
		marketId = 3;
	}else{
		//产地供应商设置两级分类
		$("#typeLevels").val(3);
	} */
	
	$("#typeLevels").val(3);
	//初始化分类
	loadProductCategory($("#cmarketId").val(), true);
	
}

//加载商品分类
function loadProductCategory(marketId, init){
	updatePtype1(marketId, init);
}

function updatePtype1(marketId, init){
	//初始化时先隐藏所有商品分类组件, 每加载一级则显示一级
	//hideProductCategory();
	
	$('#editForm #productType1').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'fastProduct/listTopProductCategory/' + marketId ,
		onSelect:function(record){
			var typeLevels = parseInt($("#typeLevels").val());
			if (typeLevels >= 2){
				updatePtype2(record.categoryId, init);
			}else{
				//设置商品分类
				$('#editForm #categoryId').val(record.categoryId);
			}
		},
		onLoadSuccess : function(){
			var data = $('#editForm #productType1').combobox('getData');
			var typeLevels = parseInt($("#typeLevels").val());
			if (data.length > 0) {
				if (first){
					if (typeLevels < 2){
						first = false;
					}
					//alert("defaultCategorys[0] : " + defaultCategorys[0].value);
					if (init){
						$('#editForm #productType1').combobox('select', defaultCategorys[0].value);
					}else{
						$('#editForm #productType1').combobox('select', data[0].categoryId);
					}
				}else {
					$('#editForm #productType1').combobox('select', data[0].categoryId);
				}
			}
			$('#productType1').next(".combo").show();
			
		}
	});
	
}

function updatePtype2(parentId, init){
	$('#editForm #productType2').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		onSelect:function(record){
			var typeLevels = parseInt($("#typeLevels").val());
			if (typeLevels >= 3){
				updatePtype3(record.categoryId,init);
			}else{
				//设置商品分类
				$('#editForm #categoryId').val(record.categoryId);
			}
		},
		onLoadSuccess : function(){
			var data = $('#productType2').combobox('getData');
			var typeLevels = parseInt($("#typeLevels").val());
			if (data.length > 0) {
				if (first){
					if (typeLevels < 3){
						first = false;
					}
					//alert("defaultCategorys[1] : " + defaultCategorys[1].value);
					if (init){
						$('#editForm #productType2').combobox('select', defaultCategorys[1].value);
					}else{
						$('#editForm #productType2').combobox('select', data[0].categoryId);
					}
				}else{
					$('#editForm #productType2').combobox('select', data[0].categoryId);
				}
			}
			$('#productType2').next(".combo").show();
		}
	}); 
}

function updatePtype3(parentId, init){
	$('#editForm #productType3').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		onSelect:function(record){
			var typeLevels = parseInt($("#typeLevels").val());
			if (typeLevels >= 4){
				updatePtype4(record.categoryId, init);
			}else{
				//设置商品分类
				$('#editForm #categoryId').val(record.categoryId);
			}
		},
		onLoadSuccess : function(){
 			var data = $('#productType3').combobox('getData');
 			var typeLevels = parseInt($("#typeLevels").val());
			if (data.length > 0) {
				if (first){
					if (typeLevels < 4){
						first = false;
					}
					//alert("defaultCategorys[2] : " + defaultCategorys[2].value);
					//$('#productType3').combobox('select', defaultCategorys[2].value);
					if (init){
						$('#editForm #productType3').combobox('select', defaultCategorys[2].value);
					}else{
						$('#editForm #productType3').combobox('select', data[0].categoryId);
					}
				}else{
					$('#productType3').combobox('select', data[0].categoryId);
				}
			}
			$('#productType3').next(".combo").show();
		}
	}); 
}

function updatePtype4(parentId, init){
	$('#editForm #productType4').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		onSelect:function(record){
			//设置商品分类
			$('#editForm #categoryId').val(record.categoryId);
		},
		onLoadSuccess : function(){
 			var data = $('#productType4').combobox('getData');
			if (data.length > 0) {
				if (first){
					first = false;
					//alert("defaultCategorys[3] : " + defaultCategorys[3].value);
					//$('#productType4').combobox('select', defaultCategorys[3].value);
					if (init){
						$('#editForm #productType4').combobox('select', defaultCategorys[3].value);
					}else{
						$('#editForm #productType4').combobox('select', data[0].categoryId);
					}
				}else{
					$('#productType4').combobox('select', data[0].categoryId);
				}
			}
			$('#productType4').next(".combo").show();
		}
	}); 
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
		//alert(len + "_"+validNum +"_"+savingNum);
		if (len > (validNum - savingNum)  ){
			//warningMessage("数值长度过长");
			return 1;
		}
	}
	return 0;
}
/**
 * 计算多价列表中的价格区间的行数
 */
function countRow(tableId){
	 return document.getElementById(tableId).getElementsByTagName('tr').length-1;//减去表头所占的一行
}
function addPriceRow(){
	 var len = countRow("priceEditTable");//$('#priceTable').datagrid('getRows').length;
	 if (len > 0){
		 if(len>=3){
			 warningMessage("最多只能添加三个区间价格！");
			 return false;
		 }
		 var buyCountStarts=$("#editForm .CbuyCountStart"); 
		 var buyCountEnds=$("#editForm .CbuyCountEnd"); 
		 var prices=$("#editForm .Cprice"); 
		 for (var i = 0; i < len ; i++){
			if (!buyCountStarts[i].value || !buyCountEnds[i].value || !prices[i].value ){
				warningMessage("存在未填写内容的行, 不能添加新行");
				return false;
			}
		}
	 }
 var trID="priceDtoList_"+len;
 var innerHtml='<tr id="'+trID+'">';
 innerHtml+='<td><input type="text" style="width: 80px;" id="priceDtoList['+len+'].buyCountStart" name="priceDtoList['+len+'].buyCountStart" class="CbuyCountStart" value="" maxlength="9" ></td>';
 innerHtml+='<td><input type="text" style="width: 80px;" id="priceDtoList['+len+'].buyCountEnd" name="priceDtoList['+len+'].buyCountEnd" class="CbuyCountEnd"value="" maxlength="9">';
 innerHtml+='<td><input type="text" style="width: 80px;" id="priceDtoList['+len+'].price" name="priceDtoList['+len+'].price" class="Cprice" value="" maxlength="11"></td>';
 innerHtml+='<td><label>不填写截止起订量表示(...)</label></td>';
 innerHtml+='<td><span><a href="javascript:void(0)" onClick="deletePriceRow(\''+trID+'\')">-删除</a></span></td>';
 innerHtml+='</tr>';
 $("#editForm #priceEditTable").append(innerHtml);  
//  $("#tdHead").after(innerHtml);  
 $("#editForm #priceEditTable").show();
}

function deletePriceRow(r){
	$("#"+r).remove();
}

function checkRow(){
	//var rows = $('#priceTable').datagrid('getRows');
/*	var starts = document.getElementsByName("buyCountStart");
	var ends = document.getElementsByName("buyCountSEnd");*/
	var starts=$(".CbuyCountStart"); 
	var ends=$(".CbuyCountEnd"); 
	var prices=$(".Cprice"); 
	var len = starts.length;
	for (var i = 0; i < len ; i++){
		//价格监测
		if (isNaN(prices[i].value)){
			warningMessage("第" + (i+1) + "行的数据输入有误, 价格必须为数字");
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

 function isSpecialChar(value){
	var regex = /[~!@$%^&_+<>?:"{},\\;'[\]]/im;
	//regex = /[`~!@#$%^&*()+=|\\\]\[\]\{\}:;'\,.<>/?]{1,}/; 
	return regex.test(value);
} 

function check(){
	if ($.trim($("#editForm #productName").val()) == ""){
		warningMessage("商品名称不能为空");
		return false;
	}
/*  	if (isSpecialChar($("#editForm #productName").val())){
		warningMessage("商品名称包含特殊字符");
		return false;
	}  */
	
	if ($("#editForm #priceType").val()=='0') {
		//单价检测
/* 		if ($.trim($("#editForm #price").val()) == ""){
			warningMessage("价格不能为空");
			return false;
		} */
		if (isNaN($("#editForm #price").val())){
			warningMessage("单价必须是数字");
			return false;
		}
		
		if($("#editForm #price").val() != '' && parseInt($("#editForm #price").val()) < 0){
			warningMessage("单价不能为负数");
			return false;
		}
		
		if (checkDecimal($("#editForm #price").val(), 10, 2) == 1){
			warningMessage("单价输入有误, 数值长度过长,整数部分最多输入8位和小数点后最多2位");
			return false;
		} else if (checkDecimal($("#editForm #price").val(), 10, 2) == 2){
			warningMessage("单价输入有误, 小数点后位数过长(小数点后保留两位)");
			return false;
		}
		
	}else {
		//多价检测
		$("#editForm #price").val("0");//提交数据时，单价必须要有值，否则后台无法注入
		if (!checkRow()){
			return false;
		}
	}
/* 	if ($.trim($("#editForm #stockCount").val()) == ""){
		warningMessage("供应量不能为空");
		return false;
	}
	if (isNaN($("#editForm #stockCount").val())){
		warningMessage("供应量必须是数字");
		return false;
	} 
	if ($("#editForm #stockCount").val() <= 0){
		warningMessage("供应量必须大于0");
		return false;
	} 
 	if (checkDecimal($("#editForm #stockCount").val(), 8, 2) == 1){
		warningMessage("供应量输入有误, 数值长度过长,整数部分最多输入6位和小数点后最多2位");
		return false;
	} else if (checkDecimal($("#editForm #stockCount").val(), 8, 2) == 2){
		warningMessage("供应量输入有误, 小数点后位数过长(小数点后保留两位)");
		return false;
	}  */
	if (cnLength($("#editForm #productName").val()) > 60){
		warningMessage("商品名称过长");
		return false;
	}
	
	if (cnLength($("#description_edit").val()) > 5000){
		warningMessage("详细信息最多可输入5000字符(包含图片路径等元素的长度)");
		return false;
	}
	//不上传则不修改图片
/* 	if ( $.trim($("input[name='masterPicture']").val()) == ""){
		warningMessage("必须上传主图");
		return false;
	} 
	if ( $.trim($("input[name='appPicture']").val()) == ""){
		warningMessage("必须上传App图");
		return false;
	}
	if ( $.trim($("input[name='multiplePicture']").val()) == ""){
		warningMessage("必须上传多角度图");
		return false;
	}*/
	
	return true;
}

function editProduct() {
	if (!check()) return false;
	var url=CONTEXT+"fastProduct/editProduct";
 	jQuery.post(url, $('#editForm').serialize(), function (data) {
 		data = $.parseJSON(data);
		if (data.status == 1) {
			slideMessage("编辑成功！");
			//刷新父页面列表
			$("#productdg").datagrid('reload');
			$('#editDialog').dialog('close');
		} else {
			warningMessage("编辑失败！");
			$('#editDialog').dialog('close');
			return ;
		}
	}); 
}

function marketSelectCallBack(index,row){
	$("#marketId_edit").val(row.id);
	$("#showMarketWin_edit").val(row.marketName);
	$('#marketDialog').dialog('close');
}


function businessCallback(index, row){
	$("#businessId_edit").val(row.businessId);
	$("#showBusinessWin_edit").val(row.shopsName);
	$('#businessDialog').dialog('close');
	
	var businessId = $("#businessId_edit").val();
	 $.ajax( {  
		    url:CONTEXT+'product/queryBusinessById',// 跳转到 action  
		    data:{  
		    	businessId : businessId
		    },  
		    type:'post',  
		    dataType:'json',  
		    success:function(data) {  
		    	//商家地址不为空
		    	if (data.provinceId){
			    	provinceId = data.provinceId;
			    	cityId = data.cityId;
			    	areaId = data.areaId;
			    	address = data.address;
			    	updateProvince();
		    	}else{
		    		updateProvince();
		    	}
		     },  
		     error : function() {  
		    	 alert("error");
		     }  
		});
}

function memberCallBack(index,row){
	var memberId = row.memberId;
	$('#memberDialog').dialog('close');
	$("#showMemberWin_edit").val(row.account);
	//$("#businessId_select_edit").removeAttr("readonly");
	
	//用户类型
	var roleType = row.level;
	//设置产品用户类型
	$("#roleType").val(roleType);
	//加载产地供应商对应的市场
	if (roleType == 4){
		//设置两级分类
		$("#typeLevels").val(2);
		//产地供应商的市场id固定为3
		updatePtype1ByMarkId(3);
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
//隐藏商品分类组件
function hideProductCategory(){
	$('#productType1').next(".combo").hide();
	$('#productType2').next(".combo").hide();
	$('#productType3').next(".combo").hide();
	$('#productType4').next(".combo").hide();
}
function updatePtype1ByMarkId(marketId){
	
	hideProductCategory();
	//商品分类
 	$('#productType1').combobox({
		valueField:'categoryId',
		textField:'cateName',
		editable:false ,
		url: CONTEXT+'product/listTopProductCategory/' + marketId ,
		onSelect:function(record){
			var typeLevels = parseInt($("#typeLevels").val());
			if ( typeLevels >= 2){
				updatePtype2(record.categoryId);
			}else{
				//设置商品分类
				$('#editForm #categoryId').val(record.categoryId);
			}
		},
		onLoadSuccess : function(){
			var data = $('#productType1').combobox('getData');
			if (data.length > 0) {
				$('#productType1').combobox('select', data[0].categoryId);
			}
			$('#productType1').next(".combo").show();
		}
	}); 
}


var businessFirst = true ;
function initBusinessInfo(){
	var memberId = $("#userId").val();
	//alert("memberId" + memberId);
	//初始化页面时从后台传递过来的商铺id, 即新增产品的时候选择的商铺
	var originBusinessId = $("#businessId_edit").val();
	//alert("originBusinessId" + originBusinessId);
	//商铺
	$('#businessId_select_edit').combobox({
		valueField:'businessId',
		textField:'shopsName',
		editable:false ,
		url: CONTEXT+'product/queryBusinessByMemberId/' + memberId ,
		onSelect:function(record){
			var businessId = record.businessId;
			//页面初始化显示已选择的商铺时, 不触发省市区地址的级联, 因为新增产品时可以改变产品发布地址导致该地址与商铺地址不一样
			if (!businessFirst){
				//设置商铺Id
				$("#businessId_edit").val(businessId);
				//加载商铺信息以初始化省市区地址信息
				loadBusinessInfoByBusinessId(businessId);
				//加载市场信息以初始化商品分类
				loadMarketInfoBybusinessId(businessId);
			}else{
				businessFirst = false ;
			}
		},
		onLoadSuccess : function(){
			//清空上个会员的商铺
			//$("#businessId_edit").val("");
			var data = $('#businessId_select_edit').combobox('getData');
			//默认选择新增产品的时候选择的商铺
			if (data.length > 0) {
				if (businessFirst){
					$('#businessId_select_edit').combobox('select', originBusinessId);
					//businessFirst = false ;
				}else{
					$('#businessId_select_edit').combobox('select', data[0].businessId);
				}
			}else{
				//新增产品页面必须选择商铺, 所以该分支不可达
				//warningMessage("该会员没有商铺, 请先开通商铺, 否则无法发布商品");
			}
		}
	});
}

function loadBusinessListByMemberId(memberId, roleType){
	//商铺
	$('#businessId_select_edit').combobox({
		valueField:'businessId',
		textField:'shopsName',
		editable:false ,
		url: CONTEXT+'product/queryBusinessByMemberId/' + memberId ,
		onSelect:function(record){
			var businessId = record.businessId;
			//设置商铺Id
			$("#businessId_edit").val(businessId);
			//加载商铺信息以初始化省市区地址信息
			loadBusinessInfoByBusinessId(businessId);
			//alert("roleTyperoleType" + roleType);
			//商铺所属会员非产地供应商
			if (parseInt(roleType) != 4){
				//加载市场信息以初始化商品分类
				loadMarketInfoBybusinessId(businessId);
				//loadProductCategory($("#marketId_edit").val());
			}
			
		},
		onLoadSuccess : function(){
			//清空上个会员的商铺
			$("#businessId_edit").val("");
			var data = $('#businessId_select_edit').combobox('getData');
			//alert("data.length"+data.length);
			//默认选择第一个
			if (parseInt(data.length) > 0) {
				$('#businessId_select_edit').combobox('select', data[0].businessId);
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
/* 		    	//商家地址不为空
		    	if (data.provinceId){
			    	provinceId = data.provinceId;
			    	cityId = data.cityId;
			    	areaId = data.areaId;
			    	address = data.address;
			    	//updateProvince(true);
			    	updateProvince();
		    	}else{
		    		updateProvince();
		    	} */
		     },  
		     error : function() {  
		    	 warningMessage("loadBusinessInfo error");
		     }  
		});
}
//根据商铺id查询市场
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
					$("#marketId_edit").val(data.id);
					$("#marketNameInput").val(data.marketName);
					//商品分类
					loadProductCategory(data.id, false);
		    	}else{
		    		slideMessage("该商家未绑定农批市场");
		    	}
		     },  
		     error : function() {  
		    	 warningMessage("loadMarketInfo failed");
		     }  
		});
}
var area_first = true;
function updateProvince(){
	//省
	$('#province_edit').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryProvince' ,
		onSelect:function(record){
			$('#province_new_edit').val(record.areaID);
			updateCity( record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#province_edit').combobox('getData');
			if (area_first){
				if (provinceId){
					$('#province_edit').combobox('select', provinceId);
				}else {
					$('#province_edit').combobox('select', data[0].areaID);
				}
			}else{
				if (data.length > 0) {
					$('#province_edit').combobox('select', data[0].areaID);
				}
			}
		}
	});
}

function updateCity(provinceId){
	//
	$('#city_edit').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryCity/'+ provinceId,
		onSelect:function(record){
			$('#city_new_edit').val(record.areaID);
			updateArea( record.areaID);
		},
		onLoadSuccess : function(){
			var data = $('#city_edit').combobox('getData');
			if (area_first){
				if (cityId){
					$('#city_edit').combobox('select', cityId);
				}else{
					$('#city_edit').combobox('select', data[0].areaID);
				}
			}else{
				if (data.length > 0) {
					$('#city_edit').combobox('select', data[0].areaID);
				}
			}
		}
	});
}

function updateArea(cityId){
	//
	$('#area_edit').combobox({
		valueField:'areaID',
		textField:'area',
		editable:false ,
		url: CONTEXT+'product/queryArea/' + cityId,
		onSelect:function(record){
			$('#area_new_edit').val(record.areaID);
			if(area_first){
				$('#address_edit').val(address);
			}
		},
		onLoadSuccess : function(){
			var data = $('#area_edit').combobox('getData');
			if (area_first){
				area_first = false;
				if (areaId){
					$('#area_edit').combobox('select', areaId);
				}else{
					$('#area_edit').combobox('select', data[0].areaID);
				}
			}else{
				$('#area_edit').combobox('select', data[0].areaID);
			}
		}
	});
}

function initUploadModule(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr, certification){
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
	        certification : certification//此处是附加参数，已经上传需要第二次修，是否可以修改
	};
	gdKissyMultipleUploader.init(config_mutiple ,function(uploader){
	     var filesStr = config_mutiple.filesStr;
	     var certification = config_mutiple.certification;
	     if(filesStr != ''&&filesStr!=undefined){
	        //var fileList = eval("("+  filesStr + ")");
	        
	        gdKissyMultipleUploader.addFiles(filesStr,uploader);
	        
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
	        //var fileList = eval("("+  filesStr + ")");
	        
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

function hideOriginPlace(hideCity, hideArea){
	if (hideCity){
		$('#originCity_comp').next(".combo").hide();
	}
	if (hideArea){
		$('#originArea_comp').next(".combo").hide();
	}
}
var doingInitOriginPlace = true ;
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
			//省份的数据肯定存在
			var data = $('#originProvince_comp').combobox('getData');
			if (doingInitOriginPlace){
				if(originProvinceId){
					$('#originProvince_comp').combobox('select', originProvinceId);
				}else{
					$('#originProvince_comp').combobox('select', data[0].areaID);
				}
			}else{
				$('#originProvince_comp').combobox('select', data[0].areaID);
			}
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
				if (doingInitOriginPlace){
					if(originCityId){
						$('#originCity_comp').combobox('select', originCityId);
					}else{
						$('#originCity_comp').combobox('select', data[0].areaID);
					}
				}else{
					$('#originCity_comp').combobox('select', data[0].areaID);
				}
			}else {//港澳台
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
			if (doingInitOriginPlace){
				doingInitOriginPlace = false;
				if(originAreaId){
					$('#originArea_comp').combobox('select', originAreaId);
				}else{
					$('#originArea_comp').combobox('select', data[0].areaID);
				}
			}else{
				$('#originArea_comp').combobox('select', data[0].areaID);
			}
		}
	});
}

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
          $(this).dialog('destroy');
      },
      onOpen : function() {
      }
  });
});

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











