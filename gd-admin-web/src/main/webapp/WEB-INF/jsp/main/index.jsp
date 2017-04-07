<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<%@ include file="../pub/head.inc" %>
	<link href="${CONTEXT }images/favicon.ico" type="image/x-icon" rel="icon" /> 
	<link rel="stylesheet" type="text/css" href="${CONTEXT}css/reset.css"/>
	<script type="text/javascript" src="${CONTEXT }js/gdadmin.js"></script>
	<script type="text/javascript" src="${CONTEXT }js/jquery.cookie.js"></script>
	<title>${sysAllName }</title>
	<script>
	javascript:window.history.forward(1);
	var my_tabs;
	var my_tabsMenu;
	var my_layout;
	$(function() {
		$(".menu-lv1").parent().siblings("ul").hide();
		$(".menu-lv1").click(function(ev){
				var $self = $(this),
					$content = $self.parent().siblings("ul");
				$content.slideToggle(function(){
					var stHeight = $content.height(),
						fixHeight = $self.offset().top+$self.height()+72;
					if($content.is(":visible")){
						if(!isInViewport($content[0])||$content[0].getBoundingClientRect().bottom<0){
							//$("#my_layout").scrollTop(stHeight+$("#my_layout").scrollTop())
							$("#my_layout").animate({scrollTop:stHeight+$("#my_layout").scrollTop()})
							
						}
					}
					/* $('#menuContainer').layout('panel', 'west').panel('resize',{height:$('#menuContainer').height()});
					$('#my_layout').layout('resize'); */
					
				});
		});
		//初始化全局的layout属性
		my_layout = $('#my_layout').layout({
			fit:true
		});
		//tab初始化
		var wH=$(window).outerHeight()-72;
		var menuH=document.getElementById("menuContainer").offsetHeight;
		if(menuH>wH){
			wH=menuH;
		}
		my_tabs = $('#my_tabs').tabs({
			height:wH,
			width:$(window).outerWidth()-225,
			border : false,
			onContextMenu : function(e, title) {
				e.preventDefault();
				my_tabsMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data('tabTitle', title);
			},
			onSelect:function(title,index){
				$("#xlc_menu .menu-lv1").removeClass("c on");
				$("#xlc_menu .menu-lv1>span").each(function(i){
					if($(this).html()==title){
						$("#xlc_menu .menu-lv1").eq(i).addClass("c on");
					}
				});
			}
		});
		//tab右键菜单
		my_tabsMenu = $('#my_tabsMenu').menu({
			hideOnUnhover:false,
			onClick : function(item) {
				var curTabTitle = $(this).data('tabTitle');
				var type = $(item.target).attr('title');
				if (type === 'refresh') {
					my_tabs.tabs('getTab', curTabTitle).panel('refresh');
					return;
				}
				if (type === 'close') {
					var t = my_tabs.tabs('getTab', curTabTitle);
					if (t.panel('options').closable) {
						my_tabs.tabs('close', curTabTitle);
					}
					return;
				}
				var allTabs = my_tabs.tabs('tabs');
				var closeTabsTitle = [];
				jQuery.each(allTabs, function() {
					var opt = $(this).panel('options');
					if (opt.closable && opt.title != curTabTitle && type === 'closeOther') {
						closeTabsTitle.push(opt.title);
					} else if (opt.closable && type === 'closeAll') {
						closeTabsTitle.push(opt.title);
					}
				});
				for ( var i = 0; i < closeTabsTitle.length; i++) {
					my_tabs.tabs('close', closeTabsTitle[i]);
				}
			}
		});		
	});
	
	function isInViewport(el) {
	    var rect = el.getBoundingClientRect();

	    return rect.bottom > 0 &&
	        rect.right > 0 &&
	        rect.left < (window.innerWidth || document. documentElement.clientWidth) &&
	        rect.top < (window.innerHeight || document. documentElement.clientHeight);
	}
	//修改密码
	function editPassword(){
		$('#headerEditDialog').dialog({'title':'修改密码','href':'${CONTEXT}sysmgr/sysRegisterUser/updateUserPWDInit'}).dialog('open');
	}
	
	//验证URL
	function checkUrl(actionUrl,title){
		if(actionUrl!=""&&actionUrl!="#"){
			//验证是否有URL权限
// 			 $.post("${CONTEXT}checkUrl",{"actionUrl":actionUrl},function(data){
// 				 if(data=="success"){
					//打开tab页面			
					openTab(actionUrl,title);
// 				 }else if(data=="error"){
// 					 warningMessage("你没有访问权限哟！");
// 				 }else{
// 					 var backUrl=$.cookie('sys_type_cookie');
// 					 if(backUrl==""||backUrl==null){
// 						 backUrl="";
// 					 }
// 					 parent.$.messager.alert("操作提示","登录超时，请重新登录！","warning",function(){window.location.href=CONTEXT+backUrl;});
// 				 }
// 			 });
		}else{
			//warningMessage("正在建设中哟...");
		}
	}
	</script>
    </head>
    <body  class="easyui-layout">
    <div id="my_layout" class=" <c:if test="${sysType eq 'purchase' }">ska</c:if><c:if test="${sysType eq 'school' }">skc</c:if><c:if test="${sysType eq 'base' }">skb</c:if>" >
        <div data-options="region:'north',border:false" class="header <c:if test="${sysType eq 'purchase' }" >hd1</c:if>">
        <%@ include file="header.jsp" %>
        </div>
        <!-- <div class="container"> -->
        	<div  region="west" split="false" class="menu-main" id="menuContainer">
    			<div class="menu-title" style="	text-align: left;padding-left: 15px;">谷登运营管理</div>
				<div class="menu-list">
					<ul style="border-right: 1px solid #ddd;">
					<c:forEach items="${index.menuList }" var="menu" varStatus="status">
						<c:if test="${empty menu.menuModuleID }">
	                        <li class="menu-lv1-con">
		                        <div><a class="menu-lv1" href="javascript:checkUrl('${menu.actionUrl }','${menu.menuName }','${menu.menuID }')">${menu.menuName }</a></div>
		                        <ul>
		                        <c:forEach items="${menu.children }" var="subMenu" varStatus="subStatus">
		                        	<li>
		                        		<div><a class="menu-lv2" href="javascript:checkUrl('${subMenu.actionUrl }','${subMenu.menuName }','${subMenu.menuID }')"><span class="switch-btn"></span>${subMenu.menuName }</a></div>
		                        		<c:if test="${!empty subMenu.children }">
		                        		<ul class="menu-lv3" style="display:none">
		                        			<c:forEach items="${subMenu.children }" var="sndMenu" varStatus="sndStatus">
		                        			<li>
				                				<a class="" style="text-decoration: none;" href="javascript:checkUrl('${sndMenu.actionUrl }','${sndMenu.menuName }','${sndMenu.menuID }')">
				                				<c:if test="${!sndStatus.last}">
				                					<span class="switch-btn switch-docu"></span>
				                				</c:if>
				                				<c:if test="${sndStatus.last}">
				                					<span class="switch-btn switch-bottom-docu"></span>
				                				</c:if>
				                				${sndMenu.menuName }
				                				</a>
		                        			</li>
		                        			</c:forEach>
		                        		</ul>
		                        		</c:if>
	                        		</li>
	                        	</c:forEach>
	                        	</ul>
						</c:if>
					</c:forEach>
					</ul>
				</div>
			</div>
            <!-- 主页 -->
            <div data-options="region:'center'" class="main">
            	<div style="">
            		<div id="my_tabs" style="overflow: hidden;">
						<div title="谷登运营后台管理" data-options="border:false" class="index_bg" >
							<!-- 默认打开第一个菜单 -->
