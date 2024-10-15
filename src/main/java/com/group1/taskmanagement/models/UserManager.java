package com.group1.taskmanagement.models;

import java.util.ArrayList;

public class UserManager {

    private ArrayList<User> users;
    private static UserManager instance;

    // Set the admin email and they can login by that
    private final String aminEmail = "adminEmail";
    private final String aminPassword = "adminPassword";

    // Constructor
    public UserManager() {
        this.users = new ArrayList<>();
        // Put admin into it
        User adminUser = new User("Admin", aminEmail, aminPassword);
        users.add(adminUser);
    }

    // Add a new user
    public void addUser(User user) {
        users.add(user);
    }

    // Retrieve all users
    public ArrayList<User> getAllUsers() {
        return users;
    }

    // Validate login credentials
    public boolean validateLogin(String email, String password) {
        for (User user : users) {
            if (user.getEmailAdress().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Check if user is admin
    public boolean isAdmin(String email) {
        return email.equals(aminEmail);
    }

    // Get the singleton instance of UserManager
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
}
