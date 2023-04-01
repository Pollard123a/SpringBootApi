package com.wipro.springboot.controller;

import com.wipro.springboot.exception.EmployeeNotFoundException;
import com.wipro.springboot.model.Employee;
import com.wipro.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @RequestMapping(value = "/employees")
    private List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/employees/{id}")
    private Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
    }

    @PostMapping(value = "/employees")
    private Employee addEmployee(@RequestBody Employee employee) { //change into json format
        System.out.println(employee);
        return employeeRepository.save(employee);
    }

    @PutMapping(value = "/employees/{id}")
    private ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee existEmployee = getEmployeeById(id);
            employeeRepository.save(employee);
            return  new ResponseEntity<Employee>(employee, HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<Employee>(employee, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/employees/{id}")
    private String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "Successfully deleted the employee with id "+ id;
    }
}
