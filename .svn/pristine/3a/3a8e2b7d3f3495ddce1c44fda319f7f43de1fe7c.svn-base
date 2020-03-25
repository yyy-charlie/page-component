var loadingImage = document.getElementById("loadingDiv");

/**
 * 加载头部菜单
 * @param index
 * @param currentPosition
 */
function loadMenuTop(index, currentPosition) {
    $('#head-top-model').load("/menuTop #headMenu");
    topMenu.Init("0", index, "authority/getAuthoritysBySysUserAndMenuCode", currentPosition);
}

var meterTableInit = function () {
    var manageTableInit = Object();
    //初始化Table
    manageTableInit.Init = function () {
        $('#tb_departments').bootstrapTable('destroy');//先销毁表格
        var tableHead = getTableHeader(meterInfo);
        $("#tb_departments").initTable(
            {
                url: "mpInfo/getMpInfoByCondition",//请求的url
                pageIndex: 1,  //显示第几页
                pageLine: 20,   //每页显示多少行
                column: tableHead,
                showExport: false,
                toolbar: false,
                sortable: true,
                showRefresh: false,
                ajaxOptions: {async: false, timeout: 8000},
                clickToSelect: false,
                height: tableHeight,    //表格高度   不传的话  则不设置高度
                isNeedOperation: true,  //是否需要操作列
                // isOtherOperation: ['update', 'remove', 'site', 'customerDetails', 'meterDetails'],
                isNeedRequestHeader: false,
                key: 'mpId',     //每一行的唯一标识，一般为主键列
                detailView: false,
                queryTableParams: function (params) { //查询参数事件
                    return "parameter=" + getTableQueryCondition(params);
                },
                update: function (e, value, row, index) {// 修改事件,参数分别为事件、undefined、行内容、第几行
                    updateMeterDetails(e, value, row, index);
                },
                remove: function (e, value, row, index) { //废弃事件
                    var oRemove = {
                        "steelNumber": row.steelNumber,
                        "createTime": row.createTime,
                        "manufacture": row.manufacture,
                        "versionName": row.versionName
                    };
                    commonDelMeter(row, "deleteModal", "废弃表计", "mpOperation/scrappedMp", "mpOperation/getScrappedAttrs", delMeterCommit, oRemove);
                },
                site: function (e, value, row, index) {//现场事件
                    siteOperationDetails(e, value, row, index);
                },
                customerDetails: function (e, value, row, index) { //客户详情事件
                    loadCustomerDetails(e, value, row, index);
                },
                meterDetails: function (e, value, row, index) { //表计详情事件
                    loadMeterDetails(e, value, row, index);
                },
                details: function () {
                    return detailsAdd();
                },
                operate: function () {
                    return matchState();
                }
            });
    };

    return manageTableInit;
};


function loadDefinedRightClickMenu(domID) {
    customOperateMenu.load(domID);
}

//现场操作详情
function siteOperationDetails(e, value, row, index) {
    customOperateMenu.initCustomMenu(e, row);    //初始化自定义菜单,并绑定事件
}


//现场操作模态框初始化
//公共开通
function initCommonOpeningModal(url, fn, row, parObj) {
    initOpeningModal('openingModal', '开通表计', url, fn, row, parObj);
}

