/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group1.taskmanagement.controllers;

import com.group1.taskmanagement.models.User;
import com.group1.taskmanagement.models.UserManager;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author lenovo
 */
public class RegisterController {
    //1.get all the data from the xfml

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorMessage;

    private UserManager userManager;

    public RegisterController() {
        this.userManager = new UserManager();
    }

    //bind the button to the event handler
    public void initialize() {
        signUpButton.setOnAction(new SignUpButtonHandler());
    }

// delegatiing the action to the handleSignUp
    private class SignUpButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            handleSignUp();
        }
    }

//validate the form
    private void handleSignUp() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Please fill out all fields.");
        } else {
            User newUser = new User(fullName, email, password);
            userManager.addUser(newUser);

            System.out.println("Registration successful for " + fullName);
            errorMessage.setText("Registration successful!");
        }
    }

    @FXML
    private void switchToLogin() {
        try {
            App.setRoot("login");  // 切换到登录页面
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
