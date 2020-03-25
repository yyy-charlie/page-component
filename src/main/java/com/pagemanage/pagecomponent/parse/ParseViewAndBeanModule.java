package com.pagemanage.pagecomponent.parse;


import com.container.treecontainer.bo.TreeNode;
import com.container.treecontainer.util.ObjectUtils;
import com.pagemanage.pagecomponent.biz.intf.BeanStructureBiz;
import com.pagemanage.pagecomponent.bo.AttrStructureInfo;
import com.pagemanage.pagecomponent.bo.BeanStructureInfo;
import com.pagemanage.pagecomponent.bo.BeanTreeStuctureInfo;
import com.pagemanage.pagecomponent.enump.GeneratedValueType;
import com.pagemanage.pagecomponent.enump.JavaType;
import com.pagemanage.pagecomponent.util.ToolUtils;
import com.pagemanage.pagecomponent.vo.VOAttrStructureInfo;
import com.pagemanage.pagecomponent.vo.VOBeanStructureInfo;
import com.pagemanage.pagecomponent.vo.VOBeanTreeStuctureInfo;
import com.pagemanage.pagecomponent.vo.VONodeStructureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:40:39
 */
@Component
public class ParseViewAndBeanModule {

	@Autowired
	private BeanStructureBiz beanStructureBiz;

	public VOBeanStructureInfo parseBO2VO(BeanStructureInfo bean) {

		if(ObjectUtils.isNull(bean)) return null;

		VOBeanStructureInfo view = ToolUtils.autoCopyProperties(bean, VOBeanStructureInfo.class);

		parseJavaType(bean,view);

		parseGeneratedValue(view);


		return view;
	}
	public VOAttrStructureInfo parseBO2VO(AttrStructureInfo bean) {

		if(ObjectUtils.isNull(bean)) return null;

		VOAttrStructureInfo view = ToolUtils.autoCopyProperties(bean, VOAttrStructureInfo.class);

		view.setJavaType(getJavaType(bean));

		parseGeneratedValue(view);

		return view;
	}

	public VOBeanTreeStuctureInfo parseBO2VO(BeanTreeStuctureInfo bean) {

		if(ObjectUtils.isNull(bean)) return null;

		VOBeanTreeStuctureInfo view = ToolUtils.autoCopyProperties(bean, VOBeanTreeStuctureInfo.class);

		return view;
	}

	public BeanStructureInfo parseVO2BO(VOBeanStructureInfo view) {

		if(ObjectUtils.isNull(view)) return null;

		setGeneratedValue(view);

		BeanStructureInfo bean = ToolUtils.autoCopyProperties(view, BeanStructureInfo.class);

		return bean;
	}

	public AttrStructureInfo parseVO2BO(VOAttrStructureInfo view) {

		if(ObjectUtils.isNull(view)) return null;

		setGeneratedValue(view);

		AttrStructureInfo bean = ToolUtils.autoCopyProperties(view, AttrStructureInfo.class);

		return bean;
	}

	public BeanTreeStuctureInfo parseVO2BO(VOBeanTreeStuctureInfo view) {

		if(ObjectUtils.isNull(view)) return null;

		BeanTreeStuctureInfo bean = ToolUtils.autoCopyProperties(view, BeanTreeStuctureInfo.class);

		return bean;
	}

	private void parseJavaType(BeanStructureInfo bean, VOBeanStructureInfo view) {

		List<VOAttrStructureInfo> voAttrStructureInfoList = view.getAttributes();
		List<AttrStructureInfo> attrStructureInfoList = bean.getAttributes();

		for (VOAttrStructureInfo oneVO:voAttrStructureInfoList) {
			for (AttrStructureInfo oneBO:attrStructureInfoList) {
				if(oneVO.getId().intValue() == oneBO.getId().intValue()){
					oneVO.setJavaType(getJavaType(oneBO));
				}
			}
		}

	}

	private String getJavaType(AttrStructureInfo oneBO) {
		if(ObjectUtils.isNull(oneBO.getIfList()) || ObjectUtils.isNull(oneBO.getJavaType())) return null;
		return oneBO.getIfList()?"List<"+getJavaTypeValue(oneBO) +">":getJavaTypeValue(oneBO);
	}

	private String getJavaTypeValue(AttrStructureInfo oneBO) {
		if(oneBO.getJavaType() != JavaType.OBJECT.getCode()) return JavaType.getEnum(oneBO.getJavaType()).getValue();
		return beanStructureBiz.getNameByBeanId(oneBO.getBeanTypeId());
	}


	private void parseGeneratedValue(VOBeanStructureInfo view) {

		List<VOAttrStructureInfo> voAttrStructureInfoList = view.getAttributes();

		for (VOAttrStructureInfo oneVO:voAttrStructureInfoList) {
			parseGeneratedValue(oneVO);
		}
	}

	private void parseGeneratedValue(VOAttrStructureInfo oneVO) {
		if(!ObjectUtils.isNull(oneVO.getGeneratedValue())){
			oneVO.setGeneratedValue(GeneratedValueType.getEnum(Integer.parseInt(oneVO.getGeneratedValue())).getValue());
		}

		oneVO.setIfId(parseBooleanView(oneVO.getIfId()));
		oneVO.setIfForeignKey(parseBooleanView(oneVO.getIfForeignKey()));
		oneVO.setIfIgnoreParam(parseBooleanView(oneVO.getIfIgnoreParam()));
	}

	private String parseBooleanView(String value) {
		return "true".equals(value)?"是":"否";
	}
	private String parseBooleanBean(String value) {
		return "是".equals(value)?"true":"false";
	}

	private void setGeneratedValue(VOBeanStructureInfo view) {

		List<VOAttrStructureInfo> voAttrStructureInfoList = view.getAttributes();

		for (VOAttrStructureInfo oneVO:voAttrStructureInfoList) { setGeneratedValue(oneVO); }
	}

	private void setGeneratedValue(VOAttrStructureInfo oneVO) {

		GeneratedValueType generatedValueType = GeneratedValueType.getEnum(oneVO.getGeneratedValue());
		if(!ObjectUtils.isNull(generatedValueType))
			oneVO.setGeneratedValue(generatedValueType.getCode()+"");

		oneVO.setIfId(parseBooleanBean(oneVO.getIfId()));
		oneVO.setIfForeignKey(parseBooleanBean(oneVO.getIfForeignKey()));
		oneVO.setIfIgnoreParam(parseBooleanBean(oneVO.getIfIgnoreParam()));

	}


    public TreeNode parseVO2TreeNode(VOBeanTreeStuctureInfo voBeanTreeStuctureInfo) {

		TreeNode treeNode = new TreeNode();

		treeNode.setId(voBeanTreeStuctureInfo.getId());
		treeNode.setName(voBeanTreeStuctureInfo.getName());
		treeNode.setData(voBeanTreeStuctureInfo);

		List<TreeNode> children = new ArrayList<>();

		for (VONodeStructureInfo one:voBeanTreeStuctureInfo.getNodeStructureInfos()) {
			TreeNode tempNode = new TreeNode();
			tempNode.setId(one.getId());
			tempNode.setName(beanStructureBiz.getNameByBeanId(one.getBeanStructureId()));
			tempNode.setData(one);
			children.add(tempNode);
		}

		treeNode.setChildren(children);

		return treeNode;
	}
}