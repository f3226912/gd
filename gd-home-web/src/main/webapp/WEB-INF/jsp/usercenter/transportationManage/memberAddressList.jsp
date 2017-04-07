<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="keyword1,keyword2,keyword3"/>
		<meta name="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>	
	    <link rel="stylesheet" href="${CONTEXT}v1.0/css/member.css"/>	
	    <%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<%@ include file="/WEB-INF/jsp/pub/tags.inc" %> 
		<title>我的物流</title>
</head>

<body>
	<!--头部-->
    <jsp:include page="../userCenter_head.jsp" flush="true"/> 
   	<jsp:include page="/WEB-INF/jsp/usercenter/userCenter_left.jsp" flush="true"/> 
	<!--头部-->
		<div class="mid-right bg-white">
			<h1 class="mid-right-store">发布物流需求</h1>
			<div class="edit-cont-box-dis edit-cont-box-act">
			<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}">
		    <input type="hidden" id="currPage" name="currPage" value="${currPage}">
				<div class="tab-increase-i"><i class="tab-increase-i0"></i>新增运输货物</div>
				<table class="tab-tra-of-gs">
					<thead>
						<tr class="tab-tra-of-hed">
							<th>始发地</th>
							<th>目的地</th>
							<th>联系人</th>
							<th>手机号码 </th>
							<th>货物名称</th>
							<!-- <th>车辆类型</th> -->
							<th>货物类型</th>
							<th>总重量</th>
							<th>总体积</th>
						<!-- <th>装车时间</th>
							<th>到达时间</th> -->
							<th>运输方式</th>
							<th>价格</th>
							<th>操作</th>
							
						</tr>
					</thead>
					<tbody class="tab-tra-of-toy">
					  <c:forEach  items="${list}" var="obj" >
						<tr>
							<td>${obj.startPlace}</td>
							<td>${obj.endPlace}</td>
							<td>${obj.nickName}</td>
							<td>${obj.userMobile}</td>
							<td>${obj.goodsName}</td>
						<%-- 	<td>${obj.carTypeString}</td> --%>
							<td>${obj.goodsTypeString}</td>
							 <c:choose>
					        <c:when test="${obj.totalWeight >0}">
							<td>${obj.hundredweightString}</td>
					        </c:when>
					        <c:otherwise>
					         <td></td>
					        </c:otherwise>	
					        </c:choose>
						    <c:choose>
					        <c:when test="${obj.totalSize >0}">
					       <td>${obj.totalSize}立方米</td>
					        </c:when>
					        <c:otherwise>
					         <td></td>
					        </c:otherwise>	
					        </c:choose>
						<%-- 	<td>${obj.sendDateString}</td>
							<td>${obj.endDateString}</td> --%>
							<td>${obj.sendGoodsTypeString}</td>
							 <c:choose>
					        <c:when test="${obj.price >0}">
					        <td>  ${obj.priceUnitTypeString}</td>
					        </c:when>
					        <c:otherwise>
					         <td> 面议 </td>
					        </c:otherwise>	
					        </c:choose>
							
							<td>
							
								<c:if test="${ userType ==1}" >
								<a class="i-modify-dc"  onclick="update('${obj.id}')">修改</a>/
							   </c:if>
							   <c:if test="${ userType ==2  && obj.userType ==2}" >
								<a class="i-modify-dc"  onclick="update('${obj.id}')">修改</a>/
							   </c:if>
								<a class="i-shelves"   onclick="deleteConfirm('${obj.id}')">删除</a>
							   
							</td>
							
						</tr>
						</c:forEach>
						
					</tbody>
				</table>

					<div id="page-next2">
						<div class="gduiPage_main"></div>
					</div>

					<div class="tab-increase-pop" style="display:none;">
					<form id="addForm" method="post" action="transportation/save">
						<div class="agree-cusClass">
							<div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232">*</em> 始发地：</span>
								</div>
								<div class="increase-pop-txt">
									<select class="increase-pop-txt-1" id="start_provinceId" name="start_provinceId">
									   <option value="">省</option>
									  <!--  <option value="0">全国</option> -->
									   <c:forEach  items="${listArea}" var="province" >
										<option value="${province.areaID}">${province.area}</option>
										</c:forEach>
									</select>
									
									<select class="increase-pop-txt-1" id="start_cityId" name="start_cityId">
									   <option value="0">市</option>
									</select>
									<select class="increase-pop-txt-1" id="start_countyId" name="start_countyId">
									   <option value="0">地区</option>
									</select>
								</div>
							</div>
							<div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232">*</em> 目的地：</span>
								</div>
								<div class="increase-pop-txt">
								<select class="increase-pop-txt-1" id="end_provinceId" name="end_provinceId">
									  <option value="">省</option>
									  <option value="0">全国</option>
									   <c:forEach  items="${listArea}" var="province" >
										<option value="${province.areaID}">${province.area}</option>
										</c:forEach>
									</select>
									
									<select class="increase-pop-txt-1" id="end_cityId" name="end_cityId">
									   <option value="0">市</option>
									</select>
									<select class="increase-pop-txt-1" id="end_countyId" name="end_countyId">
									   <option value="0">地区</option>
									</select>	
								</div>
							</div>
							<div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232"></em> 货物名称：</span>
								</div>
								<div class="increase-pop-txt">
									<input class="increase-pop-txt-in" type="text" id="goodsName" name="goodsName" maxlength="20" value="${goodsName}">
								</div>
							</div>
							<!-- <div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp">  装车时间：</span>
								</div>
								<div class="increase-pop-txt">
								日期
									<input type="text"  class="start-dat" id="sendDate" name="sendDate" value =""/>
									<select class="midday-dat-it" id="sendDateType" name="sendDateType">
										<option value="4">不限</option>
										<option value="0">上午</option>
										<option value="1">中午</option>
										<option value="2">下午</option> 
										<option value="3">晚上</option>
									</select>
									<label>截止日期：</label><input type="text"  class="end-dat"  id="endDate" name="endDate" value =""/>
								日期
								</div>
							</div> -->
							
						 <!--   <div class="agree-cusClass-tp10">
							    <div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"> 到达时间：</span>
								</div>
								<div class="increase-pop-txt">
									<input type="text"  class="end-dat"  id="endDate" name="endDate" value =""/>
									<select class="midday-dat-it" id="endDateType" name="endDateType">
										<option value="4">不限</option>
										<option value="0">上午</option>
										<option value="1">中午</option>
										<option value="2">下午</option> 
										<option value="3">晚上</option>
									</select>
								</div>
							</div> -->
							
							<div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232"></em> 货物类型：</span>
								</div>
								<div class="increase-pop-txt">
									<select class="increase-pop-txt-1" id="goodsType" name="goodsType">
									  <option selected="selected" value="0">普货</option>
                                      <option value="1">冷藏</option>
                                      <option value="2">鲜活水产</option>
                                      <option value="3">其他</option>
                                      <option value="4">重货</option>
						              <option value="5">抛货</option>
						              <option value="6">蔬菜</option>
					                  <option value="7">水果</option>
					                  <option value="8">农副产品</option>
					                  <option value="9">日用品</option>
					                  <option value="10">纺织</option>
					                  <option value="11">木材</option>
									</select>
								</div>
							</div>
							<div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232">*</em> 总重量：</span>
								</div>
								<div class="increase-pop-txt pop-txt-fl-lt">
									<label><input class="increase-pop-txt-1-1" type="text" id="totalWeight" name="totalWeight"  maxlength="8"  value="${totalWeight}" />
									<select class="increase-pop-txt-1" id="hundredweight" name="hundredweight">
										<option selected="selected" value="0">吨</option>
                                        <option value="1">公斤</option>
									</select>
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;或
								</div>
								<span style="float:left;"></span>
								<div class="increase-pop-fom" style="width:80px;">
									<span class=" "><em style="color:#fb3232"></em> 总体积：</span>
								</div>
								<div class="increase-pop-txt pop-txt-fl-lt">
									<label><input class="increase-pop-txt-1-1" type="text" id="totalSize" name="totalSize" maxlength="8" value="${totalSize}"/>立方米</label>&nbsp;&nbsp;用于估算运费
								</div>
							</div>
							 <div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232"></em> 车辆类型：</span>
								</div>
								<div class="increase-pop-txt">
									<select class="increase-pop-txt-1" id="carType" name="carType">
										 <option selected="selected" value="0">厢式货车</option>
                                          <option value="1">敞车</option>            
                                          <option value="5">高栏车</option>          
                                           <option value="6">平板车</option>          
                                           <option value="2">冷藏车</option>          
                                           <option value="3">保温车</option>          
                                            <option value="7">活鲜水车</option>        
                                             <option value="8">集装箱</option>          
                                             <option value="4">其他</option>   
									</select>
								</div>
							</div> 
						
							 <div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232"></em>车长：</span>
								</div>
								<div class="increase-pop-txt">
                       <!-- 	  <input class="increase-pop-txt-2"  type="text" id="carLength" name="carLength"  maxlength="5"  value=""/>米   -->
						
                                 <select class="increase-pop-txt-1" id="carLength" name="carLength">
								   <option value="0">不限</option> 
								    <option value="-1">小于4.2米</option> 
								    <option value="4.2">4.2米</option>                            
								    <option value="4.5">4.5米</option>                             
								    <option value="5">5米</option>                                 
								    <option value="6.2">6.2米</option>                             
								    <option value="6.8">6.8米</option>                             
								    <option value="7.2">7.2米</option>                             
								    <option value="7.7">7.7米</option>                             
								    <option value="7.8">7.8米</option>                             
								    <option value="8.2">8.2米</option>                             
								    <option value="8.6">8.6米</option>                             
								    <option value="8.7">8.7米</option>                             
								    <option value="9.6">9.6米</option>                             
								    <option value="11.7">11.7米</option>                           
								    <option value="12.5">12.5米</option>                           
								    <option value="13">13米</option>                               
								    <option value="13.5">13.5米</option>                           
								    <option value="14">14米</option> 
								    <option value="15">15米</option>                                 
								    <option value="16">16米</option>                               
								    <option value="17">17米</option>                               
								    <option value="17.5">17.5米</option>                           
								    <option value="18">18米</option> 
								    <option value="-2">大于18米</option>          
								 </select>	
                             	</div>
							</div> 
							
							
							<div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232"></em> 运输方式：</span>
								</div>
					           <div class="increase-pop-txt">
					           <select class="increase-pop-txt-1" id="sendGoodsType" name="sendGoodsType" onchange="typeChange()">
										<option  value="0">零担</option>
                                        <option  value="1" selected="selected">整车</option>
                                        <option value="2">不限</option>
					         </select> 
					         </div>
							</div>
							<div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232"></em> 价格：</span>
								</div>
								<div id="div1"  class="increase-pop-txt" style="display: none;">
									<input class="increase-pop-txt-2" placeholder="不填默认面议" type="text" id="price" name="price" maxlength="8"  value=""/>
									<select class="increase-pop-txt-1" id="priceUnitType" name="priceUnitType">
										<option selected="selected" value="0">元/吨</option>
                                        <option value="1">元/公斤</option>
                                        <option value="2">元/立方</option>
									</select>
								</div>
								
								<div id="div2" class="increase-pop-txt">
									<input class="increase-pop-txt-2" placeholder="不填默认面议" type="text" id="price2"  name="price2" maxlength="8"  value=""/>
									<span>元</span>
								</div>
							</div>
							<div class="agree-cusClass-tp10">
									<div class="increase-pop-fom">
										<span class="increase-pop-fom-sp"><em style="color:#fb3232">*</em>公司名称：</span>
									</div>
									<div class="increase-pop-txt">
									    <input  type="hidden"  name="userType" value="${ userType}" />
										 <c:if test="${ userType==2 }" > 
											 <input  type="hidden"  id="companyUserId" name="companyUserId" value="${memberCertifi.memberId }" />
											 <input  type="text"  readonly class="xa-sel-clk"   id="companyName"   name="companyName"  placeholder="" value="${memberCertifi.companyName }" />
										 </c:if> 
										 <c:if test="${ userType!=2 }" >
										 	<input  type="hidden" id="companyUserId" name="companyUserId" value="${memberCertifi.memberId }" />
											<input type="text" readonly id="companyName" class="xa-sel-clk"  placeholder="" value='<c:if test="${!empty memberCertifi}">${memberCertifi.companyName}</c:if>'>
											<br/>
											<ul cellspacing="0" class="block-sel-clk">
												<c:forEach  items="${companys}" var="company" >
													<li><a href="javascript:;" onclick='changeCompany("${company.memberId}");'>${company.companyName}</a></li>
		 										</c:forEach>
											</ul>
										 </c:if> 
									 
									</div>
							    </div>
							    
							 <div class="agree-cusClass-tp10">
								<div class="increase-pop-fom">
									<span class="increase-pop-fom-sp"><em style="color:#fb3232"></em>备注：</span>
								</div>
								<div class="increase-pop-txt">
								<textarea id="remark" name="remark" rows="4" cols="50" maxlength="50" class="increase-pop-txt-in" ></textarea>
								</div>
							</div> 
						</div>
						</form>
					</div>


			</div>

			<!--经营模式-->
		
			<div class="tab-incr-cet">发布成功物流车主将能看到您的货物信息</div>
			
		</div>


	<script type="text/javascript">
	
		function changeCompany(val){
			  $("#companyUserId").val(val);
		}
	
	
	
		$('.xa-sel-clk').click(function(){
			if($('.block-sel-clk').is(":visible")){
				$('.block-sel-clk').hide();
				$('.xa-sel-clk').css('border-bottom','#ccc solid 1px');
			}else{
				$('.block-sel-clk').show();
				$('.xa-sel-clk').css('border-bottom','#fff solid 1px');
			}		
	
		});
		$(".block-sel-clk").find("a").click(function(){
			var curVal =$(this).text();
			$(".xa-sel-clk").val(curVal);
			$(".block-sel-clk").hide();
			$('.xa-sel-clk').css('border-bottom','#ccc solid 1px');
		});
	
	
	
		$('.tab-increase-i0').click(function(){
			  $("#addForm")[0].reset();
			  //始发地 
			  $("#start_provinceId").attr("value","");
			  $("#start_cityId").attr("value","0");
			  $("#start_countyId").attr("value","0");
              //目的地
              $("#end_provinceId").attr("value","");
              $("#end_cityId").attr("value","0");
              $("#end_countyId").attr("value","0");
              
				//捕获页
			layer.open({
			    type: 1,
			    cusClass:'agree-cusClass',//添加自定义类名
			    title: "新增运输货物", //不显示标题
			    area:["650px","600px"],
			    content: $('.tab-increase-pop'), //捕获的元素
			    btn:['发布','返回'],
			    yes:function(){
			    	 if(checkData()){
			    		 save();
				         layer.open({
						    type: 1,
						    cusClass:'agree-cusClass',//添加自定义类名
						    title: "新增运输货物", //不显示标题
						    area:["600px","350px"],
						    content: $('.tab-incr-cet'), //捕获的元素
						    btn:['继续发布需求','查看发布需求'],
						    btn1:function(){
						    	$('#addForm')[0].reset();
						    },
						    btn2:function(){
							 	location.href =CONTEXT+'userCenter/transportation/query?page=1';
						    }
						    
						});
			    	}
			    	 
			    }
			});

		});
		
		//update  begin
		function update(id){
			//查询对象信息
	        $.ajax({  
	            type: "POST",  
	            url:CONTEXT+"transportation/edit?id="+id,  
	            success: function(data) {  
	                if (data == "fail") {  
	                    alert("loading failed");  
	                }  
	                else {
	                	$("#goodsId").val(data.id);
	                	$("#goodsName").val(data.goodsName);
	                	$("#totalWeight").val(data.totalWeight);
	                	$("#totalSize").val(data.totalSize);
	                	$("#sendDate").val(data.sendDateString);
	                	$("#endDate").val(data.endDateString);
	                    $("#sendDateType").attr("value",data.sendDateType);
	                    $("#goodsType").attr("value",data.goodsType);
	                    $("#carType").attr("value",data.carType);
	                    $("#carLength").attr("value",data.carLength);

	                    $("#sendGoodsType").attr("value",data.sendGoodsType);
	                    $("#companyUserId").val(data.createUserId);
	                    $("#companyName").val(data.companyMobile);
	                    $("#remark").val(data.remark);
	                    
	                    if(data.sendGoodsType =='0')
	                	$("#price").val(data.price);
	                    else if(data.sendGoodsType =='1')
	                    $("#price2").val(data.price);
	                    
	                    $("#priceUnitType").attr("value",data.priceUnitType);
	                    
	                     //始发地
	                     $("#start_provinceId").attr("value",data.s_provinceId);
	    		         $.ajax({  
	    		            type: "POST",  
	    		            url:CONTEXT+"transportation/queryCity.do?province="+data.s_provinceId,  
	    		            dataType : "json",
	    		            success: function(data1) {  
	    		                if (data1 == "fail") {  
	    		                    alert("loading failed");  
	    		                }  
	    		                else {  
	    		                    loadCityList(data1);  
	    		                    $("#start_cityId").attr("value",data.s_cityId);
	    		                }  
	    		            }  
	    		        }); 
	                	
	    			        $.ajax({  
	    			            type: "POST",  
	    			            url:CONTEXT+"transportation/queryCounty.do?city="+data.s_cityId,
	    			            dataType : "json",
	    			            success: function(data1) {  
	    			                if (data1 == "fail") {
	    			                	alert("loading failed");  
	    			                }  
	    			                else {  
	    			                 loadCountyList(data1);  
	    			                 $("#start_countyId").attr("value",data.s_areaId);
	    			                }  
	    			            }  
	    			        }); 
	    		         
		                 //目的地
		                 $("#end_provinceId").attr("value",data.f_provinceId);
		                  
		    		        $.ajax({  
		    		            type: "POST",  
		    		            url:CONTEXT+"transportation/queryCity.do?province="+data.f_provinceId,  
		    		            dataType : "json",
		    		            success: function(data1) {  
		    		                if (data1 == "fail") {  
		    		                    alert("loading failed");  
		    		                }  
		    		                else {  
		    		                    _loadCityList(data1);  
		    		                    $("#end_cityId").attr("value",data.f_cityId);
		    		                }  
		    		            }  
		    		        });  
	                     
		    				     $.ajax({  
		    				            type: "POST",  
		    				            url:CONTEXT+"transportation/queryCounty.do?city="+data.f_cityId,
		    				            dataType : "json",
		    				            success: function(data1) {  
		    				                if (data1 == "fail") { 
		    				                 alert("loading failed");  
		    				                }  
		    				                else {  
		    				                	_loadCountyList(data1);  
		    				                	 $("#end_countyId").attr("value",data.f_areaId);
		    				                }  
		    				            }  
		    				        }); 
		    		        
	                	
		                 typeChange();
		                 //捕获页
	            		layer.open({
	            		    type: 1,
	            		    cusClass:'agree-cusClass',//添加自定义类名
	            		    title: "修改运输货物", //不显示标题
	            		    area:["650px","600px"],
	            		    content: $('.tab-increase-pop'), //捕获的元素
	            		    btn:['保存','返回'],
	            		    yes:function(){
	            		    	if(checkData()){
	            					var url=CONTEXT+"transportation/save?id="+id;
	            						jQuery.post(url, $('#addForm').serialize(), function (data) {
	            							
	            							if (data == "success") {
	            								layer.msg("操作成功！");
	            								//刷新父页面列表
	            								location.href =CONTEXT+'userCenter/transportation/query?page=1';
	            							}else {
	            								layer.msg("系统异常！");
	            								return;
	            							}
	            						});
	            		    	}
	            		    }
	            		});
	                }  
	            }  
	        }); 	
      }

   //update  end	
		

		//日期
