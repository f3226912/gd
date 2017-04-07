//始发地
$(function(){
	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#s_provinceId').combobox({ 
			    url:'carLine/queryProvince?type=start',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#s_provinceId').val(rec.areaID);
		    $("#s_cityId").combobox("setValue",'');
		    $("#s_areaId").combobox("setValue",'');
			var province = $('#s_provinceId').combobox('getValue');		
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#s_cityId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#s_provinceId').val();
                     	if(v =='1' )
                        $('#s_provinceId').combobox('select', '');
                     	else
                    	$('#s_provinceId').combobox('select', v);
                    }
                 }); 
	
	$('#s_cityId').combobox({ 
		url : 'carLine/queryCity.do?province=' + $('#s_provinceId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#s_cityId').val(rec.areaID);
		    $("#s_areaId").combobox("setValue",'');
			var city = $('#s_cityId').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#s_areaId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#s_cityId').val();
                     	if(v =='0' || v =='1' )
                        $('#s_cityId').combobox('select', '');
                    	//$('#s_cityId').combobox('select', v);
                    }
	   });  
	$('#s_areaId').combobox({ 
		url : 'carLine/queryCounty.do?city=' + $('#s_cityId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#s_areaId').val(rec.areaID);
         var str=$('#s_areaId').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#s_areaId').val();
        	if(v =='0' || v =='1' )
            $('#s_areaId').combobox('select', '');
        	//$('#s_areaId').combobox('select', v);
        }
	 });  

});


//始发地2                                                                  
$(function(){                                                             
	                                                                        
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息                     
 	$('#s_provinceId2').combobox({                                       
	            url:'carLine/queryProvince?type=start',
			    editable:false, //不可编辑状态                                  
			    cache: false,                                                   
			   // panelHeight: 'auto',//自动高度适合                            
			    valueField:'areaID',                                            
			    textField:'area',                                               
			                                                                    
           onSelect : function(rec) {                                     
		    $('#s_provinceId2').val(rec.areaID);                           
		    $("#s_cityId2").combobox("setValue",'');                       
		    $("#s_areaId2").combobox("setValue",'');                       
			var province = $('#s_provinceId2').combobox('getValue');		      
			if(province!=''){                                                   
			$.ajax({                                                            
				type: "POST",                                                     
				url: "carLine/queryCity.do?province="+province,                   
				cache: false,                                                     
				dataType : "json",                                                
				success: function(data){                                          
				$("#s_cityId2").combobox("loadData",data);                     
		                               }                                      
	                               }); 	                                    
                           }                                              
                     } ,onLoadSuccess : function(){                       
                     	var v = $('#s_provinceId2').val();   
                     	if(v =='1' )
                        $('#s_provinceId2').combobox('select', '');
                         else
                    	$('#s_provinceId2').combobox('select', v);       
                    }                                                     
                 });                                                      
	                                                                        
	$('#s_cityId2').combobox({                                           
		url : 'carLine/queryCity.do?province=' + $('#s_provinceId2').val(),
	    editable:false, //不可编辑状态                                      
	    cache: false,                                                       
	    //panelHeight: 'auto',//自动高度适合                                
	    valueField:'areaID',                                                
	    textField:'area',                                                   
	    onSelect : function(rec) {                                          
			$('#s_cityId2').val(rec.areaID);                                 
		    $("#s_areaId2").combobox("setValue",'');                       
			var city = $('#s_cityId2').combobox('getValue');		              
			if(city!=''){                                                       
			$.ajax({                                                            
				type: "POST",                                                     
				url: "carLine/queryCounty.do?city="+city,                         
				cache: false,                                                     
				dataType : "json",                                                
				success: function(data){                                          
				$("#s_areaId2").combobox("loadData",data);                     
		                               }                                      
	                               }); 	                                    
                           }                                              
                     },onLoadSuccess : function(){                        
                     	var v = $('#s_cityId2').val();                   
                     	if(v =='0' || v =='1' )                                         
                        $('#s_cityId2').combobox('select', '');        
                    	//$('#s_cityId2').combobox('select', v);         
                    }                                                     
	   });                                                                  
	$('#s_areaId2').combobox({                                           
		url : 'carLine/queryCounty.do?city=' + $('#s_cityId2').val(),      
	    editable:false, //不可编辑状态                                      
	    cache: false,                                                       
	   // panelHeight: 'auto',//自动高度适合                                
	    valueField:'areaID',                                                
	    textField:'area',                                                   
		onSelect : function(rec) {                                            
		 $('#s_areaId2').val(rec.areaID);                                  
         var str=$('#s_areaId2').combobox('getText');                  
		    $("#cregicounty").val(str);	                                      
          },onLoadSuccess : function(){                                   
        	var v = $('#s_areaId2').val();                               
        	if(v =='0' || v =='1' )                                                     
            $('#s_areaId2').combobox('select', '');                    
        	//$('#s_areaId2').combobox('select', v);                     
        }                                                                 
	 });                                                                    
                                                                          
});                                                                       




