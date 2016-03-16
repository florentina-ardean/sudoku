package com.sudoku.suite;

import static org.junit.Assert.fail;

import org.apache.lucene.spatial.prefix.tree.Cell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.sudoku.controller.SudokuControllerTest;
import com.sudoku.model.CellTest;
import com.sudoku.service.SudokuServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CellTest.class, 
	SudokuServiceImplTest.class,
	SudokuControllerTest.class})
public class SudokuTestSuite {

	@Test
	public void test() {
	}

}
