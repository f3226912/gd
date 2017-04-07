<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<%@ include file="../../pub/constants.inc" %>
	<%@ include file="../../pub/head.inc" %>
	<%@ include file="../../pub/tags.inc" %>
</head>

<body>
   <jsp:include page="../userCenter_head.jsp" flush="true"/> 
   <jsp:include page="../userCenter_left.jsp" flush="true"/> 

		<div class="mid-right mid-right-bor">
			<h1 class="mid-right-store">未上架的产品</h1>
			<div id="tab">
				<ul class="tab_menu">
			    	<li class="selected">已下架的产品</li>
			        <li>审核中的产品</li>
			        <li>审核不通过的产品</li>
			    </ul>
				<form id="ListForm" method="post">
					<div class="tab_box" style="background:white;">
						<div class="tab_box-cet">
							<div class="tab_box-cet-ser"><em class="tab_box-cet-ser-nam">产品名称：</em>
								<div class="tab_box-cet-ser-box">
									<input class="tab_box-cet-ser-tit" type="text" id="productName" name="productName" value="${productName}"/>
									<button class="tab_box-cet-ser-so" type="button" onclick="javascript:submitPage()"><i class="tab_box-cet-ser-ico"></i>搜索</button> 
								</div>
							</div>
							<!-- 未上架 -->
							<c:if test="${page==null || empty page.pageData }">
									<p style="margin: 50px 0; text-align: center; font-size: 14px;">官人，还没有符合要求的产品哦！</p>
								</c:if>
								<c:if test="${page!=null && not empty page.pageData}">
								<table class="tab-gridtable">
									<thead>
										<tr>
											<th style="width:3px;"></th>
											<th>产品</th>
											<th>价格</th>
											<th>更新日期</th>
											<th>到期日期</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<c:forEach items="${page.pageData}" var="product" varStatus="s">
									<tbody id="allOffTBody">
										<tr>
											<td><label><input class="tab-att-i" id="lab-chek" type="checkbox" value="${product.productId}"/>
											</label></td>
											<td class="tab-tp30-cen"><a href="javascript:void(0);" onclick="detail('${product.productId}')">${product.productName}</a></td>
											<td><div class="ico-pos-rel-lt"><i class="tab-pri-edi" onclick="modifyPrice(this,'${product.productId}','${product.priceType}','${product.price}' )"></i>
												${gd:formatNumber(product.price) }/${gd:showValueByCode('ProductUnit',product.unit)}
												<%-- <span id="priceSpan${product.productId}"></span>
												<script type="text/javascript">
													var line_price = new Number("${product.price}");
													var integer = parseInt(Math.abs(line_price)/10000);
													var text = line_price;
													if (integer > 0){
														text = (line_price/10000).toFixed(2) + " 万";
													}
													$("#priceSpan"+"${product.productId}").text(text);
												</script> --%>
												</div></td>
											<td><fmt:formatDate value="${product.updateTime}" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${product.expirationDate}" pattern="yyyy-MM-dd"/></td>
											<td style="color:#e21313">
											<c:choose>
											<c:when test="${state == 1}">待审核</c:when>
											<c:when test="${state == 2}">审核不通过</c:when>
											<c:otherwise>已下架</c:otherwise>
											</c:choose>
											</td>
											<td>
												<a href="javascript:void(0);" onclick="edit('${product.productId}')" >修改</a>/
												<c:if test="${state == 4}">
												<a class="i-shelves" href="javascript:void(0);" pId="${product.productId}">上架</a>/
												</c:if>
												<a class="i-delete" href="javascript:void(0);" pId="${product.productId}">删除</a>
											</td>
										</tr>
									</tbody>
									</c:forEach>
									<tfoot class="tab-gridtable-tr-ft">
										<tr>
											<th colspan="7">
												<div class="tab-ang-lt">
													<input id="sel-all-chek" type="checkbox" class="checkAll" onclick="check()"/>
													<label for="sel-all-chek" onclick="allSelect()">全选</label> 
													<a class="tab-ang-lt-del" href="javascript:void(0);" onclick="batchUpdate(5)">删除</a>
													<c:choose>
														<c:when test="${state == 4}">
															<a class="tab-ang-lt-del" href="javascript:void(0);" onclick="batchUpdate(3)">上架</a>
														</c:when>
														<c:otherwise></c:otherwise>
													</c:choose>
													
												</div>
											</th>
										</tr>
									</tfoot>
								</c:if>
							</table>
							<div id="mar-com-fenye" class="fenye"></div>
							<div class="clear"></div>
							<div id="page-next">
	<%-- 								<input type="hidden" id="sort" name="sort" value="${param.sort}">
									<input type="hidden" id="order" name="order" value="${param.order}"> --%>
								<div class="gduiPage_main">
								</div>
							</div>
						</div>
					</div>
					<input type="hidden" id="chargeType" name="chargeType" value="${state}">
					<input type="hidden" id="currentPage" name="page" value="1">
					<input type="hidden" id="pageSize" name="pageSize" value="8">
					<input type="hidden" id="pageTotal" name="pageTotal" value="0">
				</form>
			</div>			
		</div>
	<div class="clear"></div>
