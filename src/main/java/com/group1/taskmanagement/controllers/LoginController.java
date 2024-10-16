package com.group1.taskmanagement.controllers;

import com.group1.taskmanagement.App;
import com.group1.taskmanagement.models.UserManager;
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

  private UserManager userManager = UserManager.getInstance();// Assuming this is instantiated properly

    @FXML
    public void loginAction() throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        boolean isAuthenticated = userManager.validateLogin(email, password);
        if (isAuthenticated) {
            boolean isAdmin =userManager.isAdmin(email);
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
                    UserSession.getIntance().setUserEmail(email);
                    System.out.println("regular user login successfully");
                    App.setRoot("task-details");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            errorMessage.setText("Login failed! Please register.");
            System.out.println("Login failed! Register please");
        }
    }

    @FXML
    private void switchToSignUp() {
        try {
            App.setRoot("register");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    }


