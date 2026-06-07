package com.aashi.placement_tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    @Autowired
    private JobApplicationService service;

    @Autowired
    private ResumeAnalyzerService analyzerService;

    @PostMapping
    public JobApplication addApplication(@RequestBody JobApplication application) {
        return service.addApplication(application);
    }

    @GetMapping
    public List<JobApplication> getAllApplications() {
        return service.getAllApplications();
    }

    @PutMapping("/{id}/status")
    public JobApplication updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable Long id) {
        service.deleteApplication(id);
        return "Deleted successfully!";
    }

    @PostMapping("/analyze")
    public Map<String, Object> analyzeResume(
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("jobDescription") String jobDescription) {
        try {
            return analyzerService.analyzeResume(resume, jobDescription);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return error;
        }
    }
}