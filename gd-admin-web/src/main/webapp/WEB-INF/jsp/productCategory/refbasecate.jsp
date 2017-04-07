<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<%@ include file="../pub/head.inc" %>
<script type="text/javascript" src="${CONTEXT }js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${CONTEXT }js/jquery.artDialog.js?skin=blue" charset='utf-8'></script>
<script type="text/javascript" src="${CONTEXT }js/iframeTools.js"   ></script>
<link rel="stylesheet" href="${CONTEXT }css/zTree/demo.css" type="text/css">
<link rel="stylesheet" href="${CONTEXT }css/zTree/zTreeStyle.css" type="text/css">
<style type="text/css">
/* 	.ztree li span.button.switch.level0 {visibility:hidden; width:1px;} */
/* 	.ztree li ul.level0 {padding:0; background:none;} */
/* 	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle} */
/* 	.ztree li span{ */
/* 		float:left; */
/* 		width:60px; */
/* 		display:block; */
/* 	 	white-space:nowrap; */
/* 	 	text-overflow:ellipsis;  */
/* 	 	overflow: hidden; */
/*  	} */
</style>
<style type="text/css">
	ul.zTree{
		height:150px;
	}
</style>
</head>
<body style="height: 96%;">
<script type="text/javascript">
		
	var setting = {
			view: {
				dblClickExpand: false,
			},
			check: {
                enable: true
            },
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				
			}
		};

		var zNodes = ${tree};
		//zNodes.unshift({"id":"-1","pid":null,"name":"商品分类","open":"true"});
		
		var tree;
		
		function getTree() {
			return tree;
		}
		$(document).ready(function(){
			tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);

			<c:if test="${not empty refNodes}">
			var arr = new Array();
			//展开选中的父节点
			<c:forEach items="${refNodes}" var="refNode" varStatus="s">
				arr[${s.index}] = tree.getNodeByParam("id",'${refNode.suppCategoryId}').getParentNode();
			</c:forEach>

			arr = unique(arr);
			
			for(var i = 0; i < arr.length; i++) {
				tree.expandNode(arr[i]);
			}
			</c:if>
		});
		
		function unique(arr) {
		    var result = [], hash = {};
		    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
		        if (!hash[elem.id]) {
		            result.push(elem);
		            hash[elem.id] = true;
		        }
		    }
		    return result;
		}
</script>
	
	<div class="munebg" width="100%"><span color="">设置：${marketDTO.marketName }</span>-${pcDTO.cateName}-基础分类</div>
	<input type="hidden" id="marketId" value="${marketDTO.id }" />
	<table width="100%" height="90%">
		<tr height="100%">
			<td width="20%" style="vertical-align: top;">
				<ul id="treeDemo" class="ztree" style="margin-top:0; background-color:white;border:1px solid #71A2CA;height:100%;width: 90%;"></ul>
			</td>
<!-- 			<td width="80%" style="vertical-align: top;"> -->
<!-- 				<iframe align="top" id="products" width="100%" height="500px;" style="border:0"></iframe> -->
<!-- 			</td> -->
		</tr>
	</table>
</body>
</html>