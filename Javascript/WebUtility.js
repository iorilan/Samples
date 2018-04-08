/// 对js中的String的扩展

// 扩展endWith
String.prototype.endsWith = function(str) {
    if (typeof str == "undefined" || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substring(this.length - str.length) == str)
        return true;
    else
        return false;

    return true;
}

// 扩展startWith
String.prototype.startsWith = function(str) {
    if (typeof str == "undefined" || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substr(0, str.length) == str)
        return true;
    else
        return false;
    return true;
}

// Removes all leading and trailing characters c from the current string object
String.prototype.trim = function(c) {
    if (typeof c == "undefined") {
        return this.replace(/^\s+|\s+$/g, "");
    }

    var re = new RegExp("^[" + c + "]+|[" + c + "]+$", "g");
    return this.replace(re, "");
}

// Removes all trailing characters c from the current string object
String.prototype.trimEnd = function(c) {
    if (typeof c == "undefined") {
        return this.replace(/\s+$/g, "");
    }

    var re = new RegExp("[" + c + "]+$", "g");
    return this.replace(re, "");
}

String.prototype.toCodeArray = function() {
    var codeArray = new Array();

    var len = this.length;

    if (len == 0) {
        return codeArray;
    }

    for (var i = 0; i < len; i++) {
        codeArray.push(this.charCodeAt(i));
    }

    return codeArray;
}

String.prototype.fromCodeArray = function(codeArray) {
    if (codeArray == null) {
        return null;
    }

    var len = codeArray.length;
    var s = "";
    for (var i = 0; i < len; i++) {
        s += String.fromCharCode(codeArray[i]);
    }

    return s;
}

// ----------------------------------------------------------------------------------------
// utility methods

// string.Format
function formatString() {
    if (arguments.length < 2) {
        return arguments;
    }

    var newStr = arguments[0];

    for (var i = 0; i < arguments.length - 1; i++) {
        newStr = newStr.replace("{" + i + "}", arguments[i + 1]);
    }

    return newStr;
}

// 计算字节长度（中文和全角等字符算2个长度）
function cnLength(str) {
    if (str == null)
        return null;

    var l = 0;
    for (var k = 0; k < str.length; k++) {
        if (str.charCodeAt(k) > 255)
            l += 2;
        else
            l++;
    }

    return l;
}

// 按字节长度的字符串
function cnSubstr(str, len, nopoint) {
    if (str == null)
        return null;

    if (cnLength(str) <= len + 2 && !nopoint) {
        return str;
    }

    var strlen = 0;
    var s = "";

    if (str != null) {
        for (var k = 0; k < str.length; k++) {
            if (str.charCodeAt(k) > 255)
                strlen += 2;
            else
                strlen++;

            if (strlen <= len) {
                s += str.charAt(k);
            } else {
                if (nopoint)
                    return s;
                else
                    return s + "...";
            }
        }
    }

    return s;
}

// 计算字节长度（中文和全角等字符算2个长度，此外，一些特殊的符号以及一些大写字母也算2个长度：@#$%^&+<>ADGHMNOQTUVWY）
function cnLength2(str) {
    if (str == null)
        return null;

    var l = 0;
    for (var k = 0; k < str.length; k++) {
        var charK = str.charAt(k);

        if (str.charCodeAt(k) > 255 || '@#$%^&+<>ADGHMNOQTUVWY'.indexOf(charK) >= 0)
            l += 2;
        else
            l++;
    }

    return l;
}

// 按字节长度的字符串
function cnSubstr2(str, len, nopoint) {
    if (str == null)
        return null;

    if (cnLength2(str) <= len + 2 && !nopoint) {
        return str;
    }

    var strlen = 0;
    var s = "";

    if (str != null) {
        for (var k = 0; k < str.length; k++) {
            var charK = str.charAt(k);

            if (str.charCodeAt(k) > 255 || '@#$%^&+<>ADGHMNOQTUVWY'.indexOf(charK) >= 0)
                strlen += 2;
            else
                strlen++;

            if (strlen <= len) {
                s += charK;
            } else {
                if (nopoint)
                    return s;
                else
                    return s + "...";
            }
        }
    }

    return s;
}