<form id="priceForm" method="post">
	<input type="hidden" id="productId_modify" name="productId" value="">
	<div class="i-modify-box-no">
		<div class="edit-cont--unit-pir pt15 clearfix">
			<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>价格：</span>
			<div class="rse-pro-fl-met">
				<select class="rse-pro-fl-sel-opt rse-pro-tt" id="priceType" name="priceType">
					<option value="0" >单价</option>
					<option value="1">价格区间</option>
				</select>
			</div>
		</div>
		<div class="clear"></div>
		<div id="priceAddContainer" class="edit-cont--unit-pir edit-cont-i pt15 clearfix">
			<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>单价：</span>
			<div class="rse-pro-fl-met"><input type="text" id="price" name="price"  maxLength="11" class="rse-pro-fl-met-gy" placeholder="单价" /></div>
		</div>
		
		<div id="priceTableContainer" class="edit-cont-sel pt15 clearfix">
			<span class="rse-pro-tit-int">价格区间：</span>
			<div class="rse-pro-fl-cp1">
				<table id="priceTable" class="rse-pro-pur-sl">
					<tr>
						<td>
							<div class="rse-pro-fix">
								<em class="rse-pro-tit-red">*</em>
						    	<span class="rse-pro-quantity">购买数量</span>
					    	</div>
				   		</td>
				   		<td></td>
						<td>
							<div class="rse-pro-fix rse-pro-fix1">
								<em class="rse-pro-tit-red">*</em>
								<span class="rse-pro-quantity">价格</span>
							</div>
						</td>
					</tr>
				</table>	
				<div class="rse-pro-fl-cp3">
					<a class="pt5 pb5 clearfix" href="javascript:void(0)" onclick="addPriceRow()"><span class="rse-pro-fl-add"></span>增加价格区间</a>
					<br/>提示：1、可根据买家采购的不同数量设置不同价格，最多可填写3条价格区间）<br/>
					<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、最后一行如果截止起订量为空表示大于当前行起始起订量者,均使用当前行价格</label>
				</div>
				
			</div>
		</div>
	</div>
</form>
	
	<!--简版底部 star-->
	
	<!--简版底部 end-->

	<script type="text/javascript">
	
		function edit(productId){
			 $.ajax({  
				    url:CONTEXT+'userCenter/product/isProductDelete',
				    data : {"productId":productId},  
				    type:'post',  
				    dataType:'json',  
				    success:function(data) {
				    	if(data.status == 1){
				    		window.location.href = CONTEXT+'userCenter/product/toEditProductCategory/' + productId;
				    	}else{
				    		alert("该产品已被删除, 无法修改");
				    	}
				    	
				     },  
				     error : function() {  
				     }  
				});
			
		}
		function detail(productId){
			window.location.href = CONTEXT+'market/'+productId+'.html';
			//window.location.href = CONTEXT+'product/detail/' + productId;
		}
		//删除按钮事件
		$('.i-delete').on("click",function(){
			var pid = $(this).attr("pId");
			layer.confirm('确定删除吗？', {
			cusClass:'',//类名
			btn: ['确定','取消'], //按钮
			btn1: function(){
					updateState(pid, 5);
				}
			});
		});
		//上架按钮事件
		$('.i-shelves').on("click",function(){
			var pid = $(this).attr("pId");
			layer.confirm('确定上架吗？', {
			cusClass:'',//类名
			btn: ['确定','取消'], //按钮
			btn1: function(){
					updateState(pid, 3);
				}
			});
		});		
