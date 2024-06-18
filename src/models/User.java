package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Admin user to add and delete other users, username of admin
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";

    /**
     * Username
     */
    public String username;

    /**
     * User albums
     */
    public ArrayList<Album> albums;

    /**
     * All tag keys used by user so far
     */
    public ArrayList<String> tagKeys;

    /**
     * User constructor
     * @param username, username for user
     */
    public User(String username){
        this.username=username;
    }

    /**
     * Gets username
     * @return username
     */
    public String getUsername(){return username;}

    /**
     * Gets user tag keys
     * @return user tag keys
     */
    public ArrayList<String> getTagKeys(){return tagKeys;}

    /**
     * Gets albums
     * @return albums
     */
    public ArrayList<Album> getAlbums(){return albums;}

    /**
     * Initializes albums array list if not initialized yet
     */
    public void initAlbums(){albums = new ArrayList<Album>();}

    /**
     * Adds album to user
     * @param a, album to be added
     * @return boolean if album can be added
     */
    public boolean addAlbum(Album a){
        if (albums.contains(a)) return false;
        albums.add(a);
        return true;
    }

    @Override
    public String toString(){return username;}

    /**
     * Initializes keys if not yet initialized
     */
    public void initKeys() {tagKeys = new ArrayList<String>();
    }
}
