var $param = decodeURIComponent(location.search).replace("?", "");
var $map = paramMap($param);
var listing;

function search(key) {
    for (var j = 0; j < $map.length; j++) {
        if (key == $map[j].key) {
            return $map[j].value;
        }
    }
    return null;
}

function paramMap(source) {
    var array_one = source.split("&");
    var array_two;
    var map = new Array();
    for (var i = 0; i < array_one.length; i++) {
        array_two = array_one[i].split("=");
        map.push(new bean(array_two[0], array_two[1]));
    }
    return map;
}

function bean(key, value) {
    this.key = key;
    this.value = value;
    return this;
};
/*获取本地地址  */
function getRootPath_web() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPaht;
}

$.extend($, {
    /**
     * 获取请求参数
     */
    getReqParam: function(name) {
        var url = location.search;
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                this[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            }
            return this[name];
        }
        return null;
    },
// 封装兼容的localstorage
        set : function(key, value){
            //在iPhone/iPad上有时设置setItem()时会出现诡异的QUOTA_EXCEEDED_ERR错误
            //这时一般在setItem之前，先removeItem()就ok了
            if( this.get(key) !== null )
                this.remove(key);
            localStorage.setItem(key, value);
        },
        //查询不存在的key时，有的浏览器返回undefined，这里统一返回null
        get : function(key){
            var v = localStorage.getItem(key);
            return v === undefined ? null : v;
        },
        remove : function(key){ localStorage.removeItem(key); },
        clear : function(){ localStorage.clear(); },
      
   
    // 描述：<br />判断移动终端浏览器版本
    appVersion: (function() {
        var u = navigator.userAgent,
            app = navigator.appVersion;
        return {
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
        };
    })(),
    /**
     * Android IOS 参数加密处理
     */
    enCode: function(anVal, iosVal, cbName, callback) {
        // ios处理
        if (this.appVersion.ios) {
            window[cbName] = callback;
            window.webkit.messageHandlers.NativeEncrypt.postMessage(iosVal);
        }
        // 安卓处理
        else if (this.appVersion.android) {
            return JsBridge.encode(anVal);
        }
    },
    /**
     * Android IOS 返回结果解密处理
     */
    deCode: function(anVal, iosVal, cbName, callback) {
        if (this.appVersion.ios) {
            window.webkit.messageHandlers.NativeDecrypt.postMessage(iosVal);
            window[cbName] = callback;
        } else if (this.appVersion.android) {
            return JSON.parse(JsBridge.decode(anVal));
        }
    },
    // 客户端返回接口
    retur: function() {
        if (this.appVersion.ios) {
            window.webkit.messageHandlers.NativeMethod.postMessage("finish");
        } else if (this.appVersion.android) {
            JsBridge.finish();
        }
    }
});
