package com.sudoku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sudoku.service.SudokuService;

@RestController
public class SudokuController {
	@Autowired
	SudokuService sudokuService;

	@RequestMapping("/board")
	public String getBoard() {
		String board = sudokuService.getBoard();
		return board;
	}

	@RequestMapping(value = "/moves", method = RequestMethod.POST)
	public String verifyMoves(@RequestBody String data) {
		String validatedMoves = sudokuService.updateBoard(data);
		
		return validatedMoves;
	}
}
