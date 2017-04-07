<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<%@ include file="../../pub/constants.inc" %>
	<%@ include file="../../pub/head.inc" %>
	<%@ include file="../../pub/tags.inc" %>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />

</head>

<body>
   <jsp:include page="../userCenter_head.jsp" flush="true"/> 
   <jsp:include page="../userCenter_left.jsp" flush="true"/> 

		<div class="mid-right">
			<h1 class="mid-right-store">发布批发产品</h1>
			<input type="hidden" id="categoryId" name="categoryId" value="${categoryId}" >
			<input type="hidden" id="productId" name="productId" value="${productId}" >
			<c:forEach var="item" items ="${categoryLevels}">
				<input type="hidden" name="categoryLevels" value="${item.categoryId}" cateName="${item.cateName}">
			</c:forEach>
			<div id="tab">
				<ul class="tab_menu">
			    	<li class="selected">农产品</li>
			        <!-- <li>您经常使用的类目</li> -->
			    </ul>
			<div class="tab-box-right">如有疑问，请<a class="tab-box-a1" href="${CONTEXT }/service/index">联系客服</a></div>
			    <div class="tab_box">
			    	<div class="tab_box-cet">
					<div class="browse-category fd-clr">
			        <!-- 类目筛选 unit-->
			        	<div class="category-select-list">
			            	<ul id="cate0" class="category-select" style="display: block;">  
			            		<c:forEach var="item"   items="${firstList}"   varStatus="i">
							    	<li>
					                	<a onclick="getChildCategorys(this,'${item.curLevel}')" target="_self" href="javascript:void(0);" id="${item.categoryId}" cateName="${item.cateName}">
					                	<!-- <i class="category-select-ioc"></i> -->${item.cateName}
					                	</a>
				                	</li>
						    	</c:forEach>
			            	</ul>
			            <ul id="cate1" class="category-select" style="display: block;">
			            	<c:if test="${secondList != null && !empty secondList}">
			            		<c:forEach var="item"   items="${secondList}"   varStatus="i">
							    	<li>
					                	<a onclick="getChildCategorys(this,'${item.curLevel}')" target="_self" href="javascript:void(0);" id="${item.categoryId}" pid="${item.parentId}" cateName="${item.cateName}">
					                	<!-- <i class="category-select-ioc"></i> -->${item.cateName}
					                	</a>
				                	</li>
						    	</c:forEach>
			            	</c:if>
			            	
			            </ul>
			            <ul id="cate2" class="category-select" style="display: block;">
			            	<c:if test="${thirdList != null && !empty thirdList}">
			            		<c:forEach var="item"   items="${thirdList}"   varStatus="i">
							    	<li>
					                	<a onclick="getChildCategorys(this,'${item.curLevel}')" target="_self" href="javascript:void(0);" id="${item.categoryId}" pid="${item.parentId}" cateName="${item.cateName}">
					                	<!-- <i class="category-select-ioc"></i> -->${item.cateName}
					                	</a>
				                	</li>
						    	</c:forEach>
			            	</c:if>
			            </ul>
			            <ul id="cate3" class="category-select last" style="display: block;">
			            	<c:if test="${fourthList != null && !empty fourthList}">
			            		<c:forEach var="item"   items="${fourthList}"   varStatus="i">
							    	<li>
					                	<a onclick="getChildCategorys(this,'${item.curLevel}')" target="_self" href="javascript:void(0);" id="${item.categoryId}" pid="${item.parentId}" cateName="${item.cateName}">
					                	<!-- <i class="category-select-ioc"></i> -->${item.cateName}
					                	</a>
				                	</li>
						    	</c:forEach>
			            	</c:if>
			            </ul>
			       	 </div>
    				</div>
					<div class="dq-opt"><em class="dq-opt-em"></em><span class="dq-opt-spa"></span>
					<strong class="dq-opt-st">您当前选择的是：</strong>
					<span class="cat-lev-box">
						<!-- <span class="cat-lev1"></span><span class="cat-lev2"></span><span class="cat-lev3"></span> -->
					</span></div>
					
					<div class="clear"></div>
					<div class="dq-opt-next"><button class="dq-opt-next-btn" onclick="toEditProductInfo()">下一步填写信息详情</button></div>
			    	</div>

			        <div class="tab_box-cet hide">
			        	<div class="tab-current"><strong>您当前选择的是：</strong></div>
			        	<div class="tab-current-tion">
			        		<ul class="tab-current-tion-pro">
			        			<li class="tab-rurrent-selected"><a href="#">冻品》肉类》猪肉</a></li>
			        			<li><a href="#">蔬菜》进口蔬菜》白菜</a></li>
			        			<li><a href="#">水果》进口水果》香蕉</a></li>
			        		</ul>
			        	</div>
			        	<div class="tab-current-tion-fill"><a  class="t-fill" href="#">下一步填写信息详情</a></div>
			        </div>

			    </div>
			</div>			
		</div>
	<div class="clear"></div>
	</div>
	
	<!--简版底部 star-->
	
	<!--简版底部 end-->

