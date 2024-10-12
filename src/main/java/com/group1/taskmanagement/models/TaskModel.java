/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group1.taskmanagement.models;

import com.group1.taskmanagement.controllers.Task;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author kiara
 */
public class TaskModel {
    private List<Task> tasks;
    private String file_path = "tasks.txt";
   
      //constructor
    public TaskModel(){
        this.tasks = new ArrayList<>();
        getTasksFromFile();

    }
    
     private void writeTasksToFile(){
         //to avoid overwriting add true to filewriter to append
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file_path, true))){
            for(Task task : tasks){
//                write task into the file
               bw.write(task.toString());
//               go to the next line after adding task
               bw.newLine();
            }
        }catch(IOException e){
            e.printStackTrace();
            
        }
    }

    public boolean submitTask(Task taskDetails){
//        save task details in an array lisr
     boolean success = tasks.add(taskDetails);
     if(success){
         writeTasksToFile();
        return true;
     }
     return false;
        
    }
    
    public List<Task> getTasksFromFile(){
        List<Task> tasks =new ArrayList<>();
         try(BufferedReader br = new BufferedReader(new FileReader(file_path))){
         String line;
         while ((line= br.readLine()) != null){
             String[] parts = line.split(",");
             if(parts.length == 9){
                 Task task = new Task(
                         parts[0],parts[1], parts[2],parts[3].replace("\\", ","),LocalDate.parse(parts[4]),
                                 LocalDate.parse(parts[5]),parts[6],parts[7],parts[8]);
            
                  tasks.add(task);
             }
         }
        }catch(IOException e){
            e.printStackTrace();
            
        }
         return tasks;
      
     }
    
    
}


