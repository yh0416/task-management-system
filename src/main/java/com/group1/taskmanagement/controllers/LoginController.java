package com.group1.taskmanagement.controllers;

import com.group1.taskmanagement.controllers.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
