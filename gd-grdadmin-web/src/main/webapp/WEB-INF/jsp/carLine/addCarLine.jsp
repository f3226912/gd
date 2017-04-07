<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
td{
    padding-left: 12px;
}
</style>
<form id="addForm" method="post" action="carLine/save">
 <input type="hidden" id="carId" name="carId" value="" readonly>
	<div>
		<table style="width: 100%; height: 200px;">
			<tr>
				<td style="width: 100px;"><span style="color:red;">*</span>车牌号码:</td>
				<td>
		        <input type="button" id="queryCarList" value="选择车牌号码" >
		      <%--   <select id=carId name="carId" class="text_sketch" >
			    <c:forEach  items="${list}" var="car" >
				 <option value="${car.id}">${car.carNumber}</option>
				</c:forEach>
				</select> --%>
				</td>
			</tr>
	  
	    <!--   <tr>                                                             
             <td class="left"><span style="color:red;">*</span>始发地省:</td>   
             <td><input name="s_provinceId"  id="s_provinceId" style="width: 174px;" required="true" class="easyui-validatebox"></td>         
          </tr> 
          <tr>
             <td class="left"><span style="color:red;">*</span>始发地市:</td>                    
             <td><input   name="s_cityId" id="s_cityId" style="width: 174px;" class="easyui-validatebox"></td>                                             
          </tr> 
          <tr>
             <td class="left"><span style="color:red;">&nbsp;</span>始发地县区:</td>   
             <td><input name="s_areaId"  id="s_areaId" style="width: 174px;"  class="easyui-validatebox"></td>
          </tr> -->
          
           <tr>                                                             
             <td class="left"><span style="color:red;">*</span>始发地1:</td>   
             <td>省:<input name="s_provinceId"  id="s_provinceId" style="width: 100px;" required="true" class="easyui-validatebox">
             市:<input   name="s_cityId" id="s_cityId" style="width: 100px;" >
             县区:<input name="s_areaId"  id="s_areaId" style="width: 100px;" >
             </td>
          </tr> 
          
          
        <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>始发地2:</td>   
             <td>省:<input name="s_provinceId2"  id="s_provinceId2" style="width: 100px;"  class="easyui-validatebox">
             市:<input   name="s_cityId2" id="s_cityId2" style="width: 100px;" >
             县区:<input name="s_areaId2"  id="s_areaId2" style="width: 100px;" >
             </td>
          </tr> 
          
           <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>始发地3:</td>   
             <td>省:<input name="s_provinceId3"  id="s_provinceId3" style="width: 100px;" class="easyui-validatebox">
             市:<input   name="s_cityId3" id="s_cityId3" style="width: 100px;" >
             县区:<input name="s_areaId3"  id="s_areaId3" style="width: 100px;" >
             </td>
          </tr> 
          
           
         <!--     <tr>                                                             
             <td class="left"><span style="color:red;">*</span>目的地省:</td>   
             <td><input name="end_provinceId"  id="end_provinceId" style="width: 174px;" required="true" class="easyui-validatebox"></td>         
             </tr> 
             <tr>
             <td class="left"><span style="color:red;">*</span>目的地市:</td>                    
             <td><input   name="end_cityId" id="end_cityId" style="width: 174px;"  class="easyui-validatebox"></td>                                             
             </tr> 
             <tr>
             <td class="left"><span style="color:red;">&nbsp;</span>目的地县区:</td>   
             <td><input name="end_areaId"  id="end_areaId" style="width: 174px;"  class="easyui-validatebox"></td>
             </tr>
	   -->
	  
	         <tr>                                                             
             <td class="left"><span style="color:red;">*</span>目的地1:</td>   
             <td>
                              省:<input name="e_provinceId"  id="e_provinceId" style="width: 100px;" required="true" class="easyui-validatebox">         
                              市:<input name="e_cityId" id="e_cityId" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId"  id="e_areaId" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	         <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地2:</td>   
             <td>
                              省:<input name="e_provinceId2"  id="e_provinceId2" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId2" id="e_cityId2" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId2"  id="e_areaId2" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
             
             <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地3:</td>   
             <td>
                              省:<input name="e_provinceId3"  id="e_provinceId3" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId3" id="e_cityId3" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId3"  id="e_areaId3" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	        <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地4:</td>   
             <td>
                              省:<input name="e_provinceId4"  id="e_provinceId4" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId4" id="e_cityId4" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId4"  id="e_areaId4" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	         <tr>                                                             
             <td class="left"><span style="color:red;">&nbsp;&nbsp;</span>目的地5:</td>   
             <td>
                              省:<input name="e_provinceId5"  id="e_provinceId5" style="width: 100px;"  class="easyui-validatebox">         
                              市:<input name="e_cityId5" id="e_cityId5" style="width: 100px;"  class="easyui-validatebox">                                          
                              县区:<input name="e_areaId5"  id="e_areaId5" style="width: 100px;"  class="easyui-validatebox">
            </td>
             </tr>
	  
	      <tr>
		     <td style="width: 100px;"><span style="color:red;">*</span>发车时间:</td>
			 <td>
		 <%--  <input  type="text"  id="sendDate"   name="sendDate" value="${sendDate}"   editable="false"  required="true"  class="easyui-datebox"    
		  style="width:150px" >&nbsp;&nbsp; --%>
		  <input type="text" id="sendDate" name="sendDate" value="${sendDate}"   readOnly="true"  class="easyui-validatebox" required="true"
					onfocus="WdatePicker({minDate:'%y-%M-{%d+0}',maxDate:'#F{$dp.$D(\'endDate\')}'})"
					style="width: 150px">&nbsp;&nbsp;
		  <select id=sentDateType name="sentDateType" class="text_sketch" >
		  <option selected="selected" value="4">不限</option>
          <option value="0">上午</option>
          <option value="1">中午</option>
          <option value="2">下午</option>
          <option value="3">晚上</option>
         </select>
	        </td>
		  </tr>
		
		 <tr>
             <td class="left"><span>&nbsp;</span>在途时间:</td>   
             <td><input name="onLineHours"  id="onLineHours" maxlength="6" class="easyui-validatebox" style="width: 150px" >&nbsp;&nbsp;&nbsp;天 
             </td>
         </tr>	
         
          <tr>
             <td class="left"><span style="color:red;">&nbsp;</span>截止时间:</td>   
             <td>
            <!--  <input  type="text"  id="endDate" name="endDate"   editable="false"  required="true"  class="easyui-datebox"  style="width:150px" > -->
            <input type="text" id="endDate" name="endDate" readOnly="true"  class="easyui-validatebox"
					onfocus="WdatePicker({minDate:'#F{$dp.$D(\'sendDate\')}'})"
					style="width: 150px">&nbsp;
             </td>
          </tr>	
         
         <tr>
		 <td style="width: 100px;"><span>&nbsp;</span>发运方式:</td>
		 <td>
		 <select id="sendGoodsType" name="sendGoodsType" class="text_sketch" onchange="typeChange()" >
          <option value="2" selected="selected">不限</option>
          <option value="1">整车</option>
          <option value="0">零担</option>
        </select>
	    </td>
	    </tr>
			
		 <tr>
		     <td style="width: 100px;"> <span>&nbsp;</span>价格:</td>
	         <td><input type="text" id="price" name="price"  maxlength="7"  disabled="disabled"
				    class="easyui-validatebox" style="width: 150px">&nbsp;&nbsp;
          <div id="div1" style="display: none;margin-right: 305px;float: right;">	
		   <select id="unitType" name="unitType"  >
           <option selected="selected" value="0">元/吨</option>
           <option value="1">元/公斤</option>
           <option value="2">元/立方</option>
           </select>
           </div>
           <div id="div2" style="margin-right: 360px;float: right;">	
           <span>元</span>
           </div>
            </td>
		 </tr>
			
		<tr>
				<td style="width: 100px;">&nbsp;备注:</td>
				<td><textarea id="remark" name="remark"  maxlength="50"
					class="easyui-validatebox" 
					style="width: 150px"/></td>
		</tr>
			
		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/carLine/add.js"></script>
<script type="text/javascript" src="${CONTEXT}js/carLine/area.js"></script>
<script type="text/javascript" src="${CONTEXT}js/carLine/car.js"></script>














