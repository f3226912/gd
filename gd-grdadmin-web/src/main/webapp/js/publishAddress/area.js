//始发地
$(function(){
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#start_provinceId').combobox({ 
			    url:'queryProvince?type=start',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#start_provinceId').val(rec.areaID);
		    $('#start_cityId').val('');
        	$('#start_areaId').val('');
		    $("#start_cityId").combobox("setValue",'');
		    $("#start_areaId").combobox("setValue",'');
		   

			var province = $('#start_provinceId').combobox('getValue');	
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#start_cityId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#start_provinceId').val();
                     	if(v =='1' )
                        $('#start_provinceId').combobox('select', '');
                        else
                    	$('#start_provinceId').combobox('select', v);
                    }
                 }); 
	
	$('#start_cityId').combobox({ 
		url : 'queryCity.do?province=' + $('#start_provinceId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#start_cityId').val(rec.areaID);
			$('#start_areaId').val('');
		    $("#start_areaId").combobox("setValue",'');
			var city = $('#start_cityId').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#start_areaId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#start_cityId').val();
                     	//alert("start_cityId ="+v);
                     	if(v =='0' || v =='1' )
                        $('#start_cityId').combobox('select', '');
                        //else
                        //$('#start_cityId').combobox('select', v);
                    }
	   });  
	$('#start_areaId').combobox({ 
		url : 'queryCounty.do?city=' + $('#start_cityId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#start_areaId').val(rec.areaID);
         var str=$('#start_areaId').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#start_areaId').val();
            if(v =='0' || v =='1' )
        	$('#start_areaId').combobox('select', '');
//        	else
//        	$('#start_areaId').combobox('select', v);
        }
	 });  

});

//目的地
$(function(){
	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
 	$('#end_provinceId').combobox({ 
			    url:'queryProvince',
			    editable:false, //不可编辑状态
			    cache: false,
			   // panelHeight: 'auto',//自动高度适合
			    valueField:'areaID',   
			    textField:'area',
			    
           onSelect : function(rec) {
		    $('#end_provinceId').val(rec.areaID);
		    $('#end_cityId').val('');
        	$('#end_areaId').val('');
		    $("#end_cityId").combobox("setValue",'');
		    $("#end_areaId").combobox("setValue",'');
			var province = $('#end_provinceId').combobox('getValue');		
			if(province!=''){
			$.ajax({
				type: "POST",
				url: "queryCity.do?province="+province,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#end_cityId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     } ,onLoadSuccess : function(){
                     	var v = $('#end_provinceId').val();
                    	if(v =='1' )
                        $('#end_provinceId').combobox('select', '');
                        else
                    	$('#end_provinceId').combobox('select', v);
                    }
                 }); 
	
	$('#end_cityId').combobox({ 
		url : 'queryCity.do?province=' + $('#end_provinceId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	    //panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
	    onSelect : function(rec) {
			$('#end_cityId').val(rec.areaID);
			$('#end_areaId').val('');
		    $("#end_areaId").combobox("setValue",'');
			var city = $('#end_cityId').combobox('getValue');		
			if(city!=''){
			$.ajax({
				type: "POST",
				url: "queryCounty.do?city="+city,
				cache: false,
				dataType : "json",
				success: function(data){
				$("#end_areaId").combobox("loadData",data);
		                               }
	                               }); 	
                           }
                     },onLoadSuccess : function(){
                     	var v = $('#end_cityId').val();
                     	if(v =='0' || v =='1' )
                        $('#end_cityId').combobox('select', '');
//                        else
//                    	$('#end_cityId').combobox('select', v);
                    }
	   });  
	$('#end_areaId').combobox({ 
		url : 'queryCounty.do?city=' + $('#end_cityId').val(),
	    editable:false, //不可编辑状态
	    cache: false,
	   // panelHeight: 'auto',//自动高度适合
	    valueField:'areaID',   
	    textField:'area',
		onSelect : function(rec) {
		 $('#end_areaId').val(rec.areaID);
         var str=$('#end_areaId').combobox('getText');
		    $("#cregicounty").val(str);	
          },onLoadSuccess : function(){
        	var v = $('#end_areaId').val();
        	if(v =='0' || v =='1' )
        	$('#end_areaId').combobox('select', '');
//        	else
//        	$('#end_areaId').combobox('select', v);
        }
	 });  

});
