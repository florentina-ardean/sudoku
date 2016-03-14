function getDataFromCustomString(customString) {
    var customString = getCustomString();

    var cells = new Array();
    for(var i = 0; i < customString.length; i++) {
        var charValue = customString[i];
        var charCode = charValue.charCodeAt(0);
        if (charCode == '0'.charCodeAt(0)) {
            var cell =  { value: "", type: "valid" };
            cells.push(cell);
        }
        else if (charCode >= '1'.charCodeAt(0) && charCode <= '9'.charCodeAt(0)) {
            var cell =  { value: charValue, type: "valid" };
            cells.push(cell);
        }
        if (charCode >= 'a'.charCodeAt(0) && charCode <= 'i'.charCodeAt(0)) {
            charValue = charCode + 1 - 'a'.charCodeAt(0);
            var cell =  { value: charValue, type: "fixed" };
            cells.push(cell);
        }
        else if (charCode >= 'A'.charCodeAt(0) && charCode <= 'I'.charCodeAt(0)) {
            charValue = charCode + 1 - 'A'.charCodeAt(0);
            var cell =  { value: charValue, type: "invalid" };
            cells.push(cell);
        }
    }

    var data = { "cells" : cells };
    return data;
}

function getDataFromJSON(json) {
    var data = JSON.parse(json);
    return data;
}
