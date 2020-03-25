var testCaseStepTree = $('#testCaseStepTree');

var addStepPanel = $('#addStepPanel');
var updateStepPanel = $('#updateStepPanel');

var batchAddStepsModal = $('#batchAddStepsModal');

var stepModuleTable = new commTable();

var systemId;
var moduleId;

(function initSystem() {
    requestAjax("post", 'systemAndModule/getAll', false, null, 'json', function (result) {
        if (result.code === 200) {
            var res = result.data;
            $.each(res.stepSystemList, function (index, item) {
                $("#selectSystem").append(" <option value=" + item.systemId + ">" + item.systemName + "</option>");
            });
            $.each(res.stepSystemModuleList, function (index, item) {
                $("#selectModule").append(" <option value=" + item.moduleId + ">" + item.moduleName + "</option>");
            });
        }
    });

    systemId = $("#selectSystem").val();
    moduleId = $("#selectModule").val();

    loadTree();

    initTable();
})();

/**
 * 初始化表格
 */
function initTable() {
    stepModuleTable.init({
        tableID: "stepModuleTable",
        //isNeedOperate和otherOperationArr配套使用
        isNeedOperate: true,
        isNeedDetails: true,
        otherOperationArr: ['update', 'delete'],
        otherDetailsArr: ['stepModuleDetails'],
        //操作按钮
        operate: function () {
            return deleteModuleBtn();
        },
        //详情按钮
        details: function () {
            return getModuleDetails();
        },
        //点击查看之后的方法
        stepModuleDetails: function (e, value, row, index) { //模块的步骤集合
            getStepModuleSteps(e, value, row, index);
        },
        //点击删除之后的方法
        delete: function (e, value, row, index) {
            deleteModule(e, value, row, index);
        },
        //点击修改之后的方法
        update: function (e, value, row, index) { //模块的步骤集合
            updateStepModuleSteps(e, value, row, index);
        }
    }, {
        showRefresh: false,
        showExport: false,
        showColumns: false,
        showPaginationSwitch: false,
        height: 500,
        pageNumber: 1,
        pageSize: 1000,
        pageList: []
    });

    let res = {
        data: [
            {
                field: 'moduleId',
                title: '模块id',
                visible: false
            },
            {
                field: 'moduleName',
                title: '模块名称'
            }
        ],
        state: {
            code: 200,
            description: "成功"
        }
    };

    stepModuleTable.fillHead(res);

    requestAjax("post", 'stepModule/getStepModule', false, JSON.stringify({
        systemId: systemId,
        systemModuleId: moduleId
    }), 'json', function (result) {
        if (result.code === 200) {
            stepModuleTable.appendData(result.data);
        } else {
            stepModuleTable.data = result.data;
        }
    });

    function deleteModuleBtn() {
        return '<button type="button" class="update btn btn-primary btn-xs">&nbsp;修改&nbsp;</button>&nbsp;&nbsp;' +
            '<button type="button" class="delete btn btn-primary btn-xs">&nbsp;删除&nbsp;</button>&nbsp;&nbsp;';
    }

    function deleteModule(e, value, row, index) {
        let r = confirm('确定要移除该条数据吗?');
        if (r) {
            requestAjax('post', 'aggregation/deleteStepModule', 'false', JSON.stringify({moduleId: row.moduleId}), 'json', function (res) {
                alert(res.description);
                if (res.code === 200) {
                    stepModuleTable.removeRow({
                        field: 'no',
                        values: [index + 1]
                    });
                }
            });
        }
    }
}

function getModuleDetails() {
    let details = '<button type="button" class="stepModuleDetails btn btn-primary  btn-xs" title="查看" id="stepModuleDetails">查看</button>&nbsp;';
    let temp = [];
    temp.push(details);
    return temp.join('');
}

