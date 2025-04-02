package ai_resume_builder;

import java.io.FileWriter;
import java.io.IOException;

public class PDFGenerator {
    
    public void generatePDF(Resume resume, ResumeTemplate template, String outputPath) throws Exception {
        // This implementation uses iText library
        // In a real application, you would need to include the iText library in your project
        
        // Here's a simplified version of what the real implementation would look like
        System.out.println("Creating PDF document: " + outputPath);
        
        // Generate HTML content based on the selected template
        String htmlContent = generateHtmlContent(resume, template);
        
        // In real implementation, the following code would use iText to convert HTML to PDF
        // Document document = new Document(PageSize.A4);
        // PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        // document.open();
        // HTMLWorker htmlWorker = new HTMLWorker(document);
        // htmlWorker.parse(new StringReader(htmlContent));
        // document.close();
        
        // For now, we'll just save the HTML to a file to simulate PDF generation
        saveHtmlToFile(htmlContent, outputPath.replace(".pdf", ".html"));
        
        System.out.println("PDF generation complete!");
    }
    
    private String generateHtmlContent(Resume resume, ResumeTemplate template) {
        StringBuilder htmlBuilder = new StringBuilder();
        
        // Start HTML document
        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html>\n");
        htmlBuilder.append("<head>\n");
        htmlBuilder.append("<title>").append(resume.getTitle()).append("</title>\n");
        htmlBuilder.append("<style>\n");
        htmlBuilder.append("body { font-family: Arial, sans-serif; margin: 40px; }\n");
        htmlBuilder.append("</style>\n");
        htmlBuilder.append("</head>\n");
        htmlBuilder.append("<body>\n");
        
        // Add header with personal information
        htmlBuilder.append(template.formatHeader(resume.getPersonalInfo()));
        
        // Add experience section
        htmlBuilder.append(template.formatExperience(resume.getExperienceList()));
        
        // Add education section
        htmlBuilder.append(template.formatEducation(resume.getEducationList()));
        
        // Add skills section
        htmlBuilder.append(template.formatSkills(resume.getSkills()));
        
        // Close HTML document
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>\n");
        return htmlBuilder.toString();
        }

        private void saveHtmlToFile(String htmlContent, String filePath) throws IOException {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(htmlContent);
            } catch (IOException e) {
                System.err.println("Error saving HTML file: " + e.getMessage());
                throw e;
            }
            System.out.println("HTML file saved to: " + filePath);
        }
        }