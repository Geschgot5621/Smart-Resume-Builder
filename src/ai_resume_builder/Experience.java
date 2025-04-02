package ai_resume_builder;

import java.io.Serializable;

public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String jobTitle;
    private String company;
    private String duration;
    private String location;
    private String description;
    
    public Experience(String jobTitle, String company, String duration, 
                    String location, String description) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.duration = duration;
        this.location = location;
        this.description = description;
    }
    
    // Getters and setters
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}