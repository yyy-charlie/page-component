var filedTable = new commTable();

(function initSystem() {

    //初始化导入
    let uploadFile = initUploadFile("uploadFile", "tableManage/importObj", null);
    uploadFile.Init();

    //导入完成
    fileUploaded("uploadFile");

    //初始化item表格
    initFiledTable();

    pageInteraction();

})();

function importObj() {
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
            let data = result.data;
            $("#objName").val(data.beanName);
            let attrs = data.attributes;
            $.each(attrs, function (index, item) {
                rowData.push({
                    itemId: item.name,
                    display: "block",
                    readonly: false,
                    enable: true,
                    required: true,
                    validType: null
                });
            })
        }

        $("#filedTable").bootstrapTable('refreshOptions', {
            data: rowData,
        });
    });
}

function initFiledTable() {
    var panelHeight = $("#tableConfigPanel").height();

    filedTable.init(
        {                                                                      //表头是固定的，数据要url请求
            tableID: "filedTable",
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
            columns: [
                {
                    field: 'field',
                    title: 'field',
                    align: "center"
                }, {
                    field: 'title',
                    title: 'title',
                    align: "center"
                }],
            pageNumber: 1,
            pageSize: [10000],
            responseHandler: function (data) {
                return data.data;
            },
            onClickRow: function (row, $el, field) {
                $('#filedTable .success').removeClass('success');
                $el.addClass('success');
            }
        }
    );
}

function exportConfig() {

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
}