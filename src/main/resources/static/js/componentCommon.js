function requestAjax(type, url, async, data, dataType, callback) {
    $.ajax({
        type: type,
        contentType: "application/json",
        url: url,
        beforeSend: function () {
            console.log("准备好发送请求了");
        },
        complete: function () {
            console.log("请求发送完毕了");
        },
        async: async,
        data: data,
        dataType: dataType,
        success: function (result) {
            callback(result);
        },
        error: function (result) {
            callback(result);
        }
    });
}

/**
 * 导出文件
 * @param url  请求地址
 * @param fileName  文件名
 * @param suffix 文件后缀，如java/txt
 */
function exportFile(url, fileName, suffix) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);//get请求，请求地址，是否异步
    xhr.responseType = "blob";  // 返回类型blob
    xhr.onload = function () {// 请求完成处理函数
        if (this.status === 200) {
            var blob = this.response;// 获取返回值
            var a = document.createElement('a');
            a.download = fileName + '.' + suffix;
            a.href = window.URL.createObjectURL(blob);
            a.click();
        }
    };
    // 发送ajax请求
    xhr.send();
}

/**
 * 下载文件
 * @param filename 文件名
 * @param text 文件内容
 */
function downloadFile(filename, text) {
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}

/**
 * 将第一个字符换成小写
 * @param str
 */
function replaceFirstStrWithLowerCase(str) {
    let firstStr = str.substring(0, 1).toLowerCase();
    str = str.substring(1);
    return firstStr + str;
}

/**
 * 导入文件
 */
function initUploadFile(ctrlName, uploadUrl, param,uploadAsync) {
    var oFile = {};
    oFile.Init = function () {
        var control = $("#" + ctrlName);
        control.fileinput({
            language: "zh",//设置语言
            uploadUrl: uploadUrl,//上传的地址
            // uploadExtraData: param, //上传的时候，增加的附加参数
            // maxFilesNum: 10,//上传最大的文件数量
            uploadAsync: uploadAsync, //默认异步上传(true)
            showUpload: false, //是否显示上传按钮
            showRemove: false, //显示移除按钮
            showPreview: true, //是否显示预览
            autoReplace: true, //再次选中文件时,替换掉当前文件
            showCaption: true,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            maxFileCount: 5,
            enctype: 'multipart/form-data',
            validateInitialCount: true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>"
        }).on('fileerror', function (event, data, msg) {  //一个文件上传失败
            alert("导入失败");
        }).on('filebatchuploaderror', function (event, data, msg) {
            alert("导入失败！");
        });
    };
    return oFile;
}


function iterateObj(iterateContent, strContent, attrNum) {
    strContent += "{";
    var attrLength = 0;
    Object.keys(iterateContent).forEach(function (key) {
        var attr = iterateContent[key];
        attrLength++;
        if (attr instanceof Object) {
            if (attr instanceof Array) {
                strContent += key + ":" + iterateContent[key] + ",";
            } else {
                strContent += key + ":";
                strContent += iterateObj(attr, '', 0)
            }
        } else {
            strContent += key + ":" + iterateContent[key] + ",";
        }
    });
    strContent += "},\n";
    return strContent;
}
