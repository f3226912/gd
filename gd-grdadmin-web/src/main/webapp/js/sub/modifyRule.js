function addRange() {
	var $cu179 = $(".rangcn"),
		firstflag = $(".rangcn:last").find("input:text"),
		firstVal = firstflag.eq(1).val(),
		selectType = $cu179.find("select");
	var len = $(".rangcn").length;
	//alert(len);
	var temphtml = '<tr class="rangcn" ><td class="mleft"  ></td>';
	temphtml += '<td class="mright"><input  type="text" id="minOrderAmount'
			+ len
			+ '"  name="rList['
			+ len
			+ '].lowerLimit" readonly="readonly"   value="'+firstVal+'" required="true" maxlength="20" class="easyui-validatebox"  style="width:100px" missingMessage="必须填写">&nbsp;元';
	temphtml += '--';
	temphtml += '<input  type="text" id="orderAmount'
			+ len
			+ '"  name="rList['
			+ len
			+ '].upperLimit"  onkeyup="clearNoNum(this)"   value="" required="true" maxlength="20" class="easyui-validatebox"  style="width:100px" missingMessage="必须填写">&nbsp;元';
	temphtml += '</td>';
	temphtml += '<td  class="mright">';
	temphtml += '补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount'
			+ len
			+ '"  name="rList['
			+ len
			+ '].subAmount"  onkeyup="clearNoNum(this)"    value="" required="true" maxlength="20"   style="width:70px" missingMessage="必须填写">&nbsp;';
	temphtml += '<select name="rList[' + len
			+ '].subUnit" id="u184" class="u184">';
	temphtml += '<option selected value="1">元</option>';
	temphtml += '<option value="41">/千</option>';
	temphtml += '<option value="42">/万</option>';
	temphtml += '<option value="43">/十万</option>';
	temphtml += '<option value="44">/百万</option>';
	temphtml += '<option value="45">/千万</option>';
	temphtml += '</select>--<span><a href="javascript:void(0)" class="row-del">删除</a></span>';
	temphtml += '</td>';
	temphtml += '</tr>';
	
	
	if(firstflag.eq(1).val()!=''){
		if(firstflag.eq(0).val()==''){
			warningMessage("第"+$(".rangcn").length+"个价格区间的开始区间不能为空");
			return
		}else{
			if(firstflag.eq(0).val()-0>=firstflag.eq(1).val()-0){
				warningMessage("第"+$(".rangcn").length+"个价格区间的结束区间不能小于或等于开始区间");
				return
			}else{
				$("#rangbn").before(temphtml);
			}			
		}		
	}else{			
		warningMessage("第"+$(".rangcn").length+"个价格区间的结束区间不能为空");
		return
	}
	$(".rangcn:last").find("select").eq(0).val(selectType.eq(0).val());
	$(".rangcn:last").find("select").eq(0).change(function(){
		$(this).val(selectType.eq(0).val())
	});
	$(".rangcn:last").find("select").eq(1).val(selectType.eq(1).val());
	$(".rangcn:last").find("select").eq(1).change(function(){
		$(this).val(selectType.eq(1).val())
	});
}

