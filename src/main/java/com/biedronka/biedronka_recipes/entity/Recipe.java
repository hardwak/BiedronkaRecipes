package com.biedronka.biedronka_recipes.entity;





import jakarta.persistence.*;

@Entity
@Table(name = "Recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "procedure")
    private String procedure;

    // FOREIGN KEY (employee_id) REFERENCES Employees(id)
    @ManyToOne
    @JoinColumn(name = "FK_Pracownik")  // or "employee_id" if you changed the column name
    private Employee employee;

    // bridging np. OpinionsRecipes, CommentsRecipes, RecipesProducts
    // albo relacje do Multimedia

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // ... get/set ...
}

