package com.sudoku.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SudokuServiceImpl.class)
//@WebAppConfiguration
public class SudokuServiceImplTest {
	@Autowired
	SudokuService sudokuService;
	
	@Test
	public void checkValidBoard() {
		//valid initial board
		String testBoard = sudokuService.getBoard();

		String newBoard = sudokuService.updateBoard(testBoard);
		
		//result should be the same as the initial entry
		String expectedBoard = testBoard;
		
		assertEquals(expectedBoard, newBoard);
	}
	
	@Test
	public void checkNonValidBoard() {
		//invalid initial board
		//duplicate on row & column & cell - 4
		String testBoard = "4000ig400" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "D000igD00" + 
							   "00000a00d" + 
							   "00i00000h" + 
							   "00dec0000" + 
							   "0e00hi0a0" + 
							   "00b0g0h00" + 
							   "e00000ag0" + 
							   "0f000hd0c" + 
							   "bd000000i";
		
		assertEquals(expectedBoard, newBoard);
	}
	
	@Test
	public void checkNonValidBoardWithInvalidMarkedValues() {
		//invalid initial board
		//duplicate on row & column & cell - D
		String testBoard = "D000igD00" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "D000igD00" + 
							   "00000a00d" + 
							   "00i00000h" + 
							   "00dec0000" + 
							   "0e00hi0a0" + 
							   "00b0g0h00" + 
							   "e00000ag0" + 
							   "0f000hd0c" + 
							   "bd000000i";
		
		assertEquals(expectedBoard, newBoard);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkNonValidBoardOtherCharactersThanConventionLessThan0() {
		//invalid initial board
		//duplicate on row & column & cell - /
		String testBoard = "4000ig400" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec000/" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkNonValidBoardOtherCharactersThanCOnventionMoreThan9() {
		//invalid initial board
		//duplicate on row & column & cell - ?
		String testBoard = "4000ig400" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec000?" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkNonValidBoardOtherCharactersThanConventionLessThana() {
		//invalid initial board
		//duplicate on row & column & cell - ^
		String testBoard = "4000ig400" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec000^" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkNonValidBoardOtherCharactersThanCOnventionMoreThani() {
		//invalid initial board
		//duplicate on row & column & cell - m
		String testBoard = "m000ig400" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec000m" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
	}
	
	@Test
	public void checkNonValidBoardDuplicateFxedValue() {
		//invalid initial board
		//duplicate on row & column & cell
		String testBoard = "0000ig000" + 
						   "50000a00d" + 
						   "00i00000h" + 
						   "00dec0003" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "0000ig000" + 
							   "E0000a00d" + 
							   "00i00000h" + 
							   "00dec000C" + 
							   "0e00hi0a0" + 
							   "00b0g0h00" + 
							   "e00000ag0" + 
							   "0f000hd0c" + 
							   "bd000000i";
			
		assertEquals(expectedBoard, newBoard);

	}
	
	
}
