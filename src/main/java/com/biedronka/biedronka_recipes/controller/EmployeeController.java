package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.EmployeeDTO;
import com.biedronka.biedronka_recipes.entity.Employee;
import com.biedronka.biedronka_recipes.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDTO> getAll() {
        return employeeService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDTO getOne(@PathVariable Long id) {
        Employee e = employeeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return toDTO(e);
    }

    @PostMapping
    public EmployeeDTO create(@RequestBody EmployeeDTO dto) {
        Employee entity = toEntity(dto);
        Employee saved = employeeService.createEmployee(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable Long id, @RequestBody EmployeeDTO dto) {
        Employee existing = employeeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setAccountCreatedDate(dto.getAccountCreatedDate());
        Employee updated = employeeService.updateEmployee(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    // mapowanie
    private EmployeeDTO toDTO(Employee e) {
        return new EmployeeDTO(
                e.getId(),
                e.getEmail(),
                e.getPassword(),
                e.getFirstName(),
                e.getLastName(),
                e.getAccountCreatedDate()
        );
    }

    private Employee toEntity(EmployeeDTO dto) {
        Employee e = new Employee();
        e.setId(dto.getId());
        e.setEmail(dto.getEmail());
        e.setPassword(dto.getPassword());
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setAccountCreatedDate(dto.getAccountCreatedDate());
        return e;
    }
}
