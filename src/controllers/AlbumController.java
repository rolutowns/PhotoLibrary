package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Album;
import models.Photo;
import models.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import main.Main;

/**
 * Controller for the Album View Screen
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class AlbumController implements Initializable {

    /**
     * current album
     */
    public static Album album;
    /**
     * current user that the album belongs to
     */
    public static User user;

    @FXML ListView<Photo> photosListView = new ListView<Photo>();

    @FXML
    Label photoDates;
    @FXML
    Label albumNameLabel;

    @FXML Label numPhotos;

    /**
     * returns to main screen
     * @throws IOException
     */
    public void back() throws IOException{
        Main.setRoot("/resources/home");
    }

    /**
     * Adds photo to album
     * @param event, button pressed, used to find where to open popup
     * @throws IOException
     */
    public void addPhoto(ActionEvent event) throws IOException{
        Node buttonHome = (Node) event.getSource();
        FileChooser openFile = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp");
        openFile.getExtensionFilters().add(imageFilter);
        openFile.setTitle("Select Image");
        File photoFile = openFile.showOpenDialog(buttonHome.getScene().getWindow());
        if (photoFile==null) return;
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Set Caption");
        td.showAndWait();
        String caption = td.getEditor().getText();
        if (caption == null || caption.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Adding Photo");
            error.setContentText("Must add a caption");
            error.showAndWait();
            return;
        }
        Photo newPhoto = new Photo(photoFile, caption);
        boolean added = album.addPhoto(newPhoto);
        if (!added){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Adding Photo");
            error.setContentText("Photo is already in this album");
            error.showAndWait();
            return;
        }
        photosListView.getItems().add(newPhoto);
        Main.setRoot("/resources/albumView");
    }

    /**
     * Opens search screen
     * @throws IOException
     */
    public void startSearch() throws IOException{
        SearchController.album=album;
        Main.setRoot("/resources/searchScreen");
    }

    /**
     * Opens photo display screen for selected photo
     * @throws IOException
     */
    public void openPhoto() throws IOException{
        Photo p = photosListView.getSelectionModel().getSelectedItem();
        if (p==null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Photo Open Error");
            error.setContentText("No Photo Selected");
            error.showAndWait();
            return;
        }
        PhotoController.p = p;
        PhotoController.a = album;
        PhotoController.u = user;
        Main.setRoot("/resources/photoDisplay");
    }

    /**
     * deletes selected photo
     * @throws IOException
     */
    public void deletePhoto() throws IOException{
        Photo p = photosListView.getSelectionModel().getSelectedItem();
        photosListView.getItems().remove(p);
        album.deletePhoto(p);
        Main.setRoot("/resources/albumView");
    }

    /**
     * Opens move screen to move photo to separate album
     * @throws IOException
     */
    public void movePhoto() throws IOException{
        MoveController.user = user;
        MoveController.currAlbum=album;
        Photo p = photosListView.getSelectionModel().getSelectedItem();
        if (p==null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Photo Move Error");
            error.setContentText("No Photo Selected");
            error.showAndWait();
            return;
        }
        MoveController.photo=p;
        album.deletePhoto(p);
        Main.setRoot("/resources/movePhoto");
    }

    /**
     * Opens copy screen to copy photo to new album
     * @throws IOException
     */
    public void copyPhoto() throws IOException{
        CopyController.user = user;
        Photo p = photosListView.getSelectionModel().getSelectedItem();
        if (p==null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Photo Copy Error");
            error.setContentText("No Photo Selected");
            error.showAndWait();
            return;
        }
        CopyController.photo=p;
        Main.setRoot("/resources/copyPhoto");
    }

    /**
     * Creates a new album from the photos being shown
     * @throws IOException
     */
    public void createFromResults() throws IOException{
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Create New Album");
        td.showAndWait();
        String newName = td.getEditor().getText();
        for (Album a: user.getAlbums()){
            if (a.getName().toLowerCase().equals(newName.toLowerCase())){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Album Add Error");
                error.setContentText("This album already exists");
                error.showAndWait();
                return;
            }
        }
        Album a = new Album(newName);
        for (Photo p:album.getPhotos()) a.addPhoto(p);
        boolean added = user.addAlbum(a);
        if (!added){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Creating Album");
            error.setContentText("Album already exists");
            error.showAndWait();
            return;
        }
        AlbumController.album=a;
        Main.setRoot("/resources/albumView");
    }

    /**
     * Updates album view to display attributes of the album
     */
    private void showAlbum(){
        albumNameLabel.setText(album.name);
        Date earliest = album.getEarliestDate();
        Date latest = album.getLatestDate();
        if (earliest != null && latest != null) {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
            photoDates.setText(sdformat.format(earliest) + "-" + sdformat.format(latest));
        }
        numPhotos.setText(photosListView.getItems().size() + " Photos");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Photo p: album.getPhotos()) {
            photosListView.setCellFactory(par -> new ListCell<Photo>() {
                ImageView thumbnail;
                {
                    try {
                        Image image = new Image(p.getImage().toURI().toString());
                        thumbnail = new ImageView(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void updateItem(Photo p, boolean empty) {
                    super.updateItem(p, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            thumbnail.setImage(new Image(p.getImage().toURI().toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        thumbnail.setFitHeight(100);
                        thumbnail.setFitWidth(100);
                        setText(p.getCaption());
                        setGraphic(thumbnail);
                    }
                }
            });
            photosListView.getItems().add(p);
        }
        showAlbum();
    }
}
