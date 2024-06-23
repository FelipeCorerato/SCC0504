import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, User> users;

    public AuthManager() {
        users = new HashMap<>();
        // Add default users
        users.put("admin", new User("admin", "admin123", "admin"));
        users.put("librarian", new User("librarian", "lib123", "librarian"));
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void addUser(String username, String password, String role) {
        users.put(username, new User(username, password, role));
    }

    public User getUser(String username) {
        return users.get(username);
    }
}
