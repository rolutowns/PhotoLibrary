package controllers;

import java.io.IOException;


import main.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Admin;
import models.User;

/**
 * Controller for Login Screen
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class LoginController {

    /**
     * Username
     */
    public static String name;
    @FXML private TextField loginName;
    @FXML
    ListView<User> userListView;

    /**
     * Logs in to admin or user system depending on username
     * @throws IOException
     */
    @FXML
    private void login() throws IOException {
        name = loginName.getText().trim();
        if (name.equals("admin")){
            Main.setRoot("/resources/admin");
            return;
        }
        for (User u:Admin.users){
            if (u.getUsername().equals(name)) {
                HomeController.user=u;
                if (u.getAlbums() == null) u.initAlbums();
                Main.setRoot("/resources/home");
                return;
            }
        }
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("No User Found");
        error.setContentText("This user does not exist");
        error.showAndWait();
        loginName.setText("");
    }
}
