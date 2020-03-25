var commTreeView = function () {

    var commTreeViewObj = Object();

    commTreeViewObj.rawTreeView = null;                         //treeview的容器

    commTreeViewObj.defaultOption = {
        // divID: null,
        // url: "",
        // bootstrap2: false,
        // showTags: false,
        // level: 2,
        // unique: true,
        backColor: "",
        // color: "",
        // expand: false,
        // childNodeAutoSelected: true,
        // onNodeExpanded:function (e) {
        //     console.log(e);
        // },
        active: false,
        onNodeClicked: function (event, data) {
            commTreeViewObj.nodeSingleClick(event, data);
        }
    };

    commTreeViewObj.init = function (option) {

        commTreeViewObj.setOption(option);

        var tempDom = $('#' + option.divID);

        commTreeViewObj.rawTreeView = tempDom.treeview(commTreeViewObj.defaultOption);

        commTreeViewObj.requirementConfigure(commTreeViewObj.defaultOption);

    };

    commTreeViewObj.requirementConfigure =function(configure){
        if(configure.expanded) {
            commTreeViewObj.spreadTree();
        }
    };

    commTreeViewObj.setOption = function (option) {
        commTreeViewObj.defaultOption = $.extend(commTreeViewObj.defaultOption, option);
        if (option.url) {
            $.ajax({
                type: "POST",
                url: option.url,
                data: "id=" + option.param,
                async: false,//取消异步
                dataType: "json",
                success: function (res) {
                    commTreeViewObj.extendData(res);
                }
            });
        }
    };

    commTreeViewObj.setData = function (data) {

        commTreeViewObj.extendData(data);

        commTreeViewObj.rawTreeView.treeview(commTreeViewObj.defaultOption);

    };

    commTreeViewObj.extendData = function (res) {
        var dataFormat;

        if(res.state && res.state.code === 200){
            dataFormat = commTreeViewObj.changeDataFormat(res.data);
        } else {
            dataFormat = commTreeViewObj.changeDataFormat(res);                                                     //为现有的接口返回提供的处理
        }

        commTreeViewObj.defaultOption = $.extend(commTreeViewObj.defaultOption, {data: dataFormat});
    };

    commTreeViewObj.changeDataFormat = function (data) {
        var tempArray = [];
        $.each(data, function (index, ele) {

            var obj = commTreeViewObj.objFormat(ele);

            if (ele.childCount > 0) {
                obj.nodes = commTreeViewObj.changeDataFormat(ele.nodeChildren);
            }

            tempArray.push(obj);

        });
        return tempArray;
    };

    commTreeViewObj.objFormat = function (customObj) {
        //返回treeview接收的对象形式
        return {
            id: customObj.nodeId,
            text: customObj.nodeName,
            href: customObj.nodeUrl,
            tags: [customObj.childCount > 0 ? customObj.childCount : ''],
            childCount: customObj.childCount > 0 ? customObj.childCount : 0
        };
    };

    commTreeViewObj.nodeSingleClick = function (event, node) {

        console.log(node);

        if (node.childCount > 0) {

            if (node.state.expanded) {

                commTreeViewObj.rawTreeView.treeview('collapseNode', node.nodeId);

            } else {

                if ((!node.nodes && node.childCount > 0) || (node.nodes && node.nodes.length === 0)) {

                    commTreeViewObj.loadChildNode(commTreeViewObj.defaultOption.url, node); //添加子节点

                }

                commTreeViewObj.rawTreeView.treeview('expandNode', node.nodeId);

                $("ul ." + node.id).next().trigger('click');
            }
        } else {
            if (!node.state.selected) {

                $("ul ." + node.id).trigger('click');                                                   //刷新点击

            }
        }
    };

    commTreeViewObj.loadChildNode = function (url, node) {

        $.ajax({
            type: "POST",
            url: url,
            data: 'id=' + node.id,
            async: false,//取消异步
            dataType: "json",
            success: function (res) {
                console.log(res);
                if (!res) {
                    return;
                }

                $.each(res, function (index, ele) {

                    var ndd = {
                        id: ele.nodeId,
                        text: ele.nodeName,
                        href: ele.nodeUrl,
                        tags: [ele.childCount > 0 ? ele.childCount : ''],
                        childCount: ele.childCount > 0 ? ele.childCount : 0
                    };
                    if (ele.childCount > 0) {
                        ndd.nodes = [];
                    }

                    commTreeViewObj.rawTreeView.treeview("addNode", [node.nodeId, {node: ndd}]);
                });
            }
        });
    };

    commTreeViewObj.spreadTree = function () {
        commTreeViewObj.rawTreeView.children().children().eq(0).trigger('click');
    };

    commTreeViewObj.on = function (type,fn) {
        commTreeViewObj.rawTreeView.on(type,fn);
    };

    return commTreeViewObj;

};