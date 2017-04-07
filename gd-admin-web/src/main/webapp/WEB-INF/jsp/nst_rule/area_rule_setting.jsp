<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="div1">
	<label style="font-size: 16px;"><input name="rule" type="radio" id="rule1" checked="checked" value="0">直接发布货源</label>
	<label style="font-size: 16px;"><input name="rule" type="radio" id="rule2" value="1">代发布货源规则</label>
</div>
<div style="margin-top: 20px;" id="div2">
	 <table>
	    <tr>
	    	<td style="position:relative;min-width: 180px;">
	    		<div style="position:absolute;top:0;left:0;">
	    			省份：<select id="a_provinceId" name="a_provinceId" class="text_sketch"  onchange="a_provinceId()">
		 					<option selected="selected" value="">全省</option>
		 					<c:if test="${!empty provinceList}">
		 						<c:forEach items="${provinceList}" var="province">
		 							<option value="${province.areaID}">${province.area}</option>
		 						</c:forEach>
		 					</c:if>
		 				</select>
	    		</div>
	    		
	    	</td>
	    	<td style="position:relative;min-width: 250px;">
	    		<div style="position:absolute;top:0;left:0;">
	    			    所属区域(市)
	    			    <select id="a_cityId" name="a_cityId" class="text_sketch"  onchange="a_cityId()">
		 					<option selected="selected" value="">选择城市</option>
		 				</select>
	    		</div>
	    		
	    	</td>
	    	<td >
					<select id="dataSource" multiple style="min-height: 300px;min-width:250px;" onclick="dataSource()">
					       
					</select>

			</td>
	    	<td>
	    		<div><button id="add" style="min-width: 100px;" onclick="add()">添加>></button></div><br>
	    		<div><button id="addAll" style="min-width: 100px;" onclick="addAll()">全部添加>>></button></div><br>
	    		<div><button id="delete" style="min-width: 100px;" onclick="ondelete()"><<<移除</button></div><br>
	    		<div><button id="deleteAll" style="min-width: 100px;" onclick="deleteAll()"><<<全部移除</button></div><br>
	    	</td>
	    	<td> 
			    <div style="display: inline;">
		    		<select id="choosed" multiple style="min-width:250px; min-height:300px;" onclick="choosed()">	
		    		</select>
		    	</div>
   			 </td>
	    	</tr>
	    </table>
   
    </div>
</div>
<script type="text/javascript">
$(function(){
		 showCont();
		 $("input[name=rule]").click(function(){
 		 showCont();
 		});
	});
function showCont(){
	 switch($("input[name=rule]:checked").attr("id")){
	  case "rule1":
	   //alert("one");
	   $("#div2").hide();
	   break;
	  case "rule2":
	   $("#div2").show();
	   break;
	  default:
	   break;
		}
}
function add(){
 	var dataSource1 = $('#dataSource option');
     if(dataSource1.length === 0){
         return warningMessage('没有分类');
     }
     var dataSource = $('#dataSource :selected');
     if(dataSource.length === 0){
         return warningMessage('请先选择分类');
     	}
     dataSource.each(function(){
     	var op = $(this).text();
     	var ov=$(this).val();
     	$("#choosed").append("<option value='"+ov+"'>"+op+"</option>")
     	$("#dataSource option[value='"+ov+"']").remove();
     });
 };
function ondelete(){
  	var choosed1 = $('#choosed option');
      if(choosed1.length === 0){
          return warningMessage('没有分类');
      }
      var choosed = $('#choosed :selected');
      if(choosed.length === 0){
          return warningMessage('请先选择分类');
      	}
      choosed.each(function(){
      	var op = $(this).text();
      	var ov=$(this).val();
      	$("#dataSource").append("<option value='"+ov+"'>"+op+"</option>")
      	$("#choosed option[value='"+ov+"']").remove();
      });
      
  };
function addAll(){
      var dataSource = $('#dataSource option');
      if(dataSource.length === 0){
          return warningMessage('没有分类');
      }
      dataSource.each(function(){
      	var op = $(this).text();
      	var ov=$(this).val();
      	$("#choosed").append("<option value='"+ov+"'>"+op+"</option>")
      	$("#dataSource option[value='"+ov+"']").remove();
      });
      
  };
function deleteAll(){
      var choosed = $('#choosed option');
      if(choosed.length === 0){
          return warningMessage('没有分类');
      }
      choosed.each(function(){
      	var op = $(this).text();
      	var ov=$(this).val();
      	$("#dataSource").append("<option value='"+ov+"'>"+op+"</option>")
      	$("#choosed option[value='"+ov+"']").remove();
      });
      
 };
function a_provinceId(){
  	$("#choosed").html('');
  	$("#dataSource").html('');
  	$.get("getCity/"+$("#a_provinceId").val(),function(data){
  		if(data.cityList!=""){
  			var result="<option selected='selected' value=''>选择城市</option>";
  			$.each(data.cityList,function(n,value){
  				result +="<option value='"+value.areaID+"'>"+value.area+"</option>";
  			});
  			$("#a_cityId").html('');
  			$("#a_cityId").append(result);
  		};
  	},"json");
 };
function a_cityId(){
  	$("#dataSource").html('');
  	$("#choosed").html('');
  	$.get("getArea/"+$("#a_cityId").val(),function(data){
  		if(data.areaList!=""){
  			var result="";
  			$.each(data.areaList,function(n,value){
  				result +="<option value='"+value.memberId+"'>"+value.deptName+"</option>";
  			});
  			$("#dataSource").html('');
  			$("#dataSource").append(result);
  		};
  	},"json");
  };
function addSave(){
  	var a_provinceId=$('#a_provinceId option:selected').val();
  	var a_cityId=$('#a_cityId option:selected').val();
  	var rule=$("input[name=rule]:checked").val();
  	var choosed=$('#choosed option');
  	var a_memberId='';
  	var a_companyName='';
  	choosed.each(function(){
  		var op = $(this).text();
      	var ov=$(this).val();
      	a_memberId+=ov+",";
      	a_companyName+=op+",";
  	});
  	var url = CONTEXT + "nstRule/save";
  	var params={
  			rule:rule,
  			code:1,
  			provinceId:a_provinceId,
  			cityId:a_cityId,
  			memberIdString:a_memberId,
  			companyNameString:a_companyName
  	};
  	jQuery.post(url, params, function(data) {
  		if (data == "success") {
  			slideMessage("保存成功！");
  			$("#nstGoodAssignRuledg").datagrid('reload');
  			$('#addDialog').dialog('close');
  		} else {
  			warningMessage("保存失败！");
  			return;
  		}
  	}); 
  };
</script>