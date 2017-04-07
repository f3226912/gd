
	
function save() {

	if($("#addForm").form('validate')){
		var marketName= $('#addForm #marketName').val();
		if(marketName==null ||  $.trim(marketName)==""){
			warningMessage("街市名称不能为空!");
			return false;
		}
		var address=  $('#addForm #address').val();
		if(address ==null || $.trim(address)== ""){
			warningMessage("详细地址不能为空!");
			return false;
		}
		var marketShortName = $('#addForm #marketShortName').val();
		if(marketShortName ==null || $.trim(marketShortName)== ""){
			warningMessage("市场简称不能为空!");
			return false;
		}
		var dataurl;
		var img =$("[data-original-url]").each(function(){
			dataurl=$(this).attr("data-original-url");
		});
		if(dataurl==undefined||dataurl==""){
			warningMessage("请上传图片！");
			return false;
		}
		var marketArea = $('#addForm #marketArea').val();
		
		 if(GetLength(marketArea)>10){  
		        warningMessage("占地面积数据超过长度，请重新输入!");
		        return false;
		    }  
		var tradeAmount = $('#addForm #tradeAmount').val();
		 if(GetLength(tradeAmount)>10){  
		        warningMessage("年交易量数据超过长度，请重新输入!");
		        return false;
		    } 
		var tradeMoney = $('#addForm #tradeMoney').val();
		 if(GetLength(tradeMoney)>10){  
		        warningMessage("年交易额数据超过长度，请重新输入!");
		        return false;
		    } 
		var sort = $('#addForm #sort').val();
		 var reg =/^[0-9]{0,5}$/;
		 if(!reg.test(sort)){  
		        warningMessage("请输入0-99999之间的数字!");
		        return false;
		    }  
		var url=CONTEXT+"market/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#marketdg").datagrid('reload');
				$('#addDialog').dialog('close');
			}else if(data == "exists")
			{
				warningMessage("街市名称已存在，请重新输入！");
				return;
			}	
			else {
				warningMessage("系统异常！");
				return;
			}
		});
	}
	
	function GetLength(str) 
	{
	    var realLength = 0;
	    for (var i = 0; i < str.length; i++) 
	    {
	        charCode = str.charCodeAt(i);
	        if (charCode >= 0 && charCode <= 128) 
			realLength += 1;
	        else 
			realLength += 2;
	    }
	    return realLength;
	}


}