package com.sudoku.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class CellTest {
	
	@DataPoint
	public static int MIN_VALUE = Integer.MIN_VALUE;
	
	@DataPoint
	public static int MAX_VALUE = Integer.MAX_VALUE;
	
	@DataPoint
	public static Cell.Type TYPE_FIXED = Cell.Type.fixed;
	
	@DataPoint
	public static Cell.Type TYPE_VALID = Cell.Type.valid;
	
	@DataPoint
	public static Cell.Type TYPE_INVALID = Cell.Type.invalid;
	
	
	@Test
	public void testHashCode() {
		Cell cellA = new Cell();
		Cell cellB = new Cell();
		
		assertTrue(cellA.equals(cellB) && cellB.equals(cellA));
		assertTrue(cellA.hashCode() == cellB.hashCode());
	}
	
	@Test
	public void testHashCodeForConsistency_2Invocations() {
		Cell cellA = new Cell();
		Cell cellB = new Cell();
		
		assertTrue(cellA.hashCode() == cellB.hashCode());
		assertTrue(cellA.hashCode() == cellB.hashCode());
	}
	
	@Theory
	public void testHashCodeForConsistency_2Invocations_AndValues(int value, Cell.Type type) {
		Cell cellA = new Cell(value, type);
		Cell cellB = new Cell(value, type);
		
		assertTrue(cellA.hashCode() == cellB.hashCode());
		assertTrue(cellA.hashCode() == cellB.hashCode());
	}

	@Test
	public void testEqualsForNull() {
		Cell cell = new Cell();
		assertFalse(cell.equals(null));
	}
	
	@Test
	public void testEqualsForReflexivity() {
		Cell cell = new Cell();
		assertTrue(cell.equals(cell));
	}
	
	@Test
	public void testEqualsForSimetry() {
		Cell cellA = new Cell();
		Cell cellB = new Cell();
		assertTrue(cellA.equals(cellB) && cellB.equals(cellA));
	}
	
	@Test
	public void testEqualsForSimetry_CopyConstructor() {
		Cell cellA = new Cell();
		Cell cellB = new Cell(cellA);
		assertTrue(cellA.equals(cellB) && cellB.equals(cellA));
	}
	
	@Test
	public void testEqualsForTransitivity() {
		Cell cellA = new Cell();
		Cell cellB = new Cell();
		Cell cellC = new Cell();
		
		assertTrue(cellA.equals(cellB));
		assertTrue(cellB.equals(cellC));
		assertTrue(cellA.equals(cellC));
	}
	
	@Test
	public void testEqualsForConsistency_2Invacations() {
		Cell cellA = new Cell();
		Cell cellB = new Cell();
		
		assertTrue(cellA.equals(cellB));
		assertTrue(cellA.equals(cellB));
	}
	
	@Test
	public void testEqualsForConsistency_3Invacations() {
		Cell cellA = new Cell();
		Cell cellB = new Cell();
		
		assertTrue(cellA.equals(cellB));
		assertTrue(cellA.equals(cellB));
		assertTrue(cellA.equals(cellB));
	}
	
	@Theory
	public void testEqualsForConsistency_3Invacations_ReturnFalse(int value, Cell.Type type) {
		Cell cellA = new Cell();
		Cell cellB = new Cell(value, type);
		
		assertFalse(cellA.equals(cellB));
		assertFalse(cellA.equals(cellB));
		assertFalse(cellA.equals(cellB));
	}
	
	@Theory
	public void testEquals_ReturnFalse_ForDifferent_Values(int value, Cell.Type type) {
		Cell cellA = new Cell();
		cellA.setValue(value);
		cellA.setType(type);
		
		Cell cellB = new Cell();
		cellB.setValue(cellA.getValue() + 1);
		cellB.setType(cellA.getType());
		
		assertFalse(cellA.equals(cellB));
		assertFalse(cellA.equals(cellB));
	}
	
	@Theory
	public void testEquals_ReturnFalse_ForDifferent_Types(int value) {
		Cell cellA = new Cell(value, Cell.Type.valid);
		Cell cellB = new Cell(value, Cell.Type.fixed);
		
		assertFalse(cellA.equals(cellB));
	}
	
	@Test
	public void testEqualsForDifferentClass() {
		Cell cell = new Cell();
		assertFalse(cell.equals(new Integer(2)));
	}
	
}
