package com.biedronka.biedronka_recipes.dto;

public class OpinionDTO {
    private Long id;
    private Integer stars;
    private Long clientId;  // FK do Client

    public OpinionDTO() {
    }

    public OpinionDTO(Long id, Integer stars, Long clientId) {
        this.id = id;
        this.stars = stars;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public Integer getStars() {
        return stars;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
