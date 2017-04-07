<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/tags.inc" %>
<%@ include file="../pub/constants.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>地区管理</title>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="../js/jquery.artDialog.js?skin=blue" charset='utf-8'></script>
<script type="text/javascript" src="../js/iframeTools.js"   ></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=l1ruOrQIf8GerQRjW2RscDSA"></script>
<link rel="stylesheet" href="../css/zTree/demo.css" type="text/css">
<link rel="stylesheet" href="../css/zTree/zTreeStyle.css" type="text/css">
<style type="text/css">
	.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
	.ztree li ul.level0 {padding:0; background:none;}
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	.ztree li span{
		float:left;
		width:60px;
		display:block;
	 	white-space:nowrap;
	 	text-overflow:ellipsis; 
	 	overflow: hidden;
 	}
</style>
<script type="text/javascript">
	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			dblClickExpand: true,
			selectedMulti: false
		},
		edit: {
			showRemoveBtn: showRemoveBtn,
			showRenameBtn: showRenameBtn,
			drag: {
				autoExpandTrigger: true,
				prev: dropPrev,
				inner: dropInner,
				next: dropNext
			},
			enable: true,
			editNameSelectAll: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick,
			beforeRemove: beforeRemove,
			beforeRename: beforeRename,
			beforeDrop: beforeDrop,
			beforeDrag: beforeDrag,
			onRename: onRename
		}
	};
	var zNodes = ${tree};
	function addHoverDom(treeId, treeNode) {
		if(treeNode.level == 4){
			return;
		}
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length > 0){
			return;
		}
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + 
			"' title='增加地区' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) {
			btn.bind("click", function(event){
				if (window.event) {  
				 	window.event.cancelBubble=true;
				} else {  
					event.stopPropagation();  
				}
				art.dialog({
					id : 'addCategory',
					lock : true,
				    content: '地区名称:&nbsp;&nbsp;<input maxlength="10" id="area"></input><br/>地区编号:&nbsp;&nbsp;<input maxlength="10" id="areaID"></input>'+
				    	'<br/>地区类型:&nbsp;&nbsp;<select id="areaType">'+
				    	'<option value="1">内地</option>'+
				    	'<option value="2">港澳台</option>'+
				    	'<option value="3">海外</option>'
				    	+'</select>',
				    init: function(){$("#area").select();},
				    ok: function () {
				    	var area = $.trim($("#area").val());
						if(area==""){
							alert("地区不能为空且不能全为空格！");
							return false;
						}
						var areaID = $.trim($("#areaID").val());
						if(areaID==""){
							alert("地区不能为空且不能全为空格！");
							return false;
						}
						var areaType = $.trim($("#areaType").val());
						//统计分类名称不能重复
						if(tree.getNodesByParam("name", area, treeNode).length >= 1){
							alert("在地区“"+treeNode.name+"”下已存在名为“"+area+"”的地区,同级地区不能重名！");
							return false;
						}						
						//发送请求添加分类
				    	$.ajax({url: "edit?t=" + new Date(),
							data:{
									"area":area,
									"areaID":areaID,
									"father":treeNode.id,
									"areaType":areaType
								},			    			
							type: 'POST',
							error: function(){
								alert("error");
							},
							success: function(result){
								//alert(result);
								var zTree = $.fn.zTree.getZTreeObj("treeDemo");
								zTree.addNodes(treeNode, {id:result, pId:treeNode.id, name:area});
							}
			    		});
			    		return true;
			    	},
				    cancelVal: '关闭',
				    cancel: true
				});
				return false;
			});
		}
		if (treeNode.editNameFlag || $("#chLoc_"+treeNode.tId).length > 0 || treeNode.level == 0) {
			return;
		}
		var chLoc = "<span class='button ico_open' style='background-image: url(\"${CONTEXT}css/zTree/img/zTreeStandard_dw.png\");' id='chLoc_" + treeNode.tId
			+ "' title='设置定位' onfocus='this.blur();'></span>";
		sObj.after(chLoc);
		var chLoc = $("#chLoc_"+treeNode.tId);
		if (chLoc) chLoc.bind("click", function(event){
			if (window.event) {  
			 	window.event.cancelBubble=true;
			} else {  
				event.stopPropagation();  
			}
			var locChange = art.dialog.open('${CONTEXT }area/loc?areaId='+treeNode.id,{
				id : 'locCategory',
				title:'定位',
				lock : true,
			    init: function(){$("#area").select();},
			    ok: function () {
			    	//获得对话框内document
			    	var doc = $(this.iframe.contentWindow.document);
			    	//获取修改数据
			    	var lat = doc.find("#lat").val();
			    	var lng = doc.find("#lng").val();
			    	var areaID = doc.find("#areaID").val();
					//发送搜搜
			    	$.ajax({url: "${CONTEXT}/area/changeLoc?t="+new Date(),
						data:{
								"areaID":areaID,
								"lat":lat,
								"lng":lng
							},
						type: 'POST',
						error: function(e){
							jQuery.dialog({
							    content: "操作失败，请联系系统管理员！",
							    time:3,
							    id:"dropInner"
							});
						},
						success: function(result){
							result = eval("("+result+")");
							if(result.code=="1") {
								jQuery.dialog({
								    content: "定位成功",
								    time:2,
								    id:"dropInner"
								});
								locChange.close();
							} else {
								jQuery.dialog({
								    content: "操作失败，请联系系统管理员！",
								    time:3,
								    id:"dropInner"
								});
							}
						}
			    	});
			    	return false;
			    },
			    button: [{
			    	name: "回定位点",
					callback: function(e) {
						this.iframe.contentWindow.backPoint();
						return false;
					}	
					}],
			    width:'570px',
			    height:'500px',
			    cancelVal: '关闭',
			    cancel:true
				});
			return false;
		});
	}
		
	function showRemoveBtn(treeId, treeNode) {
		return !treeNode.isParent;
	}
		
	function showRenameBtn(treeId, treeNode) {
		return !(treeNode.id==0);
	}
		
	function beforeRemove(treeId, treeNode) {
		var ret = false; 
		if(confirm("确认删除 地区 -- " + treeNode.name + " 吗？")){
			$.ajax({url: "delete?t="+new Date(),
				data:{
						"areaID": treeNode.id
					},			    			
				type: 'POST',
				async: false,//同步
				timeout: 1000, 
				error: function(){
					alert("删除出现异常!");
					ret = false;
				},
				success: function(result){
					if(result=="true")
						ret = true;
					else {
						alert("还关联了产品，不能删除！");
						ret = false;
					}
				}
	    	});
		}
		return ret;
	}
	
	//一下变量和方法是因为ztree处理重命名比较坑爹，详情见ztree api
	var forCancelRenameTemp;
	var isCancelTemp;
		
	function beforeRename(treeId, treeNode, newName, isCancel) {
		newName = $.trim(newName);
		forCancelRenameTemp = false;
		if($.trim(newName)==""){
			alert("重命名失败！名称不能为空或者全为空格！");
			isCancelTemp=true;
			forCancelRenameTemp = treeNode.name;
			return true;
		}
		var parentNode = treeNode.getParentNode();
		if(newName==treeNode.name){
			isCancelTemp=true;
			forCancelRenameTemp = treeNode.name;
			return true;
		}
		if(newName!=treeNode.name && tree.getNodesByParam("name", newName, parentNode).length>=1){
			alert("重命名地区错误!在地区“"+parentNode.name+"”下已存在名为“"+newName+"”的地区,同级地区不能重名！");
			isCancelTemp=true;
			forCancelRenameTemp = treeNode.name;
			return true;
		}				
		var ret;
		console.log(treeNode);
		$.ajax({url: "edit2?t="+new Date(),
			data:{
					"areaID": treeNode.id,
					"area": newName
					
				},			    			
			type: 'POST',
			async: false,//同步
			timeout: 1000, 
			error: function(){
				alert("保存失败!");
				ret = false;
			},
			success: function(result){
				if(result=="true"){
					ret = true;
				}
				else {
					alert("保存失败!");
					ret = false;						
				}
			}
    	});
		if(!ret){
			isCancelTemp = true;
			forCancelRenameTemp = treeNode.name;				
		}else{
			isCancelTemp = false;
		}
		return true;
	}
		
	function onRename(event, treeId, treeNode, isCancel) {
		if(isCancelTemp)
			treeNode.name = forCancelRenameTemp;
	}
		
	function dropPrev(treeId, nodes, targetNode) {
		return false;
	}
		
	function dropInner(treeId, nodes, targetNode) {
		return false;
		var level = targetNode.level;
		var l = nodes[0].level;
		if(level>=4){
			jQuery.dialog({
			    content: "移动失败！节点"+nodes[0].name+"不能移入‘"+targetNode.name+"’节点中，因为分类的最深层级为4级，而‘"+targetNode.name+"’已经是第"+level+"级节点,"+
			    		"如果节点‘"+nodes[0].name+"’移入，则会成为第"+(level+1)+"级节点!",
			    time:3,
			    id:"dropInner"
			});
			return false;
		}
		if(nodes[0].isParent){
			var arr = nodes[0].children;
			for(var n=0;n<arr.length;n++){
				var temp = arr[n].level;
				if((temp - l + level) >=4){
					jQuery.dialog({
					    content: "移动失败！节点"+arr[n].name+"不能移入‘"+targetNode.name+"’节点中，因为分类的最深层级为4级，而‘"+targetNode.name+"’已经是第"+level+"级节点,"+
					    		"如果节点‘"+arr[n].name+"’移入，则会成为第"+(temp - l + level+1)+"级节点!",
					    time:3,
					    id:"dropInner"
					});
					return false;	
				}
			}
		}
		var ret = false;
		$.ajax({url: "save?t="+new Date(),
			data:{
					"id": nodes[0].id,
					"parentId" : targetNode.id
				},			    			
			type: 'POST',
			async: false,//同步
			timeout: 1000, 
			error: function(){
				alert("error");
				ret = false;
			},
			success: function(result){
				if(result=="true")
					ret = true;
				else 
					ret = false;
			}
    	});
		return ret;
	}
		
	function dropNext(treeId, nodes, targetNode) {
		return false;
	}
		
	function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
		return false;
		if(!targetNode)
			return false;
		return true;
	}
	
	function beforeDrag(treeId, treeNodes) {
		return false;
		if(treeNodes[0].id==0)
			return false;
		return true;
	}
	
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
		$("#chLoc_"+treeNode.tId).unbind().remove();
	};
	
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		node = zTree.getSelectedNodes()[0];
		$("#areas").attr("src","");
		//${CONTEXT}/area/showArea
		document.getElementById("areas").src="${CONTEXT}/area/showArea?areaId="+node.id;
		
		
		
	}
	
	var tree;
	$(document).ready(function(){
		tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
</script>
<style type="text/css">
	ul.zTree{
		height:150px;
	}
</style>
</head>
<body>
<br>
	<div class="munebg" width="100%">地区管理</div>
	<table width="100%" height="90%">
		<tr height="100%">
			<td width="20%" style="vertical-align: top;">
				<ul id="treeDemo" class="ztree" style="margin-top:0; background-color:white;border:1px solid #71A2CA;height:100%;"></ul>
			</td>
			<td width="80%" style="vertical-align: top;">
				<iframe align="top" id="areas" width="100%" height="400" style="border:0"></iframe>
			</td>
		</tr>
	</table>
</body>
</html>