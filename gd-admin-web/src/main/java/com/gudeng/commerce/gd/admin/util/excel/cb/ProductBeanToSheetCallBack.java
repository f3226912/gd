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

public class ProductBeanToSheetCallBack implements BeanToSheetCallBack<ProductDto> {

	@Override
	public void mapper(List<ProductDto> beanList, WritableSheet sheet) throws RowsExceededException, WriteException {
		int len = beanList.size() ;
		if (len <= 0){
			return ;
		}
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] line = new String[]{"商品名称", "商品类目", "真实姓名", "会员账号", "创建人", "创建时间", "信息有效期", "审核状态"};
		ExcelUtils.newLine(sheet, line, 0);
		// 数据行
		for (int i = 0; i < len; i++) {
			
			ProductDto item = beanList.get(i);
			// 创建时间
			String createTime = CommonUtil.isEmpty(item.getCreateTime())?"":time.format(item.getCreateTime());
			//商品名称, 商品类目, 真实姓名, 会员账号, 创建人, 创建时间, 信息有效期, 审核状态  --> 必须与标题行对应
			line = new String[]{item.getProductName(), item.getCateName(), item.getRealName(), item.getAccount(),
					item.getSysUserCode(), createTime, item.getInfoLifeDay(), ProductStatusEnum.getValue(item.getState())};
			// 填充当前数据行
			ExcelUtils.newLine(sheet, line, i+1);
			
		}
	}

}
