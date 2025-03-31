package com.mycompany.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Manages a collection of Todo items
 */
public class TodoList implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Todo> todos;
    
    public TodoList() {
        this.todos = new ArrayList<>();
    }
    
    /**
     * Add a new todo to the list
     */
    public void addTodo(Todo todo) {
        todos.add(todo);
    }
    
    /**
     * Remove a todo from the list by its ID
     */
    public boolean removeTodo(String id) {
        return todos.removeIf(todo -> todo.getId().equals(id));
    }
    
    /**
     * Find a todo by its ID
     */
    public Optional<Todo> findById(String id) {
        return todos.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst();
    }
    
    /**
     * Get all todos
     */
    public List<Todo> getAllTodos() {
        return new ArrayList<>(todos);
    }
    
    /**
     * Get completed todos
     */
    public List<Todo> getCompletedTodos() {
        return todos.stream()
                .filter(Todo::isCompleted)
                .collect(Collectors.toList());
    }
    
    /**
     * Get pending todos
     */
    public List<Todo> getPendingTodos() {
        return todos.stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
    }
    
    /**
     * Get the number of todos
     */
    public int getSize() {
        return todos.size();
    }
}
