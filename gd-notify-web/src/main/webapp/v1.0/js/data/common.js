/*获得日期时间*/
function formatTime(){
	var myDate = new Date();
	var year = myDate.getFullYear().toString();
	var month = notOne(myDate.getMonth().toString());
	var day = notOne(myDate.getDate().toString()); 
	var hour = notOne(myDate.getHours().toString());       
	var minite = notOne(myDate.getMinutes().toString());     
	var seconds = notOne(myDate.getSeconds().toString());
	return year +"-" + month+ "-" + day +" " + hour +":" + minite+ ":" + seconds;
}

/*获得格式化日期*/
function formatDate(str){
	if(!str){
		str="";
	}
	var myDate = new Date();
	var year = myDate.getFullYear().toString();
	var month = notOne(myDate.getMonth().toString());
	var day = notOne(myDate.getDate().toString());
	return year + str + month + str + day;

}

/*判断日期时间是否只有一位*/
function notOne(str){
	if(str.length<2){
		str="0"+str;
	}
	return str;
}

/*点击今日，7日，15日，30日切换的方法*/
function changeDays($_Days){
    var $_this=$_Days;
        $(".lyHeadDays").removeClass('tapped').addClass('not-tapped');
        $_this.removeClass('not-tapped').addClass('tapped');
}

/*获取字符串str最后length位的字符窜*/

function getStr(str,length){ 
	var str_after= str.substring(str.length-length, str.length);
	return str_after; 
} 
/*格式化钱数的方法s为钱数，n为保留几位小数*/
function fmoney(s, n) { 
	n = n > 0 && n <= 20 ? n : 2; 
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
	t = ""; 
	for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
	} 
	return t.split("").reverse().join("") + "." + r; 
} 

/*获取url*/
function getQueryString(name) { 
var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
var r = window.location.search.substr(1).match(reg); 
if (r != null) return unescape(r[2]); return null; 
}

