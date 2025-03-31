package com.mycompany.app.gui;

import com.mycompany.app.model.Todo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog for adding or editing a Todo
 */
public class TodoDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JCheckBox completedCheckBox;
    private boolean saved = false;
    private Todo todo;
    
    public TodoDialog(Window owner, Todo todo) {
        super(owner, todo == null ? "Add New Todo" : "Edit Todo", ModalityType.APPLICATION_MODAL);
        
        this.todo = todo;
        if (todo == null) {
            this.todo = new Todo("", "");
        }
        
        initComponents();
        populateFields();
        
        setSize(400, 300);
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Title
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(titleField, gbc);
        
        // Description
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel descriptionLabel = new JLabel("Description:");
        formPanel.add(descriptionLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane, gbc);
        
        // Completed checkbox
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        completedCheckBox = new JCheckBox("Completed");
        formPanel.add(completedCheckBox, gbc);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    updateTodo();
                    saved = true;
                    dispose();
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        
        // Add panels to content panel
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Set content pane
        setContentPane(contentPanel);
    }
    
    private void populateFields() {
        titleField.setText(todo.getTitle());
        descriptionArea.setText(todo.getDescription());
        completedCheckBox.setSelected(todo.isCompleted());
    }
    
    private boolean validateInput() {
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Title cannot be empty", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            titleField.requestFocus();
            return false;
        }
        return true;
    }
    
    private void updateTodo() {
        todo.setTitle(titleField.getText().trim());
        todo.setDescription(descriptionArea.getText().trim());
        todo.setCompleted(completedCheckBox.isSelected());
    }
    
    public boolean isSaved() {
        return saved;
    }
    
    public Todo getTodo() {
        return todo;
    }
}
