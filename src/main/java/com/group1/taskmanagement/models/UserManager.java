/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group1.taskmanagement.models;

import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public class UserManager {

    private ArrayList<User> users;

    //set the admin email and they can login by that
    private final String aminEmail = "adminEmail";
    private final String aminPassword = "adminPassword";

    //constructor
    public UserManager() {
        this.users = new ArrayList<>();
        //put admin into it
        User adminUser = new User("Admin", aminEmail, "adminPassword");
        users.add(adminUser);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public boolean isAdmin(String email) {
        return email.equals(aminEmail);
    }

}
