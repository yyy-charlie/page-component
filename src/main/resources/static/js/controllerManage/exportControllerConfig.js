
//导出Java配置内容
var exportJavaConfigContent = "";

function exportConfig() {
    //获取导出内容
    getInitContent();
    //导出Java文件配置
    exportJavaConfig();
}

/**
 * 导出Java文件配置
 */
function exportJavaConfig() {
    //下载文件
    let controllerName = $("#controllerName").val();
    downloadFile(controllerName + ".java", exportJavaConfigContent); //fileContent is string
    exportJavaConfigContent = "";
}

/**
 * 获取导出内容
 */
function getInitContent() {
    let controllerName = $("#controllerName").val();
    let rowsDataArr = methodTable.rawTable.bootstrapTable('getOptions').data;

    exportJavaConfigContent += `
    import com.hdstcloud.security.common.msg.BaseResponse;
    import com.hdstcloud.security.common.util.ParseUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.http.HttpStatus;`;

    exportJavaConfigContent += `
    @RestController
    @RequestMapping("${controllerName}")
    public class ${controllerName}{`;

    for (let key of serviceMap.keys()) {
        exportJavaConfigContent += `
        @Autowired
        private ${key} ${replaceFirstStrWithLowerCase(key)};`;
    }

    $.each(rowsDataArr, function (index, item) {
        let serviceReturn = "";
        let serviceReturnType = "";
        let callMethod = "";
        let serviceParam = "";

        let serviceRealParam = item.param.split(" ")[1];

        $.each(serviceMap.get(item.service), function (index, s) {
            if (s.methodName == item.callMethod) {
                if (item.returnStr.indexOf("List") == 0) {
                    serviceReturn = "list";
                    serviceReturnType = s.returnStr.split("<")[1].split(">")[0];
                } else if (item.returnStr.indexOf("boolean") == 0) {
                    serviceReturn = "flag";
                    serviceReturnType = "boolean";
                } else {
                    serviceReturn = s.returnStr;
                    serviceReturnType = s.returnStr;
                }
                callMethod = s.methodName;
                if (s.param != null) {
                    serviceParam = s.param.split(" ")[1];
                }
                return true;
            }
        });
        exportJavaConfigContent += `
        /**
        * ` + item.description + `
        */`;

        exportJavaConfigContent += `
        @RequestMapping("${item.methodName}")`;

        if (item.param == "") {
            serviceRealParam = "";

            exportJavaConfigContent += `
        public BaseResponse ${item.methodName} () {`;
        } else {
            exportJavaConfigContent += `
        public BaseResponse ${item.methodName} (@RequestBody ${item.param}) {`;
        }

        exportJavaConfigContent += `
            String description = "${item.description}";`;

        if (serviceReturnType != "boolean") {
            exportJavaConfigContent += `${item.returnStr} result = null;`
        } else {
            exportJavaConfigContent += `${serviceReturnType} ${serviceReturn} = false;`
        }

        exportJavaConfigContent += `
        try{
        `;

        if (item.vo2Bo == "true") {
            exportJavaConfigContent += `${item.bo} ${replaceFirstStrWithLowerCase(item.bo)} = ToolUtils.autoCopyProperties(${item.param.split(" ")[1]},${item.bo}.class);`;
            serviceRealParam = replaceFirstStrWithLowerCase(item.bo);
        }

        if (serviceReturn == "list") {
            if (item.bo2Vo == "true") {
                exportJavaConfigContent += `List<${serviceReturnType}> ${serviceReturn} = ${replaceFirstStrWithLowerCase(item.service)}.${callMethod}(${serviceRealParam});`;
                let controllerReturnType = item.returnStr.split("<")[1].split(">")[0];
                exportJavaConfigContent += `
            for(${serviceReturnType} ${replaceFirstStrWithLowerCase(serviceReturnType)}:${serviceReturn}){
            ${controllerReturnType} ${replaceFirstStrWithLowerCase(controllerReturnType)} = new ${controllerReturnType}();
            ToolUtils.copyProperties(${replaceFirstStrWithLowerCase(serviceReturnType)},${replaceFirstStrWithLowerCase(controllerReturnType)});
            result.add(${replaceFirstStrWithLowerCase(controllerReturnType)});
            }
            `;
            } else {
                exportJavaConfigContent += `result = ${replaceFirstStrWithLowerCase(item.service)}.${callMethod}(${serviceRealParam});`;
            }
        } else if (serviceReturn == "flag") {
            exportJavaConfigContent += `${serviceReturn} =  ${replaceFirstStrWithLowerCase(item.service)}.${callMethod}(${serviceRealParam});`;
        } else {
            if (item.bo2Vo == "true") {
                exportJavaConfigContent += `${serviceReturnType} ${replaceFirstStrWithLowerCase(serviceReturn)} = ${replaceFirstStrWithLowerCase(item.service)}.${callMethod}(${serviceRealParam})`;

                exportJavaConfigContent += `
            result = ToolUtils.autoCopyProperties(${replaceFirstStrWithLowerCase(serviceReturn)},${item.vo}.class);
            `;
            } else {
                exportJavaConfigContent += `result = ${replaceFirstStrWithLowerCase(item.service)}.${callMethod}(${serviceRealParam});`;
            }
        }

        exportJavaConfigContent += `} catch (Exception e) {
            return ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "失败");
        }`;

        if (serviceReturnType == "boolean") {
            exportJavaConfigContent += `
            if (${replaceFirstStrWithLowerCase(serviceReturn)}){
            return ParseUtils.parse2Response(HttpStatus.OK,description+"成功！");
        }else {
            return ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR,description+"失败！");
        }
      }`
        } else {
            exportJavaConfigContent += `
        return ParseUtils.parse2Response(HttpStatus.OK, description + "成功！" + result);
        }`
        }
    });

    exportJavaConfigContent += `
    }`;
}