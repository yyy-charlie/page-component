var fieldTable = new commTable();
$(document).ready(function () {
    initTable();
});

function initTable() {
    fieldTable.init({
            dataParam: {
                data: getDataParam,
                contentType: "application/json",
            },
            tableID: "fieldTable",
            dataUrl: "http://rap2.taobao.org:38080/app/mock/241077/getTableData",
            queryTableParams: getQueryTableParams,
            orderNumber: true,
            checkbox: false,
            isNeedDetails: true,
            details: getDetailsButton,
            otherDetailsArr: ["delDetails"],
            delDetails: delDetails,
            isNeedOperate: true,
            operate: getOperateButton,
            otherOperationArr: ["update", "orderRemove"],
            update: update,
            orderRemove: orderRemove,
        },
        {
            columns: getColumns(),
            search: false,
            height: "",
            sidePagination: "client",
            pageSize: [10000],
            responseHandler: responseHandler,
        },
    );
}

function getDataParam() {
    return JSON.stringify({});
}

function getQueryTableParams() {

}

function getDetailsButton() {
    return '<button type="button" class="delDetails btn-primary btn-xs">&nbsp;查看详情&nbsp;</button>&nbsp;&nbsp;'
}

function delDetails(e, value, row, index) {
}

function getOperateButton() {
    return '<button type="button" class="update btn-primary btn-xs">&nbsp;修改&nbsp;</button>&nbsp;&nbsp;<button type="button" class="orderRemove btn-primary btn-xs">&nbsp;删除&nbsp;</button>&nbsp;&nbsp;'
}

function update(e, value, row, index) {
}

function orderRemove(e, value, row, index) {
}

function getColumns() {
    return [{"field": "deliveryId", "title": "送货单号"}, {"field": "number", "title": "送货数量"}, {
        "field": "typeName",
        "title": "类型名称"
    }, {"field": "versionName", "title": "型号名称"}];
}

function responseHandler(res) {
    return res.data;
}
