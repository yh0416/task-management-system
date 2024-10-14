module com.group1.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.group1.taskmanagement.controllers to javafx.fxml;
    exports com.group1.taskmanagement.controllers;
    exports com.group1.taskmanagement;

    
}
