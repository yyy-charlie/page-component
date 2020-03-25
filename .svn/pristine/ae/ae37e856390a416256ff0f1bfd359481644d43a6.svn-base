//新的对象形式
var comPanel = function () {
    var oPanel = {
        conf: {
            title: null,
            defaultstyle : {
                display:"inline-grid"

            },
            css:null,
            callback:{}
        },
        dom: {
            obj:null,
            body:null
        },
        items:[]
    };
    oPanel.oInit = function (conf) {
        $.extend(oPanel.conf, conf);
        oPanel.dom.obj = generate(oPanel.conf);
        conf.initContent && oPanel.itemsInit(conf.initContent);

        function generate(conf){
            
            var oWrapper = document.createElement("div");
            var oHead = document.createElement("div");
            var oBody = document.createElement("div");

            oWrapper.setAttribute("class", "panel panel-primary");
            //style part
            if(conf.css){
                for(var prop in conf.css){
                    oWrapper.style[prop] = conf.css[prop]; 
                }
            }
            oWrapper.style.display = conf.defaultstyle.display;
            
            oHead.setAttribute("class", "panel-heading");
            oBody.setAttribute("class", "panel-body");
            oHead.innerText = conf.title;
        
            oWrapper.appendChild(oHead);
            oWrapper.appendChild(oBody);

            oPanel.dom.body = oBody;
            return oWrapper;
        };
    };

    oPanel.itemsInit = function (arr) {
        $.each(arr, function (index, ele) {
            var temp = new comItem();
            temp.oInit(ele);
            oPanel.dom.body.appendChild(temp.getDom());
            oPanel.items.push(temp);
        });
    };

    oPanel.removeItem = function (obj) {
        for (var i = 0, j = oPanel.items.length; i < j; i++) {
            if (obj === oPanel.items[i]) {
                // obj.destroy();
                oPanel.dom.body.removeChild(oPanel.dom.body.childNodes[i]);
                // oForm.items[i].dom.entire.parentNode.removeChild(i + 1);
                //改变itemArr
                oPanel.items.splice(i, 1);
                oPanel.conf.callback.onRemoveItem && oPanel.conf.callback.onRemoveItem(obj, oPanel.items);
                i--;
                j--;
            }
        }
    };

    oPanel.appendItems = function (arr) {
        $.each(arr, function (index, ele) {
            console.log(index + " " + typeof ele);
            oPanel.dom.body.appendChild(ele.getDom());
            oPanel.items.push(ele);
        });
    };

    oPanel.getDom = function(){
        return oPanel.dom.obj;
    };
    return oPanel;
}