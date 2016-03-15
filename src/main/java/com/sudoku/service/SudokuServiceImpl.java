package com.sudoku.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudoku.model.Board;
import com.sudoku.model.Cell;

/**
 * Validates sudoku board 9x9
 * Marks elements:
 * duplicates 1-9 become A-I
 * fixed initial values 1-9 become a-i
 * validated new values 1-9 become 1-9
 * 
 * @author florentina.ardean
 *
 */
@Service
public class SudokuServiceImpl implements SudokuService {
	Cell[][] updatedBoard = new Cell[ROWS][ROWS];

	private static final int ROWS = 9;
	private static final int CELL_SIZE = 3;

	/**
	 * Returns a string of length 81
	 */
	@Override
	public String getBoard() {
		return "0000ig000" + 
			   "00000a00d" + 
			   "00i00000h" + 
			   "00dec0000" + 
			   "0e00hi0a0" + 
			   "00b0g0h00" + 
			   "e00000ag0" + 
			   "0f000hd0c" + 
			   "bd000000i";
	}
	
	/**
	 * Returns a json of 
	 * 81 Cells and boolean solved
	 */
	@Override
	public Board getBoardFromJson() {
		Board board = getBoardFromFile("/data/SudokuBoard.data");
		return board;
	}
	
	/**
	 * Returns a json of 
	 * 81 Cells and boolean solved
	 */
	public Board getBoardFromJson(String fileName) {
		Board board = getBoardFromFile(fileName);
		return board;
	}
	
	/**
	 * Validates a board of sudoku
	 * 
	 * @param moves
	 * @return
	 */
	@Override
	public String updateBoard(String moves) { 
		//char[][] newBoard = convertToBidimensional(moves.toCharArray(), ROWS, ROWS);
		String modBoard = moves;
		
		Cell[][] board = convertToBoardOfCells(moves.toCharArray(), ROWS, ROWS);
		
		if (board != null) {
			
			updatedBoard = initBoard(board);
			
			// check duplicates on row
			checkRows(updatedBoard);
			
	
			// check duplicates on column
			checkCols(updatedBoard);
			
	
			// check duplicates on cell
			checkCells(updatedBoard);
			
			//convert bidimensional array to String
			modBoard = getStringFromCellArray(updatedBoard);
		}
		
		return modBoard;
	}
	
	/**
	 * Validates a board of sudoku
	 * 
	 * @param board
	 * @return
	 */
	@Override
	public Board updateBoard(Board board) {
		Board modBoard = board;
		
		if (modBoard != null) {
			
			Cell[][] cells = convertToBoardOfCells(board, ROWS, ROWS);
			
			if (cells != null) {
				updatedBoard = initBoard(cells);
				
				// check duplicates on row
				checkRows(updatedBoard);
				
		
				// check duplicates on column
				checkCols(updatedBoard);
				
		
				// check duplicates on cell
				checkCells(updatedBoard);
				
				//convert bidimensional array to Board of Cells 
				modBoard = getBoardFromBidimensionalCellArray(updatedBoard);
			}
		}
		
		return modBoard;
	}
	
