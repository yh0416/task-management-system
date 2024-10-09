package com.natasha.taskmanagementapp;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    
    @FXML
    private void switchToNewPage() throws IOException {
        App.setRoot("login");
    }
}