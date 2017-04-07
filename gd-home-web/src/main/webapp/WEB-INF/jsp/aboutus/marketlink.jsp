<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>友情链接</title>
	<%@ include file="../pub/constants.inc"%>
	<%@ include file="../pub/head.inc"%>
	<%@ include file="../pub/tags.inc"%>
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/index-shop.css">
	
	<script src="${CONTEXT }v1.0/js/home.js"></script>
	
	<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
	<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>  
</head>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<!--nav start-->
	<!--head end-->

	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="nav_title"><span>友情链接</span>
				<div class="nav_title_show"></div>
			</div>
			<div class="main_menu">
				<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
			<!-- 二级分类 -->
		</div>
	</div>
	<!--head end-->
	
	
	<!--slide start-->
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop clearfix">
			<div class="mark-company">
				
				<div class="line-top-med">
					<h2 class="h-top-media">合作媒体</h2>
					<i class="i-bot-link"></i>
					<ul class="u-top-por">
						<c:forEach var="mediaLinks" items="${mediaLinksList}">
						<li>
							<a href="${mediaLinks.linkUrl }" target="_blank">
								<img src="${imgHostUrl}${mediaLinks.linkImage}" />
							</a>
						</li>
						</c:forEach>
					</ul>
				</div>
				<div class="line-top-med">
					<h2 class="h-top-media">友情链接</h2>
					<i class="i-bot-link"></i>
					<ul class="u-top-ink">
