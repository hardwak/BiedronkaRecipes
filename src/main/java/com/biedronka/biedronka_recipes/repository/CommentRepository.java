package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
