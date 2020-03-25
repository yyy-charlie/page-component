//新的对象形式
var comForm = function () {
    var oForm = {
        _setting: {
            message: 'This value is not valid',                                             //默认的不合法提示
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {}
        },
        _option: {
            formId: '',
            formObj: null,
            submit: {
                show: false,
                css: {}
            },
            event: {
                beforeValidate: null,
                onInput: null,
                onChange: null,
                onRemoveItem: null,
                onInsertItem: null
            },
            initContent: null
        },
        _dom: {
            obj: null,
            str: "",
            self: null,
            submit: null,
            entire: null
        },
        items: []
    }

    oForm.init = function (option) {
        $.extend(oForm._option, option);
        oForm.generate(oForm._option);

        // oForm._dom.str = "<form action='' method='post' id='" + oForm._option.formId + "' name='" + oForm._option.formId + "'><input style='display:none' class='btn btn-primary' type='submit' value='提交'></form>";
        // oForm._dom.str = "<form action='' method='post' id='" + oForm._option.formId + "' name='" + oForm._option.formId + "'></form>";

        // if (oForm._option.submit.show) {
        //     oForm._dom.str = "<form action='' method='post' id='" + oForm._option.formId + "' name='" + oForm._option.formId + "'><input style='display:block' class='btn btn-primary' type='submit' value='提交'></form>"
        // }
    };

    oForm.generate = function (option) {
        oForm._dom.obj = oForm.create(option);
        option.initContent && oForm.itemsInit(option.initContent);
    };

    oForm.itemsInit = function (initArr) {
        var tempArr = [];
        $.each(initArr, function (index, ele) {
            var temp = new comItem();
            temp.oInit(ele);
            tempArr.push(temp);
        });
        oForm.appendItems(tempArr);
    };

    oForm.create = function (option) {
        var _oform = document.createElement("form");
        var oDiv = document.createElement("div");
        var oBtn = document.createElement("input");
        _oform.setAttribute("action", "");
        _oform.setAttribute("method", "post");
        _oform.setAttribute("id", oForm._option.formId);
        _oform.setAttribute("name", oForm._option.formId);

        oBtn.setAttribute("class", "btn btn-primary");
        oBtn.setAttribute("type", "submit");
        oBtn.setAttribute("value", "提交");
        oBtn.onclick = function () {
            oForm.beforeValidate();
        };

        if (!option.submit.show) {
            oDiv.setAttribute("style", "display:none");
        }

        oDiv.appendChild(oBtn);
        _oform.appendChild(oDiv);

        oForm._dom.submit = oBtn;

        return _oform;
    };

    oForm.appendItems = function (arr) {
        $.each(arr, function (index, ele) {
            console.log(index + " " + typeof ele);
            oForm._dom.obj.insertBefore(ele.getDom(), oForm._dom.obj.lastChild);
            oForm.items.push(ele);
        });
        // oForm.oFormCreate();
        oForm.initValidator();
    };

    oForm.appendLayout = function (arr) {
        $.each(arr, function (index, ele) {
            console.log(index + " " + typeof ele);
            oForm._dom.obj.insertBefore(ele.getDom(), oForm._dom.obj.lastChild);
            oForm.items.push(ele);
            // ele.items && oForm.layoutItemsInit(ele.items);
        });
    };

    oForm.removeLayout = function (obj, itemsFlag) {
        for (var i = 0, j = oForm.items.length; i < j; i++) {
            if (obj === oForm.items[i]) {
                oForm._dom.obj.removeChild(oForm._dom.obj.childNodes[i + 1]);
                //改变itemArr
                oForm.items.splice(i, 1);
                oForm._option.event.onRemoveLayout && oForm._option.event.onRemoveLayout(obj, oForm.layout);
                i--;
                j--;
            }
        }
    };

    oForm.getDomStr = function () {
        return oForm._dom.str;
    };

    oForm.getDom = function () {
        return oForm._dom.obj;
    };

    // oForm.genRenderStr = function () {                       //好像暂时不需要，在渲染之后可以获取到jq对象
    //     return oForm.genDomStr();
    // };

    oForm.oFormCreate = function () {
        var oInput = new Object();
        var domString = "";
        var tempStr = "";
        oForm.items.forEach(item => {
            tempStr = tempStr + item.getDom();
        });
        // oForm.domObj.html(tempStr);
        oForm.genDomStr(tempStr);
    };

    oForm.getEle = function () {
        return oForm.items;
    };

    oForm.initValidator = function () {
        $(oForm._dom.obj).bootstrapValidator('destroy');
        $.each(oForm.items, function (index, ele) {
            if (ele.items) {
                ergodic(ele);
            } else {
                oForm._setting.fields[ele.getId()] = {};
                oForm._setting.fields[ele.getId()].validators = ele.getValidators();
            }
        });
        $(oForm._dom.obj).bootstrapValidator(oForm._setting);

        function ergodic(container) {
            $.each(container.items, function (index, ele) {
                if (ele.items) {
                    ergodic(ele.items)
                } else {
                    oForm._setting.fields[ele.getId()] = {};
                    oForm._setting.fields[ele.getId()].validators = ele.getValidators();
                }
            });
        }
    };

    // oForm.resetForm = function(){
    //     $.each(oForm.items, function (index, ele) {
    //         oForm._setting.fields[ele.getId()] = {};
    //         oForm._setting.fields[ele.getId()].validators = ele.getValidators();
    //         $(oForm._dom.obj).data('bootstrapValidator').addField(ele.getId(),ele.getValidators());
    //     });
    // };

    oForm.validate = function () {
        $(oForm._dom.obj).data('bootstrapValidator').validate();
    };

    oForm.isValid = function () {
        return $(oForm._dom.obj).data('bootstrapValidator').isValid();
    };

    oForm.removeItem = function (obj) {
        for (var i = 0, j = oForm.items.length; i < j; i++) {
            if (obj === oForm.items[i]) {
                // obj.destroy();
                //因为form 当中有一个隐藏的submit button ,所以数字需要＋1
                oForm._dom.obj.removeChild(oForm._dom.obj.childNodes[i + 1]);
                //改变itemArr
                oForm.items.splice(i, 1);
                oForm._option.event.onRemoveItem && oForm._option.event.onRemoveItem(obj, oForm.items);
                i--;
                j--;
            }
        }
    };

    oForm.insertItem = function (obj, tObj, flag) {
        for (var i = 0, j = oForm.items.length; i < j; i++) {
            if (tObj === oForm.items[i]) {
                // $(obj.dom.entire).insertAfter($(tObj.dom.entire));
                var _form = oForm._dom.obj;
                switch (flag) {
                    case "before":
                        //因为form 当中有一个隐藏的submit button ,所以数字需要＋1
                        _form.insertBefore(obj.dom.entire, _form.childNodes[i + 1]);
                        //改变itemArr
                        oForm.items.splice(i, 0, obj);
                        break;
                    case "after":
                    default:
                        //因为form 当中有一个隐藏的submit button ,所以数字需要＋1
                        _form.insertBefore(obj.dom.entire, _form.childNodes[i + 1 + 1]);
                        //改变itemArr
                        oForm.items.splice(i + 1, 0, obj);
                        break;
                }
                oForm._option.event.onInsertItem && oForm._option.event.onInsertItem(obj, oForm.items);
                i++;
                j++;
            }
        }
        oForm.initValidator();
    }

    oForm.getItemById = function (id) {
        var temp;
        $.each(oForm.items, function (index, ele) {
            if (ele.conf.itemId == id) {
                temp = ele;
                return;
            }
        });
        return temp;
    };

    oForm.beforeValidate = function(){
        oForm.initValidator();
    };

    return oForm;
};