function addRange2() {
	var len = $(".rangmg").length;
	var $rangmg = $(".rangmg"),
	firstflag = $(".rangmg:last").find("select"),
	firstVal = firstflag.eq(1).val();
	var html = '<tr class="rangmg"><td class="mleft"  >';
	var sel = $("#cartypeID").html();
	html += '</td>';
	html += '<td class="mleft" style="float:left;">';
	html += '<select name="mList[' + len
			+ '].carType" class="text_sketch">';
	html += sel;
	html += '</select>&nbsp;&nbsp;';
	html += '<select name="mList[' + len
			+ '].truck" class="text_sketch">';
	html += '<option selected="" value="0">选择装载量</option>';
	html += '<option value="1">明显少量</option>';
	html += '<option value="2">低于半车</option>';
	html += '<option value="3">大概半车</option>';
	html += '<option value="4">满车</option>';
	html += '</select></td>';
	html += '<td class="mleft">补贴金额&nbsp;&nbsp;<input   type="text" id="subAmount0"  name="mList['
			+ len
			+ '].subAmount" onkeyup="clearNoNum(this)"   value="" maxlength="20"   style="width:70px" >&nbsp;';
	html += '<select name="mList[' + len + '].subUnit" id="u184" class="u184">';
	html += '<option selected value="1">元</option>';
	html += '<option value="51">/元/天/车</option>';
	html += '</select>--<span><a href="javascript:void(0)" class="row-del">删除</a></span>';
	html += '</td></tr>';
	if(firstflag.eq(0).val()==0){
		warningMessage("第"+len+"个出货车型为必选");
		return
	}
	if(firstflag.eq(1).val()==0){
		warningMessage("第"+len+"个装载量为必选");
		return
	}
	if(firstflag.eq(0).val()!=0&&firstflag.eq(1).val()!=0){
		$("#rcountbn").before(html);
	}
	
}

function addRangWeight() {
	var $cu179 = $(".cu179"),
		firstflag = $(".cu179:last").find("input:text"),
		firstVal = firstflag.eq(1).val(),
		selectType = $cu179.find("select");	
	var len = $(".cu179").length;
	var sel = $("#u181r").html();
	var temp = '<tr class="cu179" >';
	temp += '<td class="mleft"  >';
	temp += '<td class="mright">';
	temp += '<input  type="text" id="minOrderAmount0" onkeyup="clearNoNum(this)"   name="zList['
			+ len
			+ '].lowerLimit"   value="'+firstVal+'" readonly="readonly"  maxlength="20"  style="width:70px">&nbsp;';
	temp += '--';
	temp += '<input  type="text" id="orderAmount0" onkeyup="clearNoNum(this)"  name="zList['
			+ len
			+ '].upperLimit"   value=""  maxlength="20"  style="width:70px">&nbsp;&nbsp;';
	temp += '<select  name="zList[' + len + '].unit" readonly="readonly">';
	temp += sel;
	temp += '</select>';
	temp += '</td>';
	temp += '<td  class="mright">';
	temp += '补贴金额&nbsp;&nbsp;<input  type="text" id="subAmount0"  name="zList['
			+ len
			+ '].subAmount"  onkeyup="clearNoNum(this)"    value="" maxlength="20"   style="width:70px" >&nbsp;';
	temp += '<select id="u184" name="zList[' + len + '].subUnit" class="u184" readonly="readonly">';
	temp += '<option selected=""  value="1">元</option>';
	temp += '<option value="31">元/吨</option>';
	temp += '<option value="32">元/公斤</option>';
	temp += '</select>--<span><a href="javascript:void(0)" class="row-del">删除</a></span>';
	temp += '</td></tr>';	
	
	if(firstflag.eq(1).val()!=''){
		if(firstflag.eq(0).val()==''){
			warningMessage("第"+$(".cu179").length+"个价格区间的开始区间不能为空");
			return
		}else{
			if(firstflag.eq(0).val()-0>=firstflag.eq(1).val()-0){
				warningMessage("第"+$(".cu179").length+"个价格区间的结束区间不能小于或等于开始区间");
				return
			}else{
				$("#u172").before(temp);
			}			
		}		
	}else{			
		warningMessage("第"+$(".cu179").length+"个价格区间的结束区间不能为空");
		return
	}
	$(".cu179:last").find("select").eq(0).val(selectType.eq(0).val());
	$(".cu179:last").find("select").eq(0).change(function(){
		$(this).val(selectType.eq(0).val());
	});
	$(".cu179:last").find("select").eq(1).val(selectType.eq(1).val());
	$(".cu179:last").find("select").eq(1).change(function(){
		$(this).val(selectType.eq(1).val());
	});
	
};

function clearNoNum(obj)  
{  
   obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
   obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.  
  obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
  obj.value=obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数  
} 

