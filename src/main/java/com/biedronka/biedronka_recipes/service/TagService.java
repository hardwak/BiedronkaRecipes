package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.dto.tag.TagResponseDTO;
import com.biedronka.biedronka_recipes.repository.TagRepository;
import com.biedronka.biedronka_recipes.utils.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public Map<String, List<TagResponseDTO>> getAllTagsGroupedByType() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toTagResponseDTO)
                .collect(Collectors.groupingBy(TagResponseDTO::type));
    }
}
