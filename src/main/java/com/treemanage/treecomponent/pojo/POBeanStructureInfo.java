
package com.treemanage.treecomponent.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.container.treecontainer.annotaion.TreeNodeName;

@Table(name = "po_bean_structure_info")
public class POBeanStructureInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    @Column(name = "id")
    private Long id;
    @TreeNodeName
    @Column(name = "bean_name")
    private String beanName;
    @Column(name = "bean_class_name")
    private String beanClassName;

    public POBeanStructureInfo() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

}
