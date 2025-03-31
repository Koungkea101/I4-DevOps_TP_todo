package com.mycompany.app.gui;

import com.mycompany.app.model.Todo;
import com.mycompany.app.model.TodoList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel that displays the list of todos
 */
public class TodoPanel extends JPanel {
    private TodoList todoList;
    private JList<Todo> todoJList;
    private DefaultListModel<Todo> listModel;
    private JComboBox<String> filterComboBox;
    
    public TodoPanel(TodoList todoList) {
        this.todoList = todoList;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create the list model and JList
        listModel = new DefaultListModel<>();
        todoJList = new JList<>(listModel);
        todoJList.setCellRenderer(new TodoListCellRenderer());
        todoJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Create the filter panel
        JPanel filterPanel = createFilterPanel();
        
        // Create the buttons panel
        JPanel buttonsPanel = createButtonsPanel();
        
        // Add components to the panel
        add(filterPanel, BorderLayout.NORTH);
        add(new JScrollPane(todoJList), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        
        // Initial population of the list
        refreshTodoList();
    }
    
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel filterLabel = new JLabel("Filter:");
        filterComboBox = new JComboBox<>(new String[]{"All", "Pending", "Completed"});
        
        filterComboBox.addActionListener(e -> refreshTodoList());
        
        panel.add(filterLabel);
        panel.add(filterComboBox);
        
        return panel;
    }
    
    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton toggleButton = new JButton("Toggle Status");
        
        editButton.addActionListener(e -> editSelectedTodo());
        deleteButton.addActionListener(e -> deleteSelectedTodo());
        toggleButton.addActionListener(e -> toggleSelectedTodoStatus());
        
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(toggleButton);
        
        return panel;
    }
    
    public void refreshTodoList() {
        listModel.clear();
        
        List<Todo> filteredTodos;
        String filter = (String) filterComboBox.getSelectedItem();
        
        if ("Pending".equals(filter)) {
            filteredTodos = todoList.getPendingTodos();
        } else if ("Completed".equals(filter)) {
            filteredTodos = todoList.getCompletedTodos();
        } else {
            filteredTodos = todoList.getAllTodos();
        }
        
        for (Todo todo : filteredTodos) {
            listModel.addElement(todo);
        }
    }
    
    private void editSelectedTodo() {
        Todo selectedTodo = todoJList.getSelectedValue();
        if (selectedTodo == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a todo to edit", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        TodoDialog dialog = new TodoDialog(SwingUtilities.getWindowAncestor(this), selectedTodo);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            refreshTodoList();
        }
    }
    
    private void deleteSelectedTodo() {
        Todo selectedTodo = todoJList.getSelectedValue();
        if (selectedTodo == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a todo to delete", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this todo?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            todoList.removeTodo(selectedTodo.getId());
            refreshTodoList();
        }
    }
    
    private void toggleSelectedTodoStatus() {
        Todo selectedTodo = todoJList.getSelectedValue();
        if (selectedTodo == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a todo to toggle status", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedTodo.setCompleted(!selectedTodo.isCompleted());
        refreshTodoList();
    }
    
    /**
     * Custom cell renderer for the todo list
     */
    private class TodoListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Todo) {
                Todo todo = (Todo) value;
                label.setText(todo.getTitle());
                
                if (todo.isCompleted()) {
                    label.setFont(label.getFont().deriveFont(Font.ITALIC));
                    if (!isSelected) {
                        label.setForeground(Color.GRAY);
                    }
                } else {
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                }
            }
            
            return label;
        }
    }
}
