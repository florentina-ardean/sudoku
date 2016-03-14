package com.sudoku.model;

import java.util.ArrayList;

public class Board {
	ArrayList<Cell> cells;
	boolean solved;

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}

}
