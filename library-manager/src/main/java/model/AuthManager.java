package main.java.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.util.PasswordUtils;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthManager {
    private static AuthManager instance;
    private List<User> users;
    private Gson gson;
    private static final Logger logger = Logger.getLogger(AuthManager.class.getName());

    private AuthManager() {
        users = new ArrayList<>();
        gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserAdapter())
                .setPrettyPrinting()
                .create();
        loadUsers();
    }

    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                logger.log(Level.WARNING, "Username already exists: " + user.getUsername());
                return false; // Username already exists
            }
        }
        // Criptografa a senha antes de armazenar
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        users.add(user);
        saveUsers();
        logger.log(Level.INFO, "User added: " + user.getUsername());
        return true;
    }

    public void deleteUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
        saveUsers();
        logger.log(Level.INFO, "User deleted: " + username);
    }

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

    public void saveUsers() {
        try (Writer writer = new FileWriter("src/main/resources/users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving users", e);
        }
    }

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