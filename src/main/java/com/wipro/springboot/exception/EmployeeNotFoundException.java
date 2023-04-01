package com.wipro.springboot.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("could not found employee for this id " +id);
    }
}
