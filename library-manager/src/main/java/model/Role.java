package main.java.model;

/**
 * Enum representing the roles of users in the library system.
 * The roles determine the level of access and permissions a user has within the system.
 */
public enum Role {
    /**
     * Represents an admin user with full access to all features and management capabilities.
     */
    ADMIN,

    /**
     * Represents a librarian user with limited access to library management features.
     */
    LIBRARIAN
}