//始发地3                                                                  
$(function(){                                                             
	                                                                        
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息                     
 	$('#s_provinceId3').combobox({                                       
	            url:'carLine/queryProvince?type=start',
	            editable:false, //不可编辑状态     
			    cache: false,                                                   
			   // panelHeight: 'auto',//自动高度适合                            
			    valueField:'areaID',                                            
			    textField:'area',                                               
			                                                                    
           onSelect : function(rec) {                                     
		    $('#s_provinceId3').val(rec.areaID);                           
		    $("#s_cityId3").combobox("setValue",'');                       
		    $("#s_areaId3").combobox("setValue",'');                       
			var province = $('#s_provinceId3').combobox('getValue');		      
			if(province!=''){                                                   
			$.ajax({                                                            
				type: "POST",                                                     
				url: "carLine/queryCity.do?province="+province,                   
				cache: false,                                                     
				dataType : "json",                                                
				success: function(data){                                          
				$("#s_cityId3").combobox("loadData",data);                     
		                               }                                      
	                               }); 	                                    
                           }                                              
                     } ,onLoadSuccess : function(){                       
                     	var v = $('#s_provinceId3').val();   
                     	if(v =='1' )
                        $('#s_provinceId3').combobox('select', '');
                        else
                    	$('#s_provinceId3').combobox('select', v);       
                    }                                                     
                 });                                                      
	                                                                        
	$('#s_cityId3').combobox({                                           
		url : 'carLine/queryCity.do?province=' + $('#s_provinceId3').val(),
	    editable:false, //不可编辑状态                                      
	    cache: false,                                                       
	    //panelHeight: 'auto',//自动高度适合                                
	    valueField:'areaID',                                                
	    textField:'area',                                                   
	    onSelect : function(rec) {                                          
			$('#s_cityId3').val(rec.areaID);                                 
		    $("#s_areaId3").combobox("setValue",'');                       
			var city = $('#s_cityId3').combobox('getValue');		              
			if(city!=''){                                                       
			$.ajax({                                                            
				type: "POST",                                                     
				url: "carLine/queryCounty.do?city="+city,                         
				cache: false,                                                     
				dataType : "json",                                                
				success: function(data){                                          
				$("#s_areaId3").combobox("loadData",data);                     
		                               }                                      
	                               }); 	                                    
                           }                                              
                     },onLoadSuccess : function(){                        
                     	var v = $('#s_cityId3').val();                   
                     	if(v =='0' || v =='1' )                                         
                        $('#s_cityId3').combobox('select', '');        
                    	//$('#s_cityId3').combobox('select', v);         
                    }                                                     
	   });                                                                  
	$('#s_areaId3').combobox({                                           
		url : 'carLine/queryCounty.do?city=' + $('#s_cityId3').val(),      
	    editable:false, //不可编辑状态                                      
	    cache: false,                                                       
	   // panelHeight: 'auto',//自动高度适合                                
	    valueField:'areaID',                                                
	    textField:'area',                                                   
		onSelect : function(rec) {                                            
		 $('#s_areaId3').val(rec.areaID);                                  
         var str=$('#s_areaId3').combobox('getText');                  
		    $("#cregicounty").val(str);	                                      
          },onLoadSuccess : function(){                                   
        	var v = $('#s_areaId3').val();                               
        	if(v =='0' || v =='1' )                                                     
            $('#s_areaId3').combobox('select', '');                    
        	//$('#s_areaId3').combobox('select', v);                     
        }                                                                 
	 });                                                                    
                                                                          
});                                                                       