function updateStepModuleSteps(e, value, row, index) {
    //modal
    let tModalConfigure = {
        id: 'updateModuleStepListModal',
        frameObj: $('#modal_department'),
        title: '修改模块步骤',
        callbacks: {
            onCommit: updateModuleStepCommit
        }
    };
    let tModal = new comModal();
    tModal.init(tModalConfigure);

    var formOption = {
        formId: 'testForm',
        initContent: [{
            itemId: "moduleName",
            label: {
                text: "模块名称"
            },
            value: row.moduleName
        }]
    };
    var tForm = new comForm();
    tForm.init(formOption);

    tModal.appendEle(tForm);

    //在模态框里设置模块id
    tModal.setData(row);

    $('#modal_department .modal-body').append("<div id=\"moduleStepTree\" class=\"ztree\">\n" + "</div>");       //tree数据

    let data = {
        moduleId: row.moduleId
    };
    //初始化模块步骤树
    loadModuleStepTree("moduleStepTree", data);

    /**
     * 提交修改模块步骤
     */
    function updateModuleStepCommit(e, oModal) {
        let oVal = oModal.getValues();

        var treeObj = $.fn.zTree.getZTreeObj("moduleStepTree");
        var nodes = treeObj.getNodes();
        var nodesArr = [];
        getChildren(nodes, nodesArr);

        nodesArr = nodesArr.reverse();
        var nodeResult = [];
        $.each(nodesArr, function (index, item) {
            var nodeObj = {
                stepName: item.name
            };
            nodeResult.push(nodeObj);
        });

        var moduleId = tModal.getData().moduleId;
        var data = {
            moduleId: moduleId,
            moduleName: oVal.moduleName,
            stepList: nodeResult,
            systemId: systemId
        };

        requestAjax('post', 'aggregation/updateModuleStep', 'false', JSON.stringify(data), 'json', function (res) {
            alert(res.description);
            //隐藏模态框
            oModal.hide();
            //刷新模块表格
            stepModuleTable.removeRow({
                field: 'moduleId',
                values: [moduleId]
            });
            data.moduleId = res.data.moduleId;
            stepModuleTable.appendData(data);
        });
    }

    /**
     * 加载修改模块步骤树
     */
    function loadModuleStepTree(eId, requestParameter) {
        var setting = {
            view: {
                selectedMulti: false,
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom
            },
            check: {
                enable: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            edit: {
                showRemoveBtn: true,
                showRenameBtn: true,
                enable: true,
                drag: {
                    isCopy: false,
                    isMove: false
                }
            }
        };

        requestAjax('post', 'aggregation/getModuleStepListById', 'false', JSON.stringify(requestParameter), 'json', function (res) {
            if (res.code === 200) {
                let nodes = ztreeFormat(res.data);

                $.fn.zTree.init($("#" + eId), setting, nodes);
                var treeObj = $.fn.zTree.getZTreeObj(eId);
                treeObj.expandAll(true);
            }
        });

        var newCount = 0;

        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");

            if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;

            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增子节点' onfocus='this.blur();'></span>";

            sObj.after(addStr);

            var btn = $("#addBtn_" + treeNode.tId);

            if (btn) btn.bind("click", function (e) {
                let oTree = $.fn.zTree.getZTreeObj(treeId);
                oTree.addNodes(treeNode, {id: (100 + newCount), pId: treeNode.id, name: "new node" + (newCount++)});
                return false;
            });
        }
    }

}

/**
 * 查看模块步骤
 * @param e
 * @param value
 * @param row
 * @param index
 */
function getStepModuleSteps(e, value, row, index) {
    //modal
    let tModalConfigure = {
        id: 'getModuleStepListModal',
        frameObj: $('#modal_department'),
        title: '查看模块步骤',
        type: 'browse'
    };
    let tModal = new comModal();
    tModal.init(tModalConfigure);

    $('#modal_department .modal-body').append("<div id=\"moduleStepTree\" class=\"ztree\">\n" + "</div>");       //tree数据

    let data = {
        moduleId: row.moduleId
    };
    //初始化模块步骤树
    loadModuleStepTree("moduleStepTree", data);

    /**
     * 加载查看模块步骤树
     */
    function loadModuleStepTree(eId, requestParameter) {
        var setting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            edit: {
                showRemoveBtn: false,
                showRenameBtn: false,
                enable: true,
                drag: {
                    isCopy: false,
                    isMove: false
                }
            }
        };

        requestAjax('post', 'aggregation/getModuleStepListById', 'false', JSON.stringify(requestParameter), 'json', function (res) {
            if (res.code === 200) {
                let nodes = ztreeFormat(res.data);

                $.fn.zTree.init($("#" + eId), setting, nodes);
                var treeObj = $.fn.zTree.getZTreeObj(eId);
                treeObj.expandAll(true);
            }
        });
    }
}

