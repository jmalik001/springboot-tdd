package com.learn.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.spring.entity.ToDo;
import com.learn.spring.service.ToDoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
@DisplayName("TO DO")
public class ToDoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ToDoService toDoService;

    @Test
    @DisplayName("Get all TO DO List")
    void testGetAllToDOs () throws Exception{
    List<ToDo> toDoList = new ArrayList<>();
    toDoList.add(new ToDo(100L, "Eat thrice",true));
    toDoList.add(new ToDo(102L, "Sleep twice",true));
    when(toDoService.findAll()).thenReturn(toDoList);

    mockMvc.perform(MockMvcRequestBuilders.get("/todos")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2))).andDo(print());

    }

    @Test
    @DisplayName("Create TODO when correct input is passed")
    void testCreationOfToDO () throws Exception {
        ToDo toDo = new ToDo(1L, "Read Java", true);
        when(toDoService.save(any(ToDo.class))).thenReturn(toDo);

        ObjectMapper objectMapper = new ObjectMapper();
        String todoJSON = objectMapper.writeValueAsString(toDo);

        ResultActions resultActions = mockMvc.perform(post("/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(todoJSON));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("Read Java"))
                .andExpect(jsonPath("$.completed").value("true"));

    }
}
