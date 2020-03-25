package com.pagemanage.pagecomponent.biz.impl;

import com.bocontainer.container.BOContainer;
import com.container.treecontainer.bo.TreeNode;
import com.container.treecontainer.util.ObjectUtils;
import com.pagemanage.pagecomponent.biz.intf.BeanStructureBiz;
import com.pagemanage.pagecomponent.bo.AttrStructureInfo;
import com.pagemanage.pagecomponent.bo.BeanStructureInfo;
import com.pagemanage.pagecomponent.enump.JavaType;
import com.pagemanage.pagecomponent.exception.BaseException;
import com.pagemanage.pagecomponent.export.intf.InportExportBean;
import com.pagemanage.pagecomponent.mapper.POBeanStructureInfoMapper;
import com.pagemanage.pagecomponent.pojo.POBeanStructureInfo;
import com.pagemanage.pagecomponent.tree.BeanStructureTreeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Mist
 * @Version: 1.0
 * @Date: 2019/12/29 9:24
 */
@Service
public class BeanStructureBizImpl implements BeanStructureBiz {

    @Autowired
    private BeanStructureTreeConfig beanStructureTreeConfig;

    @Autowired
    private POBeanStructureInfoMapper poBeanStructureInfoMapper;

    @Autowired
    private InportExportBean inportExportBean;

    @Autowired
    private BOContainer boContainer;

    @Override
    public TreeNode addNode(TreeNode treeNode) {
        return beanStructureTreeConfig.getTreeContainer().addTreeNode(treeNode);
    }

    @Override
    public boolean deleteNode(Long nodeId) {
        int count =  beanStructureTreeConfig.getTreeContainer().getChildrenCount(nodeId);
        if(count > 0) throw new BaseException("该节点下含有子节点");
        return beanStructureTreeConfig.getTreeContainer().delete(nodeId);
    }

    @Override
    public TreeNode updateNode(TreeNode treeNode) {
        return beanStructureTreeConfig.getTreeContainer().updateTreeNode(treeNode);
    }

    @Override
    public TreeNode addBeanStructureInfo(Long parentNodeId, BeanStructureInfo beanStructureInfo){
        return beanStructureTreeConfig.getTreeContainer().addNode(parentNodeId,beanStructureInfo);
    }

    @Override
    public BeanStructureInfo updateBeanStructureInfo(BeanStructureInfo beanStructureInfo) {
        BeanStructureInfo result =  beanStructureTreeConfig.getTreeContainer().update(beanStructureInfo);
        return result;
    }

    @Override
    public AttrStructureInfo updateAttrStructureInfo(AttrStructureInfo attrStructureInfo) {
        AttrStructureInfo result =  beanStructureTreeConfig.getTreeContainer().update(attrStructureInfo);
        result = boContainer.selectByPrimaryKey(result.getClass(),result.getId());
        return result;
    }

    @Override
    public BeanStructureInfo getBeanStructureInfo(Long nodeId) {
        TreeNode treeNode =  beanStructureTreeConfig.getTreeContainer().getObj(nodeId);
        return ObjectUtils.isNull(treeNode)?null:(BeanStructureInfo)treeNode.getData();
    }

    @Override
    public List<TreeNode> getTreeView() {
        return beanStructureTreeConfig.getTreeContainer().getTreeView();
    }

    @Override
    public List<TreeNode> getDescendantsView(Long parentNodeId) {
        return beanStructureTreeConfig.getTreeContainer().getDescendantsView(parentNodeId);
    }

    @Override
    public TreeNode inportBeanStructureInfo(Long parentNodeId,MultipartFile file) {
        BeanStructureInfo beanStructureInfo = inportExportBean.importBeanStructureInfo(file);
        judgeSubBean(parentNodeId,beanStructureInfo);
        return addBeanStructureInfo(parentNodeId,beanStructureInfo);
    }

    @Override
    public String exportBeanStructureInfo(Long nodeId) {
        BeanStructureInfo beanStructureInfo = getBeanStructureInfo(nodeId);
//        parseSubBeanName(beanStructureInfo);
        return inportExportBean.exportBeanStructureInfo(beanStructureInfo);
    }

    private void parseSubBeanName(BeanStructureInfo beanStructureInfo) {
        for (AttrStructureInfo one:beanStructureInfo.getAttributes()){
            if(one.getJavaType() == JavaType.OBJECT.getCode())
                one.setBeanTypeName(getNameByBeanId(one.getBeanTypeId()));
        }
    }

    @Override
    public String getNameByBeanId(Long id) {
        POBeanStructureInfo poBeanStructureInfo = poBeanStructureInfoMapper.selectByPrimaryKey(id);
        return ObjectUtils.isNull(poBeanStructureInfo)?null:poBeanStructureInfo.getBeanName();
    }



    /**
     * 判断 子BO是否已经存在
     * @param parentNodeId
     * @param beanStructureInfo
     */
    private void judgeSubBean(Long parentNodeId,BeanStructureInfo beanStructureInfo) {

        if(ObjectUtils.judgeList(beanStructureInfo.getAttributes())){

            for (AttrStructureInfo one:beanStructureInfo.getAttributes()){
                if(one.getJavaType() == JavaType.OBJECT.getCode()){
                    if(!judgeBeanExistByName(parentNodeId,one.getBeanTypeName()))
                        throw new BaseException("该对象内包含没有导入的子对象："+one.getBeanTypeName(),HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
            }

        }

    }

    private boolean judgeBeanExistByName(Long parentNodeId,String beanTypeName) {
        if(ObjectUtils.isNull(beanTypeName)) return false;

        List<TreeNode> children = beanStructureTreeConfig.getTreeContainer().getChildrenView(parentNodeId);

        if(ObjectUtils.judgeList(children)){
            for (TreeNode one:children){
                if(beanTypeName.equals(one.getName())){
                    return true;
                }
            }
        }

        return false;
    }


}