function loadTree() {
    testCaseStepTree.setting = {
        view: {
            selectedMulti: false,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom
        },
        check: {
            enable: true,
            chkboxType: {"Y": "s", "N": "s"}
        },
        data: {
            simpleData: {
                enable: true
            }
        },

        edit: {
            showRemoveBtn: true,
            showRenameBtn: false,
            enable: true,
            drag: {
                isCopy: false,
                isMove: false
            }
        },
        callback: {
            beforeRemove: beforeRemove,
            onRemove: onRemove,
            onClick: menuTreeOnClick
        }
    };

    //发送的参数，值为该系统的value（在html里）
    var data = {
        systemId: systemId,
        moduleId: moduleId
    };
    requestAjax('post', 'aggregation/getTestCaseTreeBySystemId', 'false', JSON.stringify(data), 'json', function (res) {
        if (res.code === 200) {
            var _data = res.data;
            // if (_data.length === 0) {
            //     $.fn.zTree.init(testCaseStepTree, testCaseStepTree.setting, {id: 0, name: "步骤名"});
            // } else {
            //     let nodes = ztreeFormat(_data);
            //     $.fn.zTree.init(testCaseStepTree, testCaseStepTree.setting, nodes);
            // }
            let nodes = ztreeFormat(_data);
            $.fn.zTree.init(testCaseStepTree, testCaseStepTree.setting, nodes);
            testCaseStepTree.oTree = $.fn.zTree.getZTreeObj("testCaseStepTree");
        }
    });

    function onRemove(event, treeId, treeNode) {
        var nodesArr = [];
        getObjChildren(treeNode, nodesArr);
        var nodesData = [];
        $.each(nodesArr, function (index, item) {
            let nodeObj = {
                stepId: item.id
            };
            nodesData.push(nodeObj);
        });

        requestAjax('post', 'aggregation/deleteTestStep', 'false', JSON.stringify(nodesData), 'json', function (res) {
            alert(res.description);
            if (res.code === 200) {
                //删除节点
                var treeObj = $.fn.zTree.getZTreeObj("testCaseStepTree");
                for (let i = 0, l = treeNode.length; i < l; i++) {
                    treeObj.removeNode(nodes[i]);
                }
            }
        });
    }
}


/***----------authorityManage requestAjax-----------------**/
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
            // console.log(result);
            callback(result);
        },
        error: function (result) {
            callback(result);
        }
    });
}


function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");

    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId + "2").length > 0) return;


    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='新增子节点' onfocus='this.blur();'></span>";
    var addStr2 = "<span class='button edit' id='addBtn_" + treeNode.tId + "2"
        + "' title='批量新增' onfocus='this.blur();'></span>";

    sObj.after(addStr);
    sObj.after(addStr2);

    var btn = $("#addBtn_" + treeNode.tId);
    var btn2 = $("#addBtn_" + treeNode.tId + "2");

    if (btn) btn.bind("click", function () {
        updateStepPanel.css('display', "none");
        addStepPanel.css('display', "inline-block");
        $("#addStepPanel .form-group").eq(0).css('display', 'inline-block');
        $("#lastTestCaseName").val(treeNode.name);
        $("#lastTestCaseName").attr("readOnly", true);

        addStepPanel.treeData = treeNode;
        return false;
    });
    if (btn2) btn2.bind("click", function () {

        //modal
        var tModalConfigure = {
            id: 'batchAddStepsModal',
            frameObj: $('#modal_department'),
            title: '批量新增步骤',
            callbacks: {
                onCommit: batchAddStepsCommit
            }
        };
        var tModal = new comModal();
        tModal.init(tModalConfigure);

        batchAddStepsModal.treeNode = treeNode;

        var formOption = {
            formId: 'testForm',
            initContent: [
                {
                    itemId: "stepObj",
                    label: {
                        text: "步骤对象 "
                    },
                    // validType: "Chinese"
                }, {
                    itemId: "minLength",
                    label: {
                        text: "最小值"
                    },
                    validators: {
                        greaterThan: {
                            value: 0,
                            inclusive: false,
                            message: '最大值需要大于等于1'
                        }
                    },
                    validType: "positiveNumber"
                }, {
                    itemId: "maxLength",
                    label: {
                        text: "最大值"
                    },
                    validators: {
                        greaterThan: {
                            value: 1,
                            inclusive: false,
                            message: '最大值需要大于等于2'
                        }
                    },
                    validType: "positiveNumber"
                }]
        };
        var tForm = new comForm();
        tForm.init(formOption);

        tModal.appendEle(tForm);

        return false;
    });
}

