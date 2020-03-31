package com.treemanage.treecomponent.bo;

import com.bocontainer.annotaion.ForeignKey;
import com.bocontainer.annotaion.IgnoreParam;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:25:52
 */
@Data
public class AttrStructureInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
	private Long id;							//主键
	private String name;						//属性名
	private String description;					//属性描述
	private Boolean ifList;						//是否为List
	private Integer javaType;					//java对象类型
	private Boolean ifId;						//是否为主键
	private Integer generatedValue;				//主键生成规则
	private Boolean ifForeignKey;				//是否为外键
	private String column;						//持久化字段名
	private Boolean ifIgnoreParam;				//是否忽略该属性不持久化
	private Long beanTypeId;						//属性是对象时的对象结构ID
	@IgnoreParam
	private String beanTypeName;						//属性是对象时的对象结构Name
	@ForeignKey
	private Long beanStructureInfoId;			//外键，对象结构信息ID

	public AttrStructureInfo() {
	}

	public AttrStructureInfo(String name, String description, Boolean ifList, Integer javaType, Boolean ifId, Integer generatedValue, Boolean ifForeignKey, String column, Boolean ifIgnoreParam, Long beanTypeId) {
		this.name = name;
		this.description = description;
		this.ifList = ifList;
		this.javaType = javaType;
		this.ifId = ifId;
		this.generatedValue = generatedValue;
		this.ifForeignKey = ifForeignKey;
		this.column = column;
		this.ifIgnoreParam = ifIgnoreParam;
		this.beanTypeId = beanTypeId;
	}
}