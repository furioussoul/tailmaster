package esform.pagination;

import com.github.pagehelper.PageRowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * 分页类，用作dao层参数时dao执行分页
 * 用作接口返回值时携带总页数:Repsonse.ok(paginition)
 *
 * @author 孙证杰
 * @version v0.0.1: Pagination.java, v 0.1 2017年3月22日 下午2:25:19 孙证杰 Exp $
 */
public class Pagination<T> extends PageRowBounds {

    private List<T> list;

    private int pageSize;

    public Pagination(int offset, int limit) {
        super(offset, limit);
        this.pageSize = limit;
    }

    public Long getTotalPage() {
        return new BigDecimal((null == this.getTotal() ? 0 : this.getTotal()))
                .divide(new BigDecimal(this.pageSize), 0, BigDecimal.ROUND_CEILING)
                .longValue();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