function batchAddStepsCommit(e, oModal) {
    let oVal = oModal.getValues();

    var data = {
        stepObj: oVal.stepObj,
        minLength: oVal.minLength,
        maxLength: oVal.maxLength,
        systemId: systemId,
        moduleId: moduleId,
        stepParentId: batchAddStepsModal.treeNode.id
    };
    requestAjax('post', 'testCase/addTestCaseStepBasedOnStepObj', 'false', JSON.stringify(data), 'json', function (res) {
        alert(res.description);
        if (res.code === 200) {

            //隐藏模态框
            oModal.hide();

            //新增节点
            var treeObj = $.fn.zTree.getZTreeObj("testCaseStepTree");
            var arr = [];
            $.each(res.data, function (index, item) {
                let childZNode = new ZtreeNode(item); //构造子节点
                arr.push(childZNode);
            });
            // treeObj.addNodes(batchAddStepsModal.treeNode, arr);

            $.each(arr, function (index, ele) {
                if (index === 0) {
                    treeObj.addNodes(batchAddStepsModal.treeNode, ele);
                } else {
                    var newParent = treeObj.getNodeByParam("id", arr[0].id, batchAddStepsModal.treeNode);
                    treeObj.addNodes(newParent, ele);
                }
            });
        }
    });
}

function ZtreeNode(item) {
    for (var prop in item) {
        switch (prop) {
            case "stepId":
                this.id = item[prop];
                break;
            case "stepParentId":
                this.pId = item[prop];
                break;
            case "stepName":
                this.name = item[prop];
                break;
            default :
                this[prop] = item[prop];
        }
    }
}

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
    $("#addBtn_" + treeNode.tId + "2").unbind().remove();
}

function setRemoveBtn(treeId, treeNode) {
    return !treeNode.isParent;
}

function beforeRemove() {
    return confirm("确认删除吗？");
}

function onRemove(event, treeId, treeNode) {
    //删除节点
    var treeObj = $.fn.zTree.getZTreeObj("testCaseStepTree");
    for (let i = 0, l = treeNode.length; i < l; i++) {
        treeObj.removeNode(nodes[i]);
    }
}

function menuTreeOnClick(event, treeId, treeNode) {
    updateStepPanel.css('display', "inline-block");
    addStepPanel.css('display', "none");

    if (1 === treeNode.singleStepMarkId) {
        $("#lastStepMarkId").parent().css('display', 'none');
    } else {
        $("#lastStepMarkId").parent().css('display', 'inline-block');
    }


    //清空面板
    clearStepPanel();

    //查询步骤模块
    requestAjax('post', 'stepModule/getStepModule', true, JSON.stringify({
        systemId: systemId,
        systemModuleId: moduleId
    }), 'json', function (res) {
        $.each(res.data, function (index, item) {
            $("#moduleName").append(" <option value=" + item.moduleId + ">" + item.moduleName + "</option>");
        });
        $("#moduleName").append(" <option value= '0'>" + "无" + "</option>");

    });

    var data = {
        stepId: treeNode.id
    };
    requestAjax('post', 'aggregation/getStepAttrById', true, JSON.stringify(data), 'json', function (res) {
        if (res.code === 200) {
            var _data = res.data;
            $("#stepId").val(treeNode.id);
            $("#testCaseName").val(treeNode.name);

            if (_data.moduleId == null) {
                // $("#moduleName").append(" <option value= '0'>" + "无" + "</option>").val(0);
                $("#moduleName").val('0');
            } else {
                $("#moduleName").val(_data.moduleId);
            }
            $("#testCaseTitle").val(_data.testCaseTitle);
            $("#manageModule").val(_data.manageModule);
            $("#expected").val(_data.expected);
            $("#lastStepMarkId").val(_data.lastStepMarkId);
            $("#precondition").val(_data.precondition);
            $("#testCaseType").val(_data.testCaseType);
            $("#applicableStage").val(_data.applicableStage);
        }
    });

    // $("#stepId").val(treeNode.id);
    // $("#testCaseName").val(treeNode.name);
    // $("#testCaseTitle").val(treeNode.testCaseTitle);
    // $("#manageModule").val(treeNode.manageModule);
    // $("#expected").val(treeNode.expected);
    // $("#testCaseType").val(treeNode.testCaseType);
    // $("#applicableStage").val(treeNode.applicableStage);
}