function smit() {
	var timeStart = $('#timeStart').val();
	var timeEnd = $('#timeEnd').val();
	var subRuleName = $("#subRuleName").val();
	
	var flag = true;
	if (subRuleName == null || $.trim(subRuleName) == "") {
		warningMessage("规则名称不能为空!");
		return false;
	}	
	
	if($("#lastTree").val()==''){
		warningMessage("请选择分类!");
		return false;
	}
	if (timeStart == null || $.trim(timeStart) == "" || timeEnd == null
			|| $.trim(timeEnd) == "") {
		warningMessage("补贴周期不能为空!");
		return false;
	}

	var type = $("input[name='subPayRule.subType']:checked").val();
	
	if (type == null || $.trim(type) == "") {
		warningMessage("选择补贴方案!");
		return false;
	}
	/*if(type == 3){
		checkRange($(".cu179"))
	}*/
	type = type * 1;
	if (type == 1) {
		var subPercent = $('#subPercent').val();
		if (subPercent == null || $.trim(subPercent) == "") {
			warningMessage("订单交易成交金额百分比不能为空!");
			return false;
		}

	} else if (type == 2) {
		var subAmount = $('#subAmount').val();
		if (subAmount == null || $.trim(subAmount) == "") {
			warningMessage("每笔金额补贴金额不能为空!");
			return false;
		}
	} else if (type == 3) {
		//var re = true;
		if(!checkRange($(".cu179"),type)){
			flag = false;
			return false;
		}
		
		//return re;

	} else if (type == 4) {
		//checkRange($(".rangcn"),type)
		if(!checkRange($(".rangcn"),type)){
			flag = false;
			return false;
		}
	} else if(type == 5){
		var $rangmg = $(".rangmg");
		$rangmg.each(function(index,ele){
			var $selects = $rangmg.eq(index).find("select"),
				inputl = $rangmg.eq(index).find("input:text");
			if($selects.eq(0).val()==0){
				warningMessage("第"+(index+1)+"个出货车型为必选");
				flag = false;
				return;			
			}
			if($selects.eq(1).val()==0){
				warningMessage("第"+(index+1)+"个装载量为必选");
				flag = false;
				return;	
			}
			
			if(inputl.val()==''||inputl.val()==0){
				warningMessage("第"+(index+1)+"个的补贴金额不能为空或零");
				flag = false;
				return;			
			}
			
			
		});
	}

	if(flag==true){
		var url = CONTEXT + "/subpayrule/updateRule";
		jQuery.post(url, $('#addForm').serialize(), function(data) {
			if (data.code == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				parent.$("#ruleList").datagrid('load', {});
				parent.$('#openRoleDiv').window('close');
			} else if (data.code == "subname") {
				warningMessage("规则名已存在，请修改规则名");
				return;
			} else if (data.code == "rulenamenull") {
				warningMessage("规则名不能为空！");
				return;
			}else if (data.code == "catenull") {
				warningMessage("请正确选择分类！");
				return;
			}  else if (data.code == "expired") {
				warningMessage("登录过期！");
			} else if(data.code == "same"){
				warningMessage(data.cate.name+"已经存在规则，请重新选择分类！");
			}else if(data.code == "timeerror"){
				warningMessage("补贴周期有误！");
			}  else {
				warningMessage("系统异常！");
//				parent.$("#ruleList").datagrid('load', {});
//				parent.$('#openRoleDiv').window('close');
				return;
			}
		});	
	}
}

