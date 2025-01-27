package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.dto.tag.TagResponseDTO;
import com.biedronka.biedronka_recipes.entity.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagMapper {

    public TagResponseDTO toTagResponseDTO(Tag tag) {
        return new TagResponseDTO(tag.getId(), tag.getTagType(), tag.getName());
    }
}
