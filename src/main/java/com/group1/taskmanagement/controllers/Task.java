/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group1.taskmanagement.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author kiara
 */
public class Task {
     
    private String assignedTo;
    private String taskName;
    private String description;
    private LocalDate dueDate;
    private LocalDate startDate;
    private String priority;
    private String taskId;
    private String createdBy;
    private String status;
    
      //constructor
    public Task(String taskId, String assignedTo, String taskName, String description,LocalDate startDate, LocalDate dueDate,
            String priority, String status,String createdBy) {
        this.taskId = (taskId == null)? generateTaskID() : taskId;
        this.assignedTo = assignedTo;
        this.taskName = taskName;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.createdBy = createdBy;

    }
    //generate a random task id
    private String generateTaskID(){
        return UUID.randomUUID().toString();
    }
    
//    format task details as string
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
               taskId, assignedTo,taskName,description.replace(",", "\\,"),startDate,dueDate,priority,status,createdBy);
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    
}
