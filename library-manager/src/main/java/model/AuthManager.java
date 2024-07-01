package main.java.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.util.PasswordUtils;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages user authentication and user management operations.
 *
 * <p>
 * This class follows the Singleton pattern to ensure only one instance of
 * AuthManager exists. It handles user authentication, addition, deletion,
 * and persistence of user data.
 * </p>
 *
 * @see User
 * @see Role
 * @see PasswordUtils
 */
public class AuthManager {
    private static AuthManager instance;
    private List<User> users;
    private Gson gson;
    private static final Logger logger = Logger.getLogger(AuthManager.class.getName());

    /**
     * Private constructor to prevent instantiation.
     * Initializes the user list and loads users from a JSON file.
     */
    private AuthManager() {
        users = new ArrayList<>();
        gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserAdapter())
                .setPrettyPrinting()
                .create();
        loadUsers();
    }

    /**
     * Returns the singleton instance of AuthManager.
     *
     * @return the instance of AuthManager
     */
    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    /**
     * Returns the list of users.
     *
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Adds a new user to the system. The username must be unique.
     *
     * @param user the user to be added
     * @return true if the user was added successfully, false if the username already exists
     */
    public boolean addUser(User user) {
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                logger.log(Level.WARNING, "Username already exists: " + user.getUsername());
                return false; // Username already exists
            }
        }
        // Encrypt the password before storing
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        users.add(user);
        saveUsers();
        logger.log(Level.INFO, "User added: " + user.getUsername());
        return true;
    }

    /**
     * Deletes a user by username.
     *
     * @param username the username of the user to be deleted
     */
    public void deleteUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
        saveUsers();
        logger.log(Level.INFO, "User deleted: " + username);
    }

    /**
     * Authenticates a user by username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the authenticated user, or null if authentication fails
     */
    public User authenticate(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(hashedPassword)) {
                logger.log(Level.INFO, "User authenticated: " + username);
                return user;
            }
        }
        logger.log(Level.WARNING, "Authentication failed for user: " + username);
        return null;
    }

    /**
     * Saves the list of users to a JSON file.
     */
    public void saveUsers() {
        try (Writer writer = new FileWriter("src/main/resources/users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving users", e);
        }
    }

    /**
     * Loads the list of users from a JSON file.
     */
    public void loadUsers() {
        try (Reader reader = new FileReader("src/main/resources/users.json")) {
            User[] loadedUsers = gson.fromJson(reader, User[].class);
            if (loadedUsers != null) {
                users.clear();
                users.addAll(Arrays.asList(loadedUsers));
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading users", e);
        }
    }
}