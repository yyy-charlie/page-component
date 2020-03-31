(function initSystem() {

})();

//导出js配置内容
var exportJsConfigContent = "";
//导出Java配置内容
var exportJavaConfigContent = "";


function exportConfig() {
    //导出页面表单配置
    exportFormConfig();
    //导出Java文件配置
    exportJavaConfig();
}

/**
 * 导出表单配置
 */
function exportFormConfig() {
    //获取初始化content
    let contentArr = getInitContent();
    //通过全局变量存放js导出内容
    saveJsExportContent(contentArr);
    //下载文件
    downloadFile("formConfig.js", exportJsConfigContent); //fileContent is string
}

/**
 * 导出Java文件配置
 */
function exportJavaConfig() {
    getJavaConfigContent();
    //下载文件
    let objName = $("#objName").val();
    downloadFile(objName + "Controller.java", exportJavaConfigContent); //fileContent is string
}

/**
 * 获取表单初始化内容
 * @returns {Array}
 */
function getInitContent() {
    let rowsDataArr = itemTable.rawTable.bootstrapTable('getOptions').data;
    let contentArr = [];
    $.each(rowsDataArr, function (index, item) {
        let itemContent = {
            itemId: item.itemId,
            label: {
                text: item.label
            },
            display: item.display,
            readonly: item.readonly,
            enable: item.enable,
            required: item.required,
            validType: item.validType
        };
        //过滤默认值
        itemContent = filterDefault(itemContent);
        contentArr.push(itemContent)
    });
    return contentArr;
}

/**
 * 通过全局变量存放导出内容
 * @param contentArr
 */
function saveJsExportContent(contentArr) {
    let type = $("#type").val();
    //block为默认值，默认值缺省，不为默认值则加入到配置
    if (type == 'block') {
        exportJsConfigContent += `
    //form
    let formOpt = {
        formId: ` + $("#formId").val() + `,
        initContent: ` + JSON.stringify(contentArr) + `
    };
    let nForm = new comForm();
    nForm.init(formOpt);`;
    } else {
        exportJsConfigContent += `
    //form
    let formOpt = {
        formId: ` + $("#formId").val() + `,
        type: ` + type + `,
        initContent: ` + JSON.stringify(contentArr) + `
    };
    let nForm = new comForm();
    nForm.init(formOpt);`;
    }

}

/**
 * 过滤默认值
 * @param itemContent
 * @returns {*}
 */
function filterDefault(itemContent) {
    if (itemContent.display == "block") {
        delete itemContent.display;
    }
    if (itemContent.readonly == "false" || itemContent.readonly == false) {
        delete itemContent.readonly;
    }
    if (itemContent.enable == "true" || itemContent.enable == true) {
        delete itemContent.enable;
    }
    if (itemContent.required == "true" || itemContent.required == true) {
        delete itemContent.required;
    }
    if (itemContent.validType == null || itemContent.validType == "") {
        delete itemContent.validType;
    }
    return itemContent;
}


/**
 * 获取Java配置内容
 */
function getJavaConfigContent() {
    let objName = $("#objName").val();
    let objNameHump = replaceFirstStrWithLowerCase(objName);
    let addFormUrl = $("#addFormUrl").val();
    let editFormUrl = $("#editFormUrl").val();
    let deleteFormUrl = $("#deleteFormUrl").val();

    exportJavaConfigContent += `
    import com.hdstcloud.security.common.msg.BaseResponse;
    
    @RestController
    @ResponseBody
    @RequestMapping("` + objNameHump + `")
    public class ` + objNameHump + `Controller {
        
        @RequestMapping("/` + addFormUrl + `")
        public BaseResponse add` + objName + `(@RequestBody ` + objName + ` ` + objNameHump + `) {
        
            return null;
        }
        
        @RequestMapping("/` + editFormUrl + `")
        public BaseResponse update` + objName + `(@RequestBody ` + objName + ` ` + objNameHump + `) {
        
            return null;
        }
        
        @RequestMapping("/` + deleteFormUrl + `")
        public BaseResponse delete` + objName + `(@RequestBody ` + objName + ` ` + objNameHump + `) {
        
            return null;
        }
    }
    `;
}




