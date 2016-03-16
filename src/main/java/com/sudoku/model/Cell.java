package com.sudoku.model;

public class Cell {
	public enum Type {
		fixed, valid, invalid
	}

	private int value;

	private Type type;

	public Cell() {

	}
	
	public Cell(int value, Type type) {
		this.value = value;
		this.type = type;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (type != other.type)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
}
