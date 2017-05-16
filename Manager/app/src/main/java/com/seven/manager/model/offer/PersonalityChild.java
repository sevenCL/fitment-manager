package com.seven.manager.model.offer;

import java.io.Serializable;

/**
 * 个性项-child
 *
 * @author seven
 */

public class PersonalityChild implements Serializable {

    /**
     * 子项id
     */
    private long id;

    /**
     * 个性项id
     */
    private long pid;

    /**
     * 子项名称
     */
    private String name;

    /**
     * 施工项集合
     */
    private String contractItemsList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractItemsList() {
        return contractItemsList;
    }

    public void setContractItemsList(String contractItemsList) {
        this.contractItemsList = contractItemsList;
    }


    //---------------------------------------------------自定义字段

    /**
     * 个性项名称
     */
    private String pName;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    @Override
    public String toString() {
        return "PersonalityChild{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", contractItemsList='" + contractItemsList + '\'' +
                ", pName=" + pName +
                '}';
    }
}
