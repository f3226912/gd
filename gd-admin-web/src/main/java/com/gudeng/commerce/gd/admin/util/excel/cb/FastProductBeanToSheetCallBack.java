package com.gudeng.commerce.gd.admin.util.excel.cb;

import java.text.SimpleDateFormat;
import java.util.List;

import com.gudeng.commerce.gd.admin.entity.ProductStatusEnum;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.excel.ExcelUtils;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class FastProductBeanToSheetCallBack implements BeanToSheetCallBack<ProductDto> {

	@Override
	public void mapper(List<ProductDto> beanList, WritableSheet sheet) throws RowsExceededException, WriteException {
		int len = beanList.size() ;
		if (len <= 0){
			return ;
		}
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] line = new String[]{"商品名称", "商品类目", "商品产地", "创建人", "创建时间", "更新时间", "审核状态"};
		ExcelUtils.newLine(sheet, line, 0);
		// 数据行
		for (int i = 0; i < len; i++) {
			ProductDto item = beanList.get(i);
			//省市区
			String region = (CommonUtil.isEmpty(item.getProvinceName()) ? "" :item.getProvinceName())	
					+ (CommonUtil.isEmpty(item.getCityName()) ?"":item.getCityName())
					+ (CommonUtil.isEmpty(item.getAreaName()) ?"":item.getAreaName());
			// 创建时间
			String createTime = CommonUtil.isEmpty(item.getCreateTime())?"":time.format(item.getCreateTime());
			// 更新时间
			String updateTime = CommonUtil.isEmpty(item.getUpdateTime())?"":time.format(item.getUpdateTime());
			//商品名称-分类名称-省市区-创建人-创建时间-更新时间-审核状态  --> 必须与标题行对应
			line = new String[]{item.getProductName(), item.getCateName(), region, item.getSysUserCode(), 
					createTime, updateTime, ProductStatusEnum.getValue(item.getState())};
			// 填充当前数据行
			ExcelUtils.newLine(sheet, line, i+1);
		}
	}

}
