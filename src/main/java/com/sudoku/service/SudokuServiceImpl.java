package com.sudoku.service;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

@Service
public class SudokuServiceImpl implements SudokuService {
	char[][] modifiedBoard = new char[ROWS][ROWS];

	private static final int ROWS = 9;
	private static final int CELL_SIZE = 3;

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

	@Override
	public String updateBoard(String moves) {
		char[][] newBoard = convertToBidimensional(moves.toCharArray(), ROWS, ROWS);
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < ROWS; j++) {
				modifiedBoard[i][j] = newBoard[i][j];
				//if invalid code received convert to number 1-9
				//mark as invalid should be done on server by sudoku service
				if (modifiedBoard[i][j] >= 'A' && modifiedBoard[i][j] <= 'I')
					// reset error code to int
					modifiedBoard[i][j] = getCharForValue(modifiedBoard[i][j] - 'A' + 1);
			}

		// check duplicates on row
		checkRows(newBoard);

		// check duplicates on column
		checkCols(newBoard);

		// check duplicates on cell
		checkCell(newBoard);

		System.out.println("OK");

		String modBoard = getStringFromArray(modifiedBoard);

		return modBoard;
	}

	public String getStringFromArray(char[][] arr) {
		String boardValue = "";
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < ROWS; col++) {
				boardValue += arr[row][col];
			}
		}
		return boardValue;
	}

	private void checkCell(char[][] newBoard) {
		for (int row = 0; row < ROWS; row += CELL_SIZE) {
			for (int col = 0; col < ROWS; col += CELL_SIZE) {
				checkCell(newBoard, row, col);
			}
		}
	}

	public void checkCell(char[][] newBoard, int startIndexRow, int startIndexCol) {
		Set<Integer> set = new TreeSet<Integer>();
		for (int row = startIndexRow; row < startIndexRow + CELL_SIZE; row += CELL_SIZE) {
			for (int col = startIndexCol; col < startIndexCol + CELL_SIZE; col += CELL_SIZE) {
				int value = getIntFromChar(newBoard[row][col]);
				if (value > 0) {
					//works if check duplicates on row and check duplicates on column are done before
					//else start from row2 from row and col2 from col
					for (int row2 = row + 1; row2 < startIndexRow + CELL_SIZE; row2++) {
						for (int col2 = col + 1; col2 < startIndexCol + CELL_SIZE; col2++) {
							if ((row == row2 && col == col2) || getIntFromChar(newBoard[row2][col2]) == 0) {
								continue;
							} else {
								int currentValue = getIntFromChar(newBoard[row2][col2]);
								if (value == currentValue) {
									markValueAsInvalid(modifiedBoard, row, col);
									markValueAsInvalid(modifiedBoard, row2, col2);
								}
							}
						}
					}
				}
			}
		}
	}

	private void checkCols(char[][] newBoard) {
		for (int col = 0; col < ROWS; col++) {
			for (int row = 0; row < ROWS - 1; row++) {
				int value = getIntFromChar(newBoard[row][col]);
				if (value > 0) {
					for (int i = row + 1; i < ROWS; i++) {
						int currentValue = getIntFromChar(newBoard[i][col]);
						if (value == currentValue) {
							// mark as invalid value (from A to I)
							markValueAsInvalid(modifiedBoard, row, col);
							markValueAsInvalid(modifiedBoard, i, col);
						}
					}
				}
			}
		}
	}

	private void checkRows(char[][] newBoard) {

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < ROWS - 1; col++) {
				int value = getIntFromChar(newBoard[row][col]);
				if (value > 0) {
					for (int i = col + 1; i < ROWS; i++) {
						int currentValue = getIntFromChar(newBoard[row][i]);
						if (value == currentValue) {
							// mark as invalid value (from A to I)
							markValueAsInvalid(modifiedBoard, row, col);
							markValueAsInvalid(modifiedBoard, row, i);
						}
					}
				}
			}
		}
	}

	// convert a char to numbers from 0 to 9
	public int getIntFromChar(char c) {
		if (c >= '0' && c <= '9') {
			return (c - '0');
		} else if (c >= 'a' && c <= 'i') {
			return (c - 'a' + 1);
		} else if (c >= 'A' && c <= 'I') {
			return (c - 'A' + 1);
		} else {
			throw new IllegalArgumentException();
		}
	}

	// convert integer (0-9) to char from a to i
	// apply for non-editable elements from initial board
//	public char getCharForFixedValue(int value) {
//		if (value >= 1 && value <= 9) {
//			return (char) (value + 'a');
//		} else {
//			throw new IllegalArgumentException();
//		}
//	}

	// convert integer (0-9) to char from A to I
	// apply for invalid elements (duplicated on row or column or in cell)
	public char getCharForInvalidValue(int value) {
		if (value >= 1 && value <= 9) {
			return (char) (value + 'A' - 1);
		} else {
			throw new IllegalArgumentException();
		}
	}

	// convert integer number (0-9) to char
	public char getCharForValue(int value) {
		if (value >= 1 && value <= 9) {
			return (char) (value + '0');
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public void markValueAsInvalid(char[][] board, int row, int col) {
		//get value at current position and mark it as invalid value
		char invalidChar = getCharForInvalidValue(getIntFromChar(board[row][col]));
		
		//if value is not fixed(from initial board) mark it as invalid
		if (board[row][col] < 'a' || board[row][col] > 'i')
			board[row][col] = invalidChar;
	}

	public char[][] convertToBidimensional(final char[] array, final int rows, final int cols) {
		if (array.length != (rows * cols))
			throw new IllegalArgumentException("Invalid array length");

		// System.arraycopy(src, srcPos, dest, destPos, length);
		char[][] bidi = new char[rows][cols];
		for (int i = 0; i < rows; i++)
			System.arraycopy(array, (i * cols), bidi[i], 0, cols);

		return bidi;
	}
}
