package controllers;

import java.io.IOException;
import javafx.fxml.FXML;

public class TaskController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    
    @FXML
    private void switchToNewPage() throws IOException {
        App.setRoot("login");
    }
}