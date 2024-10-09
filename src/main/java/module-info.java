module com.natasha.taskmanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.natasha.taskmanagementapp to javafx.fxml;
    exports com.natasha.taskmanagementapp;
}
