package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
