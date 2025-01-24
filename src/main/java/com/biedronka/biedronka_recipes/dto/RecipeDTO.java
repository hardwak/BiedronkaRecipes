package com.biedronka.biedronka_recipes.dto;

public class RecipeDTO {
    private Long id;
    private String title;
    private String procedure;
    private Long employeeId; // FK do Employee (jeśli w encji jest "employee")

    public RecipeDTO() {
    }

    public RecipeDTO(Long id, String title, String procedure, Long employeeId) {
        this.id = id;
        this.title = title;
        this.procedure = procedure;
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProcedure() {
        return procedure;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
