var methodTable = new commTable();
var serviceMap = new Map();

(function () {

    //初始化controller导入
    let controllerFile = initUploadFile("controllerFile", "/importController", null, true);
    controllerFile.Init();
    //导入完成
    controllerFileUploaded("controllerFile", "importControllerModal");

    //初始化services导入
    let servicesFile = initUploadFile("serviceFiles", "/importService", null, false);
    servicesFile.Init();
    //导入完成
    servicesFileUploaded("serviceFiles", "importServicesModal");

    //初始化method表格
    initMethodTable();

    //页面交互
    pageInteraction();

})();

function importControllerBtn() {
    $("#importControllerModal").modal('show');
}

function importServicesBtn() {
    $("#importServicesModal").modal('show');
}

/**
 * 文件上传完成
 */
function controllerFileUploaded(ctrlName, modalName) {
    $("#" + ctrlName).on("fileuploaded", function (event, data, previewId, index) {
        $('#' + ctrlName).fileinput('clear').fileinput('unlock');

        $("#" + modalName).modal('hide');

        var rowData = [];

        var result = data.response;
        if (result.code == 200) {
            let attrs = result.data.methodVoList;
            $("#controllerName").val(result.data.controllerName);
            $.each(attrs, function (index, item) {
                rowData.push({
                    methodName: item.methodName,
                    param: item.param,
                    returnStr: item.returnStr,
                });
            })
        }
        $("#methodTable").bootstrapTable('append', rowData);
    });
}


function servicesFileUploaded(ctrlName, modalName) {
    $('#' + ctrlName).on('filebatchuploadsuccess', function (event, data, previewId, index) {
        $('#' + ctrlName).fileinput('clear').fileinput('unlock');

        $("#" + modalName).modal('hide');

        var rowData = [];

        var result = data.response;
        if (result.code == 200) {
            let attrs = result.data;
            $.each(attrs, function (index, item) {
                serviceMap.set(item.serviceName, item.methodVoList);
            });

            let selfService = $("#service");
            selfService.empty();
            for (let key of serviceMap.keys()) {
                selfService.append("<option value='" + key + "'>" + key + "</option>");
            }

            $("#callInterface").empty();
            $.each(serviceMap.get(selfService.val()), function (index, item) {
                $("#callInterface").append("<option value='" + item.methodName + "'>" + item.methodName + "</option>");
            })
        }
    });
}

function initMethodTable() {
    var panelHeight = $("#controllerConfigPanel").height();

    methodTable.init(
        {                                                                      //表头是固定的，数据要url请求
            tableID: "methodTable",
            loadMethod: 'button',                                                           //默认是scroll 需要其他的写button
            orderNumber: true,
            checkbox: false,
            isNeedOperate: true,                                           //操作列
            otherOperationArr: ['updateMethod', 'removeMethod'],
            operate: `<button type="button" class="updateMethod btn-primary btn-xs">&nbsp;修改&nbsp;</button>&nbsp;&nbsp; 
                        <button type="button" class="removeMethod btn-primary btn-xs">&nbsp;删除&nbsp;</button>&nbsp;&nbsp; `,
            removeMethod: function (e, value, row, index) {
                removeMethod(e, value, row, index);
            },
            updateMethod: function (e, value, row, index) {
                updateMethod(e, value, row, index);
            }
        }, {
            showRefresh: false,
            showExport: false,
            showColumns: true,
            search: false,
            height: panelHeight * 0.7,
            columns: [
                {
                    field: 'methodName',
                    title: '方法名',
                    align: "center"
                }, {
                    field: 'description',
                    title: '描述',
                    align: "center"
                }, {
                    field: 'param',
                    title: '参数',
                    align: "center"
                }, {
                    field: 'returnStr',
                    title: '返回值',
                    align: "center"
                }, {
                    field: 'bo',
                    title: 'vo转bo',
                    align: "center"
                }, {
                    field: 'service',
                    title: 'service',
                    align: "center"
                }, {
                    field: 'callMethod',
                    title: '调用方法',
                    align: "center"
                }, {
                    field: 'vo',
                    title: 'vo',
                    align: "center"
                }],
            pageNumber: 1,
            pageSize: [10000],
            responseHandler: function (data) {
                return data.data;
            },
            onEditableSave: function (field, row, oldValue, $el) {
                //解决表头与内容宽度不对齐的问题
                $("#methodTable").bootstrapTable('resetView');
            }
        }
    );
}