//开通
function initOpeningModal(modalId, title, url, fn, row, parObj) {
    modalInit({
        modalFrame: "modal_department",
        modalID: modalId,
        title: title,
        submitUrl: url,
        handleFunction: fn,
        handleAdditionalData: row
    }, {
        modalItemIdList: ["steelNumber-open", "accountId-open", "customerName-open", "dialNumber-open", "fileImport-open"],
        allowModifyList: [false, "", false],
        allowEmptyList: ["", "", "", "", true],
        typeList: ["", "", "", "", "file"],
        labelTextList: ["表钢号", "客户号", "客户名", "表盘示数", "添加文件"],
        modalItemValueList: [parObj.steelNumber],
    });
    // $("#openingModal").modal('show');
    $("#accountId-open").blur(function () {
        var accountIdObj = {};
        accountIdObj.accountId = this.value;
        requestAjax("post", "/customerInfo/getCustomerInfoById", false, paraTransport(accountIdObj), "json", function (result) {
            if (result.state.code === 200) {
                $("#customerName-open").val(result.data.customerName);
                $("#dialNumber-open")[0].focus();
                $('#accountId-open').next().remove();
            } else {
                feedbackPromptsForCustom();
            }
        });
    });
    $("#modal-body").append(
        "<div class=\"form-group form-inline modalAddress\" id='dist-select' data-toggle=\"distpicker\">\n" +
        "<h4>\n" +
        "<label class=\"label-control\" for=\"installAddress-open\">安装地址:</label>\n" +
        "</h4>\n" +
        "<span id=\"installAddress-open\">\n" +
        "<select data-province=\"湖南省\" class=\"form-control\"></select>\n" +
        "<span>---</span>\n" +
        "<select data-city=\"长沙市\" class=\"form-control\"></select>\n" +
        "<span>---</span>\n" +
        "<select data-district=\"芙蓉区\" class=\"form-control\"></select>\n" +
        "</span>\n" +
        "</div>" +
        "<div class=\"form-group form-inline\">\n" +
        "<label class=\"label-control\" for=\"detailedAddress\">详细地址:</label>\n" +
        "<input class=\"form-control\" id=\"detailedAddress\"/>\n" +
        "</div>"
    );
    $("#dist-select").distpicker();
}

//公共换表
function initCommonChangeModal(modalId, title, url, fn, parObj) {
    initChangeModal(modalId, title, url, fn, parObj);
}

//检定、维修换表模态框
function initChangeModal(modalId, title, url, fn, parObj) {
    var modalObj = {
        modalFrame: "modal_department",
        modalID: modalId,
        title: title,
        submitUrl: url,
        handleFunction: fn
    };
    var itemObj = {
        modalItemIdList: ["customerName-testOne", "telephone-testOne"],
        allowModifyList: [false, false],
        labelTextList: ["客户名", "联系电话"],
        modalItemValueList: [parObj.pointName, parObj.telephone]
    };
    modalInit(modalObj, itemObj);
    //手动添加面板
    panelInsert("modal-body", "testOnePanel");
    //面板条目插入
    var panelBody = $("#testOnePanel .panel-body");
    modalItemInit({
        modalBodyId: panelBody[0].id,
        modalItemIdList: ["steelNumber-testOne-maintain", "terminalNumber-testOne-maintain"],
        labelTextList: ["表钢号", "期末表盘示数"],
        allowModifyList: [false],
        modalItemValueList: [parObj.steelNumber]
    });
    modalItemInit({
        modalBodyId: panelBody[1].id,
        modalItemIdList: ["steelNumber-testOne-reserve", "terminalNumber-testOne-reserve"],
        labelTextList: ["表钢号", "起始数"]
    });

    var tempObj = {
        "mpId": parObj.mpId
    };
    requestAjax("post", "mpInfo/getDialDisplay", true, paraTransport(tempObj), "json", function (result) {
        if (result.state.code === 200) {
            $("#terminalNumber-testOne-maintain").val(result.data.value);
        } else {
            //console.log("未读取到表盘示数");
        }
    });
    // $("#testModalOne").modal('show');
}

//公共检定2
function initCommonTestTwo(modalId, title, url, fn) {
    initTestTwo(modalId, title, url, fn);
}

//检定(2)
function initTestTwo(modalId, title, url, fn) {
    modalInit({
        modalFrame: "modal_department",
        modalID: modalId,
        title: title,
        submitUrl: url,
        handleFunction: fn,
    }, {
        modalItemIdList: ["resultSelect-test", "file-test"],
        typeList: ["select", "file"],
        allowEmptyList: [false, true],
        labelTextList: ["检定结果选择", "检定记录单上传"],
    });
    //手动填充select合格与否
    $("#resultSelect-test").append(
        "<option value='1'>合格</option>\n" +
        "<option value='0'>不合格</option>"
    );
}