function ztreeFormat(data) {
    var ztreeData = [];
    $.each(data, function (index, ele) {
        var node = {
            id: ele.id,
            pId: ele.parentId,
            name: ele.name,
            singleStepMarkId: ele.singleStepMarkId,
            lastStepMarkId: ele.lastStepMarkId,
            testCaseTitle: ele.testCaseTitle,
            priority: ele.priority,
            precondition: ele.precondition,
            manageModule: ele.manageModule,
            expected: ele.expected,
            testCaseType: ele.testCaseType,
            applicableStage: ele.applicableStage,
        };
        ztreeData.push(node);
    });
    return ztreeData;
}

/**
 * 新增步骤确认按钮
 */
function addStepBtn() {
    let treeData = addStepPanel.treeData;
    var parentId = null;
    if (treeData != null) {
        parentId = treeData.id;
    }

    var itemArr = [$("#testCaseTitleAdd"), $("#manageModuleAdd"), $("#expectedAdd"), $("#preconditionAdd")];

    var keyArr = ["testCaseTitle", "manageModule", "expected", "precondition"];

    var name = $("#testCaseNameAdd").val();
    var testCaseType = $("#testCaseTypeAdd").val();
    var applicableStage = $("#applicableStageAdd").val();
    var lastStepMarkId = $("#lastStepMarkIdAdd").val();
    var data = {
        parentId: parentId,
        name: name,
        systemId: systemId,
        moduleId: moduleId,
        lastStepMarkId: lastStepMarkId,
        stepAttr: {
            testCaseType: testCaseType,
            applicableStage: applicableStage,
        }
    };

    //如果上一个步骤是通过自动生成的
    // （如姓名输入，那么本步骤的singleStepMarkId将变为1，表示生成Excel时上一步和这一步为同一个步骤）
    if (treeData != null) {
        var parentSingleStepMarkId = treeData.singleStepMarkId;
        if (2 == parentSingleStepMarkId) {
            data.singleStepMarkId = 1;
        }
    }

    var stepAttrObj = data.stepAttr;
    $.each(itemArr, function (index, ele) {
        if (ele.val()) {
            stepAttrObj[keyArr[index]] = ele.val();
        }
    });
    if (name === '') {
        delete data.name;
    }
    if (testCaseType === "功能测试") {
        delete data.stepAttr.testCaseType;
    }

    if (applicableStage === "单元测试阶段") {
        delete data.stepAttr.applicableStage;
    }

    if ($.isEmptyObject(stepAttrObj)) {
        delete data.stepAttr;
    }

    requestAjax('post', 'aggregation/addStep', 'false', JSON.stringify(data), 'json', function (res) {
        alert(res.description);
        if (res.code === 200) {
            var treeObj = $.fn.zTree.getZTreeObj("testCaseStepTree");
            var addNode = {
                id: res.data.id,
                pId: res.data.parentId,
                name: res.data.name,
                testCaseTitle: res.data.testCaseTitle,
                manageModule: res.data.manageModule,
                expected: res.data.expected,
                precondition: res.data.precondition,
                testCaseType: res.data.testCaseType,
                applicableStage: res.data.applicableStage
            };
            treeObj.addNodes(addStepPanel.treeData, addNode);
        }
    });
    //清空新增步骤面板中的input内容
    clearAddStepPanel();
}

/**
 * 清空新增步骤面板中的input内容
 */
function clearAddStepPanel() {
    $("#testCaseNameAdd").val('');
    $("#testCaseTitleAdd").val('');
    $("#manageModuleAdd").val('');
    $("#expectedAdd").val('');
}

function clearStepPanel() {
    $("#testCaseName").val('');
    $("#testCaseTitle").val('');
    $("#manageModule").val('');
    $("#expected").val('');
    $("#moduleName").empty();
}

