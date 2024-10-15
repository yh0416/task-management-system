/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group1.taskmanagement.models;


import com.group1.taskmanagement.controllers.Task;
import com.group1.taskmanagement.controllers.TaskController;
import com.group1.taskmanagement.models.User;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 *
 * @author lenovo
 */
public class ValidationManager {

    private UserManager userManager;
    private TaskController task;

    public ValidationManager() {
        this.userManager = new UserManager();
    }

    //validate the email and password
    public boolean validateLogin(String email, String password) {
        ArrayList<User> users = userManager.getAllUsers();
        for (User user : users) {
            if (user.getEmailAdress().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(String email) {
        return userManager.isAdmin(email);
    }
    
    public boolean validateInputs(Task task){
        if (task.getTaskName().trim().isEmpty()) {
            showAlert("Task Name is required");
            return false;
        }
        if (task.getAssignedTo().trim().isEmpty()) {
            showAlert("Assigned To is required");
            return false;
        }
        if (task.getDescription().trim().isEmpty()) {
            showAlert("Description is required");
            return false;
        }
        if (task.getStartDate() == null) {
            showAlert("Start Date is required");
            return false;
        }
        if (task.getDueDate() == null) {
            showAlert("Due Date is required");
            return false;
        }
        if (task.getPriority() == null) {
            showAlert("Priority is required");
            return false;
        }
        if (task.getStatus() == null) {
            showAlert("Status is required");
            return false;
        }
        if (task.getStartDate().isAfter(task.getDueDate())) {
            showAlert("Start Date cannot be after Due Date");
            return false;
        }
        return true;
}
    
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) return false;
        return pattern.matcher(email).matches();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
