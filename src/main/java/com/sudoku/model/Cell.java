package com.sudoku.model;

public class Cell {
	public enum Type {
		FIXED, VALID, INVALID
	}

	private int value;

	private Type type;

	public Cell() {

	}

	public Cell(Cell cell) {
		this.value = cell.getValue();
		this.type = cell.getType();
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}


}
