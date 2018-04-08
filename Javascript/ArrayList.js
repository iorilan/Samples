ArrayList.prototype = {
        recapacity: function () {
            if (this.capacity - this._size < 0) {
                this.capacity += this.span;
                var oldElements = this._elements;
                this._elements = new Array(this.capacity);
                for (var i = 0; i < this._size; i++) {
                    this._elements[i] = oldElements[i];
                }
            }
        },
        add: function (pvalue) {
            this.recapacity();
            this._elements[this._size++] = pvalue;
        },
        addIndexOf: function (index, pvalue) {
            this.recapacity();
            if (index >= this._size) {
                this._elements[this._size++] = pvalue;
                return;
            }
            this._size++;
            for (var i = this._size - 1; i > index; i--) {
                this._elements[i] = this._elements[i - 1];
            }
            this._elements[index] = pvalue;
        },
        indexOf: function (pvalue) {
            for (var i = 0; i < this._size; i++) {
                var elem = this._elements[i];
                if (elem == (pvalue)) {
                    return i;
                }
            }
            return -1;
        },
        contains: function (pvalue) {
            for (var i = 0; i < this._size; i++) {
                var elem = this._elements[i];
                if (elem == (pvalue)) {
                    return true;
                }
            }
            return false;
        },
        exchange: function (i, j) {
            var elem = this._elements[i];
            this._elements[i] = this._elements[j];
            this._elements[j] = elem;
        },
        clear: function () {
            this.capacity = 16;
            this._size = 0;
            this._elements = new Array(this.capacity);
        },
        get: function (index) {
            return this._elements[index];
        },
        size: function () {
            return this._size;
        },
        isEmpty: function () {
            return this._size == 0;
        },
        removeIndexOf: function (index) {
            if (index >= 0 && index < this._size) {
                for (var i = index; i < this._size - 1; i++) {
                    this._elements[i] = this._elements[i + 1];
                }
                this._size--;
            }
        },
        remove: function (pvalue) {
            this.removeIndexOf(this.indexOf(pvalue));
        }
    }