package cn.xs.erp.model;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
 

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.Page;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PageInfo<T> implements Serializable {
   
	private static final long serialVersionUID = 1L;
	@JsonIgnoreProperties(ignoreUnknown = true)
    private int pageNum;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private int pageSize;
    private long total;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private int pages;
    private List<T> rows;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private boolean isFirstPage = false;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private boolean isLastPage = false;
 
 
    public PageInfo() {
    }
 
    /**
     * 包装Page对象
     *
     * @param list
     */
    public PageInfo(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
 
            this.pages = page.getPages();
            this.rows = page;
            this.total = page.getTotal();
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
 
            this.pages = 1;
            this.rows = list;
            this.total = list.size();
        }
        if (list instanceof Collection) {
            //判断页面边界
            judgePageBoudary();
        }
    }
 
    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages;
    }
 
    public int getPageNum() {
        return pageNum;
    }
 
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
 
    public int getPageSize() {
        return pageSize;
    }
 
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
 
    public long getTotal() {
        return total;
    }
 
    public void setTotal(long total) {
        this.total = total;
    }
 
    public int getPages() {
        return pages;
    }
 
    public void setPages(int pages) {
        this.pages = pages;
    }
 
    public List<T> getRows() {
        return rows;
    }
 
    public void setRows(List<T> list) {
        this.rows = list;
    }
 
    public boolean isIsFirstPage() {
        return isFirstPage;
    }
 
    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }
 
    public boolean isIsLastPage() {
        return isLastPage;
    }
 
    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
 
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", rows=").append(rows);
        sb.append(", isFirstPage=").append(isFirstPage);
        sb.append(", isLastPage=").append(isLastPage);
        sb.append(", navigatepageNums=");
        sb.append('}');
        return sb.toString();
    }
}