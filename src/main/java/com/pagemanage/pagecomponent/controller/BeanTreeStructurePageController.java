package com.pagemanage.pagecomponent.controller;


import com.container.treecontainer.bo.TreeNode;
import com.container.treecontainer.util.ObjectUtils;
import com.pagemanage.pagecomponent.biz.intf.BeanStructureBiz;
import com.pagemanage.pagecomponent.biz.intf.BeanTreeStructureBiz;
import com.pagemanage.pagecomponent.bo.BeanTreeStuctureInfo;
import com.pagemanage.pagecomponent.msg.BaseResponse;
import com.pagemanage.pagecomponent.parse.ParseViewAndBeanModule;
import com.pagemanage.pagecomponent.util.ReturnUtils;
import com.pagemanage.pagecomponent.vo.VOBeanTreeStuctureInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:44:43
 */
@RestController
@ResponseBody
@RequestMapping("/beanTreeStructure")
public class BeanTreeStructurePageController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private BeanTreeStructureBiz beanTreeStructureBiz;

	@Autowired
	private BeanStructureBiz beanStructureBiz;

	@Autowired
	private ParseViewAndBeanModule parseViewAndBeanModule;

	/**
	 * 新增系统
	 *
	 * @param treeNode
	 */
	@RequestMapping("/addSystem")
	public BaseResponse addSystem(@RequestBody TreeNode treeNode){

		String description = "新增系统";

		TreeNode result = null;
		try {
			result = beanStructureBiz.addNode(treeNode);
		} catch (Exception e) {
			description = ReturnUtils.judgeTreeContainerException(e,description,"新增系统名称重复");
		}

		return ReturnUtils.parseReturn(result,description);
	}

	/**
	 * 删除系统
	 *
	 * @param nodeId
	 */
	@RequestMapping("/deleteSystem")
	public BaseResponse deleteSystem(@RequestParam Long nodeId){

		String description = "删除系统";

		boolean result = false;

		try {
			result = beanStructureBiz.deleteNode(nodeId);
		} catch (Exception e) {
			description = ReturnUtils.judgeBaseException(e,description);
		}

		return ReturnUtils.parseReturn(result,description);
	}

	/**
	 * 更新系统
	 *
	 * @param treeNode
	 */
	@RequestMapping("/updateSystem")
	public BaseResponse updateSystem(@RequestBody TreeNode treeNode){

		String description = "更新系统";

		TreeNode result = null;
		try {
			result = beanStructureBiz.updateNode(treeNode);
		} catch (Exception e) {
			description = ReturnUtils.judgeTreeContainerException(e,description,"更新系统名称重复");
		}

		return ReturnUtils.parseReturn(result,description);
	}

	/**
	 * 新增BO树结构信息
	 *
	 * @param parentNodeId
	 * @param voBeanTreeStuctureInfo
	 */
	@RequestMapping("/addBeanTreeStuctureInfo")
	public BaseResponse addBeanTreeStuctureInfo(@RequestParam Long parentNodeId,@RequestBody VOBeanTreeStuctureInfo voBeanTreeStuctureInfo){
		String description = "新增BO树结构信息";

		VOBeanTreeStuctureInfo result = null;
		TreeNode treeNode = null;
		try {

			BeanTreeStuctureInfo bean = parseViewAndBeanModule.parseVO2BO(voBeanTreeStuctureInfo);
			treeNode = beanTreeStructureBiz.addBeanTreeStuctureInfo(parentNodeId,bean);

			if(!ObjectUtils.isNull(treeNode)){
				result = parseViewAndBeanModule.parseBO2VO((BeanTreeStuctureInfo)treeNode.getData());
				TreeNode treeNode1 = parseViewAndBeanModule.parseVO2TreeNode(result);
				treeNode.setData(treeNode1);
			}

		} catch (Exception e) {
			description = ReturnUtils.judgeBaseException(e,description);
		}

		return ReturnUtils.parseReturn(treeNode,description);
	}

	/**
	 * 获得BO树结构信息
	 *
	 * @param nodeId
	 */
	@RequestMapping("/getBeanTreeStructureInfo")
	public BaseResponse getBeanTreeStuctureInfo(@RequestParam Long nodeId){
		String description = "获得BO树结构信息";

		BeanTreeStuctureInfo beanStructureInfo = beanTreeStructureBiz.getBeanTreeStuctureInfo(nodeId);

		VOBeanTreeStuctureInfo result = parseViewAndBeanModule.parseBO2VO(beanStructureInfo);

		return ReturnUtils.parseReturn(result,description);
	}
	/**
	 * 展示 BO树结构信息
	 *
	 * @param nodeId
	 */
	@RequestMapping("/getBeanTreeStructureInfoView")
	public BaseResponse getBeanTreeStructureInfoView(@RequestParam Long nodeId){
		String description = "展示BO树结构信息";

		BeanTreeStuctureInfo beanStructureInfo = beanTreeStructureBiz.getBeanTreeStuctureInfo(nodeId);

		VOBeanTreeStuctureInfo voBeanTreeStuctureInfo = parseViewAndBeanModule.parseBO2VO(beanStructureInfo);

		TreeNode result = parseViewAndBeanModule.parseVO2TreeNode(voBeanTreeStuctureInfo);

		return ReturnUtils.parseReturn(result,description);
	}

	/**
	 * 修改BO树结构信息
	 *
	 * @param beanTreeStructureInfo
	 */
	@RequestMapping("/updateBeanTreeStuctureInfo")
	public BaseResponse updateBeanTreeStuctureInfo(@RequestBody VOBeanTreeStuctureInfo beanTreeStructureInfo){
		String description = "修改BO树结构信息";

		VOBeanTreeStuctureInfo result = null;
		try {

			BeanTreeStuctureInfo bean = parseViewAndBeanModule.parseVO2BO(beanTreeStructureInfo);
			bean = beanTreeStructureBiz.updateBeanTreeStuctureInfo(bean);
			result = parseViewAndBeanModule.parseBO2VO(bean);

		} catch (Exception e) {
			description = ReturnUtils.judgeBaseException(e,description);
		}

 		return ReturnUtils.parseReturn(result,description);
	}



	/**
	 *	展示树
	 *
	 */
	@RequestMapping("/getTreeView")
	public BaseResponse getTreeView(){
		String description = "展示树";

		List<TreeNode> result = beanTreeStructureBiz.getTreeView();

		return ReturnUtils.parseReturn(result,description);
	}

	/**
	 * 导出BO树Config
	 *
	 * @param response
	 * @param nodeId
	 */
	@RequestMapping("/exportBeanTreeStructureInfo")
	public void exportBeanTreeStructureInfo(HttpServletResponse response, Long nodeId){
		String description = "导出BO树Config";

		try {
			String result = beanTreeStructureBiz.exportBeanTreeStructureInfo(nodeId);
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes());
		} catch (IOException e) {
			logger.error(description+"报错\n"+e);
		}
	}



}