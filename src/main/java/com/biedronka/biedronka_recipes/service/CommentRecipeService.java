package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Comment;
import com.biedronka.biedronka_recipes.entity.CommentRecipe;
import com.biedronka.biedronka_recipes.entity.Recipe;
import com.biedronka.biedronka_recipes.repository.CommentRecipeRepository;
import com.biedronka.biedronka_recipes.repository.CommentRepository;
import com.biedronka.biedronka_recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentRecipeService {

    private final CommentRecipeRepository commentRecipeRepository;
    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;

    public CommentRecipeService(CommentRecipeRepository commentRecipeRepository,
                                CommentRepository commentRepository,
                                RecipeRepository recipeRepository) {
        this.commentRecipeRepository = commentRecipeRepository;
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
    }

    public List<CommentRecipe> findAll() {
        return commentRecipeRepository.findAll();
    }

    public Optional<CommentRecipe> findById(Long id) {
        return commentRecipeRepository.findById(id);
    }

    public CommentRecipe create(Long commentId, Long recipeId, LocalDate date) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        CommentRecipe cr = new CommentRecipe();
        cr.setComment(comment);
        cr.setRecipe(recipe);
        cr.setDate(date != null ? date : LocalDate.now());
        return commentRecipeRepository.save(cr);
    }

    public void delete(Long id) {
        commentRecipeRepository.deleteById(id);
    }
}

