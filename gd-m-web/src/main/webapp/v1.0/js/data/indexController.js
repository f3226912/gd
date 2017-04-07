
/**
 * CY 2016-6-13
 * 首页
 */

(function($){
    
    var urlData = common.getUrlData();
    
    
    // html 字符串
    var strHtml = '<table width="100%">'
    +    '<tr>'
    +       '<td onclick="todayClick(1)" style="border-bottom:1px dashed #fff;border-right:1px dashed #fff;"><span>今日订单量</span><br/><em>{{orderNumToday}}</em><span>笔</span></td>'
    +       '<td onclick="todayClick(1)" style="border-bottom:1px dashed #fff;"><span>今日交易额</span><br/><em>{{tradeAmtToday}}</em><span>元</span></td>'
    +    '</tr>'
    +    '<tr>'
    +        '<td onclick="todayClick(2)" style="border-right:1px dashed #fff;"><span>昨日交易额</span><br/><em>{{tradeAmtYestoday}}</em><span>元</span></td>'
    +        '<td onclick="todayClick(3)"><span>本月累计交易额</span><br/><em>{{tradeAmtMonth}}</em><span>元</span></td>'
    +    '</tr>'
    +'</table>'
    

    //请求数据   
    $.ajax({
         type: "get",
         url: "index",
         data:urlData,
         dataType: "JSON",
         success: function(data){
        	 
        	var oData = data["datajson"];
            //数值检测
            for (var i in oData){
            	oData[i] = processNumFormat(oData[i]);
                
            };
            
            //渲染HTML
            var indexTplt = new repeater({
                ele:'.data-area',
                templete:strHtml,
                dataDefault:oData
            });
            indexTplt.render();
         }
     });
    
    //数值处理
    function processNumFormat(num){
        //是否大于等于百万
        if(num<1000000){
            return num;
        }
        
        //大于百万小于千万 两位小数，不四舍五入
        if(num<10000000 && num>=1000000){
            var temp = num/1000000;
            temp = temp.toFixed(3);
            temp = temp.substring(0,temp.lastIndexOf('.')+3); 
            return temp+'百万';
        }

        //大于一千万 两位小数，不四舍五入
        if(num>=10000000){
            var temp = num/10000000;
            temp = temp.toFixed(3);
            temp = temp.substring(0,temp.lastIndexOf('.')+3); 
            return temp+'千万';
        }else{
            return null;
        }
        
    };
})($)