//公共维修2
function initCommonMaintainTwo(modalId, title, submitUrl, getOptionUrl, fn) {
    initMaintainTwo(modalId, title, submitUrl, getOptionUrl, fn);
}

//维修(2)
function initMaintainTwo(modalId, title, submitUrl, getOptionUrl, fn) {
    var allowEmptyList = [];
    var conclusionIdList = [];
    var conclusionNameList = [];
    var conclusionAnalysisIdList = [];
    var conclusionAnalysisNameList = [];
    var optionIdList = [];
    var optionNameList = [];
    var modalObj = {
        modalFrame: "modal_department",
        modalID: modalId,
        title: title,
        submitUrl: submitUrl,
        handleFunction: fn,
    };

    requestAjax("post", getOptionUrl, false, "", "json", function (result) {
        if (result.state.code === 200) {
            // a = {
            //     "data":
            //         [
            //             {
            //                 "conclusionId": "201903141552541580",
            //                 "conclusionName": "第一大队",
            //                 "option": [
            //                     {
            //                         "conclusionAnalysisId": "201903141553233759",
            //                         "conclusionAnalysisName": "传感器故障",
            //                     },
            //                     {
            //                         "conclusionAnalysisId": "201903141553233831",
            //                         "conclusionAnalysisName": "电路板芯片故障",
            //                     }, {
            //                         "conclusionAnalysisId": "201903141553233892",
            //                         "conclusionAnalysisName": "电池欠压",
            //                     }, {
            //                         "conclusionAnalysisId": "201904221555896666",
            //                         "conclusionAnalysisName": "其他",
            //                     }
            //                 ]
            //             },
            //             {
            //                 "conclusionId": "201903141552541621",
            //                 "conclusionName": "第二大队",
            //                 "option": [
            //                     {
            //                         "conclusionAnalysisId": "201903141553233950",
            //                         "conclusionAnalysisName": "靶片卡死",
            //                     },
            //                     {
            //                         "conclusionAnalysisId": "201903141553233975",
            //                         "conclusionAnalysisName": "阀门故障",
            //                     }, {
            //                         "conclusionAnalysisId": "201903141553234001",
            //                         "conclusionAnalysisName": "干簧管故障",
            //                     }, {
            //                         "conclusionAnalysisId": "201904221555896650",
            //                         "conclusionAnalysisName": "其他",
            //                     }
            //                 ]
            //
            //             }
            //         ],
            //     "state":
            //         {
            //             "code": 200,
            //             "description": "是否需要返回维修的业务属性success"
            //         }
            // };

            //checkbox转换使用部分
            for (let i = 0; i < result.data.length; i++) {
                conclusionIdList.push(result.data[i].conclusionId);
                conclusionNameList.push(result.data[i].conclusionName);
                conclusionAnalysisIdList = [];
                conclusionAnalysisNameList = [];
                for (let j = 0; j < result.data[i].option.length; j++) {
                    conclusionAnalysisIdList.push(result.data[i].option[j].conclusionAnalysisId);
                    conclusionAnalysisNameList.push(result.data[i].option[j].conclusionAnalysisName);
                }
                optionIdList.push(conclusionAnalysisIdList);
                optionNameList.push(conclusionAnalysisNameList);
            }

            modalObj.handleAdditionalData = conclusionIdList;
            //console.log(conclusionIdList);
            //console.log(conclusionNameList);
            //console.log(optionIdList);
            //console.log(optionNameList);

            var checkboxObj = {
                modalItemIdList: conclusionIdList.concat(["resultSelect-maintain", "file-maintain"]),
                typeList: [],
                allowEmptyList: [],
                labelTextList: conclusionNameList.concat(["维修结果选择:", "维修记录单上传"])
            };
            for (let i = 0; i < conclusionIdList.length; i++) {
                checkboxObj.typeList.push("box");
                checkboxObj.allowEmptyList.push(false);
            }
            checkboxObj.typeList = checkboxObj.typeList.concat(["select", "file"]);
            checkboxObj.allowEmptyList = checkboxObj.allowEmptyList.concat([false, true]);
            //以modal为基础生成
            modalInit(modalObj, checkboxObj);
            for (let i = 0; i < conclusionIdList.length; i++) {
                //以div为基础生成
                modalItemInit({
                    modalBodyId: conclusionIdList[i],
                    modalItemIdList: optionIdList[i],
                    typeList: "checkboxItems",
                    labelTextList: optionNameList[i],
                });
            }
            //手动填充select合格与否
            $("#resultSelect-maintain").append(
                "<option value='1'>合格</option>\n" +
                "<option value='0'>不合格</option>"
            );
        }
    });
}

