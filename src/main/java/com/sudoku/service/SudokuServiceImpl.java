package com.sudoku.service;

import org.springframework.stereotype.Service;

@Service
public class SudokuServiceImpl implements SudokuService {

	@Override
	public String getBoard() {
		return "123456789";
	}

	@Override
	public String validateMoves(String moves) {
		String validatedMoves = moves + "OK";
		return validatedMoves;
	}

}
