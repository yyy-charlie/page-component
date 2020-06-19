var defaultOption = {
    dataParam: {}
};
var bootstrapOption = {};

var initContent = "";
var functionContent = "";

var exportJavaConfigContent = "";

var tableID;
var objName;

function exportConfig() {
    tableID = $("#tableID").val();
    objName = $("#tableVoName").val();

    //获取配置table的js文件内容并导出
    getTableOption();
    downloadFile("tableConfig.js", initContent + functionContent); //fileContent is string

    //获取controller文件内容并导出
    // getJavaConfigContent();
    // downloadFile(objName + "Controller.java", exportJavaConfigContent); //fileContent is string

    clearContent();
}

function getTableOption() {
    //获取option的对象
    getDefaultOption();
    //将option对象转为字符串
    let defaultOptionStr = "";
    let defaultOptionAttrNum = Object.keys(defaultOption).length;
    defaultOptionStr = iterateObj(defaultOption, defaultOptionStr, defaultOptionAttrNum);

    //获取bootstrapOption的对象
    getBootstrapOption();
    //将bootstrapOption对象转为字符串
    let bootstrapOptionStr = "";
    let bootstrapOptionAttrNum = Object.keys(bootstrapOption).length;
    bootstrapOptionStr = iterateObj(bootstrapOption, bootstrapOptionStr, bootstrapOptionAttrNum);

    initContent += `var ` + tableID + ` = new commTable();
    $(document).ready(function () {
        initTable();
    });\n`;
    //拼接
    initContent += `function initTable(){
        ` + tableID + `.init(
        ` + defaultOptionStr + bootstrapOptionStr + `
        );
    }
    `;
}

function getDefaultOption() {
    let dataUrl = $("#getTableUrl").val();
    let orderNumber = $("#orderNumber").val();
    let isNeedDetails = $("#isNeedDetails").val();
    let detailsParam = $("#detailsParam").val();

    let isNeedOperate = $("#isNeedOperate").val();
    let operateParam = $("#operateParam").val();

    defaultOption.tableID = JSON.stringify(tableID);
    //请求url
    defaultOption.dataUrl = JSON.stringify(dataUrl);
    //第一次请求参数
    defaultOption.dataParam.data = "getDataParam";
    functionContent += `function getDataParam(){
        return JSON.stringify({});
    }\n`;
    defaultOption.dataParam.contentType = JSON.stringify("application/json");
    //刷新表格请求参数
    defaultOption.queryTableParams = "getQueryTableParams";
    functionContent += `function getQueryTableParams(){
    
        }`;
    //序列号
    defaultOption.orderNumber = Boolean(orderNumber);
    defaultOption.checkbox = false;

    //是否需要详情列
    if (isNeedDetails == "1") {
        var detailButton = "";
        defaultOption.isNeedDetails = true;
        defaultOption.details = "getDetailsButton";

        let detailsParamArray = detailsParam.split(",");
        let detailsParamArr = [];
        $.each(detailsParamArray, function (index, item) {
            let array = item.split("/");
            detailButton += `<button type="button" class="${array[0]} btn-primary btn-xs">&nbsp;${array[1]}&nbsp;</button>&nbsp;&nbsp;`;
            detailsParamArr.push(array[0]);
        });

        functionContent += `function getDetailsButton(){
        return '` + detailButton + `'
        }`;
        defaultOption.otherDetailsArr = JSON.stringify(detailsParamArr);
        $.each(detailsParamArr, function (index, item) {
            defaultOption[item] = item;

            functionContent += `function ${item}(e, value, row, index){
            }`;
        });
    } else {
        defaultOption.isNeedDetails = false;
    }

    //是否需要操作列
    if (isNeedOperate == "1") {
        var operateButton = '';
        defaultOption.isNeedOperate = true;
        defaultOption.operate = "getOperateButton";
        //如：update/修改,orderRemove/删除
        let operateParamArray = operateParam.split(",");
        let otherOperationArr = [];
        $.each(operateParamArray, function (index, item) {
            let array = item.split("/");
            operateButton += `<button type="button" class="${array[0]} btn-primary btn-xs">&nbsp;${array[1]}&nbsp;</button>&nbsp;&nbsp;`;
            otherOperationArr.push(array[0]);
        });

        //操作按钮方法
        functionContent += `function getOperateButton(){
        return '` + operateButton + `'
        }`;

        defaultOption.otherOperationArr = JSON.stringify(otherOperationArr);
        $.each(otherOperationArr, function (index, item) {
            defaultOption[item] = item;

            functionContent += `function ${item}(e, value, row, index){
            }`;
        })
    } else {
        defaultOption.isNeedOperate = false;
    }
}

function getBootstrapOption() {
    let columns = $("#columns").val();
    bootstrapOption.columns = `getColumns()`;
    if (columns) {
        let rowsDataArr = fieldTable.rawTable.bootstrapTable('getOptions').data;
        let columnArr = [];
        $.each(rowsDataArr, function (index, item) {
            columnArr.push({
                field: item.field,
                title: item.title
            })
        });
        functionContent += `function getColumns(){
            return ` + JSON.stringify(columnArr) + `;
    }\n`;
    } else {
        functionContent += `function getColumns(){
        
    }\n`;
    }

    bootstrapOption.search = false;
    bootstrapOption.height = JSON.stringify('');
    bootstrapOption.sidePagination = JSON.stringify($("#sidePagination").val());
    bootstrapOption.pageSize = JSON.stringify([10000]);

    bootstrapOption.responseHandler = `responseHandler`;
    functionContent += `function responseHandler(res){
        return res.data;
    }\n`;

}

function clearContent() {
    initContent = "";
    functionContent = "";
    exportJavaConfigContent = "";
}

/**
 * 获取Java配置内容
 */
function getJavaConfigContent() {
    let objNameHump = replaceFirstStrWithLowerCase(objName);
    let getTableUrl = $("#getTableUrl").val();
    let addTableFieldUrl = $("#addTableFieldUrl").val();
    let updateTableFieldUrl = $("#updateTableFieldUrl").val();
    let deleteTableFieldUrl = $("#deleteTableFieldUrl").val();

    exportJavaConfigContent += `
    import com.hdstcloud.security.common.msg.BaseResponse;
    
    @RestController
    @RequestMapping("` + objNameHump + `")
    public class ` + objNameHump + `Controller {
    
        @RequestMapping("/` + getTableUrl + `")
        public BaseResponse add` + objName + `(@RequestBody ` + objName + ` ` + objNameHump + `) {
        
            return null;
        }
        
        @RequestMapping("/` + addTableFieldUrl + `")
        public BaseResponse add` + objName + `(@RequestBody ` + objName + ` ` + objNameHump + `) {
        
            return null;
        }
        
        @RequestMapping("/` + updateTableFieldUrl + `")
        public BaseResponse update` + objName + `(@RequestBody ` + objName + ` ` + objNameHump + `) {
        
            return null;
        }
        
        @RequestMapping("/` + deleteTableFieldUrl + `")
        public BaseResponse delete` + objName + `(@RequestBody ` + objName + ` ` + objNameHump + `) {
        
            return null;
        }
    }
    `;
}