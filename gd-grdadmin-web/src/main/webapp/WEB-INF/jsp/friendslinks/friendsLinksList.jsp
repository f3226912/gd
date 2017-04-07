<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>product</title>
		<link href="${CONTEXT}css/uploader.css" media="screen" rel="stylesheet" type="text/css" />
	</head>
<body>
	<table id="friendslinksdg" title="">
	</table>
	<div id="friendslinkstb" style="padding:5px;height:auto">
		<form id="friendsLinksSearchForm" method="post" action="">
		<div>
			链接栏目 : <select name="linkCate" id="linkCate"  >
							<option value="">---请选择---</option>
							<option value="1" >友情链接</option>
							<option value="2" >合作媒体</option>
					  </select>&nbsp;&nbsp;
			链接类型 : <select name="linkType" id="linkType"  >
							<option value="">---请选择---</option>
							<option value="1" >文字</option>
							<option value="2" >图片</option>
<!-- 							<option value="3" >文字加图片</option> -->
					  </select>&nbsp;&nbsp;
			链接标题: <input  type="text" id="linkName" name="linkName" style="width:150px" data-options="prompt:'请输入链接文字">&nbsp;&nbsp;
			<br>
			链接URL: <input  type="text" id="linkUrl" name="linkUrl" style="width:150px" data-options="prompt:'请输入链接URL">&nbsp;&nbsp;
			状态 : <select name="status" id="status"  >
							<option value="">---请选择---</option>
							<option value="0" >停用</option>
							<option value="1" >启用</option>
<!-- 							<option value="2" >待审核</option> -->
<!-- 							<option value="3" >审核不通过</option> -->
					  </select>&nbsp;&nbsp;		
			<gd:btn btncode='BTNSPGLQBCP08'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="serachList">查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNSPGLQBCP01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="addPage">新增</a></gd:btn>
			<gd:btn btncode='BTNGGGLGOGGL02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>	
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-add-btn" onclick="add();">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="add-exit-btn" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
		<div id="editDialog" class="easyui-dialog" style="width:600px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-edit-btn" onclick="edit();">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="edit-exit-btn" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
		<div id="viewDialog" class="easyui-dialog" style="width:600px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsView">
			<div id="dlg-buttonsView" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-view-btn" onclick="view();">确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="view-exit-btn" onclick="javascript:$('#viewDialog').dialog('close')">关闭</a>
	        </div>
		</div>	
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	//var options = "<gd:btn btncode='BTNSPGLQBCP04'><a class='operate' href='javascript:;' onclick=viewObj('"+row.id+"')>查看</a></gd:btn>";
	var options = "&nbsp;<gd:btn btncode='BTNSPGLQBCP03'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"','"+row.status+"')>修改</a></gd:btn>";   
	options += "&nbsp;<gd:btn btncode='BTNSPGLQBCP02'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
	return options;
}
</script>
<script type="text/javascript" src="${CONTEXT}js/jquery.cookie.js"></script>
<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>  
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 

<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8" src="${CONTEXT}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>
<script type="text/javascript" src="${CONTEXT}js/formValidatorRegex.js"></script>
<script type="text/javascript" src="${CONTEXT}js/friendslinks/friendslinks.js"></script>
<script type="text/javascript">


$("#serachList").click(function(){
	 	var strRegex =regexEnum.url;
	  	var reg=new RegExp(strRegex);        
		var url = $("#linkUrl").val().trim();
		if(url!=""&&!reg.test(url)){
			warningMessage("请输入正确的URL链接");
			return false;
		}
	var params = {
			"linkCate":$("#linkCate").val(),
			"linkType" : $("#linkType").val(),
			"linkName" : $("#linkName").val(),
			"linkUrl" : $("#linkUrl").val(),
			"status" : $("#status").val()
		};
	loadFriendsLinks(params);
});

$('#btn-reset').click(function(){
	$('#friendsLinksSearchForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#friendsLinksSearchForm')[0].reset();
	$("#friendslinksdg").datagrid('load', {});
});