function updateStepBtn() {
    let treeObj = $.fn.zTree.getZTreeObj("testCaseStepTree");
    let nodes = treeObj.getSelectedNodes()[0];
    let testCaseTitle = nodes.testCaseTitle;
    let manageModule = nodes.manageModule;
    let moduleName = nodes.moduleName;
    let expected = nodes.expected;
    let precondition = nodes.precondition;
    let testCaseType = nodes.testCaseType;
    let applicableStage = nodes.applicableStage;
    let updModuleName = $("#moduleName").val();
    let updTitle = $("#testCaseTitle").val();
    let updModuleManage = $("#manageModule").val();
    let lastStepMarkId = $("#lastStepMarkId").val();
    let updExpected = $("#expected").val();
    let updPrecondition = $("#precondition").val();
    let updTestCaseType = $("#testCaseType").val();
    let updApplicableStage = $("#applicableStage").val();

    var data = {
        id: $("#stepId").val(),
        systemModuleId: moduleId,
        name: $("#testCaseName").val(),
        moduleId: updModuleName,
        lastStepMarkId: lastStepMarkId,
        stepAttr: {
            testCaseTitle: updTitle,
            manageModule: updModuleManage,
            expected: updExpected,
            precondition: updPrecondition,
            testCaseType: updTestCaseType,
            applicableStage: updApplicableStage
        }
    };

    if (ifNotChange(updModuleName, moduleName)) {
        delete data.moduleId
    }

    //删除步骤与模块的绑定关系
    if (data.moduleId == 0) {
        requestAjax('post', 'bind/deleteBindRelation', false, JSON.stringify({
            stepId: $("#stepId").val(),
            moduleId: data.moduleId
        }), 'json', function (res) {
            if (res.code === 200) {
                delete data.moduleId
            }
        });
    }


    var stepAttrObj = data.stepAttr;
    if (ifNotChange(updTitle, testCaseTitle)) {
        delete stepAttrObj.testCaseTitle
    }
    if (ifNotChange(updModuleManage, manageModule)) {
        delete stepAttrObj.manageModule
    }
    if (ifNotChange(updExpected, expected)) {
        delete stepAttrObj.expected
    }
    if (ifNotChange(updPrecondition, precondition)) {
        delete stepAttrObj.precondition
    }
    if (ifNotChange(updTestCaseType, testCaseType)) {
        delete stepAttrObj.testCaseType
    }
    if (ifNotChange(updApplicableStage, applicableStage)) {
        delete stepAttrObj.applicableStage
    }

    if ($.isEmptyObject(stepAttrObj)) {
        delete data.stepAttr;
    }

    requestAjax('post', 'aggregation/updateStep', 'false', JSON.stringify(data), 'json', function (res) {
        alert2(res.description);
        //修改信息成功后的显示要改变
        if (res.code === 200) {
            var node = testCaseStepTree.oTree.getNodeByParam("id", data.id);
            node.id = data.id;
            node.name = data.name;
            node.testCaseTitle = data.testCaseTitle;
            node.manageModule = data.manageModule;
            node.expected = data.expected;
            node.precondition = data.precondition;
            node.testCaseType = data.testCaseType;
            node.applicableStage = data.applicableStage;
        }
        testCaseStepTree.oTree.updateNode(node);
    });
}

function ifNotChange(updVal, val) {
    if (updVal === val) {
        return true;
    }
    if (val == null) {
        if (updVal === '') {
            return true;
        }
    }
    return false;
}

