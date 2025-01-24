package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
}

