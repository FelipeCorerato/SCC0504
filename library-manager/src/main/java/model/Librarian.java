package main.java.model;

public class Librarian extends User {
    public Librarian(String username, String password) {
        super(username, password, Role.LIBRARIAN);
    }

    @Override
    public Role getRole() {
        return Role.LIBRARIAN;
    }
}