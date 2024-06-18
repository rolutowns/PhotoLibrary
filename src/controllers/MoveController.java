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
 * Controller for Move Photo Screen
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class MoveController implements Initializable {

    /**
     * Current user
     */
    public static User user;
    /**
     * Source album
     */
    public static Album currAlbum;

    /**
     * Photo to be moved
     */
    public static Photo photo;
    @FXML
    ComboBox<Album> moveDestAlbum;

    /**
     * Moves photo to new album if possible
     * @throws IOException
     */
    public void confirmMove() throws IOException{
        AlbumController.user = user;
        Album dest = moveDestAlbum.getValue();
        if (dest==null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Photo Move Error");
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
     * Cancels moving photo
     * @throws IOException
     */
    public void cancelMove() throws IOException{
        currAlbum.addPhoto(photo);
        Main.setRoot("/resources/albumView");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Album> convert = FXCollections.observableArrayList(user.getAlbums());
        moveDestAlbum.getItems().addAll(convert);
    }
}
