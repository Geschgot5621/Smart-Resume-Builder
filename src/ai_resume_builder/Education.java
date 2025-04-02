package ai_resume_builder;

import java.io.Serializable;

public class Education implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String degree;
    private String institution;
    private String duration;
    private double gpa;
    
    public Education(String degree, String institution, String duration, double gpa) {
        this.degree = degree;
        this.institution = institution;
        this.duration = duration;
        this.gpa = gpa;
    }
    
    // Getters and setters
    public String getDegree() {
        return degree;
    }
    
    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    public String getInstitution() {
        return institution;
    }
    
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}