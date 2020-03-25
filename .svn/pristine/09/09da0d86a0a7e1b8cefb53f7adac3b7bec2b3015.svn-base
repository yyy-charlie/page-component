var comItem = function () {
    var comObj = {
        conf: {
            itemId: null,
            label: {},
            value: null,
            type: "input",//现有select,input,textArea
            enable: true,
            required: true,
            options: {
                type: "text",
                content: []
            },
            validType: null,
            validators: {}
        },
        dom: {
            entire: null,
            self: null
        },
        tools: {

        }
    };

    comObj.oInit = function (configure) {
        $.extend(comObj.conf, configure);
        comObj.generate(comObj.conf);
    };

    comObj.generate = function (conf) {
        comObj.create(conf);
        comObj.fillValue(conf.value, conf.type);
        comObj.extraFill(conf);
        conf.validType && comObj.validFill(conf.validType, conf.validators);

        if (!conf.enable) {
            comObj.dom.self.setAttribute("disabled", "disabled");
        }
        if (conf.required) {
            conf.validators.notEmpty = {
                message: '内容不能为空'
            };
        }
        if (conf.variable) {
            var innerWrapper = document.createElement("div");
            innerWrapper.setAttribute("class", "innerWrapper")
            addVariableItem(conf.variable.content);
            comObj.dom.entire.appendChild(innerWrapper);

            comObj.dom.self.onchange = function () {
                var data = {};
                conf.variable.paramName ? data[conf.variable.paramName] = comObj.dom.self.value : data = null;
                $.ajax({
                    url: conf.variable.url,
                    type: 'POST',
                    dataType: "json",
                    data: data,
                    success: function (res) {
                        if (res.state.code == 200 || res.state.code == 204) {
                            conf.variable.responseHandle ? res = conf.variable.responseHandle(res) : null;
                            addVariableItem(res);
                        }
                    },
                    complete: function (res) {
                        console.log('has complete');
                        innerWrapper.innerHTML = '';
                        var res = [{
                            itemId: "femaleGoodA",
                            label: {
                                text: 'new1'
                            },
                            validators: {
                                notEmpty: {
                                    message: '不能为空'
                                }
                            }
                        }, {
                            itemId: "femaleGoodB",
                            label: {
                                text: 'new2'
                            },
                            validators: {
                                notEmpty: {
                                    message: '选项不能为空'
                                }
                            }
                        }, {
                            itemId: "femaleGoodC",
                            label: {
                                text: 'new3'
                            },
                            value: '123',
                            validators: {
                                notEmpty: {
                                    message: '不能为空'
                                }
                            }
                        }];
                        conf.variable.responseHandle ? res = conf.variable.responseHandle(res) : null;
                        addVariableItem(res);
                    }
                });

            };
        }

        function addVariableItem(data) {
            $.each(data, function (index, ele) {
                var t = new comItem();
                t.oInit(ele);
                console.log(t);
                innerWrapper.appendChild(t.dom.entire);
            });
        }
    };

    comObj.create = function (conf) {
        switch (conf.type) {
            case "input":
                comObj.dom.entire = oInputCreate(conf.options);
                comObj.dom.self = comObj.dom.entire.getElementsByTagName("input")[0];
                break;
            case "select":
                comObj.dom.entire = oSelectCreate(conf.options);
                comObj.dom.self = comObj.dom.entire.getElementsByTagName("select")[0];
                break;
            case "textarea":
                comObj.dom.entire = oTextAreaCreate(conf.options);
                comObj.dom.self = comObj.dom.entire.getElementsByTagName("textarea")[0];
                break;
            default:
                comObj.dom.entire = oInputCreate(conf.options);
                comObj.dom.self = comObj.dom.entire.getElementsByTagName("input")[0];
        }

        if (conf.required) {
            comObj.dom.entire.childNodes[0].innerHTML += "<span style='color:red;'> * <span>";
        }

        function oInputCreate(options) {
            //file text checkbox radio button hidden image password reset submit

            var oInput = document.createElement("input");
            var oDiv = document.createElement("div");
            var oLabel = document.createElement("label");
            oDiv.setAttribute("class", "form-group inputWrapper");
            oLabel.setAttribute("class", "label-control");
            oLabel.innerText = conf.label.text;

            switch (options.type) {
                //添加name才可以使验证被识别
                case "file":
                    oInput.setAttribute("name", conf.itemId);
                    oInput.setAttribute("type", options.type);
                    oInput.setAttribute("id", conf.itemId);
                    break;
                case "text":
                    oInput.setAttribute("name", conf.itemId);
                    oInput.setAttribute("class", "form-control");
                    oInput.setAttribute("type", options.type);
                    oInput.setAttribute("id", conf.itemId);
                    break;
                case "checkbox":
                case "radio":
                    (function () {
                        var wDiv = document.createElement("div");
                        wDiv.setAttribute("class", "optWrapper");
                        $.each(options.content, function (index, ele) {
                            var newDiv = document.createElement("div");
                            var newInput = document.createElement("input");
                            var newLabel = document.createElement("label");

                            newInput.setAttribute("name", ele.name);
                            newInput.setAttribute("type", options.type);
                            newInput.setAttribute("value", ele.value);

                            newLabel.style.marginLeft = "14p";
                            newLabel.appendChild(newInput);
                            newLabel.append(ele.label);

                            newDiv.setAttribute("class", options.type);
                            newDiv.appendChild(newLabel);
                            wDiv.appendChild(newDiv);
                        });
                        oInput = wDiv;
                    }());
                    break;
            };
            oDiv.appendChild(oLabel);
            oDiv.appendChild(oInput);

            return oDiv;

        }

        function oSelectCreate(options) {
            var oSelect = document.createElement("select"),
                oDiv = document.createElement("div"),
                oLabel = document.createElement("label");

            oDiv.setAttribute("class", "form-group selectWrapper");
            oLabel.setAttribute("class", "label-control");
            oLabel.innerText = conf.label.text;
            oSelect.setAttribute("class", "form-control");
            oSelect.setAttribute("id", conf.itemId);
            oSelect.style.width = "auto";

            $.each(options.content, function (index, ele) {
                var oTemp = document.createElement('option');
                oTemp.value = ele.value;
                oTemp.innerText = ele.text;
                oSelect.appendChild(oTemp)
            });

            oDiv.appendChild(oLabel);
            oDiv.appendChild(oSelect);
            return oDiv;
        }

        function oTextAreaCreate(options) {
            var oTextArea = document.createElement("textarea"),
                oDiv = document.createElement("div"),
                oLabel = document.createElement("label");

            oDiv.setAttribute("class", "form-group textAreaWrapper");
            oLabel.setAttribute("class", "label-control");
            oLabel.innerText = conf.label.text;
            oTextArea.setAttribute("class", "form-control");
            oTextArea.setAttribute("id", conf.itemId);
            //添加name才可以使验证被识别
            oTextArea.setAttribute("name", conf.itemId);

            if (options) {
                oTextArea.cols = options.cols;
                oTextArea.rows = options.rows;
            }

            oDiv.appendChild(oLabel);
            oDiv.appendChild(oTextArea);
            return oDiv;
        }

    };

    comObj.fillValue = function (val, type) {
        switch (type) {
            case "input":
            case "select":
            case "textarea":
                comObj.dom.self.value = val;
                break;
            default:
                comObj.dom.self.value = val;
        }
    };

    comObj.validFill = function (type, valid) {
        switch (type) {
            case "name":
                valid.stringLength = {
                    min: 2,
                    max: 20,
                    message: "姓名只能大于2个字符且小于20个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                };
                valid.regexp = {
                    regexp: /^[\u4e00-\u9fa5_a-zA-Z]+$/,
                    message: '输入的内容只可以包含中英文'
                    // message: 'The username can only consist of alphabetical, number, dot and underscore'
                };
                break;
            case "password": {
                valid.regexp = {
                    regexp: /^[a-zA-Z][a-zA-Z0-9]/,
                    message: '密码需要以英文开头,且不可含有中文'
                };
                valid.stringLength = {
                    min: 6,
                    max: 20,
                    message: "姓名只能大于6个字符且小于20个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                };
            };
                break;
            case "email":
                valid.emailAddress = {
                    message: '邮箱地址格式有误'
                };
                valid.stringLength = {
                    min: 5,
                    max: 50,
                    message: "邮箱地址只能大于5个字符且小于50个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                };
                break;
            case "age":
                valid.lessThan = {
                    value: 100,
                    inclusive: true,
                    message: "年龄需要小于100"
                    // message: 'The ages has to be less than 100'
                };
                valid.greaterThan = {
                    value: 10,
                    inclusive: false,
                    message: '年龄需要大于等于10'
                    // message: 'The ages has to be greater than or equals to 10'
                };
                break;
            case "address":
                valid.stringLength = {
                    min: 5,
                    max: 20,
                    message: "地址只能大于5个字符且小于20个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                };
                valid.regexp = {
                    regexp: /[\u4e00-\u9fa5]+/,
                    message: '输入的内容需要包含中文'
                    // message: 'The username can only consist of alphabetical, number, dot and underscore'
                }
                break;
            case "description":
                valid.stringLength = {
                    min: 0,
                    max: 50,
                    message: "描述只能小于50个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                };
                break;
            case "designation":
                valid.stringLength = {
                    min: 2,
                    max: 20,
                    message: "名称只能大于2个字符且小于20个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                };
                break;
            case "IDCard":
                valid.regexp = {
                    regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                    message: '输入的内容不是身份证号'
                    // message: 'The username can only consist of alphabetical, number, dot and underscore'
                }
                break;
            case "phoneNumber":
                valid.regexp = {
                    regexp: /^[1]([3-9])[0-9]{9}$/,
                    message: "输入的内容不是电话号码"
                    // message: 'The input is not a valid phone number',
                }
                break;
            case "Landline":
                valid.regexp = {
                    regexp: /^[0-9]{3,4}[-][0-9]{7,8}$/,
                    message: '输入的内容不是座机号'
                };
                break;
            case "Chinese":
                valid.regexp = {
                    regexp: /^[\u4e00-\u9fa5]+$/,
                    message: '输入的内容只可以包含中文'
                    // message: 'The username can only consist of alphabetical, number, dot and underscore'
                };
                break;
            case "number":
                valid.regexp = {
                    regexp: /^[0-9]*$/,
                    message: '输入的内容只可以包含数字'
                    // message: 'The username can only consist of alphabetical, number, dot and underscore'
                };
                break;
            case "positiveNumber":
                valid.regexp = {
                    regexp: /^[1-9]\d*$/,
                    message: '输入的内容不是正整数'
                }
                break;
            case "validNumber":
                valid.regexp = {
                    regexp: /^[0-9]{4,6}$/,
                    message: '输入的不是4到6位的整数'
                }
                break;
            case "resourcePath":
                valid.regexp = {
                    regexp: /^[a-zA-Z]{6,30}$/,
                    message: '输入的不是6至30位的英文'
                }
                break;
            case "type":
                valid.stringLength = {
                    min: 2,
                    max: 20,
                    message: "类型只能大于2个字符且小于20个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                }
                break;
            case "formNumber":
                valid.stringLength = {
                    min: 6,
                    max: 20,
                    message: "表单号只能大于6个字符且小于20个字符"
                    // message: 'The username must be more than 6 and less than 30 characters long'
                }
                break;
            case "openingGasVolume":
                valid.regexp = {
                    regexp: /^[0-9]{1,5}$/,
                    message: '只可输入1-5位的数字'
                }
                break;
            default:
                valid.notEmpty = {
                    message: '内容默认不能为空'
                }
        };
    };

    comObj.extraFill = function (conf) {
        if (conf.placeholder) {
            comObj.dom.self.setAttribute("placeholder", conf.placeholder);
        }
    };

    // comObj.getDomObj = function () {
    //     return $("#" + comObj.conf.itemId);
    // };

    // comObj.refreshDomStr= function(){
    //     comObj.domStr = oInputCreate();
    //     return comObj.domStr;
    // };

    comObj.getDom = function () {
        return comObj.dom.entire;
    };

    comObj.getValue = function () {
        return comObj.dom.self.value;
    };

    comObj.getValidators = function () {
        return comObj.conf.validators;
    };

    comObj.getEle = function () {
        var t = {
            name: comObj.conf.itemId,
            value: comObj.dom.self.value
        };
        return t;
    };

    // comObj.insert = function (wrapper) {
    //     $(wrapper.parent).append(comObj.rawItem);
    //     $('#' + comObj.conf.itemId).data(comObj.conf);
    // };

    comObj.setId = function (id) {
        comObj.dom.self.setAttribute('id', id);
        comObj.dom.self.setAttribute('name', id);
        comObj.conf.itemId = id;
    };

    comObj.getId = function () {
        return comObj.conf.itemId;
    };

    comObj.destroy = function () {
        comObj.dom.entire.remove();
    };

    return comObj;
};