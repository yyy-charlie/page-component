package com.pagemanage.pagecomponent.biz.intf;


import com.container.treecontainer.bo.TreeNode;
import com.pagemanage.pagecomponent.bo.AttrStructureInfo;
import com.pagemanage.pagecomponent.bo.BeanStructureInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:23:53
 */
public interface BeanStructureBiz {

	/**
	 * 新增节点（系统，模块）
	 * @param treeNode
	 */
	public TreeNode addNode(TreeNode treeNode);

	/**
	 * 删除节点（系统，模块）
	 * @param nodeId
	 */
	public boolean deleteNode(Long nodeId);

	/**
	 * 修改节点（系统，模块）
	 * @param treeNode
	 */
	public TreeNode updateNode(TreeNode treeNode);

	/**
	 *
	 * @param parentNodeId
	 * @param beanStructureInfo
	 */
	public TreeNode addBeanStructureInfo(Long parentNodeId, BeanStructureInfo beanStructureInfo);

	/**
	 *
	 * @param beanStructureInfo
	 */
	public BeanStructureInfo updateBeanStructureInfo(BeanStructureInfo beanStructureInfo);

	/**
	 * 
	 * @param nodeId
	 */
	public BeanStructureInfo getBeanStructureInfo(Long nodeId);

	public List<TreeNode> getTreeView();

	public List<TreeNode> getDescendantsView(Long parentNodeId);

	/**
	 * 导入BO对象
	 *
	 * @param parentNodeId
	 * @param file
	 */
	public TreeNode inportBeanStructureInfo(Long parentNodeId, MultipartFile file);


	/**
	 * 导出BO对象
	 *
	 * @param nodeId
	 */
	public String exportBeanStructureInfo(Long nodeId);

	/**
	 * 特定接口 根据对象ID，获得对象信息
	 * @param id
	 * @return
	 */
	public String getNameByBeanId(Long id);

	/**
	 *
	 * @param bean
	 * @return
	 */
	AttrStructureInfo updateAttrStructureInfo(AttrStructureInfo bean);
}