package models;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Photo object
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";

    /**
     * File for image
     */
    public File image;
    /**
     * Tags of the photo
     */
    public HashMap<String, ArrayList<String>> tags;
    /**
     * Photo Caption
     */
    public String caption;

    /**
     * Date of Photo
     */
    public Date date;

    /**
     * Calendar to set date
     */
    private Calendar calendar;

    /**
     * Constructor for Photo
     * @param image, File of the photo
     * @param caption, photo caption
     */
    public Photo(File image, String caption){
        this.image = image;
        this.caption=caption;
        this.tags = new HashMap<String, ArrayList<String>>();
        calendar = new GregorianCalendar();
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
    }

    /**
     * Adds tag to photo
     * @param tag, key for tag
     * @param value, value for tag
     */
    public void addTag(String tag, String value){
        if (!tags.containsKey(tag)){
            ArrayList<String> values = new ArrayList<>();
            tags.put(tag, values);
        }
        tags.get(tag).add(value);
    }

    /**
     * Set caption for photo
     * @param caption, caption for photo
     */
    public void setCaption(String caption){
        this.caption=caption;
    }

    /**
     * Get caption for photo
     * @return photo caption
     */
    public String getCaption(){
        return caption;
    }

    /**
     * Get Date of photo
     * @return photo date
     */
    public Date getDate(){
        return date;
    }

    /**
     * Retrieves image file for image view
     * @return image file
     */
    public File getImage(){
        return image;
    }

    /**
     * Gets Tags
     * @return tags list
     */
    public HashMap<String, ArrayList<String>> getTags(){
        return tags;
    }
}