//公共安检
function initCommonSecurityCheckModal(modalId, title, submitUrl, getOptionUrl, fn, parObj) {
    initSecurityCheckModal(modalId, title, submitUrl, getOptionUrl, fn, parObj);
}

//安检
function initSecurityCheckModal(modalId, title, submitUrl, getOptionUrl, fn, parObj) {
    requestAjax("get", getOptionUrl, false, "", "json", function (result) {
        if (result.state.code === 200) {
            //console.log(result.data);
            var conclusionIdList = [];
            var conclusionNameList = [];
            var typeOptionIdList = [];
            var typeOptionNameList = [];
            var optionIdList = [];
            var optionNameList = [];
            var allowEmptyList = [];
            $.each(result.data, function (index, ele) {
                typeOptionIdList = [];
                typeOptionNameList = [];
                conclusionIdList.push(ele.conclusionId);
                conclusionNameList.push(ele.conclusionName);
                $.each(ele.option, function (index, ele) {
                    typeOptionIdList.push(ele.conclusionAnalysisId);
                    typeOptionNameList.push(ele.conclusionAnalysisName);
                });
                optionIdList.push(typeOptionIdList);
                optionNameList.push(typeOptionNameList);
            });

            var modalObj = {
                modalFrame: "modal_department",
                modalID: modalId,
                title: title,
                submitUrl: submitUrl,
                handleFunction: fn,
                handleAdditionalData: conclusionIdList
            };
            var checkboxObj = {
                modalItemIdList: ["customerName-check", "steelNumber-check", "address-check", "dialDisplay"].concat(conclusionIdList).concat(["file-securityCheck"]),
                typeList: ["", "", "", ""],
                allowModifyList: [false, false, false, true],
                allowEmptyList: [false, false, false, true],
                labelTextList: ["客户名", "表钢号", "安装位置", "表盘示数"].concat(conclusionNameList).concat(["安检记录单上传"]),
                modalItemValueList: [parObj.pointName, parObj.steelNumber, parObj.openLocation, parObj.dialDisplay]
            };
            for (let i = 0; i < conclusionIdList.length; i++) {
                checkboxObj.typeList.push("box");
                checkboxObj.allowEmptyList.push(false);
            }
            checkboxObj.typeList = checkboxObj.typeList.concat(["file"]);
            checkboxObj.allowEmptyList = checkboxObj.allowEmptyList.concat([true]);
            //以modal为基础生成
            modalInit(modalObj, checkboxObj);
            for (let i = 0; i < conclusionIdList.length; i++) {
                //以div为基础生成
                modalItemInit({
                    modalBodyId: conclusionIdList[i],
                    modalItemIdList: optionIdList[i],
                    typeList: "checkboxItems",
                    labelTextList: optionNameList[i],
                });
            }
        }
    });
    // $("#securityCheckModal").modal('show');
}

//公共维护
function initCommonRoutineMaintainModal(modalId, title, submitUrl, getOptionUrl, fn, parObj) {
    initRoutineMaintainModal(modalId, title, submitUrl, getOptionUrl, fn, parObj)
}