function smitcopy() {
	var timeStart = $('#timeStart').val();
	var timeEnd = $('#timeEnd').val();
	var subRuleName = $("#subRuleName").val();
	
	var flag = true;
	if (subRuleName == null || $.trim(subRuleName) == "") {
		warningMessage("规则名称不能为空!");
		return false;
	}	
	if($("#lastTree").val()==''){
		warningMessage("请选择分类!");
		return false;
	}
	if (timeStart == null || $.trim(timeStart) == "" || timeEnd == null
			|| $.trim(timeEnd) == "") {
		warningMessage("补贴周期不能为空!");
		return false;
	}

	var type = $("input[name='subPayRule.subType']:checked").val();
	
	if (type == null || $.trim(type) == "") {
		warningMessage("选择补贴方案!");
		return false;
	}
	if(type == 3){
		
	}
	type = type * 1;
	if (type == 1) {
		var subPercent = $('#subPercent').val();
		if (subPercent == null || $.trim(subPercent) == "") {
			warningMessage("订单交易成交金额百分比不能为空!");
			return false;
		}

	} else if (type == 2) {
		var subAmount = $('#subAmount').val();
		if (subAmount == null || $.trim(subAmount) == "") {
			warningMessage("每笔金额补贴金额不能为空!");
			return false;
		}
	} else if (type == 3) {
		//var re = true;
		if(!checkRange($(".cu179"),type)){
			flag = false;
			return false;
		}
		/*$("input[name^='zList']").each(function() {
			var value = ($(this).val());
			if (value == null || $.trim(value) == "") {
				warningMessage("总交易额区间不能为空!");
				flag = false;
				return false;
			}
		})*/
		//return re;

	} else if (type == 4) {
		/*checkRange($(".rangcn"))*/
		if(!checkRange($(".rangcn"),type)){
			flag = false;
			return false;
		}
	}

	if(flag==true){
		var url = CONTEXT + "/subpayrule/addRule/2";
		jQuery.post(url, $('#addForm').serialize(), function(data) {
			if (data.code == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				parent.$("#ruleList").datagrid('load', {});
				parent.$('#copyDialog').window('close');
			} else if (data.code == "subname") {
				warningMessage("规则名已存在，请修改规则名");
				return;
			}else if (data.code == "catenull") {
				warningMessage("请正确选择分类！");
				return;
			}  else if (data.code == "expired") {
				warningMessage("登录过期！");
			} else if(data.code == "same"){
				warningMessage(data.cate.name+"已经存在规则，请重新选择分类！");
			} else {
				warningMessage("系统异常！");
//				parent.$("#ruleList").datagrid('load', {});
//				parent.$('#openRoleDiv').window('close');
				return;
			}
		});	
	}
}
	
function checkRange(parent,type){
	var $interval = parent,
		$intervalLen = parent.length,
		str = type==3?'重量区间':'金额区间',
		flag = true;
	$interval.each(function(index,ele){
		var $inputs = $interval.eq(index).find("input:text");
		if($inputs.eq(0).val()==''){
			warningMessage("第"+(index+1)+"个"+str+"的开始区间不能为空");
			flag = false;
			return;			
		}
		if(index != $intervalLen-1){
			if($inputs.eq(1).val()==''){
				warningMessage("第"+(index+1)+"个"+str+"的结束区间不能为空");
				flag = false;
				return;			
			}
		}
		
		if($inputs.eq(2).val()==''||$inputs.eq(2).val()==0){
			warningMessage("第"+(index+1)+"个"+str+"的补贴金额不能为空或零");
			flag = false;
			return;			
		}
		if(index != $intervalLen-1){
			if($inputs.eq(1).val()<=$inputs.eq(0).val()){
				warningMessage("第"+(index+1)+"个"+str+"的结束区间不能小于或等于开始区间");
				flag = false;
				return;			
			}
		}
		
	});
	/*if($intervalLen > 1){
		$interval.each(function(index,ele){
			var $inputs = $interval.eq(index).find("input:text"),
				$subInputs = $interval.eq(index-1).find("input:text");
			if($subInputs.eq(0).val()!='' && $subInputs.eq(0).val()<=$inputs.eq(1).val()){
				//alert(1);
				flag = false;
				return;				
			}
		});
	}*/
	
	/*if($intervalLen>1){
		for(var i = 0;i<$intervalLen;i++){
			
		}
	}*/
	return flag;
}
var setting = {
	data : {
		key : {
			title : "name"
		},
		simpleData : {
			enable : true
		}
	},
	callback : {
		//beforeClick: beforeClick,
		onClick : onClick
	}
};
var zNodes = {};
var clickflag = 0;
function initTree(marketID){
	
	$.ajax({
		type : "GET",
		url : CONTEXT + "subpayrule/getProductCategory?marketId="+marketID,
		dataType : "json",
		async : false,
		success : function(data) {
			zNodes = data
		}
	});
}


