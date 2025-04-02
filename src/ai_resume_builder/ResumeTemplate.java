package ai_resume_builder;

import java.util.List;
import java.util.Set;

public abstract class ResumeTemplate {
    protected String templateName;
    protected String primaryColor;
    protected String secondaryColor;
    protected String fontFamily;
    
    public abstract String formatHeader(PersonalInfo personalInfo);
    public abstract String formatEducation(List<Education> educationList);
    public abstract String formatExperience(List<Experience> experienceList);
    public abstract String formatSkills(Set<String> skills);
    
    public String getTemplateName() {
        return templateName;
    }
    
    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }
    
    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
    
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }
}