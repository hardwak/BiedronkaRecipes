package com.biedronka.biedronka_recipes.service;

import com.biedronka.biedronka_recipes.entity.Client;
import com.biedronka.biedronka_recipes.entity.Product;
import com.biedronka.biedronka_recipes.entity.ProductClient;
import com.biedronka.biedronka_recipes.repository.ClientRepository;
import com.biedronka.biedronka_recipes.repository.ProductClientRepository;
import com.biedronka.biedronka_recipes.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductClientService {

    private final ProductClientRepository productClientRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public ProductClientService(ProductClientRepository productClientRepository,
                                ProductRepository productRepository,
                                ClientRepository clientRepository) {
        this.productClientRepository = productClientRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public List<ProductClient> findAll() {
        return productClientRepository.findAll();
    }

    public Optional<ProductClient> findById(Long id) {
        return productClientRepository.findById(id);
    }

    public ProductClient create(Long productId, Long clientId,
                                Double amount, LocalDate lastCheckDate) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        ProductClient pc = new ProductClient();
        pc.setProduct(product);
        pc.setClient(client);
        pc.setAmount(amount != null ? amount : 1.0);
        pc.setLastCheckDate(lastCheckDate != null ? lastCheckDate : LocalDate.now());
        return productClientRepository.save(pc);
    }

    public void delete(Long id) {
        productClientRepository.deleteById(id);
    }
}


