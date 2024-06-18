package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import models.Admin;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import main.Main;
/**
 * Controller for Admin user
 * Implements functionality for adding and removing users
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class AdminController implements Initializable {

        @FXML
    ListView<User> userListView = new ListView<User>();

    /**
     * Logs out of the admin
     * @throws IOException
     */
    public void logout() throws IOException {
        Main.setRoot("/resources/login");
    }

    /**
     * Allows Admin to add a user when button is pressed
     * @throws IOException
     */
    public void addUser() throws  IOException {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Add User");
        td.showAndWait();
        String username = td.getEditor().getText().trim().toLowerCase();
        if (username==null || username.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Adding User");
            error.setContentText("Must add a username");
            error.showAndWait();
            return;
        }
        if(username.equals("admin")){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Adding User");
            error.setContentText("Cannot add admin");
            error.showAndWait();
            return;
        }
        for (User u:Admin.users){
            if (u.getUsername().equals(username)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error Adding User");
                error.setContentText("User already exists");
                error.showAndWait();
                return;
            }
        }
        User u = new User(username);
        Admin.addUser(u);
        userListView.getItems().add(u);
    }

    /**
     * Deletes selected user from application
     * @throws IOException
     */
    public void deleteUser() throws IOException {
        User u = userListView.getSelectionModel().getSelectedItem();
        userListView.getItems().remove(u);
        Admin.deleteUser(u);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {for (User a: Admin.users) userListView.getItems().add(a);}
}