/* 		var start ={
		    elem: '#sendDate',
		    format: 'YYYY-MM-DD',
		    min: laydate.now(), //设定最小日期为当前日期
		    max: '2099-06-16 23:59:59', //最大日期
		    istime: false,
		    istoday: false,
		    choose: function(datas){
		         end.min = datas; //开始日选好后，重置结束日的最小日期
		         end.start = datas; //将结束日的初始值设定为开始日
		    }
		};
		var end = {
		    elem: '#endDate',
		    format: 'YYYY-MM-DD',
		    min: laydate.now(),
		    max: '2099-06-16 23:59:59',
		    istime: false,
		    istoday: false,
		    choose: function(datas){
		        start.max = datas; //结束日选好后，重置开始日的最大日期
		    }
		};
		laydate(start);
		laydate(end); */
		//日期

	    var totalPage2= $("#totalPage").val();
		var currPage2= $("#currPage").val();
		gduiPage({
		    cont: 'page-next2',
		    pages: totalPage2, //
		    skip: true, //是否开启跳页
		    curr:currPage2,
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		            location.href = '?page='+e.curr;
		          
		        }
		    },
		    staticPage:true 
		});	
	
		$(document).ready(function() {  
		    $("#start_provinceId").change(function() {
		    var province = $("#start_provinceId").val();  
		        $.ajax({  
		            type: "POST",  
		            url:CONTEXT+"transportation/queryCity.do?province="+province,  
		            dataType : "json",
		            success: function(data) {  
		                if (data == "fail") {  
		                    alert("loading failed");  
		                }  
		                else {
		                    loadCityList(data);  
		                }  
		            }  
		        });  
		    });  
		 }); 
		
		function loadCityList(data) {
		    $("#start_cityId").empty();
	        $("#start_cityId").append("<option value='" + "0"+ "'>" + "市" + "</option>");  
	        $("#start_countyId").empty();  
	        $("#start_countyId").append("<option value='" + "0"+ "'>" + "地区" + "</option>");  
		    $.each(data, function(i, item){ 
		        $("#start_cityId").append("<option value='" + item.areaID + "'>" + item.area + "</option>");  

		    });  
		 }
		
		$(document).ready(function() {  
		    $("#start_cityId").change(function() {  
		    var city = $("#start_cityId").val();  
		        $.ajax({  
		            type: "POST",  
		            url:CONTEXT+"transportation/queryCounty.do?city="+city,
		            dataType : "json",
		            success: function(data) {  
		                if (data == "fail") {  
		                }  
		                else {  
		                	loadCountyList(data);  
		                }  
		            }  
		        });  
		    });  
		 }); 
		
		function loadCountyList(data) {  
		    $("#start_countyId").empty();  
	        $("#start_countyId").append("<option value='" + "0"+ "'>" + "地区" + "</option>");  
		    $.each(data, function(i, item){ 
		        $("#start_countyId").append("<option value='" + item.areaID + "'>" + item.area + "</option>");  

		    });  
		 }
		
		//目的地省市县
		$(document).ready(function() {  
		    $("#end_provinceId").change(function() {
		    var province = $("#end_provinceId").val();  
		        $.ajax({  
		            type: "POST",  
		            url:CONTEXT+"transportation/queryCity.do?province="+province,  
		            dataType : "json",
		            success: function(data) {  
		                if (data == "fail") {  
		                    alert("loading failed");  
		                }  
		                else {  
		                    _loadCityList(data);  
		                }  
		            }  
		        });  
		    });  
		 }); 
		
		function _loadCityList(data) {  
		    $("#end_cityId").empty(); 
	        $("#end_cityId").append("<option value='" + "0"+ "'>" + "市" + "</option>");
	        $("#end_countyId").empty();  
	        $("#end_countyId").append("<option value='" + "0"+ "'>" + "地区" + "</option>"); 
		    $.each(data, function(i, item){ 
		        $("#end_cityId").append("<option value='" + item.areaID + "'>" + item.area + "</option>");  

		    });  
		 }
		
		$(document).ready(function() {  
		    $("#end_cityId").change(function() {  
		    var city = $("#end_cityId").val();  
		        $.ajax({  
		            type: "POST",  
		            url:CONTEXT+"transportation/queryCounty.do?city="+city,
		            dataType : "json",
		            success: function(data) {  
		                if (data == "fail") {  
		                }  
		                else {  
		                	_loadCountyList(data);  
		                }  
		            }  
		        });  
		    });  
		 }); 
		
		function _loadCountyList(data) {  
		    $("#end_countyId").empty();  
	        $("#end_countyId").append("<option value='" + "0"+ "'>" + "地区" + "</option>");  
		    $.each(data, function(i, item){ 
		        $("#end_countyId").append("<option value='" + item.areaID + "'>" + item.area + "</option>");  

		    });  
		 }
		
		//新增
		function save() {
			var url=CONTEXT+"transportation/save";
				jQuery.post(url, $('#addForm').serialize(), function (data) {
				
					if (data == "success") {
						return true;
						//layer.msg("操作成功！");
					}else {
						layer.msg("系统异常！");
						return false;
					}
				});
		}
		
		//
		function checkData()
		{  
			  
			  //始发地 
			 var start_provinceId = $("#start_provinceId").val();
			// var start_cityId = $("#start_cityId").val();
			 if (start_provinceId =='') {
				 layer.msg("请选择始发地!");
				 return false;
			 }
			 
             //目的地
             var end_provinceId = $("#end_provinceId").val();
			 // var end_cityId = $("#end_cityId").val();
			 if (end_provinceId =='') {
				 layer.msg("请选择目的地!");
				 return false;
			 }
			 
			 var goodsName = $("#goodsName").val();
			 /* 
			 if (goodsName =='') {
				 layer.msg("请输入货物名称 !");
				 return false;
			 } */
			 
			 if (goodsName !='') {
			 var reg =/^[\u0391-\uFFE5\w]+$/;
			 if (!reg.test(goodsName)) {
				 layer.msg("货物名称不能包含特殊字符!");
				 return false;
			  }
			 }
			 
			/* var sendDate=$("#sendDate").val();
			if(sendDate ==''){
				 layer.msg("请输入发货时间!");
				return false;
		    }
			var endDate=$("#endDate").val();
			if(endDate ==''){
				 layer.msg("请输入截止时间!");
				return false;
		    } */
			
			if($("#sendDate").val() != '' && $("#endDate").val() != ''){
					var sendDateType = $("#sendDateType").val();
					var endDateType = $("#endDateType").val();
					if($("#sendDate").val() == $("#endDate").val() && sendDateType !='4' && endDateType!='4'){
						if(sendDateType > endDateType){
							layer.msg("到达时间不能早于装车时间");
							return  false;
						}
					}
					if($("#sendDate").val() > $("#endDate").val())
					 {
						 layer.msg("到达时间不能早于装车时间");
						 return  false;
					 }
			}
		    
			//var test1 =/^\+?[1-9][0-9]*$/;
			var test1=/^\d+$/;
			var totalWeight=$("#totalWeight").val();
			if(totalWeight !=''){
			 if (totalWeight.search(test1) == -1) {
				 layer.msg("总重量请输入正整数!");
				return false;
			 }
			}
			
			var totalSize=$("#totalSize").val();
			if(totalSize !=''){
			 if (totalSize.search(test1) == -1) {
				 layer.msg("总体积请输入正整数!");
				return false;
			 }
			}
			if (totalWeight =='' && totalSize =='' ) {
				 layer.msg("请输入总重量或总体积!");
				 return false;
			}
			if (totalWeight <= 0 && totalSize <=0 ) {
				 layer.msg("请输入总重量或总体积至少一个大于0!");
				 return false;
			}
			/*  var testCarLength =/^[0-9]+(.[0-9]{1,1})?$/;
				var carLength=$("#carLength").val();
				if(carLength !=''){
				if (carLength.search(testCarLength) == -1) {
					layer.msg("车长请输入整数或一位小数!");
					return false;
				}
			  } */
			 var test2 =/^[0-9]+(.[0-9]{1,2})?$/;
				var price=$("#price").val();
				if(price !=''){
				 if (price.search(test2) == -1) {
					layer.msg("价格请输入整数或两位小数!");
					return false;
				 }
		    }
			
		   var companyUserId =$("#companyUserId").val();
		   var companyName = $("#companyName").val();
		   if(companyUserId == null || companyUserId =='' || companyName == null || companyName =='')
		   {
			   layer.msg("请选择物流公司 !");
			   return false;
		   }
		   
		   var remark = $("#remark").val();
		   if(remark != null  &&  remark !='' && remark.length>50)
		   {
			   layer.msg("备注长度请小于50个字符!");
			   return false;
		   }
		 
			 var sendGoodsType=$("#sendGoodsType").val();
			 var price2=$("#price2").val();
			     if (sendGoodsType =='0') {
			    	   if(price == '' ){
			    		 $("#price").val('0');
			    	 //  layer.msg("请输入价格!");
					//	 return false;
			    	   }
				 }
			     else if (sendGoodsType =='1') {
			    	   if(price2 == '' ){
				    	$("#price2").val('0');
			    	   }
				 }
			    
			
			return true;
		}

		
		function deleteConfirm(deleteId)
		{
					layer.confirm('确定删除吗？', {
					cusClass:'',//类名
				    btn: ['确定','取消'] ,
				    btn1:function(){
				    	jQuery.post(CONTEXT+"transportation/deletebyid",{"id":deleteId},function(data){
							if (data == "success"){
								layer.msg("操作成功！");
								location.href =CONTEXT+'userCenter/transportation/query?page=1';
							}else{
								layer.msg(data);
								return;
							}
			    		});
				    }
					});
		}
		
		function typeChange()
		{  
			var sendGoodsType = $("#sendGoodsType").val();
			if(sendGoodsType=='0')
			{
				$('#price').removeAttr("disabled");  
				$('#price2').val('');
			    $("#priceUnitType").removeAttr("disabled");  
				$("#div1").show();
				$("#div2").hide();
			}
			else if(sendGoodsType=='1')
			{
				$("#price2").removeAttr("disabled");  
				//$('#price').attr('disabled', 'true');
				$('#price').val('');
				$('#priceUnitType').attr('disabled', 'true');
				$("#div1").hide();
				$("#div2").show();
			}
			else 
			{
				$("#div1").hide();
				$("#div2").hide();
				$('#price').attr('disabled', 'true');
				$('#price2').attr('disabled', 'true');
				$('#priceUnitType').attr('disabled', 'true');
			}
		}
		
	</script>
	<!--简版底部 star-->
	
	<!--简版底部 end-->


</html>
