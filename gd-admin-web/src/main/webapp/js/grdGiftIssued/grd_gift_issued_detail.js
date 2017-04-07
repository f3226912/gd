$(function() {
	var carImgUploadPath = CONTEXT + 'grdGiftIssued/uploadCarPic';
	// 车辆图片一
	initUploadSignle("uploadFirstBtn", carImgUploadPath, "pictureShowFirst",
			"urlWshowImgFirst");
	// 车辆图片二
	initUploadSignle("uploadSecondBtn", carImgUploadPath, "pictureShowSecond",
			"urlWshowImgSecond");
	// 车辆图片三
	initUploadSignle("uploadThirdBtn", carImgUploadPath, "pictureShowThird",
			"urlWshowImgThird");

	// 礼品数量统计
	calculateGiftAmount();

	// 礼品金额统计
	calculateGiftPriceAmount();
   //礼品数量统计
   calculateGiftBusinessAmount();
	// 显示车牌号码
	$("#popWinCarNo").val(carNo);
});

/** 单图上传* */
function initUploadSignle(uploadBtn, uploadUrl, queueId, JUrls,
		pictureCountLimit, filesStr) {
	var config_mutiple = {
		galleryUrl : CONTEXT + "js/uploader/",// 控件静态地址
		uploadBtn : uploadBtn,// 初始的button
		queueId : queueId,// 图片装载容器id
		JUrls : JUrls,// 成功后返回url参数写入id
		action : uploadUrl,// 上传地址
		maxSize : 2048,
		imgUrlPrefix : imgHostUrl,
		max : pictureCountLimit,// 允许上传个数
		type : "ajax",// 上传类型
		filesStr : filesStr,// 此处是附加参数，已经上传需要第二次修的图片参数
	};
	gdKissySingleUploader.init(config_mutiple, function(uploader) {
		var filesStr = config_mutiple.filesStr;
		var certification = config_mutiple.certification;
		if (filesStr != '' && filesStr != undefined) {

			gdKissySingleUploader.addFiles(filesStr, uploader);

			if (certification == 0) {
				$("#" + JUrls).parent().find(".file-input").attr("disabled",
						"disabled");
			} else {
				/** 可以修改时注册删除事件* */
				$(".form-context").delegate(
						".imageUploader_del",
						"click",
						function() {
							$(this).parents("li").get(0).remove();
							var originalUrl = $(this).parent().siblings().find(
									"a img").attr("data-original-url");
							$("input[name*='" + JUrls + "']").each(function() {
								if ($(this).val() == originalUrl) {
									$(this).val("");
								}
							});
						});
			}
		}
	}, function(url) {
	});
}

// 礼品数量统计
function calculateGiftAmount() {
	var giftAmount = 0;
	$.each($("td[data-role='popWinGiftCount']"), function(index, obj) {
		giftAmount += parseInt($(obj).text());
	});

	$("#popGiftCountSum").text(giftAmount);
};

// 业务数量统计
function calculateGiftBusinessAmount() {
	var businessAmount = 0;
	$.each($("td[data-role='giftBusinessCount']"), function(index, obj) {
		var single=parseInt($(obj).text());
		if(!isNaN(single)){
			businessAmount += single;
		}
		
	});

	$("#giftBusinessSum").text(businessAmount);

};

//礼品金额统计
function calculateGiftPriceAmount() {
	var priceAmount = 0.00;
	$.each($("td[data-role='popWinGiftPrice']"), function(index, obj) {
		priceAmount += parseFloat($(obj).text());
	});
	
	//保存两位小数
	$("#popGiftPriceSum").text(priceAmount.toFixed(2));
	
};
