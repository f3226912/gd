<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var my_tree;
	var my_tree_url="getMenuList";
	$(function() {
		my_tree = $('#my_tree').tree({
			url : my_tree_url,
			parentField : 'menuModuleId',
			onClick : function(node) {
				 if(node.attributes.level!=1){
					//打开tab				
					addTab({
						url : "${CONTEXT}"+node.attributes.actionUrl,
						title : node.text,
						iconCls : node.iconCls
					});
				}
			},
			onBeforeLoad : function(node, param) {
				//只有刷新页面才会执行这个方法
				if (my_tree_url) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
				}
			},
			onLoadSuccess : function(node, data) {
				parent.$.messager.progress('close');
			}
		});
	});
	
	//新增tab页
	function addTab(params) {
		var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>';
		var t = $('#my_tabs');
		if (t.tabs('exists', params.title)) {
			t.tabs('select', params.title);
			parent.jQuery.messager.progress('close');
		} else {
			t.tabs('add', {
				title: params.title,
				closable: true,
				content: iframe,
				border: false,
				iconCls:params.iconCls,
				fit: true
			});
		}
	}
</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="系统菜单">
		<ul id="my_tree"></ul>  
	</div>
</div>
