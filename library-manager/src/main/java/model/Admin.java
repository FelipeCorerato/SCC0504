package main.java.model;

public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}