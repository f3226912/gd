package com.gudeng.commerce.gd.admin.util.excel.cb;

import java.util.List;

import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public interface BeanToSheetCallBack<T> {

	public void  mapper(List<T> beanList, WritableSheet sheet) throws RowsExceededException, WriteException ;
	
}
