package com.sudoku.service;

import com.sudoku.model.Board;

public interface SudokuService {

	String getBoard();
	
	Board getBoardFromJson();
	
	String updateBoard(String moves);
	
	Board updateBoard(Board board);
}