package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;
import models.Album;
import models.Photo;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for Photo View Screen
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class PhotoController implements Initializable {

    /**
     * Current Photo
     */
    public static Photo p;

    /**
     * Album that photo belongs to
     */
    public static Album a;

    /**
     * Current User
     */
    public static User u;

    @FXML
    ImageView selectedImage;

    @FXML
    Label selectedCaption;

    @FXML
    ListView<String> tagListView;

    @FXML Label selectedDate;

    /**
     * Returns to album view
     * @throws IOException
     */
    public void back() throws IOException {
        Main.setRoot("/resources/albumView");
    }

    /**
     * Changes photo caption
     * @throws IOException
     */
    public void recaption() throws IOException {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Recaption");
        td.showAndWait();
        String newCaption = td.getEditor().getText();
        if (newCaption==null || newCaption.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error Editing Photo");
            error.setContentText("Must add a caption");
            error.showAndWait();
            return;
        }
        p.setCaption(newCaption);
        Main.setRoot("/resources/photoDisplay");
    }

    /**
     * Opens add tag screen for photo
     * @throws IOException
     */
    public void addTag() throws IOException{
        TagController.p=p;
        TagController.u=u;
        Main.setRoot("/resources/addTag");
    }

    /**
     * Deletes selected tag
     * @throws IOException
     */
    public void deleteTag() throws IOException{
        String s = tagListView.getSelectionModel().getSelectedItem();
        String fields[]=s.split("\\|");
        ArrayList<String> temp = p.getTags().get(fields[0]);
        temp.remove(fields[1]);
        Main.setRoot("/resources/photoDisplay");
    }

    /**
     * Next image in slideshow
     * @throws IOException
     */
    public void nextImage() throws IOException{
        int curr = a.getPhotos().indexOf(p);
        int next;
        if (curr == a.getPhotos().size()-1)next=0;
        else next=curr+1;
        PhotoController.p=a.getPhotos().get(next);
        Main.setRoot("/resources/photoDisplay");
    }

    /**
     * Previous image in slideshow
     * @throws IOException
     */
    public void prevImage() throws IOException{
        int curr = a.getPhotos().indexOf(p);
        int prev;
        if (curr == 0)prev=a.getPhotos().size()-1;
        else prev=curr-1;
        PhotoController.p=a.getPhotos().get(prev);
        Main.setRoot("/resources/photoDisplay");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedCaption.setText(p.getCaption());
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(p.getImage().getAbsolutePath());
        Image image = new Image(p.getImage().toURI().toString());
        selectedImage.setImage(image);
        selectedImage.setPreserveRatio(true);
        selectedDate.setText(sdformat.format(p.getDate()));
        for (String key: p.getTags().keySet()) {
            ArrayList<String> values = p.getTags().get(key);
            for (String s:values) tagListView.getItems().add(key + "|" + s);
        }
    }
}
