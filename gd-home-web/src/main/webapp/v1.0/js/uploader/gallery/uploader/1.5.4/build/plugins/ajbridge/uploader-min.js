/*!build time : 2014-04-22 10:22:08 AM*/
KISSY.add("gallery/uploader/1.5.4/plugins/ajbridge/ajbridge",function(a,b,c){function d(c,f,j){c=c.replace(e,""),f=b._normalize(f||{});var k=this,l=e+c,m=function(b){return b.status<1?(k.fire("failed",{data:b}),void 0):(a.mix(k,b),b.dynamic&&f.src||k.activate(),void 0)};f.id=f.id||a.guid(g),d.instances[f.id]=k,f.src&&(f.params.allowscriptaccess="always",f.params.flashvars=a.merge(f.params.flashvars,{jsEntry:i,swfID:f.id})),j?k.__args=[l,f,m]:a.later(b.add,h,!1,b,[l,f,m])}var e="#",f="1.0.15",g="ks-ajb-",h=100,i="KISSY.AJBridge.eventHandler";return a.mix(d,{version:f,instances:{},eventHandler:function(a,b){var c=d.instances[a];c&&c.__eventHandler(a,b)},augment:function(b,c){a.isString(c)&&(c=[c]),a.isArray(c)&&a.each(c,function(c){b.prototype[c]=function(){try{return this.callSWF(c,a.makeArray(arguments))}catch(b){this.fire("error",{message:b})}}})}}),a.augment(d,c.Target,{init:function(){this.__args&&(b.add.apply(b,this.__args),this.__args=null,delete this.__args)},__eventHandler:function(b,c){var d=this,e=c.type;switch(c.id=b,e){case"log":a.log(c.message);break;default:d.fire(e,c)}},callSWF:function(a,b){var c=this;b=b||[];try{if(c.swf[a])return c.swf[a].apply(c.swf,b)}catch(d){var e="";return 0!==b.length&&(e="'"+b.join("','")+"'"),new Function("self","return self.swf."+a+"("+e+");")(c)}}}),d.augment(d,["activate","getReady","getCoreVersion"]),window.AJBridge=a.AJBridge=d,d},{requires:["gallery/flash/1.0/index","event"]}),KISSY.add("gallery/uploader/1.5.4/plugins/ajbridge/uploader",function(a,b,c){function d(b,c){c=c||{};var e={};a.each(["ds","dsp","btn","hand"],function(a){a in c&&(e[a]=c[a])}),c.params=c.params||{},c.params.flashvars=a.merge(c.params.flashvars,e),d.superclass.constructor.call(this,b,c)}return a.extend(d,c),c.augment(d,["setFileFilters","filter","setAllowMultipleFiles","multifile","browse","upload","uploadAll","cancel","getFile","removeFile","lock","unlock","setBtnMode","useHand","clear"]),d.version="1.0.1",c.Uploader=d,c.Uploader},{requires:["gallery/flash/1.0/index","./ajbridge"]});