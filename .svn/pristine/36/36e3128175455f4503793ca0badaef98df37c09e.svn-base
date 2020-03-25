
package com.treemanage.treecomponent.tree;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.bocontainer.annotaion.IgnoreParam;

public class SystemStructureTree {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;
    @IgnoreParam
    private Object injectionObj;
    private String name;
    private String objectClass;
    private String objectId;
    private Long parentId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setInjectionObj(Object injectionObj) {
        this.injectionObj = injectionObj;
    }

    public Object getInjectionObj() {
        return this.injectionObj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getObjectClass() {
        return this.objectClass;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return this.parentId;
    }

}
