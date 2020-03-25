package com.treemanage.treecomponent.bo;


import com.bocontainer.annotaion.ForeignKey;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:43:39
 */
@Data
public class NodeStructureInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
	private Long id;							//主键
	private Long beanStructureId;				//BO对象结构信息ID
	private String beanClassName;				//BO对象路径
	private Boolean ifPersistence;				//是否允许持久化
	@ForeignKey
	private Long beanTreeStuctureInfoId;		//BO树结构信息ID

	public NodeStructureInfo() {
	}

	public NodeStructureInfo(Long beanStructureId, Boolean ifPersistence) {
		this.beanStructureId = beanStructureId;
		this.ifPersistence = ifPersistence;
	}
}