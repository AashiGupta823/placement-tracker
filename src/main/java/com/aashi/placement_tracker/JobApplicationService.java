package com.aashi.placement_tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository repository;

    // Save new application
    public JobApplication addApplication(JobApplication application) {
        return repository.save(application);
    }

    // Get all applications
    public List<JobApplication> getAllApplications() {
        return repository.findAll();
    }

    // Delete application
    public void deleteApplication(Long id) {
        repository.deleteById(id);
    }

    // Update status
    public JobApplication updateStatus(Long id, String status) {
        JobApplication app = repository.findById(id).get();
        app.setStatus(status);
        return repository.save(app);
    }
}