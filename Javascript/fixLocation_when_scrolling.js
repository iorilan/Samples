(function() {
    var _scrolling = false;
    var userAgent = navigator.userAgent.toLowerCase();
    var _isIE6 = /msie 6.0/.test(userAgent) && !/msie 7.0/.test(userAgent) && !/msie 8.0/.test(userAgent);

    function _autoScroll() {
        if (_isIE6) {
            if (!_scrolling) {
                _scrolling = true;
                var fetionAll = document.getElementById("fetionAll");

                if (fetionAll) {
                    setTimeout(function() {
                        fetionAll.style.position = "absolute";

                        fetionAll.style.left = "0px";

                        if (fetionAll.style.bottom == "0px")
                            fetionAll.style.bottom = "1px";
                        else
                            fetionAll.style.bottom = "0px";

                        _scrolling = false;
                    }, 50);
                }
            }
        }
    }

    var _onscroll = window.onscroll;
    window.onscroll = function() {
        try {
            if (_onscroll)
                _onscroll();
        } catch (err) {
        }

        _autoScroll();
    }

    var _onresize = window.onresize;
    window.onresize = function() {
        try {
            if (_onresize)
                _onresize();
        } catch (err) {
        }

        _autoScroll();
    }
})();