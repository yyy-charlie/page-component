package com.treemanage.treecomponent.biz.impl;

import com.container.treecontainer.bo.TreeNode;
import com.container.treecontainer.util.ObjectUtils;
import com.treemanage.treecomponent.biz.intf.BeanTreeStructureBiz;
import com.treemanage.treecomponent.bo.BeanTreeStuctureInfo;
import com.treemanage.treecomponent.export.intf.ExportBeanTree;
import com.treemanage.treecomponent.tree.BeanTreeStructureTreeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mist
 * @Version: 1.0
 * @Date: 2019/12/29 10:16
 */
@Service
public class BeanTreeStructureBizImpl implements BeanTreeStructureBiz {

    @Autowired
    private BeanTreeStructureTreeConfig beanTreeStructureTreeConfig;

    @Autowired
    private ExportBeanTree exportBeanTree;


    @Override
    public TreeNode addBeanTreeStuctureInfo(Long parentNodeId, BeanTreeStuctureInfo beanTreeStructureInfo) {
        return  beanTreeStructureTreeConfig.getTreeContainer().addNode(parentNodeId,beanTreeStructureInfo);
    }

    @Override
    public BeanTreeStuctureInfo updateBeanTreeStuctureInfo(BeanTreeStuctureInfo beanTreeStuctureInfo) {
        return beanTreeStructureTreeConfig.getTreeContainer().update(beanTreeStuctureInfo);
    }

    @Override
    public BeanTreeStuctureInfo getBeanTreeStuctureInfo(Long nodeId) {
        TreeNode treeNode =  beanTreeStructureTreeConfig.getTreeContainer().getObj(nodeId);
        return ObjectUtils.isNull(treeNode)?null:(BeanTreeStuctureInfo)treeNode.getData();
    }

    @Override
    public List<TreeNode> getTreeView() {
        List<TreeNode> treeNodes =  beanTreeStructureTreeConfig.getTreeContainer().getTreeView();
        if(ObjectUtils.judgeList(treeNodes)){

            for (TreeNode one:treeNodes) {
                parseChildren(one);
            }

        }
        return treeNodes;
    }

    @Override
    public String exportBeanTreeStructureInfo(Long nodeId) {
        BeanTreeStuctureInfo beanTreeStuctureInfo =  getBeanTreeStuctureInfo(nodeId);
        return exportBeanTree.exportBeanTreeStructureInfo(beanTreeStuctureInfo);
    }





    private void parseChildren(TreeNode treeNode) {
        List<TreeNode> children = treeNode.getChildren();
        if(ObjectUtils.judgeList(children)){
            for (TreeNode one:children) {
                parseChildren(one);
            }
        }

        treeNode.setChildren(parseModule(children));
    }

    private List<TreeNode> parseModule(List<TreeNode> children) {
        if(ObjectUtils.judgeList(children)){
            List<TreeNode> returnChildren = new ArrayList<>();
            for (TreeNode one:children) {
                if(!ObjectUtils.isNull(one.getObjectId())) returnChildren.add(one);
            }
            return returnChildren;
        }
        return children;
    }






}