<!-- 						<li><a href="http://www.xgzgo.com " target="_blank">纯茶油</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.cnnclm.com ">家庭农场</a></li> -->
<!-- 						<li><a href="http://www.ruyinyuanlin.com/" target="_blank">江颜园林</a></li> -->
<!-- 						<li><a href="http://www.szkzs.com/" target="_blank">食品调料网</a></li> -->
<!-- 						<li><a href="http://www.wfgxsyxx.com" target="_blank">蔬菜大棚建设</a></li> -->
<!-- 						<li><a href="http://www.gjmmyy.com" target="_blank">花卉苗木</a></li> -->
<!-- 						<li><a href="http://www.c1-c1.com" target="_blank">银杏树</a></li> -->
<!-- 						<li><a href="http://www.hqnjl.cn" target="_blank">长兴农家乐</a></li> -->
<!-- 						<li><a href="http://www.lanhaizi.cn" target="_blank">蓝孩子农资招商网</a></li> -->
<!-- 						<li><a href="http://www.hbmmjd.com" target="_blank">河北苗木基地</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.yucmall.cn">粘虫板</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.xxhr.org">杀虫灯</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.mqybj.com">灭蚊器</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.yubangbang.com ">渔病医院</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.jinhunongye.com">铁皮石斛种植</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.boxing6.com">肉牛价格</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.shumiao.tw">苗木</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.hywjsljd.com">河阴石榴树苗</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.tianzhugui.net">天竺桂</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.hszhushu.com">广西竹鼠</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.zzt369.com">韭蛆特效药</a></li> -->
<!-- 						<li><a href="http://www.jlsjsm.com" target="_blank">长春蔬菜配送</a></li> -->
<!-- 						<li><a href="http://www.seed17.com" target="_blank">种子仪器</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.gsnjl.cn">长兴农家乐</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.b8gy.com">肉鸽养殖</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.51wyj.net">大樱桃苗</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.grgyedu.com">冬枣苗</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.jkyfs.com">进口鱼粉价格</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.35838.com">养猪网</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.ahcfyl.com">北美红栎小苗</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.nongyeguihua.com">休闲农业规划</a></li> -->
<!-- 						<li><a target="_blank" href="http://nongyao.1988.tv">农药价格</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.nykcw.com">农业快车网</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.inongjishu.com">农业技术网</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.ddlhmm.com">高杆红叶石楠</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.xiangcunmm.com">香椿苗</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.imiansha.com">色纱</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.guo52.com">板栗批发</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.xnxcjd.com">香椿树</a></li> -->
<!-- 						<li><a target="_blank" href="http://www.zgsrf.com">喜满地水溶肥</a></li> -->
						<c:forEach var="friendsLinks" items="${friendsLinksList}">
							<li><a href="${friendsLinks.linkUrl }" target="_blank">${friendsLinks.linkName }</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="line-top-med">
					<h2 class="h-top-media">申请友情链接</h2>
					<i class="i-bot-link"></i>
					<!-- <form name= "myform" method = 'post'  action ="${CONTEXT}about/applyFriendsUrl" onsubmit = "return checkSubmit();" >					
						<ul class="reg-list get-list mt-35">
							<li class="clearfix hauto">
								<div class="inp-tit">链接栏目：</div>
								<div class="fl">
									<select id="linkCate" name="linkCate" class="com-select get-select" >
										
									</select>
									<span class="f-c-adadad ml10"></span>
								</div>
							</li>
							<li class="clearfix hauto">
								<div class="inp-tit">链接类型：</div>
								<div class="fl">
									<select id="linkType" name="linkType" class="com-select get-select" >
																			
									</select>
									<span class="f-c-adadad ml10"></span>
								</div>
							</li>
							<li id="fontLi" class="clearfix">
								<div class="inp-tit">链接文字：</div>
								<div class="fl">
									<span class="bor mr01">
										<input id="linkWord" type="text" name="linkName" class="input-text mobileVerifyCode-input2"  >	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>
								<li class="clearfix">
								<div class="inp-tit">链接URL：</div>
								<div class="fl">
									<span class="bor mr01">
										<input id="linkUrl" type="text"  name="linkUrl" class="input-text mobileVerifyCode-input2" >	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>						
							<li class="clearfix">
								<div class="inp-tit">说明：</div>
								<div class="fl">
									<span class="bor mr01">
										<textarea class="right-contact-bri"  maxlength="499" name="description"> </textarea>	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>
							
							
							<li id="imgLi" class="clearfix mt-35" >
								<div class="inp-tit"><em class="rse-pro-tit-red">*</em>上传图片：</div>
								<div class="fl">
									<div class="clearfix" id="uploadContainer">
									<input type="file" class="g-u input01" id="upload_btn_link" value="" name="linkImage" />
	            					<input type="hidden" id="J_Urls_Link" name="linkImagehi" />
	            
			        				</div>
							        <div class="form-context-u">
							           <ul id="picture_queen_link" class="multPic-box"></ul>
										
							        </div>
						        </div>
							</li>
					
							<li class="clearfix mt-35">
								<div class="inp-tit">&nbsp;</div>
							
									<div class="fl">
										 <input type="submit" class="btn-red-dia btn-next" value="提交"> 
									</div>
								
							</li>
							
						</ul>
					</form> -->


					<div class="tp20-cen-ion">	
						<p class="tp20-cen-ptn">申请步骤：</p>
						<p class="tp20-cen-ptn">1、请先在贵网站做好谷登农批网的文字友情链接，链接文字：<strong class="stg-gre">谷登农批网</strong>；链接地址：<strong class="stg-gre">http://www.gdeng.cn</strong></p>
						<p class="tp20-cen-ptn">2、做好链接后，请加<strong class="stg-gre">QQ：330671259</strong>与我们取得联系。</p>
						<p class="tp20-cen-ptn">3、已经开通我站友情链接且内容健康，符合本站友情链接要求的网站，经谷登农批网管理员审核后，可以显示在此友情链接页面。</p>
					</div>
				</div>
				
		    </div>
			<div class="cl"></div>
		</div>
	</div>



