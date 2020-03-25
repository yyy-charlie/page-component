let boObjHeader = [
    {
        field: 'id',
        title: 'id',
        visible: false
    },
    {
        field: 'name',
        title: '属性名',
    },
    {
        field: 'description',
        title: '属性描述'
    },
    {
        field: 'javaType',
        title: '类型'
    },
    {
        field: 'ifId',
        title: '主键'
    },
    {
        field: 'ifForeignKey',
        title: '外键'
    },
    {
        field: 'ifIgnoreParam',
        title: '不持久化'
    }
];

function updateRowBtn() {
    return '<button type="button" class="update btn btn-primary btn-xs">&nbsp;修改&nbsp;</button>&nbsp;&nbsp;'
}

function getAttrInfoBtn() {
    return '<button type="button" class="objectInfo btn btn-primary btn-xs">&nbsp;详情&nbsp;</button>&nbsp;&nbsp;'
}

function getAttrInfo(e, value, row, index) {
    //modal
    let tModalConfigure = {
        id: 'getAttrInfoModal',
        frameObj: $('#modal_department'),
        title: '查看属性详情',
        type: 'browse'
    };
    let tModal = new comModal();
    tModal.init(tModalConfigure);

    var formOption = {
        formId: 'testForm',
        initContent: [{
            itemId: "ifIdLabel",
            label: {
                text: "主键"
            },
            value: row.ifId,
            enable: false,
            required: false
        }, {
            itemId: "ifForeignKeyLabel",
            label: {
                text: "外键"
            },
            value: row.ifForeignKey,
            enable: false,
            required: false
        }, {
            itemId: "ifIgnoreParamLabel",
            label: {
                text: "不持久化"
            },
            value: row.ifIgnoreParam,
            enable: false,
            required: false
        }, {
            itemId: "columnLabel",
            label: {
                text: "持久化字段名"
            },
            value: row.column,
            enable: false,
            required: false
        }]
    };

    var primaryKeyGenerationRules = {
        itemId: "primaryKeyGenerationRulesLabel",
        label: {
            text: "主键生成规则"
        },
        value: row.generatedValue,
        enable: false,
        required: false
    };
    if (row.ifId === "是") {
        var arr1 = formOption.initContent.slice(0, 1);
        var arr2 = formOption.initContent.slice(1);
        formOption.initContent = arr1.concat(primaryKeyGenerationRules).concat(arr2);
    }
    var tForm = new comForm();
    tForm.init(formOption);

    tModal.appendEle(tForm);
}

function updateAttrInfo(e, value, row, index) {
    //modal
    let tModalConfigure = {
        id: 'updateAttrInfoModal',
        frameObj: $('#modal_department'),
        title: '修改属性信息',
        callbacks: {
            onCommit: updateAttrCommit
        }
    };
    let tModal = new comModal();
    tModal.init(tModalConfigure);

    var formOption = {
        formId: 'testForm',
        initContent: [{
            itemId: "ifId",
            label: {
                text: '主键'
            },
            type: "select",
            options: {
                content: [{
                    value: "是",
                    text: "是"
                }, {
                    value: "否",
                    text: "否"
                }]
            },
            value: row.ifId,
        }, {
            itemId: "ifForeignKey",
            label: {
                text: '外键'
            },
            type: "select",
            options: {
                content: [{
                    value: "是",
                    text: "是"
                }, {
                    value: "否",
                    text: "否"
                }]
            },
            value: row.ifForeignKey,
        }, {
            itemId: "ifIgnoreParam",
            label: {
                text: '不持久化'
            },
            type: "select",
            options: {
                content: [{
                    value: "是",
                    text: "是"
                }, {
                    value: "否",
                    text: "否"
                }]
            },
            value: row.ifIgnoreParam,
        }, {
            itemId: "column",
            label: {
                text: "持久化字段名"
            },
            value: row.column,
            required: false
        }]
    };
    var tForm = new comForm();
    tForm.init(formOption);

    tModal.appendEle(tForm);

    if (row.ifId === "是") {
        var temp = new comItem();
        temp.oInit({
            itemId: "primaryKeyGenerationRulesLabel",
            label: {
                text: "主键生成规则"
            },
            type: "select",
            options: {
                content: [{
                    value: "数据库自增",
                    text: "数据库自增"
                }]
            },
            value: row.generatedValue
        });
        tForm.insertItem(temp, tForm.items[0], "after");
    }


    tForm.items[0].dom.self.onchange = function (e) {
        if (this.value === "否") {
            tForm.removeItem(tForm.items[1]);
        } else {
            var temp = new comItem();
            temp.oInit({
                itemId: "primaryKeyGenerationRules",
                label: {
                    text: "主键生成规则"
                },
                type: "select",
                options: {
                    content: [{
                        value: "数据库自增",
                        text: "数据库自增"
                    }]
                },
                value: "数据库自增"
            });
            tForm.insertItem(temp, tForm.items[0], "after");
        }
    };

    /**
     * 提交修改模块步骤
     */
    function updateAttrCommit(e, oModal) {
        let oVal = oModal.getValues();

        var data = {
            id: row.id,
            ifId: oVal.ifId,
            ifForeignKey: oVal.ifForeignKey,
            ifIgnoreParam: oVal.ifIgnoreParam
        };

        if (oVal.column !== '') {
            data.column = oVal.column;
        }
        if (oVal.ifId === '是') {
            data.generatedValue = oVal.primaryKeyGenerationRules;
        }

        requestAjax('post', 'beanStructure/updateAttrStructureInfo', 'false', JSON.stringify(data), 'json', function (res) {
            alert(res.description);

            if (res.code === 200) {
                //隐藏模态框
                oModal.hide();
                //刷新模块表格
                var data = res.data;
                objectInfoTable.hdstTable("updateRow", {
                    index: index,
                    row: data
                });
            }
        });
    }
}

