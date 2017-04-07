<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../../pub/head.inc" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<title>系统用户管理</title>
</head>
<style>
	.cars{font-size: 16px;}
	.cars-tit{ color: #FF0000; font-weight: bold; border-bottom: solid 1px #929292; line-height: 40px;;}
	.tit-line{ display: inline-block; margin-right: 20px;}
	.cars-meg{ color: #333333; line-height: 30px; font-weight: bold;}
	.cars-data p{ width: 200px; display: inline-block;}
	.bott-line{ bottom:solid 5px #ddd;}
	.bott-line{ border-bottom: solid 8px #EEEEEE; margin: 0px 0 10px 0;}
</style>
<script type="text/javascript" src="${CONTEXT }js/datagrid-detailview.js"></script>
<script>
//查询
function query(){
	var boardName=$('#boardName').val();
	var reportsName=$('#reportsName').val();
	var boardClass=$('#boardClass').val();
	var reportsClass=$('#reportsClass').val();
	var roleID=$('#roleID').val();
	var url= CONTEXT + 'sysmgr/sysRole/toAssignRight?roleID=' + roleID+"&boardName="+boardName+"&reportsName="+reportsName+"&boardClass="+boardClass+"&reportsClass="+reportsClass;
	window.location.href=url;
}

//重置
function cleardata(){
	$('#boardName').val('');
	$("#reportsName").val('');
	$("#boardClass").val("");
	$("#reportsClass").val("");
}


function save(roleID,attribute){
	if( 2==attribute|| 3==attribute){
		$("#assignForm").submit();
		slideMessage("操作成功！");
	}else{
		warningMessage("该角色类型无法分配前台权限,如有需求，请先修改角色类型");
		return;
	}
}

//分配前台权限
function addAction(id,attribute){
	var url= CONTEXT + 'sysmgr/sysRole/toAssignRight?roleID=' + id;
	window.location.href=url;
}
//分配后台权限
function assignRight(id,attribute) {
	var url=CONTEXT + 'sysmgr/sysRole/assignMenuList?roleID=' + id;
	window.location.href=url;
}
function back(){
	window.location.href=CONTEXT + 'sysmgr/sysRole/list';
}

function getDataClass(){
	var boardClass='${boardClass}';
	var reportsClass='${reportsClass}';
	$.ajax({
		type: "GET",
		url: CONTEXT+"sysmgr/sysMenu/query2",
		dataType: "json",
		success: function(data) {
			var menus=data.rows;
			if (menus.length != 0) {
				$('.boardClass,.reportsClass').empty();
				$('.boardClass').append("<option value=''>看板数据分类</option>");
				$('.reportsClass').append("<option value=''>报表数据分类</option>");
				for ( var n = 0; n < menus.length; n++) {
					if(menus[n].menuID==boardClass){
						$('.boardClass').append("<option selected='selected' value='"+menus[n].menuID+"'>"+menus[n].menuName+"</option>");
					}else{
						$('.boardClass').append("<option value='"+menus[n].menuID+"'>"+menus[n].menuName+"</option>");
					}
					if(menus[n].menuID==reportsClass){
						$('.reportsClass').append("<option selected='selected' value='"+menus[n].menuID+"'>"+menus[n].menuName+"</option>");
					}else{
						$('.reportsClass').append("<option value='"+menus[n].menuID+"'>"+menus[n].menuName+"</option>");
					}
				}
			}
		}
	});
}

</script>
<body style="font-size: 15px;">
	<div id="tb" style="padding:5px;height:auto">
		<div>
			<a class="easyui-linkbutton" iconCls="icon-back" onclick="back()">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
			角色名称：${dto.roleName }&nbsp;&nbsp;&nbsp;&nbsp;
			角色类型：<c:if test="${dto.attribute ==1 }">后台角色</c:if>
			<c:if test="${dto.attribute ==2 }">前台角色</c:if>
			<c:if test="${dto.attribute ==3 }">前后台角色</c:if>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" iconCls="icon-add" onclick="addAction('${dto.roleID }','${dto.attribute}')">添加前台角色权限</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" iconCls="icon-add" onclick="assignRight('${dto.roleID }','${dto.attribute}')">添加后台角色权限</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" iconCls="icon-group-link" onclick="save('${dto.roleID }','${dto.attribute}')">确定分配</a>
        </div>
        <div style="margin-top: 5px;">
	       	 看板数据名称: 
	       	<input class="easyui-textbox" type="text" id="boardName" name="boardName" style="width:150px" data-options="prompt:'请输入看板数据名称',iconCls:'icon-search'" value="${boardName }">
	        <select class="boardClass" id="boardClass" name="boardClass" editable="false" panelHeight="auto" style="width:100px">
				<option value="">看板数据分类</option>
			</select>
	       	报表数据名称: 
	       	<input class="easyui-textbox" type="text" id="reportsName" name="reportsName" style="width:150px" data-options="prompt:'请输入报表数据名称'" value="${reportsName }">
	        <select class="reportsClass" id="reportsClass" name="reportsClass" editable="false" panelHeight="auto" style="width:100px">
				<option value="">报表数据分类</option>
			</select>
	        <a class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick="cleardata()">重置</a>
        </div>
	</div>
	<form id="assignForm" action="${CONTEXT }sysmgr/sysRole/assignH5Right" >
	<input id="roleID" name="roleID" value="${roleID }" type="hidden"/>
	<div id="dashboard">
		<c:set var="borderCount" value="0"></c:set>
		<c:set var="reportCount" value="0"></c:set>
		<!--  -->
		<c:if test="${!empty menuList }"><p><input type="checkbox" id="btn-all-all" class="btn-all" />全选</p></c:if>
		<c:forEach items="${menuList }" var="menu" varStatus="i">
			<div class="cars">
			<p class="cars-tit"><span class="tit-line">|</span>${menu.menuName }</p>
			<!-- 看板数据 -->
			<!-- MENU0001表示综合看板编号 -->
			<c:if test="${boardQuery}">
				<p class="cars-meg">看板数据</p>
				<div class="cars-data">
				<!-- 有看板数据 -->
				<c:if test="${!empty menu.boardList  }">
					<c:forEach  items="${menu.boardList }" var="board">
						<p><input type="checkbox" name="boardList[${borderCount}].idStr" class="btn" value="${board.id }_${menu.menuID}" <c:forEach items="${roleBoards}" var="roleBoard"><c:if test="${board.id eq roleBoard.boardId }">checked="checked"</c:if></c:forEach>/>${board.name }</p>
						<c:set var="borderCount" value="${borderCount+1 }"></c:set>
					</c:forEach>
					<p><input type="checkbox" class="btn-all" value="" />全选</p>
				</c:if>
				<c:if test="${empty menu.boardList }">
					暂无数据
				</c:if>
				</div>
				<p class="bott-line"></p>
			</c:if>
			<!-- 报表数据 -->
			<c:if test="${reportsQuery}">
				<p class="cars-meg">报表数据</p>
				<div class="cars-data">
				<!-- 有报表数据 -->
				<c:if test="${!empty menu.reportList }">
					<c:forEach  items="${menu.reportList }" var="report" varStatus="j">
						<p><input type="checkbox" name="reportList[${reportCount}].idStr" class="btn" value="${report.id }_${menu.menuID}" <c:forEach items="${roleReoorts}" var="roleRport"><c:if test="${report.id eq roleRport.reportsId }">checked="checked"</c:if></c:forEach> />${report.name }</p>
						<c:set var="reportCount" value="${reportCount+1 }"></c:set>
					</c:forEach>
					<p><input type="checkbox" class="btn-all" value="" />全选</p>
				</c:if>
				<c:if test="${empty menu.reportList }">
					暂无数据
				</c:if>
				</div>
			</c:if>
		</c:forEach>
	</div>
	</form>
</body>
<script type="text/javascript">
$(document).ready(function(){
// 	loadData();
	$(".btn-all").click(function(){
		$(this).parents(".cars-data").find(".btn").attr("checked",this.checked);
		if(!this.checked){
			$("#btn-all-all").attr("checked",false);
		}
	});		
	$("#btn-all-all").click(function(){
		$(".btn-all,.btn").attr("checked",this.checked);
	});
	$(".btn").click(function(){
		if(!this.checked){
			$(this).parents(".cars-data").find(".btn-all").attr("checked",false);
			$("#btn-all-all").attr("checked",false);
		}else{
			var btns=$(this).parents(".cars-data").find(".btn");
			for(var i=0;i<btns.length;i++){
				if(!btns[i].checked){
					return;
				}
			}
			$(this).parents(".cars-data").find(".btn-all").attr("checked",true);
		}
	});	
});
getDataClass();
</script>
</html>