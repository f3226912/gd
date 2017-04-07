//获取url上的参数
function getQueryString(name) {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
};

//弹出框的方法
function myLayer(text){
	layer.msg(text);
};

//将一位数变成两位,length等于二
function fix(num, length) {
	  return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
	}

//获取cookiess的值
function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null)
        return unescape(arr[2]);
    return null;
};
//设置cookiess的值
function setCookie(c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + escape(value) +
    ((expiredays == null) ? "" :  "; path=/;expires=" + exdate.toGMTString())
};

//点击删除按钮的ajax方法
//memberId	用户id
//transferId	货源id
//version	版本号
function deleteAjax(memberId,transferId,version){
	$.ajax( {  
        url:'editOrder/1001',// 跳转到 action  
        data:{  
           memberId:memberId,
           transferId:transferId,
           version:version
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) { 
        	if(data.statusCode != 0){
        		$(this).attr("bol","1");
				myLayer(data.msg);
				setStatus();
				return false;
			}
        	deleteAfter();
        	myLayer("操作成功");
        },  
        error : function() {   
        	failedLoad(); 
        	setStatus();
        	$(this).attr("bol","1");
        	return false;
        }  
     })
}

//点击拒绝按钮的ajax方法
//memberId	用户id
//transferId	货源id
//orderId	预付订单id
//version	版本号
function refuseAjax(memberId,transferId,orderId,version){
	$.ajax( {  
        url:'editOrder/1002',// 跳转到 action  
        data:{  
           memberId:memberId,
           transferId:transferId,
           orderId:orderId,
           version:version
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {
			if(data.statusCode != 0){
				$(this).attr("bol","1");
				myLayer(data.msg);
				setStatus();
				return false;
			}
			
			deleteAfter(0);
			myLayer("操作成功");
        }, 
        error : function() {
        	failedLoad(); 
        	setStatus();
        	$(this).attr("bol","1");
        	return false;
        }  
     })
}

//点击接受按钮的ajax方法
//memberId	用户id
//transferId	货源id
//version	版本号
function acceptAjax(memberId,transferId,orderId,version){
	$.ajax( {  
        url:'editOrder/1003',// 跳转到 action  
        data:{  
        	memberId:memberId,
            transferId:transferId,
            orderId:orderId,
            version:version
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {  
        	if(data.statusCode != 0){
        		$(this).attr("bol","1");
				myLayer(data.msg);
				setStatus();
				return false;
			}
			
			deleteAfter(1);
			myLayer("操作成功");
        },
        error : function() {   
        	failedLoad(); 
        	$(this).attr("bol","1");
        	setStatus();
        	return false;
        }  
     })
}

//点击确认收货按钮的ajax方法
//memberId	用户id
//transferId	货源id
//version	版本号
function confirmAjax(memberId,transferId,orderId,version){
	$.ajax( {  
        url:'editOrder/1004',// 跳转到 action  
        data:{  
           memberId:memberId,
           transferId:transferId,
           orderId:orderId,
           version:version
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {
        	if(data.statusCode != 0){
        		$(this).attr("bol","1");
				myLayer(data.msg);
				setStatus();
				return false;
			} 
        	
        	deleteAfter(2);
        	myLayer("操作成功");
        },  
        error : function() {   
        	failedLoad();
        	setStatus();
        	$(this).attr("bol","1");
        	return false;
        }  
     })
}

//打电话用的方法urlType永远为0
function aonClick(urlType, params, mId){
	var mobile = params.replace("mobile=", "");
	switch(urlType){
		case 0:
			try 
			{ 
				window.webkit.messageHandlers.aonClick.postMessage(mobile);
			}
			catch(err) 
			{ 
				console.log(params)
				JsBridge.call(params);
			} 
			
			//调用接口拨打电话
			$.ajax({
				url:"addCall",
				type:"post",
				data:{
					's_Mobile': mobile,
					'memberId': mId
				},
				dataType:"text",
				success:function(data){
						
				},
				error:function(){
				}
			});
			break;
	}
}

//数据获取失败
function failedLoad(){
	var mainHtml='<style type="text/css">'
				+		'*{margin:0;padding:0;font-family: "微软雅黑";}'
				+		'.outline{width:100%;height:100%;}'
				+		'.mui-scroll{height:100%;}'
				+		'.con{width:68%;position: absolute;left:16%;top:50%;margin-top:-100px;}'
				+		'.con img{width:57%;display: block;margin:0 auto;margin-bottom:1rem;}'
				+		'.con p{width:100%;text-align:center;color:#999999;font-weight:600;margin-bottom: .5rem;}'
				+		'.con .buttom{text-align: center;color:#666666;border:1px solid #999999;width:50%;border-radius:5px;margin:3px auto;line-height:2.3rem;}'
				+	'</style>'
				+	'<div class="con">'
				+		'<img src="../../v1.0/images/noWorking.png"/>'
				+		'<p>数据获取失败</p>'
				+		'<p style="font-size:.8rem;">请检查网络后点击重新加载</p>'
				+		'<div class="buttom">重新加载</div>'
				+	'</div>';
	$("#published").html(mainHtml);
}

//获取现在的时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

//调用支付的ajax接口
function payAjax(orderid,drivermemberid,payAmt,transferid){
	$.ajax( {  
        url:context+"nstFare/signParam",
        data:{  
        	"orderNo":orderid,
        	"payerUserId":getQueryString("memberId"),
        	"totalAmt":payAmt,
        	"payAmt":payAmt,
        	"payeeUserId":drivermemberid,
        	"sourceId":transferid,
        	"orderTime":getNowFormatDate()
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {
        	console.log(data);
        	window.location.href=data.url;
        }
	});
}











