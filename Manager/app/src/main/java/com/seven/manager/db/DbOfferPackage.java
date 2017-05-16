package com.seven.manager.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 套餐
 *
 * @author seven
 */
@Table(name = "offerPackage")
public class DbOfferPackage {

    @Column(name = "id",isId = true)
    private long id;

    @Column(name = "hashCode")
    private int hashCode;

    @Column(name = "params")
    private String params;

    @Column(name = "content")
    private String content;

    @Column(name = "createTime")
    private long createTime;

    @Column(name = "projectId")
    private long projectId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DbOfferPackage{" +
                "id=" + id +
                ", hashCode=" + hashCode +
                ", params='" + params + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", projectId=" + projectId +
                '}';
    }
}