function checkSubmit(){
	if($("#linkCate_01").val()==0){
		warningMessage("必须选择链接栏目");
		return false;
	}
	if($("#linkWord").val().trim()==""){
				warningMessage("链接标题不能为空");
			return false;
		}

	if($("#linkCate_01").val()==2){
		var dataurl;
		var img =$("[data-original-url]").each(function(){
			dataurl=$(this).attr("data-original-url");
		});
		if(dataurl==undefined||dataurl==""){
			warningMessage("请上传图片！");
			return false;
		}
	}
	if($("#linkUrl_01").val().trim()==""){
		warningMessage("链接URL不能为空");
		return false;
	}else{
		 var strRegex =regexEnum.url;
	  	var reg=new RegExp(strRegex);        

		var url =$("#linkUrl_01").val().trim();
		if(!reg.test(url)){
			warningMessage("请输入正确的URL链接");
			return false;
		}
	}
}
function add() {
	if(checkSubmit()==false){
		return false;
	}
	var url=CONTEXT+"friendslinks/add";
 	jQuery.post(url, $('#addForm').serialize(), function (data) {
 		if (data == "success") {
			slideMessage("操作成功！");
			
			//刷新父页面列表
			$("#friendslinksdg").datagrid('reload');
			$('#addDialog').dialog('close');
		} else {
			warningMessage(data.message);
			$('#addDialog').dialog('close');
			return;
		}
	}); 
}

$("#addPage").click(function(){
	$('#addDialog').dialog({'title':'新增友情链接','href':CONTEXT+'friendslinks/addPage','width': 600,'height': 500}).dialog('open');
	
// 	  $('<div></div>').dialog({
//           id : 'addDialog',
//           title : '新增友情链接',
//           width : 600,
//           height : 500,
//           closed : false,
//           cache : false,
//           href : CONTEXT+'friendslinks/addPage',
//           modal : true,
//           onLoad : function() {
//           },
//           onClose : function() {
//               $(this).dialog('destroy');
//           },
//           buttons : [ {
//               text : '新增',
//               iconCls : 'icon-save',
//               handler : function() {
//                   //提交表单
//                   add();
//               }
//           }, {
//               text : '关闭',
//               iconCls : 'icon-cancel',
//               handler : function() {
//                   $("#addDialog").dialog('destroy');
//               }
//           } ],
//       });
 });



//编辑
function editObj(id,status){
	$('#editDialog').dialog({'title':'修改友情链接','href':CONTEXT+'friendslinks/editbyid/'+id,'width': 600,'height': 500}).dialog('open');
// 	 $('<div></div>').dialog({
// 	        id : 'editDialog',
// 	        title : '编辑友情链接',
// 	        width : 600,
// 	        height : 500,
// 	        closed : false,
// 	        cache : false,
// 	        href : CONTEXT+'friendslinks/editbyid/'+id,
// 	        modal : true,
// 	        onLoad : function() {
// 	        },
// 	        onClose : function() {
// 	            $(this).dialog('destroy');
// 	        },
// 	        buttons : [ {
// 	            text : '保存',
// 	            iconCls : 'icon-save',
// 	            handler : function() {
// 	                //提交表单
// 	                edit();
// 	            }
// 	        }, {
// 	            text : '关闭',
// 	            iconCls : 'icon-cancel',
// 	            handler : function() {
// 	                $("#editDialog").dialog('destroy');
// 	            }
// 	        } ],
// 	    });
}

function viewObj(id){
	$('#viewDialog').dialog({'title':'查看友情链接','href':CONTEXT+'friendslinks/viewbyid/'+id,'width': 600,'height': 500}).dialog('open');
}

function delObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
	if (r){
	 $.ajax( {  
		    url:CONTEXT+'friendslinks/delete',// 跳转到 action  
		    data:{  
		       id : id
		    },  
		    type:'post',  
		    dataType:'json',  
		    success:function(data) {  
		        if(data != "0" ){  
		        	slideMessage("删除成功！");  
		            window.location.reload();  
		        }else{  
		        	warningMessage("删除失败！"); 
		        }  
		     },  
		     error : function() {  
		     }  
		});
	}
	else
	{
		return;
	}
	});
}

//删除多个操作
$("#icon-remove").click(function(){
	var row = $('#friendslinksdg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要删除的数据！");
        return ;
    } else if ($(row).length == 1){
    	var id = $(row)[0].id ;
    	delObj(id);
    } else {
    	var deleteStr = getSelections("id");
    	delsObj(deleteStr);
    }
});

function delsObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		jQuery.post(CONTEXT+"friendslinks/deletes",{"id":id},function(data){
				if (data == "success"){
					slideMessage("操作成功！");
					$('#friendslinksdg').datagrid('reload');
					$('#friendslinksdg').datagrid("uncheckAll");
				}else{
					warningMessage(data);
					return;
				}
    		});
		}else{
			return;
		}
	});
}

</script>
</html>

