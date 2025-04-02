package ai_resume_builder;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ResumeBuilder {
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static User currentUser = null;
    private static Resume currentResume = null;
    private static FileHandler fileHandler = new FileHandler();
    
    public static void main(String[] args) {
        System.out.println("Welcome to AI-Powered Smart Resume Builder!");
        
        // Try to load existing user data
        try {
            userManager = fileHandler.loadUserData();
            System.out.println("Existing user data loaded successfully.");
        } catch (Exception e) {
            System.out.println("No existing user data found. Starting with a fresh database.");
        }
        
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                displayLoginMenu();
            } else if (currentResume == null) {
                displayResumeMenu();
            } else {
                displayEditResumeMenu();
            }
            
            // Save user data after each operation
            try {
                fileHandler.saveUserData(userManager);
            } catch (IOException e) {
                System.err.println("Error saving user data: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("Thank you for using AI-Powered Smart Resume Builder!");
    }
    
    private static void displayLoginMenu() {
        System.out.println("\n===== Login Menu =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                loginUser();
                break;
            case 2:
                registerUser();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void displayResumeMenu() {
        System.out.println("\n===== Resume Menu =====");
        System.out.println("Welcome, " + currentUser.getName() + "!");
        System.out.println("1. Create New Resume");
        
        List<Resume> resumes = currentUser.getResumes();
        if (!resumes.isEmpty()) {
            System.out.println("2. Select Existing Resume");
            System.out.println("3. Delete Resume");
        }
        
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                createNewResume();
                break;
            case 2:
                if (!resumes.isEmpty()) {
                    selectExistingResume();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
                break;
            case 3:
                if (!resumes.isEmpty()) {
                    deleteResume();
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
                break;
            case 4:
                logoutUser();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void displayEditResumeMenu() {
        System.out.println("\n===== Edit Resume Menu =====");
        System.out.println("Resume: " + currentResume.getTitle());
        System.out.println("1. Edit Personal Information");
        System.out.println("2. Add/Edit Education");
        System.out.println("3. Add/Edit Experience");
        System.out.println("4. Manage Skills");
        System.out.println("5. Generate AI Skill Recommendations");
        System.out.println("6. Export to PDF");
        System.out.println("7. Save and Exit Resume");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                editPersonalInfo();
                break;
            case 2:
                manageEducation();
                break;
            case 3:
                manageExperience();
                break;
            case 4:
                manageSkills();
                break;
            case 5:
                generateSkillRecommendations();
                break;
            case 6:
                exportToPDF();
                break;
            case 7:
                currentResume = null;
                System.out.println("Returning to Resume Menu...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void loginUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        if (userManager.authenticateUser(userId, password)) {
            currentUser = userManager.getUser(userId);
            System.out.println("Login successful. Welcome, " + currentUser.getName() + "!");
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }
    
    private static void registerUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        scanner.nextLine(); // Consume newline
        
        if (userManager.getUser(userId) != null) {
            System.out.println("User ID already exists. Please choose another one.");
            return;
        }
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        User newUser = new User(userId, password, name, email);
        userManager.addUser(newUser);
        
        System.out.println("Registration successful. You can now login.");
    }
    
    private static void logoutUser() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }
    
    private static void createNewResume() {
        System.out.print("Enter Resume Title (e.g., 'Software Engineer Resume'): ");
        String title = scanner.nextLine();
        
        currentResume = new Resume(title);
        currentUser.addResume(currentResume);
        
        System.out.println("New resume created: " + title);
    }
    
    private static void selectExistingResume() {
        List<Resume> resumes = currentUser.getResumes();
        
        System.out.println("\nYour Resumes:");
        for (int i = 0; i < resumes.size(); i++) {
            System.out.println((i + 1) + ". " + resumes.get(i).getTitle());
        }
        
        System.out.print("Select a resume (1-" + resumes.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= resumes.size()) {
            currentResume = resumes.get(choice - 1);
            System.out.println("Selected: " + currentResume.getTitle());
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private static void deleteResume() {
        List<Resume> resumes = currentUser.getResumes();
        
        System.out.println("\nYour Resumes:");
        for (int i = 0; i < resumes.size(); i++) {
            System.out.println((i + 1) + ". " + resumes.get(i).getTitle());
        }
        
        System.out.print("Select a resume to delete (1-" + resumes.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= resumes.size()) {
            Resume toDelete = resumes.get(choice - 1);
            currentUser.removeResume(toDelete);
            System.out.println("Resume deleted: " + toDelete.getTitle());
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private static void editPersonalInfo() {
        System.out.println("\n===== Personal Information =====");
        
        System.out.print("Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Location (e.g., 'San Francisco, CA'): ");
        String location = scanner.nextLine();
        
        System.out.print("LinkedIn URL: ");
        String linkedin = scanner.nextLine();
        
        System.out.print("GitHub URL: ");
        String github = scanner.nextLine();
        
        currentResume.setPersonalInfo(name, email, phone, location, linkedin, github);
        System.out.println("Personal information updated successfully.");
    }
    
    private static void manageEducation() {
        while (true) {
            System.out.println("\n===== Education Management =====");
            List<Education> educationList = currentResume.getEducationList();
            
            if (!educationList.isEmpty()) {
                System.out.println("\nCurrent Education Entries:");
                for (int i = 0; i < educationList.size(); i++) {
                    Education edu = educationList.get(i);
                    System.out.println((i + 1) + ". " + edu.getDegree() + " - " + edu.getInstitution());
                }
                System.out.println();
            }
            
            System.out.println("1. Add New Education");
            if (!educationList.isEmpty()) {
                System.out.println("2. Edit Existing Education");
                System.out.println("3. Delete Education");
            }
            System.out.println("4. Return to Previous Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addEducation();
                    break;
                case 2:
                    if (!educationList.isEmpty()) {
                        editEducation();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 3:
                    if (!educationList.isEmpty()) {
                        deleteEducation();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addEducation() {
        System.out.println("\n===== Add Education =====");
        
        System.out.print("Degree/Certificate: ");
        String degree = scanner.nextLine();
        
        System.out.print("Institution: ");
        String institution = scanner.nextLine();
        
        System.out.print("Time Period (e.g., '2015-2019'): ");
        String duration = scanner.nextLine();
        
        System.out.print("GPA (e.g., 3.8): ");
        double gpa = getDoubleInput();
        
        Education education = new Education(degree, institution, duration, gpa);
        currentResume.addEducation(education);
        
        System.out.println("Education added successfully.");
    }
    
    private static void editEducation() {
        List<Education> educationList = currentResume.getEducationList();
        
        System.out.print("Select education entry to edit (1-" + educationList.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= educationList.size()) {
            Education education = educationList.get(choice - 1);
            
            System.out.println("\nEditing: " + education.getDegree() + " - " + education.getInstitution());
            
            System.out.print("Degree/Certificate: ");
            String degree = scanner.nextLine();
            education.setDegree(degree);
            
            System.out.print("Institution: ");
            String institution = scanner.nextLine();
            education.setInstitution(institution);
            
            System.out.print("Time Period (e.g., '2015-2019'): ");
            String duration = scanner.nextLine();
            education.setDuration(duration);
            
            System.out.print("GPA (e.g., 3.8): ");
            double gpa = getDoubleInput();
            education.setGpa(gpa);
            
            System.out.println("Education updated successfully.");
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private static void deleteEducation() {
        List<Education> educationList = currentResume.getEducationList();
        
        System.out.print("Select education entry to delete (1-" + educationList.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= educationList.size()) {
            Education education = educationList.get(choice - 1);
            currentResume.removeEducation(education);
            System.out.println("Education entry deleted.");
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private static void manageExperience() {
        while (true) {
            System.out.println("\n===== Experience Management =====");
            List<Experience> experienceList = currentResume.getExperienceList();
            
            if (!experienceList.isEmpty()) {
                System.out.println("\nCurrent Experience Entries:");
                for (int i = 0; i < experienceList.size(); i++) {
                    Experience exp = experienceList.get(i);
                    System.out.println((i + 1) + ". " + exp.getJobTitle() + " - " + exp.getCompany());
                }
                System.out.println();
            }
            
            System.out.println("1. Add New Experience");
            if (!experienceList.isEmpty()) {
                System.out.println("2. Edit Existing Experience");
                System.out.println("3. Delete Experience");
            }
            System.out.println("4. Return to Previous Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addExperience();
                    break;
                case 2:
                    if (!experienceList.isEmpty()) {
                        editExperience();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 3:
                    if (!experienceList.isEmpty()) {
                        deleteExperience();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addExperience() {
        System.out.println("\n===== Add Experience =====");
        
        System.out.print("Job Title: ");
        String jobTitle = scanner.nextLine();
        
        System.out.print("Company: ");
        String company = scanner.nextLine();
        
        System.out.print("Duration (e.g., '2019-Present'): ");
        String duration = scanner.nextLine();
        
        System.out.print("Location (e.g., 'San Francisco, CA'): ");
        String location = scanner.nextLine();
        
        System.out.println("Description (enter multiple lines, type 'END' on a new line when finished):");
        StringBuilder description = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            description.append(line).append("\n");
        }
        
        Experience experience = new Experience(jobTitle, company, duration, 
                                           location, description.toString().trim());
        currentResume.addExperience(experience);
        
        System.out.println("Experience added successfully.");
    }
    
    private static void editExperience() {
        List<Experience> experienceList = currentResume.getExperienceList();
        
        System.out.print("Select experience entry to edit (1-" + experienceList.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= experienceList.size()) {
            Experience experience = experienceList.get(choice - 1);
            
            System.out.println("\nEditing: " + experience.getJobTitle() + " - " + experience.getCompany());
            
            System.out.print("Job Title: ");
            String jobTitle = scanner.nextLine();
            experience.setJobTitle(jobTitle);
            
            System.out.print("Company: ");
            String company = scanner.nextLine();
            experience.setCompany(company);
            
            System.out.print("Duration (e.g., '2019-Present'): ");
            String duration = scanner.nextLine();
            experience.setDuration(duration);
            
            System.out.print("Location (e.g., 'San Francisco, CA'): ");
            String location = scanner.nextLine();
            experience.setLocation(location);
            
            System.out.println("Current Description:");
            System.out.println(experience.getDescription());
            System.out.println("\nNew Description (enter multiple lines, type 'END' on a new line when finished):");
            StringBuilder description = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                description.append(line).append("\n");
            }
            experience.setDescription(description.toString().trim());
            
            System.out.println("Experience updated successfully.");
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private static void deleteExperience() {
        List<Experience> experienceList = currentResume.getExperienceList();
        
        System.out.print("Select experience entry to delete (1-" + experienceList.size() + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= experienceList.size()) {
            Experience experience = experienceList.get(choice - 1);
            currentResume.removeExperience(experience);
            System.out.println("Experience entry deleted.");
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private static void manageSkills() {
        while (true) {
            System.out.println("\n===== Skills Management =====");
            Set<String> skills = currentResume.getSkills();
            
            if (!skills.isEmpty()) {
                System.out.println("\nCurrent Skills:");
                int i = 1;
                for (String skill : skills) {
                    System.out.println(i + ". " + skill);
                    i++;
                }
                System.out.println();
            }
            
            System.out.println("1. Add New Skill");
            if (!skills.isEmpty()) {
                System.out.println("2. Remove Skill");
            }
            System.out.println("3. Return to Previous Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    System.out.print("Enter skill to add: ");
                    String newSkill = scanner.nextLine();
                    currentResume.addSkill(newSkill);
                    System.out.println("Skill added: " + newSkill);
                    break;
                case 2:
                    if (!skills.isEmpty()) {
                        removeSkill();
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void removeSkill() {
        Set<String> skills = currentResume.getSkills();
        String[] skillArray = skills.toArray(new String[0]);
        
        System.out.println("\nSelect skill to remove:");
        for (int i = 0; i < skillArray.length; i++) {
            System.out.println((i + 1) + ". " + skillArray[i]);
        }
        
        System.out.print("Enter choice (1-" + skillArray.length + "): ");
        int choice = getIntInput();
        
        if (choice >= 1 && choice <= skillArray.length) {
            String skillToRemove = skillArray[choice - 1];
            currentResume.removeSkill(skillToRemove);
            System.out.println("Skill removed: " + skillToRemove);
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    private static void generateSkillRecommendations() {
        System.out.println("\n===== AI Skill Recommendations =====");
        
        System.out.println("1. Get recommendations by job role");
        System.out.println("2. Get recommendations by job description");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        SkillRecommendation recommender = new SkillRecommendation();
        List<String> recommendedSkills;
        
        switch (choice) {
            case 1:
                System.out.println("\nAvailable job roles:");
                System.out.println("1. Software Engineer");
                System.out.println("2. Data Scientist");
                System.out.println("3. Web Developer");
                System.out.println("4. Project Manager");
                System.out.print("Select a job role (1-4): ");
                
                int roleChoice = getIntInput();
                String jobRole;
                
                switch (roleChoice) {
                    case 1:
                        jobRole = "Software Engineer";
                        break;
                    case 2:
                        jobRole = "Data Scientist";
                        break;
                    case 3:
                        jobRole = "Web Developer";
                        break;
                    case 4:
                        jobRole = "Project Manager";
                        break;
                    default:
                        System.out.println("Invalid choice. Using 'Software Engineer' as default.");
                        jobRole = "Software Engineer";
                }
                
                recommendedSkills = recommender.getRecommendedSkills(jobRole);
                break;
                
            case 2:
                System.out.println("\nEnter a job description (type 'END' on a new line when finished):");
                StringBuilder description = new StringBuilder();
                String line;
                while (!(line = scanner.nextLine()).equals("END")) {
                    description.append(line).append("\n");
                }
                
                recommendedSkills = recommender.getRecommendedSkillsByDescription(description.toString());
                break;
                
            default:
                System.out.println("Invalid choice. Using job role recommendation as default.");
                recommendedSkills = recommender.getRecommendedSkills("Software Engineer");
        }
        
        System.out.println("\nRecommended Skills:");
        for (int i = 0; i < recommendedSkills.size(); i++) {
            System.out.println((i + 1) + ". " + recommendedSkills.get(i));
        }
        
        System.out.println("\nDo you want to add these skills to your resume?");
        System.out.println("1. Add all skills");
        System.out.println("2. Select specific skills to add");
        System.out.println("3. Don't add any skills");
        System.out.print("Enter your choice: ");
        
        choice = getIntInput();
        switch (choice) {
            case 1:
                for (String skill : recommendedSkills) {
                    currentResume.addSkill(skill);
                }
                System.out.println("All recommended skills added to your resume.");
                break;
            case 2:
                selectSkillsToAdd(recommendedSkills);
                break;
            case 3:
                System.out.println("No skills added.");
                break;
            default:
                System.out.println("Invalid choice. No skills added.");
        }
    }
    
    private static void selectSkillsToAdd(List<String> recommendedSkills) {
        System.out.println("\nSelect skills to add (comma-separated numbers, e.g., '1,3,5'):");
        for (int i = 0; i < recommendedSkills.size(); i++) {
            System.out.println((i + 1) + ". " + recommendedSkills.get(i));
        }
        
        System.out.print("Enter your choices: ");
        String input = scanner.nextLine();
        String[] choices = input.split(",");
        
        int count = 0;
        for (String choice : choices) {
            try {
                int index = Integer.parseInt(choice.trim()) - 1;
                if (index >= 0 && index < recommendedSkills.size()) {
                    currentResume.addSkill(recommendedSkills.get(index));
                    count++;
                }
            } catch (NumberFormatException e) {
                // Skip invalid input
            }
        }
        
        System.out.println(count + " skills added to your resume.");
    }
    
    private static void exportToPDF() {
        System.out.println("\n===== Export to PDF =====");
        
        if (currentResume.getPersonalInfo() == null) {
            System.out.println("Error: Please add personal information before exporting.");
            return;
        }
        
        if (currentResume.getEducationList().isEmpty()) {
            System.out.println("Warning: Your resume has no education entries.");
        }
        
        if (currentResume.getExperienceList().isEmpty()) {
            System.out.println("Warning: Your resume has no experience entries.");
        }
        
        if (currentResume.getSkills().isEmpty()) {
            System.out.println("Warning: Your resume has no skills listed.");
        }
        
        System.out.println("\nSelect a template:");
        System.out.println("1. Modern Template");
        System.out.println("2. Classic Template");
        System.out.print("Enter your choice: ");
        
        int choice = getIntInput();
        ResumeTemplate template;
        
        switch (choice) {
            case 1:
                template = new ModernTemplate();
                break;
            case 2:
                template = new ClassicTemplate();
                break;
            default:
                System.out.println("Invalid choice. Using Modern Template as default.");
                template = new ModernTemplate();
        }
        
        System.out.print("Enter filename for the PDF (without extension): ");
        String filename = scanner.nextLine();
        
        if (filename.isEmpty()) {
            filename = currentResume.getPersonalInfo().getName().replace(" ", "") + "_Resume";
        }
        
        String outputPath = filename + ".pdf";
        
        System.out.println("Generating PDF resume...");
        Thread pdfThread = new Thread(() -> {
            try {
                PDFGenerator pdfGenerator = new PDFGenerator();
                pdfGenerator.generatePDF(currentResume, template, outputPath);
                System.out.println("PDF generated successfully: " + outputPath);
            } catch (Exception e) {
                System.err.println("Error generating PDF: " + e.getMessage());
            }
        });
        pdfThread.start();
        
        System.out.println("PDF generation has started in the background.");
        System.out.println("You can continue working while the PDF is being generated.");
    }
    
    private static int getIntInput() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return value;
        } catch (Exception e) {
            scanner.nextLine(); // Consume invalid input
            return -1;
        }
    }
    
    private static double getDoubleInput() {
        try {
            double value = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            return value;
        } catch (Exception e) {
            scanner.nextLine(); // Consume invalid input
            return 0.0;
        }
    }
}