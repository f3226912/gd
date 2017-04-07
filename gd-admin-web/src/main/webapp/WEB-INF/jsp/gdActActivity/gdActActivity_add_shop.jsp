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
<div id="shop_buyer">
	<div id="top" style="display:none;">可选<span id="marker_name"></span>商铺 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" placeholder="模糊搜索">&nbsp;&nbsp;<input id="highsearch" type="button" value="高级搜索">
	</div>
	<div id="top1">
	 商铺名称：<input  type="text" id="shopsName" name="shopsName" style="width:150px" >&nbsp;&nbsp; 
	 手机号码：<input type="text" name="mobile" id="mobile"/>&nbsp;&nbsp;
	<!--  用户类型:<select name="level" id="level"  >
				<option value="">-全部-</option>
				<option value="1" >农批商</option>
				<option value="4" >产地供应商</option>
			</select>
	 <br/>	 -->
	 主营分类:
	 <select name="mainCategoryId" id="mainCategoryId" labelPosition="top" 
		style="width: 150px">
		 <option value="">--全部--</option>
	</select>
	<br/>
	 所属市场：<span id="marker_name1"></span>
	&nbsp;&nbsp;
	<!-- 认证状态:
	<select name="offlineStatus" id="offlineStatus" >
		<option value="">-请选择-</option>
		<option value="1">已认证</option>
		<option value="2">未认证</option>
	 </select>	 -->
	 <input type="button" onclick="loadshoplist();" value="查询">
	 </div>
	<div id="left">
	  <div id="showshop_table">
	  </div>
	</div>
	<div id="middle">
	    <input id="addOne" onclick="addone()" value="添加>" type="button"><br/><br/>
	     <input id="removeOne" onclick="removeone()" value="<移除" type="button"><br/><br/>
		<input id="addAll" onclick="addall()" value="全部添加>>" type="button"><br/><br/>
		<input id="removeAll" onclick="removeall()" type="button"  value="<<全部删除">
	</div>
	<div id="right">
	  <select id="shopselect" multiple='multiple' style="width: 200px;height:340px;">
	  </select>
	</div>
</div>
<script type="text/javascript">
var marker_name= $("#market_type option:selected").text();
$("#marker_name").text(marker_name);
$("#marker_name1").text(marker_name);

function initCateShop() {
	url = CONTEXT + 'product/listTopProductCategory/' + $("#market_type").val();
	
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: "{}",
        dataType: 'json',
        success: function(data) {
        	
        	$("#shop_buyer #mainCategoryId").find("option").remove();
       		$("#shop_buyer #mainCategoryId").append("<option value='-1'>--全部--</option>");
            for(var i = 0; i < data.length; i++) {
            	$("#shop_buyer #mainCategoryId").append("<option value='"+data[i].categoryId+"'>"+data[i].cateName+"</option>");
            }
        }
    });
}

</script>
