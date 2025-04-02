package ai_resume_builder;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ModernTemplate extends ResumeTemplate {
    
    public ModernTemplate() {
        this.templateName = "Modern";
        this.primaryColor = "#2c3e50";
        this.secondaryColor = "#3498db";
        this.fontFamily = "Helvetica, Arial, sans-serif";
    }
    
    @Override
    public String formatHeader(PersonalInfo personalInfo) {
        return "<div style='text-align: center; color: " + primaryColor + "; font-family: " + fontFamily + ";'>" +
               "<h1>" + personalInfo.getName() + "</h1>" +
               "<p>" + personalInfo.getEmail() + " | " + personalInfo.getPhone() + " | " + personalInfo.getLocation() + "</p>" +
               "<p>LinkedIn: " + personalInfo.getLinkedin() + " | GitHub: " + personalInfo.getGithub() + "</p>" +
               "</div>";
    }
    
    @Override
    public String formatEducation(List<Education> educationList) {
        StringBuilder result = new StringBuilder();
        result.append("<div style='margin-top: 20px;'>");
        result.append("<h2 style='color: " + secondaryColor + "; font-family: " + fontFamily + ";'>Education</h2>");
        
        for (Education education : educationList) {
            result.append("<div style='margin-bottom: 15px;'>");
            result.append("<h3 style='margin-bottom: 5px;'>" + education.getDegree() + "</h3>");
            result.append("<p style='margin-top: 0;'><strong>" + education.getInstitution() + "</strong> | " + 
                         education.getDuration() + " | GPA: " + education.getGpa() + "</p>");
            result.append("</div>");
        }
        
        result.append("</div>");
        return result.toString();
    }
    
    @Override
    public String formatExperience(List<Experience> experienceList) {
        StringBuilder result = new StringBuilder();
        result.append("<div style='margin-top: 20px;'>");
        result.append("<h2 style='color: " + secondaryColor + "; font-family: " + fontFamily + ";'>Professional Experience</h2>");
        
        for (Experience experience : experienceList) {
            result.append("<div style='margin-bottom: 15px;'>");
            result.append("<h3 style='margin-bottom: 5px;'>" + experience.getJobTitle() + "</h3>");
            result.append("<p style='margin-top: 0;'><strong>" + experience.getCompany() + "</strong> | " + 
                         experience.getDuration() + " | " + experience.getLocation() + "</p>");
            result.append("<p>" + experience.getDescription().replace("\n", "<br>") + "</p>");
            result.append("</div>");
        }
        
        result.append("</div>");
        return result.toString();
    }
    
    @Override
    public String formatSkills(Set<String> skills) {
        StringBuilder result = new StringBuilder();
        result.append("<div style='margin-top: 20px;'>");
        result.append("<h2 style='color: " + secondaryColor + "; font-family: " + fontFamily + ";'>Skills</h2>");
        result.append("<p>");
        
        Iterator<String> iterator = skills.iterator();
        while (iterator.hasNext()) {
            result.append(iterator.next());
            if (iterator.hasNext()) {
                result.append(" • ");
            }
        }
        
        result.append("</p>");
        result.append("</div>");
        return result.toString();
    }
}