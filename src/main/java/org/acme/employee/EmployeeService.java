package org.acme.employee;

import java.util.List;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface EmployeeService {
    public List<EmployeeEntity> createBulk(FileUpload fileUpload);
    public List<EmployeeEntity> getAll();
}