//维护
function initRoutineMaintainModal(modalId, title, submitUrl, getOptionUrl, fn, parObj) {
    requestAjax("get", getOptionUrl, false, "", "json", function (result) {
        if (result.state.code === 200) {
            //console.log(result.data);
            var conclusionIdList = [];
            var conclusionNameList = [];
            var typeOptionIdList = [];
            var typeOptionNameList = [];
            var optionIdList = [];
            var optionNameList = [];
            $.each(result.data, function (index, ele) {
                typeOptionIdList = [];
                typeOptionNameList = [];
                conclusionIdList.push(ele.conclusionId);
                conclusionNameList.push(ele.conclusionName);
                $.each(ele.option, function (index, ele) {
                    typeOptionIdList.push(ele.conclusionAnalysisId);
                    typeOptionNameList.push(ele.conclusionAnalysisName);
                });
                optionIdList.push(typeOptionIdList);
                optionNameList.push(typeOptionNameList);
            });

            var modalObj = {
                modalFrame: "modal_department",
                modalID: modalId,
                title: title,
                submitUrl: submitUrl,
                handleFunction: fn,
                handleAdditionalData: conclusionIdList
            };
            var checkboxObj = {
                modalItemIdList: ["customerName-check", "steelNumber-check", "address-check"].concat(conclusionIdList).concat(["file-routineMaintain"]),
                typeList: ["", "", ""],
                allowModifyList: [false, false, false],
                allowEmptyList: ["", "", ""],
                labelTextList: ["客户名    ", "表钢号", "安装位置"].concat(conclusionNameList).concat(["维护记录单上传"]),
                modalItemValueList: [parObj.pointName, parObj.steelNumber, parObj.openLocation]
            };
            for (let i = 0; i < conclusionIdList.length; i++) {
                checkboxObj.typeList.push("box");
                checkboxObj.allowEmptyList.push(false);
            }
            checkboxObj.typeList = checkboxObj.typeList.concat(["file"]);
            checkboxObj.allowEmptyList = checkboxObj.allowEmptyList.concat([true]);

            //以modal为基础生成
            modalInit(modalObj, checkboxObj);
            for (let i = 0; i < conclusionIdList.length; i++) {
                //以div为基础生成
                modalItemInit({
                    modalBodyId: conclusionIdList[i],
                    modalItemIdList: optionIdList[i],
                    typeList: "checkboxItems",
                    labelTextList: optionNameList[i],
                });
            }
        }
    });
}

//记录
function initSceneRecordModal() {
    modalInit({
        modalFrame: "modal_department",
        modalID: "sceneRecordModal",
        title: "现场操作记录",
    });
    var requestObj = {
        "mpId": globalObjRow.mpId //表计Id
    };
    requestAjax("post", "mpOperation/getMpBusinessOperationList", false, paraTransport(requestObj), "json", function (result) {
        var boxObj = {
            modalItemIdList: ["steelNumber-record", "customerName-record"],
            labelTextList: ["表钢号", "客户名"],
            typeList: ["", ""],
            allowModifyList: [false, false],
            modalItemValueList: [globalObjRow.steelNumber, globalObjRow.pointName]
        };

        if (result.state.code === 200) {
            $.each(result.data, function (index, ele) {
                boxObj.modalItemIdList.push(ele.flowName + "--" + index);
                boxObj.typeList.push("box");
                boxObj.labelTextList.push("操作条目------------------------------------------");

            });
            modalItemInit(boxObj);
            $("#modal-body .itemsWrapper").removeClass('form-inline');
            var itemObj = {
                modalBodyId: null,
                modalItemIdList: [],
                allowModifyList: [false, false, false, false, false],
                labelTextList: ["操作时间", "操作类型", "状态", "操作内容", "操作人"],
                modalItemValueList: [],
            };
            $.each(result.data, function (index, ele) {
                itemObj.modalBodyId = ele.flowName + "--" + index;
                itemObj.modalItemIdList = ["operationTime-" + index, "flowName-" + index, "status-" + index, "operationContent-" + index, "operator-" + index];
                itemObj.modalItemValueList = [ele.operationTime, ele.flowName, ele.status, ele.operationContent, ele.operator];
                modalItemInit(itemObj);
                itemObj = {
                    modalBodyId: null,
                    modalItemIdList: [],
                    allowModifyList: [false, false, false, false, false],
                    labelTextList: ["操作时间", "操作类型", "状态", "操作内容", "操作人"],
                    modalItemValueList: [],
                };
            });
            //console.log(result.data);
        } else {
            //console.log("未获得记录");
        }
    });
}

