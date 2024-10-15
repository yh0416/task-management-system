package com.group1.taskmanagement.controllers;

import com.group1.taskmanagement.App;
import com.group1.taskmanagement.models.TaskModel;
import com.group1.taskmanagement.models.ValidationManager;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TaskController {

    TaskModel taskModel;
    private ValidationManager vm = new ValidationManager();

    private boolean isAdmin;
    private String userEmail;

    @FXML
    private VBox taskForm;
    @FXML
    private VBox dashboard;
    @FXML
    private TableView<Task> tasksTable;
    @FXML
    private TableColumn<Task, String> taskNameClm;
    @FXML
    private TableColumn<Task, String> taskDescriptionClm;
    @FXML
    private TableColumn<Task, String> taskAssignedClm;
    @FXML
    private TableColumn<Task, String> taskDueClm;
    @FXML
    private TableColumn<Task, String> taskPriorityClm;
    @FXML
    private TableColumn<Task, String> taskStatusClm;

    @FXML
    private TextField assignedToTxt;
    @FXML
    private TextField taskNameTxt;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ComboBox<String> priorityDropdown;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<String> statusDropdown;

    @FXML
    private ComboBox<String> statusFilter;
//   use and observable list to automatically update UI when changes occur
    private ObservableList<Task> taskData = FXCollections.observableArrayList();
//    initialize filter data
    private FilteredList<Task> filteredData;
    private SortedList<Task> sortedData;

    public TaskController() {
        this.taskModel = new TaskModel();
        this.isAdmin = UserSession.getIntance().getIsAdmin();
        this.userEmail = UserSession.getIntance().getUserEmail();

    }

    private Task getSelectedTask() {
        return (Task) tasksTable.getSelectionModel().getSelectedItem();
    }


    @FXML
    public void initialize() {
        priorityDropdown.getItems().addAll("Critical", "High", "Medium", "Low");
        statusDropdown.getItems().addAll("Pending", "In Progress", "Completed");
        statusFilter.getItems().addAll("All", "Pending", "In Progress", "Completed");

//       set up the table columns   
        taskNameClm.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        taskDescriptionClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskAssignedClm.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        taskDueClm.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        taskPriorityClm.setCellValueFactory(new PropertyValueFactory<>("priority"));
        taskStatusClm.setCellValueFactory(new PropertyValueFactory<>("status"));


        taskStatusClm.setCellFactory(column -> new TableCell<Task, String>() {
            private Label label = new Label();
            private Circle circle = new Circle(10);

            {
                label.setGraphic(circle);
                label.setContentDisplay(ContentDisplay.LEFT);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    label.setText(item);
                    Color color;
                    switch (item) {
                        case "Pending":
                            color = Color.DARKORANGE;
                            break;
                        case "In Progress":
                            color = Color.BLUE;
                            break;
                        case "Completed":
                            color = Color.GREEN;
                            break;
                        default:
                            color = Color.GRAY;
                    }
                    circle.setFill(color);
                    label.setTextFill(color);
                    setGraphic(label);
                }
            }

        });

//        filter
        //create a new filtered list and allow all task data to pass through the filter
        filteredData = new FilteredList<>(taskData, p -> true);
        //add listener to combo box to trigger each time an item is selected

        statusFilter.getSelectionModel()
                .selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    //set condition for the filtered data
                    filteredData.setPredicate(task -> {
                        //if new selected value is null or All then display all tasks 
                        if (newValue == null || newValue.equals("All")) {
                            return true;
                        }
                        //show tasks matching the selected status
                        return task.getStatus().equals(newValue);
                    });
                }
                );

//        sort
        //create sorted list based on the flitered data
        sortedData = new SortedList<>(filteredData);
        //bind the sorting of sorteddata to the sorting of the table

        sortedData.comparatorProperty()
                .bind(tasksTable.comparatorProperty());
        //set the items to the sorted and filtered data 
        tasksTable.setItems(sortedData);

//        load existing tasks
        getTasks();

//        listen for click
        tasksTable.setOnMouseClicked(event
                -> {
            Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                showTaskDetails(selectedTask);
            }
        }
        );

    }

