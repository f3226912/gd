<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<%@ include file="../pub/head.inc" %>
<title>商品分类管理</title>
<script type="text/javascript" src="${CONTEXT }js/jquery.min.js"></script>
<script type="text/javascript" src="${CONTEXT }js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${CONTEXT }js/jquery.artDialog.js?skin=blue" charset='utf-8'></script>
<script type="text/javascript" src="${CONTEXT }js/iframeTools.js"   ></script>
<link rel="stylesheet" href="${CONTEXT }css/zTree/demo.css" type="text/css">
<link rel="stylesheet" href="${CONTEXT }css/zTree/zTreeStyle.css" type="text/css">
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
 	.ztree li span.button.ref {margin-left:2px; margin-right: -1px; vertical-align:top; *vertical-align:middle;
 	background-image: url("${CONTEXT}images/icons/ref.jpg");
 	}
 	.ztree li span.button.refBase {margin-left:2px; margin-right: -1px; vertical-align:top; *vertical-align:middle;
 	background-image: url("${CONTEXT}images/icons/base-ref.png");
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
			    //showRenameBtn: showRenameBtn,
				drag: {
					autoExpandTrigger: true,
					prev: dropPrev,
					inner: dropInner,
					next: dropNext
				},
				enable: true,
				editNameSelectAll: true,
				showRenameBtn: true
			}, 
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick,
				beforeRemove: beforeRemove,
				//beforeRename: beforeRename,
				beforeEditName: beforeEditName,
				beforeDrop: beforeDrop,
				beforeDrag: beforeDrag,
			    onRename: onRename
			}
		};

		var zNodes = ${tree};
		//zNodes.unshift({"id":"-1","pid":null,"name":"商品分类","open":"true"});
		
		function addHoverDom(treeId, treeNode) {
			if(treeNode.level==4)
				return;
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='增加分类' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(event){
				if (window.event) {  
				 	window.event.cancelBubble=true;
				} else {  
					event.stopPropagation();  
				}
				
				var addCategory = art.dialog.open(CONTEXT + '/category/showEdit/-1?marketId=${marketDTO.id}', {
					id : "addCategory",
				    lock : true,
				    title : '添加分类',
				    width : '600px',
				    height : '400px',
				    ok: function () {
				    	var doc = $(this.iframe.contentWindow.document);
				    	
				    	var cateName = $.trim(doc.find("#cateName").val());
				    	var orderNum = $.trim(doc.find("#orderNum").val());
				    	var marketId = $.trim(doc.find("#marketId").val());
				    	var typeIcon = doc.find("#typeIcon").val();
				    	var webTypeIcon = doc.find("#webTypeIcon").val();
						if(cateName==""){
							alert("分类名称不能为空且不能全为空格！");
							return false;
						}
						
						var parentNode = treeNode.getParentNode();
						if(cateName != treeNode.name && tree.getNodesByParam("name", cateName, parentNode).length>=1){
							alert("重命名分类错误!在产品分类“"+parentNode.name+"”下已存在名为“"+cateName+"”的分类,同级分类不能重名！");
							return false;
						}
						
						//统计分类名称不能重复
						if(tree.getNodesByParam("name", cateName, treeNode).length>=1){
							alert("在产品分类“"+treeNode.name+"”下已存在名为“"+cateName+"”的分类,同级分类不能重名！");
							return false;
						}
						
						var test1 = /^\d+$/;
						if (orderNum != '') {
							if (orderNum.search(test1) == -1) {
								alert("顺序请输入正整数!");
								return false;
							}
						}
						//发送请求修改分类
				    	$.ajax({url: CONTEXT + "/category/edit?t="+new Date(),
							data:{
									"cateName":cateName,
									"orderNum":orderNum,
									"marketId":marketId,
									"parentId":treeNode.id,
									"curLevel": treeNode.level,
									"typeIcon" : typeIcon,
					    			"webTypeIcon" : webTypeIcon
								},			    			
							type: 'POST',
							timeout: 10000, 
							error: function(){
								alert("error");
							},
							success: function(result){
								//alert(result);
								var zTree = $.fn.zTree.getZTreeObj("treeDemo");
								zTree.addNodes(treeNode, {id:result, pId:treeNode.id, name:cateName});
							
							}
				    	});
						return true;
				    },
				    cancelVal: '关闭',
				    cancel:true
				
				});

				return false;
			});

			if(treeNode.level==1 && ${marketDTO.id}!=3 && ${marketDTO.id}!=0) {
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#refBtn_"+treeNode.tId).length>0) return;
				var addStr = "<span class='button ref' id='refBtn_" + treeNode.tId
					+ "' title='关联供应商分类' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn = $("#refBtn_"+treeNode.tId);
				if (btn) btn.bind("click", function(event){
					if (window.event) {  
					 	window.event.cancelBubble=true;
					} else {  
						event.stopPropagation();  
					}
					
					var refCategory = art.dialog.open(CONTEXT + '/category/refList?id='+treeNode.id+'&baseMarketId='+${marketDTO.id}, {
						id : "refCategory",
					    lock : true,
					    title : '关联供应商分类',
					    width : '300px',
					    height : '400px',
					    ok: function () {
					    	var doc = $(this.iframe.contentWindow.document);
					    	var docWindow = $(this.iframe.contentWindow);
					    	var refCategoryIds = "";
					    	var marketId = $.trim(doc.find("#marketId").val());
					    	
					    	$(docWindow[0].tree.getCheckedNodes()).each(function(v,r) {
					    		if(r.id!=-1) {
						    		refCategoryIds += r.id + ",";
					    		}
					    		
					    	});
					    	
							//发送请求修改分类关联
					    	$.ajax({url: CONTEXT + "category/ref",
								data:{
										"marketId":${marketDTO.id},
										"npsCateid":treeNode.id,
										"refCategoryIds":refCategoryIds,
										"type":"1"
									},			    			
								type: 'POST',
								timeout: 100000, 
								error: function(){
									alert("error");
								},
								success: function(result){
									//alert(result);
									artDialog.tips("关联供应商分类成功",2);
								}
					    	});
							return true;
					    },
					    cancelVal: '关闭',
					    cancel:true
					
					});
				return false;
				});
			}

			if(treeNode.level==1 && ${marketDTO.id}!=3 && ${marketDTO.id}!=0) {
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#refBaseBtn_"+treeNode.tId).length>0) return;
				var addStr = "<span class='button refBase' id='refBaseBtn_" + treeNode.tId
					+ "' title='关联基础分类' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn = $("#refBaseBtn_"+treeNode.tId);
				if (btn) btn.bind("click", function(event){
					if (window.event) {  
					 	window.event.cancelBubble=true;
					} else {  
						event.stopPropagation();  
					}
					
					var refCategory = art.dialog.open(CONTEXT + '/category/refBaseListlevel0?id='+treeNode.id+'&baseMarketId='+${marketDTO.id}, {
						id : "refCategory",
					    lock : true,
					    title : '关联基础分类',
					    width : '300px',
					    height : '400px',
					    ok: function () {
					    	var doc = $(this.iframe.contentWindow.document);
					    	var docWindow = $(this.iframe.contentWindow);
					    	var refCategoryIds = "";
					    	var marketId = $.trim(doc.find("#marketId").val());
					    	
					    	$(docWindow[0].tree.getCheckedNodes()).each(function(v,r) {
					    		if(r.id!=-1) {
						    		refCategoryIds += r.id + ",";
					    		}
					    		
					    	});
					    	
							//发送请求修改分类关联
					    	$.ajax({url: CONTEXT + "category/ref",
								data:{
										"marketId":${marketDTO.id},
										"npsCateid":treeNode.id,
										"refCategoryIds":refCategoryIds,
										"type":"2"
									},			    			
								type: 'POST',
								timeout: 100000, 
								error: function(){
									alert("error");
								},
								success: function(result){
									//alert(result);
									artDialog.tips("关联基础分类成功",2);
								}
					    	});
							return true;
					    },
					    cancelVal: '关闭',
					    cancel:true
					
					});
				return false;
				});
			}
			
			if(treeNode.level==3 && ${marketDTO.id}!=3 && ${marketDTO.id}!=0) {
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#refBaseBtn_"+treeNode.tId).length>0) return;
				var addStr = "<span class='button refBase' id='refBaseBtn_" + treeNode.tId
					+ "' title='关联基础分类' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn = $("#refBaseBtn_"+treeNode.tId);
				if (btn) btn.bind("click", function(event){
					if (window.event) {  
					 	window.event.cancelBubble=true;
					} else {  
						event.stopPropagation();  
					}
					
					var refCategory = art.dialog.open(CONTEXT + '/category/refBaseList?id='+treeNode.id+'&baseMarketId='+${marketDTO.id}, {
						id : "refBaseCategory",
					    lock : true,
					    title : '关联基础分类',
					    width : '300px',
					    height : '400px',
					    ok: function () {
					    	var doc = $(this.iframe.contentWindow.document);
					    	var docWindow = $(this.iframe.contentWindow);
					    	var refCategoryIds = "";
					    	var marketId = $.trim(doc.find("#marketId").val());
					    	
					    	$(docWindow[0].tree.getCheckedNodes()).each(function(v,r) {
					    		if(r.id!=-1) {
						    		refCategoryIds += r.id + ",";
					    		}
					    		
					    	});
					    	
							//发送请求修改分类关联
					    	$.ajax({url: CONTEXT + "category/ref",
								data:{
										"marketId":${marketDTO.id},
										"npsCateid":treeNode.id,
										"refCategoryIds":refCategoryIds,
										"type":"2"
									},
								type: 'POST',
								timeout: 100000, 
								error: function(){
									alert("error");
								},
								success: function(result){
									//alert(result);
									artDialog.tips("关联基础分类成功",2);
								}
					    	});
							return true;
					    },
					    cancelVal: '关闭',
					    cancel:true
					
					});
				return false;
				});
			}
		}
		
		
		function showRemoveBtn(treeId, treeNode) {
			return !treeNode.isParent;
		}
		
		function showRenameBtn(treeId, treeNode) {
			return !(treeNode.id==0);
		}
		
		function beforeRemove(treeId, treeNode) {
			var ret = false; 
			if(confirm("确认删除 分类 -- " + treeNode.name + " 吗？")){
				$.ajax({url: CONTEXT + "/category/delete?t="+new Date(),
					data:{
							"categoryId": treeNode.id,
							"curLevel": treeNode.level
						},			    			
					type: 'POST',
					async: false,//同步
					timeout: 10000, 
					error: function(){
						alert("删除出现异常!");
						ret = false;
					},
					success: function(result){
						if(result=="true") {
							ret = true;
						artDialog.tips("已删除分类",2);
						}else {
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
			var marketId = $.trim($("#marketId").val());
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
				alert("重命名分类错误!在产品分类“"+parentNode.name+"”下已存在名为“"+newName+"”的分类,同级分类不能重名！");
				isCancelTemp=true;
				forCancelRenameTemp = treeNode.name;
				return true;
			}
			var ret;
			$.ajax({url: CONTEXT + "/category/edit?t="+new Date(),
				data:{
						"categoryId": treeNode.id,
						"cateName": newName,
						"marketId":marketId,
						"parentId":parentNode.id
						
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
						artDialog.tips("保存成功",2);
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
			$.ajax({url: CONTEXT + "/category/save?t="+new Date(),
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
					if(result=="true") {
						ret = true;
						artDialog.tips("保存成功",2);
					}
					else {
						ret = false;
					}
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
		
		function displayOrderNum(categoryId) {
			$.ajax({
				type : "POST",
				url : CONTEXT + "/category/queryOrderNum?categoryId="+ categoryId,
				cache : false,
				dataType : "json",
				success : function(data) {
						if (data != null) {
							$("#orderNum").val(data);
						} else {
						}
				}
			});
		}
		
		
		//修改操作
		function beforeEditName(treeId, treeNode) {
			var nodeName =$("#"+treeNode.tId+"_span").html();
			displayOrderNum(treeNode.id);
			if(treeNode.level==0)
				return;
			var editOpen = art.dialog.open(CONTEXT + '/category/showEdit/' + treeNode.id, {
				id : "editOpen",
			    lock : true,
			    title : '修改分类',
			    width : '600px',
			    height : '400px',
			    ok: function () {
			    	var doc = $(this.iframe.contentWindow.document);
			    	
			    	var cateName = $.trim(doc.find("#cateName").val());
			    	var orderNum = $.trim(doc.find("#orderNum").val());
			    	var marketId = $.trim(doc.find("#marketId").val());
			    	var typeIcon = doc.find("#typeIcon").val();
			    	var webTypeIcon = doc.find("#webTypeIcon").val();
					if(cateName==""){
						alert("分类名称不能为空且不能全为空格！");
						return false;
					}
					
					var parentNode = treeNode.getParentNode();
					if(cateName != treeNode.name && tree.getNodesByParam("name", cateName, parentNode).length>=1){
						alert("重命名分类错误!在产品分类“"+parentNode.name+"”下已存在名为“"+cateName+"”的分类,同级分类不能重名！");
						return false;
					}
					
					var test1 = /^\d+$/;
					if (orderNum != '') {
						if (orderNum.search(test1) == -1) {
							alert("顺序请输入正整数!");
							return false;
						}
					}
					//发送请求修改分类
			    	$.ajax({url: CONTEXT + "/category/edit?t="+new Date(),
						data:{
							    "categoryId": treeNode.id,
								"cateName":cateName,
								"orderNum":orderNum,
								"marketId":marketId,
								"parentId":parentNode.id,
								"typeIcon" : typeIcon,
				    			"webTypeIcon" : webTypeIcon
							},			    			
						type: 'POST',
						timeout: 10000, 
						error: function(){
							alert("error");
						},
						success: function(result){
							if(result =="true"){
								artDialog.tips("保存成功",2);
								ret = true;
								$("#"+treeNode.tId+"_span").html(cateName);
							}
							else {
								alert("保存失败!");
								ret = false;
							}
						}
			    	});
					return true;
			    },
			    cancelVal: '关闭',
			    cancel:true
			
			});
			
			return false;
		};
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
			$("#refBtn_"+treeNode.tId).unbind().remove();
			$("#refBaseBtn_"+treeNode.tId).unbind().remove();
			$("#"+treeNode.tId+"_edit").unbind().remove();
		};
		
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			node = zTree.getSelectedNodes()[0];
			$("#products").attr("src","");
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
	<div class="munebg" width="100%"><span color="">${marketDTO.marketName }</span>-商品分类管理</div>
	<input type="hidden" id="marketId" value="${marketDTO.id }" />
	<table width="100%" height="90%">
		<tr height="100%">
			<td width="20%" style="vertical-align: top;">
				<ul id="treeDemo" class="ztree" style="margin-top:0; background-color:white;border:1px solid #71A2CA;height:100%;"></ul>
			</td>
			<td width="80%" style="vertical-align: top;">
				<iframe align="top" id="products" width="100%" height="500px;" style="border:0"></iframe>
			</td>
		</tr>
	</table>
</body>
</html>