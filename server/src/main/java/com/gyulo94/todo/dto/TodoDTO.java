package com.gyulo94.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
