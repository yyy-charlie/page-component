package com.pagemanage.pagecomponent.tree;


import com.container.treecontainer.bo.ClassPersistence;
import com.container.treecontainer.bo.TreeConfig;
import com.container.treecontainer.container.TreeContainer;
import com.container.treecontainer.enump.IfNameRepeat;
import com.container.treecontainer.enump.IfPersistence;
import com.container.treecontainer.factory.TreeBeanFactory;
import com.pagemanage.pagecomponent.bo.BeanStructureInfo;
import com.pagemanage.pagecomponent.bo.BeanTreeStuctureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mist
 * @version 1.0
 * @created 29-12-2019 9:44:07
 */
@Component
public class BeanTreeStructureTreeConfig {
    @Autowired
    private TreeBeanFactory treeBeanFactory;

    public TreeContainer getTreeContainer(){
        return treeBeanFactory.getTreeContainer(getTreeConfig());
    }


    public TreeConfig getTreeConfig() {

        TreeConfig treeConfig = new TreeConfig();
        treeConfig.setTreeClassName("com.pagemanage.pagecomponent.tree.SystemStructureTree");
        treeConfig.setRootTreeBOClass(SystemStructureTree.class);
        treeConfig.setIfNameRepeat(IfNameRepeat.NOREPEAT);

        List<ClassPersistence> classPersistences = new ArrayList<>();

        ClassPersistence beanStructureInfo = new ClassPersistence();
        beanStructureInfo.setBoClassName(BeanStructureInfo.class.getName());
        beanStructureInfo.setIfPersistence(IfPersistence.PERSISTENC);
        beanStructureInfo.setIfShow(false);

        ClassPersistence beanTreeStuctureInfo = new ClassPersistence();
        beanTreeStuctureInfo.setBoClassName(BeanTreeStuctureInfo.class.getName());
        beanTreeStuctureInfo.setIfPersistence(IfPersistence.PERSISTENC);
        beanTreeStuctureInfo.setIfShow(true);

        classPersistences.add(beanStructureInfo);
        classPersistences.add(beanTreeStuctureInfo);

        treeConfig.setClassPersistenceList(classPersistences);

        return treeConfig;
    }
}