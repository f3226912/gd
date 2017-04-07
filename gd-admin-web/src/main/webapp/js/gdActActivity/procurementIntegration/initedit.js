$(document).ready(function() {
	debugger;
	initMaketComm();
	initSub();
	//initRuleList();
	initUserRule();
	//isReverseCk();
})

function initMaketComm() {
	debugger;
	if(buyerArray && buyerArray.length>0) {
		var buyerdata = buyerArray[0];

		var details=buyerdata.detail;
		if(buyerdata.dayMaxIntegral!=-1){
			$("#dayIntegralBox").attr("checked","checked");
			$("#dayMaxIntegral").val(buyerdata.dayMaxIntegral)
			$("#dayMaxIntegral").removeAttr("disabled");
		}
		if(buyerdata.minIntegral!=0){
			$("#minIntegralBox").attr("checked","checked");
			$("#minIntegral").val(buyerdata.minIntegral);
			$("#minIntegral").removeAttr("disabled");

			
		}
		
		$('#buyer_tb_comm tr:last').find("#startAmt").val(details[0].startAmt);
		$('#buyer_tb_comm tr:last').find("#endAmt").val(details[0].endAmt==-1?"":details[0].endAmt);
		$('#buyer_tb_comm tr:last').find("#integral").val(details[0].integral);
		for(var i = 0; i < (details.length-1); i++) {
			addMoreRule("buyer_tb_comm");
			$('#buyer_tb_comm tr:last').find("#startAmt").val(details[i+1].startAmt);
			$('#buyer_tb_comm tr:last').find("#endAmt").val(details[i+1].endAmt==-1?"":details[i+1].endAmt);
			$('#buyer_tb_comm tr:last').find("#integral").val(details[i+1].integral);
		}
	}
	
	
}

function initSub() {
	
	
}

function initUserRule() {
    debugger;
	 $("#integralRate").val(buyerRules.integralRate);
	 if(buyerRules.integralRate!=undefined&&buyerRules.integralRate!=""&&buyerRules.integralRate!=0){
		 $("#buyerBox0").attr("checked","checked");
		 $("#integralRate").removeAttr("disabled");
	 }
	 var other=buyerRules.other;
	 var view =$("#hid_view").val();
	for (var key in other)
    {
		addUserRule("tb_userRule");
		indexArray.push(value2-1);
	    for(var key2 in other[key]){
	    	for(var i=0;i<other[key][key2].length;i++){
	    		$("#integralRate"+(value2-1)).val(key2);
		    	addBuyerDataJson(other[key][key2][i],value2-1);
                //勾选指定买家前面的checkBox
		    	 $("#buyerBox"+(value2-1)).attr("checked","checked");
		    	  if(view.length>0){
		    	
		    			    $("#tjmj"+(value2-1)).attr("disabled","disabled");
		    		
		    				  $("#integralRate"+(value2-1)).attr("disabled","disabled");
		    			
		    				  $("#delRow"+(value2-1)).remove();
		    			
		    				  $("#buyerBox"+(value2-1)).attr("disabled","disabled");
		    		
		    	  }else{
		    		   $("#tjmj"+(value2-1)).removeAttr("disabled");
			    		
	    				  $("#integralRate"+(value2-1)).removeAttr("disabled"); 
		    	  }
		    }
	    }
     }

	


	//initCategory();
	//addBuyerData = buyerRules;
}
