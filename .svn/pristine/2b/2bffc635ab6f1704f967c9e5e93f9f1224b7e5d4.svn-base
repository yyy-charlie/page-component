var testTree = $('#testTree');
(function initSystem() {
    loadTree();
})();

function loadTree() {
    testTree.setting = {
        view: {
            selectedMulti: true,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            showLine: true,
            txtSelectedEnable: false,
        },
        edit: {
            drag: {isCopy: false, isMove: false,},
            showRemoveBtn: setRemoveBtn, showRenameBtn: false, enable: true, renameTitle: "编辑节点", removeTitle: "删除节点",
        },
        check: {enable: false,},
        callback: {onClick: zTreeOnClick, onRemove: zTreeOnRemove, beforeRemove: zTreeBeforeRemove,},
    };
    requestAjax("post", "http://rap2api.taobao.org/app/mock/241077/getTreeView1", "false", null, "json", function (res) {
        var _data = res.data;
        let nodes = ztreeFormat(_data);
        $.fn.zTree.init(testTree, testTree.setting, nodes);
        testTree.oTree = $.fn.zTree.getZTreeObj("testTree");
    });

    function ztreeFormat(data) {
        $.each(data, function (index, ele) {
            ele["pId"] = ele.parentId;
            ele["open"] = true;
            var children = ele.children;
            if (children != null && children.length > 0) {
                ztreeFormat(children)
            }
        });
        return data;
    }
}

function addHoverDom(treeId, treeNode) {
    var aObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#diyBtn_" + treeNode.tId).length > 0) return;

    var editStr = "<span class='button add' title='新增子节点' id='diyBtn_" + treeNode.tId + "' ></span>";

    aObj.append(editStr);
    var btn = $("#diyBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function () {
        //初始化模态框
        initAddModal(treeNode);

        function initAddModal(treeNode) {
            //modal
            var tModalConfigure = {
                id: "addModule",
                frameObj: $("#modal_department"),
                title: "新增",
                callbacks: {
                    onCommit: addCommit
                }
            };
            var tModal = new comModal();
            tModal.init(tModalConfigure);

            var formOption = {
                formId: "moduleForm",
                initContent: [
                    {
                        itemId: "addName",
                        label: {
                            text: "新增名称"
                        },
                    }]
            };
            var tForm = new comForm();
            tForm.init(formOption);

            tModal.appendEle(tForm);

            function addCommit(e, oModal) {
                let oVal = oModal.getValues();
                var data = {
                    name: oVal.addName,
                    parentId: treeNode.id
                };
                requestAjax("post", "http://rap2.taobao.org:38080/app/mock/241077/addSystem", "false", JSON.stringify(data), "json", function (res) {
                    alert(res.description);
                    if (res.code === 200) {
                        //隐藏模态框
                        oModal.hide();

                        //新增节点
                        var treeObj = $.fn.zTree.getZTreeObj("testTree");
                        var arr = [];
                        arr.push(res.data);
                        treeObj.addNodes(treeNode, arr);
                    }
                });
            }
        }
    });
    if (treeNode.editNameFlag || $("#diyBtn_" + treeNode.tId + 2).length > 0) return;
    var editStr = "<span class='button edit' title='编辑节点' id='diyBtn_" + treeNode.tId + 2 + "' ></span>";
    aObj.append(editStr);
    var btn2 = $("#diyBtn_" + treeNode.tId + 2);
    if (btn2) btn2.bind("click", function () {//初始化模态框
        initEditModal(treeNode);

        function initEditModal(treeNode) {
            //modal
            var tModalConfigure = {
                id: "editModule",
                frameObj: $("#modal_department"),
                title: "修改",
                callbacks: {
                    onCommit: editCommit
                }
            };
            var tModal = new comModal();
            tModal.init(tModalConfigure);

            var formOption = {
                formId: "moduleForm",
                initContent: [
                    {
                        itemId: "editName",
                        label: {
                            text: "修改名称"
                        },
                        value: treeNode.name
                    }]
            };
            var tForm = new comForm();
            tForm.init(formOption);

            tModal.appendEle(tForm);

            function editCommit(e, oModal) {
                let oVal = oModal.getValues();
                var data = {
                    name: oVal.editName,
                    parentId: treeNode.id
                };
                requestAjax("post", "http://rap2.taobao.org:38080/app/mock/241077/updateSysOrModuleName", "false", JSON.stringify(data), "json", function (res) {
                    alert(res.description);
                    if (res.code === 200) {
                        //隐藏模态框
                        oModal.hide();

                        //编辑节点
                        var treeObj = $.fn.zTree.getZTreeObj("testTree");
                        var nodes = treeObj.getNodesByParam("id", treeNode.id, null);
                        var node = nodes[0];
                        node.name = oVal.editName;
                        treeObj.updateNode(node);
                    }
                });
            }
        }
    });

}

function setRemoveBtn(treeId, treeNode) {
    return !treeNode.isParent;
}

function removeHoverDom(treeId, treeNode) {
    $("#diyBtn_" + treeNode.tId).unbind().remove();
    $("#diyBtn_" + treeNode.tId + 2).unbind().remove();
}

function zTreeOnClick(event, treeId, treeNode) {
    //点击自动展开/折叠此节点
    testTree.oTree.expandNode(treeNode, null, false, false);
}

function zTreeBeforeRemove(treeId, treeNode) {
    return confirm("是否删除该节点？");
}

function zTreeOnRemove(event, treeId, treeNode) {

    requestAjax('post', 'http://rap2.taobao.org:38080/app/mock/241077/delNode', 'false', JSON.stringify({nodeId: treeNode.id}), 'json', function (res) {
        alert(res.description);
        if (res.code === 200) {
            //根据节点数据的属性搜索
            var nodes = testTree.oTree.getNodesByParam("id", treeNode.id, null);
            for (let i = 0, l = nodes.length; i < l; i++) {
                testTree.oTree.removeNode(nodes[i]);
            }
        }
    });
}