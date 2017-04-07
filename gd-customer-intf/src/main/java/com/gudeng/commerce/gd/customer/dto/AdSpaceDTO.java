package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;

public class AdSpaceDTO extends AdSpaceEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -3699012932821858302L;

    private String menuName;

    private String stateStr;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getStateStr() {
        switch (getState()) {
        case "1":
            stateStr = "正常";
            break;
        case "2":
            stateStr = "禁用";
            break;
        default:
            stateStr = "无状态";
            break;
        }
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

}
