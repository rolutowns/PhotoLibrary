package models;

import java.io.Serializable;
import java.util.*;
/**
 * Photo Album object
 * Contains an Array of photos
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";

    /**
     * Name of Album
     */
    public String name;
    /**
     * ArrayList containing all photos in the album
     */
    public ArrayList<Photo> photos;

    /**
     * Constructer for an Album
     * Initializes a new array list for photos
     * @param name, name of the album
     */
    public Album(String name){
        this.name=name;
        photos=new ArrayList<Photo>();
    }

    /**
     * Returns the name of the album
     * @return name of current album
     */
    public String getName(){return name;}

    /**
     * Changes the name of the album
     * @param name, name to be renamed to
     */
    public void setName(String name){this.name=name;}

    /**
     * returns the photos in the album
     * @return Arraylist of photos in album
     */
    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    /**
     * Finds the earliest date of any photo in the album
     * @return The earliest date of the album
     */
    public Date getEarliestDate(){
        if (photos.size()==0) return null;
        if (photos.get(0)==null) return null;
        Date x = photos.get(0).date;
        for (int i=1; i<photos.size();i++) if (photos.get(i).date.compareTo(x)<0) x=photos.get(i).date;
        return x;
    }

    /**
     * Finds the latest date of any photo in the album
     * @return The latest date of the album
     */
    public Date getLatestDate(){
        if (photos.size()==0) return null;
        if (photos.get(0)==null) return null;
        Date x = photos.get(0).date;
        for (int i=1; i<photos.size();i++) if (photos.get(i).date.compareTo(x)>0) x=photos.get(i).date;
        return x;
    }

    /**
     * Adds photo to the album
     * @param p, photo to be added
     * @return boolean if photo can be added
     */
    public boolean addPhoto(Photo p){
        for (Photo a:photos){
            if (p.getImage().equals(a.getImage())) return false;
        }
        photos.add(p);
        return true;
    }

    /**
     * Removes photo from the album
     * @param p, photo to be removed
     */
    public void deletePhoto(Photo p){photos.remove(p);}

    @Override
    public String toString(){return name;}
}
