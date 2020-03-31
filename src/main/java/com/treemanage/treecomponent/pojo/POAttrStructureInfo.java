
package com.treemanage.treecomponent.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bocontainer.annotaion.ForeignKey;

@Table(name = "po_attr_structure_info")
public class POAttrStructureInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "if_list")
    private Boolean ifList;
    @Column(name = "java_type")
    private Integer javaType;
    @Column(name = "if_id")
    private Boolean ifId;
    @Column(name = "generated_value")
    private Integer generatedValue;
    @Column(name = "if_foreign_key")
    private Boolean ifForeignKey;
    @Column(name = "`column`")
    private String column;
    @Column(name = "if_ignore_param")
    private Boolean ifIgnoreParam;
    @Column(name = "bean_type_id")
    private Long beanTypeId;
    @ForeignKey
    @Column(name = "bean_structure_info_id")
    private Long beanStructureInfoId;

    public POAttrStructureInfo() {
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

    public void setIfList(Boolean ifList) {
        this.ifList = ifList;
    }

    public Boolean getIfList() {
        return this.ifList;
    }

    public void setJavaType(Integer javaType) {
        this.javaType = javaType;
    }

    public Integer getJavaType() {
        return this.javaType;
    }

    public void setIfId(Boolean ifId) {
        this.ifId = ifId;
    }

    public Boolean getIfId() {
        return this.ifId;
    }

    public void setGeneratedValue(Integer generatedValue) {
        this.generatedValue = generatedValue;
    }

    public Integer getGeneratedValue() {
        return this.generatedValue;
    }

    public void setIfForeignKey(Boolean ifForeignKey) {
        this.ifForeignKey = ifForeignKey;
    }

    public Boolean getIfForeignKey() {
        return this.ifForeignKey;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }

    public void setIfIgnoreParam(Boolean ifIgnoreParam) {
        this.ifIgnoreParam = ifIgnoreParam;
    }

    public Boolean getIfIgnoreParam() {
        return this.ifIgnoreParam;
    }

    public void setBeanTypeId(Long beanTypeId) {
        this.beanTypeId = beanTypeId;
    }

    public Long getBeanTypeId() {
        return this.beanTypeId;
    }

    public void setBeanStructureInfoId(Long beanStructureInfoId) {
        this.beanStructureInfoId = beanStructureInfoId;
    }

    public Long getBeanStructureInfoId() {
        return this.beanStructureInfoId;
    }

}