/* 		//修改按钮事件		
		$(".i-modify").on("click",function(){
			layer.open({
				type: 1,
			    title: "修改价格", //显示标题
			    area:["600px","400px"],
			    content: $('.i-modify-box-no'), //捕获的元素
			    cancel: function(index){
			    layer.close(index);
			    }

			});
		}); */

		$('#priceType').on("change",function(){
			var priceType = $(this).val();
			if (1 == priceType){
				$("#priceTableContainer").show();
				$("#priceAddContainer").hide();
			}else {
				$("#priceAddContainer").show();
				$("#priceTableContainer").hide();
			}
		});
		function submitPriceChanges(){
			 $.ajax({  
				    url:CONTEXT+'userCenter/product/modifyPrice',
				    data : $('#priceForm').serialize(),  
				    type:'post',  
				    dataType:'json',  
				    success:function(data) { 
				    	if(data.status == 1){
				    		alert("操作成功");
				    		window.location.reload(true);
				    	}else{
				    		alert("操作失败");
				    	}
				    	
				     },  
				     error : function() {  
				     }  
				});
		}
		function modifyPrice(obj, productId,priceType, price){
			$("#productId_modify").val(productId);
			$("#priceType").val(priceType);
			if (1 == priceType){
				$("#priceTableContainer").show();
				$("#priceAddContainer").hide();
				 $.ajax({  
					    url:CONTEXT+'userCenter/product/queryProductMutilPrice/' + productId,
					    data:{  
					    	productId : productId
					    },  
					    type:'post',  
					    dataType:'json',  
					    success:function(data) { 
					    	if(data.status == 1){
					    		var list = data.list;
					    		generatePriceLines(list);
					    	}
					     },  
					     error : function() {  
					     }  
					});
				 
			}else {
				$("#priceAddContainer").show();
				$("#priceTableContainer").hide();
				//转换科学计数法为正常表示方法
				price = new Number(price);
				$("#price").val(fixNum(price));
			}
			layer.open({
				type: 1,
			    title: "修改价格", //显示标题
			    area:["600px","400px"],
			    btn:["确定","取消"],
			    cusClass:'memdia',
			    yes:function(index){
			    	if ($("#priceType").val() == 1 && !checkRow()){
			    		return false;
			    	}else {
			    		if (isNaN($("#price").val())){
							alert("单价必须是数字");
							return false;
						}
			    		if ($("#price").val() < 0){
			    			alert("单价不能为负数");
			    			return false;
			    		}
			    		if (checkDecimal($("#price").val(), 10, 2) == 1){
			    			alert("单价输入有误,数值长度过长,整数部分最多输入8位和小数点后最多2位");
			    			return false;
			    		} else if (checkDecimal($("#price").val(), 10, 2) == 2){
			    			alert("单价输入有误, 小数点后位数过长(小数点后保留两位)");
			    			return false;
			    		}
			    	}
			    		
			    	submitPriceChanges();
			    	layer.close(index);
			    	
			    },
			    btn2:function(index){
			    	layer.close(index);
			    },
			    content: $('.i-modify-box-no') //捕获的元素
			    

			});			
		}

		function generatePriceLines(list){
		 	//清空除表头之外的其他行
		 	$("#priceTable tr:gt(0)").remove();
			var startItem , endItem, priceItem;
			for(var i in list){
				startItem = list[i].buyCountStart ? list[i].buyCountStart: '';
				endItem = list[i].buyCountEnd ? list[i].buyCountEnd:'' ;
				priceItem = list[i].price ? list[i].price:'';
				var trId="priceDtoList_"+i;
				var html = 	'<tr id="'+trId+'" class="rse-pro-fl-cp2">' ;
				html+='<td><input class="rse-pro-interval" type="text" id="priceDtoList['+i+'].buyCountStart" name="priceDtoList['+i+'].buyCountStart" alias="CbuyCountStart" value="'+ startItem +'"  maxlength="9">—&nbsp;&nbsp;</td>';
				html+='<td><input class="rse-pro-interval" type="text" id="priceDtoList['+i+'].buyCountEnd" name="priceDtoList['+i+'].buyCountEnd" alias="CbuyCountEnd" value="'+ endItem +'"  maxlength="9" ></td>';
				html+='<td><input class="rse-pro-interval" type="text" id="priceDtoList['+i+'].price" name="priceDtoList['+i+'].price" alias="Cprice" value="'+ priceItem +'"  maxlength="11" ></td>';
				html+='<td></td>';
				/* html+='<td><label>截止起订量为空表示大于当前行起始起订量者,均使用当前行价格</label></td>'; */
				html+='<td><span><a href="javascript:void(0)" onClick="deletePriceRow(\''+trId+'\')">-删除</a></span></td>';
				html+='</tr>';
				$("#priceTable").append(html);  
			}
			$("#priceTable").show();
		}
		
		//标签栏点击事件
		$('.tab_menu li').on("click",function(){
			$(this).addClass('selected').siblings().removeClass('selected');
			var index = $(this).index();
			changeState(index);
			submitPage();
		}); 

		function initiateTab(){
			var state = $("#chargeType").val();
			var index = 0;
			switch(state){
				case '4': index = 0; break;
				case '1': index = 1; break;
				case '2': index = 2; break;
			}
			//console.log("state: " + state + ", index: " + index);
			$('.tab_menu li').eq(index).addClass('selected').siblings().removeClass('selected');
		};
		
		//更新产品状态， 3.已上架;4.已下架;5.已删除
		function updateState(pId, pState){
			var updUrl = CONTEXT + 'userCenter/product/updateState';
			var aj = $.ajax( {  
				url: updUrl,// 跳转到 action  
				data:{  
						 productId : pId,
						 state: pState
				},  
				type:'get',  
				cache:false,
				success:function(obj) {
					var data = $.parseJSON(obj);
					if(data.status == 1 ){   
						showMsg("操作成功！"); 
					}else{  
						
						showMsg("操作失败！！ " + data.message);   
					}  
				 },  
				 error : function() {
					  showMsg("网络异常！");
				 }  
			});
		};
		
		//批量更新产品状态， 3.已上架;5.已删除
		function batchUpdate(pState){
			var updUrl = CONTEXT + 'userCenter/product/batchUpdate';
			var ids = "";
			$(".tab-att-i:checked").each(function(){
				ids += $(this).val() + ",";
			});
			if(ids == ""){
				showMsg("请先选择产品");
				return false;
			}
			ids = ids.substring(0, ids.length - 1);
			var aj = $.ajax( {  
				url: updUrl,// 跳转到 action  
				data:{  
						 productIds : ids,
						 state: pState
				},  
				type:'post',  
				cache:false,
				success:function(obj) {
					var data = $.parseJSON(obj);
					if(data.status == 1 ){   
						showMsg("操作成功！");
					}else{  
						showMsg("操作失败！ " + data.message);  
					}  
				 },  
				 error : function() {
					  showMsg("网络异常！");
				 }  
			});
		};
		
		function allSelect() {
			$(".checkAll").attr("checked", true);
			check();
		};
		
		function check() {
			if($(".checkAll").attr("checked")) {
				$(".tab-att-i").attr("checked","checked");
			} else {
				$(".tab-att-i").attr("checked",false);
			}
		};
		
		//弹出消息
		function showMsg(message){
			layer.confirm(message, {
			cusClass:'',//类名
			btn: ['确定'], //按钮
			btn1: function(){
					location.reload();
				}
			});
		}
	
		//提交
		var submitPage = function(){
			var state = $("#chargeType").val();
			var url = CONTEXT + 'userCenter/product/saleList/' + state;
			$("#ListForm").attr("action",url).submit();
		};
		
		//设置商品状态
		var changeState = function(type){
			switch(type){
				case 0: $("#chargeType").val("4"); break;
				case 1: $("#chargeType").val("1"); break;
				case 2: $("#chargeType").val("2"); break;
				default: $("#chargeType").val("4"); break;
			}       
		};
		
		var pageTotal=${page.pageTotal};
		var pageNow=${page.currentPage};
			gduiPage({
			cont: 'mar-com-fenye',
			pages: pageTotal, //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
		/*     curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
				var page = location.search.match(/page=(\d+)/);
				return page ? page[1] : 1;
			}(),  */
			curr:pageNow,
			jump: function(e, first){ //触发分页后的回调
				if(!first){ //一定要加此判断，否则初始时会无限刷新
					$("#currentPage").val(e.curr);
					//console.log("page no: " + $("#currentPage").val());
					submitPage();
				}
			},
			staticPage:true 
		});

		initiateTab();
		
		
		/**
		 计算多价列表中的价格区间的行数
		减去表头所占的一行
	 */
	function countRow(tableId){
		 return document.getElementById(tableId).getElementsByTagName('tr').length-1;
	}
	function deletePriceRow(rowId){
		$("#"+rowId).remove();
	}
	function addPriceRow(){
		var len = countRow("priceTable");
		if (len > 0){
			 if(len>=3){
				 alert("最多只能添加三个区间价格！");
				 return false;
			 }
			var buyCountStarts=$("input[alias='CbuyCountStart']"); 
			var buyCountEnds=$("input[alias='CbuyCountEnd']"); 
			//var prices=$("input[alias='Cprice']"); 
			 for (var i = 0; i < len ; i++){
				if (!buyCountStarts[i].value || !buyCountEnds[i].value ){
					alert("存在起订量未填写的行, 不能添加新行");
					return false;
				}
			}
		}

		var trId="priceDtoList_"+len;
		
		var html = 	'<tr id="'+trId+'" class="rse-pro-fl-cp2">' ;
		html+='<td><input class="rse-pro-interval" type="text" id="priceDtoList['+len+'].buyCountStart" name="priceDtoList['+len+'].buyCountStart" alias="CbuyCountStart" value=""  maxlength="9">—&nbsp;&nbsp;</td>';
		html+='<td><input class="rse-pro-interval" type="text" id="priceDtoList['+len+'].buyCountEnd" name="priceDtoList['+len+'].buyCountEnd" alias="CbuyCountEnd" value="" maxlength="9" ></td>';
		html+='<td><input class="rse-pro-interval" type="text" id="priceDtoList['+len+'].price" name="priceDtoList['+len+'].price" alias="Cprice" value="" maxlength="11" ></td>';
		html+='<td></td>';
		/* html+='<td><label>截止起订量为空表示大于当前行起始起订量者,均使用当前行价格</label></td>'; */
		html+='<td><span><a href="javascript:void(0)" onClick="deletePriceRow(\''+trId+'\')">-删除</a></span></td>';
		html+='</tr>';
		
		$("#priceTable").append(html);  
		$("#priceTable").show();
	}

	function checkDecimal(strVal,validNum, savingNum){
		strVal = strVal + "";
		var len = strVal.length;
		var index = strVal.indexOf(".");
		if ( index != -1){
			if (len > (validNum +1) ){
				//warningMessage("数值长度过长");
				return 1;
			}
			var flen = strVal.substring(0,index).length;
			if (flen > (validNum - savingNum)){
				//warningMessage("数值长度过长");
				return 1;
			}
			var tlen = strVal.substring(index+1).length;
			if (tlen > savingNum){
				//warningMessage("小数点后位数过长");
				return 2;
			}
		}else{
			if (len > validNum - savingNum  ){
				//warningMessage("数值长度过长");
				return 1;
			}
		}
		return 0;
	}

	function checkRow(){
		var starts=$("input[alias='CbuyCountStart']"); 
		var ends=$("input[alias='CbuyCountEnd']"); 
		var prices=$("input[alias='Cprice']"); 
		var len = starts.length;
		if (len < 1){
			alert("请添加价格区间以维护区间价格");
			return false;
		}
		for (var i = 0; i < len ; i++){
			
			if (checkDecimal(starts[i].value, 8, 2) == 1 || checkDecimal(ends[i].value, 8, 2) == 1 ){
				alert("第" + (i+1) + "行的起订量输入有误, 数值长度过长,应输入8位有效数字(小数点后保留两位)");
				return false;
			}else if (checkDecimal(starts[i].value, 8, 2) == 2 || checkDecimal(ends[i].value, 8, 2) == 2 ){
				alert("第" + (i+1) + "行的起订量输入有误, 小数点后位数过长(小数点后保留两位)");
				return false;
			}
			if (checkDecimal(prices[i].value, 10, 2) == 1){
				alert("第" + (i+1) + "行的价格输入有误, 数值长度过长,应输入10位有效数字(小数点后保留两位)");
				return false;
			} else if (checkDecimal(prices[i].value, 10, 2) == 2){
				alert("第" + (i+1) + "行的价格输入有误, 小数点后位数过长(小数点后保留两位)");
				return false;
			}
			
			if ($.trim(prices[i].value) == ""){
				alert("第" + (i+1) + "行的数据输入有误, 价格不能为空");
				return false;
			}
			if (isNaN(prices[i].value) || isNaN(prices[i].value)){
				alert("第" + (i+1) + "行的数据输入有误, 价格必须为数值型");
				return false;
			}
			if (!(starts[i].value) || ( !(ends[i].value) && i != len-1 )){
				alert("第" + (i+1) + "行起订量不能为空");
				return false;
			}
			if (isNaN(starts[i].value) || isNaN(ends[i].value)){
				alert("第" + (i+1) + "行的数据输入有误, 起订量必须为数值型");
				return false;
			}
			if ((starts[i].value - ends[i].value)>=0 && $.trim(ends[i].value) != ""){
				alert("第" + (i+1) + "行的数据输入有误, 截止起订量必须大于起始起订量");
				return false;
			}
		}
		if (len > 1){
			for( var j=0; j < len-1; j++){
				if((ends[j].value - starts[j+1].value)>=0){
					alert("第" + (j+1) + "行以及第"+ (j+2)+ "行的数据输入有误, 第"+(j+2)+"行起始起订量必须大于第"+(j+1)+"行截止起订量");
					return false;
				}
			}
		}
		return true;
	}
	
	function fixNum(num){
		var numStr = num + "";
		var index = numStr.indexOf(".");
		if ( index != -1){
				var savingNum = numStr.substring(index+1).length;
				return (num * Math.pow(10, savingNum))/ Math.pow(10, savingNum)
		}
		return num;
	}
	</script>
</html>
