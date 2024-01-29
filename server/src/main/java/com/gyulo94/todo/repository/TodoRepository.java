package com.gyulo94.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gyulo94.todo.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
