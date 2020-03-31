package com.treemanage.treecomponent.bo;


import com.container.treecontainer.annotaion.TreeNodeName;
import com.container.treecontainer.util.ObjectUtils;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:25:46
 */
@Data
public class BeanStructureInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
	private Long id;							//主键
	@TreeNodeName
	private String beanName;					//对象名称
	private String beanClassName;				//对象Class地址
	private List<AttrStructureInfo> attributes;	//属性数组

	public String getBeanPackage(){
		if(ObjectUtils.isNull(this.beanClassName))return null;
		return this.beanClassName.replace("."+this.beanName,"");
	}

}