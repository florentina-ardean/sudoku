$(function($) {

    var makeSudoku, makeSudokuRow, makeSudokuCell, makeNoteBox,
        isValidSudokuChar, getkey, checkSetAndMark, clearProblem, sudokuCheck,
        getRowFor, getColumnFor, getSquareFor, getSquareBase, getCell,
        populateSudoku, populateSudokuFromObject;

    makeSudoku = window.makeSudoku = function(id, puzzle) {
        // Creates a sudoku puzzle within the element with the given id, replacing any existing content.  Should probably be a div, or suchlike.
        var base = $('#'+id).empty();
        var t = $('<table align="center" class="sudoku"></table>').appendTo(base);

        for (var i = 1; i < 10; i++)
        {
            t.append(makeSudokuRow(id, i));
        }

//        $('<button>Check for errors</button>').click(function(e) {
//            e.preventDefault();
//            sudokuCheck(id);
//        }).appendTo(base);
    }

    makeSudokuRow = function(id, y) {
        var row = $('<tr class="sudokurow' + y + '"></tr>');
        for (var ii=1; ii < 10; ii++) {
            row.append(makeSudokuCell(id, ii, y));
        }
        return row;
    }

    makeSudokuCell = function(id, x, y) {
        var str1 = '<td class="sudokucolumn' + x + ' sudokusquare' + getSquareBase(y) + getSquareBase(x) + '"></td>';
        var str2 = '<input class="sudokucell' + y + x + '" maxlength="1" value="">';
        return $(str1).append($(str2).keypress(isValidSudokuChar));
    }

    isValidSudokuChar = function(e) {
        var key = e.which;
        var pattern = "123456789".toLowerCase();
        var keychar;
        if (key == null) {
            return;
        }
        keychar = String.fromCharCode(key).toLowerCase();
        // check pattern
        if (pattern.indexOf(keychar) != -1) {
            return;
        }
        // control keys
        if ( key==null || key==0 || key==8 || key==9 || key==13 || key==27 ) {
            return;
        }

        e.preventDefault();
    }

    sudokuCheck = function(id) {
        $('#'+id+' .problem').removeClass('problem');

        // for(var i = 1; i < 10; i++) {
        //     checkSetAndMark(getRowFor(id, 1, i));
        //     checkSetAndMark(getColumnFor(id, i, 1));
        //     checkSetAndMark(getSquareFor(id,
        //                                  1 + ((i - 1) % 3) * 3,
        //                                  getSquareBase(i)));
        // }

        // var boardStr = getBoardMoves(id);
        // var txtArray = '';
        // for (var i = 9; i < 81; i += 9) {
        //     txtArray += boardStr.substring(i - 9, i) + '\n';
        // }
        // alert(txtArray);

        // alert(getBoardMovesJSON(id));
    }

    checkSetAndMark = function(a) {
        var alreadySeen = {};
        a.each(function(i, cell) {
            cell = $(cell);
            var val = cell.val();
            if(val == "") { return; }
            if(alreadySeen[val]) {
                a.filter('[value="' + val + '"]').closest('td').addClass('problem');
            }
            alreadySeen[val] = true;
        });
    }

    getRowFor = function(id, x, y) {
        return $('#' + id + ' .sudokurow' + y + ' input');
    }

    getColumnFor = function(id, x, y) {
        return $('#' + id + ' .sudokucolumn' + x + ' input');
    }

    getSquareFor = function(id, x, y) {
        return $('#' + id + ' .sudokusquare' + getSquareBase(y) + getSquareBase(x) + ' input');
    }

    getCell = function(id, x, y) {
        return $('#' + id + ' input.sudokucell' + y + x);
    }

    getSquareBase = function(i) {
        if (i < 4) { return 1; }
        if (i < 7) { return 4; }
        if (i < 10) { return 7; }
    }

    populateSudoku = window.populateSudoku = function(id, puzzle) {
        for(var i = 0; i < puzzle.length; i++) {
            var x = (i % 9) + 1;
            var y = (Math.floor(i / 9) % 9) + 1;
            var cell = getCell(id, x, y);
            var v;

            var charCode = puzzle[i].charCodeAt(0);
            if (charCode == '0'.charCodeAt(0)) {
                v = '';
                cell.attr('disabled', false).addClass('editable');
                cell.val(v);
            }
            else if (charCode >= '1'.charCodeAt(0) && charCode <= '9'.charCodeAt(0)) {
                v = puzzle[i];
                cell.attr('disabled', false).addClass('editable');
                cell.val(v);
            }
            if (charCode >= 'a'.charCodeAt(0) && charCode <= 'i'.charCodeAt(0)) {
                v = charCode + 1 - 'a'.charCodeAt(0);
                cell.attr('disabled', true).addClass('provided');
                cell.val(v);
            }
            else if (charCode >= 'A'.charCodeAt(0) && charCode <= 'I'.charCodeAt(0)) {
                v = charCode + 1 - 'A'.charCodeAt(0);
                cell.attr('disabled', false).addClass('error');
                cell.closest('td').addClass('problem');
                cell.val(v);
            }
        }
    }

    populateSudokuFromObject = window.populateSudokuFromObject = function(id, cellsObj) {
        var cells = cellsObj.cells;
        for (var i = 0; i < cells.length; i++) {
            var x = (i % 9) + 1;
            var y = (Math.floor(i / 9) % 9) + 1;
            var cell = getCell(id, x, y);

            var value = cells[i].value;
            var type = cells[i].type;

            switch (type) {
                case 'fixed':
                    cell.attr('disabled', true).addClass('provided');
                    cell.val(value);
                    break;
                case 'valid':
                    cell.attr('disabled', false).addClass('editable');
                    if (value != 0)
                    	cell.val(value);
                    break;
                case 'invalid':
                    cell.attr('disabled', false).addClass('error');
                    cell.closest('td').addClass('problem');
                    cell.val(value);
                    break;
            }
        }
        
        var solved = cellsObj.solved;
        if (solved == true)
        	alert('Congrats! You solved it! You are smart!');
    }

    getBoardMoves = function(id) {
        var value = '';
        for (var i = 1; i <= 9; i++) {
            for (var j = 1; j <= 9; j++) {
                var cell = getCell(id, j, i);
                var cellValue = cell.val();
                var cellClass = cell.attr('class');

                if (cellValue == '') {
                    value += '0';
                }
                else if (cellClass.indexOf('editable') != -1) {
                    value += cellValue;
                }
                else if (cellClass.indexOf('provided') != -1) {
                    var v = 'a'.charCodeAt(0) + parseInt(cellValue) - 1;
                    value += String.fromCharCode(v);
                }
                else if (cellClass.indexOf('error') != -1) {
                    value += cellValue;
                }
            }
        }

        return value;
    }

    getBoardMovesJSON = function(id) {
        var objData = new Array();

        for (var i = 1; i <= 9; i++) {
            for (var j = 1; j <= 9; j++) {
                var cell = getCell(id, j, i);
                var cellValue = cell.val();
                var cellClass = cell.attr('class');

                if (cellClass.indexOf('provided') != -1) {
                    var objCell = { "value": cellValue, "type": "fixed" };
                    objData.push(objCell);
                }
                else if (cellValue == '' ||
                         cellClass.indexOf('editable') != -1 ||
                         cellClass.indexOf('error') != -1) {
                     var objCell = { "value": cellValue, "type": "valid" };
                     objData.push(objCell);
                }
            }
        }

        var obj = { "cells": objData };
        
        return JSON.stringify(obj);
    }
});