// html encode和decode时的转化容器
var __htmlConverter = document.createElement("div");

// html encode
// input 输入字符；htmlElement 转换器。为减轻内存消耗，建议第二参数不要为空
function htmlEncode(input, converter) {
    if (!converter) {
        converter = __htmlConverter;
    }

    if (converter.textContent != null) {
        converter.textContent = input;
    } else {
        converter.innerText = input;
    }

    var output = converter.innerHTML;
    return output;
}

// html decode
// input 输入字符；htmlElement 转换器。为减轻内存消耗，建议第二参数不要为空
// 谨慎使用该方法，这是因为如果input中包含了html标签，使用该方法会将其清除掉
function htmlDecode(input, converter) {
    if (!converter) {
        converter = __htmlConverter;
    }

    converter.innerHTML = input;

    var output = "";
    if (converter.textContent != null) {
        output = converter.textContent;
    } else {
        output = converter.innerText;
    }

    return output;
}


// 获取浏览器可视的高度
function getViewportHeight() {
    var height = 0;

    if (window.innerHeight) {
        height = window.innerHeight;
    } else if (document.documentElement && document.documentElement.clientHeight) {
        height = document.documentElement.clientHeight;
    } else if (document.body && document.body.clientHeight) {
        height = document.body.clientHeight;
    }

    return height;
}

// 获取浏览器可视的宽度
function getViewportWidth() {
    var width = 0;

    if (window.innerWidth) {
        width = window.innerWidth;
    } else if (document.documentElement && document.documentElement.clientWidth) {
        width = document.documentElement.clientWidth;
    } else if (document.body && document.body.clientWidth) {
        width = document.body.clientWidth;
    }

    return width;
}

// 获取浏览器可见部分的中心点
function getViewportCenter() {
    var ieHeight = getViewportHeight();
    var ieWidth = getViewportWidth();

    var c = { "x": ieWidth / 2, "y": ieHeight / 2 };

    return c;
}

// 判断是否是父元素的mouseover事件，与子元素的mouseover事件区别开
var __isMsie = $.browser.msie;
function isMouseOver(sender, e) {
    e = window.event || e;
    var s = e.fromElement || e.relatedTarget;

    // 如果是IE浏览器
    if (__isMsie && s) {
        if (sender && s != sender && sender.contains && !sender.contains(s)) {
            return true;
        }
    } else if (s && sender && sender.compareDocumentPosition) {
        var res = sender.compareDocumentPosition(s);
        if (!(s == sender || res == 20 || res == 0)) {
            return true;
        }
    }

    return false;
}

// 判断是否是父元素的mouseout事件，与子元素的mouseout事件区别开
function isMouseOut(sender, e) {
    var e = window.event || e;
    var s = e.toElement || e.relatedTarget;

    // 如果是IE浏览器
    if (__isMsie && s) {
        if (sender && sender.contains && !sender.contains(s)) {
            return true;
        }
    } else if (s && sender && sender.compareDocumentPosition) {
        var res = sender.compareDocumentPosition(s);
        if (!(res == 20 || res == 0)) {
            return true;
        }
    }

    return false;
}

// 根据name获取cookie的值
function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) {
        return unescape(arr[2]);
    }

    return null;
}

// 设置cookie的值
function setCookie(name, value, expires, path, domain, secure) {
    document.cookie = name + "=" + value
                        + ((expires) ? "; expires=" + expires : "")
                        + ((path) ? "; path=" + path : "")
                        + ((domain) ? "; domain=" + domain : "")
                        + ((secure) ? "; secure" : "");
}

// remove the cookie by setting ancient expiration date
var __expires = (new Date(1970, 1, 1, 8, 0, 1)).toGMTString();
function deleteCookie(name, path, domain) {
    if (getCookie(name)) {
        document.cookie = name + "="
                        + ((path) ? "; path=" + path : "")
                        + ((domain) ? "; domain=" + domain : "")
                        + "; expires=" + __expires;
    }

}


