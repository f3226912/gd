/*
 * for select button
 * add the choosing area in userCenter
 * INFO : must in the userCenter, the authority calling is in this JavaScript AJAX function
 */

function City(province, city, area) {
	
	this.province = province;
	this.city = city;
	this.area = area;
	
	this.provinceId = 0;
	this.cityId = 0;
	this.areaId = 0;
	
	/*
	 * this function is for none of initial parameter situation.
	 */
	this.init = function() {
		var c = this;
		this.initProvince(this);
		$(this.province).bind("change",function() {c.changeProvinceToCity(c); });
		$(this.city).bind("change",function() { c.changeCityToArea(c); });
	};
	
	/*
	 * This function is for this situation when you need setting the initial parameters of privinceId , cityId and areaId
	 * Parameters must be type of String
	 * so if you want to use this function 
	 * add "'" single quotation for the parameters, please
	 */
	this.init = function(provinceId,cityId,areaId) {
		var c = this;
		
		this.provinceId = provinceId;
		this.cityId = cityId;
		this.areaId = areaId;
		
		this.initProvince(this);
		
		$(this.province).bind("change",function() {c.changeProvinceToCity(c); });
		$(this.city).bind("change",function() { c.changeCityToArea(c); });
	};
	
	this.bind = function() {
		var c = this;
		$(this.province).bind("change",function() {c.changeProvinceToCity(c); });
		$(this.city).bind("change",function() { c.changeCityToArea(c); });
	}
	
	/*
	 * Initialize province's data 
	 * It's the Inside method for init function
	 */
	this.initProvince = function(c){
		$.ajax({
			type: 'POST',
			url: CONTEXT+'common/queryProvince' ,
		    data: '' ,
		    dataType: 'json' ,
		    success: function(data) {
		    	var html = "";
		    	$(data).each(function(i,v) {
		    		if(c.provinceId=='0') {
			    		if(i==0) {
				    		html += "<option value='0' selected  >"+"请选择"+"</option>";
				    		html += "<option value='"+v.areaID+"'  >"+v.area+"</option>";
			    		} else {
				    		html += "<option value='"+v.areaID+"'>"+v.area+"</option>";
			    		}
		    		} else {
		    			if(c.provinceId==v.areaID) {
				    		html += "<option value='"+v.areaID+"' selected >"+v.area+"</option>";
			    		} else {
				    		html += "<option value='"+v.areaID+"'>"+v.area+"</option>";
			    		}
		    		}
		    		
		    	});
		    	c.province.html(html);

				c.changeProvinceToCity(c,1);
		    } ,
		    error: function(err) {
		    	alert('系统维护中。。。');
		    }
		});
	};
	
	/*
	 * Bind the onchange action to province button
	 * Inside method 
	 */
	this.changeProvinceToCity = function(c,first) {
		$.ajax({
			type: 'POST',
			url: CONTEXT+'common/queryCity/' + c.province.val() ,
		    data: '' ,
		    dataType: 'json' ,
		    success: function(data) {
		    	var html = "";
		    	if(data==''){
		    		html += "<option value='0' selected ></option>";
		    	}else{
		    		$(data).each(function(i,v) {
			    		if(c.cityId=='0') {
				    		if(i==0) {
					    		html += "<option value='0' selected  ></option>";
					    		html += "<option value='"+v.areaID+"' selected >"+v.area+"</option>";
				    		} else {
					    		html += "<option value='"+v.areaID+"'>"+v.area+"</option>";
				    		}
			    		}else {
			    			if(c.cityId==v.areaID) {
					    		html += "<option value='"+v.areaID+"' selected >"+v.area+"</option>";
				    		} else {
					    		html += "<option value='"+v.areaID+"'>"+v.area+"</option>";
				    		}
			    		}
			    		
			    	});
		    	}
		    	c.city.html(html);
		    	c.changeCityToArea(c);
		    } ,
		    error: function(err) {
		    	alert('系统维护中。。。');
		    }
		});
	};
	
	/*
	 * Bind the onchange action to city button
	 * Inside method 
	 */
	this.changeCityToArea = function(c) {
		$.ajax({
			type: 'POST',
			url: CONTEXT+'common/queryArea/' + c.city.val() ,
		    data: '' ,
		    dataType: 'json' ,
		    success: function(data) {
		    	var html = "";
		    	if(data==''){
		    		html += "<option value='0' selected ></option>";
		    	}else{
		    		$(data).each(function(i,v) {
			    		if(c.areaId=='0') {
				    		if(i==0) {
					    		html += "<option value='0' selected  ></option>";
					    		html += "<option value='"+v.areaID+"' selected >"+v.area+"</option>";
				    		} else {
					    		html += "<option value='"+v.areaID+"'>"+v.area+"</option>";
				    		}
			    		} else {
			    			if(c.areaId==v.areaID) {
					    		html += "<option value='"+v.areaID+"' selected >"+v.area+"</option>";
				    		} else {
					    		html += "<option value='"+v.areaID+"'>"+v.area+"</option>";
				    		}
			    		}
			    	});
		    	}
		    	c.area.html(html);
		    } ,
		    error: function(err) {
		    	alert(err);
		    }
		});
	};
	
}