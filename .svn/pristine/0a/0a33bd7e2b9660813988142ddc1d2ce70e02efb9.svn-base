var boObjTree = $('#boObjTree');

var addSystemModal = $('#addSystemModal');
var addModuleModal = $('#addModuleModal');

var objectInfoTable = new commTable();

(function initSystem() {

    loadTree();

    initTable();

    //初始化导入
    let uploadFile = initUploadFile("uploadFile", "/beanStructure/importBeanStructureInfo", getImportFileParam);
    uploadFile.Init();

    //文件上传完成
    fileUploaded();
})();

function loadTree() {
    boObjTree.setting = {
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
            onClick: boObjTreeOnClick
        }
    };

    requestAjax('post', 'beanStructure/getTreeView', 'false', null, 'json', function (res) {
        var _data = res.data;
        let nodes = ztreeFormat(_data);
        $.fn.zTree.init(boObjTree, boObjTree.setting, nodes);
        boObjTree.oTree = $.fn.zTree.getZTreeObj("boObjTree");
    });

    function boObjTreeOnClick(event, treeId, treeNode) {
        //点击自动展开/折叠此节点
        // boObjTree.oTree.expandNode(treeNode, null, false, false);
        requestAjax('post', 'beanStructure/getBeanStructureInfo?nodeId=' + treeNode.id, false, null, 'json', function (res) {
                if (res.code === 200) {
                    var result = res.data;
                    $("#className").text(result.beanName);
                    objectInfoTable.hdstTable("removeAll");
                    objectInfoTable.appendData(result.attributes);
                } else {
                    $("#className").text('');
                    objectInfoTable.hdstTable("removeAll");
                }
            }
        );
        //控制文件按钮是否可用
        disabledFileButton(treeNode.level);
    }
}

/**
 * 初始化表格
 */
function initTable() {
    objectInfoTable.init({
        tableID: "objectInfoTable",
        //isNeedOperate和otherOperationArr配套使用
        isNeedOperate: true,
        isNeedDetails: true,
        otherOperationArr: ['update'],
        otherDetailsArr: ['objectInfo'],
        //操作按钮
        operate: function () {
            return updateRowBtn();
        },
        // 详情按钮
        details: function () {
            return getAttrInfoBtn();
        },
        // 点击查看之后的方法
        objectInfo: function (e, value, row, index) { //模块的步骤集合
            getAttrInfo(e, value, row, index);
        },
        //点击修改之后的方法
        update: function (e, value, row, index) { //模块的步骤集合
            updateAttrInfo(e, value, row, index);
        }
    }, {
        showRefresh: false,
        showExport: false,
        showColumns: false,
        showPaginationSwitch: false,
        height: 500,
        pageNumber: 1,
        pageSize: 1000,
        pageList: [],
        columns: boObjHeader
    });
}

/**
 * 文件上传完成
 */
function fileUploaded() {
    $("#uploadFile").on("fileuploaded", function (event, data, previewId, index) {
        $('#uploadFile').fileinput('clear').fileinput('unlock');

        $("#importExcelModal").modal('hide');

        var result = data.response;
        alert(result.description);
        if (result.code === 200) {
            var res = result.data.data;
            $("#className").text(res.beanName);
            objectInfoTable.hdstTable("removeAll");
            objectInfoTable.appendData(res.attributes);

            //新增节点
            var treeObj = $.fn.zTree.getZTreeObj("boObjTree");
            var nodes = treeObj.getSelectedNodes();
            var arr = [];
            arr.push({
                id: result.data.id,
                name: res.beanName,
                pId: nodes[0].id
            });
            var newNode = treeObj.addNodes(nodes[0], arr)[0];
            //选中该新增的节点
            treeObj.selectNode(newNode);

            //导入文件按钮和导出文件按钮是否可用
            btnIfAvailable(true, false);
        }
    });
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
            uploadExtraData: param, //上传的时候，增加的附加参数
            // maxFilesNum: 1000,//上传最大的文件数量
            uploadAsync: true, //默认异步上传
            showUpload: false, //是否显示上传按钮
            showRemove: false, //显示移除按钮
            showPreview: true, //是否显示预览
            autoReplace: true, //再次选中文件时,不会替换掉当前文件
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
 * 控制文件按钮是否可用
 * @param level
 */
function disabledFileButton(level) {
    if (level === 0) {
        btnIfAvailable(true, true);
    } else if (level === 1) {
        btnIfAvailable(false, true);
    } else if (level === 2) {
        btnIfAvailable(true, false);
    }
}

function btnIfAvailable(importAvailable, exportAvailable) {
    var btnGroup = $("#objectInfo").children('.panel-body').children('.form-group:first-child');
    var importFileBtn = btnGroup.children('button:first-child');
    var exportFileBtn = btnGroup.children('button:nth-child(2)');
    importFileBtn.attr('disabled', importAvailable);
    exportFileBtn.attr('disabled', exportAvailable);
}

function addHoverDom(treeId, treeNode) {
    //只有根节点有新增按钮
    if (treeNode.level === 0) {
        addModelBtn(treeNode)
    }

    //1.没有子节点的节点
    //2.不是第三级节点
    // 有删除按钮
    if (!treeNode.children && treeNode.level !== 2) {
        deleteSysOrModuleBtn(treeNode);
    }

    //不是对象节点才有修改按钮
    if (treeNode.level !== 2) {
        editNodeName(treeNode);
    }
}

function addModelBtn(treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");

    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;

    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='新增模块' onfocus='this.blur();'></span>";

    sObj.after(addStr);

    var btn = $("#addBtn_" + treeNode.tId);

    if (btn) btn.bind("click", function () {

        initAddModuleModal(treeNode);
        return false;
    });
}

function deleteSysOrModuleBtn(treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId + "2").length > 0) return;

    var addStr = "<span class='button remove' id='addBtn_" + treeNode.tId + "2"
        + "' title='删除' onfocus='this.blur();'></span>";

    sObj.after(addStr);

    var btn = $("#addBtn_" + treeNode.tId + "2");

    if (btn) btn.bind("click", function () {
        if (confirm("确认删除吗？")) {
            requestAjax('post', 'beanStructure/deleteSystemOrModule?nodeId=' + treeNode.id, 'false', null, 'json', function (res) {
                alert(res.description);
                if (res.code === 200) {
                    //删除节点
                    var treeObj = $.fn.zTree.getZTreeObj("boObjTree");
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
        initEditNodeNameModal(treeNode);
        return false;
    });
}

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
    $("#addBtn_" + treeNode.tId + "2").unbind().remove();
    $("#addBtn_" + treeNode.tId + "3").unbind().remove();
}

/**
 * 导入文件
 */
function uploadFile() {
    $("#importExcelModal").modal('show');
}