function removeMethod(e, value, row, index) {
    methodTable.rawTable.bootstrapTable('remove', {
        field: 'no',
        values: [index + 1]
    });
}

function updateMethod(e, value, row, index) {
    if (serviceMap.size == 0) {
        alert("请先导入services！");
        return;
    }

    let services = [];
    for (let key of serviceMap.keys()) {
        services.push({value: key, text: key});
    }
    let callMethods = [];
    $.each(serviceMap.get(services[0].value), function (index, item) {
        callMethods.push({value: item.methodName, text: item.methodName});
    });

    let vo2Bo = "true";
    let bo2Vo = "true";
    if (row.bo == null || row.bo == '' || row.bo == undefined) {
        vo2Bo = "false";
    }
    if (row.vo == null || row.vo == '' || row.vo == undefined) {
        bo2Vo = "false";
    }
    //modal
    let modalConf = {
        id: 'updateMethodModal',
        frameObj: $('#modal_department'),
        title: '修改方法',
        callbacks: {
            onCommit: commitUpdateMethod                                                              //这里是填写方法名
        }
    };
    let nModal = new comModal();
    nModal.init(modalConf);
    //form
    let formOpt = {
        formId: 'nForm',
        initContent: [{
            itemId: 'methodName',
            label: {
                text: '方法名'
            },
            value: row.methodName
        }, {
            itemId: 'description',
            label: {
                text: '方法描述'
            },
            value: row.description
        }, {
            itemId: 'param',
            label: {
                text: '参数'
            },
            required: false,
            value: row.param
        }, {
            itemId: 'returnStr',
            label: {
                text: '返回值'
            },
            value: row.returnStr
        }, {
            itemId: 'vo2Bo',
            label: {
                text: 'vo转bo'
            },
            type: 'select',
            options: {
                content: [{value: "true", text: 'true'}, {value: "false", text: 'false'}]
            },
            value: vo2Bo,
            event: {
                onchange: changeEditBo
            }
        }, {
            itemId: 'bo',
            label: {
                text: 'bo类'
            },
            value: row.bo,
        }, {
            itemId: 'service',
            label: {
                text: '调用service'
            },
            type: 'select',
            options: {
                content: services
            },
            value: row.service,
            event: {
                onchange: changeEditService
            }
        }, {
            itemId: 'callMethod',
            label: {
                text: '调用方法'
            },
            type: 'select',
            options: {
                content: callMethods
            },
            value: row.callMethod
        }, {
            itemId: 'bo2Vo',
            label: {
                text: 'bo转vo'
            },
            type: 'select',
            options: {
                content: [{value: "true", text: 'true'}, {value: "false", text: 'false'}]
            },
            value: bo2Vo,
            event: {
                onchange: changeEditVo
            }
        }, {
            itemId: 'vo',
            label: {
                text: 'vo类'
            },
            value: row.vo
        }]
    };
    let nForm = new comForm();
    nForm.init(formOpt);
    //form加入到modal
    nModal.appendEle(nForm);

    var editVo = nForm.getItemById('vo');
    var editBo = nForm.getItemById('bo');
    if (vo2Bo === "false") editVo.hide();
    if (bo2Vo === "false") editBo.hide();

    function changeEditService(e) {
        var editCallMethod = nForm.getItemById('callMethod');
        let callMethods = [];
        $.each(serviceMap.get(e.target.value), function (index, item) {
            callMethods.push({value: item.methodName, text: item.methodName});
        });
        editCallMethod.reloadOptions(callMethods);
    }

    function changeEditBo(e) {
        var editBo = nForm.getItemById('bo');
        if (e.target.value == "true") {
            editBo.show();
        } else {
            editBo.hide();
        }
    }

    function changeEditVo(e) {
        var editVo = nForm.getItemById('vo');
        if (e.target.value == "true") {
            editVo.show();
        } else {
            editVo.hide();
        }
    }

    function commitUpdateMethod(e, modal) {
        let val = modal.getValues();
        methodTable.rawTable.bootstrapTable('updateRow', {
            index: index,
            row: val
        });
        modal.hide();
    }
}

