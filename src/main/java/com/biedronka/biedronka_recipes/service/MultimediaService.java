package com.biedronka.biedronka_recipes.service;



import com.biedronka.biedronka_recipes.entity.Multimedia;
import com.biedronka.biedronka_recipes.repository.MultimediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultimediaService {

    private final MultimediaRepository multimediaRepository;

    public MultimediaService(MultimediaRepository multimediaRepository) {
        this.multimediaRepository = multimediaRepository;
    }

    public List<Multimedia> findAll() {
        return multimediaRepository.findAll();
    }

    public Optional<Multimedia> findById(Long id) {
        return multimediaRepository.findById(id);
    }

    public Multimedia createMultimedia(Multimedia multimedia) {
        return multimediaRepository.save(multimedia);
    }

    public Multimedia updateMultimedia(Multimedia multimedia) {
        return multimediaRepository.save(multimedia);
    }

    public void deleteMultimedia(Long id) {
        multimediaRepository.deleteById(id);
    }
}