	/**
	 * Convert array of chars to board of Cells
	 * 
	 * @param array
	 * @param rows
	 * @param cols
	 * @return
	 */
	private Cell[][] convertToBoardOfCells(char[] array, final int rows, final int cols) {
		if (array.length != (rows * cols)) {
			return null;
		}
		
		Cell[][] cells = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				char value = array[i * cols + j];
				int number = decodeValue(value); 
				
				Cell cell = new Cell();
				cell.setValue(number);
				cell.setType(isFixed(value) ? Cell.Type.fixed : Cell.Type.valid);
				
				cells[i][j] = cell;
			}
		}
		
		return cells;
	}
	
	/**
	 * Returns true if the value is from initial board 
	 * @param value
	 * @return
	 */
	private boolean isFixed(char value) {
		boolean isFixed = false;
		if (value >= 'a' && value <= 'i') {
			isFixed = true;
		}
		return isFixed;
	}
	
	/**
	 * Copy board of Cells 
	 */
	private Cell[][] initBoard(Cell[][] board) {
		Cell[][] myBoard = new Cell[ROWS][ROWS];
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < ROWS; j++) {
				myBoard[i][j] = new Cell(board[i][j]);
			}
		}
		
		return myBoard;
	}
	
	/**
	 * Check duplicates only on rows
	 * 
	 * @param board
	 */
	private void checkRows(Cell[][] board) {

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < ROWS - 1; col++) {
				int value = board[row][col].getValue();
				if (value > 0) {
					for (int i = col + 1; i < ROWS; i++) {
						int currentValue = board[row][i].getValue();
						if (value == currentValue) {
							// mark as invalid value (from A to I)
							markValueAsInvalid(updatedBoard, row, col);
							markValueAsInvalid(updatedBoard, row, i);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Check duplicates only on colums
	 * 
	 * @param board
	 */
	private void checkCols(Cell[][] board) {
		for (int col = 0; col < ROWS; col++) {
			for (int row = 0; row < ROWS - 1; row++) {
				int value = board[row][col].getValue();
				if (value > 0) {
					for (int i = row + 1; i < ROWS; i++) {
						int currentValue = board[i][col].getValue();
						if (value == currentValue) {
							// mark as invalid value (from A to I)
							markValueAsInvalid(updatedBoard, row, col);
							markValueAsInvalid(updatedBoard, i, col);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Check duplicates in all board cells
	 * 
	 * @param board
	 */
	private void checkCells(Cell[][] board) {
		for (int row = 0; row < ROWS; row += CELL_SIZE) {
			for (int col = 0; col < ROWS; col += CELL_SIZE) {
				checkCell(board, row, col);
			}
		}
	}

	/** 
	 * Check duplicates in a cell
	 * 
	 * @param board
	 * @param startIndexRow
	 * @param startIndexCol
	 */
	private void checkCell(Cell[][] board, int startIndexRow, int startIndexCol) {
		for (int row = startIndexRow; row < startIndexRow + CELL_SIZE; row++) {
			for (int col = startIndexCol; col < startIndexCol + CELL_SIZE; col++) {
				int value = board[row][col].getValue();
				if (value > 0) {
					for (int row2 = row; row2 < startIndexRow + CELL_SIZE; row2++) {
						for (int col2 = startIndexCol; col2 < startIndexCol + CELL_SIZE; col2++) {
							int currentValue = board[row2][col2].getValue();
							if ((row == row2 && col == col2) || currentValue == 0) {
								continue;
							} else {
								if (value == currentValue) {
									markValueAsInvalid(updatedBoard, row, col);
									markValueAsInvalid(updatedBoard, row2, col2);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Mark cell as invalid Cell.Type
	 * 
	 * @param board
	 * @param row
	 * @param col
	 */
	private void markValueAsInvalid(Cell[][] board, int row, int col) {
		
		//if value is not fixed(from initial board) mark it as invalid
		Cell cell = board[row][col];
		if (!cell.getType().equals(Cell.Type.fixed)) {
			cell.setType(Cell.Type.invalid);
		}
	}

	
	/**
	 * Convert char to number
	 * 0-9, a-i become numbers 0-9
	 * other values become 0
	 * 
	 * @param c
	 * @return
	 */
	private int decodeValue(char c) {
		if (c >= '0' && c <= '9') {
			return (c - '0');
		} else if (c >= 'a' && c <= 'i') {
			return (c - 'a' + 1);
		} else {
			return 0;
		}
	}
	
	/**
	 * Convert number to char
	 * 
	 * duplicates 1-9 become A-I
	 * values from initial board 1-9 become a-i
	 * validated moves 1-9 become 1-9
	 * 
	 * @param cell
	 * @return
	 */
	private char encodeValue(Cell cell) {
		int value = cell.getValue();
		char encodedValue = '0';
		
		if (cell.getType().equals(Cell.Type.invalid)) {
			//1-9 become A-I
			encodedValue = (char) (value + 'A' - 1);
		} else if (cell.getType().equals(Cell.Type.fixed)) {
			//1-9 become a-i
			encodedValue = (char) (value + 'a' - 1);
		} else {
			encodedValue = (char) (value + '0');
		}
		
		return encodedValue;
	}
	
	/**
	 * Convert board of Cells to encoded string
	 * 
	 * @param board
	 * @return
	 */
	private String getStringFromCellArray(Cell[][] board) {
		String boardValue = "";
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < ROWS; col++) {
				char encodedValue = encodeValue(board[row][col]);
				boardValue += encodedValue;
			}
		}
		return boardValue;
	}
	
	/**
	 * Get data from json file and convert it to Board
	 * 
	 * @param jsonUrl
	 * @return
	 */
	private Board getBoardFromFile(String fileName) {
		Board board = null;
		
		Resource resource = new ClassPathResource(fileName);
		
		try {
			File file = resource.getFile();
			
			// JSON from file to Object
			ObjectMapper mapper = new ObjectMapper();
			board = mapper.readValue(file, Board.class);
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		} catch (IOException e) {
			System.out.println("IO exception when parsing file.");
		} 

		return board;
	}
	
	/**
	 * Convert array of chars to board of Cells
	 * 
	 * @param array
	 * @param rows
	 * @param cols
	 * @return
	 */
	private Cell[][] convertToBoardOfCells(Board board, final int rows, final int cols) {
		if (board.getCells().size() != (rows * cols)) {
			return null;
		}
		
		Cell[][] cells = new Cell[rows][cols];

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < ROWS; j++) {
				int index = i * ROWS + j;
				Cell cell = new Cell();
				cell.setValue(board.getCells().get(index).getValue());
				cell.setType(board.getCells().get(index).getType());

				cells[i][j] = cell;
			}
		}

		return cells;
	}
	
	/**
	 * Covert bidimensional Cell array to Board Check if Board is Completed
	 * 
	 * @param cells
	 * @return
	 */
	private Board getBoardFromBidimensionalCellArray(Cell[][] cells) {
		Board board = new Board();
		board.setCells(new ArrayList<Cell>(ROWS * ROWS));

		boolean solved = true;
		ArrayList<Cell> modCells = board.getCells();

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < ROWS; j++) {
				int index = i * ROWS + j;

				Cell myCell = new Cell();
				myCell.setValue(cells[i][j].getValue());
				myCell.setType(cells[i][j].getType());

				modCells.add(index, myCell);

				boolean cellValid = myCell.getValue() != 0 && (myCell.getType() != Cell.Type.invalid);
				solved = solved && cellValid;
			}
		}
		
		board.setSolved(solved);
		
		return board;
	}

}