function addModuleBtn() {
    //modal
    var tModalConfigure = {
        id: 'batchAddStepsModal',
        frameObj: $('#modal_department'),
        title: '新增模块步骤',
        callbacks: {
            onCommit: addModuleCommit
        }
    };
    var tModal = new comModal();
    tModal.init(tModalConfigure);

    var formOption = {
        formId: 'testForm',
        initContent: [{
            itemId: "moduleName",
            label: {
                text: "模块名称"
            }
        }]
    };
    var tForm = new comForm();
    tForm.init(formOption);

    tModal.appendEle(tForm);

    $('#modal_department .modal-body').append("<div id=\"addModuleStepTree\" class=\"ztree\">\n" + "</div>");       //tree数据

    //初始化新增模块步骤树
    loadAddModuleStepTree("addModuleStepTree");

    function loadAddModuleStepTree(eId) {
        let setting = {
            view: {
                selectedMulti: false,
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
            },
            check: {
                enable: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },

            edit: {
                showRemoveBtn: true,
                showRenameBtn: true,
                enable: true,
                drag: {
                    isCopy: false,
                    isMove: false
                }
            }
        };
        $.fn.zTree.init($("#" + eId), setting, {name: "步骤名"});
    }

    var newCount = 0;

    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");

        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId + "2").length > 0) return;

        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='新增子节点' onfocus='this.blur();'></span>";

        sObj.after(addStr);

        var btn = $("#addBtn_" + treeNode.tId);

        if (btn) btn.bind("click", function () {
            let oTree = $.fn.zTree.getZTreeObj(treeId);
            oTree.addNodes(treeNode, {id: (100 + newCount), name: "new node" + (newCount++)});
            return false;
        });
    }

}

function addModuleCommit(e, oModal) {
    let oVal = oModal.getValues();

    var treeObj = $.fn.zTree.getZTreeObj("addModuleStepTree");
    var nodes = treeObj.getNodes();
    var nodesArr = [];
    getChildren(nodes, nodesArr);

    nodesArr = nodesArr.reverse();
    var nodeResult = [];
    $.each(nodesArr, function (index, item) {
        var nodeObj = {
            stepName: item.name
        };
        nodeResult.push(nodeObj);
    });

    var data = {
        systemId: systemId,
        systemModuleId: moduleId,
        moduleName: oVal.moduleName,
        stepList: nodeResult
    };

    requestAjax('post', 'aggregation/addStepModule', 'false', JSON.stringify(data), 'json', function (res) {
        alert(res.description);
        //隐藏模态框
        oModal.hide();
        //刷新模块表格
        data.moduleId = res.data.moduleId;
        stepModuleTable.appendData(data);
    });
}

function getChildren(arr, nodesArr) {
    $.each(arr, function (index, ele) {
        if (ele.children) {
            getChildren(ele.children, nodesArr);
        }
        nodesArr.push(ele);
    })
}

function getObjChildren(nodeObj, nodesArr) {
    if (nodeObj.children) {
        $.each(nodeObj.children, function (index, item) {
            nodesArr.push(item);
            getObjChildren(item, nodesArr);
        })
    }
    nodesArr.push(nodeObj);
}

/**
 * 新增根节点按钮
 */
function addRootStepBtn() {
    updateStepPanel.css('display', "none");
    addStepPanel.css('display', "inline-block");
    $("#addStepPanel .form-group").eq(0).css('display', 'none');

    addStepPanel.treeData = null;
    //清空新增步骤面板中的input内容
    clearAddStepPanel();
}

/**
 * 步骤绑定模块按钮
 */
function stepBindModuleBtn() {
    //获取选择的步骤
    var treeObj = $.fn.zTree.getZTreeObj("testCaseStepTree");
    var nodes = treeObj.getCheckedNodes(true);
    if (nodes.length === 0) {
        alert("请选择步骤");
    } else {
        //modal
        var tModalConfigure = {
            id: 'stepBindModuleModal',
            frameObj: $('#modal_department'),
            title: '步骤绑定模块',
            callbacks: {
                onCommit: stepBindModuleCommit
            }
        };
        var tModal = new comModal();
        tModal.init(tModalConfigure);

        //form
        var tFormOption = {
            formId: 'stepBindModuleForm',
            formObj: null,
            submit: {
                show: false
            }
        };
        var tForm = new comForm();
        tForm.init(tFormOption);

        var moduleSelect = [];
        //查询步骤模块
        requestAjax('post', 'stepModule/getStepModule', true, JSON.stringify({
            systemId: systemId,
            systemModuleId: moduleId
        }), 'json', function (res) {
            console.log(res);
            $.each(res.data, function (index, item) {
                let moduleObj = {
                    value: item.moduleId,
                    text: item.moduleName
                };
                moduleSelect.push(moduleObj);
            });
            //items-selectOne
            var selectConfigure = {
                itemId: "select_module",
                label: {
                    text: '选择模块'
                },
                type: "select",
                value: moduleSelect[0].value,
                options: {
                    content: moduleSelect
                }
            };
            var select_choose = new comItem();
            select_choose.oInit(selectConfigure);

            tForm.appendItems([select_choose]);
        });

        tModal.appendEle(tForm);

        //发送步骤绑定模块的请求
        function stepBindModuleCommit() {
            var stepList = [];
            //删除父节点，只绑定子节点
            nodes.shift();
            $.each(nodes, function (index, item) {
                let stepObj = {
                    stepId: item.id
                };
                stepList.push(stepObj);
            });

            var data = {
                moduleId: $("#select_module").val(),
                stepList: stepList
            };

            requestAjax('post', 'bind/stepBindModule', true, JSON.stringify(data), 'json', function (res) {
                alert(res.description);
                //隐藏模态框
                tModal.hide();
            })
        }
    }
}

