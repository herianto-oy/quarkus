package org.acme.employee;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepo;

    @Transactional
    @Override
    public List<EmployeeEntity> createBulk(FileUpload fileUpload) {
        List<EmployeeEntity> data = null;
        if(fileUpload.contentType().equals("text/csv")){
            data = EmployeeParser.parserCsv(fileUpload);
        } else {
            data = EmployeeParser.parserExcel(fileUpload);
        }
        
        employeeRepo.persist(data);
        return data;
    }

    @Override
    public List<EmployeeEntity> getAll() {
        return employeeRepo.findAll().list();
    }
    
}