<!--底部 star-->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!--底部 end-->
	<script type="text/javascript" src="${CONTEXT}js/formValidatorRegex.js"></script>
	<script>
	
	var appUrl = CONTEXT+'about/uploadProductPic';
	initUploadSignle("upload_btn_link",appUrl, "picture_queen_link", "J_Urls_Link");

	/**单图上传**/
	function initUploadSignle(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr){
		var config_mutiple = {
		        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
		        uploadBtn : uploadBtn,//初始的button
		        queueId : queueId,//图片装载容器id
		        JUrls : JUrls,//成功后返回url参数写入id
		        action : uploadUrl ,//上传地址
		        maxSize:2048,
		        imgUrlPrefix : '${imgHostUrl}',
		        max : pictureCountLimit,//允许上传个数
		        type :"ajax",//上传类型
		        filesStr : filesStr,//此处是附加参数，已经上传需要第二次修的图片参数
		};
		gdKissySingleUploader.init(config_mutiple ,function(uploader){
		     var filesStr = config_mutiple.filesStr;
		     var certification = config_mutiple.certification;
		     if(filesStr != ''&&filesStr!=undefined){
		        //var fileList = eval("("+  filesStr + ")");
		        
		        gdKissySingleUploader.addFiles(filesStr,uploader);
		        
		        if(certification == 0){
		                $("#"+JUrls).parent().find(".file-input").attr("disabled","disabled");
		        }else{
		        	/**可以修改时注册删除事件**/
		        	$(".form-context").delegate(".imageUploader_del","click",function(){
					    $(this).parents("li").get(0).remove();
					    var originalUrl = $(this).parent().siblings().find("a img").attr("data-original-url");
					    $("input[name*='"+JUrls+"']").each(function(){
					        if($(this).val()==originalUrl){
					            $(this).val("");
					        }
					    });
					});
		        }
		        
		     }
		},function(url){
		});
	}
	
	function checkSubmit(){
		if($("#linkCate").val()==0){
			layer.msg('请选择链接栏目',{cusClass:' sss'});
			return false;
		}
		if($("#linkCate").val()==1){
			if($("#linkWord").val().trim()==""){
				layer.msg('链接文字不能为空',{cusClass:' sss'});
				return false;
			}
		}
		if($("#linkCate").val()==2){
			if($("#linkType").val()==3){
				if($("#linkWord").val().trim()==""){
					layer.msg('链接文字不能为空',{cusClass:' sss'});
					return false;
				}
			}
			
			if($("#J_Urls_Link").val().trim()==""){
				layer.msg('请上传图片',{cusClass:' sss'});
				return false;
			}
		}
		if($("#linkUrl").val().trim()==""){
			layer.msg('链接URL不能为空',{cusClass:' sss'});
			return false;
		}else{
			   var strRegex =regexEnum.url2;        
 		  	var reg=new RegExp(strRegex);        
			var linkUrl =$("#linkUrl").val().trim();
			if(!reg.test(linkUrl)){
				layer.msg('请输入正确的URL链接',{cusClass:' sss'});
				return false;
			}
		}
	}
	$("#linkCate").append("<option value='0'>请选择</option>"); 
	$("#linkCate").append("<option value='1' >友情链接</option>"); 
	$("#linkCate").append("<option value='2' >合作媒体</option>"); 
	 $("#imgLi").animate({ height: 'show', opacity: 'hide' }); 
	$("#linkCate").change(function(){
		$("#linkType").empty();
		if($("#linkCate option:selected").val()==1){
			 $("#imgLi").animate({ height: 'hide', opacity: 'hide' }, 'slow');
			 $("#fontLi").animate({ height: 'show', opacity: 'show' }, 'slow'); 
			 $("#linkType").append("<option value='1'>文字</option>"); 
		}
		if($("#linkCate option:selected").val()==2){
			 $("#fontLi").animate({ height: 'hide', opacity: 'hide' }, 'slow');
			 $("#imgLi").animate({ height: 'show', opacity: 'show' }, 'slow');  
			 $("#linkType").append("<option value='2'>图片</option>"); 
			 $("#linkType").append("<option value='3'>文字加图片</option>"); 
		}	
	});
	$("#linkType").change(function(){
		if($("#linkType option:selected").val()==2){
			 $("#fontLi").animate({ height: 'hide', opacity: 'hide' }, 'slow');
			 $("#imgLi").animate({ height: 'show', opacity: 'show' }, 'slow');  
		}	
		if($("#linkType option:selected").val()==3){
			$("#fontLi").animate({ height: 'show', opacity: 'show' }, 'slow'); 
		}
	});
	
	</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>