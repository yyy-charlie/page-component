package com.treemanage.treecomponent.biz.intf;


import com.container.treecontainer.bo.TreeNode;
import com.treemanage.treecomponent.bo.BeanTreeStuctureInfo;

import java.util.List;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 10:15:31
 */
public interface BeanTreeStructureBiz {


	/**
	 * 
	 * @param parentNodeId
	 * @param beanTreeStructureInfo
	 */
	public TreeNode addBeanTreeStuctureInfo(Long parentNodeId, BeanTreeStuctureInfo beanTreeStructureInfo);

	/**
	 *
	 * @param bean
	 * @return
	 */
	BeanTreeStuctureInfo updateBeanTreeStuctureInfo(BeanTreeStuctureInfo bean);

	/**
	 *
	 * @param nodeId
	 */
	public BeanTreeStuctureInfo getBeanTreeStuctureInfo(Long nodeId);

	/**
	 *
	 *
	 */
	public List<TreeNode> getTreeView();

	/**
	 * 
	 * @param nodeId
	 */
	public String exportBeanTreeStructureInfo(Long nodeId);


}