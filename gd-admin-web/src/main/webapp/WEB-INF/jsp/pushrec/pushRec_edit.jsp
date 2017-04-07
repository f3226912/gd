<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>
<style type="text/css">

	.mleft{
		font-size:16px;
		text-align: right;
		valign:middle;
		width:150px;
	}
	
	.mright{
		font-size:16px;
	    align:left;
	    valign:middle;
	}
</style>

<form id="addForm" method="post" action="pushrec/save">
 					<div>
 			
			<table>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>消息描述:
					</td>
					<td class="mright">
						<input  type="text" id="title"  name="title"   value="${dto.title}" required="true" maxlength="20" class="easyui-validatebox"  style="width:150px" missingMessage="必须填写">
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>内容:
					</td>
					<td class="mright">
					<textarea name="content" style="width: 400px;height:200px">${dto.content}</textarea>
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>发送给:
					</td>
					<td class="mright">
 					 <input type="radio" name="type"    value="2" onchange="changeM(this.value);"  > 所有人（广播）	
 					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					  <input type="radio" name="type"   value="1" onchange="changeM(this.value);"  > 单播
 					</td>
				</tr>
				<tr id="member" style="display:none">
					<td class="mleft"  >
					<span style="color: red;">*</span>发送人员：
					</td>
					<td class="mright">
 						<input type="button" id="showMemberWin" value="选择人员">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" id="memberId" name="memberId"    value=""/>

 					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>发送站点:
					</td>
					<td class="mright">
 					 <input type="radio" name="receiveType"    value="2" onchange="changeT(this.value);" >农商友APP
 							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 					 <input type="radio" name="receiveType"    value="3" onchange="changeT(this.value);" >农速通APP
 					</td>
				</tr>
				<tr id="url1">
					<td class="mleft"  >
						跳转地址:
					</td>
					<td class="mright">
					<select name="url1" id="selectUrl1">
						<option value="NSY_TJ">农商友APP-推荐页面</option>
<!-- 						<option value="NSY_GZ">农商友APP_关注页面</option>
 -->					</select>
 					</td>
				</tr>
				<tr id="url2" style="display:none">
					<td class="mleft"  >
						跳转地址:
					</td>
					<td class="mright">
					<select name="url2" id="selectUrl2">
						<option value="NST_WYZC">农速通APP_我要找车页面</option>
						<option value="NST_WYZH">农速通APP_我要找货页面</option>
					</select>
 					</td>
				</tr>
				
			 
			</table>
 	</div>	
 	<script type="text/javascript" >
 		 
	$('#showMemberWin').click(function(){
		$('#memberDialog').dialog({'title':'选择会员', 'width':700, 'height':300, 'href':CONTEXT+'product/memberSelect/1'}).dialog('open');
	});
	
	
	

	function memberCallBack(index,row){
		var memberId = row.memberId;
		$("#showMemberWin").val(row.account);
		$("#memberId").val(memberId);
		$('#memberDialog').dialog('close');
	}
	
	function changeM(val){
		/* if(val==2){
			document.getElementById("member").style.display="none";
			document.getElementById("member").style.display="none";
			
			var obj=document.getElementById('selectUrl1');	
	        obj.options.remove(1);//农商友广播仅仅有推荐页面
	        
		}else{
			document.getElementById("member").style.display="";

			var obj=document.getElementById('selectUrl1');
	       	obj.options.add(new Option("农商友APP_关注页面","NSY_GZ"));

			
		} */
		if(val==2){
			document.getElementById("member").style.display="none";
			document.getElementById("member").style.display="none";
			
			var obj=$('#selectUrl1');	
	        //obj.options.remove(1);//农商友广播仅仅有推荐页面
	        obj.find("option").eq(1).remove();
	        
		}else{
			document.getElementById("member").style.display="";

			var obj=$('#selectUrl1');
	       	//obj.options.add(new Option("农商友APP_关注页面","NSY_GZ"));
	       	obj.append('<option value="NSY_GZ">农商友APP_关注页面</option>')

			
		}
	}
	function changeT(val){
		if(val==3){
			document.getElementById("url1").style.display="none";
			document.getElementById("url2").style.display="";
		}else{
			document.getElementById("url1").style.display="";
			document.getElementById("url2").style.display="none";
		}
	}
	
	</script>
</form>
