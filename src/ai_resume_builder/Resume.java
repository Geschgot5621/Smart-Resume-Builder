package ai_resume_builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String title;
    private PersonalInfo personalInfo;
    private List<Education> educationList;
    private List<Experience> experienceList;
    private Set<String> skills;
    
    public Resume(String title) {
        this.title = title;
        this.educationList = new ArrayList<>();
        this.experienceList = new ArrayList<>();
        this.skills = new LinkedHashSet<>();  // Maintains insertion order while ensuring uniqueness
    }
    
    public void setPersonalInfo(String name, String email, String phone, 
                               String location, String linkedin, String github) {
        this.personalInfo = new PersonalInfo(name, email, phone, location, linkedin, github);
    }
    
    public void addEducation(Education education) {
        educationList.add(education);
    }
    
    public void removeEducation(Education education) {
        educationList.remove(education);
    }
    
    public void addExperience(Experience experience) {
        experienceList.add(experience);
    }
    
    public void removeExperience(Experience experience) {
        experienceList.remove(experience);
    }
    
    public void addSkill(String skill) {
        skills.add(skill);
    }
    
    public void removeSkill(String skill) {
        skills.remove(skill);
    }
    
    // Getters
    public String getTitle() {
        return title;
    }
    
    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }
    
    public List<Education> getEducationList() {
        return new ArrayList<>(educationList);  // Return a copy
    }
    
    public List<Experience> getExperienceList() {
        return new ArrayList<>(experienceList);  // Return a copy
    }
    
    public Set<String> getSkills() {
        return new LinkedHashSet<>(skills);  // Return a copy
    }
    
    // Setter
    public void setTitle(String title) {
        this.title = title;
    }
}