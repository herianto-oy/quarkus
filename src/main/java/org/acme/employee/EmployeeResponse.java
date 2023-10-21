package org.acme.employee;

import java.util.List;

public class EmployeeResponse {
    private Integer code;
    private String message;
    private List<EmployeeEntity> data;

    public EmployeeResponse() {
    
    }

    public EmployeeResponse(Integer code, String message, List<EmployeeEntity> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<EmployeeEntity> getData() {
        return data;
    }
    public void setData(List<EmployeeEntity> data) {
        this.data = data;
    }
}
