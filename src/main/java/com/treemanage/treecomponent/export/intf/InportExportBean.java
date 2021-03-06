package com.treemanage.treecomponent.export.intf;

import com.treemanage.treecomponent.bo.BeanStructureInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:38:46
 */
public interface InportExportBean {

	/**
	 *	导入BO对象 并解析为Bean
	 * @param file
	 */
	public BeanStructureInfo importBeanStructureInfo(MultipartFile file);

	/**
	 * 将Bean 导出为BO对象
	 * @param beanStructureInfo
	 */
	public String exportBeanStructureInfo(BeanStructureInfo beanStructureInfo);



}