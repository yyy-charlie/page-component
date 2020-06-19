package com.treemanage.treecomponent.export.impl;

import com.container.treecontainer.util.ObjectUtils;
import com.treemanage.treecomponent.bo.BeanTreeStuctureInfo;
import com.treemanage.treecomponent.bo.NodeStructureInfo;
import com.treemanage.treecomponent.export.intf.ExportBeanTree;
import com.treemanage.treecomponent.mapper.POBeanStructureInfoMapper;
import com.treemanage.treecomponent.pojo.POBeanStructureInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Mist
 * @Version: 1.0
 * @Date: 2020/1/4 11:46
 */
@Service
public class ExportBeanTreeImpl implements ExportBeanTree {

    @Autowired
    private POBeanStructureInfoMapper poBeanStructureInfoMapper;

    @Override
    public String exportBeanTreeStructureInfo(BeanTreeStuctureInfo beanTreeStuctureInfo) {

        StringBuffer stringBuffer = new StringBuffer();

        setPackage(stringBuffer,beanTreeStuctureInfo);

        setInport(stringBuffer,beanTreeStuctureInfo);

        setClass(stringBuffer,beanTreeStuctureInfo);

        stringBuffer.append("\n");

        getTreeContainer(stringBuffer);

        getTreeConfig(stringBuffer,beanTreeStuctureInfo);

        stringBuffer.append("\n")
                .append("\t\treturn treeConfig;\n")
                .append("\t}\n")
                .append("}\n")

        ;
        return stringBuffer.toString();
    }

    private void getTreeConfig(StringBuffer stringBuffer, BeanTreeStuctureInfo beanTreeStuctureInfo) {
        stringBuffer.append("\tpublic TreeConfig getTreeConfig() {\n")
                .append("\n")
                .append("\t\tTreeConfig treeConfig = new TreeConfig();\n")
                .append("\t\ttreeConfig.setTreeClassName(\""+beanTreeStuctureInfo.getBeanTreeClassName()+"\");\n")
                .append("\t\ttreeConfig.setIfNameRepeat("+getIfNameRepeat(beanTreeStuctureInfo)+");\n")
                .append("\n");

        setClassPersistence(stringBuffer,beanTreeStuctureInfo);

        stringBuffer.append("\n\t\ttreeConfig.setClassPersistenceList(classPersistences);\n");
    }

    private void setClassPersistence(StringBuffer stringBuffer, BeanTreeStuctureInfo beanTreeStuctureInfo) {
        if(ObjectUtils.judgeList(beanTreeStuctureInfo.getNodeStructureInfos())){
            stringBuffer.append("\t\tList<ClassPersistence> classPersistences = new ArrayList<>();\n\n");

            for (NodeStructureInfo one:beanTreeStuctureInfo.getNodeStructureInfos()) {

                stringBuffer.append("\t\tClassPersistence "+getBeanNameLower(one)+" = new ClassPersistence();\n")
                            .append("\t\tbeanStructureInfo.setBoClassName("+getBeanName(one)+".class.getName());\n")
                            .append("\t\tbeanStructureInfo.setIfPersistence("+getIfPersistence(one)+");\n")
                            .append("\t\tbeanStructureInfo.setIfShow(true);\n")
                            .append("\t\tclassPersistences.add("+getBeanNameLower(one)+");\n")
                ;

            }
        }
    }

    private String getIfPersistence(NodeStructureInfo one) {
        return one.getIfPersistence()?"IfPersistence.PERSISTENC":"IfPersistence.NOPERSISTENC";
    }

    private String getBeanNameLower(NodeStructureInfo one) {
        POBeanStructureInfo po = poBeanStructureInfoMapper.selectByPrimaryKey(one.getBeanStructureId());
        if(!ObjectUtils.isNull(po)){
            String str = po.getBeanName();
            return str.substring(0,1).toLowerCase()+str.substring(1);
        }
        return null;
    }
    private String getBeanName(NodeStructureInfo one) {
        POBeanStructureInfo po = poBeanStructureInfoMapper.selectByPrimaryKey(one.getBeanStructureId());
        if(!ObjectUtils.isNull(po)){
            return po.getBeanName();
        }
        return null;
    }

    private String getIfNameRepeat(BeanTreeStuctureInfo beanTreeStuctureInfo) {
        return beanTreeStuctureInfo.getIfNameRepeat()?"IfNameRepeat.REPEAT":"IfNameRepeat.NOREPEAT";
    }

    private void getTreeContainer(StringBuffer stringBuffer) {
        stringBuffer.append("\t@Autowired\n")
                .append("\tprivate TreeBeanFactory treeBeanFactory;\n")
                .append("\n")
                .append("\tpublic TreeContainer getTreeContainer(){\n")
                .append("\t\treturn treeBeanFactory.getTreeContainer(getTreeConfig());\n")
                .append("\t}\n")
                .append("\n\n")
        ;
    }

    private void setClass(StringBuffer stringBuffer, BeanTreeStuctureInfo beanTreeStuctureInfo) {
        stringBuffer.append("@Component\n");
        stringBuffer.append("public class "+beanTreeStuctureInfo.getName()+"Config {\n");
    }

    private void setPackage(StringBuffer stringBuffer, BeanTreeStuctureInfo beanTreeStuctureInfo) {
        stringBuffer.append("package "+beanTreeStuctureInfo.getBeanTreePackage()+";\n\n\n");
    }

    private void setInport(StringBuffer stringBuffer, BeanTreeStuctureInfo beanTreeStuctureInfo) {

        stringBuffer.append("import com.container.treecontainer.bo.ClassPersistence;\n")
                .append("import com.container.treecontainer.bo.TreeConfig;\n")
                .append("import com.container.treecontainer.container.TreeContainer;\n")
                .append("import com.container.treecontainer.enump.IfNameRepeat;\n")
                .append("import com.container.treecontainer.enump.IfPersistence;\n")
                .append("import com.container.treecontainer.factory.TreeBeanFactory;\n")
                .append("\n")
                .append("import org.springframework.beans.factory.annotation.Autowired;\n")
                .append("import org.springframework.stereotype.Component;\n")
                .append("\n")
                .append("import java.util.ArrayList;\n")
                .append("import java.util.List;\n")
                .append("\n")
        ;

        for (NodeStructureInfo one:beanTreeStuctureInfo.getNodeStructureInfos()){
            stringBuffer.append("import "+getBeanClassName(one)+";\n") ;
        }
        stringBuffer.append("\n") ;
    }

    private String getBeanClassName(NodeStructureInfo one) {
        POBeanStructureInfo po = poBeanStructureInfoMapper.selectByPrimaryKey(one.getBeanStructureId());
        if(!ObjectUtils.isNull(po)) return po.getBeanClassName();
        return null;
    }
}
