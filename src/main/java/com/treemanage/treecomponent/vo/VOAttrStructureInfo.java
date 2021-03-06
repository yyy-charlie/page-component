package com.treemanage.treecomponent.vo;


import lombok.Data;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:07:58
 */
@Data
public class VOAttrStructureInfo {
	private Long id;				//主键
	private String name;			//属性名
	private String description;		//属性描述
	private String javaType;		//java对象类型
	private String ifId;			//是否为主键
	private String generatedValue;	//主键生成规则
	private String ifForeignKey;	//是否为外键
	private String column;			//持久化字段名
	private String ifIgnoreParam;	//是否忽略该属性不持久化
	private Long beanStructureInfoId;	//外键，对象结构信息ID

}