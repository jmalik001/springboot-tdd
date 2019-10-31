package com.learn.spring;

import com.learn.spring.entity.ToDo;
import com.learn.spring.repository.ToDoRepository;
import com.learn.spring.service.ToDoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
public class ToDoServiceTest {

    //@MockBean
    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    @DisplayName("get all todo list when call service")
    void testGetAllToDos(){
        ToDo todoSample = new ToDo("Go to Office by 9",true);
        toDoRepository.save(todoSample);
        ToDoService toDoService = new ToDoService(toDoRepository);
        List<ToDo> todoList = toDoService.findAll();
        ToDo toDo = todoList.get(todoList.size() - 1);

        Assertions.assertEquals(todoSample.getText(), toDo.getText());
        Assertions.assertEquals(todoSample.isCompleted(), toDo.isCompleted());
    }

    @DisplayName("Save ToDO when data passed correctly")
    @Test
    void testSaveToDo() throws Exception {
        ToDoService toDoService = new ToDoService(toDoRepository);
        ToDo toDo = new ToDo("Eat almond in morning", true);
        toDoService.save(toDo);
        Assertions.assertEquals(1.0, toDoRepository.count());

    }
    @AfterEach
    void tearDown(){
        toDoRepository.deleteAll();
    }

}
