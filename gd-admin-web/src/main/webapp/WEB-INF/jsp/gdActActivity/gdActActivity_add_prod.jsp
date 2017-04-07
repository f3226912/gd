<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<style type="text/css">
div#top{;width:100%;height:50px;border:1px solid grey;background-color:#F0F0F0;}
div#top1{height:100px;border:1px solid grey;background-color:#F0F0F0; padding: 10px; line-height: 36px;}
div#left{;width:400px;height:340px;border:1px solid grey;float:left;}
div#middle{width:100px;height:200px;float:left;padding:30px;}
div#right{width:200px;height:340px;border:1px solid grey;float:right;}

.tab{border-top:1px solid #000;border-left:1px solid #000;text-align:center}
.tab td{border-bottom:1px solid #000;border-right:1px solid #000;}
.tab th{border-bottom:1px solid #000;border-right:1px solid #000;}
</style>
<div id="prod_buyer">
	<div id="top" style="display:none;">可选<span id="product_name"></span>商品 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" placeholder="模糊搜索">&nbsp;&nbsp;<input id="highsearch" type="button" value="高级搜索">
	</div>
	<div id="top1">
	 商品名称：<input  type="text" id="name" name="name" style="width:150px" >&nbsp;&nbsp; 
	 手机号码：<input type="text" name="mobile" id="mobile"/>&nbsp;&nbsp;
	 <!-- 用户类型:<select name="level" id="level"  >
				<option value="">-全部-</option>
				<option value="1" >农批商</option>
				<option value="4" >产地供应商</option>
			</select>
	 <br/>	 -->
	 所属市场：<span id="marker_name1"></span>&nbsp;&nbsp;
	<br/>
	商品类目：
	<span id="categorySearchPanel">
		<select id="cate1" onchange="cateProdChange(1)">
			<option value="-1">-请选择-</option>
		</select>
		<select id="cate2" onchange="cateProdChange(2)">
			<option value="-1">-请选择-</option>
		</select>
		<select id="cate3" onchange="cateProdChange(3)">
			<option value="-1">-请选择-</option>
		</select>
		<input type="hidden" id="categoryId_add" name="categoryId">
	</span>
	&nbsp;&nbsp;
	 商铺名称:<input type="text" name="shopsName" id="shopsName"/>
	 <input type="button" onclick="loadprodlist()" value="查询">
	 </div>
	<div id="left">
	  <div id="showprod_table">
	  </div>
	</div>
	<div id="middle">
	    <input id="addOne" onclick="addprodone()" value="添加>" type="button"><br/><br/>
	     <input id="removeOne" onclick="removeprodone()" value="<移除" type="button"><br/><br/>
		<input id="addAll" onclick="addprodall()" value="全部添加>>" type="button"><br/><br/>
		<input id="removeAll" onclick="removeprodall()" type="button"  value="<<全部删除">
	</div>
	<div id="right">
	  <select id="prodselect" multiple='multiple' style="width: 200px;height:340px;">
	  </select>
	</div>
</div>
<script type="text/javascript">
var marker_name= $("#market_type option:selected").text();
$("#marker_name").text(marker_name);
$("#marker_name1").text(marker_name);

function initProdCategory() {
	cateProdChange(0);
}

function cateProdChange(index) {
	debugger;
	var url = "";
	var val;
	
	if(index!=0) {
		val = $("#categorySearchPanel #cate"+index).val();
		$('#categorySearchPanel #categoryId_add').val(val);
	}
	
	if(index==0) {
		url = CONTEXT + 'product/listTopProductCategory/' + $("#market_type").val();
	} else if(index==1) {
		url = CONTEXT+'product/getChildProductCategory/' + val;
	} else if(index==2) {
		url = CONTEXT+'product/getChildProductCategory/' + val;
	}
	
	if(index<3) {
		$.ajax({
            type: "POST",
            contentType: "application/json",
            url: url,
            data: "{}",
            dataType: 'json',
            success: function(data) {
            	
            	$("#categorySearchPanel #cate"+(index+1)).find("option").remove();
           		$("#categorySearchPanel #cate"+(index+1)).append("<option value='-1'>请选择</option>");
           		
                for(var i = 0; i < data.length; i++) {
                	$("#categorySearchPanel #cate"+(index+1)).append("<option value='"+data[i].categoryId+"'>"+data[i].cateName+"</option>");
                }

        		//如果未0 则表示初始化需要加载二级
        		if(index==0) {
        			$.ajax({
        	            type: "POST",
        	            contentType: "application/json",
        	            url: CONTEXT+'product/getChildProductCategory/' + $("#categorySearchPanel #cate1").val(),
        	            data: "{}",
        	            dataType: 'json',
        	            success: function(data) {
        	            	$("#categorySearchPanel #cate"+(2)).find("option").remove();
        	            	$("#categorySearchPanel #cate"+(2)).append("<option value='-1'>请选择</option>");
        	            	
        	                for(var i = 0; i < data.length; i++) {
        	                	$("#categorySearchPanel #cate"+(2)).append("<option value='"+data[i].categoryId+"'>"+data[i].cateName+"</option>");
        	                }

        	        		var val = $("#categorySearchPanel #cate1").val();
        	        		$('#categorySearchPanel #categoryId_add').val(val);
        	            }
        			});
        		}
            }
        });
	}
}


</script>
