<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/head.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="${CONTEXT }images/favicon.ico" type="image/x-icon" rel="icon" /> 
	<title>谷登运营后台</title>
	<script>
        	var my_tabs;
			var my_tabsMenu;
			var my_layout;
			$(function() {
				//初始化全局的layout属性
				my_layout = $('#my_layout').layout({
					fit:true
				});
				//tab初始化
				my_tabs = $('#my_tabs').tabs({
					fit : true,
					border : false,
					onContextMenu : function(e, title) {
						e.preventDefault();
						my_tabsMenu.menu('show', {
							left : e.pageX,
							top : e.pageY
						}).data('tabTitle', title);
					}
				});
				//tab右键菜单
				my_tabsMenu = $('#my_tabsMenu').menu({
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
			
			//修改密码
			function editPassword(){
				$('#editDialog').dialog({'title':'修改密码','href':'editPasswordInit.htm'}).dialog('open');
			}
		
	</script>
    </head>
    <body onkeydown="keyDown()">
    	<div id="my_layout">
	       <div data-options="region:'north',border:false" class="header">
	            <%@ include file="header.jsp" %>
	       </div>
	       <div data-options="region:'west'" title="谷登运营后台" style="width:200px; overflow:hidden;">
				<%@ include file="lefter.jsp" %>
	       </div>
	       <div data-options="region:'center'" title="" style="overflow: hidden;">
				<div id="my_tabs" style="overflow: hidden;">
					<div title="首页" data-options="border:false" class="index_bg">
						
					</div>
				</div>
			</div>
	        <div data-options="region:'south',border:false" class="footer" style="overflow: hidden;">
	        	<%@ include file="footer.jsp" %>
	        </div>
        </div>
        <div id="my_tabsMenu" style="width: 120px; display:none;">
			<div data-options="iconCls:'icon-reload'" title="refresh">刷新</div>
			<div data-options="iconCls:'icon-del'" title="close">关闭</div>
			<div data-options="iconCls:'icon-del'" title="closeOther">关闭其他</div>
			<div data-options="iconCls:'icon-del'" title="closeAll">关闭所有</div>
		</div>
		
		<div id="editDialog"class="easyui-dialog" style="width:380px;height:200px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="eidtPassword()">保存</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
		    </div>
		</div>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>