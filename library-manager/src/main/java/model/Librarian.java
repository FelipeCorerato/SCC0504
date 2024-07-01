package main.java.model;

/**
 * Represents a librarian user in the library system.
 * Extends the {@link User} class and assigns the role as LIBRARIAN.
 *
 * <p>
 * This class is used to create librarian users who have certain privileges
 * compared to regular users or admin users.
 * </p>
 *
 * @see User
 * @see Role
 */
public class Librarian extends User {

    /**
     * Constructs a Librarian object with the specified username and password.
     * The role is set to LIBRARIAN.
     *
     * @param username the username of the librarian
     * @param password the password of the librarian
     */
    public Librarian(String username, String password) {
        super(username, password, Role.LIBRARIAN);
    }

    /**
     * Returns the role of the librarian, which is always LIBRARIAN.
     *
     * @return the role of the librarian
     */
    @Override
    public Role getRole() {
        return Role.LIBRARIAN;
    }
}