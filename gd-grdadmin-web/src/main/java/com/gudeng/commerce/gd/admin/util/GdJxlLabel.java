package com.gudeng.commerce.gd.admin.util;

import jxl.write.Label;

public class GdJxlLabel extends Label {

	public GdJxlLabel(int c, int r, Object cellValue) {
		super(c, r, cellValue == null ? "" : cellValue.toString().trim());
	}

}