/**
 * 批量步骤取消绑定模块
 */
function stepUnbindModuleBtn() {
    //获取选择的步骤
    var treeObj = $.fn.zTree.getZTreeObj("testCaseStepTree");
    var nodes = treeObj.getCheckedNodes(true);
    if (nodes.length === 0) {
        alert("请选择步骤");
    }else {
        var r = confirm("确定取消绑定模块吗？");
        if (r) {
            var stepList = [];
            //删除父节点，只绑定子节点
            nodes.shift();
            $.each(nodes, function (index, item) {
                let stepObj = {
                    stepId: item.id
                };
                stepList.push(stepObj);
            });

            var data = {
                stepList: stepList
            };

            requestAjax('post', 'bind/batchDelBindRelation', true, JSON.stringify(data), 'json', function (res) {
                alert(res.description);
            })
        }
    }
}

/**
 * 预览Excel
 */
function previewExcel() {
    requestAjax('post', 'aggregation/getTestCaseVoBySystem', false, JSON.stringify({
        systemId: systemId,
        moduleId: moduleId
    }), 'json', function (res) {

        var tModalConfigure = {
            id: 'previewExcelModal',
            frameObj: $('#modal_preview_excel'),
            type: 'browse',
            title: '预览生成用例Excel',
            eles: {
                tables: {
                    "previewExcelTable": {
                        defaultOptions: {
                            tableID: "previewExcelTable"
                        },
                        btOptions: {
                            showRefresh: false,
                            showExport: false,
                            showColumns: false,
                            columns: [
                                {
                                    field: 'manageModule',
                                    title: '所属模块'
                                }, {
                                    field: 'testCaseTitle',
                                    title: '用例标题'
                                },
                                {
                                    field: 'precondition',
                                    title: '前置条件'
                                }, {
                                    field: 'stepList',
                                    title: '步骤'
                                }, {
                                    field: 'expected',
                                    title: '预期'
                                }, {
                                    field: 'priority',
                                    title: '优先级'
                                }, {
                                    field: 'testCaseType',
                                    title: '用例类型'
                                }, {
                                    field: 'applicableStage',
                                    title: '适用阶段'
                                }
                            ],
                            showPaginationSwitch: false,
                            height: 650,
                            pageNumber: 1,
                            pageSize: 1000
                        }
                    }
                }
            }
        };
        var tModal = new comModal();
        tModal.init(tModalConfigure);

        tModal.eles.tables['previewExcelTable'].appendData(res.data);

    });
}

/**
 * 导出Excel
 */
function exportExcel() {
    Common.postExcelFile({systemId: systemId, moduleId: moduleId}, "aggregation/generateTestCasesExcel");
}

function refreshSystem(value) {
    //设置systemId
    systemId = value;
    requestAjax("post", 'systemAndModule/getModuleBySystemId', false, JSON.stringify({systemId: systemId}), 'json', function (result) {
        if (result.code === 200) {
            $("#selectModule").empty();
            //设置系统模块id
            moduleId = result.data[0].moduleId;
            $.each(result.data, function (index, item) {
                $("#selectModule").append(" <option value=" + item.moduleId + ">" + item.moduleName + "</option>");
            })
        }
    });

    loadTree();

    clearStepPanel();
    //清空新增步骤面板中的input内容
    clearAddStepPanel();
    initTable();
}

function refreshModule(value) {
    moduleId = value;

    clearStepPanel();
    //清空新增步骤面板中的input内容
    clearAddStepPanel();

    loadTree();

    initTable();
}