//公共废弃
function commonDelMeter(row, modalId, title, submitUrl, optionUrl, fn, parObj) {
    delMeterDetails(row, modalId, title, submitUrl, optionUrl, fn, parObj);
}

//废弃
function delMeterDetails(row, modalId, title, submitUrl, optionUrl, fn, parObj) {
    var modalPar = {
        modalFrame: "modal_department",
        modalID: modalId,
        title: title,
        submitUrl: submitUrl,
        handleFunction: fn,
        handleAdditionalData: row
    };

    var conclusionIdList = [];
    var conclusionNameList = [];
    var conclusionAnalysisIdList = [];
    var conclusionAnalysisNameList = [];
    var optionIdList = [];
    var optionNameList = [];
    requestAjax("post", optionUrl, false, "", "json", function (result) {
        if (result.state.code === 200) {
            //checkbox转换使用部分
            for (let i = 0; i < result.data.length; i++) {
                conclusionIdList.push(result.data[i].conclusionId);
                conclusionNameList.push(result.data[i].conclusionName);
                conclusionAnalysisIdList = [];
                conclusionAnalysisNameList = [];
                for (let j = 0; j < result.data[i].option.length; j++) {
                    conclusionAnalysisIdList.push(result.data[i].option[j].conclusionAnalysisId);
                    conclusionAnalysisNameList.push(result.data[i].option[j].conclusionAnalysisName);
                }
                optionIdList.push(conclusionAnalysisIdList);
                optionNameList.push(conclusionAnalysisNameList);
            }
            var checkboxObj = {
                modalItemIdList: ["steelNumber-del", "openingTime-del", "factory-del", "devVersion-del", "typeSelect-del"].concat(conclusionIdList).concat(["file-delete"]),
                allowModifyList: [false, false, false, false],
                allowEmptyList: [false, false, false, false, false, false, false, true],
                typeList: ["", "", "", "", "select", "box", "box", "file"],
                labelTextList: ["表钢号", "创建时间", "厂家", "型号", "废弃原因选择"].concat(["", ""]).concat(["废弃文件上传"]),
                modalItemValueList: [parObj.steelNumber, parObj.createTime, parObj.manufacture, parObj.versionName].concat(conclusionIdList)
            };
            //以modal为基础生成
            modalInit(modalPar, checkboxObj);
            for (let i = conclusionIdList.length - 1; i >= 0; i--) {
                //手动填充select正常与非正常
                $("#typeSelect-del").append(
                    "<option value='" + conclusionIdList[i] + "'>" + conclusionNameList[i] + "</option>"
                );
            }
            modalItemInit({
                modalBodyId: conclusionIdList[1],
                modalItemIdList: optionIdList[1],
                typeList: "radioItems",
                labelTextList: optionNameList[1],
            });
            // 以div为基础生成
            $("#typeSelect-del").change(function () {
                for (let i = conclusionIdList.length - 1; i >= 0; i--) {
                    $("#" + conclusionIdList[i]).empty();
                    if ($("#typeSelect-del").val() === conclusionIdList[i]) {
                        modalItemInit({
                            modalBodyId: conclusionIdList[i],
                            modalItemIdList: optionIdList[i],
                            typeList: "radioItems",
                            labelTextList: optionNameList[i],
                        });
                    }
                }
            });
        }
    });

}

//初始化模态框
/**
 *
 * @param modalPar
 * @param modalItemPar
 */
function modalInit(modalPar, modalItemPar) {
    if (modalPar.modalID) {
        modal = commModal();
        modal.modalInit(modalPar);
        if (modalItemPar) {
            modalItemInit(modalItemPar);
        }
        $("#modal_department").modal("show");
    } else {
        //console.log("未接收到modalID");
    }
}

//初始化modalItem
function modalItemInit(modalItemPar) {
    modalItem = comModalItem();
    if (!modalItemPar.modalBodyId) {
        modalItemPar.modalBodyId = modal.domObj.body.id;
    }
    modalItem.modalItemInit(modalItemPar);
}

