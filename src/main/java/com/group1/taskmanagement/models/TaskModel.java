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

    private String file_path = "tasks.txt";

    //constructor
    public TaskModel() {
        
        getTasksFromFile();

    }

    private void writeTasksToFile(List<Task> allTasks) {
        //to avoid overwriting add true to filewriter to append
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_path, false))) {
            for (Task task : allTasks) {
//                write task into the file
                bw.write(task.toString());
//               go to the next line after adding task
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public boolean submitTask(Task taskDetails) {
        List allTasks = getTasksFromFile();
//        save task details in an array lisr
        boolean success = allTasks.add(taskDetails);
        if (success) {
            writeTasksToFile(allTasks);
            return true;
        }
        return false;

    }

    public List<Task> getTasksFromFile() {
        List<Task> tasks = new ArrayList<>();
        System.out.println("Loading tasks from file...");
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    Task task = new Task(
                            parts[0], parts[1], parts[2], parts[3].replace("\\", ","), LocalDate.parse(parts[4]),
                            LocalDate.parse(parts[5]), parts[6], parts[7], parts[8]);

                    tasks.add(task);
                    // 打印日志，帮助调试每个任务的加载
//                    System.out.println("Loaded task: " + task.getTaskName());
                } else {
                    System.out.println("Invalid task data, skipping line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("Total tasks loaded: " + tasks.size());
        return tasks;

    }

    public boolean updateTask(Task updatedTask) {
        List<Task> tasks = getTasksFromFile();
        boolean updated = false;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().equals(updatedTask.getTaskId())) {
                tasks.set(i, updatedTask);
                updated = true;
                break;
            }
        }
       if(updated){
           writeTasksToFile(tasks);
       }
       return updated;
    }

    public void deleteTask(String taskId) {
        List<Task> tasks = getTasksFromFile();
        tasks.removeIf(task -> task.getTaskId().equals(taskId));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_path))) {
            for (Task task : tasks) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 

}
