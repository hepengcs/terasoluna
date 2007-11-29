
    /*$Id: validateAlphaNumericString.js 16732 2007-10-02 05:54:27Z btitouhrk $ */
    /**
    * Check to see if fields are a alphabet or numeral.
    * Fields are not checked if they are disabled.
    * <p>
    * @param form The form validation is taking place on.
    */
    function validateAlphaNumericString(form) {
        var bValid = true;
        var focusField = null;
        var i = 0;
        var fields = new Array();
        var formName = form.getAttributeNode("name");
        var focus = true;

        oInteger = eval('new ' + formName.value + '_alphaNumericString()');
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
                    if(!value.match("^[0-9A-Za-z]*$")) {
                        if (field.type != 'hidden' && focus) {
                            focusField = field;
                            focus = false;
                        }
                        fields[i++] = oInteger[x][1];
                        bValid = false;
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
