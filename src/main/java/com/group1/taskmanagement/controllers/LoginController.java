package com.group1.taskmanagement.controllers;

import com.group1.taskmanagement.App;
import com.group1.taskmanagement.models.ValidationManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private Button loginButton;
    
    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    private ValidationManager validationManager;

    public LoginController() {
        this.validationManager = new ValidationManager();
    }

    @FXML
    public void loginAction() {
        String email = emailField.getText();
        String password = passwordField.getText();

        boolean isAuthenticatd = validationManager.validateLogin(email, password);
        if (isAuthenticatd) {
            boolean isAdmin =validationManager.isAdmin(email);
            if (isAdmin) {
                UserSession.getIntance().setIsAdmin(isAdmin);
                UserSession.getIntance().setUserEmail(email);
                try {
                    System.out.println("Admin login successfully");
                    errorMessage.setText("");
                    App.setRoot("task-details");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    System.out.println("regular user login successfully");
                    App.setRoot("task-details");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Login failed! register please");
        }
    }

    @FXML
    private void switchToSignUp() {
        try {
            App.setRoot("register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
