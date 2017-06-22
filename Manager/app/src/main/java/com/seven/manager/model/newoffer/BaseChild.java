package com.seven.manager.model.newoffer;

import java.io.Serializable;

/**
 * @author seven
 */

public class BaseChild implements Serializable {

    /**
     * 类id
     */
    private long id;

    /**
     * 类名称
     */
    private String name;

    /**
     * 阶段id
     */
    private long pid;

    /**
     * 施工项集合
     */
    private String contractItemsList;

    //-----------------------------------自定义字段

    /**
     * 阶段名称
     */
    private String pname;

    /**
     * 是否选中
     */

    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getContractItemsList() {
        return contractItemsList;
    }

    public void setContractItemsList(String contractItemsList) {
        this.contractItemsList = contractItemsList;
    }

    @Override
    public String toString() {
        return "BaseChild{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", contractItemsList='" + contractItemsList + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }
}
