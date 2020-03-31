package com.treemanage.treecomponent.mapper;
import com.treemanage.treecomponent.pojo.POBeanStructureInfo;
import com.treemanage.treecomponent.bo.BeanStructureInfo;
import com.bocontainer.common.CommonMapper;
import org.springframework.stereotype.Component;

@Component
public interface POBeanStructureInfoMapper extends CommonMapper<POBeanStructureInfo,BeanStructureInfo> {
}