var ztreeData = [];

//将后台返回的格式转换成ztree的格式
function ztreeFormat(data) {
    $.each(data, function (index, ele) {
        var node = {
            id: ele.id,
            pId: ele.parentId,
            objectId: ele.objectId,
            name: ele.name,
            open: true
        };
        ztreeData.push(node);
        var children = ele.children;
        if (children != null && children.length > 0) {
            ztreeFormat(children)
        }
    });
    return ztreeData;
}

/**
 * 新增系统
 */
function addSystem() {
    // addSystemModal.treeData = null;

    //modal
    var tModalConfigure = {
        id: 'addSystemModal',
        frameObj: $('#modal_department'),
        title: '新增系统',
        callbacks: {
            onCommit: addSystemCommit
        }
    };
    var tModal = new comModal();
    tModal.init(tModalConfigure);

    var formOption = {
        formId: 'moduleForm',
        initContent: [
            {
                itemId: "systemName",
                label: {
                    text: "系统名 "
                },
            }]
    };
    var tForm = new comForm();
    tForm.init(formOption);

    tModal.appendEle(tForm);


    function addSystemCommit(e, oModal) {
        let oVal = oModal.getValues();

        var data = {
            name: oVal.systemName,
        };
        requestAjax('post', 'beanStructure/addSystem', 'false', JSON.stringify(data), 'json', function (res) {
            alert(res.description);
            if (res.code === 200) {
                //隐藏模态框
                oModal.hide();

                //新增节点
                var treeObj = $.fn.zTree.getZTreeObj("boObjTree");
                var arr = [];
                arr.push(res.data);
                treeObj.addNodes(null, arr);
            }
        });
    }
}

function initAddModuleModal(treeNode) {
    //modal
    var tModalConfigure = {
        id: 'addModule',
        frameObj: $('#modal_department'),
        title: '新增模块',
        callbacks: {
            onCommit: addModuleCommit
        }
    };
    var tModal = new comModal();
    tModal.init(tModalConfigure);

    addModuleModal.treeNode = treeNode;

    var formOption = {
        formId: 'moduleForm',
        initContent: [
            {
                itemId: "sysModule",
                label: {
                    text: "模块名 "
                },
            }]
    };
    var tForm = new comForm();
    tForm.init(formOption);

    tModal.appendEle(tForm);

    function addModuleCommit(e, oModal) {
        let oVal = oModal.getValues();

        var data = {
            name: oVal.sysModule,
            parentId: addModuleModal.treeNode.id
        };
        requestAjax('post', 'beanStructure/addModule', 'false', JSON.stringify(data), 'json', function (res) {
            alert(res.description);
            if (res.code === 200) {
                //隐藏模态框
                oModal.hide();

                //新增节点
                var treeObj = $.fn.zTree.getZTreeObj("boObjTree");
                var arr = [];
                arr.push(res.data);
                treeObj.addNodes(addModuleModal.treeNode, arr);
            }
        });
    }
}

function initEditNodeNameModal(treeNode) {
    //modal
    var tModalConfigure = {
        id: 'addModule',
        frameObj: $('#modal_department'),
        title: '编辑节点',
        callbacks: {
            onCommit: editNodeNameCommit
        }
    };
    var tModal = new comModal();
    tModal.init(tModalConfigure);

    // addModuleModal.treeNode = treeNode;

    var formOption = {
        formId: 'moduleForm',
        initContent: [
            {
                itemId: "editNodeName",
                label: {
                    text: "更新名称"
                },
                value: treeNode.name
            }]
    };
    var tForm = new comForm();
    tForm.init(formOption);

    tModal.appendEle(tForm);


    function editNodeNameCommit(e, oModal) {
        let oVal = oModal.getValues();

        var data = {
            id: treeNode.id,
            name: oVal.editNodeName
        };
        requestAjax('post', 'beanStructure/updateSystemOrModule', 'false', JSON.stringify(data), 'json', function (res) {
            alert(res.description);
            if (res.code === 200) {
                //隐藏模态框
                oModal.hide();

                //更新节点
                var treeObj = $.fn.zTree.getZTreeObj("boObjTree");
                //根据节点数据的属性搜索
                var nodes = treeObj.getNodesByParam("id", treeNode.id, null);
                var node = nodes[0];
                node.name = oVal.editNodeName;
                treeObj.updateNode(node);
            }
        });
    }

}

function getImportFileParam() {
    var treeObj = $.fn.zTree.getZTreeObj("boObjTree");
    var nodes = treeObj.getSelectedNodes();
    return {
        parentNodeId: nodes[0].id
    }
}

/**
 * 导出文件
 */
function exportBOObject() {
    var treeObj = $.fn.zTree.getZTreeObj("boObjTree");
    var nodes = treeObj.getSelectedNodes();
    var nodeId = nodes[0].id;
    var name = nodes[0].name;
    var url = "beanStructure/exportBeanStructureInfo?nodeId=" + nodeId;
    exportFile(url, name, "java");
}