<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<link rel="stylesheet" href="${CONTEXT}css/admin-order.css"/>
			<title>车辆皮重载重赋值管理</title>
		
	</head>
<body>
	<div id="layout_add" style="background-color: white;">
		<h2><gd:btn btncode='BTNCLPZZZFZGL01'><a href="javascript:addShow();">添加：(默认隐藏)</a></gd:btn></h2>
		<table id="tb_add" style="display:none;">
			<tr>
				<th colspan="5" align="left"><input type="radio" value="1" name="status" id="_rad_y" checked="checked" />启用 &nbsp;&nbsp;<input type="radio" value="0" name="status" id="_rad_n" />关闭</th>
			</tr>
			<tr>
				<th>车辆类型</th>
				<td><input maxlength="30" id="type" type="text" value="" name="type" /></td>
				<th>皮重<input maxlength="5" type="text" id="tareWeigh" value=""/></th>
				<td colspan="2"><input type="button" value="添加" onclick="add()" /></td>
			</tr>
			<tr>
				<td>
				</td>
				<th>
					明显少量(0%)总重
				</th>
				<td>
					<input maxlength="5" type="text" id="zeroperWeigh" value=""/>
				</td>
				<th>
					低于半车(30%)总重
				</th>
				<td>
					<input maxlength="5" type="text" id="thirtyperWeigh" value=""/>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<th>
					大概半年(50%)总重
				</th>
				<td>
					<input maxlength="5" type="text" id="halfperWeigh" value=""/>
				</td>
				<th>
					大概满车(100%)总重
				</th>
				<td>
					<input maxlength="5" type="text" id="allWeigh" value=""/>
				</td>
			</tr>
			<tr>
				<td colspan="5"><hr/></td>
			</tr>
		</table>
	</div>
	
	<div style=" overflow: auto; width: auto; height: 400px;">
	<h3>车辆皮重载重赋值管理：</h3>
	<hr/>
	<c:if test="${not empty cars}">
		<table>
			<tbody>
				<tr>
					<td>
						<c:forEach items="${cars }" var="car">
						<div id="layout_show_${car.cwpid }" style="background-color: white;">
							<table>
								<tr>
									<td></td>
									<th colspan="4" align="left"><input type="radio" value="1" name="status_${car.cwpid }" id="${car.cwpid }_rad_y" ${car.status==1?"checked='checked'":"" } />启用 &nbsp;&nbsp;<input type="radio" value="0" name="status_${car.cwpid }" id="${car.cwpid }_rad_n" ${car.status==0?"checked='checked'":"" } />关闭</th>
								</tr>
								<tr>
									<th rowspan="3">${car.type }</th>
									<th>皮重</th>
									<td><input maxlength="5" type="text" id="${car.cwpid }_tareWeigh" value="${car.tareWeigh }"/></td>
									<td colspan="2"><gd:btn btncode='BTNCLPZZZFZGL02'><input type="button" value="修改" onclick="update(${car.cwpid})" /></gd:btn></td>
								</tr>
								<tr>
									<th>
										明显少量(0%)总重
									</th>
									<td>
										<input maxlength="5" type="text" id="${car.cwpid }_zeroperWeigh" value="${car.zeroperWeigh }"/>
									</td>
									<th>
										低于半车(30%)总重
									</th>
									<td>
										<input maxlength="5" type="text" id="${car.cwpid }_thirtyperWeigh" value="${car.thirtyperWeigh }"/>
									</td>
								</tr>
								<tr>
									<th>
										大概半年(50%)总重
									</th>
									<td>
										<input maxlength="5" type="text" id="${car.cwpid }_halfperWeigh" value="${car.halfperWeigh }"/>
									</td>
									<th>
										大概满车(100%)总重
									</th>
									<td>
										<input maxlength="5" type="text" id="${car.cwpid }_allWeigh" value="${car.allWeigh }"/>
									</td>
								</tr>
								<tr>
									<td colspan="5"><hr/></td>
								</tr>
							</table>
						</div>
						</c:forEach>
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	</div>
	</div>
	<script type="text/javascript">
		function addShow() {
			if($("#tb_add").css("display")=="none")
				$("#tb_add").show();
			else
				$("#tb_add").hide();
		}
	
		$("input").focus(function(v){
		  $(v.target).css("background-color","#FFFFCC");
		});
		
		$("input").blur(function(v){
			  $(v.target).css("background-color","");
			});
	
		function update(id) {
			var win = $.messager.progress({
				title:'请等待',
				msg:'正在添加...'
				});
			
			var result = 0;
			var msg = "";
			var erV;
			
			$("#layout_show_"+id+" input[type='text']").each(function(s,v) {
				v = $(v);
				if(v.val()==null||$.trim(v.val())=="") {
					msg = "请填写完整数据";
					result = 1;
					erV = v;
					return;
				}
				var reg = /^[+-]?\d+(\.\d{0,2})?$/;
				if(v.attr("id")!="type"&&!reg.test(v.val())) {
					msg = "必须为数字,且小数位最多两位";
					result = 1;
					erV = v;
					return;
				}
			});
			
			if(result==1) {
				$.messager.progress('close');
				$.messager.alert("操作提示", msg,"error",function(){erV.focus();});
				return;
			}
			
			/*
				校验重
			*/
			if(parseFloat($("#"+id+"_tareWeigh").val())>parseFloat($("#"+id+"_zeroperWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '皮重不能大于任何总重',"error",function(){erV.focus();});
				return;
			}
			if(parseFloat($("#"+id+"_zeroperWeigh").val())>parseFloat($("#"+id+"_thirtyperWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '总重值不合理，请确认后输入',"error",function(){erV.focus();});
				return;
			}
			if(parseFloat($("#"+id+"_thirtyperWeigh").val())>parseFloat($("#"+id+"_halfperWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '总重值不合理，请确认后输入',"error",function(){erV.focus();});
				return;
			}
			if(parseFloat($("#"+id+"_halfperWeigh").val())>parseFloat($("#"+id+"_allWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '总重值不合理，请确认后输入',"error",function(){erV.focus();});
				return;
			}
			
			var params = {
					cwpid:id,
					status:$("input[name="+"status_"+id+"]:checked").val(),
					tareWeigh:$("#"+id+"_tareWeigh").val(),
					zeroperWeigh:$("#"+id+"_zeroperWeigh").val(),
					thirtyperWeigh:$("#"+id+"_thirtyperWeigh").val(),
					halfperWeigh:$("#"+id+"_halfperWeigh").val(),
					allWeigh:$("#"+id+"_allWeigh").val()
			};
			
			$.ajax({
				type: 'POST',
				url: '${CONTEXT }outer/update',
			    cache: 'false' ,
			    data:params,
			    dataType: 'json' ,
			    success: function(data) {
			    	slideMessage(data.msg);
			    	$.messager.progress('close');
			    } ,
			    error: function(err) {
			    	alert('系统维护中。。。');
			    }
			});
		}
		
		function add() {
			var win = $.messager.progress({
				title:'请等待',
				msg:'正在添加...'
				});
			
			var result = 0;
			var msg = "";
			
			$("#layout_add input[type='text']").each(function(s,v) {
				v = $(v);
				if(v.val()==null||$.trim(v.val())=="") {
					msg = "请填写完整数据";
					result = 1;
					erV = v;
					return;
				}
				var reg = /^[+-]?\d+(\.\d{0,2})?$/;
				if(v.attr("id")!="type"&&!reg.test(v.val())) {
					msg = "必须为数字,且小数位最多两位";
					result = 1;
					erV = v;
					return;
				}
			});
			
			if(result==1) {
				$.messager.progress('close');
				$.messager.alert("操作提示", msg,"error",function(){erV.focus();});
				return;
			}
			
			/*
				校验重
			*/
			if(parseFloat($("#tareWeigh").val())>parseFloat($("#zeroperWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '皮重不能大于任何总重',"error",function(){erV.focus();});
				return;
			}
			if(parseFloat($("#zeroperWeigh").val())>parseFloat($("#thirtyperWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '总重值不合理，请确认后输入',"error",function(){erV.focus();});
				return;
			}
			if(parseFloat($("#thirtyperWeigh").val())>parseFloat($("#halfperWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '总重值不合理，请确认后输入',"error",function(){erV.focus();});
				return;
			}
			if(parseFloat($("#halfperWeigh").val())>parseFloat($("#allWeigh").val())) {
				$.messager.progress('close');
				$.messager.alert("操作提示", '总重值不合理，请确认后输入',"error",function(){erV.focus();});
				return;
			}
			
			var params = {
					type:$("#type").val(),
					status:$("input[name=status]:checked").val(),
					tareWeigh:$("#tareWeigh").val(),
					zeroperWeigh:$("#zeroperWeigh").val(),
					thirtyperWeigh:$("#thirtyperWeigh").val(),
					halfperWeigh:$("#halfperWeigh").val(),
					allWeigh:$("#allWeigh").val()
			};
			
			$.ajax({
				type: 'POST',
				url: '${CONTEXT }outer/add',
			    cache: 'false' ,
			    data:params,
			    dataType: 'json' ,
			    success: function(data) {
			    	//slideMessage(data.msg);
			    	if(data.msg=="-1") {
			    		$.messager.alert("操作提示", "添加失败!","error");
			    	}
			    	$.messager.progress('close');
			    	window.location.reload();
			    } ,
			    error: function(err) {
			    	alert('系统维护中。。。');
			    }
			});
		}
	
	</script>
</body>
</html>