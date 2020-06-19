var itemTable = new commTable();

(function initSystem() {

    //初始化导入
    let uploadFile = initUploadFile("uploadFile", "formManage/importObj", null);
    uploadFile.Init();

    //导入完成
    fileUploaded("uploadFile");

    //初始化item表格
    initItemTable();
})();

/**
 * 导入文件——>打开模态框
 */
function importObj() {
    $("#form-importObjModal").modal('show');
}

/**
 * 导入文件
 */
function initUploadFile(ctrlName, uploadUrl, param) {
    var oFile = {};
    oFile.Init = function () {
        var control = $("#" + ctrlName);
        control.fileinput({
            language: "zh",//设置语言
            uploadUrl: uploadUrl,//上传的地址
            uploadAsync: true, //默认异步上传
            showUpload: false, //是否显示上传按钮
            showRemove: false, //显示移除按钮
            showPreview: true, //是否显示预览
            autoReplace: true, //再次选中文件时,替换掉当前文件
            showCaption: true,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            maxFileCount: 1,
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

/**
 * 文件上传完成
 */
function fileUploaded(ctrlName) {
    $("#" + ctrlName).on("fileuploaded", function (event, data, previewId, index) {
        $('#' + ctrlName).fileinput('clear').fileinput('unlock');

        $("#form-importObjModal").modal('hide');

        var rowData = [];

        var result = data.response;
        if (result.code == 200) {
            let data = result.data;
            $("#objName").val(data.beanName);
            let attrs = data.attributes;
            $.each(attrs, function (index, item) {
                rowData.push({
                    itemId: item.name,
                    label: null,
                    display: "'block'",
                    readonly: "'false'",
                    enable: "'true'",
                    required: "'true'",
                    validType: null
                });
            })
        }

        $("#itemTable").bootstrapTable('append', rowData);
    });
}


function addItemBtn(e, value, row, index) {
    //modal
    let modalConf = {
        id: 'addItemModal',
        frameObj: $('#modal_department'),
        title: '新增item',
        callbacks: {
            onCommit: commitAddItem                                                              //这里是填写方法名
        }
    };
    let nModal = new comModal();
    nModal.init(modalConf);

    //form
    let formOpt = {
        formId: 'nForm',
        initContent: itemContent
    };
    let nForm = new comForm();
    nForm.init(formOpt);

    //form加入到modal
    nModal.appendEle(nForm);

    function commitAddItem(e, modal) {
        let val = modal.getValues();
        itemTable.rawTable.bootstrapTable('insertRow', {//字符串
            index: 0,
            row: val
        });
        modal.hide();
    }
}


function initItemTable() {
    var panelHeight = $("#formConfigPanel").height();

    itemTable.init(
        {                                                                      //表头是固定的，数据要url请求
            tableID: "itemTable",
            loadMethod: 'button',                                                           //默认是scroll 需要其他的写button
            orderNumber: false,
            checkbox: false,
            isNeedOperate: true,                                           //操作列
            otherOperationArr: ['updateItem', 'removeItem'],
            operate: `<button type="button" class="updateItem btn-primary btn-xs">&nbsp;修改&nbsp;</button>&nbsp;&nbsp; 
                       <button type="button" class="removeItem btn-primary btn-xs">&nbsp;删除&nbsp;</button>&nbsp;&nbsp; `,
            updateItem: function (e, value, row, index) {                    //注册的点击事件多一个event参数
                updateItem(e, value, row, index);
            },
            removeItem: function (e, value, row, index) {
                removeItem(e, value, row, index);
            },
        }, {
            showRefresh: false,
            showExport: false,
            showColumns: true,
            search: false,
            height: panelHeight * 0.65,
            columns: getItemColumns(),
            pageNumber: 1,
            pageSize: [10000],
            responseHandler: function (data) {
                return data.data;
            }
        }
    );
}

function getItemColumns() {
    return [
        {
            field: 'itemId',
            title: 'itemId',
            align: "center",
            width: '138px',
            editable: {
                type: 'text',
                validate: function (v) {
                    if (!v) return '不能为空';
                }
            }
        }, {
            field: 'label',
            title: 'label',
            align: "center",
            width: '166px',
            editable: {
                type: 'text',
                validate: function (v) {
                    if (!v) return '不能为空';
                }
            }
        }, {
            field: 'display',
            title: 'display',
            align: "center",
            width: '123px',
            editable: {
                type: 'select',
                source: [{value: "'block'", text: "block"}, {value: "'inline'", text: "inline"}]
            }
        }, {
            field: 'readonly',
            title: 'readonly',
            align: "center",
            width: '122px',
            editable: {
                type: 'select',
                source: [{value: "'false'", text: "false"}, {value: "'true'", text: "true"}]
            }
        }, {
            field: 'enable',
            title: 'enable',
            align: "center",
            width: '116px',
            editable: {
                type: 'select',
                source: [{value: "'true'", text: "true"}, {value: "'false'", text: "false"}]
            }
        }, {
            field: 'required',
            title: 'required',
            align: "center",
            width: '129px',
            editable: {
                type: 'select',
                source: [{value: "'true'", text: "true"}, {value: "'false'", text: "false"}]
            }
        }, {
            field: 'validType',
            title: 'validType',
            align: "center",
            width: '152px',
            editable: {
                type: 'text',
                validate: function (v) {
                    if (!v) return '不能为空';
                }
            }
        }
    ]
}

function updateItem(e, value, row, index) {
    //modal
    let modalConf = {
        id: 'updateItemModal',
        frameObj: $('#modal_department'),
        title: '修改item',
        callbacks: {
            onCommit: commitUpdateItem                                                              //这里是填写方法名
        }
    };
    let nModal = new comModal();
    nModal.init(modalConf);

    let updateItemContent = JSON.parse(JSON.stringify(itemContent));
    var copyColumns = JSON.parse(JSON.stringify(getItemColumns()));
    copyColumns.forEach((ele, index) => {
        updateItemContent[index].value = row[ele.field]
    });

    //form
    let formOpt = {
        formId: 'nForm',
        initContent: updateItemContent
    };
    let nForm = new comForm();
    nForm.init(formOpt);

    //form加入到modal
    nModal.appendEle(nForm);

    function commitUpdateItem(e, modal) {
        let val = modal.getValues();
        itemTable.rawTable.bootstrapTable('updateRow', {
            index: index,
            row: val
        });
        modal.hide();
    }
}

function removeItem(e, value, row, index) {
    itemTable.rawTable.bootstrapTable('remove', {
        field: 'no',
        values: [index + 1]
    });
}

let itemContent = [
    {
        itemId: 'itemId',
        label: {
            text: 'itemId'
        }
    }, {
        itemId: 'label',
        label: {
            text: 'label'
        }
    }, {
        itemId: 'display',
        label: {
            text: 'display'
        },
        type: 'select',
        value: "'block'",
        options: {
            content: [{value: "'block'", text: 'block'}, {value: "'inline'", text: 'inline'}]
        }
    }, {
        itemId: 'readonly',
        label: {
            text: 'readonly'
        },
        type: 'select',
        value: "'false'",
        options: {
            content: [{value: "'false'", text: 'false'}, {value: "'true'", text: 'true'}]
        }
    }, {
        itemId: 'enable',
        label: {
            text: 'enable'
        },
        type: 'select',
        value: true,
        options: {
            content: [{value: "'false'", text: 'false'}, {value: "'true'", text: 'true'}]
        }
    }, {
        itemId: 'required',
        label: {
            text: 'required'
        },
        type: "select",
        value: true,
        options: {
            content: [{value: "'false'", text: 'false'}, {value: "'true'", text: 'true'}]
        },
    }, {
        itemId: 'validType',
        label: {
            text: 'validType'
        },
        required: false
    }];
