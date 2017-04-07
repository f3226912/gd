// 左边去空白(含中文的全角空白)
// sStr:传入之字串
function commLtrim(sStr) {
   //return sStr.replace(/(^\s*)/g, "");
   if(commEmpty(sStr)) return "";
   return sStr.replace(/(^\s*)|(^\u3000*)|(\u3000*$)|(^\ue4c6*)|(\ue4c6*$)|(\s*$)/g,""); 
}

// 右边去空白 (含中文的全角空白)
// sStr:传入之字串;
function commRtrim(sStr) {
   //replace(/(\s*$)/g, "");
   if(commEmpty(sStr)) return "";
   return sStr.replace(/(\s*$)|(^\u3000*)|(\u3000*$)|(^\ue4c6*)|(\ue4c6*$)|(\s*$)/g,""); 
}

// 左右边去空白(含中文的全角空白)
// sStr:传入之字串
function commTrim(sStr) {
   return commRtrim(commLtrim(sStr));
}

//判断是否为空白;
function commEmpty(oValue){
	if (oValue == null || oValue == "null" || oValue == "undefined" || oValue == "NaN" || oValue == ""){
		return true;	
	}
	return false;
}

//取得字串长度(一个中文算1个长度);
//sStr:传入之字串
function commLength(sStr) {
	var _iLength = 0;
	for( var i=0; i < sStr.length ; i++) {
	   _iLength +=1;
	}
	return _iLength;
}

//判断中文字串长度
//sStr:传入之字串
//iLength:传入之长度
function commChkLength(sStr, iLength) {
	if (commLength(sStr)>iLength)
	 return false;
	return true;
}
//***************************** 输入功能函数 begin********************************/

