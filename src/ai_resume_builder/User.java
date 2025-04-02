package ai_resume_builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String password;  // This should be encrypted in real implementation
    private String name;
    private String email;
    private List<Resume> resumes;
    
    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;  // In real app, encrypt this
        this.name = name;
        this.email = email;
        this.resumes = new ArrayList<>();
    }
    
    public void addResume(Resume resume) {
        resumes.add(resume);
    }
    
    public void removeResume(Resume resume) {
        resumes.remove(resume);
    }
    
    public List<Resume> getResumes() {
        return new ArrayList<>(resumes);  // Return a copy to maintain encapsulation
    }
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // We don't provide a getter for password for security reasons
    public boolean verifyPassword(String inputPassword) {
        // In real app, decrypt stored password and compare
        return this.password.equals(inputPassword);
    }
    
    public void setPassword(String newPassword) {
        // In real app, encrypt before storing
        this.password = newPassword;
    }
}