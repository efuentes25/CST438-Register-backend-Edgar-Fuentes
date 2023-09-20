package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TestStudentController {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private StudentRepository studentRepository;

    /*
     * Tests the retrieval of all the students.
     */
    @Test
    public void getAllStudents() throws Exception {
        MockHttpServletResponse response;

        response = mvc.perform(
                MockMvcRequestBuilders
                .get("/student")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that return status = OK (value 200) 
        assertEquals(200, response.getStatus());

        StudentDTO[] students = fromJsonString(response.getContentAsString(), StudentDTO[].class);
        assertNotEquals(0, students.length);
    }

    /*
     * Tests the retrieval of a specific student.
     */
    @Test
    public void getStudentById() throws Exception {
        MockHttpServletResponse response;

        int testStudentId = 1;  // Adjust this as necessary based on your test data

        response = mvc.perform(
                MockMvcRequestBuilders
                .get("/student/" + testStudentId)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify that return status = OK (value 200) 
        assertEquals(200, response.getStatus());

        StudentDTO student = fromJsonString(response.getContentAsString(), StudentDTO.class);
        assertEquals(testStudentId, student.student_id());
    }

    // Add more tests as necessary for other CRUD operations...
    
    /*@Test
    public void addStudent() throws Exception {
        MockHttpServletResponse response;
        
        Student studentDTO = new Student();
        studentDTO.setName("Jane Smith");
        studentDTO.setEmail("janesmith@example.com");
        studentDTO.setStatus("Active");
        studentDTO.setStatusCode(1);
        
        response = mvc.perform(MockMvcRequestBuilders.post("/assignment").accept(MediaType.APPLICATION_JSON)
        		.content(asJsonString(studentDTO)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        
        Student studentDTO = .find();
    }*/
    
	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T fromJsonString(String str, Class<T> valueType) {
        try {
            return new ObjectMapper().readValue(str, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}