var settingSel = {
	data : {
		key : {
			title : "name"
		},
		simpleData : {
			enable : true
		}
	},
	edit : {
		enable : true,
		showRemoveBtn : true,
		showRenameBtn : false
	},
	callback: {
		//beforeClick: beforeClick,
		onRemove: removeCall
	}
};
$(function(){
	if($("#lastTree").val()!=''){
		$.fn.zTree.init($("#cat-tree-sel"), settingSel,$.parseJSON($("#lastTree").val()))
	}	
});


var bakNodepath;
function onClick(event, categoryId, treeNode) {
	//alert(treeNode.categoryId + ", " + treeNode.name + ", " +treeNode.curLevel);
	var obj = {};
	var treeObj = $.fn.zTree.getZTreeObj("cat-tree");
	//console.log(treeNode.getParentNode().getParentNode())			
	var sNodes = treeObj.getSelectedNodes();
	var pid = treeNode.parentId;
	if (treeNode) {
		var nodepath = treeNode.getPath();
		bakNodepath = cloneObj(nodepath);
		var isparent = treeNode.isParent;
		var treesel;
		switch (treeNode.curLevel) {
		case 2:
			//console.log(treeNode);
			var thirldchild = bakNodepath[1].children, secondchild = bakNodepath[0].children, ppid = treeNode
					.getParentNode().categoryId;
			//if(!isparent){
			console.log(bakNodepath[0])
			for (var j = 0; j < secondchild.length; j++) {

				// if(secondchild[j].length){

				if (secondchild[j]!=undefined&&secondchild[j].children) {
					for (var i = 0; i < secondchild[j].children.length; i++) {
						if (secondchild[j].children[i] != undefined) {
							if (secondchild[j].children[i].categoryId != treeNode.categoryId) {
								delete secondchild[j].children[i];
							}
						}
						console.log(secondchild[j].children.length)
					}
				}
				if(secondchild[j]!=undefined){
					if (secondchild[j].categoryId != ppid) {
						delete secondchild[j];
					}
				}

			}
			for (var k = 0; k < thirldchild.length; k++) {
				if(thirldchild[k]!=undefined){	
					if (thirldchild[k].categoryId != treeNode.categoryId) {
						delete thirldchild[k];
					}
				}
			}
			break;
		case 1:
			var secondchild = bakNodepath[0].children, ppid = treeNode
					.getParentNode().categoryId;
			// if(!isparent){
			for (var j = 0; j < secondchild.length; j++) {
				if(secondchild[j]!=undefined){
					if (secondchild[j].categoryId != treeNode.categoryId) {
						//thirldchild[i] = null;
						delete secondchild[j];
					}
				}
			}
			// }
			break;
		case 0:
			var firstchild = bakNodepath[0]
			for (var j = 0; j < firstchild.length; j++) {
				if(firstchild[j]!=undefined){
					if (firstchild[j].categoryId != treeNode.categoryId) {
						//thirldchild[i] = null;
						delete secondchild[j];
					}
				}
			}
			break;

		}
		// $.parseJSON(bakNodepath[0])
		var bakNodepath2 = bakNodepath[0]
		console.log(bakNodepath2);
		if (clickflag == 0&&$.fn.zTree.getZTreeObj("cat-tree-sel")==null) {
			treesel = $.fn.zTree.init($("#cat-tree-sel"), settingSel,
					bakNodepath[0]);
		} else {
			var treeObj2 = $.fn.zTree.getZTreeObj("cat-tree-sel");
			//var nodep = treeObj2.getNodeByTId(pid);
			//var checkCount = treeObj.getCheckedNodes(true);
			var nodep = treeObj2.getNodesByParam("categoryId", pid, null);
			var nodepp = treeNode.getParentNode() ? treeObj2.getNodesByParam(
					"categoryId", treeNode.getParentNode().parentId, null) : [];
			var nodeeq = treeObj2.getNodesByParam("categoryId",
					treeNode.categoryId, null);
			//var fnodes = treeObj.getNodesByFilter(filter); // 查找节点集合
			if (nodep != '' && nodeeq == '') {
				//treeObj.copyNode(nodep[0], treeNode, "inner");
				nodep[0].children.push(treeNode)
				//console.log(treeObj2.getNodesByParam("categoryId", treeNode.categoryId, null))
			} else if (nodep == '' && nodeeq == '') {
				var pcurLevel = treeNode.getParentNode() ? treeNode
						.getParentNode().curLevel : 0;
				if (nodepp != '') {
					if (pcurLevel == 0) {
						nodepp[0].children.push(treeNode.getParentNode())
					} else if (pcurLevel == 1) {
						// for(var k = 0; k<bakNodepath[1].children.length; k++){
						// 	if(typeof(thirldchild[k])!= treeNode.categoryId){
						// 		delete thirldchild[k];
						// 	}								
						// }
						nodepp[0].children.push(bakNodepath[1])
					}
				} else {
					treeObj2.addNodes(null, bakNodepath[0])
				}
			}else if(nodep!=''&& nodeeq != ''){
				var treeObj2 = $.fn.zTree.getZTreeObj("cat-tree-sel");
				if(isparent){
					treeObj2.removeChildNodes(nodeeq[0]);
					treeObj2.addNodes(nodeeq[0],treeNode.children)
				}else{
					//alert(1)
				}
			}

			treeObj2.refresh();
			var tnodes = treeObj2.getNodes().toString();
			//console.log(tnodes);
		}

	}
	clickflag += 1;
	var lasttreeObj = $.fn.zTree.getZTreeObj("cat-tree-sel");
	var jsonObj = lasttreeObj.getNodes();
	var lastTreeval = JSON.stringify(jsonObj);
	$("#lastTree").val(lastTreeval);
}


