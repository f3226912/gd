<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<html>
	<head> 
		<title>活动基本信息</title>
	</head>
	<body>
	<form id="addForm" method="post" action="${CONTEXT}active/addPromBaseinfo">
		<input type="hidden" name="type" value="1"/><!-- 1 产销 -->
		<div style="">
			<div style="padding:2px;">
				<label>活动名称：</label>
				<input type="text" name="name" style="width:400px" class="easyui-validatebox" data-options="required:true,validType:'length[0,50]'">
			</div>
			<div style="padding:2px;">
				<label>所属市场：</label>
				<input type="text" id="marketId" style="width:400px"  name="marketList[0].marketId" data-options="required:true">
			</div>
			<div style="padding:2px;">
				<label>活动时间：</label>
				<input type="text" name="startTime" class="easyui-validatebox" id="startDate2" readonly data-options="required:true" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){startDate2.focus();},maxDate:'#F{$dp.$D(\'endDate2\',{s:-1})}'})" 
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){startDate2.focus();},maxDate:'#F{$dp.$D(\'endDate2\',{s:-1})}'})"/> 至 
				<input type="text" name="endTime" class="easyui-validatebox" id="endDate2" readonly data-options="required:true" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endDate2.focus();},minDate:'#F{$dp.$D(\'startDate2\',{s:1})}'})" 
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endDate2.focus();},minDate:'#F{$dp.$D(\'startDate2\',{s:1})}'})" >
			</div>
			<div tyle="padding:2px;">
				<label>活动详情URL：</label>
				<input type="text" id="activeUrl" name="url" class="easyui-validatebox" data-options="required:true,validType:'urlCheck'"/>
			</div>
			<div style="padding:2px;">
				<div style="float:left"><label>活动介绍：</label></div>
				<textarea name="introduction" id="introduction" style="border:1px sold #d3d3d3;width:600px;height:60px;" class="easyui-validatebox" data-options="required:true,validType:'length[0,200]'">
				</textarea>
				<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="initAddPicture()" id="icon-upload-btn">新增图片</a>
			</div>
			<div  class="user-iteam" id="user-iteam" style="border:1px solid #d3d3d3;overflow:auto;white-space:nowrap;">
				
			</div>
		</div>
	</form>
	<div id="dlg-buttonsUpload" style="text-align:center;margin-top:10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="bctrl.add()" id="icon-upload-btn">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" data-options="disabled:false" onclick="javascript:closeWindow(true)">关闭</a>
	</div>
	<script type="text/javascript">
	</script>

	
<script type="text/javascript" >

var appUrl = CONTEXT+'product/uploadProductPic';

	$("#marketId").combobox({
		url:'${CONTEXT}market/getAllMarket',
		valueField:'id',
		textField:'marketName' 
	});

/**单图上传**/
function initUploadSignle(uploadContainerBtn,uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr){
	var config_mutiple = {
	        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
	        uploadBtn : uploadBtn,//初始的button
	        queueId : queueId,//图片装载容器id
	        JUrls : JUrls,//成功后返回url参数写入id
	        action : uploadUrl ,//上传地址
	        maxSize:2048,
	        imgUrlPrefix : '${imgHostUrl}',
	        max : 1,//允许上传个数
	        type :"ajax"//上传类型
	        //filesStr : filesStr,//此处是附加参数，已经上传需要第二次修的图片参数
	};

	gdKissySingleUploader.init(config_mutiple ,function(uploader){
			
        	/**可以修改时注册删除事件**/
        	$("#"+queueId).closest(".form-context").delegate(".imageUploader_del","click",function(){
        		$("#"+uploadContainerBtn).show();
        	});
	},function(url){
		//上传成功后，隐藏 
		$("#"+uploadContainerBtn).hide();
		
	});
}
</script>
<script type="text/javascript" src="${CONTEXT}js/active/activeBaseinfo.js?version=${version}"></script>
</body>
	
</html>