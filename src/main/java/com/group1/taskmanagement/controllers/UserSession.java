/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group1.taskmanagement.controllers;

/**
 *
 * @author kiara
 */
public class UserSession {
    
    private static UserSession instance;
    private boolean isAdmin;
    private String userEmail;
    
    public static UserSession getIntance(){
        if(instance == null){
            instance = new UserSession();
        }
        return instance;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    
    
}
