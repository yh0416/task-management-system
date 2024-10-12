package com.group1.taskmanagement.controllers;

import com.group1.taskmanagement.App;
import com.group1.taskmanagement.models.TaskModel;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class TaskController {

    TaskModel taskModel;  

    @FXML
    private VBox taskForm;
    @FXML
    private VBox dashboard;
    @FXML private TableView<Task> tasksTable;   
    @FXML private TableColumn<Task, String> taskNameClm;
    @FXML private TableColumn<Task, String> taskDescriptionClm;
    @FXML private TableColumn<Task, String> taskAssignedClm;
    @FXML private TableColumn<Task, String> taskDueClm;
    @FXML private TableColumn<Task, String> taskPriorityClm;
    @FXML private TableColumn<Task, String> taskStatusClm;
    
    @FXML private TextField assignedToTxt;
    @FXML private TextField taskNameTxt;
    @FXML private TextArea descriptionTextArea;
    @FXML private ComboBox<String> priorityDropdown;
    @FXML private DatePicker dueDatePicker;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<String> statusDropdown;
    
    @FXML private ComboBox<String> statusFilter;
//   use and observable list to automatically update UI when changes occur
    private ObservableList<Task> taskData = FXCollections.observableArrayList();
//    initialize filter data
    private FilteredList<Task> filteredData;
    private SortedList<Task> sortedData;

   
    public TaskController(){
        this.taskModel = new TaskModel();
    }

    @FXML
    public void initialize(){
        priorityDropdown.getItems().addAll("Critical","High","Medium","Low");
       statusDropdown.getItems().addAll("Pending","In Progress","Completed");
       statusFilter.getItems().addAll("All","Pending","In Progress","Completed");
       
//       set up the table columns   
        taskNameClm.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDescriptionClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskAssignedClm.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        taskDueClm.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        taskPriorityClm.setCellValueFactory(new PropertyValueFactory<>("priority"));
        taskStatusClm.setCellValueFactory(new PropertyValueFactory<>("status"));
        
//        filter
        //create a new filtered list and allow all task data to pass through the filter
        filteredData =new FilteredList<>(taskData,p->true);
        //add listener to combo box to trigger each time an item is selected
        statusFilter.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue )->{
            //set condition for the filtered data
            filteredData.setPredicate(task->{
                //if new selected value is null or All then display all tasks 
                if(newValue ==null ||newValue.equals("All")){
                    return true;
                }
                //show tasks matching the selected status
                return task.getStatus().equals(newValue);
            });
        });
        
//        sort
        //create sorted list based on the flitered data
        sortedData = new SortedList<>(filteredData);
        //bind the sorting of sorteddata to the sorting of the table
        sortedData.comparatorProperty().bind(tasksTable.comparatorProperty());
        //set the items to the sorted and filtered data 
        tasksTable.setItems(sortedData);
        
//        load existing tasks
        getTasks();
        
//        listen for click
    tasksTable.setOnMouseClicked(event->{
        Task selectedTask =tasksTable.getSelectionModel().getSelectedItem();
        if(selectedTask != null){
            showTaskDetails(selectedTask);
        }
    });
       
    }
    
    
//   gather task details
    private Task gatherTaskDetails(){
        return new Task(null,
                assignedToTxt.getText(),
                taskNameTxt.getText(),
                descriptionTextArea.getText(),
                startDatePicker.getValue(),
                dueDatePicker.getValue(),
                priorityDropdown.getValue(),
                statusDropdown.getValue()
                
        ); 
    }
   
    //submit task data
     @FXML
    private void submitTask() {
            Task newTask =gatherTaskDetails();
            //     save the data
           boolean response = taskModel.submitTask(newTask);
            if(response){
                System.out.println("Task Saved Successfully"); 
//                add task to oberservable list
                taskData.add(newTask);
                clearForm();
                showDashboard();
            }else{
                System.out.println("There was an error saving task");  
             }   
       
    }
    //clear form after submitting
    private void clearForm(){
        assignedToTxt.clear();
        taskNameTxt.clear();
        descriptionTextArea.clear();
        priorityDropdown.setValue(null);
        dueDatePicker.setValue(null);
        startDatePicker.setValue(null);
        statusDropdown.setValue(null);
        
    }
    //get task data
    private void getTasks(){
        List<Task> tasksFromModel = taskModel.getTasksFromFile();
        taskData.setAll(tasksFromModel);
       
    }
    
    
//    sort data in ascending order
    @FXML
    private void sortAscending(){
        //clear any existing sort order
      tasksTable.getSortOrder().clear();
      //add task name column to the sort order
      tasksTable.getSortOrder().add(taskNameClm);
      //sort the task name in ascending order
      taskNameClm.setSortType(TableColumn.SortType.ASCENDING);
      //sort
      tasksTable.sort();
    }
    
//   sort data in descending order 
     @FXML
    private void sortDescending(){
      tasksTable.getSortOrder().clear();
      tasksTable.getSortOrder().add(taskNameClm);
      taskNameClm.setSortType(TableColumn.SortType.DESCENDING);
      tasksTable.sort();
    }
    
    @FXML
    private void showDashboard(){
//      hide taskform 
    taskForm.setVisible(false);
//    show dashboard
    dashboard.setVisible(true);

    }
    
    @FXML
    private void showTaskForm(){
//        show task form
    taskForm.setVisible(true);
//    show dashboard
    dashboard.setVisible(false);
    }
    
    //populate the task form with the task details
     private void showTaskDetails(Task task){
        assignedToTxt.setText(task.getAssignedTo());
        taskNameTxt.setText(task.getTaskName());
        descriptionTextArea.setText(task.getDescription());
        priorityDropdown.setValue(task.getPriority());
        dueDatePicker.setValue(task.getDueDate());
        startDatePicker.setValue(task.getStartDate());
        statusDropdown.setValue(task.getStatus());
        
        //show task form
        showTaskForm();
    }
}