//目的地1
$(function(){
	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#e_provinceId').combobox({ 
			    url:'carLine/queryProvince',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#e_provinceId').val(rec.areaID);
		    $("#e_cityId").combobox("setValue",'');
		    $("#e_areaId").combobox("setValue",'');
			var province = $('#e_provinceId').combobox('getValue');		
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_cityId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#e_provinceId').val();
                    	if(v =='1' )
                        $('#e_provinceId').combobox('select', '');
                        else
                    	$('#e_provinceId').combobox('select', v);
                    }
                 }); 
	
	$('#e_cityId').combobox({ 
		url : 'carLine/queryCity.do?province=' + $('#e_provinceId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#e_cityId').val(rec.areaID);
		    $("#e_areaId").combobox("setValue",'');
			var city = $('#e_cityId').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_areaId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#e_cityId').val();
                     	if(v =='0' || v =='1' )
                        $('#e_cityId').combobox('select', '');
                    	//$('#e_cityId').combobox('select', v);
                    }
	   });  
	$('#e_areaId').combobox({ 
		url : 'carLine/queryCounty.do?city=' + $('#e_cityId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#e_areaId').val(rec.areaID);
         var str=$('#e_areaId').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#e_areaId').val();
        	if(v =='0' || v =='1' )
            $('#e_areaId').combobox('select', '');
        	//$('#e_areaId').combobox('select', v);
        }
	 });  

});


//目的地2
$(function(){
	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#e_provinceId2').combobox({ 
			    url:'carLine/queryProvince',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#e_provinceId2').val(rec.areaID);
		    $("#e_cityId2").combobox("setValue",'');
		    $("#e_areaId2").combobox("setValue",'');
			var province = $('#e_provinceId2').combobox('getValue');		
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_cityId2").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#e_provinceId2').val();
                     	if(v =='1' )
                         $('#e_provinceId2').combobox('select', '');
                         else
                     	$('#e_provinceId2').combobox('select', v);
                    }
                 }); 
	
	$('#e_cityId2').combobox({ 
		url : 'carLine/queryCity.do?province=' + $('#e_provinceId2').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#e_cityId2').val(rec.areaID);
		    $("#e_areaId2").combobox("setValue",'');
			var city = $('#e_cityId2').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_areaId2").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#e_cityId2').val();
                     	if(v =='0' || v =='1' )
                        $('#e_cityId2').combobox('select', '');
                    	//$('#e_cityId2').combobox('select', v);
                    }
	   });  
	$('#e_areaId2').combobox({ 
		url : 'carLine/queryCounty.do?city=' + $('#e_cityId2').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#e_areaId2').val(rec.areaID);
         var str=$('#e_areaId2').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#e_areaId2').val();
        	if(v =='0' || v =='1' )
            $('#e_areaId2').combobox('select', '');
        	//$('#e_areaId2').combobox('select', v);
        }
	 });  

});


