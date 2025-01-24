package com.biedronka.biedronka_recipes.controller;

import com.biedronka.biedronka_recipes.dto.CommentDTO;
import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Comment;
import com.biedronka.biedronka_recipes.service.ClientService;
import com.biedronka.biedronka_recipes.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ClientService clientService;

    public CommentController(CommentService commentService, ClientService clientService) {
        this.commentService = commentService;
        this.clientService = clientService;
    }

    @GetMapping
    public List<CommentDTO> getAll() {
        return commentService.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CommentDTO getOne(@PathVariable Long id) {
        Comment c = commentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return toDTO(c);
    }

    @PostMapping
    public CommentDTO create(@RequestBody CommentDTO dto) {
        // Odnajdujemy Client
        Client client = clientService.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Comment entity = new Comment();
        entity.setContent(dto.getContent());
        entity.setClient(client);

        Comment saved = commentService.createComment(entity);
        return toDTO(saved);
    }

    @PutMapping("/{id}")
    public CommentDTO update(@PathVariable Long id, @RequestBody CommentDTO dto) {
        Comment existing = commentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        existing.setContent(dto.getContent());
        if (dto.getClientId() != null) {
            Client client = clientService.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            existing.setClient(client);
        }
        Comment updated = commentService.updateComment(existing);
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    // mapowanie
    private CommentDTO toDTO(Comment c) {
        return new CommentDTO(
                c.getId(),
                c.getContent(),
                c.getClient() != null ? c.getClient().getId() : null
        );
    }
}
