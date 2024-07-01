package main.java.model;

/**
 * Represents an abstract user in the library system.
 * This class serves as the base class for different types of users, such as admins and librarians.
 * It includes common attributes and methods for handling user information.
 *
 * @see Admin
 * @see Librarian
 */
public abstract class User {
    private String username;
    private String password;
    private Role role;

    /**
     * Constructs a User object with the specified username, password, and role.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of the user.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }
}