//目的地3
$(function(){
	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#e_provinceId3').combobox({ 
			    url:'carLine/queryProvince',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#e_provinceId3').val(rec.areaID);
		    $("#e_cityId3").combobox("setValue",'');
		    $("#e_areaId3").combobox("setValue",'');
			var province = $('#e_provinceId3').combobox('getValue');		
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_cityId3").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#e_provinceId3').val();
                     	if(v =='1' )
                        $('#e_provinceId3').combobox('select', '');
                         else
                    	$('#e_provinceId3').combobox('select', v);
                    }
                 }); 
	
	$('#e_cityId3').combobox({ 
		url : 'carLine/queryCity.do?province=' + $('#e_provinceId3').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#e_cityId3').val(rec.areaID);
		    $("#e_areaId3").combobox("setValue",'');
			var city = $('#e_cityId3').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_areaId3").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#e_cityId3').val();
                     	if(v =='0' || v =='1' )
                        $('#e_cityId3').combobox('select', '');
                    	//$('#e_cityId3').combobox('select', v);
                    }
	   });  
	$('#e_areaId3').combobox({ 
		url : 'carLine/queryCounty.do?city=' + $('#e_cityId3').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#e_areaId3').val(rec.areaID);
         var str=$('#e_areaId3').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#e_areaId3').val();
        	if(v =='0' || v =='1' )
            $('#e_areaId3').combobox('select', '');
        	//$('#e_areaId3').combobox('select', v);
        }
	 });  

});


//目的地4
$(function(){
	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#e_provinceId4').combobox({ 
			    url:'carLine/queryProvince',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#e_provinceId4').val(rec.areaID);
		    $("#e_cityId4").combobox("setValue",'');
		    $("#e_areaId4").combobox("setValue",'');
			var province = $('#e_provinceId4').combobox('getValue');		
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_cityId4").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#e_provinceId4').val();
                     	if(v =='1' )
                         $('#e_provinceId4').combobox('select', '');
                         else
                    	$('#e_provinceId4').combobox('select', v);
                    }
                 }); 
	
	$('#e_cityId4').combobox({ 
		url : 'carLine/queryCity.do?province=' + $('#e_provinceId4').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#e_cityId4').val(rec.areaID);
		    $("#e_areaId4").combobox("setValue",'');
			var city = $('#e_cityId4').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_areaId4").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#e_cityId4').val();
                     	if(v =='0' || v =='1' )
                        $('#e_cityId4').combobox('select', '');
                    	//$('#e_cityId4').combobox('select', v);
                    }
	   });  
	$('#e_areaId4').combobox({ 
		url : 'carLine/queryCounty.do?city=' + $('#e_cityId4').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#e_areaId4').val(rec.areaID);
         var str=$('#e_areaId4').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#e_areaId4').val();
        	if(v =='0' || v =='1' )
            $('#e_areaId4').combobox('select', '');
        	//$('#e_areaId4').combobox('select', v);
        }
	 });  

});

//目的地5
$(function(){
	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#e_provinceId5').combobox({ 
			    url:'carLine/queryProvince',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#e_provinceId5').val(rec.areaID);
		    $("#e_cityId5").combobox("setValue",'');
		    $("#e_areaId5").combobox("setValue",'');
			var province = $('#e_provinceId5').combobox('getValue');		
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_cityId5").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#e_provinceId5').val();
                     	if(v =='1' )
                        $('#e_provinceId5').combobox('select', '');
                        else
                    	$('#e_provinceId5').combobox('select', v);
                    }
                 }); 
	
	$('#e_cityId5').combobox({ 
		url : 'carLine/queryCity.do?province=' + $('#e_provinceId5').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#e_cityId5').val(rec.areaID);
		    $("#e_areaId5").combobox("setValue",'');
			var city = $('#e_cityId5').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "carLine/queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#e_areaId5").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#e_cityId5').val();
                     	if(v =='0' || v =='1' )
                        $('#e_cityId5').combobox('select', '');
                    	//$('#e_cityId5').combobox('select', v);
                    }
	   });  
	$('#e_areaId5').combobox({ 
		url : 'carLine/queryCounty.do?city=' + $('#e_cityId5').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#e_areaId5').val(rec.areaID);
         var str=$('#e_areaId5').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#e_areaId5').val();
        	if(v =='0' || v =='1' )
            $('#e_areaId5').combobox('select', '');
        	//$('#e_areaId5').combobox('select', v);
        }
	 });  

});

