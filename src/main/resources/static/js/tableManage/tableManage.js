var fieldTable = new commTable();

(function initSystem() {

    //初始化导入
    let uploadFile = initUploadFile("uploadFile", "tableManage/importObj", null, true);
    uploadFile.Init();

    //导入完成
    fileUploaded("uploadFile");

    //初始化item表格
    initFieldTable();

    //页面交互
    pageInteraction();

})();

function importTableObj() {
    $("#table-importObjModal").modal('show');
}

/**
 * 文件上传完成
 */
function fileUploaded(ctrlName) {
    $("#" + ctrlName).on("fileuploaded", function (event, data, previewId, index) {
        $('#' + ctrlName).fileinput('clear').fileinput('unlock');

        $("#table-importObjModal").modal('hide');

        var rowData = [];

        var result = data.response;
        if (result.code == 200) {
            let attrs = result.data.attributes;
            $.each(attrs, function (index, item) {
                rowData.push({
                    field: item.name,
                    title: ''
                });
            })
        }

        $("#fieldTable").bootstrapTable('append', rowData);
    });
}

function initFieldTable() {
    var panelHeight = $("#tableConfigPanel").height();

    fieldTable.init(
        {                                                                      //表头是固定的，数据要url请求
            tableID: "fieldTable",
            loadMethod: 'button',                                                           //默认是scroll 需要其他的写button
            orderNumber: false,
            checkbox: false,
            isNeedOperate: true,                                           //操作列
            otherOperationArr: ['removeField'],
            operate: `<button type="button" class="removeField btn-primary btn-xs">&nbsp;删除&nbsp;</button>&nbsp;&nbsp; `,
            removeField: function (e, value, row, index) {
                removeField(e, value, row, index);
            },
        }, {
            showRefresh: false,
            showExport: false,
            showColumns: true,
            search: false,
            height: panelHeight * 0.5,
            columns: [
                {
                    field: 'field',
                    title: 'field',
                    align: "center",
                    width: '450px',
                    editable: {
                        type: 'text',
                        validate: function (v) {
                            if (!v) return 'field不能为空';
                        }
                    }
                }, {
                    field: 'title',
                    title: 'title',
                    align: "center",
                    width: '450px',
                    editable: {
                        type: 'text',
                        validate: function (v) {
                            if (!v) return 'title不能为空';
                        }
                    }
                }],
            pageNumber: 1,
            pageSize: [10000],
            responseHandler: function (data) {
                return data.data;
            },
            onEditableSave: function (field, row, oldValue, $el) {
                //解决表头与内容宽度不对齐的问题
                $("#fieldTable").bootstrapTable('resetView');
            }
        }
    );
}

function removeField(e, value, row, index) {
    fieldTable.rawTable.bootstrapTable('remove', {
        field: 'no',
        values: [index + 1]
    });
}

function addFieldBtn(e, value, row, index) {
    //modal
    let modalConf = {
        id: 'addFieldModal',
        frameObj: $('#modal_department'),
        title: '新增field',
        callbacks: {
            onCommit: commitAddItem                                                              //这里是填写方法名
        }
    };
    let nModal = new comModal();
    nModal.init(modalConf);

    //form
    let formOpt = {
        formId: 'nForm',
        initContent: [{
            itemId: 'field',
            label: {
                text: 'field'
            }
        }, {
            itemId: 'title',
            label: {
                text: 'title'
            }
        }]
    };
    let nForm = new comForm();
    nForm.init(formOpt);

    //form加入到modal
    nModal.appendEle(nForm);

    function commitAddItem(e, modal) {
        let val = modal.getValues();
        fieldTable.rawTable.bootstrapTable('insertRow', {
            index: 0,
            row: val
        });
        modal.hide();
    }
}

function pageInteraction() {
    //查看列
    $("#isNeedDetails").change(function () {
        let self = $(this);
        if (this.value) {
            self.parent().next().css('display', 'inline-block');
        } else {
            self.parent().next().css('display', 'none');
        }
    });

    //操作列
    $("#isNeedOperate").change(function () {
        let self = $(this);
        if (this.value) {
            self.parent().next().css('display', 'inline-block');
        } else {
            self.parent().next().css('display', 'none');
        }
    });

    //配置表头是否固定
    $("#columns").change(function () {
        if (this.value) {
            $("#importBtn").attr("disabled", false);
            $("#addFieldBtn").attr("disabled", false);
        } else {
            $("#importBtn").attr("disabled", true);
            $("#addFieldBtn").attr("disabled", true);
        }
    });


}