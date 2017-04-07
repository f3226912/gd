$(function() {

	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
	$('#province').combobox({
		url : 'market/queryProvince',
		editable : true, // 不可编辑状态
		cache : false,
		// panelHeight: 'auto',//自动高度适合
		valueField : 'areaID',
		textField : 'area',

		onSelect : function(rec) {
			$('#province').val(rec.areaID);
		    $("#city").combobox("setValue", '');
			$("#county").combobox("setValue", '');
			$("#cregicounty").val('');
			var province = $('#province').combobox('getValue');
			if (province != '') {
				$.ajax({
					type : "POST",
					url : "market/queryCity.do?province=" + province,
					cache : false,
					dataType : "json",
					success : function(data) {
						$("#city").combobox("loadData", data);
					}
				});
			}
		},onLoadSuccess : function(){
        	var v = $('#province').val();
        	$('#province').combobox('select', v);
        }
	});

	$('#city').combobox({
		url : 'market/queryCity.do?province=' + $('#province').val(),
		editable : true, // 不可编辑状态
		cache : false,
		// panelHeight: 'auto',//自动高度适合
		valueField : 'areaID',
		textField : 'area',
		onSelect : function(rec) {
			$('#city').val(rec.areaID);
			$("#cregicounty").val('');
			$("#county").combobox("setValue", '');
			var city = $('#city').combobox('getValue');
			if (city != '') {
				$.ajax({
					type : "POST",
					url : "market/queryCounty.do?city=" + city,
					cache : false,
					dataType : "json",
					success : function(data) {
						$("#county").combobox("loadData", data);
					}
				});
			}
		},onLoadSuccess : function(){
        	var v = $('#city').val();
        	if(v =='0')                                         
            $('#city').combobox('select', ''); 
        	//$('#city').combobox('select', v);
        }
	});
	$('#county').combobox({
		url : 'market/queryCounty.do?city=' + $('#city').val(),
		editable : true, // 不可编辑状态
		cache : false,
		// panelHeight: 'auto',//自动高度适合
		valueField : 'areaID',
		textField : 'area',
	  	onSelect : function(rec) {
			$('#county').val(rec.areaID);
			var str = $('#county').combobox('getText');
			$("#cregicounty").val(str);
		},onLoadSuccess : function(){
        	var v = $('#county').val();
        	if(v =='0')                                         
            $('#county').combobox('select', ''); 
        	//$('#county').combobox('select', v);
        }
	});

});