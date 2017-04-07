<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
td{
    padding-left: 12px;
}
</style>
<form id="addForm" method="post" action="carLine/save">
 <input type="hidden" id="carId" name="carId" value="${carId}" readonly>
	<div>
		<table style="width: 100%; height: 200px;">
		    <tr>
				<td><input type="hidden" id="id" name="id"
					value="${id}"  style="width: 150px"></td>
			</tr>
			<tr>
				<td style="width: 100px;"><span style="color:red;">*</span>车牌号码:</td>
				<td>
		        <input type="button" id="queryCarList" value="${carNumber}" >
				</td>
			</tr>
	  
   <%-- 	      <tr>                                                             
             <td class="left"><span style="color:red;">*</span>始发地省:</td>   
             <td><input name="start_provinceId"  id="start_provinceId" value="${start_provinceId}"  style="width: 174px;" required="true" class="easyui-validatebox"></td>         
          </tr> 
          <tr>
             <td class="left"><span style="color:red;">*</span>始发地市:</td>                    
             <td><input   name="start_cityId" id="start_cityId"  value="${start_cityId}" style="width: 174px;"  class="easyui-validatebox"></td>                                             
          </tr> 
          <tr>
             <td class="left"><span style="color:red;">&nbsp;</span>始发地县区:</td>   
             <td><input name="start_areaId"  id="start_areaId" value="${start_areaId}" style="width: 174px;"  class="easyui-validatebox"></td>
          </tr>
           
             <tr>                                                             
             <td class="left"><span style="color:red;">*</span>目的地省:</td>   
             <td><input name="end_provinceId"  id="end_provinceId" value="${end_provinceId}" style="width: 174px;" required="true" class="easyui-validatebox"></td>         
             </tr> 
             <tr>
             <td class="left"><span style="color:red;">*</span>目的地市:</td>                    
             <td><input   name="end_cityId" id="end_cityId" value="${end_cityId}" style="width: 174px;"  class="easyui-validatebox"></td>                                             
             </tr> 
             <tr>
             <td class="left"><span style="color:red;">&nbsp;</span>目的地县区:</td>   
             <td><input name="end_areaId"  id="end_areaId"  value="${end_areaId}" style="width: 174px;"  class="easyui-validatebox"></td>
             </tr> --%>
             
	            <tr>                                                             
             <td class="left"><span style="color:red;">*</span>始发地1:</td>   
             <td>省:<input name="s_provinceId"  id="s_provinceId"  value="${start_provinceId}"  style="width: 100px;" required="true" class="easyui-validatebox">
             市:<input   name="s_cityId" id="s_cityId"  value="${start_cityId}" style="width: 100px;" >
             县区:<input name="s_areaId"  id="s_areaId"  value="${start_areaId}" style="width: 100px;" >
             </td>
          </tr> 
          
          
        <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>始发地2:</td>   
             <td>省:<input name="s_provinceId2"  id="s_provinceId2"  value="${start_provinceId2}" style="width: 100px;"  class="easyui-validatebox">
             市:<input   name="s_cityId2" id="s_cityId2"   value="${start_cityId2}" style="width: 100px;" >
             县区:<input name="s_areaId2"  id="s_areaId2"  value="${start_areaId2}" style="width: 100px;" >
             </td>
          </tr> 
          
           <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>始发地3:</td>   
             <td>省:<input name="s_provinceId3"  id="s_provinceId3"  value="${start_provinceId3}" style="width: 100px;"  class="easyui-validatebox">
             市:<input   name="s_cityId3" id="s_cityId3"  value="${start_cityId3}" style="width: 100px;" >
             县区:<input name="s_areaId3"  id="s_areaId3"  value="${start_areaId3}" style="width: 100px;" >
             </td>
          </tr> 
          
	         <tr>                                                             
             <td class="left"><span style="color:red;">*</span>目的地1:</td>   
             <td>
                              省:<input name="e_provinceId"  id="e_provinceId"  value="${end_provinceId}" style="width: 100px;" required="true" class="easyui-validatebox">         
                              市:<input name="e_cityId" id="e_cityId" value="${end_cityId}" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId"  id="e_areaId" value="${end_areaId}" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	         <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地2:</td>   
             <td>
                              省:<input name="e_provinceId2"  id="e_provinceId2" value="${end_provinceId2}" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId2" id="e_cityId2" value="${end_cityId2}" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId2"  id="e_areaId2" value="${end_areaId2}" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
             
             <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地3:</td>   
             <td>
                              省:<input name="e_provinceId3"  id="e_provinceId3" value="${end_provinceId3}" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId3" id="e_cityId3" value="${end_cityId3}" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId3"  id="e_areaId3" value="${end_areaId3}" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	        <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地4:</td>   
             <td>
                              省:<input name="e_provinceId4"  id="e_provinceId4"  value="${end_provinceId4}" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId4" id="e_cityId4"  value="${end_cityId4}" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId4"  id="e_areaId4" value="${end_areaId4}" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	         <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地5:</td>   
             <td>
                              省:<input name="e_provinceId5"  id="e_provinceId5" value="${end_provinceId5}" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId5" id="e_cityId5" value="${end_cityId5}" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId5"  id="e_areaId5" value="${end_areaId5}" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	      <tr>
		     <td style="width: 100px;"><span style="color:red;">*</span>发车时间:</td>
			 <td>
		  <input  type="text"  id="sendDate"   name="sendDate" value="${sendDate}"   readOnly="true"   class="easyui-validatebox" required="true"
					onfocus="WdatePicker({minDate:'%y-%M-{%d+0}',maxDate:'#F{$dp.$D(\'endDate\')}'})"
					style="width:150px" >&nbsp;&nbsp;
		  <select id=sentDateType name="sentDateType" class="text_sketch" >
		  <option  <c:if test="${sentDateType==4}">selected="selected" </c:if> value="4">不限</option>
          <option  <c:if test="${sentDateType==0}">selected="selected" </c:if> value="0">上午</option>
          <option  <c:if test="${sentDateType==1}">selected="selected" </c:if> value="1">中午</option>
          <option  <c:if test="${sentDateType==2}">selected="selected" </c:if> value="2">下午</option>
          <option  <c:if test="${sentDateType==3}">selected="selected" </c:if> value="3">晚上</option>
         </select>
	        </td>
		  </tr>
		
		 <tr>
             <td class="left">&nbsp;在途时间:</td>   
             <td><input name="onLineHours"  id="onLineHours"  maxlength="6" <c:if test="${onLineHours >0}"> value ="${onLineHours}" </c:if>  class="easyui-validatebox" style="width: 150px" >&nbsp;&nbsp;&nbsp;天 &nbsp;&nbsp;
             </td>
         </tr>	
         
          <tr>
             <td class="left"><span style="color:red;">&nbsp;</span>截止时间:</td>   
             <td>
             <input  type="text"  id="endDate" name="endDate" value="${endDate}" readOnly="true"  class="easyui-validatebox"
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'sendDate\')}'})"
					style="width:150px" >
             </td>
          </tr>	
         
         <tr>
		 <td style="width: 100px;">&nbsp;发运方式:</td>
		 <td>
		 <select id="sendGoodsType" name="sendGoodsType" class="text_sketch"  onchange="typeChange()">
          <option <c:if test="${sendGoodsType==0}"> selected="selected" </c:if> value="0">零担</option>
          <option  <c:if test="${sendGoodsType==1}"> selected="selected" </c:if> value="1">整车</option>
          <option  <c:if test="${sendGoodsType==2}"> selected="selected" </c:if>  value="2" >不限</option>
        </select>
	    </td>
	    </tr>
			
		 <tr>
		     <td style="width: 100px;">&nbsp;价格:</td>
	         <td><input type="text" id="price" name="price" maxlength="7"
					value="${price}"  style="width: 150px">&nbsp;&nbsp;
         <div id="div1" style="margin-right: 305px;float: right;">	
		  <select id="unitType" name="unitType" class="text_sketch" >
          <option <c:if test="${unitType==0}"> selected="selected"  </c:if> value="0">元/吨</option>
          <option <c:if test="${unitType==1}"> selected="selected"  </c:if> value="1">元/公斤</option>
          <option <c:if test="${unitType==2}"> selected="selected"  </c:if>  value="2">元/立方</option>
         </select>
           </div>
           <div id="div2" style="display: none;margin-right: 360px;float: right;">	
           <span>元</span>
           </div>
         
          </td>
		 </tr>
		
		<tr>
				<td style="width: 100px;">&nbsp;备注:</td>
				<td><textarea id="remark" name="remark"   maxlength="50"
					class="easyui-validatebox" 
					style="width: 150px">${remark}</textarea></td>
		</tr>
			
		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/carLine/add.js"></script>
<script type="text/javascript" src="${CONTEXT}js/carLine/area.js"></script>
<script type="text/javascript" src="${CONTEXT}js/carLine/car.js"></script>
<script type="text/javascript">

var sendGoodsType = $("#sendGoodsType").val();
$(document).ready(function(){
	var sendGoodsType = $("#sendGoodsType").val();
	if(sendGoodsType=='0')
	{
		$('#price').removeAttr("disabled");  
	    $("#unitType").removeAttr("disabled");  
		$("#div1").show();
		$("#div2").hide();
	}
	else if(sendGoodsType=='1')
	{
		$("#price").removeAttr("disabled");
		$('#unitType').attr('disabled', 'true');
		$("#div1").hide();
		$("#div2").show();
	}
	else 
	{
		$("#div1").hide();
		$("#div2").show();
		$('#price').attr('disabled', 'true');
		$('#unitType').attr('disabled', 'true');
	}
	
});

</script>














