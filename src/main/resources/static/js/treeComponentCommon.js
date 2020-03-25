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