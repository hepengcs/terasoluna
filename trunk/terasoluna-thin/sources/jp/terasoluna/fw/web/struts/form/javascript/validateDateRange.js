
    /*$RCSfile: validateDateRange.js,v $ $Revision: 16732 $ $Date: 2007-10-02 14:54:27 +0900 (火, 02 10 2007) $ */
    /**
    * Check to see if fields is in a valid date range.
    * Fields are not checked if they are disabled.
    * <p>
    * @param form The form validation is taking place on.
    */
    function validateDateRange(form) {
        var bValid = true;
        var focusField = null;
        var i = 0;
        var fields = new Array();
        var formName = form.getAttributeNode("name");
        var focus = true;
        var isStrict = true;

        oDate = eval('new ' + formName.value + '_dateRange()');

        for (x in oDate) {
            var field = form[oDate[x][0]];
            var value = field.value;
            var datePattern = oDate[x][2]("datePatternStrict");

            // try loose pattern
            if (datePattern == null) {
                datePattern = oDate[x][2]("datePattern");
                isStrict = false;
            }

            if ((field.type == 'hidden' ||
                field.type == 'text' ||
                field.type == 'textarea') &&
                (value.length > 0) &&
                (datePattern != null && datePattern.length > 0) &&
                field.disabled == false) {

                var MONTH = "MM";
                var DAY = "dd";
                var YEAR = "yyyy";
                var orderMonth = datePattern.indexOf(MONTH);
                var orderDay = datePattern.indexOf(DAY);
                var orderYear = datePattern.indexOf(YEAR);

                if (orderDay < orderYear && orderDay > orderMonth) {
                    var iDelim1 = orderMonth + MONTH.length;
                    var iDelim2 = orderDay + DAY.length;
                    var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                    var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);

                    if (iDelim1 == orderDay && iDelim2 == orderYear) {
                        dateRegexp = isStrict
                             ? new RegExp("^(\\d{2})(\\d{2})(\\d{4})$")
                             : new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
                    } else if (iDelim1 == orderDay) {
                        dateRegexp = isStrict
                             ? new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$")
                             : new RegExp("^(\\d{1,2})(\\d{1,2})[" + delim2 + "](\\d{4})$");
                    } else if (iDelim2 == orderYear) {
                        dateRegexp = isStrict
                             ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$")
                             : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})(\\d{4})$");
                    } else {
                        dateRegexp = isStrict
                             ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$")
                             : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{4})$");
                    }

                    var matched = dateRegexp.exec(value);
                    if(matched != null) {
                        if (!isValidDate(matched[2], matched[1], matched[3])) {
                            if (field.type != 'hidden' && focus) {
                                focusField = field;
                                focus = false;
                            }
                            fields[i++] = oDate[x][1];
                            bValid =  false;
                        } else {

                            var startDateStr = oDate[x][2]("startDate");
                            if (startDateStr != null
                                && startDateStr.length > 0) {
                                var startMatched
                                    = dateRegexp.exec(startDateStr);
                                if (startMatched != null
                                    && isValidDate(startMatched[2],
                                                   startMatched[1],
                                                   startMatched[3])) {
                                    var date = new Date(matched[2],
                                                        matched[1],
                                                        matched[3]);
                                    var startDate = new Date(startMatched[2],
                                                             startMatched[1],
                                                             startMatched[3]);
                                    if (date < startDate) {
                                        if (field.type != 'hidden' && focus) {
                                            focusField = field;
                                            focus = false;
                                        }
                                        fields[i++] = oDate[x][1];
                                        bValid = false;
                                        continue;
                                    }
                                }
                            }

                            var endDateStr = oDate[x][2]("endDate");
                            if (endDateStr != null && endDateStr.length > 0) {
                                var endMatched = dateRegexp.exec(endDateStr);
                                if (endMatched != null
                                    && isValidDate(endMatched[2],
                                                   endMatched[1],
                                                   endMatched[3])) {
                                    var date = new Date(matched[2],
                                                        matched[1],
                                                        matched[3]);
                                    var endDate = new Date(endMatched[2],
                                                           endMatched[1],
                                                           endMatched[3]);
                                    if (date > endDate) {
                                        if (field.type != 'hidden' && focus) {
                                            focusField = field;
                                            focus = false;
                                        }
                                        fields[i++] = oDate[x][1];
                                        bValid = false;
                                        continue;
                                    }
                                }
                            }
                        }
                    } else {
                        if (field.type != 'hidden' && focus) {
                            focusField = field;
                            focus = false;
                        }
                        fields[i++] = oDate[x][1];
                        bValid =  false;
                    }

                } else if (orderMonth < orderYear && orderMonth > orderDay) {
                    var iDelim1 = orderDay + DAY.length;
                    var iDelim2 = orderMonth + MONTH.length;
                    var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                    var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);

                    if (iDelim1 == orderMonth && iDelim2 == orderYear) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{2})(\\d{2})(\\d{4})$")
                            : new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
                    } else if (iDelim1 == orderMonth) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$")
                            : new RegExp("^(\\d{1,2})(\\d{1,2})[" + delim2 + "](\\d{4})$");
                    } else if (iDelim2 == orderYear) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$")
                            : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})(\\d{4})$");
                    } else {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$")
                            : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{4})$");
                    }

                    var matched = dateRegexp.exec(value);
                    if(matched != null) {
                        if (!isValidDate(matched[1], matched[2], matched[3])) {
                            if (field.type != 'hidden' && focus) {
                                focusField = field;
                                focus = false;
                            }
                            fields[i++] = oDate[x][1];
                            bValid =  false;
                        } else {

                            var startDateStr = oDate[x][2]("startDate");
                            if (startDateStr != null
                                && startDateStr.length > 0) {
                                var startMatched
                                    = dateRegexp.exec(startDateStr);
                                if (startMatched != null
                                    && isValidDate(startMatched[1],
                                                   startMatched[2],
                                                   startMatched[3])) {
                                    var date = new Date(matched[1],
                                                        matched[2],
                                                        matched[3]);
                                    var startDate = new Date(startMatched[1],
                                                             startMatched[2],
                                                             startMatched[3]);
                                    if (date < startDate) {
                                        if (field.type != 'hidden' && focus) {
                                            focusField = field;
                                            focus = false;
                                        }
                                        fields[i++] = oDate[x][1];
                                        bValid = false;
                                        continue;
                                    }
                                }
                            }

                            var endDateStr = oDate[x][2]("endDate");
                            if (endDateStr != null && endDateStr.length > 0) {
                                var endMatched = dateRegexp.exec(endDateStr);
                                if (endMatched != null
                                    && isValidDate(endMatched[1],
                                                   endMatched[2],
                                                   endMatched[3])) {
                                    var date = new Date(matched[1],
                                                        matched[2],
                                                        matched[3]);
                                    var endDate = new Date(endMatched[1],
                                                           endMatched[2],
                                                           endMatched[3]);
                                    if (date > endDate) {
                                        if (field.type != 'hidden' && focus) {
                                            focusField = field;
                                            focus = false;
                                        }
                                        fields[i++] = oDate[x][1];
                                        bValid = false;
                                        continue;
                                    }
                                }
                            }
                        }
                    } else {
                        if (field.type != 'hidden' && focus) {
                            focusField = field;
                            focus = false;
                        }
                        fields[i++] = oDate[x][1];
                        bValid =  false;
                    }

                } else if (orderMonth > orderYear && orderMonth < orderDay) {
                    var iDelim1 = orderYear + YEAR.length;
                    var iDelim2 = orderMonth + MONTH.length;
                    var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                    var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);

                    if (iDelim1 == orderMonth && iDelim2 == orderDay) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})(\\d{2})(\\d{2})$")
                            : new RegExp("^(\\d{4})(\\d{1,2})(\\d{1,2})$");
                    } else if (iDelim1 == orderMonth) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$")
                            : new RegExp("^(\\d{4})(\\d{1,2})[" + delim2 + "](\\d{1,2})$");
                    } else if (iDelim2 == orderDay) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$")
                            : new RegExp("^(\\d{4})[" + delim1 + "](\\d{1,2})(\\d{1,2})$");
                    } else {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$")
                            : new RegExp("^(\\d{4})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{1,2})$");
                    }

                    var matched = dateRegexp.exec(value);
                    if(matched != null) {
                        if (!isValidDate(matched[3], matched[2], matched[1])) {
                            if (field.type != 'hidden' && focus) {
                                focusField = field;
                                focus = false;
                            }
                            fields[i++] = oDate[x][1];
                            bValid =  false;
                        } else {

                            var startDateStr = oDate[x][2]("startDate");
                            if (startDateStr != null
                                && startDateStr.length > 0) {
                                var startMatched
                                    = dateRegexp.exec(startDateStr);
                                if (startMatched != null
                                    && isValidDate(startMatched[3],
                                                   startMatched[2],
                                                   startMatched[1])) {
                                    var date = new Date(matched[1],
                                                        matched[2],
                                                        matched[3]);
                                    var startDate = new Date(startMatched[1],
                                                             startMatched[2],
                                                             startMatched[3]);
                                    if (date < startDate) {
                                        if (field.type != 'hidden' && focus) {
                                            focusField = field;
                                            focus = false;
                                        }
                                        fields[i++] = oDate[x][1];
                                        bValid = false;
                                        continue;
                                    }
                                }
                            }

                            var endDateStr = oDate[x][2]("endDate");
                            if (endDateStr != null && endDateStr.length > 0) {
                                var endMatched = dateRegexp.exec(endDateStr);
                                if (endMatched != null
                                    && isValidDate(endMatched[3],
                                                   endMatched[2],
                                                   endMatched[1])) {
                                    var date = new Date(matched[1],
                                                        matched[2],
                                                        matched[3]);
                                    var endDate = new Date(endMatched[1],
                                                           endMatched[2],
                                                           endMatched[3]);
                                    if (date > endDate) {
                                        if (field.type != 'hidden' && focus) {
                                            focusField = field;
                                            focus = false;
                                        }
                                        fields[i++] = oDate[x][1];
                                        bValid = false;
                                        continue;
                                    }
                                }
                            }
                        }
                    } else {
                        if (field.type != 'hidden' && focus) {
                            focusField = field;
                            focus = false;
                        }
                        fields[i++] = oDate[x][1];
                        bValid =  false;
                    }

                } else {
                    if (field.type != 'hidden' && focus) {
                        focusField = field;
                        focus = false;
                    }
                    fields[i++] = oDate[x][1];
                    bValid =  false;
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

    function isValidDate(day, month, year) {
	    if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) &&
            (day == 31)) {
            return false;
        }
        if (month == 2) {
            var leap = (year % 4 == 0 &&
               (year % 100 != 0 || year % 400 == 0));
            if (day>29 || (day == 29 && !leap)) {
                return false;
            }
        }
        return true;
    }
