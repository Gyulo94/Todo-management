package com.gyulo94.todo.service;

import java.util.List;

import com.gyulo94.todo.dto.TodoDTO;

public interface TodoService {

    TodoDTO addTodo(TodoDTO todoDTO);

    TodoDTO getTodo(Long id);

    List<TodoDTO> getAllTodos();

    TodoDTO updateTodo(TodoDTO todoDTO, Long id);

    void deleteTodo(Long id);

    TodoDTO completeTodo(Long id);

    TodoDTO incompleteTodo(Long id);
}
