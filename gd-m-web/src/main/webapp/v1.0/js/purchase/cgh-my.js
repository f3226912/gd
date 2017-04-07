var userId=getQueryString('userId');
  $.ajax( {
	  url:CONTEXT + 'activityPromotion/activity/list',
       /* url:'/gd-api/activityPromotion/activity/list',*/
        data:{"pageNum":1,"pageSize":100},  
        type:'post',  
        cache:false,  
        dataType:'json',  
        success:function(data) {  
        	if(data.statusCode==0){
        		var ob=data.object.recordList;
	            	writeIn(ob);
	            	status(ob);
        	}else{
        		alert(data.msg);
        	}
            },  
        error : function() {   
               alert("异常！");  
            }  
     });


//把状态确定
function status(ob,date2){
	console.log(ob);
	//倒计时
    var date1=new Date().getTime();//现在的时间
	for(var i=0;i<ob.length;i++){
		var rstatus=ob[i].rstatus;
		if(rstatus=="3"){//进行中
			$(".myChanxiao>li>.header").eq(i).addClass("orage");
			$(".myChanxiao>li>.header>.right").eq(i).html("进行中");
			//倒计时运算方法
			var endTime=ob[i].endTime;
			endTime=endTime.replace(/-/g,"/");
			var date2=Date.parse(endTime);//结束的时间
			countdown((date2-date1)/1000,i);
		}else{
			if(rstatus=="2"){
				$(".myChanxiao>li>.header").eq(i).addClass("green");
				$(".myChanxiao>li>.header>.right").eq(i).html("未开始");
				//倒计时运算方法
				var date2=Date.parse(ob[i].endTime);//结束的时间
				countdown((date2-date1)/1000,i);
			}else{
				if(rstatus=="1"){
					$(".myChanxiao>li>.header").eq(i).html("<span class='right'>已结束</span>") ;
					$(".myChanxiao>li>.header").eq(i).removeClass("icon-time").addClass("ash");
				}
			}
		}
	}
}

//把数据循环输出到页面
function writeIn(ob){
	var mainHtml='';
	for(var i=0;i<ob.length;i++){
		mainHtml=mainHtml+Strings(ob[i]);
	}
	$(".myChanxiao").html(mainHtml);
}

function countdown(s,i){
	var h=Math.floor(s/60/60),
			m=Math.floor((s-(h*60*60))/60),
			newS=Math.floor(s-(h*60*60)-(m*60));
	$(".downTime").eq(i).html(h+':'+m+':'+newS);
	var timer=setInterval(function(){//这是倒计时循环部分
		newS--;
		if(newS<0){
			newS=59;
			m--;
		}
		if(m<0){
			m=59;
			h--;
		}
		h=fix(h);
		m=fix(m);
		newS=fix(newS);
		$(".downTime").eq(i).html(h+':'+m+':'+newS);
	},1000);
	
}

//把数据放到一个变量里
function Strings(ob){
	//图片的循环
	var picture=ob.pictureRefList;
	var img='',
		  imgs='';
	for(var i=0;i<picture.length;i++){
		img='<div class="swiper-slide"><img src="'+picture[i].url120+'"/></div>'
		imgs=imgs+img;
	}
	
	var mainString='<li>'
			+'<div class="iconfont icon-time header">距离结束时间:<span class="downTime"></span><span class="right"></span></div>'
			+	'<div class="con">'
			+		'<div class="imgs">'
			+			'<div class="swiper">'
			+				'<div class="swiper-container">'
			+			        '<div class="swiper-wrapper">'+imgs
			+			        '</div>'
			+			    '</div>'
			+			'</div>'
			+		'</div>'
			+		'<ul>'
			+			'<li class="header">'+ob.name+'</li>'
			+			'<li>开始时间:'+ob.createTime+'</li>'
			+			'<li>结束时间:'+ob.endTime+'</li>'
			+			'<li>活动规则:'+ob.introduction+'</li>'
			+		'</ul>'
			+	'</div>'
			+	bolUrl(ob)
			+'</li>';
		return mainString;
}

//判断展销会还是我的展销会的跳转详情页
function bolUrl(ob){
	var oUrl='';
	oUrl='<a href="' + CONTEXT + '/purchase/promotionDetail?actId='+ob.actId+'&userId='+userId+'" class="footer">查看详情</a>'

		/*if(!userId){
			oUrl='<a href="/PromotionAcitivity/promotionDetail?actId='+ob.actId+'" class="footer">查看详情</a>'
		}else{
			oUrl='<a href="/PromotionAcitivity/showDetail?actId='+ob.actId+'&userId='+userId+'" class="footer">查看详情</a>'
		}*/
	return oUrl;
}