//------------------------------------------------------------------------------------------
// JS模拟的HashMap
function jHashMap() {

    /** Map 大小 **/
    var size = 0;

    /** 对象 **/
    var entry = new Object();

    /** 存 **/
    this.put = function(key, value) {
        if (!this.containsKey(key)) {
            size++;
        }
        entry[key] = value;
    }

    /** 取 **/
    this.get = function(key) {
        return this.containsKey(key) ? entry[key] : null;
    }

    /** 删除 **/
    this.remove = function(key) {
        if (this.containsKey(key) && (delete entry[key])) {
            size--;
        }
    }

    /** 是否包含 Key **/
    this.containsKey = function(key) {
        return (key in entry);
    }

    /** 是否包含 Value **/
    this.containsValue = function(value) {
        for (var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    }

    /** 所有 Value **/
    this.values = function() {
        var values = new Array();
        for (var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    }

    /** 所有 Key **/
    this.keys = function() {
        var keys = new Array();
        for (var prop in entry) {
            keys.push(prop);
        }
        return keys;
    }

    /** Map Size **/
    this.size = function() {
        return size;
    }

    /* 清空 */
    this.clear = function() {
        size = 0;
        entry = new Object();
    }
}

// 根据key name从QueryString中获取对应的值
function getQueryString(queryStr, key) {
    var value = "";
    var re = new RegExp("[\?\&]?" + key + "=([^\&]+)", "i");
    var result = queryStr.match(re);
    if (result == null || result.length < 1) {
        value = "";
    } else {
        value = result[1];
    }

    return value;
}

// 将一个QueryString转化成一个key/value的Object
function objects(queryStr) {
    var obj = new Object();
    var re = /[\?\&]?([^=^\&]+)=([^\&]+)/i;

    var querys = queryStr.split("&");
    for (var k = 0; k < querys.length; k++) {
        var result = querys[k].match(re);
        if (result == null) {
            continue;
        }

        var length = result.length;
        if (result != null) {
            for (var i = 1; i < length; i += 2) {
                if (i + 1 < length) {
                    obj[result[i]] = result[i + 1];
                } else {
                    obj[result[i]] = "";
                }
            }
        }
    }

    return obj;
}

// 将一个URL添加至收藏夹
function addFavourite(url, title) {
    function findKeys() {
        var isMSIE = /*@cc_on!@*/false;

        // user agent sniffing is bad in general, but this is one of the times
        // when it's really necessary
        var ua = navigator.userAgent.toLowerCase(), isMac = (ua.indexOf('mac') != -1), isWebkit = (ua.indexOf('webkit') != -1), str = (isMac ? 'Command/Cmd' : 'CTRL');

        if (window.opera && (!opera.version || (opera.version() < 9))) {
            str += ' + T';  // Opera versions before 9
        } else if (ua.indexOf('konqueror') != -1) {
            str += ' + B'; // Konqueror
        } else if (window.opera || window.home || isWebkit || isMSIE || isMac) {
            // IE, Firefox, Netscape, Safari, Google Chrome, Opera 9+, iCab, IE5/Mac
            str += ' + D';
        } else {
            // default
            str += ' + D';
        }

        return str;
    }

    try {
        if (document.all) {
            window.external.addFavorite(url, title);
        } else if (window.sidebar) {
            window.sidebar.addPanel(title, url, "");
        } else {
            alert("浏览器不支持自动添加收藏夹。关闭本对话框后，请您手动使用组合快捷键'" + findKeys() + "'进行添加。");
        }
    } catch (e) {
        alert("浏览器不支持自动添加收藏夹。关闭本对话框后，请您手动使用组合快捷键'" + findKeys() + "'进行添加。");
    }
}

// 停止事件冒泡
function stopPropagation(e) {
    e = window.event || e;
    if (window.event) {
        e.cancelBubble = true;
    } else {
        e.stopPropagation();
    }
}

// 将选中的文本替换为指定的html。该方法一般用于html编辑器
function replaceSelection(range, html) {
    if (range != null) {
        if (range.pasteHTML) {
            // 当前选中html可能以为某种原因（例如点击了另一个DIV）而丢失，重新选中
            range.select();
            range.pasteHTML(html);
            return true;
        } else if (range.deleteContents && range.insertNode) {
            // 将文本html转换成DOM对象
            var temp = document.createElement("div");
            temp.innerHTML = html;

            // 将节点倒序缓存在数组中
            var elems = [];
            for (var i = temp.childNodes.length - 1; i >= 0; i--)
                elems.push(temp.childNodes[i]);

            // 首先删除选中的节点
            range.deleteContents();

            // 将html对应的节点(即temp的所有子节点)逐个插入到range中，并从temp中删除
            for (var i = 0, length = elems.length; i < length; i++)
                range.insertNode(elems[i]);

            // 重新设置光标位置
            if (elems.length > 0) {
                range.setStartBefore(elems[elems.length - 1]);
                range.setEndAfter(elems[0]);
            }

            range.collapse(false);

            temp = null;

            return true;
        }
    }

    return false;
}

// 获取系统滚动条宽度
var __scrollBarWidth = null;
function getScrollBarWidth() {
    if (__scrollBarWidth) return __scrollBarWidth;

    var scrollBarHelper = document.createElement("div");
    // if MSIE
    // 如此设置的话，scroll bar的最大宽度不能大于100px（通常不会）。
    scrollBarHelper.style.cssText = "overflow:scroll;width:100px;height:100px;";
    // else OTHER Browsers:
    // scrollBarHelper.style.cssText = "overflow:scroll;";
    document.body.appendChild(scrollBarHelper);
    if (scrollBarHelper) {
        __scrollBarWidth = {
            horizontal: scrollBarHelper.offsetHeight - scrollBarHelper.clientHeight,
            vertical: scrollBarHelper.offsetWidth - scrollBarHelper.clientWidth
        };
    }
    document.body.removeChild(scrollBarHelper);
    return __scrollBarWidth;
}

// 一个jquery插件
(function($) {
    $.extend($.fn, {
        // loading图标
        loadingPanel: function(settings) {
            var ps = $.extend({
                // loadingPanel的父元素
                parent: $(document.body),

                // loadingPanel的目标元素
                target: $(document.body),

                // loadingPanel的最长显示时间
                showTime: 18 * 1000
            }, settings);

            ps.parent = ((typeof ps.parent == "string") ? $("#" + ps.parent) : ps.parent);

            ps.target = ((typeof ps.target == "string") ? $("#" + ps.target) : ps.target);

            var panel = $('<div class="fx_ajax" style="position:absolute;text-align:center;z-index:90000;display:none;"><div class="fxDiv"></div><div class="fxColor fxTransp"></div></div>');

            ps.parent.append(panel);

            panel.hide = function() {
                panel.css("display", "none");
            };

            panel.show = function() {
                var targetPosition;
                if (ps.parent.get(0) == document.body)
                    targetPosition = ps.target.offset();
                else
                    targetPosition = ps.target.position();

                panel.css({ "top": targetPosition.top, "left": targetPosition.left, "height": ps.target.height(), "width": ps.target.width(), "display": "" });

                setTimeout(function() {
                    panel.hide();
                }, ps.showTime);
            };

            return panel;
        }
    });
})(jQuery);

// 设置剪贴板的值(仅适合ie/firefox)
function setClipboard(text) {
    if (window.clipboardData) {
        return (window.clipboardData.setData("Text", text));
    }
    else if (window.netscape) {
        netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip) return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans) return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = new String(text);
        str.data = copytext;
        trans.setTransferData("text/unicode", str, copytext.length * 2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip) return false;
        clip.setData(trans, null, clipid.kGlobalClipboard);
        return true;
    }
    return false;
}


// 返回剪贴板的内容(仅适合ie/firefox)
function getClipboard() {
    if (window.clipboardData) {
        return (window.clipboardData.getData('Text'));
    }
    else if (window.netscape) {
        netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip) return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans) return;
        trans.addDataFlavor('text/unicode');
        clip.getData(trans, clip.kGlobalClipboard);
        var str = new Object();
        var len = new Object();
        try {
            trans.getTransferData('text/unicode', str, len);
        }
        catch (error) {
            return null;
        }
        if (str) {
            if (Components.interfaces.nsISupportsWString) str = str.value.QueryInterface(Components.interfaces.nsISupportsWString);
            else if (Components.interfaces.nsISupportsString) str = str.value.QueryInterface(Components.interfaces.nsISupportsString);
            else str = null;
        }
        if (str) {
            return (str.data.substring(0, len.value / 2));
        }
    }
    return null;
} 