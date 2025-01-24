package com.biedronka.biedronka_recipes.dto;

public class CommentDTO {
    private Long id;
    private String content;
    private Long clientId; // FK do Client (jeśli tak w encji)

    public CommentDTO() {
    }

    public CommentDTO(Long id, String content, Long clientId) {
        this.id = id;
        this.content = content;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}

