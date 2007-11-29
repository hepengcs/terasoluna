
    /*$RCSfile$ $Revision: 16732 $ $Date: 2007-10-02 14:54:27 +0900 (火, 02 10 2007) $ */
    /**
    * Check to see if fields are a valid number.
    * Fields are not checked if they are disabled.
    * <p>
    * @param form The form validation is taking place on.
    */
    function validateNumber(form) {
        var bValid = true;
        var focusField = null;
        var i = 0;
        var fields = new Array();
        var formName = form.getAttributeNode("name");
        var focus = true;

        oInteger = eval('new ' + formName.value + '_number()');
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

                    if(isNaN(value)) {
                        if (field.type != 'hidden' && focus) {
                            focusField = field;
                            focus = false;
                        }
                        fields[i++] = oInteger[x][1];
                        bValid = false;
                    } else {
                        var intvalStr = getIntValue(value);
                        var scaleval = getScaleValue(value);
                        var integerValid = true;
                        tmpIntegerLength = oInteger[x][2]("integerLength");
                        if (tmpIntegerLength != null) {
                            var integerLength = parseInt(tmpIntegerLength);
                            if (oInteger[x][2]("isAccordedInteger") != null &&
                                    oInteger[x][2]("isAccordedInteger") == 'true') {
                                if (intvalStr.length != integerLength) {
                                    if (field.type != 'hidden' && focus) {
                                        focusField = field;
                                        focus = false;
                                    }
                                    fields[i++] = oInteger[x][1];
                                    bValid = false;
                                    integerValid = false;
                                }
                            } else {
                                if (intvalStr.length > integerLength) {
                                    if (field.type != 'hidden' && focus) {
                                        focusField = field;
                                        focus = false;
                                    }
                                    fields[i++] = oInteger[x][1];
                                    bValid = false;
                                    integerValid = false;
                                }
                            }
                        }

                        tmpScaleLength = oInteger[x][2]("scale");
                        if (tmpScaleLength != null && integerValid) {
                            var scaleLength = parseInt(tmpScaleLength);
                            if (oInteger[x][2]("isAccordedScale") != null &&
                                    oInteger[x][2]("isAccordedScale") == 'true') {
                                if (scaleval.length != scaleLength) {
                                    if (field.type != 'hidden' && focus) {
                                        focusField = field;
                                        focus = false;
                                    }
                                    fields[i++] = oInteger[x][1];
                                    bValid = false;
                                }
                            } else {
                                if (scaleval.length > scaleLength) {
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

    /*
     *
     */
    function getIntValue(value) {
        var ret;
        if (value > 0) {
            ret = Math.floor(value);
        } else {
            ret = Math.ceil(value);
        }
        return new String(ret);
    }

    /*
     *
     */
    function getScaleValue(value) {
        var ret = "";
        value = new String(value);
        if (value.indexOf(".") >= 0) {
           ret = String(value).split(".")[1];
        }
        return ret;

    }
    