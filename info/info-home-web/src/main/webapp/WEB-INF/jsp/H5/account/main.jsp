<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
		<base href="${CONTEXT}" >
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>我的账户</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link href="v1.0/css/login.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="v1.0/module/mui/examples/hello-mui/css/mui.css">
	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
<!-- 			<a class="mui-icon mui-pull-left">< 返回</a> -->
			<a id="info" class="mui-icon mui-icon-info-abort mui-pull-right"></a>
			<h1 class="mui-title">我的帐户</h1>
		</header>
		<nav class="mui-bar mui-bar-tab">
			<a id="defaultTab" class="mui-tab-item" href="tab-webview-subpage-about.html">
				<span class="mui-icon mui-icon-workp"></span>
				<span class="mui-tab-label">工作台</span>
			</a>
			<a class="mui-tab-item" id="messageBtn" href="tab-webview-subpage-chat.html">
				<span class="mui-icon mui-icon-msg"><span class="mui-badge" style="display: none;"></span></span>
				<span class="mui-tab-label">消息</span>
			</a>
			<a class="mui-tab-item" id="userCenterBtn" href="tab-webview-subpage-contact.html">
				<span class="mui-icon mui-icon-mine"></span>
				<span class="mui-tab-label" style="color: #007aff;">我的</span>
			</a>
		</nav>
		<script src="v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
		<script src="v1.0/module/mui/examples/hello-mui/js/update.js" type="text/javascript" charset="utf-8"></script>
		<script>
			var menu = null,
				main = null;
			var showMenu = false;
			mui.init({
				swipeBack: false,
				statusBarBackground: '#fbfbfd',
				gestureConfig: {
					doubletap: true
				},
				subpages: [{
					id: 'my-account.html',
					url: 'sysRegisterUser/getSysRegisterUser',
					styles: {
				        top: '4.4rem',
						bottom: '4.95rem',
				        bounce: 'vertical'
					}
				}],
				waiting:{
			      	autoShow:true,
			      	title:'正在加载...',
			      	options:{
			        	width:'100%',//等待框背景区域宽度，默认根据内容自动计算合适宽度
			        	height:'100%'//等待框背景区域高度，默认根据内容自动计算合适高度
			      	}
			    }
			});
			var isInTransition = false;
			/**
			 * 显示侧滑菜单
			 */
			
			/**
			 * 关闭菜单
			 */
			
			//敲击顶部导航，内容区回到顶部
			document.querySelector('header').addEventListener('doubletap', function() {
				main.children()[0].evalJS('mui.scrollTo(0, 100)');
			});
			
			
				//处理右上角关于图标的点击事件；
			var subWebview = null,
				template = null;
			document.getElementById('info').addEventListener('tap', function() {
				if (!mui.os.plus) {
					mui.openWindow({
						url: "examples/info.html",
						id: "info",
						show: {
							aniShow: 'zoom-fade-out',
							duration: 300
						}
					});
					return;
				}
				if (subWebview == null) {
					//获取共用父窗体
					template = plus.webview.getWebviewById("default-main");
				}
				if (template) {
					subWebview = template.children()[0];
					subWebview.loadURL('examples/info.html');
					//修改共用父模板的标题
					mui.fire(template, 'updateHeader', {
						title: '关于',
						showMenu: false
					});
					template.show('slide-in-right', 150);
				}
				//				mui.openWindow({
				//					url:"examples/info.html",
				//					id:"info",
				//					show:{
				//						aniShow:'zoom-fade-out',
				//						duration:300
				//					}
				//				});
			});
			
			//首页返回键处理
			//处理逻辑：1秒内，连续两次按返回键，则退出应用；
			var first = null;
			mui.back = function() {
				if (showMenu) {
					closeMenu();
				} else {
					//首次按键，提示‘再按一次退出应用’
					if (!first) {
						first = new Date().getTime();
						mui.toast('再按一次退出应用');
						setTimeout(function() {
							first = null;
						}, 1000);
					} else {
						if (new Date().getTime() - first < 1000) {
							plus.runtime.quit();
						}
					}
				}
			};
			getUnReadData('${systemLoginUser.userID }',$(".mui-badge"));
		</script>
	</body>

</html>