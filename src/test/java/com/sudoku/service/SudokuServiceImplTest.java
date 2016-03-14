package com.sudoku.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sudoku.model.Board;
import com.sudoku.model.Cell;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SudokuServiceImpl.class)
public class SudokuServiceImplTest {
	@Autowired
	SudokuService sudokuService;
	
	@Test
	public void checkValidBoardWithNoChanges() {
		//same as initial board
		String testBoard = "0000ig000" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		//result should be the same as the initial entry
		assertEquals(testBoard, newBoard);
	}
	
	@Test
	public void checkValidBoardWithChanges() {
		String testBoard = "1300ig000" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "1300ig000" + 
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
	public void checkInvalidBoardChangeWithFixedValueSameRow() {
		//duplicate on row
		String testBoard = "0000ig000" + 
						   "40000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "0000ig000" + 
							   "D0000a00d" + 
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
	public void checkInvalidBoardChangeWithNonFixedValueSameColumn() {
		String testBoard = "5000ig000" + 
						   "00000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "E000ig000" + 
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
	public void checkInvalidBoardChangeWithNonFixedValueSameCell() {
		String testBoard = "0300ig000" + 
						   "30000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "0C00ig000" + 
							   "C0000a00d" + 
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
	public void checkInvalidBoardChangeWithNonFixedInvalidValues() {
		String testBoard = "m3.0ig=Zz" + 
						   "3E000a00d" + 
						   "00i00000h" + 
						   "00dec0000" + 
						   "0e00hi0a0" + 
						   "00b0g0h00" + 
						   "e00000ag0" + 
						   "0f000hd0c" + 
						   "bd000000i";

		String newBoard = sudokuService.updateBoard(testBoard);
		
		String expectedBoard = "0C00ig000" + 
							   "C0000a00d" + 
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
	public void checkValidBoard3x3() {
		String testBoard = "000" + 
						   "0a0" + 
						   "00i";
		
		String newBoard = sudokuService.updateBoard(testBoard);
		
		assertEquals(testBoard, newBoard);
	}
	
	@Test
	public void checkGetValidBoard() {
		//same as initial board
		String testBoard = sudokuService.getBoard();
		
		String newBoard = sudokuService.updateBoard(testBoard);
		
		//result should be the same as the initial entry
		assertEquals(testBoard, newBoard);
	}
	
	@Test
	public void checkGetValidBoardJson() {
		//same as initial board
		Board testBoard = sudokuService.getBoardFromJson();
		
		Board newBoard = sudokuService.updateBoard(testBoard);
		
		Board expectedBoard = testBoard;

		//result should be the same as the initial entry
		assertEquals(expectedBoard.getCells(), newBoard.getCells());
	}
	
	@Test
	public void checkGetValidBoardJson3x3() {
		SudokuServiceImpl sudokuServiceImpl = (SudokuServiceImpl) sudokuService;
		
		Board testBoard = sudokuServiceImpl.getBoardFromJson("/data/SudokuBoard3x3.data");
		
		Board newBoard = sudokuServiceImpl.updateBoard(testBoard);
		
		assertEquals(testBoard.getCells(), newBoard.getCells());
	}
	
	@Test
	public void checkGetValidBoardJsonSolved() {
		SudokuServiceImpl sudokuServiceImpl = (SudokuServiceImpl) sudokuService;
		
		Board testBoard = sudokuServiceImpl.getBoardFromJson("/data/SudokuBoardSolution.data");
		
		Board newBoard = sudokuServiceImpl.updateBoard(testBoard);

		assertTrue(newBoard.isSolved());
	}
	
	@Test
	public void checkGetValidBoardJsonNotSolved() {
		SudokuServiceImpl sudokuServiceImpl = (SudokuServiceImpl) sudokuService;
		
		//load initial board
		Board testBoard = sudokuServiceImpl.getBoardFromJson("/data/SudokuBoard.data");
		
		//put value 2 on the first element of sudoku board
		Cell cell = new Cell();
		cell.setValue(2);
		cell.setType(Cell.Type.valid);
		testBoard.getCells().set(0, cell);
		
		//validate board
		Board newBoard = sudokuServiceImpl.updateBoard(testBoard);

		assertEquals(false, newBoard.isSolved());
	}
	
	@Test
	public void checkGetBoardJsonNotParsed() {
		SudokuServiceImpl sudokuServiceImpl = (SudokuServiceImpl) sudokuService;
		
		//load initial board
		Board testBoard = sudokuServiceImpl.getBoardFromJson("/data/SudokuBoardIncorrect.data");
		
		//validate board
		Board newBoard = sudokuServiceImpl.updateBoard(testBoard);

		assertNull(newBoard);
	}
	
	@Test
	public void checkGetBoardJsonNotLoaded() {
		SudokuServiceImpl sudokuServiceImpl = (SudokuServiceImpl) sudokuService;
		
		//load initial board
		Board testBoard = sudokuServiceImpl.getBoardFromJson("/data/SudokuBoardNonExisting.data");
		
		//validate board
		Board newBoard = sudokuServiceImpl.updateBoard(testBoard);

		assertNull(newBoard);
	}
	
	
	
}
