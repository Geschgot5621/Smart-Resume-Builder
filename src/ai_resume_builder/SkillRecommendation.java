package ai_resume_builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class SkillRecommendation {
    private Map<String, List<String>> jobSkillsMap;
    
    public SkillRecommendation() {
        jobSkillsMap = new HashMap<>();
        initializeSkillDatabase();
    }
    
    private void initializeSkillDatabase() {
        // Software Engineering related skills
        List<String> softwareEngineerSkills = Arrays.asList(
            "Java", "Python", "JavaScript", "SQL", "Git", "Docker",
            "CI/CD", "AWS", "Data Structures", "Algorithms", "RESTful APIs",
            "Spring Boot", "React", "Angular", "Node.js", "Microservices"
        );
        
        // Data Science related skills
        List<String> dataScientistSkills = Arrays.asList(
            "Python", "R", "SQL", "Machine Learning", "Deep Learning", "TensorFlow",
            "PyTorch", "Data Visualization", "Statistics", "Big Data", "Pandas",
            "NumPy", "Scikit-learn", "Natural Language Processing", "Computer Vision"
        );
        
        // Web Development related skills
        List<String> webDeveloperSkills = Arrays.asList(
            "HTML", "CSS", "JavaScript", "React", "Angular", "Vue.js",
            "Node.js", "Express.js", "MongoDB", "Responsive Design", "SEO",
            "WordPress", "PHP", "Ruby on Rails", "GraphQL", "REST APIs"
        );
        
        // Project Management related skills
        List<String> projectManagerSkills = Arrays.asList(
            "Agile Methodologies", "Scrum", "Kanban", "JIRA", "MS Project",
            "Risk Management", "Budgeting", "Stakeholder Management",
            "Team Leadership", "Project Planning", "PRINCE2", "PMP", "Resource Allocation"
        );
        
        // Add more job roles as needed
        jobSkillsMap.put("Software Engineer", softwareEngineerSkills);
        jobSkillsMap.put("Data Scientist", dataScientistSkills);
        jobSkillsMap.put("Web Developer", webDeveloperSkills);
        jobSkillsMap.put("Project Manager", projectManagerSkills);
    }
    
    public List<String> getRecommendedSkills(String jobRole) {
        List<String> skills = jobSkillsMap.get(jobRole);
        if (skills == null) {
            // If job role not found in database, return generic skills
            skills = Arrays.asList(
                "Communication", "Teamwork", "Problem Solving", 
                "Time Management", "Critical Thinking", "Adaptability"
            );
        }
        return skills;
    }
    
    // Method to get skill recommendations based on job description
    public List<String> getRecommendedSkillsByDescription(String jobDescription) {
        // In a real AI implementation, this would use NLP to analyze the job description
        // and recommend skills. For now, we'll use a simple keyword-based approach.
        List<String> recommendedSkills = new ArrayList<>();
        
        // Convert to lowercase for case-insensitive matching
        String lowerCaseDescription = jobDescription.toLowerCase();
        
        // Check for software engineering keywords
        if (lowerCaseDescription.contains("java") || 
            lowerCaseDescription.contains("software engineer") || 
            lowerCaseDescription.contains("develop")) {
            recommendedSkills.addAll(Arrays.asList("Java", "Spring Boot", "Git", "RESTful APIs"));
        }
        
        // Check for data science keywords
        if (lowerCaseDescription.contains("data") || 
            lowerCaseDescription.contains("analytics") || 
            lowerCaseDescription.contains("machine learning")) {
            recommendedSkills.addAll(Arrays.asList("Python", "Data Analysis", "SQL", "Machine Learning"));
        }
        
        // Check for web development keywords
        if (lowerCaseDescription.contains("web") || 
            lowerCaseDescription.contains("frontend") || 
            lowerCaseDescription.contains("front-end")) {
            recommendedSkills.addAll(Arrays.asList("HTML", "CSS", "JavaScript", "React"));
        }
        
        // Add generic professional skills
        recommendedSkills.addAll(Arrays.asList("Communication", "Problem Solving", "Team Collaboration"));
        
        // Remove duplicates and return
        return new ArrayList<>(new LinkedHashSet<>(recommendedSkills));
    }
}