function addMethodBtn(e, value, row, index) {
    if (serviceMap.size == 0) {
        alert("请先导入services！");
        return;
    }
    let services = [];
    for (let key of serviceMap.keys()) {
        services.push({value: key, text: key});
    }
    let callMethods = [];
    $.each(serviceMap.get(services[0].value), function (index, item) {
        callMethods.push({value: item.methodName, text: item.methodName});
    });

    //modal
    let modalConf = {
        id: 'addMethodModal',
        frameObj: $('#modal_department'),
        title: '新增方法',
        callbacks: {
            onCommit: commitAddMethod                                                              //这里是填写方法名
        }
    };
    let nModal = new comModal();
    nModal.init(modalConf);

    //form
    let formOpt = {
        formId: 'nForm',
        initContent: [{
            itemId: 'methodName',
            label: {
                text: '方法名'
            }
        }, {
            itemId: 'description',
            label: {
                text: '方法描述'
            }
        }, {
            itemId: 'param',
            label: {
                text: '参数'
            },
            required: false
        }, {
            itemId: 'returnStr',
            label: {
                text: '返回值'
            }
        }, {
            itemId: 'vo2Bo',
            label: {
                text: 'vo转bo'
            },
            type: 'select',
            options: {
                content: [{value: true, text: 'true'}, {value: false, text: 'false'}]
            },
            event: {
                onchange: changeAddBo
            }
        }, {
            itemId: 'bo',
            label: {
                text: 'bo类'
            }
        }, {
            itemId: 'service',
            label: {
                text: '调用service'
            },
            type: 'select',
            options: {
                content: services
            },
            event: {
                onchange: changeAddService
            }
        }, {
            itemId: 'callMethod',
            label: {
                text: '调用方法'
            },
            type: 'select',
            options: {
                content: callMethods
            }
        }, {
            itemId: 'bo2Vo',
            label: {
                text: 'bo转vo'
            },
            type: 'select',
            options: {
                content: [{value: true, text: 'true'}, {value: false, text: 'false'}]
            },
            event: {
                onchange: changeAddVo
            }
        }, {
            itemId: 'vo',
            label: {
                text: 'vo类'
            }
        }]
    };
    let nForm = new comForm();
    nForm.init(formOpt);

    //form加入到modal
    nModal.appendEle(nForm);

    function commitAddMethod(e, modal) {
        let val = modal.getValues();
        methodTable.rawTable.bootstrapTable('insertRow', {
            index: 0,
            row: val
        });
        modal.hide();
    }

    function changeAddService(e) {
        var addCallMethod = nForm.getItemById('callMethod');
        let callMethods = [];
        $.each(serviceMap.get(e.target.value), function (index, item) {
            callMethods.push({value: item.methodName, text: item.methodName});
        });
        addCallMethod.reloadOptions(callMethods);
    }

    function changeAddBo(e) {
        var addBo = nForm.getItemById('bo');
        if (e.target.value == "true") {
            addBo.show();
        } else {
            addBo.hide();
        }
    }

    function changeAddVo(e) {
        var addVo = nForm.getItemById('vo');
        if (e.target.value == "true") {
            addVo.show();
        } else {
            addVo.hide();
        }
    }
}

function pageInteraction() {
    $("#service").change(function () {
        $("#callInterface").empty();
        $.each(serviceMap.get($(this).val()), function (index, item) {
            $("#callInterface").append("<option value='" + item.methodName + "'>" + item.methodName + "</option>");
        });
    });
}