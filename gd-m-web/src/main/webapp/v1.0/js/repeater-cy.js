
/**
 * CY 2016-6-13
 * 首页
 */

var repeater = function(opts){
    this.opts= opts;
    this.$ele = $(this.opts.ele); 
    //console.log(this.opts)
};
repeater.prototype = {
    //清空html
    empty:function(){
        this.$ele.html("");
    },
    //暂无数据
    noMore:function(){
        this.$ele.html('<div class="nomore">暂无数据</div>');
    },
    //正在加载
    loading:function(){
    	this.$ele.html('<div class="nomore">正在加载...</div>');
    },
    //追加数据
    appendHtml:function(data){
    	this.$ele.append(Mustache.render(this.opts.templete, data))
    },
    // setData:function(){

    // },
    // action:function(){

    // },
    //渲染HTML
    render:function(data){
        if(!data){ data=this.opts.dataDefault;}

        this.$ele.html(Mustache.render(this.opts.templete, data));

        //执行回调函数
        if(this.opts.callback){
            this.opts.callback();
        }
    }
};

