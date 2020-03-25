package com.pagemanage.pagecomponent.vo;


import lombok.Data;

import java.util.List;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:44:27
 */
@Data
public class VOBeanTreeStuctureInfo {

	private Long id;				//主键
	private String name;			//BO树名称
	private String description;		//描述
	private Boolean ifNameRepeat;	//是否允许名字重复
	private List<VONodeStructureInfo> nodeStructureInfos;

}