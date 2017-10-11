package esform.request;

import esform.pagination.Pagination;

/**
 * 接口入参分页基础类
 *
 * @author 孙证杰
 * @version v0.0.1: BaseRequest.java, v 0.1 2017年3月22日 下午2:25:19 孙证杰 Exp $
 */
public class BaseRequest<T> {
    private int pageSize = 20;
    private int pageNum = 0;
    private String orderColumn;
    private String orderType;

    public Pagination<T> getPagination() {
        return new Pagination<T>(this.getFrom(), this.getPageSize());
    }

    public int getFrom() {
        return pageNum * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