/**
 * 判断变量是否为空
 * @param str
 * @returns {boolean}
 */
function judgeIsEmpty(str) {
    console.log("进入了commFunction");
    var judge = false;
    if (str == null || str === '' || str.length === 0) {
        judge = true;
        return judge;
    }
    return judge;
}

/**
 * 判断区域树是否被选中
 * @returns {*}
 */
function judgementAreaTreeIsSelected() {
    const select_node = $('#treeview').treeview('getSelected');//获得选中的节点
    if (select_node[0]) {
        return select_node[0].id;
    }
    return "";
}

function unifiedOperationalFeedbackPrompts(result) {
    // console.log("操作反馈的统一提示方法" + "公共方法中的结果:     " + result.state.description);
    // alert("公共方法中的结果:     " + result.state.description);
    alert(result.state.description);
    if (result.state.code === 200) {
        $("#modal_department").modal("hide");
        $("#modal_department").empty();
        $("#tb_departments").bootstrapTable("refresh");
    }
}

function operationalFeedbackPrompts(result, tableID) {
    // console.log("操作反馈的统一提示方法" + "公共方法中的结果:     " + result.state.description);
    // alert("公共方法中的结果:     " + result.state.description);
    alert(result.state.description);
    if (result.state.code === 200) {
        $("#modal_department").modal("hide");
        $("#modal_department").empty();
        $("#" + tableID).bootstrapTable("refresh");
    }
}

function feedbackPromptsForCustom() {
    if ($('#accountId-open').siblings().length == 1) {
        $('#accountId-open').after('<span style="color:red"> * 客户号不存在<span>');
    }
}


function paraTransport(requestData) {
    return "parameter=" + JSON.stringify(requestData);
}

function ajaxWithFiles(url, textObj, fileImportId) {
    let requestObj = new FormData();
    let filesUrl = document.getElementById(fileImportId).files;
    for (let i = 0; i < filesUrl.length; i++) {              // 多个文件上传的情况，需要啊后台进行字段匹配如：上方的importFile
        requestObj.append('uploadFileList', filesUrl[i]);
    }
    requestObj.set('parameter', JSON.stringify(textObj));
    $.ajax({
        url: url,
        type: 'post',
        data: requestObj,
        cache: false,
        async: false,
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (result) {
            console.log("操作反馈的统一提示方法");
            unifiedOperationalFeedbackPrompts(result);
        }
    });
}

function ajaxFiles(url, textObj, fileImportId) {
    let res;
    let requestObj = new FormData();
    let filesUrl = document.getElementById(fileImportId).files;
    for (let i = 0; i < filesUrl.length; i++) {              // 多个文件上传的情况，需要啊后台进行字段匹配如：上方的importFile
        requestObj.append('uploadFileList', filesUrl[i]);
    }
    requestObj.set('parameter', JSON.stringify(textObj));
    $.ajax({
        url: url,
        type: 'post',
        data: requestObj,
        cache: false,
        async: false,
        dataType: 'json',
        processData: false,
        contentType: false,
        success: function (result) {
            console.log("允许传表格id的公共处理");
            res = result;
        }
    });
    return res;
}


function panelInsert(modalId, panelId) {
    $("#" + modalId).append(
        "                <div class=\"form-group form-inline modalInfoCard\" id=\"" + panelId + "\">\n" +
        // "                    <h2 class=\"text-center\">表计信息</h2>\n" +
        "                    <div class=\"panel panel-default panel-originalMeter d-inlineBlock\" style=\"width:48%;\">\n" +
        "                        <div class=\"panel-heading\">\n" +
        "                            <h3 class=\"panel-title\">现场表计信息</h3>\n" +
        "                        </div>\n" +
        "                        <div class=\"panel-body\" id='maintainDev'>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"panel panel-default panel-spareMeter d-inlineBlock\" style=\"width:48%;\">\n" +
        "                        <div class=\"panel-heading\">\n" +
        "                            <h3 class=\"panel-title\">未开通表计信息</h3>\n" +
        "                        </div>\n" +
        "                        <div class=\"panel-body\" id='reserveDev'>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>"
    );
}

