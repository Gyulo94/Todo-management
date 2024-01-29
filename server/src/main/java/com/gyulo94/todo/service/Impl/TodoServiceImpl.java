package com.gyulo94.todo.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.gyulo94.todo.dto.TodoDTO;
import com.gyulo94.todo.entity.Todo;
import com.gyulo94.todo.exception.ResourceNotFoundException;
import com.gyulo94.todo.repository.TodoRepository;
import com.gyulo94.todo.service.TodoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    private ModelMapper modelMapper;

    @Override
    public TodoDTO addTodo(TodoDTO todoDTO) {

        // TodoDTO를 Todo Jpa Entity로 변환
        // Todo todo = new Todo();
        // todo.setTitle(todoDTO.getTitle());
        // todo.setDescription(todoDTO.getDescription());
        // todo.setCompleted(todoDTO.isCompleted());

        Todo todo = modelMapper.map(todoDTO, Todo.class);

        // Todo Jpa 엔터티
        Todo savedTodo = todoRepository.save(todo);

        // 저장된 Todo Jpa 엔터티 객체를 TodoDTO 객체로 변환
        // TodoDTO savedTodoDTO = new TodoDTO();
        // savedTodoDTO.setId(savedTodo.getId());
        // savedTodoDTO.setTitle(savedTodo.getTitle());
        // savedTodoDTO.setDescription(savedTodo.getDescription());
        // savedTodoDTO.setCompleted(savedTodo.isCompleted());

        TodoDTO savedTodoDTO = modelMapper.map(savedTodo, TodoDTO.class);
        return savedTodoDTO;
    }

    @Override
    public TodoDTO getTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당" + id + "번의 할 일이 존재하지 않습니다."));
        return modelMapper.map(todo, TodoDTO.class);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDTO updateTodo(TodoDTO todoDTO, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당" + id + "번의 할 일이 존재하지 않습니다."));
        todo.setTitle(todoDTO.getTitle());
        todo.setDescription(todoDTO.getDescription());
        todo.setCompleted(todoDTO.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDTO.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당" + id + "번의 할 일이 존재하지 않습니다."));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDTO completeTodo(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당" + id + "번의 할 일이 존재하지 않습니다."));

        todo.setCompleted(Boolean.TRUE);

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDTO.class);
    }

    @Override
    public TodoDTO incompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당" + id + "번의 할 일이 존재하지 않습니다."));

        todo.setCompleted(Boolean.FALSE);

        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo, TodoDTO.class);
    }
}