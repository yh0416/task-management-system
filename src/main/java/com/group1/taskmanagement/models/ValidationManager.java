/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group1.taskmanagement.models;

import com.group1.taskmanagement.models.User;
import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public class ValidationManager {

    private UserManager userManager;

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
}
