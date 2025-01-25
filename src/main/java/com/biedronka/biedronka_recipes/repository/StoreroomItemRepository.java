package com.biedronka.biedronka_recipes.repository;

import com.biedronka.biedronka_recipes.entity.StoreroomItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreroomItemRepository extends JpaRepository<StoreroomItem, Long> {


}
