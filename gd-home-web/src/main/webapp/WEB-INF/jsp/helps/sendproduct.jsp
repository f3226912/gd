<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../pub/constants.inc" %>
	<%@ include file="../pub/head.inc" %>
	<%@ include file="../pub/tags.inc" %>
	<meta charset="UTF-8">
	<jsp:include page="/WEB-INF/jsp/pub/helps/title.jsp"></jsp:include>
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css"/>
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	
	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="main_menu check-nav">
			<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<!--head end-->
	
	<!--slide start-->
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop">
			<jsp:include page="/WEB-INF/jsp/pub/helps/leftnav.jsp"></jsp:include>
			<div class="fl">
				<div class="con-right products-con helps-cont">
					<h5 class="helps-right-tit">发布批发商品</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<p class="helps-txt">如何发布批发产品？</p>
					<p class="helps-txt">首先需要完成注册、通过完善店铺资料之后，才能发布批发产品信息。登陆谷登农批网进入我的谷登，点击发布新产品，按要求上传产品图片和填写相关产品信息，点击提交，审核通过后即可显示发布的产品信息，具体步骤如下：</p>
					<p class="helps-txt">登录谷登农批网首页可以发布产品，如下图：</p>
					<p class="helps-txt">第一步：进入发布产品新的页面，选择“产品类目”，产品类目必须定义到最小类目，如图所示；</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic6.png">
					<p class="helps-txt">第二步：点击“下一步填写信息详情”，带*号的为必填项；</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic7.png">
					<p class="helps-txt">选择价格为单价，可以输入农产品的单价价格</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic8.png">
					<p class="helps-txt">选择价格为价格区间，可以设置3个价格区间，比如1-5吨，每吨100元，如图；&nbsp;价格不填为面议；</p>
					<p class="helps-txt">第三步：点击“上传图片”，可以上传1张app-农商友产品图片和4张商铺产品图片。注意产品图片不能带有网址，水印和联系方式；</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic9.png">
					<p class="helps-txt">第四步：填写产品“宝贝描述”。请详细填写产品介绍、产品的功能特征优势、产品的种养殖条件、品牌故事等，同时可以插入详情图片，图片数量不限。图文并茂的形式越详细越好，一是方便买家详细了解您的产品，吸引买家购买，提升点击率，也能提升排名，二是也方便搜索引擎收录您的产品。故请认真、详细填写，图片尽量美观、清晰，多角度拍摄产品图片。</p>
					<p class="helps-txt">备注：宝贝描述中不能有除您公司官网以外的任一外链，不能出现广告信息，图片宽度750px。</p>
					<img src="${CONTEXT }v1.0/images/helps/help-pic10.png">
					<p class="helps-txt">第五步：填写完成，点击“同意协议条款，我要发布”，发布成功后等待审核；发布后您可以查看您发布的产品，也可以继续发布产品信息。</p>
					<p class="helps-txt">备注：无特殊情况，审核会工作日24小时内完成</p>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>


	<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>