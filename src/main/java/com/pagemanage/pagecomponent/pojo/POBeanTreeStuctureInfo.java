
package com.pagemanage.pagecomponent.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.container.treecontainer.annotaion.TreeNodeName;

@Table(name = "po_bean_tree_stucture_info")
public class POBeanTreeStuctureInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    @Column(name = "id")
    private Long id;
    @TreeNodeName
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "if_name_repeat")
    private Boolean ifNameRepeat;
    @Column(name = "bean_tree_class_name")
    private String beanTreeClassName;

    public POBeanTreeStuctureInfo() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setIfNameRepeat(Boolean ifNameRepeat) {
        this.ifNameRepeat = ifNameRepeat;
    }

    public Boolean getIfNameRepeat() {
        return this.ifNameRepeat;
    }

    public void setBeanTreeClassName(String beanTreeClassName) {
        this.beanTreeClassName = beanTreeClassName;
    }

    public String getBeanTreeClassName() {
        return this.beanTreeClassName;
    }

}
