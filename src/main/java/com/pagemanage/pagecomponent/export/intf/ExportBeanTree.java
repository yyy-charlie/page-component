package com.pagemanage.pagecomponent.export.intf;


import com.pagemanage.pagecomponent.bo.BeanTreeStuctureInfo;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:44:16
 */
public interface ExportBeanTree {

	/**
	 * 将TreeBean 导出为BOTreeConfig.java
	 *
	 * @param beanTreeStuctureInfo
	 */
	public String exportBeanTreeStructureInfo(BeanTreeStuctureInfo beanTreeStuctureInfo);

}