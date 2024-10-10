package com.group1.taskmanagement.controllers;

import com.group1.taskmanagement.controllers.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class TaskController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}