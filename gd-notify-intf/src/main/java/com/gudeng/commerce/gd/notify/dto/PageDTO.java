package com.gudeng.commerce.gd.notify.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageDTO<T> implements Serializable {

    private static final long serialVersionUID = -5290477031747093045L;
    /** 当前页 */
    private Integer currentPage = 1;
    /** 每页条数 */
    private Integer pageSize = 10;

    /** 总页数 */
    private Integer pageTotal = 0;

    /** 总记录数 */
    private Integer totalSize = 0;
    private Integer page = 1;

    /** 没有数据标识符 */
    private Boolean isEmpty = false;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    /**
     * 获得总页数 需先获得总记录数
     * 
     * @return
     */
    public Integer getPageTotal() {

        /*
         * 计算总页数
         */
        int size = totalSize / pageSize;// 总条数/每页显示的条数=总页数
        int mod = totalSize % pageSize;// 最后一页的条数
        if (mod != 0)
            size++;

        setPageTotal(totalSize == 0 ? 1 : size);

        return this.pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public void setCurrentPage(Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            this.currentPage = 1;
            return;
        }
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1) {
            this.pageSize = 5;
            return;
        }
        this.pageSize = pageSize;
    }

    /**
     * 储存数据
     */
    private List<T> pageData = new ArrayList<T>();

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public PageDTO() {
    }

    public PageDTO(int page, int size) {
        this.currentPage = page;
        this.pageSize = size;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 计算数据库开始Index limit中用到
     * 
     * @return
     */
    public Integer getIndex() {
        return this.getCurrentPage() * this.getPageSize() - this.getPageSize();
    }

    /**
     * 计算数据库最后一页的Index
     * 
     * @return
     */
    public Integer getLastIndex() {
        return (this.getPageTotal() - 1) * this.getPageSize();
    }

}
