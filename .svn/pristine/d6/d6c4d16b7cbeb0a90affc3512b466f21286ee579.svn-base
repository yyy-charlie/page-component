function initBOTreeEditNodeNameModal(treeNode) {
    //modal
    var tModalConfigure = {
        id: 'editSystemModal',
        frameObj: $('#modal_department'),
        title: '编辑节点',
        callbacks: {
            onCommit: editNodeNameCommit
        }
    };
    var tModal = new comModal();
    tModal.init(tModalConfigure);

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
        requestAjax('post', 'beanTreeStructure/updateSystem', 'false', JSON.stringify(data), 'json', function (res) {
            alert(res.description);
            if (res.code === 200) {
                //隐藏模态框
                oModal.hide();

                //更新节点
                var treeObj = $.fn.zTree.getZTreeObj("boTree");
                //根据节点数据的属性搜索
                var nodes = treeObj.getNodesByParam("id", treeNode.id, null);
                var node = nodes[0];
                node.name = oVal.editNodeName;
                treeObj.updateNode(node);
            }
        });
    }
}

function ztreeFormat(ztreeData, data) {
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
            ztreeFormat(ztreeData, children)
        }
    });
    return ztreeData;
}

/**
 * 清除树结构预览面板里的内容
 */
function clearTreeStructurePreviewPanelContent() {
    $("#viewBOTreeName").val('');
    $("#viewBOTreeDescription").val('');
    $("#viewIfNameRepeat").val('');
    var zTreeObj = $.fn.zTree.getZTreeObj("treeStructurePreviewTree");
    if (zTreeObj != null) {
        zTreeObj.destroy();
    }
}

/**
 * 点击新增系统按钮触发的事件
 */
function addBOTreeSystemBtn() {
    //modal
    var tModalConfigure = {
        id: 'addSystem',
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
            name: oVal.systemName
        };
        requestAjax('post', 'beanTreeStructure/addSystem', 'false', JSON.stringify(data), 'json', function (res) {
            alert(res.description);
            if (res.code === 200) {
                //隐藏模态框
                oModal.hide();

                //新增节点
                var treeObj = $.fn.zTree.getZTreeObj("boTree");
                var arr = [];
                arr.push(res.data);
                treeObj.addNodes(addSystemModal.treeNode, arr);
            }
        });
    }

}

/**
 * 点击新增BO树的确认按钮触发的事件
 */
function addBOTreeCommit() {
    var name = $("#boTreeName").val();
    var description = $("#boTreeDescription").val();
    var ifNameRepeat = $("#ifNameRepeat").val();

    //获取选中的对象
    var treeObj = $.fn.zTree.getZTreeObj("boObjectTree");
    var nodes = treeObj.getCheckedNodes(true);

    var nodeStructureInfos = [];
    $.each(nodes, function (index, item) {
        var ifPersistence = $('#diyBtn_' + item.id).val();
        var nodeStructureInfo = {
            beanStructureId: item.objectId,
            ifPersistence: ifPersistence
        };
        nodeStructureInfos.push(nodeStructureInfo);
    });

    var data = {
        name: name,
        description: description,
        ifNameRepeat: ifNameRepeat,
        nodeStructureInfos: nodeStructureInfos
    };
    requestAjax('post', 'beanTreeStructure/addBeanTreeStuctureInfo?parentNodeId=' + boTreeNode.id, 'false', JSON.stringify(data), 'json', function (res) {
        alert(res.description);
        if (res.code === 200) {
            //隐藏模态框
            $("#addBOTreeModal").modal('hide');

            //导出树配置按钮可用
            $("#exportTreeConfig").attr('disabled', false);


            $("#boTreeName").val('');
            $("#boTreeDescription").val('');

            //新增节点
            var treeObj = $.fn.zTree.getZTreeObj("boTree");
            var arr = [{
                id: res.data.id,
                objectId: res.data.objectId,
                name: res.data.data.name,
                pid: res.data.parentId
            }];
            var newNode = treeObj.addNodes(boTreeNode, arr)[0];

            //点击新增的节点
            $("#" + newNode.tId + "_a").trigger('click');
        }
    });
}

/**
 * 更新BO树结构提交按钮
 */
function updateBoTreeStructure() {
    var name = $("#viewBOTreeName").val();
    var description = $("#viewBOTreeDescription").val();
    var ifNameRepeat = $("#viewIfNameRepeat").val();

    //获取选中的对象
    var treeObj = $.fn.zTree.getZTreeObj("treeStructurePreviewTree");
    var nodes = treeObj.getNodes();

    var nodeStructureInfos = [];
    $.each(nodes, function (index, item) {
        var ifPersistence = $('#diyBtn_' + item.id).val();
        var nodeStructureInfo = {
            id: item.id,
            ifPersistence: ifPersistence
        };
        nodeStructureInfos.push(nodeStructureInfo);
    });

    var boTreeObj = $.fn.zTree.getZTreeObj("boTree");
    var boTreeObjNodes = boTreeObj.getSelectedNodes();

    var data = {
        id: boTreeObjNodes[0].objectId,
        name: name,
        description: description,
        ifNameRepeat: ifNameRepeat,
        nodeStructureInfos: nodeStructureInfos
    };
    requestAjax('post', 'beanTreeStructure/updateBeanTreeStuctureInfo', 'false', JSON.stringify(data), 'json', function (res) {
        alert(res.description);
        if (res.code === 200) {

            //更新BO树名称
            var treeObj = $.fn.zTree.getZTreeObj("boTree");
            var nodes = treeObj.getSelectedNodes();
            if (nodes.length > 0) {
                nodes[0].name = name;
                treeObj.updateNode(nodes[0]);
            }
        }
    });
}

/**
 * 导出树配置
 */
function exportTreeConfigBtn() {
    var treeObj = $.fn.zTree.getZTreeObj("boTree");
    var nodes = treeObj.getSelectedNodes();
    var nodeId = nodes[0].id;
    var name = nodes[0].name;
    var url = "beanTreeStructure/exportBeanTreeStructureInfo?nodeId=" + nodeId;
    exportFile(url, name, "java");
}