function commReturnAjax(url, name, param) {

    var returnMsg = null;

    $.ajax({
        type: "POST",
        url: url,
        data: getData(name, param),
        async: false,                                                                       //取消异步
        dataType: "json",
        success: function (msg) {
            returnMsg = msg;
        }
    });

    function getData(name, param) {
        var data = "";
        if (name) data += name + "=";
        if (param) data += JSON.stringify(param);
        return data.trim() != "" ? data : null;
    }

    return returnMsg;
}

function commReturnAjaxBody(url, param) {

    var returnMsg = null;

    $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(param),
        async: false,//取消异步
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (msg) {
            returnMsg = msg;
        }
    });
    return returnMsg;
}

function dataFormat(date, fmt) {
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)

    var timestamp = date.getTime();

    var o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "H+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

}


//获取表头
function getTableHeader(tableName, statusCode) {
    const columnsArray = [];
    var requestObj = {};
    requestObj.tableName = tableName;
    const requestData = "parameter=" + JSON.stringify(requestObj);
    console.log(requestData);
    requestAjax("POST", "table/getHeader", false, requestData, "json", function (result) {
        $.each(result.data, function (x, Item) {
            var temp = {
                field: Item.id,
                title: Item.name,
                align: "center"
            };
            columnsArray.push(temp);
        });
    });
    return columnsArray;
}

function requestAjax(type, url, async, data, dataType, callback) {
    // var myUrl = 'http://api.com';
    // var result = Mock.mock(myUrl, {
    //     "user|5-10": [{
    //         'name': '@cname', //中文名称
    //         'age|1-100.1-3': 100, //100以内随机整数
    //         'birthday': '@date("yyyy-MM-dd")', //日期
    //         'city': '@city(true)' //中国城市
    //     }]
    // });

    var myTimeOut = null;
    $.ajax({
        type: type,
        // url:myUrl,
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
            // console.log(result);
            callback(result);
        },
        error: function (result) {
            callback(result);
        }
    });
}

/**
 *   获取表格查询条件
 * @param params
 * @returns {string}
 */
function getTableQueryCondition(params, tableName) {
    var page = getTableQueryPage(params);
    var property = getTableQueryProperty(tableName);
    return mergeJsonObject(page, property);
}

/**
 * 获取表格的page
 * @param params
 * @returns {{page: {pageLine: *, pageIndex: number}}}
 */
function getTableQueryPage(params) {
    var page = {
        "page": {
            pageLine: params.limit,   //页面大小
            pageIndex: params.offset / params.limit + 1  //页码
            // ,sort : params.sort,
            // order : params.order
        }
    };
    return page;
}

function mergeJsonObject(jsonObject1, jsonObject2) {
    var resultJsonObject = {};
    for (var attr in jsonObject1) {
        resultJsonObject[attr] = jsonObject1[attr];
    }
    for (var attr in jsonObject2) {
        resultJsonObject[attr] = jsonObject2[attr];
    }
    return JSON.stringify(resultJsonObject);
}

function splitTime(strTime) {
    var time = strTime.split("至");
    return time;
}

function loadFrameHeight(headId, panelId) {
    // $("#" + panelId).height($(window).height() - 66 - 30);
    $("#" + panelId).height($(window).height() - 2);
}

function setInnerItemHeight(wrapper, item, coe) {
    item.height(wrapper.height() * coe);
}

function alert2(data) {
    // alert(data);
    console.log(data);
    var showModal = '<div class="modal fade" id="modal_tips" tabindex="-10" role="dialog">' +
        '<div class="modal-dialog" role="document" style="top:100px;">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">' +
        '&times;' +
        '</button>' +
        '<h4 class="modal-title">提示信息</h4>' +
        '</div>' +
        '<div class="modal-body">' +
        '<h4>' + data + '</h4>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';

    var selector = '#modal_tips';
    if ($(selector)) {
        $(selector).remove();
    }
    $('body').append(showModal);
    $(selector).modal('show');

}