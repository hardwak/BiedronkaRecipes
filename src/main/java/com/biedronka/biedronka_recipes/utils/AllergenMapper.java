package com.biedronka.biedronka_recipes.utils;

import com.biedronka.biedronka_recipes.dto.allergen.ClientAllergenResponseDTO;
import com.biedronka.biedronka_recipes.entity.Allergen;
import com.biedronka.biedronka_recipes.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergenMapper {

    public List<ClientAllergenResponseDTO> toClientAllergenResponseDTOList(List<Allergen> allergenList, Client client) {
        return allergenList.stream()
                .map((allergen) -> toClientAllergenResponseDTO(allergen, client))
                .toList();
    }

    public ClientAllergenResponseDTO toClientAllergenResponseDTO(Allergen allergen, Client client) {
        return new ClientAllergenResponseDTO(
                allergen.getId(),
                allergen.getName(),
                client.getAllergens().contains(allergen)
        );
    }
}
