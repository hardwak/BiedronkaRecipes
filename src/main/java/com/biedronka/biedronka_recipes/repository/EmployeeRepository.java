package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // np. do wyszukiwania po emailu:

}
