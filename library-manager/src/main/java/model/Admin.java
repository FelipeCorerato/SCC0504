package main.java.model;

/**
 * Represents an administrator user in the library system.
 * Extends the {@link User} class and assigns the role as ADMIN.
 *
 * <p>
 * This class is used to create admin users who have higher privileges
 * compared to regular librarian users.
 * </p>
 *
 * @see User
 * @see Role
 */
public class Admin extends User {

    /**
     * Constructs an Admin object with the specified username and password.
     * The role is set to ADMIN.
     *
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }

    /**
     * Returns the role of the admin, which is always ADMIN.
     *
     * @return the role of the admin
     */
    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}