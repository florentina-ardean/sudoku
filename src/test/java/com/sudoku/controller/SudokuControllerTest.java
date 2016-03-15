package com.sudoku.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sudoku.service.SudokuServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({SudokuServiceImpl.class, SudokuController.class})
@WebAppConfiguration

public class SudokuControllerTest {

	
	@Autowired 
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	 
	@Before
    public void setUp() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

	@Test
	public void getBoardFromJsonTest_ShouldReturnBoardOfCells() throws Exception {
		
		mockMvc.perform(get("/sudokuboard"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.solved", is(false)))
					.andExpect(jsonPath("$.cells", hasSize(9*9)));
			
	}
	
	@Test
	public void getBoardFromJsonTest_ShouldReturnString81Characters() throws Exception {
		
		mockMvc.perform(get("/board"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", notNullValue()));
		
	}
    
    @Test
	public void updateBoard_ShouldReturnBoardOf81CellsUpdated() throws Exception {
		String boardJson = readJsonFromFile("/data/SudokuBoard.data");
		
		mockMvc.perform(post("/sudokumoves")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(boardJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.solved", is(false)))
			.andExpect(jsonPath("$.cells", hasSize(9*9)));
			
	}
    
    @Test
	public void updateBoardFromJsonTest_ShouldReturnString81CharactersNotNull() throws Exception {
    	String boardJson = "0000ig000" + 
			 			   "00000a00d" + 
			 			   "00i00000h" + 
			 			   "00dec0000" + 
			 			   "0e00hi0a0" + 
			 			   "00b0g0h00" + 
			 			   "e00000ag0" + 
			 			   "0f000hd0c" + 
			 			   "bd000000i";
    	
    	mockMvc.perform(post("/moves")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(boardJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()));
		
	}
    
	public String readJsonFromFile(String fileName) throws IOException {
		
		Resource resource = new ClassPathResource(fileName);
		InputStream in = resource.getInputStream();
		String jsonTxt = IOUtils.toString(in);
		return jsonTxt;
		
	}

}