<script type="text/javascript">

	function generateInitPromt(){
		var categorys = document.getElementsByName("categoryLevels");
		var len = categorys.length ;
		var message = "" ;
		for(var i = 0 ; i < len; i++){
			if (i == 0){
				message += '<span class="cat-lev'+ (i+1) + ' "> '+ $(categorys[i]).attr("cateName") +'</span>' ;
			}else {
				message += '<span class="cat-lev'+ (i+1) + ' "> 》》'+ $(categorys[i]).attr("cateName") +'</span>' ;
			}
		}
		$(".cat-lev-box").html(message); 
	}


	function initCategoryClass(){
		var roleType = "${roleType}";
		var self = $("#cate2 #${categoryId}");
		var parent = $("#cate1 #" + self.attr("pid"));
		var grantParent = $("#cate0 #" + parent.attr("pid"));
		grantParent.addClass("current");
		parent.addClass("current");
		self.addClass("current");
/* 		if (roleType == 4){
			var self = $("#cate1 #${categoryId}");
			var parent = $("#cate0 #" + self.attr("pid"));
			parent.addClass("current");
			self.addClass("current");
		}else {
			var self = $("#cate2 #${categoryId}");
			var parent = $("#cate1 #" + self.attr("pid"));
			var grantParent = $("#cate0 #" + parent.attr("pid"));
			grantParent.addClass("current");
			parent.addClass("current");
			self.addClass("current");
		} */
	}
	
	$(document).ready(function(){
		generateInitPromt();
		initCategoryClass();
/* 		var $tab_li = $('#tab ul li');
		$tab_li.click(function(){
		$(this).addClass('selected').siblings().removeClass('selected');
		var index = $tab_li.index(this);
		$('div.tab_box > .tab_box-cet').eq(index).show().siblings().hide();
		});	 */
	});

	function toEditProductInfo(){
		var categoryId = $("#categoryId").val();
		if (!categoryId){
			alert("请选择产品分类");
			return ;
		}
		window.location.href = CONTEXT+'userCenter/product/toEdit/' + $("#productId").val() + "-" + categoryId;
	}
	function getPrompt(current_a, level){
		var message;
		if (level == 0){
			message = '<span class="cat-lev'+ level + ' "> '+ current_a.attr("cateName") +'</span>' ;
		}else {
			message = '<span class="cat-lev'+ level + ' "> 》》'+ current_a.attr("cateName") +'</span>' ;
		}
		if (level != 0) {
			//ul
			var prev_ul = $(current_a).parent().parent().prev();
			message = getMessage(prev_ul, current_a.attr("pid"), level) + message;
		}
		return message;
	}
	function getMessage(parent_ul, pid, level){
		var message = "";
		level = parseInt(level)-1 ;
		parent_ul.children("li").each(function(i){
			var self_li_a = $(this).children("a");
			if (self_li_a.attr("id") ==  pid){
				//self_li_a.addClass("current");
				//递归
				message = getPrompt(self_li_a, level) ;
				//仅仅跳出each, 还没跳出getMessage
				return message;
			}
		});
		return message;
	}
       //单击产品分类
       function getChildCategorys(obj,level) {
           var value = $(obj).attr('id');
           var currentCategory = $("#cate"+level+" li a");
           //清空之前的选择
           currentCategory.live("click",function(event){
               currentCategory.removeClass("current");
               $(this).addClass("current");
               var message = getPrompt($(this), level);
               //alert("last " +message);
               $(".cat-lev-box").html(message);                
               event.preventDefault();
               return false;
           });
           
           cleanChildSelect(obj);
           var roleType = "${roleType}";
           //获取下一级UL 
           var next = $(obj).parent().parent().next();
           var nextlevel = parseInt(level)+1;
           if(nextlevel<=2){
        	   getChild(next, nextlevel, value);
               
           }else{
               //设置选择的产品分类
               $("#categoryId").val($(obj).attr('id'));
           }
           
/*            if (roleType == 4){
        	   if(nextlevel<=1){
	        	   getChild(next, nextlevel, value);
	           }else{
	               //设置选择的产品分类
	               $("#categoryId").val($(obj).attr('id'));
	           }
           }else{
	           if(nextlevel<=2){
	        	   getChild(next, nextlevel, value);
	               
	           }else{
	               //设置选择的产品分类
	               $("#categoryId").val($(obj).attr('id'));
	           }
           } */
           
           
/*            if(nextlevel<=2){
               
               $.ajax({
                   type: "GET",
                   url: "${CONTEXT }userCenter/product/getChildProductCategory/"+ value,
                   dataType: "json",
                   success: function(data) {
                   	
                               if (data.length != 0) {
                                   for ( var n = 0; n < data.length; n++) {
                                       $(next).append(
                                                       "<li><a onclick=getChildCategorys(this,'"+nextlevel+"') id='"+data[n].categoryId+"' cateName='"+data[n].cateName+"' pid='"+value+"'>"
                                                               + data[n].cateName
                                                               + "</a></li>");
                                   }
                           }
                       }
                   }
               );
           }else{
           	var grandParentId = $("#cate0 li .current").attr('id');
           	var parentId = $("#cate1 li .current").attr('id');
           	var selfId = $(obj).attr('id');
               $("#cateId0").val($("#cate0 li .current").attr('id'));
               $("#cateId1").val($("#cate1 li .current").attr('id'));
               $("#cateId2").val($(obj).attr('id'));
               //设置选择的产品分类
               $("#categoryId").val($(obj).attr('id'));
           } */
       }
       
       function getChild(next, level, cateId){
    	   $.ajax({
               type: "GET",
               url: "${CONTEXT }userCenter/product/getChildProductCategory/"+ cateId,
               dataType: "json",
               success: function(data) {
               	
                           if (data.length != 0) {
                               for ( var n = 0; n < data.length; n++) {
                                   $(next).append(
                                                   "<li><a onclick=getChildCategorys(this,'"+level+"') id='"+data[n].categoryId+"' cateName='"+data[n].cateName+"' pid='"+cateId+"'>"
                                                           + data[n].cateName
                                                           + "</a></li>");
                               }
                       }
                   }
               });
       }
       
       //重置产品分类
       function cleanChildSelect(obj) {
           var next = $(obj).parent().parent().next();
           if (next.length != 0) {
               $(next).find("li").remove();
               $(next).next().find("li").remove();
               //$(next).next().next().find("li").remove();
           }
       }
       
       

</script>
</html>
