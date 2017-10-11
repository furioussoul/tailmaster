package esform.domain;

import java.util.Date;

/**
 * Created by
 *
 * @name:孙证杰
 * @email:20076581@qq.com on 2017/9/17.
 */
public class BaseDomain {
    private Long id;
    private String createBy;
    private Date createDt;
    private String updateBy;
    private Date updateDt;
    private int rowStatus;

    public BaseDomain() {
    }

    public BaseDomain(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public int getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(int rowStatus) {
        this.rowStatus = rowStatus;
    }
}
