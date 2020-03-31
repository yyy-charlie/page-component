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
 * @created 29-12-2019 9:43:34
 */
@Data
public class BeanTreeStuctureInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
	private Long id;							//主键
	@TreeNodeName
	private String name;						//BO树名称
	private String description;					//描述
	private Boolean ifNameRepeat;				//是否允许名字重复
	private String beanTreeClassName;			//树对象Class地址
	private List<NodeStructureInfo> nodeStructureInfos;

	public String getBeanTreePackage() {
		if(ObjectUtils.isNull(this.beanTreeClassName))return null;
		return this.beanTreeClassName.replace("."+this.name,"");
	}
}