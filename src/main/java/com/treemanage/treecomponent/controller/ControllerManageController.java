package com.treemanage.treecomponent.controller;

import com.container.treecontainer.util.ObjectUtils;
import com.treemanage.treecomponent.bo.AttrStructureInfo;
import com.treemanage.treecomponent.msg.BaseResponse;
import com.treemanage.treecomponent.util.ParseUtils;
import com.treemanage.treecomponent.util.ToolUtils;
import com.treemanage.treecomponent.vo.ControllerVo;
import com.treemanage.treecomponent.vo.MethodVo;
import com.treemanage.treecomponent.vo.ServicesVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ControllerManageController
 * @Description TODO
 * @Author ycn
 * @Date 2020-04-07
 **/
@RestController
public class ControllerManageController {
    /**
     * 导入controller文件
     *
     * @param controllerFile
     * @return
     */
    @RequestMapping("/importController")
    public BaseResponse importController(MultipartFile controllerFile) {
        if (ToolUtils.isNull(controllerFile)) {
            return ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, "参数为空");
        }
        InputStream is = null;
        BufferedReader bufferedReader = null;
        ControllerVo controllerVo = new ControllerVo();
        List<MethodVo> methodVoList = new ArrayList<>(16);
        try {
            is = controllerFile.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(is));

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                str = str.trim();
                //获取控制器名称
                if (str.matches("public interface.*")) {
                    String[] strings = str.split("[ ]");
                    controllerVo.setControllerName(strings[2]);
                } else if (str.matches("public.*")) {
                    //获取控制器中的方法（包括方法名、参数和返回值）
                    MethodVo methodVo = new MethodVo();
                    String[] strings = str.split("[ ]");
                    methodVo.setReturnStr(strings[1]);
                    //addClassType(MaterialType
                    String[] split = strings[2].split("[(]");
                    methodVo.setMethodName(split[0]);
                    //MaterialType materialType
                    if (strings.length != 3) {
                        methodVo.setParam(split[1] + " " + strings[3].split("[)]")[0]);
                    }
                    methodVoList.add(methodVo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        controllerVo.setMethodVoList(methodVoList);
        return ParseUtils.parse2Response(HttpStatus.OK, "导入controller成功！", controllerVo);
    }

    /**
     * 批量导入services文件
     *
     * @param serviceFiles
     * @return
     */
    @RequestMapping("/importService")
    public BaseResponse importService(List<MultipartFile> serviceFiles) {
        if (ToolUtils.isNull(serviceFiles)) {
            return ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, "参数为空");
        }
        InputStream is = null;
        BufferedReader bufferedReader = null;
        List<ServicesVo> servicesVoList = new ArrayList<>(8);
        try {
            for (MultipartFile serviceFile : serviceFiles) {
                ServicesVo servicesVo = new ServicesVo();

                is = serviceFile.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(is));

                List<MethodVo> methodVoList = new ArrayList<>(16);
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    str = str.trim();
                    if (str.matches("public interface.*")) {
                        String[] strings = str.split("[ ]");
                        servicesVo.setServiceName(strings[2]);
                    } else if (str.matches("public.*")) {
                        //获取方法（包括方法名、参数和返回值）
                        MethodVo methodVo = new MethodVo();
                        String[] strings = str.split("[ ]");
                        methodVo.setReturnStr(strings[1]);
                        //addClassType(MaterialType
                        String[] split = strings[2].split("[(]");
                        methodVo.setMethodName(split[0]);
                        //MaterialType materialType
                        if (strings.length != 3) {
                            methodVo.setParam(split[1] + " " + strings[3].split("[)]")[0]);
                        }
                        methodVoList.add(methodVo);
                    }
                }
                servicesVo.setMethodVoList(methodVoList);
                servicesVoList.add(servicesVo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ParseUtils.parse2Response(HttpStatus.OK, "导入Services成功！", servicesVoList);
    }
}
