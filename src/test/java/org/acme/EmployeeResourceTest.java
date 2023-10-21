package org.acme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.acme.employee.EmployeeEntity;
import org.acme.employee.EmployeeService;
import org.acme.employee.EmployeeServiceImpl;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class EmployeeResourceTest {

    @Test
    public void testCreateBulk() {
        FileUpload fileUpload = mockFileUpload("test.csv");
        EmployeeService service = mockEmployeeService();

        List<EmployeeEntity> createdEmployees = service.createBulk(fileUpload);

        assertNotNull(createdEmployees);
        assertEquals(3, createdEmployees.size()); // Assuming you expect 3 employees in the CSV file.
    }

    @Test
    public void testGetAll() {
        EmployeeService service = mockEmployeeService();
        List<EmployeeEntity> allEmployees = service.getAll();
        assertNotNull(allEmployees);
    }


    private FileUpload mockFileUpload(String fileName) {
        FileUpload fileUpload = mock(FileUpload.class);
        when(fileUpload.contentType()).thenReturn("text/csv");
        when(fileUpload.fileName()).thenReturn(fileName);

        return fileUpload;
    }

    private EmployeeService mockEmployeeService(){
        EmployeeService service = mock(EmployeeServiceImpl.class);
        when(service.createBulk(Mockito.any(FileUpload.class))).thenReturn(getListMock());
        when(service.getAll()).thenReturn(getListMock());
        return service;
    }


    private List<EmployeeEntity> getListMock(){
        List<EmployeeEntity> result = new ArrayList<>();

        EmployeeEntity emp1 = new EmployeeEntity();
        emp1.setId(1L);
        emp1.setName("heri");
        emp1.setEmail("herianto@email.com");
        result.add(emp1);

        EmployeeEntity emp2 = new EmployeeEntity();
        emp2.setId(2L);
        emp2.setName("bejo");
        emp2.setEmail("bejo@email.com");
        result.add(emp2);

        EmployeeEntity emp3 = new EmployeeEntity();
        emp3.setId(3L);
        emp3.setName("agus");
        emp3.setEmail("agus@email.com");
        result.add(emp3);

        return result;
    }
}