//   gather task details
    private Task gatherTaskDetails() {
        Task selectedTask = getSelectedTask();
        String taskId =  (selectedTask != null)?
            taskId = selectedTask.getTaskId(): null;
        
        return new Task(taskId,
                assignedToTxt.getText(),
                taskNameTxt.getText(),
                descriptionTextArea.getText(),
                startDatePicker.getValue(),
                dueDatePicker.getValue(),
                priorityDropdown.getValue(),
                statusDropdown.getValue(),
                userEmail
        );
    }

    //submit task data
    @FXML
    private void handleSaveTask() {
        Task task = gatherTaskDetails();
        if(vm.validateInputs(task)){
             boolean isNewTask = task.getTaskId()==null;
            System.out.println(isNewTask);
            boolean response;
            if(isNewTask){
    //         generate task Id
            task.setTaskId(task.generateTaskId());
             //save the data
            response = taskModel.submitTask(task);
            }else{
              response = taskModel.updateTask(task); 
            }
            System.out.println(response);
            if (response) {
                System.out.println(isNewTask ? "Task Saved Successfully" : "Task Updated Successfully");
                if(isNewTask){
                    //add task to oberservable list
                taskData.add(task);

                }else{
                   int index = getTaskIndex(task.getTaskId());
                   if(index != -1){
                       taskData.set(index,task);
                   }

                }
                 clearForm();
                showDashboard();

            } else {
                System.out.println("There was an error saving task");
            }
            
        }
       

    }
    private int getTaskIndex(String taskId){
        for(int i=0;i< taskData.size();i++){
                   if(taskData.get(i).getTaskId().equals(taskId)){
                       return i;
                   }
               }
              return -1;
    }

    //clear form after submitting
    private void clearForm() {
        assignedToTxt.clear();
        taskNameTxt.clear();
        descriptionTextArea.clear();
        priorityDropdown.setValue(null);
        dueDatePicker.setValue(null);
        startDatePicker.setValue(null);
        statusDropdown.setValue(null);

    }

    //get task data
    private void getTasks() {
        List<Task> tasksFromModel = taskModel.getTasksFromFile();
        //check if admin
        if (isAdmin) {
            taskData.setAll(tasksFromModel);
        } else {
            List<Task> userTasks = tasksFromModel.stream()
                    .filter(task -> task.getCreatedBy().equals(userEmail))
                    .collect(Collectors.toList());
            taskData.setAll(userTasks);
        }

    }

//    sort data in ascending order by priority
    @FXML
    private void sortAscending() {
        //clear any existing sort order
        tasksTable.getSortOrder().clear();
        //add task name column to the sort order
        tasksTable.getSortOrder().add(taskPriorityClm);
        //sort the task name in ascending order
        taskPriorityClm.setSortType(TableColumn.SortType.ASCENDING);
        //sort
        tasksTable.sort();
    }

//   sort data in descending order  by priority
    @FXML
    private void sortDescending() {
        tasksTable.getSortOrder().clear();
        tasksTable.getSortOrder().add(taskPriorityClm);
        taskPriorityClm.setSortType(TableColumn.SortType.DESCENDING);
        tasksTable.sort();
    }

    @FXML
    private void showDashboard() {
//      hide taskform 
        taskForm.setVisible(false);
//    show dashboard
        dashboard.setVisible(true);
        getTasks();

    }

    @FXML
    private void showTaskForm() {
//        show task form
        taskForm.setVisible(true);
//    show dashboard
        dashboard.setVisible(false);
    }

    //populate the task form with the task details
    private void showTaskDetails(Task task) {
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


    @FXML
    private void handleDeleteTask() {
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            taskModel.deleteTask(selectedTask.getTaskId());
            taskData.remove(selectedTask); // Update UI
            showDashboard();
        }
    }



    // edit task
    public void editTask(Task task) {
        if (task != null) {
            task.setAssignedTo(assignedToTxt.getText());
            task.setTaskName(taskNameTxt.getText());
            task.setDescription(descriptionTextArea.getText());
            task.setPriority(priorityDropdown.getValue());
            task.setDueDate(dueDatePicker.getValue());
            task.setStartDate(startDatePicker.getValue());
            task.setStatus(statusDropdown.getValue());
            taskModel.updateTask(task);
            tasksTable.refresh();
        }
    }

    // delete task
    public void deleteTask(Task task) {
        if (task != null) {
            taskModel.deleteTask(task.getTaskId());
            taskData.remove(task); // Update UI
        }
    }

}
