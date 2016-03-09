package com.sudoku.service;

public interface SudokuService {

	String getBoard();

	String validateMoves(String moves);
}