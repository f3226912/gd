function clickGuize(){
	$(".markAll").show();
}

function guizeClose(){
	$(".markAll").hide();
}
   
 //倒计时
	        function getRTime() {
	        	function checkTime(i){
	/*         		if(i<0){
	        			i=i*-1;
	        		}
					if(i<10){
						i="0"+i;
					} */
					return i;
				}
	            var now = new Date().getTime();
	        	var date2 = Date.parse('2016/08/18 09:00:00')
	        	var t = date2-now;
	        	if(t>=0){
	        		var d=Math.floor(t/1000/60/60/24);
	                var h=Math.floor(t/1000/60/60%24);
	                var m=Math.floor(t/1000/60%60);
	                var s=Math.floor(t/1000%60);
	                $('.day').html(checkTime(d));
	                $('.hour').html(checkTime(h));
	                $('.fen').html(checkTime(m));
	                $('.second').html(checkTime(s));
	               var timer = setTimeout(getRTime,1000)
	               $('.bg-01 .daojishi img').show();
	               $('.bg-01 .daojishi .time').show();
	                
	        	}else{
	        		$('.daojishi').css({'display':'none'});
	        		clearTimeout(timer)
	        	}

	        }
        setTimeout(getRTime,1000);