<!-- 							<iframe id="firstIframe" src="../index.html" frameborder="0" style="border:0;width:100%;height:100%;"></iframe> -->
							<div align="center">
<%-- 								<jsp:include page="wel.jsp"></jsp:include> --%>
								<img src="${CONTEXT }images/333_03.jpg" style="height: 500px;"/>
							</div>
						</div>
					</div>
            	</div>
            </div>
            <div style="clear:both"></div>
            <div class="footer-bottom"  data-options="region:'south',border:false">
			    <div class="wrap-1200 clearfix">            
			        <div class="f-copyright">
			            <p><a target="_blank" href="http://www.miitbeian.gov.cn">粤ICP备15098440号</a> 版权所有©深圳谷登科技有限公司</p>
			        </div>
			    </div>
			</div>
        <!-- </div> -->
              
    </div>
    
        
    <div id="my_tabsMenu" style="width: 120px;margin-top:0px !important;min-height: 88px !important;height:88px !important;display:none;">
		<div data-options="iconCls:'icon-reload'" title="refresh">刷新</div>
		<div data-options="iconCls:'icon-del'" title="close">关闭</div>
		<div data-options="iconCls:'icon-del'" title="closeOther">关闭其他</div>
		<div data-options="iconCls:'icon-del'" title="closeAll">关闭所有</div>
	</div>
	<div id="headerEditDialog"class="easyui-dialog" style="width:380px;height:200px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
		<div id="dlg-buttonsEdit" style="text-align:center">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveEditPwd()">保存</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#headerEditDialog').dialog('close')">关闭</a>
	    </div>
	</div>
	<script>
	$(function(){
		$(".menu-lv1-con").each(function(index){
			var $self = $(this),
				$selis =  $self.children().children("li");
			$selis.each(function(indexLi){
				//var $togglebtn = $selis.eq(indexLi).find(".switch-btn");
				var $selfli = $(this),
					$level3 = $selfli.find("ul:first"),
					$switchBtn = $selfli.find(".switch-btn:first");
				if($selfli.index() == $selis.length-1){
					$switchBtn.addClass("switch-bottom")
				}
				if($level3.length>0){
					if($selfli.index() == $selis.length-1){
						if($level3.css("display") == "block"){
							$switchBtn.addClass("switch-bottom-open");
						}else{
							$switchBtn.addClass("switch-bottom-close");
						}
						
					}else{
						if($level3.css("display") == "block"){
							$switchBtn.addClass("switch-open");
						}else{
							$switchBtn.addClass("switch-close");
						}
						$selfli.find(".menu-lv3").addClass("menu-line2")
					}
					
				}
				else{
					if($selfli.index() == $selis.length-1){
						$switchBtn.addClass("switch-bottom-docu")
					}else{
						$switchBtn.addClass("switch-docu")
					}					
				}
				$selfli.find("div").click(function(){					
					$level3.slideToggle(function(){
						if($level3.is(":visible")){
							if($switchBtn.hasClass("switch-bottom")){
								$switchBtn.addClass("switch-bottom-open").removeClass("switch-bottom-close")
							}else{
								$switchBtn.addClass("switch-open").removeClass("switch-close")
							}
						}else{
							if($switchBtn.hasClass("switch-bottom")){
								$switchBtn.removeClass("switch-bottom-open").addClass("switch-bottom-close")
							}else{
								$switchBtn.removeClass("switch-open").addClass("switch-close")
							}
						}
					})
				})
			});
		});
	})
	</script>
</body>
</html>