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
    public TaskModel() {
        this.tasks = new ArrayList<>();
        getTasksFromFile();

    }

    private void writeTasksToFile() {
        //to avoid overwriting add true to filewriter to append
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_path, true))) {
            for (Task task : tasks) {
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
//        save task details in an array lisr
        boolean success = tasks.add(taskDetails);
        if (success) {
            writeTasksToFile();
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
                    System.out.println("Loaded task: " + task.getTaskName());
                } else {
                    System.out.println("Invalid task data, skipping line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Total tasks loaded: " + tasks.size());
        return tasks;

    }

    public void updateTask(Task updatedTask) {
        List<Task> tasks = getTasksFromFile();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskId().equals(updatedTask.getTaskId())) {
                tasks.set(i, updatedTask);
                break;
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_path))) {
            for (Task task : tasks) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void addSampleTasks() {
        // 创建几个预设的测试任务
        Task task1 = new Task(null, "John", "Task 1", "This is task 1", LocalDate.now(), LocalDate.now().plusDays(1), "High", "Pending", "Admin");
        Task task2 = new Task(null, "Jane", "Task 2", "This is task 2", LocalDate.now(), LocalDate.now().plusDays(2), "Medium", "In Progress", "Admin");
        Task task3 = new Task(null, "Doe", "Task 3", "This is task 3", LocalDate.now(), LocalDate.now().plusDays(3), "Low", "Completed", "Admin");

        // 添加这些任务到任务列表中
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        // 将这些任务写入到文件中
        writeTasksToFile();
    }

}
