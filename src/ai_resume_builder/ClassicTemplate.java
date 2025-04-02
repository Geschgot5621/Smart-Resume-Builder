package ai_resume_builder;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ClassicTemplate extends ResumeTemplate {
    
    public ClassicTemplate() {
        this.templateName = "Classic";
        this.primaryColor = "#000000";
        this.secondaryColor = "#333333";
        this.fontFamily = "Times New Roman, serif";
    }
    
    @Override
    public String formatHeader(PersonalInfo personalInfo) {
        return "<div style='text-align: center; color: " + primaryColor + "; font-family: " + fontFamily + ";'>" +
               "<h1 style='border-bottom: 1px solid #000; padding-bottom: 10px;'>" + personalInfo.getName() + "</h1>" +
               "<p>" + personalInfo.getEmail() + " | " + personalInfo.getPhone() + " | " + personalInfo.getLocation() + "</p>" +
               "<p>LinkedIn: " + personalInfo.getLinkedin() + " | GitHub: " + personalInfo.getGithub() + "</p>" +
               "</div>";
    }
    
    @Override
    public String formatEducation(List<Education> educationList) {
        StringBuilder result = new StringBuilder();
        result.append("<div style='margin-top: 20px;'>");
        result.append("<h2 style='color: " + secondaryColor + "; font-family: " + fontFamily + 
                     "; border-bottom: 1px solid #333; padding-bottom: 5px;'>EDUCATION</h2>");
        
        for (Education education : educationList) {
            result.append("<div style='margin-bottom: 15px;'>");
            result.append("<p style='margin-bottom: 5px;'><strong>" + education.getInstitution() + "</strong> | " + 
                         education.getDuration() + "</p>");
            result.append("<p style='margin-top: 0;'>" + education.getDegree() + " | GPA: " + education.getGpa() + "</p>");
            result.append("</div>");
        }
        
        result.append("</div>");
        return result.toString();
    }
    
    @Override
    public String formatExperience(List<Experience> experienceList) {
        StringBuilder result = new StringBuilder();
        result.append("<div style='margin-top: 20px;'>");
        result.append("<h2 style='color: " + secondaryColor + "; font-family: " + fontFamily + 
                     "; border-bottom: 1px solid #333; padding-bottom: 5px;'>PROFESSIONAL EXPERIENCE</h2>");
        
        for (Experience experience : experienceList) {
            result.append("<div style='margin-bottom: 15px;'>");
            result.append("<p style='margin-bottom: 5px;'><strong>" + experience.getCompany() + "</strong>, " + 
                         experience.getLocation() + " | " + experience.getDuration() + "</p>");
            result.append("<p style='margin-top: 0; font-style: italic;'>" + experience.getJobTitle() + "</p>");
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
        result.append("<h2 style='color: " + secondaryColor + "; font-family: " + fontFamily + 
                     "; border-bottom: 1px solid #333; padding-bottom: 5px;'>SKILLS</h2>");
        result.append("<p>");
        
        Iterator<String> iterator = skills.iterator();
        while (iterator.hasNext()) {
            result.append(iterator.next());
            if (iterator.hasNext()) {
                result.append(", ");
            }
        }
        
        result.append("</p>");
        result.append("</div>");
        return result.toString();
    }
}