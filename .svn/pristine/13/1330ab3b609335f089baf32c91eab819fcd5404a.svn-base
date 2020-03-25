var boTree = $('#boTree');
var boObjectTree = $('#boObjectTree');
var treeStructurePreviewTree = $('#treeStructurePreviewTree');

var addSystemModal = $('#addSystemModal');
var addBOTreeModal = $('#addBOTreeModal');
var editSystemModal = $('#editSystemModal');

var boTreeNode;

(function initSystem() {

    loadTree();

})();

function loadTree() {
    boTree.setting = {
        view: {
            selectedMulti: false,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom
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
        },
        callback: {
            onClick: boTreeOnClick
        }
    };

    requestAjax('post', 'beanTreeStructure/getTreeView', 'false', null, 'json', function (res) {
        if (res.code === 200) {
            var _data = res.data;
            ztreeData = [];
            let nodes = ztreeFormat(ztreeData, _data);
            $.fn.zTree.init(boTree, boTree.setting, nodes);
            boTree.oTree = $.fn.zTree.getZTreeObj("boTree");
        }
    });

    function addHoverDom(treeId, treeNode) {
        //只有根节点有新增和修改按钮
        if (treeNode.level === 0) {
            addBOTreeBtn(treeNode);
            //节点的修改名称按钮
            editNodeName(treeNode);
        }
        //没有子节点的节点有删除按钮
        if (!treeNode.children && treeNode.level !== 1) {
            deleteSystemBtn(treeNode);
        }
    }

    function addBOTreeBtn(treeNode) {
        boTreeNode = treeNode;
        var sObj = $("#" + treeNode.tId + "_span");

        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;

        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='新增BO树' onfocus='this.blur();'></span>";

        sObj.after(addStr);

        var btn = $("#addBtn_" + treeNode.tId);

        if (btn) btn.bind("click", function () {

            //销毁BO对象树
            $.fn.zTree.destroy("boObjectTree");

            addBOTreeModal.treeNode = treeNode;

            $("#addBOTreeModal").modal('show');

            boObjectTree.setting = {
                view: {
                    selectedMulti: false,
                    // addDiyDom: addDiyDom
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                check: {
                    enable: true,
                    chkStyle: "checkbox",
                    chkboxType: {"Y": "", "N": ""}
                },
                edit: {
                    showRemoveBtn: false,
                    showRenameBtn: false,
                    enable: true,
                    drag: {
                        isCopy: false,
                        isMove: false
                    }
                },
                callback: {
                    beforeCheck: zTreeBeforeCheck
                }
            };

            requestAjax('post', 'beanStructure/getDescendantsView?parentNodeId=' + treeNode.id, 'false', null, 'json', function (res) {
                if (res.code === 200) {
                    var _data = res.data;
                    var ztreeData = [];
                    let nodes = ztreeFormat(ztreeData, _data);
                    $.fn.zTree.init(boObjectTree, boObjectTree.setting, nodes);
                    boObjectTree.oTree = $.fn.zTree.getZTreeObj("boObjectTree");
                }
            });

            return false;
        });


    }

    function deleteSystemBtn(treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId + "2").length > 0) return;

        var addStr = "<span class='button remove' id='addBtn_" + treeNode.tId + "2"
            + "' title='删除' onfocus='this.blur();'></span>";

        sObj.after(addStr);

        var btn = $("#addBtn_" + treeNode.tId + "2");

        if (btn) btn.bind("click", function () {
            if (confirm("确认删除吗？")) {
                requestAjax('post', 'beanTreeStructure/deleteSystem?nodeId=' + treeNode.id, 'false', null, 'json', function (res) {
                    alert(res.description);
                    if (res.code === 200) {
                        //删除节点
                        var treeObj = $.fn.zTree.getZTreeObj("boTree");
                        //根据节点数据的属性搜索
                        var nodes = treeObj.getNodesByParam("id", treeNode.id, null);
                        for (let i = 0, l = nodes.length; i < l; i++) {
                            treeObj.removeNode(nodes[i]);
                        }
                    }
                });
            }
            return false;
        });
    }

    function editNodeName(treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");

        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId + 3).length > 0) return;

        var addStr = "<span class='button edit' id='addBtn_" + treeNode.tId + 3
            + "' title='编辑' onfocus='this.blur();'></span>";

        sObj.after(addStr);

        var btn = $("#addBtn_" + treeNode.tId + 3);

        if (btn) btn.bind("click", function () {

            initBOTreeEditNodeNameModal(treeNode);
            return false;
        });

    }

    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#addBtn_" + treeNode.tId + "2").unbind().remove();
        $("#addBtn_" + treeNode.tId + "3").unbind().remove();
    }


    function boTreeOnClick(event, treeId, treeNode) {
        //设置导出树配置按钮是否可用
        if (treeNode.level === 1) {
            $("#exportTreeConfig").attr('disabled', false);

            //BO树结构预览
            treeStructurePreviewTree.setting = {
                view: {
                    selectedMulti: false
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

            requestAjax('post', 'beanTreeStructure/getBeanTreeStructureInfoView?nodeId=' + treeNode.id, 'false', null, 'json', function (res) {
                if (res.code === 200) {
                    var _data = res.data.data;
                    $("#viewBOTreeName").val(_data.name);
                    $("#viewBOTreeDescription").val(_data.description);
                    $("#viewIfNameRepeat").val(_data.ifNameRepeat.toString());

                    ztreeData = [];
                    let nodes = ztreeFormat(ztreeData, res.data.children);
                    $.fn.zTree.init(treeStructurePreviewTree, treeStructurePreviewTree.setting, nodes);

                    function ztreeFormat(ztreeData, data) {
                        $.each(data, function (index, ele) {
                            var node = {
                                id: ele.id,
                                pId: ele.parentId,
                                objectId: ele.data.id,
                                name: ele.name,
                                ifPersistence: ele.data.ifPersistence,
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

                    var treeObj = $.fn.zTree.getZTreeObj("treeStructurePreviewTree");
                    var allNodes = treeObj.getNodes();
                    $.each(allNodes, function (index, item) {
                        treeStructurePreviewTree.setting.view['addDiyDom'] = addDiyDom(item.id, item, item.ifPersistence);
                    })
                } else {
                    clearTreeStructurePreviewPanelContent();
                }
            });
        } else {
            $("#exportTreeConfig").attr('disabled', true);
            clearTreeStructurePreviewPanelContent();
        }
    }

    function zTreeBeforeCheck(treeId, treeNode) {
        var checkStatus = treeNode.checked;
        if (treeNode.level === 1) {
            if (checkStatus) {
                delete boObjectTree.setting.view['addDiyDom'];
                $("#" + treeNode.tId + "_a select").remove();
            } else {
                boObjectTree.setting.view['addDiyDom'] = addDiyDom(treeId, treeNode, true);
            }
        }
    }

    //悬浮BO对象树显示的图标
    function addDiyDom(treeId, treeNode, ifPersistence) {
        var aObj = $("#" + treeNode.tId + "_a");
        if ($("#diyBtn_" + treeNode.id).length > 0) return;
        var editStr = "<span id='diyBtn_space_" + treeNode.id + "' ></span>"
            + "<select  id='diyBtn_" + treeNode.id
            + "' title='" + treeNode.name + "' onfocus='this.blur();'>";
        if (ifPersistence) {
            editStr += "<option value='true'>持久化</option><option value='false'>不持久化</option></select>";
        } else {
            editStr += "<option value='false'>不持久化</option><option value='true'>持久化</option></select>";
        }
        aObj.append(editStr);
    }
}

