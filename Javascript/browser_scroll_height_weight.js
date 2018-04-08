getViewportHeight: function() {
            var height = 0;

            if (window.innerHeight) {
                height = window.innerHeight;
            } else if (document.documentElement && document.documentElement.clientHeight) {
                height = document.documentElement.clientHeight;
            } else if (document.body && document.body.clientHeight) {
                height = document.body.clientHeight;
            }

            return height;
        },

        getViewportWidth: function() {
            var width = 0;

            if (window.innerWidth) {
                width = window.innerWidth;
            } else if (document.documentElement && document.documentElement.clientWidth) {
                width = document.documentElement.clientWidth;
            } else if (document.body && document.body.clientWidth) {
                width = document.body.clientWidth;
            }

            return width;
        },

        getScrollTop: function() {
            var scrollTop = 0;
            if (document.documentElement && document.documentElement.scrollTop) {
                scrollTop = document.documentElement.scrollTop;
            }
            else if (document.body && document.body.scrollTop) {
                scrollTop = document.body.scrollTop;
            }

            return scrollTop;
        },

        getScrollLeft: function() {
            var scrollLeft = 0;

            if (document.documentElement && document.documentElement.scrollLeft) {
                scrollLeft = document.documentElement.scrollLeft;
            }
            else if (document.body && document.body.scrollLeft) {
                scrollLeft = document.body.scrollLeft;
            }

            return scrollLeft;
        },

        // get browser center point
        getViewportCenter: function() {
            var ieHeight = $.getViewportHeight(),
                ieWidth = $.getViewportWidth(),
                scrollTop = $.getScrollTop(),
                scrollLeft = $.getScrollLeft();

            var c = { "x": ieWidth / 2 + scrollLeft, "y": ieHeight / 2 + scrollTop };

            return c;
        }