//输入对象 格式 处理 (不能挡住中文 请使用： style="ime-mode:disabled"; onpaste="return false;");
//必须在 onkeypress event 才会有作用;
//    sType
//    "9" 仅能输入数字
//    "-9" 仅能输入数字与"-";
//    "N" 仅能输入数字, "."
//    "-N" 仅能输入数字, "."与"-";
//    "C" 仅能输入英文字母
//    "C9" 仅能输入英文字母和数字;
//    "9C" 仅能输入英文字母和数字
//	  "1C" 仅能输入英文字母和数字以及斜杠/
//    "T" 不准输入特殊字符;
//    oElement: 对象物件
//    iLength: 对象长度控制
function commOnKeyPress(e, sType, oElement, iLength) {

	var _isIE=false;// 判断是否为IE浏览器;
	var _iKeyCode;// 键盘事件值;
	// 判断是否为IE浏览器;
	if(navigator.appName == "Microsoft Internet Explorer") {
		_isIE=true;
		_iKeyCode = e.keyCode; 
	 }else {
		 _isIE=false;
		 _iKeyCode = e.which; 
	 }
	// ficommox的Backspace与tab处理;
	if(!_isIE && (_iKeyCode==13 || _iKeyCode==9||_iKeyCode==8 || _iKeyCode==0)){
		 e.returnValue=true;
		 return true;
	}

	// ENTER, TAB 至下一对象
	// IE 不是 Button 按 ENTER, TAB 至下一对象
	if (_isIE && (_iKeyCode==13 || _iKeyCode==9) && window.event.srcElement.type!="button") {
		 window.event.keyCode = 9;
		 return true;
	}
	
	// 判断中文字串长度
	if (!commEmpty(oElement)) {
	 if (!commChkLength(oElement.value, iLength-1)) {
	   e.returnValue=false;
	   return false;
	 }
	}
	// 类型转成大写;
	sType=sType.toUpperCase();
	// "9" 仅能输入０－９数字
	if (sType == "9") {
	 if (_iKeyCode < 48  || _iKeyCode > 57){
		  e.returnValue=false;
		  return false;
	 }
	 return true;
	}
	
	// "9" 仅能输入０－９数字与,"-";
	if (sType == "-9") {
	 if ((_iKeyCode < 48  || _iKeyCode > 57) && _iKeyCode!=43 &&_iKeyCode!=61 && _iKeyCode!=45 && _iKeyCode!=95){
		  e.returnValue=false;
		  return false;
	 }
	 return true;
	}
	
	// "N" 仅能输入数字, "."
	if (sType == "N") {
	 if ((_iKeyCode < 48  || _iKeyCode > 57) && _iKeyCode!=46){
		  e.returnValue=false;
		  return false;
	 }
	 return true;
	}
	
	// "N" 仅能输入数字0-9, ".","-";
	if (sType == "-N") {
	 if ((_iKeyCode < 48  || _iKeyCode > 57) && _iKeyCode!=46 && _iKeyCode!=43 &&_iKeyCode!=61 && _iKeyCode!=45 && _iKeyCode!=95){
		  e.returnValue=false;
		  return false;
	 }
	 return true;
	}
	
	// "C" 仅能输入英文字母
	if (sType == "C") {
	 if (_iKeyCode < 65  || _iKeyCode > 123 ){
		 e.returnValue=false;
		 return false;
	 }
	 return true;
	}
	
	// "B" 仅能输入英文字母及数字
	if (sType == "C9" || sType == "9C") {
	 if (!((_iKeyCode>=65 && _iKeyCode<=123) || (_iKeyCode>=48 && _iKeyCode<=57))){
		 e.returnValue=false;
		 return false;
	 }
	 return true;
	}
	
	// "1C" 仅能输入英文字母和数字以及斜杠/
	if (sType == "1C") {
	 if (!((_iKeyCode>=65 && _iKeyCode<=123) || (_iKeyCode>=47 && _iKeyCode<=57))){
		 e.returnValue=false;
		 return false;
	 }
	 return true;
	}
	
	// "B" 仅能输入英文字母及数字
	if (sType == "C9" || sType == "9C") {
	 if (!((_iKeyCode>=65 && _iKeyCode<=123) || (_iKeyCode>=48 && _iKeyCode<=57))){
		 e.returnValue=false;
		 return false;
	 }
	 return true;
	}
	
	// 不能输入特别字符;
	if(sType == "T" || sType == "t"){
		// var pattern = new
		// RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]");
		var pattern = new RegExp("[<\"]");
		var charStr = String.fromCharCode(_iKeyCode);
		if(pattern.test(charStr)){
			 e.returnValue=false;
			 return false;
		}
		return true;
	}
}
// ***************************** 输入功能函数 end********************************/
$(function(){
	$("input").change(function(){
		$(this).val($.trim($(this).val().replace(/[< "]/g,"")));
	}).each(function (index,element){
		element.ondrop=function(){return false;};
	});
});

function getUnReadData(userID,returnSpanData){
	$.ajax({
		type: "GET",
		url: CONTEXT+"sysMessage/queryUnreadTotal/"+ userID,
		dataType: "json",
		success: function(data) {
			if(data && data>0){
				returnSpanData.text(data);
				returnSpanData.show();
			}else{
				returnSpanData.hide();
			}
		}
	});
}


window.onload=function(){
try{
	
	/**工作台**/
	document.getElementById('defaultTab').addEventListener('tap', function() {
		if (!mui.os.plus) {
			mui.openWindow({
				url: CONTEXT+"H5/workbench/main",
				id: "main",
				createNew:false,//是否重复创建同样id的webview，默认为false:不重复创建，直接显示
			    show:{
			      autoShow:true,//页面loaded事件发生后自动显示，默认为true
			      aniShow:'slide-in-right',//页面显示动画，默认为”slide-in-right“；
			      duration:300//页面动画持续时间，Android平台默认100毫秒，iOS平台默认200毫秒；
			    },
			    waiting:{
			      autoShow:true,//自动显示等待框，默认为true
			      title:'正在加载...'//等待对话框上显示的提示内容
			    }
			});
			return;
		}				
	}); 
	/**消息**/
	document.getElementById('messageBtn').addEventListener('tap', function() {
		if (!mui.os.plus) {
			mui.openWindow({
				url: CONTEXT+"sysMessage/queryMessageList",
				id: "footMsg",
				createNew:false,//是否重复创建同样id的webview，默认为false:不重复创建，直接显示
			    show:{
			      autoShow:true,//页面loaded事件发生后自动显示，默认为true
			      aniShow:'slide-in-right',//页面显示动画，默认为”slide-in-right“；
			      duration:300//页面动画持续时间，Android平台默认100毫秒，iOS平台默认200毫秒；
			    },
			    waiting:{
			      autoShow:true,//自动显示等待框，默认为true
			      title:'正在加载...'//等待对话框上显示的提示内容
			    }
			});
			return;
		}				
	});
	
	/**我的**/
	document.getElementById('userCenterBtn').addEventListener('tap', function() {
		if (!mui.os.plus) {
			mui.openWindow({
				url: CONTEXT+"sysRegisterUser/accountInit",
				id: "footMsg",
				createNew:false,//是否重复创建同样id的webview，默认为false:不重复创建，直接显示
			    show:{
			      autoShow:true,//页面loaded事件发生后自动显示，默认为true
			      aniShow:'slide-in-right',//页面显示动画，默认为”slide-in-right“；
			      duration:300//页面动画持续时间，Android平台默认100毫秒，iOS平台默认200毫秒；
			    },
			    waiting:{
			      autoShow:true,//自动显示等待框，默认为true
			      title:'正在加载...'//等待对话框上显示的提示内容
			    }
			});
			return;
		}				
	});
}catch(e){}
}
