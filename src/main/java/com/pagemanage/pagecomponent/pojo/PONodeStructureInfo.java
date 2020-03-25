
package com.pagemanage.pagecomponent.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bocontainer.annotaion.ForeignKey;

@Table(name = "po_node_structure_info")
public class PONodeStructureInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    @Column(name = "id")
    private Long id;
    @Column(name = "bean_class_name")
    private String beanClassName;
    @Column(name = "if_persistence")
    private Boolean ifPersistence;
    @Column(name = "bean_structure_id")
    private Long beanStructureId;
    @ForeignKey
    @Column(name = "bean_tree_stucture_info_id")
    private Long beanTreeStuctureInfoId;

    public PONodeStructureInfo() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setIfPersistence(Boolean IfPersistence) {
        this.ifPersistence = IfPersistence;
    }

    public Boolean getIfPersistence() {
        return this.ifPersistence;
    }

    public void setBeanStructureId(Long beanStructureId) {
        this.beanStructureId = beanStructureId;
    }

    public Long getBeanStructureId() {
        return this.beanStructureId;
    }

    public void setBeanTreeStuctureInfoId(Long beanTreeStuctureInfoId) {
        this.beanTreeStuctureInfoId = beanTreeStuctureInfoId;
    }

    public Long getBeanTreeStuctureInfoId() {
        return this.beanTreeStuctureInfoId;
    }

}
