package com.biedronka.biedronka_recipes.service;



import com.biedronka.biedronka_recipes.entity.Opinion;
import com.biedronka.biedronka_recipes.repository.OpinionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OpinionService {

    private final OpinionRepository opinionRepository;

    public OpinionService(OpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
    }

    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    public Optional<Opinion> findById(Long id) {
        return opinionRepository.findById(id);
    }

    public Opinion createOpinion(Opinion opinion) {
        // np. walidacja: 1 <= stars <= 5
        return opinionRepository.save(opinion);
    }

    public Opinion updateOpinion(Opinion opinion) {
        return opinionRepository.save(opinion);
    }

    public void deleteOpinion(Long id) {
        opinionRepository.deleteById(id);
    }
}

