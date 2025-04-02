package ai_resume_builder;

import java.io.Serializable;

public class PersonalInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String email;
    private String phone;
    private String location;
    private String linkedin;
    private String github;
    
    public PersonalInfo(String name, String email, String phone, 
                      String location, String linkedin, String github) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.linkedin = linkedin;
        this.github = github;
    }
    
    // Getters and setters
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
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getLinkedin() {
        return linkedin;
    }
    
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
    
    public String getGithub() {
        return github;
    }
    
    public void setGithub(String github) {
        this.github = github;
    }
}