package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import models.Album;
import models.Photo;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import main.Main;

/**
 * Controller for Copying photos between albums
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class CopyController implements Initializable {

    /**
     * current user
     */
    public static User user;

    /**
     * photo to be copied
     */
    public static Photo photo;
    @FXML
    ComboBox<Album> destCopyAlbum;

    /**
     * Copys photo to new album if possible
     * @throws IOException
     */
    public void confirmCopy() throws IOException{
        AlbumController.user = user;
        Album dest = destCopyAlbum.getValue();
        if (dest==null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Photo Copy Error");
            error.setContentText("No album Selected");
            error.showAndWait();
            return;
        }
        boolean added = dest.addPhoto(photo);
        if (!added){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Adding Photo");
            error.setContentText("Photo is already in this album");
            error.showAndWait();
            return;
        }
        AlbumController.album = dest;
        Main.setRoot("/resources/albumView");
    }

    /**
     * returns to album view
     * @throws IOException
     */
    public void cancelCopy() throws IOException{
        Main.setRoot("/resources/albumView");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Album> convert = FXCollections.observableArrayList(user.getAlbums());
        destCopyAlbum.getItems().addAll(convert);
    }
}
