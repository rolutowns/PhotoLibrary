package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.Main;
import models.Photo;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Login Screen
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class TagController implements Initializable {

    /**
     * Current Photo
     */
    public static Photo p;

    /**
     * Current User
     */
    public static User u;

    @FXML
    ListView<String> tagTypeList;

    @FXML
    TextField addTagValue;

    @FXML TextField addTagTypeText;

    /**
     * Adds tag to photo
     * @throws IOException
     */
    public void confirmAddTag() throws IOException {
        Main.setRoot("/resources/photoDisplay");
        String value = addTagValue.getText();
        if (tagTypeList.getSelectionModel().getSelectedItem()!=null){
            if (value==null || value.isEmpty()){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Tag Add Error");
                error.setContentText("No value specified");
                error.showAndWait();
                return;
            }
            p.addTag(tagTypeList.getSelectionModel().getSelectedItem(), value);
            Main.setRoot("/resources/photoDisplay");
            return;
        }
        String newTag = addTagTypeText.getText();
        if (newTag==null || newTag.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Tag Add Error");
            error.setContentText("No tag specified");
            error.showAndWait();
            return;
        }
        if (p.getTags().containsKey(newTag)){
            if (p.getTags().get(newTag).contains(value)){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Tag Add Error");
                error.setContentText("Tag already exists");
                error.showAndWait();
                return;
            }
        }
        p.addTag(newTag,value);
        if (u.getTagKeys()==null) u.initKeys();
        if (!u.getTagKeys().contains(newTag)) {
            u.getTagKeys().add(newTag);
            for (String s: u.getTagKeys()) System.out.println(s);
        }
        Main.setRoot("/resources/photoDisplay");
    }

    /**
     * Returns to photo display screen
     * @throws IOException
     */
    public void cancelAddTag() throws IOException {
        Main.setRoot("/resources/photoDisplay");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (u.getTagKeys()!=null) for (String s:u.getTagKeys()) tagTypeList.getItems().add(s);
    }
}
