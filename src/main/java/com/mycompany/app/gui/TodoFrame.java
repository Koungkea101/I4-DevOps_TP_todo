package com.mycompany.app.gui;

import com.mycompany.app.model.Todo;
import com.mycompany.app.model.TodoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main frame for the Todo application
 */
public class TodoFrame extends JFrame {
    private TodoList todoList;
    private TodoPanel todoPanel;
    
    public TodoFrame() {
        // Initialize the todo list
        todoList = new TodoList();
        
        // Set up the frame
        setTitle("Todo Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create the main panel
        todoPanel = new TodoPanel(todoList);
        
        // Add components to the frame
        getContentPane().add(createToolbar(), BorderLayout.NORTH);
        getContentPane().add(todoPanel, BorderLayout.CENTER);
        
        // Add sample todos for demonstration
        addSampleTodos();
        
        // Add window listener to handle closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Could add persistence here in the future
                System.exit(0);
            }
        });
    }
    
    private JToolBar createToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        
        JButton addButton = new JButton("Add Todo");
        addButton.setIcon(UIManager.getIcon("FileView.fileIcon"));
        addButton.addActionListener(e -> showAddTodoDialog());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setIcon(UIManager.getIcon("FileView.directoryIcon"));
        refreshButton.addActionListener(e -> todoPanel.refreshTodoList());
        
        toolbar.add(addButton);
        toolbar.add(refreshButton);
        
        return toolbar;
    }
    
    private void showAddTodoDialog() {
        TodoDialog dialog = new TodoDialog(this, null);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            Todo newTodo = dialog.getTodo();
            todoList.addTodo(newTodo);
            todoPanel.refreshTodoList();
        }
    }
    
    private void addSampleTodos() {
        todoList.addTodo(new Todo("Complete project", "Finish the Todo application with all features"));
        todoList.addTodo(new Todo("Write documentation", "Document the code and create a README"));
        todoList.addTodo(new Todo("Test application", "Test all features of the application"));
        
        Todo completedTodo = new Todo("Setup project", "Initialize the Maven project structure");
        completedTodo.setCompleted(true);
        todoList.addTodo(completedTodo);
    }
}
