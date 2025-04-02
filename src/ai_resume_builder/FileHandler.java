package ai_resume_builder;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Handles file operations for the AI Resume Builder application
 * Provides functionality for saving and loading user data, resumes, and exports
 */
public class FileHandler {
    private static final String DATA_DIRECTORY = "data";
    private static final String USERS_DIRECTORY = DATA_DIRECTORY + File.separator + "users";
    private static final String TEMPLATES_DIRECTORY = DATA_DIRECTORY + File.separator + "templates";
    private static final String USER_DATA_FILE = USERS_DIRECTORY + File.separator + "userdata.ser";
    
    /**
     * Initialize the file system by creating necessary directories
     */
    public void initializeFileSystem() throws IOException {
        createDirectoryIfNotExists(DATA_DIRECTORY);
        createDirectoryIfNotExists(USERS_DIRECTORY);
        createDirectoryIfNotExists(TEMPLATES_DIRECTORY);
        
        System.out.println("File system initialized successfully.");
    }
    
    private void createDirectoryIfNotExists(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Created directory: " + dirPath);
        }
    }
    
    /**
     * Save UserManager data containing all users
     * @param userManager The UserManager object to save
     */
    public void saveUserData(UserManager userManager) throws IOException {
        initializeFileSystem();
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(USER_DATA_FILE))) {
            oos.writeObject(userManager);
            System.out.println("User data saved to: " + USER_DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Load UserManager data
     * @return The loaded UserManager object
     */
    public UserManager loadUserData() throws IOException, ClassNotFoundException {
        if (!Files.exists(Paths.get(USER_DATA_FILE))) {
            System.out.println("No existing user data found. Creating new UserManager.");
            return new UserManager();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(USER_DATA_FILE))) {
            UserManager userManager = (UserManager) ois.readObject();
            System.out.println("User data loaded from: " + USER_DATA_FILE);
            return userManager;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading user data: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Save a resume to a file
     * @param resume The resume to save
     * @param username The username who owns the resume
     * @return The path where the file was saved
     */
    public String saveResume(Resume resume, String username) throws IOException {
        String userResumeDir = USERS_DIRECTORY + File.separator + username + File.separator + "resumes";
        createDirectoryIfNotExists(userResumeDir);
        
        String filename = sanitizeFilename(resume.getTitle()) + ".res";
        String filePath = userResumeDir + File.separator + filename;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(resume);
            System.out.println("Resume saved to: " + filePath);
            return filePath;
        } catch (IOException e) {
            System.err.println("Error saving resume: " + e.getMessage());
            throw e;
        }
    }
    
    private String sanitizeFilename(String input) {
        return input.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
    
    /**
     * Load a resume from a file
     * @param filename The resume filename
     * @param username The username who owns the resume
     * @return The loaded Resume object
     */
    public Resume loadResume(String filename, String username) throws IOException, ClassNotFoundException {
        if (!filename.endsWith(".res")) {
            filename += ".res";
        }
        
        String filePath = USERS_DIRECTORY + File.separator + username + File.separator + "resumes" + File.separator + filename;
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            Resume resume = (Resume) ois.readObject();
            System.out.println("Resume loaded from: " + filePath);
            return resume;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading resume: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * List all resumes for a specific user
     * @param username The username whose resumes to list
     * @return A list of resume filenames
     */
    public List<String> listUserResumes(String username) throws IOException {
        List<String> resumeFiles = new ArrayList<>();
        Path userResumesPath = Paths.get(USERS_DIRECTORY, username, "resumes");
        
        if (!Files.exists(userResumesPath)) {
            Files.createDirectories(userResumesPath);
            return resumeFiles;
        }
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(
                userResumesPath, "*.res")) {
            for (Path entry : stream) {
                resumeFiles.add(entry.getFileName().toString());
            }
        } catch (IOException e) {
            System.err.println("Error listing resumes: " + e.getMessage());
            throw e;
        }
        
        return resumeFiles;
    }
    
    /**
     * Delete a resume file
     * @param filename The name of the resume file
     * @param username The username who owns the resume
     * @return true if deletion was successful
     */
    public boolean deleteResume(String filename, String username) {
        if (!filename.endsWith(".res")) {
            filename += ".res";
        }
        
        String filePath = USERS_DIRECTORY + File.separator + username + File.separator + "resumes" + File.separator + filename;
        File file = new File(filePath);
        
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Resume deleted: " + filePath);
            } else {
                System.err.println("Failed to delete resume: " + filePath);
            }
            return deleted;
        } else {
            System.err.println("Resume not found: " + filePath);
            return false;
        }
    }
    
    /**
     * Export a resume as PDF
     * @param resume The resume to export
     * @param template The template to use
     * @param outputFilename The output filename
     * @param username The username who owns the resume
     * @return The path to the exported PDF
     */
    public String exportAsPDF(Resume resume, ResumeTemplate template, String outputFilename, String username) throws Exception {
        if (!outputFilename.endsWith(".pdf")) {
            outputFilename += ".pdf";
        }
        
        String userExportsDir = USERS_DIRECTORY + File.separator + username + File.separator + "exports";
        createDirectoryIfNotExists(userExportsDir);
        
        String outputPath = userExportsDir + File.separator + outputFilename;
        
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generatePDF(resume, template, outputPath);
        
        System.out.println("Resume exported as PDF: " + outputPath);
        return outputPath;
    }
    
    /**
     * Create a backup of a user's data
     * @param username The username to backup
     * @return The path to the backup file
     */
    public String createUserBackup(String username) throws IOException {
        String backupFilename = username + "_backup.zip";
        String backupPath = DATA_DIRECTORY + File.separator + backupFilename;
        Path userPath = Paths.get(USERS_DIRECTORY, username);
        
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupPath))) {
            if (Files.exists(userPath)) {
                Files.walk(userPath)
                     .forEach(path -> {
                         try {
                             if (Files.isDirectory(path)) return;
                             String entryName = userPath.relativize(path).toString().replace("\\", "/");
                             ZipEntry zipEntry = new ZipEntry(entryName);
                             zos.putNextEntry(zipEntry);
                             Files.copy(path, zos);
                             zos.closeEntry();
                         } catch (IOException e) {
                             System.err.println("Error adding file to backup: " + e.getMessage());
                         }
                     });
            }
        }
        
        System.out.println("Backup created for user " + username + ": " + backupPath);
        return backupPath;
    }
    
    /**
     * Restore a user's data from backup
     * @param backupFilename The backup file to restore from
     * @param username The username to restore to
     * @return The number of restored files
     */
    public int restoreUserBackup(String backupFilename, String username) throws IOException {
        if (!backupFilename.endsWith(".zip")) {
            backupFilename += ".zip";
        }
        
        String backupPath = DATA_DIRECTORY + File.separator + backupFilename;
        int restoredCount = 0;
        Path userDir = Paths.get(USERS_DIRECTORY, username);
        
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(backupPath))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                Path filePath = userDir.resolve(zipEntry.getName());
                
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zis, filePath, StandardCopyOption.REPLACE_EXISTING);
                    restoredCount++;
                }
                zis.closeEntry();
            }
        }
        
        System.out.println("Restored " + restoredCount + " files for user " + username);
        return restoredCount;
    }
    
    /**
     * Load a template from the templates directory
     * @param templateName The name of the template
     * @return The loaded ResumeTemplate object
     */
    public ResumeTemplate loadTemplate(String templateName) throws IOException, ClassNotFoundException {
        if (!templateName.endsWith(".template")) {
            templateName += ".template";
        }
        
        String templatePath = TEMPLATES_DIRECTORY + File.separator + templateName;
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(templatePath))) {
            ResumeTemplate template = (ResumeTemplate) ois.readObject();
            System.out.println("Template loaded: " + templatePath);
            return template;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading template: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Save a template to the templates directory
     * @param template The template to save
     * @param templateName The name for the template
     * @return The path where the template was saved
     */
    public String saveTemplate(ResumeTemplate template, String templateName) throws IOException {
        if (!templateName.endsWith(".template")) {
            templateName += ".template";
        }
        
        String templatePath = TEMPLATES_DIRECTORY + File.separator + templateName;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(templatePath))) {
            oos.writeObject(template);
            System.out.println("Template saved to: " + templatePath);
            return templatePath;
        } catch (IOException e) {
            System.err.println("Error saving template: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * List all available resume templates
     * @return A list of template names
     */
    public List<String> listTemplates() throws IOException {
        List<String> templateFiles = new ArrayList<>();
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(
                Paths.get(TEMPLATES_DIRECTORY), "*.template")) {
            for (Path entry : stream) {
                templateFiles.add(entry.getFileName().toString());
            }
        } catch (IOException e) {
            System.err.println("Error listing templates: " + e.getMessage());
            throw e;
        }
        
        return templateFiles;
    }

    // Remaining methods (exportUserToJson, saveLogFile) remain unchanged
}