package models;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Admin user to add and delete other users, username of admin
 * @author Krish Patel
 * @author Roshan Varadhan
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";

    /**
     * List of Users
     */
    public static ArrayList<User> users = new ArrayList<User>();

    /**
     * Adds a user to the list of users
     * @param u, user to be added
     */
    public static void addUser(User u){users.add(u);}

    /**
     * Removes a users from the list of users
     * @param u, user to be removed
     */
    public static void deleteUser(User u){users.remove(u);}
}