function cloneObj(obj) {
	var o, i, j, k;
	if (typeof (obj) != "object" || obj === null)
		return obj;
	if (obj instanceof (Array)) {
		o = [];
		i = 0;
		j = obj.length;
		for (; i < j; i++) {
			if (typeof (obj[i]) == "object" && obj[i] != null
					&& obj[i] != undefined) {
				o[i] = arguments.callee(obj[i]);
			} else {
				o[i] = obj[i];
			}
		}
	} else {
		o = {};
		for (i in obj) {
			if (typeof (obj[i]) === "object" && typeof (obj[i]) != "undefined") {
				o[i] = arguments.callee(obj[i]);

			} else if ($.isArray(obj[i])) {
				alert(1)
			} else {
				o[i] = obj[i];
			}
		}
	}

	return o;
}
function removeCall(event, treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj("cat-tree-sel");
	switch (treeNode.curLevel){
		case 2:
			var pnode = treeNode.getParentNode(),
				ppnode = treeNode.getParentNode().getParentNode(),
				ppid = ppnode,categoryId,
				pid = pnode.categoryId,
				thirdbaklen=0,baklen = 0;
			for(var i=0;i<pnode.children.length;i++){
				if(pnode.children[i]==undefined){
					thirdbaklen+=0;
				}else{
					thirdbaklen+=1;
				}
			}
			if(thirdbaklen == 0){
				zTree.removeNode(pnode);
			}
			for(var j=0;j<ppnode.children.length;j++){
				if(ppnode.children[j]==undefined){
					baklen+=0;
				}else{
					baklen+=1;
				}
			}
			if(baklen == 0){
				zTree.removeNode(ppnode);
			}
		break;
		case 1 :
			var pnode = treeNode.getParentNode(),
				pid = pnode.categoryId,
				baklen=0;
			for(var i=0;i<pnode.children.length;i++){
				if(pnode.children[i]==undefined){
					baklen+=0;
				}else{
					baklen+=1;
				}
			}
			if(baklen == 0){
				zTree.removeNode(pnode);
			}
		break;
	}
	//console.log(bakNodepath);
	var jsonObj = zTree.getNodes();
	var lastTreeval = JSON.stringify(jsonObj);
	$("#lastTree").val(lastTreeval);
}