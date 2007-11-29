
    /*$RCSfile$ $Revision: 16732 $ $Date: 2007-10-02 14:54:27 +0900 (火, 02 10 2007) $ */
    /**
    * Check to see if fields are a zenkaku.
    * Fields are not checked if they are disabled.
    * <p>
    * @param form The form validation is taking place on.
    */
    function validateZenkakuString(form) {
        var bValid = true;
        var focusField = null;
        var i = 0;
        var fields = new Array();
        var formName = form.getAttributeNode("name"); 
        var focus = true;

        oInteger = eval('new ' + formName.value + '_zenkakuString()');
        for (x in oInteger) {
            var field = form[oInteger[x][0]];

            if ((field.type == 'hidden' ||
                field.type == 'text' ||
                field.type == 'textarea' ||
                field.type == 'select-one' ||
                field.type == 'radio') &&
                field.disabled == false) {

                var value = '';
                // get field's value
                if (field.type == "select-one") {
                    var si = field.selectedIndex;
                    if (si >= 0) {
                        value = field.options[si].value;
                    }
                } else {
                    value = field.value;
                }

                if (value.length > 0) {

                    for (index = 0; index < value.length; index++) {
                        valueChar = value.charAt(index);
                        if (!isZenkakuChar(valueChar, hankakuKanaList)) {
                            if (field.type != 'hidden' && focus) {
                                focusField = field;
                                focus = false;
                            }
                            fields[i++] = oInteger[x][1];
                            bValid = false;
                            break;
                        }
                    }
                }
            }
        }
        if (fields.length > 0) {
            if (focusField != null) {
                focusField.focus();
            }
            alert(fields.join('\n'));
        }
        return bValid;
    }
