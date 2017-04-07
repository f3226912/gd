//获取url上的参数
function getQueryString(name) {
    var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
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