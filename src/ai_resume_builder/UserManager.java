package ai_resume_builder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Map<String, User> users;
    
    public UserManager() {
        this.users = new HashMap<>();
    }
    
    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }
    
    public User getUser(String userId) {
        return users.get(userId);
    }
    
    public boolean removeUser(String userId) {
        return users.remove(userId) != null;
    }
    
    public boolean authenticateUser(String userId, String password) {
        User user = users.get(userId);
        if (user != null) {
            return user.verifyPassword(password);
        }
        return false;
    }
    
    public Map<String, User> getAllUsers() {
        return new HashMap<>(users);  // Return a copy for encapsulation
    }
}