<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>member</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
		<div>
			创建时间：
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">
			<select name="level" id="level"  >
				<!--  （1谷登农批，2农速通，3农商友，其余待补） -->
							<option value="">--用户类型--</option>
							<option value="1" >谷登农批</option>
							<option value="2" >农速通</option>
							<option value="3" >农商友</option>
							<option value="4" >产地供应商</option>
							<option value="5" >农批友</option>
			</select>
			<select name="regetype" id="regetype"  >
				<!--  0管理后台，1谷登农批网，2农速通，3农商友，4农商友-农批商，5 农批友，6供应商,7POS刷卡，8微信授权  -->
							<option value="">--注册来源--</option>
							<option value="0" >管理后台</option>
							<option value="1" >谷登农批网</option>
							<!-- <option value="2" >农速通</option> -->
							<option value="3" >农商友</option>
							<option value="4" >农商友-农批商</option>
							<option value="5" >农批友  </option>
							<option value="6" >供应商  </option>
							<option value="7" >POS刷卡</option>
							<option value="8" >微信授权</option>
							<option value="9" >农速通-货主</option>
							<option value="10" >农速通-司机</option>
							<option value="11" >农速通-物流公司</option>
							
			</select>
				用户账号:
			<input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" >
			手机号：<input  type="text" id="mobile"    class="easyui-validatebox"    name="mobile" style="width:150px" >
			<br/>
			账号状态:

				<select name="status"  id="status" >
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_certifi 表得到）
							 -->
<!-- 							<option value="0">请选择</option>
 -->
  							<option value="">全部</option>
 							<option value="1" <c:if test="${dto.status==1 }">selected</c:if>>启用</option>
							<option value="0" <c:if test="${dto.status==0 }">selected</c:if>>禁用</option>
						</select>
				会员等级:

				<select name="memberGrade"  id="memberGrade" >
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_certifi 表得到）
							 -->
<!-- 							<option value="0">请选择</option>
 -->
  							<option value="">全部</option>
 							<option value="0">普通会员</option>
							<option value="1">金牌供应商</option>
						</select>
				商铺所属市场：
				<select name="marketId" id="marketId">
					<option value="">全部</option>
					<c:forEach items="${marketList }" var="market">
					<option value="${market.id }">${market.marketName }</option>
					</c:forEach>
				</select>
		</div>
		</form>

		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNHYGLHYLB05'>
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#memberSearchForm').form('validate');">查询</a>
			</gd:btn>&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<gd:btn btncode='BTNHYGLHYLB01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
<!-- 			<xlc:btn btncode='XZ002'><a class="easyui-linkbutton icon-edit-btn" iconCls="icon-edit" id="icon-edit" >编辑</a></xlc:btn>
 -->		<gd:btn btncode='BTNHYGLHYLB02'><a class="easyui-linkbutton icon-ok-btn" iconCls="icon-ok" id="icon-ok" >启用</a></gd:btn>
			<gd:btn btncode='BTNHYGLHYLB03'><a class="easyui-linkbutton icon-no-btn" iconCls="icon-no" id="icon-no" >禁用</a></gd:btn>
<!-- 		删除按钮不能随便使用，很多关联表，用户不能删除
			<xlc:btn btncode='XZ005'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></xlc:btn>
 -->
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
 			<gd:btn btncode='BTNHYGLHYLB07'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>

 			<gd:btn btncode='BTNHYGLHYLB09'><a class="easyui-linkbutton icon-ok-btn" iconCls="icon-ok" id="icon-sub-ok" >补贴显示</a></gd:btn>
			<gd:btn btncode='BTNHYGLHYLB10'><a class="easyui-linkbutton icon-no-btn" iconCls="icon-no" id="icon-sub-no" >补贴隐藏</a></gd:btn>

		</div>

		<!-- <div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div> -->
		<div id="editDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>

		<div id="showDialog" class="easyui-dialog"  closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#showDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="marketDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsMarket">
			<div id="#dlg-buttonsMarket" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#marketDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function optFormat(value,row,index){
	return "<gd:btn btncode='BTNHYGLHYLB06'><a class='operate' href='javascript:;' onclick=showObj('"+row.memberId+"');>查看</a></gd:btn>" +
		   "<gd:btn btncode='BTNHYGLHYLB04'><a class='operate' href='javascript:;' onclick=editObj('"+row.memberId+"');>编辑</a></gd:btn>" +
//		   "<a class='operate' href='javascript:;' onclick=delObj('"+row.memberId+"');>删除</a>" +
							"";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/member/main.js"></script>
<script type="text/javascript">

	var disableExport = false ;

	$("#exportData").click(function(){
/* 		 var queryParams = $('#memberdg').datagrid('options').queryParams;
			queryParams.account = $('#memberSearchForm #account').val();
			queryParams.mobile = $('#memberSearchForm #mobile').val();
			queryParams.level = $('#memberSearchForm #level').val();
			queryParams.isAuth = $('#memberSearchForm #isAuth').val();
			queryParams.startDate =  $('#memberSearchForm #startDate').val();
			queryParams.endDate =  $('#memberSearchForm #endDate').val();
			queryParams.marketId = $("#memberSearchForm #marketId").val();
			queryParams.regetype = $("#memberSearchForm #regetype").val(); */

			var params = {
				"account" : $('#memberSearchForm #account').val(),
				"mobile" : $('#memberSearchForm #mobile').val(),
				"level" : $('#memberSearchForm #level').val(),
				"memberGrade" : $('#memberSearchForm #memberGrade').val(),
				"marketId" : $('#memberSearchForm #marketId').val(),
				"startDate" : $('#memberSearchForm #startDate').val(),
				"endDate" : $('#memberSearchForm #endDate').val(),
				"regetype" : $('#memberSearchForm #regetype').val()
			};
			var paramList = "account=" + params.account + "&mobile="+params.mobile+"&level="+params.level+"&memberGrade="+params.memberGrade+
				"&marketId="+params.marketId+"&startDate="+params.startDate+"&endDate="+params.endDate+"&regetype="+params.regetype;

			$.ajax({
				url: CONTEXT+'member/checkExportParams',
				data : params,
				type:'post',
				success : function(data){
					//检测通过
					if (data && data.status == 1){
						if (!disableExport){
							slideMessage("数据正在导出中, 请耐心等待...");
							disableExport = true ;
							//启动下载
							$.download(CONTEXT+'member/exportData',paramList,'post' );
						}else{
							slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
						}
					}else{
						warningMessage(data.message);
					}
				},
				error : function(data){
					warningMessage(data);
				}
			});
/* 			window.location.href=CONTEXT+'member/exportData?account='+queryParams.account+
							"&mobile="+queryParams.mobile+"&level="+queryParams.level+"&isAuth="+queryParams.isAuth+
							"&marketId="+queryParams.marketId+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate+"&regetype="+queryParams.regetype; */
	});
	jQuery.download = function(url, data, method){
		// 获得url和data
	    if( url && data ){
	        // data 是 string或者 array/object
	        data = typeof data == 'string' ? data : jQuery.param(data);
	        // 把参数组装成 form的  input
	        var inputs = '';
	        jQuery.each(data.split('&'), function(){
	            var pair = this.split('=');
	            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
	        });
	        // request发送请求
	        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
	        	.appendTo('body').submit().remove();
	    };
	};
</script>
</html>

