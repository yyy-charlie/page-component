(function initSystem() {

    //悬浮显示新增按钮事件
    showAddBtnEvent();

    //ztree是否可编辑
    inEditingStateEvent();

    //显示重命名按钮事件
    showRenameBtnEvent();

    //显示删除按钮事件
    showRemoveBtnEvent();

    //显示单选/复选框事件
    showRadioOrCheckboxEvent();

    //显示新增节点的url输入框
    showAddNodeUrlInput();
})();

/**
 * 悬浮显示新增按钮事件
 */
function showAddBtnEvent() {
    showOrHideInlineEle($("#showAddBtn"), $("#addBtnTrigger").parent());
}

/**
 * ztree是否可编辑
 */
function inEditingStateEvent() {
    //重命名按钮
    showOrHideBlockEle($("#inEditingState"), $("#showRenameBtn"));
    //删除按钮
    showOrHideBlockEle($("#showRemoveBtn"), $("#removeTitle"));
}

/**
 *显示重命名按钮事件
 */
function showRenameBtnEvent() {
    $("#showRenameBtn").change(function () {
        let ifShowAddBtn = $(this).val();
        if ("2" === ifShowAddBtn) {
            $("#editDiv").css('display', 'block');
            // $("#editBtnTrigger").parent().css('display', 'inline-block');
        } else {
            $("#editDiv").css('display', 'none');
        }
    });
}

/**
 * 显示删除按钮事件
 */
function showRemoveBtnEvent() {
    showOrHideInlineEle($("#showRemoveBtn"), $("#deleteTitle"));
}

/**
 * 显示单选/复选框事件
 */
function showRadioOrCheckboxEvent() {
    showOrHideInlineEle($("#showRadioOrCheckbox"), $("#chkStyle"));
    showOrHideInlineEle($("#chkStyle"), $("#influenceParentNode"));
}

/**
 * 显示新增节点的url输入框
 */
function showAddNodeUrlInput() {
    showOrHideInlineEle($("#addBtnTrigger"), $("#addNodeUrl"));
}

/**
 * 隐藏或显示块状元素
 * @param select
 * @param ele
 */
function showOrHideBlockEle(select, ele) {
    $(select).change(function () {
        let ifShowAddBtn = $(this).val();
        if ("0" === ifShowAddBtn) {
            ele.parent().css('display', 'none');
        } else {
            ele.parent().css('display', 'block');
        }
    })
}

/**
 * 隐藏或显示行内元素
 * @param select 下拉选框id
 * @param ele 隐藏元素的子元素
 */
function showOrHideInlineEle(select, ele) {
    $(select).change(function () {
        let ifShowAddBtn = $(this).val();
        if ("0" === ifShowAddBtn) {
            ele.parent().css('display', 'none');
        } else {
            ele.parent().css('display', 'inline-block');
        }
    })
}
