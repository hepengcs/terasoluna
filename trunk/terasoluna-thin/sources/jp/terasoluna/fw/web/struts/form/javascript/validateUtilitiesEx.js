
    /*
     * Check hankaku kana.
     * True is returned for the character included in the hankaku kana list. 
     *
     * c       character for check
     * hankakuKanaList hankaku kana list
     */
    function isHankakuKanaChar(c, hankakuKanaList) {

        if (c.length != 1) {
            return false;
        }

        if (hankakuKanaList == null) {
            return false;
        }

        if (hankakuKanaList.indexOf(c) >= 0) {
            return true;
        }

        return false;
    }

    /*
     * Check hankaku.
     * When the argument is a hankaku, true is returned. 
     *
     * c       character for check
     * hankakuKanaList hankaku kana list
     */
    function isHankakuChar(c, hankakuKanaList) {

        if (c.length != 1) {
            return false;
        }

        if (hankakuKanaList == null) {
            return false;
        }

        if (c.charCodeAt(0) < 128) {
            return true;
        }

        if (hankakuKanaList.indexOf(c) >= 0) {
            return true;
        }

        return false;
    }

    /*
     * Check zenkaku.
     * When the argument is not a hankaku, true is returned. 
     *
     * c       character for check
     * hankakuKanaList hankaku kana list
     */
    function isZenkakuChar(c, hankakuKanaList) {

        if (c.length != 1) {
            return false;
        }

        return (!isHankakuChar(c, hankakuKanaList));
    }

    /*
     * Check zenkaku kana.
     * True is returned for the character included in the zenkaku kana list. 
     *
     * c       character for check
     * zenkakuKanaList zenkaku kana list
     */
    function isZenkakuKanaChar(c, zenkakuKanaList) {

        if (c.length != 1) {
            return false;
        }

        if (zenkakuKanaList == null) {
            return false;
        }

        if (zenkakuKanaList.indexOf(c) >= 0) {
            return true;
        }

        return false;
    }
